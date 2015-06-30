<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
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
		//String searchname ="推进剂";
		String searchname = request.getParameter("searchname");
		//String viewtype = "tree";
		String viewtype = request.getParameter("viewtype");
		String direction = request.getParameter("direction");
		if (null == direction)
			direction = "forward";
			System.out.println(searchname);
	//	if(null!=searchname)	
	//	searchname = new String(searchname.getBytes("ISO-8859-1"), "utf-8");
		System.out.println(searchname+"*"+viewtype);
	%>
	
	  <script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
       <script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
	<script type="text/javascript">


function showwindow(viewtype, searchname, direction) {

//var url2=encodeURI(url);
          	searchname=encodeURI(searchname);
           var url="<%=request.getContextPath()%>/ontofile/chart.jsp?viewtype="+viewtype+"&searchname="+searchname+"&direction="+direction;
         	var iWidth = 1000;
		if(iWidth<cims201.utils.getScreenSize().width )
        iWidth=cims201.utils.getScreenSize().width;
		var iHeight = 700;
        if(iHeight<cims201.utils.getScreenSize().height )
        iHeight=cims201.utils.getScreenSize().height-20;
         
           var iTop=(window.screen.height-iHeight)/2;
           var iLeft=(window.screen.width-iWidth)/2;
           window.open(url,"","Scrollbars=no,Toolbar=no,Location=no,Direction=no,Status=no,Resizeable=yes,Width=1000,Height="+iHeight+",top="+iTop+",left="+iLeft);
          }
   
function showrelate(searchname) {

//var url2=encodeURI(url);
       //   	searchname=encodeURI(searchname,'utf-8');
          	var test=window.parent.parent;
          	window.parent.parent.openNewTab(window.parent.parent.concatName(searchname), 'knowledgelist_fulltext', window.parent.parent.concatName(searchname), {key:searchname});	
           var url="<%=request.getContextPath()%>/ui!clientsearch.action?keyword="+searchname;
                	var iWidth = 1000;
		if(iWidth<cims201.utils.getScreenSize().width ) 
        iWidth=cims201.utils.getScreenSize().width;
		var iHeight = 700;
        if(iHeight<cims201.utils.getScreenSize().height )
        iHeight=cims201.utils.getScreenSize().height-20;
         
           var iTop=(window.screen.height-iHeight)/2;
           var iLeft=(window.screen.width-iWidth)/2;
          // window.open(url,"","Scrollbars=no,Toolbar=no,Location=no,Direction=no,Status=no,Resizeable=yes,Width=1000,Height="+iHeight+",top="+iTop+",left="+iLeft);
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
    	//alert("没有可以显示的数据");
       //window.document.getElementById('maplink').style.display='none'; 
    }
}          


</script>
	<body scroll="auto">
	<div id="maplink">
   <%if(null!=searchname&&!searchname.equals("")){ %>
		<applet codebase="<%=basePath%>" archive="<%=basePath%>ontofile/prefuse.jar" code="prefuse.rocketon" name="Ontology"
			width="99%" height="765" MAYSCRIPT id="Viewer">
	<!-- 江丁丁 2013-8-25修改，原地图展示不了，去掉了jnlp_href
		    <param name="jnlp_href" value="ontofile/kmap.jnlp">    -->
		    <param name="separate_jvm" value="true" />
			<param name=searchname value="<%=searchname%>">
			<param name=viewtype value="<%=viewtype%>">
			<param name=direction value="<%=direction%>">
		</applet>
			<%} %>	
	</div>
	</body>
</html>