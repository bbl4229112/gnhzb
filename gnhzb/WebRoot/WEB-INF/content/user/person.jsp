<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
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
		<title>用户信息输入</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/test/js/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/test/js/jquery.min.js"></script>
		<script src="${ctx}/test/js/jquery.Jcrop.js"></script>
		<script type="text/javascript" src="${ctx}/js/swfupload/swfupload.js"></script>
		<script type="text/javascript" src="${ctx}/js/swfupload/swfupload.queue.js"></script>
	</head>
	<body>
		<table cellpadding="0" cellspacing="0" style="height: 495px">
			<tr >
				<td rowspan="4" align="center" style="width: 300px; height: 495px">
					<div id="uploader"></div>
				</td>
			</tr>
			<tr style="width: 500px">
				<td style="width: 200px; height: 300px">
                    <div id="target" style="height: 200px;border:thin;border-width: 1;border-color: blue">
				
					</div>
				</td>
			</tr>
			<tr>
				
				<td style="width: 200px; height: 100px">
					<div
						style="width: 100px; height: 100px; overflow: hidden; margin-left: 5px;border:thin;border-width:1;border-color: blue" id="target2">
						
					
					</div>
				
		
		

				</td>
			</tr>
			<tr style="height: 50px">
			<td colspan="2" align="center" style="width: 200px">
				<div>
								
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
		</table>
	</body>
</html>


<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
<script type="text/javascript"  src="${ctx}/js/cims201.js"></script>
<script type="text/javascript"  src="${ctx}/js/utils.js"></script>
<script type="text/javascript"  src="${ctx}/js/user/person_input.js"></script>
<script src="${ctx}/js/commontools/commonForm.js" type="text/javascript"></script>


<script type="text/javascript">
var thumbnailpath = null;
var person = new userinput();


function submitcutthumbnail()
{

thumbnailpath=document.getElementById('thumbnailpath').value;
//alert("图片路径为"+thumbnailpath);
var x=document.getElementById('x').value;
var y=document.getElementById('y').value;
var h=document.getElementById('h').value;
var w=document.getElementById('w').value;
var result= cims201.utils.getData('knowledge/fileupload!cutthumbnail.action',{x:x,y:y,pwidth:w,pheight:h,filename:thumbnailpath})
alert("图片上传成功");
}


</script>


