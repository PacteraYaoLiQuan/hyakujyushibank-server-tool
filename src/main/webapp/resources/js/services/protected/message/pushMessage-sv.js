// pushMessage-sv.js
insightApp.service('pushMessageService', [ '$http', function($http) {
	return {
		getListData : function(url, params) {
			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		},

		getListDatas : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},

		setListDelete : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
		getDetailData : function(url, data) {

			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
		dataUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
		setListstatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
		setMessagestatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data,
			});
		}
	};
} ]).filter('pushTypeTitle', function(insightService) {
	var typeKbn = {};
	angular.forEach(insightService.getInsightData("pushType"), function(data) {
		typeKbn[data.id] = data.title;
	});
	return function(input) {
		return typeKbn[input];
	};
}).filter(
		'fileNameJPTitle',
		function(insightService) {
			var fileNameJP = {};
			angular.forEach(insightService.getInsightData("fileNameJPData"),
					function(data) {
						fileNameJP[data.id] = data.title;
					});
			return function(input) {
				return fileNameJP[input];
			};
		});
