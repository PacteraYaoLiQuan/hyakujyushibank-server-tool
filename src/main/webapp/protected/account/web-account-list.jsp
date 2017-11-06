<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container">
    <div class="block-header">
        <h2>申込一覧</h2>
    </div>

    <div class="card">
        <div class="card-header">
            <h2>出力条件</h2>
        </div>
        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-5 m-b-15">
                    <div class="input-group">
                        <span class="input-group-addon"><i
                            class="zmdi zmdi-view-compact zmdi-hc-fw"></i></span>
                        <select chosen multiple data-placeholder="ステータス"
                            class="w-100" ng-model="newAccountCtrl.selectStatus"  ng-options='item.id as item.title for item in newAccountCtrl.statusArr'>
                        </select>
                    </div>
                </div>
                <div class="col-sm-5 m-b-15">
                    <div class="date-picker input-group dp-blue"
                        ng-class="{ 'is-opened': newAccountCtrl.datepicker.opened2 == true }">
                        <span class="input-group-addon"><i
                            class="zmdi zmdi-calendar ma-icon"></i></span>
                        <div class="fg-line"
                            ng-class="{ 'fg-toggled': newAccountCtrl.datepicker.opened2 == true }">
                            <input type="text" class="form-control"
                                placeholder="申込受付日付"
                                ng-click="newAccountCtrl.datepicker.open($event, 'opened2')"
                                ng-model="newAccountCtrl.datepicker.dtPopup2"
                                uib-datepicker-popup="{{newAccountCtrl.datepicker.format}}"
                                show-weeks="false"
                                is-open="newAccountCtrl.datepicker.opened2"
                                min-date="newAccountCtrl.datepicker.minDate"
                                datepicker-options="newAccountCtrl.datepicker.dateOptions"
                                close-text="Close" />
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="btn-colors btn-demo text-center">
                        <button ng-disabled="newAccountCtrl.btnDisabled" ng-click="newAccountCtrl.cc()" class="btn bgm-lightblue">一覧出力</button>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="card">
        <div class="card-header">
            <h2>&nbsp;</h2>
        </div>
        <div class="card-body">
            <div class="table-responsive" pagination-customize-buttons="ss">
                <table ng-table="newAccountCtrl.table.sortAndFilter"
                    template-header="template/tables/header.html"
                    template-pagination="template/tables/pagination.html"
                    class="table table-striped table-vmiddle">
                    <tr ng-repeat="w in $data">
                        <td filter="{ '': 'newAccountCtrl/table/sortAndFilter/checkItemAll.html' }"><label
                            class="checkbox checkbox-inline m-r-20">
                                <input type="checkbox" value=""
                                ng-model="w.select">
                                <i class="input-helper"></i>
                        </label></td>
                        <td data-title="'受付番号'" filter="{ 'receiptId': 'text'}"
                            sortable="'receiptId'">{{w.receiptId}}</td>
                        <td data-title="'受付日付'"
                            filter="{ 'receiptDate': 'text' }"
                            sortable="'receiptDate'">{{w.receiptDate}}</td>
                        <td data-title="'氏名'"
                            filter="{ 'name': 'text' }"
                            sortable="'name'">{{w.name}}</td>
                        <td data-title="'ステータス'"
                            filter="{ 'status': 'select' }"
                            sortable="'status'"
                            filter-data="newAccountCtrl.table.filterListStatus">{{w.status}}</td>
                        <td data-title="'Push開封状況'"
                            filter="{ 'pushStatus': 'select' }"
                            sortable="'pushStatus'"
                            filter-data="newAccountCtrl.table.filterListPushStatus">{{w.pushStatus}}</td>
                        <td data-title="'帳票出力回数'"
                            filter="{ 'billOutputCount': 'number' }"
                            sortable="'billOutputCount'">{{w.billOutputCount}}</td>
                        <td data-title="'詳細'" filter="{ '': 'newAccountCtrl/table/sortAndFilter/clearFilter.html' }">
                            <button class="btn bgm-lightgreen" cc="{{$index}}"
                                ng-click="newAccountCtrl.table.openNa('na', w)">
                                <i class="zmdi zmdi-menu"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                     <button class="btn bgm-lightgreen" 
                                ng-click="newAccountCtrl.test()">
                                <i class="zmdi zmdi-menu"></i>
                                "test"
                    </button>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <script type="text/ng-template" id="newAccountCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="newAccountCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
    <script type="text/ng-template" id="newAccountCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="newAccountCtrl.table.checkItemAll()" ng-model="newAccountCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
    <script type="text/ng-template" id="newAccountCtrl/table/sortAndFilter/paginationButtonGroup.html">
        <button class="btn bgm-blue m-r-5" customize-button-click="lsOutput">帳票出力</button>
        <button class="btn bgm-red m-l-5">完了消込</button>
    </script>
</div>
<script type="text/ng-template" id="detailPopup.html">
    <div class="container detail-popup">
        <div class="row detail-title">
            <div class="col-sm-12">
                <title class="pull-left">申込詳細</title> <i
                    ng-click="modalInstanceCtrl.ok()"
                    class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
            </div>
        </div>
        <div class="row detail-c-item">
            <div class="col-sm-12">
                <title>申込情報</title>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">受付番号</div>
            </div>
            <div class="col-sm-3">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.receiptId}}</span>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="text-item">受付日時</div>
            </div>
            <div class="col-sm-3">
                <div class="text-item">
                    <span><input type="text" ng-model="modalInstanceCtrl.receiptDate" /></span>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">申込日</div>
            </div>
            <div class="col-sm-8">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.applicationDate}}</span>
                </div>
            </div>
    
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">カード種類</div>
            </div>
            <div class="col-sm-3">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.cardType}}</span>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="text-item">取引種類</div>
            </div>
            <div class="col-sm-3">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.transactionType}}</span>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">利用サービス</div>
            </div>
            <div class="col-sm-3">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.applicationService}}
			</span>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="text-item">申込サービス</div>
            </div>
            <div class="col-sm-3">
                <div class="text-item">
                   <span>{{modalInstanceCtrl.applyService}}</span>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">暗証番号</div>
            </div>
            <div class="col-sm-8">
                <div class="text-item">
                   <span>{{modalInstanceCtrl.password}}</span>
                </div>
            </div>
                <div class="col-sm-2 col-sm-offset-1">
                    <div class="text-item">
                        	ステータス
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <div class="fg-line">
                            <div class="select">
                                <select class="form-control" ng-model="modalInstanceCtrl.selectStatus"  ng-options='item.id as item.title for item in modalInstanceCtrl.statusArr'>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    
        <div class="row detail-c-item">
            <div class="col-sm-12">
                <title>個人情報</title>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">氏名</div>
            </div>
            <div class="col-sm-8">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.name}}</span>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">住所</div>
            </div>
            <div class="col-sm-8">
                <div class="text-item">
                   <span>{{modalInstanceCtrl.address}}</span>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">電話番号</div>
            </div>
            <div class="col-sm-8">
                <div class="text-item">
                    <span>{{modalInstanceCtrl.phoneNumber}}</span>
                </div>
            </div>
        </div>
    
<div class="row detail-c-item">
                <div class="col-sm-12">
                    <title>本人確認書類画像</title>
                </div>
                <div class="col-sm-2 col-sm-offset-1">
                    <div class="text-item">
                        	確認用書類：
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="text-item">
                        <span>{{modalInstanceCtrl.confirmationDocument}}</span>
                    </div>
                </div>
                <div class="col-sm-10 col-sm-offset-1">
                    <img src="img/id-card.jpg">
                </div>
            </div>


            <div class="row detail-c-item">
                <div class="btn-colors btn-demo col-sm-3 col-sm-offset-9">
                    <button class="btn bgm-blue waves-effect" ng-click="modalInstanceCtrl.update()">ステータス更新</button>
                </div>

            </div>


    </div>
</script>