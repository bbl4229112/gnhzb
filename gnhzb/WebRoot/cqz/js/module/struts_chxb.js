Edo.build({
	type:'app',width:'100%',height:'100%',border:[0,0,0,0],
	render:document.body,
	children:[
		{
			type:'group',
			width:'100%',
			layout:'horizontal',
			cls:'e-toolbar',
			children:[
				{type:'button',id:'addBtn',text:'新增编码大类'},
				{type: 'split'},
				{type:'button',id:'deleBtn',text:'删除编码大类',enable:false}
			]
		},
		{
			type:'tree',id:"BigClassTree",width:'100%',height:'100%',
			autoColumns:true,
			horizontalLine:false,
			columns:[
				{header:'分类结构',dataIndex:'text'}
			],
			onselectionchange:function(e){
				deleBtn.set('enable',true);

			
			}
		}
	]
});

Edo.util.Ajax.request({
    url: 'codeclass/getClassStruct.action',
    type: 'get',
    onSuccess: function(text){
    	var o = Edo.util.Json.decode(text);
        BigClassTree.set('data',o);
    },
    onFail: function(code){
        //code是网络交互错误码,如404,500之类
        alert(code);
    }
});

deleBtn.on('click',function(e){
	Edo.util.Ajax.request({
	    url: 'codeclass/deleteTreeByClassCode.action',
	    type: 'get',
	    params:{
	    	classcode:BigClassTree.selected.classcode,
	    	code:BigClassTree.selected.code,
	    	id:BigClassTree.selected.id
	    },
	    onSuccess: function(text){
	    	Edo.MessageBox.alert("提示", "删除成功");
	    	Edo.util.Ajax.request({
			    url: 'codeclass/getClassStruct.action',
			    type: 'get',
			    onSuccess: function(text){
			    	var o = Edo.util.Json.decode(text);
			        BigClassTree.set('data',o);
			    },
			    onFail: function(code){
			    	Edo.MessageBox.alert("提示", code);
			    }
			});
	    },
	    onFail: function(code){
	    	Edo.MessageBox.alert("提示", code);
	    }
	});
					
});

addBtn.on('click',function(e){
	showAddBigClassForm();
	Edo.util.Ajax.request({
	    url: 'codeclass/getcodeclass.action?flag=no',
	    type: 'get',
	    onSuccess: function(text){
	    	var o = Edo.util.Json.decode(text);
	        BigClassCombo.set('data',o);
	    },
	    onFail: function(code){
	        //code是网络交互错误码,如404,500之类
	        alert(code);
	    }
	});

});

function showAddBigClassForm(){
	
    if(!Edo.get('addBigClassForm')) {
        Edo.create({
            id: 'addBigClassForm',            
            type: 'window',title: '选择新建编码大类',
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
                    type: 'formitem',padding:[20,0,10,0],labelWidth :'80',label: '选择大类：',
                    children:[
                    	{	type:'combo', id:'BigClassCombo',
							width:'130',
							name:'classcode',
							valueField: 'classcode',
						    displayField: 'classname',	
						    readOnly : true,
						    valid :nullRegex
					    }
                    		
                   ]
                },
                {
                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
                    children:[
                        {name: 'submitBtn', type: 'button', text: '确定', 
                            onclick: function(){
                                if(addBigClassForm.valid()){
                                    var o = addBigClassForm.getForm();
                                    console.log(o);
                                    Edo.util.Ajax.request({
									    url: 'codeclass/updateclassbuildtree.action?classcode='+o.classcode,
									    type: 'post',
									    onSuccess: function(text){
									    	 Edo.util.Ajax.request({
											    url: 'codeclass/getClassStruct.action',
											    type: 'get',
											    onSuccess: function(text){
											    	var o = Edo.util.Json.decode(text);
											        BigClassTree.set('data',o);
											    },
											    onFail: function(code){
											        //code是网络交互错误码,如404,500之类
											        alert(code);
											    }
											});
									    	 Edo.MessageBox.alert("提示", "新增成功");
									    },
									    onFail: function(code){
									        Edo.MessageBox.alert("提示", "新增失败");
									    }
									});
                                    addBigClassForm.hide();
                                }
                            }
                        },
                        {type: 'space', width:50},
                        {name: 'cancleBtn', type:'button', text:'取消',
                    		onclick:function(){
                    			addBigClassForm.reset();
                    			addBigClassForm.hide();
                    		}
                    	}
                        
                    ]
                }
            ]
        });
    };
    //clsscode.set('text',ClassNameCombo.selectedItem.classcode);
    addBigClassForm.show('center', 'middle', true);
    return addBigClassForm;
};

function nullRegex(){
    if (BigClassCombo.text=="")
        return "请选择编码大类!";
    else return true;
};