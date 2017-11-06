<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://scsk.org/tags" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザ一覧</title>
</head>
<body>
<div class="row-fluid">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th scope="col"><spring:message code="UserName"/></th>
                    <th scope="col"><spring:message code="Password"/></th>
                    <th scope="col"><spring:message code="Role"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${customers}" var="customer">
                <tr>
                    <td class="tdContactsCentered">${customer.name}</td>
                    <td class="tdContactsCentered">${customer.password}</td>
                    <td class="tdContactsCentered">${customer.userRole}</td>
                    <td class="width15">
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
</div>
</body>
</html>