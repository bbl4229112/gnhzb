function createTemplatemanage(){
	var toolbar =Edo.create({
		    type: 'group',
		    width: '100%',
		    layout: 'horizontal',
		    cls: 'e-toolbar',
		    children: [
		        {type: 'button',id:'',text: '新建默认需求'},
				{type: 'split'},
		        {type: 'button',id:'',text: '删除默认需求'},
				{type: 'split'},
		        {type: 'button',id:'',text: '修改默认需求'}		        
		    ]

	});
	var table = Edo.create({
	    id: '', type: 'table', width: '100%', height: '100%',autoColumns: true,
	    padding:[0,0,0,0],rowSelectMode : 'single',
	    columns:[
	    	{
                headerText: '',
                align: 'center',
                width: 10,                        
                enableSort: true,
                enableDragDrop: true,
                enableColumnDragDrop: false,
                style:  'cursor:move;',
                renderer: function(v, r, c, i, data, t){
                    return i+1;
            	}
            },
	    	{ dataIndex: 'id',visible : false},
	        { header: '默认需求名', enableSort: true, dataIndex: 'classname', headerAlign: 'left',align: 'center'},
	        { header: '默认需求描述', enableSort: true,width:200, dataIndex: 'classcode', headerAlign: 'left',align: 'center' },
	        { header: '默认需求状态', enableSort: true, dataIndex: 'flag', headerAlign: 'center',align: 'center'},			        
	    ]    
	});
	this.getToolBar = function(){
		return toolbar;
	};
	this.getTable = function(){
		return table;
	};
	
}