var employee={};
var employdataTable = new Edo.data.DataTree()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
        },
        {name: 'emial', mapping: 'emial',  type: 'string'
        },
        {name: 'sex', mapping: 'sex', type: 'string'
            
        },
        {name: 'hobby', mapping: 'hobby',  type: 'string'
        },
        {name: 'dep', mapping: 'dep',  type: 'string'
        },
        {name: 'depid', mapping: 'depid',  type: 'string'
        },
        {name: 'privi', mapping: 'privi', type: 'string'
            
        },
        {name: 'priviid', mapping: 'priviid', type: 'string'
            
        }
        
    ]
});
var url='http://localhost:8080/Myproject20140827/department/department!getEmployee.action';
var param={};
var id='emp';
refreshdata(employdataTable,url,param,id);
var prividataTable = new Edo.data.DataTree()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
        },
        {name: 'code', mapping: 'code',  type: 'string'
        }
    ]
});
var url='http://localhost:8080/Myproject20140827/department/department!getPrivilege.action';
var param={};
var id='privi';
refreshdata(prividataTable,url,param,id);
var depdataTable = new Edo.data.DataTree()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
            
        
        }
    ]
});
var url='http://localhost:8080/Myproject20140827/department/department!getDepartment.action';
var param={};
var id='dep';
refreshdata(depdataTable,url,param,id);
Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	verticalGap:'0',
	padding:[0,0,0,0],
	render: document.body,
	children:[{
		type:'box',
		width: '100%',
		height: '100%',
		verticalGap:'0',
		padding:[0,0,0,0],
		children:[{
			type: 'group',
		    width: '100%',
		    layout: 'horizontal',
		    cls: 'e-toolbar',
		    children: [
				        {type: 'button',id:'addbtn',text: '新增',onclick: function(e){new getNewEmployeeWin()}},
						{type: 'split'},
				        {type: 'button',id:'xgbtn',text: '修改',onclick: function(e){
				        	var r=emp.getSelected();
				        	alert('是的');
				        	if(r){
				        		new getModifyEmployeeWin(r);
				        	}
				        }},
						{type: 'split'},
				        {type: 'button',id:'delebtn',text: '删除',onclick: function(e){var r = tb.getSelected();
	                    if(r){tb.data.remove(r);}else{alert("请选择行");}}},
			            {type: 'split'},
				        {type: 'button',id:'rebtn',text: '刷新'},]
		},
		{
			id: 'emp', type: 'table', width: '100%', height: '100%',autoColumns: true,
			padding:[0,0,0,0],
		    rowSelectMode : 'single',
		    columns:[{
		            	 headerText: '',
                         align: 'center',
                         width: 10,                        
                         enableSort: false,
                         enableDragDrop: true,
                         enableColumnDragDrop: false,
                         style:  'cursor:move;',
                         renderer: function(v, r, c, i, data, t){
                         return i+1;}},
                         Edo.lists.Table.createMultiColumn(),
                         {header:'用户序号',dataIndex: 'id', width: '100',headerAlign: 'center',align: 'center'},
                         {header:'用户姓名',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'},
                         {header:'邮箱',dataIndex: 'emial',width: '100', headerAlign: 'center',align: 'center'},
                         {header:'用户熟悉领域',dataIndex: 'sex', width: '100',headerAlign: 'center',align: 'center'},
                         {header:'用户姓名',dataIndex: 'hobby', width: '100',headerAlign: 'center',align: 'center'},
                         {header:'邮箱',dataIndex: 'dep',width: '100', headerAlign: 'center',align: 'center'},
                         {header:'用户熟悉领域',dataIndex: 'privi', width: '100',headerAlign: 'center',align: 'center'},
                         {type: 'space', width:'100%'}],
			data:employdataTable
		}]
	}]
});

function classnameRegex(v){
    if (v.search(/^[A-Za-z0-9]+$/) != -1)
        return true;
    else return "类别名称格式错误";
} 
function noEmpty(v){
    if(v == "") return "不能为空";
}function resetForm(){
    userForm.reset();
}
function resetForm(){
    userForm.reset();
}

xgbtn.on('click', function(e){});

function getNewEmployeeWin(){
	var func=function(id){
		employee.name=Edo.get('name').text;
		employee.sex=Edo.get('sex').text;
		employee.email=Edo.get('emial').text;
		employee.passwd=Edo.get('passwd').text;
		employee.hobby=Edo.get('hobby').text;
		employee.depid=Edo.get('depid').getValue();
		employee.priviid=Edo.get('priviid').getValue();
		var data= cims201.utils.getData('http://localhost:8080/Myproject20140827/department/department!saveEmployee.action',{employee:employee});
		var url='http://localhost:8080/Myproject20140827/department/department!getEmployee.action';
		var param={};
		var id='emp';
		refreshdata(employdataTable,url,param,id);
		employee={};
 	}
	    var content=new employeedef();
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'填写员工信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	
}
function getModifyEmployeeWin(r){
	var func=function(id){
		employee.name=Edo.get('name').text;
		employee.sex=Edo.get('sex').text;
		employee.email=Edo.get('emial').text;
		employee.passwd=Edo.get('passwd').text;
		employee.hobby=Edo.get('hobby').text;
		employee.depid=Edo.get('depid').getValue();
		employee.priviid=Edo.get('priviid').getValue();
		employee.id=r.id;
			
		var data= cims201.utils.getData('http://localhost:8080/Myproject20140827/department/department!saveEmployee.action',{employee:employee});
		var url='http://localhost:8080/Myproject20140827/department/department!getEmployee.action';
		var param={};
		var id='emp';
		refreshdata(employdataTable,url,param,id);
		employee={};
		  
		
 	}
	    var content=new employeedef(r);
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'修改部门信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	    
}	
function employeedef(r){
	
	var content = Edo.create(
		    {type: 'box',id:'empwincontent',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
	       	    children: [
	       	    //				           
	       	    {	type : 'formitem',label : '人员名称:',labelWidth : 150,labelAlign : 'right',
	       	    children : [{type : 'text',width : 200,id : 'name'}]
	       	    },
	       	    {	type : 'formitem',label : '性别:',labelWidth : 150,labelAlign : 'right',
	       	    children : [{type : 'text',width : 200,id : 'sex'}]
	       	    },
	       	    {	type : 'formitem',label : '邮箱:',labelWidth : 150,labelAlign : 'right',
	           	    children : [{type : 'text',width : 200,id : 'emial',valid:noEmpty}]
	       	    },
	       	    {	type : 'formitem',label : '密码:',labelWidth : 150,labelAlign : 'right',
	           	    children : [{type : 'text',width : 200,id : 'passwd',valid:noEmpty}]
	       	    },
	       	    {	type : 'formitem',label : '爱好:',labelWidth : 150,labelAlign : 'right',
	           	    children : [{type : 'text',width : 200,id : 'hobby'}]
	       	    },
	       	    {	type : 'formitem',label : '部门:',labelWidth : 150,labelAlign : 'right',
	           	    children : [
	           	                
       	                {
					    id:'depid',
					    type: 'treeselect',            
					    multiSelect: false,
					    displayField: 'name',
					    valueField: 'id',
					    rowSelectMode: 'single',    
					    
					    treeConfig: {
					        treeColumn: 'name',
					        columns: [     
			                  	{headerText: '',
			  	                align: 'center',
			  	                width: 10,                        
			  	                enableSort: false,
			  	                enableDragDrop: true,
			  	                enableColumnDragDrop: false,
			  	                style:  'cursor:move;',
			  	                renderer: function(v, r, c, i, data, t){
			  	                return i+1;}},      
					            {id: 'name',header: '部门名称', dataIndex: 'name'}
					        ],
					        data:depdataTable
       	                
					    }
					}]
	       	    },
	       	    {	type : 'formitem',label : '角色:',labelWidth : 150,labelAlign : 'right',
	           	    children : [
       	                {
					    id:'priviid',
					    type: 'treeselect',            
					    multiSelect: false,
					    displayField: 'name',
					    valueField: 'id',
					    rowSelectMode: 'single',    
					    
					    treeConfig: {
					        treeColumn: 'name',
					        columns: [     
			                  	{headerText: '',
			  	                align: 'center',
			  	                width: 10,                        
			  	                enableSort: false,
			  	                enableDragDrop: true,
			  	                enableColumnDragDrop: false,
			  	                style:  'cursor:move;',
			  	                renderer: function(v, r, c, i, data, t){
			  	                return i+1;}},      
					            {id: 'name',header: '角色名称', dataIndex: 'name'}
					        ],
					        data:prividataTable
       	                
					    }
					}]
	       	    }
	       	   
	       	    ]
	       	});
	if(r!=null){
		empwincontent.setForm(r);
	}
	       	return content;
}
function gettoolbar(id,func){
    var toolbar = Edo.create(
    {type: 'ct',
    cls: 'e-dialog-toolbar',
    width: '100%',
    layout: 'horizontal',
    height: '30%',
    horizontalAlign: 'center',
    verticalAlign: 'middle',
    horizontalGap: 10,
    children: [
               
        {
            type: 'button',
            text: '确定',
            minWidth: 70,
            onclick: function(e){
            if(func==undefined){
            }else{
            func(id);
            }
            this.parent.parent.parent.destroy();
            }
        },{
            type: 'button',
            text: '取消',
            minWidth: 70,
            onclick: function(e){
            this.parent.parent.parent.destroy();

            }
        }
    ]
});
return toolbar;
}
function refreshdata(dataTable,url,param,id){
    var data= cims201.utils.getData(url,param);
	dataTable.set('data',data);
}