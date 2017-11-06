insightApp
		.controller(
				'passwordResetCtrl',
				function($scope, passwordResetService, insightService,
						strSpliceService) {
					$scope.passwordResetCtrl = {};

					$scope.passwordResetCtrl.passwordReset = function() {

						var url = "./../public/passwordReset";
						var data = {
							"userID" : $scope.passwordResetCtrl.userID,
						};
						var data = passwordResetService.dataReset(url, data);
						data
								.then(
										function(r) {
											if (r.data.resultData.userID == null) {
												var params = {
													title : insightService
															.getMessageData("applyStatusMessage")["e.login001.1017"],
													text : "",
													type : "error",
													confirmButtonColor : "#DD6B55",
													confirmButtonText : "確認",
													closeOnConfirm : true,
												};
												swal(
														params,
														function(isConfirm) {
															if (isConfirm) {
																window.location = "../login";
															}
														});
											} else {
												var params2 = {
													title : insightService
													.getMessageData("applyStatusMessage")["e.login001.1006"],
													text : "",
													type : "warning",
													confirmButtonColor : "#DD6B55",
													confirmButtonText : "確認",
													closeOnConfirm : true,
												};
												swal(
														params2,
														function(isConfirm) {
															if (isConfirm) {
																window.location = "../login";
															} 
														});
											}
										}, function(e) {
											console.log(e);
										});
					}
				})
