function createPlatform_check(platformId){
	
	if(!Edo.get("platform_check_window")){
		Edo.create({
			id:'platform_check_window',
			type:'window',
			title:'平台类型审批',
			width:600,
			height:200,
            render: document.body,
            titlebar: [
                {                  
                    cls:'e-titlebar-close',
                    onclick: function(e){
                        this.parent.owner.destroy();
                    } 
                }
            ],
            layout:'vertical',
            verticalGap:0,
            padding:0,
            children:[
        		{
    			    id: 'platform_platTable_check', type: 'table', width: '100%', height: '100%',autoColumns: true,
    			    padding:[0,0,0,0],rowSelectMode : 'single',
    			    columns:[
    			    	{
    					    headerText: '',
    					    align: 'center',
    					    width: 10,                        
    					    enableSort: true,
    					    renderer: function(v, r, c, i, data, t){
    					        return i+1;
    						}
    					},           
    					{dataIndex:'id',visible:false},
    					{dataIndex:'statusId',visible:false},
    			        { header: '平台名称', enableSort: true, dataIndex: 'platName', headerAlign: 'center',align: 'center'},
    			        //{ header: '搭建者', enableSort: true,dataIndex: 'inputUserName', headerAlign: 'center',align: 'center' },
    			        //{ header: '审核者', enableSort: true, dataIndex: 'checkUserName', headerAlign: 'center',align: 'center'},
    			        { header: '状态', enableSort: true, dataIndex: 'statusName', headerAlign: 'center',align: 'center',			        				        },
    			        { header: '平台描述', enableSort: true, dataIndex: 'info', headerAlign: 'center',align: 'center'},
    			        { header: '最后修改时间', enableSort: true, dataIndex: 'beginDate', headerAlign: 'center',align: 'center'}			      
    			    ]    			    
    			}
          ]
			
		});
	}

	platform_platTable_check.set('data',
			cims201.utils.getData('platform/platform-manage!getPlatformById.action',{id:platformId}));
	platform_check_window.show('center','middle',true);
	
	
}