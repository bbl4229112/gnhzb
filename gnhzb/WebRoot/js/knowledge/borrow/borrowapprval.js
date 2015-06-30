// 创建借阅申请界面
var borrowbox;
var currentflownodeId;
var createBorrowApprovalWin;
function createBorrowApply(kid) {
	borrowbox = Edo.get('borrowbox');
	if (null != borrowbox)
		borrowbox.destroy();
	borrowbox = Edo.create({
		type : 'box',
		padding : 10,
		id : 'borrowbox',
		// title : '发起知识借阅申请',
		width : 500,
		height : 400,
		children : [{
			type : 'formitem',
			label : '借阅模式<span style="color:red;">*</span>:',
			labelWidth : 90,
			children : [{
						id : 'borrowmodel',
						type : 'combo',
						width : 170,
						valid : noEmpty,
						readOnly : true,
						displayField : 'text',
						valueField : 'value',

						onSelectionChange : function(e) {
							var model = e.selectedItem.value;

							if (model == 'timemodel') {
								Edo.get('timemodelform').set('visible', true);
								Edo.get('timesmodelform').set('visible', false);
							}
							if (model == 'timesmodel') {
								Edo.get('timesmodelform').set('visible', true);
								Edo.get('timemodelform').set('visible', false);
							}
							if (model == 'nolimited') {
								Edo.get('timesmodelform').set('visible', false);
								Edo.get('timemodelform').set('visible', false);
							}

						},
						data : [{
									text : '时间模式',
									value : 'timemodel'
								}, {
									text : '次数模式',
									value : 'timesmodel'
								}, {
									text : '不做限制',
									value : 'nolimited'
								}]

					}]
		},

		{
			type : 'formitem',
			label : '终止时间:',
			id : 'timemodelform',
			visible : false,

			labelWidth : 90,
			children : [{
						type : 'date',
						readOnly : true,
						width : 170,
						valid : brrownoEmpty,
						id : 'timemodeltime'
					}]
		}, {
			type : 'formitem',
			label : '借阅次数：',
			id : 'timesmodelform',

			visible : false,
			labelWidth : 90,
			children : [{
						valid : brrownoEmptyandnum,
						type : 'text',
						width : 170,
						id : 'timesmodeltimes'
					}]
		}, {
			type : 'formitem',
			label : '是否需要下载：',
			id : 'downloadform',
			visible : true,
			labelWidth : 90,
			children : [{
						id : 'download',
						type : 'combo',
						width : 170,
						selectedIndex : 0,
						readOnly : true,
						displayField : 'text',
						valueField : 'value',
						data : [{
									text : '是',
									value : true
								}, {
									text : '否',
									value : false
								}]

					}

			]
		}, {
			type : 'formitem',

			children : [{
						type : 'text',
						width : 170,
						height : 100,
						visible : false,
						id : 'knowledgeID',
						text : kid
					}]

		}, {
			type : 'formitem',
			layout : 'horizontal',
			labelWidth : 90,
			padding : [8, 0, 8, 0],
			children : [{
				id : 'submitBtn',
				type : 'button',
				text : '提交申请',
				onClick : function(e) {

					// 验证表单
					if (borrowbox.valid()) {
						var o = borrowbox.getForm(); // 获取表单值
						// var json = Edo.util.Json.encode(o.timemodeltime);
						// alert(json);
						var times=0;
						if(null!=o.timesmodeltimes&&o.timesmodeltimes!='')
						times=Number(o.timesmodeltimes);
						var bf = cims201.utils
								.getData(
										'knowledge/approval/borrow!createBorrowFlow.action',
										{
											knowledgeID : kid,
											download : o.download,
											limited : o.borrowmodel,
											expireTime : o.timemodeltime,
											times : times
										});
						win.hide();
					//	var myFlowBox = new createBorrowApprovalFlow(bf,'borrower');
					//	createBorrowApprovalFlowWithFirstNode(kid, myFlowBox);
					}

				}
			}, {
				type : 'space',
				width : 5
			}, {
				id : 'reset',
				type : 'button',
				text : '取消',
				onClick : function(e) {
					win.hide();
				}
			}]
		}]
	});
	// borrowbox.set("selectedIndex",0);
	var win = cims201.utils.getWin(500, 400, "发起知识借阅申请", borrowbox);
	setWinScreenPosition(500, 400, win);
}

function createBorrowApprovalFlow(borrowApprovalFlow, userrole) {
	var currentFlow = borrowApprovalFlow;
	var currentNode;
	var lastNode;
	var myFlow;

	// 选择人员的组件
	var myUserSelector;

	function getChildrenAccordingtoFlow(borrowApprovalFlow) {
		var children = 0;
		// 当借阅者刚刚发起借阅，还没有管理员创建审批流程的时候

		// 遍历审批流来构建审批流
		borrowApprovalFlow.flowNodesDTO.each(function(o, iii) {

			var myFlowNode = Edo.create({
						type : 'box',
						// width: 150,
						height : 300,
						padding : [0, 0, 0, 0],
						border : [0, 0, 0, 0],
						layout : 'vertical',
						children : []
					});

			// myFlowNode.addChild(myFlowNodestatuspanel);
			var myFlowNodestatus = Edo.create({
						type : 'box',
						// height : 200,
						layout : 'horizontal',
						children : []

					});

			Edo.get('statusindex' + borrowApprovalFlow.id).addChild(Edo.create(
					{
						type : 'label',
						style : 'font-weight: bold;',
						padding : [5, 0, 0, 10],
						text : '第' + (iii + 1) + '阶段'
					}));
		

			if ('刚创建' == o.nodeStatus) {

				Edo.get('status' + borrowApprovalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				Edo.get('approvalor' + borrowApprovalFlow.id).addChild(Edo
						.create({
									type : 'label',
									padding : [5, 0, 0, 10],
									text : '待选择...'
								}));
				// Edo.get('operateresult'+
				// borrowApprovalFlow.id).addChild(Edo.create({
				// type : 'label',
				// padding : [5, 0, 0, 10],
				// text : '无'
				// }));
	if(null!=Edo.get('operate' + borrowApprovalFlow.id)){	
				var button = builtbutton(borrowApprovalFlow, 'selectuser', o.id);
				Edo.get('operate' + borrowApprovalFlow.id).addChild(button);
	}
			}
			if ('待处理' == o.nodeStatus) {

				Edo.get('status' + borrowApprovalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				Edo.get('approvalor' + borrowApprovalFlow.id).addChild(Edo
						.create({
									type : 'label',
									padding : [5, 0, 0, 10],
									text : o.approverORLenderName + "("
											+ o.approverORLenderMail + ")"
								}));
				// Edo.get('operateresult'+
				// borrowApprovalFlow.id).addChild(Edo.create({
				// type : 'label',
				// padding : [5, 0, 0, 10],
				// text : '无'
				// }));
								
				if(null!=Edo.get('operate' + borrowApprovalFlow.id)){
				var buttontype=	'mselectuser';
				if(null!=borrowApprovalFlow.currentnodeid&&borrowApprovalFlow.currentnodeid==o.id)
				 buttontype=	'mnselectuser';
				 
				var button = builtbutton(borrowApprovalFlow,buttontype,
						o.id);
						
				Edo.get('operate' + borrowApprovalFlow.id).addChild(button);
				}
			}

			var approvalORBorrowOpinion = o.approvalORBorrowOpinion;
			if (approvalORBorrowOpinion == null
					&& approvalORBorrowOpinion == 'null')
				approvalORBorrowOpinion = '无';
			if ('通过' == o.nodeStatus || '不通过' == o.nodeStatus) {

				Edo.get('status' + borrowApprovalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				Edo.get('approvalor' + borrowApprovalFlow.id).addChild(Edo
						.create({
									type : 'label',
									text : o.approverORLenderName
								}));
//				Edo.get('operateresult' + borrowApprovalFlow.id).addChild(Edo
//						.create({
//									type : 'label',
//									text : approvalORBorrowOpinion
//								}));
	if(null!=Edo.get('operate' + borrowApprovalFlow.id)){	
				Edo.get('operate' + borrowApprovalFlow.id).addChild(Edo.create(
						{
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '无'
						}));
						}
			}

			children = children + 1;

		});

		lastNode = borrowApprovalFlow.flowNodesDTO[borrowApprovalFlow.flowNodesDTO.length- 1];
 
		if (lastNode.nodeStatus != '刚创建'&&userrole=='admin'&&borrowApprovalFlow.status=='创建中'
		) {
			        var buttonline= Edo.create({
						type : 'box',
					
						bodyCls : 'knowledge_approval',
						layout : 'horizontal',
						padding : [0, 0, 0, 200],
						border : [0, 0, 0, 0],

						children : []
					})
			 if(children<= 5){		
			var button = builtbutton(borrowApprovalFlow, 'addnode');
			buttonline.addChild(button);
			 }
			var button1 = builtbutton(borrowApprovalFlow, 'deletenode',lastNode.id); 
		    var button2 = builtbutton(borrowApprovalFlow, 'endedit',borrowApprovalFlow.id);
            //Edo.get('operate' + borrowApprovalFlow.id).addChild(button);
            buttonline.addChild(button1);
             
            //Edo.get('operate' + borrowApprovalFlow.id).addChild(button);
            buttonline.addChild(button2);
            myFlow.addChild(buttonline);

		}
	}

	if (borrowApprovalFlow.flowNodesDTO == null
			|| borrowApprovalFlow.flowNodesDTO.length == 0) {

		if (userrole == 'admin') {

			myFlow = Edo.create({
						type : 'box',
						width : 700,
						bodyCls : 'knowledge_approval',
						layout : 'vertical',
						padding : [0, 0, 0, 0],
						border : [0, 0, 0, 0],

						children : []
					});

			var status = builtborrowcontent(borrowApprovalFlow, userrole);

			myFlow.addChild(status);
			var myFlowNodestatuspanel = builtborrowFlowNodestatuspanel(borrowApprovalFlow,userrole);
			myFlow.addChild(myFlowNodestatuspanel);
			var button = builtbutton(borrowApprovalFlow, 'addnode');
			Edo.get('operate' + borrowApprovalFlow.id).addChild(button);
	
		}
		if (userrole == 'borrower') {
			myFlow = Edo.create({
						type : 'box',
						width : 700,
						bodyCls : 'knowledge_approval',
						layout : 'vertical',
						padding : [0, 0, 0, 0],
						border : [0, 0, 0, 0],

						children : []
					});
			var borrowcontent = getborrowcongtent(borrowApprovalFlow, userrole);
			var status = Edo.create({
						type : 'label',
						style : 'font-weight: bold;',
						padding : [5, 0, 0, 10],
						text : borrowcontent
					})

			myFlow.addChild(status);

		}
	} else {
	builtnodelistbox(borrowApprovalFlow,userrole);
	}

	this.getFlow = function() {
		return myFlow;
	}
	// 定义借阅内容
	function getborrowcongtent(borrowApprovalFlow, userrole) {
		var content;

		if (userrole == 'admin'||userrole == "approval") {
			content = '提示：<span class="knowledge_approval_status_1">'
					+ borrowApprovalFlow.initiator.name
					+ '('
					+ borrowApprovalFlow.initiator.email
					+ ')'
					+ '</span>申请借阅<span class="knowledge_approval_status_1">'+borrowApprovalFlow.knowledge.uploader.name+'('+borrowApprovalFlow.knowledge.uploader.email+')</span>的知识:' +
							'<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span class="knowledge_approval_status_1">《'
					+ borrowApprovalFlow.knowledge.titleName + '》</span>';
			if(borrowApprovalFlow.borrowFlowContentDTO.limited){
			if (borrowApprovalFlow.borrowFlowContentDTO.times != 0) {
				content += '<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp他希望能够以浏览<span class="knowledge_approval_status_1">次数模式</span>借阅，共可以浏览<span class="knowledge_approval_status_1">'
						+ borrowApprovalFlow.borrowFlowContentDTO.times
						+ '次</span>，';
			}
			if (borrowApprovalFlow.borrowFlowContentDTO.borrowTime != null) {
				content += '<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp他希望能够以浏览<span class="knowledge_approval_status_1">时间模式</span>借阅，在<span class="knowledge_approval_status_1">'
						+ borrowApprovalFlow.borrowFlowContentDTO.borrowTime
						+ '前</span>都可以浏览，';
			}
			}
			else
				{
				content += '<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp他希望能够<span class="knowledge_approval_status_1">不限制模式</span>借阅';
				
				}
			if (borrowApprovalFlow.borrowFlowContentDTO.download == true) {
				content += '并且可以<span class="knowledge_approval_status_1">下载</span>知识全文。';
			} else {
				content += '但不要求下载知识全文。';
			}

		}
		if (userrole == 'borrower') {
			if(borrowApprovalFlow.borrowAdmin==null){
		var admins= cims201.utils
								.getData(
										'knowledge/approval/borrow!listAdmin4init.action',
										{
											knowledgeID : borrowApprovalFlow.knowledge.id
										
										});
				var admins_str='<br>';						
				if(admins)
					{
				
					admins.each(function(o){
             		admins_str+='<span class="knowledge_approval_status_1">'+o.name+'('+o.email+')'+"</span><br>";
             		});
					}	
				
				content = '提示：您申请借阅的知识《' + borrowApprovalFlow.knowledge.titleName
					+ '》<br>正在等待以下管理员处理：'+admins_str+'请您耐心等待';
					
					}
			if(borrowApprovalFlow.borrowAdmin!=null)	
			content = '提示：您申请借阅的知识<span class="knowledge_approval_status_1">《' + borrowApprovalFlow.knowledge.titleName
					+ '》</span><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' +
							'已经被管理员<span class="knowledge_approval_status_1">'+borrowApprovalFlow.borrowAdmin.name+'('+borrowApprovalFlow.borrowAdmin.email+')</span>处理,具体借阅审批流程及状态如下：';
		}
		return content;

	}

	// 定义各种可以操作的按钮
	function builtbutton(borrowApprovalFlow, buttontype, nodeid) {
		var button;
		if (buttontype == 'addnode')
			button = Edo.create({
				type : 'button',
				text : '添加流程节点',
				onclick : function(e) {
					var newborrowApprovalFlow = cims201.utils
							.getData(
									'knowledge/approval/borrow!addBorrowFlowNode.action',
									{
										flowId : borrowApprovalFlow.id
									});
					
				  myFlow.removeAllChildren();
				  builtnodelistbox(newborrowApprovalFlow,"admin");

				}
			})
		if (buttontype == 'deletenode')
			button = Edo.create({
				type : 'button',
				text : '删除流程节点',
				onclick : function(e) {
					alert(nodeid);
					
					var newborrowApprovalFlow = cims201.utils
							.getData(
									'knowledge/approval/borrow!deleteLastBorrowFlowNode.action',
									{
										flowNodeId : nodeid,
										flowId:borrowApprovalFlow.id
									});
				  myFlow.removeAllChildren();
				  builtnodelistbox(newborrowApprovalFlow,"admin");

				}
			})
		if (buttontype == 'selectuser' || buttontype == 'mselectuser'||buttontype == 'mnselectuser') {
			var buttontext = '添加审批人'
			if (buttontype == 'mselectuser'||buttontype == 'mnselectuser')
				buttontext = '修改审批人';
			button = Edo.create({
				type : 'button',
				padding : [5, 0, 0, 10],
				text : buttontext,
				onclick : function(e) {
					currentflownodeId = nodeid;
					if (myUserSelector == null) {
						if(buttontype != 'mnselectuser')
						{  
							 myUserSelector = new createRoleUserSelector(
								'knowledge/approval/approval!getQualifiedRoleNodes.action',
								'user/role/userole!listUsersUnderRoleWithoutSelf.action',
								onUserSelection).getWin();
								
						}else
							{ 
							myUserSelector = new createRoleUserSelector(
								'knowledge/approval/approval!getQualifiedRoleNodes.action',
								'user/role/userole!listUsersUnderRoleWithoutSelf.action',
								onnUserSelection).getWin();
							
							}		
					}
					// myUserSelector.show(100, 100, true);
					setWinScreenPosition(700, 500, myUserSelector, null)
				}
			});
		}
		if (buttontype == 'endedit')
			button = Edo.create({
				type : 'button',
				text : '启动流程',
				onclick : function(e) {
					var newborrowApprovalFlow = cims201.utils
							.getData(
									'knowledge/approval/borrow!finishBorrowFlowConstruction.action',
									{
										flowId : nodeid
									});
				  myFlow.removeAllChildren();
				  builtnodelistbox(newborrowApprovalFlow,"admin");

				}
			})
		return button;

	}
	function builtborrowcontent(borrowApprovalFlow, userrole) {
		var borrowcontent = getborrowcongtent(borrowApprovalFlow, userrole);
		var status = Edo.create({
					type : 'label',
					style : 'font-weight: bold;',
					padding : [5, 0, 0, 10],
					text : borrowcontent
				})
		return status;
	}
	//创建流程节点列表的展示
	function builtnodelistbox(borrowApprovalFlow,userrole)
		{
		
		var myFlowNodestatuspanel = builtborrowFlowNodestatuspanel(borrowApprovalFlow,userrole);
		var status = builtborrowcontent(borrowApprovalFlow, userrole);
		if(null==myFlow)
		myFlow = Edo.create({
					type : 'box',
					border : [0, 0, 0, 0],
					padding : [0, 0, 0, 0],
					width : 700,
					bodyCls : 'knowledge_approval',
					layout : 'vertical',
					children : []
				});

		 myFlow.addChild(status);
		  myFlow.addChild(myFlowNodestatuspanel);
		getChildrenAccordingtoFlow(borrowApprovalFlow)
	
		
		}
	function onUserSelection(uId, uName) {
	
		myFlow.removeAllChildren();

		var modifybox = Edo.create({
					type : 'box',
					layout : 'horizontal',
					padding : [20, 0, 0, 40],
					border : [0, 0, 0, 0],
					children : [{
						type : 'label',
						padding : [20, 0, 0, 20],
						style : 'font-weight: bold;',
						text : '您已经选择了<span class="knowledge_approval_status_1">'
								+ uName + '</span>作为该阶段审批人员'
					}, {
						type : 'button',
						text : '确定',
						onclick : function(e) {
							var newborrowApprovalFlow = cims201.utils
									.getData(
											'knowledge/approval/borrow!addBorrower.action',
											{
												userId : uId,
												flowNodeId : currentflownodeId
											});
										
                          myFlow.removeAllChildren();
				  builtnodelistbox(newborrowApprovalFlow,"admin");
							//creatalert("添加审批成功", myFlow.parent.parent);
						}
					}]

				});

		myFlow.addChild(modifybox);
	}
		function onnUserSelection(uId, uName) {
		myFlow.removeAllChildren();
       
		var modifybox = Edo.create({
					type : 'box',
					layout : 'horizontal',
					padding : [20, 0, 0, 40],
					border : [0, 0, 0, 0],
					children : [{
						type : 'label',
						padding : [20, 0, 0, 20],
						style : 'font-weight: bold;',
						text : '您修改选择了<span class="knowledge_approval_status_1">'
								+ uName + '</span>作为该阶段审批人员'
					}, {
						type : 'button',
						text : '确定',
						onclick : function(e) {
							var newborrowApprovalFlow = cims201.utils
									.getData(
											'knowledge/approval/borrow!modifiedBorrower.action',
											{
												userId : uId,
												flowNodeId : currentflownodeId
											});
										
                          myFlow.removeAllChildren();
				  builtnodelistbox(newborrowApprovalFlow,"admin");
							//creatalert("添加审批成功", myFlow.parent.parent);
						}
					}]

				});

		myFlow.addChild(modifybox);
	}
}
//编辑流程窗口弹出入口
function editborrowflow(kid, userrole, borrowFlowId) {

	var mf = cims201.utils.getData(
			'knowledge/approval/borrow!viewBorrowFlow.action', {
				flowId : parseInt(borrowFlowId)
			});
	if (createApprovalWin == null) {
		var myFlowBox = new createBorrowApprovalFlow(mf, userrole);
		createApprovalWin = cims201.utils.getWinforkview(700, 350, '借阅审批流程',
				myFlowBox.getFlow(), kid);
	} else {
		createApprovalWin.destroy();
		var myFlowBox = new createBorrowApprovalFlow(mf, userrole);
		createApprovalWin = cims201.utils.getWinforkview(700, 350, '借阅审批流程',
				myFlowBox.getFlow(), kid);
	}
	// unvisiblemodul(knowledgeId);
	setWinScreenPosition(700, 350, createApprovalWin, kid);

}
//审核借阅窗口弹出入口
function operateApprovalBorrowFlow(borrowApprovalFlow, userrole)
	{
	var myFlow=	new createBorrowApprovalFlow(borrowApprovalFlow, "approval").getFlow();
	//builtnodelistbox(borrowApprovalFlow,userrole);
    var myApproveBox=	Edo.get('approveBorrowKnowledge_'+borrowApprovalFlow.id);
    if(null!=myApproveBox)
    myApproveBox.destroy();
	 myApproveBox = Edo.create({
			id: 'approveBorrowKnowledge_'+borrowApprovalFlow.id,
			type: 'box',
			//width: '100%',
			//height: '100%',
			border: [0,0,0,0],
			padding: [0,0,0,10],
			//layout: 'vertical',
			children: [
				{
		            type: 'formitem',label: '审批结果<span style="color:red;">*</span>:', labelWidth:'100',
		            children:[
		                {type: 'radiogroup', id: 'approvalBorrowResult'+borrowApprovalFlow.id, displayField: 'name',checkField: 'checked',valueField: 'value',data: [
		                	{name: '审批通过', value: '通过'},
		                	{name: '审批不通过', value: '不通过'}
		                	
		                ]}
		            ]
		        },
		   
		        {
		            type: 'formitem',layout:'horizontal', padding: [8,0,8, 150],
		            children:[
		                {id: 'approveBorrowsubmit'+borrowApprovalFlow.id, type: 'button', text: '提交'}
		            ]
		        }

			]
		});
		Edo.get('approveBorrowsubmit'+borrowApprovalFlow.id).on('click',function(e){
			if(Edo.get('approveBorrowKnowledge_'+borrowApprovalFlow.id)){
				var tempresult = Edo.get('approveBorrowKnowledge_'+borrowApprovalFlow.id).getForm();
				
				var oresult=Edo.get('approvalBorrowResult'+borrowApprovalFlow.id).getValue();
				
				//tempresult['opinion']=Edo.get('opinion'+knowledgeId).getValue();
			if(null!=oresult&&oresult!="")
			{
			var result=	cims201.utils.getData('knowledge/approval/borrow!borrow.action',{result:oresult,flowId:borrowApprovalFlow.id});
			if(null!=result) 
			creatalert("添加审批成功",myFlow.parent.parent);	
			}
			else
				{
					alert('请选择操作意见')
				}
				//approveWin.hide();
			}
		});
	if(borrowApprovalFlow.status=='借阅中')	
	myFlow.addChild(myApproveBox);
	this.getFlow = function() {
		return myFlow;
	}
	}
function approveborrowflow(kid, userrole, borrowFlowId) {

	var mf = cims201.utils.getData(
			'knowledge/approval/borrow!viewBorrowFlow.action', {
				flowId : parseInt(borrowFlowId)
			});
	if (createApprovalWin == null) {
		var myFlowBox = new operateApprovalBorrowFlow(mf, userrole);
		createApprovalWin = cims201.utils.getWinforkview(700, 350, '审核借阅',
				myFlowBox.getFlow(), kid);
	} else {
		createApprovalWin.destroy();
		var myFlowBox = new operateApprovalBorrowFlow(mf, userrole);
		createApprovalWin = cims201.utils.getWinforkview(700, 350, '审核借阅',
				myFlowBox.getFlow(), kid);
	}
	// unvisiblemodul(knowledgeId);
	setWinScreenPosition(700, 350, createApprovalWin, kid);

}
//流程结点表头创建方法
function builtborrowFlowNodestatuspanel(approvalFlow,userrole) {
	var myFlowNodestatuspanel = Edo.create({
				type : 'box',

				layout : 'horizontal',
				border : [0, 0, 0, 0],
				padding : [5, 0, 0, 10],
				children : []

			});

	var statusindex = rebuiltconponent('statusindex' + approvalFlow.id, '审批阶段',
			100);
	var status = rebuiltconponent('status' + approvalFlow.id, '节点状态', 150);
	var approvalor = rebuiltconponent('approvalor' + approvalFlow.id, '审批人',
			220);
	// var operateresult=rebuiltconponent('operateresult' +
	// approvalFlow.id,'审批意见',270);
	
	myFlowNodestatuspanel.addChild(statusindex);
	myFlowNodestatuspanel.addChild(status);
	myFlowNodestatuspanel.addChild(approvalor);
	// myFlowNodestatuspanel.addChild(operateresult);
			if(userrole=='admin'){
	var operate = rebuiltconponent('operate' + approvalFlow.id, '操作', 150);
	myFlowNodestatuspanel.addChild(operate);
			}
	return myFlowNodestatuspanel;

}
function rebuiltconponent(comid, title, width) {
	var tempcomponent = Edo.get(comid);
	if (null != tempcomponent)
		tempcomponent.destroy();
	tempcomponent = Edo.create({
				type : 'panel',
				title : title,
				id : comid,
				padding : [5, 0, 0, 10],
				border : [0, 0, 0, 0],
				width : width
			});

	return tempcomponent;

}
//// 查看和修改借阅审批流程
//// 发起审批流程触发的方法
//function createBorrowApprovalFlowWithFirstNode(knowledgeId, myFlowBox) {
//	// var mf =
//	// cims201.utils.getData('knowledge/approval/approval!createApprovalFlowWithFirstNode.action',{knowledgeId:knowledgeId});
//	if (createBorrowApprovalWin == null) {
//
//		createBorrowApprovalWin = cims201.utils.getWinforkview(700, 350,
//				'借阅审批', myFlowBox.getFlow(), knowledgeId);
//	} else {
//		createBorrowApprovalWin.destroy();
//
//		createBorrowApprovalWin = cims201.utils.getWinforkview(700, 350,
//				'借阅审批', myFlowBox.getFlow(), knowledgeId);
//	}
//	// unvisiblemodul(knowledgeId);
//	setWinScreenPosition(700, 350, createBorrowApprovalWin, knowledgeId);
//
//}

function builtBorrowFlowNodestatuspanel(approvalFlow) {
}
function rebuiltconponent(comid, title, width) {
}

function brrownoEmptyandnum(v) {

	var model = Edo.get('borrowmodel').getValue();

	if ((v == "" || v == null) && model == 'timesmodel') {
		alert("次数模式下，借阅次数不能为空");
		return "次数模式下，借阅次数不能为空";
	}
	if (model == 'timesmodel') {
		var reg = /^[1-9]\d*$/;
		var result = reg.exec(v);
		if (result == null) {
			alert("次数模式下，只能输入整数次数！");
			return "次数模式下，只能输入整数次数！";
		}
	}
}
function brrownoEmpty(v) {
	var model = Edo.get('borrowmodel').getValue();
	// alert("v="+v);
	if ((v == "undefined" || v == "" || v == null) && model == 'timemodel') {
		alert("时间模式下，终止时间不能为空！");
		return "时间模式下，终止时间不能为空！";
	}
}
function noEmpty(v) {
	if (v == "")
		return "不能为空！"
}
