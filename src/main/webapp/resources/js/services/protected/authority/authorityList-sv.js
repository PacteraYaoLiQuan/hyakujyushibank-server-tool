// authorityList-sv.js
insightApp.service('authorityListService', [ '$http', function($http) {
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
		}
	};
} ])
;
