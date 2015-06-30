<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		

		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/js/edo/res/css/core.css" rel="stylesheet" type="text/css" />

		<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>

		
		


		
	</head>

	<body>
	</body>
</html>


<script type="text/javascript">

			

			//创建用户面板
			Edo.create({
   				 id: 'userForm',    
    			 type: 'panel',
    			 title: '用户录入',
   				 render: document.body,
   				 children: [
   				  	{
           				 type: 'formitem',
           				 label: '账号<span style="color:red;">*</span>:',
  						 children:[{type: 'text', id: 'email'}]
       				},
       				{
       					type: 'formitem',
       					label: '密码<span style="color:red;">*</span>:',
            			children:[{type: 'password', id: 'password'}]
       				},
       				{
       					type: 'formitem',
       					label: '姓名<span style="color:red;">*</span>:',
            			children:[{type: 'text', id: 'name'}]
       				},
       				{
           				 type: 'formitem',
           				 label: '爱好:',
           				 children:[
                				{type: 'textarea', id: 'hobby'
                				}
           					 ]
       				 },
       				 {
           				 type: 'formitem',
           				 label: '简介:',
            				children:[{type: 'textarea', id: 'introduction'}]
       				 },
       				 {
            			type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
            			children:[
              			  	{id: 'submitBtn', type: 'button', text: '提交表单'},
               				{type: 'space', width: 5},
                			{type: 'label', text: '<a href="javascript:resetForm();">重置</a>'}
            				]
        			}
       				 
   				  ]
   				 
   				
			});
			
			//事件监听
		submitBtn.on('click', function(e){
    		//验证表单
    	var json;
    	if(userForm.valid()){
        var o = userForm.getForm();    //获取表单值
        json = Edo.util.Json.encode(o);
        alert(json);    //可以用ajax发送到服务端
        
   		 }
   		 
   		 
   		 
		Edo.util.Ajax.request({
    		url: 'user!save.action', //发送到一个动态页面地址
    		type: 'post',           //使用Post方式,发送的数据可以突破255个字符限制
    		params: {               //发送的数据对象,可以是一个复杂对象
       			json: json
    		},
    	onSuccess: function(text){
        //...
    }
	});
	
	
	
	
});
    
</script>


