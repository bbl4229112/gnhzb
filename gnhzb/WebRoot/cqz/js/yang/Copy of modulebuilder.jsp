<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
	<title>新建模型</title>
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
		// Program starts here. Creates a sample graph in the
		// DOM node with the specified ID. This function is invoked
		// from the onLoad event handler of the document (see below).
		
		var moduleobject={
		versionid:null,
		productid:null,
		modulename:null,
		modulenote:null,
	    moduletype:null,
	    issaved:false
	    
	    //isedit:false,
		}
		var cmpid=null;
		var cellcollection=new Array();
		function initmodule(moduleobj){
			if(moduleobj.moduletype=='PDM'){
				
			}else{
			moduleobject.productid=moduleobj.productid;
			}
			
			moduleobject.versionid=moduleobj.versionid;
			moduleobject.modulename=moduleobj.modulename;
			moduleobject.modulenote=moduleobj.modulenote;
			moduleobject.moduletype=moduleobj.moduletype;
			moduleobject.componentname=null;
			moduleobject.componentid=null;
			moduleobject.stage=null;
		}
		function refreshcompmodule(moduleobj)
		{
			moduleobject.componentname=moduleobj.componentname;
     		moduleobject.componentid=moduleobj.componentid;
     		moduleobject.modulename=moduleobj.modulename;
     		moduleobject.modulenote=moduleobj.modulenote;
     		moduleobject.stage=moduleobj.stage;
		}
		var editor=null;
	    var popmenu=new mxPopupMenu();
	    popmenu.init();
	    //普通菜单
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
	    //cell菜单
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

				// Disables drag-and-drop into non-swimlanes.
				graph.isValidDropTarget = function(cell, cells, evt)
				{
					return this.isSwimlane(cell);
				};
				
				// Disables drilling into non-swimlanes.
				graph.isValidRoot = function(cell)
				{
					return this.isValidDropTarget(cell);
				}

				// Does not allow selection of locked cells
				graph.isCellSelectable = function(cell)
				{
					return !this.isCellLocked(cell);
				};

				// Returns a shorter label if the cell is collapsed and no
				// label for expanded groups
				graph.getLabel = function(cell)
				{
					var tmp = mxGraph.prototype.getLabel.apply(this, arguments); // "supercall"
					
					if (this.isCellLocked(cell))
					{
						// Returns an empty label but makes sure an HTML
						// element is created for the label (for event
						// processing wrt the parent label)
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

				// Disables HTML labels for swimlanes to avoid conflict
				// for the event processing on the child cells. HTML
				// labels consume events before underlying cells get the
				// chance to process those events.
				//
				// NOTE: Use of HTML labels is only recommended if the specific
				// features of such labels are required, such as special label
				// styles or interactive form fields. Otherwise non-HTML labels
				// should be used by not overidding the following function.
				// See also: configureStylesheet.
				graph.isHtmlLabel = function(cell)
				{
					return !this.isSwimlane(cell);
				}
				// To disable the folding icon, use the following code:
				/*graph.isCellFoldable = function(cell)
				{
					return false;
				}*/
				// Shows a "modal" window when double clicking a vertex.
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
				
					// Do not fire a DOUBLE_CLICK event here as mxEditor will
					// consume the event and start the in-place editor.
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
								   var data= cims201.utils.getData('http://localhost:8080/gnhzb/tasktree/tasktree!getTaskTreebyRole.action',{role:r.id});
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
										
										var url='http://localhost:8080/gnhzb/department/department!getOperationRoles.action';
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

					// Disables any default behaviour for the double click
					mxEvent.consume(evt);
				};

				// Enables new connections
				graph.setConnectable(true);

				// Adds all required styles to the graph (see below)
				configureStylesheet(graph);

				// Adds sidebar icons.
				//
				// NOTE: For non-HTML labels a simple string as the third argument
				// and the alternative style as shown in configureStylesheet should
				// be used. For example, the first call to addSidebar icon would
				// be as follows:
				// addSidebarIcon(graph, sidebar, 'Website', 'img/earth.png');
				
				

				
				// Creates a new DIV that is used as a toolbar and adds
				// toolbar buttons.
				var spacer = document.createElement('div');
				spacer.style.display = 'inline';
				spacer.style.padding = '8px';
				
				//addToolbarButton(editor, toolbar, 'groupOrUngroup', '构建组', 'img/group.png');
				
				// Defines a new action for deleting or ungrouping
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

				
				
				//toolbar.appendChild(spacer.cloneNode(true));
				//addToolbarButton(editor, toolbar, 'delete', '删除', 'img/delete2.png');
				//addToolbarButton(editor, toolbar, 'cut', '剪切', 'img/cut.png');
				//addToolbarButton(editor, toolbar, 'copy', '复制', 'img/copy.png');
				//addToolbarButton(editor, toolbar, 'paste', '粘贴', 'img/paste.png');

				//toolbar.appendChild(spacer.cloneNode(true));
				
				//addToolbarButton(editor, toolbar, 'undo', '', 'img/undo.png');
				//addToolbarButton(editor, toolbar, 'redo', '', 'img/redo.png');
				
				//toolbar.appendChild(spacer.cloneNode(true));
				
				/* addToolbarButton(editor, toolbar, 'show', 'Show', 'img/camera.png');
				addToolbarButton(editor, toolbar, 'print', 'Print', 'img/printer.png'); */
				
				//toolbar.appendChild(spacer.cloneNode(true));

				// Defines a new export action
				editor.addAction('savemodule', function(editor, cell)
				{ 
				  /* cell = cell || editor.graph.getSelectionCell();
					var textarea = document.createElement('textarea');
					textarea.style.width = '400px';
					textarea.style.height = '400px';
					var enc = new mxCodec(mxUtils.createXmlDocument());
					var node = enc.encode(cell);
					textarea.value = mxUtils.getPrettyXml(node);
					showModalWindow(graph, 'XML', textarea, 410, 440); */
					alert('确定保存文件？');
				    var graph=editor.graph;
					var cells =  graph.getChildCells();
					
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
					var data= cims201.utils.getData('http://localhost:8080/gnhzb/module/module!addmodule.action',{modulename:moduleobject.modulename,modulenote:moduleobject.modulenote,cellcollection:cellcollection,xmldata:xmldata,moduletype:moduleobject.moduletype});
					moduleobject.modulename=null;
	     			moduleobject.modulenote=null;
				}else if(moduleobject.moduletype=='LCA'){
					alert(moduleobject.componentname);
					alert(moduleobject.modulename);
					alert(moduleobject.stage);
				for(var i=0;i<cells.length;i++){
				    if(cells[i].nodeid!=null){
					var cell={};
					cell.id=cells[i].getId();
					cell.nodeid=cells[i].nodeid;
					cell.processname=cells[i].processname;
					cell.stage=cells[i].stage;
					cellcollection.push(cell);
					}
				}
				//alert(versionid);
				//alert(productid);
				var enc = new mxCodec(mxUtils.createXmlDocument());
				var node = enc.encode(graph.getModel());
				var xmldata = mxUtils.getPrettyXml(node);
				var data= cims201.utils.getData('http://localhost:8080/gnhzb/lca/lcamodule!addmodule.action',{version:moduleobject.versionid,componentid:cmpid,pdid:productid,modulename:moduleobject.modulename,modulenote:moduleobject.modulenote,cellcollection:cellcollection,xmldata:xmldata,moduletype:moduleobject.moduletype});
				
				moduleobject.issaved=true;
				}else{
				
				
				}
					
				cellcollection=null;
				cellcollection=new Array;
				if(data==null){
				  	alert('保存成功！');
				}
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
				height:35,
			    layout: 'horizontal',
			    padding:0, //横向布局
			    render:toolbar,
			    style:'border:0;',
			    children: [
			     /* {type: 'button', width:60,height:30,text: '新增', arrowMode: 'menu',style:'background:CCFF00',
			            menu: [
			                {type: 'button', text: 'LCA模板',onclick: function(e){
			                	moduleobject=new moduledefine('LCA');
			                }
			                	},
			                {type: 'button', text: 'LCC模板',onclick: function(e){
			                	moduleobject=new moduledefine('LCC');
			                }
			                	},
			                {type: 'button', text: 'PDM模板',onclick: function(e){
			                	moduleobject=new moduledefine('PDM');
			                }
			                	}
			            ]
			        },  */                       
			        {type: 'button', text: '新增零部件模型',width:120,height:35,style:'border-radius:10px;',onclick: function(e){
			            if(moduleobject.issaved){
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
		   	        	}else if(moduleobject.stage!=null){ 
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
	   	         			var win=cims201.utils.getWin(400,200,'模型保存',[label,toolbar]);
			            
		   	        	}else{
	   	        			window.parent.createcompmodule();
			   				var graph=editor.graph;
			   	        	var model=graph.getModel();
			   	        	model.clear();
		   	        	}
		   	        	}
		   	        },
			        {type: 'button', text: '保存阶段模型',width:100,height:35,style:'border-radius:10px;',onclick: function(e){editor.execute('savemodule');}},
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
		    var data= cims201.utils.getData('http://localhost:8080/gnhzb/node/node!getnodelist.action',{});
		    addSidebarIcon(null,graph, sidebar, null, 'img/start.png','start');
		    var br = document.createElement('br');
			sidebar.appendChild(br);
			addSidebarIcon(null,graph, sidebar, null, 'img/cell.png','process');
			 var br = document.createElement('br');
			sidebar.appendChild(br);
			addSidebarIcon(null,graph, sidebar, null, 'img/stop.png','end');
			  /*  editor.setToolbarContainer(sidebar);
                 editor.toolbar.addMode('ss', 'img/earth.png', null, null);
                  var combo=editor.toolbar.addCombo();
                 editor.toolbar.addOption(combo,'ss','dd');
                 editor.toolbar.addOption(combo,'ss','dd'); */
                 // addOption
		/* var producttreebox = Edo.create({
			type:'box',
			width: 160,
			render:sidebartree,
			height:550,
			padding:0,
			style:'border:0;',
			children:[
				{type: 'box',
				id:'lcacompcontent',
          	 	width: '100%',
          	 	height: '25%',
		    	visible:false,
	    		children:[
			        {type : 'label',text : '产品名称:'},
	          		{type : 'text',id : 'pdmdname'},
	          		{type : 'label',text : '正在构建对象:'},
	          		{type : 'text',id : 'compname'}
          		]},
          		
	          			{
			            type: 'panel',padding:0,
			            id:'producttree',
			            headerVisible:false,
			            width: '100%',height: '60%',  
			            title: '<span style="font-size:13px">产品结构</span>',
			            enableCollapse: true,
			            expanded: false, 
			            onclick: onPanelClick, 
			            visible:false,                                          
			            titlebar:[{
			                cls:'e-titlebar-accordion',
			                onclick: toggle
			            }]
			        	},
				        {
				            type: 'panel',padding:0,
				            id:'nodetree',
				            width: '100%',height: '60%',    
				            headerVisible:false,          
				            enableCollapse: true,
				            expanded: true, 
				            onclick: onPanelClick,                                           
				            titlebar:[{
				                cls:'e-titlebar-accordion',
				                onclick: toggle
				                
				            }],
				            children: [
					                {type: 'table',id:'nodelist', autoColumns: true,headerVisible: false,width: '100%', height: '100%',verticalLine: false, horizontalLine: false, //rowHeight: 25,
					                    onselectionchange: function(e){	
					                        var nodetype= moduletype;
					                       // var nodetype='PDM';
								            getcollection(graph,modelbar,nodetype,e.selected.name);
								        },
					                    rowHeight: 20,
					                    columns: [
					                        {   
					                            renderer: function(v, r){
					                          
					                                return r.name;
					                            }
					                          
					                        }
					                    ],
					                    data:data
					                 
				            }]
				          
				        }
				
			]}); */
		
		 
	/* 	document.oncontextmenu = function(){
        	if(editor.graph.getSelectionCell()!=null){
        	editor.toolbar
        	}else{
        	alert('b');
        	}
		}; */
		
      
		mxEvent.addListener(graph.container, 'contextmenu',
                  function(e) {
              var cell=editor.graph.getSelectionCell();
              var x=mxEvent.getClientX(e);
              var y=mxEvent.getClientY(e);
              if(cell==null){
               
              //alert(v1.getGeometry().x);
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
		};
		
		function addToolbarButton(editor, toolbar, action, label, image, isTransparent)
		{
			var button = document.createElement('button');
			button.style.fontSize = '10';
			if (image != null)
			{
				var img = document.createElement('img');
				img.setAttribute('src', image);
				img.style.width = '16px';
				img.style.height = '16px';
				img.style.verticalAlign = 'middle';
				img.style.marginRight = '2px';
				button.appendChild(img);
			}
			if (isTransparent)
			{
				button.style.background = 'transparent';
				button.style.color = '#FFFFFF';
				button.style.border = 'none';
			}
			mxEvent.addListener(button, 'click', function(evt)
			{
				editor.execute(action);
			});
			mxUtils.write(button, label);
			toolbar.appendChild(button);
		};

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
		function getcollection(graph,sidebar,nodetype,nodecategory){
			 var datas= cims201.utils.getData('http://localhost:8080/gnhzb/node/node!getNodeListByType.action',{nodetype:nodetype,nodecategory:nodecategory});
			 for(i=0;i<datas.length;i++){
			            var id=datas[i].id
						var image=datas[i].img;
						var label=datas[i].label;
						var prompt=datas[i].nodedrawtype;
						var description=datas[i].description;
						addSidebarIcon(id,graph, sidebar, label, image,prompt,description)
			}
		}
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
						
						v1 = graph.insertVertex(parent,null, label, x, y, 80, 80,prompt);
						v1.setConnectable(true);
						
						// Presets the collapsed size
						v1.geometry.alternateBounds = new mxRectangle(0, 0, 80, 40);
							
						// Adds the ports at various relative locations
				//		var func = graph.insertVertex(v1,null, 'func', 120, 1, 12, 12,
				//				'func;image=img/flash.png;spacingLeft=18', true);
				//		func.geometry.offset = new mxPoint(-8, -8);
			
				//		var input = graph.insertVertex(v1,null, 'Input', 1, 120, 12, 12,
				//				'input;image=img/check.png;align=right;imageAlign=right;spacingRight=18', true);
				//		input.geometry.offset = new mxPoint(-6, -4);
						
						/* var port = graph.insertVertex(v1, null, 'Error', 119, 1, 16, 16,
								'port;image=img/error.png;spacingLeft=18', true);
						port.geometry.offset = new mxPoint(-8, -8); */
	
				//		var output = graph.insertVertex(v1,null, 'output', 120, 120, 12, 12,
				//				'output;image=img/information.png;spacingLeft=18', true);
				//		output.geometry.offset = new mxPoint(-8, -4);
					}
					finally
					{
						model.endUpdate();
					}
					
				  graph.setSelectionCell(v1);
			
              		
					v1.nodeid=nodeid;
				};
			// Creates the image which is used as the sidebar icon (drag source)
		/* 	if(!modelbar.hasChildNodes())
			{ */
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
			//}
			
			var dragElt = document.createElement('div');
			dragElt.style.border = 'dashed black 1px';
			dragElt.style.width = '120px';
			dragElt.style.height = '120px';
			  					
			// Creates the image which is used as the drag icon (preview)
			var ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
			ds.setGuidesEnabled(true);
		};
		
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

			style = mxUtils.clone(style);
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = 'center';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_HORIZONTAL] = true;
			style[mxConstants.STYLE_FILLCOLOR] = 'blue';
			style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = 'blue';
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
			style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
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
			style[mxConstants.STYLE_FILLCOLOR] = 'blue';
			style[mxConstants.STYLE_GRADIENTCOLOR] = 'blue';
			style[mxConstants.STYLE_STROKECOLOR] = 'blue';
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
			<img src="img/loading.gif">
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
