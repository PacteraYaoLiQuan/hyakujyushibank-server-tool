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
			ng-model="storeATMListCtrl.user" value="${sessionScope.Store}" />
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
						<td data-title="'店舗区分'"
							filter="{ 'typeKbn': 'ng-table/filters/select-clear.html'}"
							sortable="'typeKbn'"
							filter-data="storeATMListCtrl.table.filterList">{{w.typeKbn
							| typeKbnTitle}}</td>
						<td data-title="'店舗コード'"
							filter="{ 'storeATMCode': 'ng-table/filters/text-claer-2.html'}"
							sortable="'storeATMCode'">{{ w.storeATMCode}}</td>
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
						<td data-title="'窓口／ATM　営業時間（平日）'"
							filter="{ 'windowOpenStartTime': 'ng-table/filters/text-claer-2.html' }"
							sortable="'windowOpenStartTime'">{{ w.windowOpenStartTime }}</td>
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
							name="typeKbn" value="0" type="radio"
							ng-model="modalInstanceCtrl2.typeKbn"
							ng-disabled="modalInstanceCtrl2.typeKbnDisabled"
							ng-click="modalInstanceCtrl2.windowSelected()"> <i
							class="input-helper"></i> 窓口店舗
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="typeKbn" value="1" type="radio"
							ng-model="modalInstanceCtrl2.typeKbn"
							ng-disabled="modalInstanceCtrl2.typeKbnDisabled"
							ng-click="modalInstanceCtrl2.atmSelected()"> <i
							class="input-helper"></i> 店舗外ATM
						</label>
					</div>
				</div>
				<div class="col-sm-5 col-md-3 ">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="typeKbn" value="2" type="radio"
							ng-model="modalInstanceCtrl2.typeKbn"
							ng-disabled="modalInstanceCtrl2.typeKbnDisabled"
							ng-click="modalInstanceCtrl2.atmSelected()"> <i
							class="input-helper"></i> 企業内
						</label>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>店舗コード</h2>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							母店番号<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="3"
								ng-model="modalInstanceCtrl2.storeNumber"
								ng-disabled="modalInstanceCtrl2.storeNumberDisabled"
                                ng-blur="storeNumberOnblur();">
						</div>
					</div>
				</div>
				<div class="col-sm-2" style="width: 10%;">
					<div class="form-group">
						<h5 class="form-title">
							出張所枝番<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="2"
								ng-model="modalInstanceCtrl2.subStoreNumber"
								ng-disabled="modalInstanceCtrl2.subStoreNumberDisabled"
                                ng-blur="subStoreNumberOnblur();">
						</div>
					</div>
				</div>
				<div class="col-sm-2 col-md-1">
					<div class="form-group">
						<h5 class="form-title">
							ATM枝番<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="2"
								ng-model="modalInstanceCtrl2.atmStoreNumber"
								ng-disabled="modalInstanceCtrl2.atmStoreNumberDisabled"
                                ng-blur="atmStoreNumberOnblur();">
						</div>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>店舗名</h2>
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
			<div class="card-header">
				<h2>所在地</h2>
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
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							ランドマーク<br /> <span class="f-12 c-gray">(64文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="64"
								ng-model="modalInstanceCtrl2.landMark">
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
									ng-model="modalInstanceCtrl2.windowStartHour">
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
									ng-model="modalInstanceCtrl2.windowStartMinute">
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
									ng-model="modalInstanceCtrl2.windowEndHour">
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
									ng-model="modalInstanceCtrl2.windowEndMinute">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-3" style="margin-top:-20px">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.workDay"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>

				<div class="col-sm-8" style="margin-top:-20px;margin-left:-40px">
						<h5 class="form-title" style="font-size:11px;">※平日15時以降および土日祝休日については取扱い業務が一部異なる場合があります。</h5>
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
									ng-model="modalInstanceCtrl2.windowStartHour_SAT">
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
									ng-model="modalInstanceCtrl2.windowStartMinute_SAT">
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
									ng-model="modalInstanceCtrl2.windowEndHour_SAT">
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
									ng-model="modalInstanceCtrl2.windowEndMinute_SAT">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-3" style="margin-top:-20px">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.Saturday"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>

				<div class="col-sm-8" style="margin-top:-20px;margin-left:-40px">
						<h5 class="form-title" style="font-size:11px;">※平日15時以降および土日祝休日については取扱い業務が一部異なる場合があります。</h5>
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
									ng-model="modalInstanceCtrl2.windowStartHour_SUN">
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
									ng-model="modalInstanceCtrl2.windowStartMinute_SUN">
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
									ng-model="modalInstanceCtrl2.windowEndHour_SUN">
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
									ng-model="modalInstanceCtrl2.windowEndMinute_SUN">
									<option ng-repeat="minute in modalInstanceCtrl2.minuteList"
										value="{{minute.id}}">{{minute.title}}</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-3" style="margin-top:-20px">
					<div class="form-group">
						<div class="fg-line">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl2.Sunday"> <i
									class="input-helper"></i> </label>
						</div>
					</div>
				</div>

				<div class="col-sm-8" style="margin-top:-20px;margin-left:-40px">
						<h5 class="form-title" style="font-size:11px;">※平日15時以降および土日祝休日については取扱い業務が一部異なる場合があります。</h5>
				</div>
				
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							備考１<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.comment4">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							備考２<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.comment5">
						</div>
					</div>
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
								<select class="w-100" ng-model="modalInstanceCtrl2.atmStartHour">
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
									ng-model="modalInstanceCtrl2.atmStartMinute">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.atmEndHour">
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
								<select class="w-100" ng-model="modalInstanceCtrl2.atmEndMinute">
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
									ng-model="modalInstanceCtrl2.atmStartHour_SAT">
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
									ng-model="modalInstanceCtrl2.atmStartMinute_SAT">
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
									ng-model="modalInstanceCtrl2.atmEndHour_SAT">
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
									ng-model="modalInstanceCtrl2.atmEndMinute_SAT">
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
									ng-model="modalInstanceCtrl2.atmStartHour_SUN">
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
									ng-model="modalInstanceCtrl2.atmStartMinute_SUN">
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
									ng-model="modalInstanceCtrl2.atmEndHour_SUN">
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
									ng-model="modalInstanceCtrl2.atmEndMinute_SUN">
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
								maxlength="5">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="card business-time">
			<div class="card-header">
				<h2>駐車場</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">有無</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="park" value="0" type="radio"
							ng-model="modalInstanceCtrl2.park"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="park" value="1" type="radio"
							ng-model="modalInstanceCtrl2.park"> <i
							class="input-helper"></i> あり
						</label>
					</div>
				</div>
				<div class="col-sm-5">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="park" value="2" type="radio"
							ng-model="modalInstanceCtrl2.park"> <i
							class="input-helper"></i> あり（営業店に問い合わせ）
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">障害者対応</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="parkServiceForDisabled" value="0" type="radio"
							ng-model="modalInstanceCtrl2.parkServiceForDisabled"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7 col-md-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="parkServiceForDisabled" value="1" type="radio"
							ng-model="modalInstanceCtrl2.parkServiceForDisabled"> <i
							class="input-helper"></i> あり
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							備考１<br /> <span class="f-12 c-gray">(150文字以内)</span>
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

			<div class="card-header">
				<h2>ひろぎんウツミ屋</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">ひろぎんウツミ屋</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="hirginUtsumiya" value="0" type="radio"
							ng-model="modalInstanceCtrl2.hirginUtsumiya"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7 col-md-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="hirginUtsumiya" value="1" type="radio"
							ng-model="modalInstanceCtrl2.hirginUtsumiya"> <i
							class="input-helper"></i> あり
						</label>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>商品サービス</h2>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外貨両替（ﾄﾞﾙ、ﾕｰﾛ）</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceDollarEuro" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceDollarEuro"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceDollarEuro" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceDollarEuro"> <i
							class="input-helper"></i> 可（現金保有）
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceDollarEuro" value="2" type="radio"
							ng-model="modalInstanceCtrl2.serviceDollarEuro"> <i
							class="input-helper"></i> 取次ぎ
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外貨両替（ｱｼﾞｱ通貨）</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceAsia" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceAsia"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceAsia" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceAsia"> <i
							class="input-helper"></i> 可（現金保有）
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceAsia" value="2" type="radio"
							ng-model="modalInstanceCtrl2.serviceAsia"> <i
							class="input-helper"></i> 取次ぎ
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外貨両替（その他）</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceOther" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceOther"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceOther" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceOther"> <i
							class="input-helper"></i> 可（現金保有）
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceOther" value="2" type="radio"
							ng-model="modalInstanceCtrl2.serviceOther"> <i
							class="input-helper"></i> 取次ぎ
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">外貨買取</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceForeignExchange" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceForeignExchange"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceForeignExchange" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceForeignExchange"> <i
							class="input-helper"></i> 可
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceForeignExchange" value="2" type="radio"
							ng-model="modalInstanceCtrl2.serviceForeignExchange"> <i
							class="input-helper"></i> 取次ぎ
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">投資信託</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceInvestmentTrust" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceInvestmentTrust"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceInvestmentTrust" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceInvestmentTrust"> <i
							class="input-helper"></i> 可
						</label>
					</div>
				</div>

			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">年金保険</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="servicePensionInsurance" value="0" type="radio"
							ng-model="modalInstanceCtrl2.servicePensionInsurance"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="servicePensionInsurance" value="1" type="radio"
							ng-model="modalInstanceCtrl2.servicePensionInsurance"> <i
							class="input-helper"></i> 可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">金融商品仲介（みずほ証券）</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceMizuho" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceMizuho"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceMizuho" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceMizuho"> <i
							class="input-helper"></i> みずほ証券
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">金融商品仲介（ひろぎんウツミ屋）</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceHirginUtsumiya" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceHirginUtsumiya"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceHirginUtsumiya" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceHirginUtsumiya"> <i
							class="input-helper"></i> ひろぎんウツミ屋証券
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">全自動貸金庫</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceAutoSafeDepositBox" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceAutoSafeDepositBox">
							<i class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceAutoSafeDepositBox" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceAutoSafeDepositBox">
							<i class="input-helper"></i> あり
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">一般貸金庫</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceSafeDepositBox" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceSafeDepositBox"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceSafeDepositBox" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceSafeDepositBox"> <i
							class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">ｾｰﾌﾃｨｹｰｽ</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceSafeBox" value="0" type="radio"
							ng-model="modalInstanceCtrl2.serviceSafeBox"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="serviceSafeBox" value="1" type="radio"
							ng-model="modalInstanceCtrl2.serviceSafeBox"> <i
							class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>店舗設備</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">IB専用PC</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="internationalTradePC" value="0" type="radio"
							ng-model="modalInstanceCtrl2.internationalTradePC"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="internationalTradePC" value="1" type="radio"
							ng-model="modalInstanceCtrl2.internationalTradePC"> <i
							class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">ｷｯｽﾞｽﾍﾟｰｽ</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="childrenSpace" value="0" type="radio"
							ng-model="modalInstanceCtrl2.childrenSpace"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="childrenSpace" value="1" type="radio"
							ng-model="modalInstanceCtrl2.childrenSpace"> <i
							class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>バリアフリー</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">視覚障害対応ATM</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeVisualImpairment" value="0" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeVisualImpairment">
							<i class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeVisualImpairment" value="1" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeVisualImpairment">
							<i class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">点字ﾌﾞﾛｯｸ</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeBrailleBlock" value="0" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeBrailleBlock"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeBrailleBlock" value="1" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeBrailleBlock"> <i
							class="input-helper"></i>店舗内まで
						</label>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeBrailleBlock" value="2" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeBrailleBlock"> <i
							class="input-helper"></i>店舗入口まで
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-9 col-md-offset-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeBrailleBlock" value="3" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeBrailleBlock"> <i
							class="input-helper"></i>店内のみ
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">音声ｶﾞｲﾄﾞ</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeVoiceGuide" value="0" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeVoiceGuide"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeVoiceGuide" value="1" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeVoiceGuide"> <i
							class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">AED</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeAED" value="0" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeAED"> <i
							class="input-helper"></i> なし
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="barrierFreeAED" value="1" type="radio"
							ng-model="modalInstanceCtrl2.barrierFreeAED"> <i
							class="input-helper"></i>あり
						</label>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>ATM機能</h2>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">振込</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionTransfer" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionTransfer"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionTransfer" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionTransfer"> <i
							class="input-helper"></i>可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">硬貨入出金</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionCoinAccess" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionCoinAccess"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionCoinAccess" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionCoinAccess"> <i
							class="input-helper"></i>可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">宝くじｻｰﾋﾞｽ</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionLotteryService" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionLotteryService">
							<i class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionLotteryService" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionLotteryService">
							<i class="input-helper"></i>可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">手のひら認証</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionPalmAuthentication" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionPalmAuthentication">
							<i class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionPalmAuthentication" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionPalmAuthentication">
							<i class="input-helper"></i>可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">IC対応</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionIC" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionIC"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionIC" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionIC"> <i
							class="input-helper"></i>可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">PASPYチャージ</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionPASPY" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionPASPY"> <i
							class="input-helper"></i> 不可
						</label>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionPASPY" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionPASPY"> <i
							class="input-helper"></i>可
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">他行幹事</h5>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionOtherBankingAffairs" value="0" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionOtherBankingAffairs">
							<i class="input-helper"></i> 広銀ATM
						</label>
					</div>
				</div>
				<div class="col-sm-6  col-md-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionOtherBankingAffairs" value="1" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionOtherBankingAffairs">
							<i class="input-helper"></i>共同ATM（地銀）
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4 col-md-offset-3">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionOtherBankingAffairs" value="2" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionOtherBankingAffairs">
							<i class="input-helper"></i>共同ATM（セブン銀行）
						</label>
					</div>
				</div>
				<div class="col-sm-5">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="atmFunctionOtherBankingAffairs" value="3" type="radio"
							ng-model="modalInstanceCtrl2.atmFunctionOtherBankingAffairs">
							<i class="input-helper"></i>共同ATM（地銀以外）
						</label>
					</div>
				</div>
			</div>
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
				<div class="col-sm-2 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							備考１<br /> <span class="f-12 c-gray">(150文字以内)</span>
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
							備考２<br /> <span class="f-12 c-gray">(150文字以内)</span>
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
							備考３<br /> <span class="f-12 c-gray">(150文字以内)</span>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-8">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" maxlength="150"
								ng-model="modalInstanceCtrl2.comment3">
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">開始日時</h5>
					</div>
				</div>
				<div class="col-sm-3">
					<div>
						<div class="dtp-container fg-line" style="width: 100px;">
							<input ng-model="modalInstanceCtrl2.startDateTime" type="text"
								id='receiptDate' class="form-control date-time-picker">
						</div>
					</div>
				</div>
				<div class="col-sm-1">
					<div class="form-group">
						<h5 class="form-title">終了日時</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div>
						<div class="dtp-container fg-line" style="width: 100px;">
							<input ng-model="modalInstanceCtrl2.endDateTime" type="text"
								id='receiptDate1' class="form-control date-time-picker">
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