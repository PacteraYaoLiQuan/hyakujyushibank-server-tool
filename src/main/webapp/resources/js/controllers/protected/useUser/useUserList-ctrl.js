insightApp
		.controller(
				'useUserListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, useUserListService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.useUserListCtrl = {};

					// 一覧モジュール変数
					$scope.useUserListCtrl.table = {};
					$scope.useUserListCtrl.datepicker = {};
					$scope.useUserListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					// データ存在状態を取得
					// $scope.useUserListCtrl.table.filterList2 = insightService
					// .getInsightData("pushTitle");
					// ユーザータイプ取得
					$scope.useUserListCtrl.table.filterList2 = insightService
							.getInsightData("userKeyData");
					$scope.useUserListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.useUserListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.useUserListCtrl.table.checkboxes.checked;
										});
						console.log($scope.useUserListCtrl.table.checkboxes);
					}

					$scope.useUserListCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					setTimeout(function() {
						$('#lastReqTime').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.useUserListCtrl.datepicker.format = $scope.useUserListCtrl.datepicker.formats[1];
					$scope.useUserListCtrl.datepicker.open = function($event,
							opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.useUserListCtrl.datepicker[opened] = true;
					};

					$scope.useUserListCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					if ($("#useUserList").val() == "1") {
						$scope.useUserListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										lastReqTime : "desc"
									}
								},
								{
									paginationCustomizeButtons : "useUserListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.useUserListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										lastReqTime : "desc"
									}
								},
								{
									paginationCustomizeButtons : "useUserListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}

					$scope.useUserListCtrl.useUserList = $("#useUserList")
							.val();
					if ($scope.useUserListCtrl.useUserList == "1") {
						$scope.useUserListCtrl.addBtnDisabled = true;
					}

					// CSVデータ出力ボタンを押下時、
					$scope.useUserListCtrl.table.excelOutput = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach(
								$scope.useUserListCtrl.table.sortAndFilter
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
								title : "利用ユーザー情報ファイルをダウンロードしますか？",
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
											var url = "./../protected/useUser/csvButton";
											var data = $scope.useUserListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = useUserListService
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
																	var url = "./../protected/useUser/csvButtonDownLoad";
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

					// マイテーマ情報出力ボタンを押下時、
					$scope.useUserListCtrl.table.timeOutput = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach(
								$scope.useUserListCtrl.table.sortAndFilter
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
								title : "マイテーマ情報ファイルをダウンロードしますか？",
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
											var data = $scope.useUserListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = useUserListService
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

					// アップロードボタンを押下時、
					$scope.useUserListCtrl.table.uploadBtn = function(size) {
						var deferred = $q.defer();

						var flg = "0";
						var flg1 = "1";
						angular.forEach(
								$scope.useUserListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});

						var modalInstance = $uibModal
								.open({
									animation : true,
									templateUrl : 'upPopup.html',
									controller : 'modalInstanceCtrl11',
									backdrop : 'static',
									size : size,
									resolve : {
										key : function() {
											return {
												'modeType' : "3", // 新規登録場合
												'_id' : "",
												'list' : $scope.useUserListCtrl.table.sortAndFilter
														.settings().dataset
											};
										}
									}
								});

						modalInstance.result
								.then(
										function(result) {
											if (result.pushTitle != null) {
												$scope.useUserListCtrl.arrNoList = result.pushTitle;
											}

										}, function(reason) {
											console.log('Modal dismissed at: '
													+ new Date() + "  ==>  "
													+ reason);
										});
					}

					// 選択配信ボタンを押下時、
					$scope.useUserListCtrl.table.sendBtn = function(size) {
						var deferred = $q.defer();

						var flg = "0";
						var flg1 = "1";
						angular.forEach(
								$scope.useUserListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});

						if (flg == "0") {
							swal("利用ユーザーを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var modalInstance = $uibModal
									.open({
										animation : true,
										templateUrl : 'pushMessagePopup.html',
										controller : 'modalInstanceCtrl11',
										backdrop : 'static',
										size : size,
										resolve : {
											key : function() {
												return {
													'modeType' : "1", // 新規登録場合
													'_id' : "",
													'list' : $scope.useUserListCtrl.table.sortAndFilter
															.settings().dataset,
													'arrayObj' : $scope.useUserListCtrl.arrNoList
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
					}

					// 全体配信ボタンを押下時、
					$scope.useUserListCtrl.table.sendAllBtn = function(size) {

						var modalInstance = $uibModal
								.open({
									animation : true,
									templateUrl : 'pushMessagePopup.html',
									controller : 'modalInstanceCtrl11',
									backdrop : 'static',
									size : size,
									resolve : {
										key : function() {
											return {
												'modeType' : "4", // 新規登録場合
												'_id' : "",
												'list' : $scope.useUserListCtrl.table.sortAndFilter
														.settings().dataset
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

					// メッセージ詳細画面を呼び出す
					$scope.useUserListCtrl.table.openNa = function(size, item) {

						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'useUSerPopup.html',
							controller : 'modalInstanceCtrl14',
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
						$scope.useUserListCtrl.arrNoList = null;
						var url = "./../protected/useUser/useUserList";
						$scope.useUserListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = useUserListService.getListData(url, params);
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
												$scope.useUserListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.useUserList;
												$scope.useUserListCtrl.table.sortAndFilter
														.reload();
											} else {
												$scope.useUserListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.useUserList;
												$scope.useUserListCtrl.table.sortAndFilter
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
				'modalInstanceCtrl14',
				function($scope, $uibModal, $uibModalInstance, key,
						useUserListService, insightService, strSpliceService) {
					$scope.modalInstanceCtrl14 = {};
					if ($scope.modalInstanceCtrl14.pushMessage == "1") {
						$scope.modalInstanceCtrl14.UpdbtnDisabled = true;
						$scope.modalInstanceCtrl14.pushTitleDisabled = true;
					} else {
						$scope.modalInstanceCtrl14.UpdbtnDisabled = false;
						$scope.modalInstanceCtrl14.pushTitleDisabled = false;
					}

					if (key.modeType == "2") {
						$scope.modalInstanceCtrl14.visible = false;
						$scope.modalInstanceCtrl14.title = "利用ユーザー詳細画面";
						$scope.modalInstanceCtrl14.button = "更新";
						$scope.modalInstanceCtrl14.pushTitleDisabled = true;
						$scope.modalInstanceCtrl14.pushMessageDisabled = true;
						var url = "./../protected/useUser/useUserDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = useUserListService
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
												$scope.modalInstanceCtrl14.userId = r.data.resultData.userId;
												$scope.modalInstanceCtrl14.userType = r.data.resultData.userType;
												$scope.modalInstanceCtrl14.name = r.data.resultData.name;
												$scope.modalInstanceCtrl14.kanaName = r.data.resultData.kanaName;
												$scope.modalInstanceCtrl14.age = r.data.resultData.age;
												$scope.modalInstanceCtrl14.sex = r.data.resultData.sex;
												$scope.modalInstanceCtrl14.birthday = r.data.resultData.birthday;
												$scope.modalInstanceCtrl14.occupation = r.data.resultData.occupation;
												$scope.modalInstanceCtrl14.otherOccupations = r.data.resultData.otherOccupations;
												$scope.modalInstanceCtrl14.cardNoList = r.data.resultData.occupation;
												// 携帯電話番号
												if (r.data.resultData.phoneNumber == null
														|| r.data.resultData.phoneNumber == "") {
													$scope.modalInstanceCtrl14.phoneNumber = "-";
												} else {
													$scope.modalInstanceCtrl14.phoneNumber = r.data.resultData.phoneNumber;
												}
												// 自宅電話番号
												if (r.data.resultData.teleNumber == null
														|| r.data.resultData.teleNumber == "") {
													$scope.modalInstanceCtrl14.teleNumber = "-";
												} else {
													$scope.modalInstanceCtrl14.teleNumber = r.data.resultData.teleNumber;
												}
												// 勤務先名
												if (r.data.resultData.workName == null
														|| r.data.resultData.workName == "") {
													$scope.modalInstanceCtrl14.workName = "-";
												} else {
													$scope.modalInstanceCtrl14.workName = r.data.resultData.workName;
												}
												// 勤務先電話番号
												if (r.data.resultData.workTeleNumber == null
														|| r.data.resultData.workTeleNumber == "") {
													$scope.modalInstanceCtrl14.workTeleNumber = "-";
												} else {
													$scope.modalInstanceCtrl14.workTeleNumber = r.data.resultData.workTeleNumber;
												}
												$scope.modalInstanceCtrl14.noticeFlag = r.data.resultData.noticeFlag;
												$scope.modalInstanceCtrl14.deviceOSVer = r.data.resultData.deviceOSVer;
												$scope.modalInstanceCtrl14.deviceModelName = r.data.resultData.deviceModelName;
												$scope.modalInstanceCtrl14.email = r.data.resultData.email;
												$scope.modalInstanceCtrl14.postCode = r.data.resultData.postCode;
												$scope.modalInstanceCtrl14.address1 = r.data.resultData.address1;
												$scope.modalInstanceCtrl14.address2 = r.data.resultData.address2;
												$scope.modalInstanceCtrl14.kanaAddress = r.data.resultData.kanaAddress;
												$scope.modalInstanceCtrl14.accountName = r.data.resultData.accountName;
												$scope.modalInstanceCtrl14.storeName = r.data.resultData.storeName;
												$scope.modalInstanceCtrl14.kamokuName = r.data.resultData.kamokuName;
												$scope.modalInstanceCtrl14.accountNumber = r.data.resultData.accountNumber;
												$scope.modalInstanceCtrl14.agreeDate = r.data.resultData.agreeDate;
												$scope.modalInstanceCtrl14.lastReqTime = r.data.resultData.lastReqTime;
											}
										}, function(e) {
											console.log(e);
										});
					}
					$scope.modalInstanceCtrl14.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl14.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})

		.controller(
				'modalInstanceCtrl11',
				function($scope, $uibModal, $uibModalInstance, key,
						useUserListService, insightService, strSpliceService) {
					$scope.modalInstanceCtrl11 = {};
					if ($scope.modalInstanceCtrl11.useUserList == "1") {
						$scope.modalInstanceCtrl11.UpdbtnDisabled = true;
						$scope.modalInstanceCtrl11.pushTitleDisabled = true;
					} else {
						$scope.modalInstanceCtrl11.UpdbtnDisabled = false;
						$scope.modalInstanceCtrl11.pushTitleDisabled = false;
					}

					if (key.modeType == "1") {
						$scope.modalInstanceCtrl11.windowHide3 = true;
						// $scope.modalInstanceCtrl11.visible = true;
						$scope.modalInstanceCtrl11.title = "メッセージ新規画面";
						// $scope.modalInstanceCtrl11.button = "プレビュー";
						// 件名
						$scope.modalInstanceCtrl11.pushTitle = "";
						// 本文
						$scope.modalInstanceCtrl11.pushMessage = "";
					} else if (key.modeType == "4") {
						$scope.modalInstanceCtrl11.windowHide3 = true;
						// $scope.modalInstanceCtrl11.visible = true;
						$scope.modalInstanceCtrl11.title = "メッセージ新規画面";
						// $scope.modalInstanceCtrl11.button = "プレビュー";
						// 件名
						$scope.modalInstanceCtrl11.pushTitle = "";
						// 本文
						$scope.modalInstanceCtrl11.pushMessage = "";
					} else if (key.modeType == "3") {
						// $scope.modalInstanceCtrl11.visible = true;
						$scope.modalInstanceCtrl11.title = "CSVファイルアップロード画面";
						// $scope.modalInstanceCtrl11.button = "プレビュー";
						// 件名
						$scope.modalInstanceCtrl11.pushTitle = "";
						// 本文
						$scope.modalInstanceCtrl11.pushMessage = "";

					}
					$scope.modalInstanceCtrl11.close = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl11.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					$uibModalInstance.close({
						'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
					});

					var reader = new FileReader();
					$scope.usease = function() {
						var obj1 = document.getElementById("file");
						var base64String1 = "";
						reader.onload = function(e) {
							// base64String1 = e.target.result;
							// strs = base64String1.split(",");
							// base64String1 = strs[1];
							console.log(e.target.result);
							for (var j = 0; j < key.list.length; j++) {
								key.list[j].select = false;
							}
							var a = e.target.result.split("\n");
							var reg = /^\d{10}$/;
							for (var i = 0; i < a.length; i++) {
								var c = a[i].split(",");

								if (c.length != 3
										|| (!reg.test(c[0]) || c[1] == "" || c[2] == "")) {
									swal(
											"ファイルフォーマットが不正です。（店番＋口座番号（10桁数字）／配信タイトル／配信本文）",
											"", "warning");
									// 画面データを再取得し、再表示する
									$scope.modalInstanceCtrl11.SendbtnDisabled = false;
									$uibModalInstance.close({
										'pushTitle' : null
									});
									return;
								}

							}
							var arrayObj = new Array();
							var csvOutUse = new Array();
							var oldFlag = -1;
							var z = 0;
							for (var i = 0; i < a.length; i++) {
								var flag = "1";
								for (var j = 0; j < key.list.length; j++) {
									if (key.list[j].storeNo != null) {
										if (oldFlag != j) {
											var b = a[i].split(",");
											if (key.list[j].storeNo
													.indexOf(b[0]) > 0) {
												csvOutUse[z] = key.list[j]._id
														+ "," + b[1] + ","
														+ b[2] + "," + b[0]
														+ ","
												z = z + 1;
												var flag = "2";
											}
										}
									}
								}
								if (flag == "1") {
									arrayObj[i] = a[i];
								}
							}
							var url = "./../protected/message/csvMessageUpdate";
							var data = {
								"pushMessageCsv" : csvOutUse,
								"arrNoList" : arrayObj
							};
							var data = useUserListService.setListstatusUpd(url,
									data);
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
																				'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
																			});
																} else if (codeList4[0] == "e.pushMessage.1005") {
																	$uibModalInstance
																			.close({
																				'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
																			});
																}
															});
													$scope.modalInstanceCtrl11.UpdbtnDisabled = false;
												} else {
													if (key.modeType == "1") {
														swal("PUSH配信完了しました。",
																"", "success");
													}
													// CSV出力データ取得途中、エラーなし場合、CSVをダウン‐ロードする
													if (r.data.resultData.date != null
															&& r.data.resultData.date != "") {
														var url = "./../protected/message/accountNumberCsvButtonDownLoad";
														var input1 = $("<input>");
														var form = $("<form>");
														form.attr('style',
																'display:none');
														form.attr('target', '');
														form.attr('method',
																'get');
														form
																.attr('action',
																		url);
														input1.attr("type",
																"hidden");
														input1.attr("name",
																"date");
														input1
																.attr(
																		"value",
																		r.data.resultData.date);

														$('body').append(form);
														form.append(input1);
														form.submit();
														form.remove();

													}
													// 画面データを再取得し、再表示する
													$scope.modalInstanceCtrl11.SendbtnDisabled = false;
													$uibModalInstance
															.close({
																'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
															});
													// loadData(deferred);
												}
											}, function(e) {
												console.log(e);
											});
						}
						document.getElementById("fileName").value = obj1.value;

						if (obj1.files[0] != null && obj1.files[0].size > 0) {
							fileValue1 = obj1.value;
							fileSize1 = obj1.files[0].size;
							fileName = obj1.files[0].name;
							reader.readAsText(obj1.files[0]);
						} else {
							document.getElementById("fileName").value = "";
						}

					}

					// 半角英数
					function isAlphabetNumeric(argValue) {
						if (argValue.match(/[^0-9]/g)) {
							return false;
						} else {
							return true;
						}
					}

					// プレビューボタン押すと
					$scope.modalInstanceCtrl11.dataOK = function(size, item) {
						// if(key.modeType == "3"){
						// $scope.modalInstanceCtrl11.UpdbtnDisabled = true;
						// if (document.getElementById("fileName").value == null
						// || document.getElementById("fileName").value == "") {
						// swal("csvファイルを選択してください。", "", "warning");
						// $scope.modalInstanceCtrl11.UpdbtnDisabled = false;
						// return;
						// }
						// }
						$scope.modalInstanceCtrl11.UpdbtnDisabled = true;
						if ($scope.modalInstanceCtrl11.pushTitle == null
								|| $scope.modalInstanceCtrl11.pushTitle == "") {
							swal("件名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl11.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl11.pushMessage == null
								|| $scope.modalInstanceCtrl11.pushMessage == "") {
							swal("本文を入力してください。", "", "warning");
							$scope.modalInstanceCtrl11.UpdbtnDisabled = false;
							return;
						}

						var url = "./../protected/message/messageDetail";

						var data = {
							"_id" : "",
							"modeType" : key.modeType,
							"pushTitle" : $scope.modalInstanceCtrl11.pushTitle,
							"pushMessage" : $scope.modalInstanceCtrl11.pushMessage
						};
						var data = useUserListService.getDetailMessage(url,
								data);
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
												$scope.modalInstanceCtrl11.outPath = "../protected/temp/html/"
														+ r.data.resultData.outPath;
												var url = $scope.modalInstanceCtrl11.outPath;
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
					$scope.modalInstanceCtrl11.dataSend = function() {
						$scope.modalInstanceCtrl11.sendbtnDisabled = true;
						// if (key.modeType == "3") {
						// if (document.getElementById("fileName").value == null
						// || document.getElementById("fileName").value == "") {
						// swal("csvファイルを選択してください。", "", "warning");
						// $scope.modalInstanceCtrl11.sendbtnDisabled = false;
						// return;
						// }
						// }
						if ($scope.modalInstanceCtrl11.pushTitle == null
								|| $scope.modalInstanceCtrl11.pushTitle == "") {
							swal("件名を入力してください。", "", "warning");
							$scope.modalInstanceCtrl11.sendbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl11.pushMessage == null
								|| $scope.modalInstanceCtrl11.pushMessage == "") {
							swal("本文を入力してください。", "", "warning");
							$scope.modalInstanceCtrl11.sendbtnDisabled = false;
							return;
						}
						if (key.modeType == "4") {
							var url = "./../protected/message/allMessageUpdate";
						} else if (key.modeType == "1") {
							var url = "./../protected/message/messageUpdate";
						}
						var data = {
							"_id" : key._id,
							"modeType" : key.modeType,
							"pushTitle" : $scope.modalInstanceCtrl11.pushTitle,
							"pushMessage" : $scope.modalInstanceCtrl11.pushMessage,
							'pushMessageList' : key.list,
							'arrNoList' : key.arrayObj
						};
						$uibModalInstance.close({
							'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
						});
						var data = useUserListService.setListstatusUpd(url,
								data);
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
																			'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
																		});
															} else if (codeList4[0] == "e.pushMessage.1005") {
																$uibModalInstance
																		.close({
																			'pushTitle' : $scope.modalInstanceCtrl11.pushTitle
																		});
															}
														});
												$scope.modalInstanceCtrl11.UpdbtnDisabled = false;
											} else {
												if (key.modeType == "1") {
													swal("PUSH配信完了しました。", "",
															"success");
												}
												// CSV出力データ取得途中、エラーなし場合、CSVをダウン‐ロードする
												if (r.data.resultData.date != null
														&& r.data.resultData.date != "") {
													var url = "./../protected/message/accountNumberCsvButtonDownLoad";
													var input1 = $("<input>");
													var form = $("<form>");
													form.attr('style',
															'display:none');
													form.attr('target', '');
													form.attr('method', 'get');
													form.attr('action', url);
													input1.attr("type",
															"hidden");
													input1.attr("name", "date");
													input1
															.attr(
																	"value",
																	r.data.resultData.date);

													$('body').append(form);
													form.append(input1);
													form.submit();
													form.remove();

												}
												// 画面データを再取得し、再表示する
												$scope.modalInstanceCtrl11.SendbtnDisabled = false;
												// loadData(deferred);
											}
										}, function(e) {
											console.log(e);
										});
					};
					$scope.modalInstanceCtrl11.ok = function() {
						$uibModalInstance.close({});
					};
					$scope.modalInstanceCtrl11.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				})
