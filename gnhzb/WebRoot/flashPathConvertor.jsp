<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="edu.zju.cims201.GOF.util.Constants;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ttt.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
  </head>
  <% 
  String sourcefilepath=request.getParameter("sourcefilepath");
  //System.out.println("sourcefilepath==="+sourcefilepath);
  boolean ispdf=false;
  String flashpath=request.getParameter("path");
  if(null!=flashpath&&!flashpath.equals("")&&!flashpath.equals("noflash"))
  flashpath="knowledge/viewfile!view.action?fileId="+flashpath+"&test=test";
  else{
  if(null!=sourcefilepath&&(sourcefilepath.indexOf(".pdf")!=-1||sourcefilepath.indexOf(".PDF")!=-1))
  {
  ispdf=true;
  flashpath="knowledge/viewfile!view.action?fileId="+sourcefilepath+"&test=test";
  }
  else
  flashpath="flash/noflash.swf";
  
  }//System.out.println("flashpath================"+flashpath);
  %>
  <body>
<%if(ispdf){ %>
 <p style="margin-top: -50px; margin-bottom: 0">
 
            <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" id="myFlashView" width="100%">  
              <param name="_Version" value="327680">  
              <param name="_ExtentX" value="2646">  
              <param name="_ExtentY" value="1323">  
              <param name="_StockProps" value="0">  
              <param name="toolbar" value="true">  
              <param name="src" value="<%=flashpath%>">  
            </object>  
       </p>

  <script type="text/javascript">
  	document.getElementById('myFlashView').height = cims201.utils.getScreenSize().height+15;
  </script>

   <%}else{ %>
   <embed id="myFlashView" src='<%=flashpath%>' quality='high' wmode='Opaque' width='100%' type="application/x-shockwave-flash"></embed>
     <script type="text/javascript">
  	document.getElementById('myFlashView').height = cims201.utils.getScreenSize().height-35;
  </script>
   
 <%} %>
  </body>
  


</html>
