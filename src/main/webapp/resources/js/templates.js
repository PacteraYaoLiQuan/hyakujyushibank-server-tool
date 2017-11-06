angular.module('insightApp').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('template/footer.html',
    "Copyright &copy; 2017 SCSK Corporation<!-- \r" +
    "\n" +
    "<ul class=\"f-menu\">\r" +
    "\n" +
    "    <li><a href=\"\">Home</a></li>\r" +
    "\n" +
    "    <li><a href=\"\">Dashboard</a></li>\r" +
    "\n" +
    "    <li><a href=\"\">Reports</a></li>\r" +
    "\n" +
    "    <li><a href=\"\">Support</a></li>\r" +
    "\n" +
    "    <li><a href=\"\">Contact</a></li>\r" +
    "\n" +
    "</ul>\r" +
    "\n" +
    " -->"
  );


  $templateCache.put('template/header.html',
    "<ul class=\"header-inner clearfix\"><li id=\"menu-trigger\" data-target=\"mainmenu\" data-toggle-sidebar data-model-left=\"masterCtrl.sidebarToggle.left\" data-ng-class=\"{ 'open': masterCtrl.sidebarToggle.left === true }\"><div class=\"line-wrap\"><div class=\"line top\"></div><div class=\"line center\"></div><div class=\"line bottom\"></div></div></li><li class=\"logo hidden-xs\"><a data-ui-sref=\"home\" data-ng-click=\"masterCtrl.sidebarStat($event)\"><spring:message code=\"project.name\"></a></li><li class=\"pull-right\"><ul class=\"top-menu\"><li id=\"toggle-width\"><div class=\"toggle-switch\"><input id=\"tw-switch\" type=\"checkbox\" hidden data-change-layout=\"masterCtrl.layoutType\"><label for=\"tw-switch\" class=\"ts-helper\"></label></div></li></ul></li></ul>"
  );


  $templateCache.put('template/sidebar-left.html',
    "<div class=\"sidebar-inner c-overflow\"><div class=\"profile-menu\"><div toggle-submenu><div class=\"profile-pic\"><h1 id=\"userNameHeader\"></h1></div><div class=\"profile-info\" style=\"text-align: center\"><ul><li><a href=\"./../logout\"><i class=\"zmdi zmdi-time-restore\"></i> Logout</a></li></ul></div></div></div><ul class=\"main-menu\"><li><a data-ui-sref-active=\"active\" data-ui-sref=\"password.passwordUpdate\" data-ng-click=\"masterCtrl.sidebarStat($event)\">パスワード変更</a></li><!-- 		<li class=\"sub-menu\"\r" +
    "\n" +
    "			data-ng-class=\"{ 'active toggled': masterCtrl.$state.includes('user') }\">\r" +
    "\n" +
    "			<a href=\"\" toggle-submenu><i class=\"zmdi zmdi-home\"></i>ユーザー管理</a>\r" +
    "\n" +
    "\r" +
    "\n" +
    "			<ul>\r" +
    "\n" +
    "				<li><a data-ui-sref-active=\"active\"\r" +
    "\n" +
    "					data-ui-sref=\"user.userList\"\r" +
    "\n" +
    "					data-ng-click=\"masterCtrl.sidebarStat($event)\">ユーザー一覧</a></li>\r" +
    "\n" +
    "\r" +
    "\n" +
    "			</ul> --><!-- 		<li class=\"sub-menu\"\r" +
    "\n" +
    "			data-ng-class=\"{ 'active toggled': masterCtrl.$state.includes('authority') }\">\r" +
    "\n" +
    "			<a href=\"\" toggle-submenu><i class=\"zmdi zmdi-home\"></i> 権限管理</a>\r" +
    "\n" +
    "\r" +
    "\n" +
    "			<ul>\r" +
    "\n" +
    "				<li><a data-ui-sref-active=\"active\"\r" +
    "\n" +
    "					data-ui-sref=\"authority.authorityList\"\r" +
    "\n" +
    "					data-ng-click=\"masterCtrl.sidebarStat($event)\">権限一覧</a></li>\r" +
    "\n" +
    "			</ul>\r" +
    "\n" +
    "		</li> --><!-- 		<li class=\"sub-menu\" data-ng-class=\"{ 'active toggled': masterCtrl.$state.includes('master') }\"><a href=\"\" toggle-submenu><i class=\"zmdi zmdi-home\"></i> マスタ管理</a><ul><li><a data-ui-sref-active=\"active\" data-ui-sref=\"master.storeATMList\" data-ng-click=\"masterCtrl.sidebarStat($event)\">店舗ATM一覧</a></li></ul></li>--><li class=\"sub-menu\" data-ng-class=\"{ 'active toggled': masterCtrl.$state.includes('account') }\"><a href=\"\" toggle-submenu><i class=\"zmdi zmdi-home\"></i> 口座開設</a><ul><li><a data-ui-sref-active=\"active\" data-ui-sref=\"account.accountAppList\" data-ng-click=\"masterCtrl.sidebarStat($event)\">申込一覧</a></li></ul></li></ul></div>"
  );


  $templateCache.put('template/datepicker/day.html',
    "<table role=\"grid\" aria-labelledby=\"{{::uniqueId}}-title\" aria-activedescendant=\"{{activeDateId}}\" class=\"dp-table dpt-day\"><thead><tr class=\"tr-dpnav\"><th><button type=\"button\" class=\"pull-left btn-dp\" ng-click=\"move(-1)\" tabindex=\"-1\"><i class=\"zmdi zmdi-long-arrow-left\"></i></button></th><th colspan=\"{{::5 + showWeeks}}\"><button id=\"{{::uniqueId}}-title\" role=\"heading\" aria-live=\"assertive\" aria-atomic=\"true\" type=\"button\" ng-click=\"toggleMode()\" ng-disabled=\"datepickerMode === maxMode\" tabindex=\"-1\" class=\"w-100 btn-dp\"><div class=\"dp-title\">{{title}}</div></button></th><th><button type=\"button\" class=\"pull-right btn-dp\" ng-click=\"move(1)\" tabindex=\"-1\"><i class=\"zmdi zmdi-long-arrow-right\"></i></button></th></tr><tr class=\"tr-dpday\"><th ng-if=\"showWeeks\" class=\"text-center\"></th><th ng-repeat=\"label in ::labels track by $index\" class=\"text-center\"><small aria-label=\"{{::label.full}}\">{{::label.abbr}}</small></th></tr></thead><tbody><tr ng-repeat=\"row in rows track by $index\"><td ng-if=\"showWeeks\" class=\"text-center h6\"><em>{{ weekNumbers[$index] }}</em></td><td ng-repeat=\"dt in row track by dt.date\" class=\"text-center\" role=\"gridcell\" id=\"{{::dt.uid}}\" ng-class=\"::dt.customClass\"><button type=\"button\" class=\"w-100 btn-dp btn-dpday btn-dpbody\" ng-class=\"{'dp-today': dt.current, 'dp-selected': dt.selected, 'dp-active': isActive(dt)}\" ng-click=\"select(dt.date)\" ng-disabled=\"dt.disabled\" tabindex=\"-1\"><span ng-class=\"::{'dp-day-muted': dt.secondary, 'dp-day-today': dt.current}\">{{::dt.label}}</span></button></td></tr></tbody></table>"
  );


  $templateCache.put('template/datepicker/month.html',
    "<table role=\"grid\" aria-labelledby=\"{{::uniqueId}}-title\" aria-activedescendant=\"{{activeDateId}}\" class=\"dp-table\"><thead><tr class=\"tr-dpnav\"><th><button type=\"button\" class=\"pull-left btn-dp\" ng-click=\"move(-1)\" tabindex=\"-1\"><i class=\"zmdi zmdi-long-arrow-left\"></i></button></th><th><button id=\"{{::uniqueId}}-title\" role=\"heading\" aria-live=\"assertive\" aria-atomic=\"true\" type=\"button\" ng-click=\"toggleMode()\" ng-disabled=\"datepickerMode === maxMode\" tabindex=\"-1\" class=\"w-100 btn-dp\"><div class=\"dp-title\">{{title}}</div></button></th><th><button type=\"button\" class=\"pull-right btn-dp\" ng-click=\"move(1)\" tabindex=\"-1\"><i class=\"zmdi zmdi-long-arrow-right\"></i></button></th></tr></thead><tbody><tr ng-repeat=\"row in rows track by $index\"><td ng-repeat=\"dt in row track by dt.date\" class=\"text-center\" role=\"gridcell\" id=\"{{::dt.uid}}\" ng-class=\"::dt.customClass\"><button type=\"button\" class=\"w-100 btn-dp btn-dpbody\" ng-class=\"{'dp-selected': dt.selected, 'dp-active': isActive(dt)}\" ng-click=\"select(dt.date)\" ng-disabled=\"dt.disabled\" tabindex=\"-1\"><span ng-class=\"::{'dp-day-today': dt.current}\">{{::dt.label}}</span></button></td></tr></tbody></table>"
  );


  $templateCache.put('template/datepicker/popup.html',
    "<ul class=\"dropdown-menu\" ng-keydown=\"keydown($event)\"><li ng-transclude></li><li ng-if=\"showButtonBar\" class=\"dp-actions clearfix\"><button type=\"button\" class=\"btn btn-link\" ng-click=\"select('today')\">{{ getText('current') }}</button> <button type=\"button\" class=\"btn btn-link\" ng-click=\"close()\">{{ getText('close') }}</button></li></ul>"
  );


  $templateCache.put('template/datepicker/year.html',
    "<table role=\"grid\" aria-labelledby=\"{{::uniqueId}}-title\" aria-activedescendant=\"{{activeDateId}}\" class=\"dp-table\"><thead><tr class=\"tr-dpnav\"><th><button type=\"button\" class=\"pull-left btn-dp\" ng-click=\"move(-1)\" tabindex=\"-1\"><i class=\"zmdi zmdi-long-arrow-left\"></i></button></th><th colspan=\"3\"><button id=\"{{::uniqueId}}-title\" role=\"heading\" aria-live=\"assertive\" aria-atomic=\"true\" type=\"button\" class=\"w-100 btn-dp\" ng-click=\"toggleMode()\" ng-disabled=\"datepickerMode === maxMode\" tabindex=\"-1\"><div class=\"dp-title\">{{title}}</div></button></th><th><button type=\"button\" class=\"pull-right btn-dp\" ng-click=\"move(1)\" tabindex=\"-1\"><i class=\"zmdi zmdi-long-arrow-right\"></i></button></th></tr></thead><tbody><tr ng-repeat=\"row in rows track by $index\"><td ng-repeat=\"dt in row track by dt.date\" class=\"text-center\" role=\"gridcell\" id=\"{{::dt.uid}}\"><button type=\"button\" class=\"w-100 btn-dp btn-dpbody\" ng-class=\"{'dp-selected': dt.selected, 'dp-active': isActive(dt)}\" ng-click=\"select(dt.date)\" ng-disabled=\"dt.disabled\" tabindex=\"-1\"><span ng-class=\"::{'dp-day-today': dt.current}\">{{::dt.label}}</span></button></td></tr></tbody></table>"
  );


  $templateCache.put('template/tables/header.html',
    "<ng-table-filter-row></ng-table-filter-row><ng-table-sorter-row></ng-table-sorter-row>"
  );


  $templateCache.put('template/tables/pagination.html',
    "<div class=\"ng-cloak ng-table-pager\" ng-if=\"params.data.length\"><div class=\"row\"><div class=\"col-sm-7\"><div ng-if=\"params.settings().counts.length\" class=\"ng-table-counts btn-group\"><button ng-repeat=\"count in params.settings().counts\" type=\"button\" ng-class=\"{'active':params.count()==count}\" ng-click=\"params.count(count)\" class=\"btn btn-default\"><span ng-bind=\"count\"></span></button></div><ul ng-if=\"pages.length\" class=\"pagination ng-table-pagination\"><li ng-class=\"{'disabled': !page.active && !page.current, 'active': page.current}\" ng-repeat=\"page in pages\" ng-switch=\"page.type\"><a ng-switch-when=\"prev\" ng-click=\"params.page(page.number)\" href=\"\">&laquo;</a> <a ng-switch-when=\"first\" ng-click=\"params.page(page.number)\" href=\"\"><span ng-bind=\"page.number\"></span></a> <a ng-switch-when=\"page\" ng-click=\"params.page(page.number)\" href=\"\"><span ng-bind=\"page.number\"></span></a> <a ng-switch-when=\"more\" ng-click=\"params.page(page.number)\" href=\"\">&#8230;</a> <a ng-switch-when=\"last\" ng-click=\"params.page(page.number)\" href=\"\"><span ng-bind=\"page.number\"></span></a> <a ng-switch-when=\"next\" ng-click=\"params.page(page.number)\" href=\"\">&raquo;</a></li></ul></div><div class=\"col-sm-5 text-center btn-colors zdy\" pagination-buttons=\"params.settings().paginationCustomizeButtons\"></div></div></div>"
  );


  $templateCache.put('ng-table/filters/button.html',
    "<button class=\"btn bgm-gray\" ng-click=\"\">リセット</button>"
  );


  $templateCache.put('ng-table/filters/checkbox.html',
    "<label class=\"checkbox checkbox-inline m-r-20\"><input type=\"checkbox\" value=\"\" ng-click=\"\"><i class=\"input-helper\"></i></label>"
  );


  $templateCache.put('ng-table/filters/select-clear.html',
    "<div class=\"input-group\"><select ng-options=\"data.id as data.title for data in $selectData\" ng-table-select-filter-ds=\"$column\" ng-disabled=\"$filterRow.disabled\" ng-model=\"params.filter()[name]\" class=\"filter filter-select form-control\" name=\"{{name}}\"><option style=\"display: none\" value=\"\"></option></select><span ng-click=\"params.filter()[name] = ''\" class=\"input-group-addon last\"><i class=\"zmdi zmdi-close-circle\"></i></span></div>"
  );


  $templateCache.put('ng-table/filters/text-claer-2.html',
    "<div class=\"input-group\"><input type=\"text\" name=\"{{name}}\" ng-disabled=\"$filterRow.disabled\" ng-model=\"params.filter()[name]\" class=\"input-filter form-control\"><span ng-click=\"params.filter()[name] = ''\" class=\"input-group-addon last\"><i class=\"zmdi zmdi-close-circle\"></i></span></div>"
  );


  $templateCache.put('ng-table/filters/text-claer.html',
    "<div class=\"input-group w-100\"><input type=\"text\" name=\"{{name}}\" ng-disabled=\"$filterRow.disabled\" ng-model=\"params.filter()[name]\" class=\"input-filter form-control p-r-25 clear-ie-icon-x\"><span ng-click=\"params.filter()[name] = ''\" class=\"p-absolute input-group-addon last filter-clear\"><i class=\"zmdi zmdi-close\"></i></span></div>"
  );

}]);
