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
		<h2>ローン申込一覧</h2>
						<input type="hidden" id="accountLoanList" name="accountLoanList"
							ng-model="accountLoanListCtrl.user" value="${sessionScope.AccountLoan}" />
							<input type="hidden" id="accountBankCodeAppList" name="accountBankCodeAppList"
							ng-model="accountLoanListCtrl.user" value="${sessionScope.bank_cd}" />
	</div>
	<div class="card">
		<div class="card-header">
		</div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="accountLoanListCtrl.table.sortAndFilter"
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
							filter="{ 'select': 'accountLoanListCtrl/table/sortAndFilter/checkItemAll.html' }" style="padding-bottom:19px"><label
							class="checkbox checkbox-inline m-r-5"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'受付番号'" filter="{ 'accountAppSeq': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountAppSeq'">{{ w.accountAppSeq }}</td>
						<td data-title="'受付日時'"
							filter="{ 'receiptDate': 'accountLoanListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'receiptDate'">{{ w.receiptDate }}</td>
						<td data-title="'氏名'" filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						<td data-title="'ステータス'" filter="{ 'status': 'ng-table/filters/select-clear.html'}"
							sortable="'status'"
							filter-data="accountLoanListCtrl.table.filterList">{{w.status
							| statusTitle}}</td>
						<td data-title="'PUSH開封状況'" filter="{ 'pushStatus': 'ng-table/filters/select-clear.html'}"
							sortable="'pushStatus'"
							filter-data="accountLoanListCtrl.table.filterList2">
							<a ng-click="accountLoanListCtrl.table.openHistory('na', w)">
							{{w.pushStatus | pushStatusTitle}}</a></td>
						<td data-title="'商品名'" filter="{ 'appraisalTelResult': 'ng-table/filters/select-clear.html'}"
							sortable="'loanType'" filter-data="accountLoanListCtrl.table.filterList3">{{ w.loanType | loanTypeTitle}}</td>
						<td data-title="'詳細'"
							filter="{ '': 'accountLoanListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="accountLoanListCtrl.table.openNa('na', w)">
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
	id="accountLoanListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="accountLoanListCtrl.table.sortAndFilter.filter().receiptDate" type="text" id='receiptDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="accountLoanListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="accountLoanListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="accountLoanListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-5">
            <input type="checkbox" value="" ng-click="accountLoanListCtrl.table.checkItemAll()" ng-model="accountLoanListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="accountLoanListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="lsOutput" button-disabled="lsOutput" class="btn bgm-blue m-l-5" customize-button-click="accountLoanListCtrl.table.lsOutput('size')">帳票出力</br>本人確認資料印刷</button>
        <button ng-disabled="completeBtn" button-disabled="completeBtn" class="btn bgm-red m-l-5 btn-height" customize-button-click="accountLoanListCtrl.table.completeBtn()">完了消込</button>
</div>       
</script>
<script type="text/ng-template"
	id="accountLoanListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn bgm-blue m-l-5" customize-button-click="accountLoanListCtrl.table.lsOutput('size')">帳票出力</br>本人確認資料印刷</button>
        <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="accountLoanListCtrl.table.completeBtn()">完了消込</button>
</div>       
</script>
<script type="text/ng-template" id="pdfOpen.html">
<div class="popup-title" style="width:1080px">
        <i ng-click="modalInstanceController001.close()" class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
      <embed align="center" width="1080px" height="650px" src="{{openPDF}}" type="application/pdf" internalinstanceid="41">
</script>
<script type="text/ng-template" id="detailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl001.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid">
		<div class="block-header">
			<h2>ローン申込詳細</h2>
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
							<span>{{modalInstanceCtrl001.name}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">氏名(カタカナ)</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.kanaName}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">生年月日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.birthday}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">性別</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.sexKbn}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">和暦年号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.ordinaryDepositEraKbn | ordinaryDepositEraKbnTitle}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">和暦生年月日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.eraBirthday}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">年齢</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.age}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">資本金</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.money}}</span>
						</div>
					</div>
				</div>
				
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl001.loanType0">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">国籍</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.country}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">運転免許番号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.driverLicenseNo}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item" ng-hide="modalInstanceCtrl001.loanType0">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">居住形態</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.liveType}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">扶養家族（人）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.familyNumber}}</span>
						</div>
					</div>
				</div>	

				<div class="row detail-c-item">
					<div class="col-sm-2  col-sm-offset-1">
						<div class="text-item">住所</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.postCode}}</span><br /> 
                            <span>{{modalInstanceCtrl001.prefecturesCode | prefecturesCodeTitle}}</span><br />
                            <span>{{modalInstanceCtrl001.address}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">電話番号</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>自宅電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrl001.teleNumber}}</span><br />
							<span>携帯電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrl001.phoneNumber}}</span><br />
                           <span>勤務先電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrl001.workTeleNumber}}</span><br />
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">勤務先名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.companyName}}</span>
						</div>
					</div>
				</div>
                <!--<div class="row detail-c-item">
					<div class="col-sm-2  col-sm-offset-1">
						<div class="text-item">勤務先住所</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.workPostCode}}</span><br /> 
                            <span>{{modalInstanceCtrl001.workPrefecturesCode | prefecturesCodeTitle}}</span><br />
                            <span>{{modalInstanceCtrl001.workAddress}}</span>
						</div>
					</div>
				</div>--!>

				<div class="row detail-c-item">
					<div class="col-sm-12">
						<title>申込状況</title>
					</div>
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">ローン種類</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.loanType}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">受付番号</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.accountAppSeq}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">本人確認書類</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.identificationType |
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
							ng-hide="modalInstanceCtrl001.imageLoadHide">
							<svg class="pl-circular" viewBox="25 25 50 50"> <circle
								class="plc-path" cx="50" cy="50" r="20"></circle> </svg>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1">
						<img
							src="data:image/png;base64,{{modalInstanceCtrl001.identificationImage}}"
							alt="">
					</div>
				</div>
				<div class="row detail-c-item" >
					<div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1">
						<img
							src="data:image/png;base64,{{modalInstanceCtrl001.identificationImageBack}}"
							alt="">
					</div>
				</div>


				<div class="row detail-c-item" ng-hide="modalInstanceCtrl001.imageHide">
					<div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-10 col-sm-offset-1">&nbsp;</div>
				</div>
				<div class="row detail-c-item" >
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">申込受付日付</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.loanAppTime}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrl001.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">お借入希望日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.getHopeDate}}</span>
						</div>
					</div>
					<div class="col-sm-2" >
						<div class="text-item">毎月返済希望額</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.returnHopeMonth}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item" >
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">ご利用目的</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.otherPurpose}}</span>
						</div>
					</div>
					<div class="col-sm-2" ng-hide="modalInstanceCtrl001.loanType1">
						<div class="text-item">所要資金総額（万円）</div>
					</div>
					<div class="col-sm-3" ng-hide="modalInstanceCtrl001.loanType1">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.moneyTotal}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item"  ng-hide="modalInstanceCtrl001.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">お支払先①</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.payMoney1}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">金額①（万円）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.money1}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item"  ng-hide="modalInstanceCtrl001.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">お支払先②</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.payMoney2}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">金額②（万円）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.money2}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item"  ng-hide="modalInstanceCtrl001.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">返済口座</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.ownAccountKbn}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">返済内訳</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.increaseFlg}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"  ng-hide="modalInstanceCtrl001.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">返済日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.returnDay}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">返済開始日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.returnStartDay}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"  ng-hide="modalInstanceCtrl001.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">返済元金（万円）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.returnMoney}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">返済希望回数</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.returnHopeCount}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"  ng-hide="modalInstanceCtrl001.loanType0">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">支店名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.hopeStoreNmae}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">当行とのお取引</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.bankAccount}}</span>
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
							<tr ng-repeat="push in modalInstanceCtrl001.pushData">
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
								ng-model="modalInstanceCtrl001.selectStatus">
							<option value="1">受付</option>
							<option value="2">処理中</option>
							<option value="3">完了</option>
							<option value="4">却下（総合的判断）</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="btn-colors btn-demo col-sm-3 col-sm-offset-2">
				<button class="btn bgm-blue waves-effect"
					ng-click="modalInstanceCtrl001.statusUpd()"
					ng-disabled="modalInstanceCtrl001.UpdbtnDisabled">ステータス更新</button>
			</div>
		</div>
			</div>
		</div>
	</div>
</script>

<script type="text/ng-template" id="detailPopup5.html">
<div class="popup-title">
    <i ng-click="modalHistoryPushCtrl1.ok()"
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
                            </tr>
                            <tr ng-repeat="push in modalHistoryPushCtrl1.pushData">
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
        </div>
</div>
 </script>