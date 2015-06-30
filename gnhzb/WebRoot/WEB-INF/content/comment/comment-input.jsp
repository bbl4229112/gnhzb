<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'comment-input.jsp' starting page</title>
    
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
		<script src="<%=basePath %>js/commontools/boxText.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/thirdlib/ckeditor/ckeditor.js" type="text/javascript"></script>
		
		<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>

		<script src="<%=basePath %>js/comment/comment-view.js" type="text/javascript"></script>

  </head>
  
  <body>
    
  </body>
  <script type="text/javascript">
  	var knowledgeID = 4;
  	if(knowledgeID != null && knowledgeID != ''){
  		var myComment = new createCommentPanel();
	  	myComment.search({knowledgeID:knowledgeID}, 'comment/comment!listComment.action');
	  	
	  	Edo.build({
		   //id: 'ct',    
		   type: 'box',        
		   border: [0,0,0,0],
		   padding: [0,12,0,12],         
		   layout: 'vertical', 
		   width: cims201.utils.getScreenSize().width-50,
		   //height: '1200',   
		   //height: 1000,
		   autoHeight: true,
		   children: [	       	       
		   	   myComment.getComment()
		   ],
		   render: document.body
	   });	
  	}
  	
  </script>
</html>
