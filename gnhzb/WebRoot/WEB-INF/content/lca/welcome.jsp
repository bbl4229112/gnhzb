<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>carbon footprint of car component</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/welcome.css">
		<link href="<%=basePath%>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>js/edo/res/product/all.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/icon.css" rel="stylesheet"
			type="text/css" />

		<script src="<%=basePath%>js/edo/edo.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/utils.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	</head>

	<body>
		
		<div style="float: top; width:100%;padding-left: 5px; padding-right: 5px; margin-top: 3px;">
			<div style="float: left; padding-right:5px;width:23%;" id="rightarea"></div>
			<div class="leftarea" style="float: left; width:75%; height: 686px;" id="leftarea"></div>				
		</div>

		<%@include file="caculator.jsp"%>	
	</body>

	<script type="text/javascript">	
		var carChooseSelector = Edo.create({
			type: 'box',
			//title: '选择汽车零部件',
			render: document.getElementById('rightarea'),
			width: '90%',
			height: 680,
			padding: 0,
			border:[1,1,0,1],
			horizontalScrollPolicy:'off',
			children: [			
			{
				type: 'box',
				width: '100%',
				padding: [0,0,0,3],
				border: 0,
				layout: 'horizontal',
				children: [
					{type: 'button', width: 60, icon: 'e-icon-add', text: '新增', onclick: function(e){
						var currentNode = Edo.get('componentTree').getSelected();
						if(currentNode == null){
							alert('please choose one component!');
						}else{
							createComponentWin(currentNode.id);
						}
					}},
					{type: 'button', width: 60,  icon: 'e-icon-edit1', text: '更新', onclick: function(e){
						var currentNode = Edo.get('componentTree').getSelected();
						if(currentNode == null){
							alert('please choose one component!');
						}else{
							updateComponentWin(currentNode);
						}
					}},
					{type: 'button', width: 60,  icon: 'e-icon-delete1', text: '删除', onclick: function(e){
						var currentNode = Edo.get('componentTree').getSelected();
						if(currentNode == null){
							alert('please choose one component!');
						}else{
							Edo.MessageBox.confirm("confirm", "you want to delete component "+currentNode.name+'?', function(result){
								if('yes' == result){
									//加载第一次数据
									Edo.util.Ajax.request({
								        type: 'post',        
								        url: '<%=basePath%>/lca/welcome!deleteComponents.action',
								        params: {
							                cId: currentNode.id   //传递父节点的Name(也可以是ID)
							            },
								        onSuccess: function(text){
								            alert('success');
								        }
								    });
								}
							});
						}
					}}
				]
			},{
				type: 'box',
				width: '90%',
				border: 0,
				padding: 0,
				layout: 'horizontal',
				children: [
					{
						id: 'componentNameInput', type: 'combo',width: '100%', valueField: 'name',displayField: 'name', onkeyup: function(e){
							if (e.keyCode==13){ //如果为回车键就响应
					    		alert(Edo.get('componentNameInput').get('text'));
					    		Edo.util.Ajax.request({
							        type: 'post',        
							        url: '<%=basePath%>/lca/welcome!searchComponents.action',
							        params: {
						                name: Edo.get('componentNameInput').get('text')   //传递父节点的Name(也可以是ID)
						            },
							        onSuccess: function(text){
							            var data = Edo.util.Json.decode(text);
							            Edo.get('componentNameInput').set('data', data);
							            Edo.get('componentNameInput').showPopup();
							        }
							    });
					    	}
						}
					},
					{
					    type: 'button', width: 60,  text: '流程图', onclick: function(e){
						if(Edo.get('componentNameInput').get('text') == null || Edo.get('componentNameInput').get('text') == ''){
							alert('please choose one car component!');    //  画流程图 
						}else{
							Edo.util.Ajax.request({
						        type: 'post',        
						        url: '<%=basePath%>/lca/welcome!checkComponent.action',
						        params: {
					                name: Edo.get('componentNameInput').get('text')   //传递父节点的Name(也可以是ID)
					            },
						        onSuccess: function(text){
						            var data = Edo.util.Json.decode(text);
						            if(data.result >= 0){
						            	startFlowChart(data.result);
						            }else{
						            	alert('please enter right component name!');
						            }
						        }
						    });
						}
					}},
					{
					    type: 'button', width: 60,  text: '开始计算', onclick: function(e){
						if(Edo.get('componentNameInput').get('text') == null || Edo.get('componentNameInput').get('text') == ''){
							alert('please choose one car component!');            //startCaculator
						}else{
							Edo.util.Ajax.request({
						        type: 'post',        
						        url: '<%=basePath%>/lca/welcome!checkComponent.action',
						        params: {
					                name: Edo.get('componentNameInput').get('text')   //传递父节点的Name(也可以是ID)
					            },
						        onSuccess: function(text){
						            var data = Edo.util.Json.decode(text);
						            if(data.result >= 0){
						            	startCalculator(data.result);
						            }else{
						            	alert('please enter right component name!');
						            }
						        }
						    });
						}
					}}
				]
			},
			{
		        type: 'tree',
		        width: '100%',
		        height: 636,
		        
		        horizontalScrollPolicy:'off',
		        id: 'componentTree',
		        onbodymousedown: function(e){
		        	var r = this.getSelected();
		        	Edo.get('componentNameInput').set('text', r.name);
		        },
		        onbeforetoggle: function(e){
		            var row = e.record;
		            var dataTree = this.data;                        
		           
		            if(!row.children || row.children.length == 0){
		                //显示树形节点的loading图标,表示正在加载
		                this.addItemCls(row, 'tree-node-loading');
		                Edo.util.Ajax.request({
		                    //url: 'nodes.txt',
		                    url: 'lca/welcome!getComponentsList.action',
		                    params: {
		                        parentId: row.id   //传递父节点的Name(也可以是ID)
		                    },
		                    defer: 200,
		                    onSuccess: function(text){
		                        var data = Edo.util.Json.decode(text);
		                        
		                        dataTree.beginChange();
		                        if(!(data instanceof Array)) data = [data]; //必定是数组
		                        data.each(function(o){
		                            dataTree.insert(0, o, row);    
		                        });                    
		                        dataTree.endChange();    
		                    }
		                });
		            }
		            return !!row.children;
		        },
		        //verticalLine: false,
		        
		        //data: tree,
		        enabelCellSelect: false,
		        autoColumns: true,
		        enableDragDrop: true,
		        showHeader: false,
		        width: 228,
		        columns:[
		            {
		                id: "name",
		                width: 150,
		                enableDragDrop: true,
		                headerText: "选择零部件",                
		                dataIndex: "name"
		            }
		        ]
		       
		    }]
		});
            
		//弹出新增零配件的面板
		function createComponentWin(parentId){
			var win;
			if(Edo.get('componentForm') != null){
				win = Edo.get('componentForm');
			}else{
				win = new Edo.containers.Window();
				win.set('id','componentForm');
				win.set('title', 'add New Component');
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
				    			o.parentId = parentId;
				    			
				    			Edo.util.Ajax.request({
									url: '<%=basePath%>/lca/welcome!saveComponents.action',
									type : 'post',
									params : o,
									async : false,
									onSuccess : function(text) {
										// text就是从url地址获得的文本字符串
										if(text == null || text == ''){
											data = null;
										}else{								
											data = Edo.util.Json.decode(text);																		
											var row = Edo.get('componentTree').getSelected();
								            var dataTree = Edo.get('componentTree').data;                        
								            dataTree.beginChange();
								            dataTree.insert(0, data, row);
								            dataTree.expand(row,false);
								            dataTree.endChange();    						            									
										}	
										alert('success!');	
										Edo.get('componentForm').destroy();	
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
		
		//弹出更新零配件的面板
		function updateComponentWin(o){
			var win;
			if(Edo.get('componentForm') != null){
				win = Edo.get('componentForm');
			}else{
				win = new Edo.containers.Window();
				win.set('id','componentForm');
				win.set('title', 'edit Component');
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
						            text: o.name
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
						            text: o.description
						        }
						    ]
				    	},
				    	{
				    		type: 'button',
				    		text: 'OK',
				    		onclick: function(e){
				    			//alert(Edo.util.Json.encode(this.parent.getForm()));
				    			var fo = this.parent.getForm();
				    			//o.name = fo.name;
				    			//o.description = fo.description;
				    			var name = fo.name;
				    			var description= fo.description;
				    			var id=o.id;
				    			Edo.util.Ajax.request({
									url: '<%=basePath%>/lca/welcome!updateComponents.action',
									type : 'post',
									params : {id:id,name:name,description:description},
									async : false,
									onSuccess : function(text) {
										// text就是从url地址获得的文本字符串
										if(text == null || text == ''){
											data = null;
										}else{								
											data = Edo.util.Json.decode(text);																		
											var row = Edo.get('componentTree').getSelected();;
								            var dataTree = Edo.get('componentTree').data;                        
								            dataTree.beginChange();
								            dataTree.update(row, 'name', data.name);   
								            dataTree.update(row, 'description', data.description);
								            dataTree.endChange();    						            									
										}		
										alert('success!');	
										Edo.get('componentForm').destroy();	
									},
									onFail : function(code,a,b,c,d,e,f) {
										
									}
								});
				    			
				    		}
				    	}
				    ]
				});	
			}
			Edo.get('');
			win.show('center','middle',true);
		}
		
		//加载第一次数据
		Edo.util.Ajax.request({
	        type: 'post',        
	        url: 'lca/welcome!getComponentsList.action',
	        params: {
                parentId: '0'   //传递父节点的Name(也可以是ID)
            },
	        onSuccess: function(text){
	            var data = Edo.util.Json.decode(text);
	           
	            Edo.get('componentTree').set('data', data);
	        }
	    });
	</script>
</html>
