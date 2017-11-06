insightApp
		.controller(
				'userListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, userListService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.userListCtrl = {};

					// 一覧モジュール変数
					$scope.userListCtrl.table = {};
					$scope.userListCtrl.datepicker = {};
					$scope.userListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					$scope.userListCtrl.datepicker.formats = [ 'dd-MMMM-yyyy',
							'yyyy/MM/dd', 'dd.MM.yyyy', 'yyyy/MM/dd hh:mm',
							'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.userListCtrl.datepicker.formats[1])),
														maxDate: new Date(),
										useCurrent : false
									});
					$('#dtPopup1')
							.datetimepicker(
									{
										format : "YYYY/MM/DD",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.userListCtrl.datepicker.formats[1])),
														maxDate: new Date(),
										useCurrent : false
									});
					setTimeout(function() {
						$('#endLoginDateTime').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.userListCtrl.datepicker.format = $scope.userListCtrl.datepicker.formats[1];
					$scope.userListCtrl.datepicker.open = function($event,
							opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.userListCtrl.datepicker[opened] = true;
					};

					$scope.userListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					// ログイン状態を取得
					$scope.userListCtrl.table.filterList2 = insightService
							.getInsightData("loginStatusData");
					$scope.userListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.userListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
												item.select = $scope.userListCtrl.table.checkboxes.checked;
										});
						console.log($scope.userListCtrl.table.checkboxes);
					}
					if ($("#userList").val() == "1") {
						$scope.userListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										userName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "userListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.userListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										userName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "userListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					$scope.userListCtrl.user = $("#userList").val();
					if ($scope.userListCtrl.user == "1") {
						$scope.userListCtrl.addBtnDisabled = true;
						$scope.userListCtrl.csvLogOutPutBtn=true;
						// $scope.userListCtrl.table.deleteBtn = function() {}
					}

					// ユーザ一覧CSV出力ボタンを押下
					$scope.userListCtrl.table.csvOutPutBtn = function() {
						var deferred = $q.defer();
						var flg = "0";
						angular.forEach($scope.userListCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// CSV出力ボタンの制御解除する
						if (flg == "0") {
							swal("ユーザを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							// CSV出力確認メッセージを呼出
							var params4 = {
								title : "ユーザ一覧CSVファイルをダウンロードしますか？",
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
											var url = "./../protected/user/outPut";
											var data = $scope.userListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = userListService
													.setListstatusUpd(url, {
														'logOutputList' : data
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
																	var url = "./../protected/user/csvButtonDownLoad";
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

					// 操作/行動ログCSV出力ボタンを押下
					$scope.userListCtrl.table.csvLogOutPutBtn = function() {
						var applyYear = $("#dtPopup2").val().split("/")[0];
						var applyMonth = $("#dtPopup2").val().split("/")[1] - 1;
						var applyDay = $("#dtPopup2").val().split("/")[2];
						var applyDate = new Date(applyYear, applyMonth,
								applyDay);
						var applyYear1 = $("#dtPopup1").val().split("/")[0];
						var applyMonth1 = $("#dtPopup1").val().split("/")[1] - 1;
						var applyDay1 = $("#dtPopup1").val().split("/")[2];
						var applyDate1 = new Date(applyYear1, applyMonth1,
								applyDay1);
						
						var deferred = $q.defer();
						var flg = "0";
						angular.forEach($scope.userListCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});
						// 一覧で、一件データを選択しない場合、エラーメッセージを表示
						// CSV出力ボタンの制御解除する
						if (flg == "0") {
							swal("ユーザを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							// CSV出力確認メッセージを呼出
							var params4 = {
								title : "選択したユーザの操作/行動ログを出力しますか？",
								text : "",
								type : "warning",
								showCancelButton : true,
								confirmButtonColor : "#DD6B55",
								confirmButtonText : "確認",
								cancelButtonText : "キャンセル",
								closeOnConfirm : true,
								closeOnCancel : true
							};
							if (applyDate1.valueOf() <= applyDate.valueOf()) {
								swal(
										params4,
										function(isConfirm) {
											// 確認ボタンを押下場合
											if (isConfirm) {
												// CSVデータ出力データ取得
												var url = "./../protected/user/logOutPut";
												var data = $scope.userListCtrl.table.sortAndFilter
														.settings().dataset;
												var data = userListService
														.setUserListstatusUpd(
																url,
																{
																	'logOutputList' : data,
																	'startTime' : $('#dtPopup1').val(),
																	'endTime' : $('#dtPopup2').val()
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
																				"warning");
																	} else {
																		// CSV出力データ取得途中、エラーなし場合、CSVをダウン‐ロードする
																		var url = "./../protected/user/logCsvButtonDownLoad";
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

																		$(
																				'body')
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
																},
																function(e) {
																	console
																			.log(e);
																});
											} else {
												// キャンセルボタンを押下場合、CSV出力ボタンの制御解除する
												deferred.resolve("resolve");
											}
										});
							}else{
								swal("終了日は開始日より過去日付を選択できません。", "", "warning");
								deferred.resolve("resolve");
							}
						}
						return deferred.promise;

					}

					// 一括削除ボタンを押下時、

					$scope.userListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";
						var flg1 = "1";
						angular
								.forEach(
										$scope.userListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if (item.select) {
												flg = "1";
												if ($scope.userListCtrl.seesionUserID == item.userID) {
													flg1 = "2";
												}
											}
										});
					
						if (flg == "0") {
							swal("ユーザを選択してください。", "", "warning");
							deferred.resolve("resolve");
						}else if (flg1 == "2") {
							swal("ログイン中のユーザは削除できません。", "", "warning");
							deferred.resolve("resolve");
						}else {
							var params2 = {
								title : "選択したユーザを削除しますか？",
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
											var url = "./../protected/user/deleteButton";
											var data = $scope.userListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = userListService
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

					// ログ出力ボタンを押下時、
					$scope.userListCtrl.table.logOutput = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";

						angular.forEach($scope.userListCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});
						if (flg == "0") {
							swal("ユーザを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択したユーザの行動ログを出力しますか？",
								text : "",
								type : "info",
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
											var url = "./../protected/user/logOutputButton";
											var data = $scope.userListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = userListService
													.setListDelete(url, {
														'logOutputList' : data
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
					$scope.userListCtrl.addBtn = function(size) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									templateUrl : 'userDetailPopup.html',
									controller : 'modalInstanceCtrl3',
									backdrop : 'static',
									size : size,
									resolve : {
										key : function() {
											return {
												'modeType' : "1", // 新規登録場合
												'authorityList' : $scope.userListCtrl.authorityList,
												'_id' : ""

											};
										}
									}
								});

						modalInstance.result.then(function(result) {
							if (result.userName != null) {
								loadData();
							}

						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}

					// ユーザ詳細画面を呼び出す
					$scope.userListCtrl.table.openNa = function(size, item) {

						var modalInstance2 = $uibModal
								.open({
									animation : true,
									templateUrl : 'userDetailPopup.html',
									controller : 'modalInstanceCtrl3',
									backdrop : 'static',
									size : size,
									resolve : {
										key : function() {
											return {
												'modeType' : "2", // 編集場合
												'authorityList' : $scope.userListCtrl.authorityList,
												'_id' : item._id
											};
										}
									}
								});

						modalInstance2.result.then(function(result) {
							if (result.userName != null) {
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

					function userCheck(item) {
						angular
								.forEach(
										$scope.userListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											// $scope.userListCtrl.checkDisable=false;
											if ($scope.userListCtrl.seesionUserID == item.userID) {
												item.checkDisable = true;
											}

										});
					}

					// 初期化データを取得
					function loadData(deferred) {
						// 一覧初期データ取得
						var data = "";
						var url = "./../protected/user/sessionTimeOut";
						var data = userListService.setListDelete(url, data);
						
						var url = "./../protected/user/userList";
						$scope.userListCtrl.checkDisable = false;
						$scope.userListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = userListService.getListData(url, params);
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
													if (codeList[0] == "e.user001.1001") {
														$scope.userListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.userList;
														$scope.userListCtrl.table.sortAndFilter
																.reload();
													}
												}
												$scope.userListCtrl.authorityList = r.data.resultData.authorityList;
											} else {
												$scope.userListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.userList;
												$scope.userListCtrl.authorityList = r.data.resultData.authorityList;
												$scope.userListCtrl.table.sortAndFilter
														.reload();
												$scope.userListCtrl.seesionUserID = r.data.resultData.sessionUserID;
//												userCheck(r.data.resultData);
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
				'modalInstanceCtrl3',
				function($scope, $uibModalInstance, key, userListService,
						insightService, strSpliceService) {
					$scope.modalInstanceCtrl3 = {};
					var url = "./../protected/user/sessionTimeOut";
					var data = "";
					userListService.setListDelete(url, data);
					$scope.modalInstanceCtrl3.user = $("#userList").val();
					if ($scope.modalInstanceCtrl3.user == "1") {
						$scope.modalInstanceCtrl3.UpdbtnDisabled = true;
						$scope.modalInstanceCtrl3.resetBtnDisabled = true;
						$scope.modalInstanceCtrl3.userIDDisabled = true;
					} else {
						$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						$scope.modalInstanceCtrl3.userIDDisabled = false;
						$scope.modalInstanceCtrl3.resetBtnDisabled = false;
					}

					if (key.modeType == "1") {
						$scope.modalInstanceCtrl3.title = "ユーザ新規画面";
						$scope.modalInstanceCtrl3.button = "登録";
						// ユーザ名
						$scope.modalInstanceCtrl3.userName = "";
						// ユーザID
						$scope.modalInstanceCtrl3.userID = "";
						// ログインパスワード
						$scope.modalInstanceCtrl3.password = "";
						// メールアドレス
						$scope.modalInstanceCtrl3.email = "";
						// 権限
						$scope.modalInstanceCtrl3.authority = "";
						// パスワード初期化フラグ
						$scope.modalInstanceCtrl3.passwordType = "0";
						// アカウントロックフラグ
						$scope.modalInstanceCtrl3.lockStatus = false;
						$scope.modalInstanceCtrl3.authorityList = key.authorityList;
						$scope.modalInstanceCtrl3.resetHide = true;

					} else {
						$scope.modalInstanceCtrl3.title = "ユーザ編集画面";
						$scope.modalInstanceCtrl3.button = "更新";
						$scope.modalInstanceCtrl3.resetHide = false;
						$scope.modalInstanceCtrl3.userIDDisabled = true;

						var url = "./../protected/user/userDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = userListService.getDetailData(url, params);
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
												// ユーザID
												$scope.modalInstanceCtrl3.userID = r.data.resultData.userID;
												// ユーザ名
												$scope.modalInstanceCtrl3.userName = r.data.resultData.userName;
												// ログインパスワード
												$scope.modalInstanceCtrl3.password = "";
												// メールアドレス
												$scope.modalInstanceCtrl3.email = r.data.resultData.email;
												// 権限
												$scope.modalInstanceCtrl3.authority = r.data.resultData.authority;
												$scope.modalInstanceCtrl3.authorityList = key.authorityList;
												// パスワード初期化フラグ
												// $scope.modalInstanceCtrl3.passwordType
												// =
												// r.data.resultData.passwordType;
												// アカウントロックフラグ
												$scope.modalInstanceCtrl3.lockStatus = r.data.resultData.lockStatus;
												// $scope.modalInstanceCtrl3.userNameDisabled
												// = true;
												// $scope.modalInstanceCtrl3.UpdbtnDisabled
												// = false;
											}
										}, function(e) {
											console.log(e);
										});
					}

					$scope.modalInstanceCtrl3.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl3.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					// 半角英数
					function isAlphabetNumeric(argValue) {
						if (argValue.match(/[^A-Z|^a-z|^0-9]/g)) {
							return false;
						} else {
							return true;
						}
					}
					;
					// 英数混在
					function checkPass(pass) {
						var ls = 0;
						if (pass.match(/([a-z])+/)) {
							ls++;
						}
						if (pass.match(/([0-9])+/)) {
							ls++;
						}
						if (pass.match(/([A-Z])+/)) {
							ls++;
						}

						if (ls < 2) {
							return false;
						} else {
							return true;
						}
					}
					;
					$scope.modalInstanceCtrl3.passwordReset = function() {

						var url = "./../protected/user/userPasswordReset";
						var data = {
							"_id" : key._id,
							"modeType" : key.modeType,
							"userID" : $scope.modalInstanceCtrl3.userID,
							"userName" : $scope.modalInstanceCtrl3.userName,
							"password" : $scope.modalInstanceCtrl3.password,
							"email" : $scope.modalInstanceCtrl3.email,
							"authority" : $scope.modalInstanceCtrl3.authority,
							"lockStatus" : $scope.modalInstanceCtrl3.lockStatus

						};
						var data = userListService.dataUpd(url, data);
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
												swal(
														params,
														function() {
															if (codeList2[0] == "e.userDetail.1001") {
																$uibModalInstance
																		.close({
																			'userName' : $scope.modalInstanceCtrl3.userName
																		});
															}
														});
												$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
											} else {
												swal("パスワードをリセットしました。", "",
														"success");
												$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
												$uibModalInstance
														.close({
															'userName' : $scope.modalInstanceCtrl3.userName
														});
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 更新ボタンを押す
					$scope.modalInstanceCtrl3.dataUpd = function() {
						$scope.modalInstanceCtrl3.UpdbtnDisabled = true;
						if ($scope.modalInstanceCtrl3.userID == null
								|| $scope.modalInstanceCtrl3.userID == "") {
							swal("ユーザIDを入力してください。", "", "warning");
							$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
							return;
						}
//						 if ($scope.modalInstanceCtrl3.userID.toLowerCase() == "admin".toLowerCase()) {
//							swal("このユーザIDは利用できません。別のユーザIDを入力してください。", "", "warning");
//							$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
//							return;
//						}
						if ($scope.modalInstanceCtrl3.userName == null
								|| $scope.modalInstanceCtrl3.userName == "") {
							swal("ユーザ名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
							return;
						}
						// if ($scope.modalInstanceCtrl3.password == null
						// || $scope.modalInstanceCtrl3.password == "") {
						// swal("ログインパスワードを入力してください。", "", "warning");
						// $scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						// return;
						// }
						// if
						// (isAlphabetNumeric($scope.modalInstanceCtrl3.password)
						// == false) {
						// swal("ログインパスワードは半角英数を入力してください。", "", "warning");
						// $scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						// return;
						// }
						//						    
						// if (checkPass($scope.modalInstanceCtrl3.password)==
						// false){
						// swal("ログインパスワードで使用する文字は英数混在とし、数字のみ、英字のみは認めないこと。", "",
						// "warning");
						// $scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						// return;
						// }
						//						
						// if ($scope.modalInstanceCtrl3.password.length < 8) {
						// swal("ログインパスワードは８桁以上を入力してください。", "", "warning");
						// $scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						// return;
						// }
						// if ($scope.modalInstanceCtrl3.passwordConfirm == null
						// || $scope.modalInstanceCtrl3.passwordConfirm == "") {
						// swal("ログインパスワードの確認を入力してください。", "", "warning");
						// $scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						// return;
						// }
						// if ($scope.modalInstanceCtrl3.password
						// !=$scope.modalInstanceCtrl3.passwordConfirm ) {
						// swal("ログインパスワードとログインパスワードの確認が不一致。", "", "warning");
						// $scope.modalInstanceCtrl3.UpdbtnDisabled = false;
						// return;
						// }
						if ($scope.modalInstanceCtrl3.email == null
								|| $scope.modalInstanceCtrl3.email == "") {
							swal("メールアドレスを入力してください。", "", "warning");
							$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl3.authority == null
								|| $scope.modalInstanceCtrl3.authority == "") {
							swal("権限を選択してください。", "", "warning");
							$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
							return;
						}

						var url = "./../protected/user/userUpdate";
						var data = {
							"_id" : key._id,
							"modeType" : key.modeType,
							"userID" : $scope.modalInstanceCtrl3.userID,
							"userName" : $scope.modalInstanceCtrl3.userName,
							"email" : $scope.modalInstanceCtrl3.email,
							"authority" : $scope.modalInstanceCtrl3.authority,
							"lockStatus" : $scope.modalInstanceCtrl3.lockStatus

						};
						var data = userListService.dataUpd(url, data);
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
															if (codeList4[0] == "e.userDetail.1001") {
																$uibModalInstance
																		.close({
																			'userName' : $scope.modalInstanceCtrl3.userName
																		});
															}
														});
												// $uibModalInstance
												// .close({
												// 'userName' :
												// $scope.modalInstanceCtrl3.userName
												// });
												$scope.modalInstanceCtrl3.UpdbtnDisabled = false;

											} else {
												if (key.modeType == "1") {
													swal("登録しました。", "",
															"success");
												} else {
													swal("更新しました。", "",
															"success");
												}
												$scope.modalInstanceCtrl3.UpdbtnDisabled = false;
												$uibModalInstance
														.close({
															'userName' : $scope.modalInstanceCtrl3.userName
														});
											}
										}, function(e) {
											console.log(e);
										});
					};

				})
/*
 * .controller( 'userCtrl', function($scope, insightService) {
 * $scope.userCtrl={}; if($("#userList").val()=="1"){
 * $scope.userCtrl.delBtnDisabled=true;
 *  }
 *  })
 */
