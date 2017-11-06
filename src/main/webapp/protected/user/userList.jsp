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
		<h2>アカウント一覧</h2>
		<input type="hidden" id="userList" name="userList"
			ng-model="userListCtrl.user" value="${sessionScope.User}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="userListCtrl.addBtn('na')"
							class="btn bgm-lightblue"
							ng-disabled="userListCtrl.addBtnDisabled">新規登録</button>
					</div>
				</div>
				<div style="float: right" class="col-sm-2">
					<button ng-disabled="userListCtrl.csvLogOutPutBtn"
						style="width: 128px;" class="btn bgm-blue m-l-5"
						ng-click="userListCtrl.table.csvLogOutPutBtn()">
						操作/行動ログ</br>CSV出力
					</button>
				</div>
				<div style="float: right" class="col-sm-3">
					<div class="input-group form-group dp-blue">
						<span class="input-group-addon">終了日</span>
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup2'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
				<div style="float: right" class="col-sm-3">
					<div class="input-group form-group dp-blue">
						<span class="input-group-addon">開始日</span>
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup1'
								class="form-control date-time-picker">
						</div>
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
				<table ng-table="userListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="3%" />
						<col width="15%" />
						<col width="15%" />
						<col width="15%" />
						<col width="12%" />
						<col width="10%" />
						<col width="10%" />
						<col width="14%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'userListCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td data-title="'ユーザー名'"
							filter="{ 'userName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'userName'">{{ w.userName }}</td>
						<td data-title="'ユーザーID'"
							filter="{ 'userID': 'ng-table/filters/text-claer-2.html'}"
							sortable="'userID'">{{ w.userID }}</td>
						<td data-title="'メールアドレス'"
							filter="{ 'email': 'ng-table/filters/text-claer-2.html' }"
							sortable="'email'">{{ w.email }}</td>
						<td data-title="'権限'"
							filter="{ 'authority': 'ng-table/filters/text-claer-2.html' }"
							sortable="'authority'">{{ w.authority }}</td>
						<td data-title="'ログイン状態'"
							filter="{ 'loginStatus': 'ng-table/filters/select-clear.html'}"
							sortable="'loginStatus'"
							filter-data="userListCtrl.table.filterList2">{{
							w.loginStatus | loginStatusTitle }}</td>
						<td style="width: 1px;" data-title="'最終ログイン日時'"
							filter="{ 'endLoginDateTime': 'userListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'endLoginDateTime'">{{ w.endLoginDateTime }}</td>
						<td data-title="'詳細／編集'"
							filter="{ '': 'userListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="userListCtrl.table.openNa('na', w)">
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
	id="userListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="userListCtrl.table.sortAndFilter.filter().endLoginDateTime" type="text" id='endLoginDateTime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="userListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="userListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>


<script type="text/ng-template"
	id="userListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="userListCtrl.table.checkItemAll()" ng-model="userListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="userListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right" class="ng-scope" >
        <button ng-disabled="csvOutPutBtn" button-disabled="csvOutPutBtn" style="width:128px;"class="btn bgm-blue m-l-5" customize-button-click="userListCtrl.table.csvOutPutBtn()">ユーザー一覧</br>CSV出力</button>        
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="userListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="userListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right" class="ng-scope" >
       <button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="userListCtrl.table.csvOutPutBtn()">ユーザー一覧CSV出力</button>        
       <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="userListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<style type="text/css">
.scrool-size-1 {
	height: 550px;
}
</style>
<script type="text/ng-template" id="userDetailPopup.html">

<div class="popup-title">
	<i ng-click="modalInstanceCtrl3.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid scrool-size-1">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl3.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
			<div class="card-header">
				<h2>ユーザー情報</h2>
			</div>
           <div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ユーザーID&nbsp;&nbsp;<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl3.userID" ng-disabled="modalInstanceCtrl3.userIDDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ユーザー名&nbsp;&nbsp;<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl3.userName"
								ng-disabled="modalInstanceCtrl3.userNameDisabled">
						</div>
					</div>
				</div>
			</div>
			<!--<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ログインパスワード&nbsp;&nbsp;<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<input type="password" class="form-control"
								placeholder="8文字（半角英数）以上" ng-model="modalInstanceCtrl3.password">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ログインパスワードの確認&nbsp;&nbsp;<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<input type="password" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl3.passwordConfirm">
						</div>
					</div>
				</div>
			</div> -->
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							メールアドレス&nbsp;&nbsp;<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<input type="email" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl3.email">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							権&nbsp;&nbsp;&nbsp;&nbsp;限&nbsp;&nbsp;<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-3">
					<div class="form-group">
						<div class="fg-line">
							<div class="select">
								<select class="form-control" ng-init="modalInstanceCtrl3.authority='未指定'" 
									ng-model="modalInstanceCtrl3.authority">
									<option
										ng-repeat="authority in  modalInstanceCtrl3.authorityList  track by $index">{{authority}}</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							パスワード初期化フラグ
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<label class="radio radio-inline m-r-20"> <input
								name="passwordType" value="0" type="radio"
								ng-model="modalInstanceCtrl3.passwordType"> <i
									class="input-helper"></i> システム自動初期化パスワード </label>
							<label class="radio radio-inline m-r-20"> <input
								name="passwordType" value="1" type="radio"
								ng-model="modalInstanceCtrl3.passwordType"> <i
									class="input-helper"></i> ユーザー設定パスワード </label>
						</div>
					</div>
				</div>
			</div> -->
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							アカウントロックフラグ
						</h5>
					</div>
				</div>
				<div class="col-sm-8 col-md-7">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl3.lockStatus"> <i
									class="input-helper"></i> ロック中 </label>
						</div>
					</div>
				</div>
			</div>
			<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl3.dataUpd()"
					ng-disabled="modalInstanceCtrl3.UpdbtnDisabled">{{modalInstanceCtrl3.button}}</button>
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl3.passwordReset()" ng-hide="modalInstanceCtrl3.resetHide"
					ng-disabled="modalInstanceCtrl3.resetBtnDisabled">パスワードをリセット</button>
			</div>

		</div>
	</div>
</div>
</script>