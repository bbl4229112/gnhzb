//显示作者相关知识的列表
function showAuthorKnowledgeList(id,name){ 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showAuthorKnowledgeList'+id;
	 	var queryForm = {name: 'kauthorid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
		if(null!=name&&name.length>4)
		name=name.substring(0,4)+"...";
		myTabItem.selected.name = name;
	 	//openNewTabFromChild(myTabItem,this);
	 	openNewTab('showAuthorKnowledgeList'+id, 'knowledgelist', name, {formvalue:queryFormStr});	
}
//显示作者相关知识的列表
function showUploaderKnowledgeList(id,name){ 	
	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showUploaderKnowledgeList'+id;
	 	var queryForm = {name: 'uploaderid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
			if(null!=name&&name.length>4)
		name=name.substring(0,4)+"...";
		myTabItem.selected.name = name;
	 	//openNewTabFromChild(myTabItem,this);
	 	openNewTab('showUploaderKnowledgeList'+id, 'knowledgelist', name, {formvalue:queryFormStr});	
}
//显示关键词对应的知识
function showKeywordKnowledgeList(id,name){
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showKeywordKnowledgeList'+id;
	 	var queryForm = {name: 'keywordid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
			if(null!=name&&name.length>4)
		name=name.substring(0,4)+"...";
		myTabItem.selected.name = name;
	 	//openNewTabFromChild(myTabItem,this);	
		openNewTab('showKeywordKnowledgeList'+id, 'knowledgelist', name, {formvalue:queryFormStr});	
}

//显示标签对应的知识
function showTagKnowledgeList(id,name){ 	
	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showTagKnowledgeList'+id;
	 	var queryForm = {name: 'utagid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
				if(null!=name&&name.length>4)
		name=name.substring(0,4)+"...";
		myTabItem.selected.name = name;
	 	//openNewTabFromChild(myTabItem,this);	
	 	openNewTab('showTagKnowledgeList'+id, 'knowledgelist', name, {formvalue:queryFormStr});	
}
//按照知识类型搜索知识
function showKnowledgetypeKnowledgeList(id,name){ 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showknowledgetypeKnowledgeList'+id;
	 	var queryForm = {name: 'knowledgetypeid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
				if(null!=name&&name.length>4)
		name=name.substring(0,4)+"...";
		myTabItem.selected.name = name;
	 	//openNewTabFromChild(myTabItem,this);	
	 	openNewTab('showTagKnowledgeList'+id, 'knowledgelist', name, {formvalue:queryFormStr});	
}
//显示大众标签对应的知识
function showPopTagKnowledgeList(id,name){ 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showPopTagKnowledgeList'+id;
	 	var queryForm = {name: 'tagid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
				if(null!=name&&name.length>4)
		name=name.substring(0,4)+"...";
		myTabItem.selected.name = name;
	 	//openNewTabFromChild(myTabItem,this);	
	 	openNewTab('showTagKnowledgeList'+id, 'knowledgelist', name, {formvalue:queryFormStr});	
}

//显示知识详情
function showKnowledgeDetail(id,name,messageId,borrowflowid){
	//标记知识的已读
	if(null==borrowflowid)
	borrowflowid=0;
	if(messageId){
		ChatManager.markAsReaded(messageId);
	}
	var myTabItem = {};
 	myTabItem.selected = {}; 
 	myTabItem.selected.id	= 'showKnowledgeDetail'+id;
 	 	
	myTabItem.selected.url= 'knowledge/ui!knowledgedetail.action?id='+id;
	
	myTabItem.selected.name = name;

 	//openNewTabFromChild(myTabItem,this);
	openNewTab('showKnowledgeDetail'+id, 'knowledgeview', name, {knowledgeID:id,borrowFlowId:borrowflowid});
	
}
//兴趣模型知识展示（附带表格刷新）
function showImKnowledgeDetail(id,name,messageId,borrowflowid){
	if(null==borrowflowid)
	borrowflowid=0;
	if(messageId){
		ChatManager.markAsReaded(messageId);
	}
	var myTabItem = {};
 	myTabItem.selected = {}; 
 	myTabItem.selected.id	= 'showKnowledgeDetail'+id;
 	 	
	myTabItem.selected.url= 'knowledge/ui!knowledgedetail.action?id='+id;
	
	myTabItem.selected.name = name;

 	myImTable1_rt.search();	
	openNewTab('showKnowledgeDetail'+id, 'knowledgeview', name, {knowledgeID:id,borrowFlowId:borrowflowid});
	
}

//显示专家的知识
function showHisInfo(expertid,expertname){ 
	openNewTab('showHisInfo'+expertid, 'showHisInfo', expertname+'专家', {expertId:expertid});
}


//显示域树或者分类树相关知识的列表
function showDomainOrCatagoryTree(id,name,type){ 
    var queryForm = {name:type,value:id};
	var searchlist = {searchlist: [queryForm]};
 	var queryFormStr = Edo.util.Json.encode(searchlist);

 	openNewTab('showDomainOrCatagoryTree_'+type+'_'+id, 'knowledgelist', name+'的相关知识', {formvalue:queryFormStr});
}

//将作者名字截短  
function concatName(name,length){
	var title_outStr = '';
	var l = 5;
	if(length){
		l = length;
	}
	if(null!=name&&name.length > l){
		title_outStr += name.substring(0,l)+'...';
	}else{
		title_outStr += name;
	}
	return title_outStr;
}


//删除某条知识
function unvisibleKnowledge(kid)
	{
	alert(kid);
			cims201.utils.getData_Async(
						'knowledge/knowledge!delete.action',
						{
							id : kid
						
						},function(text){
		   var result = Edo.util.Json.decode(text);	
		
		  
		      Edo.MessageBox.alert("消息", result, researchunapproalbox);  
		     	
		   
		     }
		);
		
	
	}
	
//隐藏某个问题
function unvisibleQuestion(kid)
	{
			cims201.utils.getData_Async(
						'knowledge/knowledge!hideKnowledge.action',
						{
							id : kid						
						},function(text){
		   var result = Edo.util.Json.decode(text);	  
		   Edo.MessageBox.alert("消息", result, myquestionresultbox);   	   
		     }
		);
		
		
	
	}
	
function researchunapproalbox()
		{

	unapprovalbox.reload();
		}
		
function myquestionresultbox()
		{

	myQuestionTable.reload();
		}
	
    //展示没有知识文档的一些知识的属性
	function createExpandPropertyList(knowledgeid){
		var screenh=cims201.utils.getScreenSize().height;
	    var propertylist = cims201.utils.getData('knowledge/knowledge!expandKPropertyAndValueList.action',{id:knowledgeid});
	  //  alert(propertylist.length);
	    
	   	var outReply = Edo.create({
				type: 'panel',	
				title:'知识相关属性—— 本文档没有FLash文件',
				border: [1,1,1,1],							
				padding: [0,0,0,0],	
				width:"100%",
				height:screenh-300,
				verticalScrollPolicy : "auto",
				layout: 'vertical',  		
				children: []
			});	
	   
	    
	    for (var i = 0; i < propertylist.length; i++) {
			var property = propertylist[i];

				// 判断是否可见
				var commonvisible = false;
				if (property.isVisible) {
					commonvisible = true;
				}

                if(property.value.indexOf('<table')!=-1){
                	
             
                	var expandLabel = Edo.create({
        				type : 'boxtext',
        				text :"<B>"+ property.description+"</B>: &nbsp; "+property.value,
        				height:1000,
        				width : "100%"
        			
        			});
				outReply.addChild(expandLabel);
	    }else
	    {
			var expandLabel = Edo.create({
				type : 'boxtext',
				text :"<B>"+ property.description+"</B>: &nbsp; "+property.value,
				width : "100%"
			
			});
			
			outReply.addChild(expandLabel);
	    	
	    	
	    }
			
		}
		
		
		
		this.getReply = function(){
			return outReply;
		}


	}
	
	//展示文章,江丁丁添加 2013-6-18
	function createArticleContent(knowledgeid){
	    
	    var propertylist = cims201.utils.getData('knowledge/knowledge!expandKPropertyAndValueList.action',{id:knowledgeid});
	  //  alert(propertylist.length);
	    
	   	var outReply = Edo.create({
				type: 'box',
				border: [0,0,0,0],		
				padding: [0,0,0,0],	
				width:'100%',
//				minHeight:500,
				height:500,
				layout: 'vertical',
				verticalScrollPolicy:'on',
				children: []
			});	
	   
	    
	    for (var i = 0; i < propertylist.length; i++) {
			var property = propertylist[i];

				// 判断是否可见
				var commonvisible = false;
				if (property.isVisible) {
					commonvisible = true;
				}

				if(property.description =="文章内容"||property.description =="新闻正文"){
					
					if(property.value.indexOf('<table')!=-1){
		                	
			             
		                	var expandLabel = Edo.create({
		        				type : 'boxtext',
		        				text :property.value,
//		        				height:'100%',
		        				width : '100%'
		        			
		        			});
						outReply.addChild(expandLabel);
					} else{
				    	
						var expandLabel = Edo.create({
							type : 'boxtext',
							text :property.value,
//							height:'100%',
							width : '100%'
						
						});
						
						outReply.addChild(expandLabel);
				    	
				    	
				    }
				}
	    }
		
		
		
		this.getReply = function(){
			return outReply;
		}

	}
	
	//修改知识
function modifybox(knowledgeId){
var screenh=cims201.utils.getScreenSize().height;
var filepath;
	var propertylist = cims201.utils.getData('knowledge/knowledge!kVisiblePropertyAndValueList.action',{id:knowledgeId});

	   	var outReply = Edo.create({
				type: 'box',	
				title:'知识修改相关属性',
				border: [0,0,0,0],							
				padding: [0,0,0,0],	
				width:"100%",
				height:screenh-300,
				verticalScrollPolicy : "auto",
				layout: 'vertical', 
				horizontalAlign : 'center',		
				children: []
			});	
			
	   
	    /*
	    for (var i = 0; i < propertylist.length; i++) {
			var property = propertylist[i];
			var expandLabel = Edo.create({
				type:'formitem',
				label :property.description+"  :",
				labelAlign : 'left',
				labelWidth : 90,
				children:[
					{type : 'text',
					 id:property.name,
					 text :property.value
					 }
					]
			
			});			
			outReply.addChild(expandLabel);	
		}
		*/
		
	
		
		// 根据用户选择的知识类型开始构建知识属性的表单
	for (var i = 0; i < propertylist.length; i++) {

			var formitems;
			if (propertylist[i].vcomponent == 'date'){
			
					function chkDate(v) {
		                   var t=this.text;
							for (var q = 0; q < propertylist.length;q++) {

								if (propertylist[q].name == this.name) {

									if (propertylist[q].isNotNull) {
										var vr = t.replace(/(^\s*)|(\s*$)/g, "");
										if (vr == ""){
											alert(propertylist[q].description+"不能为空");
											return "不能为空";
									}
										}
									var reg = /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/;

									if (t != "") {
									//	t = t.format('Y-m-d');
										var result = reg.exec(t);
										if (result == null){
											alert(propertylist[q].description+"请按照格式要求yyyy-mm-dd选择日期");
											return "请按照格式要求yyyy-mm-dd选择日期";
										}
									}
								if(null==v){
									var date=new Date(Date.parse(t.replace(/-/g, "/")));           
									Edo.get(this.name).setValue(date);
								
								}
								}
							}
						}
						var descript=propertylist[i].description
				if(propertylist[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								type : 'date',
								readOnly:true,
								width:550,
								valid : chkDate,
								id : propertylist[i].name,
								text:propertylist[i].value
							}]
				};
			}else if (propertylist[i].vcomponent == 'combo'){
				var templistvalue = propertylist[i].valuelist.split(',');
				var outcombodata = new Array();
					
				for(var t=0;t<templistvalue.length;t++){
					outcombodata[t] = {label:templistvalue[t],value:templistvalue[t]};
				}
		

				function chkCombo(v) {
					var count = 0;
					for (var t = 0; t < propertylist.length; t++) {
						if (propertylist[t].name == this.name) {

							for (var i = 0; i < propertylist[t].valuelist
									.split(',').length; i++) {
								if (v != propertylist[t].valuelist.split(',')[i]) {
									count++;
								}
							}
							if (count == propertylist[t].valuelist.split(',').length){
								alert(propertylist[t].description+"抽取出的值不在选择项中，请重新选");
								return "抽取出的值不在选择项中，请重新选择";
							}
							if (propertylist[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == ""){
									alert(propertylist[t].description+"不能为空");
									return "不能为空";
							}
								}
							//alert(Edo.get(this.name).getValue());
							if(Edo.get(this.name).getValue()==null)
							{
								Edo.get(this.name).setValue(v);
							}
						}
					}
				}
				var descript=propertylist[i].description
				if(propertylist[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								id : propertylist[i].name,
								type : 'combo',
								width:550,
								valid : chkCombo,
								readOnly:true,
								valueField: 'value',
							    displayField: 'label',
							    data: outcombodata,
							    text:propertylist[i].value
							}]
				};
			}else if (propertylist[i].vcomponent == 'textarea'){
				
				function chkTextarea(v) {
				//	alert('check');
					for (var t = 0; t < propertylist.length; t++) {

						if (propertylist[t].name == this.name) {
							if (propertylist[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == "")
									return "不能为空";
							}
							if (propertylist[t].length1 != null) {
								var len = propertylist[t].length1;
								var reg = /^.{1,len}$/;
								var result = reg.exec(v);
								if (result == null)
									return "超出字符长度";
							}
						}
					}
				}
	          var descript=propertylist[i].description
				if(propertylist[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								type : 'textarea',
								defaultHeight : 80,
								valid : chkTextarea,
								width:550,
								id : propertylist[i].name,
								text:propertylist[i].value
							}]
				};
			}else{
			//	alert(propertylist[i].name);
			//最后默认是text
							function chkText(v) {

					for (var t = 0; t < propertylist.length; t++) {

						if (propertylist[t].name == this.name) {

							if (propertylist[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == "")
									return "不能为空";
							}
							if (propertylist[t].length1 != null) {
								var len = propertylist[t].length1;
								var reg = /^.{1,len}$/;
								var result = reg.exec(v);
								if (result == null)
									return "超出字符长度";
							}
						}

					}
				}
			 var descript=propertylist[i].description
				if(propertylist[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								type : 'text',
								width:550,
								valid : chkText,
								id : propertylist[i].name,
								text:propertylist[i].value
							}]
				};
			
			}
			outReply.addChild(formitems);	

	}	
		
	
		
		
	////////////////////////////////////////////////////////////	
	//文件上传
	var attachupload;
	var uploadcmp;

	var attachitem = Edo.get('attachupload');
	if (null != attachitem) {
		attachupload = attachitem;

	} else {
		attachupload = {

			id : 'attachupload',
			type : 'panel',
			title:'源文件上传',

			children : [{
						id : 'attachuploader',
						type : 'fileupload',
						width : 618,
						swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明

							upload_url : 'knowledge/fileupload!sourcefileupload.action', // 上传地址
							flash_url : 'js/swfupload/swfupload.swf',
							flash9_url : "js/swfupload/swfupload_fp9.swf",
							button_image_url : 'js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
							file_types : '*.*', // 上传文件后缀名限制
							file_post_name : 'file', // 上传的文件引用名
							file_size_limit : '5000000'
						},

						onfilequeueerror : function(e) {
							alert("文件选择错误");
						},
						onfilequeued : function(e) {

							alert("文件选择成功");
							//this.upload();
						
							//e.upload.startupload(); // 也可以在选择文件后, 默认直接上传
							var ppp = uploadcmp.upload;
							ppp.startUpload();			
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
							Edo.get('modifyfileuploadnotice').set('text',currentFiles);
							Edo.get('attachupload').unmask();
						},
						onfilesuccess : function(e) {
							var succssfulFile = Edo.util.Json.decode(e.serverData);
																		
							var filename = succssfulFile.filename;
							filepath = succssfulFile.filepath;
							//alert("filename=="+filename);
							//alert("filepath==="+filepath);

							var currentFiles = ''
							currentFiles += succssfulFile.msg;												
							Edo.get('modifyfileuploadnotice').set('text',currentFiles);
							Edo.get('knowledgesourcefilepath1').set('text',filepath);
							flashconvert(filepath);
							Edo.get('attachupload').unmask();
						}
					}]

		};
				
	}

	outReply.addChild(attachupload);	
	var notice  = Edo.create({id: 'modifyfileuploadnotice', type: 'label', text: ''});
	outReply.addChild(notice);	
	var flashnotice  = Edo.create({id: 'modifyflashnotice', type: 'label', text: ''});
	outReply.addChild(flashnotice);	
	
	
	var assistcmp = Edo.create(				
					{type : 'text',
					 id:'knowledgesourcefilepath1',
					 visible:false,
					 text :''
					 });
	outReply.addChild(assistcmp);	
		
	uploadcmp = Edo.get('attachuploader');
	
	//上传成功后的文件转为flash
	function flashconvert(spath){
	//alert(11);
	cims201.utils.getData_Async('knowledge/fileupload!flashconvert.action', {
							filename : spath
						}, function(text) {
							var converresult = Edo.util.Json.decode(text);
							if (converresult == '转换成功') {
								//var succssfulFile = Edo.util.Json.decode(e.serverData);																		
							    Edo.get('modifyflashnotice').set('text',"转换flash成功");
							} else {
								 Edo.get('modifyflashnotice').set('text',"转换flash失败");
							}
							
						});
}
////////////////////////////////////////////////////////////////
	var button = {
		type : 'box',
		border : [0, 0, 0, 0],
		width:700,		
		layout : 'horizontal',
	    horizontalAlign : 'center',

		children : [
		/*{
					type : 'button',
					text : '覆盖老版本',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
						modifyknowledgesubmit(knowledgeId,outReply,modifykwin,"0");					
						modifykwin.hide();
					}
				},*/
				 {
					type : 'button',
					text : '保存新版本',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
						modifyknowledgesubmit(knowledgeId,outReply,modifykwin,"1");					
						modifykwin.hide();
					}
				}, {
					type : 'button',
					text : '重置',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
							alert("重置");
						outReply.reset();
					}
				}, {
					type : 'button',
					text : '取消',
					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
						modifykwin.hide();
					}
				}

		]

	};
	
	outReply.addChild(button);
	this.getoutReply =function(){
		return outReply;
	}

}

	

function modifyknowledgesubmit(kid,outReply,modifykwin,isNew)
	{
			
		var o = outReply.getForm();
        var json = Edo.util.Json.encode(o);
       // alert(json);
			
			cims201.utils.getData_Async(
						'knowledge/knowledge!simpleupdate.action',
						{
							id : kid,							
							json:json,
							isNewVersion:isNew
						},function(text){
		        var result = Edo.util.Json.decode(text);	   
		         creatalert(result,modifykwin);
		     	
		     
		     }
		);
		

	}
	
	
	