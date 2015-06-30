/**
 * 发布博文js 
 * 江丁丁添加 2013-6-16
 */
var property;
var sourcefilepath;
var formitems;
cims201.knowledge.addArticleInit = function() {

      

		Edo.util.Ajax.request({
								url : '../knowledge/ktype/ktype!listKtypeProperty.action',
								type : 'post',
								params : {
									id : 20000
								},
								async : true,
								onSuccess : function(text) {
									// text就是从url地址获得的文本字符串

									property = Edo.util.Json.decode(text);									

									cims201.knowledge.buildArticleForm();

								},
								onFail : function(code) {
									// code是网络交互错误码,如404,500之类
									if (code = '404')
										alert("网络交互错误");
									if (code = '500')
										alert("系统内部错误");

								}
							});


};


cims201.knowledge.buildArticleForm = function() {

	// 创建知识上传的整体表单
	
	var articleFormpanelitem = Edo.get('articleform');
	if (null != articleFormpanelitem) {

		Edo.get('articleForm').destroy();

		articleFormpanelitem = Edo.create({
			id : 'articleForm',
			type : 'box',
			width : '100%',
			border : [0, 0, 0, 0],
			render : document.getElementById('afterupload')
			});
	} else {

		articleFormpanelitem = Edo.create({
			id : 'articleForm',
			type : 'box',
			width : '100%',
			render : document.getElementById('afterupload'),
			padding : [20, 0, 0, 0],
			border : [0, 0, 0, 0]

			});
	}


	var kproperties = new Array();
	// 根据用户选择的知识类型开始构建知识属性的表单

	
	for (var i = 0; i < property.length; i++) {
		// 如果当前属性为可见的时候，需要根据不同的类型创建不同的formitem
		if (property[i].name=='titlename'||property[i].name=='keywords'||property[i].name=='kauthors'||property[i].name=='domainnode'||property[i].name=='categories'||property[i].name=='knowledgetype') { //普通属性
//			alert(property[i].name);
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
					labelWidth : 50,
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
					labelWidth : 50,
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
					labelWidth : 50,
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
					type : 'formitem',
					children : [{
						id : property[i].name,
						type : 'text',
						text:'文章',
						visible:false
					}]
					
				};
				}else{
				formitems = {
					type : 'formitem',
					label : property[i].description,
					labelWidth : 50,
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
		 
			Edo.get('articleForm').addChild(formitems);

		}

	}
	
	
//	var  oFCKeditor = new  FCKeditor('articlecontent') ; 
//    oFCKeditor.BasePath  = "../js/fckeditor/"; 
//    oFCKeditor.Width = "850"; 
//    oFCKeditor.Height = "600"; 
//    oFCKeditor.ToolbarSet = "Default"; 
//    oFCKeditor.Config["SkinPath"] = "skins/silver/"; 
//    oFCKeditor.ReplaceTextarea(); 
	

		CKEDITOR.replace( 'articlecontent', {width:850,height:350,
			on: {
//				focus: onFocus,
//				blur: onBlur,
				// Check for availability of corresponding plugins.
				pluginsLoaded: function( evt ) {
					var doc = CKEDITOR.document, ed = evt.editor;
					if ( !ed.getCommand( 'bold' ) )
						doc.getById( 'exec-bold' ).hide();
					if ( !ed.getCommand( 'link' ) )
						doc.getById( 'exec-link' ).hide();
				}
			},filebrowserUploadUrl : '../uploader?Type=File',
            filebrowserImageUploadUrl : '../uploader?Type=Image',
            filebrowserFlashUploadUrl : '../uploader?Type=Flash'
		});
	

    
	var formitems_content = Edo.create({
			type : 'formitem',
//			id:'articlecontent',
			label :  '内容',
			labelWidth : 100,
			labelAlign : 'right',
			children : [
			            
			            ]
	});
//	Edo.get('articleForm').addChild(formitems_content);

	// 定义按钮事件
      

	  var buttonitem = document.getElementById('loginBtn');

	  var button = Edo.create({

			type : 'formitem',
			layout : 'horizontal',
			height:40,
			render: buttonitem,
			children : [ {
						type : 'label',						
						text : '<a href=\"javascript:articlesubmitform()\" style=\" font-size:16px;text-decoration: none; \"><img src=\'../css/finish.gif\' style=\'vertical-align:bottom;height:24px\'>&nbsp;&nbsp;发布博文</a>'

					}]
		});
		


//	Edo.get('articleForm').addChild(button);

};


function setTitle( editorInstance )
{
    var titlestring = editorInstance.GetHTML();
    if(titlestring=="<p>在这里添加 <strong>知识</strong>的内容。</p>"||titlestring=="<p>&nbsp;</p>"){
       
    }else if( document.getElementById("titlename").value==""){
       titlestring = titlestring.replace(/<br \/>/g, "\r\n");
       titlestring = titlestring.replace(/<br\/>/g, "\r\n");
       titlestring = titlestring.replace(/<br>/g, "\r\n");
       titlestring = titlestring.replace(/<\/br>/g, "\r\n");
       titlestring = titlestring.replace(/<.+?>/g, "");
       titlestring = titlestring.replace(/&nbsp;/g, "");
       titlestring = titlestring.replace(/&amp;/g, "");
       var endindex = titlestring.indexOf("。");
       if(endindex<0){
          endindex = titlestring.indexOf(".");
       }
       if(endindex<0){
          endindex = titlestring.indexOf("\r\n");
       }
       if(endindex<0){
          endindex = titlestring.indexOf("，");
       }
       if(endindex<0){
          endindex = titlestring.indexOf(",");
       }
       //alert(titlestring);       
       titlestring = titlestring.substring(0,endindex);
       document.getElementById("titlename").value = titlestring;
    }
    
}

function articlesubmitform(){
	
    if(Edo.get('articleForm').valid()) {
		var formitemmm = Edo.get('articleForm').getForm();
		var formvalue = Edo.util.Json.encode(formitemmm);
		document.getElementById("formvalue").value = formvalue;
		
		document.getElementById("sourcefilepath").value = "";
		document.getElementById("addarticle").submit();
	}

						


}

