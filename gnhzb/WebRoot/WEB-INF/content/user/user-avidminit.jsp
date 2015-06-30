<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国运载火箭技术研究院知识服务平台</title>
		<link href="../js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		
		<script src="../js/edo/edo.js" type="text/javascript"></script>
		 <script src="../js/cims201.js" type="text/javascript"></script>
        <script src="../js/utils.js" type="text/javascript"></script>

	</head>

	<body>

	</body>
</html>

<script>
	
	//用来提示用户是否真的需要构建所以
    	Edo.MessageBox.show({
								title : '提醒！',
								msg : '初始化用户，请慎重，是否继续',
								buttons : Edo.MessageBox.YESNO,
								callback : isgoback,
								icon : Edo.MessageBox.WARNING
							});
	  //confirm 确认回调函数
function isgoback(action, value) {
	if(action=='yes')
		{
	

       	    
       	    setTimeout(function(){
       	       
          	    Edo.MessageBox.show({
       	   title:'请稍等' ,
           msg: '系统正在初始化用户，请稍等...',
           progressText: 'Saving...',
           width:300,
           wait:true,
           interval: 200,
           enableClose:false,
           icon: Edo.MessageBox.INFO
       });
       	    var url="<%=basePath%>/user/user!saveAvidmUser.action";
	        cims201.utils.getData_Async(url,null,function(text){ 
            var result=Edo.util.Json.decode(text);
            Edo.MessageBox.hide();
            Edo.MessageBox.alert("消息", result, showResult);  
          });
        },200) ;
			}
		if(action=='no')
		{
		
		}	
		
}
function showResult(action, value) {
}	
	
	
</script>


