// new-account-sv.js
insightApp.service('webAccountListService', ['$http', function($http) {
    return {
        getListData : function(url) {
            return $http({'method': "GET", 'url': url});
        },
    	getDetailData : function(url,params) {
	        return $http({'method': "GET", 'url': url , "params":params});
	    },
    	print : function(url,params) {
	        return $http({'method': "GET", 'url': url , "params":params});
	    },
        updDetailData : function(url,dataParams) {
	        return $http({'method': "PUT", 'url': url , "data":dataParams});
	    }
    };
} ]);
