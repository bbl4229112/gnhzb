<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<base href="<%=basePath %>"/>
		<title>本体图谱</title>
		<style type="text/css"> 
 #iframe1{position:absolute; visibility:inherit;top:0px;left:0px; z-index:-1; filter: Alpha(Opacity=0);} 
</style> 
	</head>


	<body scroll="auto">

    <iframe height='700' width='1000' id="iframe1">

	</iframe>
	

	</body>
</html>