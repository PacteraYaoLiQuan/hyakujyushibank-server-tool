insightApp
		.controller(
				'pushRecordLoanListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, pushRecordLoanListService,
						insightService, strSpliceService) {

					// controllerオブジェクト変数
					$scope.pushRecordLoanListCtrl = {};
					$scope.visible = true;
					// ステータス変数
					$scope.pushRecordLoanListCtrl.selectStatus = [ "1" ];
					$scope.pushRecordLoanListCtrl.flag = "0";
					// 申込受付日付モジュール変数
					$scope.pushRecordLoanListCtrl.datepicker = {}

					$scope.pushRecordLoanListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD HH:mm",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.pushRecordLoanListCtrl.datepicker.formats[1])
														+ " 09:00:00"),
										useCurrent : false
									});
					setTimeout(function() {
						$('#receiptDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					setTimeout(function() {
						$('#pushDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.pushRecordLoanListCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.pushRecordLoanListCtrl.datepicker[opened] = true;
					};

					$scope.pushRecordLoanListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.pushRecordLoanListCtrl.datepicker.format = $scope.pushRecordLoanListCtrl.datepicker.formats[1];

					// 一覧モジュール変数
					$scope.pushRecordLoanListCtrl.table = {};

					$scope.pushRecordLoanListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.pushRecordLoanListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.pushRecordLoanListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if (item.status == "1"
													|| item.status == "2"
													|| $("#pushRecordLoanList") == "1") {
												item.checkDisable = true;
											} else {
												item.select = $scope.pushRecordLoanListCtrl.table.checkboxes.checked;
											}

										});
						console
								.log($scope.pushRecordLoanListCtrl.table.checkboxes);
					}

					// ステータス内容を取得
					$scope.pushRecordLoanListCtrl.table.filterList = insightService
							.getInsightData("loanStatusData");
					// PUSH開封状況内容を取得
					$scope.pushRecordLoanListCtrl.table.filterList2 = insightService
							.getInsightData("pushStatusData");
					// 配信状況内容を取得
					$scope.pushRecordLoanListCtrl.table.filterList3 = insightService
							.getInsightData("sendStatusData");

					if ($("#pushRecordLoanList").val() == "1") {
						$scope.pushRecordLoanListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "pushRecordLoanListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.pushRecordLoanListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "pushRecordLoanListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					$scope.$on();
					function pushCheck(item) {
						angular
								.forEach(
										$scope.pushRecordLoanListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if (item.status == "1"
													|| item.status == "2"
													|| $("#pushRecordLoanList") == "1") {
												item.checkDisable = true;
											}
											if (item.pushStatus == "3"
													|| item.pushStatus == "4") {
												item.visible = true;
											} else {
												item.visible = false;
											}
										});
					}

					// 一括承認ボタンを押下時、
					$scope.pushRecordLoanListCtrl.table.selectAdmitBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";
						angular
								.forEach(
										$scope.pushRecordLoanListCtrl.table.sortAndFilter
												.settings().dataset, function(
												item) {
											if (item.select) {
												flg = "1";
											}
										});
						if (flg == "0") {
							swal(
									insightService
											.getMessageData("applyStatusMessage")["e.push001.1002"],
									"", "error");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : insightService
										.getMessageData("applyStatusMessage")["i.push001.1001"],
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
											// 一括PUSH通知データ取得
											var url = "./../protected/push/sendButton";
											var data = $scope.pushRecordLoanListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = pushRecordLoanListService
													.setListSend(url, {
														'sendList' : data
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
																	var codeList = strSpliceService
																			.resultCodeSplice(
																					r.data.messages,
																					"code");
																	var errType = "";
																	if (r.data.resultData.errFlag == "1") {
																		errType = "success"
																	} else {
																		errType = "error"
																	}
																	swal(
																			insightService
																					.getMessageData("applyStatusMessage")[codeList[0]],
																			"",
																			errType);
																	$scope.pushRecordLoanListCtrl.flag = "1"
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

					// 申込詳細画面を呼び出す
					$scope.pushRecordLoanListCtrl.table.openNa = function(size,
							item) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup.html',
							controller : 'modalInstanceCtrls',
							backdrop : 'static',
							size : size,
							resolve : {
								key : function() {
									return {
										'_id' : item._id,
										'accountApp_id' : item.accountApp_id
									};
								}
							}
						});

						modalInstance.result.then(function(result) {
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

					// 承認取り下げボタンを押下時、
					$scope.pushRecordLoanListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";
						angular
								.forEach(
										$scope.pushRecordLoanListCtrl.table.sortAndFilter
												.settings().dataset, function(
												item) {
											if (item.select) {
												flg = "1";
											}
										});
						if (flg == "0") {
							swal(
									insightService
											.getMessageData("applyStatusMessage")["e.push001.1008"],
									"", "error");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : insightService
										.getMessageData("applyStatusMessage")["i.push001.1005"],
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
											// PUSH通知データ取得
											var url = "./../protected/push/deleteButton";
											var data = $scope.pushRecordLoanListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = pushRecordLoanListService
													.setListSend(url, {
														'sendList' : data
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
																	var codeList = strSpliceService
																			.resultCodeSplice(
																					r.data.messages,
																					"code");
																	var errType = "";
																	if (r.data.resultData.errFlag == "1") {
																		errType = "success"
																	} else {
																		errType = "error"
																	}
																	swal(
																			insightService
																					.getMessageData("applyStatusMessage")[codeList[0]],
																			"",
																			errType);
																	$scope.pushRecordLoanListCtrl.flag = "1"
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

					// // 配信失敗画面を呼び出す
					$scope.pushRecordLoanListCtrl.table.pushErr = function(item) {

						var params = {
							title : item.pushErr,
							text : "",
							type : "warning",
							showCancelButton : false,
							confirmButtonColor : "#DD6B55",
							confirmButtonText : "閉じる",
							closeOnConfirm : true,
							closeOnCancel : true
						}

						swal(params);
						deferred.resolve("resolve");
					};
					// $scope.$on();
					// 初期化データを取得
					function loadData(deferred) {

						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = pushRecordLoanListService.setListSend(url,
								data);

						// 一覧初期データ取得
						var url = "./../protected/push/pushRecordLoanList";
						$scope.pushRecordLoanListCtrl.checkDisable = false;
						$scope.pushRecordLoanListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = pushRecordLoanListService.getListData(url,
								params);
						data
								.then(
										function(r) {
											// データ取得途中、エラーを発生、エラーメッセージを表示
											if (r.data.resultStatus == "NG") {
												var codeList = strSpliceService
														.resultCodeSplice(
																r.data.messages,
																"code");
												if ($scope.pushRecordLoanListCtrl.flag != "1") {
													swal(
															insightService
																	.getMessageData("applyStatusMessage")[codeList[0]],
															"", "error");
												}

												// 初期化以外場合（データ再取得場合）
												if (typeof deferred != "undefined") {
													// 一括承認ボタンの制御解除する
													deferred.resolve("resolve");
													// 再検索の場合、データがなし場合、先に残留したデータをクリア
													if (codeList[0] == "e.push001.1001") {
														$scope.pushRecordLoanListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.accountAppPushList;
														$scope.pushRecordLoanListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												// データ取得途中、エラーなし場合、画面データを再表示する
												// 一括承認ボタンの制御解除する
												$scope.pushRecordLoanListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.accountAppPushList;
												$scope.pushRecordLoanListCtrl.table.sortAndFilter
														.reload();
												pushCheck(r.data.resultData);
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
		// 申込詳細表示
		.controller(
				'modalInstanceCtrls',
				function($scope, $uibModalInstance, key,
						accountLoanListService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrls = {};
					var bank_cd = $("#pushRecordBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountLoanListService.statusUpd(url, data);

					$scope.modalInstanceCtrls.originalStatus = {};
					$scope.modalInstanceCtrls.imageHide = true;
					$scope.modalInstanceCtrls.holdHide = true;
					$scope.modalInstanceCtrls.contractHide = true;
					$scope.modalInstanceCtrls.imageLoadHide = true;
					var url = "./../protected/push/pushRecordLoanDetail";
					var params = {
						"_id" : key.accountApp_id
					};

					var data = accountLoanListService
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
												closeOnConfirm : true
											};
											swal(params);
											$uibModalInstance.close({
												'load' : true
											});
										} else {

											$scope.modalInstanceCtrls.accountAppSeq = r.data.resultData.accountAppSeq;
											$scope.modalInstanceCtrls.loanType = r.data.resultData.loanType;
											if ("0" ==r.data.resultData.loanFlag) {
												$scope.modalInstanceCtrls.loanType0 == true;
												$scope.modalInstanceCtrls.loanType1 == false;
											} else {
												$scope.modalInstanceCtrls.loanType0 == false;
												$scope.modalInstanceCtrls.loanType1 == true;
											}
											$scope.modalInstanceCtrls.name = r.data.resultData.name;
											$scope.modalInstanceCtrls.kanaName = r.data.resultData.kanaName;
											$scope.modalInstanceCtrls.birthday = r.data.resultData.birthday;
											$scope.modalInstanceCtrls.sexKbn = r.data.resultData.sexKbn;
											$scope.modalInstanceCtrls.eraBirthday = r.data.resultData.eraBirthday;
											$scope.modalInstanceCtrls.ordinaryDepositEraKbn = r.data.resultData.yearType;
											$scope.modalInstanceCtrls.age = r.data.resultData.age;
											$scope.modalInstanceCtrls.money = r.data.resultData.money;
											$scope.modalInstanceCtrls.country = r.data.resultData.country;
											$scope.modalInstanceCtrls.driverLicenseNo = r.data.resultData.driverLicenseNo;
											$scope.modalInstanceCtrls.liveType = r.data.resultData.liveType;
											$scope.modalInstanceCtrls.familyNumber = r.data.resultData.familyNumber;
											$scope.modalInstanceCtrls.postCode = r.data.resultData.postCode;
											$scope.modalInstanceCtrls.prefecturesCode = r.data.resultData.prefecturesCode;
											$scope.modalInstanceCtrls.address = r.data.resultData.address;
											$scope.modalInstanceCtrls.teleNumber = r.data.resultData.teleNumber;
											$scope.modalInstanceCtrls.phoneNumber = r.data.resultData.phoneNumber;
											$scope.modalInstanceCtrls.workTeleNumber = r.data.resultData.workTeleNumber;
											$scope.modalInstanceCtrls.companyName = r.data.resultData.companyName;
											$scope.modalInstanceCtrls.loanAppTime = r.data.resultData.loanAppTime;
											$scope.modalInstanceCtrls.limitMoney = r.data.resultData.limitMoney;
											$scope.modalInstanceCtrls.applicationMoney = r.data.resultData.applicationMoney;
											$scope.modalInstanceCtrls.getHopeDate = r.data.resultData.getHopeDate;
											$scope.modalInstanceCtrls.returnHopeMonth = r.data.resultData.returnHopeMonth;
											$scope.modalInstanceCtrls.otherPurpose = r.data.resultData.otherPurpose;
											$scope.modalInstanceCtrls.purpose = r.data.resultData.purpose;
											$scope.modalInstanceCtrls.moneyTotal = r.data.resultData.moneyTotal;
											$scope.modalInstanceCtrls.payMoney1 = r.data.resultData.payMoney1;
											$scope.modalInstanceCtrls.money1 = r.data.resultData.money1;
											$scope.modalInstanceCtrls.payMoney2 = r.data.resultData.payMoney2;
											$scope.modalInstanceCtrls.money2 = r.data.resultData.money2;
											$scope.modalInstanceCtrls.ownAccountKbn = r.data.resultData.ownAccountKbn;
											$scope.modalInstanceCtrls.increaseFlg = r.data.resultData.increaseFlg;
											$scope.modalInstanceCtrls.returnDay = r.data.resultData.returnDay;
											$scope.modalInstanceCtrls.returnStartDay = r.data.resultData.returnStartDay;
											$scope.modalInstanceCtrls.returnMoney = r.data.resultData.returnMoney;
											$scope.modalInstanceCtrls.returnHopeCount = r.data.resultData.returnHopeCount;
											$scope.modalInstanceCtrls.hopeStoreNmae = r.data.resultData.hopeStoreNmae;
											$scope.modalInstanceCtrls.bankAccount = r.data.resultData.bankAccount;
											$scope.modalInstanceCtrls.status = r.data.resultData.status;
											$scope.modalInstanceCtrls.pushData = r.data.resultData.yamagataStatusModifyList;
											 imageLoad(r.data.resultData.driverLicenseSeq);
											$scope.modalInstanceCtrls.imageLoadHide = false;

										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrls.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrls.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					// 確認書類imageLoad
					function imageLoad(id) {
						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = accountLoanListService.statusUpd(url, data);
						
						var url = "./../protected/account/accountAppDetailImage";
						var params = {
							"_id" : id
						};
						var data = accountLoanListService.getDetailData(url,
								params);
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
												$scope.modalInstanceCtrls.identificationImage = "";
												$scope.modalInstanceCtrls.identificationImageBack = "";
												$scope.modalInstanceCtrls.imageLoadHide = true;
												swal(params);

											} else {
												$scope.modalInstanceCtrls.identificationImage = r.data.resultData.identificationImage;
												$scope.modalInstanceCtrls.identificationImageBack = r.data.resultData.identificationImageBack;
												$scope.modalInstanceCtrls.imageLoadHide = true;
											}
										}, function(e) {
											console.log(e);
										});
					}
				})
