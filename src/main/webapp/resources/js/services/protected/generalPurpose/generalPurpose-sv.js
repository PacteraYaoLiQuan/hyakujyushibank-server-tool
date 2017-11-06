// userList-sv.js
insightApp.service('generalPurposeService', [ '$http', function($http) {
	return {
		getListData : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
			});
		},
	};
} ])

