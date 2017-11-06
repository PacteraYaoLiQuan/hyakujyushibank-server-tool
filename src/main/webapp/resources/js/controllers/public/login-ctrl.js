insightApp
// =================================================
// LOGIN
// =================================================
.controller('loginCtrl', function($scope, $location,insightService,
		strSpliceService,loginService) {
	$scope.loginCtrl = {};
	var str = $("#errMessageID").val();
	if (str != "" && str != undefined) {
			$scope.loginCtrl.message = insightService.getMessageData("applyStatusMessage")[str];
			$scope.loginCtrl.userID = $("#userID").val();
			$scope.loginCtrl.password = $("#password").val();
			$scope.loginCtrl.hidUserID = $("#hidUserID").val();
			$scope.loginCtrl.count = $("#count").val();
			$scope.loginCtrl.selectFlg = $("#selectFlg").val();
	}else{
		$scope.loginCtrl.userID = getCookieValue("userID");
		$scope.loginCtrl.password = getCookieValue("password");
		$scope.loginCtrl.selectFlg = false;
		$scope.showFlg = 0;
		$scope.count = 0;
	}
//	 if ($scope.loginCtrl.userID.toLowerCase() == "admin".toLowerCase()) {
//		 $scope.loginCtrl.passwordResetDisabled = true;
//	 } else {
		 $scope.loginCtrl.passwordResetDisabled = false;
//	 }
	 $scope.blur = function () {
//		 if ($scope.loginCtrl.userID.toLowerCase() == "admin".toLowerCase()) {
//			 $scope.loginCtrl.passwordResetDisabled = true;
//		 } else {
			 $scope.loginCtrl.passwordResetDisabled = false;
//		 }
     }
		$scope.loginCtrl.submitLogin = function() {
		var objChk = $scope.loginCtrl.selectFlg;
		if (objChk == true) {
			// 追加cookie
			addCookie("userID", $scope.loginCtrl.userID, 30, "/");
			addCookie("password", $scope.loginCtrl.password, 30, "/");
			getCookieValue();
		} else {
			
		}
		angular.element('form').submit();
	}
				
	$scope.loginCtrl.showDirPwd = function() {
		if($scope.showFlg == 0){
			loginForm.password.type="text";
			$scope.showFlg = 1;	
		}else{
			loginForm.password.type="password";
			$scope.showFlg = 0;
		}
	}

	$scope.loginCtrl.passwordReset = function() {
		if ($scope.loginCtrl.userID == ""
				|| $scope.loginCtrl.userID == null) {
			$scope.loginCtrl.message = "ユーザーIDを入力してください!";
			return;
		} else {
			var url = "./public/passwordResetData";
			var params = {
				"userID" : $scope.loginCtrl.userID,
			};
			var data = loginService.getpasswordResetData(url, params);
			data.then(
					function(r) {
				if (r.data.resultData.userID == null) {
					$scope.loginCtrl.message = insightService.getMessageData("applyStatusMessage")["e.login001.1001"]
				} else {	
					window.location = "./public/passwordReset ";
				}
			});
		}
	}
		function addCookie(userID, password, days, path) {
		var userID = escape(userID);
		var password = escape(password);
		var expires = new Date();
		expires.setTime(expires.getTime() + days * 3600000 * 24);
		path = path == "" ? "" : ";path=" + path;
		var _expires = (typeof days) == "string" ? "" : ";expires="
				+ expires.toUTCString();
		document.cookie = userID + "=" + password + _expires + path;
	}

	function getCookieValue(userID) {
		var userID = escape(userID);
		var allcookies = document.cookie;
		userID += "=";
		var pos = allcookies.indexOf(userID);
		if (pos != -1) {
			var start = pos + userID.length;
			var end = allcookies.indexOf(";", start);
			if (end == -1)
				end = allcookies.length;
			var value = allcookies.substring(start, end);
			return (value);
		} else {
			return "";
		}
	}
})