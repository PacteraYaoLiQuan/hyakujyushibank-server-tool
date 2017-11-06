insightApp
		.controller(
				'passwordUpdCtrl',
				function($scope, $timeout, $uibModal, $q,
						NgTableParams, passwordUpdService, insightService,
						strSpliceService,$location) {
					// controllerオブジェクト変数
					$scope.passwordUpdCtrl = {};
					var data = "";
					var url = "./../protected/user/sessionTimeOut";
					var data = passwordUpdService.dataUpd(url, data);
					$scope.showFlg = 0;
					$scope.passwordUpdCtrl.showDirPwd = function() {
						if($scope.showFlg == 0){
							passwordUpdForm.password.type="text";
							$scope.showFlg = 1;	
						}else{
							passwordUpdForm.password.type="password";
							$scope.showFlg = 0;	
						}
					}
					$scope.showFlg2 = 0;
					$scope.passwordUpdCtrl.showDirPwd2 = function() {
						if($scope.showFlg2 == 0){
							passwordUpdForm.newPassword.type="text";
							$scope.showFlg2 = 1;	
						}else{
							passwordUpdForm.newPassword.type="password";
							$scope.showFlg2 = 0;	
						}
					}
					$scope.showFlg3 = 0;
					$scope.passwordUpdCtrl.showDirPwd3 = function() {
						if($scope.showFlg3 == 0){
							passwordUpdForm.newConfirmPassword.type="text";
							$scope.showFlg3 = 1;	
						}else{
							passwordUpdForm.newConfirmPassword.type="password";
							$scope.showFlg3 = 0;	
						}
					}

					// 変更ボタンを押下時、
					$scope.passwordUpdCtrl.passwordData = function() {
						$scope.passwordUpdCtrl.btnDisabled = true;

						if ($scope.passwordUpdCtrl.password == ""  || $scope.passwordUpdCtrl.password == undefined) {
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1014"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;
						} else if($scope.passwordUpdCtrl.newPassword == ""  || $scope.passwordUpdCtrl.newPassword == undefined){
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1015"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;						
						}else if (isAlphabetNumeric($scope.passwordUpdCtrl.newPassword) == false) {
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1008"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;
						}else if (checkPass($scope.passwordUpdCtrl.newPassword)== false){
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1009"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;
						}else if ($scope.passwordUpdCtrl.newPassword.length < 8) {
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1012"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;
						}else if($scope.passwordUpdCtrl.newConfirmPassword == ""  || $scope.passwordUpdCtrl.newConfirmPassword == undefined){
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1016"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;
						}else if ($scope.passwordUpdCtrl.newPassword !=$scope.passwordUpdCtrl.newConfirmPassword ) {
							swal(insightService.getMessageData("applyStatusMessage")["e.login001.1003"], "", "error");
							$scope.passwordUpdCtrl.btnDisabled = false;
						}else{
							//初期パスワードチェックOKの場合
							var params2 = {
								title : "パスワードを変更しますか？",
								text : "",
								type : "warning",
								showCancelButton : true,
								confirmButtonColor : "#DD6B55",
								confirmButtonText : "確認",
								cancelButtonText : "キャンセル",
								closeOnConfirm : true,
								closeOnCancel : true
							};
							swal(
									params2,
									function(isConfirm) {
										if (isConfirm) {
											//ユーザーデータ取得
											var url = "./../protected/password/passwordUpd";

											var params = {
													"password" : $scope.passwordUpdCtrl.password,
													"newPassword" : $scope.passwordUpdCtrl.newPassword
												};
												var data = passwordUpdService.dataUpd(url, params);
											data
													.then(
															function(r) {
																if (r.data.resultStatus == "NG") {
																	$scope.passwordUpdCtrl.btnDisabled = false;
																	var codeList = strSpliceService.resultCodeSplice(r.data.messages,"code");
																	var params = {
																		title : insightService.getMessageData("applyStatusMessage")[codeList[0]],
																		text : "",
																		type : "error",
																		closeOnConfirm : true
																	};
																	swal(params);
																} else {
																	var params = {
																			title : insightService.getMessageData("applyStatusMessage")["e.login001.1007"],
																			text : "",
																			type : "success",
																			closeOnConfirm : true
																	};
																	swal(params,function(isConfirm) {
																		if (isConfirm) {
																			$scope.passwordUpdCtrl.flg = $("#flg").val();
																			if($scope.passwordUpdCtrl.flg == "1"){
																				console.log($location.host());
																				window.location = "/login";
																			}
																			console.log($location.host());
																			window.location = "/view/master";
																		}});
																	$scope.passwordUpdCtrl.btnDisabled = false;
																}
															}, function(e) {
															});
										} else {
											$scope.passwordUpdCtrl.btnDisabled = false;
										}
									})
						};
					};
					//半角英数
					function isAlphabetNumeric(argValue) {
						if (argValue.match(/[^A-Z|^a-z|^0-9]/g)) {
							return false;
						} else { 
							return true;
						}
					};
					//英数混在
					function checkPass(pass) {
						var ls = 0;
						if (pass.match(/([a-z])+/)) {
							ls++;
						}
						if (pass.match(/([0-9])+/)) {
							ls++;
						}
						if (pass.match(/([A-Z])+/)) {
							ls++;
						}

						if (ls < 2) {
							return false;
						} else {
							return true;
						}
					}
				})