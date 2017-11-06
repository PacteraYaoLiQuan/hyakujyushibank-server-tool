insightApp
		.controller(
				'contentsCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, contentsService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.contentsCtrl = {};

					// 一覧モジュール変数
					$scope.contentsCtrl.table = {};
					$scope.contentsCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.contentsCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if ($scope.contentsCtrl.seesionUserID == item.userID) {
												item.checkDisable = true;
											} else {
												item.select = $scope.contentsCtrl.table.checkboxes.checked;
											}

										});
						console.log($scope.userListCtrl.table.checkboxes);
					}

					$scope.contentsCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					$scope.contentsCtrl.user = $("#contents").val();
					if ($scope.contentsCtrl.user == "1") {
						$scope.contentsCtrl.addBtnDisabled = true;
						$scope.contentsCtrl.table.delBtnDisabled = true;
					}
					$scope.contentsCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.contentsCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.contentsCtrl.table.checkboxes.checked;
										});
						console.log($scope.contentsCtrl.table.checkboxes);
					}
					if ($("#contents").val() == "1") {
						$scope.contentsCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "contentsCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.contentsCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										authorityName : "desc"
									}
								},
								{
									paginationCustomizeButtons : "contentsCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 一括削除ボタンを押下時、
					$scope.contentsCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";

						angular.forEach($scope.contentsCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});
						if (flg == "0") {
							swal("コンテンツデータを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択したコンテンツデータをを削除しますか？",
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
											var url = "./../protected/contents/deleteButton";
											var data = $scope.contentsCtrl.table.sortAndFilter
													.settings().dataset;
											var data = contentsService
													.setListDelete(
															url,
															{
																'contentsInitList' : data
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
					$scope.contentsCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'contentsDetailPopup.html',
							controller : 'modalInstanceCtrl7',
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
							if (result.updFlg != null) {
								loadData();
							}
						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}

					// 権限詳細画面を呼び出す
					$scope.contentsCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'contentsDetailPopup.html',
							controller : 'modalInstanceCtrl7',
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
							if (result.updFlg != null) {
								loadData();
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
						var data = contentsService.setListDelete(url, data);
						// 一覧初期データ取得
						var url = "./../protected/contents/contentsList";

						$scope.contentsCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = contentsService.getListData(url, params);
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
													if (codeList[0] == "e.contents.1005") {
														$scope.contentsCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.contentsInitList;
														$scope.contentsCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												$scope.contentsCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.contentsInitList;
												$scope.contentsCtrl.table.sortAndFilter
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
				'modalInstanceCtrl7',
				function($scope, $uibModalInstance, key, dateFilter,
						insightService, contentsService, strSpliceService) {
					$scope.modalInstanceCtrl7 = {};
					// 開始日時,終了日時
					$scope.modalInstanceCtrl7.datepicker = {}
					var maxSize = 1024 * 1024;
					var reader = new FileReader();
					var base64String1 = "", base64String2 = "", base64String3 = "", base64String4 = "", base64String5 = "";
					var fileName1 = "", fileName2 = "", fileName3 = "", fileName4 = "", fileName5 = "";
					var flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0;
					var fileSize1 = 0, fileSize2 = 0, fileSize3 = 0, fileSize4 = 0, fileSize5 = 0;
					var fileValue1 = "", fileValue2 = "", fileValue3 = "", fileValue4 = "", fileValue5 = "", contentsID = "";
					$scope.usease1 = function() {
						var obj1 = document.getElementById("file1");
						reader.onload = function(e) {
							base64String1 = e.target.result;
							strs = base64String1.split(",");
							base64String1 = strs[1];
						}
						document.getElementById("filename1").value = obj1.value;

						if (obj1.files[0] != null && obj1.files[0].size > 0) {
							fileValue1 = obj1.value;
							fileSize1 = obj1.files[0].size;
							fileName1 = obj1.files[0].name;
							reader.readAsDataURL(obj1.files[0]);
						} else {
							document.getElementById("filename1").value = fileValue1;
						}
					}
					$scope.usease2 = function() {
						var obj2 = document.getElementById("file2");
						reader.onload = function(e) {
							base64String2 = e.target.result;
							strs = base64String2.split(",");
							base64String2 = strs[1];
						}
						document.getElementById("filename2").value = obj2.value;
						if (obj2.files[0] != null && obj2.files[0].size > 0) {
							fileValue2 = obj2.value;
							fileSize2 = obj2.files[0].size;
							fileName2 = obj2.files[0].name;
							reader.readAsDataURL(obj2.files[0]);
						} else {
							document.getElementById("filename2").value = fileValue2;
						}
					}
					$scope.usease3 = function() {
						var obj3 = document.getElementById("file3");
						reader.onload = function(e) {
							base64String3 = e.target.result;
							strs = base64String3.split(",");
							base64String3 = strs[1];
						}
						document.getElementById("filename3").value = obj3.value;
						if (obj3.files[0] != null && obj3.files[0].size > 0) {
							fileValue3 = obj3.value;
							fileSize3 = obj3.files[0].size;
							fileName3 = obj3.files[0].name;
							reader.readAsDataURL(obj3.files[0]);
						} else {
							document.getElementById("filename3").value = fileValue3;
						}
					}
					$scope.usease4 = function() {
						var obj4 = document.getElementById("file4");
						reader.onload = function(e) {
							base64String4 = e.target.result;
							strs = base64String4.split(",");
							base64String4 = strs[1];
						}
						document.getElementById("filename4").value = obj4.value;
						if (obj4.files[0] != null && obj4.files[0].size > 0) {
							fileValue4 = obj4.value;
							fileSize4 = obj4.files[0].size;
							fileName4 = obj4.files[0].name;
							reader.readAsDataURL(obj4.files[0]);
						} else {
							document.getElementById("filename4").value = fileValue4;
						}
					}
					$scope.usease5 = function() {
						var obj5 = document.getElementById("file5");
						reader.onload = function(e) {
							base64String5 = e.target.result;
							strs = base64String5.split(",");
							base64String5 = strs[1];
						}
						document.getElementById("filename5").value = obj5.value;
						if (obj5.files[0] != null && obj5.files[0].size > 0) {
							fileValue5 = obj5.value;
							fileSize5 = obj5.files[0].size;
							fileName5 = obj5.files[0].name;
							reader.readAsDataURL(obj5.files[0]);
						} else {
							document.getElementById("filename5").value = fileValue5;
						}
					}
					var appCD = "", typeCD = "";
					$scope.getTypeCD = function() {
						appCD = document.getElementById("sel").value;
						var url = "./../protected/contents/contentsTypeCDList";
						var data = {
							'appCD' : appCD.split("：")[0]
						};
						var data = contentsService.dataUpd(url, data);
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
												$scope.modalInstanceCtrl7.typeCDList = r.data.resultData.typeCDList;
												typeCD = "";
											}
										}, function(e) {
											console.log(e);
										});
					}

					var url = "./../protected/contentsType/contentsAppCDList";
					var params = {};
					var data = contentsService.getListData(url, params);
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

										} else {
											$scope.modalInstanceCtrl7.appCDList = r.data.resultData.appCDList;
										}
									}, function(e) {
										console.log(e);
									});
					$scope.changeTypeCD = function() {
						typeCD = document.getElementById("select").value;
						typeCD = typeCD.split("：")[0];
					}
					$scope.modalInstanceCtrl7.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.modalInstanceCtrl7.datepicker[opened] = true;
					};

					$scope.modalInstanceCtrl7.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};
					$scope.modalInstanceCtrl7.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					// 開始日時
					setTimeout(function() {
						$('#dtPopup1').datetimepicker({
							format : "YYYYMMDD HH:mm",
							useCurrent : false
						});
					});
					// 終了日時
					setTimeout(function() {
						$('#dtPopup2').datetimepicker({
							format : "YYYYMMDD HH:mm",
							useCurrent : false
						});
					});

					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = contentsService.setListDelete(url, data);
					if ($("#contents").val() == "1") {
						$scope.modalInstanceCtrl7.UpdbtnDisabled = true;
					}
					if (key.modeType == "1") {
						$scope.modalInstanceCtrl7.title = "コンテンツ登録";
						$scope.modalInstanceCtrl7.button = "登録";
						$scope.modalInstanceCtrl7.appCD = "";
						typeCD = "";
						$scope.modalInstanceCtrl7.contentsTitle = "";
						$scope.modalInstanceCtrl7.dateFrom = "";
						$scope.modalInstanceCtrl7.dateTo = "";
						$scope.modalInstanceCtrl7.select = false;
						$scope.modalInstanceCtrl7.input = true;
						$scope.modalInstanceCtrl7.path1 = true;
						$scope.modalInstanceCtrl7.path2 = true;
						$scope.modalInstanceCtrl7.path3 = true;
						$scope.modalInstanceCtrl7.path4 = true;
						$scope.modalInstanceCtrl7.path5 = true;

						var url = "./../protected/contents/autoContentsID";
						var params = {};
						var data = contentsService.getDetailData(url, params);
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
												$uibModalInstance.close({});
											} else {
												$scope.modalInstanceCtrl7.contentsID = r.data.resultData.contentsID;
											}
										}, function(e) {
											console.log(e);
										});
					} else {
						$scope.modalInstanceCtrl7.title = "コンテンツ詳細";
						$scope.modalInstanceCtrl7.button = "更新";
						$scope.modalInstanceCtrl7.appCDDisabled = true;
						$scope.modalInstanceCtrl7.typeCDDisabled = true;
						$scope.modalInstanceCtrl7.contentsIDDisabled = true;
						$scope.modalInstanceCtrl7.select = true;
						$scope.modalInstanceCtrl7.input = false;
						$scope.modalInstanceCtrl7.path1 = true;
						$scope.modalInstanceCtrl7.path2 = true;
						$scope.modalInstanceCtrl7.path3 = true;
						$scope.modalInstanceCtrl7.path4 = true;
						$scope.modalInstanceCtrl7.path5 = true;
						var url = "./../protected/contents/contentsDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = contentsService.getDetailData(url, params);
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
												$uibModalInstance.close({});
											} else {
												$scope.modalInstanceCtrl7.appCD = r.data.resultData.appCD;
												$scope.modalInstanceCtrl7.typeCD = r.data.resultData.typeCD;
												typeCD = r.data.resultData.typeCD;
												$scope.modalInstanceCtrl7.contentsID = r.data.resultData.contentsID;

												$scope.modalInstanceCtrl7.contentsTitle = r.data.resultData.contentsTitle;
												$scope.modalInstanceCtrl7.dateFrom = r.data.resultData.dateFrom;
												$scope.modalInstanceCtrl7.dateTo = r.data.resultData.dateTo;
												$scope.modalInstanceCtrl7.comment1 = r.data.resultData.comment1;
												$scope.modalInstanceCtrl7.comment2 = r.data.resultData.comment2;
												$scope.modalInstanceCtrl7.comment3 = r.data.resultData.comment3;
												$scope.modalInstanceCtrl7.comment4 = r.data.resultData.comment4;
												$scope.modalInstanceCtrl7.comment5 = r.data.resultData.comment5;
												$scope.modalInstanceCtrl7.comment6 = r.data.resultData.comment6;
												$scope.modalInstanceCtrl7.comment7 = r.data.resultData.comment7;
												$scope.modalInstanceCtrl7.comment8 = r.data.resultData.comment8;
												$scope.modalInstanceCtrl7.comment9 = r.data.resultData.comment9;
												$scope.modalInstanceCtrl7.comment10 = r.data.resultData.comment10;
												$scope.filePath1 = r.data.resultData.contentsFilePath1;
												$scope.modalInstanceCtrl7.contentsFileName1 = r.data.resultData.contentsFileName1;
												$scope.modalInstanceCtrl7.contentsFile1 = r.data.resultData.contentsFile1;
												$scope.modalInstanceCtrl7.filename1 = r.data.resultData.contentsFileName1;
												$scope.filePath2 = r.data.resultData.contentsFilePath2;
												$scope.modalInstanceCtrl7.contentsFileName2 = r.data.resultData.contentsFileName2;
												$scope.modalInstanceCtrl7.contentsFile2 = r.data.resultData.contentsFile2;
												$scope.modalInstanceCtrl7.filename2 = r.data.resultData.contentsFileName2;
												$scope.filePath3 = r.data.resultData.contentsFilePath3;
												$scope.modalInstanceCtrl7.contentsFileName3 = r.data.resultData.contentsFileName3;
												$scope.modalInstanceCtrl7.contentsFile3 = r.data.resultData.contentsFile3;
												$scope.modalInstanceCtrl7.filename3 = r.data.resultData.contentsFileName3;
												$scope.filePath4 = r.data.resultData.contentsFilePath4;
												$scope.modalInstanceCtrl7.contentsFileName4 = r.data.resultData.contentsFileName4;
												$scope.modalInstanceCtrl7.contentsFile4 = r.data.resultData.contentsFile4;
												$scope.modalInstanceCtrl7.filename4 = r.data.resultData.contentsFileName4;
												$scope.filePath5 = r.data.resultData.contentsFilePath5;
												$scope.modalInstanceCtrl7.contentsFileName5 = r.data.resultData.contentsFileName5;
												$scope.modalInstanceCtrl7.contentsFile5 = r.data.resultData.contentsFile5;
												$scope.modalInstanceCtrl7.filename5 = r.data.resultData.contentsFileName5;
												$scope.modalInstanceCtrl7.createFlag1 = r.data.resultData.createFlag1;
												$scope.modalInstanceCtrl7.createFlag2 = r.data.resultData.createFlag2;
												$scope.modalInstanceCtrl7.createFlag3 = r.data.resultData.createFlag3;
												$scope.modalInstanceCtrl7.createFlag4 = r.data.resultData.createFlag4;
												$scope.modalInstanceCtrl7.createFlag5 = r.data.resultData.createFlag5;
												if (r.data.resultData.createFlag1 == 2) {
													$scope.modalInstanceCtrl7.delFlg1 = true;
												}
												if (r.data.resultData.createFlag2 == 2) {
													$scope.modalInstanceCtrl7.delFlg2 = true;
												}
												if (r.data.resultData.createFlag3 == 2) {
													$scope.modalInstanceCtrl7.delFlg3 = true;
												}
												if (r.data.resultData.createFlag4 == 2) {
													$scope.modalInstanceCtrl7.delFlg4 = true;
												}
												if (r.data.resultData.createFlag5 == 2) {
													$scope.modalInstanceCtrl7.delFlg5 = true;
												}
												if (r.data.resultData.contentsFile1 != "") {
													$scope.modalInstanceCtrl7.path1 = false;
												}
												if (r.data.resultData.contentsFile2 != "") {
													$scope.modalInstanceCtrl7.path2 = false;
												}
												if (r.data.resultData.contentsFile3 != "") {
													$scope.modalInstanceCtrl7.path3 = false;
												}
												if (r.data.resultData.contentsFile4 != "") {
													$scope.modalInstanceCtrl7.path4 = false;
												}
												if (r.data.resultData.contentsFile5 != "") {
													$scope.modalInstanceCtrl7.path5 = false;
												}
												var appCD = $scope.modalInstanceCtrl7.appCD
														.split("：")[0];
												var url = "./../protected/contents/contentsTypeCDList";
												var data = {
													'appCD' : appCD
												};
												var data = contentsService
														.dataUpd(url, data);
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
																		$scope.modalInstanceCtrl7.typeCDList = r.data.resultData.typeCDList;
																		$scope.modalInstanceCtrl7.typeCD = typeCD;
																	}
																},
																function(e) {
																	console
																			.log(e);
																});
											}
										}, function(e) {
											console.log(e);
										});

					}
					$scope.modalInstanceCtrl7.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl7.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					function getFileName(fileName) {
						var i = fileName.indexOf(".");
						if (i == -1) {
							return "jpeg";
						} else {
							var num = GetCharNum(fileName, ".");
							return fileName.split(".")[num];
						}
					}
					function GetCharNum(s, f) {
						var re = new RegExp("[" + f + "]", "g");
						if (re.test(s)) {
							return s.match(re).length;
						} else {
							return 0;
						}
					}

					// 登録／更新ボタンを押す
					$scope.modalInstanceCtrl7.dataUpd = function() {
						appCD = $scope.modalInstanceCtrl7.appCD.split("：")[0];
						var i = typeCD.indexOf("：");
						if (i != -1) {
							typeCD = typeCD.split("：")[0];
						}

						var reg = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
						var dateFrom = typeof ($("#dtPopup1").val()) == "undefined" ? ""
								: $("#dtPopup1").val();
						var dateTo = typeof ($("#dtPopup2").val()) == "undefined" ? ""
								: $("#dtPopup2").val();

						$scope.modalInstanceCtrl7.UpdbtnDisabled = true;
						if ($scope.modalInstanceCtrl7.appCD == null
								|| $scope.modalInstanceCtrl7.appCD == "") {
							swal("アプリケーションコードを選択してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						var reg = new RegExp('[a-zA-Z0-9]');
						if (!reg.test($scope.modalInstanceCtrl7.contentsID)) {
							swal("コンテンツIDを入力してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl7.contentsID == null
								|| $scope.modalInstanceCtrl7.contentsID == "") {
							swal("コンテンツIDを入力してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl7.contentsTitle == null
								|| $scope.modalInstanceCtrl7.contentsTitle == "") {
							swal("コンテンツタイトルを入力してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if (typeCD == null || typeCD == "") {
							swal("コンテンツ種別コードを選択してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if (dateFilter(dateFrom, "yyyy/MM/dd") == null
								|| dateFilter(dateFrom, "yyyy/MM/dd") == "") {
							swal("表示期間を選択してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if (dateFilter(dateTo, "yyyy/MM/dd") == null
								|| dateFilter(dateTo, "yyyy/MM/dd") == "") {
							swal("表示期間を選択してください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						var applyYear = $("#dtPopup2").val().substring(0, 4);
						var applyMonth = $("#dtPopup2").val().substring(4, 6) - 1;
						var applyDay = $("#dtPopup2").val().substring(6, 8);
						var applyHour = $("#dtPopup2").val().substring(9, 11);
						var applyMin = $("#dtPopup2").val().split(":")[1];
						var applyDate = new Date(applyYear, applyMonth,
								applyDay, applyHour, applyMin, 00);
						var applyYear1 = $("#dtPopup1").val().substring(0, 4);
						var applyMonth1 = $("#dtPopup1").val().substring(4, 6) - 1;
						var applyDay1 = $("#dtPopup1").val().substring(6, 8);
						var applyHour1 = $("#dtPopup1").val().substring(9, 11);
						var applyMin1 = $("#dtPopup1").val().split(":")[1];
						var applyDate1 = new Date(applyYear1, applyMonth1,
								applyDay1, applyHour1, applyMin1, 00);
						if (applyDate1.valueOf() > applyDate.valueOf()) {
							swal("終了日は開始日より過去日付を選択できません。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						var newFileName = $scope.modalInstanceCtrl7.appCD
								.split("：")[0]
								+ "_"
								+ $scope.modalInstanceCtrl7.contentsID
								+ "_";
						if (fileName1 != null && fileName1 != "") {
							fileName1 = newFileName + "A."
									+ getFileName(fileName1);
						} else {
							fileName1 = "";
						}
						if (fileName2 != null && fileName2 != "") {
							fileName2 = newFileName + "B."
									+ getFileName(fileName2);
						} else {
							fileName2 = "";
						}
						if (fileName3 != null && fileName3 != "") {
							fileName3 = newFileName + "C."
									+ getFileName(fileName3);
						} else {
							fileName3 = "";
						}
						if (fileName4 != null && fileName4 != "") {
							fileName4 = newFileName + "D."
									+ getFileName(fileName4);
						} else {
							fileName4 = "";
						}
						if (fileName5 != null && fileName5 != "") {
							fileName5 = newFileName + "E."
									+ getFileName(fileName5);
						} else {
							fileName5 = "";
						}
						if ($scope.modalInstanceCtrl7.delFlg1 == true) {
							$scope.modalInstanceCtrl7.contentsFile1 = "";
							base64String1 = "";
							fileName1 = "";
							fileSize1 = 0;
						}
						if ($scope.modalInstanceCtrl7.delFlg2 == true) {
							$scope.modalInstanceCtrl7.contentsFile2 = "";
							base64String2 = "";
							fileName2 = "";
							fileSize2 = 0;
						}
						if ($scope.modalInstanceCtrl7.delFlg3 == true) {
							$scope.modalInstanceCtrl7.contentsFile3 = "";
							base64String3 = "";
							fileName3 = "";
							fileSize3 = 0;
						}
						if ($scope.modalInstanceCtrl7.delFlg4 == true) {
							$scope.modalInstanceCtrl7.contentsFile4 = "";
							base64String4 = "";
							fileName4 = "";
							fileSize4 = 0;
						}
						if ($scope.modalInstanceCtrl7.delFlg5 == true) {
							$scope.modalInstanceCtrl7.contentsFile5 = "";
							base64String5 = "";
							fileName5 = "";
							fileSize5 = 0;
						}
						if (fileSize1 > maxSize || fileSize2 > maxSize
								|| fileSize3 > maxSize || fileSize4 > maxSize
								|| fileSize5 > maxSize) {
							swal("アップロードするファイルサイズは1M以下にしてください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if (fileSize1 + fileSize2 + fileSize3 + fileSize4
								+ fileSize5 > maxSize) {
							swal("アップロードするファイルサイズは1M以下にしてください。", "", "warning");
							$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
							return;
						}
						if (fileName1 == ""
								&& $scope.modalInstanceCtrl7.contentsFileName1 != "") {
							flag1 = $scope.modalInstanceCtrl7.createFlag1;
						}
						if (fileName2 == ""
								&& $scope.modalInstanceCtrl7.contentsFileName2 != "") {
							flag2 = $scope.modalInstanceCtrl7.createFlag2;
						}
						if (fileName3 == ""
								&& $scope.modalInstanceCtrl7.contentsFileName3 != "") {
							flag3 = $scope.modalInstanceCtrl7.createFlag3;
						}
						if (fileName4 == ""
								&& $scope.modalInstanceCtrl7.contentsFileName4 != "") {
							flag4 = $scope.modalInstanceCtrl7.createFlag4;
						}
						if (fileName5 == ""
								&& $scope.modalInstanceCtrl7.contentsFileName5 != "") {
							flag5 = $scope.modalInstanceCtrl7.createFlag5;
						}

						var url = "./../protected/contents/contentsUpd";
						if (base64String1 == "" || base64String1 == null) {
							base64String1 = $scope.modalInstanceCtrl7.contentsFile1;
						}
						if (base64String2 == "" || base64String1 == null) {
							base64String2 = $scope.modalInstanceCtrl7.contentsFile2;
						}
						if (base64String3 == "" || base64String1 == null) {
							base64String3 = $scope.modalInstanceCtrl7.contentsFile3;
						}
						if (base64String4 == "" || base64String1 == null) {
							base64String4 = $scope.modalInstanceCtrl7.contentsFile4;
						}
						if (base64String5 == "" || base64String1 == null) {
							base64String5 = $scope.modalInstanceCtrl7.contentsFile5;
						}
						if (fileName1 == "" || fileName1 == null) {
							fileName1 = $scope.modalInstanceCtrl7.contentsFileName1;
						}
						if (fileName2 == "" || fileName2 == null) {
							fileName2 = $scope.modalInstanceCtrl7.contentsFileName2;
						}
						if (fileName3 == "" || fileName3 == null) {
							fileName3 = $scope.modalInstanceCtrl7.contentsFileName3;
						}
						if (fileName4 == "" || fileName4 == null) {
							fileName4 = $scope.modalInstanceCtrl7.contentsFileName4;
						}
						if (fileName5 == "" || fileName5 == null) {
							fileName5 = $scope.modalInstanceCtrl7.contentsFileName5;
						}

						var dateFrom = dateFilter(dateFrom, "yyyy/MM/dd hh:mm");
						var dateTo = dateFilter(dateTo, "yyyy/MM/dd hh:mm");

						var data = {
							'_id' : key._id,
							"modeType" : key.modeType,
							'appCD' : appCD,
							'typeCD' : typeCD,
							'contentsID' : $scope.modalInstanceCtrl7.contentsID,
							'contentsTitle' : $scope.modalInstanceCtrl7.contentsTitle,
							'dateFrom' : dateFrom,
							'dateTo' : dateTo,
							'comment1' : $scope.modalInstanceCtrl7.comment1,
							'comment2' : $scope.modalInstanceCtrl7.comment2,
							'comment3' : $scope.modalInstanceCtrl7.comment3,
							'comment4' : $scope.modalInstanceCtrl7.comment4,
							'comment5' : $scope.modalInstanceCtrl7.comment5,
							'comment6' : $scope.modalInstanceCtrl7.comment6,
							'comment7' : $scope.modalInstanceCtrl7.comment7,
							'comment8' : $scope.modalInstanceCtrl7.comment8,
							'comment9' : $scope.modalInstanceCtrl7.comment9,
							'comment10' : $scope.modalInstanceCtrl7.comment10,
							'contentsFileName1' : fileName1,
							'contentsFile1' : base64String1,
							'contentsFileName2' : fileName2,
							'contentsFile2' : base64String2,
							'contentsFileName3' : fileName3,
							'contentsFile3' : base64String3,
							'contentsFileName4' : fileName4,
							'contentsFile4' : base64String4,
							'contentsFileName5' : fileName5,
							'contentsFile5' : base64String5,
							'createFlag1' : flag1,
							'createFlag2' : flag2,
							'createFlag3' : flag3,
							'createFlag4' : flag4,
							'createFlag5' : flag5
						};
						var data = contentsService.dataUpd(url, data);
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
												$scope.modalInstanceCtrl7.UpdbtnDisabled = false;
											} else {
												if (key.modeType == "2") {
													swal("更新しました。", "",
															"success");
												} else {
													swal("登録しました。", "",
															"success");
												}

												$uibModalInstance.close({
													'updFlg' : "1",
												});
											}
										}, function(e) {
											console.log(e);
										});

					};
				})