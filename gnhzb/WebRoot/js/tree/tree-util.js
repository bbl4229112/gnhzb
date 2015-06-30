var selectedctree = new Array();
var selecteddtree = new Array();
var approvalresult = new String();
var dmomaintreenodeid;
var receiverid = new String();
var initctree=true;
var kcid="";
var kdid="";
function creatmodifyctreebox(componentid, selectednodesid,url,ctreetype,knolweldgeid) {
	if(kcid!=knolweldgeid)
	{
	selectedctree = new Array();
    initctree=true;	
	kid=knolweldgeid;
	}
	if (null != selectednodesid&&initctree){
		selectedctree = selectednodesid;
		}
		initctree=false;
	var modifybox = Edo.get('ctreebox'+knolweldgeid);
	if (null != modifybox) {
		Edo.get('ctreebox'+knolweldgeid).destroy();
	}
	modifybox = Edo.create({
				id : 'ctreebox'+knolweldgeid,
				type : 'box',

				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],

				children : []
			});

	var ctreeData = cims201.utils.getData(
			url+'tree/privilege-tree!listPrivilegeTreeNodes.action', {
				treeType : 'categoryTree',
				operationName : '上传知识'
			});
	var ctree_select = new Edo.lists.Tree();
	ctree_select.set({
		cls : 'e-tree-allow',
		style : 'cursor:pointer;',
		width : 690,
		height : 320,
		autoColumns : true,
		data : ctreeData,
		columns : [{
			header : '分类',
			dataIndex : 'name',
			renderer : function(v, r) {
				return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '
						+ (r.checked ? 'e-table-checked' : '')
						+ '"></div>'
						+ v
						+ '</div>';

			}
		}, {
			header : '描述',
			dataIndex : 'nodeDescription',
			renderer : function(v, r) {
				return v;

			}
		}]
	});
	if (null != selectedctree) {
		var sels = [];
		for (var i = 0, l = selectedctree.length; i < l; i++) {
			var o = ctree_select.data.find({
						id : selectedctree[i]
					});

			if (!o)
				continue;

			sels.add(o);

			// 并且展开选中的节点
			var p = ctree_select.data.findParent(o);
			ctree_select.data.expand(p);
		}
		setTreeSelect(ctree_select, sels, true, false, 'muti');

	}

	ctree_select.on('bodymousedown', function(e) {
				var r = this.getSelected();

				if (r) {
					var inCheckIcon = Edo.util.Dom.hasClass(e.target,
							'e-tree-check-icon');
					var hasChildren = r.children && r.children.length > 0;
					if (inCheckIcon && r.checked) {
						setTreeSelect(ctree_select, r, false, true, 'muti');
					} else {
						setTreeSelect(ctree_select, r, true, true, 'muti');
					}
				}
			});
	var ctreerootchekcgroup = getTreerootchekcgroup(ctree_select,
			"categoryTree",url);
	modifybox.addChild(ctreerootchekcgroup);
	modifybox.addChild(ctree_select);
	var button = {
		type : 'box',
		border : [0, 0, 0, 0],
		text : '',
		layout : 'horizontal',

		children : [{
					border : [0, 0, 0, 0],
					type : 'box',
					width : 300

				}, {
					type : 'button',
					text : '确定',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
                        if(ctreetype=="upload"){
				
					//	Edo.get(componentid).setValue(selectedctree);
							window.parent.getWinforsimupload(componentid,selectedctree);
						}
						else
						{
						updateknowledgectree(knolweldgeid,null,selectedctree.join(","),ctreewin);
						}
						ctreewin.hide();
					}
				}, {
					type : 'button',
					text : '重置',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {

						var sel = getTreeSelect(ctree_select);
						for (var s = 0; s < sel.length; s++) {
							setTreeSelect(ctree_select, sel[s], false, true,
									'muti');
						}
						selectedctree = new Array();
					   if(ctreetype=="upload")
						window.parent.getWinforsimupload(componentid,"");
					   //Edo.get(componentid).setValue("");
						// ctreewin.hide();
					}
				}, {
					type : 'button',
					text : '取消',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {

						ctreewin.hide();
					}
				}

		]

	};

	modifybox.addChild(button);

	return modifybox;
}
function creatmodifydtreebox(componentid,url,ctreetype,knolweldgeid) {
	
	if(kdid!=knolweldgeid)
	{
	selecteddtree = new Array();	
	kdid=knolweldgeid;
	}
	
	var modifybox = Edo.get('dtreebox');
	if (null != modifybox) {
		Edo.get('dtreebox').destroy();
	}
	modifybox = Edo.create({
				id : 'dtreebox',
				type : 'box',

				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],

				children : []
			});

	var dtreeData = cims201.utils.getData(
			url+'tree/privilege-tree!listPrivilegeTreeNodes.action', {
				treeType : 'domainTree',
				disableInte:true,
				operationName : '上传知识'
					
			});
	var dtree_select = new Edo.lists.Tree();
	dtree_select.set({
		cls : 'e-tree-allow',
		style : 'cursor:pointer;',
		width : 690,
		height : 320,
		autoColumns : true,
		data : dtreeData,
		columns : [{
			header : '域名称',
			dataIndex : 'name',
			renderer : function(v, r) {
				return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '
						+ (r.checked ? 'e-table-checked' : '')
						+ '"></div>'
						+ v
						+ '</div>';

			}
		}, {
			header : '描述',
			dataIndex : 'nodeDescription',
			renderer : function(v, r) {
				return v;

			}
		}]
	});
	if (null != selecteddtree) {
		var sels = [];
		for (var i = 0, l = selecteddtree.length; i < l; i++) {
			var o = dtree_select.data.find({
						id : selecteddtree[i]
					});

			if (!o)
				continue;

			sels.add(o);

			// 并且展开选中的节点
			var p = dtree_select.data.findParent(o);
			dtree_select.data.expand(p);
		}
		setTreeSelect(dtree_select, sels, true, false, 'single');

	}

	dtree_select.on('bodymousedown', function(e) {
				var r = this.getSelected();

				if (r) {
					var inCheckIcon = Edo.util.Dom.hasClass(e.target,
							'e-tree-check-icon');
					var hasChildren = r.children && r.children.length > 0;
					if (inCheckIcon && r.checked) {
						setTreeSelect(dtree_select, r, false, true, 'single');
					} else {
						setTreeSelect(dtree_select, r, true, true, 'single');
					}
				}
			});
	var dtreerootchekcgroup = getTreerootchekcgroup(dtree_select, "domainTree",url);
	modifybox.addChild(dtreerootchekcgroup);
	modifybox.addChild(dtree_select);
	var button = {
		type : 'box',
		border : [0, 0, 0, 0],
		text : '',
		layout : 'horizontal',

		children : [{
					border : [0, 0, 0, 0],
					type : 'box',
					width : 300

				}, {
					type : 'button',
					text : '确定',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
                            if(ctreetype=="upload"){
                      		window.parent.getWinforsimupload(componentid,selecteddtree);
					  		
					//	Edo.get(componentid).setValue(selecteddtree);
                            }
						else
						{
					
						if(null!=selecteddtree&&selecteddtree.length==1)	
						{	
							updateknowledgectree(knolweldgeid,selecteddtree[0],null,dtreewin);
//							createApprovalFlowWithFirstNode(knolweldgeid);	
						}
						}
				
						dtreewin.hide();
					}
				}, {
					type : 'button',
					text : '重置',
					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {

						var sel = getTreeSelect(dtree_select);
						for (var s = 0; s < sel.length; s++) {
							setTreeSelect(dtree_select, sel[s], false, true,
									'single');
						}
						selecteddtree = new Array();
					   if(ctreetype=="upload")
					   	window.parent.getWinforsimupload(componentid,"");
					//	Edo.get(componentid).setValue("");
						// ctreewin.hide();
					}
				}, {
					type : 'button',
					text : '取消',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {

						dtreewin.hide();
					}
				}

		]

	};

	modifybox.addChild(button);

	return modifybox;
}
//pl 创建申请变更知识域createapprovemodifydtreebox
function createapplymodifydtreebox(componentid,url,ctreetype,knolweldgeid) {
	if(kdid!=knolweldgeid)
	{
	selecteddtree = new Array();	
	kdid=knolweldgeid;
	}
	var modifybox = Edo.get('dtreebox');
	if (null != modifybox) {
		Edo.get('dtreebox').destroy();
	}
	modifybox = Edo.create({
				id : 'dtreebox',
				type : 'box',

				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],

				children : []
			});
	applyreason = Edo.create({
		id : 'applyreason',
		type : 'box',
		width : 685,
		height : 60,
		border : [0,0,0,0],
		layout : 'horizontal',
		children : [
		            {
		            	type : 'label',
		            	text : '申请说明：'
		            },{
		            	id : 'reasontext',
		            	type : 'textarea',
		            	width : 600,
		            	height : 50,
		            	text : '请在此处填写申请原因，迁移建议等，但最终是否迁移由目的域管理员决定！',
		            	onclick : function(){
		            	if(this.text == '请在此处填写申请原因，迁移建议等，但最终是否迁移由目的域管理员决定！'){
		            		this.set('text','');
		            	}
		            	}
		            }
		            ]
	});
	modifybox.addChild(applyreason);
	var dtreeData = cims201.utils.getData(
			url+'tree/privilege-tree!listPrivilegeTreeNodes.action', {
				treeType : 'domainTree',
				disableInte:true,
//				operationName : '申请改域'         这里在数据库中加入一个字段，将权限全部放开？？？？？？？？？
				operationName : '上传知识'
					
			});
	
	var outBox;
	
	var treeColumns = [{
				      header: '知识域',
				      dataIndex: 'name'
				 }];
				 
	var myTree = new createTree({},treeColumns,dtreeData,'single',[],[],onTreeSelection);
	
   	var userTable = Edo.create({
			type: 'table',
			width: '100%',
            height: '95%',
            verticalLine: true,
			horizontalLine: true,
			headerVisible: true,
			horizontalScrollPolicy : 'off',
			autoColumns: false,
			multiSelect : false,
			data: [],
			columns:[
			    Edo.lists.Table.createMultiColumn(),
			    {
			    headerText: '选择域管理员',
                dataIndex: 'name',
                headerAlign: 'center',                 
                align: 'left',
                width: 340,
                 renderer: function(v,r){
                	
                	var out_str = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';                   	
            
                	out_str += '<span class="knowledge_simplist_title_author">'+r.name+'</span>'  ;                 	
                
                	return out_str;
                }
			    }
   	]});
	
	function onTreeSelection(v){		
		if(v){
			dmomaintreenodeid = v.id;
			cims201.utils.getData_Async('user/role/userole!listDomainAdministrators.action', {
				id : v.id
			}, function(text) {
				if (text == null || text == '')
					return;
				var data = Edo.util.Json.decode(text);
				userTable.set('data',data);
			});
		}
	}
		
	outBox = Edo.create({
		type: 'box',
		width: 680,
		height: 340,
		border: [0,0,0,0],
		padding: [0,0,0,0],
		layout: 'horizontal',
		children: [
			myTree.getTree(),userTable
		]
	});
	
	modifybox.addChild(outBox);
	var button = {
		type : 'box',
		border : [0, 0, 0, 0],
		text : '',
		layout : 'horizontal',

		children : [ {
					type : 'button',
					text : '确定',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
                            if(ctreetype=="upload"){
                      		window.parent.getWinforsimupload(componentid,selecteddtree);
					  		
                            }
						else
						{
						if(null!=dmomaintreenodeid&&dmomaintreenodeid!=0)	
						{	
							var applytext = new String();
							applytext = Edo.get('reasontext').get('text');
							if(applytext == '请在此处填写申请原因，迁移建议等，但最终是否迁移由目的域管理员决定！'.toString() || applytext == ''.toString() || applytext == null){
								creatalert("请填写申请说明！",dtreewin);
								
							}else{
								applyupdateknowledgectree(knolweldgeid,dmomaintreenodeid,userTable.selected.id,dtreewin,applytext);
							}
						}else{
							creatalert("知识域不能为空!");
						}
						}
				
						dtreewin.hide();
					}
				}, 
				{
					type : 'button',
					text : '取消',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
						dtreewin.hide();
					}
				}

		]

	};

	modifybox.addChild(button);

	return modifybox;
}
function putReceiverId(receiverId){
	receiverid = receiverId;
}
//pl 创建审批变更知识域对话框
function createapprovemodifydtreebox(componentid,url,ctreetype,knolweldgeid) {
	
	if(kdid!=knolweldgeid)
	{
	selecteddtree = new Array();	
	kdid=knolweldgeid;
	}
	var modifybox = Edo.get('dtreebox');
	if (null != modifybox) {
		Edo.get('dtreebox').destroy();
	}
	modifybox = Edo.create({
				id : 'dtreebox',
				type : 'box',

				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],

				children : []
			});
	
	apply = Edo.create({
//		id : 'apply',
//		type : 'box',
//		verticalScrollPolicy: 'auto',
//		horizontalScrollPolicy : 'auto',
//		width : 685,
//		height : 60,
//		layout : 'horizontal',
//		children : [
//		            {
//		            	type : 'label',
//		            	text : '申请情况：'
//		            },{
//		            	id : 'applylabel',
//		            	verticalScrollPolicy: 'on',
//		            	type : 'label',
//		            	width : 600,
//		            	height : 40,
//		            	text : ''
//		            }
//		            ]
		type : 'box',
		id : 'apply',
		verticalScrollPolicy: 'auto',
		horizontalScrollPolicy : 'auto',
		width : 685,
		height : 100,
		layout : 'horizontal',
		children : [
			            {
						type : 'label',
						labelWidth : '30%',
						text : '申请信息：    '

					}, {
						id : 'applylabel',
						verticalScrollPolicy: 'on',
						type : 'label',
						labelWidth : '70%',
						labelAlign : 'left',
						text : ''

					}
		]
	});
	
	//pl 打印申请信息，包括已经审批过的未审批过的
	
	var applyinfmormationresult = cims201.utils.getData(
			url+'knowledge/knowledge!getApplyImformation.action', {
				id : knolweldgeid
			});
	var finalapplyimformation = new String();
	var informationtext = new String();
	var arr = applyinfmormationresult.split("@@@");
	var applyinfmormation = arr[0];
	var messageId = arr[1];
	if(applyinfmormation == ""){
		finalapplyimformation = '<font size = 4 color = "black">本知识当前无人申请修改域!但超级管理员仍然能够修改域!</font><br>';
	}else{
			informationtext = stringChangeLinePrint(applyinfmormation,45,informationtext);
			finalapplyimformation += '<font size = 2.5 color = "blue">'+informationtext+'</font>';
	}
	Edo.get('applylabel').set('text',finalapplyimformation);
	modifybox.addChild(apply);
	var button = {
		type : 'box',
		border : [0, 0, 0, 0],
		text : '',
		layout : 'horizontal',
		position : 'left',

		children : [ {
					type : 'button',
					text : '同意',
					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
						cims201.utils.getData_Async('knowledge/knowledge!approvalupdateknowledgectree.action', {
							messageId : messageId,
							approvalresult : "yes"
						}, function(text) {
							var data = Edo.util.Json.decode(text);
							creatalert(data);
							dtreewin.hide();
							deleteapproval(knolweldgeid);
							
						});
					}
				},
				{
					type : 'button',
					text : '拒绝',

					padding : [0, 0, 0, 0],
					enableToggle : false,
					onclick : function(e) {
					cims201.utils.getData_Async('knowledge/knowledge!approvalupdateknowledgectree.action', {
						messageId : messageId,
						approvalresult : "no"
					}, function(text) {
						var data = Edo.util.Json.decode(text);
						creatalert(data);
						dtreewin.hide();
					});
				}
				}

		]

	};

	modifybox.addChild(button);

	return modifybox;
}

function getTreeSelect(tree_select) {
	var sels = [];
	tree_select.data.source.each(function(node) {
				if (node.checked)
					sels.add(node);
			});
	return sels;
}
function getTreerootchekcgroup(tree_select, treetype,url) {

	var treeroot = cims201.utils.getData(url+'tree/privilege-tree!listTreeRoots.action', {
		treeType : treetype,
		// categoryTree
		disableInte:true,
		operationName : '上传知识'
		});
	var treerootchekcgroup = new Edo.controls.RadioGroup().set({

		displayField : 'name',
		repeatDirection : 'horizontal',
		repeatItems : 4,
		repeatLayout : 'table',
		itemWidth : '160px',
		valueField : 'nodeId',
		data : treeroot,
		onItemclick : function(e) {

			var nodeId = e.item.nodeId;

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
						

					});
		}

	});
	return treerootchekcgroup;
}
function setTreeSelect(tree, sels, checked, deepSelect, selecttype) {// deepSelect:是否深度跟随选择
	// //init判断是否是初建树，不做sels的记录//selecttype
	// 用于判断单选或多选
	// 多选
	if (!Edo.isArray(sels))
		sels = [sels];
	tree.data.beginChange();
	if (selecttype != 'single') {

		for (var i = 0, l = sels.length; i < l; i++) {
			var r = sels[i];
			var cs = r.children;

			tree.data.update(r, 'checked', checked);

			if (checked) {
				var canbeadded = true;
				for (var t = 0; t < selectedctree.length; t++) {
					if (selectedctree[t] == r.id) {

						canbeadded = false;
					}
				}
				if (canbeadded)
					selectedctree[selectedctree.length] = r.id;

			} else {
				for (var t = 0; t < selectedctree.length; t++) {

					if (selectedctree[t] == r.id) {

						selectedctree.splice(t, 1);

					}
				}

			}

		}

	} else {
		// 单选

		tree.data.source.each(function(o) {
					this.data.update(o, 'checked', false);
				}, tree);

		sels.each(function(o) {
		
					if (checked)
						selecteddtree[0] = o.id;
					else {
						if (selecteddtree[0] != null)
							selecteddtree.splice(0, 1);
					}
					this.data.update(o, 'checked', checked);

				}, tree);
			
	}
	tree.data.endChange();
}
function setWinScreenPosition(width, height, win) {
	var screenw = cims201.utils.getScreenSize().width;
	var screenh = cims201.utils.getScreenSize().height;
	if (width < screenw) {
		width = (screenw - width) / 2

	} else {
		width = 0;
	}
	if (height < screenh) {
		height = (screenh - height) / 2

	} else {
		height = 0;
	}
	win.show(width, height, true);
}
function stringChangeLinePrint(longString,slength,newString){
		
	var num = Math.floor(longString.length / slength);
	for(var r = 0;r<=num;r++){
		if(r != num){
			newString += longString.substring(r*slength,(r+1)*slength)+'<br/>';
		}else{
			newString += longString.substring(r*slength,longString.length+1);
		}
	}
	return newString;
}