insightApp.service('documentListService', [ '$http', function($http) {
	return {
		getListData : function(url, params) {

			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		},
		setListstatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		}
	};
} ]).filter(
		'documentStatusTitle',
		function(insightService) {
			var status = {};
			angular.forEach(insightService.getInsightData("documentStatusData"),
					function(data) {
						status[data.id] = data.title;
					});
			return function(input) {
				return status[input];
			};
		}).filter(
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
			angular.forEach(insightService
					.getInsightData("applicationReasonData"), function(data) {
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
		'documentPurposeTitle',
		function(insightService) {
			var accountPurpose = {};
			angular.forEach(insightService
					.getInsightData("documentPurposeData"), function(data) {
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
