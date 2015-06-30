/**
 * 零部件分类结构框架
 */
Edo.build({
    type: 'app',
    width: '100%',height: '100%',border:[0,0,0,0],
	render: document.body,
    layout: 'horizontal',
    children:[
    	{                
            type: 'ct',
            width: '220',
            height: '100%',
            collapseProperty: 'width',
            enableCollapse: true,
            splitRegion: 'west',
            splitPlace: 'after',
            children: [
                {
                    type: 'panel',
                    title:'类别导航',
                    width: '100%',
                    height: '100%',
                    padding:[0,0,0,0],
		            titlebar:[
                        {
                            cls:'e-titlebar-toggle-west',
                            icon: 'button',
                            onclick: function(e){
                                this.parent.owner.parent.toggle();
                            }
                        }
                    ],
                 	children:[
                 		{
                 			type:'tree',
                 			id:'LbdhTree',
                 			width:'100%',
                 			height:'100%',
                 			autoColumns :true,
                 			horizontalLine : false,
                 			headerVisible :false,
                 			cls: 'e-tree-allow',
                 			columns:[
                 				{header:'分类结构',dataIndex:'text'}                 				
                 			],
                 			onselectionchange:function(e){
                 				if(e.selected.text=="分类类别"){
                 					return;
                 				}
                 				Edo.util.Ajax.request({
								    url: 'codeclass/getChildNodes.action?pid='+ e.selected.pid + '&classcode=' + e.selected.classcode,
								    type: 'get',
								    onSuccess: function(text){
								    	var o = Edo.util.Json.decode(text);
								    	if(o[0].leaf==0){
								    		o[0].__viewicon=true,
								    		o[0].icon='e-tree-folder',
								    		o[0].expanded=false;
								    	}
								        FljgTree.set('data',o);
								    },
								    onFail: function(code){
								        //code是网络交互错误码,如404,500之类
								        alert(code);
								    }
								});
                 			}
                 		}
                 	]
              
                }
            ]
        },
        {
            type: 'panel',
            title:'分类结构',
            width: '100%',
            height: '100%',
            padding:[0,0,0,0],
            layout:'vertical',
            verticalGap :0,
            children:[
            	{
            		type:'box',width: '100%',height: '30',cls: 'e-toolbar',padding:[0,0,0,0],
            		layout:'horizontal',
            		children:[
            			{type:'button',text:'全部收缩',
            				onclick:function(e){
            					if(FljgTree.data.source[0]){
            						FljgTree.data.collapse(FljgTree.data.source[0]);
            					}
            					            					  
            				}
            			},
            			{type:'button',text:'全部展开',
            				onclick:function(e){
/*            					console.log(FljgTree.data.source[0]);
            					if(FljgTree.data.source[0]){
            						FljgTree.data.expand(FljgTree.data.source[0],true);
            					}*/
            					Edo.MessageBox.alert("提示","未定义");
            					            					  
            				}
            			},
            			{type:'button',id:'addBtn',text:'添加子类',enable:false},
            			{type:'button',id:'deleBtn',text:' 删除类',enable:false},
            			{type:'button',id:'xBtn',text:'修改分类信息',enable:false}
            		]
            	},
            	{
            		type:'tree',
            		id:'FljgTree',
            		horizontalLine : false,
                 	headerVisible :false,
                 	autoColumns :true,
            		cls: 'e-tree-allow',
            		width:'100%',
            		height:'100%',
            		columns:[
            			{header:'分类结构',dataIndex:'text'}
            		],
            		onbeforetoggle: function(e){            			
                 		var row = e.record;
			            var dataTree = this.data;
			            if(!row.children || row.children.length == 0){
			                //this.addItemCls(row, 'tree-node-loading');
			                Edo.util.Ajax.request({
			                    url: 'codeclass/getChildNodes.action?pid='+ row.id + '&classcode=' + row.classcode,
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
//            			console.log(e);            点击展开时调用了两次，为什么？
            			if(e.selected){
            				addBtn.set("enable",true);
            				deleBtn.set("enable",true);
            				xBtn.set("enable",true);
            				Edo.util.Ajax.request({
							    url: 'codeclass/getTreeByCode.action',
							    type: 'get',
							    params:{
							    	nodcod: e.selected.code
							    },
							    onSuccess: function(text){
							    	var o = Edo.util.Json.decode(text);
							        FlmclForm.setForm({
							        	classname: e.selected.text, code: e.selected.code, classcode: e.selected.classcode, clsdes: o.clsdes, leaf: e.selected.leaf
							        });
							    },
							    onFail: function(code){
							        //code是网络交互错误码,如404,500之类
							        alert(code);
							    }
							});
            			}else{
            				addBtn.set("enable",false);
            				deleBtn.set("enable",false);
            				xBtn.set("enable",false);
            			}
            			
         			}
            		
            	
            	}
            ]
        },
        {                
            type: 'ct',
            width: '220',
            height: '100%',
            collapseProperty: 'width',
            enableCollapse: true,
            splitRegion: 'east',
            splitPlace: 'before',
            children: [
                {
                    type: 'panel',
                    id:'FlmclForm',
                    title:'分类明细栏',
                    width: '100%',
                    height: '100%',
                    layout:'vertical',
                    padding:[0,0,0,0],
                    titlebar:[
                        {
                            cls:'e-titlebar-toggle-east',
                            icon: 'button',
                            onclick: function(e){
                                this.parent.owner.parent.toggle();
                            }
                        }
                    ],
                    children:[
                    	{
		            		type:'box',width: '100%',height: '30',cls: 'e-toolbar',padding:[0,0,0,0],
		            		children:[
		            			{type:'button',text:'修改当前码段编码'}
		            		]
		            	},
		            	{type:'label',text:'&nbsp&nbsp&nbsp分类名称：'},
		            	{type: 'formitem',labelWidth:0,padding:[0,0,0,16],children: [
			                {name: 'classname', type: 'text',width:180}
			            ]},
			            {type:'label',text:'&nbsp&nbsp&nbsp分类码：'},
			            {type: 'formitem',labelWidth:0,padding:[0,0,0,16], children: [
			                {name: 'code', type: 'text',width:180}
			            ]},
			            {type:'label',text:'&nbsp&nbsp&nbsp所属大类代号：'},
			            {type: 'formitem',labelWidth:0,padding:[0,0,0,16], children: [
			                {name: 'classcode', type: 'text',width:180}
			            ]},
			             {type:'label',text:'&nbsp&nbsp&nbsp分类信息：'},
			            {type: 'formitem',labelWidth:0,padding:[0,0,0,16], children: [
			                {name: 'clsdes', type: 'textarea',width: 180, height: 120}
			            ]},		
			            {type: 'formitem',visible:false,children: [
			                {name: 'leaf', type: 'text'}
			            ]}
                    ]
                    
                }
            ]
        }
    ]
});      

/**
 * 将获取的数据放到类别导航窗口中
 */
Edo.util.Ajax.request({
    url: 'codeclass/getClassStruct.action',
    type: 'get',
    onSuccess: function(text){
    	var o = Edo.util.Json.decode(text);
    	var tree = [{text:'分类类别',icon:'e-tree-folder',expanded:true,children:o}];
        LbdhTree.set('data',tree);
    },
    onFail: function(code){
        //code是网络交互错误码,如404,500之类
        alert(code);
    }
});

/**
 * 点击添加子类按钮时触发的事件
 */
addBtn.on("click",function(e){
	var form=showAddChildForm();
	form.reset();
	form.setForm(FljgTree.selected);
});

/**
 * 点击修改分类信息时按钮时触发的事件
 */
xBtn.on("click",function(e){
	var form=showEditTitleForm();
	Edo.util.Ajax.request({
		url:'codeclass/getTreeByCode.action',
		type:'get',
		params:{
			nodcod:FljgTree.selected.code
		},
		onSuccess:function(text){
			var o = Edo.util.Json.decode(text);
			form.setForm({
				id:FljgTree.selected.id,
				code:FljgTree.selected.code,
				text:FljgTree.selected.text,
				excode:o.excode,
				clsdes:o.clsdes,
				standinstname : o.standinstname							
			});
			
		},
		onFail:function(code){
		}
	});
	
});

/**
 * 删除类的事件
 */
deleBtn.on('click',function(e){
	console.log(FljgTree.selected);
	var o = Edo.util.JSON.encode(FljgTree.selected);
	console.log(o);
	if(FljgTree.selected.leaf==0){
		Edo.MessageBox.alert('提示','请先删除其子类后再删除该类');
		return;
	};
	Edo.MessageBox.confirm('提示','确定删除该节点？',function(action){
		if(action=="yes"){
			
			Edo.util.Ajax.request({
				url:'codeclass/deleteNode.action',
				params:o,
				onSuccess:function(text){
					Edo.util.Ajax.request({
	                    url: 'codeclass/getChildNodes.action?pid='+ FljgTree.selected.pid + '&classcode=' + FljgTree.selected.classcode,
	                    onSuccess: function(text){
	                    	var row = FljgTree.selected; 					                    	
	                    	var dataTree = FljgTree.data;
	                        var data = Edo.util.Json.decode(text);			                        
	                        dataTree.beginChange();
	                        dataTree.removeRange (dataTree.getChildren(row,true));
	                        if(!(data instanceof Array)) data = [data]; //必定是数组
	                        for(var i=0;i<data.length;i++){
	                        	if(data[i].leaf==0){
	                        		data[i].__viewicon=true,
						    		data[i].icon='e-tree-folder',
						    		data[i].expanded=false;			                       		
	                        	}
	                        	dataTree.insert(i, data[i], row);
	                        };                    
	                        dataTree.endChange();    
	                    },
	                    onFail:function(code){
	                    	alert(code);
	                    }
	                });	
					Edo.MessageBox.alert("提示","删除子类成功");
					console.log(text);
				},
				onFail:function(code){
					alert(code);
				
				}
			});
		}
	});
	
});

/**
 * 
 * 添加子节点窗口
 */
function showAddChildForm(){
    if(!Edo.get('addChildForm')) {
        Edo.create({
            id: 'addChildForm',            
            type: 'window',title: '添加子分类节点',
            render: document.body,
            width:330,
            titlebar: [
                {
                    cls: 'e-titlebar-close',
                    onclick: function(e){
                    this.parent.owner.hide();
                    }
                }
            ],
            children: [
            	{type:'formitem',visible:false,children:[{type:'text',name:'classcode'}]},
            	{type:'formitem',visible:false,children:[{type:'text',name:'code'}]},
            	{type:'formitem',visible:false,children:[{type:'text',name:'id'}]},
            	{type:'formitem',visible:false,children:[{type:'text',name:'leaf'}]},
            	{type:'formitem',visible:false,children:[{type:'text',name:'lastchildcode'}]},
                {
                    type: 'formitem',padding:[20,0,10,0],labelWidth :'80',label: '分类名称：',
                    layout:'horizontal',
                    horizontalGap:'5',
                    children:[
                    	{	type:'text',
                    		name:'nodename',
							width:'130',
						    readOnly : true,
						    valid :nullRegex
					    },
					    {
					    	type:'button',
					    	text:'选择分类名称',
					    	onclick:function(e){
					    		showFindNameWin();
					    		Edo.util.Ajax.request({
								    url: 'partclass/ctgetstandardname.action?chinese=&english=',
								    type: 'get',
								    onSuccess: function(text){								
								    	var o = Edo.util.Json.decode(text);
								        findNameWinTable.set('data',o.list);
								    },
								    onFail: function(code){
								        //code是网络交互错误码,如404,500之类
								        alert(code);
								    }
								});
					    	}
					    }
                    		
                   ]
                },
                {
                    type: 'formitem', padding: [0,10,10, 0],label: '分类信息:<br>(不超过200字)',labelWidth :'80',
                    layout:'horizontal',
                    children:[
                        {	
                        	type: 'textarea',
                        	name : 'clsdes',
                        	width:'200',
                        	height:'100'                   	
                        },
                        {type:'space',width:'1'}
                        
                    ]
                },
                //下方按钮
                {
                	type:'ct',
                	layout:'horizontal',
                	children:[
                		{type:'space',width:'40'},
                		{type:'button',text:'提交并继续添加'},
                		{type:'space',width:'10'},
                		{type:'button',text:'提交',
                			onclick:function(e){
                				if(addChildForm.valid()){
                					var text =addChildForm.children[5].children[0].text;
	                					Edo.util.Ajax.request({
	                						url:'codeclass/addNewNode.action',
	                						type:'post',
	                						params:o,
	                						onSuccess:function(text){	
	                							var o = Edo.util.Json.decode(text);    							
	                							if(o.success==true){
	                								Edo.util.Ajax.request({
									                    url: 'codeclass/getChildNodes.action?pid='+ FljgTree.selected.id + '&classcode=' + FljgTree.selected.classcode,
									                    onSuccess: function(text){
									                    	var row = FljgTree.selected; 					                    	
									                    	var dataTree = FljgTree.data;
									                        var data = Edo.util.Json.decode(text);			                        
									                        dataTree.beginChange();
									                        dataTree.removeRange (dataTree.getChildren(row,true));
									                        if(!(data instanceof Array)) data = [data]; //必定是数组
									                        for(var i=0;i<data.length;i++){
									                        	if(data[i].leaf==0){
									                        		data[i].__viewicon=true,
														    		data[i].icon='e-tree-folder',
														    		data[i].expanded=false;			                       		
									                        	}
									                        	dataTree.insert(i, data[i], row);
									                        };                    
									                        dataTree.endChange();    
									                    },
									                    onFail:function(code){
									                    	alert(code);
									                    }
									                });	
	                							}else{
	                								Edo.MessageBox.alert("提示",o.msg);
	                							}
	                							
								                addChildForm.hide();
								                
								                
	                						},
	                						onFail:function(code){
	                							alert(code);
	                						}
	                					});
	                				
                				}
                				
                				//console.log(text!="");
                				
                			
                			}
                		},
                		{type:'space',width:'10'},
                		{type:'button',text:'取消',
                			onclick:function(e){
                				addChildForm.reset();
                				addChildForm.hide();
                			}
                		}
                	]
                }
            ]
        });
    };
    addChildForm.show('center', 'middle', true);
    return addChildForm;
};


function nullRegex(){
	if(addChildForm.children[5].children[0].text==""){
		return "分类名称不能为空";
	}
	return true;
}
/**
 * 选择分类名称窗口
 * @return {}
 */
function showFindNameWin(){
	
    if(!Edo.get('findNameWin')) {
        Edo.create({
            id: 'findNameWin',            
            type: 'window',title: '选择名称',
            render: document.body,
            width:600,
            padding:[0,0,0,0],
            titlebar: [
                {
                    cls: 'e-titlebar-close',
                    onclick: function(e){
                    this.parent.owner.hide();
                    }
                }
            ],
            children: [
            	{type:'group',layout:'horizontal',width:'100%',cls: 'e-toolbar',children:[
            		{type:'label',text:'中文名称：'},
            		{type:'text',width:'100'},
            		{type:'button',text:'检索'},
            		{type:'button',text:'清除'}
            	]},
            	{
            		type:'table',
            		id:'findNameWinTable',
            		width:'100%',height : 500,
            		autoColumns:true,
            		rowSelectMode: 'singel',
            		columns:[
            			{
		                    headerText: '',
		                    align: 'center',
		                    width: 25,                        
		                    enableSort: false,
		                    enableDragDrop: false,
		                    enableColumnDragDrop: false,
		                    style:  'cursor:move;',
		                    renderer: function(v, r, c, i, data, t){
		                        //return '<span style="padding:2px;padding-left:6px;padding-right:6px;line-height:20px;">'+i+'</span>';
		                        //return '<div style="width:25px;height:22px;text-align:center;line-height:20px;">'+(i+1)+'</div>';
		                        return i+1;
		                    }
		                },                
		                Edo.lists.Table.createMultiColumn(),
            			{header:'ID',id :'id',dataIndex:'id',visible:false},
            			{header:'中文名称',id : 'chinese',dataIndex:'chinese'},
            			{header:'英文名称',id : 'english',dataIndex:'english'},
            			{header:'备注',id : 'comment',dataIndex:'comment'}
            		],
            		onbodydblclick:function(e){
            			addChildForm.children[5].children[0].set('text',e.item.chinese);
            			findNameWin.hide();
            		}
            	}
            ]
        });
    };
    findNameWin.show('center', 'middle', true);
    return findNameWin;
};

/**
 * 
 * @return 修改分类信息窗口
 */
function showEditTitleForm(){
	if(!Edo.get('editTitleForm')){
		Edo.create({
			id:'editTitleForm',
			type:'window',
			title:'修改分类信息',
			render:document.body,
			width:400,
			titlebar:[
				{
					cls:'e-titlebar-close',
					onclick:function(e){
						this.parent.owner.hide();
					}
				}
			],
			children:[
				{type:'formitem',visible:false,children:[
					{type:'text',name:'id'}
				]},
				{type:'formitem',label:'分类码：',labelWidth:'80',width:'100%',padding:[20,0,0,0],children:[
					{type:'text',name :'code',width:'100%',readOnly:true}
				]},
				{type:'formitem',label:'分类名称：',labelWidth:'80',width:'100%',children:[
					{type:'text',name :'text',width:'100%',readOnly:true}
				]},
				{type:'formitem',label:'分类信息：<br>（不超过200字）',labelWidth:'80',width:'100%',children:[
					{type:'textarea',name :'clsdes',width:'100%',height:'150'}
				]},
				{type:'box',width:'100%',layout:'horizontal',border:[0,0,0,0],padding:[20,0,10,0],children:[
					{type:'space',width:'140'},
					{type:'button',text:'保存',
						onclick:function(e){
							if(editTitleForm.valid()){
								var o =editTitleForm.getForm();
								Edo.util.Ajax.request({
									url:'codeclass/updateClass.action',
									type:'post',
									params:o,
									onSuccess:function(text){
										Edo.util.Ajax.request({
										    url: 'codeclass/getTreeByCode.action',
										    type: 'get',
										    params:{
										    	nodcod: FljgTree.selected.code
										    },
										    onSuccess: function(text){
										    	var o = Edo.util.Json.decode(text);
										        FlmclForm.setForm({
										        	classname: FljgTree.selected.text, code: FljgTree.selected.code, classcode: FljgTree.selected.classcode, clsdes: o.clsdes, leaf: FljgTree.selected.leaf
										        });
										    },
										    onFail: function(code){
										        //code是网络交互错误码,如404,500之类
										        alert(code);
										    }
										});
													editTitleForm.hide();
												},
												onFail:function(code){
													alert(code);
												}
											});
							}
							
							
						}
						
					},
					{type:'space',width:'30'},
					{type:'button',text:'取消',
						onclick:function(e){
							editTitleForm.reset();
							editTitleForm.hide();
						}
					}
					
				]}
			]
		});
	};
	editTitleForm.show('center','middle',true);
	return editTitleForm;
}