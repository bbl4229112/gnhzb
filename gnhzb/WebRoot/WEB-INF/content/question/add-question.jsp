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
		
		<script src="../js/question/add_question.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="../js/ckeditor/ckeditor.js"></script>
		<script src="../js/commontools/commonForm.js" type="text/javascript"></script>
        
	</head>
	<body>
	    <div id="afterupload" style="width:900px;padding:5px 0 0 30px;z-index:100;display:block;">
			</div>
			<div id='questionform'>
												<s:form id="addquestion" action="knowledge!simplesave.action" 
													method="POST" >


                                                    <s:hidden name="sourcefilepath" id="sourcefilepath" />
													<s:hidden name="ktypeid" id="ktypeid" value="10000" />
													
													<s:hidden name="domain" id="domain" />
                                                    <s:hidden name="formvalue" id="formvalue" />
                                                    <s:hidden name="attachfile" id="attachfile" />
                                                    <s:hidden name="citation" id="citation" />
            
                                                    
                                                  
												</s:form>


											
		</div>
			
	</body>
	<script type="text/javascript">
cims201.knowledge.addQuestionInit();
//cims201.knowledge.simpuploaddetail();
</script>