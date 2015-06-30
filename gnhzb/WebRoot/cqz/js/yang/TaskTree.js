var taskTreeNode={};
//设置数据源
var roledataTable = new Edo.data.DataTable()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
        }
    ]
});

var url='http://localhost:8080/gnhzb/department/department!getOperationRoles.action';
var param={};
var id='role';
refreshdata(roledataTable,url,param,id);

var tasktreeTable = new Edo.data.DataTree()
.set({
    fields: [
        {name: 'id', mapping: 'id', type: 'string'
           
        },
        {name: 'name', mapping: 'name',  type: 'string'
        }
    ]
});

Edo.build({
	type: 'app',
	width: '100%',
	height: '100%',
	border:[0,0,0,0],
	padding:[0,0,0,0],
	render: document.body,
	layout: 'horizontal',
	children:[
		{
		type:'box',
		height: '100%',
		layout:'vertical',
		verticalGap:'0',
		padding:[0,0,0,0],
		width:'20%', 
		children:[	
	        {		
			id: 'role', type: 'table', width: '100%', height: '100%',autoColumns: true,
			padding:[0,0,0,0],
			rowSelectMode : 'single',
			columns:[{
				 headerText: '',
				 align: 'center',
				 width: '100%',                        
				 enableSort: false,
				 enableDragDrop: true,
				 enableColumnDragDrop: false,
				 style:  'cursor:move;',
				 renderer: function(v, r, c, i, data, t){
				 return i+1;}},
				 {header:'权限名称',dataIndex: 'name', width: 150,headerAlign: 'center',align: 'center'}
				 ],
             data:roledataTable,
             onselectionchange: function(e){	
		        	new roleSelect();
		         }
			}]},
	       	
	
	    {
   		type: 'box',
   		width: '30%',
        height: '100%',
        layout: 'vertical',
        verticalGap:'0',
		padding:[0,0,0,0],
        children:[
          	{
    			type: 'group',
    		    layout: 'horizontal',
    		    cls: 'e-toolbar',
    		    width:'100%',
    		    children: [
    				        {type: 'button',text: '新增父节点',onclick: function(e){new getNewParentWin()}},
    				        {type: 'button',text: '新增子节点',onclick: function(e){
    				        	var r=tasktree.getSelected();
    				        	if(r){
    				        		new getNewChildWin();
    				        		}else{
    				        			alert('请选择父节点！');
    				        		}}},
    						{type: 'split'},
    				        {type: 'button',id:'xgbtn',text: '修改',onclick: function(e){
    				        	var r=tasktree.getSelected();
    				        	if(r){
    				        		new getModifyNodeWin(r);
    				        	}
    				        	}},
    						{type: 'split'},
    				        {type: 'button',id:'delebtn',text: '删除',onclick: function(e){
    				        	var r = tasktree.getSelecteds();
    				        	if(r.length>0){
    				        		var data= cims201.utils.getData('http://localhost:8080/gnhzb/tasktree/tasktree!deleteTaskTreeNodes.action',{deletenodes:r});
    				        		new roleSelect();
    				        	}else{
    			        			alert("请选择模块");
    		        			}}},
    			            {type: 'split'}
    				      ]
			},      
	   		{
   			id: 'tasktree',
   			type: 'tree',
			width: '100%',
	        height: '100%',
	        headerVisible: false,
	        autoColumns: true,
	        horizontalLine: false,
			padding:[0,0,0,0],
		    rowSelectMode : 'single',
		    columns:[
                 {header:'名称',dataIndex: 'name', width: '100',headerAlign: 'center',align: 'center'
                	/* renderer: function(v, r){
                         return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '+(r.checked ? 'e-table-checked' : '')+'"></div>'+v+'</div>';
                     }*/
                 }
                 
                 ],
             data:tasktreeTable
			
			 
	   		}]
	}]
});
/*tasktree.on('bodymousedown', function(e){
    var r = this.getSelected();
       
    if(r){
        var inCheckIcon = Edo.util.Dom.hasClass(e.target, 'e-tree-check-icon');
        var hasChildren = r.children && r.children.length > 0;
        if(inCheckIcon && r.checked){
            setTreeSelect(r, false, true);
        }else{
            setTreeSelect(r, true, true);
        }
    }
});
function setTreeSelect(sels, checked, deepSelect){//deepSelect:是否深度跟随选择
    //多选
    if(!Edo.isArray(sels)) sels = [sels];
    tasktree.data.beginChange();
    for(var i=0,l=sels.length; i<l; i++){
        var r = sels[i];        
        var cs = r.children;        
        if(deepSelect){
            tasktree.data.iterateChildren(r, function(o){
                this.data.update(o, 'checked', checked);
            },tasktree);
        }
        tasktree.data.update(r, 'checked', checked);
    }
    tasktree.data.endChange();
}*/
function refreshdata(dataTable,url,param,id){
    var data= cims201.utils.getData(url,param);
	dataTable.set('data',data);
}
function roleSelect()
{
   var r=role.getSelected();
   if(r!=null){
   var data= cims201.utils.getData('http://localhost:8080/gnhzb/tasktree/tasktree!getTaskTreebyRole.action',{role:r.id});
   tasktreeTable.set('data',data);
   
   }
}

function getNewParentWin(){
	var func=function(id){
		taskTreeNode.name=Edo.get('name').text;
		taskTreeNode.des=Edo.get('des').text;
		taskTreeNode.url=Edo.get('url').text;
		taskTreeNode.code=Edo.get('code').text;
		taskTreeNode.orderid=Edo.get('orderid').text;
		var r=role.getSelected();
		taskTreeNode.role=r.id;
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/tasktree/tasktree!saveTaskTreeNode.action',{taskTreeNode:taskTreeNode});
	    new roleSelect();
	    taskTreeNode={};
		  
		
 	}
	    var content=new departNodedef(true,null);
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'填写根节点信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	
}
function getNewChildWin(){
	var func=function(id){
		taskTreeNode.name=Edo.get('name').text;
		taskTreeNode.des=Edo.get('des').text;
		taskTreeNode.url=Edo.get('url').text;
		taskTreeNode.code=Edo.get('code').text;
		taskTreeNode.orderid=Edo.get('orderid').text;
		var r=tasktree.getSelected();
		taskTreeNode.parent=r.id;
		var r2=role.getSelected();
		taskTreeNode.role=r2.id;
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/tasktree/tasktree!saveTaskTreeNode.action',{taskTreeNode:taskTreeNode});
	    new roleSelect();
	    taskTreeNode={};
		  
		
 	}
	    var content=new departNodedef(false,null);
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'填写子节点信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	
}
function getModifyNodeWin(r){
	var func=function(id){
		taskTreeNode.name=Edo.get('name').text;
		taskTreeNode.des=Edo.get('des').text;
		taskTreeNode.url=Edo.get('url').text;
		taskTreeNode.code=Edo.get('code').text;
		taskTreeNode.id=r.id;
		var data= cims201.utils.getData('http://localhost:8080/gnhzb/tasktree/tasktree!saveTaskTreeNode.action',{taskTreeNode:taskTreeNode});
		new roleSelect();
		taskTreeNode={};
		  
		
 	}
	    var content=new departNodedef(false,r);
	   // Edo.get('name').set('text',r.name);
	    var toolbar=new gettoolbar(null,func);
 	    var win=cims201.utils.getWin(400,320,'修改部门信息',[content,toolbar]);
	    win.show('center', 'middle', true);
	    
	
}

function departNodedef(isParent,r){
	var content = Edo.create(
	    {type: 'ct',id:'nodewincontent',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical', autoChange: true,
       	    children: [
       	    //				           
       	    {	type : 'formitem',label : '模块名称:',labelWidth : 150,labelAlign : 'right',
       	    children : [{type : 'text',id:'name'}]
       	    },
       	    {	type : 'formitem',label : '执行url:',labelWidth : 150,labelAlign : 'right',
           	    children : [{type : 'text',id:'url'}]
       	    },
       	    {	type : 'formitem',label : '编码:',labelWidth : 150,labelAlign : 'right',
           	    children : [{type : 'text',id:'code'}]
       	    },
       	    {	type : 'formitem',label : '执行序号:',labelWidth : 150,labelAlign : 'right',
           	    children : [{type : 'text',id:'orderid'}]
       	    },
       	    {	type : 'formitem',label : '模块说明:',labelWidth : 150,labelAlign : 'right',
           	    children : [{type : 'text',id:'des'}]
       	    }
       	    
       	    
       	    ]
	       	});
	if(r!=null){
		nodewincontent.setForm(r);
	}else{
		if(!isParent){
			var r=tasktree.getSelected();
			var parent=Edo.create(
					{	type : 'formitem',label : '父节点:',labelWidth : 150,labelAlign : 'right',
           	    children : [{type : 'text',id:'parent',text:r.name}]
       	    });
			content.addChild(parent);
		}
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