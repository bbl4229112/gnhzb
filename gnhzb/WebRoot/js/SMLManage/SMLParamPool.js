function createSMLParamPool(){
	var Menu =Edo.create({
	    type: 'group',
	    width: '100%',
	    layout: 'horizontal',
	    cls: 'e-toolbar',
	    children: [
			{type: 'button',id:'SMLParamPool_Add',text: '新增事物特性'},
			{type:'split'},
			{type: 'button',id:'SMLParamPool_Delete',text: '删除事物特性'},
			{type:'split'},
			{type: 'button',id:'SMLParamPool_Modify',text: '修改事物特性'},
			{type: 'space',width: '100%'},
			{type:'split'},
			{type: 'label',text: '检查内容'},
			{type:'text'},
			{type:'split'},
			{type: 'button',text: '按名称查询'},
			{type:'split'},
			{type: 'button',text: '按代码查询'},
			{type:'split'},
			{type: 'button',text: '移除查询'}		            	
	    ]
	});
	var smlTable=Edo.create({
		type:'ct',width:'100%',height:'100%',layout:'vertical',verticalGap:'0',
		children:[
		   {
			id:'SMLParamPool_Table',type:'table',width:'100%',height:'100%',autoColumns: true,
			columns:[
			     {dataIndex:'id',visible:false},
			     {dataIndex:'moduleId',visible:false},
			     {dataIndex:'smlCodeId',visible:false},
		         { header: '事物特性名称', dataIndex: 'smlName', headerAlign: 'center',align: 'center'},
		         { header: '事物特性代码', dataIndex: 'smlCode', headerAlign: 'center',align: 'center'},
		         { header: '分类代码', dataIndex: 'codeBelong', headerAlign: 'center',align: 'center'},
		         { header: '数据类型', dataIndex: 'dataType', headerAlign: 'center',align: 'center',
		        	 renderer: function(v){
	                        if(v=="INTEGER") return "整型";
	                        else if(v=="FLOAT")return "浮点型";
	                        else return "文本型";
	                 }
		         },
		         { header: '最大值', dataIndex: 'maxValue', headerAlign: 'center',align: 'center'},
		         { header: '最小值', dataIndex: 'minValue', headerAlign: 'center',align: 'center'},
		         { header: '隶属关系', dataIndex: 'moduleBelong', headerAlign: 'center',align: 'center'},
		         { header: '单位', dataIndex: 'unit', headerAlign: 'center',align: 'center'},
		         { header: '缺省值', dataIndex: 'defaultValue', headerAlign: 'center',align: 'center'},
		         { header: '描述', dataIndex: 'information', headerAlign: 'center',align: 'center'}
			 ]
		   },{
        	   id:'SMLParamPool_Paging',type:'pagingbar',width:'100%',padding:[0,0,15,0],border:[0,0,0,0],
        	   onpaging:function(e){
        		   SMLParamPoolSearch(SMLParamPool_Paging,SMLParamPool_Table);
        	   }
		   }
		 ]
		
	});
	SMLParamPoolSearch(SMLParamPool_Paging,SMLParamPool_Table);
	SMLParamPool_Add.on('click',function(e){
		var form = showAddSMLParamForm();
		 
		form.children[3].children[0].set('data',cims201.utils.getData('sml/sml-code-pool!getAllSmlCode.action '));
		form.children[4].children[0].set('data',[{ch:'整型',en:'INTEGER'},{ch:'浮点型',en:'FLOAT'},{ch:'文本型',en:'VARCHAR'}]);
		form.children[5].children[0].set('data',cims201.utils.getData('classificationtree/classification-tree!getClassStruct.action'));
		form.children[6].children[0].set('data',cims201.utils.getData('sml/sml-unit-pool!getAllSmlUnit.action'));
	});
	SMLParamPool_Delete.on('click',function(e){
		if(SMLParamPool_Table.selecteds.length!=1){
			Edo.MessageBox.alert("提示","请选择删除项");
		}else{
			Edo.MessageBox.confirm("提示","确定要删除吗？",function(action){
				if('yes'==action){
					Edo.util.Ajax.request({
					    url: 'sml/sml-parameter-pool!deleteSmlParameter.action',
					    type: 'post',
					    params:{id:SMLParamPool_Table.selected.id},
					    onSuccess: function(text){
					    		Edo.MessageBox.alert("提示", text);
					    		SMLParamPoolSearch(SMLParamPool_Paging,SMLParamPool_Table);
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
	SMLParamPool_Modify.on('click',function(e){
		if(SMLParamPool_Table.selecteds.length!=1){
			Edo.MessageBox.alert("提示","请选择修改项");
			return;
		}
		var form = showAddSMLParamForm();
		form.set('title','修改事物特性参数');
		form.reset();
		form.children[3].children[0].set('data',cims201.utils.getData('sml/sml-code-pool!getAllSmlCode.action '));
		form.children[4].children[0].set('data',[{ch:'整型',en:'INTEGER'},{ch:'浮点型',en:'FLOAT'},{ch:'文本型',en:'VARCHAR'}]);
		form.children[5].children[0].set('data',cims201.utils.getData('classificationtree/classification-tree!getClassStruct.action'));
		form.children[6].children[0].set('data',cims201.utils.getData('sml/sml-unit-pool!getAllSmlUnit.action'));
		
		form.setForm(SMLParamPool_Table.selected);

	
	});
	function SMLParamPoolSearch(paging,table){
		paging.size=18;
		var url = '';
		var partData =null;
		if(table.id=='SMLParamPool_Table'){
			url = 'sml/sml-parameter-pool!getAllSmlParameter.action?index='+paging.index+'&size='+paging.size;
			partData=cims201.utils.getData(url);
		}
		
		if(partData){
			if(partData.data==null || partData.data.length==0){
				paging.total=0;
				paging.totalPage=0;
			}else{
				paging.total=partData.total;
				if(paging.total%paging.size==0){
					paging.totalPage = paging.total/paging.size;
				}else{
					paging.totalPage = paging.total/paging.size+1;
				}
				
			}
			table.set('data',partData.data);
			paging.refresh();
		}
	}
	
	function showAddSMLParamForm(){
		if(!Edo.get('SMLParamPool_AddSMLParamForm')){
			Edo.create({
	            id: 'SMLParamPool_AddSMLParamForm',            
	            type: 'window',title: '新增事物特性参数',
	            render: document.body,
	            width:290,
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
	                    type: 'formitem',padding:[20,0,10,0],labelWidth :'90',label: '事物特性名称:',
	                    children:[{type: 'text',width:'165',name: 'smlName',valid: noEmpty}]
	                },
	                {
	                    type: 'formitem',labelWidth :'90',label: '事物特性代码:',
	                    children:[{type: 'text', width:'165',name: 'smlCode',valid: noEmpty}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '事物特性分类:',
	                    children:[{type: 'combo',width:'165',name: 'smlCodeId',displayField:'firstCode',valueField:'id',readOnly:true}]
	                }, 
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '数据类型:',
	                    children:[{type: 'combo',width:'165',name: 'dataType',displayField:'ch',valueField:'en',readOnly:true,
	                    	onselectionchange:function(e){
	                    		if(e.selectedItem.en=='VARCHAR'){
	                    			SMLParamPool_AddSMLParamForm.children[7].children[0].set('text','0');
	                    			SMLParamPool_AddSMLParamForm.children[8].children[0].set('text','0');

	                    			SMLParamPool_AddSMLParamForm.children[7].children[0].set('readOnly',true);
	                    			SMLParamPool_AddSMLParamForm.children[8].children[0].set('readOnly',true);           			
	                    		}else if(e.selectedItem.en=='INTEGER'){
	                    			SMLParamPool_AddSMLParamForm.children[7].children[0].set('text','0');
	                    			SMLParamPool_AddSMLParamForm.children[8].children[0].set('text','0');
	                    			
	                    			SMLParamPool_AddSMLParamForm.children[7].children[0].set('readOnly',false);
	                    			SMLParamPool_AddSMLParamForm.children[8].children[0].set('readOnly',false);                   			
	                    		}else{
	                    			SMLParamPool_AddSMLParamForm.children[7].children[0].set('text','0.0');
	                    			SMLParamPool_AddSMLParamForm.children[8].children[0].set('text','0.0');

	                    			SMLParamPool_AddSMLParamForm.children[7].children[0].set('readOnly',false);
	                    			SMLParamPool_AddSMLParamForm.children[8].children[0].set('readOnly',false);     			
	                    		}
	                    	}
	                    }]
	                },  
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '隶属于:',
	                    children:[{type: 'combo', width:'165',name: 'moduleId',displayField:'text',valueField:'id',readOnly:true}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '单位:',
	                    children:[{type: 'combo', width:'165',name: 'unitId',displayField:'unitCode',valueField:'id',readOnly:true}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '最大值:',
	                    children:[{type: 'text', width:'165',name: 'maxValue',valid: floatRegex}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '最小值:',
	                    children:[{type: 'text', width:'165',name: 'minValue',valid: floatRegex}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '缺省值:',
	                    children:[{type: 'text', width:'165',name: 'defaultValue'}]
	                },
	                {
	                    type: 'formitem',padding:[10,0,0,0],labelWidth :'90',label: '描述:',
	                    children:[{type: 'textarea', height:'80',width:'165',name: 'information'}]
	                },
	                {
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {type: 'button', text: '提交', 
	                            onclick: function(){
	                                if(SMLParamPool_AddSMLParamForm.valid()){
	                                    var o = SMLParamPool_AddSMLParamForm.getForm();
	                                    if(null==o.moduleId ||  null==o.smlCodeId ||null ==o.unitId || null ==o.dataType ){
	                                    	Edo.MessageBox.alert("提示","数据填写不完整，请重新填写");
	                                    	return;
	                                    }
	                                    var urlStr='';
	                                    if(SMLParamPool_AddSMLParamForm.title=='新增事物特性参数'){
	                                    	urlStr='sml/sml-parameter-pool!addSmlParameter.action';
	                                    }
	                                    if(SMLParamPool_AddSMLParamForm.title=='修改事物特性参数'){
	                                    	urlStr='sml/sml-parameter-pool!modifySmlParameter.action';
	                                    }
	                                    Edo.util.Ajax.request({
										    url: urlStr,
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	if("添加成功！"==text){
										    		SMLParamPool_AddSMLParamForm.destroy();
										    	}
										    	if("修改成功！"==text){
										    		SMLParamPool_AddSMLParamForm.destroy();
										    	}
										    	Edo.MessageBox.alert("提示", text);
										    	SMLParamPoolSearch(SMLParamPool_Paging,SMLParamPool_Table);
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        Edo.MessageBox.alert("提示", "操作失败"+code);
										    }
										});
	                                    
	                                }
	                            }
	                        },
	                        {type: 'space', width:50},
	                        {type:'button', text:'取消',
	                    		onclick:function(){
	                    			SMLParamPool_AddSMLParamForm.destroy();
	                    		}
	                    	}
	                    ]
	                }
	            ]
			});
			SMLParamPool_AddSMLParamForm.show('center','middle',true);
			return SMLParamPool_AddSMLParamForm;
		}
	}
	

	function floatRegex(v){
		console.log(SMLParamPool_AddSMLParamForm.children[4].children[0]);
	    if ((v.search(/^\d+(\.\d+)?$/) != -1) & v>=0){
	    	return true;
	    }else {return "请输入大于或等于零的数!";}
	};

	function noEmpty(v){
	    if(v == "") return "不能为空";
	}	
	
	this.getMenu=function(){
		return Menu;
	};
	
	this.getSMLTable=function(){
		return smlTable;
	};
	
}