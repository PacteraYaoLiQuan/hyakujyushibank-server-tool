// useUserDownLoad-sv.js
insightApp.service('useUserDownLoadService', [ '$http', function($http) {
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
		getDetailMessage : function(url, data) {

			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data
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
		setMessagestatusUpd : function(url, data) {
			return $http({
				'method' : "POST",
				'url' : url,
				'data' : data,
			});
		}
	};
} ])

.filter(
		'userTypeTitle',
		function(insightService) {
			var userType = {};
			angular.forEach(insightService.getInsightData("userTypeData"),
					function(data) {
						userType[data.id] = data.title;
					});
			return function(input) {
				return userType[input];
			};
		})
// 性別
.filter(
		'sexTitle',
		function(insightService) {
			var sex = {};
			angular.forEach(insightService.getInsightData("iyoSexData"),
					function(data) {
						sex[data.id] = data.title;
					});
			return function(input) {
				return sex[input];
			};
		})
// 職業
.filter(
		'occupationTitle',
		function(insightService) {
			var occupation = {};
			angular.forEach(insightService.getInsightData("iyoOccupationData"),
					function(data) {
						occupation[data.id] = data.title;
					});
			return function(input, param) {
				if (input == "9" && param != "") {
					return occupation[input] + "（" + param + "）";
				} else {
					return occupation[input];
				}
			};
		})
// カードの種類
.filter(
		'cardNoListTitle',
		function(insightService) {
			var cardNoListTitle = {};
			angular.forEach(insightService
					.getInsightData("cardNoListTitleData"), function(data) {
				cardNoListTitle[data.id] = data.title;
			});
			return function(input) {
				return cardNoListTitle[input];
			};
		})
// ログインタイプ
.filter(
		'userKeyTitle',
		function(insightService) {
			var userKey = {};
			angular.forEach(insightService.getInsightData("userKeyData"),
					function(data) {
						userKey[data.id] = data.title;
					});
			return function(input) {
				return userKey[input];
			};
		})
