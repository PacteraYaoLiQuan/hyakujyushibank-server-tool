insightApp
		.controller(
				'accountDocumentListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, documentListService, insightService,
						strSpliceService) {

					// controllerオブジェクト変数
					$scope.accountDocumentListCtrl = {};
					var bank_cds = $("#accountBankCodeAppList").val();

					// ステータス変数
					$scope.accountDocumentListCtrl.selectStatus = [ "1" ];

					// 申込受付日付モジュール変数
					$scope.accountDocumentListCtrl.datepicker = {}
					$scope.accountDocumentListCtrl.user = $("#accountAppList")
							.val();
					if ($scope.accountDocumentListCtrl.user == "1") {
						$scope.accountDocumentListCtrl.btnDisabled = true;
					}
					$scope.accountDocumentListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD HH:mm",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.accountDocumentListCtrl.datepicker.formats[1])
														+ " 09:00:00"),
										useCurrent : false
									});
					setTimeout(function() {
						$('#receiptDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.accountDocumentListCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.accountDocumentListCtrl.datepicker[opened] = true;
					};

					$scope.accountDocumentListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.accountDocumentListCtrl.datepicker.format = $scope.accountDocumentListCtrl.datepicker.formats[1];

					// 一覧モジュール変数
					$scope.accountDocumentListCtrl.table = {};
					$scope.accountDocumentListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.accountDocumentListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.accountDocumentListCtrl.table.checkboxes.checked;
										});
						console
								.log($scope.accountDocumentListCtrl.table.checkboxes);
					}
					$scope.accountDocumentListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.accountDocumentListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.accountDocumentListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.accountDocumentListCtrl.table.checkboxes.checked;
										});
						console
								.log($scope.accountDocumentListCtrl.table.checkboxes);
					}
					// ステータス内容を取得
					$scope.accountDocumentListCtrl.table.filterList = insightService
							.getInsightData("documentStatusData");
					// PUSH開封状況内容を取得
					$scope.accountDocumentListCtrl.table.filterList2 = insightService
							.getInsightData("pushStatusData");
					// 電話番号鑑定/IPアドレス鑑定結果内容を取得
					$scope.accountDocumentListCtrl.table.purposeList = insightService
							.getInsightData("documentPurposeData");
					if ($("#accountAppList").val() == "1") {
						$scope.accountDocumentListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "accountDocumentListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.accountDocumentListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "accountDocumentListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 帳票出力ボタンを押下時、
					$scope.accountDocumentListCtrl.table.lsOutput = function(
							size) {
						var deferred = $q.defer();
						var flg = "0";
						var index = 0;
						angular
								.forEach(
										$scope.accountDocumentListCtrl.table.sortAndFilter
												.settings().dataset, function(
												item) {
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
								title : "帳票CSVファイルをダウンロードしますか？",
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
											var url = "./../protected/account/outputButton";
											var data = $scope.accountDocumentListCtrl.table.sortAndFilter
													.settings().dataset;
											if (bank_cds == "0169") {
												var data = documentListService
														.setListstatusUpd(
																url,
																{
																	'outputList' : data
																});
											} else if (bank_cds == "0122") {
												var data = documentListService
														.setListstatusUpd(
																url,
																{
																	'outputList2' : data
																});
											}
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
																	// 帳票出力データ取得途中、エラーなし場合、帳票をダウン‐ロードする
																	var url = "./../protected/account/csvDownLoad";
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

																	// 画面データを再取得し、再表示する
																	// 画面で、帳票出力ボタンの制御解除する
																	loadData(deferred);

																	var modalInstance = $uibModal
																			.open({
																				animation : true,
																				templateUrl : 'pdfOpen.html',
																				controller : 'modalInstanceController2',
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

					// CSVデータ出力ボタンを押下時、
					$scope.accountDocumentListCtrl.table.csvOutput = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular
								.forEach(
										$scope.accountDocumentListCtrl.table.sortAndFilter
												.settings().dataset, function(
												item) {
											if (item.select) {
												flg = "1";
											}
										});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// CSV出力ボタンの制御解除する
						if (flg == "0") {
							swal("処理対象の申込を選択してください。", "", "error");
							deferred.resolve("resolve");
						} else {
							// CSV出力確認メッセージを呼出
							var params4 = {
								title : "申込顧客CSVファイルをダウンロードしますか？",
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
									params4,
									function(isConfirm) {
										// 確認ボタンを押下場合
										if (isConfirm) {
											// CSVデータ出力データ取得
											var url = "./../protected/account/csvButton";
											var data = $scope.accountDocumentListCtrl.table.sortAndFilter
													.settings().dataset;
											if (bank_cds == "0169") {
												var data = documentListService
														.setListstatusUpd(
																url,
																{
																	'csvList' : data
																});
											} else if (bank_cds == "0122") {
												var data = documentListService
														.setListstatusUpd(
																url,
																{
																	'csvList2' : data
																});
											}
											data
													.then(
															function(r) {
																// CSV出力データ取得途中、エラーを発生、エラーメッセージを表示
																// CSV出力ボタンの制御解除する
																if (r.data.resultStatus == "NG") {
																	deferred
																			.resolve("resolve");
																	var codeList = strSpliceService
																			.resultCodeSplice(
																					r.data.messages,
																					"code");
																	swal(
																			insightService
																					.getMessageData("applyStatusMessage")[codeList[0]],
																			"",
																			"error");
																} else {
																	// CSV出力データ取得途中、エラーなし場合、CSVをダウン‐ロードする
																	var url = "./../protected/account/csvButtonDownLoad";
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

																	// 画面データを再取得し、再表示する
																	// 画面で、CSV出力ボタンの制御解除する
																	loadData(deferred);
																}
															}, function(e) {
																console.log(e);
															});
										} else {
											// キャンセルボタンを押下場合、CSV出力ボタンの制御解除する
											deferred.resolve("resolve");
										}
									});
						}
						return deferred.promise;
					}

					// 完了消込ボタンを押下時、
					$scope.accountDocumentListCtrl.table.completeBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";

						angular
								.forEach(
										$scope.accountDocumentListCtrl.table.sortAndFilter
												.settings().dataset, function(
												item) {
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
							if (bank_cds == "0169") {
								swal("ステータスが「処理中」の申込を選択してください。", "", "error");
							} else if (bank_cds == "0122") {
								swal("ステータスが「処理中／連絡依頼」の申込を選択してください。", "",
										"error");
							}
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
											var url = "./../protected/account/completeButton";
											var data = $scope.accountDocumentListCtrl.table.sortAndFilter
													.settings().dataset;
											if (bank_cds == "0169") {
												var data = documentListService
														.setListstatusUpd(
																url,
																{
																	'completeList' : data,
																});
											} else if (bank_cds == "0122") {
												var data = documentListService
														.setListstatusUpd(
																url,
																{
																	'completeList2' : data
																});
											}
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

					// 一覧出力ボタンを押下時、
					$scope.accountDocumentListCtrl.cc = function(size) {

						var params3 = {
							title : "進捗管理表を出力しますか？",
							text : "",
							type : "warning",
							showCancelButton : true,
							confirmButtonColor : "#DD6B55",
							confirmButtonText : "OK",
							cancelButtonText : "キャンセル",
							closeOnConfirm : true
						};
						swal(
								params3,
								function() {
									// btnDisabled
									$scope.accountDocumentListCtrl.btnDisabled = true;
									$scope.accountDocumentListCtrl.datepicker.dtPopup2 = $(
											"#dtPopup2").val();

									var data = "";
									var url = "./../protected/user/sessionTimeOut";
									var data = documentListService
											.setListstatusUpd(url, data);

									// 一覧出力データ取得
									var url = "./../protected/account/accountAppListReport";
									var receiptDate = typeof ($("#dtPopup2")
											.val()) == "undefined" ? "" : $(
											"#dtPopup2").val()
									if ($scope.accountDocumentListCtrl.selectStatus.length == 0) {
										$scope.accountDocumentListCtrl.selectStatus = [ "all" ];
									}
									var params = {
										"status" : $scope.accountDocumentListCtrl.selectStatus,
										"receiptDate" : dateFilter(receiptDate,
												"YYYY/MM/DD HH:mm")
									};
									var data = documentListService.getListData(
											url, params);
									data
											.then(
													function(r) {
														if (r.data.resultStatus == "NG") {
															$scope.accountDocumentListCtrl.btnDisabled = false;
															var codeList = strSpliceService
																	.resultCodeSplice(
																			r.data.messages,
																			"code");
															swal(
																	insightService
																			.getMessageData("applyStatusMessage")[codeList[0]],
																	"", "error");
														} else {
															var modalInstance = $uibModal
																	.open({
																		animation : true,
																		templateUrl : 'downLoad.html',
																		controller : 'modalInstanceController1',
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

															$scope.accountDocumentListCtrl.btnDisabled = false;

														}
													}, function(e) {
														console.log(e);
													});
								});
						$scope.accountDocumentListCtrl.btnDisabled = false;
					}

					// 申込詳細画面を呼び出す
					$scope.accountDocumentListCtrl.table.openNa = function(
							size, item) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup.html',
							controller : 'modalInstanceCtrl23',
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
					$scope.accountDocumentListCtrl.table.openHistory = function(
							size, item) {
						var modalHistoryPush = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup4.html',
							controller : 'documentHistoryPushCtrl',
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

					// 電話番号鑑定結果画面を呼び出す
					$scope.accountDocumentListCtrl.table.openTel = function(
							size, item) {};

					// ＩＰアドレス鑑定結果画面を呼び出す
					$scope.accountDocumentListCtrl.table.openIP = function(
							size, item) {};
					$scope.$on();

					// 初期化データを取得
					function loadData(deferred) {

						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = documentListService.setListstatusUpd(url,
								data);

						// 一覧初期データ取得
						var url = "./../protected/account/accountDocumentList";

						$scope.accountDocumentListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = documentListService.getListData(url, params);
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
														$scope.accountDocumentListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.accountDocumentList;
														$scope.accountDocumentListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												// データ取得途中、エラーなし場合、画面データを再表示する
												// 帳票出力ボタン／CSV出力ボタン／完了消込ボタンの制御解除する
												$scope.accountDocumentListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.accountDocumentList;
												$scope.accountDocumentListCtrl.table.sortAndFilter
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
				'modalInstanceController1',
				function($scope, $uibModalInstance, key) {
					var pdfDate = key.date;
					$scope.openPDF = "./../protected/account/downLoad?date="
							+ pdfDate;
					$scope.modalInstanceController1 = {};
					$scope.modalInstanceController1.close = function() {
						$uibModalInstance.close({});
					};
				})
		.controller(
				'modalInstanceController2',
				function($scope, $uibModalInstance, key) {
					var pdfDate = key.date;
					$scope.openPDF = "./../protected/account/imageOpen?date="
							+ pdfDate;
					var pdfDate = key.date;
					$scope.openPDF1 = "./../protected/account/phoneOpen?date="
							+ pdfDate;
					var pdfDate = key.date;
					$scope.openPDF2 = "./../protected/account/ipOpen?date="
							+ pdfDate;
					$scope.modalInstanceController2 = {};
					$scope.modalInstanceController2.close = function() {
						$uibModalInstance.close({});
					};
				})

		// 申込詳細表示
		.controller(
				'modalInstanceCtrl23',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrl23 = {};
					var bank_cd = $("#accountBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);
					$scope.modalInstanceCtrl23.originalStatus = {};
					$scope.modalInstanceCtrl23.imageHide = true;
					$scope.modalInstanceCtrl23.holdHide = true;
					$scope.modalInstanceCtrl23.contractHide = true;
					$scope.modalInstanceCtrl23.imageLoadHide = true;
					$scope.modalInstanceCtrl23.passwordHide = false;
					$scope.modalInstanceCtrl23.dirPasswordHide = false;
					var url = "./../protected/account/accountDocumentSel";
					var params = {
						"_id" : key._id
					};
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
											$scope.modalInstanceCtrl23.userId = r.data.resultData.userId;
											$scope.modalInstanceCtrl23.userType = r.data.resultData.userType;
											$scope.modalInstanceCtrl23.documentAppSeq = r.data.resultData.documentAppSeq;
											$scope.modalInstanceCtrl23.documentAppTime = r.data.resultData.documentAppTime;
											$scope.modalInstanceCtrl23.status = r.data.resultData.status;
											$scope.modalInstanceCtrl23.otherTypeContent = r.data.resultData.otherTypeContent;
											$scope.modalInstanceCtrl23.readFirstName = r.data.resultData.readFirstName;
											$scope.modalInstanceCtrl23.readLastName = r.data.resultData.readLastName;
											$scope.modalInstanceCtrl23.readBirthDay = r.data.resultData.readBirthDay;
											$scope.modalInstanceCtrl23.agreeTime = r.data.resultData.agreeTime;
											$scope.modalInstanceCtrl23.agreeCheck = r.data.resultData.agreeCheck;
											$scope.modalInstanceCtrl23.selfConfirmFlg = r.data.resultData.selfConfirmFlg;
											$scope.modalInstanceCtrl23.purpose = r.data.resultData.purpose;
											$scope.modalInstanceCtrl23.type = r.data.resultData.type;
											$scope.modalInstanceCtrl23.driverLicenseFSeq = r.data.resultData.driverLicenseFSeq;
											$scope.modalInstanceCtrl23.driverLicenseBSeq = r.data.resultData.driverLicenseBSeq;
											$scope.modalInstanceCtrl23.incomeCertificateSeq = r.data.resultData.incomeCertificateSeq;
											$scope.modalInstanceCtrl23.typeFlg = r.data.resultData.typeFlg;
											$scope.modalInstanceCtrl23.readDriverLicenseNo = r.data.resultData.readDriverLicenseNo;
											$scope.modalInstanceCtrl23.readName = r.data.resultData.readName;
											$scope.modalInstanceCtrl23.readBirthDay = r.data.resultData.readBirthDay;
											$scope.modalInstanceCtrl23.driverLicenseNo = r.data.resultData.driverLicenseNo;
											$scope.modalInstanceCtrl23.lastName = r.data.resultData.lastName;
											$scope.modalInstanceCtrl23.firstName = r.data.resultData.firstName;
											$scope.modalInstanceCtrl23.kanaLastName = r.data.resultData.kanaLastName;
											$scope.modalInstanceCtrl23.kanaFirstName = r.data.resultData.kanaFirstName;
											$scope.modalInstanceCtrl23.telephoneNo = r.data.resultData.telephoneNo;
											$scope.modalInstanceCtrl23.bankNo = r.data.resultData.bankNo;
											$scope.modalInstanceCtrl23.storeNo = r.data.resultData.storeNo;
											$scope.modalInstanceCtrl23.storeName = r.data.resultData.storeName;
											$scope.modalInstanceCtrl23.other = r.data.resultData.other;
											$scope.modalInstanceCtrl23.oid = r.data.resultData.oid;
											$scope.modalInstanceCtrl23.bankAccountSendStatus = r.data.resultData.bankAccountSendStatus;
											$scope.modalInstanceCtrl23.bankAddressSendStatus = r.data.resultData.bankAddressSendStatus;
											$scope.modalInstanceCtrl23.dnpSendStatus = r.data.resultData.dnpSendStatus;
											$scope.modalInstanceCtrl23.sendStatus = r.data.resultData.sendStatus;
											$scope.modalInstanceCtrl23.sendStep = r.data.resultData.sendStep;
											$scope.modalInstanceCtrl23.bankAccountSendDate = r.data.resultData.bankAccountSendDate;
											$scope.modalInstanceCtrl23.bankAddressSendDate = r.data.resultData.bankAddressSendDate;
											$scope.modalInstanceCtrl23.dnpSendDate = r.data.resultData.dnpSendDate;
											$scope.modalInstanceCtrl23.compDate = r.data.resultData.compDate;
											$scope.modalInstanceCtrl23.errorMsg = r.data.resultData.errorMsg;
											$scope.modalInstanceCtrl23.sendComplete = r.data.resultData.sendComplete;
											$scope.modalInstanceCtrl23.sendedOid = r.data.resultData.sendedOid;
											$scope.modalInstanceCtrl23.applicationDate = r.data.resultData.applicationDate;
											$scope.modalInstanceCtrl23.selectStatus = r.data.resultData.status;
											$scope.modalInstanceCtrl23.userId = r.data.resultData.userId;
											$scope.modalInstanceCtrl23.userType = r.data.resultData.userType;
											$scope.modalInstanceCtrl23.pushData = r.data.resultData.statusModifyList;
											if (r.data.resultData.card1Seq != "") {
												$scope.modalInstanceCtrl23.card1Seq = r.data.resultData.card1Seq;
											}
											if (r.data.resultData.card2Seq != "") {
												$scope.modalInstanceCtrl23.card2Seq = r.data.resultData.card2Seq;
											}
											if (r.data.resultData.card3Seq != "") {
												$scope.modalInstanceCtrl23.card3Seq = r.data.resultData.card3Seq;
											}
											if (r.data.resultData.card4Seq != "") {
												$scope.modalInstanceCtrl23.card4Seq = r.data.resultData.card4Seq;
											}
											if (r.data.resultData.card5Seq != "") {
												$scope.modalInstanceCtrl23.card5Seq = r.data.resultData.card5Seq;
											}
											if (r.data.resultData.card6Seq != "") {
												$scope.modalInstanceCtrl23.card6Seq = r.data.resultData.card6Seq;
											}
										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrl23.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl23.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

					// ステータス更新ボタンを押す
					$scope.modalInstanceCtrl23.statusUpd = function() {
						$scope.modalInstanceCtrl23.UpdbtnDisabled = true;
						var url = "./../protected/account/documentStatusUpd";
						if ($scope.modalInstanceCtrl23.originalStatus != $scope.modalInstanceCtrl23.selectStatus) {
							var data = {
								"_id" : key._id,
								"status" : $scope.modalInstanceCtrl23.selectStatus
							};
							var data = accountAppDetailService.statusUpd(url,
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
													$scope.modalInstanceCtrl23.UpdbtnDisabled = false;
													$uibModalInstance.close({
														'load' : true
													});
												} else {
													swal("ステータスを変更しました。", "",
															"success");
													$scope.modalInstanceCtrl23.UpdbtnDisabled = false;
													$uibModalInstance
															.close({
																'status' : $scope.modalInstanceCtrl23.selectStatus,
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
							$scope.modalInstanceCtrl23.UpdbtnDisabled = false;
						}
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
												$scope.modalInstanceCtrl23.identificationImage = "";
												$scope.modalInstanceCtrl23.identificationImageBack = "";
												$scope.modalInstanceCtrl23.imageLoadHide = true;
												swal(params);

											} else {
												$scope.modalInstanceCtrl23.identificationImage = r.data.resultData.identificationImage;
												$scope.modalInstanceCtrl23.identificationImageBack = r.data.resultData.identificationImageBack;
												$scope.modalInstanceCtrl23.imageLoadHide = true;
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 暗証番号表示
					$scope.modalInstanceCtrl23.showSecPwd = function() {
						$scope.modalInstanceCtrl23.passwordHide = true;
					}
					// 暗証番号非表示
					$scope.modalInstanceCtrl23.hideSecPwd = function() {
						$scope.modalInstanceCtrl23.passwordHide = false;
					}
					// ダイレクトバンキングカード暗証番号表示
					$scope.modalInstanceCtrl23.showDirPwd = function() {
						$scope.modalInstanceCtrl23.dirPasswordHide = true;
					}
					// ダイレクトバンキングカード暗証番号非表示
					$scope.modalInstanceCtrl23.hideDirPwd = function() {
						$scope.modalInstanceCtrl23.dirPasswordHide = false;
					}
				})
		// Push通知履歴結果画面表示
		.controller(
				'documentHistoryPushCtrl',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.documentHistoryPushCtrl = {};
					var bank_cd = $("#accountBankCodeAppList").val();
					$scope.documentHistoryPushCtrl.hideFlg1 = false;
					$scope.documentHistoryPushCtrl.hideFlg2 = false;
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);

					var url = "./../protected/account/documentPushDetail";
					var params = {
						"_id" : key._id
					};
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
											if (bank_cd == "0122") {
												$scope.documentHistoryPushCtrl.pushData = r.data.resultData.yamagataStatusModifyList;
											} else if (bank_cd == "0169") {
												$scope.documentHistoryPushCtrl.pushData = r.data.resultData.accountAppDetailPushList;
											} else if (bank_cd == "0173") {
												$scope.documentHistoryPushCtrl.pushData = r.data.resultData.statusModifyList;
											}

										}
									}, function(e) {
										console.log(e);
									});

					$scope.documentHistoryPushCtrl.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.documentHistoryPushCtrl.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})
