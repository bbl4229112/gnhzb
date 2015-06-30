<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'm1.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath %>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
	<link href="<%=basePath %>css/wenku.css" rel="stylesheet"
			type="text/css" />
	<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/Window.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/PopupManager.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/commonTree.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/commonTable.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/user/roleuserselector.js" type="text/javascript"></script>
	
	<script src="<%=basePath %>js/knowledge/approval/approval_new.js" type="text/javascript"></script>
  </head>
  
  <body>
    
  </body>
  
  <script type="text/javascript">
  	var mf = {
  		status: '审批通过',
  		nodes: [
  			{
  				nodeStatus: '通过',
  				approverORLenderName: '吉祥',
  				approvalORBorrowOpinion: '通过'		  			
  			},
  			{
  				nodeStatus: '通过',
  				approverORLenderName: '吉祥',
  				approvalORBorrowOpinion: '通过'		  			
  			},
  			{
  				nodeStatus: '通过',
  				approverORLenderName: '吉祥',
  				approvalORBorrowOpinion: '通过'		  			
  			}
  		]
  	};
  	
  	var myFlowBox = new createApprovalFlow(mf);
  	
  	Edo.create({
		type: 'box', 
		render: document.body,
		width: 700,
		height: 500,
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout: 'horizontal',
		children: [myFlowBox.getFlow()]
	});
  
  </script>
</html>
