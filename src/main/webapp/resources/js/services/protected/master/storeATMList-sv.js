// storeATMList-sv.js
insightApp.service('storeATMListService', [ '$http', function($http) {
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
		}
	};
} ])

.filter(
		'storeTypeTitle',
		function(insightService) {
			var typeKbn = {};
			angular.forEach(insightService.getInsightData("storeTypeData"),
					function(data) {
						typeKbn[data.id] = data.title;
					});
			return function(input) {
				return typeKbn[input];
			};
		})
.filter(
		'typeKbnTitle',
		function(insightService) {
			var typeKbn = {};
			angular.forEach(insightService.getInsightData("typeKbnData"),
					function(data) {
						typeKbn[data.id] = data.title;
					});
			return function(input) {
				return typeKbn[input];
			};
		})
.filter(
		'typeTitle',
		function(insightService) {
			var typeKbn = {};
			angular.forEach(insightService.getInsightData("typeData"),
					function(data) {
						typeKbn[data.id] = data.title;
					});
			return function(input) {
				return typeKbn[input];
			};
		})
.filter(
		'delFlgTitle',
		function(insightService) {
			var delFlg = {};
			angular.forEach(insightService.getInsightData("delFlgData"),
					function(data) {
						delFlg[data.id] = data.title;
					});
			return function(input) {
				return delFlg[input];
			};

		})
.filter(
		'openFlgTitle',
		function(insightService) {
			var openFlg = {};
			angular.forEach(insightService.getInsightData("openFlgData"),
					function(data) {
						openFlg[data.id] = data.title;
					});
			return function(input) {
				return openFlg[input];
			};

		});