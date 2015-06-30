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
		
		<script src="${ctx}/js/commontools/commonTable.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/commonTree.js" type="text/javascript"></script>
		<script src="${ctx}/js/privilege/roleUserTree.js" type="text/javascript"></script>
		
		<script src="${ctx}/js/commontools/Window.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/PopupManager.js" type="text/javascript"></script>
		
	</head>

	<body>
    </body>
</html>


<script type="text/javascript">
    /**
    分配权限的界面
    */
    //定义左侧的树结构
    //从服务器端获取数据
    
    var currentNode;
    var currentPowerset;
    var crut;
    var powersetWin = null;
    var actionWin = null;
    var myTable4 = null;
    
	var treeData = cims201.utils.getData('${ctx}/tree/privilege-tree!listPrivilegeTreeNodes.action',{treeType:"<%=request.getAttribute("type")%>",operationName:"节点管理"});
	//定义树列的显示名称
	var treeColumns = [{
				      header: '权限',
				      dataIndex: 'name'
				 }];
		
				 
		var myTree = new createTree({},treeColumns,treeData,'single',[],[],selectionEvent);
    	var leftPanel = Edo.create({
    		id: 'leftPanel',    
		    type: 'box',
		       
		    width: '20%',
		    height: "100%",
		    border: [0,0,0,0],  
		    padding: [0,0,0,0],    
		    layout: 'vertical',    
		    children: [
		        myTree.getTree()
		    ]
    	});
    	
    	//创建角色集合列表
    	var myConfig = {
    		horizontalScrollPolicy : 'off',
    		onBodymousedown: powersetSelection};
		var myColumns = [
                {
                    headerText: '权限集合',
                    dataIndex: 'name',
                    headerAlign: 'center',             
                    align: 'left',
                     width:60
                } ,
                   {
                    headerText: '创建人',
                    dataIndex: 'name',
                    headerAlign: 'center',  
                         
                    align: 'center',
                     renderer: function(v,r){
                     var result=r.creater.name+'(<font color=blue>'+r.creater.email+'</font>)';
                     return result;
                     }
                } ,
                   {
                	headerText: '',
                	dataIndex: 'name',            
                    align: 'center',
                    width: 17,
                    renderer: function(v,r){
                    	
                    	var out_str = '';                   	
               
                    	out_str += '<span class="icon-cims201-delete" onclick="deletePowerset('+r.id+');">';
                    	out_str += '&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}                 
            ];
    	myTable = new createTable(myConfig,'35%','100%','业务列表',myColumns,[],[],'', {},false);
    	
    	//创建角色和人员显示
    	var myConfig1 = {horizontalScrollPolicy : 'off' };
		var myColumns1 = [
                {
                    headerText: '已选角色',
                    dataIndex: 'name',
                    headerAlign: 'center',                 
                    align: 'center',
                    width: 200
                },
                {
                	headerText: '',
                	dataIndex: 'name',            
                    align: 'center',
                    width: 20,
                    renderer: function(v,r){
                    	
                    	var out_str = '';                   	
             
                    	out_str += '<span class="icon-cims201-delete" onclick="deletePowersetRole('+r.id+');">';
                    	out_str += '&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}              
            ];
    	myTable1 = new createTable(myConfig1,'100%','40%','角色名称',myColumns1,[],[],'', {},false);
    	var fieldSet1 = new Edo.containers.FieldSet()
		.set({
		    legend: '已选角色',
		    enableCollapse: true,//允许折叠
		    layout: 'vertical',    
		    children: [
		       myTable1.getTable()
		    ]
		});
		
		var myConfig2 = {horizontalScrollPolicy : 'off'};
		var myColumns2 = [
                {
                    headerText: '已选人员',
                    dataIndex: 'name',
                    headerAlign: 'center',                 
                    align: 'left',
                   width: 80,
                    renderer: function(v,r){
                    return '&nbsp&nbsp&nbsp&nbsp'+v+'   (<font color="blue">'+r.email+'</font>)';
                }
                },
                {
                	headerText: '',
                	dataIndex: 'name',            
                    align: 'center',
                    width: 20,
                    renderer: function(v,r){
                    	
                    	var out_str = '';                   	
             
                    	out_str += '<span class="icon-cims201-delete" onclick="deletePowersetUser('+r.id+');">';
                    	out_str += '&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}             
            ];
    	myTable2 = new createTable(myConfig2,'100%','58%','人员名称',myColumns2,[],[],'', {},false);
    	var fieldSet2 = new Edo.containers.FieldSet()
		.set({
		    legend: '已选角色',
		    enableCollapse: true,//允许折叠
		    layout: 'vertical',    
		    children: [
		        myTable2.getTable()
		    ]
		});
    	
    	var roleUserPanel = Edo.create({
    		id: 'roleUserPanel',    
		    type: 'box',         
		    width: '25%',
		    height: '100%',
		    border: [0,0,0,0],  
		    padding: [0,0,0,0],    
		    layout: 'vertical',    
		    children: [
		    	myTable1.getTable(),
		        myTable2.getTable()
		    ]
    	});
    	
    	var myConfig3 = {horizontalScrollPolicy : 'off'};
		var myColumns3 = [
                {
                    headerText: '已选动作',
                    dataIndex: 'name',
                    headerAlign: 'center',                 
                    align: 'center'
                } ,
                {
                	headerText: '',
                	dataIndex: 'name',            
                    align: 'center',
                    width: 20,
                    renderer: function(v,r){
                    	
                    	var out_str = '';                   	
           
                    	out_str += '<span class="icon-cims201-delete" onclick="deletePowersetAction('+r.id+');">';
                    	out_str += '&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}                
            ];
    	var myTable3 = new createTable(myConfig3,'100%','100%','已选动作',myColumns3,[],[],'', {},false);
    	
    
    
    	
    	var rightPanel = Edo.create({
    		id: 'rightPanel',    
		    type: 'box',         
		    width: '20%',
		    height: '100%',
		    border: [0,0,0,0],  
		    padding: [0,0,0,0],    
		    layout: 'vertical',    
		    children: [
		    	myTable3.getTable()
		    	//btPanel
		    ]
    	});
    	
    	
    	var mainPanel = Edo.create({
    		type: 'box',
    	
		    width: '100%',
		    height: '100%',  
		    border: [0,0,0,0],   
		    padding: [0,0,0,0],   
		    layout: 'horizontal',    
		    children: [
		    	leftPanel,
		    	myTable.getTable(),
		    	roleUserPanel,
		        rightPanel
		    ]
    	});
    	
    	
    	var outPanel = Edo.create({
    		type: 'box',
    		title: '选择人员',         
		    width: '1000',
		    height: '750',  
		    border: [0,0,0,0],   
		    padding: [0,0,0,0],     
		    layout: 'vertical',    
		    children: [
		    	cims201.utils.createBtPanel(['新增权限集合','新增人员角色','新增动作'],[addNewPowerset,addUserAndRole,addAction]),
		    	mainPanel
		    ],
		    render: document.body
    	});
    	
    
    	//定义当树节点选择后执行的选择方法
    	function selectionEvent(cn){  		
    		currentNode = cn.id;
    		var powerset = cims201.utils.getData('assignprivilege!listEmpowerment.action',{nodeId:currentNode});
    		myTable.setTableData(powerset);
    		currentPowerset = null;
    		
    		//清除表格数据
    		myTable1.clearAllRows();
    		myTable2.clearAllRows();
    		myTable3.clearAllRows();
    	}
    	
    	
    	//当选择了权限集合后的方法
    	function powersetSelection(v,r){
    	
    		if(v.item){
	    		//currentPowerset = Edo.util.Json.encode(v.item.id);
	    		currentPowerset = v.item.id;
	    	
	    		var powerset = cims201.utils.getData('assignprivilege!listUserAndRole.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});
	    		myTable1.setTableData(powerset.roleDtos);
	    		myTable2.setTableData(powerset.userDtos);
	    		
	    		var actionset = cims201.utils.getData('assignprivilege!listSelectedRights.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});
	    		myTable3.setTableData(actionset);
    		}
    	}
    	
    	/**
    	设置新增角色和人员的界面
    	*/
    	
    	function addUserAndRole(){
    		if(currentNode == null){
    			
    			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择一个域节点!',Edo.MessageBox.YESNO,
    			function(e){
    				
    			});
    			 
    		}else if(currentPowerset == null){
    			
    			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择一个角色集合!',Edo.MessageBox.YESNO,
    			function(e){
    				
    			});
    		}else{
    			if(crut == null){
	    			crut = new createRoleUserTree(currentNode,currentPowerset,['确定选择','重置选择'],[rtSubmitionAction,resetRtPanel]);	
	    		}else{
	    			crut.reSetSelectionContainter(currentNode,currentPowerset);    		
	    		}
	    		//在ie6下生成遮罩
	    			setWinScreenPosition(900,500,crut.getRoleUserTreeWin(),null);
	    	//	crut.getRoleUserTreeWin().show(100,100,true);
	    		}		  		
    	}
    	
    	function rtSubmitionAction(){
    		var roles=crut.getSelectRoles();
    		var users=crut.getSelectUsers();
    		var roleID=new Array();
    		for(var i=0;i<roles.length;i++){
    			roleID.push(roles[i].id);
    		}
    		var userID=new Array();
    		for(var i=0;i<users.length;i++){
    			userID.push(users[i].id);
    		}
    		var json_roles=Edo.util.Json.encode(roleID);
    		var json_users=Edo.util.Json.encode(userID);
    		
    		cims201.utils.getData('empowerment!addUsersRolesInEmpowerment.action',{nodeId:currentNode,currentEmpowerment:currentPowerset,jsonUsers:json_users,jsonRoles:json_roles});
    		
    		crut.getRoleUserTreeWin().hide();
    		
    		//刷新表格
    		var powerset = cims201.utils.getData('assignprivilege!listUserAndRole.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});
    		myTable1.setTableData(powerset.roleDtos);
    		myTable2.setTableData(powerset.userDtos);
    		
    		var actionset = cims201.utils.getData('assignprivilege!listSelectedRights.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});
    		myTable3.setTableData(actionset);
    		addAction();
    	}
    	
    	function resetRtPanel(){
    		crut.reSetSelectionContainter(currentNode,currentPowerset);  
    	}
    	
    	    	//删除一个权限集合集
    	function deletePowerset(rId){
    		//向后台请求删除数据
    		       
        Edo.MessageBox.confirm("提醒", "您确定删除这个管理员集合吗?", function(action, value){
       
        if(action=='yes')
        {	cims201.utils.getData('empowerment!delete.action',{currentEmpowerment:currentPowerset});
    		var r = myTable.getRowById_zd(rId);
    		myTable.deleteRecord(r);   	
    		myTable1.clearAllRows();
    		myTable2.clearAllRows();
    		myTable3.clearAllRows();	
    		//myTable4.clearAllRows();
    	}
    		});
    
    		
    	
    	}
    	
    	
    	//删除某个业务角色集合里面的一个角色
    	function deletePowersetRole(rId){
    		//向后台请求删除数据
    			       
        Edo.MessageBox.confirm("提醒", "您确定删除权限集合中的这个角色吗?", function(action, value){
       
        if(action=='yes')
        {
    		cims201.utils.getData('empowerment!deleteRoleInEmpowerment.action',{nodeId:currentNode,currentEmpowerment:currentPowerset,roleId:rId});
    		var r = myTable1.getRowById_zd(rId);
    		myTable1.deleteRecord(r);   		
    		if(!myTable1.hasRow())
    		if(!myTable2.hasRow())
    		myTable3.clearAllRows();
    	}
    	
    		});
    		}
    	//删除某个业务角色集合里面的一个人员
    	function deletePowersetUser(uId){
    		//向后台请求删除数据
        Edo.MessageBox.confirm("提醒", "您确定删除权限集合中的这个用户吗?", function(action, value){
       
        if(action=='yes')
        {
    		cims201.utils.getData('empowerment!deleteUserInEmpowerment.action',{nodeId:currentNode,currentEmpowerment:currentPowerset,userId:uId});
    		var r = myTable2.getRowById_zd(uId);
    		myTable2.deleteRecord(r);   
    		if(!myTable1.hasRow())
    		if(!myTable2.hasRow())
    		myTable3.clearAllRows();
    		
    			}
    	
    		});		
    	}
    	
    	//删除某个业务角色集合里面的一个动作
    	function deletePowersetAction(aId){
    		//向后台请求删除数据
    		Edo.MessageBox.confirm("提醒", "您确定删除权限集合中的这个动作?", function(action, value){
       
        if(action=='yes')
        {
    		cims201.utils.getData('empowerment!deleteRightInEmpowerment.action',{nodeId:currentNode,currentEmpowerment:currentPowerset,rightId:aId});
    		var r = myTable3.getRowById_zd(aId);
    		myTable3.deleteRecord(r);   		
    	}
    	
    		});		
    	}
    	
    	//新增权限集合的方法
    	function addNewPowerset(){
	    		if(currentNode == null){
	    			
	    			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择一个域节点!',Edo.MessageBox.YESNO,
	    			function(e){
	    				
	    			});
	    			 
	    		}else{
	    		if(powersetWin == null){   			
		    		var powersetForm = Edo.create({
					    id: 'powersetForm',    
					    type: 'box',
					    width: '100%',
					    height: '100%',
					    title: '1/3：新建权限集合',
					    
					    children: [
					        {
					            type: 'formitem',label: '名称<span style="color:red;">*</span>:',
					            children:[{type: 'text', width:200,id: 'empowermentName', valid: cims201.utils.validate.noEmpty}]
					        },
					        {
					            type: 'formitem',label: '描述:',
					            children:[{type: 'textarea', width:200, defaultHeight:80, id: 'empowermentDescription'}]
					        },
					        {
					            type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
					            children:[
					                {type: 'button', text: '提交表单', onclick: function(e){
					                	var o = Edo.get('powersetForm').getForm();
					                	o.nodeId=currentNode;
					                	
					                	//var o_json=Edo.util.Json.encode(o);
					                	var saveo = cims201.utils.getData('empowerment!save.action',o);

					                	myTable.addRecord(saveo);
					                	
					                	Edo.get('empowermentName').set('text','');
					                	Edo.get('empowermentDescription').set('text','');
					                	powersetWin.hide();
					                	currentPowerset=saveo.id;
					                	addUserAndRole();
					                }},
					                {type: 'space', width: 5},
					                {type: 'label', text: '<a href="javascript:resetForm();">重置</a>'}
					            ]
					        }
					    ]
					}); 	
					powersetWin = cims201.utils.getWin(350,200,'1/3：新建权限集合',powersetForm);
	    		
	    		}
	    		setWinScreenPosition(350,200,powersetWin,null);
	    		//powersetWin.show(300,100,true);		
    		} 			
    	}
    	
    	//选择动作
    	function addAction(){
    		//创建table    		
    		if(currentNode == null){
    			
    			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择一个域节点!',Edo.MessageBox.YESNO,
    			function(e){
    				
    			});
    			 
    		}else if(currentPowerset == null){
    			
    			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择一个角色集合!',Edo.MessageBox.YESNO,
    			function(e){
    				
    			});
    		}else{
    		
    		if(actionWin == null){
    			var myColumns4 = [
    				Edo.lists.Table.createMultiColumn(),
    				
	                {
	                    headerText: '已选动作',
	                    dataIndex: 'name',
	                    headerAlign: 'center',                 
	                    align: 'center'
	                }                
	            ];
				myTable4 = new createTable(myConfig3,'100%','100%','已选动作',myColumns4,['确定'],[selectAction],'', {},false);    			
    			actionWin = cims201.utils.getWin(350,400,'3/3：选择动作',myTable4.getTable());
    		}
    		var actionset = cims201.utils.getData('empowerment!listRights.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});	 
	    	myTable4.setTableData(actionset);
	    	//alert(myTable3.getSelectedItems().length);
    		myTable4.setSelectMyRow(myTable3.getAllRows());
    		//myTable4.setSelectMyRow(myTable3.getSelectedItems());
    		//actionWin.show(300,100,true);
    		setWinScreenPosition(350,400,actionWin,null);
    		}
    		
    		//执行选定动作的函数
    		function selectAction(e){
    			//nodeId:currentNode,currentEmpowerment:currentPowerset,
    			var selectedItems=myTable4.getSelectedItems();
    			var operationId=new Array();
    			for(var i=0;i<selectedItems.length;i++){
    				operationId.push(selectedItems[i].id)
    			}
    			var json_operationId=Edo.util.Json.encode(operationId);
    			
    			var add=cims201.utils.getData('empowerment!addRightsInEmpowerment.action',{nodeId:currentNode,currentEmpowerment:currentPowerset,jsonOperations:json_operationId,isAdmin:0});
    			myTable3.clearAllRows();
    			//myTable3.addRecords(selectedItems);	
    			var actionset = cims201.utils.getData('assignprivilege!listSelectedRights.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});
	    		myTable3.setTableData(actionset);
    			actionWin.hide();		
    		}    	
    	}
    	
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


