<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://scsk.org/functions" prefix="f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
function test(input){
	var x = document.getElementById("input");
	 var y =x.value;
	 alert(y);
	 var message = "${f:js(y)}";
}
</script>
    
<title>XSSテスト画面</title>
</head>
<body>
		<form action="" method="get">
		
		<input type="text" id="input" name="input" value=""/>
		
		<input type="submit" name="submit" value="search" onclick="test(input)"/>
		</form>
</body>
</html>