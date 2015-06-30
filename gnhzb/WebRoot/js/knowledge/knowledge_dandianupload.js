
// 快速上传页面
var uploadFileIndex;
var uploadFileNames;
var uploadFilePaths;
var propertyss;
var savecount;
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
			file_types : '*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.gif;*.png;*.jpg;*.txt;*.pdf', // 上传文件后缀名限制
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

			cims201.knowledge.dandianuploaddetail();
			

		},
		onfileerror : function(e) {
		},
		onfilesuccess : function(e) {
			
			var sourcefiledata = Edo.util.Json.decode(e.serverData);
			
			cims201.knowledge.changeupstatus(sourcefiledata);
			cims201.knowledge.buildKnowledgeForm(sourcefiledata);

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

cims201.knowledge.batchuploadknowledgeInit = function(x) {
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
			file_types : '*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.gif;*.png;*.jpg;*.txt;*.pdf', // 上传文件后缀名限制
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

			cims201.knowledge.dandianuploaddetail();
			

		},
		onfileerror : function(e) {
		},
		onfilesuccess : function(e) {
			
			var sourcefiledata = Edo.util.Json.decode(e.serverData);
			
			cims201.knowledge.changeupstatus(sourcefiledata);
			cims201.knowledge.buildKnowledgeForm(sourcefiledata);

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

// 初始化文档展示页面
cims201.knowledge.dandianuploaddetail = function() {
	alert("里面"+filename);
	// 隐藏文档上传页面
	document.getElementById('beforeupload').style.height = '0';
	Edo.get('sourcefileuploader').set('height', 1);
	Edo.get('sourcefileuploader').set('width', 1);
	document.getElementById('afterupload').style.display = 'block';

	// 显示文档detail页面
	document.getElementById('notice').style.display = 'none';
	// alert(document.getElementById('afterupload'));
	// 获得当前知识类型列表构建comb


	

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
			height : 60,
	
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
						minHeight : 500,
						labelAlign : 'left',
						label : '<font color=blue><img src=../css/loader.gif>文档《'
								+ filename + '》正在上传...</font>'
						// width:750

					}]
		},
		{
				type : 'box',
				id : 'formbox',
				height : 100,
				labelAlign : 'left',
				bodyStyle : 'background-color: #f8fcfb;',
				padding : [10, 0, 0, 80],
				border : [0, 0, 0, 0],
				children : [
				]
		},
		{
			id : 'filestatus2',
			border : [0, 0, 0, 0],
			padding : [10, 0, 0, 0],
			type : 'formitem',
			labelWidth : 850,
			minHeight : 500,
			labelAlign : 'left'
		
			
			
		}

		]
	});

}
// 构建知识属性表单
cims201.knowledge.buildKnowledgeForm = function(data) {

	
	
	
	function chkDomain(v) {

        v= Edo.get('domainnode').getValue();
		var vr = v.replace(/(^\s*)|(\s*$)/g, "");
		if (vr == ""){
			
			alert("知识域不能为空");
			return "不能为空";
		}
	}
	
	var treeData = cims201.utils.getData(
			'../tree/tree!listDandianSubNode.action',
			{
				//treeType : 'domainTree',
				//disableInte:true,
				//operationName : '上传知识'
			});

	var domaintree_select = new Edo.controls.TreeSelect()
				.set({
				    id : 'domainnode',
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
		label : '知识域<font color=red>*</font>',
		labelWidth : 100,
		padding : [0, 0, 20, 0],
		labelAlign : 'left',
		children : [domaintree_select]
	};

	var formbox = Edo.get('formbox');
	formbox.addChild(formitems);
	

	

	
	var submitButton=Edo.create({
		type:'button',
		onclick:function(e){
			submitform(data);
		},
		text:'抽取并保存知识'
		
	});
	var buttonbox=Edo.create({
		type : 'box',
		id : 'buttonbox1',
		border : [0, 0, 0, 0],
		padding : [0, 0, 0, 160],
		horizontalAlign: 'center',
		children : [submitButton]
		});
	formbox.addChild(buttonbox);
		

}

function printUploadFiles() {}

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
	//statustemp = '<font color=red><img src=../css/loader.gif>正在抽取知识内容,您可以稍等也可以直接填写知识内容...</font>';
	//status.set('label', status1 + statustemp);
	status.set('label', status1 );

	

}

cims201.knowledge.dandianExtract=function (data,formitemmm){
	//alert(formitemmm.domainnode);
		//alert(formitemmm.categorynode);
	var statustemp;
	var status2;
	var status3;
	var filename = data.filename;
	sourcefilepath = data.filepath;
	var status = Edo.get('filestatus');
	statustemp = '<font color=red><img src=../css/loader.gif>正在抽取知识内容,您可以稍等也可以直接填写知识内容...</font>';
	status.set('label', statustemp);
	cims201.utils.getData_Async('fileupload!getdandianfileinfor.action', {
				filename : sourcefilepath,domainnode:formitemmm.domainnode,categorynode:formitemmm.categorynode
			}, function(text) {
				statustemp="";

				if (isNaN(text)) {
					status2 = '<img src=../css/alert_s.png><font color=red>'
							+ '抱歉，没有单点故障知识被抽取保存，</font><br>请查看您上传的文档是否正确，如果还有问题请及时联系管理员！';

				} else {
					status2 = '<font color=blue>文档《' + filename
							+ '》内容抽取成功!,共有'+text+'条单点故障知识被保存</font><br>';
			
				}

				status.set('label', status2 + statustemp);

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
cims201.knowledge.GetWordBookMarksValue = function() {}

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
function submitform(data) {
	if (formbox.valid()) {

		var formitemmm = Edo.get('formbox').getForm();

		cims201.knowledge.dandianExtract(data,formitemmm);
		var form=Edo.get('formbox');
		form.destroy();
	
	}

}

//
