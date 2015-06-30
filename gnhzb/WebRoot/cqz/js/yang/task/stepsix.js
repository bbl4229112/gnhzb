function getstepsix(){
	var resultjsonarray = "";
	var dataPanel = Edo.create({
		type: 'panel',
		title: '特征化结果',
		padding: [10,0,0,8], 
		width: 900,
		height: 250,
		border: [0,1,1,1],
		layout: 'vertical',
		verticalScrollPolicy:'on',
		children: [
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: '气候变化:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_1'},
				{type: 'label', text: 'kg'}
			]},
			
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
			 	{type: 'label', text: '酸化:', width: 200},
			 	{type: 'label', text: '0', id: 'stageresult_2'},
			 	{type: 'label', text: 'kg'}
			]},
			
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: '资源消耗-矿物，化石:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_3'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: '资源消耗-水:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_4'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: '富营养化-水体:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_5'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: '富营养化-陆地:', width: 200},
				{type: 'label', text: '0', id: 'stageresult_6'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
				{type: 'label', text: '臭氧层消耗:', width: 200},
				{type: 'label', text: '0', id: 'finalresult'},
				{type: 'label', text: 'kg'}
			]},
			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '生态毒性-淡水:', width: 200},
 				{type: 'label', text: '0', id: 'stageresult_7'},
 				{type: 'label', text: 'kg'}
 			]},
 			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '人体毒性-癌症:', width: 200},
 				{type: 'label', text: '0', id: 'stageresult_8'},
 				{type: 'label', text: 'kg'}
 			]},
 			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '人体毒性-非癌症:', width: 200},
 				{type: 'label', text: '0', id: 'finalresult9'},
 				{type: 'label', text: 'kg'}
     			]},
 			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '可吸入无机物:', width: 200},
 				{type: 'label', text: '0', id: 'stageresult_10'},
 				{type: 'label', text: 'kg'}
 			]},
 			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '电离辐射-人体健康:', width: 200},
 				{type: 'label', text: '0', id: 'stageresult_11'},
 				{type: 'label', text: 'kg'}
 			]},
 			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '光化学臭氧合成:', width: 200},
 				{type: 'label', text: '0', id: 'finalresult12'},
 				{type: 'label', text: 'kg'}
     			]},
 			{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
 				{type: 'label', text: '土地转化:', width: 200},
 				{type: 'label', text: '0', id: 'stageresult_13'},
 				{type: 'label', text: 'kg'}
 			]}
			/*{type: 'box', width: '100%', border: 0, padding: 0, layout: 'horizontal', children: [
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
			]}*/
		]
	});

	/*var chartPanel = Edo.create({
		type: 'box',
		width: 450,
		height: 250,
		border: [1,1,1,1],
		padding: [0,0,0,0],
		layout: 'vertical', 
		children: [new lcaAnalysis(resultjsonarray,'chart')]
	});*/
	var resultct=Edo.create({
		id: 'carbonCalculatorBox',
		type: 'ct',
		width: 1100,
		height: 700,
		verticalGap: 0,
		layout: 'vertical',
		children: [
		createStageCalculator(),
		{
			type: 'box',
			border: [0,0,0,0], 
		    padding: [0,0,0,0], 
		    width: 900,
		    height: 250,
		    horizontalGap:'0',
		    layout: 'horizontal',
			children: [
	           dataPanel/*,chartPanel*/
	           ]
		},
		{
			type:'box',layout:'horizontal',width:'100%',padding:[20,0,0,0],border: [0,0,0,0],height: 80,
			children:[
				{type: 'button',text: '上一步',style:'margin-left:300px;',width:80,height: 30,onclick: function(e){
					    removeselected();
						openNewTab(stepdata.source[4]);
			        	}},
		        {type: 'button',text: '完成',width:80,height: 30,style:'margin-left:320px;',onclick: function(e){
		        	var c=document.getElementById('detaildiv');
		        	c.style.width='0px';
		        	resetm2();
		        	cell.isedit=true;
		        	removeselected();
		        	}}
	       	
			
			]
   	    }
		]
	});	
	
	
	
	

	return resultct;

	
	}
function createStageCalculator(){	    
    /*var calTable = Edo.create({
    	id: 'calTable',
    	type: 'table',                
        width: '100%', 
        //width: 950, 
        height: 250,       
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
    });*/
    var inouttable=Edo.create(
    		{type: 'box',
    	    	width: 900,
    	    	height:250,
    	    	border: [0,0,0,0],
    	    	padding: [0,0,0,0],
    	    	layout: 'horizontal',
    	    	horizontalGap:'0',
    	    	/*verticalAlign:'middle',
    	    	horizontalAlign:'center',*/
    	   	    children: [
		    		{
		    			type: 'panel',
		    			title: '输入',
		    			padding: [0,0,0,0], 
		    			width: 450,
		    			height: 250,
		    			border: [1,1,1,1],
		    			layout: 'vertical',
		    			children: [
				    		{
						        type: 'table',
						        id:'inputresulttable',
						        style:'border:0;',
						        padding:[0,0,0,0],
						        width: 450,
						        height: 300,
						        showHeader: true,
						        rowSelectMode: 'single',        
						        enableDragDrop: true,   
						        columns:[
						            
					                    {header: '编号', dataIndex: 'id',width:110,headerAlign: 'center',align: 'center'
						               
					                    },
					                    {header: '名称', dataIndex: 'name',width:110,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '单位', dataIndex: 'unit',width:110,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '数量', dataIndex: 'unit',width:110,headerAlign: 'center',align: 'center'
					                    }
						                
						        ]
						      
						    }
			    		]},
			    		{
			    			type: 'panel',
			    			title: '输出',
			    			padding: [0,0,0,0], 
			    			width: 450,
			    			height: 250,
			    			border: [1,1,1,1],
			    			layout: 'vertical',
			    			children: [
					    		{
							        type: 'table',
							        id:'outputresulttable',
							        style:'border:0;',
							        padding:[0,0,0,0],
							        width: 450,
							        height: 300,
							        showHeader: true,
							        rowSelectMode: 'single',        
							        enableDragDrop: true,   
							        columns:[
							            
						                    {header: '编号', dataIndex: 'id',width:110,headerAlign: 'center',align: 'center'
							               
						                    },
						                    {header: '名称', dataIndex: 'name',width:110,headerAlign: 'center',align: 'center'
						                    },
						                    {header: '单位', dataIndex: 'unit',width:110,headerAlign: 'center',align: 'center'
						                    },
						                    {header: '数量', dataIndex: 'unit',width:110,headerAlign: 'center',align: 'center'
						                    }
							                
							        ]
							      
							    }
				    		]}]
    		}
	    		
    		);
    var data;
	
	/*Edo.util.Ajax.request({
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
    });*/
    
    return inouttable;
}
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
		   		src: 'http://localhost:8080/Myproject20140827/cqz/js/yang/resultChart.jsp'
		   		/*src: 'lca/calculator!showChart.action?data=' + data*/
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
