// fileList-sv.js
insightApp.service('fileListService', [ '$http', function($http) {
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
		setfileListstatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data,
			});
		}
	};
} ])
.filter(
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
