function createNewtemplate (){
	var demandList = Edo.create({
			type:'panel',id:'',title:'需求列表',layout:'vertical',verticalGap:0,width:'35%',height:'100%',padding:[0,0,0,0],
			children:[{
	    	   id:'',type:'table',width: '100%',height: '100%',autoColumns: true,showHeader: true,
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
	    	        {header:'id',dataIndex:'id',visible:false},
		            {header:'需求名称',dataIndex:'',enableSort:true,headerAlign: 'center',align:'center'},
		            {header:'需求备注',dataIndex:'',enableSort:true,headerAlign: 'center',align:'center'},    	           
		        ]
		    }]
	});
	var defaultDemandValueList =Edo.create({
			type:'panel',title:'默认需求值列表',layout:'vertical',verticalGap:0,width:'65%',height:'100%',padding:[0,0,0,0],
			children:[
		       {
		    	   type:'box',width:'100%',height:'32',cls:'e-toolbar',layout:'horizontal',
		    	   children:[
		             {type:'combo',width:'200',id:'',text:' '},
		             {type:'split'},
		             {type:'button',id:'',text:'删除'},
		             {type:'split'},
		             {type:'button',id:'',text:'修改'},
		             {type:'split'},
		             {type:'button',id:'',text:'清空'}
		    	   ]
		       },
		      	{
		    	   id:'',type:'table',width: '100%',height: '100%',autoColumns: true,showHeader: true,
		    	   columns:[
		    	        {header:'id',dataIndex:'id',visible:false},
			            {header:'需求名',dataIndex:'typeName',enableSort:true,headerAlign: 'center',align:'center'},
			            {header:'需求值',dataIndex:'typeCode',enableSort:true,headerAlign: 'center',align:'center'},
		            	{header:'需求值备注',dataIndex:'typeSuffix',enableSort:true,headerAlign: 'center',align:'center'},    	           
			    	]
		   		},{
		   			type:'box',width:'100%',height:'32',cls:'e-toolbar',layout:'horizontal',
		    	   children:[
		             {type:'button',id:'',text:'完成录入'},
		             {type:'split'},
		             {type:'button',id:'',text:'刷新'}
		    	   ]
		   		}
			]   
	});
	
	
	this.getDemandList = function(){
		return demandList;
	};
	this.getDefaultDemandValueList = function(){
		return defaultDemandValueList;
	};
}