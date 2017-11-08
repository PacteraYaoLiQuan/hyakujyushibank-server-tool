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
						<div class="text-item">ドキュメントタイプ</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.docType}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">ユーザーID</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.userId}}</span>
						</div>
					</div>
				</div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ユーザータイプ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.userType}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">受付番号</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.loanAppSeq}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">受付日時</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.loanAppTime}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ステイタス</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.status}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ローン種類</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.loanType}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">カードローン申し込み済フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.reLoadInfoFlg}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">同意日時</div>
              <span>{{modalInstanceCtrl001.agreeTime}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">同意チェックボックス</div>
              <span>{{modalInstanceCtrl001.agreeCheck}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お借入限度額</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.limitMoney}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ダイレクトメール</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.dairekutoMail}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">運転免許証画像の受付番号</div>
              <span>{{modalInstanceCtrl001.driverLicenseSeq}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お名前_漢字の名</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.firstName}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お名前_漢字の姓</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.lastName}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お名前_フリガナの名</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.firstKanaName}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お名前_フリガナの姓</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.lastKanaName}}</span>
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
            <div class="text-item">生年月日_和暦</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.yearType}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">和暦生年月日</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.eraBirthday}}</span>
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
						<div class="text-item">満年齢</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.age}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">国籍</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl001.country}}</span>
						</div>
					</div>
				</div>

        <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">運転免許番号フラグ</div>
              <span>{{modalInstanceCtrl001.driverLicenseFlg}}</span>
					</div>
					<div class="col-sm-3">
						<div class="text-item">

						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">運転免許番号</div>
              <span>{{modalInstanceCtrl001.driverLicenseNo}}</span>
					</div>
					<div class="col-sm-3">
						<div class="text-item">

						</div>
					</div>
				</div>

				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">郵便番号</div>
              <span>{{modalInstanceCtrl001.postCode}}</span>
					</div>
					<div class="col-sm-3">
						<div class="text-item">

						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">ご自宅住所_都道府県</div>
              <span>{{modalInstanceCtrl001.prefecturesCode}}</span>
					</div>
					<div class="col-sm-3">
						<div class="text-item">

						</div>
					</div>
				</div>	

				<div class="row detail-c-item">
					<div class="col-sm-2  col-sm-offset-1">
						<div class="text-item">ご自宅住所_住所</div>
               <span>{{modalInstanceCtrl001.address}}</span>
					</div>
					<div class="col-sm-8">
						<div class="text-item">

						</div>
					</div>
				</div>

        <div class="row detail-c-item">
          <div class="col-sm-2  col-sm-offset-1">
            <div class="text-item">ご自宅住所_以降ご住所</div>
               <span>{{modalInstanceCtrl001.otherAddress}}</span>
          </div>
          <div class="col-sm-8">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お電話番号_自宅</div>
              <span>{{modalInstanceCtrl001.teleNumber}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お電話番号_携帯・PHS</div>
              <span>{{modalInstanceCtrl001.phoneNumber}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お住まい</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.enAddress}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">居住年数_年</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.liveYear}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">居住年数_ヶ月</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.liveMonth}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">家賃（住宅ローン）_毎月（円）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.rentLoan}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ご家族_配偶者</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.spouse}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ご家族_居住形態</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.liveType}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ご家族_扶養家族（人）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.familyNumber}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">承知フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.masterKnowFlg}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2  col-sm-offset-1">
            <div class="text-item">有無フラグ</div>
          </div>
          <div class="col-sm-8">
            <div class="text-item">
               <span>{{modalInstanceCtrl001.sendFlg}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お勤め先名_漢字</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.companyName}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お勤め先名_フリガナ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.companyKanaName}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2  col-sm-offset-1">
            <div class="text-item">業種</div>
          </div>
          <div class="col-sm-8">
            <div class="text-item">
               <span>{{modalInstanceCtrl001.jobType}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お勤め先郵便番号</div>
              <span>{{modalInstanceCtrl001.workPostCode}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お勤め先所在地_都道府県</div>
              <span>{{modalInstanceCtrl001.workPrefecturesCode}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お勤め先所在地_住所</div>
              <span>{{modalInstanceCtrl001.workAddress}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お勤め先所在地_以降住所</div>
              <span>{{modalInstanceCtrl001.workOtherAddress}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お勤め先電話番号_代表電話</div>
              <span>{{modalInstanceCtrl001.workTeleNumber}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お勤め先電話番号_所属部直通</div>
              <span>{{modalInstanceCtrl001.workPhoneNumber}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">所得区分フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getKbnFlg}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">所得区分</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getKbn}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">役職</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.position}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">従業員数</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.jobNumber}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">勤続（営）年数_年</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workYear}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">勤続（営）年数_ヶ月</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workMonth}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">所属部課名</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.department}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">入社年月_和暦</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workStartYearType}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">入社年月</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workYearMonth}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">給料日_毎月（日）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.payment}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">最終学歴卒業年月</div>
              <span>{{modalInstanceCtrl001.graduateYearMonth}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">最終学歴卒業年月_和暦</div>
              <span>{{modalInstanceCtrl001.graduateYearType}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">出向先会社名_漢字</div>
              <span>{{modalInstanceCtrl001.visitingName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">出向先会社名_フリガナ</div>
              <span>{{modalInstanceCtrl001.visitingKanaName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">出向先所属部課名</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.visitingDepartment}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">役職</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.visitingPosition}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">出向先郵便番号</div>
              <span>{{modalInstanceCtrl001.visitingPostCode}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">出向先所在地_都道府県</div>
              <span>{{modalInstanceCtrl001.visitPreCode}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">出向先所在地_住所</div>
              <span>{{modalInstanceCtrl001.visitAddress}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">出向先所在地_以降住所</div>
              <span>{{modalInstanceCtrl001.visitOtherAddress}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">出向先電話番号</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.visitTelNumber}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">年収/税込年収フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.incomeFlg}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">年収/税込年収</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.incomeYear}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お仕事の内容</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workContent}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">社員数</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workNumber}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">就業形態</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workType}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お勤め先の種類</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.workTypeCode}}</span>
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

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">住宅ローン契約（当行）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.rentLoanContract}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">本社所在地</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.comPreCode}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">健康保険証の種類</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.healthType}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お申込金額_万円</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.applicationMoney}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お借入希望日</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getHopeDate}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">毎月返済希望額_円</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnHopeMonth}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">返済希望回数_回</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnHopeCount}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">内据置回数_回</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.inCount}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ご利用目的フラグ</div>
              <span>{{modalInstanceCtrl001.purposeFlg}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ご利用目的_「10.その他」選択時</div>
              <span>{{modalInstanceCtrl001.otherPurpose}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ご利用目的_目的</div>
              <span>{{modalInstanceCtrl001.purpose}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ご利用目的_所要資金総額（万円）</div>
              <span>{{modalInstanceCtrl001.moneyTotal}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ご利用目的_お支払先（ご購入先）①</div>
              <span>{{modalInstanceCtrl001.payMoney1}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ご利用目的_金額①（万円）</div>
              <span>{{modalInstanceCtrl001.money1}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">ご利用目的_お支払先（ご購入先）②</div>
              <span>{{modalInstanceCtrl001.payMoney2}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ご利用目的_金額②（万円）</div>
              <span>{{modalInstanceCtrl001.money2}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">口座持ちフラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.ownAccountKbn}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">支店</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.storeName}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">店舗コード</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.storeNameFlg}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">口座番号（普通）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.accountNumber}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">返済併用フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.increaseFlg}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">返済日</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnDay}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">返済開始日</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnStartDay}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">返済元金（万円）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnMoney}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">返済月1</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.increaseReturn1}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">返済月2</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.increaseReturn2}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">返済開始日</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnStartDay2}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">返済元金（万円）</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.returnMoney2}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">借入フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getFlg}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">件</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getCount}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">万円</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getMoney}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">借入フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.getFromOtherFlg}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">[無担保ローン] 残高（万円）</div>
              <span>{{modalInstanceCtrl001.noLoanRest}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">[無担保ローン] 年間返済額（万円）</div>
              <span>{{modalInstanceCtrl001.noLoanReturnMoney}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">[住宅ローン] 残高（万円）</div>
              <span>{{modalInstanceCtrl001.loanRest}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">[住宅ローン] 年間返済額（万円）</div>
              <span>{{modalInstanceCtrl001.loanReturnMoney}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">[カードローン] 残高（万円）</div>
              <span>{{modalInstanceCtrl001.cardLoanRest}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">[カードローン] 年間返済額（万円）</div>
              <span>{{modalInstanceCtrl001.cardLoanRestRM}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">※「はい」選択時[信販会社等その他] 残高（万円）１</div>
              <span>{{modalInstanceCtrl001.otherComRest}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">※「はい」選択時[信販会社等その他] 年間返済額（万円）１</div>
              <span>{{modalInstanceCtrl001.otherComReturnMoney}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">※「はい」選択時[信販会社等その他] 残高（万円）２</div>
              <span>{{modalInstanceCtrl001.otherComRest2}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">※「はい」選択時[信販会社等その他] 年間返済額（万円）２</div>
              <span>{{modalInstanceCtrl001.otherComReturnMoney2}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">※「はい」選択時[信販会社等その他] 残高（万円）３</div>
              <span>{{modalInstanceCtrl001.otherComRest3}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">※「はい」選択時[信販会社等その他] 年間返済額（万円）３</div>
              <span>{{modalInstanceCtrl001.otherComReturnMoney3}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">※「はい」選択時[信販会社等その他] 残高（万円）４</div>
              <span>{{modalInstanceCtrl001.otherComRest4}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">※「はい」選択時[信販会社等その他] 年間返済額（万円）４</div>
              <span>{{modalInstanceCtrl001.otherComReturnMoney4}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">※「はい」選択時[信販会社等その他] 残高（万円）５</div>
              <span>{{modalInstanceCtrl001.otherComRest5}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">※「はい」選択時[信販会社等その他] 年間返済額（万円）5</div>
              <span>{{modalInstanceCtrl001.otherComReturnMoney5}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">※「はい」選択時[信販会社等その他] 残高（万円）6</div>
              <span>{{modalInstanceCtrl001.otherComRest6}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">※「はい」選択時[信販会社等その他] 年間返済額（万円）6</div>
              <span>{{modalInstanceCtrl001.otherComReturnMoney6}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お取引希望店</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.hopeStoreNmae}}</span>
            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お取引希望店フラグ</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.hopeStoreFlg}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">当行とのお取引</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl001.bankAccount}}</span>
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
				
</script>

