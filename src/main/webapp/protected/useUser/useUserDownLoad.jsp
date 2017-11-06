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
	padding-left: 1px;
	padding-right: 1px;
	padding-bottom: 10px;
	padding-top: 10px;
}
</style>

<div class="container">
	<div class="block-header">
		<h2>マイテーマ利用データダウンロード</h2>
		<input type="hidden" id="useUserDownLoad" name="useUserDownLoad"
			ng-model="useUserDownLoadCtrl.useUserDownLoad"
			value="${sessionScope.UseUserDownLoad}" />
	</div>

	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="useUserDownLoadCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="2%" />
						<col width="16%" />
						<col width="24%" />
						<col width="38%" />
						<col width="16%" />
						<col width="3%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'useUserDownLoadCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td data-title="'ログインタイプ'" filter="{ 'userKey': 'ng-table/filters/select-clear.html'}"
							sortable="'userKey'"
							filter-data="useUserDownLoadCtrl.table.filterList2">
							{{w.userKey | userKeyTitle}}</a></td>
						<!-- <td data-title="'ログインタイプ'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'userKey': 'ng-table/filters/text-claer-2.html'}"
							sortable="'userKey'">{{ w.userKey }}</td> -->
						<td data-title="'メールアドレス'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'email': 'ng-table/filters/text-claer-2.html'}"
							sortable="'email'">{{ w.email }}</td>
						<!-- <td data-title="'ユーザー名'" style="word-wrap: break-word;"
							filter="{ 'name': 'ng-table/filters/text-claer-2.html'}"
							sortable="'name'">{{ w.name }}</td>
						<td data-title="'年齢'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'age': 'ng-table/filters/text-claer-2.html'}"
							sortable="'age'">{{ w.age }}</td>
						<td data-title="'性別'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'sex': 'ng-table/filters/text-claer-2.html'}"
							sortable="'sex'">{{ w.sex | sexTitle}}</td>
						<td data-title="'職業'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'otherOccupations': 'ng-table/filters/text-claer-2.html'}"
							sortable="'otherOccupations'"><pre
								style='border: dashed 0px;'>{{ w.otherOccupations}}</pre></td> -->
						<td data-title="'店舗/科目/口座番号/口座名'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'accountNumber': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountNumber'"><pre style='border: dashed 0px;'>{{ w.accountNumber}}</pre></td>
						<!-- <td data-title="'登録店番'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'storeName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'storeName'"><pre style='border: dashed 0px;'>{{ w.storeName}}</pre></td>
						<td data-title="'科目'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'kamokuName': 'ng-table/filters/text-claer-2.html'}"
							sortable="'kamokuName'"><pre style='border: dashed 0px;'>{{ w.kamokuName}}</pre></td>
						<td data-title="'口座番号'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'accountNumber': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountNumber'"><pre style='border: dashed 0px;'>{{ w.accountNumber}}</pre></td> -->
						<td data-title="'最終利用日時'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'lastReqTime': 'useUserDownLoadCtrl/table/sortAndFilter/datepicker.html'}"
							sortable="'lastReqTime'">{{ w.lastReqTime }}</td>
						<!-- <td data-title="'カメリオ'"
							filter="{'kamerio': 'ng-table/filters/text-claer-2.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="useUserDownLoadCtrl.table.openNa2('na', w)">
								<i class="zmdi zmdi-menu"></i>
							</button>
						</td> -->
						<td data-title="'詳細'"
							filter="{ '': 'useUserDownLoadCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="useUserDownLoadCtrl.table.openNa('na', w)">
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
	id="useUserDownLoadCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="useUserDownLoadCtrl.table.sortAndFilter.filter().lastReqTime" type="text" id='lastReqTime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="useUserDownLoadCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="useUserDownLoadCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="useUserDownLoadCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="useUserDownLoadCtrl.table.checkItemAll()" ng-model="useUserDownLoadCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="useUserDownLoadCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right" class="ng-scope" >
        <button ng-disabled="csvOutput" button-disabled="csvOutput" class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserDownLoadCtrl.table.timeOutput()">テーマダウンロード</button>
        <button ng-disabled="csvOutput" button-disabled="csvOutput" class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserDownLoadCtrl.table.clipOutput()">クリップダウンロード</button>
</div>    
</script>
<script type="text/ng-template"
	id="useUserDownLoadCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right" class="ng-scope" >
       <button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserDownLoadCtrl.table.timeOutput()">テーマダウンロード</button>
       <button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserDownLoadCtrl.table.clipOutput()">クリップダウンロード</button>
</div> 
</script>
<script type="text/ng-template" id="useUSerPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl14.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid">
		<div class="block-header">
			<h2>利用ユーザー詳細</h2>
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
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.userId}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">氏名(漢字)</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.name}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">氏名(カタカナ)</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.kanaName}}</span>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">生年月日</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.birthday}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">性別</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.sex | sexTitle}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">年齢</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.age}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">ログインタイプ</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.userType | userTypeTitle}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">E-mail</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.email}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">郵便番号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.postCode}}</span>
						</div>
					</div>
				</div>


<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">住所</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.address1}}{{modalInstanceCtrl14.address2}}</span>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="text-item">住所（カナ）</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.kanaAddress}}</span>
						</div>
					</div>
				</div>

<div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">自宅電話番号</div>
					</div>
                    <div class="col-sm-3">
						<div class="text-item">
							{{modalInstanceCtrl14.teleNumber}}</span>
						</div>
					</div>
                </div>

<div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">携帯電話番号</div>
					</div>
                    <div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.teleNumber}}</span>
						</div>
					</div>
                </div>

<div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">勤務先電話番号</div>
					</div>
                    <div class="col-sm-3">
						<div class="text-item">
							{{modalInstanceCtrl14.teleNumber}}</span>
						</div>
					</div>
                </div>
                <div class="row detail-c-item">
                    <div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">職業</div>
					</div>
                    <div class="col-sm-3">
						<div class="text-item">
							<span><option
									ng-repeat="occupation in modalInstanceCtrl14.occupation">{{occupation
									| occupationTitle: modalInstanceCtrl14.otherOccupations}}<br />
								</option></span>
						</div>
					</div>
                </div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">勤務先名</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.workName}}</span>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">店番</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                            <pre style='border: dashed 0px;'>{{ modalInstanceCtrl14.storeName}}</pre>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">科目</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                            <pre style='border: dashed 0px;'>{{ modalInstanceCtrl14.kamokuName}}</pre>
						</div>
					</div>
				</div>
                <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">口座番号</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                            <pre style='border: dashed 0px;'>{{ modalInstanceCtrl14.accountNumber}}</pre>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">口座名称</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                            <pre style='border: dashed 0px;'>{{ modalInstanceCtrl14.accountName}}</pre>
						</div>
					</div>
				</div>
				<div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">利用規約同意日時</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.agreeDate}}</span>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">最終リクエスト日時</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
							<span>{{modalInstanceCtrl14.lastReqTime}}</span>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
</script>