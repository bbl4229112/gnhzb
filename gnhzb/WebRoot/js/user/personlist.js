
function createPersonList2(){
var myWin = null;
var myUserinput = null;
var myModelinput = null;

var person = cims201.utils.getData('user!listperson.action',{});
	
var iuser = Edo.create({
    type: 'panel',        
    title: '用户个人信息', 
    id: 'userInformationBox',              
    height: 550,
    width: 720, 
    layout: 'absolute',
    border: 1,                  
    padding: 10,   
    render: document.getElementById('plist'),          
    
    children: [
        {
    	type: 'box', 
    	     
	    border: [0,0,0,0],
	    padding: [5,5,5,5],
	    height: 145,
        width: 145, 
        left:40,
        top: 10,
		children: [
				//src="userIcon/xizi2.jpg"
				{type: 'label', id:'personimg' , text: '<img src=thumbnail/'+person.picturePath+' height=120></img>'}
				
			]
	   		
		},
		{
    	type: 'box',        
	    border: [0,0,0,0],
	   // padding: [0,0,0,0],
	    height: 50,
        width: 140, 
        left:15,
        top: 130,
 	    children: [	
	    {
			type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
			children:[
    			{id: 'modifypic', 
    			type: 'button', 
    			text: '修改头像',
    			onClick:function(e)
    			{
    			showfileupload();
    			}
    			
    			}
			]
		}         
	    ]  		
		},
		{
    	type: 'box',        
	    border: [0,0,0,0],
	    padding: [10,10,10,10],
	    height: 120,
        width: 430, 
        left:220,
        top: 20,
        children: [
	        {
				type: 'formitem',
				label: '姓名:',
				labelWidth: 90,
		  		children:[{
		  		type: 'label',
		  		width : 130, 
		  		id: 'name_person',
		  		text:person.name
		  		}]
			},
			{
				type: 'formitem',label: '性别:',labelWidth : 90,
				children:[{
			    id: 'sex_person',
				type: 'label', 
				width : 130,            				
				text:person.sex                       				            
			    }]
			},
			{
   				 type: 'formitem',
   				 label: '邮箱:',
   				 labelWidth: 90,
   				 children:[{
   				 type: 'label', 
   				 width : 130,
   				 id: 'email_person',
   				 text:person.email
   				 }]
			}
		]  		
		},
		{
    	type: 'box',        
	    border: [0,0,0,0],
	    padding: [10,10,10,10],
	    height: 130,
        width: 540, 
        left:75,
        top: 170,
        children: [
        	{
			 type: 'formitem',
			 label: '爱好:',
			 labelWidth: 50,
			 children:[
    				{type: 'textarea',
    				width :450,
    				height:100,
    				 id: 'hobby_person',
    				text:person.hobby,
    				readOnly: true
    				 }]
			}
	    ]  		
		},
		{
    	type: 'box',        
	    border: [0,0,0,0],
	    padding: [10,10,10,10],
	    height: 140,
        width: 540, 
        left:75,
        top: 300,
        children: [
        	{
			 type: 'formitem',
			 label: '简介:',
			 labelWidth: 50,           				 
			 children:[{type: 'textarea',
					 width :450,
					height:110,
					id: 'introduction_person',
					text:person.introduction,
					readOnly: true
					}]
			}	
  	    ]  		
		},
		{
    	type: 'box',        
	    border: [0,0,0,0],
	    padding: [10,10,10,10],
	    height: 50,
        width: 200, 
        left:225,
        top: 430,
 	    children: [	
	    {
			type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
			children:[
    			{id: 'modify', 
    			type: 'button', 
    			text: '修改个人基本信息'}
			]
		}         
	    ]  		
		}
     ]
    
});


modify.on('click', function(e){

	if(myWin == null){
		myUserinput = new userinput(function(){
		
			myWin.hide();
						
			person = myUserinput.getUserinput().getForm();
			Edo.get('name_person').set('text',person['name']);
			Edo.get('sex_person').set('text',person['sex']);
			Edo.get('email_person').set('text',person['email']);
			Edo.get('hobby_person').set('text',person['hobby']);
			Edo.get('introduction_person').set('text',person['introduction']);
			Edo.get('picturePath_person').set('text',person['picturePath']);
					
		});
		
		myWin = new cims201.utils.getWin(263,370,"修改用户窗口",myUserinput.getUserinput());
	}
	myUserinput.getUserinput().setForm(person);
	Edo.get("email").set('readOnly',true);
	Edo.get("pictureword").set('visible',false);
	
	
	myWin.show(400,100,true);
});	
 
	var fileupoad1=Edo.create( {
            id: 'fileupload1',
            padding: [20,0,10,10],
            type: 'fileupload',  
           
            width: 272,
       //     visible:false,
            swfUploadConfig: {              //swfUploadConfig配置对象请参考swfupload组件说明                
	          	upload_url : '../knowledge/fileupload!thumbnailfileupload.action', // 上传地址
				flash_url : 'js/swfupload/swfupload.swf',
				flash9_url : "js/swfupload/swfupload_fp9.swf",
				button_image_url : '../js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
				file_types : '*.gif;*.png;*.jpg;*.bmp', // 上传文件后缀名限制
				file_post_name : 'file', // 上传的文件引用名
				file_size_limit : '2000'              
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
          
               
                document.getElementById("target").innerHTML="头像预览<br><img height='300px' src='thumbnail/temp/"+filepath+"' id='jcrop_target'/>";

                //document.getElementById("target2").innerHTML="<img src='thumbnail/temp/"+filepath+"' id='preview' />";
                document.getElementById("thumbnailpath").value=filepath;
             
                document.getElementById("submitimg").style.display="block";
                jQuery('#jcrop_target').css({
						width: '240px',
						height: '240px',
						marginLeft: '-0px',
						marginTop: '-0px'
					});
			
//	            jQuery('#x').val(0);
//				jQuery('#y').val(0);
			
//				jQuery('#w').val(120);
//				jQuery('#h').val(120);
                
//                
//                 $(function(){
//
//	$('#jcrop_target').Jcrop({
//		onChange: showPreview,
//	//	onSelect: showPreview,
//		aspectRatio: 1
//	});
//	
//	function showPreview(coords)
//{
//	if (parseInt(coords.w) > 0)
//				{
//					var rx = 100 / coords.w;
//					var ry = 100 / coords.h;
//
//					jQuery('#preview').css({
//						width: '120px',
//						height: '120px',
//						marginLeft: '-0px',
//						marginTop: '-0px'
//					});
//				}
//	            jQuery('#x').val(0);
//				jQuery('#y').val(0);
//			
//				jQuery('#w').val(120);
//				jQuery('#h').val(120);
//	
//	
//	
//}; 
//	
//	
//	
//
//});             	            
            }
                  });
	 Edo.create({
       				     id:'uploadpanel',
           				 type: 'panel',
           				 title:'选择头像',
           				 visible:false,
                         render:document.getElementById('uploader'),    
           				 width: '100%',
           				 layout: 'horizontal',
           				 labelWidth: 150,
           				 padding: 2, 
           				 children:[fileupload1]

       				 });
}	

function submitcutthumbnail()
{

var thumbnailpath=document.getElementById('thumbnailpath').value;
//alert("图片路径为"+thumbnailpath);
var x=document.getElementById('x').value;
var y=document.getElementById('y').value;
var h=document.getElementById('h').value;
var w=document.getElementById('w').value;
//var result= cims201.utils.getData('knowledge/fileupload!cutthumbnail.action',{x:x,y:y,pwidth:w,pheight:h,filename:thumbnailpath});
var result= cims201.utils.getData('knowledge/fileupload!cutthumbnail.action',{filename:thumbnailpath});

Edo.get('personimg').setValue('<img src=thumbnail/'+thumbnailpath+' height=120></img>');
Edo.get('uploadpanel').set('visible',false);
document.getElementById('target').style.display="none";
document.getElementById('target2').style.display="none";
document.getElementById('submitimgdiv').style.display="none";
}

function showfileupload(){
Edo.get('uploadpanel').set('visible',true);
document.getElementById('target').style.display="block";
document.getElementById('target2').style.display="block";
document.getElementById('submitimgdiv').style.display="block";

}