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
		<h2>各種書類申込承認</h2>
								<input type="hidden" id="pushRecordAppList" name="pushRecordAppList"
							ng-model="documentRecordListCtrl.user" value="${sessionScope.DocumentPush}" />
							<input type="hidden" id="pushRecordBankCodeAppList" name="pushRecordAppList"
							ng-model="documentRecordListCtrl.user" value="${sessionScope.bank_cd}" />
	</div>
	<div class="card">
		<div class="card-header">
		</div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="documentRecordListCtrl.table.sortAndFilter"
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
							filter="{ 'select': 'documentRecordListCtrl/table/sortAndFilter/checkItemAll.html' }" style="padding-bottom:19px"><label
							class="checkbox checkbox-inline m-r-5"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable"> <i
								class="input-helper"></i>
						</label></td>
						<td>
						<a ng-show="w.visible" style="cursor: pointer;"
							ng-click="documentRecordListCtrl.table.pushErr(w)"> <img
								src="../img/error.png"></a>

						</td>
						<td data-title="'受付番号'" filter="{ 'accountAppSeq': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountAppSeq'">{{ w.accountAppSeq }}<span><div ng-show="show">{{ w.pushErr }}</div></span></td>
						<td data-title="'受付日時'"
							filter="{ 'receiptDate': 'documentRecordListCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'receiptDate'">{{ w.receiptDate }}</td>
						<td data-title="'氏名'" filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						<td data-title="'ステータス'" filter="{ 'status': 'ng-table/filters/select-clear.html'}"
							sortable="'status'"
							filter-data="documentRecordListCtrl.table.filterList">{{w.status
							| documentStatusTitle}}</td>
						
						<td data-title="'詳細'"
                            filter="{ '': 'documentRecordListCtrl/table/sortAndFilter/clearFilter.html' }">
                            <button class="btn bgm-lightgreen" cc="{{$index}}"
                                ng-click="documentRecordListCtrl.table.openNa('na', w)">
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
	id="documentRecordListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="documentRecordListCtrl.table.sortAndFilter.filter().receiptDate" type="text" id='receiptDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
  
  <script type="text/ng-template"
	id="documentRecordListCtrl/table/sortAndFilter/datepicker-1.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="documentRecordListCtrl.table.sortAndFilter.filter().pushDate" type="text" id='pushDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="documentRecordListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="documentRecordListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="documentRecordListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-5">
            <input type="checkbox" value="" ng-click="documentRecordListCtrl.table.checkItemAll()" ng-model="documentRecordListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="documentRecordListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="completeBtnDisabled" button-disabled="completeBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="documentRecordListCtrl.table.selectAdmitBtn()">一括承認</button>
	<button ng-disabled="completeBtnDisabled" button-disabled="completeBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="documentRecordListCtrl.table.deleteBtn()">承認取り下げ</button>
</div>       
</script>
<script type="text/ng-template"
	id="documentRecordListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="documentRecordListCtrl.table.selectAdmitBtn()">一括承認</button>
  		<button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="documentRecordListCtrl.table.deleteBtn()">承認取り下げ</button>
</div>       
</script>
<script type="text/ng-template" id="detailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl25.ok()"
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
							<span>{{modalInstanceCtrl25.name}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">氏名(カタカナ)</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.kanaName}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">ユーザーID</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.userId}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">ユーザータイプ</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.userType}}</span>
						</div>
					</div>
				</div>

				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">同意日時</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.agreeTime}}</span>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">同意チェックボックス</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.agreeCheck}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">本人確認書フラグ</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.selfConfirmFlg}}</span>
						</div>
					</div>

					<div class="col-sm-3">
						<div class="text-item">届出いただく目的</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.purpose}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">届出いただく書類</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.type}}</span>
						</div>
					</div>
				</div>
                
				<div class="row detail-c-item">
					<div class="col-sm-3  col-sm-offset-1">
						<div class="text-item">画像</div>
					</div>
					<div class="col-sm-10 col-sm-offset-1">
							<img src="data:image/png;base64,{{modalInstanceCtrl25.card1Seq}}"
							alt=""><br />
					</div>
					<div class="col-sm-10 col-sm-offset-1">
							<img src="data:image/png;base64,{{modalInstanceCtrl25.card6Seq}}"
							alt=""><br />
					</div>
					<div class="col-sm-10 col-sm-offset-1">
							<img src="data:image/png;base64,{{modalInstanceCtrl25.card2Seq}}"
							alt=""><br />
					</div>
					<div class="col-sm-10 col-sm-offset-1">
							<img src="data:image/png;base64,{{modalInstanceCtrl25.card3Seq}}"
							alt=""><br />
					</div>

					<div class="col-sm-10 col-sm-offset-1">
							<img src="data:image/png;base64,{{modalInstanceCtrl25.card4Seq}}"
							alt=""><br />
					</div>
					<div class="col-sm-10 col-sm-offset-1">
							<img src="data:image/png;base64,{{modalInstanceCtrl25.card5Seq}}"
							alt=""><br />
					</div>
					
				</div>

				<div class="row detail-c-item">
					<div class="col-sm-3 col-sm-offset-1">
						<div class="text-item">届出いただく書類フラグ</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.typeFlg}}</span>
						</div>
					</div>

				</div>
				
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">勤務先名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.workName}}</span>
						</div>
					</div>
				</div>


				<div class="row detail-c-item">
					<div class="col-sm-12">
						<title>申込状況</title>
					</div>
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">お電話番号(日中のご連絡先)</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.telephoneNo}}</span>
						</div>
					</div>

					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">キャッシュカード記載番号_銀行番号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.bankNo}}</span>
						</div>
					</div>

					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">キャッシュカード記載番号_口座店番</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.storeNo}}</span>
						</div>
					</div>

					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">キャッシュカード記載番号_口座店名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.storeName}}</span>
						</div>
					</div>
					
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-4 col-sm-offset-1">
						<div class="text-item">その他</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl25.other}}</span>
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
							<tr ng-repeat="push in modalInstanceCtrl25.pushData">
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
               <span>{{modalInstanceCtrl25.status | documentStatusTitle}}</span>
            </div>
        </div>
     </div>
   </div>
</div>
</script>


