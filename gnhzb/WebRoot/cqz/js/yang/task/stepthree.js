function getstepthree(){
	var output = Edo.build(
			{type: 'box',
		    	width: 1100,
		    	height:'100%',
		    	border: [0,0,0,0],
		    	padding: [0,0,0,0],
		    	layout: 'vertical',
		    	verticalGap:'0',
		    	/*verticalAlign:'middle',
		    	horizontalAlign:'center',*/
		   	    children: [
					{type: 'panel',title:'步骤说明',width: '100%',height:200,layout: 'horizontal',border: [0,0,0,0],padding: [0,0,0,0],
						children:[     
						        {type:'textarea',
					       	width : 300,
					       	height:200,
					           style:  'font-size:20px;font-family:verdana;font-weight:bold;border:0;background:rgba(0,0,0,0);',
					       	text:'请填写该过程节点的输出资源或零部件,通过添加选择资源或零部件，并设定默认数据'
						        }]
					}, 
					{type: 'panel',title:'完善输出信息',width: '100%',height:300,layout: 'vertical',border: [0,0,1,0],padding: [0,0,0,0],verticalGap:'0',
						children:[     
							{
							type: 'group',
						    width: 120,
						    layout: 'horizontal',
						    cls: 'e-toolbar',
						    children: [
								{
								    type: 'button',
								    text: '添加',
								    onclick: function(e){
								    	
								    	var func=function(id){
								    		var rows=outputmaterial.getSelecteds()
								    		for(var i=0;i<rows.length;i++){
								    			outputtable.data.insert(0,rows[i])
								    		}
								    	}
								        var content=new outputresourcecontent();
								 	    var toolbar=new gettoolbar(null,func);
								  	    var win=cims201.utils.getWin(600,480,'选择输出',[content,toolbar]);
								 	    win.show('center', 'middle', true);
								 	    win.set('padding',[0,0,0,0]);
								    }                
								    
								    },
					            {
				                type: 'button',
				                text: '删除',
				                onclick: function(e){
				                    var r = Edo.get(outputtable).getSelected();
				                    if(r){
				                        Edo.get(outputtable).data.remove(r);
				                    }else{
				                        alert("请选择行");
				                    }
				                }                
				                
					            }
							]
							},
							{
					        type: 'table',
					        id:'outputtable',
					        padding:[0,0,0,0],
					        width: 720,
					        height: 300,
					        showHeader: true,
					        rowSelectMode: 'multi',        
					        enableDragDrop: true,   
					        columns:[
				                 	Edo.lists.Table.createMultiColumn(),
				                    {header: '编号', dataIndex: 'id',width:100,headerAlign: 'center',align: 'center'},
				                    {header: '名称', dataIndex: 'name',width:120,headerAlign: 'center',align: 'center'},
				                    {header:'类别',dataIndex: 'category',width: 120, headerAlign: 'center',align: 'center'},
			                        {header:'单位',dataIndex: 'unit', width: 120,headerAlign: 'center',align: 'center'},
			                        {header: '数量', dataIndex: 'amount',width:120,headerAlign: 'center',align: 'center'},
			                        {header:'来源',dataIndex: 'origin',width: 120, headerAlign: 'center',align: 'center'},
					        ],
					        data:  levelmodule.levelmoduleobject.outputmaterial
					        	/*[{id:1,name:'冷轧钢板',category:'资源',unit:'kg',amount:0.1,origin:'中国'},
					              {id:2,name:'ABS(粒料)',category:'资源',unit:'kg',amount:0.05,origin:'中国'},
					              {id:3,name:'异氰酸酯',category:'资源',unit:'kg',amount:0.02,origin:'中国'},
					              {id:4,name:'环戊烷',category:'资源',unit:'kg',amount:0.003,origin:'中国'}
					        ]*/
					      
					    }]
						},
						{
							type:'box',layout:'horizontal',width:'100%',padding:[10,0,0,0],border: [0,0,0,0],
							children:[
								{type: 'button',text: '上一步',style:'margin-left:120px;',width:80,height: 30,onclick: function(e){
										var outputmaterial=outputtable.data.source;
								       	levelmodule.levelmoduleobject.outputmaterial=outputmaterial;	
										removeselected();
										openNewTab(stepdata.source[1]);
							        	}},
						        {type: 'button',text: '下一步',width:80,height: 30,style:'margin-left:140px;',onclick: function(e){
						        	 var outputmaterial=outputtable.data.source;
							       	 levelmodule.levelmoduleobject.outputmaterial=outputmaterial;	
						        	 removeselected();
							       	 openNewTab(stepdata.source[3]);
							       	 
						        	}}
					       	
							
							]
							}
			       	    
				
				]});
		return output;

	}
function outputresourcecontent(){
	var outputresourcecontent=Edo.create(
			{type: 'box',
		    	width: 600,
		    	height:400,
		    	border: [0,0,1,0],
		    	padding: [0,0,0,0],
		    	layout: 'vertical',
		    	verticalGap:'0',
		   	    children: [
			            {
						type:'box',
						layout:'horizontal',
						border: [0,0,0,0],
						height: 50,
						padding:[0,0,0,0],
						verticalAlign:'middle',
				    	horizontalAlign:'left',
						children:[
							{
							type:'label',
						    text:'搜索条件:'
							},      
							{   
							type:'Search',
							width:150,
						    ontrigger: function(e){
						        this.set('clearVisible', true);
						        alert("查询");
						    },
						    oncleartrigger: function(e){
						        alert("清除查询");
						    }}]
			            },
			            
			            {
							type:'panel',
							title: '物质类别',
							layout:'horizontal',
							width:'100%',
							padding:[0,0,0,0],
							border:[1,0,1,0],
							verticalAlign:'middle',
					    	horizontalAlign:'left',
					    	enableCollapse: true,
		                   // onclick: onPanelClick,
		                    expanded:true,
		                   /* titlebar:[{
		                    cls:'e-titlebar-accordion',
		                    onclick: toggle}],*/
							children:[
				            {   
				            	type:'CheckGroup', 
				                repeatDirection: 'vertical',
				                repeatItems: 3,
				                repeatLayout: 'table',       
				                itemWidth: '100px',
				                valueField: 'value',
				                data: [
				                    {text: '水资源', value: 1},
				                    {text: '土地资源', value: 2},
				                    {text: '生物资源', value: 3},
				                    {text: '矿产资源', value: 4},
				                    {text: '能源资源', value: 5}
				                ]
				            }]
			            },
			            {
							type:'panel',
							title: '标准',
							layout:'horizontal',
							width:'100%',
							padding:[0,0,0,0],
							border:[0,0,1,0],
							verticalAlign:'middle',
					    	horizontalAlign:'left',
					    	enableCollapse: true,
		                    //onclick: onPanelClick,
		                    expanded:true,
		                  /*  titlebar:[{
		                    cls:'e-titlebar-accordion',
		                    onclick: toggle}],*/
							children:[
				            {   
				            	type:'CheckGroup', 
				                repeatDirection: 'horizontal',
				                repeatItems: 3,
				                repeatLayout: 'table',       
				                itemWidth: '100px',
				                valueField: 'value',
				                data: [
				                    {text: '美国', value: 1},
				                    {text: '欧洲', value: 2},
				                    {text: '中国', value: 3}
				                ]
				            }]
			            },
			        	{type: 'panel',title:'资源列表',width: '100%',height:300,layout: 'horizontal',padding: [0,0,0,0],border:[0,0,0,0],
							children:[   
				              {
						        type: 'table',
						        id:'outputmaterial',
						        padding:[0,0,0,0],
						        width: 570,
						        height: 300,
						        showHeader: true,
						        rowSelectMode: 'multi',        
						        enableDragDrop: true,   
						        columns:[
					                 	 Edo.lists.Table.createMultiColumn(),
						                 {header: '编号', dataIndex: 'id',width:50,headerAlign: 'center',align: 'center'},
					                     {header: '名称', dataIndex: 'name',width:100,headerAlign: 'center',align: 'center'},
					                     {header:'类别',dataIndex: 'category',width: 100, headerAlign: 'center',align: 'center'},
				                         {header:'单位',dataIndex: 'unit', width: 100,headerAlign: 'center',align: 'center'},
				                         {header:'来源',dataIndex: 'origin',width: 100, headerAlign: 'center',align: 'center'},
				                         {header:'更新时间',dataIndex: 'updatetime', width: 100,headerAlign: 'center',align: 'center'}
						        ],
						        data:[{id:1,name:'冷轧钢板',category:'资源',unit:'kg',origin:'中国',updatetime:'2014-10-26'},
						              {id:2,name:'ABS(粒料)',category:'资源',unit:'kg',origin:'中国',updatetime:'2014-10-26'},
						              {id:3,name:'异氰酸酯',category:'资源',unit:'kg',origin:'中国',updatetime:'2014-10-26'},
						              {id:4,name:'环戊烷',category:'资源',unit:'kg',origin:'中国',updatetime:'2014-10-26'},
						              {id:4,name:'电能',category:'能源',unit:'kW.h',origin:'中国',updatetime:'2014-10-26'}]
						      
						    }]}
			            ]
			});
	return outputresourcecontent;
}
