<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header id="header" data-current-skin={{masterCtrl.currentSkin}} data-ng-include="'template/header.html'" data-ng-controller="headerCtrl"></header>
<script src="<c:url value='/resources/js/controllers/public/template/header-ctrl.js' />"></script>
