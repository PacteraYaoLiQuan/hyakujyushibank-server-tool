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
        <h2>ファイル一覧</h2>
        <input type="hidden" id="imageFile" name="imageFile"
            ng-model="fileListCtrl.imageFile" value="${sessionScope.ImageFile}" />
    </div>
    <div class="card">
        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-2">
                    <div class="btn-colors btn-demo text-center">
                        <button ng-click="fileListCtrl.addBtn('na')"
                            class="btn bgm-lightblue"
                            ng-disabled="fileListCtrl.addBtnDisabled">新規登録</button>
                    </div>
                </div>
                <!--  <div class="col-sm-8 col-md-3">
                    <div class="div1"  style="width:200;height:200;display:none">
                        <input  type="file" class="inputstyle" name="hiddenFile" id="hiddenFile">
                    </div>
                  </div>
                  -->
            </div>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive"
                pagination-customize-buttons="sortAndFilterButtons">
				<div style="float: left;" class="col-sm-3">
					<div style="margin-top: 15px;"
						class="input-group form-group dp-blue">
						<span class="input-group-addon">ファイル名：</span>
						<div class="dtp-container fg-line">
							<input type="text" id='insert1'
								class="form-control date-time-picker">
						</div>
					</div>
					<div class="input-group form-group dp-blue">
						<span class="input-group-addon">利用箇所：</span>
						<div style="margin-left: 5px;">
							<div class="dtp-container fg-line">
								<input type="text" id='insert2'
									class="form-control date-time-picker">
							</div>
						</div>
					</div>
				</div>
				<div style="float: left" class="col-sm-2">
                    <button ng-disabled="fileListCtrl.fileListSelBtn"
                        style="margin-top:50px;" class="btn bgm-blue m-l-5"
                        ng-click="fileListCtrl.table.fileListSelBtn()">
                        &nbsp;検&nbsp;&nbsp;&nbsp;&nbsp;索&nbsp;
                    </button>
                </div>
                <table ng-table="fileListCtrl.table.sortAndFilter"
                    template-header="template/tables/header.html"
                    template-pagination="template/tables/pagination.html"
                    class="table table-striped table-vmiddle">
                    <colgroup>
                        <col width="3%" />
                        <col width="20%" />
                        <col width="20%" />
                        <col width="42%" />
                    </colgroup>
                    <tr ng-repeat="w in $data">
                        <td
                            filter="{ 'select': 'fileListCtrl/table/sortAndFilter/checkItemAll.html' }"
                            style="padding-bottom: 19px"><label
                            class="checkbox checkbox-inline m-r-20"> <input
                                type="checkbox" ng-model="w.select" ng-disabled="w.checkDisable">
                                <i class="input-helper"></i>
                        </label></td>
                        <td data-title="'ファイル名'"
                        filter="{ 'fileNameJP': 'ng-table/filters/text-claer-2.html'}"
                            sortable="'fileNameJP'">{{ w.fileNameJP }}</td>
                        <td data-title="'利用箇所'"
                        filter="{ 'useLocal': 'ng-table/filters/text-claer-2.html'}"
                            sortable="'useLocal'">{{ w.useLocal }}</td>
                        <td data-title="'参照URL'"
                        filter="{ 'referURL': 'ng-table/filters/text-claer-2.html'}"
                            sortable="'referURL'" >
                          <a target="_blank"  href = '{{ w.referURL }}' >{{ w.referURL }}</a></td>
                        <td data-title="'詳細／編集'" filter="{ '': 'fileListCtrl/table/sortAndFilter/clearFilter.html' }">
                            <button class="btn bgm-lightgreen" cc="{{$index}}"
                                ng-click="fileListCtrl.table.openNa('na', w)">
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
    id="fileListCtrl/table/sortAndFilter/checkItemAll.html">
        <label class="checkbox checkbox-inline m-r-20">
            <input type="checkbox" value="" ng-click="fileListCtrl.table.checkItemAll()" ng-model="fileListCtrl.table.checkboxes.checked"><i class="input-helper"></i>
        </label>
</script>

<script type="text/ng-template"
    id="fileListCtrl/table/sortAndFilter/clearFilter.html">
        <button class="btn bgm-gray" ng-click="fileListCtrl.table.sortAndFilter.filter({})">リセット</button>
    </script>

<script type="text/ng-template"
    id="fileListCtrl/table/sortAndFilter/paginationButtonGroup.html">
<div style="float:right" class="ng-scope" >
        <button ng-disabled="delBtnDisabled" button-disabled="delBtnDisabled" class="btn bgm-red m-l-5 btn-height" customize-button-click="fileListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>
<script type="text/ng-template"
    id="fileListCtrl/table/sortAndFilter/paginationButton.html">
<div style="float:right" class="ng-scope" >
       <button disabled class="btn bgm-red m-l-5 btn-height" customize-button-click="fileListCtrl.table.deleteBtn()">一括削除</button>
</div>    
</script>



<style type="text/css">
.scrool-size-1 {
    height: 550px;
}
</style>



<script type="text/ng-template" id="fileDetailPopup.html">

<div class="popup-title">
    <i ng-click="modalInstanceCtrl5.ok()"
        class="zmdi zmdi-close zmdi-hc-fw pull-right"></i>
</div>
<div style="padding-top: 20px;" class="bgm-edecec">
    <div class="container-fluid scrool-size-1">
        <div class="block-header">
            <h2>
                {{modalInstanceCtrl5.title }}<small> (*は入力必須)</small>
            </h2>
        </div>
        <div class="card business-time">
           <div class="row">
                <div class="col-sm-3 col-md-offset-1">
                    <div class="form-group">
                        <h5 class="form-title">
                            ファイル名(日本語)<sup>*</sup>
                        </h5>
                    </div>
                </div>
                <div class="col-sm-8 col-md-7">
                    <div class="form-group">
                        <div class="fg-line">
                            <input type="text" class="form-control" placeholder=""
                                ng-model="modalInstanceCtrl5.fileNameJP" ng-disabled="modalInstanceCtrl5.fileNameJPDisabled">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-3 col-md-offset-1">
                    <div class="form-group">
                        <h5 class="form-title">
                            ファイル名(英語)<sup>*</sup>
                        </h5>
                    </div>
                </div>
                <div class="col-sm-8 col-md-7">
                    <div class="form-group">
                        <div class="fg-line">
                            <input type="text" class="form-control" placeholder=""
                                ng-model="modalInstanceCtrl5.fileNameEN"
                                ng-disabled="modalInstanceCtrl5.fileNameENDisabled">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-3 col-md-offset-1">
                    <div class="form-group">
                        <h5 class="form-title">
                            利用箇所&nbsp;&nbsp;<sup>*</sup>
                        </h5>
                    </div>
                </div>
                <div class="col-sm-8 col-md-7">
                    <div class="form-group">
                        <div class="fg-line">
                            <input type="useLocal" class="form-control" placeholder=""
                                ng-model="modalInstanceCtrl5.useLocal">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-3 col-md-offset-1">
                    <div class="form-group">
                        <h5 class="form-title">
                            ファイルアップロード&nbsp;<sup>*</sup>
                        </h5>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div>
                        <input type="text" name="name" id="filename"
                            style="margin-left: 0px;" disabled />
                    </div>
                </div>
                <div class="col-sm-8 col-md-3">
                    <div class="div1">
                        <button class="btn bgm-lightblue">参照</button>
                        <input type="file" class="inputstyle" name="file" id="file" upload change="usease()">
                    </div>
                  </div>
                </div>
　　　　　<div class="btn-colors btn-dome text-center p-20 bgm-edecec ng-scope">
                <button class="btn bgm-blue waves-effect" data-swal-warning1=""
                    ng-click="modalInstanceCtrl5.dataUpd()"
                    ng-disabled="modalInstanceCtrl5.UpdbtnDisabled">{{modalInstanceCtrl5.button}}</button>
            </div>
            </div>
    </div>
</div>
</script>
<script type="text/ng-template"
    id="fileListCtrl/table/sortAndFilter/paginationButton.html">
</script>
