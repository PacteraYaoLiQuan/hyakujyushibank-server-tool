// accountAppList-sv.js
insightApp.service('accountAppListService', [ '$http', function($http) {
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
} ])

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
	.filter(
		'accountStatusTitle',
		function(insightService) {
			var status = {};
			angular.forEach(insightService.getInsightData("accountStatusData"),
					function(data) {
						status[data.id] = data.title;
					});
			return function(input) {
				return status[input];
			};
		})
.filter(
		'pushStatusTitle',
		function(insightService) {
			var pushStatus = {};
			angular.forEach(insightService.getInsightData("pushStatusData"),
					function(data) {
						pushStatus[data.id] = data.title;
					});
			return function(input) {
				return pushStatus[input];
			};
		})
.filter(
		'pushStatusTitleDB',
		function(insightService) {
			var pushStatus = {};
			angular.forEach(insightService.getInsightData("pushStatusDataDB"),
					function(data) {
						pushStatus[data.id] = data.title;
					});
			return function(input) {
				return pushStatus[input];
			};
		})		
.filter(
		'appraisalResultTitle',
		function(insightService) {
			var appraisalResult = {};
			angular.forEach(insightService.getInsightData("appraisalResultData"),
					function(data) {
				appraisalResult[data.id] = data.title;
					});
			return function(input) {
				return appraisalResult[input];
			};
		})
.filter(
		'resultTitle',
		function(insightService) {
			var tc_result = {};
			angular.forEach(insightService.getInsightData("resultData"),
					function(data) {
						tc_result[data.id] = data.title;
					});
			return function(input) {
				return tc_result[input];
			};
		})
.filter(
		'attentionTitle',
		function(insightService) {
			var tc_attention = {};
			angular.forEach(insightService.getInsightData("attentionData"),
					function(data) {
				tc_attention[data.id] = data.title;
					});
			return function(input) {
				return tc_attention[input];
			};
		})
.filter(
		'tacsflagTitle',
		function(insightService) {
			var tc_tacsflag = {};
			angular.forEach(insightService.getInsightData("tacsflagData"),
					function(data) {
				tc_tacsflag[data.id] = data.title;
					});
			return function(input) {
				return tc_tacsflag[input];
			};
		})
.filter(
		'psipTitle',
		function(insightService) {
			var ic_psip = {};
			angular.forEach(insightService.getInsightData("psipData"),
					function(data) {
						ic_psip[data.id] = data.title;
					});
			return function(input) {
				return ic_psip[input];
			};
		})
.filter(
		'proxyTitle',
		function(insightService) {
			var ic_proxy = {};
			angular.forEach(insightService.getInsightData("proxyData"),
					function(data) {
						ic_proxy[data.id] = data.title;
					});
			return function(input) {
				return ic_proxy[input];
			};
		})
.filter(
		'ipThreatTitle',
		function(insightService) {
			var ic_IpThreat = {};
			angular.forEach(insightService.getInsightData("ipThreatData"),
					function(data) {
						ic_IpThreat[data.id] = data.title;
					});
			return function(input) {
				return ic_IpThreat[input];
			};
		})
		.filter(
		'sendStatusTitle',
		function(insightService) {
			var sendStatus = {};
			angular.forEach(insightService.getInsightData("sendStatusData"),
					function(data) {
				sendStatus[data.id] = data.title;
					});
			return function(input) {
				return sendStatus[input];
			};
		})
		.filter(
		'yamagataSendStatusTitle',
		function(insightService) {
			var sendStatus = {};
			angular.forEach(insightService.getInsightData("yamgataSendStatusData"),
					function(data) {
				sendStatus[data.id] = data.title;
					});
			return function(input) {
				return sendStatus[input];
			};
		});
		