function createCodeClassStructManage_check(classficationTreeId){
	//类别导航
/*	var LbdhPanel =Edo.create({          
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
                 			id:'LbdhTree_check',
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
                 					CodeClassStructManageLock.set('enable',false);
                 					return;
                 				}
                 				var fljgTreeData = e.selected;
                 				if(fljgTreeData.leaf == 0){
                 					fljgTreeData.__viewicon = true;
                 					fljgTreeData.expanded = false;
                 				}else{
                 					fljgTreeData.icon='ui-module';
                 				}
                 				FljgTree.set('data',fljgTreeData);
                 				var lock = true;
                 				//1表示解除锁定，默认情况下是锁定状态0
                 				if(fljgTreeData.lockTree == 1){
                 					lock = false;
                 				}
                 				CodeClassStructManageLock.set('checked',lock);
                 				CodeClassStructManageLock.set('enable',true);
                 			}
                 		}
                 	]
              
                }
            ]	
	});*/
	
	//luweijiang
/*	function codeClassStructManageTask(classficationTreeId){
		var lbdhTreeData =cims201.utils.getData('classificationtree/classification-tree!getClassStructById.action',{id:classficationTreeId});
		//console.log(lbdhTreeData);
		for(var i =0;i<lbdhTreeData.length;i++){
			lbdhTreeData[i].icon='e-tree-folder';
		}
		LbdhTree.set('data',
				[{text:'分类类别',icon:'e-tree-folder',expanded:true,children:lbdhTreeData}]
		);
		
	}*/
	
	var fljgTreeData =cims201.utils.getData('classificationtree/classification-tree!getClassStructById.action',{id:classficationTreeId});
/*	console.log(fljgTreeData[0]);
		if(fljgTreeData[0].leaf == 0){
			fljgTreeData[0].__viewicon = true;
			fljgTreeData[0].expanded = false;
		}else{
			fljgTreeData[0].icon='ui-module';
		}*/
		//FljgTree.set('data',fljgTreeData);
	//分类结构
	if(!Edo.get('CodeClassStructManage_check_window')){
		var FljgPanel_check = Edo.create({		
            type: 'panel',
            title:'分类结构',
            width: '100%',
            height: '100%',
            padding:[0,0,0,0],
            layout:'vertical',
            verticalGap :0,
            children:[
            	{
            		type:'tree',
            		id:'FljgTree_check',
            		horizontalLine : false,
                 	headerVisible :false,
                 	autoColumns :true,
            		cls: 'e-tree-allow',
            		width:'100%',
            		height:'100%',
            		columns:[
            			{header:'分类结构',dataIndex:'text'}
            		],
            		onselectionchange:function(e){
//            			console.log(e);            点击展开时调用了两次，为什么？
            			if(e.selected){
            				FlmclForm_check.setForm(e.selected);
            			}else{
            			}
            			
         			},
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
                 	}
            	}
            ]
	});
		
		//分类明细栏
		var FlmclForm_check = Edo.create({             
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
	                    id:'FlmclForm_check',
	                    title:'分类明细栏',
	                    width: '100%',
	                    height: '100%',
	                    layout:'vertical',
	                    padding:[0,0,0,0],
	                    children:[
			            	{type:'label',text:'&nbsp&nbsp&nbsp分类名称：'},
			            	{type: 'formitem',labelWidth:0,padding:[0,0,0,16],children: [
				                {name: 'text', type: 'text',width:180}
				            ]},
				            {type:'label',text:'&nbsp&nbsp&nbsp分类码：'},
				            {type: 'formitem',labelWidth:0,padding:[0,0,0,16], children: [
				                {name: 'code', type: 'text',width:180}
				            ]},
				            {type:'label',text:'&nbsp&nbsp&nbsp所属大类代号：'},
				            {type: 'formitem',labelWidth:0,padding:[0,0,0,16], children: [
				                {name: 'classCode', type: 'text',width:180}
				            ]},
				             {type:'label',text:'&nbsp&nbsp&nbsp分类信息：'},
				            {type: 'formitem',labelWidth:0,padding:[0,0,0,16], children: [
				                {name: 'classDes', type: 'textarea',width: 180, height: 120}
				            ]}
	                    ]      
	                }
	            ]
		});
		
		Edo.create({
			id:'CodeClassStructManage_check_window',
			type:'window',
			title:'产品结构审批',
			height:'400',
			width:'600',
			padding:[0,0,0,0],
			titlebar:[
	            {
	                cls: 'e-titlebar-close',
	                onclick: function(e){
	                    this.parent.owner.hide();       //hide方法
	                }
	            }
	        ],
	        layout:'horizontal',
	        children:[FljgPanel_check,FlmclForm_check]
		})
	}
	
	FljgTree_check.set('data',fljgTreeData);
	
	CodeClassStructManage_check_window.show('center','middle',true);

}