<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://scsk.org/tags" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"  type="text/css"  href="<%=request.getContextPath()%>/resources/css/message.css"/>
<title>ユーザ編集画面</title>
</head>
<body>
 	<t:messagesPanel />
	<form action="/insightManagement/protected/userok" method="post">
		<table align="center">
			<tr>
				<td>UserName:</td>
				<td><input ${requestScope.readonly}  type="text" size="39"
					name="username" value="${requestScope.username}" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" size="40" name="password"
					value="${requestScope.password}" /></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" size="40"
					value="${requestScope.password}" /></td>
			</tr>
			<tr>
				<td>Role:</td>
				<td><select name="role">
						<option value="0">role_uesr</option>
						<option value="1">role_admin</option>
				</select></td>
			</tr>
			<tr>
				<td style="text-align: center" colspan="2"><input type="submit"
					value="add" name="action"></td>
				<td style="text-align: center" colspan="2"><input type="submit"
					value="upd" name="action"></td>					
			</tr>
		</table>
	</form>
</body>
</html>