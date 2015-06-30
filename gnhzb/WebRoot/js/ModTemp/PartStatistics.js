function createPartStatistics(){
	var component = Edo.create({
				type:'box', width: '100%', height: '100%', verticalGap:'0', padding:[0,0,0,0], layout:'vertical',
		children:[
		           {
							type: 'group',
						    width: '100%',
						    layout: 'horizontal',
						    cls: 'e-toolbar',
						    children: [
								        {type: 'button',id:'addbtn',text: '新增',/*onclick: function(e){new getNewEmployeeWin()}*/},						
							]	/*{
							id: 'emp1', type: 'table', width: '100%', height: '100%',autoColumns: true,
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
						}]*/
		           	},
		            {
						id:'', type:'table', width:'100%', height:'50%',
						columns: [
						          { header:'  ' },
						          { header:'序号',enableSort:  true,  headerAlign: 'center' , },
						          { header:'新零件名称',enableSort:  true,headerAlign: 'center' ,},
						          { header:'旧零部件名称',enableSort:  true,headerAlign: 'center' ,},
						          { header:'编码',enableSort:  true,headerAlign: 'center' ,},	
						          { header:'类型',enableSort:  true,headerAlign: 'center' ,},
						          { header:'使用频率',enableSort:  true,headerAlign: 'center' ,},
						          { header:'备注',enableSort:  true,headerAlign: 'center' ,},
						]
				    },
				    {
			        	  type:'box',width: '100%',height: '50%'
			         },
				    {   type:'box',width:'100%',
				    	children:[{type:'button',text:'实例'}]				    	
				    }
		]
	});
	
	
	this.getComponent = function(){
		return component;
	};
}