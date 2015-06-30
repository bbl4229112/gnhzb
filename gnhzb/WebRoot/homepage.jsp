<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>模块化配置知识服务平台</title>
    
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
	<script src="<%=basePath %>js/thirdlib/ckeditor/ckeditor.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/boxText.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/index.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/commonTable.js" type="text/javascript"></script>

	<script src="<%=basePath %>js/knowledge/knowledge-util.js" type="text/javascript"></script>
	
	<!-- 搜索 -->	
	<script src="<%=basePath %>js/knowledge/knowledge_search.js"
		type="text/javascript"></script>
	<!-- 搜索的简约界面 -->
	<script src="<%=basePath %>js/knowledge/knowledge_search_index.js"
		type="text/javascript"></script>
			
	<!-- 知识展示 -->
	<script src="<%=basePath %>js/commontools/starJudge.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-view.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-list-box.js" type="text/javascript"></script>
	
	<!-- 评论 -->
	<script src="<%=basePath %>js/comment/comment-view.js" type="text/javascript"></script>
	
	
	<!-- 主页js -->
	<script src="<%=basePath %>js/index.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/getComponentByIndex.js" type="text/javascript"></script>
  </head>
  
  <body>
    
  </body>
  <script type="text/javascript">
	  	var cims201IndexTopBar = new createIndexTopBar();
	  	var cims201IndexNavTree = new createIndexNavTree();
	  	var cims201IndexContent = new createIndexContent();
	  	//构建主体框架
		Edo.build(
		{
		    type: 'app',
		    render: document.body,
		    layout: 'vertical',
		    padding: [0,0,0,0],
		    border: [0,0,0,0],
		    //增加框架的元素
		    children:[
		    	//顶栏描述
		    	cims201IndexTopBar.getTopBar()
		    	,	         
		        //主界面
		        {
		            type: 'ct',
		            padding: [0,0,0,0],
		            width: '100%',
		            height: '100%',
		            layout: 'horizontal',
		            children:[
		            //左侧边
		            cims201IndexNavTree.getNavTree()
		            ,
		            //右主界面
		            cims201IndexContent.getContent()
				    ]
				}
		    ]
		});  
  </script>
</html>
