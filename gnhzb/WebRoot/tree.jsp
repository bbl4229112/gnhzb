<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'tree.jsp' starting page</title>
    
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
		
		<script src="<%=basePath %>js/commontools/commonTree.js" type="text/javascript"></script>

  </head>
  
  <body>
    
  </body>
  <script type="text/javascript">
		//从服务器端获取数据
	var treeData = cims201.utils.getData('<%=basePath%>tree/tree!list.action',{json:"domainRoot"});
	//定义树列的显示名称
	var treeColumns = [{
				      header: '权限',
				      dataIndex: 'name',
				      renderer: function(v,r){					
						if(r.status == 0){
							return '<div class="e-tree-checkbox"><div class="e-tree-check-icon "></div>'+v+'</div>'; 
						}
						if(r.status == 1){
							return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  e-table-checked"></div>'+v+'</div>'; 
						}	
						
						if(r.status == 2){
							return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  e-table-notallowchecked"></div>'+v+'</div>'; 
						}		
						
						if(r.status == 3){
							return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  e-table-notallowchecked_c"></div>'+v+'</div>'; 
						}      	        
				      	        
				   		return '<div class="e-tree-checkbox"><div class="e-tree-check-icon "></div>'+v+'</div>'; 
				      }
				 }];
				 
				 
		//定义树列的显示名称
	var treeColumns1 = [{
				      header: '权限',
				      dataIndex: 'name'
				 }];
				 
		var myTree = new createTree({},treeColumns,treeData,'cascade',['确定'],[
			function(e){
				var rs = myTree.getCurrentNodes();
				rs.each(function(o){
					alert(o.name);
				});
				alert('currentNode:'+myTree.getCurrentNode().name);
			}
		]);
		var myTree1 = new createTree({},treeColumns1,treeData,'single',['确定'],[
			function(e){
				var r = myTree1.getCurrentNode();
				alert(r.name);
			}
		]);
				
		Edo.build({
		    id: 'ct',    
		    type: 'box',         
		    width: '1000',
		    height: '500',  
		    border: [0,0,0,0],      
		    layout: 'vertical',    
		    children: [
		        myTree.getTree(),
		        myTree1.getTree()
		    ],
		    render: document.body
		});
		
	
	</script>
</html>
