insightApp
		.controller(
				'storeATMListCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, storeATMListService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.storeATMListCtrl = {};
					var bank_cds = $("#bankCode").val();
					// 一覧モジュール変数
					$scope.storeATMListCtrl.table = {};
					$scope.storeATMListCtrl.user = $("#storeATMList").val();
					if ($scope.storeATMListCtrl.user == "1") {
						$scope.storeATMListCtrl.addBtnDisabled = true;
						$scope.storeATMListCtrl.table.deleteBtnDisabled = true;
					}
					$scope.storeATMListCtrl.table.checkboxes = {
						checked : false,
						items : {}
					};

					$scope.storeATMListCtrl.table.checkItemAll = function() {
						angular
								.forEach(
										$scope.storeATMListCtrl.table.sortAndFilter
												.settings().dataset,
										function(item) {
											item.select = $scope.storeATMListCtrl.table.checkboxes.checked;
										});
						console.log($scope.storeATMListCtrl.table.checkboxes);
					}

					$scope.storeATMListCtrl.table.filterList = insightService
							.getInsightData("typeKbnData");
					$scope.storeATMListCtrl.table.typeList = insightService
							.getInsightData("typeData");
					$scope.storeATMListCtrl.table.delFlgList = insightService
							.getInsightData("delFlgData");
					$scope.storeATMListCtrl.table.storeTypeList = insightService
							.getInsightData("storeTypeData");
					$scope.storeATMListCtrl.table.openFlgList = insightService
							.getInsightData("openFlgData");
					if ($("#storeATMList").val() == "1") {
						$scope.storeATMListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										typeKbn : "desc"
									}
								},
								{
									paginationCustomizeButtons : "storeATMListCtrl/table/sortAndFilter/paginationButton.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					} else {
						$scope.storeATMListCtrl.table.sortAndFilter = new NgTableParams(
								{
									page : 1, // show first page
									count : 10, // count per page
									sorting : {
										typeKbn : "desc"
									}
								},
								{
									paginationCustomizeButtons : "storeATMListCtrl/table/sortAndFilter/paginationButtonGroup.html",
									counts : [ 10, 20, 30, 50, 100 ],
									dataset : []
								});
					}
					// 一括削除ボタンを押下時、
					$scope.storeATMListCtrl.table.deleteBtn = function() {
						var deferred = $q.defer();

						var flg = "0";
						var endFlg = "0";

						angular.forEach(
								$scope.storeATMListCtrl.table.sortAndFilter
										.settings().dataset, function(item) {
									if (item.select) {
										flg = "1";
									}
								});
						if (flg == "0") {
							swal("店舗ATMを選択してください。", "", "warning");
							deferred.resolve("resolve");
						} else {
							var params2 = {
								title : "一括削除処理を行う。よろしいでしょうか？",
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
											var url = "./../protected/master/deleteButton";
											var data = $scope.storeATMListCtrl.table.sortAndFilter
													.settings().dataset;
											var data = storeATMListService
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
					$scope.storeATMListCtrl.addBtn = function(size) {
						var modalInstance = $uibModal.open({
							animation : true,
							templateUrl : 'storeDetailPopup.html',
							controller : 'modalInstanceCtrl2',
							backdrop : 'static',
							size : size,
							resolve : {
								key : function() {
									return {
										'modeType' : "1", // 新規登録場合
										'_id' : "",
										'longitude' : "139.73100",
										'latitude' : "35.66572"
									};
								}
							}
						});

						modalInstance.result.then(function(result) {
							if (result.typeKbn != null) {
								loadData();
							} else if (result.longitude != null) {
								loadData();
							}

						}, function(reason) {
							console.log('Modal dismissed at: ' + new Date()
									+ "  ==>  " + reason);
						});
					}

					// 店舗ATM詳細画面を呼び出す
					$scope.storeATMListCtrl.table.openNa = function(size, item) {
						var modalInstance2 = $uibModal
								.open({
									animation : true,
									templateUrl : 'storeDetailPopup.html',
									controller : 'modalInstanceCtrl2',
									backdrop : 'static',
									size : size,
									resolve : {
										key : function() {
											return {
												'modeType' : "2", // 編集場合
												'_id' : item._id,
												'longitude' : item.longitude != "" ? item.longitude
														: "139.73100",
												'latitude' : item.latitude != "" ? item.latitude
														: "35.66572"
											};
										}
									}
								});

						modalInstance2.result
								.then(
										function(result) {
											if (result.address != null) {
												item.address = result.address;
												item.teleNumber = result.teleNumber;
												item.storeATMName = result.storeATMName;
												item.kanaStoreATMName = result.kanaStoreATMName;
												item.windowOpenStartTime = result.windowOpenStartTime;
												item.longitude = result.longitude;
												item.latitude = result.latitude;
											} else {
											}
											loadData();
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
						var data = storeATMListService.setListDelete(url, data);
						// 一覧初期データ取得
						var url = "./../protected/master/storeATMList";

						$scope.storeATMListCtrl.table.checkboxes = {
							checked : false,
							items : {}
						};
						var params = {};
						var data = storeATMListService.getListData(url, params);
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
													if (codeList[0] == "e.storeATMList.1002") {
														$scope.storeATMListCtrl.table.sortAndFilter
																.settings().dataset = r.data.resultData.storeATMList;
														$scope.storeATMListCtrl.table.sortAndFilter
																.reload();
													}
												}
											} else {
												$scope.storeATMListCtrl.table.sortAndFilter
														.settings().dataset = r.data.resultData.storeATMList;
												$scope.storeATMListCtrl.table.sortAndFilter
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
				'modalInstanceCtrl2',
				function($scope, $uibModalInstance, key, storeATMListService,
						insightService, strSpliceService) {
					var bank_cds = $("#bankCode").val();
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = storeATMListService.setListDelete(url, data);
					var type = "", toilet = "";
					$scope.changeStoreType = function() {
						type = document.getElementById("select").value;
						if (type == "1") {
							$scope.modalInstanceCtrl2.windowHide = false;
						} else {
							$scope.modalInstanceCtrl2.windowHide = true;
						}
					}
					var policeCompany = "";
					$scope.changePoliceCompany = function() {
						policeCompany = document.getElementById("select").value;
						if (policeCompany == 1) {
							$scope.modalInstanceCtrl2.policeCompany = "四国警備";
						} else if (policeCompany == 2) {
							$scope.modalInstanceCtrl2.policeCompany = "綜合警備";
						} else if (policeCompany == 3) {
							$scope.modalInstanceCtrl2.policeCompany = "綜合";
						} else if (policeCompany == 4) {
							$scope.modalInstanceCtrl2.policeCompany = "セコム";
						} else if (policeCompany == 5) {
							$scope.modalInstanceCtrl2.policeCompany = "セノン";
						}
					}
					$scope.changeToilet = function() {
						toilet = document.getElementById("selectToilet").value;
					}
					// 開始日時
					setTimeout(function() {
						$('#receiptDate').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					// 終了日時
					setTimeout(function() {
						$('#receiptDate1').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					// 緯度
					$scope.latitude = key.latitude;
					// 経度
					$scope.longitude = key.longitude;
					$scope.modalInstanceCtrl2 = {};
					console.log($("#storeATMList").val());
					if ($("#storeATMList").val() == "1") {
						$scope.modalInstanceCtrl2.UpdbtnDisabled = true;
					}
					$scope.modalInstanceCtrl2.hourList = insightService
							.getInsightData("hourData");
					$scope.modalInstanceCtrl2.minuteList = insightService
							.getInsightData("minuteData");
					$scope.modalInstanceCtrl2.windowHide = false;
					$scope.modalInstanceCtrl2.windowOpenStartTime = "";

					// 開始日時,終了日時
					$scope.modalInstanceCtrl2.datepicker = {}

					$scope.modalInstanceCtrl2.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.modalInstanceCtrl2.datepicker[opened] = true;
					};

					$scope.modalInstanceCtrl2.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					if (key.modeType == "1") {
						$scope.modalInstanceCtrl2.title = "店舗ATM新規画面";
						$scope.modalInstanceCtrl2.button = "登録";
						if (bank_cds == "0169") {
							// 店舗区分
							$scope.modalInstanceCtrl2.typeKbn = 0;
							// 窓口営業時間_平日始業
							$scope.modalInstanceCtrl2.windowStartHour = "";
							$scope.modalInstanceCtrl2.windowStartMinute = "";
							// 窓口営業時間_平日終業
							$scope.modalInstanceCtrl2.windowEndHour = "";
							$scope.modalInstanceCtrl2.windowEndMinute = "";
							// 窓口営業時間_土曜日始業
							$scope.modalInstanceCtrl2.windowStartHour_SAT = "";
							$scope.modalInstanceCtrl2.windowStartMinute_SAT = "";
							// 窓口営業時間_土曜日終業
							$scope.modalInstanceCtrl2.windowEndHour_SAT = "";
							$scope.modalInstanceCtrl2.windowEndMinute_SAT = "";
							// 窓口営業時間_日曜日始業
							$scope.modalInstanceCtrl2.windowStartHour_SUN = "";
							$scope.modalInstanceCtrl2.windowStartMinute_SUN = "";
							// 窓口営業時間_日曜日終業
							$scope.modalInstanceCtrl2.windowEndHour_SUN = "";
							$scope.modalInstanceCtrl2.windowEndMinute_SUN = "";
							// ATM営業時間_平日始業
							$scope.modalInstanceCtrl2.atmStartHour = "";
							$scope.modalInstanceCtrl2.atmStartMinute = "";
							// ATM営業時間_平日終業
							$scope.modalInstanceCtrl2.atmEndHour = "";
							$scope.modalInstanceCtrl2.atmEndMinute = "";
							// ATM営業時間_土曜日始業
							$scope.modalInstanceCtrl2.atmStartHour_SAT = "";
							$scope.modalInstanceCtrl2.atmStartMinute_SAT = "";
							// ATM営業時間_土曜日終業
							$scope.modalInstanceCtrl2.atmEndHour_SAT = "";
							$scope.modalInstanceCtrl2.atmEndMinute_SAT = "";
							// ATM営業時間_日曜日始業
							$scope.modalInstanceCtrl2.atmStartHour_SUN = "";
							$scope.modalInstanceCtrl2.atmStartMinute_SUN = "";
							// ATM営業時間_日曜日終業
							$scope.modalInstanceCtrl2.atmEndHour_SUN = "";
							$scope.modalInstanceCtrl2.atmEndMinute_SUN = "";
							// 駐車場_有無
							$scope.modalInstanceCtrl2.park = "0";
							// 駐車場_障害者対応
							$scope.modalInstanceCtrl2.parkServiceForDisabled = "0";
							// ひろぎんウツミ屋
							$scope.modalInstanceCtrl2.hirginUtsumiya = "0";
							// 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
							$scope.modalInstanceCtrl2.serviceDollarEuro = "0";
							// 商品サービス_外貨両替（ｱｼﾞｱ通貨）
							$scope.modalInstanceCtrl2.serviceAsia = "0";
							// 商品サービス_外貨両替（その他）
							$scope.modalInstanceCtrl2.serviceOther = "0";
							// 商品サービス_外貨買取
							$scope.modalInstanceCtrl2.serviceForeignExchange = "0";
							// 商品サービス_投資信託
							$scope.modalInstanceCtrl2.serviceInvestmentTrust = "0";
							// 商品サービス_年金保険
							$scope.modalInstanceCtrl2.servicePensionInsurance = "0";
							// 商品サービス_金融商品仲介（みずほ証券）
							$scope.modalInstanceCtrl2.serviceMizuho = "0";
							// 商品サービス_金融商品仲介（ひろぎんウツミ屋）
							$scope.modalInstanceCtrl2.serviceHirginUtsumiya = "0";
							// 商品サービス_全自動貸金庫
							$scope.modalInstanceCtrl2.serviceAutoSafeDepositBox = "0";
							// 商品サービス_一般貸金庫
							$scope.modalInstanceCtrl2.serviceSafeDepositBox = "0";
							// 商品サービス_ｾｰﾌﾃｨｹｰｽ
							$scope.modalInstanceCtrl2.serviceSafeBox = "0";
							// 店舗設備_IB専用PC
							$scope.modalInstanceCtrl2.internationalTradePC = "0";
							// 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
							$scope.modalInstanceCtrl2.childrenSpace = "0";
							// バリアフリー_視覚障害対応ATM
							$scope.modalInstanceCtrl2.barrierFreeVisualImpairment = "0";
							// バリアフリー_点字ﾌﾞﾛｯｸ
							$scope.modalInstanceCtrl2.barrierFreeBrailleBlock = "0";
							// バリアフリー_音声ｶﾞｲﾄﾞ
							$scope.modalInstanceCtrl2.barrierFreeVoiceGuide = "0";
							// バリアフリー_AED
							$scope.modalInstanceCtrl2.barrierFreeAED = "0";
							// ATM機能_振込
							$scope.modalInstanceCtrl2.atmFunctionTransfer = "0";
							// ATM機能_硬貨入出金
							$scope.modalInstanceCtrl2.atmFunctionCoinAccess = "0";
							// ATM機能_宝くじｻｰﾋﾞｽ
							$scope.modalInstanceCtrl2.atmFunctionLotteryService = "0";
							// ATM機能_手のひら認証
							$scope.modalInstanceCtrl2.atmFunctionPalmAuthentication = "0";
							// ATM機能_IC対応
							$scope.modalInstanceCtrl2.atmFunctionIC = "0";
							// ATM機能_PASPYチャージ
							$scope.modalInstanceCtrl2.atmFunctionPASPY = "0";
							// ATM機能_他行幹事
							$scope.modalInstanceCtrl2.atmFunctionOtherBankingAffairs = "0";
						} else if (bank_cds == "0174") {
							// 窓口営業時間_平日始業
							$scope.modalInstanceCtrl2.windowOpenStartHour = "";
							$scope.modalInstanceCtrl2.windowOpenStartMinute = "";
							// 窓口営業時間_平日終業
							$scope.modalInstanceCtrl2.windowOpenEndHour = "";
							$scope.modalInstanceCtrl2.windowOpenEndMinute = "";
							// 窓口営業時間_土曜日始業
							$scope.modalInstanceCtrl2.windowOpenStartHour_SAT = "";
							$scope.modalInstanceCtrl2.windowOpenStartMinute_SAT = "";
							// 窓口営業時間_土曜日終業
							$scope.modalInstanceCtrl2.windowOpenEndHour_SAT = "";
							$scope.modalInstanceCtrl2.windowOpenEndMinute_SAT = "";
							// 窓口営業時間_日曜日始業
							$scope.modalInstanceCtrl2.windowOpenStartHour_SUN = "";
							$scope.modalInstanceCtrl2.windowOpenStartMinute_SUN = "";
							// 窓口営業時間_日曜日終業
							$scope.modalInstanceCtrl2.windowOpenEndHour_SUN = "";
							$scope.modalInstanceCtrl2.windowOpenEndMinute_SUN = "";
							// ATM営業時間_平日始業
							$scope.modalInstanceCtrl2.atmOpenStartHour = "";
							$scope.modalInstanceCtrl2.atmOpenStartMinute = "";
							// ATM営業時間_平日終業
							$scope.modalInstanceCtrl2.atmOpenEndHour = "";
							$scope.modalInstanceCtrl2.atmOpenEndMinute = "";
							// ATM営業時間_土曜日始業
							$scope.modalInstanceCtrl2.atmOpenStartHour_SAT = "";
							$scope.modalInstanceCtrl2.atmOpenStartMinute_SAT = "";
							// ATM営業時間_土曜日終業
							$scope.modalInstanceCtrl2.atmOpenEndHour_SAT = "";
							$scope.modalInstanceCtrl2.atmOpenEndMinute_SAT = "";
							// ATM営業時間_日曜日始業
							$scope.modalInstanceCtrl2.atmOpenStartHour_SUN = "";
							$scope.modalInstanceCtrl2.atmOpenStartMinute_SUN = "";
							// ATM営業時間_日曜日終業
							$scope.modalInstanceCtrl2.atmOpenEndHour_SUN = "";
							$scope.modalInstanceCtrl2.atmOpenEndMinute_SUN = "";

							$scope.modalInstanceCtrl2.loanMachineStartHour_holiday = ""
							$scope.modalInstanceCtrl2.loanMachineStartMinute_holiday = ""
							$scope.modalInstanceCtrl2.loanMachineEndHour_holiday = ""
							$scope.modalInstanceCtrl2.loanMachineEndMinute_holiday = ""

							$scope.modalInstanceCtrl2.loanMachineStartHour = ""
							$scope.modalInstanceCtrl2.loanMachineStartMinute = ""
							$scope.modalInstanceCtrl2.loanMachineEndHour = ""
							$scope.modalInstanceCtrl2.loanMachineEndMinute = ""

							$scope.modalInstanceCtrl2.autoLoanMachineStartHour_holiday = ""
							$scope.modalInstanceCtrl2.autoLoanMachineStartMinute_holiday = ""
							$scope.modalInstanceCtrl2.autoLoanMachineEndHour_holiday = ""
							$scope.modalInstanceCtrl2.autoLoanMachineEndMinute_holiday = ""

							$scope.modalInstanceCtrl2.autoLoanMachineStartHour = ""
							$scope.modalInstanceCtrl2.autoLoanMachineStartMinute = ""
							$scope.modalInstanceCtrl2.autoLoanMachineEndHour = ""
							$scope.modalInstanceCtrl2.autoLoanMachineEndMinute = ""

							$scope.modalInstanceCtrl2.conversionMachineStartHour_holiday = ""
							$scope.modalInstanceCtrl2.conversionMachineStartMinute_holiday = ""
							$scope.modalInstanceCtrl2.conversionMachineEndHour_holiday = ""
							$scope.modalInstanceCtrl2.conversionMachineEndMinute_holiday = ""

							$scope.modalInstanceCtrl2.conversionMachineStartHour = ""
							$scope.modalInstanceCtrl2.conversionMachineStartMinute = ""
							$scope.modalInstanceCtrl2.conversionMachineEndHour = ""
							$scope.modalInstanceCtrl2.conversionMachineEndMinute = ""

							$scope.modalInstanceCtrl2.poiStatus = true;
							$scope.modalInstanceCtrl2.windowHide = true;
						} else if (bank_cds == "0173") {
							$scope.modalInstanceCtrl2.type = 0;
							$scope.modalInstanceCtrl2.area = "";
							$scope.modalInstanceCtrl2.storeNumber = "";
							$scope.modalInstanceCtrl2.storeName = "";
							$scope.modalInstanceCtrl2.atmAddress = "";
							$scope.modalInstanceCtrl2.manageStore = "";
							$scope.modalInstanceCtrl2.postCode = "";
							$scope.modalInstanceCtrl2.address = "";
							$scope.modalInstanceCtrl2.teleNumber = "";
							$scope.modalInstanceCtrl2.serviceConversionIn = "";
							$scope.modalInstanceCtrl2.serviceConversionOut = "";
							$scope.modalInstanceCtrl2.trustAgent = "";
							$scope.modalInstanceCtrl2.policeCompany = "";
							$scope.modalInstanceCtrl2.conversionMachine = "";
							$scope.modalInstanceCtrl2.accountMachine = "";
							$scope.modalInstanceCtrl2.loanMachine = "";
							$scope.modalInstanceCtrl2.storeOpenStartHour = "";
							$scope.modalInstanceCtrl2.storeOpenEndHour = "";
							$scope.modalInstanceCtrl2.storeOpenStartHour_SAT = "";
							$scope.modalInstanceCtrl2.storeOpenEndHour_SAT = "";
							$scope.modalInstanceCtrl2.storeOpenStartHour_SUN = "";
							$scope.modalInstanceCtrl2.storeOpenEndHour_SUN = "";
							$scope.modalInstanceCtrl2.atmOpenStartHour = "";
							$scope.modalInstanceCtrl2.atmOpenEndHour = "";
							$scope.modalInstanceCtrl2.atmOpenStartHour_SAT = "";
							$scope.modalInstanceCtrl2.atmOpenEndHour_SAT = "";
							$scope.modalInstanceCtrl2.atmOpenStartHour_SUN = "";
							$scope.modalInstanceCtrl2.atmOpenEndHour_SUN = "";
							$scope.modalInstanceCtrl2.storeOpenStartMinute = "";
							$scope.modalInstanceCtrl2.storeOpenEndMinute = "";
							$scope.modalInstanceCtrl2.storeOpenStartMinute_SAT = "";
							$scope.modalInstanceCtrl2.storeOpenEndMinute_SAT = "";
							$scope.modalInstanceCtrl2.storeOpenStartMinute_SUN = "";
							$scope.modalInstanceCtrl2.storeOpenEndMinute_SUN = "";
							$scope.modalInstanceCtrl2.atmOpenStartMinute = "";
							$scope.modalInstanceCtrl2.atmOpenEndMinute = "";
							$scope.modalInstanceCtrl2.atmOpenStartMinute_SAT = "";
							$scope.modalInstanceCtrl2.atmOpenEndMinute_SAT = "";
							$scope.modalInstanceCtrl2.atmOpenStartMinute_SUN = "";
							$scope.modalInstanceCtrl2.atmOpenEndMinute_SUN = "";
							$scope.modalInstanceCtrl2.latitude = "";
							$scope.modalInstanceCtrl2.longitude = "";
							$scope.modalInstanceCtrl2.delFlg = "";
							$scope.modalInstanceCtrl2.storeNameHide = false;
							$scope.modalInstanceCtrl2.teleNumberHide = false;
							$scope.modalInstanceCtrl2.conversionHide = false;
							$scope.modalInstanceCtrl2.storeHide = false;
							$scope.modalInstanceCtrl2.atmAddressHide = true;
							$scope.modalInstanceCtrl2.manageStoreHide = true;
							$scope.modalInstanceCtrl2.atmHide = true;
						}

					} else {
						$scope.modalInstanceCtrl2.title = "店舗ATM編集画面";
						$scope.modalInstanceCtrl2.button = "更新";
						$scope.modalInstanceCtrl2.typeKbnDisabled = true;
						$scope.modalInstanceCtrl2.typeDisabled = true;
						$scope.modalInstanceCtrl2.storeNumberDisabled = true;
						$scope.modalInstanceCtrl2.subStoreNumberDisabled = true;
						$scope.modalInstanceCtrl2.atmStoreNumberDisabled = true;
						// $scope.modalInstanceCtrl2.storeATMNameDisabled =
						// true;
						// $scope.modalInstanceCtrl2.kanaStoreATMNameDisabled =
						// true;
						var url = "./../protected/master/storeATMDetail";
						var params = {
							"modeType" : key.modeType,
							"_id" : key._id
						};
						var data = storeATMListService.getDetailData(url,
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
													showCancelButton : false,
													closeOnConfirm : true
												};
												swal(params);
												$uibModalInstance.close({
													'load' : true
												});
											} else {
												if (bank_cds == "0169") {
													// 店舗区分
													$scope.modalInstanceCtrl2.typeKbn = r.data.resultData.typeKbn;
													if ($scope.modalInstanceCtrl2.typeKbn != 0) {
														$scope.modalInstanceCtrl2.windowHide = true;
													}
													// 店舗コード_母店番号
													$scope.modalInstanceCtrl2.storeNumber = r.data.resultData.storeNumber;
													// 店舗コード_出張所枝番
													$scope.modalInstanceCtrl2.subStoreNumber = r.data.resultData.subStoreNumber;
													// 店舗コード_ATM枝番
													$scope.modalInstanceCtrl2.atmStoreNumber = r.data.resultData.atmStoreNumber;
													// 店舗名_（漢字）
													$scope.modalInstanceCtrl2.storeATMName = r.data.resultData.storeATMName;
													// 店舗名_（カナ）
													$scope.modalInstanceCtrl2.kanaStoreATMName = r.data.resultData.kanaStoreATMName;
													// 所在地_郵便番号
													$scope.modalInstanceCtrl2.postCode = r.data.resultData.postCode;
													// 所在地_住所
													$scope.modalInstanceCtrl2.address = r.data.resultData.address;
													// 所在地_ランドマーク
													$scope.modalInstanceCtrl2.landMark = r.data.resultData.landMark;
													// 電話番号
													$scope.modalInstanceCtrl2.teleNumber = r.data.resultData.teleNumber;
													// 窓口営業時間_平日始業
													$scope.modalInstanceCtrl2.windowStartHour = r.data.resultData.windowStartHour;
													$scope.modalInstanceCtrl2.windowStartMinute = r.data.resultData.windowStartMinute;
													// 窓口営業時間_平日終業
													$scope.modalInstanceCtrl2.windowEndHour = r.data.resultData.windowEndHour;
													$scope.modalInstanceCtrl2.windowEndMinute = r.data.resultData.windowEndMinute;
													// 窓口営業時間_土曜日始業
													$scope.modalInstanceCtrl2.windowStartHour_SAT = r.data.resultData.windowStartHour_SAT;
													$scope.modalInstanceCtrl2.windowStartMinute_SAT = r.data.resultData.windowStartMinute_SAT;
													// 窓口営業時間_土曜日終業
													$scope.modalInstanceCtrl2.windowEndHour_SAT = r.data.resultData.windowEndHour_SAT;
													$scope.modalInstanceCtrl2.windowEndMinute_SAT = r.data.resultData.windowEndMinute_SAT;
													// 窓口営業時間_日曜日始業
													$scope.modalInstanceCtrl2.windowStartHour_SUN = r.data.resultData.windowStartHour_SUN;
													$scope.modalInstanceCtrl2.windowStartMinute_SUN = r.data.resultData.windowStartMinute_SUN;
													// 窓口営業時間_日曜日終業
													$scope.modalInstanceCtrl2.windowEndHour_SUN = r.data.resultData.windowEndHour_SUN;
													$scope.modalInstanceCtrl2.windowEndMinute_SUN = r.data.resultData.windowEndMinute_SUN;
													// ATM営業時間_平日始業
													$scope.modalInstanceCtrl2.atmStartHour = r.data.resultData.atmStartHour;
													$scope.modalInstanceCtrl2.atmStartMinute = r.data.resultData.atmStartMinute;
													// ATM営業時間_平日終業
													$scope.modalInstanceCtrl2.atmEndHour = r.data.resultData.atmEndHour;
													$scope.modalInstanceCtrl2.atmEndMinute = r.data.resultData.atmEndMinute;
													// ATM営業時間_土曜日始業
													$scope.modalInstanceCtrl2.atmStartHour_SAT = r.data.resultData.atmStartHour_SAT;
													$scope.modalInstanceCtrl2.atmStartMinute_SAT = r.data.resultData.atmStartMinute_SAT;
													// ATM営業時間_土曜日終業
													$scope.modalInstanceCtrl2.atmEndHour_SAT = r.data.resultData.atmEndHour_SAT;
													$scope.modalInstanceCtrl2.atmEndMinute_SAT = r.data.resultData.atmEndMinute_SAT;
													// ATM営業時間_日曜日始業
													$scope.modalInstanceCtrl2.atmStartHour_SUN = r.data.resultData.atmStartHour_SUN;
													$scope.modalInstanceCtrl2.atmStartMinute_SUN = r.data.resultData.atmStartMinute_SUN;
													// ATM営業時間_日曜日終業
													$scope.modalInstanceCtrl2.atmEndHour_SUN = r.data.resultData.atmEndHour_SUN;
													$scope.modalInstanceCtrl2.atmEndMinute_SUN = r.data.resultData.atmEndMinute_SUN;
													// AＴＭ設置台数
													$scope.modalInstanceCtrl2.atmCount = r.data.resultData.atmCount;
													// 駐車場_有無
													$scope.modalInstanceCtrl2.park = r.data.resultData.park;
													// 駐車場_障害者対応
													$scope.modalInstanceCtrl2.parkServiceForDisabled = r.data.resultData.parkServiceForDisabled;
													// 駐車場_備考
													$scope.modalInstanceCtrl2.parkComment = r.data.resultData.parkComment;
													// ひろぎんウツミ屋
													$scope.modalInstanceCtrl2.hirginUtsumiya = r.data.resultData.hirginUtsumiya;
													// 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
													$scope.modalInstanceCtrl2.serviceDollarEuro = r.data.resultData.serviceDollarEuro;
													// 商品サービス_外貨両替（ｱｼﾞｱ通貨）
													$scope.modalInstanceCtrl2.serviceAsia = r.data.resultData.serviceAsia;
													// 商品サービス_外貨両替（その他）
													$scope.modalInstanceCtrl2.serviceOther = r.data.resultData.serviceOther;
													// 商品サービス_外貨買取
													$scope.modalInstanceCtrl2.serviceForeignExchange = r.data.resultData.serviceForeignExchange;
													// 商品サービス_投資信託
													$scope.modalInstanceCtrl2.serviceInvestmentTrust = r.data.resultData.serviceInvestmentTrust;
													// 商品サービス_年金保険
													$scope.modalInstanceCtrl2.servicePensionInsurance = r.data.resultData.servicePensionInsurance;
													// 商品サービス_金融商品仲介（みずほ証券）
													$scope.modalInstanceCtrl2.serviceMizuho = r.data.resultData.serviceMizuho;
													// 商品サービス_金融商品仲介（ひろぎんウツミ屋）
													$scope.modalInstanceCtrl2.serviceHirginUtsumiya = r.data.resultData.serviceHirginUtsumiya;
													// 商品サービス_全自動貸金庫
													$scope.modalInstanceCtrl2.serviceAutoSafeDepositBox = r.data.resultData.serviceAutoSafeDepositBox;
													// 商品サービス_一般貸金庫
													$scope.modalInstanceCtrl2.serviceSafeDepositBox = r.data.resultData.serviceSafeDepositBox;
													// 商品サービス_ｾｰﾌﾃｨｹｰｽ
													$scope.modalInstanceCtrl2.serviceSafeBox = r.data.resultData.serviceSafeBox;
													// 店舗設備_IB専用PC
													$scope.modalInstanceCtrl2.internationalTradePC = r.data.resultData.internationalTradePC;
													// 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
													$scope.modalInstanceCtrl2.childrenSpace = r.data.resultData.childrenSpace;
													// バリアフリー_視覚障害対応ATM
													$scope.modalInstanceCtrl2.barrierFreeVisualImpairment = r.data.resultData.barrierFreeVisualImpairment;
													// バリアフリー_点字ﾌﾞﾛｯｸ
													$scope.modalInstanceCtrl2.barrierFreeBrailleBlock = r.data.resultData.barrierFreeBrailleBlock;
													// バリアフリー_音声ｶﾞｲﾄﾞ
													$scope.modalInstanceCtrl2.barrierFreeVoiceGuide = r.data.resultData.barrierFreeVoiceGuide;
													// バリアフリー_AED
													$scope.modalInstanceCtrl2.barrierFreeAED = r.data.resultData.barrierFreeAED;
													// ATM機能_振込
													$scope.modalInstanceCtrl2.atmFunctionTransfer = r.data.resultData.atmFunctionTransfer;
													// ATM機能_硬貨入出金
													$scope.modalInstanceCtrl2.atmFunctionCoinAccess = r.data.resultData.atmFunctionCoinAccess;
													// ATM機能_宝くじｻｰﾋﾞｽ
													$scope.modalInstanceCtrl2.atmFunctionLotteryService = r.data.resultData.atmFunctionLotteryService;
													// ATM機能_手のひら認証
													$scope.modalInstanceCtrl2.atmFunctionPalmAuthentication = r.data.resultData.atmFunctionPalmAuthentication;
													// ATM機能_IC対応
													$scope.modalInstanceCtrl2.atmFunctionIC = r.data.resultData.atmFunctionIC;
													// ATM機能_PASPYチャージ
													$scope.modalInstanceCtrl2.atmFunctionPASPY = r.data.resultData.atmFunctionPASPY;
													// ATM機能_他行幹事
													$scope.modalInstanceCtrl2.atmFunctionOtherBankingAffairs = r.data.resultData.atmFunctionOtherBankingAffairs;
													// 座標_経度
													$scope.modalInstanceCtrl2.longitude = r.data.resultData.longitude;
													// 座標_緯度
													$scope.modalInstanceCtrl2.latitude = r.data.resultData.latitude;
													// 備考①
													$scope.modalInstanceCtrl2.comment1 = r.data.resultData.comment1;
													// 備考②
													$scope.modalInstanceCtrl2.comment2 = r.data.resultData.comment2;
													// 備考③
													$scope.modalInstanceCtrl2.comment3 = r.data.resultData.comment3;
													// 備考④
													$scope.modalInstanceCtrl2.comment4 = r.data.resultData.comment4;
													// 備考⑤
													$scope.modalInstanceCtrl2.comment5 = r.data.resultData.comment5;
													// 開始日時
													$scope.modalInstanceCtrl2.startDateTime = r.data.resultData.startDateTime;
													// 終了日時
													$scope.modalInstanceCtrl2.endDateTime = r.data.resultData.endDateTime;

													if (r.data.resultData.delFlg == "0") {
														$scope.modalInstanceCtrl2.delFlg = false;
													} else {
														$scope.modalInstanceCtrl2.delFlg = true;
													}
													// $scope.modalInstanceCtrl2.storeATMNameDisabled
													// = true;
													if (r.data.resultData.workDayFlag == "0") {
														$scope.modalInstanceCtrl2.workDay = false;
													} else {
														$scope.modalInstanceCtrl2.workDay = true;
													}
													if (r.data.resultData.saturdayFlag == "0") {
														$scope.modalInstanceCtrl2.Saturday = false;
													} else {
														$scope.modalInstanceCtrl2.Saturday = true;
													}
													if (r.data.resultData.sundayFlag == "0") {
														$scope.modalInstanceCtrl2.Sunday = false;
													} else {
														$scope.modalInstanceCtrl2.Sunday = true;
													}
												} else if (bank_cds == "0174") {
													// 店舗コード_母店番号
													$scope.modalInstanceCtrl2.storeNumber = r.data.resultData.storeNumber;
													// 店舗名_（漢字）
													$scope.modalInstanceCtrl2.storeATMName = r.data.resultData.storeATMName;
													// poi
													if (r.data.resultData.poiStatus == "0") {
														$scope.modalInstanceCtrl2.poiStatus = false;
													} else {
														$scope.modalInstanceCtrl2.poiStatus = true;
													}

													// 座標_経度
													$scope.modalInstanceCtrl2.longitude = r.data.resultData.longitude;
													// 座標_緯度
													$scope.modalInstanceCtrl2.latitude = r.data.resultData.latitude;
													// 所在地_住所
													$scope.modalInstanceCtrl2.address = r.data.resultData.address;
													// 廃止店番
													$scope.modalInstanceCtrl2.delStoreNumber = r.data.resultData.delStoreNumber;
													// 店舗区分
													$scope.modalInstanceCtrl2.typeKbn = r.data.resultData.typeKbn
															.toString();
													type = r.data.resultData.typeKbn
															.toString();
													// 店舗名_（カナ）
													$scope.modalInstanceCtrl2.kanaStoreATMName = r.data.resultData.kanaStoreATMName;
													// 所在地_郵便番号
													$scope.modalInstanceCtrl2.postCode = r.data.resultData.postCode;
													// 電話番号
													$scope.modalInstanceCtrl2.teleNumber = r.data.resultData.teleNumber;
													// ATM営業時間_平日始業
													$scope.modalInstanceCtrl2.atmOpenStartHour = r.data.resultData.atmOpenStartHour;
													$scope.modalInstanceCtrl2.atmOpenStartMinute = r.data.resultData.atmOpenStartMinute;
													// ATM営業時間_平日終業
													$scope.modalInstanceCtrl2.atmOpenEndHour = r.data.resultData.atmOpenEndHour;
													$scope.modalInstanceCtrl2.atmOpenEndMinute = r.data.resultData.atmOpenEndMinute;
													// ATM営業時間_土曜日始業
													$scope.modalInstanceCtrl2.atmOpenStartHour_SAT = r.data.resultData.atmOpenStartHour_SAT;
													$scope.modalInstanceCtrl2.atmOpenStartMinute_SAT = r.data.resultData.atmOpenStartMinute_SAT;
													// ATM営業時間_土曜日終業
													$scope.modalInstanceCtrl2.atmOpenEndHour_SAT = r.data.resultData.atmOpenEndHour_SAT;
													$scope.modalInstanceCtrl2.atmOpenEndMinute_SAT = r.data.resultData.atmOpenEndMinute_SAT;
													// ATM営業時間_日曜日始業
													$scope.modalInstanceCtrl2.atmOpenStartHour_SUN = r.data.resultData.atmOpenStartHour_SUN;
													$scope.modalInstanceCtrl2.atmOpenStartMinute_SUN = r.data.resultData.atmOpenStartMinute_SUN;
													// ATM営業時間_日曜日終業
													$scope.modalInstanceCtrl2.atmOpenEndHour_SUN = r.data.resultData.atmOpenEndHour_SUN;
													$scope.modalInstanceCtrl2.atmOpenEndMinute_SUN = r.data.resultData.atmOpenEndMinute_SUN;
													$scope.modalInstanceCtrl2.atmComment1 = r.data.resultData.atmComment1;
													$scope.modalInstanceCtrl2.atmComment2 = r.data.resultData.atmComment2;
													// 窓口営業時間_平日始業
													$scope.modalInstanceCtrl2.windowOpenStartHour = r.data.resultData.windowOpenStartHour;
													$scope.modalInstanceCtrl2.windowOpenStartMinute = r.data.resultData.windowOpenStartMinute;
													// 窓口営業時間_平日終業 =
													// r.data.resultData.
													$scope.modalInstanceCtrl2.windowOpenEndHour = r.data.resultData.windowOpenEndHour;
													$scope.modalInstanceCtrl2.windowOpenEndMinute = r.data.resultData.windowOpenEndMinute;
													// 窓口営業時間_土曜日始業
													$scope.modalInstanceCtrl2.windowOpenStartHour_SAT = r.data.resultData.windowOpenStartHour_SAT;
													$scope.modalInstanceCtrl2.windowOpenStartMinute_SAT = r.data.resultData.windowOpenStartMinute_SAT;
													// 窓口営業時間_土曜日終業
													$scope.modalInstanceCtrl2.windowOpenEndHour_SAT = r.data.resultData.windowOpenEndHour_SAT;
													$scope.modalInstanceCtrl2.windowOpenEndMinute_SAT = r.data.resultData.windowOpenEndMinute_SAT;
													// 窓口営業時間_日曜日始業
													$scope.modalInstanceCtrl2.windowOpenStartHour_SUN = r.data.resultData.windowOpenStartHour_SUN;
													$scope.modalInstanceCtrl2.windowOpenStartMinute_SUN = r.data.resultData.windowOpenStartMinute_SUN;
													// 窓口営業時間_日曜日終業
													$scope.modalInstanceCtrl2.windowOpenEndHour_SUN = r.data.resultData.windowOpenEndHour_SUN;
													$scope.modalInstanceCtrl2.windowOpenEndMinute_SUN = r.data.resultData.windowOpenEndMinute_SUN;
													$scope.modalInstanceCtrl2.conversionMachineStartHour = r.data.resultData.conversionMachineStartHour;
													$scope.modalInstanceCtrl2.conversionMachineStartMinute = r.data.resultData.conversionMachineStartMinute;
													$scope.modalInstanceCtrl2.conversionMachineEndHour = r.data.resultData.conversionMachineEndHour;
													$scope.modalInstanceCtrl2.conversionMachineEndMinute = r.data.resultData.conversionMachineEndMinute;
													$scope.modalInstanceCtrl2.conversionMachineStartHour_holiday = r.data.resultData.conversionMachineStartHour_holiday;
													$scope.modalInstanceCtrl2.conversionMachineStartMinute_holiday = r.data.resultData.conversionMachineStartMinute_holiday;
													$scope.modalInstanceCtrl2.conversionMachineEndHour_holiday = r.data.resultData.conversionMachineEndHour_holiday;
													$scope.modalInstanceCtrl2.conversionMachineEndMinute_holiday = r.data.resultData.conversionMachineEndMinute_holiday;

													if (r.data.resultData.accountMachineStartTime == "") {
														$scope.modalInstanceCtrl2.accountMachineStartTime = false;
													} else {
														$scope.modalInstanceCtrl2.accountMachineStartTime = true;
													}

													if (r.data.resultData.accountMachineStartTime_SAT == "") {
														$scope.modalInstanceCtrl2.accountMachineStartTime_SAT = false;
													} else {
														$scope.modalInstanceCtrl2.accountMachineStartTime_SAT = true;
													}

													if (r.data.resultData.accountMachineStartTime_SUN == "") {
														$scope.modalInstanceCtrl2.accountMachineStartTime_SUN = false;
													} else {
														$scope.modalInstanceCtrl2.accountMachineStartTime_SUN = true;
													}
													$scope.modalInstanceCtrl2.autoLoanMachineStartHour = r.data.resultData.autoLoanMachineStartHour;
													$scope.modalInstanceCtrl2.autoLoanMachineStartMinute = r.data.resultData.autoLoanMachineStartMinute;
													$scope.modalInstanceCtrl2.autoLoanMachineEndHour = r.data.resultData.autoLoanMachineEndHour;
													$scope.modalInstanceCtrl2.autoLoanMachineEndMinute = r.data.resultData.autoLoanMachineEndMinute;
													$scope.modalInstanceCtrl2.autoLoanMachineStartHour_holiday = r.data.resultData.autoLoanMachineStartHour_holiday;
													$scope.modalInstanceCtrl2.autoLoanMachineStartMinute_holiday = r.data.resultData.autoLoanMachineStartMinute_holiday;
													$scope.modalInstanceCtrl2.autoLoanMachineEndHour_holiday = r.data.resultData.autoLoanMachineEndHour_holiday;
													$scope.modalInstanceCtrl2.autoLoanMachineEndMinute_holiday = r.data.resultData.autoLoanMachineEndMinute_holiday;
													if (r.data.resultData.autoLoanMachineFlag == "") {
														$scope.modalInstanceCtrl2.autoLoanMachineFlag = false;
													} else {
														$scope.modalInstanceCtrl2.autoLoanMachineFlag = true;
													}
													$scope.modalInstanceCtrl2.loanMachineStartHour = r.data.resultData.loanMachineStartHour;
													$scope.modalInstanceCtrl2.loanMachineStartMinute = r.data.resultData.loanMachineStartMinute;
													$scope.modalInstanceCtrl2.loanMachineEndHour = r.data.resultData.loanMachineEndHour;
													$scope.modalInstanceCtrl2.loanMachineEndMinute = r.data.resultData.loanMachineEndMinute;
													$scope.modalInstanceCtrl2.loanMachineStartHour_holiday = r.data.resultData.loanMachineStartHour_holiday;
													$scope.modalInstanceCtrl2.loanMachineStartMinute_holiday = r.data.resultData.loanMachineStartMinute_holiday;
													$scope.modalInstanceCtrl2.loanMachineEndHour_holiday = r.data.resultData.loanMachineEndHour_holiday;
													$scope.modalInstanceCtrl2.loanMachineEndMinute_holiday = r.data.resultData.loanMachineEndMinute_holiday;
													if (r.data.resultData.loanMachineFlag == "") {
														$scope.modalInstanceCtrl2.loanMachineFlag = false;
													} else {
														$scope.modalInstanceCtrl2.loanMachineFlag = true;
													}
													if (r.data.resultData.aed == "") {
														$scope.modalInstanceCtrl2.aed = false;
													} else {
														$scope.modalInstanceCtrl2.aed = true;
													}

													if (r.data.resultData.internationalStore == "") {
														$scope.modalInstanceCtrl2.internationalStore = false;
													} else {
														$scope.modalInstanceCtrl2.internationalStore = true;
													}
													// 備考①
													$scope.modalInstanceCtrl2.comment1 = r.data.resultData.comment1;
													// 備考②
													$scope.modalInstanceCtrl2.comment2 = r.data.resultData.comment2;
													$scope.modalInstanceCtrl2.atmCount = r.data.resultData.atmCount;
													$scope.modalInstanceCtrl2.parkCount = r.data.resultData.parkCount;
													$scope.modalInstanceCtrl2.parkComment = r.data.resultData.parkComment;
													$scope.modalInstanceCtrl2.toilet = r.data.resultData.toilet;
													toilet = r.data.resultData.toilet;

													if (r.data.resultData.serviceConversionStore == "") {
														$scope.modalInstanceCtrl2.serviceConversionStore = false;
													} else {
														$scope.modalInstanceCtrl2.serviceConversionStore = true;
													}

													if (r.data.resultData.wheelChair == "") {
														$scope.modalInstanceCtrl2.wheelChair = false;
													} else {
														$scope.modalInstanceCtrl2.wheelChair = true;
													}

													if (r.data.resultData.wheelChairStore == "") {
														$scope.modalInstanceCtrl2.wheelChairStore = false;
													} else {
														$scope.modalInstanceCtrl2.wheelChairStore = true;
													}
													$scope.modalInstanceCtrl2.currentStation1 = r.data.resultData.currentStation1;
													$scope.modalInstanceCtrl2.currentStationDistance1 = r.data.resultData.currentStationDistance1;
													$scope.modalInstanceCtrl2.currentStationTime1 = r.data.resultData.currentStationTime1;
													$scope.modalInstanceCtrl2.currentStation2 = r.data.resultData.currentStation2;
													$scope.modalInstanceCtrl2.currentStationDistance2 = r.data.resultData.currentStationDistance2;
													$scope.modalInstanceCtrl2.currentStationTime2 = r.data.resultData.currentStationTime2;
													$scope.modalInstanceCtrl2.currentStation3 = r.data.resultData.currentStation3;
													$scope.modalInstanceCtrl2.currentStationDistance3 = r.data.resultData.currentStationDistance3;
													$scope.modalInstanceCtrl2.currentStationTime3 = r.data.resultData.currentStationTime3;
													$scope.modalInstanceCtrl2.message = r.data.resultData.message;
													$scope.modalInstanceCtrl2.image = r.data.resultData.image;
													$scope.modalInstanceCtrl2.imageUrl = r.data.resultData.imageUrl;
													$scope.modalInstanceCtrl2.icon = r.data.resultData.icon;
													// delFlg

													if (r.data.resultData.delFlg == "0") {
														$scope.modalInstanceCtrl2.delFlg = false;
													} else {
														$scope.modalInstanceCtrl2.delFlg = true;
													}
												} else if (bank_cds == "0173") {
													if (r.data.resultData.typeKbn == 0) {
														$scope.modalInstanceCtrl2.storeNameHide = false;
														$scope.modalInstanceCtrl2.teleNumberHide = false;
														$scope.modalInstanceCtrl2.conversionHide = false;
														$scope.modalInstanceCtrl2.storeHide = false;
														$scope.modalInstanceCtrl2.atmAddressHide = true;
														$scope.modalInstanceCtrl2.manageStoreHide = true;
														$scope.modalInstanceCtrl2.atmHide = true;
														$scope.modalInstanceCtrl2.type = 0;
													} else {
														$scope.modalInstanceCtrl2.storeNameHide = true;
														$scope.modalInstanceCtrl2.teleNumberHide = true;
														$scope.modalInstanceCtrl2.conversionHide = true;
														$scope.modalInstanceCtrl2.storeHide = true;
														$scope.modalInstanceCtrl2.atmAddressHide = false;
														$scope.modalInstanceCtrl2.manageStoreHide = false;
														$scope.modalInstanceCtrl2.atmHide = false;
														$scope.modalInstanceCtrl2.type = 1;
													}

													$scope.modalInstanceCtrl2.area = r.data.resultData.area;
													$scope.modalInstanceCtrl2.storeNumber = r.data.resultData.storeNumber;
													$scope.modalInstanceCtrl2.storeName = r.data.resultData.storeName;
													$scope.modalInstanceCtrl2.atmAddress = r.data.resultData.atmAddress;
													$scope.modalInstanceCtrl2.manageStore = r.data.resultData.manageStore;
													$scope.modalInstanceCtrl2.postCode = r.data.resultData.postCode;
													$scope.modalInstanceCtrl2.address = r.data.resultData.address;
													$scope.modalInstanceCtrl2.teleNumber = r.data.resultData.teleNumber;
													$scope.modalInstanceCtrl2.serviceConversionIn = r.data.resultData.serviceConversionIn;
													$scope.modalInstanceCtrl2.serviceConversionOut = r.data.resultData.serviceConversionOut;
													$scope.modalInstanceCtrl2.trustAgent = r.data.resultData.trustAgent;
													$scope.modalInstanceCtrl2.policeCompany = r.data.resultData.policeCompany;
													$scope.modalInstanceCtrl2.conversionMachine = r.data.resultData.conversionMachine;
													$scope.modalInstanceCtrl2.accountMachine = r.data.resultData.accountMachine;
													$scope.modalInstanceCtrl2.loanMachine = r.data.resultData.loanMachine;
													$scope.modalInstanceCtrl2.storeOpenStartHour = r.data.resultData.storeOpenStartHour;
													$scope.modalInstanceCtrl2.storeOpenEndHour = r.data.resultData.storeOpenEndHour;
													$scope.modalInstanceCtrl2.storeOpenStartHour_SAT = r.data.resultData.storeOpenStartHour_SAT;
													$scope.modalInstanceCtrl2.storeOpenEndHour_SAT = r.data.resultData.storeOpenEndHour_SAT;
													$scope.modalInstanceCtrl2.storeOpenStartHour_SUN = r.data.resultData.storeOpenStartHour_SUN;
													$scope.modalInstanceCtrl2.storeOpenEndHour_SUN = r.data.resultData.storeOpenEndHour_SUN;
													$scope.modalInstanceCtrl2.atmOpenStartHour = r.data.resultData.atmOpenStartHour;
													$scope.modalInstanceCtrl2.atmOpenEndHour = r.data.resultData.atmOpenEndHour;
													$scope.modalInstanceCtrl2.atmOpenStartHour_SAT = r.data.resultData.atmOpenStartHour_SAT;
													$scope.modalInstanceCtrl2.atmOpenEndHour_SAT = r.data.resultData.atmOpenEndHour_SAT;
													$scope.modalInstanceCtrl2.atmOpenStartHour_SUN = r.data.resultData.atmOpenStartHour_SUN;
													$scope.modalInstanceCtrl2.atmOpenEndHour_SUN = r.data.resultData.atmOpenEndHour_SUN;
													$scope.modalInstanceCtrl2.storeOpenStartMinute = r.data.resultData.storeOpenStartMinute;
													$scope.modalInstanceCtrl2.storeOpenEndMinute = r.data.resultData.storeOpenEndMinute;
													$scope.modalInstanceCtrl2.storeOpenStartMinute_SAT = r.data.resultData.storeOpenStartMinute_SAT;
													$scope.modalInstanceCtrl2.storeOpenEndMinute_SAT = r.data.resultData.storeOpenEndMinute_SAT;
													$scope.modalInstanceCtrl2.storeOpenStartMinute_SUN = r.data.resultData.storeOpenStartMinute_SUN;
													$scope.modalInstanceCtrl2.storeOpenEndMinute_SUN = r.data.resultData.storeOpenEndMinute_SUN;
													$scope.modalInstanceCtrl2.atmOpenStartMinute = r.data.resultData.atmOpenStartMinute;
													$scope.modalInstanceCtrl2.atmOpenEndMinute = r.data.resultData.atmOpenEndMinute;
													$scope.modalInstanceCtrl2.atmOpenStartMinute_SAT = r.data.resultData.atmOpenStartMinute_SAT;
													$scope.modalInstanceCtrl2.atmOpenEndMinute_SAT = r.data.resultData.atmOpenEndMinute_SAT;
													$scope.modalInstanceCtrl2.atmOpenStartMinute_SUN = r.data.resultData.atmOpenStartMinute_SUN;
													$scope.modalInstanceCtrl2.atmOpenEndMinute_SUN = r.data.resultData.atmOpenEndMinute_SUN;
													$scope.modalInstanceCtrl2.latitude = r.data.resultData.latitude;
													$scope.modalInstanceCtrl2.longitude = r.data.resultData.longitude;
													if (r.data.resultData.delFlg == 0) {
														$scope.modalInstanceCtrl2.delFlg = false;
													} else {
														$scope.modalInstanceCtrl2.delFlg = true;
													}

												}
											}
										}, function(e) {
											console.log(e);
										});
					}

					$scope.modalInstanceCtrl2.ok = function() {
						$uibModalInstance.close({});
					};

					$scope.modalInstanceCtrl2.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					$scope.modalInstanceCtrl2.windowSelected = function() {
						$scope.modalInstanceCtrl2.windowHide = false;
					}
					$scope.modalInstanceCtrl2.atmSelected = function() {
						$scope.modalInstanceCtrl2.windowHide = true;
						// $scope.modalInstanceCtrl2.teleNumber = "";
						// 窓口営業時間_平日始業
						// $scope.modalInstanceCtrl2.windowStartHour = "";
						// $scope.modalInstanceCtrl2.windowStartMinute = "";
						// 窓口営業時間_平日終業
						// $scope.modalInstanceCtrl2.windowEndHour = "";
						// $scope.modalInstanceCtrl2.windowEndMinute = "";

					}
					$scope.modalInstanceCtrl2.store = function() {
						$scope.modalInstanceCtrl2.storeNameHide = false;
						$scope.modalInstanceCtrl2.teleNumberHide = false;
						$scope.modalInstanceCtrl2.conversionHide = false;
						$scope.modalInstanceCtrl2.storeHide = false;
						$scope.modalInstanceCtrl2.atmAddressHide = true;
						$scope.modalInstanceCtrl2.manageStoreHide = true;
						$scope.modalInstanceCtrl2.atmHide = true;
					}
					$scope.modalInstanceCtrl2.atm = function() {
						$scope.modalInstanceCtrl2.storeNameHide = true;
						$scope.modalInstanceCtrl2.teleNumberHide = true;
						$scope.modalInstanceCtrl2.conversionHide = true;
						$scope.modalInstanceCtrl2.storeHide = true;
						$scope.modalInstanceCtrl2.atmAddressHide = false;
						$scope.modalInstanceCtrl2.manageStoreHide = false;
						$scope.modalInstanceCtrl2.atmHide = false;
					}
					$scope.kanaStoreATMNameOnblur = function() {

						if ($scope.modalInstanceCtrl2.kanaStoreATMName != null
								&& $scope.modalInstanceCtrl2.kanaStoreATMName != "") {
							var Str = $scope.modalInstanceCtrl2.kanaStoreATMName;
							var strTemp = ""
							var intAsc
							for (i = 0; i < Str.length; i++) {
								if ((Str.charCodeAt(i) >= 12354)
										&& (Str.charCodeAt(i) <= 12435)) {
									intAsc = Number(Str.charCodeAt(i)) + 96;
									strTemp = strTemp
											+ String.fromCharCode(intAsc);
								} else {
									strTemp = strTemp + Str.charAt(i);
								}
							}
							$scope.modalInstanceCtrl2.kanaStoreATMName = strTemp;

						}
					};
					$scope.storeNumberOnblur = function() {
						if ($scope.modalInstanceCtrl2.storeNumber != null
								&& $scope.modalInstanceCtrl2.storeNumber != "") {
							var postStoreNum = $scope.modalInstanceCtrl2.storeNumber
							var reg = /^[\d]+$/
							if ((reg.test(postStoreNum))) {
								if ($scope.modalInstanceCtrl2.storeNumber.length < 3) {
									var count = 3 - $scope.modalInstanceCtrl2.storeNumber.length;
									var str = $scope.modalInstanceCtrl2.storeNumber;
									var zero = "";
									for (i = 0; i < count; i++) {
										zero = zero + "0";
									}
									$scope.modalInstanceCtrl2.storeNumber = zero
											+ str;
								}
							}
						}
					}
					$scope.subStoreNumberOnblur = function() {
						if ($scope.modalInstanceCtrl2.subStoreNumber != null
								&& $scope.modalInstanceCtrl2.subStoreNumber != "") {
							var postSubStoreNum = $scope.modalInstanceCtrl2.subStoreNumber
							var reg = /^[\d]+$/
							if ((reg.test(postSubStoreNum))) {
								if ($scope.modalInstanceCtrl2.subStoreNumber.length < 2
										&& $scope.modalInstanceCtrl2.subStoreNumber.length > 0) {
									var count = 2 - $scope.modalInstanceCtrl2.subStoreNumber.length;
									var str = $scope.modalInstanceCtrl2.subStoreNumber;
									var zero = "";
									for (i = 0; i < count; i++) {
										zero = zero + "0";
									}
									$scope.modalInstanceCtrl2.subStoreNumber = zero
											+ str;
								}
							}
						}
					}
					$scope.atmStoreNumberOnblur = function() {
						if ($scope.modalInstanceCtrl2.atmStoreNumber != null
								&& $scope.modalInstanceCtrl2.atmStoreNumber != "") {
							var postAtmStoreNum = $scope.modalInstanceCtrl2.atmStoreNumber
							var reg = /^[\d]+$/
							if ((reg.test(postAtmStoreNum))) {
								if ($scope.modalInstanceCtrl2.atmStoreNumber.length < 2
										&& $scope.modalInstanceCtrl2.atmStoreNumber.length > 0) {
									var count = 2 - $scope.modalInstanceCtrl2.atmStoreNumber.length;
									var str = $scope.modalInstanceCtrl2.atmStoreNumber;
									var zero = "";
									for (i = 0; i < count; i++) {
										zero = zero + "0";
									}
									$scope.modalInstanceCtrl2.atmStoreNumber = zero
											+ str;
								}
							}
						}
					}
					$scope.postCodeOnblur = function() {
						if ($scope.modalInstanceCtrl2.postCode != null
								&& $scope.modalInstanceCtrl2.postCode != "") {
							var postCode = $scope.modalInstanceCtrl2.postCode;
							var reg = /^[\d]{7}$/
							if ((reg.test(postCode))) {
								$scope.modalInstanceCtrl2.postCode = postCode
										.substring(0, 3)
										+ "-" + postCode.substring(3);
							}
						}
					}

					// 更新ボタンを押す
					$scope.modalInstanceCtrl2.dataUpd = function() {
						$scope.modalInstanceCtrl2.UpdbtnDisabled = true;

						var delFlg = "";
						var poiStatus = "0";
						var accountMachineStartTime = "";
						var accountMachineStartTime_SAT = "";
						var accountMachineStartTime_SUN = "";
						var autoLoanMachineFlag = "";
						var loanMachineFlag = "";
						var aed = "";
						var internationalStore = "";
						var serviceConversionStore = "";
						var wheelChair = "";
						var wheelChairStore = "";
						var iyoAtmCount = "";

						if ($scope.modalInstanceCtrl2.atmCount != null
								&& $scope.modalInstanceCtrl2.atmCount != "") {
							iyoAtmCount = new Number(
									$scope.modalInstanceCtrl2.atmCount);
							iyoAtmCount = iyoAtmCount.toString();
						}
						if (bank_cds == "0169") {
							if ($scope.modalInstanceCtrl2.typeKbn == null) {
								swal("店舗分類を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.storeNumber == null
									|| $scope.modalInstanceCtrl2.storeNumber == "") {
								swal("母店番号を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.subStoreNumber == null
									|| $scope.modalInstanceCtrl2.subStoreNumber == "") {
								swal("出張所枝番を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.atmStoreNumber == null
									|| $scope.modalInstanceCtrl2.atmStoreNumber == "") {
								swal("ATM枝番を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.storeATMName == null
									|| $scope.modalInstanceCtrl2.storeATMName == "") {
								swal("店舗名（漢字）を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.kanaStoreATMName == null
									|| $scope.modalInstanceCtrl2.kanaStoreATMName == "") {
								swal("店舗名（カナ）を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							var reg = /^(?:[ぁ-んァ-ンー])+$/
							if ((!reg
									.test($scope.modalInstanceCtrl2.kanaStoreATMName))) {
								swal("店舗名（カナ）を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.longitude == null
									|| $scope.modalInstanceCtrl2.longitude == ""
									|| $scope.modalInstanceCtrl2.latitude == null
									|| $scope.modalInstanceCtrl2.latitude == "") {
								swal("経度／緯度を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.storeNumber != null
									&& $scope.modalInstanceCtrl2.storeNumber != "") {
								var reg = /^\d{3}$/;
								if (!reg
										.test($scope.modalInstanceCtrl2.storeNumber)) {
									swal("母店番号は半角数字3桁で入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.subStoreNumber != null
									&& $scope.modalInstanceCtrl2.subStoreNumber != "") {
								var reg = /^\d{2}$/;
								if (!reg
										.test($scope.modalInstanceCtrl2.subStoreNumber)) {
									swal("出張所枝番は半角数字2桁で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.atmStoreNumber != null
									&& $scope.modalInstanceCtrl2.atmStoreNumber != "") {
								var reg = /^\d{2}$/;
								if (!reg
										.test($scope.modalInstanceCtrl2.atmStoreNumber)) {
									swal("ATM枝番は半角数字2桁で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.postCode != null
									&& $scope.modalInstanceCtrl2.postCode != "") {
								var postCode = $scope.modalInstanceCtrl2.postCode
										.replace("-", "1");
								var numCnt = postCode.replace(/\D/g, '').length;
								if (numCnt != $scope.modalInstanceCtrl2.postCode.length) {
									swal("郵便番号は半角数字7桁と「-」1桁で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								var reg = /^(\d{3}-\d{4})+$/;
								if ((!reg
										.test($scope.modalInstanceCtrl2.postCode))) {
									swal("郵便番号は「000-0000」形式で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.storeATMName.length > 64) {
								swal("店舗名（漢字）は６４桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.kanaStoreATMName.length > 64) {
								swal("店舗名（カナ）は６４桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.teleNumber != null
									&& $scope.modalInstanceCtrl2.teleNumber != "") {
								var teleNumber = $scope.modalInstanceCtrl2.teleNumber
										.replace(/\-/g, "1");
								var numCnt = teleNumber.replace(/\D/g, '').length;
								if (numCnt != $scope.modalInstanceCtrl2.teleNumber.length) {
									swal("電話番号は半角数字10桁または11桁と「-」2桁で入力してください。",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								var reg1 = /^(\d{2}-\d{4}-\d{4})+$/;
								var reg2 = /^(\d{3}-\d{3}-\d{4})+$/;
								var reg3 = /^(\d{3}-\d{4}-\d{4})+$/;
								var reg4 = /^(\d{4}-\d{2}-\d{4})+$/;
								var reg5 = /^[\d]{10}$|^[\d]{11}$/;
								if ((!reg1
										.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg2
												.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg3
												.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg4
												.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg5
												.test($scope.modalInstanceCtrl2.teleNumber))) {
									swal(
											"電話番号は下記形式で入力してください。\n00-0000-0000\n000-000-0000\n000-0000-0000\n0000-00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.address != null
									&& $scope.modalInstanceCtrl2.address.length > 255) {
								swal("住所は２５５桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.landMark != null
									&& $scope.modalInstanceCtrl2.landMark.length > 64) {
								swal("ランドマークは６４桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.comment4 != null
									&& $scope.modalInstanceCtrl2.comment4.length > 150) {
								swal("窓口営業時間の備考１は１５０桁以内で入力してください。", "",
										"warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.comment5 != null
									&& $scope.modalInstanceCtrl2.comment5.length > 150) {
								swal("窓口営業時間の備考２は１５０桁以内で入力してください。", "",
										"warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}

							if ($scope.modalInstanceCtrl2.comment1 != null
									&& $scope.modalInstanceCtrl2.comment1.length > 150) {
								swal("地図情報の備考１は１５０桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.comment2 != null
									&& $scope.modalInstanceCtrl2.comment2.length > 150) {
								swal("地図情報の備考２は１５０桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.comment3 != null
									&& $scope.modalInstanceCtrl2.comment3.length > 150) {
								swal("地図情報の備考３は１５０桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.typeKbn == 0) {
								// 窓口店舗の場合
								// 窓口平日営業時間
								if ($scope.modalInstanceCtrl2.windowStartHour == ""
										|| $scope.modalInstanceCtrl2.windowStartMinute == ""
										|| $scope.modalInstanceCtrl2.windowEndHour == ""
										|| $scope.modalInstanceCtrl2.windowEndMinute == "") {
									swal("窓口平日営業時間を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								} else {
									if ($scope.modalInstanceCtrl2.windowStartHour > $scope.modalInstanceCtrl2.windowEndHour) {
										swal("正しい窓口平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									} else {
										if ($scope.modalInstanceCtrl2.windowStartHour == $scope.modalInstanceCtrl2.windowEndHour
												&& $scope.modalInstanceCtrl2.windowStartMinute >= $scope.modalInstanceCtrl2.windowEndMinute) {
											swal("正しい窓口平日営業時間を入力して下さい。", "",
													"warning");
											$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
											return;
										}
									}
								}

								// 窓口土曜日営業時間
								if ($scope.modalInstanceCtrl2.windowStartHour_SAT == ""
										&& $scope.modalInstanceCtrl2.windowStartMinute_SAT == ""
										&& $scope.modalInstanceCtrl2.windowEndHour_SAT == ""
										&& $scope.modalInstanceCtrl2.windowEndMinute_SAT == "") {
								} else {
									if ($scope.modalInstanceCtrl2.windowStartHour_SAT != ""
											&& $scope.modalInstanceCtrl2.windowStartMinute_SAT != ""
											&& $scope.modalInstanceCtrl2.windowEndHour_SAT != ""
											&& $scope.modalInstanceCtrl2.windowEndMinute_SAT != "") {
										if ($scope.modalInstanceCtrl2.windowStartHour_SAT > $scope.modalInstanceCtrl2.windowEndHour_SAT) {
											swal("正しい窓口土曜日営業時間を入力して下さい。", "",
													"warning");
											$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
											return;
										} else {
											if ($scope.modalInstanceCtrl2.windowStartHour_SAT == $scope.modalInstanceCtrl2.windowEndHour_SAT
													&& $scope.modalInstanceCtrl2.windowStartMinute_SAT >= $scope.modalInstanceCtrl2.windowEndMinute_SAT) {
												swal("正しい窓口土曜日営業時間を入力して下さい。",
														"", "warning");
												$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
												return;
											}
										}
									} else {
										swal("正しい窓口土曜日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								// 窓口日曜日営業時間
								if ($scope.modalInstanceCtrl2.windowStartHour_SUN == ""
										&& $scope.modalInstanceCtrl2.windowStartMinute_SUN == ""
										&& $scope.modalInstanceCtrl2.windowEndHour_SUN == ""
										&& $scope.modalInstanceCtrl2.windowEndMinute_SUN == "") {
								} else {
									if ($scope.modalInstanceCtrl2.windowStartHour_SUN != ""
											&& $scope.modalInstanceCtrl2.windowStartMinute_SUN != ""
											&& $scope.modalInstanceCtrl2.windowEndHour_SUN != ""
											&& $scope.modalInstanceCtrl2.windowEndMinute_SUN != "") {
										if ($scope.modalInstanceCtrl2.windowStartHour_SUN > $scope.modalInstanceCtrl2.windowEndHour_SUN) {
											swal("正しい窓口日曜日営業時間を入力して下さい。", "",
													"warning");
											$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
											return;
										} else {
											if ($scope.modalInstanceCtrl2.windowStartHour_SUN == $scope.modalInstanceCtrl2.windowEndHour_SUN
													&& $scope.modalInstanceCtrl2.windowStartMinute_SUN >= $scope.modalInstanceCtrl2.windowEndMinute_SUN) {
												swal("正しい窓口日曜日営業時間を入力して下さい。",
														"", "warning");
												$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
												return;
											}
										}

									} else {
										swal("正しい窓口日曜日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}

								}
								// ATMの場合
								if ($scope.modalInstanceCtrl2.atmStartHour != ""
										|| $scope.modalInstanceCtrl2.atmStartMinute != ""
										|| $scope.modalInstanceCtrl2.atmEndHour != ""
										|| $scope.modalInstanceCtrl2.atmEndMinute != ""
										|| $scope.modalInstanceCtrl2.atmStartHour_SAT != ""
										|| $scope.modalInstanceCtrl2.atmStartMinute_SAT != ""
										|| $scope.modalInstanceCtrl2.atmEndHour_SAT != ""
										|| $scope.modalInstanceCtrl2.atmEndMinute_SAT != ""
										|| $scope.modalInstanceCtrl2.atmStartHour_SUN != ""
										|| $scope.modalInstanceCtrl2.atmStartMinute_SUN != ""
										|| $scope.modalInstanceCtrl2.atmEndHour_SUN != ""
										|| $scope.modalInstanceCtrl2.atmEndMinute_SUN != "") {
									if ($scope.modalInstanceCtrl2.atmCount == null
											|| $scope.modalInstanceCtrl2.atmCount == ""
											|| $scope.modalInstanceCtrl2.atmCount == 0) {
										swal("正しいATM設置台数を入力してください。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
									if (atmTimeCheck() == false) {
										return;
									}

								}
							} else {
								// ATMの場合
								if (atmTimeCheck() == false) {
									return;
								}
								if ($scope.modalInstanceCtrl2.atmCount == null
										|| $scope.modalInstanceCtrl2.atmCount == ""
										|| $scope.modalInstanceCtrl2.atmCount == 0) {
									swal("正しいATM設置台数を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}

							}
							if ($scope.modalInstanceCtrl2.typeKbn == 0) {
								$scope.modalInstanceCtrl2.windowOpenStartTime = $scope.modalInstanceCtrl2.windowStartHour
										+ ":"
										+ $scope.modalInstanceCtrl2.windowStartMinute
										+ "~"
										+ $scope.modalInstanceCtrl2.windowEndHour
										+ ":"
										+ $scope.modalInstanceCtrl2.windowEndMinute;
							} else {
								$scope.modalInstanceCtrl2.windowOpenStartTime = $scope.modalInstanceCtrl2.atmStartHour
										+ ":"
										+ $scope.modalInstanceCtrl2.atmStartMinute
										+ "~"
										+ $scope.modalInstanceCtrl2.atmEndHour
										+ ":"
										+ $scope.modalInstanceCtrl2.atmEndMinute;
							}
							if ($scope.modalInstanceCtrl2.delFlg == true) {
								delFlg = "1";
							} else {
								delFlg = "0";
							}
							var workDayEnd = "";
							if ($scope.modalInstanceCtrl2.workDay == true) {
								workDayEnd = $scope.modalInstanceCtrl2.windowEndMinute
										+ "※";
							} else {
								workDayEnd = $scope.modalInstanceCtrl2.windowEndMinute;
							}
							var SaturdayEnd = "";
							if ($scope.modalInstanceCtrl2.Saturday == true) {
								SaturdayEnd = $scope.modalInstanceCtrl2.windowEndMinute_SAT
										+ "※";
							} else {
								SaturdayEnd = $scope.modalInstanceCtrl2.windowEndMinute_SAT;
							}
							var SundayEnd = "";
							if ($scope.modalInstanceCtrl2.Sunday == true) {
								SundayEnd = $scope.modalInstanceCtrl2.windowEndMinute_SUN
										+ "※";
							} else {
								SundayEnd = $scope.modalInstanceCtrl2.windowEndMinute_SUN;
							}
						} else if (bank_cds == "0174") {
							if ($scope.modalInstanceCtrl2.typeKbn == null) {
								swal("店舗分類を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.storeNumber == null
									|| $scope.modalInstanceCtrl2.storeNumber == "") {
								swal("店番号を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}

							if ($scope.modalInstanceCtrl2.storeATMName == null
									|| $scope.modalInstanceCtrl2.storeATMName == "") {
								swal("店舗名（漢字）を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.storeATMName.length > 64) {
								swal("店舗名（漢字）は６４桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.kanaStoreATMName == null
									|| $scope.modalInstanceCtrl2.kanaStoreATMName == "") {
								swal("店舗名（カナ）を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}

							if ($scope.modalInstanceCtrl2.kanaStoreATMName.length > 64) {
								swal("店舗名（カナ）は６４桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							var reg = /^(?:[ぁ-んァ-ンー])+$/
							if ((!reg
									.test($scope.modalInstanceCtrl2.kanaStoreATMName))) {
								swal("店舗名（カナ）を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.address != null
									&& $scope.modalInstanceCtrl2.address.length > 255) {
								swal("住所は２５５桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.teleNumber != null
									&& $scope.modalInstanceCtrl2.teleNumber != "") {
								var teleNumber = $scope.modalInstanceCtrl2.teleNumber
										.replace(/\-/g, "1");
								var numCnt = teleNumber.replace(/\D/g, '').length;
								if (numCnt != $scope.modalInstanceCtrl2.teleNumber.length) {
									swal("電話番号は半角数字10桁または11桁と「-」2桁で入力してください。",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								var reg1 = /^(\d{2}-\d{4}-\d{4})+$/;
								var reg2 = /^(\d{3}-\d{3}-\d{4})+$/;
								var reg3 = /^(\d{3}-\d{4}-\d{4})+$/;
								var reg4 = /^(\d{4}-\d{2}-\d{4})+$/;
								var reg5 = /^[\d]{10}$|^[\d]{11}$/;
								if ((!reg1
										.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg2
												.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg3
												.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg4
												.test($scope.modalInstanceCtrl2.teleNumber))
										&& (!reg5
												.test($scope.modalInstanceCtrl2.teleNumber))) {
									swal(
											"電話番号は下記形式で入力してください。\n00-0000-0000\n000-000-0000\n000-0000-0000\n0000-00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.postCode != null
									&& $scope.modalInstanceCtrl2.postCode != "") {
								var postCode = $scope.modalInstanceCtrl2.postCode
										.replace("-", "1");
								var numCnt = postCode.replace(/\D/g, '').length;
								if (numCnt != $scope.modalInstanceCtrl2.postCode.length) {
									swal("郵便番号は半角数字7桁と「-」1桁で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								var reg = /^(\d{3}-\d{4})+$/;
								if ((!reg
										.test($scope.modalInstanceCtrl2.postCode))) {
									swal("郵便番号は「000-0000」形式で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.longitude == null
									|| $scope.modalInstanceCtrl2.longitude == ""
									|| $scope.modalInstanceCtrl2.latitude == null
									|| $scope.modalInstanceCtrl2.latitude == "") {
								swal("経度／緯度を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.typeKbn == 1) {
								if ($scope.modalInstanceCtrl2.windowOpenStartHour == ""
										|| $scope.modalInstanceCtrl2.windowOpenStartMinute == ""
										|| $scope.modalInstanceCtrl2.windowOpenEndHour == ""
										|| $scope.modalInstanceCtrl2.windowOpenEndMinute == "") {
									swal("窓口平日営業時間を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.windowOpenStartHour,
											$scope.modalInstanceCtrl2.windowOpenStartMinute,
											$scope.modalInstanceCtrl2.windowOpenEndHour,
											$scope.modalInstanceCtrl2.windowOpenEndMinute) == false) {
										swal("正しい窓口平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								if ($scope.modalInstanceCtrl2.atmCount > 0) {
									if ($scope.modalInstanceCtrl2.atmOpenStartHour == ""
											|| $scope.modalInstanceCtrl2.atmOpenStartMinute == ""
											|| $scope.modalInstanceCtrl2.atmOpenEndHour == ""
											|| $scope.modalInstanceCtrl2.atmOpenEndMinute == "") {
										swal("ATM平日営業時間を入力してください。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									} else {
										if (iYoTimeCheck(
												$scope.modalInstanceCtrl2.atmOpenStartHour,
												$scope.modalInstanceCtrl2.atmOpenStartMinute,
												$scope.modalInstanceCtrl2.atmOpenEndHour,
												$scope.modalInstanceCtrl2.atmOpenEndMinute) == false) {
											swal("正しいATM平日営業時間を入力して下さい。", "",
													"warning");
											$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
											return;
										}
									}
								}
							} else {
								if ($scope.modalInstanceCtrl2.atmOpenStartHour == ""
										|| $scope.modalInstanceCtrl2.atmOpenStartMinute == ""
										|| $scope.modalInstanceCtrl2.atmOpenEndHour == ""
										|| $scope.modalInstanceCtrl2.atmOpenEndMinute == "") {
									swal("ATM平日営業時間を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.atmOpenStartHour,
											$scope.modalInstanceCtrl2.atmOpenStartMinute,
											$scope.modalInstanceCtrl2.atmOpenEndHour,
											$scope.modalInstanceCtrl2.atmOpenEndMinute) == false) {
										swal("正しいATM平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								if ($scope.modalInstanceCtrl2.atmCount == ""
										|| $scope.modalInstanceCtrl2.atmCount == null
										|| $scope.modalInstanceCtrl2.atmCount == 0) {
									swal("正しいATM設置台数を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.windowOpenStartHour == ""
									&& $scope.modalInstanceCtrl2.windowOpenStartMinute == ""
									&& $scope.modalInstanceCtrl2.windowOpenEndHour == ""
									&& $scope.modalInstanceCtrl2.windowOpenEndMinute == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.windowOpenStartHour,
										$scope.modalInstanceCtrl2.windowOpenStartMinute,
										$scope.modalInstanceCtrl2.windowOpenEndHour,
										$scope.modalInstanceCtrl2.windowOpenEndMinute) == false) {
									swal("正しい窓口平日営業時間を入力して下さい。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.windowOpenStartHour_SAT == ""
									&& $scope.modalInstanceCtrl2.windowOpenStartMinute_SAT == ""
									&& $scope.modalInstanceCtrl2.windowOpenEndHour_SAT == ""
									&& $scope.modalInstanceCtrl2.windowOpenEndMinute_SAT == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.windowOpenStartHour_SAT,
										$scope.modalInstanceCtrl2.windowOpenStartMinute_SAT,
										$scope.modalInstanceCtrl2.windowOpenEndHour_SAT,
										$scope.modalInstanceCtrl2.windowOpenEndMinute_SAT) == false) {
									swal("正しい窓口土曜日営業時間を入力して下さい。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.windowOpenStartHour_SUN == ""
									&& $scope.modalInstanceCtrl2.windowOpenStartMinute_SUN == ""
									&& $scope.modalInstanceCtrl2.windowOpenEndHour_SUN == ""
									&& $scope.modalInstanceCtrl2.windowOpenEndMinute_SUN == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.windowOpenStartHour_SUN,
										$scope.modalInstanceCtrl2.windowOpenStartMinute_SUN,
										$scope.modalInstanceCtrl2.windowOpenEndHour_SUN,
										$scope.modalInstanceCtrl2.windowOpenEndMinute_SUN) == false) {
									swal("正しい窓口日曜日営業時間を入力して下さい。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.atmOpenStartHour_SAT == ""
									&& $scope.modalInstanceCtrl2.atmOpenStartMinute_SAT == ""
									&& $scope.modalInstanceCtrl2.atmOpenEndHour_SAT == ""
									&& $scope.modalInstanceCtrl2.atmOpenEndMinute_SAT == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.atmOpenStartHour_SAT,
										$scope.modalInstanceCtrl2.atmOpenStartMinute_SAT,
										$scope.modalInstanceCtrl2.atmOpenEndHour_SAT,
										$scope.modalInstanceCtrl2.atmOpenEndMinute_SAT) == false) {
									swal("正しいATM土曜日営業時間を入力して下さい。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							if ($scope.modalInstanceCtrl2.atmOpenStartHour_SUN == ""
									&& $scope.modalInstanceCtrl2.atmOpenStartMinute_SUN == ""
									&& $scope.modalInstanceCtrl2.atmOpenEndHour_SUN == ""
									&& $scope.modalInstanceCtrl2.atmOpenEndMinute_SUN == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.atmOpenStartHour_SUN,
										$scope.modalInstanceCtrl2.atmOpenStartMinute_SUN,
										$scope.modalInstanceCtrl2.atmOpenEndHour_SUN,
										$scope.modalInstanceCtrl2.atmOpenEndMinute_SUN) == false) {
									swal("正しいATM日曜日営業時間を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.conversionMachineStartHour == ""
									&& $scope.modalInstanceCtrl2.conversionMachineStartMinute == ""
									&& $scope.modalInstanceCtrl2.conversionMachineEndHour == ""
									&& $scope.modalInstanceCtrl2.conversionMachineEndMinute == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.conversionMachineStartHour,
										$scope.modalInstanceCtrl2.conversionMachineStartMinute,
										$scope.modalInstanceCtrl2.conversionMachineEndHour,
										$scope.modalInstanceCtrl2.conversionMachineEndMinute) == false) {
									swal("正しい自動両替機平日を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.conversionMachineStartHour_holiday == ""
									&& $scope.modalInstanceCtrl2.conversionMachineStartMinute_holiday == ""
									&& $scope.modalInstanceCtrl2.conversionMachineEndHour_holiday == ""
									&& $scope.modalInstanceCtrl2.conversionMachineEndMinute_holiday == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.conversionMachineStartHour_holiday,
										$scope.modalInstanceCtrl2.conversionMachineStartMinute_holiday,
										$scope.modalInstanceCtrl2.conversionMachineEndHour_holiday,
										$scope.modalInstanceCtrl2.conversionMachineEndMinute_holiday) == false) {
									swal("正しい自動両替機土・日・祝を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.autoLoanMachineStartHour == ""
									&& $scope.modalInstanceCtrl2.autoLoanMachineStartMinute == ""
									&& $scope.modalInstanceCtrl2.autoLoanMachineEndHour == ""
									&& $scope.modalInstanceCtrl2.autoLoanMachineEndMinute == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.autoLoanMachineStartHour,
										$scope.modalInstanceCtrl2.autoLoanMachineStartMInute,
										$scope.modalInstanceCtrl2.autoLoanMachineEndHour,
										$scope.modalInstanceCtrl2.autoLoanMachineEndMInute) == false) {
									swal("正しい全自動型貸金庫平日を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.autoLoanMachineStartHour_holiday == ""
									&& $scope.modalInstanceCtrl2.autoLoanMachineStartMinute_holiday == ""
									&& $scope.modalInstanceCtrl2.autoLoanMachineEndHour_holiday == ""
									&& $scope.modalInstanceCtrl2.autoLoanMachineEndMinute_holiday == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.autoLoanMachineStartHour_holiday,
										$scope.modalInstanceCtrl2.autoLoanMachineStartMInute_holiday,
										$scope.modalInstanceCtrl2.autoLoanMachineEndHour_holiday,
										$scope.modalInstanceCtrl2.autoLoanMachineEndMInute_holiday) == false) {
									swal("正しい全自動型貸金庫土・日・祝を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.loanMachineStartHour == ""
									&& $scope.modalInstanceCtrl2.loanMachineStartMinute == ""
									&& $scope.modalInstanceCtrl2.loanMachineEndHour == ""
									&& $scope.modalInstanceCtrl2.loanMachineEndMinute == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.loanMachineStartHour,
										$scope.modalInstanceCtrl2.loanMachineStartMinute,
										$scope.modalInstanceCtrl2.loanMachineEndHour,
										$scope.modalInstanceCtrl2.loanMachineEndMinute) == false) {
									swal("正しい貸金庫平日を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.loanMachineStartHour_holiday == ""
									&& $scope.modalInstanceCtrl2.loanMachineStartMinute_holiday == ""
									&& $scope.modalInstanceCtrl2.loanMachineEndHour_holiday == ""
									&& $scope.modalInstanceCtrl2.loanMachineEndMinute_holiday == "") {
							} else {
								if (iYoTimeCheck(
										$scope.modalInstanceCtrl2.loanMachineStartHour_holiday,
										$scope.modalInstanceCtrl2.loanMachineStartMinute_holiday,
										$scope.modalInstanceCtrl2.loanMachineEndHour_holiday,
										$scope.modalInstanceCtrl2.loanMachineEndMinute_holiday) == false) {
									swal("正しい貸金庫土・日・祝を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.delFlg == true) {
								delFlg = "1";
							} else {
								delFlg = "0";
							}
							if ($scope.modalInstanceCtrl2.autoLoanMachineFlag == true) {
								autoLoanMachineFlag = "1";
							}
							if ($scope.modalInstanceCtrl2.loanMachineFlag == true) {
								loanMachineFlag = "1";
							}
							if ($scope.modalInstanceCtrl2.poiStatus == true) {
								poiStatus = "1"
							}
							if ($scope.modalInstanceCtrl2.accountMachineStartTime == true) {
								accountMachineStartTime = "1"
							}
							if ($scope.modalInstanceCtrl2.accountMachineStartTime_SAT == true) {
								accountMachineStartTime_SAT = "1"
							}
							if ($scope.modalInstanceCtrl2.accountMachineStartTime_SUN == true) {
								accountMachineStartTime_SUN = "1"
							}
							if ($scope.modalInstanceCtrl2.aed == true) {
								aed = "1"
							}
							if ($scope.modalInstanceCtrl2.internationalStore == true) {
								internationalStore = "1"
							}

							if ($scope.modalInstanceCtrl2.serviceConversionStore == true) {
								serviceConversionStore = "1"
							}
							if ($scope.modalInstanceCtrl2.wheelChair == true) {
								wheelChair = "1"
							}
							if ($scope.modalInstanceCtrl2.wheelChairStore == true) {
								wheelChairStore = "1"
							}
						} else if (bank_cds == "0173") {
							if ($scope.modalInstanceCtrl2.type == null) {
								swal("店舗分類を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.storeNumber == null
									|| $scope.modalInstanceCtrl2.storeNumber == "") {
								swal("店番を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.type == 0) {
								if ($scope.modalInstanceCtrl2.storeName == null
										|| $scope.modalInstanceCtrl2.storeName == "") {
									swal("店舗名を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								if ($scope.modalInstanceCtrl2.storeName.length > 64) {
									swal("店舗名は６４桁以内で入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}
							var reg = /^(?:[ぁ-んァ-ンー])+$/
							if ($scope.modalInstanceCtrl2.area != null
									&& $scope.modalInstanceCtrl2.area.length > 255) {
								swal("所在地は２５５桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.atmAddress != null
									&& $scope.modalInstanceCtrl2.atmAddress.length > 255) {
								swal("設置場所は２５５桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.address != null
									&& $scope.modalInstanceCtrl2.address.length > 255) {
								swal("住所は２５５桁以内で入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.teleNumber != null
									&& $scope.modalInstanceCtrl2.teleNumber != "") {
								var teleNumber = $scope.modalInstanceCtrl2.teleNumber;

								var reg1 = /^(\d{10})+$/;
								var reg2 = /^(\d{11})+$/;
								var reg3 = /^[\d]{10}$|^[\d]{11}$/;
								if (teleNumber.indexOf("(") != 0) {
									swal(
											"電話番号は下記形式で入力してください。\n(00)0000-0000\n(000)000-0000\n(000)0000-0000\n(0000)00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								if (teleNumber.indexOf(")") != 3
										&& teleNumber.indexOf(")") != 4
										&& teleNumber.indexOf(")") != 5) {
									swal(
											"電話番号は下記形式で入力してください。\n(00)0000-0000\n(000)000-0000\n(000)0000-0000\n(0000)00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								if (teleNumber.indexOf("-") != 8
										&& teleNumber.indexOf("-") != 9) {
									swal(
											"電話番号は下記形式で入力してください。\n(00)0000-0000\n(000)000-0000\n(000)0000-0000\n(0000)00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								teleNumber = teleNumber.replace("(", "");
								teleNumber = teleNumber.replace(")", "");
								teleNumber = teleNumber.replace("-", "");
								if (teleNumber.length != 11
										&& teleNumber.length != 10) {
									swal(
											"電話番号は下記形式で入力してください。\n(00)0000-0000\n(000)000-0000\n(000)0000-0000\n(0000)00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								if ((!reg1.test(teleNumber))
										&& (!reg2.test(teleNumber))
										&& (!reg3.test(teleNumber))) {
									swal(
											"電話番号は下記形式で入力してください。\n(00)0000-0000\n(000)000-0000\n(000)0000-0000\n(0000)00-0000",
											"", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.postCode != null
									&& $scope.modalInstanceCtrl2.postCode != "") {
								var postCode = $scope.modalInstanceCtrl2.postCode
										.replace("-", "1");
								var numCnt = postCode.replace(/\D/g, '').length;
								if (numCnt != $scope.modalInstanceCtrl2.postCode.length) {
									swal("郵便番号は半角数字7桁と「-」1桁で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
								var reg = /^(\d{3}-\d{4})+$/;
								if ((!reg
										.test($scope.modalInstanceCtrl2.postCode))) {
									swal("郵便番号は「000-0000」形式で入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								}
							}

							if ($scope.modalInstanceCtrl2.type == 0) {
								if ($scope.modalInstanceCtrl2.storeOpenStartHour == ""
										|| $scope.modalInstanceCtrl2.storeOpenStartMinute == ""
										|| $scope.modalInstanceCtrl2.storeOpenEndHour == ""
										|| $scope.modalInstanceCtrl2.storeOpenEndMinute == "") {
									swal("窓口平日営業時間を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.storeOpenStartHour,
											$scope.modalInstanceCtrl2.storeOpenStartMinute,
											$scope.modalInstanceCtrl2.storeOpenEndHour,
											$scope.modalInstanceCtrl2.storeOpenEndMinute) == false) {
										swal("正しい窓口平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}

								if ($scope.modalInstanceCtrl2.storeOpenStartHour == ""
										&& $scope.modalInstanceCtrl2.storeOpenStartMinute == ""
										&& $scope.modalInstanceCtrl2.storeOpenEndHour == ""
										&& $scope.modalInstanceCtrl2.storeOpenEndMinute == "") {
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.storeOpenStartHour,
											$scope.modalInstanceCtrl2.storeOpenStartMinute,
											$scope.modalInstanceCtrl2.storeOpenEndHour,
											$scope.modalInstanceCtrl2.storeOpenEndMinute) == false) {
										swal("正しい窓口平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								if ($scope.modalInstanceCtrl2.storeOpenStartHour_SAT == ""
										&& $scope.modalInstanceCtrl2.storeOpenStartMinute_SAT == ""
										&& $scope.modalInstanceCtrl2.storeOpenEndHour_SAT == ""
										&& $scope.modalInstanceCtrl2.storeOpenEndMinute_SAT == "") {
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.storeOpenStartHour_SAT,
											$scope.modalInstanceCtrl2.storeOpenStartMinute_SAT,
											$scope.modalInstanceCtrl2.storeOpenEndHour_SAT,
											$scope.modalInstanceCtrl2.storeOpenEndMinute_SAT) == false) {
										swal("正しい窓口土曜日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								if ($scope.modalInstanceCtrl2.storeOpenStartHour_SUN == ""
										&& $scope.modalInstanceCtrl2.storeOpenStartMinute_SUN == ""
										&& $scope.modalInstanceCtrl2.storeOpenEndHour_SUN == ""
										&& $scope.modalInstanceCtrl2.storeOpenEndMinute_SUN == "") {
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.storeOpenStartHour_SUN,
											$scope.modalInstanceCtrl2.storeOpenStartMinute_SUN,
											$scope.modalInstanceCtrl2.storeOpenEndHour_SUN,
											$scope.modalInstanceCtrl2.storeOpenEndMinute_SUN) == false) {
										swal("正しい窓口日曜日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}

							} else {
								if ($scope.modalInstanceCtrl2.atmOpenStartHour == ""
										|| $scope.modalInstanceCtrl2.atmOpenStartMinute == ""
										|| $scope.modalInstanceCtrl2.atmOpenEndHour == ""
										|| $scope.modalInstanceCtrl2.atmOpenEndMinute == "") {
									swal("ATM平日営業時間を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return;
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.atmOpenStartHour,
											$scope.modalInstanceCtrl2.atmOpenStartMinute,
											$scope.modalInstanceCtrl2.atmOpenEndHour,
											$scope.modalInstanceCtrl2.atmOpenEndMinute) == false) {
										swal("正しいATM平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}

								if ($scope.modalInstanceCtrl2.atmOpenStartHour == ""
										&& $scope.modalInstanceCtrl2.atmOpenStartMinute == ""
										&& $scope.modalInstanceCtrl2.atmOpenEndHour == ""
										&& $scope.modalInstanceCtrl2.atmOpenEndMinute == "") {
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.atmOpenStartHour,
											$scope.modalInstanceCtrl2.atmOpenStartMinute,
											$scope.modalInstanceCtrl2.atmOpenEndHour,
											$scope.modalInstanceCtrl2.atmOpenEndMinute) == false) {
										swal("正しいATM平日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								if ($scope.modalInstanceCtrl2.atmOpenStartHour_SAT == ""
										&& $scope.modalInstanceCtrl2.atmOpenStartMinute_SAT == ""
										&& $scope.modalInstanceCtrl2.atmOpenEndHour_SAT == ""
										&& $scope.modalInstanceCtrl2.atmOpenEndMinute_SAT == "") {
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.atmOpenStartHour_SAT,
											$scope.modalInstanceCtrl2.atmOpenStartMinute_SAT,
											$scope.modalInstanceCtrl2.atmOpenEndHour_SAT,
											$scope.modalInstanceCtrl2.atmOpenEndMinute_SAT) == false) {
										swal("正しいATM土曜日営業時間を入力して下さい。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
								if ($scope.modalInstanceCtrl2.atmOpenStartHour_SUN == ""
										&& $scope.modalInstanceCtrl2.atmOpenStartMinute_SUN == ""
										&& $scope.modalInstanceCtrl2.atmOpenEndHour_SUN == ""
										&& $scope.modalInstanceCtrl2.atmOpenEndMinute_SUN == "") {
								} else {
									if (iYoTimeCheck(
											$scope.modalInstanceCtrl2.atmOpenStartHour_SUN,
											$scope.modalInstanceCtrl2.atmOpenStartMinute_SUN,
											$scope.modalInstanceCtrl2.atmOpenEndHour_SUN,
											$scope.modalInstanceCtrl2.atmOpenEndMinute_SUN) == false) {
										swal("正しいATM日曜日営業時間を入力してください。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return;
									}
								}
							}
							if ($scope.modalInstanceCtrl2.longitude == null
									|| $scope.modalInstanceCtrl2.longitude == ""
									|| $scope.modalInstanceCtrl2.latitude == null
									|| $scope.modalInstanceCtrl2.latitude == "") {
								swal("経度／緯度を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return;
							}
							if ($scope.modalInstanceCtrl2.delFlg == true) {
								delFlg = "1";
							} else {
								delFlg = "0";
							}
						}
						var url = "./../protected/master/storeATMDetailUpd";
						if (bank_cds == "0169") {
							var data = {
								'_id' : key._id,
								"modeType" : key.modeType,
								// 店舗区分
								'typeKbn' : $scope.modalInstanceCtrl2.typeKbn,
								// 店舗コード_母店番号
								'storeNumber' : $scope.modalInstanceCtrl2.storeNumber,
								// 店舗コード_出張所枝番
								'subStoreNumber' : $scope.modalInstanceCtrl2.subStoreNumber,
								// 店舗コード_ATM枝番
								'atmStoreNumber' : $scope.modalInstanceCtrl2.atmStoreNumber,
								// 店舗名_（漢字）
								'storeATMName' : $scope.modalInstanceCtrl2.storeATMName,
								// 店舗名_（カナ）
								'kanaStoreATMName' : $scope.modalInstanceCtrl2.kanaStoreATMName,
								// 所在地_郵便番号
								'postCode' : $scope.modalInstanceCtrl2.postCode,
								// 所在地_住所
								'address' : $scope.modalInstanceCtrl2.address,
								// 所在地_ランドマーク
								'landMark' : $scope.modalInstanceCtrl2.landMark,
								// 電話番号
								'teleNumber' : $scope.modalInstanceCtrl2.teleNumber,
								// 窓口営業時間_平日始業
								'windowStartHour' : $scope.modalInstanceCtrl2.windowStartHour,
								'windowStartMinute' : $scope.modalInstanceCtrl2.windowStartMinute,
								// 窓口営業時間_平日終業
								'windowEndHour' : $scope.modalInstanceCtrl2.windowEndHour,
								'windowEndMinute' : workDayEnd,
								// 窓口営業時間_土曜日始業
								'windowStartHour_SAT' : $scope.modalInstanceCtrl2.windowStartHour_SAT,
								'windowStartMinute_SAT' : $scope.modalInstanceCtrl2.windowStartMinute_SAT,
								// 窓口営業時間_土曜日終業
								'windowEndHour_SAT' : $scope.modalInstanceCtrl2.windowEndHour_SAT,
								'windowEndMinute_SAT' : SaturdayEnd,
								// 窓口営業時間_日曜日始業
								'windowStartHour_SUN' : $scope.modalInstanceCtrl2.windowStartHour_SUN,
								'windowStartMinute_SUN' : $scope.modalInstanceCtrl2.windowStartMinute_SUN,
								// 窓口営業時間_日曜日終業
								'windowEndHour_SUN' : $scope.modalInstanceCtrl2.windowEndHour_SUN,
								'windowEndMinute_SUN' : SundayEnd,
								// ATM営業時間_平日始業
								'atmStartHour' : $scope.modalInstanceCtrl2.atmStartHour,
								'atmStartMinute' : $scope.modalInstanceCtrl2.atmStartMinute,
								// ATM営業時間_平日終業
								'atmEndHour' : $scope.modalInstanceCtrl2.atmEndHour,
								'atmEndMinute' : $scope.modalInstanceCtrl2.atmEndMinute,
								// ATM営業時間_土曜日始業
								'atmStartHour_SAT' : $scope.modalInstanceCtrl2.atmStartHour_SAT,
								'atmStartMinute_SAT' : $scope.modalInstanceCtrl2.atmStartMinute_SAT,
								// ATM営業時間_土曜日終業
								'atmEndHour_SAT' : $scope.modalInstanceCtrl2.atmEndHour_SAT,
								'atmEndMinute_SAT' : $scope.modalInstanceCtrl2.atmEndMinute_SAT,
								// ATM営業時間_日曜日始業
								'atmStartHour_SUN' : $scope.modalInstanceCtrl2.atmStartHour_SUN,
								'atmStartMinute_SUN' : $scope.modalInstanceCtrl2.atmStartMinute_SUN,
								// ATM営業時間_日曜日終業
								'atmEndHour_SUN' : $scope.modalInstanceCtrl2.atmEndHour_SUN,
								'atmEndMinute_SUN' : $scope.modalInstanceCtrl2.atmEndMinute_SUN,
								// AＴＭ設置台数
								'atmCount' : $scope.modalInstanceCtrl2.atmCount == null ? 0
										: $scope.modalInstanceCtrl2.atmCount,
								// 駐車場_有無
								'park' : $scope.modalInstanceCtrl2.park,
								// 駐車場_障害者対応
								'parkServiceForDisabled' : $scope.modalInstanceCtrl2.parkServiceForDisabled,
								// 駐車場_備考
								'parkComment' : $scope.modalInstanceCtrl2.parkComment,
								// ひろぎんウツミ屋
								'hirginUtsumiya' : $scope.modalInstanceCtrl2.hirginUtsumiya,
								// 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
								'serviceDollarEuro' : $scope.modalInstanceCtrl2.serviceDollarEuro,
								// 商品サービス_外貨両替（ｱｼﾞｱ通貨）
								'serviceAsia' : $scope.modalInstanceCtrl2.serviceAsia,
								// 商品サービス_外貨両替（その他）
								'serviceOther' : $scope.modalInstanceCtrl2.serviceOther,
								// 商品サービス_外貨買取
								'serviceForeignExchange' : $scope.modalInstanceCtrl2.serviceForeignExchange,
								// 商品サービス_投資信託
								'serviceInvestmentTrust' : $scope.modalInstanceCtrl2.serviceInvestmentTrust,
								// 商品サービス_年金保険
								'servicePensionInsurance' : $scope.modalInstanceCtrl2.servicePensionInsurance,
								// 商品サービス_金融商品仲介（みずほ証券）
								'serviceMizuho' : $scope.modalInstanceCtrl2.serviceMizuho,
								// 商品サービス_金融商品仲介（ひろぎんウツミ屋）
								'serviceHirginUtsumiya' : $scope.modalInstanceCtrl2.serviceHirginUtsumiya,
								// 商品サービス_全自動貸金庫
								'serviceAutoSafeDepositBox' : $scope.modalInstanceCtrl2.serviceAutoSafeDepositBox,
								// 商品サービス_一般貸金庫
								'serviceSafeDepositBox' : $scope.modalInstanceCtrl2.serviceSafeDepositBox,
								// 商品サービス_ｾｰﾌﾃｨｹｰｽ
								'serviceSafeBox' : $scope.modalInstanceCtrl2.serviceSafeBox,
								// 店舗設備_IB専用PC
								'internationalTradePC' : $scope.modalInstanceCtrl2.internationalTradePC,
								// 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
								'childrenSpace' : $scope.modalInstanceCtrl2.childrenSpace,
								// バリアフリー_視覚障害対応ATM
								'barrierFreeVisualImpairment' : $scope.modalInstanceCtrl2.barrierFreeVisualImpairment,
								// バリアフリー_点字ﾌﾞﾛｯｸ
								'barrierFreeBrailleBlock' : $scope.modalInstanceCtrl2.barrierFreeBrailleBlock,
								// バリアフリー_音声ｶﾞｲﾄﾞ
								'barrierFreeVoiceGuide' : $scope.modalInstanceCtrl2.barrierFreeVoiceGuide,
								// バリアフリー_AED
								'barrierFreeAED' : $scope.modalInstanceCtrl2.barrierFreeAED,
								// ATM機能_振込
								'atmFunctionTransfer' : $scope.modalInstanceCtrl2.atmFunctionTransfer,
								// ATM機能_硬貨入出金
								'atmFunctionCoinAccess' : $scope.modalInstanceCtrl2.atmFunctionCoinAccess,
								// ATM機能_宝くじｻｰﾋﾞｽ
								'atmFunctionLotteryService' : $scope.modalInstanceCtrl2.atmFunctionLotteryService,
								// ATM機能_手のひら認証
								'atmFunctionPalmAuthentication' : $scope.modalInstanceCtrl2.atmFunctionPalmAuthentication,
								// ATM機能_IC対応
								'atmFunctionIC' : $scope.modalInstanceCtrl2.atmFunctionIC,
								// ATM機能_PASPYチャージ
								'atmFunctionPASPY' : $scope.modalInstanceCtrl2.atmFunctionPASPY,
								// ATM機能_他行幹事
								'atmFunctionOtherBankingAffairs' : $scope.modalInstanceCtrl2.atmFunctionOtherBankingAffairs,
								// 座標_経度
								'longitude' : $scope.modalInstanceCtrl2.longitude,
								// 座標_緯度
								'latitude' : $scope.modalInstanceCtrl2.latitude,
								// 備考①
								'comment1' : $scope.modalInstanceCtrl2.comment1,
								// 備考②
								'comment2' : $scope.modalInstanceCtrl2.comment2,
								// 備考③
								'comment3' : $scope.modalInstanceCtrl2.comment3,
								// 備考④
								'comment4' : $scope.modalInstanceCtrl2.comment4,
								// 備考⑤
								'comment5' : $scope.modalInstanceCtrl2.comment5,
								// 開始日時
								'startDateTime' : $scope.modalInstanceCtrl2.startDateTime,
								// 終了日時
								'endDateTime' : $scope.modalInstanceCtrl2.endDateTime,
								// delFlg
								'delFlg' : delFlg

							};
						} else if (bank_cds == "0174") {
							var data = {
								'_id' : key._id,
								"modeType" : key.modeType,

								// 店舗コード_母店番号
								'storeNumber' : $scope.modalInstanceCtrl2.storeNumber,
								// 店舗名_（漢字）
								'storeATMName' : $scope.modalInstanceCtrl2.storeATMName,
								// poi
								'poiStatus' : poiStatus,
								// 座標_経度
								'longitude' : $scope.modalInstanceCtrl2.longitude,
								// 座標_緯度
								'latitude' : $scope.modalInstanceCtrl2.latitude,
								// 所在地_住所
								'address' : $scope.modalInstanceCtrl2.address,
								// 廃止店番
								'delStoreNumber' : $scope.modalInstanceCtrl2.delStoreNumber,
								// 店舗区分
								'typeKbn' : type,
								// 店舗名_（カナ）
								'kanaStoreATMName' : $scope.modalInstanceCtrl2.kanaStoreATMName,
								// 所在地_郵便番号
								'postCode' : $scope.modalInstanceCtrl2.postCode,
								// 電話番号
								'teleNumber' : $scope.modalInstanceCtrl2.teleNumber,
								// ATM営業時間_平日始業
								'atmOpenStartHour' : $scope.modalInstanceCtrl2.atmOpenStartHour,
								'atmOpenStartMinute' : $scope.modalInstanceCtrl2.atmOpenStartMinute,
								// ATM営業時間_平日終業
								'atmOpenEndHour' : $scope.modalInstanceCtrl2.atmOpenEndHour,
								'atmOpenEndMinute' : $scope.modalInstanceCtrl2.atmOpenEndMinute,
								// ATM営業時間_土曜日始業
								'atmOpenStartHour_SAT' : $scope.modalInstanceCtrl2.atmOpenStartHour_SAT,
								'atmOpenStartMinute_SAT' : $scope.modalInstanceCtrl2.atmOpenStartMinute_SAT,
								// ATM営業時間_土曜日終業
								'atmOpenEndHour_SAT' : $scope.modalInstanceCtrl2.atmOpenEndHour_SAT,
								'atmOpenEndMinute_SAT' : $scope.modalInstanceCtrl2.atmOpenEndMinute_SAT,
								// ATM営業時間_日曜日始業
								'atmOpenStartHour_SUN' : $scope.modalInstanceCtrl2.atmOpenStartHour_SUN,
								'atmOpenStartMinute_SUN' : $scope.modalInstanceCtrl2.atmOpenStartMinute_SUN,
								// ATM営業時間_日曜日終業
								'atmOpenEndHour_SUN' : $scope.modalInstanceCtrl2.atmOpenEndHour_SUN,
								'atmOpenEndMinute_SUN' : $scope.modalInstanceCtrl2.atmOpenEndMinute_SUN,
								'atmComment1' : $scope.modalInstanceCtrl2.atmComment1,
								'atmComment2' : $scope.modalInstanceCtrl2.atmComment2,
								// 窓口営業時間_平日始業
								'windowOpenStartHour' : $scope.modalInstanceCtrl2.windowOpenStartHour,
								'windowOpenStartMinute' : $scope.modalInstanceCtrl2.windowOpenStartMinute,
								// 窓口営業時間_平日終業
								'windowOpenEndHour' : $scope.modalInstanceCtrl2.windowOpenEndHour,
								'windowOpenEndMinute' : $scope.modalInstanceCtrl2.windowOpenEndMinute,
								// 窓口営業時間_土曜日始業
								'windowOpenStartHour_SAT' : $scope.modalInstanceCtrl2.windowOpenStartHour_SAT,
								'windowOpenStartMinute_SAT' : $scope.modalInstanceCtrl2.windowOpenStartMinute_SAT,
								// 窓口営業時間_土曜日終業
								'windowOpenEndHour_SAT' : $scope.modalInstanceCtrl2.windowOpenEndHour_SAT,
								'windowOpenEndMinute_SAT' : $scope.modalInstanceCtrl2.windowOpenEndMinute_SAT,
								// 窓口営業時間_日曜日始業
								'windowOpenStartHour_SUN' : $scope.modalInstanceCtrl2.windowOpenStartHour_SUN,
								'windowOpenStartMinute_SUN' : $scope.modalInstanceCtrl2.windowOpenStartMinute_SUN,
								// 窓口営業時間_日曜日終業
								'windowOpenEndHour_SUN' : $scope.modalInstanceCtrl2.windowOpenEndHour_SUN,
								'windowOpenEndMinute_SUN' : $scope.modalInstanceCtrl2.windowOpenEndMinute_SUN,
								'conversionMachineStartHour' : $scope.modalInstanceCtrl2.conversionMachineStartHour,
								'conversionMachineStartMinute' : $scope.modalInstanceCtrl2.conversionMachineStartMinute,
								'conversionMachineEndHour' : $scope.modalInstanceCtrl2.conversionMachineEndHour,
								'conversionMachineEndMinute' : $scope.modalInstanceCtrl2.conversionMachineEndMinute,
								'conversionMachineStartHour_holiday' : $scope.modalInstanceCtrl2.conversionMachineStartHour_holiday,
								'conversionMachineStartMinute_holiday' : $scope.modalInstanceCtrl2.conversionMachineStartMinute_holiday,
								'conversionMachineEndHour_holiday' : $scope.modalInstanceCtrl2.conversionMachineEndHour_holiday,
								'conversionMachineEndMinute_holiday' : $scope.modalInstanceCtrl2.conversionMachineEndMinute_holiday,
								'accountMachineStartTime' : accountMachineStartTime,
								'accountMachineStartTime_SAT' : accountMachineStartTime_SAT,
								'accountMachineStartTime_SUN' : accountMachineStartTime_SUN,
								'autoLoanMachineStartHour' : $scope.modalInstanceCtrl2.autoLoanMachineStartHour,
								'autoLoanMachineStartMinute' : $scope.modalInstanceCtrl2.autoLoanMachineStartMinute,
								'autoLoanMachineEndHour' : $scope.modalInstanceCtrl2.autoLoanMachineEndHour,
								'autoLoanMachineEndMinute' : $scope.modalInstanceCtrl2.autoLoanMachineEndMinute,
								'autoLoanMachineStartHour_holiday' : $scope.modalInstanceCtrl2.autoLoanMachineStartHour_holiday,
								'autoLoanMachineStartMinute_holiday' : $scope.modalInstanceCtrl2.autoLoanMachineStartMinute_holiday,
								'autoLoanMachineEndHour_holiday' : $scope.modalInstanceCtrl2.autoLoanMachineEndHour_holiday,
								'autoLoanMachineEndMinute_holiday' : $scope.modalInstanceCtrl2.autoLoanMachineEndMinute_holiday,
								'autoLoanMachineFlag' : autoLoanMachineFlag,
								'loanMachineStartHour' : $scope.modalInstanceCtrl2.loanMachineStartHour,
								'loanMachineStartMinute' : $scope.modalInstanceCtrl2.loanMachineStartMinute,
								'loanMachineEndHour' : $scope.modalInstanceCtrl2.loanMachineEndHour,
								'loanMachineEndMinute' : $scope.modalInstanceCtrl2.loanMachineEndMinute,
								'loanMachineStartHour_holiday' : $scope.modalInstanceCtrl2.loanMachineStartHour_holiday,
								'loanMachineStartMinute_holiday' : $scope.modalInstanceCtrl2.loanMachineStartMinute_holiday,
								'loanMachineEndHour_holiday' : $scope.modalInstanceCtrl2.loanMachineEndHour_holiday,
								'loanMachineEndMinute_holiday' : $scope.modalInstanceCtrl2.loanMachineEndMinute_holiday,
								'loanMachineFlag' : loanMachineFlag,
								'aed' : aed,
								'internationalStore' : internationalStore,
								// 備考①
								'comment1' : $scope.modalInstanceCtrl2.comment1,
								// 備考②
								'comment2' : $scope.modalInstanceCtrl2.comment2,
								'atmCount' : iyoAtmCount,
								'parkCount' : $scope.modalInstanceCtrl2.parkCount,
								'parkComment' : $scope.modalInstanceCtrl2.parkComment,
								'toilet' : toilet,
								'serviceConversionStore' : serviceConversionStore,
								'wheelChair' : wheelChair,
								'wheelChairStore' : wheelChairStore,
								'currentStation1' : $scope.modalInstanceCtrl2.currentStation1,
								'currentStationDistance1' : $scope.modalInstanceCtrl2.currentStationDistance1,
								'currentStationTime1' : $scope.modalInstanceCtrl2.currentStationTime1,
								'currentStation2' : $scope.modalInstanceCtrl2.currentStation2,
								'currentStationDistance2' : $scope.modalInstanceCtrl2.currentStationDistance2,
								'currentStationTime2' : $scope.modalInstanceCtrl2.currentStationTime2,
								'currentStation3' : $scope.modalInstanceCtrl2.currentStation3,
								'currentStationDistance3' : $scope.modalInstanceCtrl2.currentStationDistance3,
								'currentStationTime3' : $scope.modalInstanceCtrl2.currentStationTime3,
								'message' : $scope.modalInstanceCtrl2.message,
								'image' : $scope.modalInstanceCtrl2.image,
								'imageUrl' : $scope.modalInstanceCtrl2.imageUrl,
								'icon' : $scope.modalInstanceCtrl2.icon,
								'finalUpdateTime' : "",
								// delFlg
								'delFlg' : delFlg
							};
						} else if (bank_cds == "0173") {
							var data = {
								'_id' : key._id,
								"modeType" : key.modeType,
								"typeKbn" : $scope.modalInstanceCtrl2.type,
								"area" : $scope.modalInstanceCtrl2.area,
								"storeNumber" : $scope.modalInstanceCtrl2.storeNumber,
								"storeName" : $scope.modalInstanceCtrl2.storeName,
								"atmAddress" : $scope.modalInstanceCtrl2.atmAddress,
								"manageStore" : $scope.modalInstanceCtrl2.manageStore,
								"postCode" : $scope.modalInstanceCtrl2.postCode,
								"address" : $scope.modalInstanceCtrl2.address,
								"teleNumber" : $scope.modalInstanceCtrl2.teleNumber,
								"serviceConversionIn" : $scope.modalInstanceCtrl2.serviceConversionIn,
								"serviceConversionOut" : $scope.modalInstanceCtrl2.serviceConversionOut,
								"trustAgent" : $scope.modalInstanceCtrl2.trustAgent,
								"policeCompany" : $scope.modalInstanceCtrl2.policeCompany,
								"conversionMachine" : $scope.modalInstanceCtrl2.conversionMachine,
								"accountMachine" : $scope.modalInstanceCtrl2.accountMachine,
								"loanMachine" : $scope.modalInstanceCtrl2.loanMachine,
								"storeOpenStartHour" : $scope.modalInstanceCtrl2.storeOpenStartHour,
								"storeOpenEndHour" : $scope.modalInstanceCtrl2.storeOpenEndHour,
								"storeOpenStartHour_SAT" : $scope.modalInstanceCtrl2.storeOpenStartHour_SAT,
								"storeOpenEndHour_SAT" : $scope.modalInstanceCtrl2.storeOpenEndHour_SAT,
								"storeOpenStartHour_SUN" : $scope.modalInstanceCtrl2.storeOpenStartHour_SUN,
								"storeOpenEndHour_SUN" : $scope.modalInstanceCtrl2.storeOpenEndHour_SUN,
								"atmStartHour" : $scope.modalInstanceCtrl2.atmOpenStartHour,
								"atmEndHour" : $scope.modalInstanceCtrl2.atmOpenEndHour,
								"atmStartHour_SAT" : $scope.modalInstanceCtrl2.atmOpenStartHour_SAT,
								"atmEndHour_SAT" : $scope.modalInstanceCtrl2.atmOpenEndHour_SAT,
								"atmStartHour_SUN" : $scope.modalInstanceCtrl2.atmOpenStartHour_SUN,
								"atmEndHour_SUN" : $scope.modalInstanceCtrl2.atmOpenEndHour_SUN,
								"storeOpenStartMinute" : $scope.modalInstanceCtrl2.storeOpenStartMinute,
								"storeOpenEndMinute" : $scope.modalInstanceCtrl2.storeOpenEndMinute,
								"storeOpenStartMinute_SAT" : $scope.modalInstanceCtrl2.storeOpenStartMinute_SAT,
								"storeOpenEndMinute_SAT" : $scope.modalInstanceCtrl2.storeOpenEndMinute_SAT,
								"storeOpenStartMinute_SUN" : $scope.modalInstanceCtrl2.storeOpenStartMinute_SUN,
								"storeOpenEndMinute_SUN" : $scope.modalInstanceCtrl2.storeOpenEndMinute_SUN,
								"atmStartMinute" : $scope.modalInstanceCtrl2.atmOpenStartMinute,
								"atmEndMinute" : $scope.modalInstanceCtrl2.atmOpenEndMinute,
								"atmStartMinute_SAT" : $scope.modalInstanceCtrl2.atmOpenStartMinute_SAT,
								"atmEndMinute_SAT" : $scope.modalInstanceCtrl2.atmOpenEndMinute_SAT,
								"atmStartMinute_SUN" : $scope.modalInstanceCtrl2.atmOpenStartMinute_SUN,
								"atmEndMinute_SUN" : $scope.modalInstanceCtrl2.atmOpenEndMinute_SUN,
								"latitude" : $scope.modalInstanceCtrl2.latitude,
								"longitude" : $scope.modalInstanceCtrl2.longitude,
								"delFlg" : delFlg
							};
						}
						var data = storeATMListService.dataUpd(url, data);
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
												$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
											} else {
												if (key.modeType == "1") {
													swal("登録しました。", "",
															"success");
												} else {
													swal("更新しました。", "",
															"success");
												}

												$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
												$uibModalInstance
														.close({
															'typeKbn' : $scope.modalInstanceCtrl2.typeKbn,
															'address' : $scope.modalInstanceCtrl2.address,
															'teleNumber' : $scope.modalInstanceCtrl2.teleNumber,
															'storeATMName' : $scope.modalInstanceCtrl2.storeATMName,
															'kanaStoreATMName' : $scope.modalInstanceCtrl2.kanaStoreATMName,
															'windowOpenStartTime' : $scope.modalInstanceCtrl2.windowOpenStartTime,
															'longitude' : $scope.modalInstanceCtrl2.longitude,
															'latitude' : $scope.modalInstanceCtrl2.latitude,
															'storeOpenStartTime' : $scope.modalInstanceCtrl2.storeOpenStartTime
														});

											}
										}, function(e) {
											console.log(e);
										});
					};
					function iYoTimeCheck(startHour, startMinute, endHour,
							endMinute) {
						if (startHour == "" || startMinute == ""
								|| endHour == "" || endMinute == "") {
							$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
							return false;
						} else {
							if (startHour > endHour) {
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return false;
							} else {
								if (startHour == endHour
										&& startMinute >= endMinute) {
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return false;
								}
							}
						}
					}
					function atmTimeCheck() {
						// ATMの場合
						if ($scope.modalInstanceCtrl2.atmStartHour == ""
								|| $scope.modalInstanceCtrl2.atmStartMinute == ""
								|| $scope.modalInstanceCtrl2.atmEndHour == ""
								|| $scope.modalInstanceCtrl2.atmEndMinute == "") {
							swal("ATM平日営業時間を入力してください。", "", "warning");
							$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
							return false;
						} else {
							if ($scope.modalInstanceCtrl2.atmStartHour > $scope.modalInstanceCtrl2.atmEndHour) {
								swal("正しいATM平日営業時間を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return false;
							} else {
								if ($scope.modalInstanceCtrl2.atmStartHour == $scope.modalInstanceCtrl2.atmEndHour
										&& $scope.modalInstanceCtrl2.atmStartMinute >= $scope.modalInstanceCtrl2.atmEndMinute) {
									swal("正しいATM平日営業時間を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return false;
								}
							}
						}
						// ATM土曜日営業時間
						if ($scope.modalInstanceCtrl2.atmStartHour_SAT == ""
								&& $scope.modalInstanceCtrl2.atmStartMinute_SAT == ""
								&& $scope.modalInstanceCtrl2.atmEndHour_SAT == ""
								&& $scope.modalInstanceCtrl2.atmEndMinute_SAT == "") {
						} else {
							if ($scope.modalInstanceCtrl2.atmStartHour_SAT != ""
									&& $scope.modalInstanceCtrl2.atmStartMinute_SAT != ""
									&& $scope.modalInstanceCtrl2.atmEndHour_SAT != ""
									&& $scope.modalInstanceCtrl2.atmEndMinute_SAT != "") {
								if ($scope.modalInstanceCtrl2.atmStartHour_SAT > $scope.modalInstanceCtrl2.atmEndHour_SAT) {
									swal("正しいATM土曜日営業時間を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return false;
								} else {
									if ($scope.modalInstanceCtrl2.atmStartHour_SAT == $scope.modalInstanceCtrl2.atmEndHour_SAT
											&& $scope.modalInstanceCtrl2.atmStartMinute_SAT >= $scope.modalInstanceCtrl2.atmEndMinute_SAT) {
										swal("正しいATM土曜日営業時間を入力してください。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return false;
									}
								}
							} else {
								swal("正しいATM土曜日営業時間を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return false;
							}
						}
						// ATM日曜日営業時間
						if ($scope.modalInstanceCtrl2.atmStartHour_SUN == ""
								&& $scope.modalInstanceCtrl2.atmStartMinute_SUN == ""
								&& $scope.modalInstanceCtrl2.atmEndHour_SUN == ""
								&& $scope.modalInstanceCtrl2.atmEndMinute_SUN == "") {
						} else {
							if ($scope.modalInstanceCtrl2.atmStartHour_SUN != ""
									&& $scope.modalInstanceCtrl2.atmStartMinute_SUN != ""
									&& $scope.modalInstanceCtrl2.atmEndHour_SUN != ""
									&& $scope.modalInstanceCtrl2.atmEndMinute_SUN != "") {
								if ($scope.modalInstanceCtrl2.atmStartHour_SUN > $scope.modalInstanceCtrl2.atmEndHour_SUN) {
									swal("正しいATM日曜日営業時間を入力してください。", "",
											"warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return false;
								} else {
									if ($scope.modalInstanceCtrl2.atmStartHour_SUN == $scope.modalInstanceCtrl2.atmEndHour_SUN
											&& $scope.modalInstanceCtrl2.atmStartMinute_SUN >= $scope.modalInstanceCtrl2.atmEndMinute_SUN) {
										swal("正しいATM日曜日営業時間を入力してください。", "",
												"warning");
										$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
										return false;
									}
								}
								if ($scope.modalInstanceCtrl2.atmCount == null) {
									swal("正しいATM設置台数を入力してください。", "", "warning");
									$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
									return false;

								}
							} else {
								swal("正しいATM日曜日営業時間を入力してください。", "", "warning");
								$scope.modalInstanceCtrl2.UpdbtnDisabled = false;
								return false;
							}
						}
					}
				});
// Map
insightApp.directive('storeAtmMap', function($compile) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var ymap = new Y.Map("map", {
				configure : {
					doubleClickZoom : false,
					scrollWheelZoom : true,
					dragging : true
				}
			});
			ymap.drawMap(new Y.LatLng(scope.latitude, scope.longitude), 17,
					Y.LayerSetId.NORMAL);
			var marker_a;
			marker_a = new Y.Marker(new Y.LatLng(scope.latitude,
					scope.longitude));
			ymap.addFeature(marker_a);
			ymap.bind("click", function(latLng) {
				ymap.removeFeature(marker_a);
				$('#output').html(latLng.Lat + "" + latLng.Lon);
				marker_a = new Y.Marker(new Y.LatLng(latLng.Lat, latLng.Lon));
				ymap.addFeature(marker_a);
				scope.modalInstanceCtrl2.longitude = latLng.Lon;
				scope.modalInstanceCtrl2.latitude = latLng.Lat;
				scope.$apply();
			});
		}
	}
})