function createVariantDesignBuidl_check(variantTaskId){
	if(!Edo.get('VarinantDesignBuild_check_window')){
		Edo.create({
			id:'VarinantDesignBuild_check_window',
			type:'window',
			title:'变形设计任务审批',
			layout:'vertical',
			verticalGap:0,
			width:'100%',
			height:'100%',
			padding:[0,0,0,0],
			width:'500',
			height:'200',
			titlebar:[
	            {
	                cls: 'e-titlebar-close',
	                onclick: function(e){
	                    this.parent.owner.hide();       //hide方法
	                }
	            }
	        ],
			children:[
		          {
		        	  type:'box',width:'100%',height:'32',cls:'e-toolbar',layout:'horizontal',
		        	  children:[
		    	            {type:'button',id:'VariantDesign_LookupTask_check',text:'查看任务详情'},
		    	            {type:'space',width:'100%'}
		    	       ]
		          },{
		  			id:'VariantDesign_TaskTable_check',type:'table',width:'100%',height:'100%',autoColumns: true,
		  			columns:[
		  			     {dataIndex:'id',visible:false},
		  			     {dataIndex:'partId',visible:false},
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
		  		         { header: '任务名称', dataIndex: 'taskName', headerAlign: 'center',align: 'center'},
		  		         { header: '开始时间', dataIndex: 'startDate', headerAlign: 'center',align: 'center'},
		  		         { header: '结束时间', dataIndex: 'endDate', headerAlign: 'center',align: 'center'},
		  		         //{ header: '状态', dataIndex: 'status', headerAlign: 'center',align: 'center'},
		  		         { header: '备注', dataIndex: 'demo', headerAlign: 'center',align: 'center'},
		  			 ]
		          }
			]
			
		});
	}

	
	VariantDesign_TaskTable_check.set('data',cims201.utils.getData('sml/variant!getVariantTaskById.action',{id:variantTaskId}));
	
	VariantDesign_LookupTask_check.on('click',function(){
		var form  = showVariantTaskForm();

		if(VariantDesign_TaskTable_check.selected != null){
			form.setForm(VariantDesign_TaskTable_check.selected);
		}else{
			form.setForm(VariantDesign_TaskTable_check.data.source[0]);
		}
		
	});
	
	function showVariantTaskForm(){
		if(!Edo.get('VariantDesign_VariantTaskForm_check')){
			Edo.create({
				id:'VariantDesign_VariantTaskForm_check',
				title:'变型任务详情',
				type:'window',
				width:'350',
				render:document.body,
				titlebar:{
					cls:'e-titlebar-close',
					onclick:function(e){
						this.parent.owner.destroy();
					}
				},
				children:[
					{
					    type: 'formitem',padding:[20,0,10,0],labelWidth :'75',label: '任务名称：',
					    children:[{type: 'text',width:'250',name: 'taskName',readOnly:true}]
					},
					{
					    type: 'formitem',padding:[0,0,10,0],labelWidth :'75',label: '实例编码：',
					    children:[{type: 'text',width:'250',name: 'partNumber',readOnly:true}]
					},
					{
					    type: 'formitem',padding:[0,0,10,0],labelWidth :'75',label: '实例名称：',
					    children:[{type: 'text',width:'250',name: 'partName',readOnly:true}]
					},
					{
					    type: 'formitem',padding:[0,0,10,0],labelWidth :'75',label: '变型要求：',
					    children:[{type: 'textarea',width:'250',height:'100',name: 'requirement',readOnly:true}]
					},
					{
					    type: 'formitem',padding:[0,0,10,0],labelWidth :'75',label: '备注：',
					    children:[{type: 'textarea',width:'250',name: 'demo',readOnly:true}]
					},
					{
	                    type: 'formitem',layout:'horizontal', padding: [20,0,10, 0],
	                    children:[
	                        {type: 'space', width:60},
	                        {type:'button', text:'关闭',
	                    		onclick:function(){
	                    			VariantDesign_VariantTaskForm_check.destroy();
	                    		}
	                    	}
	                    ]
	                }
				]
			});
		}
		VariantDesign_VariantTaskForm_check.show('center','middle',true);
		return VariantDesign_VariantTaskForm_check;
	}
	VarinantDesignBuild_check_window.show('center','middle',true);
}