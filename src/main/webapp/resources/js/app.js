var insightApp = angular
		.module(
				'insightApp',
				[ 'ngAnimate', 'ngResource', 'ui.router', 'ui.bootstrap',
						'angular-loading-bar', 'oc.lazyLoad', 'nouislider',
						'ngTable' ])
		.run(function($rootScope, $urlRouter) {
		})
		.config(
				function($stateProvider, $urlRouterProvider,$httpProvider) {

					$urlRouterProvider.otherwise("/welcome");
					$httpProvider.interceptors.push("httpInterceptor");
					$stateProvider
							.state('welcome', {
								url : '/welcome',
								templateUrl : './../view/welcomePage',
							})
							//ユーザ一覧
							.state(
									'user',
									{
										abstract : true,
										url : '/user',
										template : '<div class="level-menu-1" ui-view></div>',
									})
							.state(
									'user.userList',
									{
										url : '/userList',
										templateUrl : './../view/userList',
										controller : "userListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									})

							.state(
									'authority',
									{
										abstract : true,
										url : '/authority',
										template : '<div class="level-menu-1" ui-view></div>',
									})
							.state(
									'authority.authorityList',
									{
										url : '/authorityList',
										templateUrl : './../view/authorityList',
										controller : "authorityListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									})
									
							.state(
									'master',
									{
										abstract : true,
										url : '/master',
										template : '<div class="level-menu-1" ui-view></div>',
									})
							.state(
									'master.storeATMList',
									{
										url : '/storeATMList',
										templateUrl : './../view/storeATMList',
										controller : "storeATMListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									})
									
							.state(
									'master.iYoStoreATMList',
									{
										url : '/iYoStoreATMList',
										templateUrl : './../view/iYoStoreATMList',
										controller : "storeATMListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									})		
									
							.state(
									'master.114StoreATMList',
									{
										url : '/114StoreATMList',
										templateUrl : './../view/114StoreATMList',
										controller : "storeATMListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									})		
									
							.state(
									'account',
									{
										abstract : true,
										url : '/account',
										template : '<div class="level-menu-1" ui-view></div>',
									})
									
							.state(
									'account.accountYamaGataAppList',
									{
										url : '/accountYamaGataAppList',
										templateUrl : './../view/accountYamaGataAppList',
										controller : "accountAppListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})		
							
							.state(
									'account.accountAppList',
									{
										url : '/accountAppList',
										templateUrl : './../view/accountAppList',
										controller : "accountAppListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'account.account114AppList',
									{
										url : '/account114AppList',
										templateUrl : './../view/account114AppList',
										controller : "accountAppListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'account.accountDocumentList',
									{
										url : '/accountDocumentList',
										templateUrl : './../view/accountDocumentList',
										controller : "accountDocumentListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'password',
									{
										abstract : true,
										url : '/password',
										template : '<div class="level-menu-1" ui-view></div>',
									})
							
							.state(
									'password.passwordUpd',
									{
										url : '/passwordUpd',
										templateUrl : './../view/passwordUpd',
										controller : "passwordUpdCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'push.pushRecordAppList',
									{
										url : '/pushRecordAppList',
										templateUrl : './../view/pushRecordAppList',
										controller : "pushRecordAppListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							
							.state(
									'account.pushRecordAppYamaGataList',
									{
										url : '/pushRecordAppYamaGataList',
										templateUrl : './../view/pushRecordAppYamaGataList',
										controller : "pushRecordAppListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'account.pushRecordApp114List',
									{
										url : '/pushRecordApp114List',
										templateUrl : './../view/pushRecordApp114List',
										controller : "pushRecordAppListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'account.documentRecordList',
									{
										url : '/documentRecordList',
										templateUrl : './../view/documentRecordList',
										controller : "documentRecordListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							.state(
									'file',
									{
										abstract : true,
										url : '/file',
										template : '<div class="level-menu-1" ui-view></div>',
									})

							.state('file.file', {
								url : '/file',
								templateUrl : './../view/file',
								controller : "fileUploadCtrl",
								resolve : {
									loadPlugin : function($ocLazyLoad) {
										return $ocLazyLoad
												.load([
														{
															name : 'css',
															insertBefore : '#app-css-level',
															files : [
																	'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																	'../resources/vendors/farbtastic/farbtastic.css',
																	'../resources/vendors/bower_components/summernote/dist/summernote.css',
																	'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																	'../resources/vendors/bower_components/chosen/chosen.min.css' ]
														},
														{
															name : 'vendors',
															insertBefore : '#app-js-vendors-level',
															files : [
																	'../resources/vendors/input-mask/input-mask.min.js',
																	'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																	'../resources/vendors/bower_components/moment/min/moment.min.js',
																	'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																	'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																	'../resources/vendors/fileinput/fileinput.min.js',
																	'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																	'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																	'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
														} ])
									}
								}
							})
							
							.state(
									'password.passwordUpdate',
									{
										url : '/passwordUpdate',
										templateUrl : './../view/passwordUpdate',
										controller : "passwordUpdCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})
							
							//							.state(
//									'batch',
//									{
//										abstract : true,
//										url : '/batch',
//										template : '<div class="level-menu-1" ui-view></div>',
//									})
//
//							.state('batch.batch', {
//								url : '/batch',
//								templateUrl : './../view/batch',
//								controller : "batchListCtrl",
//							})
							.state(
									'application',
									{
										abstract : true,
										url : '/application',
										template : '<div class="level-menu-1" ui-view></div>',
									})


							.state('application.application', {
								url : '/application',
								templateUrl : './../view/application',
								controller : "applicationCtrl",
							})
							
							.state(
									'type',
									{
										abstract : true,
										url : '/type',
										template : '<div class="level-menu-1" ui-view></div>',
									})

							.state('type.type', {
								url : '/type',
								templateUrl : './../view/type',
								controller : "typeCtrl",
							})
							
							.state(
									'contents',
									{
										abstract : true,
										url : '/contents',
										template : '<div class="level-menu-1" ui-view></div>',
									})

							.state('contents.contents', {
								url : '/contents',
								templateUrl : './../view/contents',
								controller : "contentsCtrl",
							})


							.state(
									'masterData',
									{
										abstract : true,
										url : '/masterData',
										template : '<div class="level-menu-1" ui-view></div>',
									})

							.state('masterData.masterData', {
								url : '/masterData',
								templateUrl : './../view/masterData',
								controller : "masterDataCtrl",
							})
							
							.state(
									'useUser',
									{
										abstract : true,
										url : '/useUser',
										template : '<div class="level-menu-1" ui-view></div>',
									})
									
							.state(
									'useUser.useUserList',
									{
										url : '/useUserList',
										templateUrl : './../view/useUserList',
										controller : "useUserListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							
							.state(
									'useUser.useUser114List',
									{
										url : '/useUser114List',
										templateUrl : './../view/useUser114List',
										controller : "useUserListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							
							.state(
									'useUser.useUserDownLoad',
									{
										url : '/useUserDownLoad',
										templateUrl : './../view/useUserDownLoad',
										controller : "useUserDownLoadCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							
					        .state(
									'message',
									{
										abstract : true,
										url : '/message',
										template : '<div class="level-menu-1" ui-view></div>',
									})
									
							.state(
									'message.pushMessage',
									{
										url : '/pushMessage',
										templateUrl : './../view/pushMessage',
										controller : "pushMessageCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							.state(
									'message.iyoPushMessage',
									{
										url : '/iyoPushMessage',
										templateUrl : './../view/iyoPushMessage',
										controller : "pushMessageCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							.state(
									'message.iyoPushUserList',
									{
										url : '/iyoPushUserList',
										templateUrl : './../view/iyoPushUserList',
										controller : "useUserListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							.state(
									'generalPurpose',
									{
										abstract : true,
										url : '/generalPurpose',
										template : '<div class="level-menu-1" ui-view></div>',
									})
									
							.state(
									'generalPurpose.generalPurpose',
									{
										url : '/generalPurpose',
										templateUrl : './../view/generalPurpose',
										controller : "generalPurposeCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							
							.state(
									'account.accountLoanList',
									{
										url : '/accountLoanList',
										templateUrl : './../view/accountLoanList',
										controller : "accountLoanListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							.state(
									'account.pushRecordLoanList',
									{
										url : '/pushRecordLoanList',
										templateUrl : './../view/pushRecordLoanList',
										controller : "pushRecordLoanListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
							})	
							
					        .state(
									'imageFile',
									{
										abstract : true,
										url : '/imageFile',
										template : '<div class="level-menu-1" ui-view></div>',
									})		
							


							.state(
									'imageFile.fileList',
									{
										url : '/fileList',
										templateUrl : './../view/fileList',
										controller : "fileListCtrl",
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-css-level',
																	files : [
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.css',
																			'../resources/vendors/farbtastic/farbtastic.css',
																			'../resources/vendors/bower_components/summernote/dist/summernote.css',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'../resources/vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-js-vendors-level',
																	files : [
																			'../resources/vendors/input-mask/input-mask.min.js',
																			'../resources/vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'../resources/vendors/bower_components/moment/min/moment.min.js',
																			'../resources/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'../resources/vendors/bower_components/summernote/dist/summernote.min.js',
																			'../resources/vendors/fileinput/fileinput.min.js',
																			'../resources/vendors/bower_components/chosen/chosen.jquery.min.js',
																			'../resources/vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'../resources/vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									});
					
				});

insightApp.factory("httpInterceptor", function($q) {
	return {
		response: function(response) {
			if (response.config.method == 'POST'  && typeof response.data == "string") {
				location.href="./../page/timeout";
			}
			return response || $q.when(response);
		},
		responseError : function(rejection) {
			return $q.reject(rejection);
		}
	};
});
