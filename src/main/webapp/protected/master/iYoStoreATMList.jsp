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
						<col width="10%" />
						<col width="10%" />
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
							filter-data="storeATMListCtrl.table.storeTypeList">{{w.typeKbn
							| storeTypeTitle}}</td>
						<td data-title="'店番号'"
							filter="{ 'storeNumber': 'ng-table/filters/text-claer-2.html'}"
							sortable="'storeNumber'">{{ w.storeNumber}}</td>
						<td data-title="'店舗名（漢字）'"
							filter="{ 'storeATMName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'storeATMName'">{{ w.storeATMName }}</td>
						<td data-title="'店舗名（カナ）'"
							filter="{ 'kanaStoreATMName': 'ng-table/filters/text-claer-2.html' }"
							sortable="'kanaStoreATMName'">{{ w.kanaStoreATMName }}</td>
						<td data-title="'住所'"
							filter="{ 'address': 'ng-table/filters/text-claer-2.html' }"
							sortable="'address'">{{ w.address }}</td>
						<td data-title="'電話番号'"
							filter="{ 'teleNumber': 'ng-table/filters/text-claer-2.html' }"
							sortable="'teleNumber'">{{ w.teleNumber }}</td>
						<td data-title="'窓口／ATM\r\n&#13;稼動時間（平日）'"
							filter="{ 'windowOpenStartTime': 'ng-table/filters/text-claer-2.html' }"
							sortable="'windowOpenStartTime'">{{ w.windowOpenStartTime }}</td>
						<td data-title="'公開'"
							filter="{ 'poiStatus': 'ng-table/filters/select-clear.html'}"
							sortable="'poiStatus'"
							filter-data="storeATMListCtrl.table.openFlgList">{{w.poiStatus |
							openFlgTitle}}</td>
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
				<h2>店舗情報</h2>
			</div>

			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							店舗分類<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<div class="fg-line">
								<select id="select" class="w-100" upload change="changeStoreType()"
									ng-model="modalInstanceCtrl2.typeKbn">
									<option value=1>本支店</option>
									<option value=2>役所・役場</option>
									<option value=3>公共施設</option>
									<option value=4>デパートスーパー</option>
									<option value=5>ショッピング他</option>
									<option value=6>空港・駅・港</option>
									<option value=7>病院</option>
									<option value=8>学校</option>
									<option value=9>その他</option>
								</select>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							店番号<sup>*</sup>
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
			
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							廃止店番
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
								ng-model="modalInstanceCtrl2.delStoreNumber"
								ng-disabled="modalInstanceCtrl2.delStoreNumberDisabled"
                                ng-blur="delStoreNumberOnblur();">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							店舗名（漢字）<sup>*</sup><br /> <span class="f-12 c-gray">(64文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="64"
								ng-model="modalInstanceCtrl2.storeATMName"
								ng-disabled="modalInstanceCtrl2.storeATMNameDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							店舗名（カナ）<sup>*</sup><br /> <span class="f-12 c-gray">(64文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="64"
								ng-model="modalInstanceCtrl2.kanaStoreATMName"
								ng-disabled="modalInstanceCtrl2.kanaStoreATMNameDisabled"
								ng-blur="kanaStoreATMNameOnblur();" >
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 開
						</h5>
					</div>
				</div>

				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.poiStatus"> <i
									class="input-helper"></i> </label>
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
			
			<div class="row" ng-hide="modalInstanceCtrl2.windowHide">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">電話番号</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control"
								data-input-mask="{mask: '000-000-0000'}"
								placeholder="000-000-0000"
								ng-model="modalInstanceCtrl2.teleNumber" maxlength="13" >
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
		</div>

		<div class="card business-time"
			ng-hide="modalInstanceCtrl2.windowHide">
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
									ng-model="modalInstanceCtrl2.windowOpenStartHour">
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
									ng-model="modalInstanceCtrl2.windowOpenStartMinute">
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
									ng-model="modalInstanceCtrl2.windowOpenEndHour">
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
									ng-model="modalInstanceCtrl2.windowOpenEndMinute">
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
									ng-model="modalInstanceCtrl2.windowOpenStartHour_SAT">
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
									ng-model="modalInstanceCtrl2.windowOpenStartMinute_SAT">
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
									ng-model="modalInstanceCtrl2.windowOpenEndHour_SAT">
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
									ng-model="modalInstanceCtrl2.windowOpenEndMinute_SAT">
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
									ng-model="modalInstanceCtrl2.windowOpenStartHour_SUN">
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
									ng-model="modalInstanceCtrl2.windowOpenStartMinute_SUN">
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
									ng-model="modalInstanceCtrl2.windowOpenEndHour_SUN">
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
									ng-model="modalInstanceCtrl2.windowOpenEndMinute_SUN">
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
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ATM設置台数<br /> <span class="f-12 c-gray">(半角数字のみ)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-3">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control"
								ng-model="modalInstanceCtrl2.atmCount"
								onkeyup="this.value=this.value.replace(/\D/g,'')"
								onafterpaste="this.value=this.value.replace(/\D/g,'')"
								onblur="this.value=this.value.replace(/\D/g,'')"
								maxlength="5">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ATM備考(定型文)<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.atmComment1">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ATM備考(自由欄)<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.atmComment2">
						</div>
					</div>
				</div>
			</div>
		</div>


		
		<div class="card business-time">
			<div class="card-header">
				<h2>取扱サービス・設備</h2>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">自動両替機平日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100" ng-model="modalInstanceCtrl2.conversionMachineStartHour">
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
									ng-model="modalInstanceCtrl2.conversionMachineStartMinute">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.conversionMachineEndHour">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.conversionMachineEndMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">自動両替機土・日・祝</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.conversionMachineStartHour_holiday">
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
									ng-model="modalInstanceCtrl2.conversionMachineStartMinute_holiday">
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
									ng-model="modalInstanceCtrl2.conversionMachineEndHour_holiday">
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
									ng-model="modalInstanceCtrl2.conversionMachineEndMinute_holiday">
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
						<h5 class="form-title"></h5>
					</div>
				</div>
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">平日通帳繰越可能</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.accountMachineStartTime"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title"></h5>
					</div>
				</div>
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">土曜通帳繰越可能</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.accountMachineStartTime_SAT"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title"></h5>
					</div>
				</div>
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">日祝日通帳繰越可能</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.accountMachineStartTime_SUN"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">全自動型貸金庫平日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100" ng-model="modalInstanceCtrl2.autoLoanMachineStartHour">
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
									ng-model="modalInstanceCtrl2.autoLoanMachineStartMinute">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.autoLoanMachineEndHour">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.autoLoanMachineEndMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">全自動型貸金庫土・日・祝</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.autoLoanMachineStartHour_holiday">
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
									ng-model="modalInstanceCtrl2.autoLoanMachineStartMinute_holiday">
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
									ng-model="modalInstanceCtrl2.autoLoanMachineEndHour_holiday">
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
									ng-model="modalInstanceCtrl2.autoLoanMachineEndMinute_holiday">
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
						<h5 class="form-title">
							
						</h5>
					</div>
				</div>
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							休日はご利用いただけません
						</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.autoLoanMachineFlag"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">貸金庫平日</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100" ng-model="modalInstanceCtrl2.loanMachineStartHour">
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
									ng-model="modalInstanceCtrl2.loanMachineStartMinute">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.loanMachineEndHour">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.loanMachineEndMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">貸金庫土・日・祝</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<ul class="work-time">
						<li>
							<div class="form-group">
								<select class="w-100"
									ng-model="modalInstanceCtrl2.loanMachineStartHour_holiday">
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
									ng-model="modalInstanceCtrl2.loanMachineStartMinute_holiday">
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
									ng-model="modalInstanceCtrl2.loanMachineEndHour_holiday">
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
									ng-model="modalInstanceCtrl2.loanMachineEndMinute_holiday">
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
						<h5 class="form-title">
							
						</h5>
					</div>
				</div>
				<div class="col-sm-4 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							休日は全自動型のみご利用いただけます
						</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.loanMachineFlag"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">インターネット支店フラグ</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.internationalStore"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">AED</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.aed"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							特記事項<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.comment1">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							特記事項(携帯用)<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.comment2">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							 駐車可能台数
						</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="3"
								onkeyup="this.value=this.value.replace(/\D/g,'')"
								onafterpaste="this.value=this.value.replace(/\D/g,'')"
								onblur="this.value=this.value.replace(/\D/g,'')"
								ng-model="modalInstanceCtrl2.parkCount"
								ng-disabled="modalInstanceCtrl2.parkCountDisabled"
                                ng-blur="parkCountOnblur();">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							駐車場補足<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.parkComment">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">トイレ有無</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
								<select id="selectToilet" class="w-100" upload change="changeToilet()"
									ng-model="modalInstanceCtrl2.toilet">
									<option value=1>お客様用トイレ</option>
									<option value=2>多目的トイレ</option>
									<option value=3>テナント共用トイレ</option>
								</select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外貨両替実施店</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.serviceConversionStore"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">車椅子あり</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.wheelChair"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">車椅子用スロープ設置店</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.wheelChairStore"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="card business-time">
			<div class="card-header">
				<h2>最寄駅・停留所</h2>
			</div>
			
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所１<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStation1">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所距離１<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStationDistance1">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所時間１<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStationTime1">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所２<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStation2">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所距離２<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStationDistance2">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所時間２<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStationTime2">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所３<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStation3">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所距離３<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStationDistance3">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							最寄駅・停留所時間３<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.currentStationTime3">
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="card business-time">
			<div class="card-header">
				<h2>その他</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							一言メッセージ<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.message">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							詳細ページ画像<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.image">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							画像リンクURL<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.imageUrl">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							アイコンID<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.icon">
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