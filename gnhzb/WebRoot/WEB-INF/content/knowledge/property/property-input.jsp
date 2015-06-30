<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>知识属性输入</title>
		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body style="padding: 5px;">

	</body>
</html>


<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>

<script type="text/javascript"  src="${ctx}/js/cims201.js"></script>
<script type="text/javascript"  src="${ctx}/js/utils.js"></script>
<script type="text/javascript"  src="${ctx}/js/knowledge/ktype/property_input.js"></script>
<script src="${ctx}/js/commontools/commonForm.js" type="text/javascript"></script>

<script type="text/javascript">

cims201.knowledge.propertyinput.Init();

</script>