<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国运载火箭技术研究院知识服务平台</title>
		<link href="../js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/wenku.css" rel="stylesheet"
			type="text/css" />
		<script src="../js/edo/edo.js" type="text/javascript"></script>
		<script src="../js/commontools/boxText.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/keep/keeputils.js" type="text/javascript"></script>
		<script src="../js/cims201.js" type="text/javascript"></script>
		<script src="../js/utils.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/index.js" type="text/javascript"></script>
		<script src="../js/commontools/commonTable.js" type="text/javascript"></script>

		<script src="../js/knowledge/knowledge_search.js"
			type="text/javascript"></script>
		<script src="../js/knowledge/knowledge-util.js" type="text/javascript"></script>
		<script src="../js/knowledge/knowledge-list-box.js" type="text/javascript"></script>

	</head>

	<body>

	</body>
</html>

<script>
	
	//用来显示知识
	//var myTableList = new createKnowledgeList();
	//查询框
	//var mySearchUI = new knowledgeSearchPanel();
	//myTableList	
	var myUrl = '<%=request.getAttribute("url")%>';	
	var formvalue = '<%=request.getAttribute("formvalue")%>';	
	
	var myTable1_rt = new createKnowledgeList_box;   	
	myTable1_rt.search({formvalue:formvalue},myUrl);
		
	Edo.build({
	    id: 'ct',    
	    type: 'box',                 
	    layout: 'vertical',  
	    width: 850,
	    //height: 1250,  
	    border:[0,0,0,0],
	    padding: [0,0,0,0],
	    children: [
	        myTable1_rt.getKnowledgeList()
	    ],
	    render: document.body
	});
	
	
	
	
</script>


