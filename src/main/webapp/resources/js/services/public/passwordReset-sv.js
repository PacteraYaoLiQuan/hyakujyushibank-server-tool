// passwordReset-sv.js
insightApp.service('passwordResetService', [ '$http', function($http) {
	return {
		dataReset : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		}
	};
} ]);
