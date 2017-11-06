insightApp
		.controller(
				'documentRecordListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, pushRecordAppListService,
						insightService, strSpliceService) {

					// controllerオブジェクト変数
					$scope.documentRecordListCtrl = {};
					$scope.visible = true;
					// ステータス変数
					$scope.documentRecordListCtrl.selectStatus = [ "1" ];
					$scope.documentRecordListCtrl.flag ="0";
					// 申込受付日付モジュール変数
					$scope.documentRecordListCtrl.datepicker = {}

					$scope.documentRecordListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD HH:mm",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.documentRecordListCtrl.datepicker.formats[1])
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
					$scope.documentRecordListCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.documentRecordListCtrl.datepicker[opened] = true;
					};

					$scope.documentRecordListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.documentRecordListCtrl.datepicker.format = $scope.documentRecordListCtrl.datepicker.formats[1];

					// 一覧モジュール変数
					$scope.documentRecordListCtrl.table = {};

					$scope.documentRecordListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.documentRecordListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.documentRecordListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if (item.status == "1"
													|| item.status == "2" || $("#pushRecordAppList")=="1") {
												item.checkDisable = true;
											} else {
												item.select = $scope.documentRecordListCtrl.table.checkboxes.checked;
											}
							
										});
						console
								.log($scope.documentRecordListCtrl.table.checkboxes);
					}

					// ステータス内容を取得
					$scope.documentRecordListCtrl.table.filterList = insightService
							.getInsightData("documentStatusData");
					// PUSH開封状況内容を取得
					$scope.documentRecordListCtrl.table.filterList2 = insightService
							.getInsightData("pushStatusData");
					// 配信状況内容を取得
					$scope.documentRecordListCtrl.table.filterList3 = insightService
							.getInsightData("sendStatusData");
					
					if ($("#pushRecordAppList").val() == "1") {
						$scope.documentRecordListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "documentRecordListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.documentRecordListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "documentRecordListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					$scope.$on();
					function pushCheck(item) {
						angular
								.forEach(
										$scope.documentRecordListCtrl.table.sortAndFilter
												.settings().dataset, function(
												item) {
											if (item.status == "1"
													|| item.status == "2" || $("#pushRecordAppList")=="1") {
												item.checkDisable = true;
											}
											if (item.pushStatus == "3" || item.pushStatus == "4") {
												item.visible =true;
											} else {
												item.visible = false;
											}
										});
					}

					// 一括承認ボタンを押下時、
					$scope.documentRecordListCtrl.table.selectAdmitBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";
						angular
								.forEach(
										$scope.documentRecordListCtrl.table.sortAndFilter
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
											var url = "./../protected/push/documentSendButton";
											var data = $scope.documentRecordListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = pushRecordAppListService
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
																	$scope.documentRecordListCtrl.flag = "1"
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
					$scope.documentRecordListCtrl.table.openNa = function(size,
							item) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup.html',
							controller : 'modalInstanceCtrl25',
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
					$scope.documentRecordListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";
						angular
								.forEach(
										$scope.documentRecordListCtrl.table.sortAndFilter
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
											var url = "./../protected/push/documentDeleteButton";
											var data = $scope.documentRecordListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = pushRecordAppListService
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
																	$scope.documentRecordListCtrl.flag = "1"
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
					
					
//					// 配信失敗画面を呼び出す
					$scope.documentRecordListCtrl.table.pushErr = function(
							item) {
						
						var params = {
								title :item.pushErr,
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
//					$scope.$on();
					// 初期化データを取得
					function loadData(deferred) {

						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = pushRecordAppListService.setListSend(url, data);
						
						// 一覧初期データ取得
						var url = "./../protected/push/documentRecordAppList";
						$scope.documentRecordListCtrl.checkDisable = false;
						$scope.documentRecordListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = pushRecordAppListService.getListData(url,
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
												if ($scope.documentRecordListCtrl.flag != "1") {
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
														$scope.documentRecordListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.accountAppPushList;
														$scope.documentRecordListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												// データ取得途中、エラーなし場合、画面データを再表示する
												// 一括承認ボタンの制御解除する
												$scope.documentRecordListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.accountAppPushList;
												$scope.documentRecordListCtrl.table.sortAndFilter
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
				'modalInstanceCtrl25',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrl25 = {};
					var bank_cd = $("#pushRecordBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);
					
					$scope.modalInstanceCtrl25.originalStatus = {};
					$scope.modalInstanceCtrl25.imageHide = true;
					$scope.modalInstanceCtrl25.holdHide = true;
					$scope.modalInstanceCtrl25.contractHide = true;
					$scope.modalInstanceCtrl25.imageLoadHide = true;
					$scope.modalInstanceCtrl25.passwordHide = false;
					$scope.modalInstanceCtrl25.dirPasswordHide = false;
					var url = "./../protected/push/documentRecordAppDetail";
					if (bank_cd == "0173") {
						var params = {
								"_id" : key.accountApp_id
							};
					}
				
					var data = accountAppDetailService.getDetailData(url,
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
												closeOnConfirm : true
											};
											swal(params);
											$uibModalInstance.close({
												'load' : true
											});
										} else {
											$scope.modalInstanceCtrl25.name = r.data.resultData.name;
											$scope.modalInstanceCtrl25.kanaName = r.data.resultData.kanaName;
											$scope.modalInstanceCtrl25.agreeTime = r.data.resultData.agreeTime;
											$scope.modalInstanceCtrl25.agreeCheck = r.data.resultData.agreeCheck;
											$scope.modalInstanceCtrl25.selfConfirmFlg = r.data.resultData.selfConfirmFlg;
											$scope.modalInstanceCtrl25.purpose = r.data.resultData.purpose;
											$scope.modalInstanceCtrl25.type = r.data.resultData.type;
											$scope.modalInstanceCtrl25.driverLicenseFSeq = r.data.resultData.driverLicenseFSeq;
											$scope.modalInstanceCtrl25.driverLicenseBSeq = r.data.resultData.driverLicenseBSeq;
											$scope.modalInstanceCtrl25.incomeCertificateSeq = r.data.resultData.incomeCertificateSeq;
											$scope.modalInstanceCtrl25.typeFlg = r.data.resultData.typeFlg;
											$scope.modalInstanceCtrl25.readDriverLicenseNo = r.data.resultData.readDriverLicenseNo;
											$scope.modalInstanceCtrl25.readName = r.data.resultData.readName;
											$scope.modalInstanceCtrl25.readBirthDay = r.data.resultData.readBirthDay;
											$scope.modalInstanceCtrl25.driverLicenseNo = r.data.resultData.driverLicenseNo;
											$scope.modalInstanceCtrl25.lastName = r.data.resultData.lastName;
											$scope.modalInstanceCtrl25.firstName = r.data.resultData.firstName;
											$scope.modalInstanceCtrl25.kanaLastName = r.data.resultData.kanaLastName;
											$scope.modalInstanceCtrl25.kanaFirstName = r.data.resultData.kanaFirstName;
											$scope.modalInstanceCtrl25.telephoneNo = r.data.resultData.telephoneNo;
											$scope.modalInstanceCtrl25.bankNo = r.data.resultData.bankNo;
											$scope.modalInstanceCtrl25.storeNo = r.data.resultData.storeNo;
											$scope.modalInstanceCtrl25.storeName = r.data.resultData.storeName;
											$scope.modalInstanceCtrl25.other = r.data.resultData.other;
											$scope.modalInstanceCtrl25.oid = r.data.resultData.oid;
											$scope.modalInstanceCtrl25.bankAccountSendStatus = r.data.resultData.bankAccountSendStatus;
											$scope.modalInstanceCtrl25.bankAddressSendStatus = r.data.resultData.bankAddressSendStatus;
											$scope.modalInstanceCtrl25.dnpSendStatus = r.data.resultData.dnpSendStatus;
											$scope.modalInstanceCtrl25.sendStatus = r.data.resultData.sendStatus;
											$scope.modalInstanceCtrl25.sendStep = r.data.resultData.sendStep;
											$scope.modalInstanceCtrl25.bankAccountSendDate = r.data.resultData.bankAccountSendDate;
											$scope.modalInstanceCtrl25.bankAddressSendDate = r.data.resultData.bankAddressSendDate;
											$scope.modalInstanceCtrl25.dnpSendDate = r.data.resultData.dnpSendDate;
											$scope.modalInstanceCtrl25.compDate = r.data.resultData.compDate;
											$scope.modalInstanceCtrl25.errorMsg = r.data.resultData.errorMsg;
											$scope.modalInstanceCtrl25.sendComplete = r.data.resultData.sendComplete;
											$scope.modalInstanceCtrl25.sendedOid = r.data.resultData.sendedOid;
											$scope.modalInstanceCtrl25.applicationDate = r.data.resultData.applicationDate;
											$scope.modalInstanceCtrl25.selectStatus = r.data.resultData.status;
											$scope.modalInstanceCtrl25.userId = r.data.resultData.userId;
											$scope.modalInstanceCtrl25.userType = r.data.resultData.userType;
											$scope.modalInstanceCtrl25.pushData = r.data.resultData.statusModifyList;
											if (r.data.resultData.card1Seq != "") {
												$scope.modalInstanceCtrl25.card1Seq = r.data.resultData.card1Seq;
											}
											if (r.data.resultData.card2Seq != "") {
												$scope.modalInstanceCtrl25.card2Seq = r.data.resultData.card2Seq;
											}
											if (r.data.resultData.card3Seq != "") {
												$scope.modalInstanceCtrl25.card3Seq = r.data.resultData.card3Seq;
											}
											if (r.data.resultData.card4Seq != "") {
												$scope.modalInstanceCtrl25.card4Seq = r.data.resultData.card4Seq;
											}
											if (r.data.resultData.card5Seq != "") {
												$scope.modalInstanceCtrl25.card5Seq = r.data.resultData.card5Seq;
											}
											if (r.data.resultData.card6Seq != "") {
												$scope.modalInstanceCtrl25.card6Seq = r.data.resultData.card6Seq;
											}
										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrl25.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl25.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};


					// 確認書類imageLoad
					function imageLoad(id) {
						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = accountAppDetailService.statusUpd(url, data);
						
						var url = "./../protected/account/accountAppDetailImage";
						var params = {
							"_id" : id
						};
						var data = accountAppDetailService.getDetailData(url,
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
												$scope.modalInstanceCtrl25.identificationImage = "";
												$scope.modalInstanceCtrl25.identificationImageBack = "";
												$scope.modalInstanceCtrl25.imageLoadHide = true;
												swal(params);

											} else {
												$scope.modalInstanceCtrl25.identificationImage = r.data.resultData.identificationImage;
												$scope.modalInstanceCtrl25.identificationImageBack = r.data.resultData.identificationImageBack;
												$scope.modalInstanceCtrl25.imageLoadHide = true;
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 暗証番号表示
					$scope.modalInstanceCtrl25.showSecPwd = function() {
						$scope.modalInstanceCtrl25.passwordHide = true;
					}
					// 暗証番号非表示
					$scope.modalInstanceCtrl25.hideSecPwd = function() {
						$scope.modalInstanceCtrl25.passwordHide = false;
					}
					// ダイレクトバンキングカード暗証番号表示
					$scope.modalInstanceCtrl25.showDirPwd = function() {
						$scope.modalInstanceCtrl25.dirPasswordHide = true;
					}
					// ダイレクトバンキングカード暗証番号非表示
					$scope.modalInstanceCtrl25.hideDirPwd = function() {
						$scope.modalInstanceCtrl25.dirPasswordHide = false;
					}
				})
