Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	render: document.body,
	children:[{
		type:'box',
		width: '100%',
		height: '50%',
		children:[{
			type: 'group',
		    width: '100%',
		    layout: 'horizontal',
		    cls: 'e-toolbar',
		    children: [
				        {type: 'button',id:'addbtn',text: '新增',},
						{type: 'split'},
				        {type: 'button',id:'xgbtn',text: '修改'},
						{type: 'split'},
				        {type: 'button',id:'delebtn',text: '删除'},
			            {type: 'split'},
				        {type: 'button',id:'rebtn',text: '刷新'},]},
		{
				        	id: 'tb', type: 'table', width: '100%', height: '100%',autoColumns: true,
							padding:[0,0,0,0],
						    rowSelectMode : 'single',
						    columns:[
{
    headerText: '',
    align: 'center',
    width: 10,                        
    enableSort: false,
    enableDragDrop: true,
    enableColumnDragDrop: false,
    style:  'cursor:move;',
    renderer: function(v, r, c, i, data, t){
        return i+1;
	}},
			                    	Edo.lists.Table.createMultiColumn(),
			                    	{header:'用户序号',dataIndex: 'userid', width: '100',headerAlign: 'center',align: 'center'},
						             {header:'用户姓名',dataIndex: 'username', width: '100',headerAlign: 'center',align: 'center'},
						             
						             {header:'邮箱',dataIndex: 'userEmail',width: '200', headerAlign: 'center',align: 'center'},
						             //{header:'用户熟悉领域',dataIndex: 'userfiled', width: '200',headerAlign: 'center',align: 'center'},
						             //{header:'用户兴趣领域',dataIndex: 'userinterest', width: '200',headerAlign: 'center',align: 'center'},
						             {type: 'space', width:400},]		        	
		}]
	}]
});
addbtn.on('click', function(e){
    var form = showAddForm();
    form.reset();});
function showAddForm(){
    if(!Edo.get('addForm')) {
        //创建用户面板
        Edo.create({
            id: 'addForm',            
            type: 'window',title: '添加用户',
            render: document.body,
            width:'300',
            height: '500',
            titlebar: [
                {
                    cls: 'e-titlebar-close',
                    onclick: function(e){
                    this.parent.owner.hide();
                    }
                }
            ],
            children: [{
            	id:'userForm',
            	type:'box',
            	width: '100%',
            	height: '100%',	
            	children: [
            		{type: 'formitem',padding:[0,0,10,0],label:'用户序号:',labelAlign:'right',
            		children:[{type: 'text', width:'100',name: 'userid',valid: classnameRegex}]},
            		{type: 'formitem',padding:[0,0,10,0],label:'用户姓名:',labelAlign:'right',
                	children:[{type: 'text', width:'100',name: 'userid',valid: noEmpty}]},
                	{
                        type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
                        children:[
                            {id: 'submitBtn', type: 'button', text: '提交表单',onclick:function(){addForm.hide();}},
                            {type: 'space', width: 5},
                            {id: 'reset',type: 'button',text: '重置',onclick: function(e){resetForm()}}
                        ]
                    }
            	]
            }]
        });
    }
    addForm.show('center', 'middle', true);
    return addForm;
}
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
age.on('valuechange', function(e){
    agetext.set('text', e.value);
});