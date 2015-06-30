// 批量审批界面
function createBatchApprovalFlow(approvalFlowarray) {
	approvalFlow = approvalFlowarray[0];
	var currentFlow = approvalFlow;
	var currentNode;
	var lastNode;
	var myFlow;
	var approvalFlowIdString = new String();
	for(var t= 0 ;t<approvalFlowarray.length;t++){
		if(t != (approvalFlowarray.length - 1)){
			approvalFlowIdString += (approvalFlowarray[t].id+"@");
		}else{
			approvalFlowIdString += approvalFlowarray[t].id;
		}
	}
	
	// 选择人员的组件
	var myUserSelector;

	function getChildrenAccordingtoFlow(approvalFlow) {
		var children = 0;
		// 遍历审批流来构建审批流
		
		approvalFlow.nodes.each(function(o, iii) {
			var myFlowNode = Edo.create({
						type : 'box',
						// width: 150,
						height : 300,
						padding : [0, 0, 0, 0],
						border : [0, 0, 0, 0],
						layout : 'vertical',
						children : []
					});
			
					
			//myFlowNode.addChild(myFlowNodestatuspanel);		
			var myFlowNodestatus = Edo.create({
						type : 'box',
						//height : 200,
						layout:'horizontal',
						children:[]

					});

			Edo.get('statusindex'+ approvalFlow.id).addChild(Edo.create({
						type : 'label',
						style : 'font-weight: bold;',
						padding : [5, 0, 0, 10],
						text : '第' + (iii + 1) + '阶段'
					}));
			if ('等待审批' == o.nodeStatus) {
			
				Edo.get('status'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
								padding : [5, 0, 0, 10],
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				Edo.get('approvalor'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text :  o.approverORLenderName
						}));
						
					Edo.get('operateresult'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '等待中...'
						}));		
				Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
					type : 'button',
					padding : [5, 0, 0, 10],
					text : '修改审批人',
					onclick : function(e) {
						currentNode = myFlowNode;
						if (myUserSelector == null) {
							myUserSelector = new createRoleUserSelector(
									'knowledge/approval/approval!getQualifiedRoleNodes.action',
									'user/role/userole!listUsersUnderRoleWithoutSelf.action',
									onUserSelection).getWin();
						}
				//		myUserSelector.show(100, 100, true);
							setWinScreenPosition(700,500,myUserSelector,null)
					}
				}));

			}

			if ('刚刚创建' == o.nodeStatus) {
		
				Edo.get('status'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				 Edo.get('approvalor'+ approvalFlow.id).addChild(Edo.create({type: 'label',	padding : [5, 0, 0, 10], text:'待选择...'}));
				Edo.get('operateresult'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '无'
						}));
				 Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
					type : 'button',
					padding : [5, 0, 0, 10],
					text : '添加审批人',
					onclick : function(e) {
						currentNode = myFlowNode;
						if (myUserSelector == null) {
							myUserSelector = new createRoleUserSelector(
									'knowledge/approval/approval!getQualifiedRoleNodes.action',
									'user/role/userole!listUsersUnderRoleWithoutSelf.action',
									onUserSelection).getWin();
						}
						//myUserSelector.show(100, 100, true);
						setWinScreenPosition(700,500,myUserSelector,null)
					}
				}));

			}
					
			
var approvalORBorrowOpinion=o.approvalORBorrowOpinion;
if(approvalORBorrowOpinion==null&&approvalORBorrowOpinion=='null')
approvalORBorrowOpinion='无';
			if ('通过' == o.nodeStatus || '通过但停止升级' == o.nodeStatus||'未通过'== o.nodeStatus) {
			
				Edo.get('status'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				 Edo.get('approvalor'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : o.approverORLenderName
						}));
				Edo.get('operateresult'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : approvalORBorrowOpinion
						}));
						
				Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '无'
						}));		
			}
           
	     children=children+1;

		});
		
		lastNode = approvalFlow.nodes[approvalFlow.nodes.length - 1];
		
			if (lastNode.nodeStatus == '通过' && children<= 3) {
				 Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
					type : 'button',
					text : '发起新审批',
					onclick : function(e) {
						var newApprovalFlow = cims201.utils
								.getData(
										'knowledge/approval/approval!createFlowNode.action',
										{
											approvalFlowId : approvalFlow.id
										});
						myFlow.removeAllChildren();
						//myFlow.set('layout', 'horizontal');
						var myFlowNodestatuspanel=builtmyFlowNodestatuspanel(newApprovalFlow);
						myFlow.addChild(myFlowNodestatuspanel);
						getChildrenAccordingtoFlow(newApprovalFlow);

					}
				}));

				//myFlowNode.addChild(newFlowNodeBt);
				//children[children.length] = myFlowNode;
			}
	//	}
	
	}
  
 var myFlowNodestatuspanel=builtmyFlowNodestatuspanel(approvalFlow);
	myFlow = Edo.create({
				type : 'box',
				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],
				width:700,
				bodyCls : 'knowledge_approval',
				layout : 'vertical',
				children : [
                 myFlowNodestatuspanel
				]
			});

	//var flowboxinner = Edo.get('approvalFlow_' + approvalFlow.id);
	//if (null != flowboxinner)
	//	Edo.get('approvalFlow_' + approvalFlow.id).destroy();
	getChildrenAccordingtoFlow(approvalFlow)

	//myFlow.addChild(flowboxinner);

	this.getFlow = function() {
		return myFlow;
	}

	function onUserSelection(uId, uName) {
		myFlow.removeAllChildren();
		
		var modifybox=Edo.create({
		type:'box',
		layout:'horizontal',
	    padding : [20, 0, 0, 40],
		border : [0, 0, 0, 0],
		children:[{
					type : 'label',
				    padding : [20, 0, 0, 20],
					style : 'font-weight: bold;',
					text : '您已经选择了<span class="knowledge_approval_status_1">'
							+ uName + '</span>作为该阶段审批人员'
				},{
			type : 'button',
			text : '确定',
			onclick : function(e) {
				// alert(approvalFlow.id);
				var newApprovalFlow = cims201.utils
						.getData(
								'knowledge/approval/approval!batchSaveOrUpdateApprovalor.action',
								{
									approvalorId : uId,
									approvalFlowIdString : approvalFlowIdString
								});
								
						creatalert("添加审批成功",myFlow.parent.parent);		
					 //   Edo.MessageBox.alert("消息", "添加审批成功", null);  		
				      //  myFlow.removeAllChildren();
					  //  myFlow.addChild(builtmyFlowNodestatuspanel(newApprovalFlow));
					//	getChildrenAccordingtoFlow(newApprovalFlow);
	
			}
		}]
		
		});
		
	

		myFlow.addChild(modifybox);
	}
}
// 普通审批界面
function createApprovalFlow(approvalFlow) {
	var currentFlow = approvalFlow;
	var currentNode;
	var lastNode;
	var myFlow;
	
	// 选择人员的组件
	var myUserSelector;

	function getChildrenAccordingtoFlow(approvalFlow) {
		var children = 0;
		// 遍历审批流来构建审批流
		
		approvalFlow.nodes.each(function(o, iii) {
			var myFlowNode = Edo.create({
						type : 'box',
						// width: 150,
						height : 300,
						padding : [0, 0, 0, 0],
						border : [0, 0, 0, 0],
						layout : 'vertical',
						children : []
					});
			
					
			//myFlowNode.addChild(myFlowNodestatuspanel);		
			var myFlowNodestatus = Edo.create({
						type : 'box',
						//height : 200,
						layout:'horizontal',
						children:[]

					});

			Edo.get('statusindex'+ approvalFlow.id).addChild(Edo.create({
						type : 'label',
						style : 'font-weight: bold;',
						padding : [5, 0, 0, 10],
						text : '第' + (iii + 1) + '阶段'
					}));
			if ('等待审批' == o.nodeStatus) {
			
				Edo.get('status'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
								padding : [5, 0, 0, 10],
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				Edo.get('approvalor'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text :  o.approverORLenderName
						}));
						
					Edo.get('operateresult'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '等待中...'
						}));		
				Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
					type : 'button',
					padding : [5, 0, 0, 10],
					text : '修改审批人',
					onclick : function(e) {
						currentNode = myFlowNode;
						if (myUserSelector == null) {
							myUserSelector = new createRoleUserSelector(
									'knowledge/approval/approval!getQualifiedRoleNodes.action',
									'user/role/userole!listUsersUnderRoleWithoutSelf.action',
									onUserSelection).getWin();
						}
				//		myUserSelector.show(100, 100, true);
							setWinScreenPosition(700,500,myUserSelector,null)
					}
				}));

			}

			if ('刚刚创建' == o.nodeStatus) {
		
				Edo.get('status'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				 Edo.get('approvalor'+ approvalFlow.id).addChild(Edo.create({type: 'label',	padding : [5, 0, 0, 10], text:'待选择...'}));
				Edo.get('operateresult'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '无'
						}));
				 Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
					type : 'button',
					padding : [5, 0, 0, 10],
					text : '添加审批人',
					onclick : function(e) {
						currentNode = myFlowNode;
						if (myUserSelector == null) {
							myUserSelector = new createRoleUserSelector(
									'knowledge/approval/approval!getQualifiedRoleNodes.action',
									'user/role/userole!listUsersUnderRoleWithoutSelf.action',
									onUserSelection).getWin();
						}
						//myUserSelector.show(100, 100, true);
						setWinScreenPosition(700,500,myUserSelector,null)
					}
				}));

			}
					
			
var approvalORBorrowOpinion=o.approvalORBorrowOpinion;
if(approvalORBorrowOpinion==null&&approvalORBorrowOpinion=='null')
approvalORBorrowOpinion='无';
			if ('通过' == o.nodeStatus || '通过但停止升级' == o.nodeStatus||'未通过'== o.nodeStatus) {
			
				Edo.get('status'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : '<span class="knowledge_approval_status_1">'
									+ o.nodeStatus + '</span>'
						}));
				 Edo.get('approvalor'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : o.approverORLenderName
						}));
				Edo.get('operateresult'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							text : approvalORBorrowOpinion
						}));
						
				Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
							type : 'label',
							padding : [5, 0, 0, 10],
							text : '无'
						}));		
			}
           
	     children=children+1;

		});
		
		lastNode = approvalFlow.nodes[approvalFlow.nodes.length - 1];
		
			if (lastNode.nodeStatus == '通过' && children<= 3) {
				 Edo.get('operate'+ approvalFlow.id).addChild(Edo.create({
					type : 'button',
					text : '发起新审批',
					onclick : function(e) {
						var newApprovalFlow = cims201.utils
								.getData(
										'knowledge/approval/approval!createFlowNode.action',
										{
											approvalFlowId : approvalFlow.id
										});
						myFlow.removeAllChildren();
						//myFlow.set('layout', 'horizontal');
						var myFlowNodestatuspanel=builtmyFlowNodestatuspanel(newApprovalFlow);
						myFlow.addChild(myFlowNodestatuspanel);
						getChildrenAccordingtoFlow(newApprovalFlow);

					}
				}));

				//myFlowNode.addChild(newFlowNodeBt);
				//children[children.length] = myFlowNode;
			}
	//	}
	
	}
  
 var myFlowNodestatuspanel=builtmyFlowNodestatuspanel(approvalFlow);
	myFlow = Edo.create({
				type : 'box',
				border : [0, 0, 0, 0],
				padding : [0, 0, 0, 0],
				width:700,
				bodyCls : 'knowledge_approval',
				layout : 'vertical',
				children : [
                 myFlowNodestatuspanel
				]
			});

	//var flowboxinner = Edo.get('approvalFlow_' + approvalFlow.id);
	//if (null != flowboxinner)
	//	Edo.get('approvalFlow_' + approvalFlow.id).destroy();
	getChildrenAccordingtoFlow(approvalFlow)

	//myFlow.addChild(flowboxinner);

	this.getFlow = function() {
		return myFlow;
	}

	function onUserSelection(uId, uName) {
		myFlow.removeAllChildren();
		
		var modifybox=Edo.create({
		type:'box',
		layout:'horizontal',
	    padding : [20, 0, 0, 40],
		border : [0, 0, 0, 0],
		children:[{
					type : 'label',
				    padding : [20, 0, 0, 20],
					style : 'font-weight: bold;',
					text : '您已经选择了<span class="knowledge_approval_status_1">'
							+ uName + '</span>作为该阶段审批人员'
				},{
			type : 'button',
			text : '确定',
			onclick : function(e) {
				// alert(approvalFlow.id);
				var newApprovalFlow = cims201.utils
						.getData(
								'knowledge/approval/approval!saveOrUpdateApprovalor.action',
								{
									approvalorId : uId,
									approvalFlowId : approvalFlow.id
								});
								
						creatalert("添加审批成功",myFlow.parent.parent);		
					 //   Edo.MessageBox.alert("消息", "添加审批成功", null);  		
				      //  myFlow.removeAllChildren();
					  //  myFlow.addChild(builtmyFlowNodestatuspanel(newApprovalFlow));
					//	getChildrenAccordingtoFlow(newApprovalFlow);
	
			}
		}]
		
		});
		
	

		myFlow.addChild(modifybox);
	}
}
function builtmyFlowNodestatuspanel(approvalFlow)
	{
		 var myFlowNodestatuspanel=Edo.create({
						type : 'box',
					
						layout:'horizontal',
						border : [0, 0, 0, 0],
						padding : [5, 0, 0, 10],
						children:[]

					});		
					
	var statusindex=rebuiltconponent('statusindex' + approvalFlow.id,'审批阶段',100);		
		var status=rebuiltconponent('status' + approvalFlow.id,'节点状态',110);			
		var approvalor=rebuiltconponent('approvalor' + approvalFlow.id,'审批人',90);	
	    var operateresult=rebuiltconponent('operateresult' + approvalFlow.id,'审批意见',270);
	    var operate=rebuiltconponent('operate' + approvalFlow.id,'操作',100);
	    myFlowNodestatuspanel.addChild(statusindex);
	    myFlowNodestatuspanel.addChild(status);
	    myFlowNodestatuspanel.addChild(approvalor);
	    myFlowNodestatuspanel.addChild(operateresult);
	    myFlowNodestatuspanel.addChild(operate);
	return myFlowNodestatuspanel;
	
	}
function rebuiltconponent(comid,title,width)
	{
var tempcomponent=Edo.get(comid);
if(null!=tempcomponent)
tempcomponent.destroy();
tempcomponent=Edo.create({
						type:'panel',
						title:title,
						id:comid,
						padding : [5, 0, 0, 10],
	                 	border : [0, 0, 0, 0],
						width:width
						});
	
	return tempcomponent;
	
	}