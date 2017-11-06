<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html class="login-content" data-ng-app="insightApp">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="project.title" /></title>

<style type="text/css">
[ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak,
	.x-ng-cloak {
	display: none !important;
}
</style>

<!-- Vendor CSS -->
<link
    href="<c:url value='/resources/vendors/bower_components/animate.css/animate.min.css' />"
    rel="stylesheet" />
<link
    href="<c:url value='/resources/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css' />"
    rel="stylesheet" />

<!-- CSS -->
<link href="<c:url value='/resources/css/app.min.1.css' />"
    rel="stylesheet" />
<link href="<c:url value='/resources/css/app.min.2.css' />"
    rel="stylesheet" />
<link href="<c:url value='/resources/css/customize.css' />"
    rel="stylesheet" />
     <link rel="shortcut icon" href="<c:url value='/favicon.ico' />" />
</head>
<body class="login-content" data-ng-controller="loginCtrl">
	<div class="project-title">
        <h1>
            <img src="./img/mineFocus_logo.png">
        </h1>
    </div>
    
	<!-- Login -->
    <div class="lc-block toggled" id="l-login">
        <form method="post" action="/hyakujyushibank-server-admin/login"
            id="loginForm" >
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div>
				<div class="card-panel red lighten-2" ng-cloak>
					<span id="errorMessage" class="card-title" style="color: red"><spring:message code="{{loginCtrl.message}}" /></span>
				</div>

				<div class="input-group m-b-20">
                    <span class="input-group-addon">
                    	<i  class="zmdi zmdi-account"></i>
                    </span>
                    <div class="fg-line" style="width:282px;margin-left:-65px;">
                        <input name="j_username" id="userID" type="text" class="form-control" placeholder="ユーザーID" ng-model="loginCtrl.userID" value="${sessionScope.userID}" ng-blur="blur()"/>
                        <input type="hidden" name="hidUserID" ng-model="loginCtrl.hidUserID" value="${sessionScope.hidUserID}"/> 
                        <input type="hidden" name="count" ng-model="loginCtrl.count" value="${sessionScope.count}"/> 
                        <input type="hidden" id="errMessageID" name="errMessageID" ng-model="loginCtrl.errMessageID" value="${sessionScope.errMessageID}"/> 
                        <input type="hidden" name="flg" ng-model="loginCtrl.flg"/> 
                    </div>
                </div>

                <div class="input-group m-b-20">
                    <span class="input-group-addon">
                    	<i class="zmdi zmdi-male"></i>
                    </span>
                    <div class="fg-line">
                        <input name="j_password" id="password" type="password" class="form-control" placeholder="パスワード" ng-model="loginCtrl.password" value="${sessionScope.password}"/>
                    </div>
                    <span class="input-group-addon last">
						<a class="zmdi zmdi-eye  input-group-addon" ng-click="loginCtrl.showDirPwd()"></a>
					</span>
                </div>

				<div class="clearfix"></div>

				<div class="checkbox">
					<label> <input type="checkbox"
						ng-model="loginCtrl.selectFlg" value="${sessionScope.selectFlg}">
						<i class="input-helper"></i> ログイン情報を保持する
					</label>
					<button type="button"  ng-disabled="loginCtrl.passwordResetDisabled"  ng-click="loginCtrl.passwordReset()"  class=" btn  waves-effect pwdReset"  >パスワードを忘れた場合</button>
				</div>
				<span ng-click="loginCtrl.submitLogin();"
					class="btn btn-login btn-danger btn-float"> <i
					class="zmdi zmdi-arrow-forward"></i>
				</span>
			</div>
        </form>
	</div>    
    
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
        src="<c:url value='/resources/vendors/bower_components/jquery.nicescroll/jquery.nicescroll.min.js' />"></script>
    <script
        src="<c:url value='/resources/vendors/bower_components/Waves/dist/waves.min.js' />"></script>
    <script
        src="<c:url value='/resources/vendors/bower_components/angular-nouislider/src/nouislider.min.js' />"></script>
    <script
        src="<c:url value='/resources/vendors/bower_components/ng-table/dist/ng-table.min.js' />"></script>

	<!-- App level -->
    <script src="<c:url value='/resources/js/app.js' />"></script>
	<script src="<c:url value='/resources/js/insight.js' />"></script>
	<script src="<c:url value='/resources/js/templates.js' />"></script>
	<script src="<c:url value='/resources/js/services.js' />"></script>

    <!-- controllers -->
    <script
        src="<c:url value='/resources/js/controllers/public/login-ctrl.js' />"></script>
        <!-- service -->
    <script
        src="<c:url value='/resources/js/services/public/login-sv.js' />"></script>

    <!-- Template Modules -->
    <script src="<c:url value='/resources/js/modules/ui.js' />"></script>
    <script src="<c:url value='/resources/js/modules/form.js' />"></script>
</body>
</html>