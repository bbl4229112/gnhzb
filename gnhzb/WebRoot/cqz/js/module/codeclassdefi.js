Edo.build({
	 type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	 render: document.body,
        children:[
        	{
        		    type: 'group',
        		    width: '100%',
				    layout: 'horizontal',
				    cls: 'e-toolbar',
				    children: [
				        {type: 'button',id:'addbtn',text: '新增'},
						{type: 'split'},
				        {type: 'button',id:'xgbtn',text: '修改'},
						{type: 'split'},
				        {type: 'button',id:'delebtn',text: '删除'},
				        {type: 'split'},
				        {type: 'button',id:'rebtn',text: '刷新'},
				        {type: 'space',width:'100%'},
				        {type: 'label', text:'提示：对于已经建立分类结构的类别不能进行编辑和删除'}
				    ]
        	},
        
        	{
			    id: 'codeclassdefiTb', type: 'table', width: '100%', height: '100%',autoColumns: true,
			    padding:[0,0,0,0],
			    rowSelectMode : 'single',
			    columns:[
			    	{
	                    headerText: '',
	                    align: 'center',
	                    width: 10,                        
	                    enableSort: false,
	                    enableDragDrop: true,
	                    enableColumnDragDrop: false,
	                    style:  'cursor:move;',
	                    renderer: function(v, r, c, i, data, t){
	                        return i+1;
                    	}
                    },
                    Edo.lists.Table.createMultiColumn(),
			    	{ dataIndex: 'cid',visible : false},
			        { header: '类型名称', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
			        { header: '类型代号', dataIndex: 'classcode', headerAlign: 'center',align: 'center' },
			        { header: '分类结构', dataIndex: 'flag', headerAlign: 'center',align: 'center',
			          renderer: function(val){
						    var text;
							if (val > 0) {
								text = "已建立分类结构";
								return '<span style="color:green;">' + text + '</span>';
							} else {
								text = "未建立分类结构";
								return '<span style="color:red;">' + text + '</span>';
							}
							return val;
			          }
			        },
			        { header: '码首字段', dataIndex: 'rule', headerAlign: 'center',align: 'center',
			        	renderer:function codeHead(val) {	
							var text = val.split("-");
							return text[0]; 
						}
			        }
			    ]    
			    
			}
        ]
});

Edo.util.Ajax.request({
    url: 'codeclass/getallcodeclass.action',
    type: 'get',
    onSuccess: function(text){

    	var o = Edo.util.Json.decode(text);
        codeclassdefiTb.set('data',o);
    },
    onFail: function(code){
        //code是网络交互错误码,如404,500之类
        alert(code);
    }
});

addbtn.on('click', function(e){
    var form = showAddForm();
    form.reset();
});
rebtn.on('click', function(e){
    Edo.util.Ajax.request({
	    url: 'codeclass/getallcodeclass.action',
	    type: 'get',
	    onSuccess: function(text){
	    	var o = Edo.util.Json.decode(text);
	        codeclassdefiTb.set('data',o);
	    },
	    onFail: function(code){
	        //code是网络交互错误码,如404,500之类
	        alert(code);
	    }
	});
});

xgbtn.on('click', function(e){
	var record = codeclassdefiTb.getSelected();
	console.log(record);
    if(!record) {
        Edo.MessageBox.alert("提示", "请选中要编辑的记录");
        return;
    }else if(record.flag==1){
    	 Edo.MessageBox.alert("提示", "已建立分类结构，此处不提供编辑功能！");
        return;
    }
    var form = showEditForm();
    form.setForm(record);
    
});    

delebtn.on('click',function(e){
	var record = codeclassdefiTb.getSelected();
	
    if(!record){
        Edo.MessageBox.alert("提示","请选中要删除的记录");
        return;
    }else if(record.flag==1){
    	 Edo.MessageBox.alert("提示","不能对已建立分类结构的类别进行删除操作！");
        return;
    }
    Edo.MessageBox.confirm("提示", "是否删除选中的记录？", action);
    function action(text){
    	if(text=='yes'){
            Edo.util.Ajax.request({
			    url: 'codeclass/deleteClassByClassCode.action',
			    type: 'get',
			    params:{classcode:record.classcode},
			    onSuccess: function(text){
			    	 Edo.MessageBox.alert("提示", "删除成功！");
			    	 
			    },
			    onFail: function(code){
			        Edo.MessageBox.alert("提示", "删除失败");
			    }
			});
    		codeclassdefiTb.data.remove(record);
    	}
    
    }   
	
});




function showAddForm(){
    if(!Edo.get('addForm')) {
        //创建用户面板
        Edo.create({
            id: 'addForm',            
            type: 'window',title: '新增类别',
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
                    type: 'formitem',padding:[20,0,10,0],labelWidth :'100',label: '类别名称<span style="color:red;">*</span>:',
                    children:[{type: 'text', width:'130',name: 'classname',valid: classnameRegex}]
                },
                {
                    type: 'formitem',labelWidth :'100',label: '类别代号<span style="color:red;">*</span>:',
                    children:[{type: 'text', width:'130',name: 'classcode',valid: classcodeRegex}]
                },
                {
                    type: 'formitem',padding:[10,0,0,0],labelWidth :'100',label: '类别码首字段<span style="color:red;">*</span>:',
                    children:[{type: 'text', width:'130',name: 'codehead',valid: codeheadRegex}]
                },            
                {
                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
                    children:[
                        {name: 'submitBtn', type: 'button', text: '确定', 
                            onclick: function(){
                                if(addForm.valid()){
                                    var o = addForm.getForm();
                                    Edo.util.Ajax.request({
									    url: 'codeclass/insertClass.action',
									    type: 'post',
									    params:o,
									    onSuccess: function(text){
									    	   Edo.util.Ajax.request({
												    url: 'codeclass/getallcodeclass.action',
												    type: 'get',
												    onSuccess: function(text){
												    	var o = Edo.util.Json.decode(text);
												        codeclassdefiTb.set('data',o);
												    },
												    onFail: function(code){
												        //code是网络交互错误码,如404,500之类
												        alert(code);
												    }
												});
									    	 Edo.MessageBox.alert("提示", "添加成功");
									    	 
									    },
									    onFail: function(code){
									        //code是网络交互错误码,如404,500之类
									        Edo.MessageBox.alert("提示", "添加失败");
									    }
									});
                                    addForm.hide();
                                }
                            }
                        },
                        {type: 'space', width:50},
                        {name: 'cancleBtn', type:'button', text:'取消',
                    		onclick:function(){
                    			addForm.reset();
                    			addForm.hide();
                    		}
                    	}
                        
                    ]
                }
            ]
        });
    }
    addForm.show('center', 'middle', true);
    return addForm;
}
function noEmpty(v){
    if(v == "") return "不能为空";
}

function classnameRegex(v){
    if (v.search(/^[A-Za-z0-9]+$/) != -1)
        return true;
    else return "类别名称格式错误";
}
function classcodeRegex(v){
    if (v.search(/^[A-Za-z0-9]+$/) != -1)
        return true;
    else return "类别代号格式错误";
}
function codeheadRegex(v){
    if (v.search(/^[A-Za-z0-9]+$/) != -1)
        return true;
    else return "类别码首字段格式错误";
}

function showEditForm(){
    if(!Edo.get('editForm')) {
        //创建用户面板
        Edo.create({
            id: 'editForm',            
            type: 'window',title: '修改类别',
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
                    type: 'formitem',padding:[20,0,10,0],labelWidth :'100',label: '类别名称<span style="color:red;">*</span>:',
                    children:[{type: 'text', width:'130',name: 'classname',valid: classnameRegex}]
                },
                {
                    type: 'formitem',labelWidth :'100',label: '类别代号<span style="color:red;">*</span>:',
                    children:[{type: 'text', width:'130',name: 'classcode',valid: classcodeRegex}]
                },
                {
                    type: 'formitem',padding:[10,0,0,0],labelWidth :'100',label: '类别码首字段<span style="color:red;">*</span>:',
                    children:[{type: 'text', width:'130',name: 'rule',valid: codeheadRegex}]
                },
                 {
                    type: 'formitem',visible : false,label: '后台ID<span style="color:red;">*</span>:',
                    children:[{type: 'text',name: 'cid'}]
                }, 
                {
                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
                    children:[
                        {name: 'submitBtn', type: 'button', text: '确定', 
                            onclick: function(){
                                if(editForm.valid()){
                                    var o = editForm.getForm();
                                    var record = codeclassdefiTb.getSelected();                                   
                                    Edo.util.Ajax.request({
									    url: 'codeclass/updateCodeClass.action',
									    type: 'post',
									    params:o,
									    onSuccess: function(text){
									    		codeclassdefiTb.data.updateRecord(record,o);
									    	 	Edo.MessageBox.alert("提示", "修改成功");									    	 
									    },
									    onFail: function(code){
									        //code是网络交互错误码,如404,500之类
									        Edo.MessageBox.alert("提示", "修改失败");
									    }
									});
                                    editForm.hide();
                                }
                            }
                        },
                        {type: 'space', width:50},
                        {name: 'cancleBtn', type:'button', text:'取消',
                    		onclick:function(){
                    			editForm.reset();
                    			editForm.hide();
                    		}
                    	}
                        
                    ]
                }
            ]
        });
    }
    editForm.show('center', 'middle', true);
    return editForm;
}