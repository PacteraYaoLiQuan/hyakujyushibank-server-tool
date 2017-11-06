<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html data-ng-app="insightApp" data-ng-controller="masterCtrl">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="project.title" /></title>

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
<link
	href="<c:url value='/resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css' />"
	rel="stylesheet" />
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

<script
	src="<c:url value='/resources/vendors/bower_components/moment/min/moment.min.js' />"></script>
<script
	src="<c:url value='/resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js' />"></script>

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
	src="<c:url value='/resources/vendors/yahoo-maps/jsapi.js?appid=dj0zaiZpPWNTZHlUYUNBV3ZtRyZzPWNvbnN1bWVyc2VjcmV0Jng9NWE-' />"></script>
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
	src="<c:url value='/resources/js/services/protected/authority/authorityList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/authority/authorityList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/authority/authorityDetail-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/master/storeATMList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/master/storeATMList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/account/accountAppList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/account/accountAppList-ctrl.js' />"></script>

<script
	src="<c:url value='/resources/js/services/protected/account/accountLoanList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/account/accountLoanList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/push/pushRecordAppList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/document/accountDocumentList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/record/documentRecordList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/push/pushRecordAppList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/push/pushRecordLoanList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/push/pushRecordLoanList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/document/accountDocumentList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/push/pushRecordAppList-sv.js' />"></script>

<script
	src="<c:url value='/resources/js/services/protected/account/accountAppDetail-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/account/web-account-list-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/account/web-account-list-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/user/userList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/user/userList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/password/passwordUpd-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/password/passwordUpd-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/imageFile/fileList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/imageFile/fileList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/file/fileUpload-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/file/fileUpload-sv.js' />"></script>
<%-- <script
	src="<c:url value='/resources/js/controllers/protected/batch/batchList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/batch/batchList-sv.js' />"></script> --%>
<script
	src="<c:url value='/resources/js/controllers/protected/masterData/masterData-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/masterData/masterData-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/public/template/footer-ctrl.js' />"
	id="app-js-ctrl-level"></script>

<script
	src="<c:url value='/resources/js/controllers/protected/application/application-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/type/type-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/contents/contents-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/application/applicationList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/type/type-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/contents/contents-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/useUser/useUserList-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/useUser/useUserList-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/useUser/useUserDownLoad-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/useUser/useUserDownLoad-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/message/pushMessage-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/message/pushMessage-sv.js' />"></script>
<script
	src="<c:url value='/resources/js/controllers/protected/generalPurpose/generalPurpose-ctrl.js' />"></script>
<script
	src="<c:url value='/resources/js/services/protected/generalPurpose/generalPurpose-sv.js' />"></script>
<!-- <script src="<c:url value='/resources/js/services/protected/login/login-user-list-sv.js' />"></script> -->
<script
	src="<c:url value='/resources/js/controllers/protected/login/login-user-list-ctrl.js' />"></script>
<!-- <script src="<c:url value='/resources/js/services/protected/login/upt-login-user-sv.js' />"></script> -->
<script
	src="<c:url value='/resources/js/controllers/protected/login/upt-login-user-ctrl.js' />"></script>

<!-- Template Modules -->
<script src="<c:url value='/resources/js/modules/template.js' />"></script>
<script src="<c:url value='/resources/js/modules/ui.js' />"></script>
<script src="<c:url value='/resources/js/modules/form.js' />"></script>
<link rel="shortcut icon" href="<c:url value='/favicon.ico' />" />
</head>
<body data-ng-class="{ 'sw-toggled': masterCtrl.layoutType === '1'}">
	<input type="hidden" id="userName" value="${userName}">
	<header id="header" data-current-skin={{masterCtrl.currentSkin}}
		data-ng-include="'template/header.html'"
		data-ng-controller="headerCtrl"></header>
	<section id="main">
		<aside id="sidebar" class="ng-scope toggled"
			data-ng-class="{ 'toggled': masterCtrl.sidebarToggle.left === true }">
			<div class="sidebar-inner c-overflow">
				<div class="profile-menu">
					<div toggle-submenu>
						<div class="profile-pic">
							<h1 id="userNameHeader"></h1>
						</div>
						<div class="profile-info" style="text-align: center">
							<ul>
								<li><a href="./../logout"><i
										class="zmdi zmdi-time-restore"></i> Logout</a></li>
							</ul>
						</div>
					</div>
				</div>
				<ul class="main-menu">
					<c:if test="${sessionScope.loginID != 'admin'}">
						<li><a data-ui-sref-active="active"
							data-ui-sref="password.passwordUpdate"
							data-ng-click="masterCtrl.sidebarStat($event)">パスワード変更</a></li>
						<!-- 	<li><a data-ui-sref-active="active" data-ui-sref="file.file"
                        data-ng-click="masterCtrl.sidebarStat($event)">file管理</a></li> -->
					</c:if>
					<!-- <li><a data-ui-sref-active="active" data-ui-sref="batch.batch"
						data-ng-click="masterCtrl.sidebarStat($event)">バッチ管理</a></li> -->
					<c:if test="${sessionScope.loginID == 'admin'}">
						<li><a style="color: #cacaca; cursor: not-allowed;">パスワード変更</a></li>
					</c:if>
					<li class="sub-menu"
						data-ng-class="{ 'active toggled': masterCtrl.$state.includes('user') }">
						<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>アカウント管理</a>
						<ul>
							<c:if test="${sessionScope.User != '0'}">
								<li><a data-ui-sref-active="active"
									data-ui-sref="user.userList"
									data-ng-click="masterCtrl.sidebarStat($event)">アカウント一覧</a></li>
							</c:if>
							<c:if test="${sessionScope.User == '0'}">
								<li><a style="color: #cacaca; cursor: not-allowed;">アカウント一覧</a></li>
							</c:if>
						</ul>
					</li>
					<li class="sub-menu"
						data-ng-class="{ 'active toggled': masterCtrl.$state.includes('authority') }">
						<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>権限管理</a>
						<ul>
							<c:if test="${sessionScope.Authority != '0'}">
								<li><a data-ui-sref-active="active"
									data-ui-sref="authority.authorityList"
									data-ng-click="masterCtrl.sidebarStat($event)">権限一覧</a></li>
							</c:if>
							<c:if test="${sessionScope.Authority == '0'}">
								<li><a style="color: #cacaca; cursor: not-allowed;">権限一覧</a></li>
							</c:if>
						</ul>
					</li>
					<c:if test="${sessionScope.bank_cd == '0169'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('master') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>マスタ管理</a>
							<ul>
								<c:if test="${sessionScope.Store != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="master.storeATMList"
										data-ng-click="masterCtrl.sidebarStat($event)">店舗ATM一覧</a></li>
								</c:if>
								<c:if test="${sessionScope.Store == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">店舗ATM一覧</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<c:if test="${sessionScope.bank_cd == '0174'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('master') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>マスタ管理</a>
							<ul>
								<c:if test="${sessionScope.Store != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="master.iYoStoreATMList"
										data-ng-click="masterCtrl.sidebarStat($event)">店舗ATM一覧</a></li>
								</c:if>
								<c:if test="${sessionScope.Store == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">店舗ATM一覧</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<c:if test="${sessionScope.bank_cd == '0173'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('master') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>マスタ管理</a>
							<ul>
								<c:if test="${sessionScope.Store != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="master.114StoreATMList"
										data-ng-click="masterCtrl.sidebarStat($event)">店舗ATM一覧</a></li>
								</c:if>
								<c:if test="${sessionScope.Store == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">店舗ATM一覧</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<c:if
						test="${sessionScope.bank_cd == '0122' || sessionScope.bank_cd== '0169' || sessionScope.bank_cd== '0173'}">

						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('account') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i> 口座開設</a>
							<ul>
								<c:if test="${sessionScope.bank_cd == '0122'}">
									<c:if test="${sessionScope.Account != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.accountYamaGataAppList"
											data-ng-click="masterCtrl.sidebarStat($event)">申込一覧</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0169'}">
									<c:if test="${sessionScope.Account != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.accountAppList"
											data-ng-click="masterCtrl.sidebarStat($event)">申込一覧</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0173'}">
									<c:if test="${sessionScope.Account != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.account114AppList"
											data-ng-click="masterCtrl.sidebarStat($event)">口座申込一覧</a></li>
									</c:if>

									<c:if test="${sessionScope.Account == '0'}">
										<li><a style="color: #cacaca; cursor: not-allowed;">口座申込一覧</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0122'}">
									<c:if test="${sessionScope.Push != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.pushRecordAppYamaGataList"
											data-ng-click="masterCtrl.sidebarStat($event)">Push通知承認</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0169'}">
									<c:if test="${sessionScope.Push != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.pushRecordAppList"
											data-ng-click="masterCtrl.sidebarStat($event)">Push通知承認</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0173'}">
									<c:if test="${sessionScope.Push != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.pushRecordApp114List"
											data-ng-click="masterCtrl.sidebarStat($event)">口座申込承認</a></li>
									</c:if>

									<c:if test="${sessionScope.Push == '0'}">
										<li><a style="color: #cacaca; cursor: not-allowed;">口座申込承認</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0173'}">
									<c:if test="${sessionScope.Loan != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.accountLoanList"
											data-ng-click="masterCtrl.sidebarStat($event)">ローン申込一覧</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.Loan == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">ローン申込一覧</a></li>
								</c:if>

								<c:if test="${sessionScope.bank_cd == '0173'}">
									<c:if test="${sessionScope.LoanPush != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.pushRecordLoanList"
											data-ng-click="masterCtrl.sidebarStat($event)">ローン申込承認</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.LoanPush == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">ローン申込承認</a></li>
								</c:if>

								<c:if test="${sessionScope.bank_cd == '0173'}">
									<c:if test="${sessionScope.Document != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.accountDocumentList"
											data-ng-click="masterCtrl.sidebarStat($event)">各種書類申込一覧</a></li>
									</c:if>

									<c:if test="${sessionScope.Document == '0'}">
										<li><a style="color: #cacaca; cursor: not-allowed;">各種書類申込一覧</a></li>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.bank_cd == '0173'}">
									<c:if test="${sessionScope.DocumentPush != '0'}">
										<li><a data-ui-sref-active="active"
											data-ui-sref="account.documentRecordList"
											data-ng-click="masterCtrl.sidebarStat($event)">各種書類申込承認</a></li>
									</c:if>

									<c:if test="${sessionScope.DocumentPush == '0'}">
										<li><a style="color: #cacaca; cursor: not-allowed;">各種書類申込承認</a></li>
									</c:if>
								</c:if>
							</ul>
						</li>
					</c:if>

					<li class="sub-menu"
						data-ng-class="{ 'active toggled': masterCtrl.$state.includes('application') }">
						<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>
							コンテンツ管理</a>
						<ul>
							<c:if test="${sessionScope.Application != '0'}">
								<li><a data-ui-sref-active="active"
									data-ui-sref="application.application"
									data-ng-click="applicationCtrl.sidebarStat($event)">アプリケーション管理</a></li>
							</c:if>
							<c:if test="${sessionScope.Application == '0'}">
								<li><a style="color: #cacaca; cursor: not-allowed;">アプリケーション管理</a></li>
							</c:if>
							<c:if test="${sessionScope.Type != '0'}">
								<li><a data-ui-sref-active="active"
									data-ui-sref="type.type"
									data-ng-click="typeCtrl.sidebarStat($event)">コンテンツ種別</a></li>
							</c:if>
							<c:if test="${sessionScope.Type == '0'}">
								<li><a style="color: #cacaca; cursor: not-allowed;">コンテンツ種別</a></li>
							</c:if>
							<c:if test="${sessionScope.Contents != '0'}">
								<li><a data-ui-sref-active="active"
									data-ui-sref="contents.contents"
									data-ng-click="contentsCtrl.sidebarStat($event)">コンテンツ</a></li>
							</c:if>
							<c:if test="${sessionScope.Contents == '0'}">
								<li><a style="color: #cacaca; cursor: not-allowed;">コンテンツ</a></li>
							</c:if>
						</ul>
					</li>

					<c:if test="${sessionScope.bank_cd == '0174'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('use') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>
								利用ユーザー管理</a>
							<ul>
								<c:if test="${sessionScope.UseUser != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="useUser.useUserList"
										data-ng-click="useUserListCtrl.sidebarStat($event)">利用ユーザー一覧</a></li>
								</c:if>
								<c:if test="${sessionScope.UseUser == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">利用ユーザー一覧</a></li>
								</c:if>

								<%-- <c:if test="${sessionScope.UseUserDownLoad != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="useUser.useUserDownLoad"
										data-ng-click="useUserDownLoadCtrl.sidebarStat($event)">ﾏｲﾃｰﾏ利用ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if>
								<c:if test="${sessionScope.UseUserDownLoad == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">ﾏｲﾃｰﾏ利用ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if> --%>
							</ul>
						</li>
					</c:if>
					
					<c:if test="${sessionScope.bank_cd == '0173'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('use') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>
								利用ユーザー管理</a>
							<ul>
								<c:if test="${sessionScope.UseUser != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="useUser.useUser114List"
										data-ng-click="useUserListCtrl.sidebarStat($event)">利用ユーザー一覧</a></li>
								</c:if>
								<c:if test="${sessionScope.UseUser == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">利用ユーザー一覧</a></li>
								</c:if>

								<%-- <c:if test="${sessionScope.UseUserDownLoad != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="useUser.useUserDownLoad"
										data-ng-click="useUserDownLoadCtrl.sidebarStat($event)">ﾏｲﾃｰﾏ利用ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if>
								<c:if test="${sessionScope.UseUserDownLoad == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">ﾏｲﾃｰﾏ利用ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if> --%>
							</ul>
						</li>
					</c:if>

					<c:if test="${sessionScope.bank_cd == '0122'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('common') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i> その他</a>
							<ul>
								<c:if test="${sessionScope.Message != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="message.pushMessage"
										data-ng-click="pushMessageCtrl.sidebarStat($event)">任意ﾒｯｾｰｼﾞPush配信</a></li>
								</c:if>
								<c:if test="${sessionScope.Message == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">任意ﾒｯｾｰｼﾞPush配信</a></li>
								</c:if>
								<c:if test="${sessionScope.Message != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="generalPurpose.generalPurpose"
										data-ng-click="generalPurposeCtrl.sidebarStat($event)">汎用DB＆ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if>
								<c:if test="${sessionScope.Message == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">汎用DB＆ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>

					<c:if
						test="${sessionScope.bank_cd == '0174' || sessionScope.bank_cd== '0173'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('common') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>
								任意Push配信管理</a>
							<ul>
								<c:if test="${sessionScope.Message != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="message.iyoPushUserList"
										data-ng-click="pushMessageCtrl.sidebarStat($event)">任意Push配信登録</a></li>
								</c:if>
								<c:if test="${sessionScope.Message == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">任意Push配信登録</a></li>
								</c:if>

								<c:if test="${sessionScope.MessageList != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="message.iyoPushMessage"
										data-ng-click="pushMessageCtrl.sidebarStat($event)">任意Push配信履歴</a></li>
								</c:if>
								<c:if test="${sessionScope.MessageList == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">任意Push配信履歴</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<%-- <c:if test="${sessionScope.bank_cd == '0173'}">
						<li class="sub-menu"
							data-ng-class="{ 'active toggled': masterCtrl.$state.includes('common') }">
							<a href="" toggle-submenu><i class="zmdi zmdi-home"></i> その他</a>
							<ul>
								<c:if test="${sessionScope.Message != '0'}">
									<li><a data-ui-sref-active="active"
										data-ui-sref="generalPurpose.generalPurpose"
										data-ng-click="generalPurposeCtrl.sidebarStat($event)">汎用DB＆ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if>
								<c:if test="${sessionScope.Message == '0'}">
									<li><a style="color: #cacaca; cursor: not-allowed;">汎用DB＆ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ</a></li>
								</c:if>
							</ul>
						</li>
					</c:if> --%>
					<%-- <c:if test="${sessionScope.loginID == 'admin'}"> --%>
					<%-- <li class="sub-menu"
						data-ng-class="{ 'active toggled': masterCtrl.$state.includes('file') }">
						<a href="" toggle-submenu><i class="zmdi zmdi-home"></i>
							ファイル管理</a>
						<ul>
							 <c:if test="${sessionScope.HtmlPdfFile != '0'}">
                            <c:if test="${sessionScope.loginID == 'admin'}">
                                <li><a data-ui-sref-active="active"
                                    data-ui-sref="file.file"
                                    data-ng-click="masterCtrl.sidebarStat($event)">HTMLとPDFファイル管理</a></li>
                            </c:if>
                           <c:if test="${sessionScope.HtmlPdfFile == '0'}">
                            <c:if test="${sessionScope.loginID != 'admin'}">
                                <li><a style="color: #cacaca; cursor: not-allowed;">HTMLとPDFファイル管理</a></li>
                            </c:if>
                           <c:if test="${sessionScope.StoreAtmDataFile != '0'}">
                            <c:if test="${sessionScope.loginID == 'admin'}">
                                <li><a data-ui-sref-active="active"
                                    data-ui-sref="masterData.masterData"
                                    data-ng-click="masterCtrl.sidebarStat($event)">店舗ATMマスタデータ管理</a></li>
                            </c:if>
                            <c:if test="${sessionScope.StoreAtmDataFile == '0'}">
                            <c:if test="${sessionScope.loginID != 'admin'}">
                                <li><a style="color: #cacaca; cursor: not-allowed;">店舗ATMマスタデータ管理</a></li>
                            </c:if>
							<c:if test="${sessionScope.ImageFile != '0'}"> 
							<c:if test="${sessionScope.loginID == 'admin'}">
                                        <li><a data-ui-sref-active="active"
                                            data-ui-sref="imageFile.fileList"
                                            data-ng-click="masterCtrl.sidebarStat($event)">画像＆URL管理</a></li>
                                </c:if> 
                                <c:if test="${sessionScope.ImageFile == '0'}"> 
                                 <c:if test="${sessionScope.loginID != 'admin'}">
                                        <li><a style="color:#cacaca;cursor: not-allowed;">画像＆URL管理</a></li>
                                </c:if> 
						</ul>
					</li> --%>
					<%-- </c:if> --%>
				</ul>
			</div>
		</aside>
		<section id="content" class="page-view" data-ui-view></section>
	</section>
	<footer id="footer" data-ng-include="'template/footer.html'"></footer>
</body>
</html>