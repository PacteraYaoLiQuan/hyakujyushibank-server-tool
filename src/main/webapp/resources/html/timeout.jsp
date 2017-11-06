<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>      
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>セッションタイムアウト</title>
<style>
 .box{ width:952px; height:450px; margin:0 auto; text-align:center;
 color: rgba(0,0,0,.15)x-offset: 5 * cos(180°- 145°) = 4.09pxy-offset: 5 * sin(180°- 145°) = 2.87pxspread-radius: 10 * 6% = 0.6pxblur-radius: 10 - 0.6 = 9.4px;
box-shadow: 4.09px 2.87px 9.4px 0.6px rgba(0,0,0,.15);text-shadow: 4.09px 2.87px 9.4px rgba(0,0,0,.15);
}
 .Title{ border-bottom:solid 1px #e0e0e0; width:952px;margin-top:20px;}
 p{ text-align:center; color:#444444;}
 .content{margin-top:100px;}
 .line{ width:690px; text-align:center;border-bottom:solid 1px #e0e0e0;
 margin-top:107px; margin-left:130px;}
 .button {
  color: #ffffff;
  padding: 10px;
  text-decoration: none;
  border-radius: 2px;
  width:150px; text-align:center; margin:0 auto;
}

</style>
</head>  
<body>  
  <div class="box">
     <p style="padding-top:20px; font-size:1.2em;">セッションタイムアウト</p>
     <p class="Title"></p>
     <div class="content">
        <p>自動ログアウト時間を経過したため、自動的にログアウトしました。</p>
        <p>再度利用になるには以下のボタンをクリックし、再度ログインしてください。</p>
        <p class="line"></p>
     </div>
     <div class="button">
    <a class="button" href="/" style="background-color:#4caf50;">再ログインする</a>
     </div>
     
  </div>
</body>  
</html>  