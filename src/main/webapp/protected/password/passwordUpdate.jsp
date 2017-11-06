<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html class="login-content" data-ng-app="insightApp">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
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