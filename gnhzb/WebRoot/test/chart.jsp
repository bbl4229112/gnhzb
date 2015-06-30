<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<base href="<%=basePath %>"/>
		<title>本体图谱</title>
	</head>
	<%
		String searchname = request.getParameter("searchname");
		String viewtype =  request.getParameter("viewtype");
         	if (null == viewtype)
			viewtype = "tree";
		String direction = request.getParameter("direction");
		if (null == direction)
			direction = "forward";
		
		
	%>
	<script type="text/javascript">


function showwindow(viewtype, searchname, direction) {

//var url2=encodeURI(url);
          	searchname=encodeURI(searchname);
           var url="<%=request.getContextPath()%>/test/chart.jsp?viewtype="+viewtype+"&searchname="+searchname+"&direction="+direction;
           var iWidth=1000; //窗口宽度 
           var iHeight=700;//窗口高度
         
           var iTop=(window.screen.height-iHeight)/2;
           var iLeft=(window.screen.width-iWidth)/2;
           window.open(url,"","Scrollbars=no,Toolbar=no,Location=no,Direction=no,Status=no,Resizeable=yes,Width=1000,Height="+iHeight+",top="+iTop+",left="+iLeft);
          }
   
function showrelate(searchname) {


          searchname=encodeURI(searchname);
        
          var parent= window.parent;
               }          
          
window.onload=function()
{
 
}          
</script>
	<body scroll="auto">

	
	<%if(null!=searchname&&!searchname.equals("")&&!searchname.equals("null")) {%>
		<applet codebase="<%=basePath%>" archive="<%=basePath%>/test/prefuse.jar" code="prefuse.rocketon.class" name="Ontology"
			width="99%" height="565" MAYSCRIPT id="Viewer"  wmode='Opaque'   >
			<param name=searchname value="<%=searchname%>">
			<param name=viewtype value="<%=viewtype%>">
			<param name=direction value="<%=direction%>">

		</applet>
		<%} %>
	</body>
</html>