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
		<h2>ローンPush通知承認</h2>
		<input type="hidden" id="pushRecordAppList" name="pushRecordAppList"
			ng-model="pushRecordLoanListCtrl.user"
			value="${sessionScope.PushLoan}" /> <input type="hidden"
			id="pushRecordBankCodeAppList" name="pushRecordAppList"
			ng-model="pushRecordLoanListCtrl.user"
			value="${sessionScope.bank_cd}" />
	</div>
	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="pushRecordLoanListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="3%" />
						<col width="3%" />
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
							filter="{ 'select': 'pushRecordLoanListCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-5"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td><a ng-show="w.visible" style="cursor: pointer;"
							ng-click="pushRecordLoanListCtrl.table.pushErr(w)"> <img
								src="../img/error.png"></a> <!-- 							
							<span a ng-show="!visible"  ng-mouseenter="show = true" ng-mouseleave="show = false"><img
								src="../img/error.png"></a></span> --></td>
						<td data-title="'受付番号'"
							filter="{ 'accountAppSeq': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountAppSeq'">{{ w.accountAppSeq }}<span><div
									ng-show="show">{{ w.pushErr }}</div></span></td>
						<td data-title="'受付日時'"
							filter="{ 'receiptDate': 'pushRecordLoanListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'receiptDate'">{{ w.receiptDate }}</td>
						<td data-title="'氏名'"
							filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						<td data-title="'ステータス'"
							filter="{ 'status': 'ng-table/filters/select-clear.html'}"
							sortable="'status'"
							filter-data="pushRecordLoanListCtrl.table.filterList">{{w.status
							| statusTitle}}</td>
						<td data-title="'詳細'"
							filter="{ '': 'pushRecordLoanListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="pushRecordLoanListCtrl.table.openNa('na', w)">
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
	id="pushRecordLoanListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="pushRecordLoanListCtrl.table.sortAndFilter.filter().receiptDate" type="text" id='receiptDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>

<script type="text/ng-template"
	id="pushRecordLoanListCtrl/table/sortAndFilter/datepicker-1.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="pushRecordLoanListCtrl.table.sortAndFilter.filter().pushDate" type="text" id='pushDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="pushRecordLoanListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="pushRecordLoanListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="pushRecordLoanListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-5">
            <input type="checkbox" value="" ng-click="pushRecordLoanListCtrl.table.checkItemAll()" ng-model="pushRecordLoanListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="pushRecordLoanListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="completeBtnDisabled" button-disabled="completeBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordLoanListCtrl.table.selectAdmitBtn()">一括承認</button>
	<button ng-disabled="completeBtnDisabled" button-disabled="completeBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordLoanListCtrl.table.deleteBtn()">承認取り下げ</button>
</div>       
</script>
<script type="text/ng-template"
	id="pushRecordLoanListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordLoanListCtrl.table.selectAdmitBtn()">一括承認</button>
  		<button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="pushRecordLoanListCtrl.table.deleteBtn()">承認取り下げ</button>
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
							<span>{{modalInstanceCtrls.sexKbn}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">和暦年号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.ordinaryDepositEraKbn | ordinaryDepositEraKbnTitle}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">和暦生年月日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.eraBirthday}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">年齢</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.age}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">資本金</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.money}}</span>
						</div>
					</div>
				</div>
				
				<div class="row detail-c-item" ng-hide="modalInstanceCtrls.loanType0">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">国籍</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.country}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">運転免許番号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.driverLicenseNo}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item" ng-hide="modalInstanceCtrls.loanType0">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">居住形態</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.liveType}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">扶養家族（人）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.familyNumber}}</span>
						</div>
					</div>
				</div>	

				<div class="row detail-c-item">
					<div class="col-sm-2  col-sm-offset-1">
						<div class="text-item">住所</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrls.postCode}}</span><br /> 
                            <span>{{modalInstanceCtrls.prefecturesCode | prefecturesCodeTitle}}</span><br />
                            <span>{{modalInstanceCtrls.address}}</span>
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
                           <span>勤務先電話番号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>{{modalInstanceCtrls.workTeleNumber}}</span><br />
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">勤務先名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.companyName}}</span>
						</div>
					</div>
				</div>
                <!--<div class="row detail-c-item">
					<div class="col-sm-2  col-sm-offset-1">
						<div class="text-item">勤務先住所</div>
					</div>
					<div class="col-sm-8">
						<div class="text-item">
							<span>{{modalInstanceCtrls.workPostCode}}</span><br /> 
                            <span>{{modalInstanceCtrls.workPrefecturesCode | prefecturesCodeTitle}}</span><br />
                            <span>{{modalInstanceCtrls.workAddress}}</span>
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
							<span>{{modalInstanceCtrls.loanType}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">受付番号</div>
					</div>
					<div class="col-sm-6">
						<div class="text-item">
							<span>{{modalInstanceCtrls.accountAppSeq}}</span>
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
				<div class="row detail-c-item" ng-hide="!modalInstanceCtrls.imageHide">
					<div class="col-sm-10 col-sm-offset-1"
						ng-hide="!modalInstanceCtrls.imageLoadHide">
						<img
							src="data:image/png;base64,{{modalInstanceCtrls.identificationImageBack}}"
							alt="">
					</div>
				</div>


				<div class="row detail-c-item" ng-hide="modalInstanceCtrls.imageHide">
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
							<span>{{modalInstanceCtrls.loanAppTime}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" ng-hide="modalInstanceCtrls.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">お借入希望日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.getHopeDate}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">毎月返済希望額</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.returnHopeMonth}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item" >
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">ご利用目的</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.otherPurpose}}</span>
						</div>
					</div>
					<div class="col-sm-2" ng-hide="modalInstanceCtrls.loanType1">
						<div class="text-item">所要資金総額（万円）</div>
					</div>
					<div class="col-sm-3" ng-hide="modalInstanceCtrls.loanType1">
						<div class="text-item">
							<span>{{modalInstanceCtrls.moneyTotal}}</span>
						</div>
					</div>
				</div>
				

				<div class="row detail-c-item"  ng-hide="modalInstanceCtrls.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">お支払先①</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.payMoney1}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">金額①（万円）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.money1}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item"  ng-hide="modalInstanceCtrls.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">お支払先②</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.payMoney2}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">金額②（万円）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.money2}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item"  ng-hide="modalInstanceCtrls.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">返済口座</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.ownAccountKbn}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">返済内訳</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.increaseFlg}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"  ng-hide="modalInstanceCtrls.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">返済日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.returnDay}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">返済開始日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.returnStartDay}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"  ng-hide="modalInstanceCtrls.loanType1">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">返済元金（万円）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.returnMoney}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">返済希望回数</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.returnHopeCount}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item"  ng-hide="modalInstanceCtrls.loanType0">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">支店名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.hopeStoreNmae}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">当行とのお取引</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrls.bankAccount}}</span>
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
								<td>{{ push.statusModifyDate }}</td>
                                <td>{{ push.sendStatus | yamagataSendStatusTitle }}</td>
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


