<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
<script src="${ctx}/js/cims201.js" type="text/javascript"></script>
<script src="${ctx}/js/utils.js" type="text/javascript"></script>
<script src="${ctx}/js/knowledge/knowledge-view.js" type="text/javascript"></script>

<html>
	<head>
		<title>开始审批流程</title>
		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body style="margin: 0px; width: 100%; height:500;">
		<button name="ec" value="ce" onclick="showKnowledge();">efe</button>
	</body>
</html>

<script type="text/javascript" >
	function showKnowledge(){
		var kw = cims201.knowledge.knowledgeview();
		kw.show(20,20,false);
	}
</script>
