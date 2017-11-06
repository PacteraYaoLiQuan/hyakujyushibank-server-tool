insightApp.service('accountAppDetailService', [ '$http', function($http) {
	return {
		getDetailData : function(url, params) {

			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		},

		statusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
		pushNotifications : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		}

	};
} ])
// 性別
.filter('sexTitle', function(insightService) {
	var sex = {};
	angular.forEach(insightService.getInsightData("sexData"), function(data) {
		sex[data.id] = data.title;
	});
	return function(input) {
		return sex[input];
	};
})
// カードの種類
.filter(
		'cardTypeTitle',
		function(insightService) {
			var cardType = {};
			angular.forEach(insightService.getInsightData("cardTypeData"),
					function(data) {
						cardType[data.id] = data.title;
					});
			return function(input) {
				return cardType[input];
			};
		})
// 取引種類
.filter(
		'transactionTypeTitle',
		function(insightService) {
			var transactionType = {};
			angular.forEach(insightService
					.getInsightData("transactionTypeData"), function(data) {
				transactionType[data.id] = data.title;
			});
			return function(input) {
				return transactionType[input];
			};
		})
// 利用サービス
.filter(
		'noApplicationServiceTitle',
		function(insightService) {
			var applicationService = {};
			angular.forEach(insightService
					.getInsightData("noApplicationServiceData"),
					function(data) {
						applicationService[data.id] = data.title;
					});
			return function(input) {
				return applicationService[input];
			};
		})
// 申込サービス
.filter(
		'applyServiceTitle',
		function(insightService) {
			var applyService = {};
			angular.forEach(insightService.getInsightData("applyServiceData"),
					function(data) {
						applyService[data.id] = data.title;
					});
			return function(input) {
				return applyService[input];
			};
		})
// ステータス
.filter(
		'statusTitle',
		function(insightService) {
			var status = {};
			angular.forEach(insightService.getInsightData("applyStatusData"),
					function(data) {
						status[data.id] = data.title;
					});
			return function(input) {
				return status[input];
			};
		})
// 商品指定
.filter(
		'goodsAppointTitle',
		function(insightService) {
			var goodsAppoint = {};
			angular.forEach(insightService.getInsightData("goodsAppointData"),
					function(data) {
						goodsAppoint[data.id] = data.title;
					});
			return function(input) {
				return goodsAppoint[input];
			};
		})
// 取引目的
.filter(
		'traPurposesTitle',
		function(insightService) {
			var traPurposes = {};
			angular.forEach(insightService.getInsightData("traPurposesData"),
					function(data) {
						traPurposes[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "6" && param != "") {
					return traPurposes[input] + "（" + param + "）";
				} else {
					return traPurposes[input];
				}
			};
		})
// 職業
.filter(
		'occupationTitle',
		function(insightService) {
			var occupation = {};
			angular.forEach(insightService.getInsightData("occupationData"),
					function(data) {
						occupation[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "90" && param != "") {
					return occupation[input] + "（" + param + "）";
				} else {
					return occupation[input];
				}
			};
		})
// 本人確認書類
.filter(
		'identificationTypeTitle',
		function(insightService) {
			var identificationType = {};
			angular.forEach(insightService
					.getInsightData("identificationTypeData"), function(data) {
				identificationType[data.id] = data.title;
			});
			return function(input) {
				return identificationType[input];
			};
		})
// 生活状況確認書類
.filter(
		'livingConditionsTitle',
		function(insightService) {
			var livingConditions = {};
			angular.forEach(insightService
					.getInsightData("livingConditionsData"), function(data) {
				livingConditions[data.id] = data.title;
			});
			return function(input) {
				return livingConditions[input];
			};
		})
// 既に口座をお持ちの方/ダイレクトバンキングサービスのご契約
.filter('holdTitle', function(insightService) {
	var hold = {};
	angular.forEach(insightService.getInsightData("holdData"), function(data) {
		hold[data.id] = data.title;
	});
	return function(input) {
		return hold[input];
	};
})
// ひろぎんネット支店の口座開設の動機
.filter(
		'accountAppMotiveTitle',
		function(insightService) {
			var accountAppMotive = {};
			angular.forEach(insightService
					.getInsightData("accountAppMotiveData"), function(data) {
				accountAppMotive[data.id] = data.title;
			});
			return function(input, param) {
				if (input == "90" && param != "") {
					return accountAppMotive[input] + "（" + param + "）";
				} else {
					return accountAppMotive[input];
				}
			};
		})
// ひろぎんネット支店を知った経緯
.filter(
		'knowProcessTitle',
		function(insightService) {
			var knowProcess = {};
			angular.forEach(insightService.getInsightData("knowProcessData"),
					function(data) {
						knowProcess[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "90" && param != "") {
					return knowProcess[input] + "（" + param + "）";
				} else {
					return knowProcess[input];
				}
			};
		})
// 性別
.filter(
		'sexKbnTitle',
		function(insightService) {
			var sexKbn = {};
			angular.forEach(insightService.getInsightData("sexKbnData"),
					function(data) {
						sexKbn[data.id] = data.title;
					});
			return function(input) {
				return sexKbn[input];
			};
		})
// 都道府県/勤務先都道府県
.filter(
		'prefecturesCodeTitle',
		function(insightService) {
			var prefecturesCode = {};
			angular.forEach(insightService
					.getInsightData("prefecturesCodeData"), function(data) {
				prefecturesCode[data.id] = data.title;
			});
			return function(input) {
				return prefecturesCode[input];
			};
		})
/*
 * //勤務先都道府県 .filter( 'workPrefecturesCodeTitle', function(insightService) { var
 * workPrefecturesCode = {};
 * angular.forEach(insightService.getInsightData("workPrefecturesCodeData"),
 * function(data) { workPrefecturesCode[data.id] = data.title; }); return
 * function(input) { return workPrefecturesCode[input]; }; })
 */
// 和暦年号
.filter(
		'ordinaryDepositEraKbnTitle',
		function(insightService) {
			var ordinaryDepositEraKbn = {};
			angular.forEach(insightService
					.getInsightData("ordinaryDepositEraKbnData"),
					function(data) {
						ordinaryDepositEraKbn[data.id] = data.title;
					});
			return function(input) {
				return ordinaryDepositEraKbn[input];
			};
		})
// 普通預金の種類
.filter(
		'accountTypeTitle',
		function(insightService) {
			var accountType = {};
			angular.forEach(insightService.getInsightData("accountTypeData"),
					function(data) {
						accountType[data.id] = data.title;
					});
			return function(input) {
				return accountType[input];
			};
		})
// 職業
.filter(
		'jobKbnTitle',
		function(insightService) {
			var jobKbn = {};
			angular.forEach(insightService.getInsightData("jobKbnData"),
					function(data) {
						jobKbn[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "49" && param != "") {
					return jobKbn[input] + "（" + param + "）";
				} else {
					return jobKbn[input];
				}
			};
		})
// 事業内容
.filter(
		'businessTypeTitle',
		function(insightService) {
			var businessType = {};
			angular.forEach(insightService.getInsightData("businessTypeData"),
					function(data) {
						businessType[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "99" && param != "") {
					return businessType[input] + "（" + param + "）";
				} else {
					return businessType[input];
				}
			};
		})
// 通帳デザイン/カードデザイン
.filter(
		'bankbookDesignKbnTitle',
		function(insightService) {
			var bankbookDesignKbn = {};
			angular.forEach(insightService
					.getInsightData("bankbookDesignKbnData"), function(data) {
				bankbookDesignKbn[data.id] = data.title;
			});
			return function(input) {
				return bankbookDesignKbn[input];
			};
		})
// カードデザイン
.filter(
		'cardDesingKbnTitle',
		function(insightService) {
			var cardDesingKbn = {};
			angular.forEach(insightService.getInsightData("cardDesingKbnData"),
					function(data) {
						cardDesingKbn[data.id] = data.title;
					});
			return function(input) {
				return cardDesingKbn[input];
			};
		})
// 口座開設する店舗
.filter(
		'salesOfficeOptionTitle',
		function(insightService) {
			var salesOfficeOption = {};
			angular.forEach(insightService
					.getInsightData("salesOfficeOptionData"), function(data) {
				salesOfficeOption[data.id] = data.title;
			});
			return function(input) {
				return salesOfficeOption[input];
			};
		})
// インターネット支店を知ったきっかけ
.filter(
		'knowProcess2Title',
		function(insightService) {
			var knowProcess = {};
			angular.forEach(insightService.getInsightData("knowProcess2Data"),
					function(data) {
						knowProcess[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "6" && param != "") {
					return knowProcess[input] + "（" + param + "）";
				} else {
					return knowProcess[input];
				}
			};
		})
// 取引目的
.filter(
		'accountPurposeTitle',
		function(insightService) {
			var accountPurpose = {};
			angular.forEach(
					insightService.getInsightData("accountPurposeData"),
					function(data) {
						accountPurpose[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "99" && param != "") {
					return accountPurpose[input] + "（" + param + "）";
				} else {
					return accountPurpose[input];
				}
			};
		}).filter(
		'purposeTitle',
		function(insightService) {
			var accountPurpose = {};
			angular.forEach(insightService.getInsightData("purposeData"),
					function(data) {
						accountPurpose[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "99" && param != "") {
					return accountPurpose[input] + "（" + param + "）";
				} else {
					return accountPurpose[input];
				}
			};
		}).filter(
		'knowProcessTitle',
		function(insightService) {
			var accountPurpose = {};
			angular.forEach(insightService.getInsightData("knowProcessData"),
					function(data) {
						accountPurpose[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "6" && param != "") {
					return accountPurpose[input] + "（" + param + "）";
				} else {
					return accountPurpose[input];
				}
			};
		}).filter(
				'applicationReasonTitle',
				function(insightService) {
					var accountPurpose = {};
					angular.forEach(insightService.getInsightData("applicationReasonData"),
							function(data) {
								accountPurpose[data.id] = data.title;
							});
					return function(input, param) {
						if (input == "99" && param != "") {
							return accountPurpose[input] + "（" + param + "）";
						} else {
							return accountPurpose[input];
						}
					};
				});
