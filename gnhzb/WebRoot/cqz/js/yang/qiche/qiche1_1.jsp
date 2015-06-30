<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String moduleid=request.getParameter("moduleid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'qiche1.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		// Program starts here. Creates a sample graph in the
		// DOM node with the specified ID. This function is invoked
		// from the onLoad event handler of the document (see below).
	    var moduleid=<%=moduleid%>;
	    
  </script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

    <link rel="stylesheet" href="cqz/edojs/scripts/edo/res/css/edo-all.css" type="text/css"></link>
  </head>
 
  <body>
    
  </body>
  <script src="<%=basePath %>cqz/edojs/scripts/edo/edo.js" type="text/javascript"></script>
  <script src="<%=basePath %>cqz/edojs/data.js" type="text/javascript"></script>
  <script src="<%=basePath %>cqz/js/yang/qiche/qiche1_1.js" type="text/javascript"></script>
</html>
