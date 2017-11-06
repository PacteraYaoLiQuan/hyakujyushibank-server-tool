<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container">
    <div class="block-header">
        <h2>ユーザ一覧</h2>
    </div>

    <div class="card">
        <div class="card-header">
            <h2>&nbsp;</h2>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <!-- <table ng-table="newAccountCtrl.table.sortAndFilter"
                    template-header="template/tables/header.html"
                    template-pagination="template/tables/pagination.html"
                    class="table table-striped table-vmiddle">
                    <tr ng-repeat="w in $data">
                        <td filter="{ '': 'newAccountCtrl/table/sortAndFilter/checkItemAll.html' }"><label
                            class="checkbox checkbox-inline m-r-20">
                                <input type="checkbox" value=""
                                ng-model="newAccountCtrl.table.checkboxes.items[w.id]">
                                <i class="input-helper"></i>
                        </label></td>
                        <td data-title="'受付番号'" filter="{ 'id': 'text'}"
                            sortable="'id'">{{ w.receiptId }}</td>
                        <td data-title="'受付日付'"
                            filter="{ 'date': 'text' }"
                            sortable="'date'">{{ w.receiptDate }}</td>
                        <td data-title="'氏名'"
                            filter="{ 'name': 'text' }"
                            sortable="'name'">{{ w.name }}</td>
                        <td data-title="'ステータス'"
                            filter="{ 'status': 'select' }"
                            sortable="'status'"
                            filter-data="newAccountCtrl.table.filterList">
                            <div class="select">
                                <select class="form-control">
                                    <option
                                        ng-repeat="v in newAccountCtrl.table.filterList　track by v.id"
                                        ng-selected="w.status == v.id">{{v.title}}</option>
                                </select>
                            </div>
                        </td>
                        <td data-title="'帳票出力回数'"
                            filter="{ 'outnum': 'number' }"
                            sortable="'outnum'">{{ w.billOutputCount }}</td>
                        <td data-title="'詳細'" filter="{ '': 'newAccountCtrl/table/sortAndFilter/clearFilter.html' }">
                            <button class="btn bgm-lightgreen"
                                ng-click="newAccountCtrl.table.openNa('na', w)">
                                <i class="zmdi zmdi-menu"></i>
                            </button>
                        </td>
                    </tr>
                </table>
                 -->
            </div>
        </div>
    </div>

</div>
