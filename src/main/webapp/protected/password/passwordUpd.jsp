<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html class="login-content" data-ng-app="insightApp">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Vendor CSS -->
<link
	href="<c:url value='/resources/vendors/bower_components/animate.css/animate.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/bower_components/angular-loading-bar/build/loading-bar.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css' />"
	rel="stylesheet" id="app-css-level" />

<!-- CSS -->
<link href="<c:url value='/resources/css/app.min.1.css' />"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/app.min.2.css' />"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/customize.css' />"
	rel="stylesheet" />

<!-- Core -->
<script
	src="<c:url value='/resources/vendors/bower_components/jquery/dist/jquery.min.js' />"></script>

<!-- Angular -->
<script
	src="<c:url value='/resources/vendors/bower_components/angular/angular.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/angular-resource/angular-resource.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/angular-animate/angular-animate.min.js' />"></script>

<!-- Angular Modules -->
<script
	src="<c:url value='/resources/vendors/bower_components/angular-ui-router/release/angular-ui-router.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/angular-loading-bar/build/loading-bar.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/oclazyload/dist/ocLazyLoad.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js' />"></script>

<!-- Common Vendors -->
<script
	src="<c:url value='/resources/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/Waves/dist/waves.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bootstrap-growl/bootstrap-growl.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/ng-table/dist/ng-table.min.js' />"></script>

<!-- Using below vendors in order to avoid misloading on resolve -->
<script
	src="<c:url value='/resources/vendors/bower_components/flot/jquery.flot.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/flot.curvedlines/curvedLines.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/flot/jquery.flot.resize.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/moment/min/moment.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/fullcalendar/dist/fullcalendar.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/flot-orderBars/js/jquery.flot.orderBars.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/flot/jquery.flot.pie.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/angular-nouislider/src/nouislider.min.js' />"
	id="app-js-vendors-level"></script>
<script
	src="http://js.api.olp.yahooapis.jp/OpenLocalPlatform/V1/jsapi?appid=dj0zaiZpPWNTZHlUYUNBV3ZtRyZzPWNvbnN1bWVyc2VjcmV0Jng9NWE-"></script>
<!-- App level -->
<script src="<c:url value='/resources/js/app.js' />"></script>
<script src="<c:url value='/resources/js/insight.js' />"></script>
<script src="<c:url value='/resources/js/templates.js' />"></script>
<script src="<c:url value='/resources/js/services.js' />"></script>

<!-- controllers -->
<script
	src="<c:url value='/resources/js/controllers/public/template/master-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/public/template/header-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/public/template/footer-ctrl.js' />"
	id="app-js-ctrl-level"></script>
	<script
	src="<c:url value='/resources/js/controllers/protected/password/passwordUpd-ctrl.js' />"></script>
	<script
	src="<c:url value='/resources/js/services/protected/password/passwordUpd-sv.js' />"></script>

<!-- Template Modules -->
<script src="<c:url value='/resources/js/modules/template.js' />"></script>
<script src="<c:url value='/resources/js/modules/ui.js' />"></script>
<script src="<c:url value='/resources/js/modules/form.js' />"></script>

</head>

<body data-ng-controller="passwordUpdCtrl">
<form method="post"  id="passwordUpdForm">
<input type="hidden" id="flg" name="flg" value="${sessionScope.flg}" ng-model="passwordUpdCtrl.flg"/>
<div class="container">
	<div class="block-header">
		<h2>パスワード変更</h2>
	</div>

	<div class="card">
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-sm-9">
					<div class="row">
						<div class="col-sm-5 col-md-offset-1">
							<div class="form-group">
								<h5 class="form-title" style="float: right">古いパスワード:</h5>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<div class="fg-line">
									<input type="password" id="password" class="form-control" placeholder="8文字（半角英数）以上" 
										ng-model="passwordUpdCtrl.password">
								</div>
							</div>
						</div>
						<div class="col-sm-2">
								<span class="input-group-addon last">
									<a class="zmdi zmdi-eye  input-group-addon" ng-click="passwordUpdCtrl.showDirPwd()"></a>
								</span>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-5 col-md-offset-1">
							<div class="form-group">
								<h5 class="form-title" style="float: right">新しいパスワード:</h5>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<div class="fg-line">
									<input type="password" id="newPassword" class="form-control" placeholder="8文字（半角英数）以上" 
										ng-model="passwordUpdCtrl.newPassword">
								</div>
							</div>
						</div>
						<div class="col-sm-2">
								<span class="input-group-addon last">
									<a class="zmdi zmdi-eye  input-group-addon" ng-click="passwordUpdCtrl.showDirPwd2()"></a>
								</span>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-5 col-md-offset-1">
							<div class="form-group">
								<h5 class="form-title" style="float: right">パスワードの確認入力:</h5>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<div class="fg-line">
									<input type="password" id="newConfirmPassword" class="form-control" placeholder="8文字（半角英数）以上" 
										ng-model="passwordUpdCtrl.newConfirmPassword">
								</div>
							</div>
						</div>
						<div class="col-sm-2">
								<span class="input-group-addon last">
									<a class="zmdi zmdi-eye  input-group-addon" ng-click="passwordUpdCtrl.showDirPwd3()"></a>
								</span>
						</div>
					</div>
				</div>
			</div>
			<div class="btn-colors btn-dome text-center p-20 ng-scope">
				<button ng-disabled="passwordUpdCtrl.btnDisabled"
					button-disabled="csvBtnDisabled" class="btn bgm-blue waves-effect"
					data-swal-warning1="" ng-click="passwordUpdCtrl.passwordData()">変更</button>
			</div>
		</div>
	</div>
</div>
</form>
</body>