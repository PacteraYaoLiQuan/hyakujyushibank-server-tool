insightApp
		.controller(
				'fileUploadCtrl',
				function($scope,$timeout,$uibModal,
						NgTableParams,$q,dateFilter, 
						insightService, fileUploadService,strSpliceService) {
					var file = document.getElementById("file");
					var name = document.getElementById("name");
					
					file.onchange = function() {
						name.value = getFullPath(this);
					}
					$scope.fileUploadCtrl = {};
					$scope.fileUploadCtrl.table = {};
					$scope.fileUploadCtrl.datepicker = {};
					
					$scope.fileUploadCtrl.windowHide = true;
					$scope.fileUploadCtrl.datepicker.formats = [ 'dd-MMMM-yyyy',
					               							'yyyy/MM/dd', 'dd.MM.yyyy', 'yyyy/MM/dd hh:mm',
					               							'shortDate' ];
					var dateMin =  new Date();
					dateMin.setDate(dateMin.getDate());
					dateMin.setTime(dateMin.getTime()+1800000);
					$('#dtPopup2')
					.datetimepicker(
							{
								format : "YYYY/MM/DD HH:mm",
								defaultDate : dateMin,
								minDate:dateMin,
								useCurrent : false
							});
					$scope.fileUploadCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
					
					$scope.fileUploadCtrl.datepicker.format = $scope.fileUploadCtrl.datepicker.formats[1];
					setTimeout(function() {
						$('#hopingUseDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
							
						});
					});
					setTimeout(function() {
						$('#uploadDatetime').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					setTimeout(function() {
						$('#batExecuteDatetime').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.fileUploadCtrl.datepicker.open = function($event,
							opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.fileUploadCtrl.datepicker[opened] = true;
					};


					
					$scope.fileUploadCtrl.datepicker.dateOptions = {
							formatYear : 'yy',
							startingDay : 1
						};
					
						$scope.fileUploadCtrl.table.checkItemAll = function() {
							angular
									.forEach(
											$scope.fileUploadCtrl.table.sortAndFilter
													.settings().dataset,
											function(item) {
												if("0"==item.useFlag && "―" == item.batExecuteDatetime){
													item.select = $scope.fileUploadCtrl.table.checkboxes.checked;
												} else {
													item.checkDisable=true;
												}
											});
							console.log($scope.fileUploadCtrl.table.checkboxes);
						}
					// 一覧モジュール変数
						function check(item){
							angular
							.forEach(
									$scope.fileUploadCtrl.table.sortAndFilter
											.settings().dataset,
									function(item) {
//										$scope.userListCtrl.checkDisable=false;
										if("0"==item.useFlag && "―" == item.batExecuteDatetime){
											
										} else {
											item.checkDisable=true;
										}

									});
						}
						//利用中フラグを取得
						$scope.fileUploadCtrl.table.filterList2 = insightService
								.getInsightData("useFlagData");
						
						if ($("#fileSession").val() == "1") {
							$scope.fileUploadCtrl.table.sortAndFilter = new NgTableParams(
									{
										page : 1, // show first page
										count : 10, // count per page
										sorting : {
											uploadDatetime : "desc"
										}
									},
									{
										paginationCustomizeButtons : "fileUploadCtrl/table/sortAndFilter/paginationButtonDisabled.html",
										counts : [ 10, 20, 30, 50, 100 ],
										dataset : []
									});
						} else {
							$scope.fileUploadCtrl.table.sortAndFilter = new NgTableParams(
									{
										page : 1, // show first page
										count : 10, // count per page
										sorting : {
											uploadDatetime : "desc"
										}
									},
									{
										paginationCustomizeButtons : "fileUploadCtrl/table/sortAndFilter/paginationButton.html",
										counts : [ 10, 20, 30, 50, 100 ],
										dataset : []
									});
						}
						$scope.fileUploadCtrl.fileSession = $("#fileSession").val();
						if ($scope.fileUploadCtrl.fileSession == "1") {
							$scope.fileUploadCtrl.findFileBtnDisabled = true;
							$scope.fileUploadCtrl.uploadBtnDisabled=true;
							// $scope.userListCtrl.table.deleteBtn = function() {}
						}
						
						
					
					
					// controllerオブジェクト変数
					$scope.fileUploadCtrl.typeKbn = 0;
					var base64String = "";
					var fileName = "";
					var newFileName = "";
					var strs = new Array();
					var reader = new FileReader();
					function getFullPath(obj) {
						fileName = obj.files[0].name;
						reader.onload = function(e) {
							base64String = e.target.result;
							strs = base64String.split(",");
							base64String = strs[1];
							
						}
						if(obj.files[0] != null && obj.files[0].size > 0) {
							reader.readAsDataURL(obj.files[0]);
							if (obj) {
								newFileName = obj.files[0].name;
								document.getElementById("file").value = "";
								return newFileName ;
							}
							}else{
								newFileName = name.value;
								document.getElementById("file").value = "";
								return newFileName ;
							}
					}
					$scope.findFile = function() {
						var fileNameJP = document.getElementById("selectName").value
						
						if (fileNameJP== null || fileNameJP == "") {
							swal("更新ファイルを選択してください。", "", "warning");
							return;
						}
						if ($("#dtPopup2").val()== null || $("#dtPopup2").val() == "") {
							swal("希望利用日を選択してください。", "", "warning");
							return;
						}
						loadData();
					}
					$scope.upload = function() {
						var fileNameJP = document.getElementById("selectName").value
					
						if (fileNameJP== null || fileNameJP == "") {
							swal("更新ファイルを選択してください。", "", "warning");
							return;
						}
						if (name.value == null || name.value == "") {
							swal("アップロードファイルを選択してください。", "", "warning");
							return;
						}
						if ($("#dtPopup2").val()== null || $("#dtPopup2").val() == "") {
							swal("希望利用日を選択してください。", "", "warning");
							return;
						}
						var url = "./../file/upload";
						var receiptDate = typeof ($("#dtPopup2")
								.val()) == "undefined" ? "" : $(
								"#dtPopup2").val()
						var params = {
							'fileCode' : base64String,
							'fileName' : fileName,
							'fileNameJP' :fileNameJP,
							'iosORandroid' : $scope.fileUploadCtrl.typeKbn,
							"hopingUseDate" : dateFilter(receiptDate,
							"yyyy/MM/dd")
						};
						var data = fileUploadService
								.fileUpload(url, params);
						data
								.then(function(r) {if (r.data.resultStatus == "NG") {
									deferred.resolve("resolve");
									var codeList = strSpliceService
											.resultCodeSplice(
													r.data.messages, "code");
									swal(
											insightService
													.getMessageData("applyStatusMessage")[codeList[0]],
											"", "error");
								} else {
									if(r.data.resultData==null){
										swal("ファイルをアップロードしました。", "", "success");
										$scope.fileUploadCtrl.windowHide = false;
										loadData();
									}else{
										swal("正しい希望利用日時を入力して下さい。", "", "warning");
										$scope.fileUploadCtrl.windowHide = true;
									}
								}});
					}
					$scope.fileUploadCtrl.table.download = function(item) {
						var deferred = $q.defer();
						var url = "./../file/download";
						var params = {
							'_id' : item._id
						};
						var data = fileUploadService.fileUpload(url, params);
						data
								.then(
										function(r) {
											if (r.data.resultStatus == "NG") {
												deferred.resolve("resolve");
												var codeList = strSpliceService
														.resultCodeSplice(
																r.data.messages,
																"code");
												swal(
														insightService
																.getMessageData("applyStatusMessage")[codeList[0]],
														"", "error");
											} else {
												// CSV出力データ取得途中、エラーなし場合、CSVをダウン‐ロードする
												var url = "./../file/HtmlPdfFileDownLoad";
												var input1 = $("<input>");
												var input2=$("<input>");
												var form = $("<form>");
												form.attr('style',
														'display:none');
												form.attr('target', '');
												form.attr('method', 'get');
												form.attr('action', url);
												input1.attr("type", "hidden");
												input1.attr("name", "name");
												input1.attr("value",
														item._id);
												input2.attr("type", "hidden");
												input2.attr("name", "date");
												input2.attr("value",
														r.data.resultData.date);
												$('body').append(form);
												form.append(input1);
												form.append(input2);
												form.submit();
												form.remove();

												// 画面データを再取得し、再表示する
												// 画面で、CSV出力ボタンの制御解除する

												loadData(deferred);
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 一括削除ボタンを押下時、
					$scope.fileUploadCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach($scope.fileUploadCtrl.table.sortAndFilter
								.settings().dataset, function(item) {
							if (item.select) {
								flg = "1";
							}
						});
						if (flg == "0") {
							swal("データを選択してください。", "", "error");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "選択したデータを削除しますか？",
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
											var url = "./../file/fileDeleteButton";
											var data = $scope.fileUploadCtrl.table.sortAndFilter
													.settings().dataset;
											var data = fileUploadService
											.fileUpload(url, {
														'fileFindListReqVo' : data
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
																	deferred
																	.resolve("resolve");

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
					
					// 初期化データを取得
					function loadData() {
						$scope.fileUploadCtrl.typeKBnName = "";
						$scope.fileUploadCtrl.fileNameJP = "";
						$scope.fileUploadCtrl.fileNameEN =  "";
						$scope.fileUploadCtrl.path ="";
						var fileNameJP = document.getElementById("selectName");
						document.getElementById("file").value = "";
						document.getElementById("name").value = "";
						$scope.fileUploadCtrl.windowHide = true;
						// 一覧初期データ取得
						var url = "./../file/findFileList";
						var receiptDate = typeof ($("#dtPopup2")
								.val()) == "undefined" ? "" : $(
								"#dtPopup2").val()
						var params = {
								'fileNameJP' : fileNameJP.options[fileNameJP.selectedIndex].text,
								'iosORandroid' : $scope.fileUploadCtrl.typeKbn,
								"hopingUseDate" : dateFilter(receiptDate,
								"yyyy/MM/dd")
							};
						var data = fileUploadService
						.fileUpload(url, params);
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
									} else {
										$scope.fileUploadCtrl.windowHide = false;
										// データ取得途中、エラーなし場合、画面データを再表示する
										// 帳票出力ボタン／CSV出力ボタン／完了消込ボタンの制御解除する
//										$scope.accountAppListCtrl.table.sortAndFilter
//												.settings().dataset = r.data.resultData.fileFindListReqVo;
//										$scope.accountAppListCtrl.table.sortAndFilter
//												.reload();
										var fileNameJP = document.getElementById("selectName")
										if (r.data.resultData.fileFindListReqVo != null 
												&& r.data.resultData.fileFindListReqVo.length > 0) {
											$scope.fileUploadCtrl.table.sortAndFilter
											.settings().dataset = r.data.resultData.fileFindListReqVo;
											$scope.fileUploadCtrl.table.sortAndFilter
											.reload();
											check(r.data.resultData);
											$scope.fileUploadCtrl.typeKBnName = r.data.resultData.fileFindListReqVo[0].iosORandroid;
											$scope.fileUploadCtrl.fileNameJP = fileNameJP.options[fileNameJP.selectedIndex].text;
											$scope.fileUploadCtrl.fileNameEN =  r.data.resultData.fileFindListReqVo[0].fileNameEN;
											$scope.fileUploadCtrl.path =r.data.resultData.fileFindListReqVo[0].path;
										} else {
											$scope.fileUploadCtrl.windowHide = true;
										}

									}
								}, function(e) {
									console.log(e);
								});
					}
				});