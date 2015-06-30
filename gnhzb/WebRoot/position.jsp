<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="<%=basePath %>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
	<link href="<%=basePath %>css/wenku.css" rel="stylesheet"
			type="text/css" />
	<link href="<%=basePath %>css/cims201.css" rel="stylesheet"
			type="text/css" />
	<SCRIPT LANGUAGE="JavaScript">
		<!-- Hide
		function killErrors() {
			return true;
		}
		window.onerror = killErrors;
		//-->强制不报错
	</SCRIPT>
  </head>	
  <% 
	 String flashPath = "businessactivities/BusinessActivities.swf";
  %>
  <body style="margin: 0px; width: 100%;height:100%;">
   	<embed id="myFlashView" src='<%=flashPath%>' quality='high' wmode='Opaque' 
   		width='100%' height ='100%' type="application/x-shockwave-flash"></embed>
   	<script type="text/javascript">
		function flexShowKnowledgeDetail(knowledgeId, titleName) {
			window.parent.openNewTab('showKnowledgeDetail'+knowledgeId, 'knowledgeview', titleName, {knowledgeID:knowledgeId,borrowFlowId:0});					
		}
	</script>
  </body>  
</html>
	

