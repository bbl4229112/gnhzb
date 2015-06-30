function createDocTypeManage(){
	var docTypeTable=Edo.create({
    	type:'panel',title:'上传类型列表',layout:'vertical',verticalGap:0,width:'100%',height:'100%',padding:[0,0,0,0],
    	children:[
           {
        	   type:'box',width:'100%',height:'32',cls:'e-toolbar',layout:'horizontal',
        	   children:[
	             {type:'button',id:'DocTypeManage_EditModel',text:'编辑主模型'},
	             {type:'split'},
	             {type:'button',id:'DocTypeManage_AddSelfDefiType',text:'添加自定义类型'},
	             {type:'split'},
	             {type:'button',id:'DocTypeManage_DeleSelfDefiType',text:'删除自定义类型'},
	             {type:'split'},
	             {type:'button',id:'DocTypeManage_EditSelfDefiType',text:'编辑自定义类型'}
        	   ]
           },
           {
        	   id:'DocTypeManage_TypeTable',type:'table',width: '100%',height: '100%',autoColumns: true,showHeader: true,
        	   columns:[
        	        {header:'id',dataIndex:'id',visible:false},
    	            {header:'类型名称',dataIndex:'typeName',headerAlign: 'center',align:'center',
    	            	renderer: function(value, record){
    	            		if(record.ismaster==1){
    	            			return '<span style="color:red;">'+value+'</span>';
    	            		}else{
    	            			return value;
    	            		}
    	            }},
    	            {header:'类型代号',dataIndex:'typeCode',headerAlign: 'center',align:'center',
    	            	renderer: function(value, record){
    	            		if(record.ismaster==1){
    	            			return '<span style="color:red;">'+value+'</span>';
    	            		}else{
    	            			return value;
    	            		}
    	            }},
    	            {header:'类型后缀',dataIndex:'typeSuffix',headerAlign: 'center',align:'center',
    	            	renderer: function(value, record){
    	            		if(record.ismaster==1){
    	            			return '<span style="color:red;">'+value+'</span>';
    	            		}else{
    	            			return value;
    	            		}
    	            }},
    	            {header:'类型描述',dataIndex:'typeDes',headerAlign: 'center',align:'center',
    	            	renderer: function(value, record){
    	            		if(record.ismaster==1){
    	            			return '<span style="color:red;">'+value+'</span>';
    	            		}else{
    	            			return value;
    	            		}
    	            }}
        	   ],
        	   onbodyclick:function(e){
        		   if(e.record){
        			   if(e.record.ismaster && 1==e.record.ismaster){
            			   DocTypeManage_DeleSelfDefiType.set('enable',false);
            			   DocTypeManage_EditSelfDefiType.set('enable',false);
            		   }else{
            			   DocTypeManage_DeleSelfDefiType.set('enable',true);
            			   DocTypeManage_EditSelfDefiType.set('enable',true);
            		   }
        		   }
        		   
        	   }
           }
    	]
      
	});
	
	DocTypeManage_TypeTable.set('data',cims201.utils.getData('draft/draft-type!getAll.action'));
	//编辑主模型
	DocTypeManage_EditModel.on('click',function(e){
		var form =showEditModelForm();
		form.reset();
		var data="";
		var datas=DocTypeManage_TypeTable.data.source;
		for(var i=0;i<datas.length;i++){
			if (datas[i].ismaster==1){
				data= datas[i];
			}
		}
		form.setForm(data);
	});
	//添加自定义文档
	DocTypeManage_AddSelfDefiType.on('click',function(e){
		var form =showAddSelfDefiTypeForm();
		form.set("title","定义上传类型");
		form.reset();
	});
	//删除自定义文档
	DocTypeManage_DeleSelfDefiType.on('click',function(e){

		if(1!=DocTypeManage_TypeTable.selecteds.length){
			Edo.MessageBox.alert('提示','您还没有选择删除项');
		}else{
			Edo.MessageBox.confirm('提示','确定要删除吗？',function(action){
				if('yes'==action){
					Edo.util.Ajax.request({
					    url: 'draft/draft-type!deleteSelf.action',
					    type: 'post',
					    params:{id:DocTypeManage_TypeTable.selected.id},
					    onSuccess: function(text){
					    	console.log(text);
					    	if("删除成功！"==text){
					    		Edo.MessageBox.alert("提示", text);
					    		DocTypeManage_TypeTable.set('data',cims201.utils.getData('draft/draft-type!getAll.action'));
					    	}else{
					    		Edo.MessageBox.alert("提示", "系统出错，请联系管理员！");
					    	}
					    },
					    onFail: function(code){
					        //code是网络交互错误码,如404,500之类
					        Edo.MessageBox.alert("提示", "删除失败"+code);
					    }
					});
				}
			});
		}
	});
	//编辑自定义文档
	DocTypeManage_EditSelfDefiType.on('click',function(e){
		if(1!=DocTypeManage_TypeTable.selecteds.length){
			Edo.MessageBox.alert('提示','您还没有选择删除项');
		}else{
			var form = showAddSelfDefiTypeForm();
			form.reset();
			form.setForm(DocTypeManage_TypeTable.selected);
			
		}
	});
	
	function showEditModelForm(){
	    if(!Edo.get('DocTypeManage_EditModelForm')) {
	        //创建用户面板
	        Edo.create({
	            id: 'DocTypeManage_EditModelForm',            
	            type: 'window',title: '编辑上传类型',
	            render: document.body,
	            width:260,
	            titlebar: [
	                {
	                    cls: 'e-titlebar-close',
	                    onclick: function(e){
	                    this.parent.owner.destroy();
						
	                    }
	                }
	            ],
	            children: [
	                {type:'formitem',visible:false,children:{
	                	type:'text',name:'id'
	                }},
	                {
	                    type: 'formitem',padding:[20,0,10,0],labelWidth :'65',label: '类型名称:',
	                    children:[{type: 'text',width:'165',name: 'typeName',readOnly:true}]
	                },
	                {
	                    type: 'formitem',labelWidth :'65',label: '类型代号:',
	                    children:[{type: 'text', width:'165',name: 'typeCode',readOnly:true}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'65',label: '类型后缀:',
	                    children:[{type: 'text', width:'165',name: 'typeSuffix',valid: noEmpty,autoValid:false}]
	                }, 
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'65',label: '类型描述:',
	                    children:[{type: 'textarea', height:80,width:'165',name: 'typeDes'}]
	                },  
	                {
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {name: 'submitBtn', type: 'button', text: '确定', 
	                            onclick: function(){
	                                if(DocTypeManage_EditModelForm.valid()){
	                                    var o = DocTypeManage_EditModelForm.getForm();
	                                    Edo.util.Ajax.request({
										    url: 'draft/draft-type!editModel.action',
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	if("修改成功！"==text){
										    		Edo.MessageBox.alert("提示", text);
										    		DocTypeManage_TypeTable.set('data',cims201.utils.getData('draft/draft-type!getAll.action'));
										    	}else{
										    		Edo.MessageBox.alert("提示", "系统出错，请联系管理员！");
										    	}
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        Edo.MessageBox.alert("提示", "修改失败"+code);
										    }
										});
	                                    DocTypeManage_EditModelForm.destroy();
	                                }
	                            }
	                        },
	                        {type: 'space', width:50},
	                        {name: 'cancleBtn', type:'button', text:'取消',
	                    		onclick:function(){
	                    			DocTypeManage_EditModelForm.destroy();
	                    		}
	                    	}
	                        
	                    ]
	                }
	            ]
	        });
	    }
	    DocTypeManage_EditModelForm.show('center', 'middle', true);
	    return DocTypeManage_EditModelForm;

	}
	function showAddSelfDefiTypeForm(){
	    if(!Edo.get('DocTypeManage_AddSelfDefiTypeForm')) {
	        //创建用户面板
	        Edo.create({
	            id: 'DocTypeManage_AddSelfDefiTypeForm',            
	            type: 'window',title: '编辑上传类型',
	            render: document.body,
	            width:260,
	            titlebar: [
	                {
	                    cls: 'e-titlebar-close',
	                    onclick: function(e){
	                    this.parent.owner.destroy();
						
	                    }
	                }
	            ],
	            children: [
					{type:'formitem',visible:false,children:{
						type:'text',name:'id'
					}},
	                {
	                    type: 'formitem',padding:[20,0,10,0],labelWidth :'65',label: '类型名称:',
	                    children:[{type: 'text',width:'165',name: 'typeName',valid: noEmpty,autoValid:false}]
	                },
	                {
	                    type: 'formitem',labelWidth :'65',label: '类型代号:',
	                    children:[{type: 'text', width:'165',name: 'typeCode',valid: noEmpty,autoValid:false}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'65',label: '类型后缀:',
	                    children:[{type: 'text', width:'165',name: 'typeSuffix',valid: noEmpty,autoValid:false}]
	                }, 
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'65',label: '类型描述:',
	                    children:[{type: 'textarea', height:80,width:'165',name: 'typeDes'}]
	                },  
	                {
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {name: 'submitBtn', type: 'button', text: '确定', 
	                            onclick: function(){
	                                if(DocTypeManage_AddSelfDefiTypeForm.valid()){
	                                    var o = DocTypeManage_AddSelfDefiTypeForm.getForm();
	                                    var urlStr = '';
	                                    if("编辑上传类型"==DocTypeManage_AddSelfDefiTypeForm.title){
	                                    	urlStr='draft/draft-type!editSelf.action';
	                                    }
	                                    if("定义上传类型"==DocTypeManage_AddSelfDefiTypeForm.title){
	                                    	urlStr='draft/draft-type!addSelf.action';
	                                    }
	                                    Edo.util.Ajax.request({
										    url: urlStr,
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	Edo.MessageBox.alert("提示", text);
										    	DocTypeManage_TypeTable.set('data',cims201.utils.getData('draft/draft-type!getAll.action'))
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        Edo.MessageBox.alert("提示", "操作失败"+code);
										    }
										});
	                                    DocTypeManage_AddSelfDefiTypeForm.destroy();
	                                }
	                            }
	                        },
	                        {type: 'space', width:50},
	                        {name: 'cancleBtn', type:'button', text:'取消',
	                    		onclick:function(){
	                    			DocTypeManage_AddSelfDefiTypeForm.destroy();
	                    		}
	                    	}
	                        
	                    ]
	                }
	            ]
	        });
	    }
	    DocTypeManage_AddSelfDefiTypeForm.show('center', 'middle', true);
	    return DocTypeManage_AddSelfDefiTypeForm;
	}
	
	function noEmpty(v){
	    if(v == "") return "不能为空";
	}	
	this.getDocTypeTable=function(){
		return docTypeTable;
	};
}