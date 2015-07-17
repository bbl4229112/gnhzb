function createSMLEdit(){
	var CodeClassChoose =Edo.create({
		type:'panel',
		title:'分类导航',
		height:'100%',
		width:'250',
		padding:[0,0,0,0],
		layout:'vertical',
		verticalGap:0,
		children:[
		   {
			   type:'box',width:'100%',height:'35',cls:'e-toolbar',layout:'horizontal',
			   children:[
			       {type:'label',text:'请选择分类'},
			       {	type:'combo', id:'SMLEditClassNameCombo',
						width:'150',
						Text :'请先选择适合的分类',
						readOnly : true,
						valueField: 'id',
					    displayField: 'text',			    
					    onselectionchange: function(e){			
					    	if(e.selectedItem.leaf==0){
					    		e.selectedItem.__viewicon=true;
					    		e.selectedItem.expanded=false;
					    		e.selectedItem.icon='e-tree-folder';
					    	}else{
					    		e.selectedItem.icon='e-tree-folder';
					    	}
					    	SMLEditTree.set('data',e.selectedItem);
					    }
			       }
			   ]
		   },
		   {
			   type:'tree',id:'SMLEditTree',width:'100%',height:'100%',autoColumns:true,horizontalLine:false,headerVisible:false,
			   columns:[{dataIndex:'text'}],
			   onbeforetoggle: function(e){            			
           		var row = e.record;
		            var dataTree = this.data;
		            if(!row.children || row.children.length == 0){
		                //this.addItemCls(row, 'tree-node-loading');
		                Edo.util.Ajax.request({
		                    url: 'classificationtree/classification-tree!getChildrenNode.action?pid='+ row.id,
		                    //defer: 500,
		                    onSuccess: function(text){
		                        var data = Edo.util.Json.decode(text);			                        
		                        dataTree.beginChange();
		                        if(!(data instanceof Array)) data = [data]; //必定是数组
		                        for(var i=0;i<data.length;i++){
		                        	if(data[i].leaf==0){
		                        		data[i].__viewicon=true,
							    		data[i].icon='e-tree-folder',
							    		data[i].expanded=false;		
		                        	}else{
		                        		data[i].icon='ui-module';
		                        	}
		                        	dataTree.insert(i, data[i], row);
		                        };                    
		                        dataTree.endChange();    
		                    }
		                });
		            }
		            return !!row.children;
           		},
			   onselectionchange:function(e){
				   if(e.selected){
						 if(e.selected.code){
							 var tableName=e.selected.code;
							 SMLEdit_ct.children[0].addChild(createSMLTable(tableName));
						 }
				   }			   
				},
				data:[{text:'请先选择结构所属分类'}]
		   }
		]
	});
	
	var smlEditCt =Edo.create({
		id:'SMLEdit_ct',
		type:'ct',
		height:'100%',
		width:'100%',
		layout:'vertical',
		verticalGap:0,
		enable:false,
		children:[
	          {
	        	type:'panel',title :'事物特性表编辑',layout:'vertical',verticalGap:0,width:'100%',height:'100%',padding:[0,0,0,0],
	        	children:[
		           {
		        	   type:'box',width:'100%',height:'32',cls:'e-toolbar',layout:'horizontal',
		        	   children:[
        	             {type:'button',id:'',text:'查看主模型'},
        	             {type:'split'},
        	             {type:'button',id:'',text:'查看主文档'},
        	             {type:'split'},
        	             {type:'button',text:'参与计算整机长'},
        	             {type:'split'},
        	             {type:'button',text:'查看接口'},
        	             {type:'space',width:'100%'},
        	             {type:'label',text:'<span style="color:red;">双击字段进行编辑</span>'}
		        	   ]
		           }
	        	]
	          }
		]
	});
	

	SMLEditClassNameCombo.set('data',cims201.utils.getData('classificationtree/classification-tree!getClassStruct.action'));
	
	function createSMLTable(tableName){
		if(Edo.get('SMLEdit_SMLTable')){
		   SMLEdit_SMLTable.destroy();
	    }
		var columnsData =cims201.utils.getData('sml/sml-table-field!getSmlTableField.action?tableName='+tableName);
		console.log(columnsData);
		var columnChildren = new Array();
		columnChildren[0]={header:'id',dataIndex:'id',visible:false};
		columnChildren[1]={header:'partId',dataIndex:'partId',visible:false};
		for(var i=2;i<columnsData.length+2;i++){
			var columnData=columnsData[i-2];
			columnChildren[i] = {header:columnData.headShow+'(<span style="color:red;">'+columnData.tableHead+'</span>)',dataIndex:columnData.tableHead,headerAlign: 'center',align:'center'};
		}

		var table =new Edo.lists.Table();
		table.set('id','SMLEdit_SMLTable');
		table.set('width','100%');
		table.set('height','100%');
		//table.set('autoColumns',true);
		table.set('showHeader',true);
		table.set('columns',columnChildren);
		table.set('enableCellSelect',true);
		
		
		Edo.util.Ajax.request({
		    url: 'sml/sml-table-field!checkSmlTableByTableName.action',
		    type: 'post',
		    params:{tableName:tableName},
		    onSuccess: function(text){
		    	if(text=='no'){
		    		SMLEdit_ct.set('enable',false);
		    		table.set('data',{});
		    	}else{
		    		SMLEdit_ct.set('enable',true);
		    		var tableData = cims201.utils.getData('sml/sml-table-field!getSmlTableByTableName.action?tableName='+tableName);
		    		table.set('data',tableData);
		    	}
		    },
		    onFail: function(code){
		        //code是网络交互错误码,如404,500之类
		        Edo.MessageBox.alert("提示", "操作失败"+code);
		    }
		});
		
		table.on('celldblclick',function(e){
			if(e.column.dataIndex=="partnumber"){
				Edo.MessageBox.alert("提示","实例编码不能编辑");
				return;
			}
			if(e.column.dataIndex=="partname"){
				Edo.MessageBox.alert("提示","实例名称不能编辑");
				return;
			}
			
			var tableName = SMLEditTree.selected.code;
			var fieldName = e.column.dataIndex;
			var data = cims201.utils.getData('sml/sml-table-field!getMsgForEditPartSMLForm.action?tableName='+tableName+'&tableHead='+fieldName);
			var unit=data.unit;
			var smlName =data.smlName;
			var dataType = data.dataType;
			console.log(unit);
			var fieldLabel =smlName+'(<span style="color:red;">'+unit+'</span>)：';
			console.log(fieldLabel);
			e.record.dataType=dataType;
			var form = showEditPartSMLForm(tableName,fieldName,fieldLabel);
			form.setForm(e.record);

		});
		return table;
	}
	/**
	 * 
	 * @param tableName  表名
	 * @param fieldName  表单事物特性栏的key
	 * @param fieldLabel 表单事物特性栏的标签
	 * @returns 事物特性表修改表单
	 */
	function showEditPartSMLForm(tableName,fieldName,fieldLabel){
		
		if(!Edo.get('SMLEdit_EditPartSmlForm')){
			Edo.create({
				id:'SMLEdit_EditPartSmlForm',
				type:'window',
				title:'编辑',
				width:'260',
				render:document.body,
				titlebar:{
					cls:'e-titlebar-close',
					onclick:function(e){
						this.parent.owner.destroy();
					}
				},
				children:[
					{type:'formitem',visible:false,children:[{type:'text',name:'id'}]},
					{type:'formitem',visible:false,children:[{type:'text',name:'dataType'}]},
					{
					    type: 'formitem',padding:[20,0,10,0],labelWidth :'83',label: '实例编码：',
					    children:[{type: 'text',width:'165',name: 'partnumber',readOnly:true}]
					},
					{
					    type: 'formitem',padding:[0,0,10,0],labelWidth :'83',label: '实例名称：',
					    children:[{type: 'text',width:'165',name: 'partname',readOnly:true}]
					},
					{
					    type: 'formitem',padding:[0,0,10,0],labelWidth :'83',label: fieldLabel,
					    children:[{type: 'text',width:'165',name: fieldName}]
					},
					{
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {type: 'button', text: '提交', 
	                            onclick: function(){
	                                if(SMLEdit_EditPartSmlForm.valid()){
	                                    var o = SMLEdit_EditPartSmlForm.getForm();
	                                    var tableName=SMLEditTree.selected.code;
	                                    var tableHead = SMLEdit_SMLTable.cellSelected.column.dataIndex;
	                                    o.tableName = tableName;
	                                    o.tableHead = tableHead;
	                                    o.smlValue = o[o.tableHead];
	                                    Edo.util.Ajax.request({
										    url: 'sml/sml-table-field!modifyPartSML.action',
										    type: 'post',
										    params:o,
										    onSuccess: function(text){
										    	if('修改成功！'==text){
										    		var tableData = cims201.utils.getData('sml/sml-table-field!getSmlTableByTableName.action?tableName='+tableName);
											    	SMLEdit_SMLTable.set('data',tableData);
											    	SMLEdit_EditPartSmlForm.destroy();
										    	}
										    	Edo.MessageBox.alert("提示", text);
										    	
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
	                    			SMLEdit_EditPartSmlForm.destroy();
	                    		}
	                    	}
	                    ]
	                }
				]
			});
			
			SMLEdit_EditPartSmlForm.show('center','middle',true);
			return SMLEdit_EditPartSmlForm;
		}
	}
	
	this.getCodeClassChoose=function(){
		return CodeClassChoose;
	};
	
	this.getSMLEditCt=function(){
		return smlEditCt;
	};
}