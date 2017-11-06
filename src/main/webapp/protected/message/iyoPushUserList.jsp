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

.div1 {
	float: left;
	height: 30px;
	background: #ffffff;
	width: 24px;
	position: relative;
}

.inputstyle {
	width: 50px;
	height: 30px;
	cursor: pointer;
	font-size: 30px;
	outline: medium none;
	position: absolute;
	filter: alpha(opacity = 0);
	-moz-opacity: 0;
	opacity: 0;
	left: 0px;
	top: 0px;
}
</style>

<div class="container">
	<div class="block-header">
		<h2>任意Push配信登録</h2>
		<input type="hidden" id="useUserList" name="useUserList"
			ng-model="useUserListCtrl.useUserList"
			value="${sessionScope.Message}" />
	</div>

	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">

				<table ng-table="useUserListCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="2%" />
						<col width="16%" />
						<col width="16%" />
						<col width="24%" />
						<col width="16%" />
						<col width="28%" />
						<col width="3%" />
					</colgroup>
					<tr ng-repeat="w in $data">

						<td
							filter="{ 'select': 'useUserListCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td data-title="'ユーザID'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ '_id': 'ng-table/filters/text-claer-2.html'}"
							sortable="'_id'">{{ w._id }}</td>
						<td data-title="'ログインタイプ'"
							filter="{ 'userKey': 'ng-table/filters/select-clear.html'}"
							sortable="'userKey'"
							filter-data="useUserListCtrl.table.filterList2">{{w.userKey
							| userKeyTitle}}
						</td>
						<!-- <td data-title="'ログインタイプ'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'userKey': 'ng-table/filters/text-claer-2.html'}"
							sortable="'userKey'">{{ w.userKey }}</td> -->
						<td data-title="'メールアドレス'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'email': 'ng-table/filters/text-claer-2.html'}"
							sortable="'email'">{{ w.email }}</td>
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
							filter="{ 'lastReqTime': 'useUserListCtrl/table/sortAndFilter/datepicker.html'}"
							sortable="'lastReqTime'">{{ w.lastReqTime }}</td>
						<td data-title="'店番/店名/科目/口座番号/口座名義'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'accountNumber': 'ng-table/filters/text-claer-2.html'}"
							sortable="'accountNumber'"><pre style='border: dashed 0px;'>{{ w.accountNumber}}</pre></td>
						<td data-title="'詳細'"
							filter="{ '': 'useUserListCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="useUserListCtrl.table.openNa('na', w)">
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
	id="useUserListCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input ng-model="useUserListCtrl.table.sortAndFilter.filter().lastReqTime" type="text" id='lastReqTime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="useUserListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="useUserListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="useUserListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="useUserListCtrl.table.checkItemAll()" ng-model="useUserListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
</script>

<script type="text/ng-template"
	id="useUserListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button button-disabled="sendBtn" class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserListCtrl.table.sendAllBtn()">全体配信</button>
        <button button-disabled="sendBtn" class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserListCtrl.table.sendBtn()">選択配信</button>
        <button button-disabled="uploadBtn" class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserListCtrl.table.uploadBtn()">CSV配信</button>
</div>       
</script>
<script type="text/ng-template"
	id="useUserListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserListCtrl.table.sendAllBtn()">全体配信</button>
        <button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserListCtrl.table.sendBtn()">選択配信</button>
        <button disabled class="btn bgm-blue m-l-5 btn-height" customize-button-click="useUserListCtrl.table.uploadBtn()">アップロード配信</button>
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
					<div class="col-sm-5 col-sm-offset-1">
						<div class="text-item">店番/店名/科目/口座番号/口座名義</div>
					</div>
					<div class="col-sm-5">
						<div class="text-item">
                           <pre style='font-weight:normal;margin:0 0 0 0;padding:0px;border: dashed 0px;color: gray;'>{{ modalInstanceCtrl14.storeName}}</pre>
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
				 <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">OS</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                           <pre style='font-weight:normal;margin:0 0 0 0;padding:0px;border: dashed 0px;color: gray;'>{{ modalInstanceCtrl14.deviceOSVer}}</pre>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="text-item">端末</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                           <pre style='font-weight:normal;margin:0 0 0 0;padding:0px;border: dashed 0px;color: gray;'>{{ modalInstanceCtrl14.deviceModelName}}</pre>
						</div>
					</div>
				</div>
				 <div class="row detail-c-item">
					<div class="col-sm-2 col-sm-offset-1">
						<div class="text-item">Push設定</div>
					</div>
					<div class="col-sm-3">
						<div class="text-item">
                           <pre style='font-weight:normal;margin:0 0 0 0;padding:0px;border: dashed 0px;color: gray;'>{{ modalInstanceCtrl14.noticeFlag}}</pre>
						</div>
					</div>
				</div>

			</div>
		</div>
</div>
</script>

<script type="text/ng-template" id="pushMessagePopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl11.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-3">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl11.title }}<small> (*は入力必須)</small>
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
								ng-model="modalInstanceCtrl11.pushTitle"
								ng-disabled="modalInstanceCtrl11.pushTitleDisabled">
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
                           <textarea class="form-control" style="border:1px solid #e0e0e0; height:300px; width:100%" placeholder="" ng-model="modalInstanceCtrl11.pushMessage" ng-disabled="modalInstanceCtrl11.pushMessageDisabled"></textarea>
						</div>
					</div>
				</div>
			</div>

　　　　　<div ng-hide="modalInstanceCtrl11.windowHide1" class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button id = "ccccc" class="btn bgm-blue waves-effect" data-swal-warning1=""
					onclick="bbb()">プレビュー</button>
		<input type="hidden" id="outPath" name="outPath"
			ng-model="modalInstanceCtrl11.outPath"
			value="${modalInstanceCtrl11.outPath}" />
		<div ng-hide = true>
		　　　　　　　　　　<div ng-hide="modalInstanceCtrl11.windowHide1" class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button id = "hiddenUse"class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl11.dataOK()">プレビュー</button>
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl11.dataSend()" id = "sendbtn" onclick="modalInstanceCtrl11.dataSend()"
					ng-disabled="modalInstanceCtrl11.sendbtnDisabled">配信する</button>
				<div>
			</div>

		</div>
	</div>
</div>
</script>
<script type="text/ng-template" id="upPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl11.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-3">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl11.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
		<div class="card-header">
				<h2>
					CSVファイルアップロード
				</h2>
			</div>
            <div class="row">
               	<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							CSV<sup>*</sup>
						</h5>
					</div>
				</div>

                <div class="col-sm-3">
                    <div>
                        <input type="text" name="name" id="fileName" ng-model="modalInstanceCtrl11.fileName"
                            style="margin-left: 0px" disabled />
                    </div>
                </div>
                <div class="col-sm-8 col-md-3">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file" id="file" upload change="usease()">
                    </div>
                </div>
            </div>
		</div>
	</div>
</div>
</script>
<script>
	function bbb() {
		document.getElementById('hiddenUse').click();
		$scope.modalInstanceCtrl10.outPath = 
			+ r.data.resultData.outPath;
		var url = "../protected/temp/html/" + document.getElementById('outPath');
		var iWidth = 375;
		var iHeight = 775;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
		window.childWindow = window
				.open(
						url,
						"preview",
						'height='
								+ iHeight
								+ ',innerHeight='
								+ iHeight
								+ ',width='
								+ iWidth
								+ ',innerWidth='
								+ iWidth
								+ ',top='
								+ iTop
								+ ',left='
								+ iLeft
								+ ',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
		window.childWindow.focus();
	}
	function aaa() {
		document.getElementById('sendbtn').click();
	}
</script>