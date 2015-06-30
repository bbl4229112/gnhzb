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
	<title>新建实例</title>
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
		
	<script type="text/javascript">
		// Program starts here. Creates a sample graph in the
		// DOM node with the specified ID. This function is invoked
		// from the onLoad event handler of the document (see below).
		function main(container, outline, toolbar, modelbar, status)
		{
		var editor = new mxEditor();
		var graph = editor.graph;
		var versionid=null;
		var cmpid=null;
		var pdid=null;
		var moduleid=<%=moduleid%>;
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
						
						    var label=null;
						    var title=null;
							if(type=='func'){
							var box=Edo.create(
								{type: 'box',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
				               	    children: [
				               	    //				           
				               	    	{type : 'formitem',
				               	    	label : '公式名称:',
				               	    	labelWidth : 100,
				               	    	labelAlign : 'right',
				               	    	children : [
				               	    		
				               	    		/* {type : 'button',label : '编辑公式',id : 'editbutton', */
				               	    		{type : 'text',width : 100,id : 'formulaname',
				               	    		onclick: function(e){
				               	    		
				               	    			var fmtable=Edo.create(
													{
											        id: 'fmtable',
											        type: 'table',                
											        width: 300,
											        height: '70%',
											        showHeader: true,
											        rowSelectMode: 'single',        
											        enableDragDrop: true,   
											        
											        summaryRowVisible: true,
											        summaryRowPosition: 'bottom',   //top
											        foolterVisible: true,               //false
											        columns:[
											            
											                    {header: '公式编号', dataIndex: 'fmid',width:50,align:'center'
											                        
											                    },
											                    {header: '公式名称', dataIndex: 'fmname',width:100,align:'center'
											                    }, 
											                    {header: '公式表示', dataIndex: 'fmdetail',width:100,align:'center' 
											                    } 
											                
											            
											        ],
											        data:[
											        	{fmid:'001', fmname:'乘法',fmdetail:'单价*消耗'}
											        ]    
											    } )
											    //bbl
											   	var func=function(){
									            	var row=Edo.get('fmtable').getSelected();
										            Edo.get('formulaname').set('text',row.fmname);
										             Edo.get('fmdetail').set('text',row.fmdetail);
										           }
											    var toolbar=new gettoolbar(null,func);
											    var winfm=cims201.utils.getWin(400,200,'选择公式',[fmtable,toolbar]);
											    winfm.show('center', 'middle', true);
								            }}]
				               	   		 },
				               	    	{type : 'formitem',
				               	    	label : '公式表示:',
				               	    	labelWidth : 100,
				               	    	labelAlign : 'right',
				               	    	children : [
				               	    	{type : 'text',width : 150,id : 'fmdetail'}]
				               	    }
				               	   
	               	    
	               	    			]
	               				
							
							});
							var toolbar=new gettoolbar();
							var winfunc=cims201.utils.getWin(400,200,'选择产品',[box,toolbar]);
							 winfunc.show('center', 'middle', true);
							}else if(type=='input'){
							    var inputid='input'+graph.model.getParent(cell).getId();
							    var processid=graph.model.getParent(cell).getId();
							    //var processname='新的 ';
							   //  var r =  Edo.get('componentmodelTree').getSelected();
			        	       // cmpid=r.id; 
							    function showeditor(){
							   		 var flowtable=Edo.create(
											{
									        type: 'table',                
									        width: '100%',
									        height: '70%',
									        showHeader: true,
									        rowSelectMode: 'single',        
									        enableDragDrop: true,   
									        columns:[
									            
								                    {header: '编号', dataIndex: 'id',width:120
									               
								                    },
								                    {header: '名称', dataIndex: 'name',width:100,
								                    },
								                    {header: '单价', dataIndex: 'unitcost',width:100
								                    },
								                    {header: '单位', dataIndex: 'unit',width:100
								                    }
									                
									            
									        ]
									      
									    } )
									    //bbl
								 	    var data= cims201.utils.getData('welcome!getioflow.action',{});
								 	    flowtable.set('data',data);
									   	var func=function(){
							            	var r1=flowtable.getSelected();
								            Edo.get(inputid).data.insert(0, {id:r1.id,name:r1.name,unitcost:r1.unitcost,amount:'0',unit:r1.unit});
								           }
									    var toolbar=new gettoolbar(null,func);
									    var winfw=cims201.utils.getWin(400,400,'选择资源',[flowtable,toolbar]);
									    winfw.show('center', 'middle', true);
							    }
							    var data2= cims201.utils.getData('welcome!getmodelflow.action',{processid:processid,moduleid:moduleid});
								var box=Edo.create(
									{type: 'box',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
					               	    children: [
					               	  
						               	    {id: inputid,
									        type: 'table',                
									        width: '100%',
									        height:'80%',
									        showHeader: true,
									        rowSelectMode: 'single',        
									        enableDragDrop: true,   
									        foolterVisible: true,               //false
									        columns:[
									            
									                    {header: '编号', dataIndex: 'id',width:120
									                    },
									                    {header: '名称', dataIndex: 'name',width:100
									                    },
									                    {header: '单价', dataIndex: 'unitcost',width:100
									                    },
									                    {header: '数量', dataIndex: 'amount',width:100, editor: {
														                        type: 'textarea',
														                        oneditstart: function(e){
														                            this.minWidth = 100;
														                            this.minHeight = 50;
														                        }}
									                    },
									                    {header: '单位', dataIndex: 'unit',width:100
									                    }
									                
											            
											        ],
											        data:data2
											    }
				               	    
		               	    
		               	    			]
		               				
								
								});
								 	
								var wininput=cims201.utils.getWin(600,400,'输入流',[box]);
								wininput.show('center', 'middle', true);
							}else if(type=='output'){
							var box=Edo.create(
									{type: 'box',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
					               	    children: [
					               	    //				           
					               	    {	type : 'formitem',label : '模板名称:',labelWidth : 150,labelAlign : 'right',
					               	    children : [{type : 'text',width : 200,id : '7'}]
					               	    },
					               	    {	type : 'formitem',label : '模板备注:',labelWidth : 150,labelAlign : 'right',
					               	    children : [{type : 'text',width : 200,id : '8'}]
					               	    }
					               	   
		               	    
		               	    			]
		               				
								
								});
								var toolbar=new gettoolbar();
								var winoutput=cims201.utils.getWin(400,200,'选择产品',[box,toolbar]);
								 winoutput.show('center', 'middle', true);
							}else{
							
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
		width: '100%',
		height:'100%',
	    layout: 'horizontal',   //横向布局
	    render:toolbar,
	    children: [
	        {type: 'button', text: '导出',onclick: function(e){editor.execute('export');}},
	        {type: 'split'},    //分隔符
	        {type: 'button', text: '组', arrowMode: 'menu',
	            menu: [
	                {type: 'button', text: '进入组',onclick: function(e){editor.execute('enterGroup');}},
	                {type: 'button', text: '跳出组',onclick: function(e){editor.execute('exitGroup');}}
	            ]
	        }, 
	        {type: 'split'}, 
	        {type: 'button', text: '放大',onclick: function(e){editor.execute('zoomIn');}},
	        {type: 'button', text: '缩小',onclick: function(e){editor.execute('zoomOut');}}
	            
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
			        panel.toggle()
			    }
			}
		var nodedata= cims201.utils.getData('http://localhost:8080/gnhzb/node/node!getnodelist.action',{});
		var producttreebox = Edo.create({
			type:'box',
			width: 140,
			render:sidebartree,
			height:550,
			children:[
			        {
			            type: 'panel',padding:0,
			            width: '100%',height: '100%',              
			            title: '<span style="font-size:13px">节点库</span>',
			            enableCollapse: true,
			            
			            expanded: true, 
			            onclick: onPanelClick,                                           
			            titlebar:[{
			                cls:'e-titlebar-accordion',
			                onclick: toggle
			                
			            }],
			            children: [
				                
						    {type:'tree',
						    id:'nodetree',
						    headerVisible: false,
						    cls: 'e-tree-allow',
						    ScrollPolicy:'on',
						    style: 'cursor:pointer;',
						    width: '100%',
						    height: '100%',
						    autoColumns: true,    
						    cellEditAction: 'celldblclick',
						    columns: [
						        {
						            dataIndex: 'name',
						            renderer: function(v, r){
						                return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '+(r.checked ? 'e-table-checked' : '')+'"></div>'+v+'</div>';
						            }
						        }
						    ],
						    data:nodedata
						}]
			          
			        }]}
				
			);
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
					new mxDivResizer(modelbar);
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
			getmodulebyid(graph,moduleid);
				
		};
		function getmodulebyid(graph,moduleid){
		
		
		graph.getModel().beginUpdate();
					try
					{	
						read(graph,"http://localhost:8080/gnhzb/module/module!downloadXML.action?moduleid="+moduleid);	
					}
					finally
					{
						// Updates the display
					 
						graph.getModel().endUpdate();
						
					}
		
		
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
		
		function configureStylesheet(graph)
		{
			var style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#41B9F5';
			style[mxConstants.STYLE_FILLCOLOR] = '#8CCDF5';
			style[mxConstants.STYLE_STROKECOLOR] = '#1B78C8';
			style[mxConstants.STYLE_FONTCOLOR] = '#000000';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '100';
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = 0;
			style[mxConstants.STYLE_IMAGE_WIDTH] = '14';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '14';
			graph.getStylesheet().putDefaultVertexStyle(style);

            style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#41B9F5';
			style[mxConstants.STYLE_FILLCOLOR] = '#41B9F5';
			style[mxConstants.STYLE_STROKECOLOR] = '#41B9F5';
			style[mxConstants.STYLE_FONTCOLOR] = '#000000';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '100';
			style[mxConstants.STYLE_FONTSIZE] = '3';
			style[mxConstants.STYLE_FONTSTYLE] = 0;
			style[mxConstants.STYLE_IMAGE_WIDTH] = '14';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '14';
			graph.getStylesheet().putCellStyle('process', style);
			
			
			style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
			style[mxConstants.STYLE_FILLCOLOR] = '#FF9103';
			style[mxConstants.STYLE_GRADIENTCOLOR] = '#F8C48B';
			style[mxConstants.STYLE_STROKECOLOR] = '#E86A00';
			style[mxConstants.STYLE_FONTCOLOR] = '#F8C48B';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_OPACITY] = '100';
			style[mxConstants.STYLE_STARTSIZE] = '30';
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = 1;
			graph.getStylesheet().putCellStyle('group', style);
			
			style = new Object();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
			style[mxConstants.STYLE_FONTCOLOR] = '#774400';
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
			style[mxConstants.STYLE_PERIMETER_SPACING] = '6';
			style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_LEFT;
			style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
			style[mxConstants.STYLE_FONTSIZE] = '12';
			style[mxConstants.STYLE_FONTSTYLE] = 2;
			style[mxConstants.STYLE_IMAGE_WIDTH] = '14';
			style[mxConstants.STYLE_IMAGE_HEIGHT] = '14';
			graph.getStylesheet().putCellStyle('func', style);
			
		    style = mxUtils.clone(style);
			graph.getStylesheet().putCellStyle('input', style);
			
		    style = mxUtils.clone(style);
			graph.getStylesheet().putCellStyle('port', style);
			style = mxUtils.clone(style);
			graph.getStylesheet().putCellStyle('output', style);
			
			style = graph.getStylesheet().getDefaultEdgeStyle();
			style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#FFFFFF';
			style[mxConstants.STYLE_STROKEWIDTH] = '2';
			style[mxConstants.STYLE_ROUNDED] = true;
			style[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;
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
			document.getElementById('statusContainer'));" style="margin:0px;">
	
	<!-- Creates a container for the splash screen -->
	<div id="splash"
		style="position:absolute;top:0px;left:0px;width:100%;height:100%;background:white;z-index:1;">
		<center id="splash" style="padding-top:230px;">
			<img src="img/loading.gif">
		</center>
	</div>
	<!-- Creates a container for the sidebar -->
	<div id="toolbarContainer"
		style="position:absolute;white-space:nowrap;overflow:hidden;top:0px;left:0px;max-width:320px;width:400px;max-height:40px;height:36px;right:0px;padding:5px">
	</div>
    <div id="modelbarContainer"
		style="position:absolute;overflow:hidden;top:0px;left:400px;max-height:40px;height:36px;padding:5px;right:0px">
	</div>
	<!-- Creates a container for the toolboox -->
	<div id="sidebartree"
		style="position:absolute;overflow:hidden;top:50px;left:0px;bottom:36px;max-width:200px;width:200px;padding-top:5px;padding-left:4px">
	</div>
	<!-- Creates a container for the graph -->
	<div id="graphContainer"
		style="position:absolute;overflow:hidden;top:50px;left:200px;bottom:36px;right:20px;background-image:url('img/grid.gif');cursor:default;padding-top:5px;">
	</div>

	<!-- Creates a container for the outline -->
	<div id="outlineContainer"
		style="position:absolute;overflow:hidden;top:50px;right:0px;width:200px;height:140px;background:transparent;border-style:solid;border-color:black;">
	</div>
</body>
</html>
