insightApp
		.controller(
				'fileListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, fileListService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.fileListCtrl = {};
					var filePath="";
					// 一覧モジュール変数
					$scope.fileListCtrl.table = {};
					$scope.fileListCtrl.datepicker = {};
					$scope.fileListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					// データ存在状態を取得
					$scope.fileListCtrl.table.filterList2 = insightService
							.getInsightData("fileNameJP");
					$scope.fileListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.fileListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.fileListCtrl.table.checkboxes.checked;
										});
						console.log($scope.fileListCtrl.table.checkboxes);
					}

					if ($("#imageFile").val() == "1") {
						$scope.fileListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										fileNameJP : "desc"
									}
								},
								{
									paginationCustomizeButtons : "fileListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.fileListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										fileNameJP : "desc"
									}
								},
								{
									paginationCustomizeButtons : "fileListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}

					$scope.fileListCtrl.imageFile = $("#imageFile").val();
					if ($scope.fileListCtrl.imageFile == "1") {
						$scope.fileListCtrl.addBtnDisabled = true;
						$scope.fileListCtrl.fileListSelBtn = true;
						//						 $scope.fileListCtrl.table.deleteBtn = function() {}
					}

					// 一括削除ボタンを押下時、
					$scope.fileListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";
						var flg1 = "1";
						angular.forEach($scope.fileListCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});

						if (flg == "0") {
							swal("表示用画像＆URLデータを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択したファイルを削除しますか？",
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
											var url = "./../protected/imageFile/deleteButton";
											var data = $scope.fileListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = fileListService
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
					// 新規登録ボタン
					// 新規登録ボタンを押下時、
					$scope.fileListCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'fileDetailPopup.html',
							controller : 'modalInstanceCtrl5',
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
							if (result.fileNameJP != null) {
								loadData();
							}
						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}

					// ファイル詳細画面を呼び出す
					$scope.fileListCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal.open({
							animation : true,
							templateUrl : 'fileDetailPopup.html',
							controller : 'modalInstanceCtrl5',
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
							if (result.fileNameJP != null) {
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
										$scope.fileListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											// $scope.fileListCtrl.checkDisable=false;
											if ($scope.fileListCtrl.seesionUserID == item.userID) {
												item.checkDisable = true;
											}

										});
					}

					// 検索データを取得
					$scope.fileListCtrl.table.fileListSelBtn = function() {
						var url = "./../protected/imageFile/fileSel";
						$scope.fileListCtrl.checkDisable = false;
						$scope.fileListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = fileListService.getListDatas(url, {
							'fileSel' : data,
							'fileNameJP' : $('#insert1').val(),
							'useLocal' : $('#insert2').val()
						});
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
												if (codeList[0] == "e.fileList.1001") {
													$scope.fileListCtrl.table.sortAndFilter
															.settings().dataset = r.data.resultData.fileList;
													$scope.fileListCtrl.table.sortAndFilter
															.reload();
												}
											} else {
												$scope.fileListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.fileList;
												$scope.fileListCtrl.table.sortAndFilter
														.reload();
												if (typeof deferred != "undefined") {
													deferred.resolve("resolve");
												}
											}
										}, function(e) {
											console.log(e);
										});
					}

					// 初期化データを取得
					function loadData(deferred) {
						var url = "./../protected/imageFile/fileList";
						$scope.fileListCtrl.checkDisable = false;
						$scope.fileListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = fileListService.getListData(url, params);
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
														$scope.fileListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.fileList;
														$scope.fileListCtrl.table.sortAndFilter
																.reload();
											} else {
												$scope.fileListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.fileList;
												$scope.fileListCtrl.table.sortAndFilter
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
				'modalInstanceCtrl5',
				function($scope, $uibModalInstance, key, fileListService,
						insightService, strSpliceService) {
					$scope.modalInstanceCtrl5 = {};
					$scope.modalInstanceCtrl5.imageFile = $("#imageFile").val();
					if ($scope.modalInstanceCtrl5.imageFile == "1") {
						$scope.modalInstanceCtrl5.UpdbtnDisabled = true;
						$scope.modalInstanceCtrl5.fileNameJPDisabled = true;
					} else {
						$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
						$scope.modalInstanceCtrl5.fileNameJPDisabled = false;
					}

					var reader = new FileReader();
					var base64String = "";
					var flag = "0";
					$scope.usease = function() {
						var obj=document.getElementById("file");
						reader.onload = function(e) {
							base64String = e.target.result;
							strs = base64String.split(",");
							base64String = strs[1];
							flag = "1";
						}
						document.getElementById("filename").value = obj.value ;
						if(obj.files[0] != null && obj.files[0].size > 0) {
							reader.readAsDataURL(obj.files[0]);
							}
						else{
							document.getElementById("filename").value = "";
						}
//						$scope.modalInstanceCtrl5.path = $("#file").val();
					}		
					if (key.modeType == "1") {
						$scope.modalInstanceCtrl5.title = "ファイル新規画面";
						$scope.modalInstanceCtrl5.button = "登録";
						// ファイル名（日本語）
						$scope.modalInstanceCtrl5.fileNameJP = "";
						// ファイル名（英語）
						$scope.modalInstanceCtrl5.fileNameEN = "";
						// 利用箇所
						$scope.modalInstanceCtrl5.useLocal = "";
						// ファイルアップロード
						$scope.modalInstanceCtrl5.path = "";
						// 参照URL
						$scope.modalInstanceCtrl5.referURL = "";

					} else {
						$scope.modalInstanceCtrl5.title = "ファイル編集画面";
						$scope.modalInstanceCtrl5.button = "更新";
						$scope.modalInstanceCtrl5.fileNameJPDisabled = true;
						$scope.modalInstanceCtrl5.fileNameENDisabled = true;
						var url = "./../protected/file/fileDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = fileListService.getDetailData(url, params);
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
												// ファイル名（日本語）
												$scope.modalInstanceCtrl5.fileNameJP = r.data.resultData.fileNameJP;
												// ファイル名（英語）
												$scope.modalInstanceCtrl5.fileNameEN = r.data.resultData.fileNameEN;
												// 利用箇所
												$scope.modalInstanceCtrl5.useLocal = r.data.resultData.useLocal;
												// ファイルアップロード
//												$scope.modalInstanceCtrl5.path = r.data.resultData.path;
												// fileStream
												$scope.modalInstanceCtrl5.fileStream = r.data.resultData.fileStream;
//												document.getElementById("filename").value = r.data.resultData.path ;
											}
										}, function(e) {
											console.log(e);
										});
					}
					$scope.modalInstanceCtrl5.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl5.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					
					// 更新ボタンを押す
					$scope.modalInstanceCtrl5.dataUpd = function() {
						$scope.modalInstanceCtrl5.UpdbtnDisabled = true;
						if ($scope.modalInstanceCtrl5.fileNameJP == null
								|| $scope.modalInstanceCtrl5.fileNameJP == "") {
							swal("ファイル名（日本語）を入力してください。", "", "warning");
							$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
							return;
						}
						if ($scope.modalInstanceCtrl5.fileNameEN == null
								|| $scope.modalInstanceCtrl5.fileNameEN == "") {
							swal("ファイル名（英語）を入力してください。", "", "warning");
							$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
							return;
						}
						if (isAlphabetNumeric($scope.modalInstanceCtrl5.fileNameEN) == false) {
							 swal("ファイル名（英語）は半角英数を入力してください。", "", "warning");
							 $scope.modalInstanceCtrl5.UpdbtnDisabled = false;
							 return;
							 }
						if ($scope.modalInstanceCtrl5.useLocal == null
								|| $scope.modalInstanceCtrl5.useLocal == "") {
							swal("利用箇所を入力してください。", "", "warning");
							$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
							return;
						}
						if (key.modeType == "1") {
							if (document.getElementById("filename").value == null
									|| document.getElementById("filename").value == "") {
								swal("ファイルアップロードを選択してください。", "", "warning");
								$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
								return;
							}
						}
						if (flag == "0") {
							base64String = $scope.modalInstanceCtrl5.fileStream;
						}
						$scope.modalInstanceCtrl5.path = document.getElementById("filename").value
						var url = "./../protected/file/fileUpdate";
						var data = {
							"_id" : key._id,
							"modeType" : key.modeType,
							"fileNameJP" : $scope.modalInstanceCtrl5.fileNameJP,
							"fileNameEN" : $scope.modalInstanceCtrl5.fileNameEN,
							"useLocal" : $scope.modalInstanceCtrl5.useLocal,
							"referURL" : $scope.modalInstanceCtrl5.referURL,
							"path" : $scope.modalInstanceCtrl5.path,
							"fileStream" : base64String
						};
						var data = fileListService.dataUpd(url, data);
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
															if (codeList4[0] == "e.fileDetail.1004") {
																$uibModalInstance
																		.close({
																			'fileNameJP' : $scope.modalInstanceCtrl5.fileNameJP
																		});
															}
														});
												$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
											} else {
												if (key.modeType == "1") {
													swal("登録成功しました。", "",
															"success");
												} else {
													swal("更新成功しました。", "",
															"success");
												}
												$scope.modalInstanceCtrl5.UpdbtnDisabled = false;
												$uibModalInstance
														.close({
															'fileNameJP' : $scope.modalInstanceCtrl5.fileNameJP
														});
											}
										}, function(e) {
											console.log(e);
										});
					};
					function isAlphabetNumeric(argValue) {
						if (argValue.match(/[^A-Z|^a-z|^0-9]/g)) {
							return false;
						} else { 
							return true;
						}
					};
				})
