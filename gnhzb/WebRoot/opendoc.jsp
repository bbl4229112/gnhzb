<%@page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String knowledgeid = request.getParameter("knowledgeid");
	String ktypeidstring = request.getParameter("ktypeid");
	Long ktypeid = Long.parseLong(ktypeidstring); 
	String sourcefilepath = request.getParameter("sourcefilepath");
	String dddd = "this11111111111.doc";
	System.out.println("------------------------====="+knowledgeid);
	System.out.println("------------------------====="+ktypeid);
	System.out.println("------------------------====="+sourcefilepath);
	System.out.println("-----------------------basePathbasePath-====="+basePath);
%>
<html>

<head>
	<link href="<%=basePath %>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
	<link href="<%=basePath %>css/wenku.css" rel="stylesheet"
			type="text/css" />
	<link href="<%=basePath %>css/cims201.css" rel="stylesheet"
			type="text/css" />
	
	<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/thirdlib/ckeditor/ckeditor.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/boxText.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/Window.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/PopupManager.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/navigator/nav.js" type="text/javascript"></script>

	<script src="<%=basePath %>js/systemevent.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/commonTable.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/commontools/commonTree.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/tree/commontree.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-list-table.js"
		type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-util.js" type="text/javascript"></script>
	
	<!-- 搜索 -->	
	<script src="<%=basePath %>js/knowledge/knowledge_search.js"
		type="text/javascript"></script>
	<!-- 搜索的简约界面 -->
	<script src="<%=basePath %>js/knowledge/knowledge_search_index.js"
		type="text/javascript"></script>
		<!-- avidm搜索界面 -->
	<script src="<%=basePath %>js/knowledge/avidm_search.js"
		type="text/javascript"></script>
		<!-- standard搜索界面 -->
	<script src="<%=basePath %>js/knowledge/standard_search.js"
		type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/standard-list-box.js" type="text/javascript"></script>
			
	<!-- 知识展示 -->
	<script src="<%=basePath %>js/commontools/starJudge.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-view.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/question-view.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-list-box.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/knowledge/knowledge-list-fulltext.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/tree/tree-util.js" type="text/javascript"></script>
	<!-- 借阅-->
	<script src="<%=basePath %>js/knowledge/borrow/borrowapprval.js" type="text/javascript"></script>
	<!-- 评论 -->
	<script src="<%=basePath %>js/comment/comment-view.js" type="text/javascript"></script>
	
	
	<!-- 主页js -->
	<script src="<%=basePath %>js/index.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/getComponentByIndex.js" type="text/javascript"></script>
  	<script src="<%=basePath %>js/tree/treeKnowledgePanel.js" type="text/javascript"></script>
  	<script src="<%=basePath %>js/system_index.js" type="text/javascript"></script>
  	
  	
  	<!-- 消息 -->
  	<script type="text/javascript" src="<%=basePath %>js/dwr/chat.js"></script> 
  	<script type='text/javascript' src='<%=basePath %>dwr/interface/ChatManager.js'></script> 
    <script type='text/javascript' src='<%=basePath %>dwr/engine.js'></script> 
    <script type='text/javascript' src='<%=basePath %>dwr/util.js'></script> 
    
    <script src="<%=basePath %>js/message/message-list.js" type="text/javascript"></script>
  	
  	<script type="text/javascript">
  		//整个应用的全局变量
  		//系统用户
  		cims201User = null;
  		
  	</script>
	<script src="${ctx}/js/message/message.js" type="text/javascript"></script>
	<script src="${ctx}/js/message/message-bar.js" type="text/javascript"></script>
	<script src="${ctx}/js/message/chat-box.js" type="text/javascript"></script>
	
	<!-- 审批知识 -->
	<script src="<%=basePath %>js/user/roleuserselector.js" type="text/javascript"></script>	
	<script src="<%=basePath %>js/knowledge/approval/approval_new.js" type="text/javascript"></script>
	
	<!-- 知识问答 -->
	<script src="<%=basePath %>js/question/answerrank.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/expertinput.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/experttreeinput.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/treeexpertlist.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/myanswerlist.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/questionsearch.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/waitedquestionlist.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/expertinfolist.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/question/questionFilter.js" type="text/javascript"></script>
	
		<!-- 兴趣模型 -->	
    <script src="<%=basePath %>js/interestmodel/interestmodellist.js" type="text/javascript"></script> 
    <script src="<%=basePath %>js/interestmodel/interestmodel.js" type="text/javascript"></script> 
    <script src="<%=basePath %>js/interestmodel/changeImCount.js" type="text/javascript"></script> 
    <script src="<%=basePath %>js/interestmodel/imknowledge-list-box.js" type="text/javascript"></script> 
	
	
	<!-- 统计分析 -->
	<script src="<%=basePath %>js/statistics/knowledgerank.js" type="text/javascript"></script>	
	<script src="<%=basePath %>js/statistics/userrank.js" type="text/javascript"></script>
	
	<!-- 系统管理 -->
	<script src="<%=basePath %>js/systemmanage/behaviormanage.js" type="text/javascript"></script>	

<title>
	编辑知识
</title>
<script>
var ntkoobj = null;
//var ntkoobj.filename=null;
function initDoc(docURL,filename){
	//得到控件对象变量
	ntkoobj = document.all("TANGER_OCX"); //"TANGER_OCX"是网页中的NTKO OFFICE文档控件的ID
	if(null == ntkoobj)
	{
		alert("NTKO OFFICE文档控件未能正确装载。请在本页面装载时窗口上方出现的提示条上点击安装ActiveX控件，并在随后出现的安装ActiveX的对话框中选择'是'");
		return;
	}
	//如果没有传入docURL，则创建一个Word文档对象。
	if( (null == docURL) || (0 == docURL.length) )
	{
		
	}
	else //否则，调用NTKO OFFICE文档控件的BeginOpenFromURL方法打开服务器上的文档
	{
		docURL=docURL+"?filename="+filename;
		ntkoobj.filename=filename;
		ntkoobj.OpenFromURL(docURL);
	}
	forbiddenMenu(ntkoobj);
}

	function forbiddenMenu(ntkoobj){
	
		ntkoobj.FileNew=false;
		ntkoobj.FileClose=false;
		ntkoobj.FileSaveAs=false;
		ntkoobj.FilePrint=false;
		ntkoobj.FilePrintPreview=false;
}




</script>

</head>

<body  onload='initDoc("${pageContext.request.contextPath}/knowledge/viewfile!docEdit.action","<%=sourcefilepath%>")'>


	<script src="${pageContext.request.contextPath}/js/knowledge/modify/jiazaizhengwen.js" ></script>
	<script language="JScript" for="TANGER_OCX" event="OnFileCommand(cmd,canceled)">
	//knowledge的file，ktypeid,knowledgeid
	var vktypeid = "<%=ktypeid%>";
	var vsourcefilepath = "<%=sourcefilepath%>";
	var vknowledgeid = "<%=knowledgeid%>";
	
		if (cmd == 3) //user has clicked on file save menu or button
		{
			var r=confirm("是确定保存？")
			
			if(r==true){
				var t=confirm("保存为新版本？")
				if(t==true){
					TANGER_OCX.SaveToURL("${ctx}/knowledge/fileupload!uploadForModify.action","filepathTemp","isNewVersion=true");
					transKinfo(true,vktypeid,vsourcefilepath,vknowledgeid);
				}else if(t==false){
					TANGER_OCX.SaveToURL("${ctx}/knowledge/fileupload!uploadForModify.action","filepathTemp","isNewVersion=false&filename="+ntkoobj.filename);
					transKinfo(false,vktypeid,vsourcefilepath,vknowledgeid);
				}
	
				document.all("TANGER_OCX").CancelLastCommand = true;
			}else{
				document.all("TANGER_OCX").CancelLastCommand = true;
			}			
			
		}
		
	function transKinfo(isNewV,ktypeid,sourcefilepath,knowledgeid){
	alert("22==="+ktypeid);
				//抽取与属性的匹配
			var properties = getData('/caltks/knowledge/ktype/ktype!listKtypeProperty.action',{id:ktypeid});

			getData_Async('/caltks/knowledge/fileupload!getfileinfor.action', {
				filename : vsourcefilepath
			}, function(text) {
				alert(11);
				dproperty = Edo.util.Json.decode(text);			
				if (dproperty == '文件格式不支持解析' || dproperty == '没有解析出结果') {
					alert('文件格式不支持解析,保存知识失败');

				} else {					
					getMatchedProperty(properties,dproperty,ktypeid,knowledgeid);				
				}				
				// 调用flash转换action
				cims201.utils.getData_Async('fileupload!flashconvert.action', {
							filename : sourcefilepath
						}, function(text) {
							var converresult = Edo.util.Json.decode(text);
							if (converresult == '转换成功') {
								alert("falsh转换成功");
							} else {
								alert("falsh转换失败");
							}						

						});

			});

		}		
	
		
// 采集文件中的数据，并与修改知识的类型的属性进行匹配
function getMatchedProperty(properties,dproperty,ktypeid,knowledgeid) {
	var notequaltype = true;
	var resultList = new Array();
	if (null != properties && dproperty != null) {

		if (dproperty != '文件格式不支持解析' && dproperty != '没有解析出结果'
				&& dproperty != '') {
			// 判断用户选择的知识类型和抽取出的知识类型是否一致，如果不一致提醒用户
		
			// 依次迭代给表单赋值
			for (var i = 0; i < properties.length; i++) {

				for (j = 0; j < dproperty.length; j++) {
					
					if (dproperty[j].name == properties[i].description
							&& properties[i].isVisible) {
						var result = {};
						result.name = dproperty[j].name;
						result.value = dproperty[j].value;
						resultList[resultList.length] = result;
					}
				}

			}

		}
		var warn = cims201.utils.getData('/caltks/knowledge/knowledge!simpleupdate.action',{formvalue:resultList,id:knowledgeid,ktypeid:ktypeid});
		if(warn!=null){
			alert(warn);
		}

	} else {
		// if(selectedktype!="在线知识")
		// {
		// //如果第一次通过书签没有抽取出知识的内容则第二次通过具体知识类型抽取
		//		
		//		
		// }
	}

}

//通过ajax想后台取数据
function getData(url,params){
//alert("util");
// 从服务器端获取树的数据
	var data;
	Edo.util.Ajax.request({
		url : url,
		type : 'post',
		params : params,
		async : false,
		onSuccess : function(text) {
			// text就是从url地址获得的文本字符串
			if(text == null || text == ''){
				data = null;
			}else{
		
				data = Edo.util.Json.decode(text);
			}			
		},
		onFail : function(code,a,b,c,d,e,f) {
			// code是网络交互错误码,如404,500之类
			if(code=="500")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统内部错误！是可能有误操作，如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=="403")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '您没有权限该操作！如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=="404")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统错误！没有找到该页面',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});				
			
			data = 'error'; 
		}
	});
	
	return data;
}

//通过ajax想后台取数据,通过异步方式
function getData_Async(url,params,callback){
// 从服务器端获取树的数据
	var data;
	Edo.util.Ajax.request({
		url : url,
		type : 'post',
		params : params,
		async : true,
		onSuccess : callback,
		onFail : function(code,a,b,c,d,e,f) {
			// code是网络交互错误码,如404,500之类
			if(code=500)
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统内部错误！是可能有误操作，如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=403)
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '您没有权限该操作！如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=404)
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统错误！没有找到该页面',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});				
			
			data = 'error'; 
		}
	});
	
	return data;
}
	</script>
	
		
		

</body>

</html>



