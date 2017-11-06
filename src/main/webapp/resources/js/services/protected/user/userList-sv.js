// userList-sv.js
insightApp.service('userListService', [ '$http', function($http) {
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
		},
		setListstatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
		setUserListstatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data,
			});
		}
	};
} ])
.filter(
		'loginStatusTitle',
		function(insightService) {
			var loginStatus = {};
			angular.forEach(insightService.getInsightData("loginStatusData"),
					function(data) {
						loginStatus[data.id] = data.title;
					});
			return function(input) {
				return loginStatus[input];
			};
		});
