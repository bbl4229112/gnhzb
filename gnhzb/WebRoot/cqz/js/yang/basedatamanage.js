var basedata={};
var basedataTable = new Edo.data.DataTree()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
        },
        {name: 'name', mapping: 'name',  type: 'string'
        },
        {name: 'category', mapping: 'category',  type: 'string'
        },
        {name: 'unit', mapping: 'unit', type: 'string'
        },
        {name: 'unitprice', mapping: 'unitprice',  type: 'string'
        },
        {name: 'origin', mapping: 'origin',  type: 'string'
        },
        {name: 'updatetime', mapping: 'updatetime',  type: 'string'
        }
    ]
});
//bbl
/*var url='http://localhost:8080/gnhzb/department/department!getEmployee.action';
var param={};
var id='emp';
refreshdata(basedataTable,url,param,id);*/
/*var prividataTable = new Edo.data.DataTree()
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
var url='http://localhost:8080/gnhzb/department/department!getPrivilege.action';
var param={};
var id='privi';
refreshdata(prividataTable,url,param,id);*/
var depdataTable = new Edo.data.DataTree()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
            
        
        }
    ]
});
/*var url='http://localhost:8080/gnhzb/department/department!getDepartment.action';
var param={};
var id='dep';
refreshdata(depdataTable,url,param,id);*/
Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	verticalGap:'0',
	padding:[0,0,0,0],
	render: document.body,
	layout:'horizontal',
	horizontalGap: 10,
	children:[
	      {
    	    type:'panel',
			title:'数据类别',
			height: '100%',
			width:260,
			layout:'vertical',
			verticalGap:'0',
			padding:[0,0,0,0],
			border:[0,1,0,0],
			children:[	
	          	{
				type: 'group',
				width: '100%',
			    layout: 'horizontal',
			    cls: 'e-toolbar',
			    children: [
					        {type: 'button',id:'addbtn1',text: '新增类别',onclick: function(e){new getNewDepartmentWin()}},
							{type: 'split'},
					        {type: 'button',id:'xgbtn1',text: '修改类别',onclick: function(e){
					        	var r=dep.getSelected();
					        	if(r){
					        		new getModifyDepartmentWin(r);
					        	}
					        	}},
							{type: 'split'},
					        {type: 'button',id:'delebtn1',text: '删除',onclick: function(e){
					        	var r = dep.getSelected();
					        	if(r){
					        		dep.data.remove(r);
					        		var data= cims201.utils.getData('http://localhost:8080/gnhzb/department/department!deleteDepartment.action',{id:r.id});
				        		}else{
				        			alert("请选择部门");
			        			}}},
				            //{type: 'split'}
					      ]
				},
		        {
				id:'dep',
				type: 'tree',		
		        width: 260,
		        height: '100%',
		        headerVisible: false,
		        autoColumns: true,
		        horizontalLine: false,
		        columns: [{header: '名称',dataIndex: 'name'}],
		        onselectionchange: function(e){	
	                var data= cims201.utils.getData('http://localhost:8080/gnhzb/department/department!getEmployeeByDepartment.action',{id:e.selected.id});
	                emp.set("data",data)
		        },
				data:depdataTable
		       	}]
	      },
       	  {
    	    type:'panel',
			title:'基础数据',
			width: 850,
			height: '100%',
			verticalGap:'0',
			padding:[0,0,0,0],
			border:[0,1,0,0],
			children:[{
				type: 'group',
			    width: 400,
			    layout: 'horizontal',
			    cls: 'e-toolbar',
			    children: [
					        {type: 'button',id:'addbtn',text: '新增数据',onclick: function(e){new getNewBaseDataWin()}},
							{type: 'split'},
					        {type: 'button',id:'xgbtn',text: '修改数据',onclick: function(e){
					        	var r=emp.getSelected();
					        	alert('是的');
					        	if(r){
					        		new getModifyBaseDataWin(r);
					        	}
					        }},
							{type: 'split'},
					        {type: 'button',id:'delebtn',text: '删除',onclick: function(e){var r = tb.getSelected();
		                    if(r){tb.data.remove(r);}else{alert("请选择行");}}},
				            {type: 'split'},
					        {type: 'button',id:'rebtn',text: '刷新'},]
			},
			{
				id: 'basedata', type: 'table', width: 850, height: '100%',
			    rowSelectMode : 'single',
			    horizontalScrollPolicy:'off',
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
	                         {header:'序号',dataIndex: 'id', width: '100',headerAlign: 'center',align: 'center'},
	                         {header:'名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'},
	                         {header:'类别',dataIndex: 'category',width: '100', headerAlign: 'center',align: 'center'},
	                         {header:'单位',dataIndex: 'unit', width: '100',headerAlign: 'center',align: 'center'},
	                         {header:'单价',dataIndex: 'unitprice', width: '100',headerAlign: 'center',align: 'center'},
	                         {header:'来源',dataIndex: 'origin',width: '100', headerAlign: 'center',align: 'center'},
	                         {header:'更新时间',dataIndex: 'updatetime', width: '100',headerAlign: 'center',align: 'center'},
	                         {header:'操作',dataIndex: 'operation', width: '100',headerAlign: 'center',align: 'center'}],
				data:basedataTable
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

function getNewBaseDataWin(){
	var func=function(id){
		/*basedata.name=Edo.get('name').text;
		basedata.category=Edo.get('category').getValue();
		basedata.unit=Edo.get('unit').getValue();
		basedata.unitprice=Edo.get('unitprice').text;
		basedata.origin=Edo.get('origin').getValue();
		basedata.updatetime=Edo.get('updatetime').date.format('Y-m-d')();
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/department/department!saveEmployee.action',{employee:employee});
		var url='http://localhost:8080/gnhzb/department/department!getEmployee.action';
		var param={};
		var id='basedata';
		refreshdata(basedataTable,url,param,id);
		basedata={};*/
 	}
	    var content=new basedatadef();
	    var toolbar=new getnextbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'填写基础物质信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	
}
function getModifyBaseDataWin(r){
	var func=function(id){
		/*basedata.name=Edo.get('name').text;
		basedata.category=Edo.get('category').getValue();
		basedata.unit=Edo.get('unit').getValue();
		basedata.unitprice=Edo.get('unitprice').text;
		basedata.origin=Edo.get('origin').getValue();
		basedata.updatetime=Edo.get('updatetime').date.format('Y-m-d')();
		basedata.id=r.id;
			
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/department/department!saveEmployee.action',{employee:employee});
		var url='http://localhost:8080/gnhzb/department/department!getEmployee.action';
		var param={};
		var id='basedata';
		refreshdata(basedataTable,url,param,id);
		basedata={};*/
		
		  
		
 	}
	    var content=new basedatadef(r);
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'修改基础物质信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	    
}	
function basedatadef(r){
	var content = Edo.create(
		    {type: 'box',id:'basedatawincontent',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
		    	verticalGap:'10',
	       	    children: [
	       	    //				           
	       	    {	type : 'formitem',label : '名称:',labelWidth : 150,labelAlign : 'right',
	       	    children : [{type : 'text',width : 200,id : 'name'}]
	       	    },
	       	    {	type : 'formitem',label : '类别:',labelWidth : 150,labelAlign : 'right',
	       	    children : [
	       	             {
			       	  		type: 'combo',
			       	  		id : 'category',
			       	  		displayField: 'label', 
			       			valueField: 'value',
			       			width: 200,
			       		    data: [
			       	        {label: '未解决', value: 1},
			       	        {label: '已解决', value: 2}
			       	        ]
		
			       	   	}]
	       	    },
	       	    {	type : 'formitem',label : '单位:',labelWidth : 150,labelAlign : 'right',
	           	    children : [
	           	             {
				       	  		type: 'combo',
				       	  		id : 'unit',
				       	  		displayField: 'label', 
				       			valueField: 'value',
				       			width: 200,
		/*				       			onclick: function(e){
				       				registEnter();
				       			},  
				       			onblur: function(e){
				       				unregistEnter();
				       			},
				       			
*/				       		    data: [
				       	        {label: '未解决', value: 1},
				       	        {label: '已解决', value: 2}
				       	        ]
			
				       	   	}]
	       	    },
	       	    {	type : 'formitem',label : '单价:',labelWidth : 150,labelAlign : 'right',
	           	    children : [{type : 'text',width : 200,id : 'unitprice',valid:noEmpty}]
	       	    },
	       	    {	type : 'formitem',label : '来源:',labelWidth : 150,labelAlign : 'right',
	           	    children : [
	           	             {
				       	  		type: 'combo',
				       	  		id : 'origin',
				       	  		displayField: 'label', 
				       			valueField: 'value',
				       			width: 200,
/*				       			onclick: function(e){
				       				registEnter();
				       			},  
				       			onblur: function(e){
				       				unregistEnter();
				       			},
				       			
*/				       		    data: [
				       	        {label: '未解决', value: 1},
				       	        {label: '已解决', value: 2}
				       	        ]
			
				       	   	}]
	       	    }
	       	    ]
	       	});
	if(r!=null){
		basedatawincontent.setForm(r);
	}
	       	return content;
}
function getnextbar(){
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
            id:'mm',
            type: 'button',
            text: '下一步',
            minWidth: 70,
            onclick: function(e){
               //定义填写完模型信息后的功能，包括模型panel的信息赋值，
             	var func=function(id){
             	}
             	//如果没有选择产品类别，提示选择
             	if(Edo.get('name').text!=null){
             		var content=new materialconsume();
             	    var toolbar=new gettoolbar(null,func);
              	    var win=cims201.utils.getWin(620,450,'编辑涉及能源消耗',[content,toolbar]);
             	    win.show('center', 'middle', true);
             	}else{
             		alert('请填写完整信息！');
             	}
            
            }
        },
        {
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
function gettoolbar(id,func){
    var toolbar = Edo.create(
    {type: 'ct',
    cls: 'e-dialog-toolbar',
    width: '100%',
    layout: 'horizontal',
    height: 50,
    horizontalAlign: 'center',
    verticalAlign: 'middle',
    horizontalGap: 10,
    children: [
			{
			    type: 'button',
			    text: '上一步',
			    minWidth: 70,
			    onclick: function(e){
			    if(func==undefined){
			    }else{
			    func(id);
			    }
			    this.parent.parent.parent.destroy();
			    }
			},       
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
function materialconsume(){
	var materialconsumebox=Edo.create(
			{
				id: 'materialconsumebox', type: 'box', width: '100%', height: '100%',padding:[0,0,0,0],
				border:[0,0,0,0],
				children:[{
			        type: 'button',
			        width: 80,
			        height: 25,
			        text: '添加',
			        onclick: function(e){
			            showeditor();
			        }                
			        },
			        {
						id: 'basematerial', type: 'table', width: '100%', height: 150,
						padding:[0,0,0,0],
					    rowSelectMode : 'single',
					    horizontalScrollPolicy:'off',
					    columns:[
			                         Edo.lists.Table.createMultiColumn(),
			                         {header:'现有物质名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'类别',dataIndex: 'category',width: '100', headerAlign: 'center',align: 'center'},
			                         {header:'单位',dataIndex: 'unit', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'单价',dataIndex: 'unitprice', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'来源',dataIndex: 'origin',width: '100', headerAlign: 'center',align: 'center'},
			                         {header:'更新时间',dataIndex: 'updatetime', width: '100',headerAlign: 'center',align: 'center'},
			                        ],
						data:basedataTable
					},
					{
				        type: 'button',
				        width: 80,
				        height: 25,
				        text: '删除',
				        onclick: function(e){
				            showeditor();
				        }                
			        },
			        {
						id: 'choosenbasematerial', type: 'table', width: '100%', height: 150,horizontalScrollPolicy:'off',
						padding:[0,0,0,0],
					    rowSelectMode : 'single',
					    columns:[
			                         Edo.lists.Table.createMultiColumn(),
			                         {header:'已选名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'类别',dataIndex: 'category',width: '100', headerAlign: 'center',align: 'center'},
			                         {header:'单位',dataIndex: 'unit', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'数量',dataIndex: 'amount', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'来源',dataIndex: 'origin',width: '100', headerAlign: 'center',align: 'center'},
			                         {header:'更新时间',dataIndex: 'updatetime', width: '100',headerAlign: 'center',align: 'center'}
			                         ]
					}
					
				]}
			
			)
			return materialconsumebox;
	
}