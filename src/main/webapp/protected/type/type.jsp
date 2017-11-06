<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
.height-size-1 {
	height: 550px;
}
.scrool-size-1{
	height: 600px;
}
.scrool-size-2 {
	height: 400px;
}
.modal-size {
  width:  75%;
}

.btn-height {
  line-height:  36px;
  width:  128px;
}

.ng-table .form-control {
    min-width: 50px;
    padding: 10px 5px;
}


.table > thead > tr > th,
.table > tbody > tr > th,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > tbody > tr > td,
.table > tfoot > tr > td {
  padding-left: 5px;
  padding-right: 5px;
  padding-bottom: 10px;
  padding-top: 10px;
}
</style>

<div class="container">
	<div class="block-header">
		<h2>コンテンツ種別一覧</h2>
		<input type="hidden" id="type" name="type"
			ng-model="typeCtrl.user" value="${sessionScope.Type}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="typeCtrl.addBtn('na')"
							class="btn bgm-lightblue"
							ng-disabled="typeCtrl.addBtnDisabled">新規登録</button>
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
				<table ng-table="typeCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="10%" />
						<col width="30%" />
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'typeCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'アプリケーション'" style="word-break:break-all; word-wrap:break-word;"
							filter="{ 'appCD': 'ng-table/filters/text-claer-2.html'}"
							sortable="'appCD'">{{ w.appCD }}</td>
						<td data-title="'コンテンツ種別コード'" style="word-break:break-all; word-wrap:break-word;"
							filter="{ 'typeCD': 'ng-table/filters/text-claer-2.html' }"
							sortable="'typeCD'">{{ w.typeCD }}</td>
						<td data-title="'コンテンツ種別名'" style="word-break:break-all; word-wrap:break-word;"
							filter="{ 'typeName': 'ng-table/filters/text-claer-2.html' }"
							sortable="'typeName'">{{ w.typeName }}</td>
						<td data-title="'詳細／編集'"
							filter="{ '': 'typeCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="typeCtrl.table.openNa('na', w)">
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
	id="typeCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="typeCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="typeCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="typeCtrl.table.checkItemAll()" ng-model="typeCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="typeCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn btn-danger btn bgm-red waves-effect" customize-button-click="typeCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="typeCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn btn-danger btn bgm-red waves-effect" customize-button-click="typeCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template" id="typeDetailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl9.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-2">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl9.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
		<div class="card-header">
				<h2>
					基本情報
				</h2>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							アプリケーションコード  <sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line" ng-hide="modalInstanceCtrl9.select">
							<span class="input-group-addon"></span>
								<select class="w-100"
									ng-model="modalInstanceCtrl9.appCD">
									<option ng-repeat="appCD in modalInstanceCtrl9.appCDList">{{appCD}}</option>
								</select>
						</div>
						<div class="fg-line" ng-hide="modalInstanceCtrl9.input">
							<input type="text" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl9.appCD"
								ng-disabled="modalInstanceCtrl9.typeCDDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							コンテンツ種別コード <sup>*</sup>
						</h5>
						<small>(半角英数 20文字)</small>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" maxlength="20"
								onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								onafterpaste="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								ng-model="modalInstanceCtrl9.typeCD"
								ng-disabled="modalInstanceCtrl9.typeCDDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							コンテンツ種別名 <sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" maxlength="64"
								ng-model="modalInstanceCtrl9.typeName"
								ng-disabled="modalInstanceCtrl9.typeNameDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="card-header" ng-hide="modalInstanceCtrl9.order">
				<h2>
					コンテンツ表示順
				</h2>
			</div>
			<div ng-hide="modalInstanceCtrl9.orderTable" class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="modalInstanceCtrl9.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="40%" />
						<col width="40%" />
						<col width="20%" />
					</colgroup>
					<tr ng-repeat="w in orderIDList">
						<td data-title="'コンテンツID'" 
							sortable="'contentsID'">{{ w.contentsID }}</td>
						<td data-title="'コンテンツタイトル'" 
							sortable="'contentsTitle'">{{ w.contentsTitle }}</td>
						<td data-title="'表示順'" 
							sortable="'orderID'">
							<input type="text" class="form-control" placeholder="" maxlength="3"
								onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
								onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')"
								ng-model="SelectedCollection[w.contentsID]" ng-value={{w.orderID}}>
						</td>
					</tr>
				</table>
			</div>
			<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl9.dataUpd()"
					ng-disabled="modalInstanceCtrl9.UpdbtnDisabled">{{modalInstanceCtrl9.button}}</button>
			</div>

		</div>
	</div>
</div>
</script>