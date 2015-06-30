/**
专家领域信息输入的界面
*/
var expertTable;
function createExpert(){
    var powersetWin = null;


var expertColumns = [
                    Edo.lists.Table.createMultiColumn(),
                    
                   {header: '姓名', width:'20%', headerAlign:'center',align:'center',  dataIndex: 'username'},
                    {header: '性别', width:'20%', headerAlign:'center',align:'center',  dataIndex: 'usersex'},
                    {header: '职称', width:'20%', headerAlign:'center',align:'center',  dataIndex: 'userprofess'},
                    {header: '擅长领域', width:'40%', headerAlign:'center',align:'center',  dataIndex: 'treenodename'} 
   			
                ];
           
           
expertTable = new createTable({autoColumns:true},'70%','60%','专家列表展示',expertColumns,['新增','删除'],[addExpert,delExpert],'qanda/qanda!listAllExpert.action', {},true,null);
//alert("ddddd"+expertTable.getTable());

var searchExperts = Edo.create({
			//id: 'autoexpert',
			type: 'autocomplete', 
			width: 200, 
			queryDelay: 500,
			url: 'user/user!searchuser.action',
			popupHeight: '100',
			valueField: 'name', 
			displayField: 'name'
			
    	});	
    	
function addExpert(){
	
  		if(powersetWin == null){   			
   		var powersetForm = Edo.create({  
		    type: 'box',
		    width: '100%',
		    height: '100%',
		    title: '设置专家',
		    
		    children: [
		        {
		            type: 'formitem',label: '用户名<span style="color:red;">*</span>:',
		            children:searchExperts
		        },
		        
		        {
		            type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
		            children:[
		                {type: 'button', text: '设为专家', onclick: function(e){		                
		                			                			                	
		                	 var selecttree = cims201.utils.getData('qanda/qanda!setExpert.action',{json:searchExperts.get('text')});
		                	 if(selecttree!=null){
		                	 	 alert(selecttree);
		                	 }		                	
		                	 expertTable.search();
                
		                	powersetWin.hide();		                	
		                }}
		              
		            ]
		        }
		    ]
		}); 	
		powersetWin = cims201.utils.getWin(350,200,'设置专家',powersetForm);
  		
  		}
  		setWinScreenPosition(350,200,powersetWin,null);
  		
}


function delExpert(){
	
	var rs=expertTable.getSelectedItems();
	
	if(rs){
		expertTable.deleteRecord(rs);
		cims201.utils.getData('qanda/qanda!deleteExpert.action',{json:rs[0].id});
		expertTable.search();	
		
	}

}

this.getExpertList = function(){
	return expertTable.getTable();
}


}






