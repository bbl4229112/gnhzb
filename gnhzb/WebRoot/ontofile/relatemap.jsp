<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8">
		<title>本体图谱</title>
	</head>
	<script>
	function showrelate(searchname) {

		//var url2=encodeURI(url);

		searchname = encodeURI(searchname);

		var url="../ui!clientsearch.action?keyword="+searchname;
		var iWidth = 1000;
		if(iWidth<cims201.utils.getScreenSize().width )
        iHeight=cims201.utils.getScreenSize().width;
		var iHeight = 800;
        if(iHeight<cims201.utils.getScreenSize().height )
        iHeight=cims201.utils.getScreenSize().height-20;
		var iTop = (window.screen.height - iHeight) / 2;
		var iLeft = (window.screen.width - iWidth) / 2;
		window
				.open(
						url,
						"",
						"Scrollbars=no,Toolbar=no,Location=no,Direction=no,Status=no,Resizeable=yes,Width=1000,Height="
								+ iHeight + ",top=" + iTop + ",left=" + iLeft);
	}
	
	window.onload=function()
{
 try
    {

        if (document.applets("Viewer").isActive())
        {
     //   alert("??");
       window.document.getElementById('maplink').style.display='block';  
       // var width=cims201.utils.getScreenSize().width;
        var height=cims201.utils.getScreenSize().height-20;
        if(height>765)
        {
         document.applets("Viewer").height=height-50; 
        }
       	
        }
        
    }
    catch(e)
    {
    	
       window.document.getElementById('maplink').style.display='none'; 
    }
}          
	
	
	
	
	
</script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<%
		String searchname = request.getParameter("searchname");

	%>

	<body>
<div id="maplink">
   <%if(null!=searchname&&!searchname.equals("")){ %>
		<applet codebase="<%=request.getContextPath()%>/"
			archive="<%=basePath%>/ontofile/prefuse.jar"
			code="prefuse.rocketrelateon.class" name="Ontology" width="97%"
			height="550" MAYSCRIPT id="Viewer">
			<param name=searchname value="<%=URLEncoder.encode(searchname,"utf-8")%>">
		</applet>		
		<%} %>	
	</div>
	</body>
</html>