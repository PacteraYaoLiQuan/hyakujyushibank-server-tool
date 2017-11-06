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
		<h2>Push通知承認</h2>
								<input type="hidden" id="pushRecordAppList" name="pushRecordAppList"
							ng-model="pushRecordAppListCtrl.user" value="${sessionScope.Push}" />
							<input type="hidden" id="pushRecordBankCodeAppList" name="pushRecordAppList"
							ng-model="pushRecordAppListCtrl.user" value="${sessionScope.bank_cd}" />
	</div>
	<div class="card">
		<div class="card-header">
		</div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="pushRecordAppListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="3%" />
						<col   width="3%"/>
						<col width="24%" />
						<col width="21%" />
						<col width="22%" />
						<col width="15%" />
						<%-- <col width="15%" />
						<col width="13%" />
						<col width="23%" /> --%>
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'pushRecordAppListCtrl/table/sortAndFilter/checkItemAll.html' }" style="padding-bottom:19px"><label
							class="checkbox checkbox-inline m-r-5"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable"> <i
								class="input-helper"></i>
						</label></td>
						<td>
						<a ng-show="w.visible" style="cursor: pointer;"
							ng-click="pushRecordAppListCtrl.table.pushErr(w)"> <img
								src="../img/error.png"></a>
<!-- 							
							<span a ng-show="!visible"  ng-mouseenter="show = true" ng-mouseleave="show = false"><img
								src="../img/error.png"></a></span> -->
						</td>
						<td data-title="'受付番号'" filter="{ 'accountAppSeq': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountAppSeq'">{{ w.accountAppSeq }}<span><div ng-show="show">{{ w.pushErr }}</div></span></td>
						<td data-title="'受付日時'"
							filter="{ 'receiptDate': 'pushRecordAppListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'receiptDate'">{{ w.receiptDate }}</td>
						<td data-title="'氏名'" filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						<td data-title="'ステータス'" filter="{ 'status': 'ng-table/filters/select-clear.html'}"
							sortable="'status'"
							filter-data="pushRecordAppListCtrl.table.filterList">{{w.status
							| statusTitle}}</td>
						<!-- <td data-title="'配信状況'" filter="{ 'sendStatus': 'ng-table/filters/select-clear.html'}"
							sortable="'sendStatus'"
							filter-data="pushRecordAppListCtrl.table.filterList3">
							{{w.sendStatus | sendStatusTitle}}</td>	
						<td data-title="'配信日時'"
							filter="{ 'pushDate': 'pushRecordAppListCtrl/table/sortAndFilter/datepicker-1.html' }"
							sortable="'pushDate'">{{ w.pushDate }}</td>	
						<td data-title="'Push開封状況'" filter="{ 'pushStatus': 'ng-table/filters/select-clear.html'}"
							sortable="'pushStatus'"
							filter-data="pushRecordAppListCtrl.table.filterList2">
							{{w.pushStatus | pushStatusTitle}}</td> -->
						<td data-title="'詳細'"
                            filter="{ '': 'pushRecordAppListCtrl/table/sortAndFilter/clearFilter.html' }">
                            <button class="btn bgm-lightgreen" cc="{{$index}}"
                                ng-click="pushRecordAppListCtrl.table.openNa('na', w)">
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
	id="pushRecordAppListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="pushRecordAppListCtrl.table.sortAndFilter.filter().receiptDate" type="text" id='receiptDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
  
  <script type="text/ng-template"
	id="pushRecordAppListCtrl/table/sortAndFilter/datepicker-1.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="pushRecordAppListCtrl.table.sortAndFilter.filter().pushDate" type="text" id='pushDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="pushRecordAppListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="pushRecordAppListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="pushRecordAppListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-5">
            <input type="checkbox" value="" ng-click="pushRecordAppListCtrl.table.checkItemAll()" ng-model="pushRecordAppListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="pushRecordAppListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="completeBtnDisabled" button-disabled="completeBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordAppListCtrl.table.selectAdmitBtn()">一括承認</button>
	<button ng-disabled="completeBtnDisabled" button-disabled="completeBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordAppListCtrl.table.deleteBtn()">承認取り下げ</button>
</div>       
</script>
<script type="text/ng-template"
	id="pushRecordAppListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordAppListCtrl.table.selectAdmitBtn()">一括承認</button>
  		<button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordAppListCtrl.table.deleteBtn()">承認取り下げ</button>
</div>       
</script>
<script type="text/ng-template" id="detailPopup.html">
<div class="popup-title">
    <i ng-click="modalInstanceCtrls.ok()"
        class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
    <div class="container-fluid">
        <div class="block-header">
            <h2>申込詳細</h2>
        </div>

        <div class="card">

            <div class="card-body card-padding detail-popup scrool-size-1">
                <div class="row detail-c-item">
                    <div class="col-sm-12">
                        <title>個人情報</title>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">氏名(漢字)</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.name}}</span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="text-item">氏名(カタカナ)</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.kanaName}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">生年月日</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.birthday}}</span>
                        </div>
                    </div>

                    <div class="col-sm-2">
                        <div class="text-item">性別</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.sex | sexTitle}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">満年齢</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.age}}</span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="text-item">勤務先名</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.workName}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2  col-sm-offset-1">
                        <div class="text-item">住所</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.postCode}}</span><br /> <span>{{modalInstanceCtrls.address1}}{{modalInstanceCtrls.address2}}</span><br />
                            <span>{{modalInstanceCtrls.kanaAddress}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">電話番号</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>自宅電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrls.teleNumber}}</span><br />
                            <span>携帯電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrls.phoneNumber}}</span><br />
                            <span>勤務先電話番号&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrls.workTeleNumber}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="text-item">既に口座をお持ちの方</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.holdAccount | holdTitle}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item" ng-hide="modalInstanceCtrls.holdHide">
                    <div class="col-sm-2 col-sm-offset-3">
                        <div class="text-item">店名</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.holdAccountBank}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item" ng-hide="modalInstanceCtrls.holdHide">
                    <div class="col-sm-2 col-sm-offset-3">
                        <div class="text-item">口座番号</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.holdAccountNumber}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item" ng-hide="modalInstanceCtrls.holdHide">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="text-item">ダイレクトバンキングサービスのご契約</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.directServicesContract | holdTitle}}</span>
                        </div>
                    </div>
                </div>
                <!-- <div class="row detail-c-item"
                    ng-hide="modalInstanceCtrls.contractHide">
                    <div class="col-sm-2 col-sm-offset-3">
                        <div class="text-item">店名</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.directServicesContractBank}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item"
                    ng-hide="modalInstanceCtrls.contractHide">
                    <div class="col-sm-2 col-sm-offset-3">
                        <div class="text-item">口座番号</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.directServicesContractAccountNumber}}</span>
                        </div>
                    </div>
                </div> -->
                <div class="row detail-c-item">
                    <div class="col-sm-12">
                        <title>申込情報</title>
                    </div>
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">受付番号</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.accountAppSeq}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">本人確認書類</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.identificationType |
                                identificationTypeTitle}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-5 col-sm-offset-5">
                        <div class="preloader pls-teal"
                            ng-hide="modalInstanceCtrls.imageLoadHide">
                            <svg class="pl-circular" viewBox="25 25 50 50"> <circle
                                class="plc-path" cx="50" cy="50" r="20"></circle> </svg>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1"
                        ng-hide="!modalInstanceCtrls.imageLoadHide">
                        <img
                            src="data:image/png;base64,{{modalInstanceCtrls.identificationImage}}"
                            alt="">
                    </div>
                </div>
                <div class="row detail-c-item" ng-hide="modalInstanceCtrls.imageHide">
                    <div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
                </div>
                <div class="row detail-c-item" ng-hide="modalInstanceCtrls.imageHide">
                    <div class="col-sm-10 col-sm-offset-1"
                        ng-hide="!modalInstanceCtrls.imageLoadHide">
                        <img
                            src="data:image/png;base64,{{modalInstanceCtrls.identificationImageBack}}"
                            alt="">
                    </div>
                </div>
                <!--<div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">生活状況確認書類</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.livingConditions |
                                livingConditionsTitle}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-5 col-sm-offset-5">
                        <div class="preloader pls-teal"
                            ng-hide="modalInstanceCtrls.imageLoadHide">
                            <svg class="pl-circular" viewBox="25 25 50 50">
                          <circle class="plc-path" cx="50" cy="50"
                                    r="20"></circle>
                      </svg>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1"
                        ng-hide="!modalInstanceCtrls.imageLoadHide">
                        <img
                            src="data:image/png;base64,{{modalInstanceCtrls.livingConditionsImage}}"
                            alt="">
                    </div>
                </div>-->
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">申込日</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.applicationDate}}</span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="text-item">申込受付日付</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.receiptDate}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">取引種類</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.transactionType|
                                transactionTypeTitle}}</span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="text-item">申込サービス</div>
                    </div>

                    <div class="col-sm-3">
                        <div class="text-item">
                            <span><option
                                    ng-repeat="applyService in modalInstanceCtrls.applyServiceList">{{applyService
                                    | applyServiceTitle}}<br />
                                </option></span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">発行するカード種類</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.cardType| cardTypeTitle}}</span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="text-item">利用しないサービス</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span><option
                                    ng-repeat="noApplicationService in modalInstanceCtrls.noApplicationServiceList"
                                    value="{{noApplicationService}}">{{noApplicationService
                                    | noApplicationServiceTitle}}<br />
                                </option></span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">取引目的</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span><option
                                    ng-repeat="traPurposes in modalInstanceCtrls.tradingPurposes">{{traPurposes
                                    | traPurposesTitle: modalInstanceCtrls.otherTradingPurposes}}<br />
                                </option></span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="text-item">職業</div>
                    </div>
                    <div class="col-sm-3">
                        <div class="text-item">
                            <span><option
                                    ng-repeat="occupation in modalInstanceCtrls.occupation">{{occupation
                                    | occupationTitle: modalInstanceCtrls.otherOccupations}}<br />
                                </option></span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="text-item">キャッシュカード暗証番号</div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="text-item">
                            <span>●●●●</span>
                        </div>
                    </div>
                    <!-- <div class="text-item" ng-hide="modalInstanceCtrls.passwordHide">
                            <span>●●●●</span><a class="zmdi zmdi-eye zmdi-hc-fw"
                                ng-click="modalInstanceCtrls.showSecPwd()"></a>
                        </div>
                        <div class="text-item" ng-hide="!modalInstanceCtrls.passwordHide">
                            <span>{{modalInstanceCtrls.securityPassword}}</span><a
                                class="zmdi zmdi-eye-off zmdi-hc-fw"
                                ng-click="modalInstanceCtrls.hideSecPwd()"></a>
                        </div>
                    </div>-->
                </div>
                <!-- <div class="row detail-c-item">
                <div class="col-sm-10 col-sm-offset-1">
                    <div class="text-item">
                        口座開設と同時にお申込みいただける商品指定
                    </div>
                </div>
                <div class="col-sm-8 col-sm-offset-3">
                    <div class="text-item">
                        <span><option ng-repeat="goodsAppoint in modalInstanceCtrls.goodsAppointed"  >{{goodsAppoint | goodsAppointTitle}}<br /></option></span>
                    </div>
                </div>
            </div>-->
                <div class="row detail-c-item"
                    ng-hide="modalInstanceCtrls.contractHide">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="text-item">ダイレクトバンキングカード暗証番号</div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="text-item">
                             <span>●●●●</span>
                        </div>
                    </div>
                    <!-- <div class="col-sm-6">
                        <div class="text-item" ng-hide="modalInstanceCtrls.dirPasswordHide">
                            <span>●●●●</span><a class="zmdi zmdi-eye zmdi-hc-fw"
                                ng-click="modalInstanceCtrls.showDirPwd()"></a>
                        </div>
                        <div class="text-item"
                            ng-hide="!modalInstanceCtrls.dirPasswordHide">
                            <span>{{modalInstanceCtrls.directServicesCardPassword}}</span><a
                                class="zmdi zmdi-eye-off zmdi-hc-fw"
                                ng-click="modalInstanceCtrls.hideDirPwd()"></a>
                        </div>
                    </div>-->
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-11 col-sm-offset-1">
                        <div class="text-item">テレホンバンキングサービス振込限度額</div>
                    </div>
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">事前登録振込</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>１回あたり{{modalInstanceCtrls.telRegisterPerTrans}}万円</span><br />
                            <span>１日あたり{{modalInstanceCtrls.telRegisterPerDay}}万円</span>
                        </div>
                    </div>
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">都度登録振込</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>１回あたり{{modalInstanceCtrls.telOncePerTrans}}万円</span><br /> <span>１日あたり{{modalInstanceCtrls.telOncePerDay}}万円</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item"
                    ng-hide="modalInstanceCtrls.contractHide">
                    <div class="col-sm-11 col-sm-offset-1">
                        <div class="text-item">インターネットバンキングサービス振込限度額</div>
                    </div>
                    <div class="col-sm-11 col-sm-offset-1">
                        <div class="text-item">モバイルバンキングサービス振込限度額</div>
                    </div>
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">事前登録振込</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>１回あたり{{modalInstanceCtrls.internetRegisterPerTrans}}万円</span><br />
                            <span>１日あたり{{modalInstanceCtrls.internetRegisterPerDay}}万円</span>
                        </div>
                    </div>
                    <div class="col-sm-2 col-sm-offset-1">
                        <div class="text-item">都度登録振込</div>
                    </div>
                    <div class="col-sm-8">
                        <div class="text-item">
                            <span>１回あたり{{modalInstanceCtrls.internetOncePerTrans}}万円</span><br />
                            <span>１日あたり{{modalInstanceCtrls.internetOncePerDay}}万円</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">
                        <div class="text-item">ひろぎんネット支店の口座開設の動機</div>
                    </div>
                    <div class="col-sm-8 col-sm-offset-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.accountAppMotive |
                                accountAppMotiveTitle: modalInstanceCtrls.accountAppOtherMotive}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">
                        <div class="text-item">ひろぎんネット支店を知った経緯</div>
                    </div>
                    <div class="col-sm-8 col-sm-offset-3">
                        <div class="text-item">
                            <span>{{modalInstanceCtrls.knowProcess | knowProcessTitle:
                                modalInstanceCtrls.knowOtherProcess}}</span>
                        </div>
                    </div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-11 col-sm-offset-1">
                        <div class="text-item">ステータス変更履歴</div>
                    </div>
                </div>
                <div class="row detail-c-item" >
                    <div class="col-sm-10 col-sm-offset-1">
                        <table class="dp-table" style="margin-top:-23px;">
                            <tr>
                                <th>申込処理ステータス</th>
                                <th>端末ID</th>
　　　　　　　　　　<th>変更日時</th>
　　　　　　　　　　<th>配信状態</th>
                                <th>配信日時</th>
                                <th>Push開封状況</th>
                            </tr>
                            <tr ng-repeat="push in modalInstanceCtrls.pushData">
                                <td>{{ push.status | statusTitle}}</td>
                                <td>{{ push.deviceTokenId }}</td>
　　　　　　　　　　<td>{{ push.createDate }}</td>
                                <td>{{ push.sendStatus | sendStatusTitle }}</td>
                                <td>{{ push.pushDate }}</td>
                                <td>{{ push.pushStatus | pushStatusTitle }}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
<div class="card-body card-padding detail-popup ">
        <div class="row detail-c-item">
            <div class="col-sm-2 col-sm-offset-1">
                <div class="text-item">ステータス</div>
            </div>
            <div class="col-sm-4">
               <span>{{modalInstanceCtrls.status | statusTitle}}</span>
            </div>
        </div>
     </div>
   </div>
</div>
</script>
<!--  <script type="text/ng-template" id="detailPopup1.html">
<div class="popup-title">
	<i ng-click="modalPushErrAppraisalCtrl.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-1">
		<div class="block-header">
			<h2>配信失敗</h2>
		</div>
		<div class="card">
			<div class="card-body card-padding detail-popup">
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">配信失敗</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalPushErrAppraisalCtrl.pushErr}}</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</script>
-->


