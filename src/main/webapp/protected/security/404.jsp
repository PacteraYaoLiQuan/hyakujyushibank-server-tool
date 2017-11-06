<%--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<title>404 Not Found</title>
<style>
.bg_bg{ margin:0 auto; position:fixed;}
.btn { display: block; position: relative; background: #aaa; text-align:center; padding-top:10px; padding-bottom:10px; float: left; color: #fff; text-decoration: none; cursor: pointer;
width:170px;}
.btn.green { background: #00a4b9; }
* html .btn { border:10px double #aaa; }
* html .btn.blue { border-color: #00a4b9; }
p { clear: both; padding-bottom: 2em; }
</style>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" />
</head>

<body>
     <div class="bg_bg">
      <img src="<%=request.getContextPath()%>/img/404.png">
      <p><a href="/login" class="btn green" style="margin-top:-100px;
      margin-left:560px;">再ログインする</a></p>

     </div>
        

</body>
</html>

