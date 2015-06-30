/**
创建通过角色树选取人员和角色的方法
*/

/**
submitAction是确认之后的执行函数
*/

function createRoleUserTreeWithoutRoleSelection(cn,cp, btNames,btFunctions){
	var currentNode = cn;
	var currentPowerset = cp;
	/**
	创建左侧的角色级联树
	*/
	//从服务器端获取数据
	
	treeData = cims201.utils.getData('empowerment!listRoleTreeNodesWithSelect.action',{nodeId:currentNode,currentEmpowerment:currentPowerset});
	var tttttt = treeData;
	//定义树列的显示名称
	var treeColumns = [{
				      header: '权限',
				      dataIndex: 'name',
				      renderer: renderCascadeTree
				 }];
				 
		roleTree = new createTree({},treeColumns,treeData,'single',[],[],onTreeBodySelection,onTreeBodySelection);
		
		var leftPanel = Edo.create({
    		//id: 'leftPanel',    
		    type: 'box',         
		    width: '30%',
		    height: '100%',
		    border: [0,0,0,0],      
		    layout: 'vertical',    
		    children: [
		        roleTree.getTree()
		    ]
    	});
    	
    	//创建人员列表
    	var myConfig = {horizontalScrollPolicy : 'off',
    	enableCellSelect : true,
    	
    	onBodymousedown : function(e){
    		//alert(myTable_rt.isSelect(e.item));
    		
			myTable_rt.getRowById_zd(1);
			//myTable1_rt.getRowById_zd(1);
    		myTable2_rt.getRowById_zd(1);
    		
    		if(myTable_rt.isSelect(e.item)){
    			myTable2_rt.addRecord(e.item);
    		}else{
    			myTable2_rt.deleteRecord(e.item);		
    		}
    		
    		myTable_rt.getRowById_zd(1);
			//myTable1_rt.getRowById_zd(1);
    		myTable2_rt.getRowById_zd(1);
    		
    	},
    	
    	onHeaderclick : function(e){
    	myTable2_rt.addRecords(myTable_rt.getAllRows());
    	//		myTable2_rt.addRecords(myTable_rt.getAllRows());
    		
    	}};
		var myColumns = [
				Edo.lists.Table.createMultiColumn(),	
                {
                    headerText: '可选人员',
                    dataIndex: 'name',
                    headerAlign: 'center',                 
                    align: 'left',
                    renderer: function(v,r){
                    return '&nbsp&nbsp&nbsp&nbsp'+v+'   (<font color="blue">'+r.email+'</font>)';
                }  
                }                
            ];
    	
		
		
	
    	
		//创建人员显示
		var myConfig2 = {horizontalScrollPolicy : 'off'};
		var myColumns2 = [
                {
                    headerText: '已选管理员',
                    dataIndex: 'name',
                    headerAlign: 'center',                 
                    align: 'left',
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
                    	 	
                    	//out_str += '<input type="button" value="点击" style="height:20px;" onclick="deleteSelectedUser();">';
           
                    	//out_str += '</input>';
                    	out_str += '<span class="icon-cims201-delete" onclick="deleteSelectedUser('+r.id+');">';
                    	out_str += '&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }           	
                }              
            ];
    	myTable2_rt = new createTable(myConfig2,'100%','100%','已选人员',myColumns2,[],[],'', {},false);
    	
    	myTable_rt = new createTable(myConfig,'30%','100%','人员名称',myColumns,[],[],'', {},false,myTable2_rt);
    	
    	  	
    	var roleUserPanel = Edo.create({
    		id: 'roleUserPanel1',    
		    type: 'box',         
		    width: '30%',
		    height: '100%',
		    border: [0,0,0,0],      
		    layout: 'vertical',    
		    children: [
		         myTable2_rt.getTable()
		    ]
    	});
    	
    	var mainPanel = Edo.create({
    		type: 'box',         
		    width: '100%',
		    height: '97%',  
		    border: [0,0,0,0],      
		    layout: 'horizontal',    
		    children: [
		    	leftPanel,
		    	myTable_rt.getTable(),
		    	roleUserPanel
		    ]
    	});
    	
    	var btPanel = cims201.utils.createBtPanel(btNames,btFunctions);
    	

		//创建外层的win
		var myWin = cims201.utils.getWin(900,500,'2/3：选择人员',[btPanel,mainPanel]);
		
		
		/**
			返回该win
		*/
		this.getRoleUserTreeWin = function(){
			return myWin;
		}
		
		/**
		设置currentNode和currentPowerset
		*/
		this.setCurrentNode = function(cn){
			currentNode = cn;
		}		
		this.setCurrentPowerset = function(cn){
			currentPowerset = cn;
		}
		this.getCurrentNode = function(){
			return currentNode;
		}
		this.getCurrentPowerset = function(){
			return currentPowerset;
		}
		
		
		/**
		初始化选择 
		*/
		
		this.reSetSelectionContainter = function(domainId,roleSetId){
			currentNode = domainId;
			currentPowerset = roleSetId;
			
			var rData = cims201.utils.getData('empowerment!listRoleTreeNodesWithSelect.action',{nodeId:domainId,currentEmpowerment:roleSetId});
			roleTree.reloadData(rData);
			
			//清空其他三个表格的数据
			myTable_rt.clearAllRows();
			//myTable1_rt.clearAllRows();
			myTable2_rt.clearAllRows();
		}
		
		/**
		*/
		
		//树节点选择后的方法 
		function onTreeSelection(v){
			if(v){
				//if(v.checked){
				//	myTable1_rt.addRecord({id:v.id,name:v.name});
				//}else{
				//	myTable1_rt.deleteRecord({id:v.id,name:v.name});
				//}				
			}
		}
		
		/**
		定义树节点的bodyclick选择时间，并不是点击checkbox 
		*/
		function onTreeBodySelection(sId){
			if(sId){
				
				var powerset = cims201.utils.getData('tree-empowerment!listUsersOutEmpowerment.action',{nodeId:sId.id,currentEmpowerment:currentPowerset});
	    		myTable_rt.setTableData(powerset.userDtos);
			}
		}
		
		//定义查询框的条件
		function searchAvailableUsers(r){
			//alert(r.text);
		}	
		
		/**
		获取选择的角色
		*/
		//this.getSelectRoles = function(){
		//	return myTable1_rt.getAllRows();
		//}
		
		/**
		获取选择的人员
		*/			
		this.getSelectUsers = function(){
			return myTable2_rt.getAllRows();
		}
}

/**
当点击已选人员删除按钮后执行的动作
*/	
function deleteSelectedUser(rId){
	//alert(rId);
	var r = myTable2_rt.getRowById_zd(rId);
	myTable2_rt.deleteRecord(r);
  	myTable_rt.setDeSelectMyRow([r]);		
	//myTable2_rt.deleteRecord(r);
	//myTable_rt.setSelectMyRow([rId]);			
}

/**
当点击已选人员删除按钮后执行的动作
*/	
//function deleteSelectedRole(rId){
	//alert(rId);
	//var r = myTable1_rt.getRowById_zd(rId);
	//myTable1_rt.deleteRecord(r);
	//roleTree.selectNode(r,true);
	//alert(r.id);
	//myTable2_rt.deleteRecord(r);
	//myTable_rt.setSelectMyRow([rId]);			
//}

