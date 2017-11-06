insightApp.service('applicationListService', [ '$http', function($http) {
	return {
		getListData : function(url, params) {

			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		},
		setListDelete : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},

		getDetailData : function(url, params) {
			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		},

		dataUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		}

	};
} ]).filter(
		'userFlagTitle',
		function(insightService) {
			var userFlag = {};
			angular.forEach(insightService.getInsightData("userFlagData"),
					function(data) {
						userFlag[data.id] = data.title;
					});
			return function(input) {
				return userFlag[input];
			};
		});
