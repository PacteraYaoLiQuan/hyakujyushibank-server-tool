insightApp
		.controller(
				'applicationCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, applicationListService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.applicationCtrl = {};
					// 一覧モジュール変数
					$scope.applicationCtrl.table = {};
					$scope.applicationCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.applicationCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if ($scope.applicationCtrl.seesionUserID == item.userID) {
												item.checkDisable = true;
											} else {
												item.select = $scope.applicationCtrl.table.checkboxes.checked;
											}

										});
					}
					$scope.applicationCtrl.table.userFlagList = insightService
							.getInsightData("userFlagData");
					$scope.applicationCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					$scope.applicationCtrl.application = $("#application")
							.val();
					if ($scope.applicationCtrl.application == "1") {
						$scope.applicationCtrl.addBtnDisabled = true;
						$scope.applicationCtrl.table.delBtnDisabled = true;
					}
					$scope.applicationCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.applicationCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.applicationCtrl.table.checkboxes.checked;
										});
						console.log($scope.applicationCtrl.table.checkboxes);
					}
					if ($("#application").val() == "1") {
						$scope.applicationCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "applicationCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.applicationCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "applicationCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 一括削除ボタンを押下時、
					$scope.applicationCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";

						angular.forEach(
								$scope.applicationCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});
						if (flg == "0") {
							swal("アプリケージョンデータを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択したアプリケージョンデータを削除しますか？",
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
											var url = "./../protected/contentsApp/deleteButton";
											var data = $scope.applicationCtrl.table.sortAndFilter
													.settings().dataset;
											var data = applicationListService
													.setListDelete(
															url,
															{
																'contentsAppInitList' : data
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
					$scope.applicationCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'applicationDetailPopup.html',
							controller : 'modalInstanceCtrl8',
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

					// 権限詳細画面を呼び出す
					$scope.applicationCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'applicationDetailPopup.html',
							controller : 'modalInstanceCtrl8',
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
						var data = applicationListService.setListDelete(url,
								data);
						// 一覧初期データ取得
						var url = "./../protected/contentsApp/contentsAppList";

						$scope.applicationCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = applicationListService.getListData(url,
								params);
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
													if (codeList[0] == "e.contentsApp.1002") {
														$scope.applicationCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.contentsAppInitList;
														$scope.applicationCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												$scope.applicationCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.contentsAppInitList;
												$scope.applicationCtrl.table.sortAndFilter
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
				'modalInstanceCtrl8',
				function($scope, $uibModalInstance, key,
						applicationListService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrl8 = {};

					var url = "./../protected/contentsApp/contentsVersion";
					var params = {};
					var data = applicationListService.getListData(url, params);
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
											$scope.modalInstanceCtrl8.versionList = r.data.resultData.version;
										}
									}, function(e) {
										console.log(e);
									});

					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = applicationListService.setListDelete(url, data);
					if ($("#application").val() == "1") {
						$scope.modalInstanceCtrl8.UpdbtnDisabled = true;
					}
					if (key.modeType == "1") {
						$scope.modalInstanceCtrl8.title = "アプリケーション登録";
						$scope.modalInstanceCtrl8.button = "登録";
						$scope.modalInstanceCtrl8.appName = "";
						$scope.modalInstanceCtrl8.appCD = "";
						$scope.modalInstanceCtrl8.userFlag = 1;
					} else {
						$scope.modalInstanceCtrl8.title = "アプリケーション詳細";
						$scope.modalInstanceCtrl8.button = "更新";
						$scope.modalInstanceCtrl8.appCDDisabled = true;
						var url = "./../protected/contentsApp/contentsAppDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = applicationListService.getDetailData(url,
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
												$uibModalInstance.close({});
											} else {
												$scope.modalInstanceCtrl8.userFlag = r.data.resultData.userFlag;
												$scope.modalInstanceCtrl8.appCD = r.data.resultData.appCD;
												$scope.modalInstanceCtrl8.appName = r.data.resultData.appName;
											}
										}, function(e) {
											console.log(e);
										});
					}

					$scope.modalInstanceCtrl8.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl8.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

					// 登録／更新ボタンを押す
					$scope.modalInstanceCtrl8.dataUpd = function() {
						$scope.modalInstanceCtrl8.UpdbtnDisabled = true;
						var reg = new RegExp('[a-zA-Z0-9]');
						if(!reg.test($scope.modalInstanceCtrl8.appCD)){
							swal("アプリケーションコードを入力してください。", "", "warning");
							$scope.modalInstanceCtrl8.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl8.appCD == null
								|| $scope.modalInstanceCtrl8.appCD == "") {
							swal("アプリケーションコードを入力してください。", "", "warning");
							$scope.modalInstanceCtrl8.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl8.appName == null
								|| $scope.modalInstanceCtrl8.appName == "") {
							swal("アプリケーション名称を入力してください。", "", "warning");
							$scope.modalInstanceCtrl8.UpdbtnDisabled = false;
							return;
						}

						var url = "./../protected/contentsApp/contentsAppUpd";
						var data = {
							'_id' : key._id,
							"modeType" : key.modeType,
							'appCD' : $scope.modalInstanceCtrl8.appCD,
							'appName' : $scope.modalInstanceCtrl8.appName,
							'userFlag' : $scope.modalInstanceCtrl8.userFlag
						};
						var data = applicationListService.dataUpd(url, data);
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
												$scope.modalInstanceCtrl8.UpdbtnDisabled = false;
											} else {
												if (key.modeType == "2") {
													swal("更新しました。", "",
															"success");
												} else {
													swal("登録しました。", "",
															"success");
												}

												$uibModalInstance
														.close({
															'appCD' : $scope.modalInstanceCtrl8.appCD,
															'appName' : $scope.modalInstanceCtrl8.appName,
															'userFlag' : $scope.modalInstanceCtrl8.userFlag
														});
											}
										}, function(e) {
											console.log(e);
										});
					}

				})