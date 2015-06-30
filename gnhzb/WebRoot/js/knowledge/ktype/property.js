 var myTable;
function createPropertyList(){
var myWin = null;
var myPropertyinput = null;
 //var myData = cims201.utils.getData('property!creatpropertyinput.action');
 
var myColumns = [
                    Edo.lists.Table.createSingleColumn(),
                  //  {header: '', dataIndex: 'id'},
                    {header: '是否通用',  width:50, headerAlign:'center',align:'center', dataIndex: 'isCommon'},
                    {header: '属性名称',  width:80, headerAlign:'center',align:'center', dataIndex: 'name'},
        		    {header: '中文名描述', width:80, headerAlign:'center',align:'center',  dataIndex: 'description'},
        			{header: '是否为空',  width:50, headerAlign:'center',align:'center', dataIndex: 'isNotNull'},
        			{header: '是否可见',  width:50, headerAlign:'center',align:'center', dataIndex: 'isVisible'},
        			{header: '字段类型',  width:55, headerAlign:'center',align:'center', dataIndex: 'propertyType'},
        			{header: '字符长度', width:50, headerAlign:'center',align:'center',  dataIndex: 'length'}
       			
                ];
                
 var myTable = new createTable({},'100%','100%','知识属性展示',myColumns,['新增','删除'],[addProperty,delRow],'property!creatpropertyinput.action', {},true,null);

function addProperty(){
	
	if(myWin == null){
		myPropertyinput = new propertyinput(function(){
			myWin.hide();
			myTable.search();		
		});
		myWin = new cims201.utils.getWin(232,270,"创建知识属性窗口",myPropertyinput.getPropertyinput());	
	
	}
	myPropertyinput.getPropertyinput().reset();
	myWin.show(400,100,true);
	
}


function delRow(){
var rs=myTable.getSelectedItems();

if(rs){
	myTable.deleteRecord(rs);
	cims201.utils.getData('property!deleteproperty.action',{id:rs[0].id});
	myTable.search();
}
}

Edo.create({
	id: 'ct',    
    type: 'box',                 
    layout: 'vertical',
    width: 1000,
    height: 500,    
    children: [
        myTable.getTable()
    ],
    render: document.body
	
});

this.getPropertyList = function(){
	return myTable;
}
}



