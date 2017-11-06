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
		<h2>店舗ATM一覧</h2>
		<input type="hidden" id="storeATMList" name="storeATMList"
			ng-model="storeATMListCtrl.user" value="${sessionScope.Store}" /> <input
			type="hidden" id="bankCode" name="bankCode"
			ng-model="storeATMListCtrl.bankCode" value="${sessionScope.bank_cd}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="storeATMListCtrl.addBtn('na')"
							ng-disabled="storeATMListCtrl.addBtnDisabled"
							class="btn bgm-lightblue">新規登録</button>
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
				<table ng-table="storeATMListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="2%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="20%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'storeATMListCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'店舗分類'"
							filter="{ 'typeKbn': 'ng-table/filters/select-clear.html'}"
							sortable="'typeKbn'"
							filter-data="storeATMListCtrl.table.typeList">{{w.typeKbn
							| typeTitle}}</td>
						<td data-title="'店番'"
							filter="{ 'storeNumber': 'ng-table/filters/text-claer-2.html'}"
							sortable="'storeNumber'">{{ w.storeNumber}}</td>
						<td data-title="'店舗名'"
							filter="{ 'storeName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'storeName'">{{ w.storeName }}</td>
						
						<td data-title="'住所'"
							filter="{ 'address': 'ng-table/filters/text-claer-2.html' }"
							sortable="'address'">{{ w.address }}</td>
						<td data-title="'電話番号'"
							filter="{ 'teleNumber': 'ng-table/filters/text-claer-2.html' }"
							sortable="'teleNumber'">{{ w.teleNumber }}</td>
						<td data-title="'店舗／ATM　営業時間(平日)'"
							filter="{ 'storeOpenStartTime': 'ng-table/filters/text-claer-2.html' }"
							sortable="'storeOpenStartTime'">{{ w.storeOpenStartTime }}</td>
						<td data-title="'有効／無効'"
							filter="{ 'delFlg': 'ng-table/filters/select-clear.html'}"
							sortable="'delFlg'"
							filter-data="storeATMListCtrl.table.delFlgList">{{w.delFlg |
							delFlgTitle}}</td>
						<td data-title="'詳細／編集'"
							filter="{ '': 'storeATMListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="storeATMListCtrl.table.openNa('na', w)">
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
	id="storeATMListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="storeATMListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="storeATMListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="storeATMListCtrl.table.checkItemAll()" ng-model="storeATMListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="storeATMListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="deleteBtnDisabled" button-disbaled="deleteBtnDisabled" class="btn btn-danger btn bgm-red waves-effect" customize-button-click="storeATMListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="storeATMListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn btn-danger btn bgm-red waves-effect" customize-button-click="storeATMListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template" id="storeDetailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl2.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid scrool-size-1">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl2.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
			<div class="card-header">
				<h2>
					店舗区分<sup>*</sup>
				</h2>
			</div>			
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="type" value="0" type="radio"
							ng-model="modalInstanceCtrl2.type"
							ng-disabled="modalInstanceCtrl2.typeDisabled"
							ng-click="modalInstanceCtrl2.store()"> <i
							class="input-helper"></i> 店舗
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="type" value="1" type="radio"
							ng-model="modalInstanceCtrl2.type"
							ng-disabled="modalInstanceCtrl2.typeDisabled"
							ng-click="modalInstanceCtrl2.atm()"> <i
							class="input-helper"></i> ATM
						</label>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>店舗情報</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							所在地<br /> <span
								class="f-12 c-gray">(255文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="255"
								ng-model="modalInstanceCtrl2.area">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							店番<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="4"
								onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								onafterpaste="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								onblur="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								ng-model="modalInstanceCtrl2.storeNumber"
								ng-disabled="modalInstanceCtrl2.storeNumberDisabled"
                                ng-blur="storeNumberOnblur();">
						</div>
					</div>
				</div>
			</div>

			<div class="row" ng-hide="modalInstanceCtrl2.storeNameHide">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							店舗名<sup>*</sup><br /> <span class="f-12 c-gray">(64文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="64"
								ng-model="modalInstanceCtrl2.storeName"
								ng-disabled="modalInstanceCtrl2.storeNameDisabled">
						</div>
					</div>
				</div>
			</div>

			<div class="row" ng-hide="modalInstanceCtrl2.atmAddressHide">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							設置場所<br /> <span
								class="f-12 c-gray">(255文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="255"
								ng-model="modalInstanceCtrl2.atmAddress">
						</div>
					</div>
				</div>
			</div>
			<div class="row" ng-hide="modalInstanceCtrl2.manageStoreHide">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							管理店(*幹事行)<br /> <span
								class="f-12 c-gray"></span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="255"
								ng-model="modalInstanceCtrl2.manageStore">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">郵便番号</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control"
								data-input-mask="{mask: '000-0000'}" placeholder="000-0000"
								ng-model="modalInstanceCtrl2.postCode" maxlength="8"
								ng-blur="postCodeOnblur();" >
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 所<br /> <span
								class="f-12 c-gray">(255文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="255"
								ng-model="modalInstanceCtrl2.address">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row" ng-hide="modalInstanceCtrl2.teleNumberHide">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">電話番号</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control"
								data-input-mask="{mask: '(000)000-0000'}"
								placeholder="(000)000-0000"
								ng-model="modalInstanceCtrl2.teleNumber" maxlength="14" >
						</div>
					</div>
				</div>
			</div>
			<div class="card business-time" ng-hide="modalInstanceCtrl2.conversionHide">

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外国為取扱</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceConversionIn" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceConversionIn"
							ng-disabled="modalInstanceCtrl2.serviceConversionInDisabled"
							> <i
							class="input-helper"></i> 無
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceConversionIn" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceConversionIn"
							ng-disabled="modalInstanceCtrl2.serviceConversionInDisabled"
							> <i
							class="input-helper"></i> 有
						</label>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外国為取次</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceConversionOut" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceConversionOut"
							ng-disabled="modalInstanceCtrl2.serviceConversionOutDisabled"
							> <i
							class="input-helper"></i> 無
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceConversionOut" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceConversionOut"
							ng-disabled="modalInstanceCtrl2.serviceConversionOutDisabled"
							> <i
							class="input-helper"></i> 有
						</label>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">信託代理業務取扱</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="trustAgent" value="0" type="radio"
							ng-model="modalInstanceCtrl2.trustAgent"
							ng-disabled="modalInstanceCtrl2.trustAgentDisabled"
							> <i
							class="input-helper"></i> 無
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="trustAgent" value="1" type="radio"
							ng-model="modalInstanceCtrl2.trustAgent"
							ng-disabled="modalInstanceCtrl2.trustAgentDisabled"
							> <i
							class="input-helper"></i> 有
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							警備会社
						</h5>
					</div>
				</div>
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<div class="fg-line">
								<select id="select" class="w-100" upload change="changePoliceCompany()"
									ng-model="modalInstanceCtrl2.policeCompany">
									<option value=1>四国警備</option>
									<option value=2>綜合警備</option>
									<option value=3>綜合</option>
									<option value=4>セコム</option>
									<option value=5>セノン</option>
								</select>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">両替機設置店</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="conversionMachine" value="0" type="radio"
							ng-model="modalInstanceCtrl2.conversionMachine"
							ng-disabled="modalInstanceCtrl2.conversionMachineDisabled"
							> <i
							class="input-helper"></i> 無
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="conversionMachine" value="1" type="radio"
							ng-model="modalInstanceCtrl2.conversionMachine"
							ng-disabled="modalInstanceCtrl2.conversionMachineDisabled"
							> <i
							class="input-helper"></i> 有
						</label>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="conversionMachine" value="2" type="radio"
							ng-model="modalInstanceCtrl2.conversionMachine"
							ng-disabled="modalInstanceCtrl2.conversionMachineDisabled"
							> <i
							class="input-helper"></i> 有（平日・土日稼働）
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">通繰機設置店</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="accountMachine" value="0" type="radio"
							ng-model="modalInstanceCtrl2.accountMachine"
							ng-disabled="modalInstanceCtrl2.accountMachineDisabled"
							> <i
							class="input-helper"></i> 無
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="accountMachine" value="1" type="radio"
							ng-model="modalInstanceCtrl2.accountMachine"
							ng-disabled="modalInstanceCtrl2.accountMachineDisabled"
							> <i
							class="input-helper"></i> 有
						</label>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="accountMachine" value="2" type="radio"
							ng-model="modalInstanceCtrl2.accountMachine"
							ng-disabled="modalInstanceCtrl2.accountMachineDisabled"
							> <i
							class="input-helper"></i> 有（平日・土日稼働）
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">貸金庫設置店</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="loanMachine" value="0" type="radio"
							ng-model="modalInstanceCtrl2.loanMachine"
							ng-disabled="modalInstanceCtrl2.loanMachineDisabled"
							> <i
							class="input-helper"></i> 無
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="loanMachine" value="1" type="radio"
							ng-model="modalInstanceCtrl2.loanMachine"
							ng-disabled="modalInstanceCtrl2.loanMachineDisabled"
							> <i
							class="input-helper"></i> 有
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="loanMachine" value="2" type="radio"
							ng-model="modalInstanceCtrl2.loanMachine"
							ng-disabled="modalInstanceCtrl2.loanMachineDisabled"
							> <i
							class="input-helper"></i> 半自動
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="loanMachine" value="3" type="radio"
							ng-model="modalInstanceCtrl2.loanMachine"
							ng-disabled="modalInstanceCtrl2.loanMachineDisabled"
							> <i
							class="input-helper"></i> 全自動
						</label>
					</div>
				</div>
			</div>
			</div>
		</div>
		

		<div class="card business-time"
			ng-hide="modalInstanceCtrl2.storeHide">
			<div class="card-header">
				<h2>窓口営業時間</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">平&nbsp;&nbsp;&nbsp;&nbsp;日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenStartHour">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenStartMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-sm-1">
					<div class="form-group text-center">
						<i class="zmdi zmdi-minus zmdi-hc-fw line-35 f-25 c-gray"></i>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenEndHour">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenEndMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">土曜日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenStartHour_SAT">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenStartMinute_SAT">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-sm-1">
					<div class="form-group text-center">
						<i class="zmdi zmdi-minus zmdi-hc-fw line-35 f-25 c-gray"></i>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenEndHour_SAT">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenEndMinute_SAT">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">日曜日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenStartHour_SUN">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenStartMinute_SUN">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-sm-1">
					<div class="form-group text-center">
						<i class="zmdi zmdi-minus zmdi-hc-fw line-35 f-25 c-gray"></i>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenEndHour_SUN">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.storeOpenEndMinute_SUN">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			
		</div>

		<div class="card business-time" ng-hide="modalInstanceCtrl2.atmHide">
			<div class="card-header">
				<h2>ATM営業時間</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">平&nbsp;&nbsp;&nbsp;&nbsp;日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100" ng-model="modalInstanceCtrl2.atmOpenStartHour">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenStartMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-sm-1">
					<div class="form-group text-center">
						<i class="zmdi zmdi-minus zmdi-hc-fw line-35 f-25 c-gray"></i>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100" ng-model="modalInstanceCtrl2.atmOpenEndHour">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100" ng-model="modalInstanceCtrl2.atmOpenEndMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">土曜日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenStartHour_SAT">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenStartMinute_SAT">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-sm-1">
					<div class="form-group text-center">
						<i class="zmdi zmdi-minus zmdi-hc-fw line-35 f-25 c-gray"></i>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenEndHour_SAT">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenEndMinute_SAT">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">日曜日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenStartHour_SUN">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenStartMinute_SUN">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-sm-1">
					<div class="form-group text-center">
						<i class="zmdi zmdi-minus zmdi-hc-fw line-35 f-25 c-gray"></i>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenEndHour_SUN">
									<option ng-repeat="hour in modalInstanceCtrl2.hourList "
										value="{{hour.id}}">{{hour.title}}</option>
								</select>
							</div>
						</li>
						<li style="width: 10%;">
							<div class="form-group text-center">
								<h5 class="c-gray">:</h5>
							</div>
						</li>
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.atmOpenEndMinute_SUN">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="card business-time">
			<div class="card-header">
				<h2>地図情報</h2>
			</div>
			<div class="row">
				<div class="col-sm-11  col-md-8 col-md-offset-1">
					<div class="form-group">
						<div store_atm_map id="map" style="width: 100%; height: 300px;"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">経度</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" disabled
								ng-model="modalInstanceCtrl2.longitude">
						</div>
					</div>
				</div>
				<div class="col-sm-1">
					<div class="form-group">
						<h5 class="form-title">緯度</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" disabled
								ng-model="modalInstanceCtrl2.latitude">
						</div>
					</div>
				</div>
			</div>	
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							無効
						</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.delFlg"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
			<button class="btn bgm-blue waves-effect" data-swal-warning1=""
				ng-click="modalInstanceCtrl2.dataUpd()"
				ng-disabled="modalInstanceCtrl2.UpdbtnDisabled">{{modalInstanceCtrl2.button}}</button>
		</div>
	</div>
</div>
</script>