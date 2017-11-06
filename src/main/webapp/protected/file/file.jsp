<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
.div1 {
	float: left;
	height: 30px;
	background: #ffffff;
	width: 74px;
	position: relative;
}

.inputstyle {
	width: 74px;
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

.col-md-offset-1 {
	margin-left: 2.33333333%;
}
</style>
<div class="container">
	<div class="block-header">
		<h2>HTMLとPDFファイル管理</h2>
		<input type="hidden" id="fileSession" name="fileSession"
			ng-model="fileUploadCtrl.fileSession"
			value="${sessionScope.HtmlPdfFile}" />
	</div>
	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">iOS/Android：</h5>
					</div>
				</div>
				<div class="col-sm-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="typeKbn" value="0" type="radio"
							ng-model="fileUploadCtrl.typeKbn"> <i
							class="input-helper"></i> iOS
						</label>
					</div>
				</div>
				<div class="col-sm-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20"> <input
							name="typeKbn" value="1" type="radio"
							ng-model="fileUploadCtrl.typeKbn"> <i
							class="input-helper"></i> Android
						</label>
					</div>
				</div>
				<div class="col-sm-5">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="findFile()" class="btn bgm-lightblue"
							style="height: 45px;"
							ng-disabled="fileUploadCtrl.findFileBtnDisabled">更新履歴検索</button>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">ファイル名：</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="form-group">
						<div class="fg-line">
							<div class="select">
								<select class="form-control" name="selectName" id="selectName">
									<option selected></option>
									<option value="0">お知らせHTML</option>
									<option value="1">ライセンス情報HTML</option>
									<option value="2">反社会的勢力HTML</option>
									<option value="3">その他口座に関する同意事項HTML</option>
									<option value="4">ひろぎんネット支店ご利用規定集PDF</option>
									<option value="5">総合口座等取引規定集PDF</option>
									<option value="6">（仮）いよぎんアプリ利用規定HTML</option>
								</select>
							</div>
						</div>
					</div>
				</div>

			</div>
			<hr style="color: #e0e0e0" />
			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">ファイルアップロード：</h5>
					</div>
				</div>
				<div class="col-sm-2">
					<div>
						<input type="text" name="name" id="name" disabled />
					</div>
				</div>
				<div class="col-sm-1" style="margin-left: -25px;">
					<button class="btn bgm-lightblue">参照</button>
					<input type="file" class="inputstyle" name="file" id="file">
				</div>
				<div class="col-sm-3" style="margin-left: 25px;">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="upload()" class="btn bgm-lightblue"
							style="height: 45px;"
							ng-disabled="fileUploadCtrl.uploadBtnDisabled">アップロード</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2">
					<div class="form-group">
						<h5 class="form-title">希望利用日：</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup2'
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="card" ng-hide="fileUploadCtrl.windowHide">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">iOS/Android：</h5>
					</div>
				</div>
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20" style="cursor: default;">
							{{fileUploadCtrl.typeKBnName}} </label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">ファイル名（日本語）：</h5>
					</div>
				</div>
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20" style="cursor: default;">
							{{fileUploadCtrl.fileNameJP}} </label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">ファイル名（英語）：</h5>
					</div>
				</div>
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20" style="cursor: default;">
							{{fileUploadCtrl.fileNameEN}} </label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-1 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">ファイルパス：</h5>
					</div>
				</div>
				<div class="col-sm-4 col-md-offset-1">
					<div class="form-group">
						<label class="radio radio-inline m-r-20" style="cursor: default;">
							{{fileUploadCtrl.path}} </label>
					</div>
				</div>
			</div>
		</div>
		<div class="card-body">
			<div pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="fileUploadCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="10%" />
						<col width="15%" />
						<col width="18%" />
						<col width="10%" />
						<col width="18%" />
						<col width="12%" />
						<col width="17%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'fileUploadCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
								<i class="input-helper"></i>
						</label></td>
						<td data-title="'アップロード日時'"
							filter="{ 'uploadDatetime': 'fileUploadCtrl/table/sortAndFilter/datepicker1.html'}"
							sortable="'uploadDatetime'">{{ w.uploadDatetime }}</td>
						<td style="width: 1px;" data-title="'希望利用日'"
							filter="{ 'hopingUseDate': 'fileUploadCtrl/table/sortAndFilter/datepicker.html' }"
							sortable="'hopingUseDate'">{{ w.hopingUseDate }}</td>
						<td data-title="'利用中フラグ'"
							filter="{ 'useFlag': 'ng-table/filters/select-clear.html'}"
							sortable="'useFlag'"
							filter-data="fileUploadCtrl.table.filterList2">{{w.useFlag |
							useFlagTitle }}</td>
						<td style="width: 1px;" data-title="'バッチ利用日時'"
							filter="{ 'batExecuteDatetime': 'fileUploadCtrl/table/sortAndFilter/datepicker2.html'}"
							sortable="'batExecuteDatetime'">{{ w.batExecuteDatetime }}</td>
						<td data-title="'アップロード者'"
							filter="{ 'createdBy': 'ng-table/filters/text-claer-2.html'}"
							sortable="'createdBy'">{{ w.createdBy }}</td>
							<td data-title="'ダウンロード'"
							filter="{ '': 'fileUploadCtrl/table/sortAndFilter/clearFilter.html' }">
							<a class="btn bgm-lightgreen"
							ng-click="fileUploadCtrl.table.download(w)"> <img
								src="../img/download.png">
						</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
</div>

<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/datepicker.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input style="z-index:100000px;"ng-model="fileUploadCtrl.table.sortAndFilter.filter().hopingUseDate" type="text" id='hopingUseDate'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/datepicker1.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input style="z-index:100000px;"ng-model="fileUploadCtrl.table.sortAndFilter.filter().uploadDatetime" type="text" id='uploadDatetime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/datepicker2.html">
      <div class="input-group">
                        <div class="dtp-container fg-line">
                        <input style="z-index:100000px;"ng-model="fileUploadCtrl.table.sortAndFilter.filter().batExecuteDatetime" type="text" id='batExecuteDatetime'  class="form-control date-time-picker">
                    </div><span ng-click="params.filter()[name] = ''"
		class="input-group-addon last"><i
		class="zmdi zmdi-close-circle"></i></span>
                </div>
  </script>
<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="fileUploadCtrl.table.sortAndFilter.filter({})">リセット</button>
</script>
<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="fileUploadCtrl.table.checkItemAll()" ng-model="fileUploadCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
</script>
<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/paginationButton.html">
	<div style="float:right" class="ng-scope" >
      <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="fileUploadCtrl.table.deleteBtn()">一括削除</button>
</div>   
</script>
<script type="text/ng-template"
	id="fileUploadCtrl/table/sortAndFilter/paginationButtonDisabled.html">
<div style="float:right" class="ng-scope" >
       <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="fileUploadCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
