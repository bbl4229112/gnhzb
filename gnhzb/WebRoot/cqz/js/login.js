Edo.build({
	id:'background',
	type:'app',
	layout: 'horizontal',
	border:[0,0,0,0],
	padding:[0,0,0,0],
	render: document.body,
	verticalAlign: 'middle',    //竖向位置
	//horizontalAlign: 'center',   //横向位置
    children:[
         {
        	 type: 	'label',width:'60%', height: '100%'
         },
		{
			 id: 'loginForm',
			 type: 'box',
			 border:[0,0,0,0],

			 children:[
			           {
				            type:'error', forId: 'loginForm', width: '100%'
				        },
				        {type: 'formitem', label: '用户名：',
				            children: [
				                {type: 'text',text :'superman', id: 'username',valid:noEmpty}            
				            ]
				        },
				        {type: 'formitem', label: '密码：',
				            children: [
				                {type: 'password',text :'superman', id: 'password',valid:noEmptyP}                
				            ]
				        },
				        {type: 'formitem',layout: 'horizontal',
				            children: [
				                {type: 'button', text: '登录', id: 'loginBtn'},
				                {type: 'button', text: '重置', id: 'resetBtn'}
				            ]
				        }
			 ]
		}    	
              
    ]
		    
});
		
loginBtn.on('click', function(e){
    if(loginForm.valid()){
    	var o =loginForm.getForm();   	
    	//console.log(o.username);
    	//var json = Edo.util.Json.encode(o);
    	Edo.util.Ajax.request({
		    url: 'user.action',
		    type: 'post',         
		    params: {               
		        username: o.username,
				password: o.password
		    },
		    onSuccess: function(text){
		    	window.location.href = 'user.jsp';
		        console.log(text);
		    },
		    onFail: function(code){
		        //code是网络交互错误码,如404,500之类
		        alert(code);
		        window.location.href = 'user.jsp';
		        console.log(text);
		    }
		});
    }
});

resetBtn.on('click',function(e){
	loginForm.reset();
	//username.setValue('');
	//password.setValue('');
});

function noEmpty(v){
    if(!v) return "账号不能为空";
}
function noEmptyP(v){
    if(!v) return "密码不能为空";
}