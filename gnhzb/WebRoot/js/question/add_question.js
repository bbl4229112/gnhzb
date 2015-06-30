//本js是knowledge_simupload.js的子版本
var property;
var sourcefilepath;
var formitems;
cims201.knowledge.addQuestionInit = function() {


		Edo.util.Ajax.request({
								url : '../knowledge/ktype/ktype!listKtypeProperty.action',
								type : 'post',
								params : {
									id : 10000
								},
								async : true,
								onSuccess : function(text) {
									// text就是从url地址获得的文本字符串

									property = Edo.util.Json.decode(text);									

									cims201.knowledge.buildQuestionForm();

								},
								onFail : function(code) {
									// code是网络交互错误码,如404,500之类
									if (code = '404')
										alert("网络交互错误");
									if (code = '500')
										alert("系统内部错误");

								}
							});


}


cims201.knowledge.buildQuestionForm = function() {

	// 创建知识上传的整体表单
	
	var knowledgeFormpanelitem = Edo.get('knowledgeForm');
	if (null != knowledgeFormpanelitem) {

		Edo.get('knowledgeForm').destroy();

		knowledgeFormpanelitem = Edo.create({
			id : 'knowledgeForm',
			type : 'panel',
			width : 1000,
			border : [1, 1, 1, 1],
			render : document.getElementById('afterupload'),
			title : '发布问题'

			});
	} else {

		knowledgeFormpanelitem = Edo.create({
			id : 'knowledgeForm',
			type : 'panel',
			title : '发布问题',
			width : 1000,
			render : document.getElementById('afterupload'),
			padding : [20, 0, 0, 0],
			border : [1, 1, 1, 1]

			});
	}


	var kproperties = new Array();
	// 根据用户选择的知识类型开始构建知识属性的表单

	
	for (var i = 0; i < property.length; i++) {
		// 如果当前属性为可见的时候，需要根据不同的类型创建不同的formitem
		if (property[i].name=='titlename'||property[i].name=='keywords'||property[i].name=='domainnode'||property[i].name=='categories'||property[i].name=='questioncontent'||property[i].name=='knowledgetype') { //普通属性
			
		 if (property[i].vcomponent == 'textarea') {
			
				function chkTextarea(v) {
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
							}
						}
					}
				}
			
				formitems = {
					type : 'formitem',
					label : property[i].description,
					labelWidth : 100,
					labelAlign : 'right',
					valid : chkTextarea,
					children : [{
								type : 'textarea',
								valid:chkTextarea,
								defaultHeight : 200,
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
						'../tree/privilege-tree!listCDTreeNodes.action',
						{
							treeType : 'domainTree',
							operationName : '上传知识'
						});
				var domaintree_select = new Edo.controls.TreeSelect().set({
							id : property[i].name,
							type : 'treeselect',
							multiSelect : false,
							displayField : 'name',
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
							}
						});
				formitems = {
					type : 'formitem',
					valid:chkDomain,
					label : property[i].description,
					labelWidth : 100,
					labelAlign : 'right',
					children : [domaintree_select]
				};

			} else if (property[i].vcomponent == 'catagorytree') {
				// 取树节点的数据

				var treeData = cims201.utils.getData(
						'../tree/privilege-tree!listCDTreeNodes.action',
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
							if (property[t].length != null) {
								var len = property[t].length;
								if(v.length>len)
									{
									
									alert(property[t].description+"长度超限，不能超过"+len+"个字符");
									return property[t].description+"长度超限，不能超过"+len+"个字符";
									}
															
							}
						}

					}
				}
				if(property[i].name=='knowledgetype'){
					formitems = {
					id : property[i].name,
					type : 'text',
					visible:false,
					text:'问题'
				};
				}else{
				formitems = {
					type : 'formitem',
					label : property[i].description,
					labelWidth : 100,
					labelAlign : 'right',
					children : [{
								type : 'text',
								valid:chkText,
								width : 800,
								id : property[i].name
							}]
				};
				}

			}
			Edo.get('knowledgeForm').addChild(formitems);

		}

	}

	// 定义按钮事件
      var button;

	  var buttonitem = Edo.get('loginBtn');
	if (null != buttonitem) {
		button = buttonitem;

	} else {
		button = {

			type : 'formitem',
			layout : 'horizontal',
			height:40,
			children : [{
						type : 'label',
						text : ''
					}, {
						type : 'space',
						width : 350

					}, {
						type : 'label',						
						text : '<a href=\"javascript:questionsubmitform()\" style=\" font-size:16px;text-decoration: none; \"><img src=\'../css/finish.gif\' style=\'vertical-align:bottom;height:24px\'>&nbsp;&nbsp;发布问题</a>'

					}]
		};
		

	}

	Edo.get('knowledgeForm').addChild(button);

}

function questionsubmitform(){
		
						    if(Edo.get('knowledgeForm').valid()) {
							var formitemmm = Edo.get('knowledgeForm').getForm();
							var formvalue = Edo.util.Json.encode(formitemmm);
							document.getElementById("formvalue").value = formvalue;
							
							document.getElementById("sourcefilepath").value = "";
							document.getElementById("addquestion").submit();
							}

						


}