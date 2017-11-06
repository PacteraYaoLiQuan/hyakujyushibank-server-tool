insightApp
		.controller(
				'generalPurposeCtrl',
				function($scope, $timeout, $uibModal, $q, dateFilter,
						NgTableParams, generalPurposeService, insightService,
						strSpliceService) {
					// controllerオブジェクト変数
					$scope.generalPurposeCtrl = {};
					// 一覧モジュール変数
					$scope.generalPurposeCtrl.table = {};
					$scope.generalPurposeCtrl.datepicker = {};
					$scope.generalPurposeCtrl.datepicker.formats = [
							'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
							'yyyy/MM/dd hh:mm', 'shortDate' ];
					$('#dtPopup2')
							.datetimepicker(
									{
										format : "YYYY/MM/DD",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.generalPurposeCtrl.datepicker.formats[1])),
										useCurrent : false
									});
					$('#dtPopup1')
							.datetimepicker(
									{
										format : "YYYY/MM/DD",
										defaultDate : new Date(
												dateFilter(
														new Date(),
														$scope.generalPurposeCtrl.datepicker.formats[1])),
										useCurrent : false
									});
					$('#dtPopup1').val("");
					$('#dtPopup2').val("");
					$scope.generalPurposeCtrl.windowHide = true;
					setTimeout(function() {
						$('#loginDateTimeTo').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					setTimeout(function() {
						$('#loginDateTimeFrom').datetimepicker({
							format : "YYYY/MM/DD",
							useCurrent : false
						});
					});
					$scope.generalPurposeCtrl.datepicker.format = $scope.generalPurposeCtrl.datepicker.formats[1];
					$scope.generalPurposeCtrl.datepicker.open = function(
							$event, opened) {
						$event.preventDefault();
						$event.stopPropagation();
						$scope.generalPurposeCtrl.datepicker[opened] = true;
					};
					$scope.generalPurposeCtrl.datepicker.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};
					$scope.generalPurposeCtrl.table.sortAndFilter = new NgTableParams(
							{
								page : 1, // show first page
								count : 10, // count per page
								sorting : {
									dateFrom : "desc"
								}
							}, {

								counts : [ 10, 20, 30, 50, 100 ],
								dataset : []
							});
					// 検索ボタンを押下
					$scope.generalPurposeCtrl.table.retrievalBtn = function() {
						if ($('#dtPopup1').val() != ""
								&& $('#dtPopup2').val() != "") {
							if ($('#dtPopup1').val() > $('#dtPopup2').val()) {
								swal("正しい表示期間を入力してください。", "", "warning");
								$scope.generalPurposeCtrl.windowHide = true;
								return;
							}
						}
						loadData();
					}
					// 初期化データを取得
					function loadData() {
						$scope.generalPurposeCtrl.windowHide = true;
						// 一覧初期データ取得
						var url = "./../protected/generalPurpose/generalPurposeList";
						var dateFrom = typeof ($("#dtPopup1").val()) == "undefined" ? ""
								: $("#dtPopup1").val()
						var dateTo = typeof ($("#dtPopup2").val()) == "undefined" ? ""
								: $("#dtPopup2").val()
						var data = {
							"dateFrom" : dateFilter(dateFrom, "yyyy/MM/dd"),
							"dateTo" : dateFilter(dateTo, "yyyy/MM/dd")
						};
						var data = generalPurposeService.getListData(url, data);
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
												$scope.generalPurposeCtrl.windowHide = false;
												if (r.data.resultData.generalPurposeInitList != null
														&& r.data.resultData.generalPurposeInitList.length > 0) {
													$scope.generalPurposeCtrl.table.sortAndFilter
															.settings().dataset = r.data.resultData.generalPurposeInitList;
													$scope.generalPurposeCtrl.table.sortAndFilter
															.reload();
												} else {
													$scope.generalPurposeCtrl.windowHide = true;
												}
											}
										}, function(e) {
											console.log(e);
										});
					}
					// 汎用DBダウンロードボタンを押下
					$scope.generalPurposeCtrl.table.csvDownLoadBtn = function(
							item) {
						var deferred = $q.defer();
						var url = "./../protected/generalPurpose/generalPurposeOutCsv";
						var params = {
							'comment1' : item.comment1,
							'contentsID' : item.contentsID,
							'dateFrom' : item.dateFrom,
							'dateTo' : item.dateTo
						};
						var data = generalPurposeService.getListData(url, params);
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
												var url = "./../protected/generalPurpose/csvDownLoad";
												var input = $("<input>");
												var form = $("<form>");
												form.attr('style',
														'display:none');
												form.attr('target', '');
												form.attr('method', 'get');
												form.attr('action', url);
												input.attr("type", "hidden");
												input.attr("name", "date");
												input.attr("value", r.data.resultData.date);
												$('body').append(form);
												form.append(input);
												form.submit();
												form.remove();
											}
										}, function(e) {
											console.log(e);
										});
					}
					$scope.$on();
				})
