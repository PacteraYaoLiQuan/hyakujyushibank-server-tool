// authorityDetail-sv.js
insightApp.service('authorityDetailService', [ '$http', function($http) {
	return {
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
} ])


;
