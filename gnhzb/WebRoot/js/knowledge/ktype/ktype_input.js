
// 首先从后台接受属性列表
cims201.knowledge.ktypeinput.Init=function(){
   var propertylist;
  
    cims201.utils.getData_Async('ktype!creatktypeinput.action',{},function(text){
    	propertylist = Edo.util.Json.decode(text);
    	cims201.knowledge.ktypeinput.builtform(propertylist);
    	
    });
  
}
cims201.knowledge.ktypeinput.builtform = function(p) {
	var propertylist=p;
	// 填充common属性在表单中 by hebi
	var comboxindex = 0;
	var textindex = 0;
	var expandpropertyList = new Array();
	var commonpropertyList = new Array();
	var commonpList = new Array();
	var expandpList = new Array();
 
    
//	Edo.util.Ajax.request({
//				type : "post", // 交互方式:get,post
//				url : '/knowledge/ktype!creatktypeinput.action', // 数据源地址
//				async : false, // 是否异步
//
//				onSuccess : function(text) { // 成功回调函数, 函数接收一个从服务端返回的字符串
//					// 将json字符串,转化为js对象
//					// alert(text);
//					
//					if (propertylist.error == -1) {
//						alert("提交成败"); // 这里是逻辑上的失败, 比如"用户名不能重复"等
//						return;
//					}
//
//				},
//				onFail : function(code) {
//					alert(code); // 失败回调函数.这里是网络失败, 比如500错误等
//
//				}
//			});

	for (var i = 0; i < propertylist.length; i++) {
		var property = propertylist[i];
		// var descr = property['description'];
		// var pid = property['id'];
		// var ic = property['isCommon'];
		if (property.isCommon == true) {
			commonpropertyList[commonpropertyList.length] = property;
			// 判断是否可见
			var commonvisible = false;
			if (property.isVisible) {
				commonvisible = true;
			}

			commonitem = {
				type : 'formitem',
				label : "属性: " + property.description,
				labelAlign : 'left',
				labelWidth : 90,
				visible : commonvisible,
				children : [{
							type : 'formitem',
							id : 'common' + i,
							label : "显示名:",
							labelWidth : 60,
							labelAlign : 'left',

							children : [{
										type : 'text',
										id : "text" + textindex++,
										text : property.description,
										valid:cims201.form.validate.mostLength
									}]
						}]
			}

			commonpList[commonpList.length] = commonitem;
		} else {
			expandpropertyList[expandpropertyList.length] = property;
		}
	}

	// by hebi 结束

	Edo.create({
				id : 'ktypeForm',
				title : '知识模板输入',
				type : 'panel',
				render :document.body,
				width : 980,
				height : 550,
				// collapseWidth:0,
				// layout: 'horizontal',
				children : [{
							type : 'formitem',
							layout : 'horizontal',
							labelWidth : 5,
							labelAlign : 'left',
							children : [{
										type : 'formitem',
										label : '知识模板英文名<span style="color:red;">*</span>:',
										labelWidth : 102,
										children : [{
													type : 'text',
												     
													id : 'name',
													valid:cims201.form.validate.isEnglishAndLenthlimit
												}]
									}, {
										type : 'formitem',
										label : '知识模板中文名<span style="color:red;">*</span>:',
										labelWidth : 120,
										labelAlign : 'right',
										children : [{
													type : 'text',
													id : 'ktypename',
													valid:cims201.form.validate.noEmpty
												}]
									}]
						}, {
							type : 'formitem',
							labelWidth : 10,
							labelAlign : 'left',
							layout : 'horizontal',
							children : [{
										type : 'panel',

										title : '必选的通用属性',
										minHeight : 300,

										children : [{
													type : 'formitem',
													labelAlign : 'left',
													labelWidth : 10,
													id : 'commonp',
													children : commonpList
												}

										]
									}, {
										type : 'panel',
										layout : 'vertical',
										labelAlign : 'left',
										width : 670,
										verticalScrollPolicy : "on",
										minHeight : 300,
										height:300,
										title : '选择添加扩展属性',

										id : 'propertybar',
										children : [{
													type : 'formitem',
													id : 'propertybuttonbar',
													labelWidth : 0,
													labelAlign : 'left',
													layout : 'horizontal'
												}

										]
									}

							]
						}, {
							type : 'formitem',
							layout : 'horizontal',
							padding : [8, 0, 8, 280],
							children : [{
										id : 'submitBtn',
										type : 'button',
										text : '提交'
									}, {
										id : 'reset',
										type : 'button',
										text : '重置'
									}]
						}]

			});

	submitBtn.on('click', function(e) {
                    var resultpropertyList = new Array();
				// 为了得到通用属性，
	                for (var index = 0; index < commonpropertyList.length; index++) {
					var commonvalue = Edo.get("text" + index).getValue();
					var commonproperty = commonpropertyList[index];
					commonproperty.description = commonvalue;
					//Edo.get("text" + index).setValue(commonproperty);
                    resultpropertyList[resultpropertyList.length]=commonproperty;
				}
				// 为了得到扩展属性对象，及对象的显示名
				
				for (var index = 0; index < comboxindex; index++) {
					var expandproperty = Edo.get("expandidcombox" + index)
							.getValue(); // 得到对象
                   //得到扩展属性的显示名
					expandproperty.description = Edo.get("shownameExpandLabel"
							+ 'expandidcombox' + index).text;
					//得到扩展属性的可搜索属性		
					expandproperty.searchable = Edo.get("SearchableExpandLabel"
							+ 'expandidcombox' + index).selectedItem.value;
                   //得到扩展属性的展示类型vcomponent 
					expandproperty.vcomponent = Edo.get("ComponentExpandLabel"
							+ 'expandidcombox' + index).selectedItem.value;
                   //得到扩展属性为combo是对值域
					expandproperty.listvalue = Edo
							.get("vvaluelist" + "ComponentExpandLabel"
									+ 'expandidcombox' + index).text;
                    resultpropertyList[resultpropertyList.length]=expandproperty;
					// text设置进去
					//Edo.get("expandidcombox" + index).setValue(expandproperty); // 重新包装
				}

			

				// 验证表单

			//	var o = ktypeForm.getForm(); // 获取表单值
			if(ktypeForm.valid()){
				var name=Edo.get('name').text;
			    var ktypename=Edo.get('ktypename').text;
			    
				var json = Edo.util.Json.encode({name:name,ktypename:ktypename,propertylist:resultpropertyList});
				// alert(json); // 可以用ajax发送到服务端
               
				Edo.util.Ajax.request({
							type : "post", // 交互方式:get,post
							url : 'ktype!save.action', // 数据源地址
							params : {
								json : json
							},
							async : false,
							// 是否异步
							onSuccess : function(t) { // 成功回调函数,
								// 函数接收一个从服务端返回的字符串
								// alert('保存成功!');
							 	cims201.utils.warn("",null,t,null,null)
								Edo.get('ktypeForm').destroy();
								cims201.knowledge.ktypeinput.Init();
							
							},
							onFail : function(code) { // 失败回调函数.这里是网络失败,
								// 比如500错误等
								alert(code);
							}
						});}

			});

	var deleteexpandCombox = {
		id : 'submitBtn3',
		type : 'button',
		text : '删除'

	}
	var addexpandCombox = {
		id : 'submitBtn2',
		type : 'button',
		text : '添加'

	}

	Edo.get('propertybuttonbar').addChild(addexpandCombox);
	Edo.get('propertybuttonbar').addChild(deleteexpandCombox);
	submitBtn2.on('click', function(e) {
	//	if (comboxindex < expandpropertyList.length) {
			var expandItemData = new Array();
			for (var ie = 0; ie < expandpropertyList.length; ie++) {

				var temp = {
					text : expandpropertyList[ie].description,
					value : expandpropertyList[ie],
					index : ie
				};
				// temp.value.index = vi+1;
				expandItemData[expandItemData.length] = temp;

			}

			var expanditem = {
				type : 'formitem',
				label : '属性: ',
				labelAlign : 'left',
				layout : 'horizontal',
				labelWidth :40,
				// style: 'background:#ccc;',
				children : [{
					id : 'expandidcombox' + comboxindex,
					type : 'combo',
					readOnly : true,
					// layout: 'horizontal',
                    width:80,
					displayField : 'text',
					valueField : 'value',
					data : expandItemData,
					onSelectionChange : function(e) {

						var tt = e.selectedItem;

						Edo.get('shownameExpandLabel' + this.id).set('text',
								e.selectedItem.text);
								
						if(e.selectedItem.value.propertyType=='java.util.Date')
							{
							Edo.get('ComponentExpandLabel' +this.id).set('data',
								[{
									text : '日期',
									value : 'date'
								}]);
								
							Edo.get('ComponentExpandLabel' +this.id).set('selectedIndex',1);	
							}	
							
						else{
						Edo.get('ComponentExpandLabel' +this.id).set('data',
								[{
									text : '文本框',
									value : 'textarea'
								}, 
								{
									text : '文本',
									value : 'textfield'
								}, 	
								{
									text : '下拉菜单',
									value : 'combo'
								}]);
								
							Edo.get('ComponentExpandLabel' +this.id).set('selectedIndex',1);
						
							
							
						}	
						 		
					}
				},

				{
					type : 'formitem',
					label : '可搜索:',
					labelWidth : 50,
					labelAlign : 'right',
					width:90,
					children : [

					{
						type : 'combo',
						readOnly : true,
					
						id : 'SearchableExpandLabel' + 'expandidcombox'
								+ comboxindex,
									width:40,
                        selectedIndex:0,
						displayField : 'text',
						valueField : 'value',
						data : [{
									text : '是',
									value : 'true'
								}, {
									text : '否',
									value : 'false'
								}]
					}]
				},

				{
					type : 'formitem',
					label : '显示名:',
					labelWidth : 50,
					labelAlign : 'right',
					children : [

					{           width:70,
								type : 'text',
								text : '',
								id : 'shownameExpandLabel' + 'expandidcombox'
										+ comboxindex,
							    valid:cims201.form.validate.mostLength
							}]
				}, {
					type : 'formitem',
					label : '类型:',
					labelWidth : 35,
					labelAlign : 'right',
					children : [

					{
						type : 'combo',
						readOnly : true,
						id : 'ComponentExpandLabel' + 'expandidcombox'+ comboxindex,
                      //  selectedIndex:1,
						displayField : 'text',
						valueField : 'value',
						 width:70,
                         selectedIndex:1,
						data : [ {
									text : '文本框',
									value : 'textarea'
								},
								{
									text : '文本',
									value : 'textfield'
								},
									{
									text : '下拉菜单',
									value : 'combo'
								}],
						onSelectionChange : function(e) {

							var shownameExpandLabelvtype = e.selectedItem.value;
							if(null!=Edo.get('vvaluelist' + this.id)){
							if (shownameExpandLabelvtype == 'combo') {

								Edo.get('vvaluelistbar' + this.id).set(	'visible', true);
								if (Edo.get('vvaluelist' + this.id).getValue() == "")
									Edo.get('vvaluelist' + this.id)
											.setValue('用逗号隔开如： 是，否');
							} else {

								Edo.get('vvaluelistbar' + this.id).set(	'visible', false);
								Edo.get('vvaluelist' + this.id).setValue('');

							}
							}
						}

					}]
				}, {
					type : 'formitem',
					label : '值域:',
					labelWidth : 35,
					labelAlign : 'right',
					id : 'vvaluelistbar' + 'ComponentExpandLabel'
							+ 'expandidcombox' + comboxindex,
					visible : false,
					children : [

					{
						type : 'text',
						width : 140,

						id : 'vvaluelist' + 'ComponentExpandLabel'
								+ 'expandidcombox' + comboxindex,
						text : '用逗号隔开如： 是，否',
						onTextFocus : function(e) {
							var value = this.text;
							// alert(value);
							if (value.indexOf("用逗号隔开如： 是，否") != -1)
								Edo.get(this.id).setValue('');
						}

					}]
				}

				]

			};
			comboxindex = comboxindex + 1;
			Edo.get('propertybar').addChildAt(
					Edo.get('propertybar').numChildren(), expanditem);
	//	}

	})
	submitBtn3.on('click', function(e) {
			

				var propertybarchilednum = Edo.get('propertybar').numChildren();
				if (propertybarchilednum > 1){
					Edo.get('propertybar').getChildAt(Edo.get('propertybar')
							.numChildren()- 1).destroy();
							
					comboxindex = comboxindex - 1;
				}
			})
			
reset.on('click', function(e){
    
ktypeForm.reset();    
    
});

}
