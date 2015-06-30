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
<script src="../js/knowledge/knowledge_upload.js" type="text/javascript"></script>
<script src="../js/swfupload/swfupload.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="../js/swfupload/swfupload.queue.js"></script>

	</head>

	<body style="padding-bottom:40px;">
		<div id="doc3">

			<div id="bd">
				<body scroll="auto">
					
					<table class="n_table_body" cellpadding="0" cellspacing="1"
						border="0" width="99%" align="center">
						<tr class="n_table_row">
							<td>
								<table style="width: 100%">
								
									<tr>
										<td>
										
											<div  id='selTemplate'>
									
											</div>
                                       
											<div id='uploadfile'>
												<s:form id="knowledgeuplod" action="knowledge!save.action" 
													method="POST" enctype="multipart/form-data">


 
													<div style="font:normal 11px tahoma, arial, helvetica, sans-serif; 14px;margin-top: 10px;padding-bottom: 7px;">选择源文件&nbsp;&nbsp;&nbsp;<s:file theme="simple" name="sourcefile" labelSeparator="  "  cssStyle="width:400px;" onchange="cims201.knowledge.GetWordBookMarksValue(this)"/></div>
													<s:hidden name="ktypeid" id="ktypeid" />
													<s:hidden name="domain" id="domain" />
                                                    <s:hidden name="formvalue" id="formvalue" />
                                                    <s:hidden name="attachfile" id="attachfile" />
                                                    <s:hidden name="citation" id="citation" />
                                                  
												</s:form>


											
											</div>
											<div id='knowledgeform'>
											</div>
										</td>
									</tr>


								</table>
							</td>
						</tr>
					</table>
					<input type="hidden" id="temppath" name="temppath" value="" />
			</div>

		</div>
	</body>
</html>

<script>
cims201.knowledge.uploadknowledgeInit();
</script>


