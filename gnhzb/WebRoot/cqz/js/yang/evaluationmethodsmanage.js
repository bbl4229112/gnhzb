var evaluationmethods={};
var evaluationmethodsdataTable = new Edo.data.DataTree()
.set({
    fields: [

        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
        },
        {name: 'creator', mapping: 'creator',  type: 'string'
        },
        {name: 'updatetime', mapping: 'updatetime', type: 'string'
        }
    ]
});
/*var url='http://localhost:8080/gnhzb/department/department!getEmployee.action';
var param={};
var id='evaluationmethodsdata';
refreshdata(evaluationmethodsdataTable,url,param,id);*/
Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	verticalGap:'0',
	padding:[0,0,0,0],
	border:[0,0,0,0],
	render: document.body,
	layout:'horizontal',
	horizontalGap:10,
	children:[
	      {
			type:'panel',
			title:'评价方法',
			width: 600,
			height: '100%',
			verticalGap:'0',
			padding:[0,0,0,0],
			border:[0,1,0,0],
			children:[{
				type: 'group',
			    width: 300,
			    layout: 'horizontal',
			    cls: 'e-toolbar',
			    children: [
					        {type: 'button',id:'addbtn',text: '新增',onclick: function(e){new getNewEvaluationMethodWin()}},
							{type: 'split'},
					        {type: 'button',id:'xgbtn',text: '修改',onclick: function(e){
					        	var r=emp.getSelected();
					        	alert('是的');
					        	if(r){
					        		new getModifyEvaluationMethodWin(r);
					        	}
					        }},
							{type: 'split'},
					        {type: 'button',id:'delebtn',text: '删除',onclick: function(e){var r = tb.getSelected();
		                    if(r){tb.data.remove(r);}else{alert("请选择行");}}},
				            {type: 'split'},
					        {type: 'button',id:'rebtn',text: '刷新'}]
			},
			{
				id: 'evaluationmethodsdata', type: 'table', width: 600, height: '100%',
				padding:[0,0,0,0],
			    rowSelectMode : 'single',
			    horizontalScrollPolicy:'off',
			    columns:[{
			            	 headerText: '',
	                         align: 'center',
	                         width: 20,                        
	                         enableSort: false,
	                         enableDragDrop: true,
	                         enableColumnDragDrop: false,
	                         style:  'cursor:move;',
	                         renderer: function(v, r, c, i, data, t){
	                         return i+1;}},
	                         Edo.lists.Table.createMultiColumn(),
	                         {header:'编号',dataIndex: 'id', width: '100',headerAlign: 'center',align: 'center'},
	                         {header:'名称',dataIndex: 'name', width: '150',headerAlign: 'center',align: 'center'},
	                         {header:'创建者',dataIndex: 'creator',width: '150', headerAlign: 'center',align: 'center'},
	                         {header:'更新时间',dataIndex: 'updatetime', width: '150',headerAlign: 'center',align: 'center'}
	                         ],
				data:evaluationmethodsdataTable
			}]
		},
		{
			type:'panel',
			title:'评价指标',
			width: 500,
			height: '100%',
			padding:[0,0,0,0],
			border:[0,1,0,0],
			verticalGap:0,
			children:[
		          {
					type: 'group',
				    width: 300,
				    layout: 'horizontal',
				    cls: 'e-toolbar',
				    children: [
						        {type: 'button',id:'addbtn1',text: '新增',onclick: function(e){new getNewEvaluationMethodWin()}},
								{type: 'split'},
						        {type: 'button',id:'xgbtn1',text: '修改',onclick: function(e){
						        	var r=emp.getSelected();
						        	alert('是的');
						        	if(r){
						        		new getModifyEvaluationMethodWin(r);
						        	}
						        }},
								{type: 'split'},
						        {type: 'button',id:'delebtn1',text: '删除',onclick: function(e){var r = tb.getSelected();
			                    if(r){tb.data.remove(r);}else{alert("请选择行");}}},
					            {type: 'split'},
						        {type: 'button',id:'rebtn1',text: '刷新'}]
				},
				{
					id: 'effecttable', type: 'table', width: 500, height: '100%',
					padding:[0,0,0,0],
				    rowSelectMode : 'single',
				    horizontalScrollPolicy:'off',
				    columns:[{
				            	 headerText: '',
		                         align: 'center',
		                         width: 20,                        
		                         enableSort: false,
		                         enableDragDrop: true,
		                         enableColumnDragDrop: false,
		                         style:  'cursor:move;',
		                         renderer: function(v, r, c, i, data, t){
		                         return i+1;}},
		                         Edo.lists.Table.createMultiColumn(),
		                         {header:'编号',dataIndex: 'id', width: '150',headerAlign: 'center',align: 'center'},
		                         {header:'名称',dataIndex: 'name', width: '150',headerAlign: 'center',align: 'center'},
		                         {header:'操作',dataIndex: 'updatetime', width: '150',headerAlign: 'center',align: 'center',
		                        	 renderer: function(v, r){
					                    	return '<span style="cursor: pointer;" onclick="editeffectmaterial();">查看</span>';
					                    }
		                        	 }
		                         ],
					data:[{id:1}]
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

function getNewEvaluationMethodWin(){
	var func=function(id){
		evaluationmethods.name=Edo.get('name').text;
		evaluationmethods.creator=Edo.get('sex').text;
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/department/department!saveEmployee.action',{employee:employee});
		var url='http://localhost:8080/gnhzb/department/department!getEmployee.action';
		var param={};
		var id='evaluationmethodsdata';
		refreshdata(evaluationmethodsdataTable,url,param,id);
		evaluationmethods={};
 	}
	    var content=new evaluationmethodsdef();
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,150,'填写评价方法信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	
}
function getModifyEvaluationMethodWin(r){
	var func=function(id){
		evaluationmethods.name=Edo.get('name').text;
		evaluationmethods.creator=Edo.get('sex').text;
		evaluationmethods.id=r.id;
			
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/department/department!saveEmployee.action',{employee:employee});
		var url='http://localhost:8080/gnhzb/department/department!getEmployee.action';
		var param={};
		var id='evaluationmethodsdata';
		refreshdata(evaluationmethodsdataTable,url,param,id);
		evaluationmethods={};
		  
		
 	}
	    var content=new evaluationmethodsdef(r);
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,150,'修改评价方法信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	    
}	
function evaluationmethodsdef(r){
	
	var content = Edo.create(
		    {type: 'box',id:'evaluationmethodswincontent',width: '100%',height:100,border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
	       	    children: [
	       	    //
	       	    {	type : 'formitem',label : '名称:',labelWidth : 150,labelAlign : 'right',
	       	    children : [{type : 'text',width : 200,id : 'name'}]
	       	    },
	       	    {	type : 'formitem',label : '创建者:',labelWidth : 150,labelAlign : 'right',
	       	    children : [{type : 'text',width : 200,id : 'creator'}]
	       	    },
	       	    {	type : 'formitem',label : '影响指标:',labelWidth : 150,labelAlign : 'right',
		       	    children : [{type : 'button',width : 200,text:'制定影响指标',onclick: function(e){
				       	    var content=new seteffectsbox();
				     	    var toolbar=new gettoolbar(null,null);
				      	    var win=cims201.utils.getWin(500,300,'设置影响指标',[content,toolbar]);
				     	    win.show('center', 'middle', true);
		                }}]
	       	    }
	       	    ]
	       	});
	if(r!=null){
		evaluationmethodswincontent.setForm(r);
	}
	       	return content;
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
function seteffectsbox(){
	var effectbox=Edo.create(
			{
				type:'box',
				width: '100%',
				height: '100%',
				padding:[10,0,0,0],
				border:[0,0,0,0],
				verticalGap:0,
				children:[
			          {
						type: 'group',
					    width: 480,
					    layout: 'horizontal',
					    cls: 'e-toolbar',
					    children: [
							        {type: 'button',id:'addbtn1',text: '新增',onclick: function(e){new getNewEvaluationMethodWin()}},
									{type: 'split'},
							        {type: 'button',id:'xgbtn1',text: '修改',onclick: function(e){
							        	var r=emp.getSelected();
							        	alert('是的');
							        	if(r){
							        		new getModifyEvaluationMethodWin(r);
							        	}
							        }},
									{type: 'split'},
							        {type: 'button',id:'delebtn1',text: '删除',onclick: function(e){var r = tb.getSelected();
				                    if(r){tb.data.remove(r);}else{alert("请选择行");}}},
						            {type: 'split'},
							        {type: 'button',id:'rebtn1',text: '刷新'}]
					},
					{
						id: 'effecttable', type: 'table', width: 480, height: 200,
						padding:[0,0,0,0],
					    rowSelectMode : 'single',
					    horizontalScrollPolicy:'off',
					    columns:[{
					            	 headerText: '',
			                         align: 'center',
			                         width: 20,                        
			                         enableSort: false,
			                         enableDragDrop: true,
			                         enableColumnDragDrop: false,
			                         style:  'cursor:move;',
			                         renderer: function(v, r, c, i, data, t){
			                         return i+1;}},
			                         Edo.lists.Table.createMultiColumn(),
			                         {header:'编号',dataIndex: 'id', width: '150',headerAlign: 'center',align: 'center'},
			                         {header:'名称',dataIndex: 'name', width: '150',headerAlign: 'center',align: 'center'},
			                         {header:'操作',dataIndex: 'updatetime', width: '150',headerAlign: 'center',align: 'center',
			                        	 renderer: function(v, r){
						                    	return '<span style="cursor: pointer;" onclick="editeffectmaterial();">查看</span>';
						                    }
			                        	 }
			                         ],
						data:[{id:1}]
					}]
			}
			)
	return effectbox;
	
}
function editeffectmaterial(){
	var editeffectmaterialbox=Edo.create(
			{
				id: 'editeffectmaterialbox', type: 'box', width: '100%', height: '100%',padding:[0,0,0,0],verticalGap:0,
				border:[0,0,0,0],layout: 'vertical',
				children:[
					{
						type: 'box', width: '100%', height: 70,padding:[0,0,0,0],
						border:[0,0,0,0],layout: 'horizontal', /*horizontalAlign: 'left',*/ horizontalGap: 10,
					    /*verticalGap:10,*/
					    /*verticalAlign: 'middle',*/
						children:[
							{
								type: 'box', width: 480, height: '100%',padding:[0,0,0,0],
								border:[0,0,0,0],layout: 'vertical',
								children:[
									{
										type: 'box', width:'100%', padding:[0,0,0,0],
										border:[0,0,0,0],layout: 'horizontal',
										children:[
								          
								          
										{	type : 'formitem',label : '名称:',labelWidth : 80,labelAlign : 'center',
											    children : [{type : 'text',width : 120,id : 'materialname'}]
									    },
									    {	type : 'formitem',label : '类别:',labelWidth : 80,labelAlign : 'center',
									    children : [{type : 'text',width : 120,id : 'category'}]
									    }
									    ]
									},
									{
										type: 'box', width: '100%', padding:[0,0,0,0],
										border:[0,0,0,0],layout: 'horizontal',
										children:[
								          
								          
										{	type : 'formitem',label : '单位',labelWidth : 80,labelAlign : 'center',
											    children : [{type : 'text',width : 120,id : 'unit'}]
									    },
									    {	type : 'formitem',label : '来源:',labelWidth : 80,labelAlign : 'center',
									    children : [{type : 'text',width : 120,id : 'origin'}]
									    }
									    ]
									}]
							},
							{
					            type: 'button',
					            text: '选择基准物质',
					            width: 100,
					            onclick: function(e){
					            if(func==undefined){
					            }else{
					            func(id);
					            }
					            this.parent.parent.parent.destroy();
					            }
					        }
				          ]
					},    
					{
						type: 'group',
					    width: 640,
					    layout: 'horizontal',
					    cls: 'e-toolbar',
					    children: [
							        {type: 'button',id:'addbtn2',text: '新增',onclick: function(e){new getNewEvaluationMethodWin()}},
									{type: 'split'},
							        {type: 'button',id:'xgbtn2',text: '修改',onclick: function(e){
							        	var r=emp.getSelected();
							        	alert('是的');
							        	if(r){
							        		new getModifyEvaluationMethodWin(r);
							        	}
							        }},
									{type: 'split'},
							        {type: 'button',id:'delebtn2',text: '删除',onclick: function(e){var r = tb.getSelected();
				                    if(r){tb.data.remove(r);}else{alert("请选择行");}}}
						          ]
					},
					{
						id: 'effectmaterialtable', type: 'table', width: 640, height: 200,horizontalScrollPolicy:'off',
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
			                         {header:'编号',dataIndex: 'id', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'类别',dataIndex: 'category',width: '100', headerAlign: 'center',align: 'center'},
			                         {header:'单位',dataIndex: 'unit', width: '100',headerAlign: 'center',align: 'center'},
			                         {header:'来源',dataIndex: 'origin',width: '100', headerAlign: 'center',align: 'center'},
			                         {header:'特征化指数',dataIndex: 'updatetime', width: '100',headerAlign: 'center',align: 'center'}
			                         ]
					}
					]
			}
			)
	    var toolbar=new gettoolbar(null,null);
	    var win=cims201.utils.getWin(650,360,'设置指标相关物质',[editeffectmaterialbox,toolbar]);
	    win.show('center', 'middle', true);
	
	
}