<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'knowledge-view.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath %>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
			<link href="<%=basePath %>css/wenku.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/commontools/boxText.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/thirdlib/ckeditor/ckeditor.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/knowledge/keep/keeputils.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
		
		<script src="<%=basePath %>js/commontools/commonTable.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/commontools/starJudge.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/knowledge/knowledge-util.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/knowledge/knowledge-view.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/comment/comment-view.js" type="text/javascript"></script>
		

		<script src="<%=basePath %>js/knowledge/knowledge_upload.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/swfupload/swfupload.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>js/swfupload/swfupload.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/swfupload/swfupload.queue.js"></script>

  </head>
  
  <body>
    
  </body>
  <script type="text/javascript">
  		//var mk = new knowledgeview();
  		var myId = <%=request.getAttribute("id")%>;
  		if(myId != null && myId != 'null' && myId != ''){
  			
  			var mk = new createKnowledgeView(myId).getKnowledgeView();
	  		Edo.build({
			    id: 'ct',    
			    type: 'box',                 
			    layout: 'vertical',  
			    //width: 1000,
			    width: cims201.utils.getScreenSize().width-20,
			    //height: 1200,  
			    border: [0,0,0,0], 
		    	padding: [0,0,0,0], 
			    children: [
			        mk
			    ],
			    render: document.body
			});
  		}
  </script>
  
</html>
