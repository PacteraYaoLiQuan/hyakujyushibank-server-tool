<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
.height-size-1 {
	height: 550px;
}

.scrool-size-1 {
	height: 600px;
}

.scrool-size-2 {
	height: 400px;
}

.modal-size {
	width: 75%;
}

.btn-height {
	line-height: 36px;
	width: 128px;
}

.ng-table .form-control {
	min-width: 50px;
	padding: 10px 5px;
}

.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
	.table>tbody>tr>td, .table>tfoot>tr>td {
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 10px;
	padding-top: 10px;
}
</style>
<div class="container">
	<div class="block-header">
		<h2>汎用DB＆データダウンロード</h2>
		<input type="hidden" id="generalPurpose" name="generalPurpose"
			ng-model="generalPurposeCtrl.generalPurpose"
			value="${sessionScope.GeneralPurpose}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
		<!-- 	<span class="input-group-addon" style="font-size: 15px; width: 10px;">検索</span> -->
			<div class="row">
				<div class="col-sm-3">
					<div class="input-group form-group dp-blue">
						<span class="input-group-addon">有効期間：</span>
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup1'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
				<div class="col-sm-1">
					<span class="input-group-addon">～</span>
				</div>
				<div class="col-sm-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup2'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
				<div class="col-sm-2">
					<button ng-disabled="generalPurposeCtrl.retrievalBtn"
						style="width: 128px;" class="btn bgm-blue m-l-5"
						ng-click="generalPurposeCtrl.table.retrievalBtn()">検索</button>
				</div>
			</div>
		</div>
	</div>

	<div class="card" ng-hide="generalPurposeCtrl.windowHide">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="generalPurposeCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="46%" />
						<col width="15%" />
						<col width="15%" />
						<col width="12%" />
						<col width="12%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td data-title="'キャンペン'"
							filter="{ 'comment1': 'ng-table/filters/text-claer-2.html'}"
							sortable="'comment1'">{{ w.comment1 }}</td>
						<td style="width: 1px;" data-title="'有効期間FROM'"
							filter="{ 'dateFrom': 'generalPurposeCtrl/table/sortAndFilter/datepickerFrom.html' }"
							sortable="'dateFrom'">{{ w.dateFrom }}</td>
						<td style="width: 1px;" data-title="'有効期間TO'"
							filter="{ 'dateTo': 'generalPurposeCtrl/table/sortAndFilter/datepickerTo.html' }"
							sortable="'dateTo'">{{ w.dateTo }}</td>
						<td data-title="'キャンペン詳細'"><a ng-href={{w.contentsFile1}}
							target="_blank" style="margin-left: 35px;"> <img
								src="../img/campaign_details.png">
								</button></td>
						<c:if test="${sessionScope.GeneralPurpose == '1'}">
							<td data-title="'ダウンロード'"
							filter="{ '': 'generalPurposeCtrl/table/sortAndFilter/clearFilter.html' }">
							<a  style="cursor: not-allowed">
							 <img src="../img/download_general.png">
						</td>
						</c:if>
						<c:if test="${sessionScope.GeneralPurpose != '1'}">
							<td data-title="'ダウンロード'"
							filter="{ '': 'generalPurposeCtrl/table/sortAndFilter/clearFilter.html' }">
							<a disabled ng-click="generalPurposeCtrl.table.csvDownLoadBtn(w)"
							style="cursor: pointer"> <img
								src="../img/download_general.png">
							</td>
						</c:if>
						
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/ng-template"
	id="generalPurposeCtrl/table/sortAndFilter/datepickerFrom.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="generalPurposeCtrl.table.sortAndFilter.filter().dateFrom" type="text" id='dateFrom'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="generalPurposeCtrl/table/sortAndFilter/datepickerTo.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="generalPurposeCtrl.table.sortAndFilter.filter().dateTo" type="text" id='dateTo'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="generalPurposeCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="generalPurposeCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<style type="text/css">
.scrool-size-1 {
	height: 550px;
}
</style>
