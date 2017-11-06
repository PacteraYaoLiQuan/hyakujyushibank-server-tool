insightApp.service('fileUploadService', [ '$http', function($http) {
	return {
		fileUpload : function(url, params) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : params
			});
		}
	};
} ])
.filter(
		'useFlagTitle',
		function(insightService) {
			var useFlag = {};
			angular.forEach(insightService.getInsightData("useFlagData"),
					function(data) {
						useFlag[data.id] = data.title;
					});
			return function(input) {
				return useFlag[input];
			};
		});
