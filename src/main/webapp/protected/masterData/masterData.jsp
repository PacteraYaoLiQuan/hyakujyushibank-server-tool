<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
.div1 {
	float: left;
	height: 30px;
	background: #ffffff;
	width: 74px;
	position: relative;
}

.inputstyle {
	width: 64px;
	height: 30px;
	cursor: pointer;
	font-size: 30px;
	outline: medium none;
	position: absolute;
	filter: alpha(opacity = 0);
	-moz-opacity: 0;
	opacity: 0;
	left: 0px;
	top: 0px;
}

.ng-table .form-control {
	min-width: 50px;
	padding: 10px 5px;
}

.col-md-offset-1 {
	margin-left: 2.33333333%;
}
</style>
<div class="container">
	<div class="block-header">
		<h2>店舗ATMマスタデータファイル管理</h2>
	</div>
	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2 ">
					<div class="form-group">
						<h5 class="form-title">アップロード日付：</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup1'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="findFile()" class="btn bgm-lightblue"
							style="height: 45px;">更新履歴検索</button>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">更新日：</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup2'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
			</div>
			<hr style="color: #e0e0e0" />
			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">ファイルアップロード：</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div>
						<input type="text" name="name" id="name" disabled />
					</div>
				</div>
				<div class="col-sm-1" style="margin-left: -25px;">
					<button class="btn bgm-lightblue">参照</button>
					<input type="file" class="inputstyle" name="file" id="file">
				</div>
				<div class="col-sm-3" style="margin-left: 25px;">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="upload()" class="btn bgm-lightblue"
							style="height: 45px;">アップロード</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">希望利用日：</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup3'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="card" ng-hide="masterDataCtrl.windowHide">
		<div class="card-header"></div>
		<div class="card-body">
			<div pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="masterDataCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="5%" />
						<col width="10%" />
						<col width="15%" />
						<col width="10%" />
						<col width="10%" />
						<col width="15%" />
						<col width="10%" />
						<col width="10%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td  style="width: 1px;"
							filter="{ 'select': 'masterDataCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td data-title="'ファイル名'" 
							filter="{ 'fileName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'fileName'">{{ w.fileName }}</td>
						<td data-title="'アップロード日時'"
							filter="{ 'uploadDatetime': 'masterDataCtrl/table/sortAndFilter/datepicker.html'}"
							sortable="'uploadDatetime'">{{ w.uploadDatetime }}</td>
						<td data-title="'希望利用日'"
							filter="{ 'hopingUseDate': 'masterDataCtrl/table/sortAndFilter/datepicker-2.html'}"
							sortable="'hopingUseDate'">{{ w.hopingUseDate }}</td>
						<td data-title="'更新済フラグ'"
							filter="{ 'useFlag': 'ng-table/filters/select-clear.html'}"
							sortable="'useFlag'"
							filter-data="masterDataCtrl.table.filterList2">{{ w.useFlag
							| useFlgTitle }}</td>
						<td data-title="'バッチ更新日時'"
							filter="{ 'batExecuteDatetime': 'masterDataCtrl/table/sortAndFilter/datepicker-1.html' }"
							sortable="'batExecuteDatetime'">{{ w.batExecuteDatetime }}</td>
						<td data-title="'アップロード者'" 
							filter="{ 'createdBy': 'ng-table/filters/text-claer-2.html'}"
							sortable="'createdBy'">{{ w.createdBy }}</td>
						<td data-title="'ダウンロード'"
							filter="{ '': 'masterDataCtrl/table/sortAndFilter/clearFilter.html' }">
							<a class="btn bgm-lightgreen"
							ng-click="masterDataCtrl.table.download(w)"> <img
								src="../img/download.png">
						</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/ng-template"
	id="masterDataCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="masterDataCtrl.table.sortAndFilter.filter().uploadDatetime" type="text" id='uploadDatetime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="masterDataCtrl/table/sortAndFilter/datepicker-1.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="masterDataCtrl.table.sortAndFilter.filter().batExecuteDatetime" type="text" id='batExecuteDatetime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
 <script type="text/ng-template"
	id="masterDataCtrl/table/sortAndFilter/datepicker-2.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="masterDataCtrl.table.sortAndFilter.filter().hopingUseDate" type="text" id='hopingUseDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="masterDataCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="masterDataCtrl.table.sortAndFilter.filter({})">リセット</button>
</script>
<script type="text/ng-template"
	id="masterDataCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="masterDataCtrl.table.checkItemAll()" ng-model="masterDataCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
</script>
<script type="text/ng-template"
	id="masterDataCtrl/table/sortAndFilter/paginationButton.html">
	<div style="float:right" class="ng-scope" >
      <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="masterDataCtrl.table.deleteBtn()">一括削除</button>
</div>   
</script>
