<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>知识地图展示</title>
    
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
	<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
	    <script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
  </head>
  
  <body>
  
  
	
  </body>
  <script type="text/javascript">
    	var searchInput = Edo.create({
    		type: 'autocomplete', 
			width: 400, 
			queryDelay: 2000,
			url: 'kmap/onto!recommentTag.action',
			popupHeight: '65',
			valueField: 'tagName', 
			displayField: 'tagName'
    	});
    	
    	var radioInput = new Edo.controls.RadioGroup()
		.set({    
		        //width: 200,
		        repeatDirection: 'horizontal',
		        repeatItems: 3,
		        repeatLayout: 'flow',       
		        data: [
		            {text: '知识树', value: 'tree', checked: true},
		            {text: '知识网络', value: 'graph'}
		        ]
		    }
		);
    	
    	
    	var kmapModule = Edo.create({
    		type:"module",
    		width: '100%',
    		height: '100%',
    		border: [0,0,0,0],
    		padding: [0,5,5,0],
    		src: 'ontofile/chart.jsp'
    	});
    	
      
    	
    	Edo.create({
    		type: 'box',
    	    width: cims201.utils.getScreenSize().width,
            height: cims201.utils.getScreenSize().height-20,
    		border: [0,0,0,0],
    		padding: [0,0,0,0],
    		layout: 'vertical',
    		render: document.body,
    		children: [
    			{
    				type: 'box',
		    		width: '100%',
		    		height: 65,
		    		border: [0,0,0,0],
		    		padding: [5,0,0,10],
		    		layout: 'horizontal',
		    		children: [
		    			{type: 'label', text: '搜索术语:'},
		    			searchInput,
		    			{type: 'label', text: '图形类型:'},
		    			radioInput,
		    			{type: 'button', text: '确定', onclick: function(e){
		    				var viewtype = radioInput.getValue();
		    				if(viewtype == null || viewtype == '' || viewtype == 'null'){
		    					viewtype = 'tree';
		    				}
		    				var searchname = searchInput.get('text');
		    				if(viewtype == 'net'){
		    					kmapModule.load('ontofile/relatemap.jsp?searchname='+encodeURI(searchname,'utf-8'));
		    				}else{
		    					kmapModule.load('ontofile/chart.jsp?searchname='+encodeURI(searchname)+'&viewtype='+viewtype);
		    				}
		    			}}
		    		]
    			},
    			kmapModule
    		]
    	});
    </script>
  
</html>
