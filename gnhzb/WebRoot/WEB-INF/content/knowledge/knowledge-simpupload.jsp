<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国运载火箭技术研究院知识服务平台</title>
		<link href="../js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<script src="../js/edo/edo.js" type="text/javascript"></script>

		<script src="../js/cims201.js" type="text/javascript"></script>
		<script src="../js/utils.js" type="text/javascript"></script>
		
		<script src="../js/knowledge/knowledge_simupload.js"
			type="text/javascript"></script>
		<script src="../js/swfupload/swfupload.js" type="text/javascript"></script>
		<script type="text/javascript" src="../js/swfupload/swfupload.js"></script>
		<script type="text/javascript"
			src="../js/swfupload/swfupload.queue.js"></script>
		<script src="../js/commontools/commonForm.js" type="text/javascript"></script>
        <script src="../js/commontools/Window.js" type="text/javascript"></script>
        <script src="../js/commontools/PopupManager.js" type="text/javascript"></script>
        <script src="../js/tree/tree-util.js" type="text/javascript"></script>
	</head>
	<body>
	    <div id="beforeupload" style="overflow: hidden">
	    <div id="upbox" style="width:1000px;padding:5px 0 0 30px;"></div>
		<div id="notice" style="background-color: #fffbf0;width:980px; padding:20px 0px 30px 20px;margin:0px 0px 0 30px; font-size:14px;">
			<h3>
				文档上传须知
			</h3>

			<div><span>请上传大小小于50M的附件.</span></div>
			<div><span>为了保证文档能正常显示，我们支持以下格式的文档上传:</span></div>

		    <div>
		    <li>
				MS Office文档
				<img src="../css/doc.gif" style="vertical-align: text-bottom;"/>
					doc docx  
				<img src="../css/xls.gif" style="vertical-align: text-bottom;"/>
					xls xlsx 
				<img src="../css/ppt.gif" style="vertical-align: text-bottom;">
					ppt pptx pot pps 
			</li>
			<li>
				PDF
				<img src="../css/pdf.gif" style="vertical-align: text-bottom;">
					pdf 
			</li>
			<li>
				图片
				<img src="../css/jpg.png" height=16
					style="vertical-align: text-bottom;">
					jpg 
				<img src="../css/gif.png" height=16>
					gif 
				<img src="../css/png.png" height=16>
					png 
			</li>

			<li>
				纯文本
				<img src="../css/txt.gif" style="vertical-align: text-bottom; ">
					txt
			</li>
		<br>


				系统将根据您上传的模板文档作为主文档抽取知识内容，若无法抽取，请手工填写。 

			</div>
			</div>
		</div>	
		    <div id="afterupload" style="width:900px;padding:5px 0 0 30px;z-index:100;display:none;">
			</div>
			<div id='knowledgeform'>
				<s:form id="knowledgeuplod" action="knowledge!simplesave.action" 
					method="POST" >
                    <s:hidden name="sourcefilepath" id="sourcefilepath" />
					<s:hidden name="ktypeid" id="ktypeid" />
					<s:hidden name="domain" id="domain" />
                    <s:hidden name="formvalue" id="formvalue" />
                    <s:hidden name="attachfile" id="attachfile" />
                    <s:hidden name="citation" id="citation" />                                          
				</s:form>							
			</div>
			
	</body>
	<script type="text/javascript">
var x=new   Date().getTime();
	
cims201.knowledge.simpuploadknowledgeInit(x);

function setcdtreeselected(componentid,selectedcdtree)
{
var tempselected;
    if(selectedcdtree!=""&&selectedcdtree!=null)
    tempselected=selectedcdtree.join(",");
    else
   tempselected=""; 
	Edo.get(componentid).setValue(tempselected);
}

</script>