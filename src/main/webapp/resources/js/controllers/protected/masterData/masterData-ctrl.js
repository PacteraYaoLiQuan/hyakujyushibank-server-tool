insightApp
		.controller(
				'masterDataCtrl',
				function($scope, insightService, NgTableParams, $q, dateFilter,
						strSpliceService, masterDataService) {
					var file = document.getElementById("file");
					var name = document.getElementById("name");
					file.onchange = function() {
						name.value = getFullPath(this);
					}
					$scope.masterDataCtrl = {};
					$scope.masterDataCtrl.datepicker = {};
					$scope.masterDataCtrl.table = {};
					$scope.masterDataCtrl.windowHide = true;
					$scope.masterDataCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					var dateMin = new Date();
					dateMin.setDate(dateMin.getDate() + 1);
					$('#dtPopup1').datetimepicker({
						format : "YYYY/MM/DD",
						useCurrent : false
					});
					$('#dtPopup2').datetimepicker({
						format : "YYYY/MM/DD",
						useCurrent : false
					});
					$('#dtPopup3').datetimepicker({
						format : "YYYY/MM/DD",
						defaultDate : dateMin,
						minDate : dateMin,
						useCurrent : false
					});
					$scope.masterDataCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					setTimeout(function() {
						$('#batExecuteDatetime').datetimepicker({
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
						$('#hopingUseDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.masterDataCtrl.table.filterList2 = insightService
							.getInsightData("useFlgData");
					$scope.masterDataCtrl.datepicker.format = $scope.masterDataCtrl.datepicker.formats[1];
					$scope.masterDataCtrl.datepicker.open = function($event,
							opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.masterDataCtrl.datepicker[opened] = true;
					};

					$scope.masterDataCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};
					function check(item) {
						angular.forEach(
								$scope.masterDataCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if ("0" == item.useFlag
											&& "―" == item.batExecuteDatetime) {
										item.checkDisable = false;
									} else {
										item.checkDisable = true;
									}

								});
					}
					$scope.masterDataCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};
					$scope.masterDataCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.masterDataCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											if ("0" == item.useFlag
													&& "―" == item.batExecuteDatetime) {
												item.select = $scope.masterDataCtrl.table.checkboxes.checked;
											} else {
												item.checkDisable = true;
											}
										});
						console.log($scope.masterDataCtrl.table.checkboxes);
					}
					if($("#storeATMData").val() == "1"){
						$scope.masterDataCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										uploadDatetime : "desc"
									}
								},
								{
									paginationCustomizeButtons : "masterDataCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}else{
						$scope.masterDataCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										uploadDatetime : "desc"
									}
								},
								{
									paginationCustomizeButtons : "masterDataCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					$scope.masterDataCtrl.storeATMData = $("#storeATMData").val();
					if($scope.masterDataCtrl.storeATMData=="1"){
						$scope.masterDataCtrl.table.uploadDisabled=true;
						$scope.masterDataCtrl.table.findFileDisabled=true;
					}
					// controllerオブジェクト変数
					$scope.masterDataCtrl.typeKbn = 0;
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
						if (obj.files[0] != null && obj.files[0].size > 0) {
							reader.readAsDataURL(obj.files[0]);
							if (obj) {
								newFileName = obj.files[0].name;
								document.getElementById("file").value = "";
								return newFileName;
							}
						} else {
							newFileName = name.value;
							document.getElementById("file").value = "";
							return newFileName;
						}
					}

					$scope.upload = function() {
						if ($("#dtPopup3").val() == null
								|| $("#dtPopup3").val() == "") {
							swal("希望利用日を選択してください。", "", "warning");
							return;
						}
						if (name.value != null && name.value != "") {
							var url = "./../masterData/upload";
							var hopingUseDate = typeof ($("#dtPopup3").val()) == "undefined" ? ""
									: $("#dtPopup3").val();
							var params = {
								'fileStream' : base64String,
								'fileName' : fileName,
								'hopingUseDate' : dateFilter(hopingUseDate,
										"yyyy/MM/dd")
							};
							var data = masterDataService
									.fileUpload(url, params);
							data
									.then(function(r) {
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
											swal("ファイルをアップロードしました。", "",
													"success");
											$scope.masterDataCtrl.windowHide = false;
											loadData();
										}
									});
						} else {
							swal("アップロードファイルを選択してください。", "", "warning");
						}
					}
					$scope.findFile = function() {

						loadData();
					}
					$scope.masterDataCtrl.table.download = function(item) {
						var deferred = $q.defer();
						var url = "./../masterData/download";
						var params = {
							'_id' : item._id
						};
						var data = masterDataService.fileUpload(url, params);
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
												var url = "./../masterData/masterDataDownLoad";
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
					$scope.masterDataCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();
						var flg = "0";

						angular.forEach(
								$scope.masterDataCtrl.table.sortAndFilter
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
											var url = "./../masterData/masterDataDelete";
											var data = $scope.masterDataCtrl.table.sortAndFilter
													.settings().dataset;
											var data = masterDataService
													.fileUpload(
															url,
															{
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
						$scope.masterDataCtrl.fileName = "";
						document.getElementById("name").value = "";
						$scope.masterDataCtrl.windowHide = true;
						// 一覧初期データ取得
						var url = "./../masterData/masterDataList";
						var uploadDatetime = typeof ($("#dtPopup1").val()) == "undefined" ? ""
								: $("#dtPopup1").val();
						var batExecuteDatetime = typeof ($("#dtPopup2").val()) == "undefined" ? ""
								: $("#dtPopup2").val();
						var params = {
							'uploadDatetime' : dateFilter(uploadDatetime,
									"yyyy/MM/dd"),
							'batExecuteDatetime' : dateFilter(
									batExecuteDatetime, "yyyy/MM/dd")
						};
						var data = masterDataService.fileUpload(url, params);
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
												$scope.masterDataCtrl.windowHide = false;
												if (r.data.resultData.fileFindListReqVo != null
														&& r.data.resultData.fileFindListReqVo.length > 0) {
													$scope.masterDataCtrl.table.sortAndFilter
															.settings().dataset = r.data.resultData.fileFindListReqVo;
													$scope.masterDataCtrl.table.sortAndFilter
															.reload();
													check(r.data.resultData);
													if (typeof deferred != "undefined") {
														deferred
																.resolve("resolve");
													}
												} else {
													$scope.masterDataCtrl.windowHide = true;
												}

											}
										}, function(e) {
											console.log(e);
										});
					}
				});