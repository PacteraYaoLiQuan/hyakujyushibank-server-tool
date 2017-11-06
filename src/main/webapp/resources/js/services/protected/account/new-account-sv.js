// new-account-sv.js
insightApp.service('newAccountService', function($http) {
    return {
        getListData : function(url) {
            return $http({
                'method' : "GET",
                'url' : url
            });
        }
    };
}).filter('statusTitle', function(insightService) {
    var status = {};
    angular.forEach(insightService.getInsightData("applyStatusData"), function(data){
        status[data.id] = data.title;
    });
    return function(input) {
        return status[input];
    };
});
