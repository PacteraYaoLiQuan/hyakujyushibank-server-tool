insightApp
		.controller(
				'pushMessageCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, pushMessageService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.pushMessageCtrl = {};

					// 一覧モジュール変数
					$scope.pushMessageCtrl.table = {};
					$scope.pushMessageCtrl.datepicker = {};
					$scope.pushMessageCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					// データ存在状態を取得
					$scope.pushMessageCtrl.table.filterList2 = insightService
							.getInsightData("pushTitle");
					$scope.pushMessageCtrl.table.filterList3 = insightService
							.getInsightData("pushType");
					$scope.pushMessageCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.pushMessageCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.pushMessageCtrl.table.checkboxes.checked;
										});
						console.log($scope.pushMessageCtrl.table.checkboxes);
					}

					$scope.pushMessageCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					setTimeout(function() {
						$('#pushDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.pushMessageCtrl.datepicker.format = $scope.pushMessageCtrl.datepicker.formats[1];
					$scope.pushMessageCtrl.datepicker.open = function($event,
							opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.pushMessageCtrl.datepicker[opened] = true;
					};

					$scope.pushMessageCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					if ($("#pushMessage").val() == "1") {
						$scope.pushMessageCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										pushDate : "desc"
									}
								},
								{
									paginationCustomizeButtons : "fileListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.pushMessageCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										pushDate : "desc"
									}
								},
								{
									paginationCustomizeButtons : "pushMessageCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}

					$scope.pushMessageCtrl.pushMessage = $("#pushMessage")
							.val();
					if ($scope.pushMessageCtrl.pushMessage == "1") {
						$scope.pushMessageCtrl.addBtnDisabled = true;
					}

					// 一括削除ボタンを押下時、

					$scope.pushMessageCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var flg1 = "1";
						angular.forEach(
								$scope.pushMessageCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});

						if (flg == "0") {
							swal("データを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択したデータを削除しますか？",
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
											var url = "./../protected/message/deleteButton";
											var data = $scope.pushMessageCtrl.table.sortAndFilter
													.settings().dataset;
											var data = pushMessageService
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
					$scope.pushMessageCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'pushMessagePopup.html',
							controller : 'modalInstanceCtrl10',
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
							if (result.pushTitle != null) {
								loadData();
							}

						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}
					// 配信対象画面を呼び出す
					$scope.pushMessageCtrl.table.openPushType = function(size,
							item) {
						var modalPushType = $uibModal.open({
							animation : true,
							templateUrl : 'pushTypePopup.html',
							controller : 'pushTypeCtrl',
							backdrop : 'static',
							size : size,
							resolve : {
								key : function() {
									return {
										'_id' : item._id
									};
								}
							}
						});
						modalPushType.result.then(function(result) {
							if (result.status != null) {
								item.status = result.status
								item.pushStatus = result.pushStatus
							} else {
								if (result.load == true) {
									loadData();
								}
							}
						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					};
					// メッセージ詳細画面を呼び出す
					$scope.pushMessageCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'pushMessagePopup.html',
							controller : 'modalInstanceCtrl10',
							backdrop : 'static',
							size : size,
							resolve : {
								key : function() {
									return {
										'modeType' : "2", // 詳細場合
										'_id' : item._id
									};
								}
							}
						});

						modalInstance2.result.then(function(result) {
							if (result.pushTitle != null) {
								loadData();
							} else {
								if (result.load == true) {
									loadData();
								}
							}
						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					};

					$scope.$on();

					// 初期化データを取得
					function loadData(deferred) {
						var url = "./../protected/message/pushMessageList";
						$scope.pushMessageCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = pushMessageService.getListData(url, params);
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
												$scope.pushMessageCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.pushMessage;
												$scope.pushMessageCtrl.table.sortAndFilter
														.reload();
											} else {
												$scope.pushMessageCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.messageList;
												$scope.pushMessageCtrl.table.sortAndFilter
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
		// Push通知履歴結果画面表示
		.controller(
				'pushTypeCtrl',
				function($scope, $uibModalInstance, key, pushMessageService,
						insightService, strSpliceService) {
					$scope.pushTypeCtrl = {};

					var url = "./../protected/message/pushTypeList";
					var data = {
						"_id" : key._id
					};
					var data = pushMessageService.getDetailData(url, data);
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
												closeOnConfirm : true
											};
											swal(params);
											$uibModalInstance.close({
												'load' : true
											});
										} else {
											$scope.pushTypeCtrl.pushTypeList = r.data.resultData.pushTypeList;
										}
									}, function(e) {
										console.log(e);
									});

					$scope.pushTypeCtrl.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.pushTypeCtrl.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})
		.controller(
				'modalInstanceCtrl10',
				function($scope, $uibModal, $uibModalInstance, key,
						pushMessageService, insightService, strSpliceService) {
					$scope.modalInstanceCtrl10 = {};
					if ($scope.modalInstanceCtrl10.pushMessage == "1") {
						$scope.modalInstanceCtrl10.UpdbtnDisabled = true;
						$scope.modalInstanceCtrl10.pushTitleDisabled = true;
					} else {
						$scope.modalInstanceCtrl10.UpdbtnDisabled = false;
						$scope.modalInstanceCtrl10.pushTitleDisabled = false;
					}

					if (key.modeType == "1") {
						$scope.modalInstanceCtrl10.windowHide2 = true;

						$scope.modalInstanceCtrl10.title = "メッセージ新規画面";
						// 件名
						$scope.modalInstanceCtrl10.pushTitle = "";
						// 本文
						$scope.modalInstanceCtrl10.pushMessage = "";
					} else {
						$scope.modalInstanceCtrl10.windowHide1 = true;
						$scope.modalInstanceCtrl10.title = "メッセージ詳細画面";
						$scope.modalInstanceCtrl10.button = "プレビュー";
						$scope.modalInstanceCtrl10.pushTitleDisabled = true;
						$scope.modalInstanceCtrl10.pushMessageDisabled = true;
						var url = "./../protected/message/messageDetail";

						var data = {
							"_id" : key._id,
							"modeType" : key.modeType,
						};
						var data = pushMessageService.getDetailData(url, data);
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
												$uibModalInstance.close({
													'load' : true
												});
											} else {
												// 件名
												$scope.modalInstanceCtrl10.pushTitle = r.data.resultData.pushTitle;
												// 本文
												$scope.modalInstanceCtrl10.pushMessage = r.data.resultData.pushMessage;
											}
										}, function(e) {
											console.log(e);
										});
					}
					function close10() {
						$uibModalInstance.close({});
					}

					$scope.modalInstanceCtrl10.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl10.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					$uibModalInstance.close({
						'pushTitle' : $scope.modalInstanceCtrl10.pushTitle
					});

					// プレビューボタン押すと
					$scope.modalInstanceCtrl10.dataOK = function(size, item) {

						$scope.modalInstanceCtrl10.UpdbtnDisabled = true;
						if ($scope.modalInstanceCtrl10.pushTitle == null
								|| $scope.modalInstanceCtrl10.pushTitle == "") {
							swal("件名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl10.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl10.pushMessage == null
								|| $scope.modalInstanceCtrl10.pushMessage == "") {
							swal("本文を入力してください。", "", "warning");
							$scope.modalInstanceCtrl10.UpdbtnDisabled = false;
							return;
						}

						var url = "./../protected/message/messageDetail";
						if (key.modeType == "1") {
							var data = {
								"_id" : "",
								"modeType" : key.modeType,
								"pushTitle" : $scope.modalInstanceCtrl10.pushTitle,
								"pushMessage" : $scope.modalInstanceCtrl10.pushMessage
							};
						} else {
							var data = {
								"_id" : key._id,
								"modeType" : key.modeType,
								"pushTitle" : "",
								"pushMessage" : ""
							};
						}
						var data = pushMessageService.getDetailData(url, data);
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
												$uibModalInstance.close({
													'load' : true
												});
											} else {
												// 件名
												$scope.modalInstanceCtrl10.outPath = "../protected/temp/html/"
														+ r.data.resultData.outPath;
												var url = $scope.modalInstanceCtrl10.outPath;
												var iWidth = 375;
												var iHeight = 775;
												var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
												var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
												window.childWindow = window
														.open(
																url,
																"preview",
																'height='
																		+ iHeight
																		+ ',innerHeight='
																		+ iHeight
																		+ ',width='
																		+ iWidth
																		+ ',innerWidth='
																		+ iWidth
																		+ ',top='
																		+ iTop
																		+ ',left='
																		+ iLeft
																		+ ',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
												window.childWindow.focus();
											}
										}, function(e) {
											console.log(e);
										});

					};

					// 配信するボタンを押す
					$scope.modalInstanceCtrl10.dataSend = function() {
						$scope.modalInstanceCtrl10.SendbtnDisabled = true;
						if ($scope.modalInstanceCtrl10.pushTitle == null
								|| $scope.modalInstanceCtrl10.pushTitle == "") {
							swal("件名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl10.SendbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl10.pushMessage == null
								|| $scope.modalInstanceCtrl10.pushMessage == "") {
							swal("本文を入力してください。", "", "warning");
							$scope.modalInstanceCtrl10.SendbtnDisabled = false;
							return;
						}

						var url = "./../protected/message/messageUpdate";
						var data = {
							"_id" : key._id,
							"modeType" : key.modeType,
							"pushTitle" : $scope.modalInstanceCtrl10.pushTitle,
							"pushMessage" : $scope.modalInstanceCtrl10.pushMessage
						};
						var data = pushMessageService.dataUpd(url, data);
						data
								.then(
										function(r) {
											if (r.data.resultStatus != "OK") {
												var codeList4 = strSpliceService
														.resultCodeSplice(
																r.data.messages,
																"code");
												var params = {
													title : insightService
															.getMessageData("applyStatusMessage")[codeList4[0]],
													text : "",
													type : "error",
													showCancelButton : false,
													closeOnConfirm : true
												};
												swal(
														params,
														function() {
															if (codeList4[0] == "e.pushMessage.1004") {
																$uibModalInstance
																		.close({
																			'pushTitle' : $scope.modalInstanceCtrl10.pushTitle
																		});
															}
														});
												$scope.modalInstanceCtrl10.UpdbtnDisabled = false;
											} else {
												if (key.modeType == "1") {
													swal("PUSH配信完了しました。", "",
															"success");
												}
												$scope.modalInstanceCtrl10.SendbtnDisabled = false;
												$uibModalInstance
														.close({
															'pushTitle' : $scope.modalInstanceCtrl10.pushTitle
														});
											}
										}, function(e) {
											console.log(e);
										});
					};
					$uibModalInstance.close({
						'pushTitle' : $scope.modalInstanceCtrl10.pushTitle
					});
					$scope.modalInstanceCtrl10.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl10.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})
