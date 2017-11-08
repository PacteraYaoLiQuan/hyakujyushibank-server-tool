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
		<h2>各種書類申込一覧</h2>
		<input type="hidden" id="accountAppList" name="accountAppList"
			ng-model="accountDocumentListCtrl.user"
			value="${sessionScope.Document}" /> <input type="hidden"
			id="accountBankCodeAppList" name="accountAppList"
			ng-model="accountDocumentListCtrl.user"
			value="${sessionScope.bank_cd}" />
	</div>

	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="accountDocumentListCtrl.table.sortAndFilter"
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
							filter="{ 'select': 'accountDocumentListCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-5"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'受付番号'"
							filter="{ 'documentAppSeq': 'ng-table/filters/text-claer-2.html'}"
							sortable="'documentAppSeq'">{{ w.documentAppSeq }}</td>
						<td data-title="'受付日時'"
							filter="{ 'receiptDate': 'accountDocumentListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'receiptDate'">{{ w.documentAppTime }}</td>
						<td data-title="'氏名'"
							filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						
						<td data-title="'申込み商品'"
							filter="{ 'purpose': 'ng-table/filters/select-clear.html'}"
							sortable="'purpose'"
							filter-data="accountDocumentListCtrl.table.purposeList">{{
							w.purpose |documentPurposeTitle}}</td>
						<td data-title="'詳細'"
							filter="{ '': 'accountDocumentListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="accountDocumentListCtrl.table.openNa('na', w)">
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
	id="accountDocumentListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="accountDocumentListCtrl.table.sortAndFilter.filter().receiptDate" type="text" id='receiptDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="accountDocumentListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="accountDocumentListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="accountDocumentListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-5">
            <input type="checkbox" value="" ng-click="accountDocumentListCtrl.table.checkItemAll()" ng-model="accountDocumentListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>



<script type="text/ng-template"
	id="accountDocumentListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
		<button ng-disabled="csvOutput" button-disabled="csvOutput" class="btn bgm-blue m-l-5 btn-height" customize-button-click="accountDocumentListCtrl.table.csvOutput()">申込データCSV出力</button>
        <button ng-disabled="completeBtn" button-disabled="completeBtn" class="btn bgm-red m-l-5 btn-height" customize-button-click="accountDocumentListCtrl.table.completeBtn()">完了消込</button>
</div>       
</script>
<script type="text/ng-template"
	id="accountDocumentListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
		<button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="accountDocumentListCtrl.table.csvOutput()">申込データCSV出力</button>
        <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="accountDocumentListCtrl.table.completeBtn()">完了消込</button>
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

      <!-- <embed align="center" width="1080px" height="650px"  src="{{openPDF2}}" type="application/pdf" internalinstanceid="41"> -->


</script>

<script type="text/ng-template" id="detailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl23.ok()"
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
            <div class="text-item">ユーザーID</div>
              <span>{{modalInstanceCtrl23.userId}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">ユーザータイプ</div>
              <span>{{modalInstanceCtrl23.userType}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

         <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">受付番号</div>
              <span>{{modalInstanceCtrl23.documentAppSeq}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">受付日時</div>
              <span>{{modalInstanceCtrl23.documentAppTime}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>
        
        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">同意日時</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl23.agreeTime}}</span>
            </div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">同意チェックボックス</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl23.agreeCheck}}</span>
            </div>
          </div>
        </div>




				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">ステイタス</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl23.status}}</span>
						</div>
					</div>
				</div>
				
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">本人確認書フラグ</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl23.selfConfirmFlg}}</span>
						</div>
					</div>

					<div class="col-sm-3">
						<div class="text-item">届出いただく目的</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl23.purpose}}</span>
						</div>
					</div>
				</div>
        <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">届出いただく書類</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl23.type}}</span>
						</div>
					</div>
				</div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">届出いただく書類_その他</div>
              <span>{{modalInstanceCtrl23.otherTypeContent}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>
                
        <div class="row detail-c-item">
          <div class="col-sm-3 col-sm-offset-1">
            <div class="text-item">運転免許証番号</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl23.driverLicenseNo}}</span>
            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お名前（漢字）：姓(入力エリア)</div>
              <span>{{modalInstanceCtrl23.lastName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お名前（漢字）：名(入力エリア)</div>
              <span>{{modalInstanceCtrl23.firstName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お名前（カナ）：セイ</div>
              <span>{{modalInstanceCtrl23.kanaLastName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">お名前（カナ）：メイ</div>
              <span>{{modalInstanceCtrl23.kanaFirstName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">お電話番号(日中のご連絡先)</div>
              <span>{{modalInstanceCtrl23.telephoneNo}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">キャッシュカード記載番号_銀行番号</div>
              <span>{{modalInstanceCtrl23.bankNo}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-2 col-sm-offset-1">
            <div class="text-item">キャッシュカード記載番号_口座店番</div>
              <span>{{modalInstanceCtrl23.storeNo}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
          <div class="col-sm-2">
            <div class="text-item">キャッシュカード記載番号_口座店名</div>
              <span>{{modalInstanceCtrl23.storeName}}</span>
          </div>
          <div class="col-sm-3">
            <div class="text-item">

            </div>
          </div>
        </div>

        <div class="row detail-c-item">
          <div class="col-sm-3 col-sm-offset-1">
            <div class="text-item">その他</div>
          </div>
          <div class="col-sm-3">
            <div class="text-item">
              <span>{{modalInstanceCtrl23.other}}</span>
            </div>
          </div>
        </div>
       
        <div class="row detail-c-item">
          <div class="col-sm-3  col-sm-offset-1">
            <div class="text-item">画像</div>
          </div>
          <div class="col-sm-10 col-sm-offset-1">
              <img src="data:image/png;base64,{{modalInstanceCtrl23.card1Seq}}"
              alt=""><br />
          </div>
          <div class="col-sm-10 col-sm-offset-1">
              <img src="data:image/png;base64,{{modalInstanceCtrl23.card6Seq}}"
              alt=""><br />
          </div>
          <div class="col-sm-10 col-sm-offset-1">
              <img src="data:image/png;base64,{{modalInstanceCtrl23.card2Seq}}"
              alt=""><br />
          </div>
          <div class="col-sm-10 col-sm-offset-1">
              <img src="data:image/png;base64,{{modalInstanceCtrl23.card3Seq}}"
              alt=""><br />
          </div>

          <div class="col-sm-10 col-sm-offset-1">
              <img src="data:image/png;base64,{{modalInstanceCtrl23.card4Seq}}"
              alt=""><br />
          </div>
          <div class="col-sm-10 col-sm-offset-1">
              <img src="data:image/png;base64,{{modalInstanceCtrl23.card5Seq}}"
              alt=""><br />
          </div>
          
        </div>

                
</script>

