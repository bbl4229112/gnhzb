<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath %>cqz/edojs/scripts/edo/res/css/edo-all.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body style="margin:0px; height:100%; width:100%;overflow:hidden;" scroll="no">
  </body>
  <script src="<%=basePath %>cqz/edojs/scripts/edo/edo.js" type="text/javascript"></script>
  <script src="<%=basePath %>cqz/js/app.js" type="text/javascript"></script>
</html>
