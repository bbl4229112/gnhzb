
// 快速上传页面
var uploadFileIndex;
var uploadFileNames;
var uploadFilePaths;
var propertyss;
var selectedktype;
var sourcefilepath;
var formitems;
var ctreewin;
var dtreewin;
var uuid;
// 初始化上传界面
cims201.knowledge.simpuploadknowledgeInit = function(x) {
uuid=x;
	var sourcefileupload = Edo.create({
		id : 'sourcefileuploader',
		type : 'fileupload',
		height : 300,
		width : 300,
		// visible :false,
		textVisible : false,
		swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明

			upload_url : 'fileupload!sourcefileupload.action', // 上传地址
			flash_url : '../js/swfupload/swfupload.swf',
			flash9_url : "../js/swfupload/swfupload_fp9.swf",
			button_image_url : '../css/uploadfile.jpg', // 按钮图片地址
			button_width : 158,
			button_height : 46,
			button_cursor : -2,
			button_window_mode : 'opaque',
			file_types : '*.xml;*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.gif;*.png;*.jpg;*.txt;*.pdf', // 上传文件后缀名限制
			file_post_name : 'file', // 上传的文件引用名
			file_size_limit : '50000',
			post_params : {
				'uploadDir' : 'uploadDir'
			}

		},

		onfilequeueerror : function(e) {
			alert("文件选择错误:" + e.message);
		},
		onfilequeued : function(e) {
			filename = e.filename;

			this.upload.startUpload();
		},

		onfilestart : function(e) {
			// 当文档开始上传 初始化文档detail展示页面

			cims201.knowledge.simpuploaddetail();

		},
		onfileerror : function(e) {
		},
		onfilesuccess : function(e) {
			var sourcefiledata = Edo.util.Json.decode(e.serverData);
			cims201.knowledge.changeupstatus(sourcefiledata);

		}

	});

	// 构建上传的文件的box 主要用于布局
	var uploadfie = Edo.create({

				id : 'sourcefileupload',
				type : 'box',
				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],
				height : 300,
				width : 1000,
				children : [{
							type : 'label',
							width : 200,
							height : 100,
							border : [0, 0, 0, 0],
							padding : [0, 0, 0, 0],
							text : ''
						}, {
							type : 'box',
							height : 200,
							width : '100%',
							border : [0, 0, 0, 0],
							padding : [0, 0, 0, 0],
							layout : 'horizontal',
							children : [{
										type : 'label',
										width : 420,
										height : 100,
										text : ''

									}, sourcefileupload]

						}]

			});
	// 上传文件面板 用于展示
	Edo.create({
				type : 'box',
				bodyStyle : 'background-color: #f8fcfb;',
				border : [1, 1, 1, 1],
				padding : [0, 0, 0, 0],
				width : 1000,
				height : 300,
				layout : 'horizontal',
				render : document.getElementById('upbox'),
				children : [uploadfie]
			});

}

var property;
var  ktypes = cims201.utils.getData(
			'ktype/ktype!listAllktypeWithoutPage.action', {
				withoutCommon : true
			});
// 初始化文档展示页面
cims201.knowledge.simpuploaddetail = function() {
	// 隐藏文档上传页面
	document.getElementById('beforeupload').style.height = '0';
	Edo.get('sourcefileuploader').set('height', 1);
	Edo.get('sourcefileuploader').set('width', 1);
	document.getElementById('afterupload').style.display = 'block';

	// 显示文档detail页面
	document.getElementById('notice').style.display = 'none';
	// alert(document.getElementById('afterupload'));
	// 获得当前知识类型列表构建comb

	var ktypescomb = Edo.create({

				id : 'typeselectcombo',
				type : 'combo',
				readOnly : true,
				// layout: 'horizontal',
				displayField : 'ktypeName',
				valueField : 'id',
				data : ktypes,
				selectedIndex : 0,
				width : 500,
				onSelectionChange : function(e) {
					selectedktype = this.selectedItem.ktypeName;
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

			});

	var detailbox = Edo.get('detailbox');
	if (null != detailbox)
		detailbox.destroy();
	// 创建知识detail 面板
	Edo.create({
		type : 'box',
		id : 'detailbox',
		border : [1, 1, 1, 1],
		padding : [0, 0, 0, 0],
		width : 1000,
		// height : 600,
		layout : 'vertical',
		bodyStyle : 'background-color: #f8fcfb;',
		render : document.getElementById('afterupload'),
		children : [
				// 创建文件上传状态 box

				{
			type : 'box',
			height : 100,

			border : [0, 0, 2, 0],
			padding : [0, 0, 0, 0],
			layout : 'horizontal',
			bodyStyle : 'background-color: #f8fcfb;',
			children : [{
						border : [0, 0, 0, 0],
						padding : [10, 0, 0, 0],
						type : 'formitem',
						labelWidth : 150,
						labelAlign : 'right',
						label : '文档状态：'

					}, {

						id : 'filestatus',
						border : [0, 0, 0, 0],
						padding : [10, 0, 0, 0],
						type : 'formitem',
						labelWidth : 850,
						minHeight : 100,
						labelAlign : 'left',
						label : '<font color=blue><img src=../css/loader.gif>文档《'
								+ filename + '》正在上传...</font>'
						// width:750

					}]
		}, {
			type : 'box',
			id : 'formbox',
			bodyStyle : 'background-color: #f8fcfb;',
			padding : [20, 0, 0, 0],
			border : [0, 0, 0, 0],
			children : [

			{
						type : 'formitem',
						border : [0, 0, 0, 0],
						padding : [5, 0, 20, 0],
						label : '请选择模板',
						labelWidth : 250,
						labelAlign : 'center',
						children : [ktypescomb]
					}

			]
		}

		]
	});

}
// 构建知识属性表单
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
			width : 1000,
			title : '知识属性'
				// render : document.getElementById("knowledgeform")

			});
	} else {

		knowledgeFormpanelitem = Edo.create({
			id : 'knowledgeForm',
			type : 'panel',
			title : '知识属性',
			width : 1000,
			padding : [20, 0, 0, 0],
			border : [0, 0, 0, 0]
				// render : document.getElementById("knowledgeform")

			});
	}
	var formbox = Edo.get('formbox');
	// alert(formbox);
	formbox.addChild(knowledgeFormpanelitem);

	var kproperties = new Array();
	// 根据用户选择的知识类型开始构建知识属性的表单

	for (var i = 0; i < property.length; i++) {
		// alert(property[i].name);
		// vcomponent
		// listvalue
		// property[i].vcomponent
		// 如果当前属性为可见的时候，需要根据不同的类型创建不同的formitem
		if (property[i].isVisible) { // 普通属性
			// alert(property[i].vcomponent);

			if (property[i].vcomponent == 'date') {

		
				
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
					labelWidth : 100,
					labelAlign : 'right',
					children : [{
								type : 'date',
								valid : chkDate,
								width : 800,
								readOnly : true,
								id : property[i].name
							}]
				};
			} else if (property[i].vcomponent == 'combo') {
				var templistvalue = property[i].valuelist.split(',');
				var outcombodata = new Array();
			
				for (var t = 0; t < templistvalue.length; t++) {
					outcombodata[t] = {
						label : templistvalue[t],
						value : templistvalue[t]
					};

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
				var descript=property[i].description;
				if(property[i].isNotNull)
				descript+='<font color=red>*</font>';
				if(property[i].name=='knowledgetype'){
				formitems = {
					type : 'formitem',
					label : descript,
					labelWidth : 100,
					labelAlign : 'right',
					children : [{
								id : property[i].name,
								type : 'combo',
								valid : chkCombo,
								selectedIndex :0,
								width : 800,
								readOnly : true,
								valueField : 'value',
								displayField : 'label',
								data : outcombodata
							}]
				};
				}
				else
				{
					formitems = {
							type : 'formitem',
							label : descript,
							labelWidth : 100,
							labelAlign : 'right',
							children : [{
										id : property[i].name,
										type : 'combo',
										valid : chkCombo,
										width : 800,
										readOnly : true,
										valueField : 'value',
										displayField : 'label',
										data : outcombodata
									}]
						};	
				}
				
				
			} else if (property[i].vcomponent == 'textarea') {

				function chkTextarea(v) {
				//	alert('check');
					for (var t = 0; t < property.length; t++) {

						if (property[t].name == this.name) {
							if (property[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == ""){
									alert(property[t].description+"不能为空");
									return "不能为空";
							}
								}
							if (property[t].length != null) {
								var len = property[t].length;
								if(v.length>len)
									{
									
									alert(property[t].description+"长度超限，不能超过"+len+"个字符");
									return property[t].description+"长度超限，不能超过"+len+"个字符";
									}
							//	var reg = /^.{1,len}$/;
							//	var result = reg.exec(v);
							//	if (result == null)
							//		return "超出字符长度";
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
					labelWidth : 100,
					labelAlign : 'right',
				
					children : [{
								type : 'textarea',
								valid : chkTextarea,
								defaultHeight : 40,
								width : 800,
								id : property[i].name
							}]
				};
			} else if (property[i].vcomponent == 'domaintree') {
				// 取树节点的数据
				function chkDomain(v) {

                       v= Edo.get('domainnode').getValue();
						var vr = v.replace(/(^\s*)|(\s*$)/g, "");
					if (vr == ""){
						
						alert("知识域不能为空");
						return "不能为空";
					}
				}
				var treeData = cims201.utils.getData(
						'../tree/privilege-tree!listPrivilegeTreeNodes.action',
						{
							treeType : 'domainTree',
							disableInte:true,
							operationName : '上传知识'
						});
				var domaintree_select = new Edo.controls.TreeSelect().set({
							id : property[i].name,
							type : 'treeselect',
							multiSelect : false,
							displayField : 'name',
							triggerPopup : false,
							width : 800,
							valueField : 'id',
							// rowSelectMode: 'single',
							data : treeData,
                            valid : chkDomain,
							treeConfig : {
								treeColumn : 'name',
								columns : [Edo.lists.Table.createMultiColumn(),
										{
											id : 'name',
											header : '域名称',
											width : 750,
										
											dataIndex : 'name'
										}]
							},
							onBeforetrigger : function() {
                             //  var test1=this.topContainercontainers;
								
                                 window.parent.getWinfordtree(this.id,uuid);
							//	dtreewin = cims201.utils.getWinforcdtree(700, 400, "选择域名称",creatmodifydtreebox(this.id,"../","upload",null));
								
							}
						});
				formitems = {
					type : 'formitem',
					label : property[i].description+'<font color=red>*</font>',
					labelWidth : 100,
					labelAlign : 'right',
					children : [domaintree_select]
				};

			} else if (property[i].vcomponent == 'catagorytree') {
				// 取树节点的数据

				var treeData = cims201.utils.getData(
						'../tree/privilege-tree!listPrivilegeTreeNodes.action',
						{
							treeType : 'categoryTree',
							operationName : '上传知识'
						});
				
				var domaintree_select = new Edo.controls.TreeSelect().set({
							id : property[i].name,
							type : 'treeselect',
							multiSelect : true,
							displayField : 'name',
							valueField : 'id',
							triggerPopup : false,
							width : 800,
							// rowSelectMode: 'single',
							data : treeData,

							treeConfig : {
								treeColumn : 'name',
								columns : [Edo.lists.Table.createMultiColumn(),
										{
											id : 'name',
											header : '分类名称',
											width : 772,
											dataIndex : 'name'
										}]
							},
							onBeforetrigger : function() {
								 window.parent.getWinforctree(this.id,uuid);
								
							}
						});
				formitems = {
					type : 'formitem',
					label : property[i].description,
					labelWidth : 100,
					labelAlign : 'right',
						
					children : [domaintree_select]
				};
			} else {
				// alert(property[i].name);
				// 最后默认是text
				function chkText(v) {

					for (var t = 0; t < property.length; t++) {

						if (property[t].name == this.name) {

							if (property[t].isNotNull) {
								var vr = v.replace(/(^\s*)|(\s*$)/g, "");
								if (vr == ""){
									
									alert(property[t].description+"不能为空");
									return "不能为空";
						
								}
								}
							if (property[t].length1 != null) {
								var len = property[t].length;
								if(v.length>len)
									{
									
									alert(property[t].description+"长度超限，不能超过"+len+"个字符");
									return property[t].description+"长度超限，不能超过"+len+"个字符";
									}
															
//								var len = property[t].length1;
//								var reg = /^.{1,len}$/;
//								var result = reg.exec(v);
//								if (result == null)
//									return "超出字符长度";
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
					labelWidth : 100,
					labelAlign : 'right',
					children : [{
								type : 'text',
								valid : chkText,
								width : 800,
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
			width: 775, 			
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
					labelWidth :100,
					labelAlign : 'right',
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
            width: 800,
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
                    width: 500,
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
		labelWidth :100,
		labelAlign : 'right',
		heitht:'auto',		
		children:[]
		});
		
	tablebox.addChild(citationTable);
	Edo.get('knowledgeForm').addChild(tablebox);

	// 定义附件上传事件
	var attachupload

	var attachitem = Edo.get('attachupload');
	if (null != attachitem) {
		attachupload = attachitem;

	} else {
		attachupload = {

			id : 'attachupload',
			type : 'box',
			padding : [0, 0, 0, 100],
			border : [0, 0, 0, 0],
			children : [{
						type : 'panel',
						id : 'attachlist',
						title : '上传附件列表',
						width : '100%',

						layout : 'horizontal',
						padding : [0, 0, 0, 0],
						children : [{
									id : 'successfulfiles',
									type : 'label',
									text : ''
								}]

					}, {
						id : 'attachuploader',
						type : 'fileupload',
						width : 800,
						padding : [50, 0, 0, 0],
						swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明

							upload_url : 'fileupload!fileupload.action', // 上传地址
							flash_url : '../js/swfupload/swfupload.swf',
							flash9_url : "../js/swfupload/swfupload_fp9.swf",
							button_image_url : '../js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
							file_types : '*.*', // 上传文件后缀名限制
							file_post_name : 'file', // 上传的文件引用名
							file_size_limit : '50000'
						},

						onfilequeueerror : function(e) {
							alert("文件选择错误:" + e.message);
						},
						onfilequeued : function(e) {

							// alert("文件选择成功");

							// e.upload.startupload(); // 也可以在选择文件后, 默认直接上传
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
							var currentFiles = Edo.get('successfulfiles')
									.get('text');
							var succssfulFile = Edo.util.Json
									.decode(e.serverData);
							currentFiles += succssfulFile.msg;
							currentFiles += '<span style="color:red;">上传失败</span>';
							currentFiles += '<br>';

							Edo.get('successfulfiles')
									.set('text', currentFiles);
							Edo.get('attachupload').unmask();
						},
						onfilesuccess : function(e) {
							var succssfulFile = Edo.util.Json
									.decode(e.serverData);

							// 首先创建上传文件的index
							if (uploadFileIndex == null) {
								uploadFileIndex = 0;
							} else {
								uploadFileIndex = uploadFileNames.length;
							}

							// 然后创建保存显示文件的数组
							if (uploadFileNames == null) {
								uploadFileNames = new Array();
							}
							// 创建保存文件路径的数组
							if (uploadFilePaths == null) {
								uploadFilePaths = new Array();
							}
							uploadFileNames[uploadFileIndex] = succssfulFile.filename;
							uploadFilePaths[uploadFileIndex] = succssfulFile.filepath;

							printUploadFiles();
							// e.serverData.msg

							Edo.get('attachupload').unmask();
						},
						onPropertychange : function(e) {
							var ttt = e;
							var abd = 1;
						}
					}]

		};

	}
	// kproperties[kproperties.length] = attachupload;
	Edo.get('knowledgeForm').addChild(attachupload);
	// alert("定义附件上传结束");

	// var tt = Edo.get('attachuploader');
	// var ttupload = tt.upload;
	// var abcd = 1;
	cims201Upload = Edo.get('attachuploader');

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
						width : 350

					}, {
						type : 'label',
						text : '<a href=\"javascript:submitform()\" style=\" font-size:16px;text-decoration: none; \"><img src=\'../css/finish.gif\' style=\'vertical-align:bottom;height:24px\'>&nbsp;&nbsp;完成知识上传</a>'

					}]
		};

	}

	Edo.get('knowledgeForm').addChild(button);
    var space=
    	{
    	type : 'space',
height:20 	};
Edo.get('knowledgeForm').addChild(space);
	setTimeout(cims201.knowledge.GetWordBookMarksValue, 1000);
	// cims201.knowledge.GetWordBookMarksValue();

}

function printUploadFiles() {
	// 打印文件链表
	var currentFiles = '';
	for (var i = 0; i < uploadFileNames.length; i++) {
		// currentFiles += '<span class="icon-cims201-delete"
		// style="width:300px; cursor:pointer;" onclick="deleteUploadFile('
		// + i
		// +
		// ');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
		currentFiles += '<span style="color:blue;">上传成功!</span>';
		currentFiles += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		currentFiles += '<img src=\'../css/attach.gif\'>';
		currentFiles += uploadFileNames[i];
		currentFiles += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		currentFiles += '<a href=\'javascript:deleteUploadFile(' + i
				+ ')\'>删除</a>';
		currentFiles += '<br>';
	}

	// 设置form表单的值
	var successFilePaths = '';
	for (var i = 0; i < uploadFilePaths.length; i++) {
		successFilePaths += uploadFilePaths[i] + "#" + uploadFileNames[i];
		successFilePaths += '@';
	};

	document.getElementById('attachfile').value = successFilePaths;
	Edo.get('successfulfiles').set('text', currentFiles);
}

// 删除目前已经上传的文件
function deleteUploadFile(i) {
	if (uploadFileNames != null && uploadFilePaths != null) {
		uploadFileNames.splice(i, 1);
		uploadFilePaths.splice(i, 1);
	}
	// 打印目前的文件链表
	printUploadFiles();
}
// 改变知识上传的状态 包括上传 抽取 转换flash
cims201.knowledge.changeupstatus = function(data) {
	var statustemp;
	var status1;
	var status2;
	var status3;
	var filename = data.filename;
	sourcefilepath = data.filepath;
	var status = Edo.get('filestatus');
	status1 = '<font color=blue>文档《' + filename + '》上传成功!</font><br>';
	statustemp = '<font color=red><img src=../css/loader.gif>正在转换抽取知识内容,您可以稍等也可以直接填写知识内容...</font>';
	status.set('label', status1 + statustemp);

	cims201.utils.getData_Async('fileupload!getfileinfor.action', {
				filename : sourcefilepath
			}, function(text) {
				propertyss = Edo.util.Json.decode(text);
				statustemp = '<font color=red><img src=../css/loader.gif>文档《'
						+ filename + '》正在被转换为flash可能需要一些时间,但不影响您知识上传...</font>';
				if (propertyss == '文件格式不支持解析' || propertyss == '没有解析出结果') {
					status2 = '<img src=../css/alert_s.png><font color=red>'
							+ propertyss + ',但不影响您继续上传</font><br>'

				} else {
					status2 = '<font color=blue>文档《' + filename
							+ '》内容抽取成功!</font><br>';
						if (selectedktype != null) {	
							cims201.knowledge.GetWordBookMarksValue();
				}
				}
//statustemp = '<font color=red><img src=../css/loader.gif>文档《'
			//			+ filename + '》正在被转换为flash可能需要一些时间,但不影响您知识上传...</font>';
				status.set('label', status1 + status2 + statustemp);
				// 调用flash转换action
				cims201.utils.getData_Async('fileupload!flashconvert.action', {
							filename : sourcefilepath
						}, function(text) {
							var converresult = Edo.util.Json.decode(text);
							if (converresult == '转换成功') {
								stauts3 = '<font color=blue>文档《' + filename
										+ '》转换为flash成功!</font>';
							} else {
								stauts3 = '<font color=red><img src=../css/alert_s.png>文档《'
										+ filename
										+ '》转换flash失败'
										+ converresult + '...</font>';
							}
							status.set('label', status1 + status2 + stauts3);

						});

//				if (selectedktype != null && selectedktype != '在线知识') {
//					Edo.MessageBox.show({
//								title : '是否载入文档抽取的内容？',
//								msg : '系统已经根据您上传的文件抽取出知识属性，载入当前表单吗?',
//								buttons : Edo.MessageBox.YESNO,
//								callback : isputvalue,
//								icon : Edo.MessageBox.QUESTION
//							});
//
//				}

			}

	);

}

function isputvalue(action, value) {

	if (action == 'yes') {

		cims201.knowledge.GetWordBookMarksValue();

	}
	if (action == 'no') {

	}

}

var iscontinue;

// 采集文件中的数据，显示出来
cims201.knowledge.GetWordBookMarksValue = function() {
//	alert("是simupload里面嘛？进来了没有呢？拿书签值");
	var notequaltype = true;

	if (null != property && propertyss != null) {

		if (propertyss != '文件格式不支持解析' && propertyss != '没有解析出结果'
				&& propertyss != '') {
			if (selectedktype == propertyss[0].value) {

				notequaltype = false;

			}
		//判断用户还没有选择过知识类型，则自动匹配抽取出的结果
		if (notequaltype&&selectedktype=='在线知识') {

	    for(var t=0;t<ktypes.length;t++)
		{
			var ktype=ktypes[t];
		if(ktype.ktypeName==propertyss[0].value)
			{
	    Edo.get("typeselectcombo").setValue(ktype.id);	
		break;
				
			}
		}
return null;
			}
			// 判断用户选择的知识类型和抽取出的知识类型是否一致，如果不一致提醒用户
			if (notequaltype&&selectedktype!='在线知识') {
				Edo.MessageBox.show({
							title : '提醒！知识模板不相符',
							msg : '您上传的文档知识模板与选择模板不符，还继续上传吗?(点击取消可以重新选择知识模板)',
							buttons : Edo.MessageBox.YESNOCANCEL,
							callback : isgoback,
							icon : Edo.MessageBox.QUESTION
						});
				if (iscontinue == false)
					return null;

			}
			// 依次迭代给表单赋值
			for (var i = 0; i < property.length; i++) {

				for (j = 0; j < propertyss.length; j++) {

					if (propertyss[j].name == property[i].description
							&& property[i].isVisible) {

						Edo.get(property[i].name).set('text',
								propertyss[j].value);
						break;
					}
				}

			}

			// }
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

function getKinforFromPOI() {

}

// confirm 确认回调函数
function isgoback(action, value) {
	if (action == 'yes') {

		cims201.knowledge.GetWordBookMarksValue();

	}
	if (action == 'no') {
		iscontinue = false;
		document.getElementById('afterupload').style.display = 'none';
		document.getElementById('notice').style.display = 'block';
		document.getElementById('beforeupload').style.height = '700';
		Edo.get('sourcefileuploader').set('height', 300);
		Edo.get('sourcefileuploader').set('width', 300);
		Edo.get('typeselectcombo').destroy();
		Edo.get('filestatus').destroy();
		Edo.get('formbox').destroy();
		// Edo.get('detailbox').destroy();
		// Edo.get('sourcefileuploader').destroy();

		selectedktype = null;
		propertyss = null;
		sourcefilepath = null;
		document.getElementById('attachfile').value = '';
		document.getElementById("ktypeid").value = '';
		// cims201.knowledge.simpuploadknowledgeInit();
	}
	if (action == 'cancel') {
		iscontinue = false;

	}
}

// 最终递交表单
function submitform() {

	if (formbox.valid()) {

		var formitemmm = Edo.get('knowledgeForm').getForm();
		var formvalue = Edo.util.Json.encode(formitemmm);
		document.getElementById("formvalue").value = formvalue;
		var citationIDs='';
		citationTable.data.source.each(function(o){
			if(citationIDs==''){citationIDs+=o.id}else{citationIDs+=';'+o.id;}
		});
		document.getElementById("citation").value = citationIDs;
		document.getElementById("sourcefilepath").value = sourcefilepath;
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

