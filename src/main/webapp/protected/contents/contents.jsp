<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
.height-size-1 {
	height: 550px;
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
		<h2>コンテンツ一覧</h2>
		<input type="hidden" id="contents" name="contents"
			ng-model="contentsCtrl.user" value="${sessionScope.Contents}" />
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-2">
					<div class="btn-colors btn-demo text-center">
						<button ng-click="contentsCtrl.addBtn('na')"
							class="btn bgm-lightblue"
							ng-disabled="contentsCtrl.addBtnDisabled">新規登録</button>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="card">
		<div class="card-header"></div>
		<div class="card-body">
			<div class="table-responsive"
				pagination-customize-buttons="sortAndFilterButtons">
				<table ng-table="contentsCtrl.table.sortAndFilter"
					template-header="template/tables/header.html"
					template-pagination="template/tables/pagination.html"
					class="table table-striped table-vmiddle">
					<colgroup>
						<col width="1%" />
						<col width="10%" />
						<col width="10%" />
						<col width="30%" />
						<col width="20%" />
						<col width="20%" />
					</colgroup>
					<tr ng-repeat="w in $data">
						<td
							filter="{ 'select': 'contentsCtrl/table/sortAndFilter/checkItemAll.html' }"
							style="padding-bottom: 19px"><label
							class="checkbox checkbox-inline m-r-20"> <input
								type="checkbox" ng-model="w.select"> <i
								class="input-helper"></i>
						</label></td>
						<td data-title="'コンテンツタイトル'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'contentsTitle': 'ng-table/filters/text-claer-2.html'}"
							sortable="'contentsTitle'">{{ w.contentsTitle }}</td>
						<td data-title="'コンテンツ種別'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'typeCD': 'ng-table/filters/text-claer-2.html' }"
							sortable="'typeCD'">{{ w.typeCD }}</td>
						<td data-title="'表示期間'"
							filter="{ 'dateFrom': 'ng-table/filters/text-claer-2.html' }"
							sortable="'dateFrom'">{{ w.dateFrom }}~{{w.dateTo}}</td>
						<td data-title="'アプリケーション'"
							style="word-break: break-all; word-wrap: break-word;"
							filter="{ 'appCD': 'ng-table/filters/text-claer-2.html'}"
							sortable="'appCD'">{{ w.appCD }}</td>
						<td data-title="'詳細／編集'"
							filter="{ '': 'contentsCtrl/table/sortAndFilter/clearFilter.html' }">
							<button class="btn bgm-lightgreen" cc="{{$index}}"
								ng-click="contentsCtrl.table.openNa('na', w)">
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
	id="contentsCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="contentsCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>
<script type="text/ng-template"
	id="contentsCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="contentsCtrl.table.checkItemAll()" ng-model="contentsCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
    </script>
<script type="text/ng-template"
	id="contentsCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right">
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn btn-danger btn bgm-red waves-effect" customize-button-click="contentsCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
	id="contentsCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right">
        <button disabled class="btn btn-danger btn bgm-red waves-effect" customize-button-click="contentsCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template" id="contentsDetailPopup.html">
<div class="popup-title">
	<i ng-click="modalInstanceCtrl7.ok()"
		class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
	<div class="container-fluid height-size-2">
		<div class="block-header">
			<h2>
				{{modalInstanceCtrl7.title }}<small> (*は入力必須)</small>
			</h2>
		</div>
		<div class="card business-time">
		<div class="card-header">
				<h2>
					アプリケーション
				</h2>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							アプリケーション  <sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line" ng-hide="modalInstanceCtrl7.select">
								<select id="sel" class="w-100" upload change="getTypeCD()"
									ng-model="modalInstanceCtrl7.appCD">
									<option ng-repeat="appCD in modalInstanceCtrl7.appCDList">{{appCD}}</option>
								</select>
						</div>
						<div class="fg-line" ng-hide="modalInstanceCtrl7.input">
							<input type="text" class="form-control" placeholder=""
								ng-model="modalInstanceCtrl7.appCD"
								ng-disabled="modalInstanceCtrl7.appCDDisabled">
						</div>
					</div>
				</div>
			</div>


			<div class="card-header">
				<h2>
					コンテンツ基本情報
				</h2>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							コンテンツID  <sup>*</sup>
						</h5>
						<small> (半角英数 40文字)</small>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" maxlength="40"
								onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								onafterpaste="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"
								ng-model="modalInstanceCtrl7.contentsID"
								ng-disabled="modalInstanceCtrl7.contentsIDDisabled">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							コンテンツタイトル  <sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" maxlength="64"
								ng-model="modalInstanceCtrl7.contentsTitle"
								ng-disabled="modalInstanceCtrl7.contentsTitleDisabled">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							コンテンツ種別 <sup>*</sup>
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
								<select id="select" class="w-100" upload change="changeTypeCD()"
									ng-model="modalInstanceCtrl7.typeCD">
									<option ng-repeat="typeCD in modalInstanceCtrl7.typeCDList">{{typeCD}}</option>
								</select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">
							表示期間(yyyymmdd) <sup>*</sup> 
						</h5>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup1' ng-model="modalInstanceCtrl7.dateFrom"
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="input-group form-group dp-blue">
						<div class="dtp-container fg-line">
							<input type="text" id='dtPopup2' ng-model="modalInstanceCtrl7.dateTo"
								class="form-control date-time-picker">
						</div>
					</div>
				</div>
				</div>

			<div class="card-header">
				<h2>
					詳細テキスト
				</h2>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト1
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment1">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト2
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment2">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト3
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment3">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト4
						</h5>
					</div>
				</div>
					<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment4">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト5
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment5">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト6
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment6">
						</div>
					</div>
				</div>
			</div>			
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト7
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment7">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト8
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment8">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト9
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment9">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 col-md-offset-1">
					<div class="form-group">
						<h5 class="form-title">	
							詳細テキスト10
						</h5>
					</div>
				</div>
				<div class="col-sm-5 col-md-5">
					<div class="form-group">
						<div class="fg-line">
							<input type="text" class="form-control" placeholder="" 
								ng-model="modalInstanceCtrl7.comment10">
						</div>
					</div>
				</div>
			</div>
			<div class="card-header">
				<h2>
					コンテンツファイル情報
				</h2>
			</div>
			<div class="row">

                <div class="col-sm-7 col-md-offset-1">
                    <div>
                        <input type="text" name="name" id="filename1" ng-model="modalInstanceCtrl7.filename1"
                            style="margin-left: 0px;width:450px" disabled />
						<a ng-hide="modalInstanceCtrl7.path1" ng-href={{filePath1}} target="_blank">{{filePath1}}</a>
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file1" id="file1" upload change="usease1()">
                    </div>
                  </div>
				<div class="col-sm-1">
					<div class="form-group">
						<div class="fg-line">
							<h5 class="form-title">削除</h5>
						</div>
					</div>
				</div>
				<div class="col-sm-1" style="margin-left:-25px">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"  
								ng-model="modalInstanceCtrl7.delFlg1"> <i
									class="input-helper"></i> </label>
				</div>
				<div type="hidden">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFileName1">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFile1">
					<input type="hidden" ng-model="modalInstanceCtrl7.createFlag1">
				</div>
                </div>
			<div class="row">

                <div class="col-sm-7 col-md-offset-1">
                    <div>
                        <input type="text" name="name" id="filename2" ng-model="modalInstanceCtrl7.filename2"
                            style="margin-left: 0px;width:450px" disabled />
						<a ng-hide="modalInstanceCtrl7.path2" ng-href={{filePath2}} target="_blank">{{filePath2}}</a>
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file2" id="file2" upload change="usease2()">
                    </div>
                  </div>
				<div class="col-sm-1">
					<div class="form-group">
						<div class="fg-line">
							<h5 class="form-title">削除</h5>
						</div>
					</div>
				</div>
				<div class="col-sm-1" style="margin-left:-25px">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl7.delFlg2"> <i
									class="input-helper"></i> </label>
				</div>
				<div type="hidden">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFileName2">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFile2">
					<input type="hidden" ng-model="modalInstanceCtrl7.createFlag2">
				</div>
                </div>
			<div class="row">
                <div class="col-sm-7 col-md-offset-1">
                    <div>
                        <input type="text" name="name" id="filename3" ng-model="modalInstanceCtrl7.filename3"
                            style="margin-left: 0px;width:450px" disabled />
						<a ng-hide="modalInstanceCtrl7.path3" ng-href={{filePath3}} target="_blank">{{filePath3}}</a>
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file3" id="file3" upload change="usease3()">
                    </div>
                  </div>
				<div class="col-sm-1">
					<div class="form-group">
						<div class="fg-line">
							<h5 class="form-title">削除</h5>
						</div>
					</div>
				</div>
				<div class="col-sm-1" style="margin-left:-25px">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl7.delFlg3"> <i
									class="input-helper"></i> </label>
				</div>
				<div type="hidden">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFileName3">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFile3">
					<input type="hidden" ng-model="modalInstanceCtrl7.createFlag3">
				</div>
                </div>
			<div class="row">
			<div class="col-sm-7 col-md-offset-1">

                    <div>
                        <input type="text" name="name" id="filename4" ng-model="modalInstanceCtrl7.filename4"
                            style="margin-left: 0px;width:450px" disabled />
						<a ng-hide="modalInstanceCtrl7.path4" ng-href={{filePath4}} target="_blank">{{filePath4}}</a>
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file4" id="file4" upload change="usease4()">
                    </div>
                  </div>
				<div class="col-sm-1">
					<div class="form-group">
						<div class="fg-line">
							<h5 class="form-title">削除</h5>
						</div>
					</div>
				</div>
				<div class="col-sm-1" style="margin-left:-25px">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl7.delFlg4"> <i
									class="input-helper"></i></label>
				</div>
				<div type="hidden">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFileName4">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFile4">
					<input type="hidden" ng-model="modalInstanceCtrl7.createFlag4">
				</div>
                </div>
			<div class="row">
			<div class="col-sm-7 col-md-offset-1">

                    <div>
                        <input type="text" name="name" id="filename5" ng-model="modalInstanceCtrl7.filename5"
                            style="margin-left: 0px;width:450px" disabled />
						<a ng-hide="modalInstanceCtrl7.path5" ng-href={{filePath5}} target="_blank">{{filePath5}}</a>
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file5" id="file5" upload change="usease5()">
                    </div>
                  </div>
				<div class="col-sm-1">
					<div class="form-group">
						<div class="fg-line">
							<h5 class="form-title">削除</h5>
						</div>
					</div>
				</div>
				<div class="col-sm-1" style="margin-left:-25px">
							<label class="checkbox checkbox-inline m-r-20"> <input
								value="0" type="checkbox"
								ng-model="modalInstanceCtrl7.delFlg5"><i
									class="input-helper"></i> </label>
				</div>
				<div type="hidden">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFileName5">
					<input type="hidden" ng-model="modalInstanceCtrl7.contentsFile5">
					<input type="hidden" ng-model="modalInstanceCtrl7.createFlag5">
				</div>
                </div>

			</div>
			<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
				<button class="btn bgm-blue waves-effect" data-swal-warning1=""
					ng-click="modalInstanceCtrl7.dataUpd()"
					ng-disabled="modalInstanceCtrl7.UpdbtnDisabled">{{modalInstanceCtrl7.button}}</button>
			</div>
			</div>
	</div>
</div>

</script>