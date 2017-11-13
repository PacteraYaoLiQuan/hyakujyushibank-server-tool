insightApp
		.controller(
				'accountLoanListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, accountLoanListService, insightService,
						strSpliceService) {

					// controllerオブジェクト変数
					$scope.accountLoanListCtrl = {};
					var bank_cds = $("#accountBankCodeAppList").val();

					// ステータス変数
					$scope.accountLoanListCtrl.selectStatus = [ "1" ];

					// 申込受付日付モジュール変数
					$scope.accountLoanListCtrl.datepicker = {}
					$scope.accountLoanListCtrl.user = $("#accountAppList")
							.val();
					if ($scope.accountLoanListCtrl.user == "1") {
						$scope.accountLoanListCtrl.btnDisabled = true;
					}
					$scope.accountLoanListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD HH:mm",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.accountLoanListCtrl.datepicker.formats[1])
														+ " 09:00:00"),
										useCurrent : false
									});
					setTimeout(function() {
						$('#receiptDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.accountLoanListCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.accountLoanListCtrl.datepicker[opened] = true;
					};

					$scope.accountLoanListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.accountLoanListCtrl.datepicker.format = $scope.accountLoanListCtrl.datepicker.formats[1];

					// 一覧モジュール変数
					$scope.accountLoanListCtrl.table = {};
					$scope.accountLoanListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.accountLoanListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.accountLoanListCtrl.table.checkboxes.checked;
										});
						console
								.log($scope.accountLoanListCtrl.table.checkboxes);
					}
					$scope.accountLoanListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.accountLoanListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.accountLoanListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.accountLoanListCtrl.table.checkboxes.checked;
										});
						console
								.log($scope.accountLoanListCtrl.table.checkboxes);
					}
					// ステータス内容を取得
					$scope.accountLoanListCtrl.table.filterList = insightService
							.getInsightData("loanData");
					// PUSH開封状況内容を取得
					$scope.accountLoanListCtrl.table.filterList2 = insightService
							.getInsightData("pushStatusData");
					$scope.accountLoanListCtrl.table.filterList3 = insightService
							.getInsightData("loanTypeData");
					if ($("#accountLoanList").val() == "1") {
						$scope.accountLoanListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "accountLoanListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.accountLoanListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "accountLoanListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}

					// 帳票出力ボタンを押下時、
					$scope.accountLoanListCtrl.table.lsOutput = function(size) {
						var deferred = $q.defer();
						var flg = "0";
						var index = 0;
						angular.forEach(
								$scope.accountLoanListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
										index++;
									}
								});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// 帳票出力ボタンの制御解除する
						if (flg == "0") {
							swal("処理対象の申込を選択してください。", "", "error");
							deferred.resolve("resolve");
						} else if (index > 10) {
							swal("１０データ以内で選択してください。。", "", "error");
							deferred.resolve("resolve");
						} else {
							// 帳票出力確認メッセージを呼出
							var params5 = {
								title : "帳票ファイルをダウンロードしますか？",
								text : "",
								type : "warning",
								showCancelButton : true,
								confirmButtonColor : "#DD6B55",
								confirmButtonText : "OK",
								cancelButtonText : "キャンセル",
								closeOnConfirm : true,
								closeOnCancel : true
							};
							swal(
									params5,
									function(isConfirm) {
										// 確認ボタンを押下場合
										if (isConfirm) {
											// 帳票出力データ取得
											var url = "./../protected/account/loan/outputButton";
											var data = $scope.accountLoanListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = accountLoanListService
													.setListstatusUpd(url, {
														'outputList' : data
													});

											data
													.then(
															function(r) {
																// 帳票出力データ取得途中、エラーを発生、エラーメッセージを表示
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
																				// 更新ボタンを押下場合、画面データを再取得し、再表示する
																				// 画面で、帳票出力ボタンの制御解除する
																				if (isConfirm) {
																					loadData(deferred);
																				} else {
																					// キャンセルボタンを押下場合、帳票出力ボタンの制御解除する
																					deferred
																							.resolve("resolve");
																				}
																			});
																} else {
																	/*// 帳票出力データ取得途中、エラーなし場合、帳票をダウン‐ロードする
																	var url = "./../protected/account/loan/csvDownLoad";
																	var input1 = $("<input>");
																	var form = $("<form>");
																	form
																			.attr(
																					'style',
																					'display:none');
																	form
																			.attr(
																					'target',
																					'');
																	form
																			.attr(
																					'method',
																					'get');
																	form
																			.attr(
																					'action',
																					url);
																	input1
																			.attr(
																					"type",
																					"hidden");
																	input1
																			.attr(
																					"name",
																					"date");
																	input1
																			.attr(
																					"value",
																					r.data.resultData.date);

																	$('body')
																			.append(
																					form);
																	form
																			.append(input1);
																	form
																			.submit();
																	form
																			.remove();
																	loadData(deferred);*/
																	loadData(deferred);
																	var modalInstance = $uibModal
																			.open({
																				animation : true,
																				templateUrl : 'pdfOpen.html',
																				controller : 'modalInstanceController001',
																				backdrop : 'static',
																				size : size,
																				resolve : {
																					key : function() {
																						return {
																							'date' : r.data.resultData.date

																						};
																					}
																				}
																			});

																	modalInstance.result
																			.then(
																					function(
																							result) {

																					},
																					function(
																							reason) {
																						console
																								.log('Modal dismissed at: '
																										+ new Date()
																										+ "  ==>  "
																										+ reason);
																					});
																}
															}, function(e) {
																console.log(e);
															});
										} else {
											// キャンセルボタンを押下場合、帳票出力ボタンの制御解除する
											deferred.resolve("resolve");
										}
									});
						}
						return deferred.promise;
					}

					// 完了消込ボタンを押下時、
					$scope.accountLoanListCtrl.table.completeBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";

						angular.forEach(
								$scope.accountLoanListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
										if (item.status == "2"
												|| item.status == "9") {
											endFlg = "0";
										} else {
											endFlg = "1";
										}
									}
								});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// 完了消込ボタンの制御解除する
						if (flg == "0") {
							swal("処理対象の申込を選択してください。", "", "error");
							deferred.resolve("resolve");
						} else if (endFlg == "1") {
							// 一覧で、選択したデータ中に、ステータスは「処理中」ではない場合、エラーメッセージを表示
							// 完了消込ボタンの制御解除する
							swal("ステータスが「処理中／連絡依頼」の申込を選択してください。", "", "error");
							deferred.resolve("resolve");
						} else {
							// 完了確認メッセージを呼出
							var params2 = {
								title : "ステータスを完了にしますか？",
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
										// 確認ボタンを押下場合
										if (isConfirm) {
											// 完了消込データ取得
											var url = "./../protected/accountLoan/completeButton";
											var data = $scope.accountLoanListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = accountLoanListService
													.setListstatusUpd(url, {
														'completeList' : data
													});

											data
													.then(
															function(r) {
																// 完了消込データ取得途中、エラーを発生、エラーメッセージを表示
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
																				// 更新ボタンを押下場合、画面データを再取得し、再表示する
																				// 画面で、完了消込ボタンの制御解除する
																				if (isConfirm) {
																					loadData(deferred);
																				} else {
																					// キャンセルボタンを押下場合、完了消込ボタンの制御解除する
																					deferred
																							.resolve("resolve");
																				}
																			});
																} else {
																	// 完了消込データ取得途中、エラーなし場合、画面データを再取得し、再表示する
																	// 画面で、完了消込ボタンの制御解除する
																	loadData(deferred);
																}
															}, function(e) {
															});
										} else {
											// キャンセルボタンを押下場合、完了消込ボタンの制御解除する
											deferred.resolve("resolve");
										}
									});
						}
						return deferred.promise;
					}
					// 申込詳細画面を呼び出す
					$scope.accountLoanListCtrl.table.openNa = function(size,
							item) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup.html',
							controller : 'modalInstanceCtrl001',
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

					// Push通知履歴結果画面を呼び出す
					$scope.accountLoanListCtrl.table.openHistory = function(
							size, item) {
						var modalHistoryPush = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup5.html',
							controller : 'modalHistoryPushCtrl1',
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

						modalHistoryPush.result.then(function(result) {
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

					$scope.$on();

					// 初期化データを取得
					function loadData(deferred) {

						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = accountLoanListService.setListstatusUpd(url,
								data);

						// 一覧初期データ取得
						var url = "./../protected/account/loanAppList";

						$scope.accountLoanListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = accountLoanListService.getListData(url,
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
												swal(
														insightService
																.getMessageData("applyStatusMessage")[codeList[0]],
														"", "error");
												// 初期化以外場合（データ再取得場合）
												if (typeof deferred != "undefined") {
													// 帳票出力ボタン／CSV出力ボタン／完了消込ボタンの制御解除する
													deferred.resolve("resolve");
													// 再検索の場合、データがなし場合、先に残留したデータをクリア
													if (codeList[0] == "e.accountAppList.1002") {
														$scope.accountLoanListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.accountAppList;
														$scope.accountLoanListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												// データ取得途中、エラーなし場合、画面データを再表示する
												// 帳票出力ボタン／CSV出力ボタン／完了消込ボタンの制御解除する
												$scope.accountLoanListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.accountLoanList;
												$scope.accountLoanListCtrl.table.sortAndFilter
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
				'modalInstanceController001',
				function($scope, $uibModalInstance, key) {
					var pdfDate = key.date;
					$scope.openPDF = "./../protected/account/imageOpen?date="
							+ pdfDate;
					$scope.modalInstanceController001 = {};
					$scope.modalInstanceController001.close = function() {
						$uibModalInstance.close({});
					};
				})
		// 申込詳細表示
		.controller(
				'modalInstanceCtrl001',
				function($scope, $uibModalInstance, key,
						accountLoanListService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrl001 = {};
					var bank_cd = $("#accountBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountLoanListService.statusUpd(url, data);
					$scope.modalInstanceCtrl001.originalStatus = {};
					$scope.modalInstanceCtrl001.imageHide = true;
					$scope.modalInstanceCtrl001.holdHide = true;
					$scope.modalInstanceCtrl001.contractHide = true;
					$scope.modalInstanceCtrl001.imageLoadHide = true;
					$scope.modalInstanceCtrl001.passwordHide = false;
					$scope.modalInstanceCtrl001.dirPasswordHide = false;
					var url = "./../protected/account/accountLoanDetail";
					var params = {
						"_id" : key._id
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

											$scope.modalInstanceCtrl001.accountAppSeq = r.data.resultData.accountAppSeq;
											$scope.modalInstanceCtrl001.loanType = r.data.resultData.loanType;
											if ("0" == r.data.resultData.loanFlag) {
												$scope.modalInstanceCtrl001.loanType0 == true;
												$scope.modalInstanceCtrl001.loanType1 == false;
											} else {
												$scope.modalInstanceCtrl001.loanType0 == false;
												$scope.modalInstanceCtrl001.loanType1 == true;
											}
											$scope.modalInstanceCtrl001.docType = r.data.resultData.docType;
											$scope.modalInstanceCtrl001.userId = r.data.resultData.userId;
											$scope.modalInstanceCtrl001.userType = r.data.resultData.userType;
											$scope.modalInstanceCtrl001.loanAppSeq = r.data.resultData.loanAppSeq;
											$scope.modalInstanceCtrl001.loanAppTime = r.data.resultData.loanAppTime;
											$scope.modalInstanceCtrl001.status = r.data.resultData.status;
											$scope.modalInstanceCtrl001.loanType = r.data.resultData.loanType;
											$scope.modalInstanceCtrl001.reLoadInfoFlg = r.data.resultData.reLoadInfoFlg;
											$scope.modalInstanceCtrl001.agreeTime = r.data.resultData.agreeTime;
											$scope.modalInstanceCtrl001.agreeCheck = r.data.resultData.agreeCheck;
											$scope.modalInstanceCtrl001.limitMoney = r.data.resultData.limitMoney;
											$scope.modalInstanceCtrl001.dairekutoMail = r.data.resultData.dairekutoMail;
											$scope.modalInstanceCtrl001.driverLicenseSeq = r.data.resultData.driverLicenseSeq;
											$scope.modalInstanceCtrl001.readDriverLicenseNo = r.data.resultData.readDriverLicenseNo;
											$scope.modalInstanceCtrl001.readFirstName = r.data.resultData.readFirstName;
											$scope.modalInstanceCtrl001.readLastName = r.data.resultData.readLastName;
											$scope.modalInstanceCtrl001.readBirthDay = r.data.resultData.readBirthDay;
											$scope.modalInstanceCtrl001.firstName = r.data.resultData.firstName;
											$scope.modalInstanceCtrl001.lastName = r.data.resultData.lastName;
											$scope.modalInstanceCtrl001.firstKanaName = r.data.resultData.firstKanaName;
											$scope.modalInstanceCtrl001.lastKanaName = r.data.resultData.lastKanaName;
											$scope.modalInstanceCtrl001.birthday = r.data.resultData.birthday;
											$scope.modalInstanceCtrl001.yearType = r.data.resultData.yearType;
											$scope.modalInstanceCtrl001.sexKbn = r.data.resultData.sexKbn;
											$scope.modalInstanceCtrl001.eraBirthday = r.data.resultData.eraBirthday;
											$scope.modalInstanceCtrl001.age = r.data.resultData.age;
											$scope.modalInstanceCtrl001.country = r.data.resultData.country;
											$scope.modalInstanceCtrl001.driverLicenseFlg = r.data.resultData.driverLicenseFlg;
											$scope.modalInstanceCtrl001.driverLicenseNo = r.data.resultData.driverLicenseNo;
											$scope.modalInstanceCtrl001.readPostCode = r.data.resultData.readPostCode;
											$scope.modalInstanceCtrl001.readPrefecturesCode = r.data.resultData.readPrefecturesCode;
											$scope.modalInstanceCtrl001.readAddress = r.data.resultData.readAddress;
											$scope.modalInstanceCtrl001.readOtherAddress = r.data.resultData.readOtherAddress;
											$scope.modalInstanceCtrl001.postCode = r.data.resultData.postCode;
											$scope.modalInstanceCtrl001.prefecturesCode = r.data.resultData.prefecturesCode;
											$scope.modalInstanceCtrl001.address = r.data.resultData.address;
											$scope.modalInstanceCtrl001.otherAddress = r.data.resultData.otherAddress;
											$scope.modalInstanceCtrl001.teleNumber = r.data.resultData.teleNumber;
											$scope.modalInstanceCtrl001.phoneNumber = r.data.resultData.phoneNumber;
											$scope.modalInstanceCtrl001.enAddress = r.data.resultData.enAddress;
											$scope.modalInstanceCtrl001.liveYear = r.data.resultData.liveYear;
											$scope.modalInstanceCtrl001.liveMonth = r.data.resultData.liveMonth;
											$scope.modalInstanceCtrl001.rentLoan = r.data.resultData.rentLoan;
											$scope.modalInstanceCtrl001.spouse = r.data.resultData.spouse;
											$scope.modalInstanceCtrl001.liveType = r.data.resultData.liveType;
											$scope.modalInstanceCtrl001.familyNumber = r.data.resultData.familyNumber;
											$scope.modalInstanceCtrl001.masterKnowFlg = r.data.resultData.masterKnowFlg;
											$scope.modalInstanceCtrl001.sendFlg = r.data.resultData.sendFlg;
											$scope.modalInstanceCtrl001.companyName = r.data.resultData.companyName;
											$scope.modalInstanceCtrl001.companyKanaName = r.data.resultData.companyKanaName;
											$scope.modalInstanceCtrl001.jobType = r.data.resultData.jobType;
											$scope.modalInstanceCtrl001.workPostCode = r.data.resultData.workPostCode;
											$scope.modalInstanceCtrl001.workPrefecturesCode = r.data.resultData.workPrefecturesCode;
											$scope.modalInstanceCtrl001.workAddress = r.data.resultData.workAddress;
											$scope.modalInstanceCtrl001.workOtherAddress = r.data.resultData.workOtherAddress;
											$scope.modalInstanceCtrl001.workTeleNumber = r.data.resultData.workTeleNumber;
											$scope.modalInstanceCtrl001.workPhoneNumber = r.data.resultData.workPhoneNumber;
											$scope.modalInstanceCtrl001.getKbnFlg = r.data.resultData.getKbnFlg;
											$scope.modalInstanceCtrl001.getKbn = r.data.resultData.getKbn;
											$scope.modalInstanceCtrl001.position = r.data.resultData.position;
											$scope.modalInstanceCtrl001.jobNumber = r.data.resultData.jobNumber;
											$scope.modalInstanceCtrl001.workYear = r.data.resultData.workYear;
											$scope.modalInstanceCtrl001.workMonth = r.data.resultData.workMonth;
											$scope.modalInstanceCtrl001.department = r.data.resultData.department;
											$scope.modalInstanceCtrl001.workStartYearType = r.data.resultData.workStartYearType;
											$scope.modalInstanceCtrl001.workYearMonth = r.data.resultData.workYearMonth;
											$scope.modalInstanceCtrl001.payment = r.data.resultData.payment;
											$scope.modalInstanceCtrl001.graduateYearMonth = r.data.resultData.graduateYearMonth;
											$scope.modalInstanceCtrl001.graduateYearType = r.data.resultData.graduateYearType;
											$scope.modalInstanceCtrl001.visitingName = r.data.resultData.visitingName;
											$scope.modalInstanceCtrl001.visitingKanaName = r.data.resultData.visitingKanaName;
											$scope.modalInstanceCtrl001.visitingDepartment = r.data.resultData.visitingDepartment;
											$scope.modalInstanceCtrl001.visitingPosition = r.data.resultData.visitingPosition;
											$scope.modalInstanceCtrl001.visitingPostCode = r.data.resultData.visitingPostCode;
											$scope.modalInstanceCtrl001.visitPreCode = r.data.resultData.visitPreCode;
											$scope.modalInstanceCtrl001.visitAddress = r.data.resultData.visitAddress;
											$scope.modalInstanceCtrl001.visitOtherAddress = r.data.resultData.visitOtherAddress;
											$scope.modalInstanceCtrl001.visitTelNumber = r.data.resultData.visitTelNumber;
											$scope.modalInstanceCtrl001.incomeFlg = r.data.resultData.incomeFlg;
											$scope.modalInstanceCtrl001.incomeYear = r.data.resultData.incomeYear;
											$scope.modalInstanceCtrl001.workContent = r.data.resultData.workContent;
											$scope.modalInstanceCtrl001.workNumber = r.data.resultData.workNumber;
											$scope.modalInstanceCtrl001.workType = r.data.resultData.workType;
											$scope.modalInstanceCtrl001.workTypeCode = r.data.resultData.workTypeCode;
											$scope.modalInstanceCtrl001.money = r.data.resultData.money;
											$scope.modalInstanceCtrl001.rentLoanContract = r.data.resultData.rentLoanContract;
											$scope.modalInstanceCtrl001.comPreCode = r.data.resultData.comPreCode;
											$scope.modalInstanceCtrl001.healthType = r.data.resultData.healthType;
											$scope.modalInstanceCtrl001.applicationMoney = r.data.resultData.applicationMoney;
											$scope.modalInstanceCtrl001.getHopeDate = r.data.resultData.getHopeDate;
											$scope.modalInstanceCtrl001.returnHopeMonth = r.data.resultData.returnHopeMonth;
											$scope.modalInstanceCtrl001.returnHopeCount = r.data.resultData.returnHopeCount;
											$scope.modalInstanceCtrl001.inCount = r.data.resultData.inCount;
											$scope.modalInstanceCtrl001.purposeFlg = r.data.resultData.purposeFlg;
											$scope.modalInstanceCtrl001.otherPurpose = r.data.resultData.otherPurpose;
											$scope.modalInstanceCtrl001.purpose = r.data.resultData.purpose;
											$scope.modalInstanceCtrl001.moneyTotal = r.data.resultData.moneyTotal;
											$scope.modalInstanceCtrl001.payMoney1 = r.data.resultData.payMoney1;
											$scope.modalInstanceCtrl001.money1 = r.data.resultData.money1;
											$scope.modalInstanceCtrl001.payMoney2 = r.data.resultData.payMoney2;
											$scope.modalInstanceCtrl001.money2 = r.data.resultData.money2;
											$scope.modalInstanceCtrl001.ownAccountKbn = r.data.resultData.ownAccountKbn;
											$scope.modalInstanceCtrl001.storeName = r.data.resultData.storeName;
											$scope.modalInstanceCtrl001.storeNameFlg = r.data.resultData.storeNameFlg;
											$scope.modalInstanceCtrl001.accountNumber = r.data.resultData.accountNumber;
											$scope.modalInstanceCtrl001.increaseFlg = r.data.resultData.increaseFlg;
											$scope.modalInstanceCtrl001.returnDay = r.data.resultData.returnDay;
											$scope.modalInstanceCtrl001.returnStartDay = r.data.resultData.returnStartDay;
											$scope.modalInstanceCtrl001.returnMoney = r.data.resultData.returnMoney;
											$scope.modalInstanceCtrl001.returnHopeCount = r.data.resultData.returnHopeCount;
											$scope.modalInstanceCtrl001.increaseReturn1 = r.data.resultData.increaseReturn1;
											$scope.modalInstanceCtrl001.increaseReturn2 = r.data.resultData.increaseReturn2;
											$scope.modalInstanceCtrl001.returnStartDay2 = r.data.resultData.returnStartDay2;
											$scope.modalInstanceCtrl001.returnMoney2 = r.data.resultData.returnMoney2    ;
											$scope.modalInstanceCtrl001.getFlg = r.data.resultData.getFlg;
											$scope.modalInstanceCtrl001.getCount = r.data.resultData.getCount;
											$scope.modalInstanceCtrl001.getMoney = r.data.resultData.getMoney;
											$scope.modalInstanceCtrl001.getFromOtherFlg = r.data.resultData.getFromOtherFlg;
											$scope.modalInstanceCtrl001.noLoanRest = r.data.resultData.noLoanRest;
											$scope.modalInstanceCtrl001.noLoanReturnMoney = r.data.resultData.noLoanReturnMoney;
											$scope.modalInstanceCtrl001.loanRest = r.data.resultData.loanRest;
											$scope.modalInstanceCtrl001.loanReturnMoney = r.data.resultData.loanReturnMoney;
											$scope.modalInstanceCtrl001.cardLoanRest = r.data.resultData.cardLoanRest;
											$scope.modalInstanceCtrl001.cardLoanRestRM = r.data.resultData.cardLoanRestRM;
											$scope.modalInstanceCtrl001.otherComRest2 = r.data.resultData.otherComRest2;
											$scope.modalInstanceCtrl001.otherComReturnMoney2 = r.data.resultData.otherComReturnMoney2;
											$scope.modalInstanceCtrl001.otherComRest3 = r.data.resultData.otherComRest3;
                                            $scope.modalInstanceCtrl001.otherComReturnMoney3 = r.data.resultData.otherComReturnMoney3;
                                            $scope.modalInstanceCtrl001.otherComRest4 = r.data.resultData.otherComRest4;
                                            $scope.modalInstanceCtrl001.otherComReturnMoney4 = r.data.resultData.otherComReturnMoney4;
                                            $scope.modalInstanceCtrl001.otherComRest5 = r.data.resultData.otherComRest5;
                                            $scope.modalInstanceCtrl001.otherComReturnMoney5 = r.data.resultData.otherComReturnMoney5;
                                            $scope.modalInstanceCtrl001.otherComRest6 = r.data.resultData.otherComRest6;
                                            $scope.modalInstanceCtrl001.otherComReturnMoney6 = r.data.resultData.otherComReturnMoney6;
											$scope.modalInstanceCtrl001.hopeStoreNmae = r.data.resultData.hopeStoreNmae;
											$scope.modalInstanceCtrl001.hopeStoreFlg = r.data.resultData.hopeStoreFlg;
											$scope.modalInstanceCtrl001.bankAccount = r.data.resultData.bankAccount;
											$scope.modalInstanceCtrl001.selectStatus = r.data.resultData.status;
	                                         imageLoad(r.data.resultData.driverLicenseSeq);
	                                            $scope.modalInstanceCtrl001.imageLoadHide = false;
	                                            if ($("#accountAppList").val() == "1") {
	                                                $scope.modalInstanceCtrl001.UpdbtnDisabled = true;
	                                            } else {
	                                                $scope.modalInstanceCtrl001.UpdbtnDisabled = false;
	                                            }

										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrl001.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl001.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

					// ステータス更新ボタンを押す
					$scope.modalInstanceCtrl001.statusUpd = function() {
						$scope.modalInstanceCtrl001.UpdbtnDisabled = true;
						var url = "./../protected/account/accountLoanStatusUpd";
						if ($scope.modalInstanceCtrl001.originalStatus != $scope.modalInstanceCtrl001.selectStatus) {
							var data = {
								"_id" : key._id,
								"status" : $scope.modalInstanceCtrl001.selectStatus
							};
							var data = accountLoanListService.statusUpd(url,
									data);
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
													$scope.modalInstanceCtrl001.UpdbtnDisabled = false;
													$uibModalInstance.close({
														'load' : true
													});
												} else {
													swal("ステータスを変更しました。", "",
															"success");
													$scope.modalInstanceCtrl001.UpdbtnDisabled = false;
													$uibModalInstance
															.close({
																'status' : $scope.modalInstanceCtrl001.selectStatus,
																'pushStatus' : r.data.resultData.pushStatus
															});
												}
											}, function(e) {
												console.log(e);
											});
						} else {
							var params = {
								title : insightService
										.getMessageData("applyStatusMessage")["e.accountAppDetail.1002"],
								text : "",
								type : "warning",
								showCancelButton : false,
								closeOnConfirm : true
							};
							swal(params);
							$scope.modalInstanceCtrl001.UpdbtnDisabled = false;
						}
					};
					// 暗証番号表示
					$scope.modalInstanceCtrl001.showSecPwd = function() {
						$scope.modalInstanceCtrl001.passwordHide = true;
					}
					// 暗証番号非表示
					$scope.modalInstanceCtrl001.hideSecPwd = function() {
						$scope.modalInstanceCtrl001.passwordHide = false;
					}
					// ダイレクトバンキングカード暗証番号表示
					$scope.modalInstanceCtrl001.showDirPwd = function() {
						$scope.modalInstanceCtrl001.dirPasswordHide = true;
					}
					// ダイレクトバンキングカード暗証番号非表示
					$scope.modalInstanceCtrl001.hideDirPwd = function() {
						$scope.modalInstanceCtrl001.dirPasswordHide = false;
					}
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
												$scope.modalInstanceCtrl001.identificationImage = "";
												$scope.modalInstanceCtrl001.identificationImageBack = "";
												$scope.modalInstanceCtrl001.imageLoadHide = true;
												swal(params);

											} else {
												$scope.modalInstanceCtrl001.identificationImage = r.data.resultData.identificationImage;
												$scope.modalInstanceCtrl001.identificationImageBack = r.data.resultData.identificationImageBack;
												$scope.modalInstanceCtrl001.imageLoadHide = true;
											}
										}, function(e) {
											console.log(e);
										});
					}
				})
		// Push通知履歴結果画面表示
		.controller(
				'modalHistoryPushCtrl1',
				function($scope, $uibModalInstance, key,
						accountLoanListService, insightService,
						strSpliceService) {
					$scope.modalHistoryPushCtrl1 = {};
					var bank_cd = $("#accountBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountLoanListService.statusUpd(url, data);

					var url = "./../protected/account/accountLoanDetail";
					var params = {
						"_id" : key._id
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
											$scope.modalHistoryPushCtrl1.pushData = r.data.resultData.yamagataStatusModifyList;
										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalHistoryPushCtrl1.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalHistoryPushCtrl1.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

				})