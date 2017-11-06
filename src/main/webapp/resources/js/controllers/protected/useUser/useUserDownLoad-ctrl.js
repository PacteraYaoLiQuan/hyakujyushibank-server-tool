insightApp
		.controller(
				'useUserDownLoadCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, useUserDownLoadService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.useUserDownLoadCtrl = {};

					// 一覧モジュール変数
					$scope.useUserDownLoadCtrl.table = {};
					$scope.useUserDownLoadCtrl.datepicker = {};
					$scope.useUserDownLoadCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					// データ存在状態を取得
//					$scope.useUserDownLoadCtrl.table.filterList2 = insightService
//							.getInsightData("pushTitle");
					//　ユーザータイプ取得
					$scope.useUserDownLoadCtrl.table.filterList2 = insightService
					.getInsightData("userKeyData");
					$scope.useUserDownLoadCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.useUserDownLoadCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.useUserDownLoadCtrl.table.checkboxes.checked;
										});
						console.log($scope.useUserDownLoadCtrl.table.checkboxes);
					}

					$scope.useUserDownLoadCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					setTimeout(function() {
						$('#lastReqTime').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.useUserDownLoadCtrl.datepicker.format = $scope.useUserDownLoadCtrl.datepicker.formats[1];
					$scope.useUserDownLoadCtrl.datepicker.open = function($event,
							opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.useUserDownLoadCtrl.datepicker[opened] = true;
					};

					$scope.useUserDownLoadCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					if ($("#useUserDownLoad").val() == "1") {
						$scope.useUserDownLoadCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										lastReqTime : "desc"
									}
								},
								{
									paginationCustomizeButtons : "useUserDownLoadCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.useUserDownLoadCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										lastReqTime : "desc"
									}
								},
								{
									paginationCustomizeButtons : "useUserDownLoadCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}

					$scope.useUserDownLoadCtrl.useUserDownLoad = $("#useUserDownLoad")
							.val();
					if ($scope.useUserDownLoadCtrl.useUserDownLoad == "1") {
						$scope.useUserDownLoadCtrl.addBtnDisabled = true;
					}

					// テーマダウンロードCSV出力ボタンを押下時、
					$scope.useUserDownLoadCtrl.table.timeOutput = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach(
								$scope.useUserDownLoadCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// CSV出力ボタンの制御解除する
						if (flg == "0") {
							swal("利用ユーザーを選択してください。", "", "error");
							deferred.resolve("resolve");
						} else {
							// CSV出力確認メッセージを呼出
							var params4 = {
								title : "テーマCSVファイルをダウンロードしますか？",
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
											var url = "./../protected/useUser/timeCsvButton";
											var data = $scope.useUserDownLoadCtrl.table.sortAndFilter
													.settings().dataset;
											var data = useUserDownLoadService
													.setListstatusUpd(url, {
														'csvList' : data
													});
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
																	var url = "./../protected/useUser/timeCsvButtonDownLoad";
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
					
					// クリップダウンロードCSV出力ボタンを押下時、
					$scope.useUserDownLoadCtrl.table.clipOutput = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach(
								$scope.useUserDownLoadCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// CSV出力ボタンの制御解除する
						if (flg == "0") {
							swal("利用ユーザーを選択してください。", "", "error");
							deferred.resolve("resolve");
						} else {
							// CSV出力確認メッセージを呼出
							var params4 = {
								title : "クリップCSVファイルをダウンロードしますか？",
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
											var url = "./../protected/useUser/clipCsvButton";
											var data = $scope.useUserDownLoadCtrl.table.sortAndFilter
													.settings().dataset;
											var data = useUserDownLoadService
													.setListstatusUpd(url, {
														'csvList' : data
													});
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
																	var url = "./../protected/useUser/clipCsvButtonDownLoad";
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
					
					// メッセージ詳細画面を呼び出す
					$scope.useUserDownLoadCtrl.table.openNa = function(size, item) {

						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'useUSerPopup.html',
							controller : 'modalInstanceCtrl10000',
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
						var url = "./../protected/useUser/useUserDownloadList";
						$scope.useUserDownLoadCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = useUserDownLoadService.getListData(url, params);
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
												$scope.useUserDownLoadCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.useUserList;
												$scope.useUserDownLoadCtrl.table.sortAndFilter
														.reload();
											} else {
												$scope.useUserDownLoadCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.useUserList;
												$scope.useUserDownLoadCtrl.table.sortAndFilter
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
				'modalInstanceCtrl10000',
				function($scope, $uibModal, $uibModalInstance, key,
						useUserDownLoadService, insightService, strSpliceService) {
					$scope.modalInstanceCtrl10000 = {};
					if ($scope.modalInstanceCtrl10000.pushMessage == "1") {
						$scope.modalInstanceCtrl10000.UpdbtnDisabled = true;
						$scope.modalInstanceCtrl10000.pushTitleDisabled = true;
					} else {
						$scope.modalInstanceCtrl10000.UpdbtnDisabled = false;
						$scope.modalInstanceCtrl10000.pushTitleDisabled = false;
					}

					if (key.modeType == "2") {
						$scope.modalInstanceCtrl10000.visible = false;
						$scope.modalInstanceCtrl10000.title = "利用ユーザー詳細画面";
						$scope.modalInstanceCtrl10000.button = "更新";
						$scope.modalInstanceCtrl10000.pushTitleDisabled = true;
						$scope.modalInstanceCtrl10000.pushMessageDisabled = true;
						var url = "./../protected/useUser/useUserDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = useUserDownLoadService
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
												$uibModalInstance.close({
													'load' : true
												});
											} else {
												// お客様情報
												$scope.modalInstanceCtrl10000.userId = r.data.resultData.userId;
												$scope.modalInstanceCtrl10000.userType = r.data.resultData.userType;
												$scope.modalInstanceCtrl10000.name = r.data.resultData.name;
												$scope.modalInstanceCtrl10000.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl10000.age = r.data.resultData.age;
												$scope.modalInstanceCtrl10000.sex = r.data.resultData.sex;
												$scope.modalInstanceCtrl10000.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl10000.occupation = r.data.resultData.occupation;
												$scope.modalInstanceCtrl10000.otherOccupations = r.data.resultData.otherOccupations;
												$scope.modalInstanceCtrl10000.cardNoList = r.data.resultData.occupation;
												// 携帯電話番号
												if (r.data.resultData.phoneNumber == null
														|| r.data.resultData.phoneNumber == "") {
													$scope.modalInstanceCtrl10000.phoneNumber = "-";
												} else {
													$scope.modalInstanceCtrl10000.phoneNumber = r.data.resultData.phoneNumber;
												}
												// 自宅電話番号
												if (r.data.resultData.teleNumber == null
														|| r.data.resultData.teleNumber == "") {
													$scope.modalInstanceCtrl10000.teleNumber = "-";
												} else {
													$scope.modalInstanceCtrl10000.teleNumber = r.data.resultData.teleNumber;
												}
												// 勤務先名
												if (r.data.resultData.workName == null
														|| r.data.resultData.workName == "") {
													$scope.modalInstanceCtrl10000.workName = "-";
												} else {
													$scope.modalInstanceCtrl10000.workName = r.data.resultData.workName;
												}
												// 勤務先電話番号
												if (r.data.resultData.workTeleNumber == null
														|| r.data.resultData.workTeleNumber == "") {
													$scope.modalInstanceCtrl10000.workTeleNumber = "-";
												} else {
													$scope.modalInstanceCtrl10000.workTeleNumber = r.data.resultData.workTeleNumber;
												}

												$scope.modalInstanceCtrl10000.email = r.data.resultData.email;
												$scope.modalInstanceCtrl10000.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl10000.address1 = r.data.resultData.address1;
												$scope.modalInstanceCtrl10000.address2 = r.data.resultData.address2;
												$scope.modalInstanceCtrl10000.kanaAddress = r.data.resultData.kanaAddress;
												$scope.modalInstanceCtrl10000.accountName = r.data.resultData.accountName;
												$scope.modalInstanceCtrl10000.storeName = r.data.resultData.storeName;
												$scope.modalInstanceCtrl10000.kamokuName = r.data.resultData.kamokuName;
												$scope.modalInstanceCtrl10000.accountNumber = r.data.resultData.accountNumber;
												$scope.modalInstanceCtrl10000.agreeDate = r.data.resultData.agreeDate;
												$scope.modalInstanceCtrl10000.lastReqTime = r.data.resultData.lastReqTime;
											}
										}, function(e) {
											console.log(e);
										});
					}
					$scope.modalInstanceCtrl10000.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl10000.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})

