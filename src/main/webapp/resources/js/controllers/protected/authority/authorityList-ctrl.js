insightApp
		.controller(
				'authorityListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, authorityListService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.authorityListCtrl = {};

					// 一覧モジュール変数
					$scope.authorityListCtrl.table = {};
					$scope.authorityListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.authorityListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if ($scope.authorityListCtrl.seesionUserID == item.userID) {
												item.checkDisable = true;
											} else {
												item.select = $scope.authorityListCtrl.table.checkboxes.checked;
											}

										});
						console.log($scope.userListCtrl.table.checkboxes);
					}

					$scope.authorityListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					$scope.authorityListCtrl.user = $("#authorityList").val();
					if ($scope.authorityListCtrl.user == "1") {
						$scope.authorityListCtrl.addBtnDisabled = true;
						$scope.authorityListCtrl.table.delBtnDisabled = true;
					}
					$scope.authorityListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.authorityListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.authorityListCtrl.table.checkboxes.checked;
										});
						console.log($scope.authorityListCtrl.table.checkboxes);
					}
					if ($("#authorityList").val() == "1") {
						$scope.authorityListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "authorityListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.authorityListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "authorityListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 一括削除ボタンを押下時、
					$scope.authorityListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";

						angular.forEach(
								$scope.authorityListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});
						if (flg == "0") {
							swal("権限を選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択した権限を削除しますか？",
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
											var url = "./../protected/authority/deleteButton";
											var data = $scope.authorityListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = authorityListService
													.setListDelete(url, {
														'deleteList' : data
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
					$scope.authorityListCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'authorityDetailPopup.html',
							controller : 'modalInstanceCtrl4',
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
							if (result.updFlg != null) {
								loadData();
							}
						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}

					// 権限詳細画面を呼び出す
					$scope.authorityListCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'authorityDetailPopup.html',
							controller : 'modalInstanceCtrl4',
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
							if (result.updFlg != null) {
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
						var data = authorityListService.setListDelete(url, data);
						// 一覧初期データ取得
						var url = "./../protected/authority/authorityList";

						$scope.authorityListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = authorityListService
								.getListData(url, params);
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
													if (codeList[0] == "e.authorityList.1003") {
														$scope.authorityListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.authorityList;
														$scope.authorityListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												$scope.authorityListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.authorityList;
												$scope.authorityListCtrl.table.sortAndFilter
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
				'modalInstanceCtrl4',
				function($scope, $uibModalInstance, key,
						authorityDetailService, insightService,authorityListService,
						strSpliceService) {
					$scope.modalInstanceCtrl4 = {};
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = authorityListService.setListDelete(url, data);
					if ($("#authorityList").val() == "1") {
						$scope.modalInstanceCtrl4.UpdbtnDisabled = true;
					}
					if (key.modeType == "1") {
						$scope.modalInstanceCtrl4.title = "権限新規画面";
						$scope.modalInstanceCtrl4.button = "登録";
						// 権限名
						$scope.modalInstanceCtrl4.authorityName = "";

					} else {
						$scope.modalInstanceCtrl4.title = "権限編集画面";
						$scope.modalInstanceCtrl4.button = "更新";
						$scope.modalInstanceCtrl4.authorityNameDisabled = true;
					}
					var url = "./../protected/authority/authorityDetail";
					var params = {
						"modeType" : key.modeType,
						"_id" : key._id
					};
					var data = authorityDetailService
							.getDetailData(url, params);
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
											// 権限名
											$scope.modalInstanceCtrl4.authorityName = r.data.resultData.authorityName;
											$scope.modalInstanceCtrl4.functionList = r.data.resultData.functionList;
										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrl4.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl4.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

					// 登録／更新ボタンを押す
					$scope.modalInstanceCtrl4.dataUpd = function() {
						$scope.modalInstanceCtrl4.UpdbtnDisabled = true;
						if ($scope.modalInstanceCtrl4.authorityName == null
								|| $scope.modalInstanceCtrl4.authorityName == "") {
							swal("権限名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl4.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl4.authorityName == "未指定") {
							swal("この権限名は既に登録されています。別の権限名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl4.UpdbtnDisabled = false;
							return;
						}
						if (key.modeType == "1") {
							var url = "./../protected/authority/authorityNameCheck";
							var data = {
								// 権限名
								'authorityName' : $scope.modalInstanceCtrl4.authorityName,
							};
							var data = authorityDetailService.dataUpd(url, data);
							data
								.then(
										function(r) {
											if (r.data.resultStatus != "OK") {
												swal("権限名既に登録しました。", "", "warning");
												$scope.modalInstanceCtrl4.UpdbtnDisabled = false;
												return;
											}  else {
												var url = "./../protected/authority/authorityDetailUpd";
												var data = {
													'_id' : key._id,
													"modeType" : key.modeType,
													// 権限名
													'authorityName' : $scope.modalInstanceCtrl4.authorityName,
													'functionList' : $scope.modalInstanceCtrl4.functionList,
												};
												var data = authorityDetailService.dataUpd(url, data);
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

																	} else {
																		if (key.modeType == "2") {
																			swal("変更しました。", "",
																					"success");
																		} else {
																			swal("登録しました。", "",
																					"success");
																		}

																		$uibModalInstance.close({
																			'updFlg' : "1",
																		});
																	}
																}, function(e) {
																	console.log(e);
																});
											}
										});
						} else {
							var url = "./../protected/authority/authorityDetailUpd";
							var data = {
								'_id' : key._id,
								"modeType" : key.modeType,
								// 権限名
								'authorityName' : $scope.modalInstanceCtrl4.authorityName,
								'functionList' : $scope.modalInstanceCtrl4.functionList,
							};
							var data = authorityDetailService.dataUpd(url, data);
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

												} else {
													if (key.modeType == "2") {
														swal("変更しました。", "",
																"success");
													} else {
														swal("登録しました。", "",
																"success");
													}

													$uibModalInstance.close({
														'updFlg' : "1",
													});
												}
											}, function(e) {
												console.log(e);
											});
						}

					};
				})