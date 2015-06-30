<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">	
	
	var resultjsonarray = "";
	
	function startCalculator(cId){
		//首先将背景样式除去
		Edo.util.Dom.removeClass(document.getElementById('leftarea'),'leftarea'); 
		
		if(Edo.get('carbonCalculatorBox')){
			Edo.get('carbonCalculatorBox').destroy();
		}
		
		Edo.create({
			id: 'carbonCalculatorBox',
			type: 'ct',
			render: document.getElementById('leftarea'),
			width: '90%',
			height: 700,
			verticalGap: 0,
			layout: 'vertical',
			children: [{
			    id: 'tbar',
				type: 'tabbar',
				selectedIndex: 0,
				border: [0,0,1,0],
				style: 'background: white',//red
				onselectionchange: function(e){
					tabContent.set('selectedIndex', e.selectedIndex);
				},
				children: [
					{type: 'button',width: 100, text: '设计阶段', icon: 'e-icon-design'},
					{type: 'button',width: 120, text: '原材料获取阶段', icon: 'e-icon-material'},
					{type: 'button',width: 100, text: '制造阶段', icon: 'e-icon-manufacture'},
					{type: 'button',width: 100, text: '运输阶段', icon: 'e-icon-transport'},
					{type: 'button',width: 100, text: '服务阶段', icon: 'e-icon-service'},
					{type: 'button',width: 100, text: '回收阶段', icon: 'e-icon-recycle'}
				]
			},
			{
				id: 'tabContent',
				//selectedIndex: 0,
				layout: 'viewstack',
				type: 'box',
				width: 900,
				height: '100%',
				border: [0,1,1,1],
				verticalScrollPolicy: 'off',
				children: [
					createStageCalculator(1, cId),
					createStageCalculator(2, cId),
					createStageCalculator(3, cId),
					createStageCalculator(4, cId),
					createStageCalculator(5, cId),
					createStageCalculator(6, cId)
				]
			},{
				type: 'box',
				border: [0,0,0,0], 
			    padding: [0,0,0,0], 
			    width: '100%',
			    height: '100%',
			    layout: 'horizontal',
				children: [dataPanel,chartPanel]
			}]
		});	
	}

	//构建图表panel   江丁丁添加   20120112
	var chartPanel = Edo.create({
		type: 'box',
		width: '50%',
		height: 250,
		border: [1,1,1,1],
		padding: [0,0,0,0],
		layout: 'vertical', 
		children: [new lcaAnalysis(resultjsonarray,'chart')]
	});
	
	var dataPanel = Edo.create({
		type: 'panel',
		title: 'Result',
		padding: [10,0,0,8], 
		width: '50%',
		height: 250,
		border: [1,1,1,1],
		layout: 'vertical',
		children: [
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: 'Designing Stage:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_1'},
				{type: 'label', text: 'kg'}
			]},
			
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
			 	{type: 'label', text: 'Material_Obtaining Stage:', width: 200},
			 	{type: 'label', text: '0', id: 'stageresult_2'},
			 	{type: 'label', text: 'kg'}
			]},
			
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: 'Manufacturing Stage:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_3'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: 'Transporint and Saling Stage:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_4'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: 'Service Stage:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_5'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: 'Recycling Stage:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_6'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: 'Final Result:', width: 200},
				{type: 'label', text: '0', id: 'finalresult'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'button', width: '100', text: 'calculating', onclick: function(e){
					calculateStageEmission();
					
					//构建图表panel   江丁丁添加   20120112
					chartPanel.addChild({
						type: 'box',
						width: '100%',
						height: 250,
						border: [0,0,0,0],
						padding: [0,0,0,0],
						layout: 'vertical', 
						children: [	new lcaAnalysis(resultjsonarray,'chart')]
					});				
					
				}},
				{type: 'button', width: '100', text: 'reset', onclick: function(e){
					resultjsonarray = "";
					for(var i=1; i<=5; i++){							
						Edo.get('calTable_'+i).data.beginChange();
						Edo.get('calTable_'+i).data.source.each(function(o){							
							Edo.get('calTable_'+i).data.update(o, 'emission', 0);
							Edo.get('calTable_'+i).data.update(o, 'sum', 0);
						});							
						Edo.get('calTable_'+i).data.endChange();
						Edo.get('stageresult_'+i).set('text',0);			
					}
					Edo.get('finalresult').set('text',0);					
					//重置图表panel   江丁丁添加   20120112
					chartPanel.addChild({
						type: 'box',
						width: '100%',
						height: 250,
						border: [0,0,0,0],
						padding: [0,0,0,0],
						layout: 'vertical', 
						children: [	new lcaAnalysis(resultjsonarray,'chart')]
					});	
				}},
				{type: 'button', width: '100', text: 'save', onclick: function(){
					var or = new Array();
					for(var i=1; i<=5; i++){							
						Edo.get('calTable_'+i).data.source.each(function(o){							
							or[or.length] = {
								materialId: o.id,
								componentId: cId,
								emission: o.emission,
								sum: o.sum									
							};
						});										
					}
					
					//加载第一次数据
					Edo.util.Ajax.request({
				        type: 'post',        
				        url: 'lca/calculator!saveCarbonEmission.action',
				        params: {
			                data: Edo.util.Json.encode(or)   //传递父节点的Name(也可以是ID)
			            },
				        onSuccess: function(text){
				            alert('success!');
				            var data = Edo.util.Json.decode(text);
				            Edo.get('componentTree').set('data', data);
				        }
				    });
				}}
			]}
		]
	});
	
	
	
	function lcaAnalysis(data,select) {
		var outPanel = null;
		var analysis = Edo.create({
			type: 'box',
	    	width: '100%',
	    	height: '100%',
	    	border: [0,0,0,0],
	    	padding: [0,0,0,0],
	    	layout: 'vertical',
	    	verticalScrollPolicy: 'off',
	    	children: [
				{
					type: 'module',
					width: '100%',
					height: '100%',
			   		border: [0,0,0,0],
			   		src: 'lca/calculator!showChart.action?data=' + data
				}
			]
		});
		outPanel = Edo.create({
			type: 'box',
			width: '100%',
			height: '100%',
			border: [0,0,0,0],
			padding: [0,0,0,0],
			layout: 'vertical',
			verticalScrollPolicy: 'auto',
			children: [
				analysis
			]
		});
		
		return outPanel;
	}
	
	
	//创建每一个阶段的计算界面
	function createStageCalculator(stage, cId){	    
	    var tempEditText = 0;
	    var calTable = Edo.create({
	    	id: 'calTable_'+stage,
	    	type: 'table',                
	        width: '100%', 
	        //width: 950, 
	        height: 400,       
	        showHeader: true,
        	//rowSelectMode: 'multi',        
        	enableDragDrop: false,   
        	//data: data,
        	columns:[
        	//	Edo.lists.Table.createMultiColumn(), 		
        		{
                    headerText: '序号>',
                    align: 'center',
                    width: 60,                        
                    enableSort: false,
                    enableDragDrop: true,
                    enableColumnDragDrop: false,
                    style:  'cursor:move;',
                    headerAlign: 'center',
                    renderer: function(v, r, c, i, data, t){                        
                        return i+1;
                    }
                },    
                {header: 'Name of Material', width: 120,  dataIndex: 'name', headerAlign: 'center', align: 'center'},        
                {header: 'CO<font size =1px>2</font> Emission per Unit(kg)',width: 200, dataIndex: 'emission', headerAlign: 'center', align: 'center', editor: {
                	type: 'text'
                },valid: function(v,r){
                	var reg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;                	
                	if(reg.exec(v)){              		
                	}else{    
                		if(v == '0' || v == 0){
                		               			
                		}else{
                			return 'please enter float number!';
                		}       		        
                	}       	
                }},        
                {header: 'Sum of Using(unit)', dataIndex: 'sum', width: 120, headerAlign: 'center', align: 'center', editor: {
                	type: 'text'
                },valid: function(v,r){
                	var reg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;             	
                	if(reg.exec(v)){               		
                	}else{               		
                		if(v == '0' || v == 0){
                		               			
                		}else{
                			return 'please enter float number!';
                		} 
                	}
                	
                }},
                {header: '描述', dataIndex: 'description', width: 150, headerAlign: 'center'}                
        	],
        	onbodymousedown: function(e){
        		//var wef = 1;
        		//alert('X:'+e.screenX+'  Y:'+e.screenY);
        		//加载第一次数据
        		if('emission' == e.column.dataIndex){
        			Edo.util.Ajax.request({
				        type: 'post',        
				        url: 'lca/calculator!carbonEmissionStatistics.action',
				        params: {
			                componentId: cId,   //传递父节点的Name(也可以是ID)
			            	materialId: e.item.id
			            },
				        onSuccess: function(text){
				            var data = Edo.util.Json.decode(text);			            				            
				            var win;
							if(Edo.get('statisticForm') != null){
								win = Edo.get('statisticForm');
							}else{
								win = new Edo.containers.Window();
								win.set('id','statisticForm');
								win.set('title', 'Tips');
								win.set('titlebar',
								    [      //头部按钮栏
								        {
								            cls: 'e-titlebar-close',
								            onclick: function(e){		           
								                this.parent.owner.destroy();       //hide方法
								            }
								        }
								    ]
								);
								win.addChild({
								    type: 'box',
								    id: 'statisticsBox',
								    width: 300,
								    //height: 100,
								    layout: 'vertical',    
								    border: 0,
								    children: []
								});	
							}
							Edo.get('statisticsBox').removeAllChildren();
							for(var i=0 ; i<data.length; i++){
								Edo.get('statisticsBox').addChild({
									type: 'label',
									text: data[i][1]+'% persons have choosen emission value '+data[i][0]
								});	
							}

							win.show('center','middle',false);
				        }
				    });	
        		}
        	}
	    });
	    
	    var data;
		
		Edo.util.Ajax.request({
	        type: 'post',        
	        url: 'lca/calculator!getMaterialByStage.action',
	        params: {
                stage: stage,   //传递父节点的Name(也可以是ID)
                cId: cId
            },
	        onSuccess: function(text){
	            var data = Edo.util.Json.decode(text);	 
	            calTable.set('data',data);           
	        }
	    });
	    
	    var calBox = Edo.create({
	    	type: 'box',
	    	width: '100%',
	    	height: '100%',
	    	border: 0,
	    	padding: 0,
	    	layout: 'vertical',
	    	children: [
	    		{
				 	type: 'box',
			        width: '100%',    
			        padding: 2, 
			        border: 0,   
			        layout: 'horizontal',
			        verticalAlign: 'bottom',
			        horizontalAlign: 'left',
			        //cls: 'e-toolbar',                
			        children:[
			        	{type: 'button', width: 120, text: '添加', icon: 'e-icon-add', onclick:function(e){
			        		createMaterial(stage);
			        	}},
			        	{type: 'button', width: 120, text: '编辑', icon: 'e-icon-edit1', onclick: function(e){
			        		var rs = Edo.get('calTable_'+stage).getSelecteds();
			        		if(rs.length <= 0){
			        			alert('please choose one material!');
			        		}else{		        			
			        			updateMaterial(rs[0]);	
			        		}			
			        	}},
			        	{type: 'button', width: 120, text: '删除', icon: 'e-icon-delete1', onclick: function(e){
			        		var rs = Edo.get('calTable_'+stage).getSelecteds();
			        		if(rs.length <= 0){
			        			alert('please choose one material!');
			        		}else{		        			
			        			Edo.MessageBox.confirm("confirm", "you want to delete material "+rs[0].name+'?', function(result){
									if('yes' == result){
										//加载第一次数据
										Edo.util.Ajax.request({
									        type: 'post',        
									        url: 'lca/calculator!deleteMaterial.action',
									        params: {
								                id: rs[0].id   //传递父节点的Name(也可以是ID)
								            },
									        onSuccess: function(text){
									            alert('success');
									            Edo.get('calTable_'+stage).data.beginChange();
												Edo.get('calTable_'+stage).data.remove(rs[0]); 
												Edo.get('calTable_'+stage).data.endChange();
									        }
									    });
									}
								});
			        		}			
			        	}}
			        ]
			        
			    },
			    calTable
	    	]
	    });
	    
	    return calBox;
	}
	
	
	//增加新的原材料
	function createMaterial(stage){
		var win;
		if(Edo.get('materialForm') != null){
			win = Edo.get('materialForm');
		}else{
			win = new Edo.containers.Window();
			win.set('id','materialForm');
			win.set('title', 'add New Material');
			win.set('titlebar',
			    [      //头部按钮栏
			        {
			            cls: 'e-titlebar-close',
			            onclick: function(e){		           
			                this.parent.owner.destroy();       //hide方法
			            }
			        }
			    ]
			);
			win.addChild({
			    type: 'box',
			    width: 300,
			    height: 200,    
			    border: [1,1,1,1],
			    children: [
			    	{
				    	type: 'formitem',
					    label: 'Name:',
					    labelWidth: 80,					 
					    children: [
					        {
					        	id: 'name',
					            type: 'text',
					            width: 200
					        }
					    ]
			    	},
			    	{
				    	type: 'formitem',
					    label: 'Description:',
					    labelWidth: 80,					 
					    children: [
					        {
					        	id: 'description',
					            type: 'textarea',
					            height: 80,
					            width: 200
					        }
					    ]
			    	},
			    	{
			    		type: 'button',
			    		text: 'OK',
			    		onclick: function(e){
			    			//alert(Edo.util.Json.encode(this.parent.getForm()));
			    			var o = this.parent.getForm();
			    			o.stage = stage;
			    			
			    			Edo.util.Ajax.request({
								url: 'lca/calculator!addMaterial.action',
								type : 'post',
								params : o,
								async : false,
								onSuccess : function(text) {
									// text就是从url地址获得的文本字符串
									if(text == null || text == ''){
										data = null;
									}else{								
										data = Edo.util.Json.decode(text);																		
										Edo.get('calTable_'+stage).data.add(data);  						            									
									}	
									alert('success!');	
									Edo.get('materialForm').destroy();	
								},
								onFail : function(code,a,b,c,d,e,f) {
									
								}
							});
			    			
			    		}
			    	}
			    ]
			});	
		}
		win.reset();
		win.show('center','middle',true);
	}
	
	//更新原材料
	function updateMaterial(m){
		var win;
		if(Edo.get('materialForm') != null){
			win = Edo.get('materialForm');
		}else{
			win = new Edo.containers.Window();
			win.set('id','materialForm');
			win.set('title', 'edit Material');
			win.set('titlebar',
			    [      //头部按钮栏
			        {
			            cls: 'e-titlebar-close',
			            onclick: function(e){		           
			                this.parent.owner.destroy();       //hide方法
			            }
			        }
			    ]
			);

			win.addChild({
			    type: 'box',
			    width: 300,
			    height: 200,    
			    border: [1,1,1,1],
			    children: [
			    	{
				    	type: 'formitem',
					    label: 'Name:',
					    labelWidth: 80,					 
					    children: [
					        {
					        	id: 'name',
					            type: 'text',
					            width: 200,
					            text: m.name
					        }
					    ]
			    	},
			    	{
				    	type: 'formitem',
					    label: 'Description:',
					    labelWidth: 80,					 
					    children: [
					        {
					        	id: 'description',
					            type: 'textarea',
					            height: 80,
					            width: 200,
					            text: m.description
					        }
					    ]
			    	},
			    	{
			    		type: 'button',
			    		text: 'OK',
			    		onclick: function(e){
			    			//alert(Edo.util.Json.encode(this.parent.getForm()));
			    			var o = this.parent.getForm();
			    			m.name = o.name;
			    			m.description = o.description;
			    			
			    			Edo.util.Ajax.request({
								url: 'lca/calculator!updateMaterial.action',
								type : 'post',
								params : m,
								async : false,
								onSuccess : function(text) {
									// text就是从url地址获得的文本字符串
									if(text == null || text == ''){
										data = null;
									}else{								
										data = Edo.util.Json.decode(text);																	

										Edo.get('calTable_'+m.stage).data.beginChange();
										Edo.get('calTable_'+m.stage).data.update(m,'name',data.name); 
										Edo.get('calTable_'+m.stage).data.update(m,'description',data.description);
										Edo.get('calTable_'+m.stage).data.endChange();					            									
									}	
									alert('success!');	
									Edo.get('materialForm').destroy();	
								},
								onFail : function(code,a,b,c,d,e,f) {
									
								}
							});
			    			
			    		}
			    	}
			    ]
			});	
		}
		win.show('center','middle',true);
	}
	
	//计算各个阶段的碳排放量
	function calculateStageEmission(){
		for(var i=1; i<=6; i++){
			if(Edo.get('calTable_'+i).valid() == false){
				Edo.get('tbar').set('selectedIndex', i-1);
				return;
			}
		}
		//开始计算各个阶段的碳排放量
		var sum = 0;
		resultjsonarray = new Array();
		for(var i=1; i<=6; i++){
			
			var singleSum = 0;
			//var oo=Edo.get('calTable_'+i).getSelectedItems();
			//alert(oo.length);
			//for(int j=0;j<oo.length;j++)
			//{alert(oo[j].sum);}
			Edo.get('calTable_'+i).data.source.each(function(o){
				 
			//	alert(o.checked);
				  singleSum = singleSum+o.emission* o.sum;
				  			});							
			Edo.get('stageresult_'+i).set('text',singleSum.toFixed(3));			
			resultjsonarray.add(Edo.get('stageresult_'+i).getValue());
			sum += singleSum;
		}
		Edo.get('finalresult').set('text',sum.toFixed(3));
		//alert(resultjsonarray);
	}
	
</script>