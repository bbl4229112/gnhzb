<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8">
		<title>本体图谱</title>
	</head>
	<script>
	function showrelate(searchname) {

		//var url2=encodeURI(url);

		searchname = encodeURI(searchname);

		var url = "/MapleTr/KnowledgeMap/HTML/associateknowledge.html?name="
				+ searchname;
		var iWidth = 1000; //窗口宽度 
		var iHeight = 700;//窗口高度

		var iTop = (window.screen.height - iHeight) / 2;
		var iLeft = (window.screen.width - iWidth) / 2;
		window
				.open(
						url,
						"",
						"Scrollbars=no,Toolbar=no,Location=no,Direction=no,Status=no,Resizeable=yes,Width=1000,Height="
								+ iHeight + ",top=" + iTop + ",left=" + iLeft);
	}
</script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<%
		String searchname = request.getParameter("searchname");
		//searchname = new String(searchname.getBytes("ISO-8859-1"), "utf-8");
		searchname="火箭发动机";
		System.out.println(searchname);
	%>

	<body>

		<applet codebase="<%=request.getContextPath()%>/"
			archive="<%=basePath%>/test/prefuse.jar"
			code="prefuse.rocketrelateon.class" name="Ontology" width="97%"
			height="550" MAYSCRIPT>
			<param name=searchname value="<%=searchname%>">
		</applet>
	</body>
</html>