<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>模块化配置知识服务平台</title>
		<link href="../js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<script src="../js/edo/edo.js" type="text/javascript"></script>

		<script src="../js/cims201.js" type="text/javascript"></script>
		<script src="../js/utils.js" type="text/javascript"></script>
		
		<script src="../js/expert/add_article.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="../js/ckeditor/ckeditor.js"></script>
		<script src="../js/commontools/commonForm.js" type="text/javascript"></script>
		<link rel="stylesheet" href="../js/ckeditor/samples/sample.css" type="text/css"></link>
	<body>
	    <div id="afterupload" align=center style="padding:5px 0 0 5px;z-index:100;display:block;">
			</div>
			<div id='articleForm' align=center style="padding:0 0 0 10px;display:block;">
												<s:form id="addarticle" action="knowledge!simplesave.action" 
													method="POST" >

												    <s:textarea name="articlecontent" id="articlecontent" /> 
												    
                                                    <s:hidden name="sourcefilepath" id="sourcefilepath" />
													<s:hidden name="ktypeid" id="ktypeid" value="20000" />
													
													<s:hidden name="domain" id="domain" />
                                                    <s:hidden name="formvalue" id="formvalue" />
                                                    <s:hidden name="attachfile" id="attachfile" />
                                                    <s:hidden name="citation" id="citation" />
                                                  
												</s:form>
		</div>
		<div id="loginBtn" align=center></div>	
	</body>
	<script type="text/javascript">
cims201.knowledge.addArticleInit();
//cims201.knowledge.simpuploaddetail();
</script>