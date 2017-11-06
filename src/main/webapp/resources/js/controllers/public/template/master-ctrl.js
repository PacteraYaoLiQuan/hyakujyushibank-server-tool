insightApp
// =========================================================================
// Base controller for common functions
// =========================================================================
.controller('masterCtrl', function($timeout, $state, $scope) {
	$scope.masterCtrl = {};	
	
	angular.element(document).ready(function() {
		$("#userNameHeader").html($("#userName").val());
	});
	
	// Detact Mobile Browser
	if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
		angular.element('html').addClass('ismobile');
	}

	// By default Sidbars are hidden in boxed layout and in wide layout only the
	// right sidebar is hidden.
	$scope.masterCtrl.sidebarToggle = {
		left : false
	}

	console.log("localStorage.getItem('ma-layout-status')", localStorage.getItem('ma-layout-status'));
	// By default template has a boxed layout
	$scope.masterCtrl.layoutType = localStorage.getItem('ma-layout-status');

	// For Mainmenu Active Class
	$scope.masterCtrl.$state = $state;

	// Close sidebar on click
	$scope.masterCtrl.sidebarStat = function(event) {
		if (!angular.element(event.target).parent().hasClass('active')) {
			$scope.masterCtrl.sidebarToggle.left = false;
		}
	}

	// Listview Search (Check listview pages)
	$scope.masterCtrl.listviewSearchStat = false;

	$scope.masterCtrl.lvSearch = function() {
		$scope.listviewSearchStat = true;
	}

	// Listview menu toggle in small screens
	$scope.masterCtrl.lvMenuStat = false;

	// Blog
	$scope.masterCtrl.wallCommenting = [];

	$scope.masterCtrl.wallImage = false;
	$scope.masterCtrl.wallVideo = false;
	$scope.masterCtrl.wallLink = false;

	// Skin Switch
	$scope.masterCtrl.currentSkin = 'blue';

	$scope.masterCtrl.skinList = [ 'lightblue', 'bluegray', 'cyan', 'teal', 'green',
			'orange', 'blue', 'purple' ]

	$scope.masterCtrl.skinSwitch = function(color) {
		$scope.currentSkin = color;
	}
})