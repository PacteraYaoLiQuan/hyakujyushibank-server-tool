// new-account.js
insightApp.controller('webAccountListCtrl', function($scope, $timeout, $uibModal, NgTableParams, webAccountListService,insightService) {
    // controllerオブジェクト変数
    $scope.newAccountCtrl = {};
    
    // 入力条件のステータス
    $scope.newAccountCtrl.statusArr = insightService.getInsightData("applyStatusData");
    
    // 申込受付日付モジュール変数
    $scope.newAccountCtrl.datepicker = {}

    $scope.newAccountCtrl.datepicker.toggleMin = function() {
        $scope.newAccountCtrl.datepicker.minDate = $scope.newAccountCtrl.datepicker.minDate ? null
                : new Date();
    };
    $scope.newAccountCtrl.datepicker.toggleMin();

    $scope.newAccountCtrl.datepicker.open = function($event,
            opened) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.newAccountCtrl.datepicker[opened] = true;
    };

    $scope.newAccountCtrl.datepicker.dateOptions = {
        formatYear : 'yy',
        startingDay : 1
    };

    $scope.newAccountCtrl.datepicker.formats = [
            'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy',
            'shortDate' ];
    $scope.newAccountCtrl.datepicker.format = $scope.newAccountCtrl.datepicker.formats[0];

    // 一覧モジュール変数
    $scope.newAccountCtrl.table = {};
    
    $scope.newAccountCtrl.table.checkboxes = {
        checked: false
    };
    $scope.newAccountCtrl.table.checkItemAll = function() {
        angular.forEach($scope.newAccountCtrl.table.sortAndFilter.settings().dataset, function(item) {
            item.select = $scope.newAccountCtrl.table.checkboxes.checked;
        });
        console.log($scope.newAccountCtrl.table.checkboxes);
    }

    $scope.newAccountCtrl.table.lsOutput = function() {
        console.log($scope.newAccountCtrl.table.checkboxes);
    }
    
    //ステータス一覧を取得
    $scope.newAccountCtrl.table.filterListStatus = insightService.getInsightData("applyStatusData");
    //Push開封状況一覧を取得
    $scope.newAccountCtrl.table.filterListPushStatus = insightService.getInsightData("applyPushStatusData");

    // 一覧データ変数
//    $scope.newAccountCtrl.table.listData = [];
    
    $scope.newAccountCtrl.table.sortAndFilter = new NgTableParams({
        page : 1, // show first page
        count : 10, // count per page
        sorting : {
            id : "desc"
        }
    }, {
        paginationCustomizeButtons: "newAccountCtrl/table/sortAndFilter/paginationButtonGroup.html",
        counts : [ 10, 20, 30, 50, 100 ],
        dataset : []
    });
    
    // 一覧出力ボタン二重押下制御
    $scope.newAccountCtrl.btnDisabled = false;
    // TODO テスト方法
    $scope.newAccountCtrl.cc = function() {
        // btnDisabled
        $scope.newAccountCtrl.btnDisabled = true;
        $timeout(function(){
            $scope.newAccountCtrl.btnDisabled = false;
        }, 2000)
        
    }
    
    $scope.newAccountCtrl.table.openNa = function (size, item) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'detailPopup.html',
            controller: 'liuModalInstanceCtrl',
            size: size,
            resolve: {
                key: function () {
                    return {'_id': item._id};
                }
            }
        });
        
        modalInstance.result.then(function (result) {
            item.receiptId = result.receiptId,
            item.receiptDate = result.receiptDate,
            item.name = result.name,
            item.status = result.status
        }, function (reason) {
            console.log('Modal dismissed at: ' + new Date() + "  ==>  " + reason);
        });
    };

    $scope.$on();
    /**
    // watch for check all checkbox
    $scope.$watch(function() {
        return $scope.newAccountCtrl.table.checkboxes.checked;
    }, function(value) {
        console.log($scope.newAccountCtrl.table.checkboxes);
        angular.forEach($scope.newAccountCtrl.table.sortAndFilter.settings().dataset, function(item) {
            $scope.newAccountCtrl.table.checkboxes.items[item.id] = value;
        });
    });
    // watch for data checkboxes
    $scope.$watch(function() {
        return $scope.newAccountCtrl.table.checkboxes.items;
    }, function(values) {
        var checked = 0, unchecked = 0,
              total = simpleList.length;
        angular.forEach(simpleList, function(item) {
            checked   +=  (self.checkboxes.items[item.id]) || 0;
            unchecked += (!self.checkboxes.items[item.id]) || 0;
        });
        if ((unchecked == 0) || (checked == 0)) {
            self.checkboxes.checked = (checked == total);
        }
        // grayed checkbox
        angular.element($element[0].getElementsByClassName("select-all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    }, true);
    **/
    
    function loadData() {
        // 一覧データ取得
        var url = "./../protected/account/accountAppList";
//        var params = {"id": [1, 2, 3, 4, 5],
//                      "name": "abc"};
        var data = webAccountListService.getListData(url);
        data.then(function(r) {
            $scope.newAccountCtrl.table.sortAndFilter.settings().dataset = r.data.resultData.accountAppList;
            $scope.newAccountCtrl.table.sortAndFilter.reload();
        }, function(e) {
            console.log(e);
        });
    }
    loadData();
    
    
    $scope.newAccountCtrl.test = function(){
    	 $scope.newAccountCtrl.key = [];
    	 angular.forEach($scope.newAccountCtrl.table.sortAndFilter.settings().dataset, function(item) {
             if(item.select){
//            	 $scope.newAccountCtrl.key =  $scope.newAccountCtrl.key + item._id + ",";
            	 $scope.newAccountCtrl.key.push(item._id);
             }
         });
    	 var url = "./../protected/report";
    	 var params = {"key": $scope.newAccountCtrl.key};
    	 var data = webAccountListService.print(url,params);
    };
    
    
})
.controller('liuModalInstanceCtrl', function($scope, $uibModalInstance, key, webAccountListService , insightService){
    $scope.modalInstanceCtrl = {};
    var url = "./../protected/account/accountAppDetail";
    var params = {"_id" : key._id};
    var data = webAccountListService.getDetailData(url,params);
    data.then(function(r) {
    	$scope.modalInstanceCtrl.name = r.data.resultData.name;
        $scope.modalInstanceCtrl.receiptId = r.data.resultData.receiptId;
        $scope.modalInstanceCtrl.receiptDate = r.data.resultData.receiptDate;
        $scope.modalInstanceCtrl.cardType = r.data.resultData.cardType;
        $scope.modalInstanceCtrl.applicationDate = r.data.resultData.applicationDate;
        $scope.modalInstanceCtrl.transactionType = r.data.resultData.transactionType;
        $scope.modalInstanceCtrl.applicationService = r.data.resultData.applicationService;
        $scope.modalInstanceCtrl.applyService = r.data.resultData.applyService;
        $scope.modalInstanceCtrl.address = r.data.resultData.address;
        $scope.modalInstanceCtrl.phoneNumber = r.data.resultData.phoneNumber;
        $scope.modalInstanceCtrl.password=r.data.resultData.password;
        $scope.modalInstanceCtrl.confirmationDocument=r.data.resultData.confirmationDocument;
        $scope.modalInstanceCtrl.selectStatus=r.data.resultData.status;
    }, function(e) {
        console.log(e);
    });
    
    //ステータス配列を設定
//    $scope.modalInstanceCtrl.statusArr = [{id:"1",value:"受付"},{id:"2",value:"処理中"},{id:"3",value:"完了"}];
    $scope.modalInstanceCtrl.statusArr = insightService.getInsightData("applyStatusData");

    $scope.modalInstanceCtrl.ok = function () {
        $uibModalInstance.close({
            'date': $scope.modalInstanceCtrl.date
        });
    };

    $scope.modalInstanceCtrl.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    
    //更新ボタンを押下する
    $scope.modalInstanceCtrl.update = function(){
    	var statusId = $scope.modalInstanceCtrl.selectStatus;
    	var dataParams = {"_id" : key._id,"status":statusId};
    	var url = "./../protected/account/accountAppStatusUpd";
        var data = webAccountListService.updDetailData(url,dataParams);
        data.then(function(r) {
            $uibModalInstance.close({
                'receiptId': $scope.modalInstanceCtrl.receiptId,
                'receiptDate':$scope.modalInstanceCtrl.receiptDate,
                'name':$scope.modalInstanceCtrl.name,
                'status':$scope.modalInstanceCtrl.selectStatus,
            });
        }, function(e) {
            console.log(e);
        });
    };
    
})
