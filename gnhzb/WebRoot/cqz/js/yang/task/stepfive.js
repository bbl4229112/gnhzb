function getstepfive(){
	var evaluationmehodsbox=getevaluationmethods();
	
	var evaluationbox = Edo.build(
			
		    {
		    	type: 'box',
		    	width: 1100,
		    	height:'100%',
		    	border: [0,0,0,0],
		    	padding: [0,0,0,0],
		    	layout: 'vertical',
		    	verticalGap:'0',
		    	/*verticalAlign:'middle',
		    	horizontalAlign:'center',*/
		    	//style: 'background:white;',
		   	    children: [
					{type: 'panel',title:'步骤说明',width: '100%',height:200,layout: 'horizontal',border: [0,0,0,0],padding: [0,0,0,0],
						children:[     
					        {	type:'textarea',
					        	width : 300,
					        	height:200,
					            style:  'font-size:20px;font-family:verdana;font-weight:bold;border:0;background:rgba(0,0,0,0);',
					       		text:'请选对于该节点的默认的评价方法，并可查看相关的影响指标'
						        }]
					},      
					{type: 'panel',title:'选择默认的评价方法',width: '100%',height:100,layout: 'vertical',border: [0,0,1,0],padding: [0,0,0,0],verticalGap:'0',
						children:[
					          	evaluationmehodsbox
						        ]},
			        {type: 'panel',title:'相关的评价指标',width: '100%',height:200,layout: 'vertical',border: [0,0,1,0],padding: [0,0,0,0],verticalGap:'0',
					        	children:[
							        {
								        type: 'table',
								        id:'effectlisttable',
								        width: 420,
								        height: 300,
								        columns:[
								            
							                    {header: '编号', dataIndex: 'id',width:120,headerAlign: 'center',align: 'center'
								               
							                    },
							                    {header: '指标名称', dataIndex: 'name',width:150,headerAlign: 'center',align: 'center'
							                    },
							                    {header: '操作', dataIndex: 'operation',width:150,headerAlign: 'center',align: 'center',renderer: function(v, r){
							                    	return '<span style="cursor: pointer;" onclick="showeffectmaterialdetail();">查看相关物质列表</span>';}
							                    }
								        ]
								      
								    }
							        
							      
							    
							]
					},
					{
						type:'box',layout:'horizontal',width:'100%',padding:[10,0,0,0],border: [0,0,0,0],
						children:[
							{type: 'button',text: '上一步',style:'margin-left:100px;',width:80,height: 30,onclick: function(e){
									removeselected();
									openNewTab(stepdata.source[3]);
						        	}},
					        {type: 'button',text: '下一步',width:80,height: 30,style:'margin-left:120px;',onclick: function(e){
					        	 removeselected();
						       	 openNewTab(stepdata.source[5]);
					        	}
					        }
						]
	   	   	    	}
	   	   	        /*{
					    type: 'box',
					    border: [0,0,0,0],
					    children: [
			   	   	        {
			   	   	        	type:'textarea',
			      	        	width : 300,
			      	        	height:400,
			      	            style:  'font-size:20px;font-family:verdana;font-weight:bold;border:0;background:rgba(0,0,0,0);',
			      	        	text:'说明文字。。。。。。。。。。。。。。。dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd'
			   	   	        }, 
				   	   	    {
									type:'box',layout:'horizontal',width:'100%',padding:[0,0,0,0],border: [0,0,0,0],
									children:[
										{type: 'button',text: '上一步',style:'margin-left:100px;',width:80,height: 30,onclick: function(e){
												openNewTab(stepdata.source[3]);
									        	}},
								        {type: 'button',text: '下一步',width:80,height: 30,style:'margin-left:120px;',onclick: function(e){
									       	 openNewTab(stepdata.source[5]);
								        	}
								        }
									]
				   	   	    }]
				    },*/
					
			]});
	return evaluationbox;

	
	}
function getevaluationmethods() {
/*
	var treeroot = cims201.utils.getData(url+'tree/privilege-tree!listTreeRoots.action', {
		treeType : treetype,
		// categoryTree
		disableInte:true,
		operationName : '上传知识'
		});*/
	var methodscheckgroup = new Edo.controls.RadioGroup().set({
	
		displayField : 'name',
		repeatDirection : 'horizontal',
		repeatItems : 4,
		repeatLayout : 'table',
		itemWidth : '160px',
		valueField : 'id',
		displayField:'name',
		defaultValue:levelmodule.levelmoduleobject.evaluationmethod,
		data : [{name:'PEF',id:'1'}],
		onItemclick : function(e) {
			//alert(this.getValue());
		    var evaluationmethod=this.getValue();
       	 	levelmodule.levelmoduleobject.evaluationmethod=evaluationmethod;	                                                                     				
	        var name=e.item.name;
	        var data=[{id:'1',name:'气候变化GWP'},{id:'2',name:'酸化AP'},
	                  {id:'3',name:'资源消耗-矿物，化石'},{id:'4',name:'资源消耗-水'},
	                  {id:'5',name:'富营养化-水体'},{id:'6',name:'富营养化-陆地'},
	                  {id:'7',name:'臭氧层消耗'},{id:'8',name:'生态毒性-淡水'},
	                  {id:'9',name:'人体毒性-癌症'},{id:'10',name:'人体毒性-非癌症'},
	                  {id:'11',name:'可吸入无机物'},{id:'12',name:'电离辐射-人体健康'},
	                  {id:'13',name:'光化学臭氧合成'},{id:'14',name:'土地转化'}];
	        effectlisttable.set('data',data);
		/*	var nodeId = e.item.nodeId;
	
			cims201.utils.getData_Async(url+'tree/tree!listSubNode.action', {
						nodeId : nodeId,
						operationName : '上传知识',
						collapse : true
					}, function(text) {
						if (text == null || text == '')
							return;
						var data = Edo.util.Json.decode(text);
						tree_select.set('data', data);
						var sels = [];
						if (treetype == 'categoryTree') {
							if (null != selectedctree) {
	
								for (var i = 0, l = selectedctree.length; i < l; i++) {
									var o = tree_select.data.find({
												id : selectedctree[i]
											});
	
									if (!o)
										continue;
	
									sels.add(o);
	
									// 并且展开选中的节点
									var p = tree_select.data.findParent(o);
									tree_select.data.expand(p);
								}
	
								setTreeSelect(tree_select, sels, true, false,
										'muti');
							}
							} else {
								
								if (null != selecteddtree) {
						
									var o = tree_select.data.find({
												id : selecteddtree[0]
											});
	
									if (o)
									{	sels.add(o);
	
									// 并且展开选中的节点
									var p = tree_select.data.findParent(o);
									if(p)
									tree_select.data.expand(p);
									}
									setTreeSelect(tree_select, sels, true,
											false, 'single');
								}
								
							}
						
	
					});*/
		}
	
	});
	
	return methodscheckgroup;
}
function showeffectmaterialdetail(){
	var content= new geteffectmaterialbox();
    var toolbar=new gettoolbar();
	var win=cims201.utils.getWin(400,300,'物质列表',[content,toolbar]);
	win.show('center', 'middle', true);
	
}
function geteffectmaterialbox(){
	var materialtable=Edo.create(
		{
	        type: 'table',
	        id:'materialtable',
	        width: 380,
	        height: 250,
	        horizontalScrollPolicy:'off',
	        columns:[
                 	/*Edo.lists.Table.createMultiColumn(),*/
                    {	    	
	                headerText: '编号',
	                headerAlign: 'center',
	                align: 'center',
	                width: 100,                    
	                renderer: function(v, r, c, i, data, t){
	                    return i+1;
	            	}
                    },
                    {header: '名称', dataIndex: 'name',width:140,headerAlign: 'center',align: 'center'
                    },
                    {header: '类别', dataIndex: 'category',width:140,headerAlign: 'center',align: 'center'
                    }
	                
	        ],
	        data:[{name:'CO2'},{name:'CH4'}
	              ]
	      
	    });
	return materialtable;
}
function gettoolbar(id,func){
    var toolbar = Edo.create(
    {type: 'ct',
    cls: 'e-dialog-toolbar',
    width: '100%',
    layout: 'horizontal',
    height: 30,
    horizontalAlign: 'center',
    verticalAlign: 'middle',
    horizontalGap: 10,
    children: [
               
        {
            type: 'button',
            text: '确定',
            minWidth: 70,
            onclick: function(e){
            if(func==undefined){
            }else{
            func(id);
            }
            this.parent.parent.parent.destroy();
            }
        },{
            type: 'button',
            text: '取消',
            minWidth: 70,
            onclick: function(e){
            this.parent.parent.parent.destroy();

            }
        }
    ]
});
return toolbar;
}
