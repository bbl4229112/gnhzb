function createOrderview(){
	var table = Edo.create({
		    id: '', type: 'table', width: '100%', height: '100%',autoColumns: true,
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
	            	}
	            },
		    	{ dataIndex: 'id',visible : false},
		        { header: '配置需求号', dataIndex: '', headerAlign: 'center',align: 'center'},
		        { header: '描述', dataIndex: '', headerAlign: 'center',align: 'center' },
		        { header: '开始日期', dataIndex: '', headerAlign: 'center',align: 'center'},
		        { header: '录入人', dataIndex: '', headerAlign: 'center',align: 'center'},
		        { header: '审核人', dataIndex: '', headerAlign: 'center',align: 'center'},
		        { header: '状态', dataIndex: '', headerAlign: 'center',align: 'center'}
		    ]    
	});
	
	this.getTable = function(){
		return table;
	};
}