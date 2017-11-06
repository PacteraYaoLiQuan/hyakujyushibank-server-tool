<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>


</script>
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
	padding-left: 1px;
	padding-right: 1px;
	padding-bottom: 10px;
	padding-top: 10px;
}
</style>

<div class="container">
	<div class="block-header">
		<h2>任意Push配信履歴</h2>
		<input type="hidden" id="pushMessage" name="pushMessage"
			ng-model="pushMessageCtrl.pushMessage"
			value="${sessionScope.MessageList}" />
	</div>

	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="pushMessageCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="2%" />
						<col width="24%" />
						<col width="16%" />
						<col width="14%" />
						<col width="14%" />
						<col width="11%" />
						<col width="11%" />
						<col width="4%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'pushMessageCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td data-title="'配信件名'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'pushTitle': 'ng-table/filters/text-claer-2.html'}"
							sortable="'pushTitle'">{{ w.pushTitle }}</td>
						<td data-title="'配信日時'" style="word-wrap: break-word;"
							filter="{ 'pushDate': 'pushMessageCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'pushDate'">{{ w.pushDate }}</td>
						<td data-title="'配信対象'"
							filter="{ 'pushType': 'ng-table/filters/select-clear.html'}"
							sortable="'pushType'"
							filter-data="pushMessageCtrl.table.filterList3">
						<div ng-show="{{ w.pushOthreFlag }}">
								<a style="cursor:pointer;" ng-click="pushMessageCtrl.table.openPushType('na', w)">{{w.pushType| pushTypeTitle}}</a>
						</div>
						<div ng-show="{{ w.pushAllFlag }}">
							{{w.pushType| pushTypeTitle}}
							</div>
						</td>
						<td data-title="'配信者'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'pushAccessUser': 'ng-table/filters/text-claer-2.html'}"
							sortable="'pushAccessUser'">{{ w.pushAccessUser }}</td>
						<td data-title="'配信ステータス'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'pushStatus': 'ng-table/filters/text-claer-2.html'}"
							sortable="'pushStatus'">{{ w.pushStatus }}</td>
						<!-- <td data-title="'配信件数'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'pushCnt': 'ng-table/filters/text-claer-2.html' }"
							sortable="'pushCnt'">{{ w.pushCnt }}</td>
						<td data-title="'成功件数'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'pushSuccessCnt': 'ng-table/filters/text-claer-2.html' }"
							sortable="'pushSuccessCnt'">{{ w.pushSuccessCnt }}</td>
						<td data-title="'失敗件数'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'pushFaileCnt': 'ng-table/filters/text-claer-2.html' }"
							sortable="'pushFaileCnt'">{{ w.pushFaileCnt }}</td> -->
						<td data-title="'送達＆開封数'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'andOpenCnt': 'ng-table/filters/text-claer-2.html' }"
							sortable="'andOpenCnt'">{{ w.andOpenCnt }}</td>
						<td data-title="'送達＆未開封数'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'andCloseCnt': 'ng-table/filters/text-claer-2.html' }"
							sortable="'andCloseCnt'">{{ w.andCloseCnt }}</td>
						<td data-title="'詳細'"
							filter="{ '': 'pushMessageCtrl/table/sortAndFilter/clearFilter.html' }">
							<a class="btn bgm-lightgreen" cc="{{$index}}" ng-click="pushMessageCtrl.table.openNa('na', w)">
								<i class="zmdi zmdi-menu"></i>
							</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/ng-template"
	id="pushMessageCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="pushMessageCtrl.table.sortAndFilter.filter().pushDate" type="text" id='pushDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="pushMessageCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="pushMessageCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="pushMessageCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="pushMessageCtrl.table.checkItemAll()" ng-model="pushMessageCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="pushMessageCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right" class="ng-scope" >
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="pushMessageCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="pushMessageCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right" class="ng-scope" >
       <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="pushMessageCtrl.table.deleteBtn()">一括削除</button>
</div> 
</script>
<script type="text/ng-template" id="pushMessagePopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl10.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-3">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl10.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
		<div class="card-header">
				<h2>
					任意メッセージPUSH配信
				</h2>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							件名<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-9" >
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl10.pushTitle"
								ng-disabled="modalInstanceCtrl10.pushTitleDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							本文<sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-9 col-md-9" >
					<div class="form-group">
						<div class="fg-line">
                           <textarea class="form-control" style="border:1px solid #e0e0e0; height:300px; width:100%" placeholder="" ng-model="modalInstanceCtrl10.pushMessage" ng-disabled="modalInstanceCtrl10.pushMessageDisabled"></textarea>
						</div>
					</div>
				</div>
			</div>

　　　　　　　　　　<div ng-hide="modalInstanceCtrl10.windowHide1" class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl10.dataOK()">プレビュー</button>
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl10.dataSend()"
					ng-disabled="modalInstanceCtrl10.SendbtnDisabled">配信する</button>
			</div>
            <div ng-hide="modalInstanceCtrl10.windowHide2" class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
                <button class="btn bgm-blue waves-effect" data-swal-warning1=""
                    ng-click="modalInstanceCtrl10.dataOK()">{{modalInstanceCtrl10.button}}</button>
            </div>

		</div>
	</div>
</div>
</script>
<script type="text/ng-template" id="pushTypePopup.html">
<div class="popup-title">
    <i ng-click="pushTypeCtrl.ok()"
        class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
        <div class="block-header">
            <h2>配信対象</h2>
        </div>
        <div class="card">
            <div class="card-body card-padding detail-popup">
                <div class="row detail-c-item">
                    <div">
                        <table class="dp-table">
                            <tr>
                                <th style="width:15%px;">ユーザID</th>
                                <th style="width:15%px;">ユーザータイプ</th>
                                <th style="width:15%px;">メールアドレス</th>
								<th style="width:15%px;">店番/店名/科目/口座番号/口座名義</th>
                            </tr>
                            <tr ng-repeat="pushType in pushTypeCtrl.pushTypeList">
                                <td >{{ pushType.userID}}</td>
								<td>{{ pushType.userType | userTypeTitle }}</td>
								<td>{{ pushType.email}}</td>
								<td>{{ pushType.accountNumber}}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
</div>
 </script>

