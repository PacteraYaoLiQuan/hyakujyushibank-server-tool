insightApp
		.controller(
				'pushRecordAppListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, pushRecordAppListService,
						insightService, strSpliceService) {

					// controllerオブジェクト変数
					$scope.pushRecordAppListCtrl = {};
					$scope.visible = true;
					// ステータス変数
					$scope.pushRecordAppListCtrl.selectStatus = [ "1" ];
					$scope.pushRecordAppListCtrl.flag = "0";
					// 申込受付日付モジュール変数
					$scope.pushRecordAppListCtrl.datepicker = {}

					$scope.pushRecordAppListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD HH:mm",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.pushRecordAppListCtrl.datepicker.formats[1])
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
					$scope.pushRecordAppListCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.pushRecordAppListCtrl.datepicker[opened] = true;
					};

					$scope.pushRecordAppListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.pushRecordAppListCtrl.datepicker.format = $scope.pushRecordAppListCtrl.datepicker.formats[1];

					// 一覧モジュール変数
					$scope.pushRecordAppListCtrl.table = {};

					$scope.pushRecordAppListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.pushRecordAppListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.pushRecordAppListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if (item.status == "1"
													|| item.status == "2"
													|| $("#pushRecordAppList") == "1") {
												item.checkDisable = true;
											} else {
												item.select = $scope.pushRecordAppListCtrl.table.checkboxes.checked;
											}

										});
						console
								.log($scope.pushRecordAppListCtrl.table.checkboxes);
					}

					// ステータス内容を取得
					$scope.pushRecordAppListCtrl.table.filterList = insightService
							.getInsightData("accountStatusData");
					// PUSH開封状況内容を取得
					$scope.pushRecordAppListCtrl.table.filterList2 = insightService
							.getInsightData("pushStatusData");
					// 配信状況内容を取得
					$scope.pushRecordAppListCtrl.table.filterList3 = insightService
							.getInsightData("sendStatusData");

					if ($("#pushRecordAppList").val() == "1") {
						$scope.pushRecordAppListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "pushRecordAppListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.pushRecordAppListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "pushRecordAppListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					$scope.$on();
					function pushCheck(item) {
						angular
								.forEach(
										$scope.pushRecordAppListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if (item.status == "1"
													|| item.status == "2"
													|| $("#pushRecordAppList") == "1") {
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
					$scope.pushRecordAppListCtrl.table.selectAdmitBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";
						angular
								.forEach(
										$scope.pushRecordAppListCtrl.table.sortAndFilter
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
											var data = $scope.pushRecordAppListCtrl.table.sortAndFilter
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
																	$scope.pushRecordAppListCtrl.flag = "1"
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
					$scope.pushRecordAppListCtrl.table.openNa = function(size,
							item) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup.html',
							controller : 'modalInstanceCtrl26',
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
					$scope.pushRecordAppListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";
						angular
								.forEach(
										$scope.pushRecordAppListCtrl.table.sortAndFilter
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
											var data = $scope.pushRecordAppListCtrl.table.sortAndFilter
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
																	$scope.pushRecordAppListCtrl.flag = "1"
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
					$scope.pushRecordAppListCtrl.table.pushErr = function(item) {

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
						var data = pushRecordAppListService.setListSend(url,
								data);

						// 一覧初期データ取得
						var url = "./../protected/push/pushRecordAppList";
						$scope.pushRecordAppListCtrl.checkDisable = false;
						$scope.pushRecordAppListCtrl.table.checkboxes = {
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
												if ($scope.pushRecordAppListCtrl.flag != "1") {
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
														$scope.pushRecordAppListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.accountAppPushList;
														$scope.pushRecordAppListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												// データ取得途中、エラーなし場合、画面データを再表示する
												// 一括承認ボタンの制御解除する
												$scope.pushRecordAppListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.accountAppPushList;
												$scope.pushRecordAppListCtrl.table.sortAndFilter
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
				'modalInstanceCtrl26',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrl26 = {};
					var bank_cd = $("#pushRecordBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);

					$scope.modalInstanceCtrl26.originalStatus = {};
					$scope.modalInstanceCtrl26.imageHide = true;
					$scope.modalInstanceCtrl26.holdHide = true;
					$scope.modalInstanceCtrl26.contractHide = true;
					$scope.modalInstanceCtrl26.imageLoadHide = true;
					$scope.modalInstanceCtrl26.passwordHide = false;
					$scope.modalInstanceCtrl26.dirPasswordHide = false;
					var url = "./../protected/push/pushRecordAppDetail";
					if (bank_cd == "0169") {
						var params = {
							"_id" : key._id
						};
					} else if (bank_cd == "0122") {
						var params = {
							"_id" : key.accountApp_id
						};
					} else if (bank_cd == "0173") {

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
											if (bank_cd == "0169") {
												// お客様情報
												$scope.modalInstanceCtrl26.name = r.data.resultData.name;
												$scope.modalInstanceCtrl26.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl26.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl26.sex = r.data.resultData.sex;
												$scope.modalInstanceCtrl26.age = r.data.resultData.age;
												// 勤務先名
												if (r.data.resultData.workName == null
														|| r.data.resultData.workName == "") {
													$scope.modalInstanceCtrl26.workName = "-";
												} else {
													$scope.modalInstanceCtrl26.workName = r.data.resultData.workName;
												}
												$scope.modalInstanceCtrl26.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl26.address1 = r.data.resultData.address1;
												$scope.modalInstanceCtrl26.address2 = r.data.resultData.address2;
												$scope.modalInstanceCtrl26.kanaAddress = r.data.resultData.kanaAddress;
												// 自宅電話番号
												if (r.data.resultData.teleNumber == null
														|| r.data.resultData.teleNumber == "") {
													$scope.modalInstanceCtrl26.teleNumber = "-";
												} else {
													$scope.modalInstanceCtrl26.teleNumber = r.data.resultData.teleNumber;
												}
												// 携帯電話番号
												if (r.data.resultData.phoneNumber == null
														|| r.data.resultData.phoneNumber == "") {
													$scope.modalInstanceCtrl26.phoneNumber = "-";
												} else {
													$scope.modalInstanceCtrl26.phoneNumber = r.data.resultData.phoneNumber;
												}
												// 勤務先電話番号
												if (r.data.resultData.workTeleNumber == null
														|| r.data.resultData.workTeleNumber == "") {
													$scope.modalInstanceCtrl26.workTeleNumber = "-";
												} else {
													$scope.modalInstanceCtrl26.workTeleNumber = r.data.resultData.workTeleNumber;
												}
												// 既に口座をお持ちの方
												$scope.modalInstanceCtrl26.holdAccount = r.data.resultData.holdAccount;
												// 既に口座をお持ちの方:有の場合
												if (r.data.resultData.holdAccount == "1") {
													$scope.modalInstanceCtrl26.holdHide = false;
													$scope.modalInstanceCtrl26.holdAccountBank = r.data.resultData.holdAccountBank;
													$scope.modalInstanceCtrl26.holdAccountNumber = r.data.resultData.holdAccountNumber;
												}
												// ダイレクトバンキングサービスのご契約
												$scope.modalInstanceCtrl26.directServicesContract = r.data.resultData.directServicesContract;
												// 有の場合
												if (r.data.resultData.directServicesContract == "0") {
													$scope.modalInstanceCtrl26.contractHide = false;
													$scope.modalInstanceCtrl26.directServicesCardPassword = r.data.resultData.directServicesCardPassword;
													// インターネットバンキングサービス
													$scope.modalInstanceCtrl26.internetRegisterPerTrans = r.data.resultData.internetRegisterPerTrans;
													$scope.modalInstanceCtrl26.internetRegisterPerDay = r.data.resultData.internetRegisterPerDay;
													$scope.modalInstanceCtrl26.internetOncePerTrans = r.data.resultData.internetOncePerTrans;
													$scope.modalInstanceCtrl26.internetOncePerDay = r.data.resultData.internetOncePerDay;
												}
												// 申込情報
												$scope.modalInstanceCtrl26.accountAppSeq = r.data.resultData.accountAppSeq;
												$scope.modalInstanceCtrl26.applicationDate = r.data.resultData.applicationDate;
												$scope.modalInstanceCtrl26.receiptDate = r.data.resultData.receiptDate;
												$scope.modalInstanceCtrl26.cardType = r.data.resultData.cardType;
												$scope.modalInstanceCtrl26.noApplicationServiceList = r.data.resultData.noApplicationService;
												$scope.modalInstanceCtrl26.goodsAppointed = r.data.resultData.goodsAppointed;
												$scope.modalInstanceCtrl26.tradingPurposes = r.data.resultData.tradingPurposes;
												$scope.modalInstanceCtrl26.otherTradingPurposes = r.data.resultData.otherTradingPurposes;
												$scope.modalInstanceCtrl26.occupation = r.data.resultData.occupation;
												$scope.modalInstanceCtrl26.otherOccupations = r.data.resultData.otherOccupations;
												$scope.modalInstanceCtrl26.securityPassword = r.data.resultData.securityPassword;
												// テレホンバンキングサービス
												$scope.modalInstanceCtrl26.telRegisterPerTrans = r.data.resultData.telRegisterPerTrans;
												$scope.modalInstanceCtrl26.telRegisterPerDay = r.data.resultData.telRegisterPerDay;
												$scope.modalInstanceCtrl26.telOncePerTrans = r.data.resultData.telOncePerTrans;
												$scope.modalInstanceCtrl26.telOncePerDay = r.data.resultData.telOncePerDay;

												$scope.modalInstanceCtrl26.identificationType = r.data.resultData.identificationType;
												if (r.data.resultData.identificationType == "A1") {
													$scope.modalInstanceCtrl26.imageHide = false;
												}

												$scope.modalInstanceCtrl26.livingConditions = r.data.resultData.livingConditions;
												$scope.modalInstanceCtrl26.ipAddress = r.data.resultData.ipAddress;
												$scope.modalInstanceCtrl26.transactionType = r.data.resultData.transactionType;
												$scope.modalInstanceCtrl26.applyServiceList = r.data.resultData.applyService;
												$scope.modalInstanceCtrl26.originalStatus = r.data.resultData.status;
												// ひろぎんネット支店の口座開設の動機
												$scope.modalInstanceCtrl26.accountAppMotive = r.data.resultData.accountAppMotive;
												$scope.modalInstanceCtrl26.accountAppOtherMotive = r.data.resultData.accountAppOtherMotive;
												// ひろぎんネット支店を知った経緯
												$scope.modalInstanceCtrl26.knowProcess = r.data.resultData.knowProcess;
												$scope.modalInstanceCtrl26.knowOtherProcess = r.data.resultData.knowOtherProcess;
												$scope.modalInstanceCtrl26.pushData = r.data.resultData.accountAppDetailPushList;
												$scope.modalInstanceCtrl26.status = r.data.resultData.status;
												imageLoad(r.data.resultData.identificationImage);
												$scope.modalInstanceCtrl26.imageLoadHide = false;
											} else if (bank_cd == "0122") {
												// お客様情報
												$scope.modalInstanceCtrl26.name = r.data.resultData.name;
												$scope.modalInstanceCtrl26.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl26.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl26.sexKbn = r.data.resultData.sexKbn;
												$scope.modalInstanceCtrl26.age = r.data.resultData.age;
												$scope.modalInstanceCtrl26.accountType = r.data.resultData.accountType;
												$scope.modalInstanceCtrl26.ordinaryDepositEraKbn = r.data.resultData.ordinaryDepositEraKbn;
												$scope.modalInstanceCtrl26.eraBirthday = r.data.resultData.eraBirthday;
												$scope.modalInstanceCtrl26.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl26.prefecturesCode = r.data.resultData.prefecturesCode;
												$scope.modalInstanceCtrl26.address = r.data.resultData.address;
												// お勤め先（学校名）
												if (r.data.resultData.workName == null
														|| r.data.resultData.workName == "") {
													$scope.modalInstanceCtrl26.workName = "-";
												} else {
													$scope.modalInstanceCtrl26.workName = r.data.resultData.workName;
												}
												// 自宅電話番号
												if (r.data.resultData.teleNumber == null
														|| r.data.resultData.teleNumber == "") {
													$scope.modalInstanceCtrl26.teleNumber = "-";
												} else {
													$scope.modalInstanceCtrl26.teleNumber = r.data.resultData.teleNumber;
												}
												// 携帯電話番号
												if (r.data.resultData.phoneNumber == null
														|| r.data.resultData.phoneNumber == "") {
													$scope.modalInstanceCtrl26.phoneNumber = "-";
												} else {
													$scope.modalInstanceCtrl26.phoneNumber = r.data.resultData.phoneNumber;
												}
												// 勤務先電話番号
												if (r.data.resultData.workTeleNumber == null
														|| r.data.resultData.workTeleNumber == "") {
													$scope.modalInstanceCtrl26.workTeleNumber = "-";
												} else {
													$scope.modalInstanceCtrl26.workTeleNumber = r.data.resultData.workTeleNumber;
												}
												// 申込情報
												$scope.modalInstanceCtrl26.accountAppSeq = r.data.resultData.accountAppSeq;
												$scope.modalInstanceCtrl26.applicationDate = r.data.resultData.applicationDate;
												$scope.modalInstanceCtrl26.receiptDate = r.data.resultData.receiptDate;
												$scope.modalInstanceCtrl26.bankbookDesignKbn = r.data.resultData.bankbookDesignKbn;
												$scope.modalInstanceCtrl26.cardDesingKbn = r.data.resultData.cardDesingKbn;
												$scope.modalInstanceCtrl26.accountPurpose = r.data.resultData.accountPurpose;
												$scope.modalInstanceCtrl26.accountPurposeOther = r.data.resultData.accountPurposeOther;
												$scope.modalInstanceCtrl26.jobKbn = r.data.resultData.jobKbn;
												$scope.modalInstanceCtrl26.jobKbnOther = r.data.resultData.jobKbnOther;
												$scope.modalInstanceCtrl26.securityPassword = r.data.resultData.securityPassword;
												$scope.modalInstanceCtrl26.creditlimit = r.data.resultData.creditlimit;
												$scope.modalInstanceCtrl26.onlinePassword = r.data.resultData.onlinePassword;
												$scope.modalInstanceCtrl26.salesOfficeOption = r.data.resultData.salesOfficeOption;

												$scope.modalInstanceCtrl26.identificationType = r.data.resultData.identificationType;
												if (r.data.resultData.identificationType == "A1") {
													$scope.modalInstanceCtrl26.imageHide = false;
												}
												$scope.modalInstanceCtrl26.livingConditions = r.data.resultData.livingConditions;
												$scope.modalInstanceCtrl26.ipAddress = r.data.resultData.ipAddress;
												$scope.modalInstanceCtrl26.originalStatus = r.data.resultData.status;
												// ひろぎんネット支店を知った経緯
												$scope.modalInstanceCtrl26.knowProcess = r.data.resultData.knowProcess;
												$scope.modalInstanceCtrl26.knowProcessOther = r.data.resultData.knowProcessOther;
												$scope.modalInstanceCtrl26.pushData = r.data.resultData.yamagataStatusModifyList;
												$scope.modalInstanceCtrl26.status = r.data.resultData.status;
												imageLoad(r.data.resultData.identificationImage);
												$scope.modalInstanceCtrl26.imageLoadHide = false;

											} else if (bank_cd == "0173") {

												$scope.modalInstanceCtrl26.accountAppSeq = r.data.resultData.accountAppSeq;
												$scope.modalInstanceCtrl26.userId = r.data.resultData.userId;
												$scope.modalInstanceCtrl26.userType = r.data.resultData.userType;
												$scope.modalInstanceCtrl26.deviceTokenId = r.data.resultData.deviceTokenId;
												$scope.modalInstanceCtrl26.applicationEndFlg = r.data.resultData.applicationEndFlg;
												$scope.modalInstanceCtrl26.applicationFlg = r.data.resultData.applicationFlg;
												$scope.modalInstanceCtrl26.licenseIdR = r.data.resultData.licenseIdR;
												$scope.modalInstanceCtrl26.name = r.data.resultData.name;
												$scope.modalInstanceCtrl26.firstNameR = r.data.resultData.firstNameR;
												$scope.modalInstanceCtrl26.birthdayR = r.data.resultData.birthdayR;
												$scope.modalInstanceCtrl26.licenseId = r.data.resultData.licenseId;
												$scope.modalInstanceCtrl26.lastName = r.data.resultData.lastName;
												$scope.modalInstanceCtrl26.firstName = r.data.resultData.firstName;
												$scope.modalInstanceCtrl26.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl26.kanaFirstName = r.data.resultData.kanaFirstName;
												$scope.modalInstanceCtrl26.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl26.sexKbn = r.data.resultData.sexKbn;
												$scope.modalInstanceCtrl26.postCodeR = r.data.resultData.postCodeR;
												$scope.modalInstanceCtrl26.prefecturesCodeR = r.data.resultData.prefecturesCodeR;
												$scope.modalInstanceCtrl26.addressR = r.data.resultData.addressR;
												$scope.modalInstanceCtrl26.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl26.prefecturesCode = r.data.resultData.prefecturesCode;
												$scope.modalInstanceCtrl26.address = r.data.resultData.address;
												$scope.modalInstanceCtrl26.kanaAddress = r.data.resultData.kanaAddress;
												$scope.modalInstanceCtrl26.teleNumber = r.data.resultData.teleNumber;
												$scope.modalInstanceCtrl26.phoneNumber = r.data.resultData.phoneNumber;
												$scope.modalInstanceCtrl26.jobKbn = r.data.resultData.jobKbn;
												$scope.modalInstanceCtrl26.jobKbnOther = r.data.resultData.jobKbnOther;
												$scope.modalInstanceCtrl26.workName = r.data.resultData.workName;
												$scope.modalInstanceCtrl26.kanaWorkName = r.data.resultData.kanaWorkName;
												$scope.modalInstanceCtrl26.workPostCode = r.data.resultData.workPostCode;
												$scope.modalInstanceCtrl26.workPrefecturesCode = r.data.resultData.workPrefecturesCode;
												$scope.modalInstanceCtrl26.workAddress = r.data.resultData.workAddress;
												$scope.modalInstanceCtrl26.workTeleNumber = r.data.resultData.workTeleNumber;
												$scope.modalInstanceCtrl26.workNumberKbn = r.data.resultData.workNumberKbn;
												$scope.modalInstanceCtrl26.accountType = r.data.resultData.accountType;
												$scope.modalInstanceCtrl26.bankbookType = r.data.resultData.bankbookType;
												$scope.modalInstanceCtrl26.cardType = r.data.resultData.cardType;
												$scope.modalInstanceCtrl26.salesOfficeOption = r.data.resultData.salesOfficeOption;
												$scope.modalInstanceCtrl26.accountPurpose = r.data.resultData.accountPurpose;
												$scope.modalInstanceCtrl26.accountPurposeOther = r.data.resultData.accountPurposeOther;
												$scope.modalInstanceCtrl26.securityPassword = r.data.resultData.securityPassword;
												$scope.modalInstanceCtrl26.securityPasswordConfirm = r.data.resultData.securityPasswordConfirm;
												$scope.modalInstanceCtrl26.creditlimit = r.data.resultData.creditlimit;
												$scope.modalInstanceCtrl26.onlinePassword = r.data.resultData.onlinePassword;
												$scope.modalInstanceCtrl26.onlinePasswordConfirm = r.data.resultData.onlinePasswordConfirm;
												$scope.modalInstanceCtrl26.knowProcess = r.data.resultData.knowProcess;
												$scope.modalInstanceCtrl26.applicationReason = r.data.resultData.applicationReason;
												$scope.modalInstanceCtrl26.status = r.data.resultData.status;
												$scope.modalInstanceCtrl26.selectStatus = r.data.resultData.status;
												$scope.modalInstanceCtrl26.identificationType = r.data.resultData.identificationType;
												if (r.data.resultData.identificationType == "A1") {
													$scope.modalInstanceCtrl26.imageHide = false;
												}
												$scope.modalInstanceCtrl26.pushData = r.data.resultData.statusModifyList;
												imageLoad(r.data.resultData.identificationImage);
												$scope.modalInstanceCtrl26.imageLoadHide = false;
												$scope.modalInstanceCtrl26.livingConditions = r.data.resultData.livingConditions;

											}
										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrl26.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl26.cancel = function() {
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
												$scope.modalInstanceCtrl26.identificationImage = "";
												$scope.modalInstanceCtrl26.identificationImageBack = "";
												$scope.modalInstanceCtrl26.imageLoadHide = true;
												swal(params);

											} else {
												$scope.modalInstanceCtrl26.identificationImage = r.data.resultData.identificationImage;
												$scope.modalInstanceCtrl26.identificationImageBack = r.data.resultData.identificationImageBack;
												$scope.modalInstanceCtrl26.imageLoadHide = true;
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 暗証番号表示
					$scope.modalInstanceCtrl26.showSecPwd = function() {
						$scope.modalInstanceCtrl26.passwordHide = true;
					}
					// 暗証番号非表示
					$scope.modalInstanceCtrl26.hideSecPwd = function() {
						$scope.modalInstanceCtrl26.passwordHide = false;
					}
					// ダイレクトバンキングカード暗証番号表示
					$scope.modalInstanceCtrl26.showDirPwd = function() {
						$scope.modalInstanceCtrl26.dirPasswordHide = true;
					}
					// ダイレクトバンキングカード暗証番号非表示
					$scope.modalInstanceCtrl26.hideDirPwd = function() {
						$scope.modalInstanceCtrl26.dirPasswordHide = false;
					}
				})
