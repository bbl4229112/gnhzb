var ktypes;
var ktypename;
var property;
var equaltype = true;
var progressing = 0;
var uploadFileIndex;
var uploadFileNames;
var uploadFilePaths;
var citationTable;
									
// 从服务器端获取类别信息

// 知识上传部分js
cims201.knowledge.uploadknowledgeInit = function() {
	ktypes = cims201.utils.getData('ktype/ktype!listAllktypeWithoutPage.action',{withoutCommon:true});	
	

	// 构建知识类型选择菜单
	new Edo.containers.FormItem().set({
		type : 'formitem',
		label : '选择模板: ',
		labelAlign : 'left',
		layout : 'horizontal',

		render : document.getElementById('selTemplate'),
		// style: 'background:#ccc;',
		children : [{
			id : 'typeselectcombo',
			type : 'combo',
			readOnly : true,
			// layout: 'horizontal',
			displayField : 'ktypeName',
			valueField : 'id',
			data : ktypes,
			width : 400,
			onSelectionChange : function(e) {
				ktypename = this.selectedItem.ktypeName;
				var ktypeid = this.getValue();
				// 从服务器端获取类别属性信息
				Edo.util.Ajax.request({
							url : 'ktype/ktype!listKtypeProperty.action',
							type : 'post',
							params : {
								id : ktypeid
							},
							async : true,
							onSuccess : function(text) {
								// text就是从url地址获得的文本字符串

								property = Edo.util.Json.decode(text);
						      
				                cims201.knowledge.buildKnowledgeForm();

							},
							onFail : function(code) {
								// code是网络交互错误码,如404,500之类
								if (code = '404')
									alert("网络交互错误");
								if (code = '500')
									alert("系统内部错误");

							}
						});
                 document.getElementById("ktypeid").value = this.selectedItem.id;
				
			}
		}

		]

	});

}

// 根据知识类型选择，展示相应知识属性的表单
cims201.knowledge.buildKnowledgeForm = function() {
	// 创建知识上传的整体表单
	var knowledgeFormpanelitem = Edo.get('knowledgeForm');
	if (null != knowledgeFormpanelitem) {

		Edo.get('knowledgeForm').destroy();
		// var ppp = kproperties;
		// alert("knowledgeForm 注销成功");
		// alert("现在共有的表单的长度"+kproperties.length);

		knowledgeFormpanelitem = Edo.create({
					id : 'knowledgeForm',
					type : 'panel',
					title : '知识属性',
					render : document.getElementById("knowledgeform")

				});

		// Edo.get('knowledgeForm').relayout();
	} else {

		knowledgeFormpanelitem = Edo.create({
					id : 'knowledgeForm',
					type : 'panel',
					title : '知识属性',
					render : document.getElementById("knowledgeform")

				});
	}
	// Edo.get('knowledgeForm').set('children',kproperties);

	var kproperties = new Array();
	// 根据用户选择的知识类型开始构建知识属性的表单
	for (var i = 0; i < property.length; i++) {
	//	alert(property[i].name);
		//vcomponent
		//listvalue
		//property[i].vcomponent
		//如果当前属性为可见的时候，需要根据不同的类型创建不同的formitem
		if (property[i].isVisible) {
			//alert(property[i].vcomponent);
			var formitems;
			if (property[i].vcomponent == 'date'){
					function chkDate(v) {
						//if(null!=v){
		                   var t=this.text;
		                // var testvalue=this;
							for (var q = 0; q < property.length;q++) {

								if (property[q].name == this.name) {

									if (property[q].isNotNull) {
										var vr = t.replace(/(^\s*)|(\s*$)/g, "");
										if (vr == ""){
											alert(property[q].description+"不能为空");
											return "不能为空";
									}
										}
									var reg = /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/;

									if (t != "") {
									//	t = t.format('Y-m-d');
										var result = reg.exec(t);
										if (result == null){
											alert(property[q].description+"请按照格式要求yyyy-mm-dd选择日期");
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
						var descript=property[i].description
				if(property[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								type : 'date',
								readOnly:true,
								width:300,
								valid : chkDate,
								id : property[i].name
							}]
				};
			}else if (property[i].vcomponent == 'combo'){
				var templistvalue = property[i].valuelist.split(',');
				var outcombodata = new Array();
					
				for(var t=0;t<templistvalue.length;t++){
					outcombodata[t] = {label:templistvalue[t],value:templistvalue[t]};
				}
		

				function chkCombo(v) {
					var count = 0;
					for (var t = 0; t < property.length; t++) {
						if (property[t].name == this.name) {

							for (var i = 0; i < property[t].valuelist
									.split(',').length; i++) {
								if (v != property[t].valuelist.split(',')[i]) {
									count++;
								}
							}
							if (count == property[t].valuelist.split(',').length){
								alert(property[t].description+"抽取出的值不在选择项中，请重新选");
								return "抽取出的值不在选择项中，请重新选择";
							}
							if (property[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == ""){
									alert(property[t].description+"不能为空");
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
				var descript=property[i].description
				if(property[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								id : property[i].name,
								type : 'combo',
								width:300,
								valid : chkCombo,
								readOnly:true,
								valueField: 'value',
							    displayField: 'label',
							    data: outcombodata
							}]
				};
			}else if (property[i].vcomponent == 'textarea'){
				
				function chkTextarea(v) {
				//	alert('check');
					for (var t = 0; t < property.length; t++) {

						if (property[t].name == this.name) {
							if (property[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == "")
									return "不能为空";
							}
							if (property[t].length1 != null) {
								var len = property[t].length1;
								var reg = /^.{1,len}$/;
								var result = reg.exec(v);
								if (result == null)
									return "超出字符长度";
							}
						}
					}
				}
	          var descript=property[i].description
				if(property[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								type : 'textarea',
								defaultHeight : 80,
								valid : chkTextarea,
								width:300,
								id : property[i].name
							}]
				};
			}else if(property[i].vcomponent == 'domaintree'){
				//取树节点的数据
				function chkDomain(v) {

                       v= Edo.get('domainnode').getValue();
						var vr = v.replace(/(^\s*)|(\s*$)/g, "");
					if (vr == "")
						return "不能为空";

				}
				var treeData = cims201.utils.getData('../tree/privilege-tree!listPrivilegeTreeNodes.action',{treeType:'domainTree',disableInte:true,operationName:'上传知识'});
				var domaintree_select = new Edo.controls.TreeSelect()
				.set({
				    id : property[i].name,
				    type: 'treeselect',              
				    multiSelect: false,
				    displayField: 'name',
				    width:300,
				    valueField: 'id',
				    //rowSelectMode: 'single',    
				    data: treeData,
				      valid : chkDomain,
				    treeConfig: {
				        treeColumn: 'name',
				        columns: [            
				            Edo.lists.Table.createMultiColumn(),
				            {id: 'name',header: '域名称',width:250, dataIndex: 'name'}
				        ]
				    }
				});
				formitems = {
					type : 'formitem',
					labelWidth :80,
					label : property[i].description+'<font color=red>*</font>',
					children : [domaintree_select]
				};
				
			}else if(property[i].vcomponent == 'catagorytree'){
				//取树节点的数据
				
				var treeData = cims201.utils.getData('../tree/privilege-tree!listPrivilegeTreeNodes.action',{treeType:'categoryTree',operationName:'上传知识'});
				var domaintree_select = new Edo.controls.TreeSelect()
				.set({
				    id : property[i].name,
				    type: 'treeselect',              
				    multiSelect: true,
				    displayField: 'name',
				    valueField: 'id',
				    width:300,
				    //rowSelectMode: 'single',    
				    data: treeData,
				    
				    treeConfig: {
				        treeColumn: 'name',
				        columns: [            
				            Edo.lists.Table.createMultiColumn(),
				            {id: 'name',header: '分类名称',width:250, dataIndex: 'name'}
				        ]
				    }
				});
				formitems = {
					type : 'formitem',
					labelWidth :80,
					label : property[i].description,
					children : [domaintree_select]
				};
			}else{
			//	alert(property[i].name);
			//最后默认是textfield
							function chkText(v) {

					for (var t = 0; t < property.length; t++) {

						if (property[t].name == this.name) {

							if (property[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == "")
									return "不能为空";
							}
							if (property[t].length1 != null) {
								var len = property[t].length1;
								var reg = /^.{1,len}$/;
								var result = reg.exec(v);
								if (result == null)
									return "超出字符长度";
							}
						}

					}
				}
			 var descript=property[i].description
				if(property[i].isNotNull)
				descript+='<font color=red>*</font>';
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth :80,
					children : [{
								type : 'text',
								width:300,
								valid : chkText,
								id : property[i].name
							}]
				};
			
			}
			Edo.get('knowledgeForm').addChild(formitems);
			
		}

	}
	// alert("表单定义结束");
	//定义引证文献
	var searchCitations = Edo.create({
    		type: 'autocomplete', 
			width: 275, 			
			queryDelay: 500,
			url: 'knowledge/knowledge!listReferences.action',			
			popupHeight: '100',
			valueField: 'id', 
			displayField: 'titleName'		
    	});	
    	
    var resultList = new Array();
	var citations = Edo.create(
		       {
		 			type : 'formitem',
					label : '引证文献',
					labelWidth :80,
					layout:'horizontal',
					children : [searchCitations,
					   {
						type:'button',
						text:'+',
						onclick:function(e){
							var result = {};
							var mark = 0;
							if(searchCitations.getValue()!=null){
								result.name = searchCitations.get('text');
								result.id = searchCitations.getValue();
								resultList.each(function(o){
									if(o.id==result.id)
										mark++;
								});
								if(mark==0){
									resultList[resultList.length] = result;
								}
							}else{
								alert("没有相关文献，请从下拉菜单选取");
							}
													
							citationTable.data.load(resultList);
							}
						}
					]
		       }		
	           );	         
	Edo.get('knowledgeForm').addChild(citations);
   
    citationTable = Edo.create(
        {          
            type: 'table',
            headerVisible: false,
            horizontalLine : false,
    	    verticalLine : false,
            width: 300,
            height:'auto',
           // height: 200,
            columns:[{
                    headerText: '序号',
                    dataIndex: 'name',
                    width: 25,
                    headerAlign: 'center',                             
                    align: 'center',
                    renderer: function(v, r, c, i, data, t){
	             	var outStr = i+1;
	             	return outStr;
                    }                   
                },
                {
                    headerText: '题目',
                    dataIndex: 'name',
                    width: 250,
                    headerAlign: 'center'                           
                    //align: 'center'
                },
                {
		             headerText: '删除',
		             dataIndex: 'id',
		             headerAlign: 'center',  
		             width:20,               
		             align: 'center',
		             renderer: function(v,r){
			             	var outStr = '&nbsp';
			             	outStr += '<span class="icon-cims201-delete" onclick="deletecitationitem('+r.id+');">'; 
			             	outStr += '&nbsp';                                      	
                    	    outStr += '</span>';
		             	return outStr;
		             }
                }
                ]
           });

	var tablebox = Edo.create({
		type : 'formitem',
		label : '文献列表',
		labelWidth :80,
		heitht:'auto',		
		children:[]
		});
		
	tablebox.addChild(citationTable);
	Edo.get('knowledgeForm').addChild(tablebox);
	
	// 定义附件上传事件
	var attachupload;

	var attachitem = Edo.get('attachupload');
	if (null != attachitem) {
		attachupload = attachitem;

	} else {
		attachupload = {

			id : 'attachupload',
			type : 'box',

			children : [{
						type : 'panel',
						id : 'attachlist',
						title : '上传附件列表',
						width : '100%',
						//height: '200',
						layout : 'horizontal',
						padding : 2,
						children: [
							{id: 'successfulfiles', type: 'label', text: ''}
						]

					}, {
						id : 'attachuploader',
						type : 'fileupload',
						width : 500,
						swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明

							upload_url : 'fileupload!fileupload.action', // 上传地址
							flash_url : '../js/swfupload/swfupload.swf',
							flash9_url : "../js/swfupload/swfupload_fp9.swf",
							button_image_url : '../js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
							file_types : '*.*', // 上传文件后缀名限制
							file_post_name : 'file', // 上传的文件引用名
							file_size_limit : '5000000'
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
						width : 350,
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

	// kproperties[kproperties.length] = kuplaodprogress;
	Edo.get('knowledgeForm').addChild(kuplaodprogress);

	// alert('定义上传进度条结束')
	// Edo.create({
	// id : 'progress',
	// type : 'progress',
	// onprogresschange : function(e) {
	// progress.set({
	// text : '上传中...'
	// })
	// if (e.progress == 100) {
	// progress.set({
	// text : '完成！'
	// })
	// }
	// },
	// render : document.getElementById('uploadfile')
	// });

	// 定义按钮事件
	var button

	var buttonitem = Edo.get('loginBtn');
	if (null != buttonitem) {
		button = buttonitem;

	} else {
		button = {

			type : 'formitem',
			layout : 'horizontal',
			children : [{
						type : 'label',
						text : ''
					}, {
						type : 'space',
						width : 70

					}, {
						type : 'label',
						text : '<a href=\"javascript:submitform()\">上传知识</a>'
					

				
					}]
		};

	}
	// kproperties[kproperties.length] = button;
	Edo.get('knowledgeForm').addChild(button);

	// alert('定义提交按钮结束');

	// //创建知识上传的整体表单
	// var knowledgeFormpanelitem = Edo.get('knowledgeForm');
	// if (null != knowledgeFormpanelitem) {
	//
	// Edo.get('knowledgeForm').destroy();
	// var ppp = kproperties;
	// alert("knowledgeForm 注销成功");
	// alert("现在共有的表单的长度"+kproperties.length);
	//		
	// Edo.create({
	// id : 'knowledgeForm',
	// type : 'panel',
	// title : '知识属性',
	// render : document.getElementById("knowledgeform"),
	//			
	// children : kproperties
	// });
	// alert(123);
	//
	// // Edo.get('knowledgeForm').relayout();
	// } else {
	//
	// Edo.create({
	// id : 'knowledgeForm',
	// type : 'panel',
	// title : '知识属性',
	// render : document.getElementById("knowledgeform"),
	// children : kproperties
	// });
	// }
	// Edo.get('knowledgeForm').set('children',kproperties);
}

// 采集文件中的数据，显示出来
cims201.knowledge.GetWordBookMarksValue = function(obj) {
	alert("这里呢？");
	// setProgress(null,10);
	// setStepProgress(1,70,200);

	var word;
	var filename = obj.value;

	var filenametype = filename.substring(filename.lastIndexOf("."),
			filename.length);
	if (filenametype != '.doc' && filenametype != '.docx') {
		equaltype = false;
		alert('只支持word模板文档上传,文件格式不正确！');

		document.getElementById("knowledgeuplod").reset();
		// setProgress(null,0);
		return null;
	}

	try {
		word = new ActiveXObject("Word.Application");
	} catch (e) {
		word.Quit();
		alert("无法调用Office对象，请确保您的机器已安装了Office并已将本系统的站点名加入到IE的信任站点列表中！");
		return null;
	}

	try {
		word.Documents.Open(obj.value);
	} catch (e) {
		word.Quit();
		alert("上传文件格式不正确！");
		return null;
	}
	word.Application.Visible = false;
	var xmlNodes = word.ActiveDocument.XMLNodes;

	for (var i = 0; i < property.length; i++) {

		for (j = 0; j < xmlNodes.count; j++) {

			if (xmlNodes.Item(j + 1) == property[i].description
					&& property[i].name == 'KTYPEID') {

				if (ktypename == xmlNodes(j + 1).Range.Text) {
					// alert('知识类型一致，进行解析')

				} else {
					alert('知识模板不一致，文档选择错误')
					equaltype = false;
				}
				break;
			}
		}

	}

	if (equaltype) {

		for (var i = 0; i < property.length; i++) {
			// setStepProgress(step,null);
			// setStepProgress(step,progressing+step,200);
			for (j = 0; j < xmlNodes.count; j++) {
				if (xmlNodes.Item(j + 1) == property[i].description
						&& property[i].isVisible) {

					Edo.get(property[i].name).set('text',
							xmlNodes(j + 1).Range.Text);
					break;
				}
			}

		}
		// setProgress(null,80);

	}

	word.Quit();
}

cims201.knowledge.setProgress = function(step, progressnow) {
	if (progressnow != null) {
		progressing = progressnow;
		progressnow = null;
	}
	if (step != null)
		progressing += step;
	progress.set('progress', progressing);

}

cims201.knowledge.setStepProgress = function(step, progressEnd, intervalTime) {

	var t = setInterval(function() {
				progressing += step;// Math.floor(3/2);
				if (progressing > progressEnd) {
					clearInterval(t);
					return
				}

				progress.set('progress', progressing);
			}, intervalTime);
}

//打印目前的上传文件链表 
function printUploadFiles(){
	//打印文件链表
	var currentFiles = '';
	for(var i=0;i<uploadFileNames.length;i++){
		currentFiles += '<span class="icon-cims201-delete" style="width:300px; cursor:pointer;" onclick="deleteUploadFile('+i+');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
		currentFiles += uploadFileNames[i]; 
		currentFiles += '<span style="color:blue;">上传成功</span>';
		currentFiles += '<br>';	
	}
	
	
	//设置form表单的值
	var successFilePaths = '';
	for(var i=0;i<uploadFilePaths.length;i++){
		successFilePaths += uploadFilePaths[i]+"#"+uploadFileNames[i];
		successFilePaths += '@';
	};
	
	document.getElementById('attachfile').value = successFilePaths;														
	Edo.get('successfulfiles').set('text',currentFiles);
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
function submitform(){				
		if ( Edo.get('knowledgeForm').valid()) {
							cims201.knowledge.setStepProgress(10, 99, 200);
							var formitemmm = Edo.get('knowledgeForm').getForm();

							var formvalue = Edo.util.Json.encode(formitemmm);
							document.getElementById("formvalue").value = formvalue;
							var citationIDs='';
							citationTable.data.source.each(function(o){
								if(citationIDs==''){citationIDs+=o.id}else{citationIDs+=';'+o.id;}
							});
							document.getElementById("citation").value = citationIDs;
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
