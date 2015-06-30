function Ancestor(){};
Ancestor.prototype.toString=function(){
	return str="ancestor";
};
Ancestor.prototype.tabsArray=[];
Ancestor.prototype.createNewTab=function(index,title,content,icon){
	changeAccording(null,index);
	if(index==null||index==undefined){
		return
	}
	var isOpen=false;
	for(var i=0;i<this.tabsArray.length;i++){
		if(this.tabsArray[i]&&title==this.tabsArray[i].titl){
		   isOpen=true;
		   break;
		}
	}
	if(!isOpen){
		if(!content){
			content=new Object;
		}
		$("#tabs"+index).tabs('add',{
			title:title,
			content:content,
			closable:true
		});
		content.titl=title;
		this.tabsArray.push(content);
	}else{
		$("#tabs"+index).tabs('select',title);
	}
};
function com_zyct_setHeight()
{
	//alert("test");
	var selectedtab=$("#tabs0").tabs("getSelected");
	var ss = $(selectedtab).scrollTop(300);
	
	}
//Ancestor.prototype.uploadComp=function(buttonId){
//	var modulefactory=new moduleFactory();
//	var uploadbutton=modulefactory.createNode({
//		type:"div",
//		attr:{id:"spanButtonPlaceHolder"},
//		style:{width:"50px",height:"30px",background:"gray"}
//	});
//	var settings_object={
//			upload_url:"upload/upload!fileUpload.action",
//			flash_url:"js/swfupload25/swfupload_fp10/swfupload.swf",
//			flash9_url:"js/swfupload25/swfupload_fp9/swfupload_fp9.swf",
//			file_size_limit:"20MB",
//			file_types : "*.*",
//			file_types_description : "All Files",
//			file_upload_limit:0,
//			flash_width:"10px",
//			flash_height:"10px",
//			upload_success_handler:uploadSuccess,
//			upload_complete_handler:uploadComplete,
//			file_dialog_complete_handler:fileDialogComplete,
//			upload_error_handler:uploadError,
//			file_queued_handler:fileQueued,
//			upload_progress_handler:uploadProgress,
//			button_width:"80px",
//			button_height: "20px",
//			button_placeholder_id: buttonId,
//			button_text: "<span>选择相关文档</span>"
//			
//	   };
//	   swfu=new SWFUpload(settings_object);
//	return uploadbutton;
//};
Ancestor.data=function(){};
Ancestor.data.getA=function(){
	alert("");
};
Ancestor.prototype.perSePanel=function(inputblock,treeUrl,getUserUrl){
	if(!treeUrl){
		treeUrl="tree/tree!getQualifiedRoleNodes.action";
	}
	if(!getUserUrl){
		getUserUrl="user/role/userole!listUsersUnderRole.action";
	}
	var modulefactory=new moduleFactory();
	var penwin=modulefactory.createNode({
		type:"div",
		style:{width:"650px",height:"500px"}
	});

    var treePanel=modulefactory.createNode({
    	type:"div",
        style:{width:"310px",height:"99%",borderWidth:"1px",
               borderStyle:"solid",borderColor:"gray",styleFloat:"left",marginLeft:"0px",cssFloat:"left"}
    });
    var persontable=modulefactory.createNode({
    	type:"div",
    	style:{width:"310px",height:"99%",borderWidth:"1px",borderStyle:"solid",borderColor:"gray",
    		   marginLeft:"auto",marginRight:"0px",styleFloat:"right",cssFloat:"right"}
    });
    var roletree=modulefactory.createNode({
    	type:"ul"
    });
    var table=modulefactory.createNode({
    	div:"table"
    });
    persontable.appendChild(table);
    
    //设置table的相关参数
    $(table).datagrid({
    	
    	singleSelect:true,
    	columns:[[
    	         {field:'check',checkbox:true},
    	       //   {field:'code',title:'选择',width:150},  
    	          {field:'name',title:'姓名',width:270}
    	          ]]
           ,
    	onSelect:function(rowIndex, rowData){
    		$(penwin).dialog('close');
			inputblock.value=rowData.name;
			inputblock.perid=rowData.id;
			$(inputblock).validatebox('isValid');
    		
//    		rowData.code.checked=true;
//    		 $(penwin).dialog('close');
//			 inputblock.value=rowData.name;
//			 inputblock.perid=rowData.id;
//			 $(inputblock).validatebox('isValid');
    	}
    });

    treePanel.appendChild(roletree);
    penwin.appendChild(treePanel);
    penwin.appendChild(persontable);
 // window.top.document.body.appendChild(penwin);
    $(penwin).dialog({title:"选择负责人"});
  
    //异步获得组织域
    com_zyct_getDataSync(treeUrl,null,function(data){
    	treedata=dataTj(data);
    	$(roletree).tree({
    	   data:treedata,
    	   onClick:function(node){

    			   //同步获得后台数据
    		//	var data= com_zyct_getDataAsyn(getUserUrl,{id:node.id,size:30,index:0});
    		//	alert(JSONtoString(data));
    			$(table).datagrid('loadData',{rows:[]});
    			$(table).datagrid({
    				fit:true,
    				pagination:true,
    				pageSize:15,
    				pageList:[10,15,20,30],
    				pageNumber:1,
    				url:getUserUrl,
    				queryParams:{id:node.id},
    				onCheck:function(rowIndex,rowData){
    					$(penwin).dialog('close');
   					    inputblock.value=rowData.name;
   					    inputblock.perid=rowData.id;
   					    $(inputblock).validatebox('isValid');
    				}
    				});
//    			for(var i=0;data.rows&&i<data.rows.length;i++){
//    				 var radio=modulefactory.createNode({
//    				    	type:"input",
//    				    	attr:{type:"radio",name:"sel"}
//    				    });
//    				 radio.uname=data.rows[i].name;
//    				 radio.perid=data.rows[i].id;
//    				 $(radio).click(function(){
//    					 $(penwin).dialog('close');
//    					 inputblock.value=this.uname;
//    					 inputblock.perid=this.perid;
//    					 $(inputblock).validatebox('isValid');
//    				 });
//    				 $(table).datagrid('appendRow',{id:data.rows[i].id,code:radio,name:data.rows[i].name});
//    			 }   
    	   }
    	});
    });
  
};
var ancestor=new Ancestor();

Ancestor.prototype.getProgress=function(){
	var modulefactory=new moduleFactory();
	var progress=modulefactory.createNode({
		type:"div",
		style:{width:150}
	  });
	$(progress).progressbar({value:0});
	return progress;
};
Ancestor.prototype.getCheck=function(){
	var modulefactory=new moduleFactory();
	var check=modulefactory.createNode({
	   type:"input",
	   attr:{type:"radio",name:"mainFile"}
	});
	return check;
};
