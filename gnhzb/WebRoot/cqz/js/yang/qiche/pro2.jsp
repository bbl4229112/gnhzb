<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String moduleid=request.getParameter("moduleid");
%>

<html>
<head>
	<title>生命周期流程图模板</title>
	<style type="text/css" media="screen">
		BODY {
			font-family: Arial;
		}
		H1 {
			font-size: 18px;
		}
		H2 {
			font-size: 16px;
		}
		.skin {
            width : 100px;
            border : 1px solid gray;
            padding : 2px;
            visibility : hidden;
            position : absolute;
        }
        div.menuitems {
            margin : 1px 0;
        }
        div.menuitems a {
            text-decoration : none;
        }
        div.menuitems:hover {
            background-color : #c0c0c0;
        }
		
	</style>
	<!-- Sets the basepath for the library if not in same directory -->
	<script type="text/javascript">
	</script>
	<!-- Loads and initializes the library -->
	<script type="text/javascript" src="<%=basePath%>js/lca/flow/mxclient1.8goo.js"></script>
	<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/welcome.css">
		<link href="<%=basePath%>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/icon.css" rel="stylesheet"
			type="text/css" />

		<script src="<%=basePath%>js/edo/edo.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/utils.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/lca/lca_moduledefine_builder.js" type="text/javascript"></script>
		<%-- <script src="<%=basePath%>js/lca/nodelist.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/lca/moduledefine.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/lca/pdm_moduledefine.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/lca/lca_moduledefine.js" type="text/javascript"></script> --%>
		
	<script type="text/javascript">
	 var moduleid=<%=moduleid%>;
    	//定义模型对象	
		var moduleobject={
			versionid:null,
			productid:null,
			modulename:null,
			modulenote:null,
		    moduletype:null,
		    issaved:false
		}
    	function initmoduleobject(){
	    	moduleobject.components=null;
			moduleobject.stages=null;
   			moduleobject.issaved=false;
    	}
    	function clearmodule(){
    		initgraph();
    		parent.clearcompmodule();
    	}
		function initgraph(){
	 		var graph=editor.graph;
        	var model=graph.getModel();
        	model.clear();
        	initmoduleobject();
   		}
   		function judgesave(){
   		if(moduleobject.stages&&!moduleobject.issaved){
   			return false;
   		}else{
   		    return true;
   		}
   		
   		}
   		function askifsave(callback){
  				var label=Edo.create({
   	        	    type:'label',
   	        	    text:'请先保存在建模型'
   	        	    });
        	    var func=function(){
        	    editor.execute('savemodule');
           		}
           		var toolbar = Edo.create(
					    {type: 'ct',
					    cls: 'e-dialog-toolbar',
					    width: '100%',
					    layout: 'horizontal',
					    height: '30%',
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
					            callback();
					            this.parent.parent.parent.destroy();
					
					            }
					        }
					    ]
					});
	         	var win=cims201.utils.getWin(300,100,'模型保存',[label,toolbar]);
             	win.show('center', 'middle', true);
   		}
		
		//定义流程数组
		var cellcollection=new Array();
		//从父窗口初始化产品模型时调用此方法，根据模型类型不同初始化也不同
		function initmodule(moduleobj){
			if(moduleobj.moduletype=='PDM'){
				
			}else{
			moduleobject.productid=moduleobj.productid;
			}
			
			moduleobject.versionid=moduleobj.versionid;
			moduleobject.modulename=moduleobj.modulename;
			moduleobject.modulenote=moduleobj.modulenote;
			moduleobject.moduletype=moduleobj.moduletype;
			/* moduleobject.componentname=null;
			moduleobject.componentid=null; */
			moduleobject.stages=null;
		}
		//零件模型初始化时操作
		function refreshcompmodule(moduleobj)
		{
			moduleobject.components=moduleobj.components;
     		/* moduleobject.modulename=moduleobj.modulename;
     		moduleobject.modulenote=moduleobj.modulenote; */
     		moduleobject.stages=moduleobj.stages;
		}
		//定义编辑器
		var editor=null;
		
		//定义右键菜单
	    var popmenu=new mxPopupMenu();
	    popmenu.init();
	    var groupfunc=function(){
	    	editor.execute('groupOrUngroup');
	    };
	    var exitgroupfunc=function(){
	    	editor.execute('exitGroup');
	    	tr1.hidden=true;
	    };
	    var entergroupfunc=function(){
	    	editor.execute('enterGroup');
	    	tr1.hidden=false;
	    };
	    var deletefunc=function(){
	    	editor.execute('delete');
	    };
	    var copyfunc=function(){
	    	editor.execute('copy');
	    	tr.hidden=false;
	    };
	    var cutfunc=function(){
	    	editor.execute('cut');
	    	tr.hidden=false;
	    };
	    var pastefun=function(){
	    	editor.execute('paste');
	    	tr.hidden=true;
	    };
	    var tr=popmenu.addItem ('删除', 'images/delete.gif', deletefunc, popmenu);
        popmenu.addItem ('复制', 'images/copy.gif', copyfunc, popmenu);
        popmenu.addItem ('剪切','images/cut.gif', cutfunc, popmenu);
        popmenu.addItem ('构建组', 'img/group.png', groupfunc, popmenu); 
        popmenu.addItem ('进入组', 'img/groupin.gif', entergroupfunc, popmenu);
        var  popmenu2=new mxPopupMenu();
	    popmenu2.init();
	    var tr=popmenu2.addItem ('粘贴', 'images/paste.gif', pastefun, popmenu2);
	    tr.hidden=true;
        var tr1=popmenu2.addItem ('跳出组', 'img/groupout.gif', exitgroupfunc, popmenu2);
        tr1.hidden=true;
        
        //文档初始化主方法
		function main(container, outline, toolbar, modelbar,sidebar, status)
		{
	
				editor = new mxEditor();
				var graph = editor.graph;
				var model = graph.getModel();
				//alert(mxUtils.getFunctionName(graph.constructor));

				// Disable highlight of cells when dragging from toolbar
				graph.setDropEnabled(false);

				// Uses the port icon while connections are previewed
				graph.connectionHandler.getConnectImage = function(state)
				{
					return new mxImage(state.style[mxConstants.STYLE_IMAGE], 16, 16);
				};

				// Centers the port icon on the target port
				graph.connectionHandler.targetConnectImage = true;

				// Does not allow dangling edges
				graph.setAllowDanglingEdges(false);

				// Sets the graph container and configures the editor
				editor.setGraphContainer(container);
				var config = mxUtils.load(
					'editors/config/keyhandler-commons.xml').
					getDocumentElement();
				editor.configure(config);
				
				// Defines the default group to be used for grouping. The
				// default group is a field in the mxEditor instance that
				// is supposed to be a cell which is cloned for new cells.
				// The groupBorderSize is used to define the spacing between
				// the children of a group and the group bounds.
				var group = new mxCell('Group', new mxGeometry(), 'group');
				group.setVertex(true);
				group.setConnectable(false);
				editor.defaultGroup = group;
				editor.groupBorderSize = 20;

				graph.isValidDropTarget = function(cell, cells, evt)
				{
					return this.isSwimlane(cell);
				};
				
				graph.isValidRoot = function(cell)
				{
					return this.isValidDropTarget(cell);
				}

				graph.isCellSelectable = function(cell)
				{
					return !this.isCellLocked(cell);
				};

				graph.getLabel = function(cell)
				{
					var tmp = mxGraph.prototype.getLabel.apply(this, arguments); // "supercall"
					
					if (this.isCellLocked(cell))
					{
						return '';
					}
					else if (this.isCellCollapsed(cell))
					{
						var index = tmp.indexOf('</h1>');
						
						if (index > 0)
						{
							tmp = tmp.substring(0, index+5);
						}
					}
					
					return tmp;
				}

				graph.isHtmlLabel = function(cell)
				{
					return !this.isSwimlane(cell);
				}
				//定义双击事件
				graph.dblClick = function(evt, cell)
				{    
				var label =  graph.model.getStyle(cell);
				var type=null;
				if(label==null){
				return null;
				}
				if(label=='process'){
				type='process';
				}else{
				var endindex=label.indexOf(';');
				type=label.substring(0,endindex);
				}
				
					if (this.isEnabled() &&
						!mxEvent.isConsumed(evt) &&
						cell != null &&
						this.isCellEditable(cell))
					{
						if (this.model.isEdge(cell) ||
							!this.isHtmlLabel(cell))
						{
							this.startEditingAtCell(cell);
						}
						else
						{
						if(type=='process'){
						
						
						   if(moduleobject.moduletype=='PDM'){
								var tasktreeTable = new Edo.data.DataTree()
									.set({
									    fields: [
									        {name: 'id', mapping: 'id', type: 'string'
									           
									        },
									        {name: 'name', mapping: 'name',  type: 'string'
									        }
									    ]
									});	
								function refreshdata(dataTable,url,param,id){
								    var data= cims201.utils.getData(url,param);
									dataTable.set('data',data);
								}
								function roleSelect()
									{
								   var r=role.getSelected();
								   if(r!=null){
								   var data= cims201.utils.getData('http://localhost:8080/Myproject20140827/tasktree/tasktree!getTaskTreebyRole.action',{role:r.id});
								   tasktreeTable.set('data',data);
									   
									   }
									}
			                	var box=Edo.create(
									{type: 'box',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
					               	    children: [
					               	    {	type : 'formitem',label : '流程名称:',labelWidth : 150,labelAlign : 'right',
					               	    children : [{type : 'text',width : 200,id : 'processname',text:cell.processname}]
					               	    },
					               	    {	type : 'formitem',label : '流程执行模块:',labelWidth : 150,labelAlign : 'right',
					               	    children : [{type : 'text',width : 200,id : 'tasktreenodeid',text:cell.tasktreenodeid,visible:false},{type : 'text',width : 200,id : 'tasktreenode',text:cell.tasktreenodename,
				               	    	onclick: function(e){
				               	    	var roledataTable = new Edo.data.DataTable()
											.set({
											    fields: [
											        {name: 'id', mapping: 'id', type: 'string'
											           
											        },
											        {name: 'name', mapping: 'name',  type: 'string'
											        }
											    ]
											});
										
										var url='http://localhost:8080/Myproject20140827/department/department!getOperationRoles.action';
										var param={};
										var id='role';
										refreshdata(roledataTable,url,param,id);
		               	    			var box=Edo.create(
		               	    			{type: 'box',width: '100%',height:'100%',layout: 'horizontal',
		               	    			children:[
		               	    				{
											type:'box',
											height: '100%',
											layout:'vertical',
											children:[	
										        {		
												id: 'role', type: 'table', width: '100%', height: '100%',autoColumns: true,
												padding:[0,0,0,0],
												rowSelectMode : 'single',
												columns:[{
													 headerText: '',
													 align: 'center',
													 width: 10,                        
													 enableSort: false,
													 enableDragDrop: true,
													 enableColumnDragDrop: false,
													 style:  'cursor:move;',
													 renderer: function(v, r, c, i, data, t){
													 return i+1;}},
													 {header:'权限名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'}
													 ],
									             data:roledataTable,
									             onselectionchange: function(e){	
											        	new roleSelect();
											         }
												}]},
										      
										   		{
									   			id: 'tasktree',
									   			type: 'tree',
												width: '260',
										        height: '100%',
										        headerVisible: false,
										        autoColumns: true,
										        horizontalLine: false,
												padding:[0,0,0,0],
											    rowSelectMode : 'single',
											    columns:[
									                 {header:'名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'}
									                 ],
									             data:tasktreeTable
										   		}
		               	    				]})
									   	var func=function(){
									   	
							            	var row=Edo.get('tasktree').getSelected();
								            Edo.get('tasktreenode').set('text',row.name);
								            Edo.get('tasktreenodeid').set('text',row.id);
								           }
									    var toolbar=new gettoolbar(null,func);
									    var winfm=cims201.utils.getWin(400,400,'选择功能模块',[box,toolbar]);
									    winfm.show('center', 'middle', true);
						            	}}]
					               	    }
		               	    			]
										});
									var func=function(){
						            	var processname=Edo.get('processname').text;
						            	var tasktreenodeid=Edo.get('tasktreenodeid').text;
						            	var tasktreenodename=Edo.get('tasktreenode').text;
						            	cell.processname=processname;
						            	cell.tasktreenodeid=tasktreenodeid;
						            	cell.tasktreenodename=tasktreenodename;
							            var label =  graph.convertValueToString(cell);
						    		    var nodelabel =  label.slice(0,label.length);
						    		    var newlabel = label.replace(nodelabel,
						    		    '<div style=" text-align: center;padding:0px 0px 0px 0px;width:120px;background:#41B9F5;height:50px;opacity:000">'+
											'<table style=" margin: auto;padding:0px 0px 0px 0px;font-size:4px">'+
								                '<tr>'+
								                   ' <td>流程名称:</td>'+
								                    ' <td>'+processname+'</td>'+
								                '</tr>'+
								                '</table>'+
								                '</div>');
								                 graph.labelChanged(cell,newlabel,mxEvent.LABEL_CHANGED);
									           }
									var toolbar=new gettoolbar(null,func);
									var winprocess=cims201.utils.getWin(400,200,'填写流程信息',[box,toolbar]);
									 winprocess.show('center', 'middle', true);
								
						}else{
						new lcaProcessDefine(cell,graph);
						
						}
						}
						 
						}
					}

					mxEvent.consume(evt);
				};
				graph.setConnectable(true);
				configureStylesheet(graph);
				var spacer = document.createElement('div');
				spacer.style.display = 'inline';
				spacer.style.padding = '8px';
				editor.addAction('groupOrUngroup', function(editor, cell)
				{
					cell = cell || editor.graph.getSelectionCell();
					if (cell != null && editor.graph.isSwimlane(cell))
					{
						editor.execute('ungroup', cell);
					}
					else
					{
						editor.execute('group');
					}
				});

				editor.addAction('savemodule', function(editor, cell)
				{ 
				    var graph=editor.graph;
					var cells =  graph.getChildCells();
					/* if(cells.length>0&&moduleobject.stage!=null){ */
					if(cells.length>0&&!moduleobject.issaved){
						alert('确定保存文件？');
						if(moduleobject.moduletype=='PDM'){
							for(var i=0;i<cells.length;i++){
							    if(cells[i].nodeid!=null){
								var cell={};
								cell.id=cells[i].getId();
								cell.nodeid=cells[i].nodeid;
								cell.processname=cells[i].processname;
								cell.tasktreenodeid=cells[i].tasktreenodeid;
								cellcollection.push(cell);
								}
							}
							var enc = new mxCodec(mxUtils.createXmlDocument());
							var node = enc.encode(graph.getModel());
							var xmldata = mxUtils.getPrettyXml(node);
							var data= cims201.utils.getData('http://localhost:8080/Myproject20140827/module/module!addmodule.action',{modulename:moduleobject.modulename,modulenote:moduleobject.modulenote,cellcollection:cellcollection,xmldata:xmldata,moduletype:moduleobject.moduletype});
							moduleobject.modulename=null;
			     			moduleobject.modulenote=null;
						}else if(moduleobject.moduletype=='LCA'){
							/* alert(moduleobject.componentname);
							alert(moduleobject.modulename);
							alert(moduleobject.stage); */
							var iscompleted=true;
							for(var i=0;i<cells.length;i++){
							//alert(cells[i].getStyle());
							    if(cells[i].getStyle()=='process'){
							    if(moduleobject[cells[i].stage]){
								    var stagecomponents=moduleobject[cells[i].stage].components;
								    if(stagecomponents){
									    for(var c=0;c<stagecomponents.length;c++)
									    {   
										    if(!stagecomponents[c].isassigned)
										    iscompleted=false;
									    }
								    }else{
								    iscompleted=false;
								    }
							    }else{
							    	iscompleted=false;
							    }
							    if(iscompleted){
							    var cell={};
								cell.id=cells[i].getId();
								cell.stage=cells[i].stage;
								cell.stagename=cells[i].stagename;
								cell.components=stagecomponents;
								cellcollection.push(cell);
							    }else{
							    alert('请先完成各阶段人员制定！');
							    cellcollection=null;
								cellcollection=new Array;
							    return null;
							    }
							    
								/* var cell={};
								cell.id=cells[i].getId();
								cell.nodeid=cells[i].nodeid;
								cell.processname=cells[i].processname;
								cell.stage=cells[i].stage;
								cell.processperson=cells[i].processperson;
								cellcollection.push(cell); */
								
								}
							}
							//alert(versionid);
							//alert(productid);
							/* alert(moduleobject.productid);
							alert(moduleobject.versionid);
							alert(moduleobject.moduletype);
							alert(moduleobject.modulename);
							alert(moduleobject.modulenote);
							alert(moduleobject.components.length);
							alert(cellcollection.length); */
							var enc = new mxCodec(mxUtils.createXmlDocument());
							var node = enc.encode(graph.getModel());
							var xmldata = mxUtils.getPrettyXml(node);
							var data= cims201.utils.getData('http://localhost:8080/Myproject20140827/lca/lcamodule!addmodule.action',{version:moduleobject.versionid,components:moduleobject.components,pdid:moduleobject.productid,modulename:moduleobject.modulename,modulenote:moduleobject.modulenote,cellcollection:cellcollection,xmldata:xmldata,moduletype:moduleobject.moduletype});
							moduleobject.issaved=true;
							parent.savecompleted();
						}else{
					    	
						
						}
					}else if(cells.length==0){
						alert('请完成流程图再保存!');
						return null;
					}
					if(data==null){
					  	alert('保存成功！');
					}
					cellcollection=null;
					cellcollection=new Array;
					
				});
				editor.addAction('export', function(editor, cell)
				{ 
					var textarea = document.createElement('textarea');
					textarea.style.width = '400px';
					textarea.style.height = '400px';
					var enc = new mxCodec(mxUtils.createXmlDocument());
					var node = enc.encode(editor.graph.getModel());
					textarea.value = mxUtils.getPrettyXml(node);
					showModalWindow(graph, 'XML', textarea, 410, 440);
				
				    
				});
				var outln = new mxOutline(graph, outline);
				var menubar= Edo.create({
				type: 'box',
				id:'menubox',
				width: '100%',
				height:40,
			    layout: 'horizontal',
			    padding:[0,0,0,0], //横向布局
			    border: [0,0,0,0],
			    render:toolbar,
			    children: [
			  
			        /* {type: 'button', text: '新增零部件模型',width:120,height:35,style:'border-radius:10px;',onclick: function(e){
			           if(moduleobject.stages&&moduleobject.issaved){
				        	window.parent.createcompmodule();
			   				var graph=editor.graph;
			   	        	var model=graph.getModel();
			   	        	model.clear();
			   	        	initmoduleobject();
		   	        	}else{ 
		   	        	    var label=Edo.create({
		   	        	    type:'label',
		   	        	    text:'是否保存模型信息?'
		   	        	    });
		   	        	    var func=function(){
		   	        	    editor.execute('savemodule');
		   	        	    window.parent.createcompmodule();
			   				var graph=editor.graph;
			   	        	var model=graph.getModel();
			   	        	model.clear();
			   	        	moduleobject.componentname=null;
							moduleobject.componentid=null;
							moduleobject.stage=null;
							moduleobject.modulename=null;
			     			moduleobject.modulenote=null;
			     			moduleobject.issaved=false;
			           		}
			           		var toolbar=new gettoolbar(null,func);
	   	         			var win=cims201.utils.getWin(300,100,'模型保存',[label,toolbar]);
			             	win.show('center', 'middle', true);
			             	alert('请先保存所建模型！');
		   	        	}
		   	        	}
		   	        }, */
			        /* {type: 'button', text: '保存模型',width:100,height:35,style:'border-radius:10px;',onclick: function(e){editor.execute('savemodule');}},
			        {type: 'button', text: '清空模型',width:100,height:35,style:'border-radius:10px;',onclick: function(e){clearmodule();}}, */
			        {type: 'button', id:'checkstage',text: '查看阶段模型',width:100,height:35,style:'border-radius:10px;',onclick: function(e){
			         //bbl
			          showprocess(); 
			          returnmajor.set('visible',true)
			          this.set('visible',false)
			          editor.graph.getModel().clear(); 
			        }},
			        {type: 'button', id:'returnmajor',text: '返回主模型',width:100,height:35,style:'border-radius:10px;',visible:false,onclick: function(e){
			        checkstage.set('visible',true)
			          this.set('visible',false)
			           editor.graph.getModel().clear();
			          graph.getModel().beginUpdate();
					try
					{	
						read(graph,"http://localhost:8080/Myproject20140827/lca/lcamodule!downloadXML.action?moduleid="+moduleid);	
					}
					finally
					{
						// Updates the display
					 
						graph.getModel().endUpdate();
			        
			        }}},
			        
			        /* {type: 'button', text: '导出',width:70,height:35,style:'border-radius:10px;',onclick: function(e){editor.execute('export');}}, */
			        //{type: 'split'}, 
			        {type: 'button', text: '放大',width:70,height:35,style:'border-radius:10px;',onclick: function(e){editor.execute('zoomIn');}},
			        {type: 'button', text: '缩小',width:70,height:35,style:'border-radius:10px;',onclick: function(e){editor.execute('zoomOut');}}
			            
			    ]
				});
		
		    function toggle(e){

			    var panel = this.parent.owner;
			    var accordion = panel.parent;
			    accordion.getChildren().each(function(child){
			        if(panel != child) child.collapse();
			    });
			    panel.toggle()
			}
			
			function onPanelClick(e){
			    if(e.within(this.headerCt)){
			        var panel = this;
			        var accordion = panel.parent;
			        accordion.getChildren().each(function(child){
			            if(panel != child) child.collapse();
			        });
			        panel.toggle();
			    }
			}
		
      
		mxEvent.addListener(graph.container, 'contextmenu',
                  function(e) {
              var cell=editor.graph.getSelectionCell();
              var x=mxEvent.getClientX(e);
              var y=mxEvent.getClientY(e);
              //判端右击事件是否在流程cell上
              if(cell==null){
               
              	  if(popmenu2.isMenuShowing){
		      		 popmenu2.hideMenu();
		          }
		          if(popmenu.isMenuShowing){
		      		 popmenu.hideMenu();
		          }
		          var offset = mxUtils.getOffset(graph.container);
                  popmenu2.div.style.left = x+ 'px';
       			  popmenu2.div.style.top = y+'px';
                  popmenu2.showMenu();
              }else{
                  if(popmenu.isMenuShowing){
		      		 popmenu.hideMenu();
		          }
		          if(popmenu2.isMenuShowing){
				        popmenu2.hideMenu();
			      }
                  //var offset = mxUtils.getOffset(graph.container);
                  popmenu.div.style.left =  x+cell.getGeometry().width/2 +'px';
       			  popmenu.div.style.top = y +'px';
                  popmenu.showMenu();
              
              }
                });	
		
		
		  mxEvent.addListener(graph.container, 'click', 
				    function(evt) {
				        if(popmenu.isMenuShowing){
				        popmenu.hideMenu();
				        }
				         if(popmenu2.isMenuShowing){
				        popmenu2.hideMenu();
				        }
				        mxEvent.consume(evt);
				    });	
		
		
		
		
		
		
			// Checks if the browser is supported
			if (!mxClient.isBrowserSupported())
			{
				// Displays an error message if the browser is not supported.
				mxUtils.error('Browser is not supported!', 200, false);
			}
			else
			{
				// Assigns some global constants for general behaviour, eg. minimum
				// size (in pixels) of the active region for triggering creation of
				// new connections, the portion (100%) of the cell area to be used
				// for triggering new connections, as well as some fading options for
				// windows and the rubberband selection.
				mxConstants.MIN_HOTSPOT_SIZE = 16;
				mxConstants.DEFAULT_HOTSPOT = 1;
				
				// Enables guides
				mxGraphHandler.prototype.guidesEnabled = true;

			    // Alt disables guides
			    mxGuide.prototype.isEnabledForEvent = function(evt)
				{
					return !mxEvent.isAltDown(evt);
				};

				// Enables snapping waypoints to terminals
				mxEdgeHandler.prototype.snapToTerminals = true;

				// Workaround for Internet Explorer ignoring certain CSS directives
				if (mxClient.IS_QUIRKS)
				{
					document.body.style.overflow = 'hidden';
					new mxDivResizer(container);
					new mxDivResizer(outline);
					new mxDivResizer(toolbar);
					//new mxDivResizer(modelbar);
					new mxDivResizer(status);
				}
				
				// Creates a wrapper editor with a graph inside the given container.
				// The editor is used to create certain functionality for the
				// graph, such as the rubberband selection, but most parts
				// of the UI are custom in this example.
				
				

				// To show the img in the outline, uncomment the following code
				//outln.outline.labelsVisible = true;
				//outln.outline.setHtmlLabels(true);
				
				// Fades-out the splash screen after the UI has been loaded.
				var splash = document.getElementById('splash');
				if (splash != null)
				{
					try
					{
						mxEvent.release(splash);
						mxEffects.fadeOut(splash, 100, true);
					}
					catch (e)
					{
					
						// mxUtils is not available (library not loaded)
						splash.parentNode.removeChild(splash);
					}
				}
			}
			graph.getModel().beginUpdate();
					try
					{	
						read(graph,"http://localhost:8080/Myproject20140827/lca/lcamodule!downloadXML.action?moduleid="+moduleid);	
					}
					finally
					{
						// Updates the display
					 
						graph.getModel().endUpdate();
						
					}
		};
	/* 	function drawstage(stages){
		     var graph=editor.graph;
		     var parent = graph.getDefaultParent();
			 var start = graph.insertVertex(parent,null, '', 20, 220, 80, 80,'start');
			 var previous=start;
			 for(var i=0;i<stages.length;i++){
				 var v1 = graph.insertVertex(parent,null,'<div style="margin:0px;padding:40px 0px 0px 0px;width:100px;background:#00CCFF;height:60px;opacity:100">'+
					'<table style=" margin: auto;padding:0px 0px 0px 0px;">'+
		                '<tr>'+
		                    ' <td align="center">'+stages[i].name+'</td>'+
		                '</tr>'+
		                '</table>'+
		                '</div>', (i+1)*150, 200, 120, 120,'process');
				 v1.stagename=stages[i].name;
				 v1.stage=stages[i].stage;
				 var e1=graph.insertEdge(parent, id, '', previous, v1,'crossover');
				 previous=v1;
				 }
				 var end = graph.insertVertex(parent,null, '', (stages.length+1)*150+20, 220, 80, 80,'end');
			 var e1=graph.insertEdge(parent, id, '', previous, end, 'crossover');
				 
			 
		} */
		//弹出窗口
		function editcell(cell,processname){
						    var label =  graph.convertValueToString(cell);
							var nodelabel =  label.slice(0,label.length);
											    		    var newlabel = label.replace(nodelabel,
											    		    '<div style=" text-align: center;padding:0px 0px 0px 0px;width:120px;background:#41B9F5;height:50px;opacity:000">'+
																'<table style=" margin: auto;padding:0px 0px 0px 0px;font-size:4px">'+
													                '<tr>'+
													                   ' <td>流程名称:</td>'+
													                    ' <td>'+processname+'</td>'+
													                '</tr>'+
													                '</table>'+
													                '</div>');
													                 editor.graph.labelChanged(cell,newlabel,mxEvent.LABEL_CHANGED);
							
							}
		//bbl
		function showprocess(){
		
		}					
		function showModalWindow(graph, title, content, width, height)
		{
			
			var x = Math.max(0, document.body.scrollWidth/2-width/2);
			var y = Math.max(10, (document.body.scrollHeight ||
						document.documentElement.scrollHeight)/2-height*2/3);
			var wnd = new mxWindow(title, content, x, y, width, height, false, true);
			wnd.setClosable(true);
			
			// Fades the background out after after the window has been closed
			wnd.addListener(mxEvent.DESTROY, function(evt)
			{
				graph.setEnabled(true);
			});
			graph.setEnabled(false);
			graph.tooltipHandler.hide();
			wnd.setVisible(true);
			return wnd;
		};
		//获取不同类型的节点
		function getcollection(graph,sidebar,nodetype,nodecategory){
			 var datas= cims201.utils.getData('http://localhost:8080/Myproject20140827/node/node!getNodeListByType.action',{nodetype:nodetype,nodecategory:nodecategory});
			 for(i=0;i<datas.length;i++){
			            var id=datas[i].id
						var image=datas[i].img;
						var label=datas[i].label;
						var prompt=datas[i].nodedrawtype;
						var description=datas[i].description;
						addSidebarIcon(id,graph, sidebar, label, image,prompt,description)
			}
		}
		//添加节点图标
		function addSidebarIcon(nodeid,graph, sidebar, label, image,prompt,title)
		{
			// Function that is executed when the image is dropped on
			// the graph. The cell argument y
				var parent = graph.getDefaultParent();
				var model = graph.getModel();
				//alert(parent.getId());
				var v1 = null;
				var funct = function(graph, evt, cell, x, y)
			        {
			  		var parent = graph.getDefaultParent();
					var model = graph.getModel();
					var v1 = null;
					model.beginUpdate();
					try
					{
						// NOTE: For non-HTML labels the image must be displayed via the style
						// rather than the label markup, so use 'image=' + image for the style.
						// as follows: v1 = graph.insertVertex(parent, null, label,
						// pt.x, pt.y, 120, 120, 'image=' + image);
						if('process'==prompt){
							v1 = graph.insertVertex(parent,null, label, x, y, 120, 120,prompt);
							v1.setConnectable(true);
							v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 80);
						}else{
							v1 = graph.insertVertex(parent,null, label, x, y, 80, 80,prompt);
							v1.setConnectable(true);
							v1.geometry.alternateBounds = new mxRectangle(0, 0, 80, 60);
					}
						}
						
					finally
					{
						model.endUpdate();
					}
					
				  graph.setSelectionCell(v1);
			
              		
					v1.nodeid=nodeid;
				};
			// Creates the image which is used as the sidebar icon (drag source)
			var img = document.createElement('img');
			if(image==null){
			img.setAttribute('src', 'img/gear.png');
			}else{
			img.setAttribute('src', image);
			}
			img.style.width = '50px';
			img.style.height = '50px';
			img.title = title;
			sidebar.appendChild(img);
			var dragElt = document.createElement('div');
			dragElt.style.border = 'dashed black 1px';
			dragElt.style.width = '120px';
			dragElt.style.height = '120px';
			  					
			// Creates the image which is used as the drag icon (preview)
			var ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
			ds.setGuidesEnabled(true);
		};
		
		//设置不同类型节点样式
		function configureStylesheet(graph)
		{
			var style = graph.getStylesheet().getDefaultVertexStyle();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = 'top';
			style[mxConstants.STYLE_FONTSIZE] = 11;
			style[mxConstants.STYLE_STARTSIZE] = 22;
			style[mxConstants.STYLE_HORIZONTAL] = false;
			style[mxConstants.STYLE_FONTCOLOR] = 'black';
			style[mxConstants.STYLE_FILLCOLOR] = '#eeeeee';
			delete style[mxConstants.STYLE_FILLCOLOR];
			delete style[mxConstants.STYLE_FONTSIZE];

			style = mxUtils.clone(style);
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = 'center';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_HORIZONTAL] = true;
			style[mxConstants.STYLE_FONTSIZE] = 20;
			style[mxConstants.STYLE_FILLCOLOR] = '#00CCFF';
			style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#00CCFF';
			style[mxConstants.STYLE_SPACING_RIGHT] = 0;
			style[mxConstants.STYLE_SPACING_TOP] = 0;
			style[mxConstants.STYLE_IMAGE] = 'img/connect.png';
			delete style[mxConstants.STYLE_STARTSIZE];
			graph.getStylesheet().putCellStyle('process', style);
			
			style = mxUtils.clone(style);
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_TRIANGLE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.EllipsePerimeter;
			style[mxConstants.STYLE_FILLCOLOR] = '#8FBC8F';
			style[mxConstants.STYLE_FONTSIZE] = 14;
			style[mxConstants.STYLE_SPACING_TOP] = 20;
			style[mxConstants.STYLE_SPACING_RIGHT] = 27;
			style[mxConstants.STYLE_IMAGE] = 'img/connect.png';
			delete style[mxConstants.STYLE_ROUNDED];
			graph.getStylesheet().putCellStyle('start', style);
											
			style = mxUtils.clone(style);
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RHOMBUS;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RhombusPerimeter;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = 'top';
			style[mxConstants.STYLE_SPACING_TOP] = 18;
			style[mxConstants.STYLE_SPACING_RIGHT] = 0;
			style[mxConstants.STYLE_IMAGE] = 'img/connect.png';
			style[mxConstants.STYLE_FILLCOLOR] = '#FFFFE0';
			graph.getStylesheet().putCellStyle('condition', style);
							
			style = mxUtils.clone(style);
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_DOUBLE_ELLIPSE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.EllipsePerimeter;
			style[mxConstants.STYLE_SPACING_TOP] = 18;
			style[mxConstants.STYLE_FONTSIZE] = 14;
			//style[mxConstants.STYLE_FONTSTYLE] = 1;
			style[mxConstants.STYLE_FILLCOLOR] = '#F4A460';
			delete style[mxConstants.STYLE_SPACING_RIGHT];
			style[mxConstants.STYLE_IMAGE] = 'img/stop.png';
			graph.getStylesheet().putCellStyle('end', style);
			
			style = graph.getStylesheet().getDefaultEdgeStyle();
			style[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;
			style[mxConstants.STYLE_ENDARROW] = mxConstants.ARROW_BLOCK;
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_FONTCOLOR] = 'black';
			style[mxConstants.STYLE_STROKECOLOR] = 'black';
			
			style = mxUtils.clone(style);
			style[mxConstants.STYLE_DASHED] = true;
			style[mxConstants.STYLE_ENDARROW] = mxConstants.ARROW_OPEN;
			style[mxConstants.STYLE_STARTARROW] = mxConstants.ARROW_OVAL;
			graph.getStylesheet().putCellStyle('crossover', style);
			
			style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
			style[mxConstants.STYLE_FILLCOLOR] = '#00CCFF';
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#00CCFF';
			style[mxConstants.STYLE_STROKECOLOR] = '#00CCFF';
			style[mxConstants.STYLE_FONTCOLOR] = 'black';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '100';
			style[mxConstants.STYLE_STARTSIZE] = '30';
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = 1;
			graph.getStylesheet().putCellStyle('group', style);

			var style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '80';
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = 0;
			style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
			graph.getStylesheet().putDefaultVertexStyle(style);
			
		};
		function read(graph, filename)
			{
				var req = mxUtils.load(filename);
				var root = req.getDocumentElement();
				
				 var dec = new mxCodec(root.ownerDocument);
				 dec.decode(root, graph.getModel());
				 
					
			};
		
		
	</script>
</head>

<!-- Page passes the container for the graph to the program -->
<body onload="main(document.getElementById('graphContainer'),
			document.getElementById('outlineContainer'),
		 	document.getElementById('toolbarContainer'),
			document.getElementById('modelbarContainer'),
			document.getElementById('sidebartree'),
			document.getElementById('statusContainer'));" style="margin:0px;background:-webkit-gradient(linear, 0 0, 0 100%, from(#2894FF), to(#B5E3E0));">
	<!-- Creates a container for the splash screen -->
	<div id="splash"
		style="position:absolute;top:0px;left:0px;width:100%;height:100%;background:white;z-index:1;">
		<center id="splash" style="padding-top:230px;">
			<img src="<%=basePath %>cqz/js/yang/img/loading.gif">
		</center>
	</div>
	
	<!-- Creates a container for the sidebar -->
	<div id="toolbarContainer"
		style="position:absolute;white-space:nowrap;overflow:hidden;top:5px;left:200px;max-width:500px;width:500px;max-height:40px;height:40px;right:0px;padding:0px">
	</div>
<!--     <div id="modelbarContainer"
		style="position:absolute;overflow:hidden;top:5px;left:500px;max-height:40px;height:40px;padding:5px;right:0px;">
	</div> -->
	<!-- Creates a container for the toolboox -->
	<div id="sidebartree"
		style="position:absolute;text-align:center;overflow:hidden;top:45px;left:0px;bottom:0px;max-width:160px;width:160px;padding-top:5px;padding-left:0px;">
	</div>
	<!-- Creates a container for the graph -->
	<div id="graphContainer"
		style="position:absolute;border-radius:1em;overflow:hidden;top:45px;left:160px;bottom:0px;right:0px;cursor:default;padding-top:5px;background:F3F6FB;">
	</div>

	<!-- Creates a container for the outline -->
	<div id="outlineContainer"
		style="position:absolute;overflow:hidden;top:45px;right:0px;width:200px;height:140px;background:transparent;border-style:solid;border-color:black;">
	</div>
</body>
</html>
