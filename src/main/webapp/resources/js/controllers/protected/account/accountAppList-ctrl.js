insightApp
		.controller(
				'accountAppListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, accountAppListService, insightService,
						strSpliceService) {

					// controllerオブジェクト変数
					$scope.accountAppListCtrl = {};
					var bank_cds = $("#accountBankCodeAppList").val();

					// ステータス変数
					$scope.accountAppListCtrl.selectStatus = [ "1" ];

					// 申込受付日付モジュール変数
					$scope.accountAppListCtrl.datepicker = {}
					$scope.accountAppListCtrl.user = $("#accountAppList").val();
					if ($scope.accountAppListCtrl.user == "1") {
						$scope.accountAppListCtrl.btnDisabled = true;
					}
					$scope.accountAppListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD HH:mm",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.accountAppListCtrl.datepicker.formats[1])
														+ " 09:00:00"),
										useCurrent : false
									});
					setTimeout(function() {
						$('#receiptDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.accountAppListCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.accountAppListCtrl.datepicker[opened] = true;
					};

					$scope.accountAppListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.accountAppListCtrl.datepicker.format = $scope.accountAppListCtrl.datepicker.formats[1];

					// 一覧モジュール変数
					$scope.accountAppListCtrl.table = {};
					$scope.accountAppListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.accountAppListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.accountAppListCtrl.table.checkboxes.checked;
										});
						console.log($scope.accountAppListCtrl.table.checkboxes);
					}
					$scope.accountAppListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.accountAppListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.accountAppListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.accountAppListCtrl.table.checkboxes.checked;
										});
						console.log($scope.accountAppListCtrl.table.checkboxes);
					}
					// ステータス内容を取得
					$scope.accountAppListCtrl.table.filterList = insightService
							.getInsightData("applyStatusData");
					$scope.accountAppListCtrl.table.accountStatusList = insightService
							.getInsightData("accountStatusData");
					// PUSH開封状況内容を取得
					$scope.accountAppListCtrl.table.filterList2 = insightService
							.getInsightData("pushStatusData");
					// 電話番号鑑定/IPアドレス鑑定結果内容を取得
					$scope.accountAppListCtrl.table.filterList3 = insightService
							.getInsightData("appraisalResultData");
					if ($("#accountAppList").val() == "1") {
						$scope.accountAppListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "accountAppListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.accountAppListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										accountAppSeq : "desc"
									}
								},
								{
									paginationCustomizeButtons : "accountAppListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 帳票出力ボタンを押下時、
					$scope.accountAppListCtrl.table.lsOutput = function(size) {
						var deferred = $q.defer();
						var flg = "0";
						var index = 0;
						angular.forEach(
								$scope.accountAppListCtrl.table.sortAndFilter
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
											var data = $scope.accountAppListCtrl.table.sortAndFilter
													.settings().dataset;
											if (bank_cds == "0169") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'outputList' : data
																});
											} else if (bank_cds == "0122") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'outputList2' : data
																});
											} else if (bank_cds == "0173") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'outputList3' : data
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
					$scope.accountAppListCtrl.table.csvOutput = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach(
								$scope.accountAppListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
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
											var data = $scope.accountAppListCtrl.table.sortAndFilter
													.settings().dataset;
											if (bank_cds == "0169") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'csvList' : data
																});
											} else if (bank_cds == "0122") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'csvList2' : data
																});
											} else if (bank_cds == "0173") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'csvList3' : data
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
					$scope.accountAppListCtrl.table.completeBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						var endFlg = "0";

						angular.forEach(
								$scope.accountAppListCtrl.table.sortAndFilter
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
							if (bank_cds == "0169") {
								swal("ステータスが「処理中」の申込を選択してください。", "", "error");
							} else if (bank_cds == "0122") {
								swal("ステータスが「処理中／連絡依頼」の申込を選択してください。", "",
										"error");
							} else if (bank_cds == "0173") {
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
											var data = $scope.accountAppListCtrl.table.sortAndFilter
													.settings().dataset;
											if (bank_cds == "0169") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'completeList' : data,
																});
											} else if (bank_cds == "0122") {
												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'completeList2' : data
																});
											} else if (bank_cds == "0173") {

												var data = accountAppListService
														.setListstatusUpd(
																url,
																{
																	'completeList3' : data
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
					$scope.accountAppListCtrl.cc = function(size) {

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
									$scope.accountAppListCtrl.btnDisabled = true;
									$scope.accountAppListCtrl.datepicker.dtPopup2 = $(
											"#dtPopup2").val();

									var data = "";
									var url = "./../protected/user/sessionTimeOut";
									var data = accountAppListService
											.setListstatusUpd(url, data);

									// 一覧出力データ取得
									var url = "./../protected/account/accountAppListReport";
									var receiptDate = typeof ($("#dtPopup2")
											.val()) == "undefined" ? "" : $(
											"#dtPopup2").val()
									if ($scope.accountAppListCtrl.selectStatus.length == 0) {
										$scope.accountAppListCtrl.selectStatus = [ "all" ];
									}
									var params = {
										"status" : $scope.accountAppListCtrl.selectStatus,
										"receiptDate" : dateFilter(receiptDate,
												"YYYY/MM/DD HH:mm")
									};
									var data = accountAppListService
											.getListData(url, params);
									data
											.then(
													function(r) {
														if (r.data.resultStatus == "NG") {
															$scope.accountAppListCtrl.btnDisabled = false;
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

															$scope.accountAppListCtrl.btnDisabled = false;

														}
													}, function(e) {
														console.log(e);
													});
								});
						$scope.accountAppListCtrl.btnDisabled = false;
					}

					// 申込詳細画面を呼び出す
					$scope.accountAppListCtrl.table.openNa = function(size,
							item) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup.html',
							controller : 'modalInstanceCtrl',
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
					$scope.accountAppListCtrl.table.openHistory = function(
							size, item) {
						var modalHistoryPush = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup4.html',
							controller : 'modalHistoryPushCtrl',
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
					$scope.accountAppListCtrl.table.openTel = function(size,
							item) {
						var modalTelAppraisal = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup2.html',
							controller : 'modalTelAppraisalCtrl',
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

						modalTelAppraisal.result
								.then(
										function(result) {
											if (result.appraisalTelResult != null) {
												item.appraisalTelResult = result.appraisalTelResult
											} else {
												if (result.load == true) {
													loadData();
												}

											}

										}, function(reason) {
											console.log('Modal dismissed at: '
													+ new Date() + "  ==>  "
													+ reason);
										});
					};

					// ＩＰアドレス鑑定結果画面を呼び出す
					$scope.accountAppListCtrl.table.openIP = function(size,
							item) {
						var modalIPAppraisal = $uibModal.open({
							animation : true,
							templateUrl : 'detailPopup3.html',
							controller : 'modalIPAppraisalCtrl',
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

						modalIPAppraisal.result
								.then(
										function(result) {
											if (result.appraisalIPResult != null) {
												item.appraisalIPResult = result.appraisalIPResult
											} else {
												if (result.load == true) {
													loadData();
												}
											}
										}, function(reason) {
											console.log('Modal dismissed at: '
													+ new Date() + "  ==>  "
													+ reason);
										});
					};
					$scope.$on();

					// 初期化データを取得
					function loadData(deferred) {

						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = accountAppListService.setListstatusUpd(url,
								data);

						// 一覧初期データ取得
						var url = "./../protected/account/accountAppList";

						$scope.accountAppListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = accountAppListService.getListData(url,
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
														$scope.accountAppListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.accountAppList;
														$scope.accountAppListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												// データ取得途中、エラーなし場合、画面データを再表示する
												// 帳票出力ボタン／CSV出力ボタン／完了消込ボタンの制御解除する
												$scope.accountAppListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.accountAppList;
												$scope.accountAppListCtrl.table.sortAndFilter
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
		// .controller(
		// 'modalInstanceController4',
		// function($scope, $uibModalInstance, key) {
		//
		// $scope.modalInstanceController4 = {};
		// $scope.modalInstanceController4.close = function() {
		// $uibModalInstance.close({});
		// };
		// })
		// .controller(
		// 'modalInstanceController5',
		// function($scope, $uibModalInstance, key) {
		// var pdfDate = key.date;
		// $scope.openPDF = "./../protected/account/pdfOpen2?date="
		// + pdfDate;
		// $scope.modalInstanceController5 = {};
		// $scope.modalInstanceController5.close = function() {
		// $uibModalInstance.close({});
		// };
		// })

		// 申込詳細表示
		.controller(
				'modalInstanceCtrl',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalInstanceCtrl = {};
					var bank_cd = $("#accountBankCodeAppList").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);
					$scope.modalInstanceCtrl.originalStatus = {};
					$scope.modalInstanceCtrl.imageHide = true;
					$scope.modalInstanceCtrl.holdHide = true;
					$scope.modalInstanceCtrl.contractHide = true;
					$scope.modalInstanceCtrl.imageLoadHide = true;
					$scope.modalInstanceCtrl.passwordHide = false;
					$scope.modalInstanceCtrl.dirPasswordHide = false;
					var url = "./../protected/account/accountAppDetail";
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
											if (bank_cd == "0169") {
												// お客様情報
												$scope.modalInstanceCtrl.name = r.data.resultData.name;
												$scope.modalInstanceCtrl.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl.sex = r.data.resultData.sex;
												$scope.modalInstanceCtrl.age = r.data.resultData.age;
												$scope.modalInstanceCtrl.sexKbn = r.data.resultData.sexKbn;
												$scope.modalInstanceCtrl.accountType = r.data.resultData.accountType;
												// 勤務先名
												if (r.data.resultData.workName == null
														|| r.data.resultData.workName == "") {
													$scope.modalInstanceCtrl.workName = "-";
												} else {
													$scope.modalInstanceCtrl.workName = r.data.resultData.workName;
												}
												$scope.modalInstanceCtrl.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl.address1 = r.data.resultData.address1;
												$scope.modalInstanceCtrl.address2 = r.data.resultData.address2;
												$scope.modalInstanceCtrl.kanaAddress = r.data.resultData.kanaAddress;
												// 自宅電話番号
												if (r.data.resultData.teleNumber == null
														|| r.data.resultData.teleNumber == "") {
													$scope.modalInstanceCtrl.teleNumber = "-";
												} else {
													$scope.modalInstanceCtrl.teleNumber = r.data.resultData.teleNumber;
												}
												// 携帯電話番号
												if (r.data.resultData.phoneNumber == null
														|| r.data.resultData.phoneNumber == "") {
													$scope.modalInstanceCtrl.phoneNumber = "-";
												} else {
													$scope.modalInstanceCtrl.phoneNumber = r.data.resultData.phoneNumber;
												}
												// 勤務先電話番号
												if (r.data.resultData.workTeleNumber == null
														|| r.data.resultData.workTeleNumber == "") {
													$scope.modalInstanceCtrl.workTeleNumber = "-";
												} else {
													$scope.modalInstanceCtrl.workTeleNumber = r.data.resultData.workTeleNumber;
												}
												// 既に口座をお持ちの方
												$scope.modalInstanceCtrl.holdAccount = r.data.resultData.holdAccount;
												// 既に口座をお持ちの方:有の場合
												if (r.data.resultData.holdAccount == "1") {
													$scope.modalInstanceCtrl.holdHide = false;
													$scope.modalInstanceCtrl.holdAccountBank = r.data.resultData.holdAccountBank;
													$scope.modalInstanceCtrl.holdAccountNumber = r.data.resultData.holdAccountNumber;
												}
												// ダイレクトバンキングサービスのご契約
												$scope.modalInstanceCtrl.directServicesContract = r.data.resultData.directServicesContract;
												// 有の場合
												if (r.data.resultData.directServicesContract == "0") {
													$scope.modalInstanceCtrl.contractHide = false;
													$scope.modalInstanceCtrl.directServicesCardPassword = r.data.resultData.directServicesCardPassword;
													// インターネットバンキングサービス
													$scope.modalInstanceCtrl.internetRegisterPerTrans = r.data.resultData.internetRegisterPerTrans;
													$scope.modalInstanceCtrl.internetRegisterPerDay = r.data.resultData.internetRegisterPerDay;
													$scope.modalInstanceCtrl.internetOncePerTrans = r.data.resultData.internetOncePerTrans;
													$scope.modalInstanceCtrl.internetOncePerDay = r.data.resultData.internetOncePerDay;
												}
												// 申込情報
												$scope.modalInstanceCtrl.accountAppSeq = r.data.resultData.accountAppSeq;
												$scope.modalInstanceCtrl.applicationDate = r.data.resultData.applicationDate;
												$scope.modalInstanceCtrl.receiptDate = r.data.resultData.receiptDate;
												$scope.modalInstanceCtrl.cardType = r.data.resultData.cardType;
												$scope.modalInstanceCtrl.noApplicationServiceList = r.data.resultData.noApplicationService;
												$scope.modalInstanceCtrl.goodsAppointed = r.data.resultData.goodsAppointed;
												$scope.modalInstanceCtrl.tradingPurposes = r.data.resultData.tradingPurposes;
												$scope.modalInstanceCtrl.otherTradingPurposes = r.data.resultData.otherTradingPurposes;
												$scope.modalInstanceCtrl.occupation = r.data.resultData.occupation;
												$scope.modalInstanceCtrl.otherOccupations = r.data.resultData.otherOccupations;
												$scope.modalInstanceCtrl.securityPassword = r.data.resultData.securityPassword;
												// テレホンバンキングサービス
												$scope.modalInstanceCtrl.telRegisterPerTrans = r.data.resultData.telRegisterPerTrans;
												$scope.modalInstanceCtrl.telRegisterPerDay = r.data.resultData.telRegisterPerDay;
												$scope.modalInstanceCtrl.telOncePerTrans = r.data.resultData.telOncePerTrans;
												$scope.modalInstanceCtrl.telOncePerDay = r.data.resultData.telOncePerDay;

												$scope.modalInstanceCtrl.identificationType = r.data.resultData.identificationType;
												if (r.data.resultData.identificationType == "A1") {
													$scope.modalInstanceCtrl.imageHide = false;
												}

												$scope.modalInstanceCtrl.livingConditions = r.data.resultData.livingConditions;
												$scope.modalInstanceCtrl.ipAddress = r.data.resultData.ipAddress;
												$scope.modalInstanceCtrl.transactionType = r.data.resultData.transactionType;
												$scope.modalInstanceCtrl.applyServiceList = r.data.resultData.applyService;
												$scope.modalInstanceCtrl.selectStatus = r.data.resultData.status;
												$scope.modalInstanceCtrl.originalStatus = r.data.resultData.status;
												// ひろぎんネット支店の口座開設の動機
												$scope.modalInstanceCtrl.accountAppMotive = r.data.resultData.accountAppMotive;
												$scope.modalInstanceCtrl.accountAppOtherMotive = r.data.resultData.accountAppOtherMotive;
												// ひろぎんネット支店を知った経緯
												$scope.modalInstanceCtrl.knowProcess = r.data.resultData.knowProcess;
												$scope.modalInstanceCtrl.knowOtherProcess = r.data.resultData.knowOtherProcess;
												$scope.modalInstanceCtrl.pushData = r.data.resultData.accountAppDetailPushList;
												if ($("#accountAppList").val() == "1") {
													$scope.modalInstanceCtrl.UpdbtnDisabled = true;
												} else {
													$scope.modalInstanceCtrl.UpdbtnDisabled = false;
												}
												imageLoad(r.data.resultData.identificationImage);
												$scope.modalInstanceCtrl.imageLoadHide = false;
											} else if (bank_cd == "0122") {
												// お客様情報
												$scope.modalInstanceCtrl.name = r.data.resultData.name;
												$scope.modalInstanceCtrl.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl.sexKbn = r.data.resultData.sexKbn;
												$scope.modalInstanceCtrl.age = r.data.resultData.age;
												$scope.modalInstanceCtrl.accountType = r.data.resultData.accountType;
												$scope.modalInstanceCtrl.ordinaryDepositEraKbn = r.data.resultData.ordinaryDepositEraKbn;
												$scope.modalInstanceCtrl.eraBirthday = r.data.resultData.eraBirthday;
												$scope.modalInstanceCtrl.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl.prefecturesCode = r.data.resultData.prefecturesCode;
												$scope.modalInstanceCtrl.address = r.data.resultData.address;
												// お勤め先（学校名）
												if (r.data.resultData.workName == null
														|| r.data.resultData.workName == "") {
													$scope.modalInstanceCtrl.workName = "-";
												} else {
													$scope.modalInstanceCtrl.workName = r.data.resultData.workName;
												}
												// 自宅電話番号
												if (r.data.resultData.teleNumber == null
														|| r.data.resultData.teleNumber == "") {
													$scope.modalInstanceCtrl.teleNumber = "-";
												} else {
													$scope.modalInstanceCtrl.teleNumber = r.data.resultData.teleNumber;
												}
												// 携帯電話番号
												if (r.data.resultData.phoneNumber == null
														|| r.data.resultData.phoneNumber == "") {
													$scope.modalInstanceCtrl.phoneNumber = "-";
												} else {
													$scope.modalInstanceCtrl.phoneNumber = r.data.resultData.phoneNumber;
												}
												// 勤務先電話番号
												if (r.data.resultData.workTeleNumber == null
														|| r.data.resultData.workTeleNumber == "") {
													$scope.modalInstanceCtrl.workTeleNumber = "-";
												} else {
													$scope.modalInstanceCtrl.workTeleNumber = r.data.resultData.workTeleNumber;
												}
												// 申込情報
												$scope.modalInstanceCtrl.accountAppSeq = r.data.resultData.accountAppSeq;
												$scope.modalInstanceCtrl.applicationDate = r.data.resultData.applicationDate;
												$scope.modalInstanceCtrl.receiptDate = r.data.resultData.receiptDate;
												$scope.modalInstanceCtrl.bankbookDesignKbn = r.data.resultData.bankbookDesignKbn;
												$scope.modalInstanceCtrl.cardDesingKbn = r.data.resultData.cardDesingKbn;
												$scope.modalInstanceCtrl.accountPurpose = r.data.resultData.accountPurpose;
												$scope.modalInstanceCtrl.accountPurposeOther = r.data.resultData.accountPurposeOther;
												$scope.modalInstanceCtrl.jobKbn = r.data.resultData.jobKbn;
												$scope.modalInstanceCtrl.jobKbnOther = r.data.resultData.jobKbnOther;
												$scope.modalInstanceCtrl.securityPassword = r.data.resultData.securityPassword;
												$scope.modalInstanceCtrl.creditlimit = r.data.resultData.creditlimit;
												$scope.modalInstanceCtrl.onlinePassword = r.data.resultData.onlinePassword;
												$scope.modalInstanceCtrl.salesOfficeOption = r.data.resultData.salesOfficeOption;

												$scope.modalInstanceCtrl.identificationType = r.data.resultData.identificationType;
												if (r.data.resultData.identificationType == "A1") {
													$scope.modalInstanceCtrl.imageHide = false;
												}
												$scope.modalInstanceCtrl.livingConditions = r.data.resultData.livingConditions;
												$scope.modalInstanceCtrl.ipAddress = r.data.resultData.ipAddress;
												$scope.modalInstanceCtrl.selectStatus = r.data.resultData.status;
												$scope.modalInstanceCtrl.originalStatus = r.data.resultData.status;
												// ひろぎんネット支店を知った経緯
												$scope.modalInstanceCtrl.knowProcess = r.data.resultData.knowProcess;
												$scope.modalInstanceCtrl.knowProcessOther = r.data.resultData.knowProcessOther;
												$scope.modalInstanceCtrl.pushData = r.data.resultData.yamagataStatusModifyList;
												if ($("#accountAppList").val() == "1") {
													$scope.modalInstanceCtrl.UpdbtnDisabled = true;
												} else {
													$scope.modalInstanceCtrl.UpdbtnDisabled = false;
												}
												imageLoad(r.data.resultData.identificationImage);
												$scope.modalInstanceCtrl.imageLoadHide = false;
											} else if (bank_cd == "0173") {
											    $scope.modalInstanceCtrl.accountAppSeq = r.data.resultData.accountAppSeq;
											    $scope.modalInstanceCtrl.docType = r.data.resultData.docType;
											    $scope.modalInstanceCtrl.userId = r.data.resultData.userId;
											    $scope.modalInstanceCtrl.userType = r.data.resultData.userType;
												$scope.modalInstanceCtrl.deviceTokenId = r.data.resultData.deviceTokenId;
												$scope.modalInstanceCtrl.applicationEndFlg = r.data.resultData.applicationEndFlg;
												$scope.modalInstanceCtrl.applicationFlg = r.data.resultData.applicationFlg;
												$scope.modalInstanceCtrl.agreeTime = r.data.resultData.agreeTime;
												$scope.modalInstanceCtrl.agreeCheck = r.data.resultData.agreeCheck;
//												$scope.modalInstanceCtrl.name = r.data.resultData.name;
												$scope.modalInstanceCtrl.licenseIdR = r.data.resultData.licenseIdR;
												$scope.modalInstanceCtrl.lastNameR = r.data.resultData.lastNameR;
												$scope.modalInstanceCtrl.firstNameR = r.data.resultData.firstNameR;
												$scope.modalInstanceCtrl.birthdayR = r.data.resultData.birthdayR;
												$scope.modalInstanceCtrl.licenseId = r.data.resultData.licenseId;
												$scope.modalInstanceCtrl.lastName = r.data.resultData.lastName;
												$scope.modalInstanceCtrl.firstName = r.data.resultData.firstName;
												$scope.modalInstanceCtrl.kanaLastName = r.data.resultData.kanaLastName;
												$scope.modalInstanceCtrl.kanaFirstName = r.data.resultData.kanaFirstName;
												$scope.modalInstanceCtrl.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl.sexKbn = r.data.resultData.sexKbn;
												$scope.modalInstanceCtrl.postCodeR = r.data.resultData.postCodeR;
												$scope.modalInstanceCtrl.prefecturesCodeR = r.data.resultData.prefecturesCodeR;
												$scope.modalInstanceCtrl.addressR = r.data.resultData.addressR;
												$scope.modalInstanceCtrl.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl.prefecturesCode = r.data.resultData.prefecturesCode;
												$scope.modalInstanceCtrl.address = r.data.resultData.address;
												$scope.modalInstanceCtrl.kanaAddress = r.data.resultData.kanaAddress;
												$scope.modalInstanceCtrl.teleNumber = r.data.resultData.teleNumber;
												$scope.modalInstanceCtrl.phoneNumber = r.data.resultData.phoneNumber;
												$scope.modalInstanceCtrl.jobKbn = r.data.resultData.jobKbn;
												$scope.modalInstanceCtrl.jobKbnOther = r.data.resultData.jobKbnOther;
												$scope.modalInstanceCtrl.workName = r.data.resultData.workName;
												$scope.modalInstanceCtrl.kanaWorkName = r.data.resultData.kanaWorkName;
												$scope.modalInstanceCtrl.workPostCode = r.data.resultData.workPostCode;
												$scope.modalInstanceCtrl.workPrefecturesCode = r.data.resultData.workPrefecturesCode;
												$scope.modalInstanceCtrl.workddress = r.data.resultData.workAddress;
												$scope.modalInstanceCtrl.workTeleNumber = r.data.resultData.workTeleNumber;
												$scope.modalInstanceCtrl.workNumberKbn = r.data.resultData.workNumberKbn;
												$scope.modalInstanceCtrl.accountType = r.data.resultData.accountType;
												$scope.modalInstanceCtrl.bankbookType = r.data.resultData.bankbookType;
												$scope.modalInstanceCtrl.cardType = r.data.resultData.cardType;
												$scope.modalInstanceCtrl.salesOfficeOption = r.data.resultData.salesOfficeOption;
												$scope.modalInstanceCtrl.accountPurpose = r.data.resultData.accountPurpose;
												$scope.modalInstanceCtrl.accountPurposeOther = r.data.resultData.accountPurposeOther;
												$scope.modalInstanceCtrl.securityPassword = r.data.resultData.securityPassword;
												$scope.modalInstanceCtrl.securityPasswordConfirm = r.data.resultData.securityPasswordConfirm;
												$scope.modalInstanceCtrl.creditlimit = r.data.resultData.creditlimit;
												$scope.modalInstanceCtrl.onlinePassword = r.data.resultData.onlinePassword;
												$scope.modalInstanceCtrl.onlinePasswordConfirm = r.data.resultData.onlinePasswordConfirm;
												$scope.modalInstanceCtrl.knowProcess = r.data.resultData.knowProcess;
												$scope.modalInstanceCtrl.applicationReason = r.data.resultData.applicationReason;
												$scope.modalInstanceCtrl.applicationReasonOther = r.data.resultData.applicationReasonOther;
												$scope.modalInstanceCtrl.applicationDate = r.data.resultData.applicationDate;
												$scope.modalInstanceCtrl.receiptDate = r.data.resultData.receiptDate;
												$scope.modalInstanceCtrl.status = r.data.resultData.status;
												$scope.modalInstanceCtrl.selectStatus = r.data.resultData.status;
												$scope.modalInstanceCtrl.identificationType = r.data.resultData.identificationType;
												$scope.modalInstanceCtrl.pushData = r.data.resultData.statusModifyList;
												imageLoad(r.data.resultData.identificationImage);
												$scope.modalInstanceCtrl.imageLoadHide = false;
												$scope.modalInstanceCtrl.livingConditions = r.data.resultData.livingConditions;
												$scope.modalInstanceCtrl.ipAddress = r.data.resultData.ipAddress;
											}
										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalInstanceCtrl.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};

					// ステータス更新ボタンを押す
					$scope.modalInstanceCtrl.statusUpd = function() {
						$scope.modalInstanceCtrl.UpdbtnDisabled = true;
						var url = "./../protected/account/accountAppStatusUpd";
						if ($scope.modalInstanceCtrl.originalStatus != $scope.modalInstanceCtrl.selectStatus) {
							var data = {
								"_id" : key._id,
								"status" : $scope.modalInstanceCtrl.selectStatus
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
													$scope.modalInstanceCtrl.UpdbtnDisabled = false;
													$uibModalInstance.close({
														'load' : true
													});
												} else {
													swal("ステータスを変更しました。", "",
															"success");
													$scope.modalInstanceCtrl.UpdbtnDisabled = false;
													$uibModalInstance
															.close({
																'status' : $scope.modalInstanceCtrl.selectStatus,
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
							$scope.modalInstanceCtrl.UpdbtnDisabled = false;
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
												$scope.modalInstanceCtrl.identificationImage = "";
												$scope.modalInstanceCtrl.identificationImageBack = "";
//												$scope.modalInstanceCtrl.imageLoadHide = true;
												swal(params);

											} else {
												$scope.modalInstanceCtrl.identificationImage = r.data.resultData.identificationImage;
												$scope.modalInstanceCtrl.identificationImageBack = r.data.resultData.identificationImageBack;
//												$scope.modalInstanceCtrl.imageLoadHide = true;
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 暗証番号表示
					$scope.modalInstanceCtrl.showSecPwd = function() {
						$scope.modalInstanceCtrl.passwordHide = true;
					}
					// 暗証番号非表示
					$scope.modalInstanceCtrl.hideSecPwd = function() {
						$scope.modalInstanceCtrl.passwordHide = false;
					}
					// ダイレクトバンキングカード暗証番号表示
					$scope.modalInstanceCtrl.showDirPwd = function() {
						$scope.modalInstanceCtrl.dirPasswordHide = true;
					}
					// ダイレクトバンキングカード暗証番号非表示
					$scope.modalInstanceCtrl.hideDirPwd = function() {
						$scope.modalInstanceCtrl.dirPasswordHide = false;
					}
				})
		// Push通知履歴結果画面表示
		.controller(
				'modalHistoryPushCtrl',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalHistoryPushCtrl = {};
					var bank_cd = $("#accountBankCodeAppList").val();
					$scope.modalHistoryPushCtrl.hideFlg1 = false;
					$scope.modalHistoryPushCtrl.hideFlg2 = false;
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);

					var url = "./../protected/account/accountAppDetail";
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
												$scope.modalHistoryPushCtrl.pushData = r.data.resultData.yamagataStatusModifyList;
											} else if (bank_cd == "0169") {
												$scope.modalHistoryPushCtrl.pushData = r.data.resultData.accountAppDetailPushList;
											} else if (bank_cd = "0173") {
												$scope.modalHistoryPushCtrl.pushData = r.data.resultData.statusModifyList;
											}

										}
									}, function(e) {
										console.log(e);
									});

					$scope.modalHistoryPushCtrl.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalHistoryPushCtrl.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})

		// 電話番号鑑定結果画面表示
		.controller(
				'modalTelAppraisalCtrl',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalTelAppraisalCtrl = {};

					$scope.modalTelAppraisalCtrl.hideFlg1 = false;
					$scope.modalTelAppraisalCtrl.hideFlg2 = false;
					$scope.modalTelAppraisalCtrl.windowHide = true;
					$scope.modalTelAppraisalCtrl.windowHide1 = true;
					$scope.modalTelAppraisalCtrl.TeleNumberDisabled = false;
					$scope.modalTelAppraisalCtrl.PhoneNumDisabled = false;
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);
					loadData();

					// 初期化データを取得
					function loadData() {
						var url = "./../protected/account/accountAppAppraisal";
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
												$scope.modalTelAppraisalCtrl.windowHide = false;
												$scope.modalTelAppraisalCtrl.windowHide1 = false;
												swal(params);
												$uibModalInstance.close({
													'load' : true
												});
											} else {
												// 電話番号鑑定情報
												$scope.modalTelAppraisalCtrl.phoneNumber = r.data.resultData.phoneNumber;
												$scope.modalTelAppraisalCtrl.teleNumber = r.data.resultData.teleNumber;
												$scope.modalTelAppraisalCtrl.workTeleNumber = r.data.resultData.workTeleNumber;
												$scope.modalTelAppraisalCtrl.TC_Access1 = r.data.resultData.tc_Access1;
												$scope.modalTelAppraisalCtrl.TC_Result1 = r.data.resultData.tc_Result1;
												$scope.modalTelAppraisalCtrl.TC_Month1 = r.data.resultData.tc_Month1;
												$scope.modalTelAppraisalCtrl.TC_Movetel1 = r.data.resultData.tc_Movetel1;
												$scope.modalTelAppraisalCtrl.TC_Carrier1 = r.data.resultData.tc_Carrier1;
												$scope.modalTelAppraisalCtrl.TC_Count1 = r.data.resultData.tc_Count1;
												$scope.modalTelAppraisalCtrl.TC_Attention1 = r.data.resultData.tc_Attention1;
												$scope.modalTelAppraisalCtrl.TC_Tacsflag1 = r.data.resultData.tc_Tacsflag1;
												$scope.modalTelAppraisalCtrl.TC_LatestDate1 = r.data.resultData.tc_LatestDate1;
												$scope.modalTelAppraisalCtrl.TC_TacsflagList1 = r.data.resultData.tacsflagList1;
												$scope.modalTelAppraisalCtrl.TC_Access2 = r.data.resultData.tc_Access2;
												$scope.modalTelAppraisalCtrl.TC_Result2 = r.data.resultData.tc_Result2;
												$scope.modalTelAppraisalCtrl.TC_Month2 = r.data.resultData.tc_Month2;
												$scope.modalTelAppraisalCtrl.TC_Movetel2 = r.data.resultData.tc_Movetel2;
												$scope.modalTelAppraisalCtrl.TC_Carrier2 = r.data.resultData.tc_Carrier2;
												$scope.modalTelAppraisalCtrl.TC_Count2 = r.data.resultData.tc_Count2;
												$scope.modalTelAppraisalCtrl.TC_Attention2 = r.data.resultData.tc_Attention2;
												$scope.modalTelAppraisalCtrl.TC_Tacsflag2 = r.data.resultData.tc_Tacsflag2;
												$scope.modalTelAppraisalCtrl.TC_LatestDate2 = r.data.resultData.tc_LatestDate2;
												$scope.modalTelAppraisalCtrl.TC_TacsflagList2 = r.data.resultData.tacsflagList2;
												$scope.modalTelAppraisalCtrl.AppraisalTelResult = r.data.resultData.appraisalTelResult
												if (r.data.resultData.tc_Tacsflag1 != "2") {
													$scope.modalTelAppraisalCtrl.hideFlg1 = true;
												}
												if (r.data.resultData.tc_Tacsflag2 != "2") {
													$scope.modalTelAppraisalCtrl.hideFlg2 = true;
												}
												if (r.data.resultData.phoneNumber != null
														&& r.data.resultData.phoneNumber != "") {
													if (r.data.resultData.tc_Result2 != null
															&& r.data.resultData.tc_Result2 != "") {
														$scope.modalTelAppraisalCtrl.windowHide = true;
													} else {
														$scope.modalTelAppraisalCtrl.windowHide = false;
													}
												} else {
													$scope.modalTelAppraisalCtrl.windowHide = true;
												}
												if (r.data.resultData.teleNumber != null
														&& r.data.resultData.teleNumber != "") {
													if (r.data.resultData.tc_Result1 != null
															&& r.data.resultData.tc_Result1 != "") {

														$scope.modalTelAppraisalCtrl.windowHide1 = true;
													} else {
														$scope.modalTelAppraisalCtrl.windowHide1 = false;
													}
												} else {
													$scope.modalTelAppraisalCtrl.windowHide1 = true;
												}

											}
										}, function(e) {
											console.log(e);
										});
					}

					// 携帯電話番号再取得するボタンを押す
					$scope.modalTelAppraisalCtrl.phoneNumberFind = function() {
						$scope.modalTelAppraisalCtrl.PhoneNumDisabled = true;
						var url = "./../protected/account/phoneNumberFind";
						var data = {
							"_id" : key._id
						};
						var data = accountAppDetailService.statusUpd(url, data);
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
												loadData();
											}
										}, function(e) {
											console.log(e);
										});
					};

					// 自宅電話番号再取得するボタンを押す
					$scope.modalTelAppraisalCtrl.teleNumberFind = function() {
						$scope.modalTelAppraisalCtrl.TeleNumberDisabled = true;
						var url = "./../protected/account/TelNumberFind";
						var data = {
							"_id" : key._id
						};
						var data = accountAppDetailService.statusUpd(url, data);
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
												loadData();
											}
										}, function(e) {
											console.log(e);
										});
					};

					$scope.modalTelAppraisalCtrl.ok = function() {
						if (!$scope.modalTelAppraisalCtrl.windowHide
								|| !$scope.modalTelAppraisalCtrl.windowHide1) {
							$uibModalInstance.close({
								'appraisalTelResult' : "3"
							});
						} else {
							$uibModalInstance
									.close({
										'appraisalTelResult' : $scope.modalTelAppraisalCtrl.AppraisalTelResult
									});
						}

					};

					$scope.modalTelAppraisalCtrl.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})

		// ＩＰアドレス鑑定結果画面表示
		.controller(
				'modalIPAppraisalCtrl',
				function($scope, $uibModalInstance, key,
						accountAppDetailService, insightService,
						strSpliceService) {
					$scope.modalIPAppraisalCtrl = {};
					$scope.modalIPAppraisalCtrl.windowHide2 = true;
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = accountAppDetailService.statusUpd(url, data);

					loadData();
					// 初期化データを取得
					function loadData() {
						var url = "./../protected/account/accountAppAppraisal";
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
												$scope.modalIPAppraisalCtrl.windowHide2 = false;
											} else {
												// ＩＰアドレス鑑定情報
												$scope.modalIPAppraisalCtrl.ipAddress = r.data.resultData.ipAddress;
												$scope.modalIPAppraisalCtrl.IC_CountryCode = r.data.resultData.ic_CountryCode;
												$scope.modalIPAppraisalCtrl.IC_CountryName = r.data.resultData.ic_CountryName;
												$scope.modalIPAppraisalCtrl.IC_CountryThreat = r.data.resultData.ic_CountryThreat;
												$scope.modalIPAppraisalCtrl.IC_PSIP = r.data.resultData.ic_PSIP;
												$scope.modalIPAppraisalCtrl.IC_Proxy = r.data.resultData.ic_Proxy;
												$scope.modalIPAppraisalCtrl.IC_isMobile = r.data.resultData.ic_isMobile;
												$scope.modalIPAppraisalCtrl.IC_CompanyName = r.data.resultData.ic_CompanyName;
												$scope.modalIPAppraisalCtrl.IC_CompanyDomain = r.data.resultData.ic_CompanyDomain;
												$scope.modalIPAppraisalCtrl.IC_CompanyCity = r.data.resultData.ic_CompanyCity;
												$scope.modalIPAppraisalCtrl.IC_Distance = r.data.resultData.ic_Distance;
												$scope.modalIPAppraisalCtrl.IC_IpThreat = r.data.resultData.ic_IpThreat;
												$scope.modalIPAppraisalCtrl.IC_TwoWeeksCount = r.data.resultData.ic_TwoWeeksCount;
												$scope.modalIPAppraisalCtrl.AppraisalIPResult = r.data.resultData.appraisalIPResult;
												if (r.data.resultData.ipAddress != null
														&& r.data.resultData.ipAddress != "") {
													if (r.data.resultData.ic_CountryCode != null
															&& r.data.resultData.ic_CountryCode != "") {
														$scope.modalIPAppraisalCtrl.windowHide2 = true;
													} else {
														$scope.modalIPAppraisalCtrl.windowHide2 = false;
													}
												} else {
													$scope.modalIPAppraisalCtrl.windowHide2 = true;
												}

											}
										}, function(e) {
											console.log(e);
										});
					}

					// ＩＰアドレス鑑定結果再取得するボタンを押す
					$scope.modalIPAppraisalCtrl.IpAddressFind = function() {
						$scope.modalIPAppraisalCtrl.IpAddressDisabled = true;
						var url = "./../protected/account/IpAddressFind";
						var data = {
							"_id" : key._id
						};
						var data = accountAppDetailService.statusUpd(url, data);
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
												loadData()
											}
										}, function(e) {
											console.log(e);
										});
					};
					$scope.modalIPAppraisalCtrl.ok = function() {
						if (!$scope.modalIPAppraisalCtrl.windowHide2) {
							$uibModalInstance.close({
								'appraisalIPResult' : "3"
							});
						} else {
							$uibModalInstance
									.close({
										'appraisalIPResult' : $scope.modalIPAppraisalCtrl.AppraisalIPResult
									});
						}
					};

					$scope.modalIPAppraisalCtrl.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})