<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>testlayout</title>
    

	<link href="<%=basePath %>cqz/edojs/scripts/edo/res/css/edo-all.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
    
  </body>
 <%-- <link href="<%=basePath %>cqz/edojs/ide/css/index.css" rel="stylesheet" type="text/css" /> --%>
  <script src="<%=basePath %>cqz/edojs/scripts/edo/edo.js" type="text/javascript"></script>
  <script src="<%=basePath %>cqz/js/module/testlayout.js" type="text/javascript"></script>
<%--  <script src="<%=basePath %>cqz/edojs/ide/UIComponents.js" type="text/javascript"></script> --%>
</html>
