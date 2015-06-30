<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		

		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		

		<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
		<script src="${ctx}/js/cims201.js" type="text/javascript"></script>
		<script src="${ctx}/js/utils.js" type="text/javascript"></script>
		<script src="${ctx}/js/tree/commontree.js" type="text/javascript"></script>
		<script src="${ctx}/js/tree/domaintree.js" type="text/javascript"></script>
		
		<script src="${ctx}/js/commontools/Window.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/PopupManager.js" type="text/javascript"></script>
		
		
	</head>

	<body>
		
	
	</body>
</html>


<script type="text/javascript">
	
	//Edo.util.Dom.append(document.body, '<div style="height:25px;overflow:hidden;"></div>');
	
	var myWin;
	var myForm;
	var btPanel;	
	var myDomainTree;
	
	function recall(){
	
	}
	
	
	//创建按钮
	var methodNames = ['增加子节点','删除','编辑'];
	
	
	
	
	
	var methodFunctions = [
		function(e){
			var currentNode = myDomainTree.currentNode;
			if(currentNode != null){
				if(myForm == null){
					myForm = cims201.tree.createTreeNodeForm();
				}
				if(myWin == null) 
					myWin = cims201.utils.getWin(370,200,'',myForm);
				
				Edo.get('domainSubmitBtn1').clearEvent();
				Edo.get('domainSubmitBtn1').set('onclick',function(){
					
						var o = myForm.getForm();
						o.id = currentNode.id;
						var newNode = cims201.utils.getData('tree!save.action',o);
						cims201.tree.addTreeNode(myDomainTree,newNode);
						//Edo.MessageBox.confirm("保存成功", "保存成功！", null);
						
						myWin.hide();
				});
			
					
				myWin.set('title','增加节点');
				cims201.tree.addDomainTreeNode(myForm);
				setWinScreenPosition(370,200,myWin,null);
				//myWin.show(100,250,true);	
			}else{
				cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'必须要选择一个节点',Edo.MessageBox.YESNOCANCEL); 
			}	
		},

		
		function(e){
			var currentNode = myDomainTree.currentNode;
			if(currentNode != null){
				
				Edo.MessageBox.confirm("确认删除", "你确定删除节点"+currentNode.name, function(action){
				if(action=='yes'){
					var result=cims201.utils.getData('tree!delete.action',currentNode);
					
					if(result.indexOf("不能删除节点:")!=-1){
						alert(result);
			
					}else if('error' !=result ){
						cims201.tree.deleteTreeNode(myDomainTree);
					}
				}	
				});
				
				//cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'effe',Edo.MessageBox.YESNOCANCEL); 
				
			}else{
				cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'必须要选择一个节点',Edo.MessageBox.YESNOCANCEL); 
			}
		},
		function(e){
			var currentNode = myDomainTree.currentNode;
			if(currentNode != null){
				if(myForm == null){
					myForm = cims201.tree.createTreeNodeForm();
				}
				if(myWin == null) 
					myWin = cims201.utils.getWin(370,200,'',myForm);
				
				Edo.get('domainSubmitBtn1').clearEvent();	
				Edo.get('domainSubmitBtn1').set('onclick',function(){
					var o = myForm.getForm();
					o.id=currentNode.id;
					var newNode = cims201.utils.getData('tree!update.action',o);
					
					cims201.tree.editTreeNode(myDomainTree,newNode);
					myWin.hide();
					//Edo.MessageBox.confirm("保存成功", "保存成功！", null);
					
				});
				
				myWin.set('title','编辑域');
				var r = myDomainTree.currentNode;
				cims201.tree.editDomainTreeNode(myForm,r);	
			//	myWin.show(200,200,true);	
				setWinScreenPosition(370,200,myWin,null);
				
			}else{
				cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'必须要选择一个节点',Edo.MessageBox.YESNOCANCEL); 
			}
			}
			
		];
		
	function addTreeNode(e){
			if(myForm == null){
				myForm = cims201.tree.createTreeNodeForm();
			}
			if(myWin == null) 
				myWin = cims201.utils.getWin(370,200,'',myForm);
			
			var dsb = Edo.get('domainSubmitBtn1');
			dsb.clearEvent();
			dsb.set('onclick',function(e){
				var o = myForm.getForm();
				o.json="<%=request.getAttribute("type")%>";
				var newNode = cims201.utils.getData('tree!save.action',o);
				cims201.tree.addTopTreeNode(myDomainTree,newNode);	
				Edo.MessageBox.confirm("保存成功", "保存成功！", null);
				
				myWin.hide();
			});
		
				
			myWin.set('title','增加域');
			cims201.tree.addDomainTreeNode(myForm);
			setWinScreenPosition(370,200,myWin,null);
			//myWin.show(200,200,true);	
		}
		
	function order(direction){
		
			if(direction>0){
				cims201.tree.order(myDomainTree,1,'tree!order.action');
			}else{
				cims201.tree.order(myDomainTree,-1,'tree!order.action');
			}
		}
		
	var data=cims201.utils.getData('${ctx}/privilege/show-button!whetherShowSuperAdminBtn.action');
		
	if(data.show == true || data.show == 'true'){
		//alert(data.show);
		methodNames.push('增加根节点');
		methodFunctions.push(addTreeNode);
	}
	
	var orderBtn = Edo.create({
		 type: 'button',
         //icon: 'user16',
         arrowMode: 'menu',
         text: '排序',
         menu: [
             
             {
                 type: 'button',
                 text: '向上',
                 onclick: function (e){
                 	order(1);
                 }
             } ,
             {
                 type: 'button',
                 text: '向下',
                 onclick: function (e){
                 	order(-1);
                 }
             }
         ]
	
	});
	
	btPanel = cims201.utils.createBtPanel(methodNames,methodFunctions,orderBtn);
	
	myDomainTree = cims201.tree.createDomainTree("<%=request.getAttribute("type")%>");
	
	//myDomainTree = cims201.utils.getData('${ctx}/tree/privilege-tree!listPrivilegeTreeNodes.action',{treeType:"roleTree",operationName:"节点管理"});
	//var DomainTreeheight=cims201.utils.getScreenSize().height-300;
	Edo.create({
	    id: 'myDomainTree123',    
	    type: 'box',
	    border: [0,0,0,0],
	    padding: [0,0,0,0],
	    width: 300,
	    height: '100%',
	    render: document.body,  
	    children: [btPanel,myDomainTree]	
	});		
       function setWinScreenPosition(width,height,win,kid)
		{
		var screenw= cims201.utils.getScreenSize().width;
		var screenh=cims201.utils.getScreenSize().height;
		if(width<screenw)
		{width=(screenw-width)/2
		
		}
		else{
		width=0;
		}
		if(height<screenh)
		{height=(screenh-height)/2
		
		}
		else{
		height=0;
		}
		if(null!=kid)
		unvisiblemodul(kid);
		 win.show(width,height,true);
		}		
	
</script>


