insightApp
		.controller(
				'typeCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, typeService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.typeCtrl = {};
					// 一覧モジュール変数
					$scope.typeCtrl.table = {};
					$scope.typeCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.typeCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if ($scope.typeCtrl.seesionUserID == item.userID) {
												item.checkDisable = true;
											} else {
												item.select = $scope.typeCtrl.table.checkboxes.checked;
											}

										});
					}
					$scope.typeCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					$scope.typeCtrl.type = $("#type").val();
					if ($scope.typeCtrl.type == "1") {
						$scope.typeCtrl.addBtnDisabled = true;
						$scope.typeCtrl.table.delBtnDisabled = true;
					}
					$scope.typeCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.typeCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.typeCtrl.table.checkboxes.checked;
										});
						console.log($scope.typeCtrl.table.checkboxes);
					}
					if ($("#type").val() == "1") {
						$scope.typeCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "typeCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.typeCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "typeCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 一括削除ボタンを押下時、
					$scope.typeCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";

						angular.forEach($scope.typeCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});
						if (flg == "0") {
							swal("種別データを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択した種別データをを削除しますか？",
								text : "",
								type : "warning",
								showCancelButton : true,
								confirmButtonColor : "#DD6B55",
								confirmButtonText : "確認",
								cancelButtonText : "キャンセル",
								closeOnConfirm : true,
								closeOnCancel : true
							};
							swal(
									params2,
									function(isConfirm) {
										if (isConfirm) {
											// 一括削除データ取得
											var url = "./../protected/contentsType/deleteButton";
											var data = $scope.typeCtrl.table.sortAndFilter
													.settings().dataset;
											var data = typeService
													.setListDelete(
															url,
															{
																'contentsTypeInitList' : data
															});
											data
													.then(
															function(r) {
																if (r.data.resultStatus == "NG") {
																	var codeList = strSpliceService
																			.resultCodeSplice(
																					r.data.messages,
																					"code");
																	var params = {
																		title : insightService
																				.getMessageData("applyStatusMessage")[codeList[0]],
																		text : "",
																		type : "warning",
																		showCancelButton : true,
																		confirmButtonColor : "#DD6B55",
																		confirmButtonText : "更新",
																		cancelButtonText : "キャンセル",
																		closeOnConfirm : true,
																		closeOnCancel : true
																	};
																	swal(
																			params,
																			function(
																					isConfirm) {
																				if (isConfirm) {
																					loadData(deferred);
																				} else {
																					deferred
																							.resolve("resolve");
																				}
																			});
																} else {
																	loadData(deferred);
																}
															}, function(e) {
															});
										} else {
											deferred.resolve("resolve");
										}
									});
						}
						return deferred.promise;
					}

					// 新規登録ボタン
					// 新規登録ボタンを押下時、
					$scope.typeCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'typeDetailPopup.html',
							controller : 'modalInstanceCtrl9',
							backdrop : 'static',
							size : size,
							resolve : {
								key : function() {
									return {
										'modeType' : "1", // 新規登録場合
										'_id' : ""
									};
								}
							}
						});

						modalInstance.result.then(function(result) {
							if (result.appCD != null) {
								loadData();
							}

						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}

					$scope.typeCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'typeDetailPopup.html',
							controller : 'modalInstanceCtrl9',
							backdrop : 'static',
							size : size,
							resolve : {
								key : function() {
									return {
										'modeType' : "2", // 編集場合
										'_id' : item._id
									};
								}
							}
						});

						modalInstance2.result.then(function(result) {
							if (result.appCD != null) {
								loadData();
							}
						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					};

					$scope.$on();

					// 初期化データを取得
					function loadData(deferred) {
						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = typeService.setListDelete(url, data);
						// 一覧初期データ取得
						var url = "./../protected/contentsType/contentsTypeList";

						$scope.typeCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = typeService.getListData(url, params);
						data
								.then(
										function(r) {
											if (r.data.resultStatus == "NG") {
												var codeList = strSpliceService
														.resultCodeSplice(
																r.data.messages,
																"code");
												swal(
														insightService
																.getMessageData("applyStatusMessage")[codeList[0]],
														"", "error");
												if (typeof deferred != "undefined") {
													deferred.resolve("resolve");
													if (codeList[0] == "e.contentsType.1001") {
														$scope.typeCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.contentsTypeInitList;
														$scope.typeCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												$scope.typeCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.contentsTypeInitList;
												$scope.typeCtrl.table.sortAndFilter
														.reload();
												if (typeof deferred != "undefined") {
													deferred.resolve("resolve");
												}
											}
										}, function(e) {
											console.log(e);
										});
					}
					loadData();
				})
		.controller(
				'modalInstanceCtrl9',
				function($scope, $uibModalInstance, key, typeService,
						insightService, strSpliceService) {
					$scope.modalInstanceCtrl9 = {};
					$scope.SelectedCollection = {};
					var url = "./../protected/contentsType/contentsAppCDList";
					var params = {};
					var data = typeService.getListData(url, params);
					data
							.then(
									function(r) {
										if (r.data.resultStatus == "NG") {
											var codeList = strSpliceService
													.resultCodeSplice(
															r.data.messages,
															"code");
											swal(
													insightService
															.getMessageData("applyStatusMessage")[codeList[0]],
													"", "error");

										} else {
											$scope.modalInstanceCtrl9.appCDList = r.data.resultData.appCDList;
										}
									}, function(e) {
										console.log(e);
									});
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = typeService.setListDelete(url, data);
					if ($("#type").val() == "1") {
						$scope.modalInstanceCtrl9.UpdbtnDisabled = true;
					}
					if (key.modeType == "1") {
						$scope.modalInstanceCtrl9.title = "コンテンツ種別登録";
						$scope.modalInstanceCtrl9.button = "登録";
						$scope.modalInstanceCtrl9.typeName = "";
						$scope.modalInstanceCtrl9.appCD = "";
						$scope.modalInstanceCtrl9.typeCD = "";
						$scope.modalInstanceCtrl9.select = false;
						$scope.modalInstanceCtrl9.input = true;
						$scope.modalInstanceCtrl9.orderTable = true;
						$scope.modalInstanceCtrl9.order = true;
					} else {
						$scope.modalInstanceCtrl9.title = "コンテンツ種別詳細";
						$scope.modalInstanceCtrl9.button = "更新";
						$scope.modalInstanceCtrl9.appCDDisabled = true;
						$scope.modalInstanceCtrl9.typeCDDisabled = true;
						$scope.modalInstanceCtrl9.select = true;
						$scope.modalInstanceCtrl9.input = false;
						var appCD = "";

						var url = "./../protected/contentsType/contentsTypeDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = typeService.getDetailData(url, params);

						data
								.then(
										function(r) {
											if (r.data.resultStatus != "OK") {
												var codeList1 = strSpliceService
														.resultCodeSplice(
																r.data.messages,
																"code");
												var params = {
													title : insightService
															.getMessageData("applyStatusMessage")[codeList1[0]],
													text : "",
													type : "error",
													showCancelButton : false,
													closeOnConfirm : true
												};
												swal(params);
												$uibModalInstance.close({});
											} else {
												$scope.modalInstanceCtrl9.typeCD = r.data.resultData.typeCD;
												$scope.modalInstanceCtrl9.appCD = r.data.resultData.appCD;
												$scope.modalInstanceCtrl9.typeName = r.data.resultData.typeName;
												appCD = r.data.resultData.appCD
														.split("：")[0];
												typeCD = r.data.resultData.typeCD;
												var url = "./../protected/contentsType/contentsTypeOrderID";
												var params = {
													"appCD" : appCD,
													"typeCD" : typeCD
												};
												var data = typeService
														.setListDelete(url,
																params);
												data
														.then(
																function(r) {
																	if (r.data.resultStatus != "OK") {
																		var codeList1 = strSpliceService
																				.resultCodeSplice(
																						r.data.messages,
																						"code");
																		var params = {
																			title : insightService
																					.getMessageData("applyStatusMessage")[codeList1[0]],
																			text : "",
																			type : "error",
																			showCancelButton : false,
																			closeOnConfirm : true
																		};
																		swal(params);
																		$uibModalInstance
																				.close({});
																	} else {
																		$scope.orderIDList = r.data.resultData.contentsInitList;
																	}
																},
																function(e) {
																	console
																			.log(e);
																});
											}
										}, function(e) {
											console.log(e);
										});

					}
					$scope.modalInstanceCtrl9.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl9.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

					// 登録／更新ボタンを押す
					$scope.modalInstanceCtrl9.dataUpd = function() {
						$scope.modalInstanceCtrl9.UpdbtnDisabled = true;
						
						if ($scope.modalInstanceCtrl9.appCD == null
								|| $scope.modalInstanceCtrl9.appCD == "") {
							swal("アプリケーションコードを選択してください。", "", "warning");
							$scope.modalInstanceCtrl9.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl9.typeCD == null
								|| $scope.modalInstanceCtrl9.typeCD == "") {
							swal("コンテンツ種別コード を入力してください。", "", "warning");
							$scope.modalInstanceCtrl9.UpdbtnDisabled = false;
							return;
						}
						var reg = new RegExp('[a-zA-Z0-9]');
						if(!reg.test($scope.modalInstanceCtrl9.typeCD)){
							swal("コンテンツ種別コード を入力してください。", "", "warning");
							$scope.modalInstanceCtrl9.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl9.typeName == null
								|| $scope.modalInstanceCtrl9.typeName == "") {
							swal("コンテンツ種別名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl9.UpdbtnDisabled = false;
							return;
						}
						var reg = new RegExp('[1-9][0-9]?[0-9]?');
						var appCD = $scope.modalInstanceCtrl9.appCD.split("：")[0];
						var selected = $scope.SelectedCollection;
						var index = "";
						var arr = new Array();
						for ( var obj in selected) {
							if(!reg.test(selected[obj])&& !selected[obj]==""){
								swal("表示順は半角数字（1～999）で入力してください。", "", "warning");
								$scope.modalInstanceCtrl9.UpdbtnDisabled = false;
								return;
							}
							var d = selected[obj];
							if(selected[obj]==null || selected[obj]==""){
								d=0;
							}
							arr.push(obj+":"+d);
						}
						
						var url = "./../protected/contentsType/contentsTypeUpd";
						var data = {
							'_id' : key._id,
							"modeType" : key.modeType,
							'appCD' : appCD,
							'typeCD' : $scope.modalInstanceCtrl9.typeCD,
							'typeName' : $scope.modalInstanceCtrl9.typeName
						};

						var data = typeService.dataUpd(url, data);
						data
								.then(
										function(r) {
											if (r.data.resultStatus != "OK") {
												var codeList2 = strSpliceService
														.resultCodeSplice(
																r.data.messages,
																"code");
												var params = {
													title : insightService
															.getMessageData("applyStatusMessage")[codeList2[0]],
													text : "",
													type : "error",
													showCancelButton : false,
													closeOnConfirm : true
												};
												swal(params);
												$scope.modalInstanceCtrl9.UpdbtnDisabled = false;
											} else {
												var url = "./../protected/contentsType/contentsTypeOrderIDCheck";
												var data = {
													'appCD' : appCD,
													'typeCD' : $scope.modalInstanceCtrl9.typeCD,
													'orderIDArr' : arr
												};
												var data = typeService.dataUpd(
														url, data);
												data
														.then(
																function(r) {
																	if (r.data.resultStatus != "OK") {
																		var codeList2 = strSpliceService
																				.resultCodeSplice(
																						r.data.messages,
																						"code");
																		var params = {
																			title : insightService
																					.getMessageData("applyStatusMessage")[codeList2[0]],
																			text : "",
																			type : "error",
																			showCancelButton : false,
																			closeOnConfirm : true
																		};
																		swal(params);
																		$scope.modalInstanceCtrl9.UpdbtnDisabled=false;
																	} else {
																		if (key.modeType == "2") {
																			swal(
																					"更新しました。",
																					"",
																					"success");
																		} else {
																			swal(
																					"登録しました。",
																					"",
																					"success");
																		}

																		$uibModalInstance
																				.close({
																					'appCD' : appCD,
																					'typeCD' : $scope.modalInstanceCtrl9.typeCD,
																					'contentsID' : obj,
																					'orderID' : selected[obj]
																				});
																	}
																},
																function(e) {
																	console
																			.log(e);
																});
											}
										}, function(e) {
											console.log(e);
										});

					}

				})