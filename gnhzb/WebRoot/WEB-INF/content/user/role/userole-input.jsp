<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		

		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />

	

        

		
	</head>

	<body>
    </body>
</html>

	<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
		<script src="${ctx}/js/cims201.js" type="text/javascript"></script>
		<script src="${ctx}/js/utils.js" type="text/javascript"></script>
		
		<script src="${ctx}/js/commontools/commonTable.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/commonTree.js" type="text/javascript"></script>
		<script src="${ctx}/js/tree/commontree.js" type="text/javascript"></script>
		
		<script src="${ctx}/js/commontools/Window.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/PopupManager.js" type="text/javascript"></script>
	    <script src="${ctx}/js/user/user_input.js" type="text/javascript"></script>
	    <script src="${ctx}/js/commontools/commonForm.js" type="text/javascript"></script>
<script type="text/javascript">
    /**创建左侧的树
    */
    
    var myTable;
    var currentNode;
    //从服务器端获取数据
	var treeData = cims201.utils.getData('${ctx}/tree/privilege-tree!listPrivilegeTreeNodes.action',{treeType:"roleTree",operationName:"节点管理"});
	//alert(treeData);
	
	//定义树列的显示名称
	var treeColumns = [{
				      header: '权限',
				      dataIndex: 'name',
				      renderer: function(v,r){
				      	  return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '+(r.checked ? 'e-table-checked' : '')+'"></div>'+v+'</div>';            
				      }
				 }];
	//定义选择的方法
	function selectEvent(e){
		//alert(e.id);
		//首先重设表单
		myTable.reset();
		//然后读取数据
		var queryForm = {};
		queryForm.id = e.id;
		queryForm.size=5;
		
		currentNode = e;
		
		//myTable.search(queryForm,'userole!listRoleUsers.action');
		myTable.search(queryForm,'userole!listUsersUnderRole.action');
		rightBox.set('visible',true);
	
	}
	//var myTree = cims201.tree.createTree('domainTree',treeColumns,treeData,selectEvent,'30%','100%','single');	
    //myConfig,treeColumns,treeData,mode,btNames, btFunctions,selectionEvent,bodySelectionEvent
    var myTree = new createTree({},treeColumns,treeData,'single',[],[],selectEvent);
    
    var leftPanel = Edo.create({
    	type: 'box',
    	border: [0,0,0,0],
    	padding: [0,0,0,0],
    	width: '30%',
    	height: '100%',
    	children: [
    		myTree.getTree()
    	]
    });
    
    
    var queryBox = Edo.create({
    	type: 'box',
    	//width: '100%',
    	horizontalAlign: 'left', 
    	//padding: [0,0,10,0],
    	layout: 'horizontal',
    	border:0,
    	children: [
    		{id: 'key_name', type: 'text', width: 200},                        
            {id: 'mySearchBtn',type: 'button', text: '查找', width: 50,
            onclick: function(e){
							var searchName = Edo.get('key_name').text;
							
							var queryForm1 = {};
							queryForm1.id = myTree.getCurrentNode().id;
							queryForm1.size=5;
							queryForm1.searchName=searchName;
							myTable.search(queryForm1,'userole!listRoleUsers.action');	            	
		            	}}
            ]
    });
    
    var myColumns = [
    	Edo.lists.Table.createMultiColumn(),
        {header: '人员姓名', dataIndex: 'name', width: "100%",
          renderer: function(v,r){
          return '&nbsp&nbsp&nbsp&nbsp'+v+'   (<font color="blue">'+r.email+'</font>)';
          
          }
        
        }
    ];
    
    var myBtNames = ['新增','选择','删除'];
    var myBtFunctions = [
        function(e){    		
    		addUser(currentNode.id);
    	},
    	function(e){    		
    		addUserWin(currentNode.id);
    	},
    	function(e){
    		var myIds = myTable.getSelectedIds();
    		var myIds_Json = Edo.util.Json.encode(myIds);
    		
    		
			Edo.MessageBox.confirm("确定删除", "您确定删除这"+myIds.length+"条记录!", function(e){
				if(e=='yes'){
					//执行删除操作
					cims201.utils.getData('userole!deleteRoleUserRelation.action',{userIds:myIds_Json,id:myTree.getCurrentNode().id});
					myTable.search();
					alert('删除成功!');				
				}
			});
		}
		];
	//排序按钮，后加入	
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
                 	myTable.orderSwap(1,"userole!swap.action",currentNode);
                 }
             } ,
             {
                 type: 'button',
                 text: '向下',
                 onclick: function (e){
                 	myTable.orderSwap(-1,"userole!swap.action",currentNode);
                 }
             }
         ]
	
	});
    myTable = new createTable({},'100%','100%','人员列表',myColumns,myBtNames,myBtFunctions,'', {},null,null,null,orderBtn);
    
    var rightBox = Edo.create({
    	type: 'box',
    	width: '80%',
    	height: '100%',
    	layout: 'vertical',
    	border: [0,0,0,0],
    	padding: [0,0,0,0],
    	visible: false,
    	//render: document.body,
   
    	children: [queryBox,{type:'space', width: '100%'},myTable.getTable()]
    });
    
    var mainBox = Edo.create({
    	type: 'box',
    	width: 900,
    	height: 700,
    	layout: 'horizontal',
    	padding: [0,0,0,0],
    	render: document.body,
   
    	children: [leftPanel, rightBox]
    });
    
    Edo.get('mySearchBtn').set('onclick',function(e){
    	var queryForm = {};
    	queryForm.name = Edo.get('key_name').text;
    	myTable.search(queryForm);
    });
    
    var myWin;
    var myTable_addUser;
    var tableCon1;
    //该变量保存目前已经选择的人员id
    var selectedIds;
    //增加人员的弹出窗口
    function addUserWin(nodeId){
    	if(myWin != null){
    	myWin.destroy();
    	}
    	{
    		var myBtNames_addUser = ['选定人员'];
	    	var myBtFunctions_addUser = [
	    		function(e){
	    			var mRows = myTable_addUser.getSelectedItems();
					//删除选中的这几条记录
	    			Edo.MessageBox.confirm("确定", "您确定选定这"+mRows.length+"个人员!", function(e){
						if(e=='yes'){
							//删除选定的记录					
							tableCon1.addRecords(mRows);		    								
						}
					});
	    			
	    		}
	    	];
	    	
	    	var myConfig3 = {
	    		onBodyclick: function(e){
	    			//alert(e.item.id);
	    			tableCon1.addRecord(e.item);
	    			//tableCon1.setSelectMyRow([e.item]);
	    		},
	    		onHeaderclick: function(e){
	    			tableCon1.addRecords(myTable_addUser.getAllRows());
	    		}
	    	};
	    	
	    	myTable_addUser = new createTable(myConfig3,'100%','100%','可选人员列表',myColumns,myBtNames_addUser,myBtFunctions_addUser,'userole!listNotInRoleUsers.action', {id:nodeId});  		
	   		
	   		//在选定人员的界面中也增加搜索栏 
	   		var queryBox1 = Edo.create({
		    	type: 'box',
		    	//width: '100%',
		    	horizontalAlign: 'left', 
		    	//padding: [0,0,10,0],
		    	layout: 'horizontal',
		    	border:0,
		    	children: [
		    		{id: 'key_name1', type: 'text', width: 180},                        
		            {id: 'mySearchBtn1',type: 'button', text: '查找', width: 150,
		            	onclick: function(e){
		            		
		            	
							var sName = Edo.get('key_name1').text;
							myTable_addUser.search({searchName:sName});		            	
		            	}}
		            ]
		    });
	   		
	   		//创建table容器
	   		var btNames1 = ['删除','确定'];
	   		var btFunctions1 = [
	   			function(e){
					var mRows = tableCon1.getSelectedItems();
					//删除选中的这几条记录
	    			Edo.MessageBox.confirm("确定", "您删除选定这"+mRows.length+"个人员!", function(e){
						if(e=='yes'){
							//删除选定的记录					
							myTable_addUser.setDeSelectMyRow(mRows);	
	    					tableCon1.deleteRecords(mRows);
	    					alert('删除成功!');  				
						}
					});
				},
	   			function(e){
	   				//var userIds = tableCon1.getSelectedIds();
	   				var userIdsSource = tableCon1.getAllRows();
	   				
	   				var userIds=new Array()
					for(var i=0;i<userIdsSource.length;i++){
						var temp=userIdsSource[i].id;
						userIds.push(temp);
						
					}
	    			var userIds_json = Edo.util.Json.encode(userIds);
	    			var nodeId = myTable_addUser.nodeId;
	    			//执行选定人员
	    			Edo.MessageBox.confirm("确定", "您确定选定这"+userIds.length+"个人员!", function(e){
						if(e=='yes'){
							//保存选定的记录
							cims201.utils.getData('userole!saveRoleUserRelation.action',{userIds:userIds_json,id:nodeId});
	    					myTable.search({});
	    					//alert('添加成功!');
	    					myWin.hide();
						}
					});
	   			}];
	   		
	   		tableCon1 = new createTable({},'100%','100%','已选人员',myColumns,btNames1,btFunctions1,'', {},false);
	   		
	   		
	   		//设置该myTable_addUser的container为tableCon1
	   		myTable_addUser.setContainer(tableCon1);
	   		myTable_addUser.setIsCheckedStatus();
	   		
	   		var rightPanel = Edo.create({
	   			type: 'fieldset',
	   			legend: '已选人员',
		    	width: '50%',
		    	height: '100%',
		    	horizontalAlign: 'left', 
		    	//padding: [0,0,10,0],
		    	//layout: 'horizontal',
		    	border:0,
		    	children: [tableCon1.getTable()]		    		
	   		});
	   		
	   		
	   		var centerPanel = Edo.create({
	   			type: 'box',
		    	width: '50%',
		    	height: '100%',
		    	horizontalAlign: 'left', 
		    	//padding: [0,0,10,0],
		    	layout: 'vertical',
		    	border:0,
		    	children: [queryBox1,myTable_addUser.getTable()]
	   		});
	   		
	   		var mainPanel = Edo.create({
	   			type: 'box',
		    	width: '100%',
		    	height: '100%',
		    	horizontalAlign: 'left', 
		    	//padding: [0,0,10,0],
		    	layout: 'horizontal',
		    	border:0,
		    	children: [centerPanel,rightPanel]
	   		});
	   		
	   		
	   		
	   		
	   		myWin = cims201.utils.getWin(800,500,'增加人员',[mainPanel]);
    	}
    	
    	
    	myTable_addUser.nodeId = nodeId;
    	Edo.get('key_name1').set('text','');
    	myTable_addUser.reset();
    	myTable_addUser.search({id: currentNode.id});
    	myTable_addUser.setDeSelectMyRow(myTable_addUser.getAllRows());
    	tableCon1.clearAllRows();
    	//myTable_addUser.setSelectMyRow([0,1,2]);
    	setWinScreenPosition(800,500,myWin,null);
   	//	myWin.show(100,100,true);
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
function addUser(roletreenodeid){
   	if(myWin == null){
		myUserinput = new userinput(function(){
			myWin.hide();
			myTable.search();		
		},roletreenodeid);
		
		myWin = new cims201.utils.getWin(450,410,"增加用户窗口",myUserinput.getUserinput());				
	}
	else
	{
	myWin.destroy();
		myUserinput = new userinput(function(){
			myWin.hide();
			myTable.search();		
		},roletreenodeid);
	myWin = new cims201.utils.getWin(450,410,"增加用户窗口",myUserinput.getUserinput());	
	
	}
	Edo.get("email").set('valid',chkEmail);
	Edo.get("pictureword").set('visible',false);
	//Edo.get("roletreenode").set('visible',false);
	//Edo.get("picturePath").setValue("c5d8c0c9-b8a7-49c7-88f6-4f3514bd313f.jpg");
	myUserinput.getUserinput().reset();
	setWinScreenPosition(450,458,myWin,null);

}
function chkEmail(v){
	var userlist = cims201.utils.getData('user!listuser1.action',{});
	for (var i = 0; i < userlist.length; i++) {
		var user = userlist[i];
		var descr = user['email'];
		if(v == descr){
		return "邮箱已存在，请重新输入";
		}
	}
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{1,}){1,})$/;
	var result = reg.exec(v);
	if(result == null) 
		return "只能输入字母、数字和下划线";
}		
</script>


