insightApp.service('masterDataService', [ '$http', function($http) {
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
		'useFlgTitle',
		function(insightService) {
			var useFlag = {};
			angular.forEach(insightService.getInsightData("useFlgData"),
					function(data) {
						useFlag[data.id] = data.title;
					});
			return function(input) {
				return useFlag[input];
			};
		});