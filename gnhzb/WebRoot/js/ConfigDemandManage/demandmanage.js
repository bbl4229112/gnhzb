function createDemandmanage(){
	var demandList = Edo.create({       
       	 type: 'panel',
       	 title:'需求列表',
         width: '50%',
         height: '100%',
         layout: 'vertical',
         verticalGap:0,    
         padding:[0,0,0,0],
         children:[
            {
			    type: 'group',
			    width: '100%',
			    layout: 'horizontal',
			    cls: 'e-toolbar',
	        	children: [
			        {type: 'button',id:'demandmanage_addDemandBtn',text: '增加需求'},
					{type: 'split'},
			        {type: 'button',id:'demandmanage_deleteDemandBtn',text: '删除需求'},
					{type: 'split'},
			        {type: 'button',id:'demandmanage_updateDemandBtn',text: '修改需求'},										        
			        {type: 'space',width:'100%'}			        
				]
            },               
      		{
			    id: 'demandmanage_demandTable', type: 'table', width: '100%', height: '100%',autoColumns: true,
			    padding:[0,0,0,0],rowSelectMode : 'single',
			    columns:[
			    	{
	                    headerText: '',
	                    align: 'center',
	                    width: 10,                        
	                    enableSort: true,
	                    enableDragDrop: true,
	                    enableColumnDragDrop: false,
	                    style:  'cursor:move;',
	                    renderer: function(v, r, c, i, data, t){
	                        return i+1;
	                	}
                    },
			    	{ dataIndex: 'id',visible : false},
			        { header: '需求名称', enableSort: true, dataIndex: 'demandName', headerAlign: 'center',align: 'center'},
			        { header: '备注', enableSort: true,width:200, dataIndex: 'demandMemo', headerAlign: 'center',align: 'center' },          			       
			    ],
			    oncellclick:function(e){
			    	var demandId =e.record.id;
			    	demandmanage_demandValueTable.set('data',
			    			cims201.utils.getData('demand/demand-value!getValueByDemandId.action?demandId='+demandId)
			    	);
			    	
			    }
      		}
         ]
	});
	
	demandmanage_demandTable.set('data',cims201.utils.getData('demand/demand-manage!getAll.action'));
	
	demandmanage_addDemandBtn.on('click',function(e){
		var form = showAddDemandForm();
		form.reset();
	});
	
	demandmanage_deleteDemandBtn.on('click',function(e){
		if(demandmanage_demandTable.selecteds.length!=1){
			Edo.MessageBox.alert('提示','请选择删除项');
			return;
		}
		Edo.MessageBox.confirm('提示','删除需求将会将需求值一起删除，确定删除吗？',function(action){
			if(action=='yes'){
				Edo.util.Ajax.request({
				    url: 'demand/demand-manage!deleteDemand.action',
				    type: 'post',
				    params:{id:demandmanage_demandTable.selected.id},
				    onSuccess: function(text){
				    		Edo.MessageBox.alert("提示", text);
				    		demandmanage_demandTable.set('data',cims201.utils.getData('demand/demand-manage!getAll.action'));
				    		demandmanage_demandValueTable.set('data',{});
				    },
				    onFail: function(code){
				        //code是网络交互错误码,如404,500之类
				        Edo.MessageBox.alert("提示", "删除失败"+code);
				    }
				});
			}
		});
	});
	
	demandmanage_updateDemandBtn.on('click',function(e){
		if(demandmanage_demandTable.selecteds.length!=1){
			Edo.MessageBox.alert('提示','请选择修改项');
			return;
		}
		var form =showAddDemandForm();
		form.set('title','修改需求');
		form.setForm(demandmanage_demandTable.selected);
		
	});
	
	function showAddDemandForm(){
		if(!Edo.get('demandmanage_addDemandForm')){
			Edo.create({
	            id: 'demandmanage_addDemandForm',            
	            type: 'window',title: '新增需求',
	            render: document.body,
	            width:330,
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
	                    type: 'formitem',padding:[20,0,10,0],labelWidth :'90',label: '需求名:',
	                    children:[{type: 'text',width:'195',name: 'demandName',valid:noEmpty}]
	                },
	                {
	                    type: 'formitem',labelWidth :'90',label: '需求备注:',
	                    children:[{type: 'textarea', height:'100',width:'195',name: 'demandMemo'}]
	                }, 
	                {
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {type: 'button', text: '提交', 
	                            onclick: function(){
	                                if(demandmanage_addDemandForm.valid()){
	                                    var o = demandmanage_addDemandForm.getForm();
	                                    console.log(o);
	                                    var urlStr='';
	                                    if(demandmanage_addDemandForm.title=='新增需求'){
	                                    	urlStr='demand/demand-manage!addDemand.action';
	                                    }
	                                    if(demandmanage_addDemandForm.title=='修改需求'){
	                                    	urlStr='demand/demand-manage!updateDemand.action';
	                                    }
	                                    Edo.util.Ajax.request({
										    url: urlStr,
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	if("添加成功！"==text){
										    		demandmanage_addDemandForm.destroy();
										    	}
										    	if("修改成功！"==text){
										    		demandmanage_addDemandForm.destroy();
										    	}
										    	Edo.MessageBox.alert("提示", text);
										    	demandmanage_demandTable.set('data',cims201.utils.getData('demand/demand-manage!getAll.action'));
										    	
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        Edo.MessageBox.alert("提示", "操作失败"+code);
										    }
										});
	                                }
	                            }
	                        },
	                        {type: 'space', width:110},
	                        {type:'button', text:'取消',
	                    		onclick:function(){
	                    			demandmanage_addDemandForm.destroy();
	                    		}
	                    	}
	                    ]
	                }
	            ]
			});
		}
		demandmanage_addDemandForm.show('center','middle',true);
		return demandmanage_addDemandForm;
	}
	var demandValueList = Edo.create({        
       	 type: 'panel',
       	 title:'需求值列表',
       	 width: '50%',
       	 height: '100%',
       	 layout: 'vertical',
       	 verticalGap:0,         
       	 padding:[0,0,0,0],
       	 children:[         		                   
             {
    		    type: 'group',
    		    width: '100%',
			    layout: 'horizontal',
			    cls: 'e-toolbar',
            	children: [
			        {type: 'button',id:'demandmanage_addDemandValueBtn',text: '新增'},
					{type: 'split'},
			        {type: 'button',id:'demandmanage_deleteDemandValueBtn',text: '删除'},
					{type: 'split'},
			        {type: 'button',id:'demandmanage_updateDemandValueBtn',text: '修改'},
			        {type: 'split'},
			        {type: 'button',id:'',text: '统计信息'},
			        {type: 'space',width:'100%'}				        
				]
             },            
      		 {
			    id: 'demandmanage_demandValueTable', type: 'table', width: '100%', height: '100%',autoColumns: true,
			    padding:[0,0,0,0],rowSelectMode : 'single',
			    columns:[
			    	{
	                    headerText: '',
	                    align: 'center',
	                    width: 10,                        
	                    enableSort: true,//(2)
	                    enableDragDrop: true,
	                    enableColumnDragDrop: false,
	                    style:  'cursor:move;',
	                    renderer: function(v, r, c, i, data, t){
	                        return i+1;
                    	}
                    },
			    	{ dataIndex: 'id',visible : false},
			        { header: '需求值', enableSort: true, dataIndex: 'demandValueName', headerAlign: 'center',align: 'center'},
			        { header: '需求值备注', enableSort: true,width:200, dataIndex: 'demandValueMemo', headerAlign: 'center',align: 'center' }      			       
			  ]    			    
      		}
         ]
	});
	
	
	demandmanage_addDemandValueBtn.on('click',function(e){
		if(demandmanage_demandTable.selecteds.length!=1){
			Edo.MessageBox.alert('提示','请选择要增加需求值所属的需求类');
			return;
		}
		showAddDemandValueForm();
	});
	demandmanage_updateDemandValueBtn.on('click',function(e){
		if(demandmanage_demandValueTable.selecteds.length !=1){
			Edo.MessageBox.alert('提示','请选择修改项');
			return;
		}
		var form  = showAddDemandValueForm();
		form.set('title','修改需求值');
		form.setForm(demandmanage_demandValueTable.selected);
	});
	demandmanage_deleteDemandValueBtn.on('click',function(e){
		if(demandmanage_demandValueTable.selecteds.length !=1){
			Edo.MessageBox.alert('提示','请选择删除项');
			return;
		}
		
		Edo.MessageBox.confirm('提示','确定删除吗？',function(action){
			if(action=='yes'){
				var demandId=demandmanage_demandValueTable.selected.id;
				Edo.util.Ajax.request({
				    url: 'demand/demand-value!deleteDemandValue.action',
				    type: 'post',
				    params:{id:demandId},
				    onSuccess: function(text){
			    		Edo.MessageBox.alert("提示", text);
			    		demandmanage_demandValueTable.set('data',
				    			cims201.utils.getData('demand/demand-value!getValueByDemandId.action?demandId='+demandId)
				    	);
				    },
				    onFail: function(code){
				        //code是网络交互错误码,如404,500之类
				        Edo.MessageBox.alert("提示", "删除失败"+code);
				    }
				});
			}
		});
		
	});
	
	function showAddDemandValueForm(){
		if(!Edo.get('demandmanage_addDemandValueForm')){
			Edo.create({
	            id: 'demandmanage_addDemandValueForm',            
	            type: 'window',title: '新增需求值',
	            render: document.body,
	            width:330,
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
	                    type: 'formitem',padding:[20,0,10,0],labelWidth :'90',label: '需求值名:',
	                    children:[{type: 'text',width:'195',name: 'demandValueName',valid:noEmpty}]
	                },
	                {
	                    type: 'formitem',labelWidth :'90',label: '需求值备注:',
	                    children:[{type: 'textarea', height:'100',width:'195',name: 'demandValueMemo'}]
	                }, 
	                {
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {type: 'button', text: '提交', 
	                            onclick: function(){
	                                if(demandmanage_addDemandValueForm.valid()){
	                                    var o = demandmanage_addDemandValueForm.getForm();
	                                    var demandId =demandmanage_demandTable.selected.id;
	                                    o.demandId =demandId ;
	                                    var urlStr='';
	                                    if(demandmanage_addDemandValueForm.title=='新增需求值'){
	                                    	urlStr='demand/demand-value!addDemandValue.action';
	                                    }
	                                    if(demandmanage_addDemandValueForm.title=='修改需求值'){
	                                    	urlStr='demand/demand-value!updateDemandValue.action';
	                                    }
	                                    Edo.util.Ajax.request({
										    url: urlStr,
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	if("添加成功！"==text){
										    		demandmanage_addDemandValueForm.destroy();
										    	}
										    	if("修改成功！"==text){
										    		demandmanage_addDemandValueForm.destroy();
										    	}
										    	Edo.MessageBox.alert("提示", text);
										    	demandmanage_demandValueTable.set('data',
										    			cims201.utils.getData('demand/demand-value!getValueByDemandId.action?demandId='+demandId)
										    	);
										    	
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        Edo.MessageBox.alert("提示", "操作失败"+code);
										    }
										});
	                                }
	                            }
	                        },
	                        {type: 'space', width:110},
	                        {type:'button', text:'取消',
	                    		onclick:function(){
	                    			demandmanage_addDemandValueForm.destroy();
	                    		}
	                    	}
	                    ]
	                }
	            ]
			});
		}
		demandmanage_addDemandValueForm.show('center','middle',true);
		return demandmanage_addDemandValueForm;
	}
	
	function noEmpty(v){
	    if(v == "") return "不能为空";
	}	
	
	this.getDemandList = function(){
		return demandList;
	};
	this.getDemandValueList =function(){
		return demandValueList;
	};
}