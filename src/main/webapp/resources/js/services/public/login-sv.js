// login-sv.js
insightApp.service('loginService', [ '$http', function($http) {
	return {
		getListData : function(url, params) {

			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		},
		getpasswordResetData :function(url, params){
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : params
			});
		},
		loginData :function(url, params){
			return $http({
				'method' : "GET",
				'url' : url,
				'params' : params
			});
		}
	};
} ]);