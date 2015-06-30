
function userinput(callback){
//创建用户输入面板

	var fileupload1 = Edo.create( {
            id: 'fileupload1',
            type: 'fileupload',         
            width: 173,
            swfUploadConfig: {              //swfUploadConfig配置对象请参考swfupload组件说明                
	          	upload_url : '../knowledge/fileupload!thumbnailfileupload.action', // 上传地址
				flash_url : 'js/swfupload/swfupload.swf',
				flash9_url : "js/swfupload/swfupload_fp9.swf",
				button_image_url : '../js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
				file_types : '*.gif;*.png;*.jpg', // 上传文件后缀名限制
				file_post_name : 'file', // 上传的文件引用名
				file_size_limit : '100'              
            },
            
            onfilequeueerror: function(e){
                alert("文件选择错误:"+e.message);
            },
            onfilequeued: function(e){
              fileupload1.upload.startUpload();
            
            
            
            },
            
            onfilestart: function(e){   
               //alert("开始上传");
                 //  box.mask();
            },
            onfileerror: function(e){
                alert("上传失败:"+e.message);
                
             //   box.unmask();
            },
            onfilesuccess: function(e){    
              	var succssfulFile = Edo.util.Json.decode(e.serverData);
           
           
            
               	var filepath=succssfulFile['filepath'];
              
               	var pwidth=succssfulFile.width;
               	
               	var pheight=succssfulFile.height;
          
               
                document.getElementById("target").innerHTML="<img height='300px' src='thumbnail/temp/"+filepath+"' id='jcrop_target' />";

                document.getElementById("target2").innerHTML="<img src='thumbnail/temp/"+filepath+"' id='preview' />";
                document.getElementById("thumbnailpath").value=filepath;
             
                document.getElementById("submitimg").style.display="block";
                 $(function(){

	$('#jcrop_target').Jcrop({
		onChange: showPreview,
		onSelect: showPreview,
		aspectRatio: 1
	});
	
	function showPreview(coords)
{
	if (parseInt(coords.w) > 0)
				{
					var rx = 100 / coords.w;
					var ry = 100 / coords.h;

					jQuery('#preview').css({
						width: Math.round(rx * (300/pheight)* pwidth) + 'px',
						height: Math.round(ry * (300/pheight)* pheight) + 'px',
						marginLeft: '-' + Math.round(rx * coords.x) + 'px',
						marginTop: '-' + Math.round(ry * coords.y) + 'px'
					});
				}
	            jQuery('#x').val(parseInt(coords.x* (pheight/300)));
				jQuery('#y').val(parseInt(coords.y* (pheight/300)));
			
				jQuery('#w').val(parseInt(coords.w* (pheight/300)));
				jQuery('#h').val(parseInt(coords.h* (pheight/300)));
	
	
	
}; 
	
	
	

});             	            
            }
                  });
	

	var iuser = Edo.create({
   				 //id: 'userForm',    
    			 type: 'panel',
    			 padding: 10,
    			 title: '创建用户',
    			 width :300,
    			 height:470,
   				 render: document.getElementById('uploader'),
   				 
   				 children: [
   				  	{
           				 type: 'formitem',
           				 label: '邮箱<span style="color:red;">*</span>:',
           				 labelWidth: 90,
  						 children:[{type: 'text', width : 170,id: 'email',valid:chkEmail}]
       				},
       				{
       					type: 'formitem',
       					label: '密码<span style="color:red;">*</span>:',
       					labelWidth: 90,
            			children:[{type: 'password', width : 170,id: 'password',valid: cims201.form.validate.leastLength}]
       				},
       				{
       					type: 'formitem',
       					label: '确认密码<span style="color:red;">*</span>:',
       					labelWidth: 90,
            			children:[{type: 'password', width : 170,id: 'repeatpassword',valid:chkPasswordconfirm}]
       				},
       				{
       					type: 'formitem',
       					label: '姓名<span style="color:red;">*</span>:',
       					labelWidth: 90,
            			children:[{type: 'text',width : 170, id: 'name',valid: cims201.form.validate.noEmpty}]
       				},
       				{
          				 type: 'formitem',label: '性别<span style="color:red;">*</span>:',labelWidth : 90,
            			 children:[{
            			    id: 'sex',
            				type: 'combo', 
            				width : 170,
            				valid: cims201.form.validate.noEmpty,
           					readOnly: true,              
            				displayField: 'text',
           	 				valueField: 'value',
            					data: [
                				{   text: '男' ,value: '男'},
                				{   text: '女' ,value: '女'}
           					 ]
            
        					}
            				]
        			},
       				
       				{
           				 type: 'formitem',
           				 label: '爱好:',
           				 labelWidth: 90,
           				 children:[
                				{type: 'textarea',width : 170,height:85, id: 'hobby',valid:cims201.form.validate.mostLength1}]
       				 },
       				 {
           				 type: 'formitem',
           				 label: '简介:',
           				 labelWidth: 90,           				 
            			children:[{type: 'textarea',width : 170,height:100,id: 'introduction',valid:cims201.form.validate.mostLength1}]
       				 },
       				 
       				        			
       				 {
           				 type: 'formitem',
           				 label: '上传头像:',
           				 labelWidth: 90,
            			 //children:[{type: 'text',width : 130, id: 'picturePath'}]
            		//	children:[ {
           			//		 type: 'panel',title:'上传用户头像',width: '100%', layout: 'horizontal', padding: 2,
            				 children:[fileupload1]
           
       				//	 } ]
       					 
       				 },
       				 {
           				 type: 'formitem',
           				 label: '头像路径:',
           				 labelWidth: 90,        
           				 visible:false,   				 
            			children:[{type: 'text',width : 170,height:100,id: 'picturePath'}]
       				 },
       				      			 
       				 {
            			type: 'formitem',layout:'horizontal', labelWidth: 90, padding: [8,0,8, 0],
            			children:[
              			  	{id: 'submitBtn', type: 'button', text: '提交表单'},
               				{type: 'space', width: 5},
                			{id: 'reset', type: 'button', text: '重置'}
            				]
        			}       				 
   				  ]
			});
			



			
//事件监听
submitBtn.on('click', function(e){

    //验证表单
    if(iuser.valid()){
    Edo.get('picturePath').set('text',thumbnailpath);
    //var fileld = cims201.utils.getData('knowledge/fileupload!cutthumbnail.action',{});

    
     var o = iuser.getForm();    //获取表单值
     var json = Edo.util.Json.encode(o);
       
     cims201.utils.getData('user!save.action',{json:json});
     callback();
    }
        
    
});

reset.on('click', function(e){
    iuser.reset();    
 });	

function chkPasswordconfirm(v){
	var password= Edo.get('password').text;
	if(password != v){
//showNoticeMessage("wrong","两次输入的密码不一致","passconfimWrap");
		return "两次输入的密码不一致";
}
}

function chkEmail(v){
	var userlist = cims201.utils.getData('user!listuser1.action',{});
	for (var i = 0; i < userlist.length; i++) {
		var user = userlist[i];
		var descr = user['email'];
		if(v == descr){
		return "邮箱已存在，请重新输入";
		}
	}
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{1,}){1,})$/;
	var result = reg.exec(v);
	if(result == null) 
		return "只能输入字母、数字和下划线";
}

this.getUserinput = function(){
	return iuser;
}	
			
	
}
