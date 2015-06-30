var ktypes;
var ktypename;
var property;
var equaltype = true;
var progressing = 0;
var uploadFileIndex;
var uploadFileNames;
var uploadFilePaths;
var citationTable;
var uuid;									
// 从服务器端获取类别信息

// 知识上传部分js
cims201.knowledge.batchUploadknowledgeInit = function(x) {
	uuid=x;
	ktypes = cims201.utils.getData('ktype/ktype!listAllktypeWithoutPage.action',{withoutCommon:true});	
	

	// 创建知识上传的整体表单
	var knowledgeFormpanelitem = Edo.get('knowledgeForm');
	if (null != knowledgeFormpanelitem) {

		Edo.get('knowledgeForm').destroy();

		knowledgeFormpanelitem = Edo.create({
					id : 'knowledgeForm',
					type : 'panel',
					title : '批量知识公共属性',
					render : document.getElementById("knowledgeform")

				});

	} else {

		knowledgeFormpanelitem = Edo.create({
					id : 'knowledgeForm',
					type : 'panel',
					title : '批量知识公共属性',
					render : document.getElementById("knowledgeform")

				});
	}
	
//	function chkDomain(v) {
//
//        v= Edo.get('domainnode').getValue();
//		var vr = v.replace(/(^\s*)|(\s*$)/g, "");
//		if (vr == ""){
//			
//			alert("知识域不能为空");
//			return "不能为空";
//		}
//	}
	var treeData = cims201.utils.getData(
			'../tree/privilege-tree!listPrivilegeTreeNodes.action',
			{
				treeType : 'domainTree',
//				disableInte:true,
				operationName : '上传知识'
			});
	
	var domaintree_select = new Edo.controls.TreeSelect().set({
				    id : 'domainnode',
				    type: 'treeselect',              
				    multiSelect: false,
				    displayField: 'name',
				    triggerPopup : false,
				    width:300,
				    valueField: 'id',
				    rowSelectMode: 'single',    
				    data: treeData,
				    treeConfig: {
				        treeColumn: 'name',
				        columns: [            
				            Edo.lists.Table.createMultiColumn(),
				            {id: 'name',header: '域名称',width:250, dataIndex: 'name'}
				        ]
				    },
				    onBeforetrigger : function() {
                        //  var test1=this.topContainercontainers;
				    	
                            window.parent.getWinfordtree(this.id,uuid);
						//	dtreewin = cims201.utils.getWinforcdtree(700, 400, "选择域名称",creatmodifydtreebox(this.id,"../","upload",null));
							
						}
				});

	formitems123 = {
		type : 'formitem',
		label : '知识域<font color=red>*</font>',
		labelWidth : 100,
		padding : [0, 0, 15, 10],
		labelAlign : 'left',
		children : [domaintree_select]
	};

	Edo.get('knowledgeForm').addChild(formitems123);
	
	var categoryData =  cims201.utils.getData(
			'../tree/privilege-tree!listPrivilegeTreeNodes.action',
			{
				treeType : 'categoryTree',
				operationName : '上传知识'
			});
	
	var category_select = new Edo.controls.TreeSelect().set({

		id : 'categories',
		type : 'categoryTree',
		multiSelect : true,
		displayField : 'name',
		triggerPopup : false,
		valueField : 'id',
		width : 300,
		data : categoryData,
		treeConfig : {
			treeColumn : 'name',
			columns : [Edo.lists.Table.createMultiColumn(),
					{
						id : 'name',
						header : '分类名称',
						width : 250,
						dataIndex : 'name'
					}]
		},
		onBeforetrigger : function() {
			 window.parent.getWinforctree(this.id,uuid);
			
		}
	});
	formitemscategory = {
	type : 'formitem',
	label : '知识分类<font color=red></font>',
	labelWidth : 100,
	padding : [0, 0, 10, 10],
	labelAlign : 'left',
		
	children : [category_select]
	};
	
	Edo.get('knowledgeForm').addChild(formitemscategory);
	
	
	// 定义附件上传事件
	var attachupload;

	var attachitem = Edo.get('attachupload');
	if (null != attachitem) {
		attachupload = attachitem;

	} else {
		attachupload = {

			id : 'attachupload',
			type : 'box',
			border : [0,0,0,0],
			children : [{
						id : 'attachuploader',
						type : 'fileupload',
						textVisible : false,
						width : 800,
						swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明

							upload_url : 'fileupload!sourcefileupload.action', // 上传地址
							flash_url : '../js/swfupload/swfupload.swf',
							flash9_url : "../js/swfupload/swfupload_fp9.swf",
							button_image_url : '../js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
							file_types : '*.doc;*.docx', // 上传文件后缀名限制
							file_post_name : 'file', // 上传的文件引用名
							file_size_limit : '5000000',
							post_params : {
								'uploadDir' : 'uploadDir'
							}
						},

						onfilequeueerror : function(e) {
							alert("文件选择错误:" + e.message);
						},
						onfilequeued : function(e) {

							// alert("文件选择成功");
						
							//e.upload.startupload(); // 也可以在选择文件后, 默认直接上传
							var ppp = cims201Upload.upload;
							ppp.startUpload();

							var jwief = 1;
						},

						onfilestart : function(e) {
							// alert("开始上传");
							Edo.get('attachupload').mask();
							// attachupload.mask();
						},
						onfileerror : function(e) {
							var currentFiles = Edo.get('successfulfiles').get('text');
							var succssfulFile = Edo.util.Json.decode(e.serverData);
							currentFiles += succssfulFile.msg;
							currentFiles += '<span style="color:red;">上传失败</span>';
							currentFiles += '<br>';
							
							Edo.get('successfulfiles').set('text',currentFiles);
							Edo.get('attachupload').unmask();
						},
						onfilesuccess : function(e) {
							Edo.get('ecs').set('text','<a href=\"javascript:extractconvertsave()\"><font size="4" color="blue">确定上传</font></a>');
							var succssfulFile = Edo.util.Json.decode(e.serverData);
							
							//首先创建上传文件的index
							if(uploadFileIndex == null){
								uploadFileIndex = 0;
							}else{
								uploadFileIndex = uploadFileNames.length;
							}
							
							//然后创建保存显示文件的数组
							if(uploadFileNames == null){
								uploadFileNames = new Array();
							}
							//创建保存文件路径的数组
							if(uploadFilePaths == null){
								uploadFilePaths = new Array();
							}											
							uploadFileNames[uploadFileIndex] = succssfulFile.filename;
							uploadFilePaths[uploadFileIndex] = succssfulFile.filepath;
							
							printUploadFiles();
							//e.serverData.msg																				
							
							Edo.get('attachupload').unmask();
						},
						onPropertychange: function(e){
							var ttt = e;
							var abd = 1;
						}
					}]

		};
				
	}
	// kproperties[kproperties.length] = attachupload;
	Edo.get('knowledgeForm').addChild(attachupload);
	// alert("定义附件上传结束");

	//var tt = Edo.get('attachuploader');
	//var ttupload = tt.upload;
	//var abcd = 1;
	cims201Upload = Edo.get('attachuploader');

	

	var button1;

	var buttonitem = Edo.get('loginBtn');
	if (null != buttonitem) {
		button1 = buttonitem;

	} else {
		button1 = {

			type : 'formitem',
			layout : 'horizontal',
			children : [{
						type : 'label',
						text : ''
					}, {
						type : 'space',
						width : 70

					}, {
						id : 'ecs',
						type : 'label',
						text : '<a href=\"javascript:extractconvertsave()\"><font size="4" color="blue">确定上传</font></a>'
					

				
					}]
		};

	}
	Edo.get('knowledgeForm').addChild(detailbox);
	Edo.get('knowledgeForm').addChild(button1);

	// 定义上传进度条
	var kuplaodprogress

	var kuplaodprogressitem = Edo.get('kuplaodprogress');
	if (null != kuplaodprogress) {
		kuplaodprogress = kuplaodprogressitem;

	} else {

		kuplaodprogress = {
			id : "kuplaodprogress",
			type : 'formitem',
			label : '上传进度: ',
			labelAlign : 'left',
			layout : 'horizontal',

			children : [{
						id : 'progress',
						type : 'progress',
						width : 450,
						onprogresschange : function(e) {
							progress.set({
										text : '上传中...'
									})
							if (e.progress == 100) {
								progress.set({
											text : '完成！'
										})
							}
						},
						render : document.getElementById('uploadfile')
					}]
		};
	}

}
//打印目前的上传文件链表 
function printUploadFiles(){
	//打印文件链表
	var currentFiles = '';
	for(var i=0;i<uploadFileNames.length;i++){
		
			currentFiles += '<span class="icon-cims201-delete" style="width:300px; cursor:pointer;" onclick="deleteUploadFile('+i+');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>文档《';
			currentFiles += uploadFileNames[i]; 
			currentFiles += '》&nbsp;&nbsp;<span style="color:blue;"></span>';
			if(i != (uploadFileNames.length-1) ){
				currentFiles += '<br>';	
			}
		}
	
	//设置form表单的值
	var successFilePaths = '';
	for(var i=0;i<uploadFilePaths.length;i++){
		successFilePaths += uploadFilePaths[i]+"#"+uploadFileNames[i];
		successFilePaths += '@';
	};
	
	document.getElementById('attachfile').value = successFilePaths;														
	Edo.get('filestatus').set('text',currentFiles);
}


//删除目前已经上传的文件
function deleteUploadFile(i){
	if(uploadFileNames != null && uploadFilePaths!= null){
		uploadFileNames.splice(i,1); 
	 	uploadFilePaths.splice(i,1);	
	}
	//打印目前的文件链表
	printUploadFiles();	 
}
var detailbox = Edo.get('detailbox');
if (null != detailbox)
	detailbox.destroy();
// 创建知识detail 面板
Edo.create({
	type : 'box',
	id : 'detailbox',
	verticalScrollPolicy: 'auto',
	horizontalScrollPolicy : 'auto',
	border : [1, 1, 1, 1],
	padding : [0, 0, 0, 0],
	width : 800,
	height : 250,
	layout : 'horizontal',
//	bodyStyle : 'background-color: #f8fcfb;',
	render : document.getElementById('afterupload'),
	children : [
		            {
					type : 'label',
					labelWidth : '30%',
					labelAlign : 'left',
					text : '文档状态：    '

				}, {
					id : 'filestatus',
					verticalScrollPolicy: 'on',
					type : 'label',
					labelWidth : '70%',
					labelAlign : 'left',
					text : ''

				}
//				]
//	}

	]
});


//pl 抽取、转换和保存所有工作
function extractconvertsave(){
	var statustemp;
	var status1;
	var status2;
	var status3;
	var filenamearray = new Array();
    v= Edo.get('domainnode').getValue();
	var vr = v.replace(/(^\s*)|(\s*$)/g, "");
	if (vr == ""){
	     Edo.MessageBox.alert("消息", "知识域不能为空!");
	}else if(uploadFileNames == null){
		Edo.MessageBox.alert("消息", "请选择待上传文件!");
	}else{
		Edo.get('ecs').set('text','<font size="4" color="grey">确定上传</font>');
		var sourcefilepatharray = new Array();
		for(var a = 0;a<uploadFileNames.length;a++){
//		alert(uploadFileNames.length);
//		alert(uploadFileNames[a]);
			filenamearray[a] = uploadFileNames[a];
			sourcefilepatharray[a] = uploadFilePaths[a];
		}
		var filenamestring = new String();
		var fileuploadresults = new String();
		for(var m = 0;m<filenamearray.length;m++){
//		filenamestring += "《"+filenamearray[m]+"》 ";
			fileuploadresults += '<font color=blue>文档《' + filenamearray[m] + '》载入完成!</font><font color=red><img src=../css/loader.gif>正在处理中</font><br>';
		}
		var status = Edo.get('filestatus');
		status.set('text', fileuploadresults);
		var sourcefilepathstring = new String();
		var statusstring = '';
		var statusstring2 = new String();
		var ktypenamestring = new String();
		
		for(var x = 0;x<sourcefilepatharray.length;x++){
			sourcefilepathstring += sourcefilepatharray[x]+" ";
		}
		
		var domainnodevalue= Edo.get('domainnode').getValue();
		var categorynode = Edo.get('categories').getValue();
		var y = 0;
		cims201.utils.getData_Async('fileupload!extractconvertsave.action', {
			filename : sourcefilepathstring,domainnode : domainnodevalue,categorynode : categorynode
		}, function(text) {
			resultarray = Edo.util.Json.decode(text);
			var resultarray = resultarray.split("@#");
			resultarray.remove((resultarray.length-1));
			for(var b = 0;b<resultarray.length;b++){
					propertyss = resultarray[b];
					if(propertyss != '' || propertyss != "" ){
						statusstring += '<font color=blue>'
							+ "文档《"+filenamearray[b]+"》载入完成!"+propertyss + '</font>';
						statusstring += '<br>';
					}
			}
			status.set('text', statusstring);
		}
		
		);
		uploadFileNames = new Array();
		uploadFileIndex = null;
		
	}
	
}
function submitform(){	
	for(var b = 0;b<propertysslist.length;b++){
		propertyss = propertysslist[b];
		alert(propertyss[0].value);
		
	}
	alert("ktypeid:"+ktypeid);
		if ( Edo.get('knowledgeForm').valid()) {
//							cims201.knowledge.setStepProgress(10, 99, 200);
							var formitemmm = Edo.get('knowledgeForm').getForm();

							var formvalue = Edo.util.Json.encode(formitemmm);
							document.getElementById("formvalue").value = formvalue;
							document.getElementById("knowledgeuplod").submit();

		}		


}

function deletecitationitem(rId){
	citationTable.data.source.each(function(o){
			if(rId == o.id){
				citationTable.data.remove(o);
			}
		}); 	
   }
