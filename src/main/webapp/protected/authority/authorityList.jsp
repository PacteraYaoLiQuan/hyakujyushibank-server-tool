<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
.height-size-1 {
	height: auto;
}
</style>

<div class="container">
	<div class="block-header">
		<h2>権限一覧</h2>
				<input type="hidden" id="authorityList" name="authorityList"
							ng-model="authorityListCtrl.user" value="${sessionScope.Authority}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="authorityListCtrl.addBtn('na')"
							class="btn bgm-lightblue" ng-disabled="authorityListCtrl.addBtnDisabled">新規登録</button>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="card">
		<div class="card-header">
		</div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="authorityListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="1%" />
						<col width="1%" />
						<col width="40%" />
						<col width="40%" />
						<col width="18%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'authorityListCtrl/table/sortAndFilter/checkItemAll.html' }" style="padding-bottom:19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td nowrap data-title="'権限名称'" filter="{ 'authorityName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'authorityName'">{{ w.authorityName }}</td>
						<td nowrap data-title="'参照権限'" filter="{ 'reference': 'ng-table/filters/text-claer-2.html' }"
							sortable="'reference'"><pre style = 'border:dashed 0px ;'>{{ w.reference }}</pre></td>
						<td nowrap data-title="'フル権限'" filter="{ 'management': 'ng-table/filters/text-claer-2.html' }"
							sortable="'management'"><pre style = 'border:dashed 0px ;'>{{ w.management }}</pre></td>
						<td nowrap data-title="'詳細／編集'"
							filter="{ '': 'authorityListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="authorityListCtrl.table.openNa('na', w)">
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
	id="authorityListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="authorityListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="authorityListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="authorityListCtrl.table.checkItemAll()" ng-model="authorityListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="authorityListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn btn-danger btn bgm-red waves-effect" customize-button-click="authorityListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="authorityListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn btn-danger btn bgm-red waves-effect" customize-button-click="authorityListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template" id="authorityDetailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl4.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-1" style="height:auto;">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl4.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							権限名称<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl4.authorityName"
								ng-disabled="modalInstanceCtrl4.authorityNameDisabled">
						</div>
					</div>
				</div>
			</div>

			<div class="row" ng-repeat="push in modalInstanceCtrl4.functionList | orderBy : 'Country'">
				<div class="col-sm-2 col-md-offset-1"><div class="form-group"><h5 class="form-title">{{push.functionName}}</h5><h5 class="form-title" ng-hide="true">{{push.functionID}}</h5></div></div>
				<div class="col-sm-3"><div class="form-group"><label class="radio radio-inline m-r-20" style="margin-left:35px;"><input type="radio" ng-model="push.functionValue" name="{{push.radioName}}" value="0" /><i class="input-helper"></i> なし</label></div></div>
				<div class="col-sm-3"><div class="form-group"><label class="radio radio-inline m-r-20"><input type="radio" ng-model="push.functionValue" name="{{push.radioName}}" value="1" /><i class="input-helper"></i> 参照</label></div></div>
				<div class="col-sm-3"><div class="form-group"><label class="radio radio-inline m-r-20"><input type="radio" ng-model="push.functionValue" name="{{push.radioName}}" value="2" /><i class="input-helper"></i> フル</label></div></div>
			</div>

			<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl4.dataUpd()"
					ng-disabled="modalInstanceCtrl4.UpdbtnDisabled">{{modalInstanceCtrl4.button}}</button>
			</div>

		</div>
	</div>
</div>
</script>