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
		
		<script src="../js/knowledge/knowledge_dandianupload.js"
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
			<div><span>为了保证文档能正常显示，我们支持单点故障的知识文档上传:</span></div>

		    <div>
	

				系统将根据您上传的文档作为主文档抽取知识内容，若无法抽取，若格式不符则无法上传。 
			
		    
		    
		    
		    
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