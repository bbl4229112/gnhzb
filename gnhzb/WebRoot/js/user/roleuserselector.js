//创建角色人员选择框
//treeUrl = 'knowledge/approval/approval!getQualifiedRoleNodes.action';
//tableUrl = 'user/role/userole!listUsersUnderRole.action';
function createRoleUserSelector(treeUrl,tableUrl,onSelection){
	var myWin;
	var outBox;
	
	//创建角色选择人员面板
	var myTreeData = cims201.utils.getData(treeUrl);
	//定义树列的显示名称
	var treeColumns = [{
				      header: '角色',
				      dataIndex: 'name'
				 }];
				 
//	var myTree = new createTree({},treeColumns,myTreeData,'single',[],[],onTreeSelection);  为了动态加载树   吉祥
	var myTree = new createTree({},treeColumns,myTreeData,'single',[],[],onTreeSelection,[],treeUrl);
	var myConfig2 = {horizontalScrollPolicy : 'off'};
	var myColumns2 = [
               {
                   headerText: '人员',
                   dataIndex: 'name',
                   headerAlign: 'center',                 
                   align: 'left',
                   width: 150,
                    renderer: function(v,r){
                   	
                   	var out_str = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';                   	
               
                   	out_str += '<span class="knowledge_simplist_title_author">'+r.name+'</span>'  ;                 	
                   
                   	return out_str;
                   }      
               },
               	  
               {
               	headerText: '',
               	dataIndex: 'name',            
                   align: 'center',
                   width: 90,
                   renderer: function(v,r){
                   	
                   	var out_str = '';                   	
                   	out_str += '<input type=radio onclick=onUserSelection("'+myWin.id+'",'+r.id+',"'+r.name+'");>';
                   	out_str += '选择'                   	
                  
                   	return out_str;
                   }           	
               }              
           ];
   	var userTable = new createTable(myConfig2,'100%','100%','已选人员',myColumns2,[],[],'', {},true);
	
	function onTreeSelection(v){		
		if(v){
			userTable.search({id:v.id},tableUrl);				
		}
	}
		
	outBox = Edo.create({
		type: 'box',
		width: 700,
		height: 490,
		border: [0,0,0,0],
		padding: [0,0,0,0],
		layout: 'horizontal',
		children: [
			myTree.getTree(),userTable.getTable()
		]
	});
	
	myWin = cims201.utils.getWin(730,500,'选择人员',outBox);
	myWin.userSelection = function(uId,uName){
			onSelection(uId, uName);
		};
	this.getWin = function(){
		return myWin;
	}
}

function onUserSelection(cmpId,uId,uName){
	var ddd = Edo.get(cmpId);

	ddd.userSelection(uId,uName);
	ddd.hide();
	//if(myWin){
	//	myWin.hide();
	//}
	//onSelection(uId,uName);				
}