<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://scsk.org/tags" prefix="t"%>
<!--  -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html data-ng-app="insightApp">
<head>
<style>
.box {
	width: 952px;
	height: 144px;
	margin: 0 auto;
	text-align: center;
	color: rgba(0, 0, 0, .15) x-offset: 5* cos(180°- 145°)= 4.09pxy-offset:
		 5* sin(180°- 145°)= 2.87pxspread-radius: 10* 6%= 0.6pxblur-radius: 10-
		
		
		
		
		 0.6= 9.4px;
	box-shadow: 4.09px 2.87px 9.4px 0.6px rgba(0, 0, 0, .15);
	text-shadow: 4.09px 2.87px 9.4px rgba(0, 0, 0, .15);
}

a:hover {
	text-decoration: none;
}

.btn {
	margin-top: 10px;
	display: block;
	width: 169px;
	margin-left: 30px;
	padding: 12px 20px;
	border-radius: 4px;
	background-color: #63b7ff;
	color: #fff;
	cursor: pointer;
}

.btn:hover {
	background-color: #99c6ff;
}

.btn {
	font-style: normal;
}

.bgbtn02 img {
	margin-bottom: -3px;
	margin-right: 10px;
}

.btn1 {
	margin-top: 10px;
	display: block;
	width: 169px;
	margin-left: 30px;
	padding: 12px 20px;
	border-radius: 4px;
	background-color: #4caf50;
	color: #fff;
	cursor: pointer;
}

.btn1:hover {
	background-color: #70bf73;
}

.btn1 {
	font-style: normal;
}

.bgbtn01 img {
	margin-bottom: -3px;
	margin-right: 10px;
}
</style>
</head>

<body>
	<div class="container">
		<div class="box">
			<p style="padding-top: 20px; text-align: left; margin-left: 30px;">パスワード管理</p>
			<p class="Title"></p>
			<c:if test="${sessionScope.loginID != 'admin'}">
				<a data-ui-sref="password.passwordUpdate" class="bgbtn02 btn"
					style="width: 210px; height: 49px;"> <img
					src="../img/Password.png">パスワード管理
				</a>
			</c:if>
			<c:if test="${sessionScope.loginID == 'admin'}">
				<span class="bgbtn02 btn1"
					style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
					<img src="../img/Password.png">パスワード管理
				</span>
			</c:if>
		</div>

		<div class="box">
			<p style="padding-top: 20px; text-align: left; margin-left: 30px;">アカウント管理</p>
			<p class="Title"></p>
			<c:if test="${sessionScope.User != '0'}">
				<a data-ui-sref="user.userList" class="bgbtn02 btn"
					style="width: 210px; height: 49px;"> <img
					src="../img/User_-list.png">アカウント一覧
				</a>
			</c:if>
			<c:if test="${sessionScope.User == '0'}">
				<span class="bgbtn02 btn1"
					style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
					<img src="../img/User_-list.png">アカウント一覧
				</span>
			</c:if>
		</div>

		<div class="box">
			<p style="padding-top: 20px; text-align: left; margin-left: 30px;">権限管理</p>
			<p class="Title"></p>
			<c:if test="${sessionScope.Authority != '0'}">
				<a data-ui-sref="authority.authorityList" class="bgbtn02 btn"
					style="width: 210px; height: 49px;"> <img
					src="../img/Authorization.png">権限一覧
				</a>
			</c:if>
			<c:if test="${sessionScope.Authority == '0'}">
				<span class="bgbtn01 btn1"
					style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
					<img src="../img/Authorization.png">権限一覧
				</span>
			</c:if>
		</div>

		<c:if test="${sessionScope.bank_cd == '0169'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">マスタ管理</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.Store != '0'}">
					<a data-ui-sref="master.storeATMList" class="bgbtn01 btn1"
						style="width: 210px;"> <img src="../img/Shop_ATM.png">店舗ATM一覧
					</a>
				</c:if>
				<c:if test="${sessionScope.Store == '0'}">
					<span class="bgbtn01 btn1"
						style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
						<img src="../img/Shop_ATM.png">店舗ATM一覧
					</span>
				</c:if>
			</div>
		</c:if>

		<c:if test="${sessionScope.bank_cd == '0174'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">マスタ管理</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.Store != '0'}">
					<a data-ui-sref="master.iYoStoreATMList" class="bgbtn01 btn1"
						style="width: 210px;"> <img src="../img/Shop_ATM.png">店舗ATM一覧
					</a>
				</c:if>
				<c:if test="${sessionScope.Store == '0'}">
					<span class="bgbtn01 btn1"
						style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
						<img src="../img/Shop_ATM.png">店舗ATM一覧
					</span>
				</c:if>
			</div>
		</c:if>

		<c:if test="${sessionScope.bank_cd == '0173'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">マスタ管理</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.Store != '0'}">

					<a data-ui-sref="master.114StoreATMList" class="bgbtn02 btn"
						style="width: 210px;"> <img src="../img/Shop_ATM.png">店舗ATM一覧
					</a>
				</c:if>
				<c:if test="${sessionScope.Store == '0'}">
					<span class="bgbtn01 btn1"
						style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
						<img src="../img/Shop_ATM.png">店舗ATM一覧
					</span>
				</c:if>
			</div>
		</c:if>
		<c:if
			test="${sessionScope.bank_cd == '0122' || sessionScope.bank_cd== '0169' || sessionScope.bank_cd== '0173'}">
			<div class="box" style="height: 200px;">

				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">口座開設</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.bank_cd == '0122'}">
					<c:if test="${sessionScope.Account != '0'}">
						<div>
							<a data-ui-sref="account.accountYamaGataAppList"
								class="bgbtn01 btn1" style="width: 220px; height: 49px;"> <img
								src="../img/Application-.png">申込一覧
							</a>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0169'}">
					<c:if test="${sessionScope.Account != '0'}">
						<div>
							<a data-ui-sref="account.accountAppList" class="bgbtn01 btn1"
								style="width: 210px; height: 49px;"> <img
								src="../img/Application-.png">申込一覧
							</a>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.Account == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
							<img src="../img/Application-.png">申込一覧
						</span>
					</div>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0122'}">
					<c:if test="${sessionScope.Push != '0'}">
						<div
							style="margin-left: 250px; margin-top: -58px; height: 49px; width: 210px;">
							<a data-ui-sref="account.pushRecordAppYamaGataList"
								class="bgbtn01 btn1" style="width: 210px;"> <img
								src="../img/push.png">Push通知承認
							</a>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0169'}">
					<c:if test="${sessionScope.Push != '0'}">
						<div
							style="margin-left: 250px; margin-top: -58px; height: 49px; width: 210px;">
							<a data-ui-sref="account.pushRecordAppList" class="bgbtn01 btn1"
								style="width: 210px;"> <img src="../img/push.png">Push通知承認
							</a>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.Push == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/push.png">Push通知承認
						</span>
					</div>
				</c:if>

				<c:if test="${sessionScope.bank_cd == '0173'}">
					<c:if test="${sessionScope.Account != '0'}">
						<div>
							<a data-ui-sref="account.account114AppList" class="bgbtn02 btn"
								style="width: 210px; height: 49px;"> <img
								src="../img/Application-.png">口座申込一覧
							</a>
						</div>
					</c:if>

					<c:if test="${sessionScope.Account == '0'}">
						<div>
							<span class="bgbtn01 btn1"
								style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
								<img src="../img/Application-.png">口座申込一覧
							</span>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0173'}">
					<c:if test="${sessionScope.Loan != '0'}">
						<div
							style="margin-left: 250px; margin-top: -58px; height: 49px; width: 210px;">
							<a data-ui-sref="account.accountLoanList" class="bgbtn02 btn"
								style="width: 210px; height: 49px;"> <img
								src="../img/list-of-loans.png">ローン申込一覧
							</a>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.Loan == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
							<img src="../img/list-of-loans.png">ローン申込一覧
						</span>
					</div>
				</c:if>


				<c:if test="${sessionScope.bank_cd == '0173'}">
					<c:if test="${sessionScope.Document != '0'}">
						<div
							style="margin-left: 500px; margin-top: -58px; height: 49px; width: 220px;">
							<a data-ui-sref="account.accountDocumentList"
								class="bgbtn02 btn" style="width: 210px; height: 49px;"> <img
								src="../img/person_list.png">各種書類申込一覧
							</a>
						</div>
					</c:if>

					<c:if test="${sessionScope.Document == '0'}">
						<div>
							<span class="bgbtn01 btn1"
								style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
								<img src="../img/person_list.png">各種書類申込一覧
							</span>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0173'}">
					<c:if test="${sessionScope.Push != '0'}">
						<div>
							<a data-ui-sref="account.pushRecordApp114List" class="bgbtn02 btn"
								style="width: 210px;"> <img src="../img/push.png">口座申込承認
							</a>
						</div>
					</c:if>

					<c:if test="${sessionScope.Push == '0'}">
						<div>
							<span class="bgbtn01 btn1"
								style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
								<img src="../img/push.png">口座申込承認
							</span>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0173'}">
					<c:if test="${sessionScope.LoanPush != '0'}">
						<div
							style="margin-left: 250px; margin-top: -58px; height: 49px; width: 220px;">
							<a data-ui-sref="account.pushRecordLoanList"
								class="bgbtn02 btn" style="width: 210px;"> <img
								src="../img/loan-approval.png">ローン申込承認
							</a>
						</div>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.LoanPush == '0'}">
					<div
						style="margin-left: 250px; margin-top: -58px; height: 49px; width: 220px;">
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/loan-approval.png">ローン申込承認
						</span>
					</div>
				</c:if>
				<c:if test="${sessionScope.bank_cd == '0173'}">
					<c:if test="${sessionScope.DocumentPush != '0'}">
						<div
							style="margin-left: 500px; margin-top: -58px; height: 49px; width: 220px;">
							<a data-ui-sref="account.documentRecordList" class="bgbtn02 btn"
								style="width: 210px;"> <img src="../img/file.png">各種書類申込承認
							</a>
						</div>
					</c:if>

					<c:if test="${sessionScope.DocumentPush == '0'}">
						<div
							style="margin-left: 500px; margin-top: -58px; height: 49px; width: 220px;">
							<span class="bgbtn01 btn1"
								style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
								<img src="../img/file.png">各種書類申込承認
							</span>
						</div>
					</c:if>
				</c:if>
			</div>
		</c:if>
		<%-- <c:if test="${sessionScope.loginID == 'admin'}"> --%>
		<%-- <div class="box">
			<p style="padding-top: 20px; text-align: left; margin-left: 30px;">ファイル管理</p>
			<p class="Title"></p>
			<c:if test="${sessionScope.HtmlPdfFile != '0'}">
			<c:if test="${sessionScope.loginID == 'admin'}">
					<div>
						<a data-ui-sref="file.file" class="bgbtn02 btn" style="width:190px;"> <img
								src="../img/pdf.png" >HTMLとPDFファイル
						</a>
					</div>
				</c:if> 
				<c:if test="${sessionScope.HtmlPdfFile == '0'}">
				<c:if test="${sessionScope.loginID != 'admin'}">
					<div>
						<span  class="bgbtn01 btn1" style="background-color:#cacaca;cursor: not-allowed;width:190px;"> <img
							src="../img/pdf.png">HTMLとPDFファイル
						</span>
					</div>
				</c:if>
				<c:if test="${sessionScope.StoreAtmDataFile != '0'}">
				<c:if test="${sessionScope.loginID == 'admin'}">
					<div>
						<a data-ui-sref="masterData.masterData" class="bgbtn02 btn" style="margin-left: 260px;margin-top: -48px;width:190px;"> <img
								src="../img/atm.png" >店舗ATMファイル
						</a>
					</div>
				</c:if> 
				<c:if test="${sessionScope.StoreAtmDataFile == '0'}">
				<c:if test="${sessionScope.loginID != 'admin'}">
					<div>
						<span  class="bgbtn01 btn1" style="background-color:#cacaca;cursor: not-allowed;margin-left: 260px;margin-top: -48px;width:190px;"> <img
							src="../img/atm.png">店舗ATMファイル
						</span>
					</div>
				</c:if>
				<c:if test="${sessionScope.ImageFile != '0'}"> 
				<c:if test="${sessionScope.loginID == 'admin'}">
					<div>
						<a data-ui-sref="imageFile.fileList" class="bgbtn02 btn" style="margin-left: 480px;margin-top: -48px;width:190px;"> <img
								src="../img/img.png" >画像＆URLファイル
						</a>
					</div>
				</c:if> 
				<c:if test="${sessionScope.ImageFile == '0'}"> 
				<c:if test="${sessionScope.loginID != 'admin'}">
					<div>
						<span  class="bgbtn01 btn1" style="background-color:#cacaca;cursor: not-allowed;margin-left: 480px;margin-top: -48px;width:190px;"> <img
							src="../img/img.png">画像＆URLファイル
						</span>
					</div>
				</c:if> 
		</div> --%>
		<%-- </c:if> --%>
		<div class="box">
			<p style="padding-top: 20px; text-align: left; margin-left: 30px;">コンテンツ管理</p>
			<p class="Title"></p>
			<c:if test="${sessionScope.Application != '0'}">
				<div>
					<a data-ui-sref="application.application" class="bgbtn02 btn"
						style="width: 210px; height: 49px;"> <img src="../img/app.png">アプリケーション管理
					</a>
				</div>
			</c:if>
			<c:if test="${sessionScope.Application == '0'}">
				<div>
					<span class="bgbtn01 btn1"
						style="background-color: #cacaca; cursor: not-allowed; width: 220px; height: 49px;">
						<img src="../img/app.png">アプリケーション管理
					</span>
				</div>
			</c:if>
			<c:if test="${sessionScope.Type != '0'}">
				<div style="margin-left: 250px; margin-top: -58px; width: 220px;">
					<a data-ui-sref="type.type" class="bgbtn02 btn"
						style="width: 210px;"> <img src="../img/type.png">コンテンツ種別
					</a>
				</div>
			</c:if>
			<c:if test="${sessionScope.Type == '0'}">
				<div>
					<span class="bgbtn01 btn1"
						style="background-color: #cacaca; cursor: not-allowed; margin-left: 250px; margin-top: -48px; width: 220px;">
						<img src="../img/type.png">コンテンツ種別
					</span>
				</div>
			</c:if>
			<c:if test="${sessionScope.Contents != '0'}">
				<div style="margin-left: 500px; margin-top: -58px; width: 220px;">
					<a data-ui-sref="contents.contents" class="bgbtn02 btn"
						style="width: 210px;"> <img src="../img/contents.png">コンテンツ管理
					</a>
				</div>
			</c:if>
			<c:if test="${sessionScope.Contents == '0'}">
				<div>
					<span class="bgbtn01 btn1"
						style="background-color: #cacaca; cursor: not-allowed; margin-left: 520px; margin-top: -48px; width: 220px;">
						<img src="../img/contents.png">コンテンツ管理
					</span>
				</div>
			</c:if>
		</div>

		<c:if test="${sessionScope.bank_cd == '0174'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">利用ユーザー管理</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.UseUser != '0'}">
					<div>
						<a data-ui-sref="useUser.useUserList" class="bgbtn02 btn"
							style="width: 210px; height: 49px;"> <img
							src="../img/person.png">利用ユーザー一覧
						</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.UseUser == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/person.png">利用ユーザー一覧
						</span>
					</div>
				</c:if>

<%-- 				<c:if test="${sessionScope.UseUserDownLoad != '0'}">
					<div>
						<a data-ui-sref="useUser.useUserDownLoad" class="bgbtn02 btn"
							style="margin-left: 280px; margin-top: -48px; width: 210px; height: 49px;">
							<img src="../img/large.png">ﾏｲﾃｰﾏ利用ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ
						</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.UseUserDownLoad == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/large.png">ﾏｲﾃｰﾏ利用ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ
						</span>
					</div>
				</c:if> --%>
			</div>
		</c:if>

		<c:if test="${sessionScope.bank_cd == '0173'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">利用ユーザー管理</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.UseUser != '0'}">
					<div>
						<a data-ui-sref="useUser.useUser114List" class="bgbtn02 btn"
							style="width: 210px; height: 49px;"> <img
							src="../img/person.png">利用ユーザー一覧
						</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.UseUser == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/person.png">利用ユーザー一覧
						</span>
					</div>
				</c:if>


			</div>
		</c:if>

		<c:if test="${sessionScope.bank_cd == '0174' || sessionScope.bank_cd== '0173'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">任意Push配信管理</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.Message != '0'}">
					<div>
						<a data-ui-sref="message.iyoPushUserList" class="bgbtn02 btn"
							style="width: 210px;height:49px;"> <img src="../img/Mass.png">任意Push配信登録
						</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.Message == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/Mass.png">任意Push配信登録
						</span>
					</div>
				</c:if>
				
				<c:if test="${sessionScope.MessageList != '0'}">
					<div>
						<a data-ui-sref="message.iyoPushMessage" class="bgbtn02 btn"
							style="margin-left: 280px;margin-top: -48px; width: 210px;height:49px;"> <img src="../img/edit.png">任意Push配信履歴
						</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.MessageList == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/edit.png">任意Push配信履歴
						</span>
					</div>
				</c:if>
			</div>
		</c:if>

<%-- 		<c:if test="${sessionScope.bank_cd == '0173'}">
			<div class="box">
				<p style="padding-top: 20px; text-align: left; margin-left: 30px;">その他</p>
				<p class="Title"></p>
				<c:if test="${sessionScope.GeneralPurpose != '0'}">
					<div>
						<a data-ui-sref="generalPurpose.generalPurpose"
							class="bgbtn02 btn"
							style="width: 210px;height:49px;">
							<img src="../img/download_currency.png">汎用DB＆ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ
						</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.GeneralPurpose == '0'}">
					<div>
						<span class="bgbtn01 btn1"
							style="background-color: #cacaca; cursor: not-allowed; width: 220px;">
							<img src="../img/download_currency.png">汎用DB＆ﾃﾞｰﾀﾀﾞｳﾝﾛｰﾄﾞ
						</span>
					</div>
				</c:if>

			</div>
		</c:if> --%>

	</div>
</body>
</html>