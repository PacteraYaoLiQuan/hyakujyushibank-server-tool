<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
.height-size-1 {
	height: 550px;
}
</style>

<div class="container">
	<div class="block-header">
		<h2>アプリケーション一覧</h2>
		<input type="hidden" id="application" name="application"
			ng-model="applicationCtrl.user" value="${sessionScope.Application}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="applicationCtrl.addBtn('na')"
							class="btn bgm-lightblue"
							ng-disabled="applicationCtrl.addBtnDisabled">新規登録</button>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="applicationCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="10%" />
						<col width="30%" />
						<col width="30%" />
						<col width="10%" />
						<col width="20%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'applicationCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'アプリケーションコード'" style="word-break:break-all; word-wrap:break-word;"
							filter="{ 'appCD': 'ng-table/filters/text-claer-2.html'}"
							sortable="'appCD'">{{ w.appCD }}</td>
						<td data-title="'アプリケーション名称'" style="word-break:break-all; word-wrap:break-word;"
							filter="{ 'appName': 'ng-table/filters/text-claer-2.html' }"
							sortable="'appName'">{{ w.appName }}</td>
						<td data-title="'公開フラグ'"
							filter="{ 'userFlag': 'ng-table/filters/select-clear.html' }"
							sortable="'userFlag'"
							filter-data="applicationCtrl.table.userFlagList">{{ w.userFlag|userFlagTitle}}</td>
						<td data-title="'詳細／編集'"
							filter="{ '': 'applicationCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="applicationCtrl.table.openNa('na', w)">
								<i class="zmdi zmdi-menu"></i>
							</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/ng-template"
	id="applicationCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="applicationCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="applicationCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="applicationCtrl.table.checkItemAll()" ng-model="applicationCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="applicationCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn btn-danger btn bgm-red waves-effect" customize-button-click="applicationCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="applicationCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn btn-danger btn bgm-red waves-effect" customize-button-click="applicationCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template" id="applicationDetailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl8.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-1">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl8.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
		<div class="card-header">
				<h2>
					基本情報
				</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">公開フラグ <sup>*</sup></h5>
					</div>
				</div>
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="park" value="0" type="radio"
							ng-model="modalInstanceCtrl8.userFlag"> <i
							class="input-helper"></i> 公開
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="park" value="1" type="radio"
							ng-model="modalInstanceCtrl8.userFlag"> <i
							class="input-helper"></i> 非公開
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							アプリケーションコード <sup>*</sup>
						</h5>
						<small> (半角英数 20文字)</small>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" maxlength="20"
								onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								onafterpaste="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								ng-model="modalInstanceCtrl8.appCD"
								ng-disabled="modalInstanceCtrl8.appCDDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							アプリケーション名称 <sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" maxlength="64"
								ng-model="modalInstanceCtrl8.appName"
								ng-disabled="modalInstanceCtrl8.appNameDisabled">
						</div>
					</div>
				</div>
			</div>

			<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl8.dataUpd()"
					ng-disabled="modalInstanceCtrl8.UpdbtnDisabled">{{modalInstanceCtrl8.button}}</button>
			</div>

		</div>
	</div>
</div>
</script>