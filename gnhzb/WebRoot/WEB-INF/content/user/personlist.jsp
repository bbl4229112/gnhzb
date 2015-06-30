<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
	<base href="<%=basePath%>">
		<title>用户个人信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="${ctx}/test/js/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
			<script type="text/javascript" src="${ctx}/js/swfupload/swfupload.js"></script>
		<script type="text/javascript" src="${ctx}/js/swfupload/swfupload.queue.js"></script>
		<script src="${ctx}/test/js/jquery.min.js"></script>
		<script src="${ctx}/test/js/jquery.Jcrop.min.js"></script>
	
	</head>
	<body>
	<table style="border: black 1 medium;"><tr>
		<td  align="center" style="width: 500px; height:600px">
					<div id="plist"  style="height: 495px"></div>
			
	
	</td>
	<td><div>
		<table cellpadding="0" cellspacing="0" style="height: 600px">
		
			
			<tr style="width: 500px">
				<td style="width: 200px; ">
                   <div id="uploader" style="padding-left: 20;display: inline">
				
					</div>
				</td>
			</tr>
			<tr style="width: 250px">
				<td style="width: 200px; height: 300px">
			
                    <div id="target" style="height: 300px;border:thin;border-width: 1;border-color: blue">
				
					</div>
				</td>
			</tr>
	
			<tr style="height: 50px">
			<td colspan="2" align="center" style="width: 250px">
				<div id="submitimgdiv">
								
			<form action="knowledge/fileupload!cutthumbnail.action" style="width: 188px"> 
			<input type="hidden" size="4" id="thumbnailpath" name="filename" />
			<input type="hidden" size="4" id="x" name="x" />
			<input type="hidden" size="4" id="y" name="y" />	
	        <input type="hidden" size="4" id="w" name="pwidth" />
			<input type="hidden" size="4" id="h" name="pheight" />
			<input type="button" id="submitimg" name="确定" style="display:none;" value="确定头像" onclick="submitcutthumbnail()">
				</form> 	
					</div>
			</td>
			</tr>
				<tr>
				
				<td style="width: 250px; height: 140px">
					<div
						style="width: 100px; height: 100px; overflow: hidden; margin-left: 5px;border:thin;border-width:1;border-color: blue" id="target2">
						
					
					</div>
				
		
		

				</td>
			</tr>
			
		</table>
		<div>
		</td>
		</tr>
		</table>
	</body>
</html>


<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
<script type="text/javascript"  src="${ctx}/js/cims201.js"></script>
<script type="text/javascript"  src="${ctx}/js/utils.js"></script>
<script type="text/javascript"  src="${ctx}/js/user/personlist.js"></script>
<script src="${ctx}/js/commontools/commonForm.js" type="text/javascript"></script>
<script type="text/javascript"  src="${ctx}/js/user/user_input.js"></script>
<script src="${ctx}/js/commontools/PopupManager.js" type="text/javascript"></script>
<script src="${ctx}/js/commontools/Window.js" type="text/javascript"></script>


<script type="text/javascript">
var personlist = new createPersonList2();







</script>


