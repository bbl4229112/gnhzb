Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	render: document.body,
	children:[
		{
			type:'box',width: '100%',height: '40',cls: 'e-toolbar',
			layout:'horizontal',
			children:[
				{	type:'label',text:'请选择分类：'},
				{	type:'combo', id:'ClassNameCombo',
					width:'150',
					Text :'请先选择适合的分类',
					readOnly : true,
					valueField: 'classcode',
				    displayField: 'classname',			    
				    onselectionchange: function(e){
				    	newLayerBtn.set('enable',true);
				    	Edo.util.Ajax.request({
						    url: 'codeclass/getRuleByCode.action?clss='+e.selectedItem.classcode,
						    type: 'get',
						    onSuccess: function(text){
						    	var o = Edo.util.Json.decode(text);
						        CodeClassRuleManageTree.set('data',o);
						        
						    },
						    onFail: function(code){
						        //code是网络交互错误码,如404,500之类
						        alert(code);
						    }
						});
				        
				    }
			    },
			    { 	type:'space', width:'20'},
			    {	type:'button',id:'newLayerBtn',text:'增加新层',enable:false},
			    {type: 'split'},
			    {	type:'button',id:'editLayerBtn',text:'编辑层',enable:false},
			    {type: 'split'},
			    {	type:'button',id:'deleLayerBtn',text:'删除层',enable:false}
			]
		},
		{
			type:'tree',id:'CodeClassRuleManageTree',width:'100%',height:'100%',
			headerVisible:false,
		    autoColumns: true,
		    horizontalLine: false,
			columns: [
		        {
		            dataIndex: 'text'
		        }
		    ],
		    onselectionchange: function(e){
		    	if(e.selected.__index!=0){
		    		editLayerBtn.set('enable',true);
		    		deleLayerBtn.set('enable',true);
		    	}else{
		    		editLayerBtn.set('enable',false);
		    		deleLayerBtn.set('enable',false);
		    	}
		    	
		    	
		    }
		}
	]
		
});

Edo.util.Ajax.request({
    url: 'codeclass/getallcodeclass.action',
    type: 'get',
    onSuccess: function(text){
    	var o = Edo.util.Json.decode(text);
        ClassNameCombo.set('data',o);
    },
    onFail: function(code){
        //code是网络交互错误码,如404,500之类
        alert(code);
    }
});


Edo.util.Ajax.request({
    url: 'codeclass/getRuleByCode.action',
    type: 'get',
    onSuccess: function(text){
    	var o = Edo.util.Json.decode(text);
        CodeClassRuleManageTree.set('data',o);
    },
    onFail: function(code){
        //code是网络交互错误码,如404,500之类
        alert(code);
    }
});
newLayerBtn.on('click',function(e){
	var form=showAddNewLayerForm();
	form.reset();
	form.setForm({clscode : ClassNameCombo.selectedItem.classcode});
});

editLayerBtn.on('click',function(e){
	var form=showEditLayerForm();
	form.reset();
	var nval = CodeClassRuleManageTree.selected.value;
	var nvtt = nval.split(":");
	var tttt = nvtt[1];
	var tchar = tttt.charAt(0);
	var chines="";
	if (tchar == 'C') {
		chines = "字符型";
	}
	if (tchar == 'B') {
		chines = "混合型";
	}
	if (tchar == 'N') {
		chines = "数字型";
	}
	form.setForm(
		{
			encodetype:chines,
			codelength:tttt.substr(1),
			clscode : ClassNameCombo.selectedItem.classcode,
			seq:nvtt[0]				
		}
	);
});

deleLayerBtn.on('click',function(e){
	var nval = CodeClassRuleManageTree.selected.value;
	var ttn = nval.split(":")[0];
	Edo.util.Ajax.request({
	    url: 'codeclass/deleteRuleNode.action',
	    type: 'post',
	    params:{
	    	cls:ClassNameCombo.selectedItem.classcode,
	    	tn:ttn
	    },
	    onSuccess: function(text){
	    	 Edo.util.Ajax.request({
			    url: 'codeclass/getRuleByCode.action?clss='+ClassNameCombo.selectedItem.classcode,
			    type: 'get',
			    onSuccess: function(text){
			    	var o = Edo.util.Json.decode(text);
			        CodeClassRuleManageTree.set('data',o);
			        
			    },
			    onFail: function(code){
			        alert(code);
			    }
			});
	    	Edo.MessageBox.alert("提示", "删除成功");
	    },
	    onFail: function(code){
	        Edo.MessageBox.alert("提示", "删除失败");
	    }
	});
	
});

function showAddNewLayerForm(){
	
    if(!Edo.get('addNewLayerForm')) {
        Edo.create({
            id: 'addNewLayerForm',            
            type: 'window',title: '新增当前类编码层',
            render: document.body,
            width:260,
            titlebar: [
                {
                    cls: 'e-titlebar-close',
                    onclick: function(e){
                    this.parent.owner.hide();
                    }
                }
            ],
            children: [
                {
                    type: 'formitem',padding:[20,0,10,0],labelWidth :'80',label: '编码类型：',
                    children:[
                    	{	type:'combo', id:'ClassCodeTypeCombo',
							width:'130',
							name:'encodetype',
							text:'请选择...',
							valueField: 'encodetype',
						    displayField: 'disvalue',	
						    data:[
						    	{encodetype:"N",disvalue:"数字型"},
						    	{encodetype:"C",disvalue:"字符型"},
						    	{encodetype:"B",disvalue:"混合型"}
						    ],
						    readOnly : true,
						    valid :nullRegex
					    }
                    		
                   ]
                },
                {
                    type: 'formitem',labelWidth :'80',label: '编码长度：',
                    children:[
                    	{type: 'text', width:'130',name: 'codelength',valid :numberRegex}
                    ]
                },
                {
                	type:'formitem',visible : false,label:'编码类别',//visible : false,
                	children:[
                		{type:'text',name:'clscode'}
                	]               	
                },
                {
                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
                    children:[
                        {name: 'submitBtn', type: 'button', text: '确定', 
                            onclick: function(){
                                if(addNewLayerForm.valid()){
                                    var o = addNewLayerForm.getForm();
                                    Edo.util.Ajax.request({
									    url: 'codeclass/updateCodeClassRule.action',
									    type: 'post',
									    params:o,
									    onSuccess: function(text){
									    	 Edo.util.Ajax.request({
											    url: 'codeclass/getRuleByCode.action?clss='+ClassNameCombo.selectedItem.classcode,
											    type: 'get',
											    onSuccess: function(text){
											    	var o = Edo.util.Json.decode(text);
											        CodeClassRuleManageTree.set('data',o);
											        
											    },
											    onFail: function(code){
											        //code是网络交互错误码,如404,500之类
											        alert(code);
											    }
											});
									    	 Edo.MessageBox.alert("提示", "新增成功");
									    },
									    onFail: function(code){
									        //code是网络交互错误码,如404,500之类
									        Edo.MessageBox.alert("提示", "新增失败");
									    }
									});
                                    addNewLayerForm.hide();
                                }
                            }
                        },
                        {type: 'space', width:50},
                        {name: 'cancleBtn', type:'button', text:'取消',
                    		onclick:function(){
                    			addNewLayerForm.reset();
                    			addNewLayerForm.hide();
                    		}
                    	}
                        
                    ]
                }
            ]
        });
    };
    //clsscode.set('text',ClassNameCombo.selectedItem.classcode);
    addNewLayerForm.show('center', 'middle', true);
    return addNewLayerForm;
};

function numberRegex(v){
    if (v.search(/^[0-9]+$/) != -1 & v>0)
        return true;
    else return "请输入大于零的数字!";
};

function nullRegex(){
    if (ClassCodeTypeCombo.text=="请选择..." ||ClassCodeTypeCombo.text=="")
        return "请选择编码类型!";
    else return true;
};



function showEditLayerForm(){
	
    if(!Edo.get('editLayerForm')) {
        //创建用户面板
        Edo.create({
            id: 'editLayerForm',            
            type: 'window',title: '编辑当前类编码层',
            render: document.body,
            width:260,
            titlebar: [
                {
                    cls: 'e-titlebar-close',
                    onclick: function(e){
                    this.parent.owner.hide();
                    }
                }
            ],
            children: [
                {
                    type: 'formitem',padding:[20,0,10,0],labelWidth :'80',label: '编码类型：',
                    children:[
                    	{	type:'combo', id:'ClassCodeTypeCombo2',
							width:'130',
							name:'encodetype',
							text:'请选择...',
							valueField: 'encodetype',
						    displayField: 'disvalue',	
						    data:[
						    	{encodetype:"数字型",disvalue:"数字型"},
						    	{encodetype:"字符型",disvalue:"字符型"},
						    	{encodetype:"混合型",disvalue:"混合型"}
						    ],
						    readOnly : true
					    }
                    		
                   ]
                },
                {
                    type: 'formitem',labelWidth :'80',label: '编码长度：',
                    children:[
                    	{type: 'text', width:'130',name: 'codelength',valid :numberRegex}
                    ]
                },
                {
                	type:'formitem',visible : false,label:'编码类别',
                	children:[
                		{type:'text',name:'clscode'}
                	]               	
                },
                {
                	type:'formitem',visible : false,label:'编码类别',
                	children:[
                		{type:'text',name:'seq'}
                	]               	
                },
                {
                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
                    children:[
                        {name: 'submitBtn', type: 'button', text: '确定', 
                            onclick: function(){
                                if(editLayerForm.valid()){
                                    var o = editLayerForm.getForm();
                                    Edo.util.Ajax.request({
									    url: 'codeclass/updateCodeClassRule2.action',
									    type: 'post',
									    params:o,
									    onSuccess: function(text){
									    	 Edo.util.Ajax.request({
											    url: 'codeclass/getRuleByCode.action?clss='+ClassNameCombo.selectedItem.classcode,
											    type: 'get',
											    onSuccess: function(text){
											    	var o = Edo.util.Json.decode(text);
											        CodeClassRuleManageTree.set('data',o);
											        
											    },
											    onFail: function(code){
											        //code是网络交互错误码,如404,500之类
											        alert(code);
											    }
											});
									    	 Edo.MessageBox.alert("提示", "修改成功");
									    },
									    onFail: function(code){
									        //code是网络交互错误码,如404,500之类
									        Edo.MessageBox.alert("提示", "修改失败");
									    }
									});
                                    editLayerForm.hide();
                                }
                            }
                        },
                        {type: 'space', width:50},
                        {name: 'cancleBtn', type:'button', text:'取消',
                    		onclick:function(){
                    			editLayerForm.reset();
                    			editLayerForm.hide();
                    		}
                    	}
                        
                    ]
                }
            ]
        });
    };
    //clsscode.set('text',ClassNameCombo.selectedItem.classcode);
    editLayerForm.show('center', 'middle', true);
    return editLayerForm;
};
