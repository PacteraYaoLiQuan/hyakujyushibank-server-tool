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
		<h2>申込一覧</h2>
						<input type="hidden" id="accountAppList" name="accountAppList"
							ng-model="accountAppListCtrl.user" value="${sessionScope.Account}" />
							<input type="hidden" id="accountBankCodeAppList" name="accountAppList"
							ng-model="accountAppListCtrl.user" value="${sessionScope.bank_cd}" />
	</div>

	<div class="card">
		<div class="card-header">
			<h2>出力条件</h2>
		</div>
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-5 m-b-15">
					<div class="input-group">
						<span class="input-group-addon"><i
							class="zmdi zmdi-view-compact zmdi-hc-fw"></i></span> <select
							ng-init="1" 
							ng-model="accountAppListCtrl.selectStatus" chosen multiple
							data-placeholder="ステータス" class="w-100">
							<option value="1">受付</option>
							<option value="2">処理中</option>
							<option value="3">完了</option>
							<option value="4">却下（総合的判断）</option>
							<option value="5">却下（本人確認連絡不可）</option>
							<option value="6">却下（郵便受取不可）</option>
							<option value="7">却下（内容不備）</option>
							<option value="8">却下（本人申出）</option>
						</select>
					</div>
				</div>
				<div class="col-sm-5 m-b-15">
					<div class="input-group form-group dp-blue">
	                    <span class="input-group-addon"><i class="zmdi zmdi-calendar"></i></span>
	                    <div class="dtp-container fg-line">
	                    	<input type="text" id='dtPopup2' class="form-control date-time-picker" >
	                    </div>
	                </div>
				</div>
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-disabled="accountAppListCtrl.btnDisabled"
							ng-click="accountAppListCtrl.cc('size')" class="btn bgm-lightblue">一覧出力</button>
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
				<table ng-table="accountAppListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="3%" />
						<col width="14%" />
						<col width="14%" />
						<col width="12%" />
						<col width="15%" />
						<col width="13%" />
						<col width="13%" />
						<col width="13%" />
						<col width="3%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'accountAppListCtrl/table/sortAndFilter/checkItemAll.html' }" style="padding-bottom:19px"><label
							class="checkbox checkbox-inline m-r-5"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'受付番号'" filter="{ 'accountAppSeq': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountAppSeq'">{{ w.accountAppSeq }}</td>
						<td data-title="'受付日時'"
							filter="{ 'receiptDate': 'accountAppListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'receiptDate'">{{ w.receiptDate }}</td>
						<td data-title="'氏名'" filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						<td data-title="'ステータス'" filter="{ 'status': 'ng-table/filters/select-clear.html'}"
							sortable="'status'"
							filter-data="accountAppListCtrl.table.filterList">{{w.status
							| statusTitle}}</td>
						<!-- <td data-title="'変更履歴'"
                            sortable="'pushHistory'"><a
                            ng-click="accountAppListCtrl.table.openHistory('na', w)">変更履歴</a></td>
                         -->
						<td data-title="'Push開封状態'" filter="{ 'pushStatus': 'ng-table/filters/select-clear.html'}"
							sortable="'pushStatus'"
							filter-data="accountAppListCtrl.table.filterList2">
							<a ng-click="accountAppListCtrl.table.openHistory('na', w)">
							{{w.pushStatus | pushStatusTitle}}</a></td>
						<td data-title="'電話番号鑑定'" filter="{ 'appraisalTelResult': 'ng-table/filters/select-clear.html'}"
							sortable="'appraisalTelResult'" filter-data="accountAppListCtrl.table.filterList3"><a ng-click="accountAppListCtrl.table.openTel('na', w)">{{ w.appraisalTelResult | appraisalResultTitle}}</a></td>
						<td data-title="'ＩＰアドレス鑑定'" filter="{ 'appraisalIPResult': 'ng-table/filters/select-clear.html'}"
							sortable="'appraisalIPResult'" filter-data="accountAppListCtrl.table.filterList3"><a ng-click="accountAppListCtrl.table.openIP('na', w)">{{ w.appraisalIPResult | appraisalResultTitle}}</a></td>						
						<td data-title="'詳細'"
							filter="{ '': 'accountAppListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="accountAppListCtrl.table.openNa('na', w)">
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
	id="accountAppListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="accountAppListCtrl.table.sortAndFilter.filter().receiptDate" type="text" id='receiptDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="accountAppListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="accountAppListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="accountAppListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-5">
            <input type="checkbox" value="" ng-click="accountAppListCtrl.table.checkItemAll()" ng-model="accountAppListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="accountAppListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
		<button ng-disabled="csvOutput" button-disabled="csvOutput" class="btn bgm-blue m-l-5 btn-height" customize-button-click="accountAppListCtrl.table.csvOutput()">顧客CSV出力</button>
        <button ng-disabled="lsOutput" button-disabled="lsOutput" class="btn bgm-blue m-l-5" customize-button-click="accountAppListCtrl.table.lsOutput('size')">帳票CSV出力</br>本人確認資料印刷</button>
        <button ng-disabled="completeBtn" button-disabled="completeBtn" class="btn bgm-red m-l-5 btn-height" customize-button-click="accountAppListCtrl.table.completeBtn()">完了消込</button>
</div>       
</script>
<script type="text/ng-template"
	id="accountAppListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
		<button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="accountAppListCtrl.table.csvOutput()">顧客CSV出力</button>
        <button disabled class="btn bgm-blue m-l-5" customize-button-click="accountAppListCtrl.table.lsOutput('size')">帳票CSV出力</br>本人確認資料印刷</button>
        <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="accountAppListCtrl.table.completeBtn()">完了消込</button>
</div>       
</script>
<script type="text/ng-template" id="downLoad.html">
<div class="popup-title">
        <i ng-click="modalInstanceController1.close()" class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
      <embed align="center" width="1080px" height="650px" name="plugin" id="plugin" src="{{openPDF}}" type="application/pdf" internalinstanceid="41">
</script>

<script type="text/ng-template" id="pdfOpen.html">
<div class="popup-title" style="width:1080px">
        <i ng-click="modalInstanceController2.close()" class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
      <embed align="center" width="1080px" height="650px" src="{{openPDF}}" type="application/pdf" internalinstanceid="41">

      <embed align="center" width="1080px" height="650px"  src="{{openPDF1}}" type="application/pdf" internalinstanceid="41">

      <embed align="center" width="1080px" height="650px"  src="{{openPDF2}}" type="application/pdf" internalinstanceid="41">


</script>

<!--  <script type="text/ng-template" id="pdfOpen1.html">
<div class="popup-title">
        <i ng-click="modalInstanceController4.close()" class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
    
</script>-->

<!--<script type="text/ng-template" id="pdfOpen2.html">
<div class="popup-title">
        <i ng-click="modalInstanceController5.close()" class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
      <embed align="center" width="640px" height="320px" name="plugin" id="plugin" src="{{openPDF}}" type="application/pdf" internalinstanceid="41">
</script>-->

<script type="text/ng-template" id="detailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl.ok()"
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
							<span>{{modalInstanceCtrl.name}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">氏名(カタカナ)</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.kanaName}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">生年月日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.birthday}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">性別</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.sex | sexTitle}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">満年齢</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.age}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">勤務先名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.workName}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2  col-sm-offset-1">
						<div class="text-item">住所</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrl.postCode}}</span><br /> <span>{{modalInstanceCtrl.address1}}{{modalInstanceCtrl.address2}}</span><br />
							<span>{{modalInstanceCtrl.kanaAddress}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">電話番号</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>自宅電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrl.teleNumber}}</span><br />
							<span>携帯電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrl.phoneNumber}}</span><br />
							<span>勤務先電話番号&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrl.workTeleNumber}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">既に口座をお持ちの方</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl.holdAccount | holdTitle}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl.holdHide">
					<div class="col-sm-2 col-sm-offset-3">
						<div class="text-item">店名</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl.holdAccountBank}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl.holdHide">
					<div class="col-sm-2 col-sm-offset-3">
						<div class="text-item">口座番号</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl.holdAccountNumber}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl.holdHide">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">ダイレクトバンキングサービスのご契約</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl.directServicesContract | holdTitle}}</span>
						</div>
					</div>
				</div>
				<!-- <div class="row detail-c-item"
					ng-hide="modalInstanceCtrl.contractHide">
					<div class="col-sm-2 col-sm-offset-3">
						<div class="text-item">店名</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl.directServicesContractBank}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"
					ng-hide="modalInstanceCtrl.contractHide">
					<div class="col-sm-2 col-sm-offset-3">
						<div class="text-item">口座番号</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl.directServicesContractAccountNumber}}</span>
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
							<span>{{modalInstanceCtrl.accountAppSeq}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">本人確認書類</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrl.identificationType |
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
							ng-hide="modalInstanceCtrl.imageLoadHide">
							<svg class="pl-circular" viewBox="25 25 50 50"> <circle
								class="plc-path" cx="50" cy="50" r="20"></circle> </svg>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1"
						ng-hide="!modalInstanceCtrl.imageLoadHide">
						<img
							src="data:image/png;base64,{{modalInstanceCtrl.identificationImage}}"
							alt="">
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl.imageHide">
					<div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl.imageHide">
					<div class="col-sm-10 col-sm-offset-1"
						ng-hide="!modalInstanceCtrl.imageLoadHide">
						<img
							src="data:image/png;base64,{{modalInstanceCtrl.identificationImageBack}}"
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
							<span>{{modalInstanceCtrl.livingConditions |
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
							ng-hide="modalInstanceCtrl.imageLoadHide">
							<svg class="pl-circular" viewBox="25 25 50 50">
                          <circle class="plc-path" cx="50" cy="50"
									r="20"></circle>
                      </svg>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1"
						ng-hide="!modalInstanceCtrl.imageLoadHide">
						<img
							src="data:image/png;base64,{{modalInstanceCtrl.livingConditionsImage}}"
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
							<span>{{modalInstanceCtrl.applicationDate}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">申込受付日付</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.receiptDate}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">取引種類</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.transactionType|
								transactionTypeTitle}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">申込サービス</div>
					</div>

					<div class="col-sm-3">
						<div class="text-item">
							<span><option
									ng-repeat="applyService in modalInstanceCtrl.applyServiceList">{{applyService
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
							<span>{{modalInstanceCtrl.cardType| cardTypeTitle}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">利用しないサービス</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span><option
									ng-repeat="noApplicationService in modalInstanceCtrl.noApplicationServiceList"
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
									ng-repeat="traPurposes in modalInstanceCtrl.tradingPurposes">{{traPurposes
									| traPurposesTitle: modalInstanceCtrl.otherTradingPurposes}}<br />
								</option></span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">職業</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span><option
									ng-repeat="occupation in modalInstanceCtrl.occupation">{{occupation
									| occupationTitle: modalInstanceCtrl.otherOccupations}}<br />
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
					<!-- <div class="text-item" ng-hide="modalInstanceCtrl.passwordHide">
							<span>●●●●</span><a class="zmdi zmdi-eye zmdi-hc-fw"
								ng-click="modalInstanceCtrl.showSecPwd()"></a>
						</div>
						<div class="text-item" ng-hide="!modalInstanceCtrl.passwordHide">
							<span>{{modalInstanceCtrl.securityPassword}}</span><a
								class="zmdi zmdi-eye-off zmdi-hc-fw"
								ng-click="modalInstanceCtrl.hideSecPwd()"></a>
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
                        <span><option ng-repeat="goodsAppoint in modalInstanceCtrl.goodsAppointed"  >{{goodsAppoint | goodsAppointTitle}}<br /></option></span>
                    </div>
                </div>
            </div>-->
				<div class="row detail-c-item"
					ng-hide="modalInstanceCtrl.contractHide">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">ダイレクトバンキングカード暗証番号</div>
					</div>
                    <div class="col-sm-6 col-md-3">
					    <div class="text-item">
						     <span>●●●●</span>
					    </div>
					</div>
					<!-- <div class="col-sm-6">
						<div class="text-item" ng-hide="modalInstanceCtrl.dirPasswordHide">
							<span>●●●●</span><a class="zmdi zmdi-eye zmdi-hc-fw"
								ng-click="modalInstanceCtrl.showDirPwd()"></a>
						</div>
						<div class="text-item"
							ng-hide="!modalInstanceCtrl.dirPasswordHide">
							<span>{{modalInstanceCtrl.directServicesCardPassword}}</span><a
								class="zmdi zmdi-eye-off zmdi-hc-fw"
								ng-click="modalInstanceCtrl.hideDirPwd()"></a>
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
							<span>１回あたり{{modalInstanceCtrl.telRegisterPerTrans}}万円</span><br />
							<span>１日あたり{{modalInstanceCtrl.telRegisterPerDay}}万円</span>
						</div>
					</div>
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">都度登録振込</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>１回あたり{{modalInstanceCtrl.telOncePerTrans}}万円</span><br /> <span>１日あたり{{modalInstanceCtrl.telOncePerDay}}万円</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"
					ng-hide="modalInstanceCtrl.contractHide">
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
							<span>１回あたり{{modalInstanceCtrl.internetRegisterPerTrans}}万円</span><br />
							<span>１日あたり{{modalInstanceCtrl.internetRegisterPerDay}}万円</span>
						</div>
					</div>
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">都度登録振込</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>１回あたり{{modalInstanceCtrl.internetOncePerTrans}}万円</span><br />
							<span>１日あたり{{modalInstanceCtrl.internetOncePerDay}}万円</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="text-item">ひろぎんネット支店の口座開設の動機</div>
					</div>
					<div class="col-sm-8 col-sm-offset-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.accountAppMotive |
								accountAppMotiveTitle: modalInstanceCtrl.accountAppOtherMotive}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="text-item">ひろぎんネット支店を知った経緯</div>
					</div>
					<div class="col-sm-8 col-sm-offset-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl.knowProcess | knowProcessTitle:
								modalInstanceCtrl.knowOtherProcess}}</span>
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
　　　　　　　　　　<th>配信状況</th>
								<th>配信日時</th>
								<th>Push開封状況</th>
							</tr>
							<tr ng-repeat="push in modalInstanceCtrl.pushData">
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
				<div class="form-group">
					<div class="fg-line">
						<div class="select">
							<select class="form-control"
								ng-model="modalInstanceCtrl.selectStatus">
							<option value="1">受付</option>
							<option value="2">処理中</option>
							<option value="3">完了</option>
							<option value="4">却下（総合的判断）</option>
							<option value="5">却下（本人確認連絡不可）</option>
							<option value="6">却下（郵便受取不可）</option>
							<option value="7">却下（内容不備）</option>
                            <option value="8">却下（本人申出）</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="btn-colors btn-demo col-sm-3 col-sm-offset-2">
				<button class="btn bgm-blue waves-effect"
					ng-click="modalInstanceCtrl.statusUpd()"
					ng-disabled="modalInstanceCtrl.UpdbtnDisabled">ステータス更新</button>
			</div>
		</div>
			</div>
		</div>
	</div>
</script>


<script type="text/ng-template" id="detailPopup2.html">
<div class="popup-title">
	<i ng-click="modalTelAppraisalCtrl.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid scrool-size-1">
		<div class="block-header">
			<h2>携帯電話番号鑑定結果</h2>
		</div>

		<div class="card">

			<div class="card-body card-padding detail-popup">

				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">電話番号</div>
					</div>
					<div class="col-sm-5">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.phoneNumber}}</span>
						</div>
					</div>
					<div class="col-sm-3" ng-hide="modalTelAppraisalCtrl.windowHide">
						<button class="btn bgm-blue waves-effect"
							ng-click="modalTelAppraisalCtrl.phoneNumberFind()"
							ng-disabled="modalTelAppraisalCtrl.PhoneNumDisabled">再取得する</button>
							
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">検索結果</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Result2 | resultTitle}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">過去12ヶ月以内の検索数</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span style='color:red;'><b>{{modalTelAppraisalCtrl.TC_Count2}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">直近判定結果</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span style='color:red;'><b>{{modalTelAppraisalCtrl.TC_Tacsflag2 | tacsflagTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalTelAppraisalCtrl.hideFlg2">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">移転番号</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Movetel2}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">過去の状況</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span style='color:red;'><b>{{modalTelAppraisalCtrl.TC_Attention2 | attentionTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">キャリア</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Carrier2}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">加入期間</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Month2}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">照会データ最新年月</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_LatestDate2}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">利用ステータス</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-8 col-sm-offset-1">
						<table class="dp-table">
							<tr>
								<th>更新日</th>
								<th>ステータス</th>
							</tr>
							<tr ng-repeat="push in modalTelAppraisalCtrl.TC_TacsflagList2">
								<td class="col-sm-4">{{ push.date}}</td>
								<td class="col-sm-4" style="text-align:left;">{{ push.tacsflag  | tacsflagTitle}}</td>
							</tr>
						</table>
					</div>
				</div>			
			</div>
		</div>

		<div class="block-header">
			<h2>自宅電話番号鑑定結果</h2>
		</div>
		<div class="card">

			<div class="card-body card-padding detail-popup">

				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">電話番号</div>
					</div>
					<div class="col-sm-5">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.teleNumber}}</span>
						</div>
					</div>
					<div class="col-sm-3" ng-hide="modalTelAppraisalCtrl.windowHide1">
						<button class="btn bgm-blue waves-effect"
							ng-click="modalTelAppraisalCtrl.teleNumberFind()"
							ng-disabled="modalTelAppraisalCtrl.TeleNumberDisabled">再取得する</button>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">検索結果</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Result1 | resultTitle}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">過去12ヶ月以内の検索数</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span style='color:red;'><b>{{modalTelAppraisalCtrl.TC_Count1}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">直近判定結果</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span style='color:red;'><b>{{modalTelAppraisalCtrl.TC_Tacsflag1 | tacsflagTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalTelAppraisalCtrl.hideFlg1">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">移転番号</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Movetel1}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">過去の状況</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span style='color:red;'><b>{{modalTelAppraisalCtrl.TC_Attention1| attentionTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">キャリア</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Carrier1}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">加入期間</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_Month1}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">照会データ最新年月</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalTelAppraisalCtrl.TC_LatestDate1}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">利用ステータス</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-8 col-sm-offset-1">
						<table class="dp-table">
							<tr>
								<th>更新日</th>
								<th>ステータス</th>
							</tr>
							<tr ng-repeat="push in modalTelAppraisalCtrl.TC_TacsflagList1">
								<td class="col-sm-4">{{ push.date}}</td>
								<td class="col-sm-4" style="text-align:left;">{{ push.tacsflag  | tacsflagTitle}}</td>
							</tr>
						</table>
					</div>
				</div>			
			</div>
		</div>

	</div>
</div>

</script>

<script type="text/ng-template" id="detailPopup3.html">
<div class="popup-title">
	<i ng-click="modalIPAppraisalCtrl.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-1">
		<div class="block-header">
			<h2>ＩＰアドレス鑑定結果</h2>
		</div>

		<div class="card">

			<div class="card-body card-padding detail-popup">

				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1" style="margin-left:48px;">
						<div class="text-item">ＩＰアドレス</div>
					</div>
					<div class="col-sm-4"  style="margin-left:10px;">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.ipAddress}}</span>
						</div>
					</div>
					<div class="col-sm-3" ng-hide="modalIPAppraisalCtrl.windowHide2">
						<button class="btn bgm-blue waves-effect"
							ng-click="modalIPAppraisalCtrl.IpAddressFind()"
							ng-disabled="modalIPAppraisalCtrl.IpAddressDisabled">再取得する</button>
					</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">ＩＰ脅威度</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span style='color:red;'><b>{{modalIPAppraisalCtrl.IC_IpThreat | ipThreatTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">国名</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.IC_CountryName}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">国脅威度</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span style='color:red;'><b>{{modalIPAppraisalCtrl.IC_CountryThreat | ipThreatTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">公共ＩＰアドレス利用状況</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span style='color:red;'><b>{{modalIPAppraisalCtrl.IC_PSIP | psipTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">プロキシーサーバーの利用状況</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span style='color:red;'><b>{{modalIPAppraisalCtrl.IC_Proxy | proxyTitle}}</b></span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">ＩＰアドレス属性</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.IC_isMobile}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">登録企業</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.IC_CompanyName}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">登録企業ドメイン</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.IC_CompanyDomain}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">ＩＰ登録地</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.IC_CompanyCity}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">ＩＰアドレスと郵便番号の直線距離</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span>{{modalIPAppraisalCtrl.IC_Distance}}</span>
						</div>
					</div>
				</div>		
				<div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">2週間以内の検索回数</div>
					</div>
					<div class="col-sm-7">
						<div class="text-item">
							<span style='color:red;'><b>{{modalIPAppraisalCtrl.IC_TwoWeeksCount}}</b></span>
						</div>
					</div>
				</div>		
			</div>
		</div>
	</div>
</div>

</script>

<script type="text/ng-template" id="detailPopup4.html">
<div class="popup-title">
    <i ng-click="modalHistoryPushCtrl.ok()"
        class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
        <div class="block-header">
            <h2>ステータス変更履歴</h2>
        </div>
        <div class="card">
            <div class="card-body card-padding detail-popup">
                <div class="row detail-c-item">
                    <div class="col-sm-10 col-sm-offset-1">
                        <table class="dp-table">
                            <tr>
                                <th>申込処理ステータス</th>
                                <th>端末ID</th>
　　　　　　　　　　<th>変更日時</th>
　　　　　　　　　　<th>配信状態</th>
                                <th>配信日時</th>
                                <th>Push開封状況</th>
								<th>Push開封状況(DB)</th>
                            </tr>
                            <tr ng-repeat="push in modalHistoryPushCtrl.pushData">
                                <td>{{ push.status | statusTitle}}</td>
                                <td>{{ push.deviceTokenId }}</td>
                                <td>{{ push.createDate }}</td>
                                <td>{{ push.sendStatus | sendStatusTitle }}</td>
                                <td>{{ push.pushDate }}</td>
                                <td>{{ push.pushStatus | pushStatusTitle }}</td>
								<td>{{ push.pushStatusDB | pushStatusTitleDB }}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
</div>
 </script>