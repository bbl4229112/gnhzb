function createSMLCodePool(){

	var Menu =Edo.create({
	    type: 'group',
	    width: '100%',
	    layout: 'horizontal',
	    cls: 'e-toolbar',
	    children: [
			{type:'button',id:'SMLCodePool_Add',text: '新增分类'},
			{type:'split'},
			{type:'button',id:'SMLCodePool_Modify',text: '修改分类'},
			{type:'split'},
			{type:'button',id:'SMLCodePool_Delete',text: '删除'},
				            	
	    ]
	});
	var smlCodeTable=Edo.create({
		id:'SMLCodePool_Table',type:'table',width:'100%',height:'100%',autoColumns: true,
		columns:[
		     {dataIndex:'id',visible:false},
	         { header: '分类码', dataIndex: 'firstCode', headerAlign: 'center',align: 'center'},
	         { header: '名称', dataIndex: 'codeName', headerAlign: 'center',align: 'center'},
	         { header: '描述', dataIndex: 'information', headerAlign: 'center',align: 'center'}
		]
	});
	
	SMLCodePool_Table.set('data',cims201.utils.getData('sml/sml-code-pool!getAllSmlCode.action'));
	
	SMLCodePool_Add.on('click',function(e){
		var form = showAddSMLCodeForm();
		form.reset();
	});
	SMLCodePool_Modify.on('click',function(e){
		if(SMLCodePool_Table.selecteds.length != 1){
			Edo.MessageBox.alert("提示","请选择要修改项");
			
		}else{
			var form=showAddSMLCodeForm();
			form.set('title','编辑事物特性分类参数');
			form.reset();
			form.setForm(SMLCodePool_Table.selected);
		}
		
	});
	SMLCodePool_Delete.on('click',function(e){
		if(SMLCodePool_Table.selecteds.length != 1){
			Edo.MessageBox.alert("提示","请选择要删除项");
			
		}else{
			Edo.MessageBox.confirm("提示","确定要删除吗？",function(action){
				if('yes'==action){
					 Edo.util.Ajax.request({
						    url: 'sml/sml-code-pool!deleteSmlCode.action',
						    type: 'post',
						    params:{id:SMLCodePool_Table.selected.id},
						    onSuccess: function(text){
						    	Edo.MessageBox.alert("提示", text);
						    	SMLCodePool_Table.set('data',cims201.utils.getData('sml/sml-code-pool!getAllSmlCode.action'));
						    },
						    onFail: function(code){
						        //code是网络交互错误码,如404,500之类
						        Edo.MessageBox.alert("提示", "操作失败"+code);
						    }
						});
				}
			});
		}
	});
	
	function showAddSMLCodeForm(){
		if(!Edo.get('SMLCodePool_AddSMLCodeForm')){
			Edo.create({
	            id: 'SMLCodePool_AddSMLCodeForm',            
	            type: 'window',title: '新增事物特性分类',
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
	                {type:'formitem',visible:false,children:[{type:'text',name:'id'}]},
	                {
	                    type: 'formitem',padding:[20,0,10,0],labelWidth :'55',label: '分类码:',
	                    children:[{type: 'text',width:'165',name: 'firstCode',valid: noEmpty,autoValid:false}]
	                },
	                {
	                    type: 'formitem',labelWidth :'55',label: '名称:',
	                    children:[{type: 'text', width:'165',name: 'codeName',valid: noEmpty,autoValid:false}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'55',label: '描述:',
	                    children:[{type: 'textarea',height:'80',width:'165',name: 'information'}]
	                },  
	                {
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {type: 'button', text: '提交', 
	                            onclick: function(){
	                                if(SMLCodePool_AddSMLCodeForm.valid()){
	                                    var o = SMLCodePool_AddSMLCodeForm.getForm();
	                                    console.log(o);
	                                    var urlStr='';
	                                    if(SMLCodePool_AddSMLCodeForm.title=='新增事物特性分类'){
	                                    	urlStr='sml/sml-code-pool!addSmlCode.action';
	                                    }
	                                    if(SMLCodePool_AddSMLCodeForm.title=='编辑事物特性分类参数'){
	                                    	urlStr='sml/sml-code-pool!modifySmlCode.action';
	                                    }
	                                    console.log(urlStr);
	                                    Edo.util.Ajax.request({
										    url: urlStr,
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	Edo.MessageBox.alert("提示", text);
										    	SMLCodePool_Table.set('data',cims201.utils.getData('sml/sml-code-pool!getAllSmlCode.action'));
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        Edo.MessageBox.alert("提示", "操作失败"+code);
										    }
										});
	                                    SMLCodePool_AddSMLCodeForm.destroy();
	                                }
	                            }
	                        },
	                        {type: 'space', width:50},
	                        {type:'button', text:'取消',
	                    		onclick:function(){
	                    			SMLCodePool_AddSMLCodeForm.destroy();
	                    		}
	                    	}
	                    ]
	                }
	            ]
	        });
		}
		SMLCodePool_AddSMLCodeForm.show('center','middle',true);
		return SMLCodePool_AddSMLCodeForm;
	}
	function noEmpty(v){
	    if(v == "") return "不能为空";
	}	
	
	this.getMenu=function(){
		return Menu;
	};
	
	this.getSMLCodeTable=function(){
		return smlCodeTable;
	};
	

}