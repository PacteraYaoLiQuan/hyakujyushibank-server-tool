// passwordUpd-sv.js
insightApp.service('passwordUpdService', [ '$http', function($http) {
	return {
		dataUpd : function(url, params) {
			return $http({
				'method' : "POST",
				'url' : url,
				'params' : params
			});
		}
	};
} ]);
