<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'knowledge.jsp' starting page</title>
    
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
		
		<script src="<%=basePath %>js/commontools/commonTable.js" type="text/javascript"></script>
  </head>
  
  <body>
    
  </body>
  
	<script type="text/javascript">
		var myConfig = {
			verticalLine: false,
			horizontalLine: false,
			headerVisible: false,
			rowHeight: 200,
			onBodymousedown: function(v,r){
				var vv = v;
				var rr = r;
				alert(v.item.titleName);
			}};
		var myColumns = [
				Edo.lists.Table.createMultiColumn(),
                {
                    headerText: '标题',
                    dataIndex: 'titleName',
                    headerAlign: 'center',
                    width: 900,
                 
                    align: 'left',
                    renderer: function(v,r){
                    	var outStr = '';
                    	outStr += '<span style="color:red;">';
                    	outStr += v;
                    	outStr += '</span><br>';
                    	outStr += '<span style="color:red;">';
                    	outStr += r.abstractText;
                    	outStr += '</span>';
                    	return outStr;
                    }
                },
                {
                	headerText: '标题',
                    dataIndex: 'knowledgeAction',
                    //headerAlign: 'center',
                    align: 'right'
                }
                
            ];
		var myTable = new createTable(myConfig,'100%','100%','知识列表',myColumns,[],[],'<%=basePath%>knowledge/knowledge!ksearch.action', {});
				
		Edo.build({
		    id: 'ct',    
		    type: 'box',         
		    width: '1000',
		    height: '500',  
		    border: [0,0,0,0],      
		    layout: 'vertical',    
		    children: [
		        myTable.getTable()
		    ],
		    render: document.body
		});
		
	
	</script>  
  
  
</html>
