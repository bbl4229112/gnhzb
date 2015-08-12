function createCodeClassDefi_check(codeClassId){
	if(!Edo.get('createCodeClassDefi_check_Window')){
		Edo.create({
			type:'window',
			id:'createCodeClassDefi_check_Window',
			title:'产品分类定义审批',
			titlebar:[
	            {
	                cls: 'e-titlebar-close',
	                onclick: function(e){
	                    this.parent.owner.hide();       //hide方法
	                }
	            }
	        ],
	        padding:[0,0,0,0],
	        height:'200',
	        width:'600',
	        children:[{
			    id: 'codeclassdefiTb_check', type: 'table', width: '100%', height: '100%',autoColumns: true,
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
	                Edo.lists.Table.createMultiColumn(),
			    	{ dataIndex: 'id',visible : false},
			        { header: '类型名称', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
			        { header: '类型代号', dataIndex: 'classcode', headerAlign: 'center',align: 'center' },
			        { header: '分类结构', dataIndex: 'flag', headerAlign: 'center',align: 'center',
			          renderer: function(val){
						    var text;
							if (val > 0) {
								text = "已建立分类结构";
								return '<span style="color:green;">' + text + '</span>';
							} else {
								text = "未建立分类结构";
								return '<span style="color:red;">' + text + '</span>';
							}
							return val;
			          }
			        },
			        { header: '码首字段', dataIndex: 'rule', headerAlign: 'center',align: 'center',
			        	renderer:function codeHead(val) {	
							var text = val.split("-");
							return text[0]; 
						}
			        }
			    ]    
	        }]
		});
	}
	var data = cims201.utils.getData('codeclass/code-class!findById.action',{id:codeClassId});
	console.log(data);
	codeclassdefiTb_check.set('data',data);
	createCodeClassDefi_check_Window.show('center', 'middle', true);
}