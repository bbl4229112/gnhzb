//创建行为列表
function createBehaviorList(){
	var currentTarget;
	var currentId;
	
	var myBehaviorData = cims201.utils.getData('repoint!list.action');
	var myBehaviorList = Edo.create({
		type: 'table',
        width: '100%',
        height: '100%',
        editAction: 'doubleclick',
        columns: [    
            {
                headerText: '贡献度',
                dataIndex: 'behaviorcname',
                headerAlign: 'center',
                align: 'center',
                width: 100
            },          
            {
                headerText: '行为积分',
                dataIndex: 'behaviorpoint',
                headerAlign: 'center',
                align: 'center',
                width: 100,
                editor: {
                    type: 'combo',
                    valueField: 'value',
				    displayField: 'label',
				    data: [
                        {label: '-2', value: -2},   
				        {label: '-1', value: -1},
				        {label: '0', value: 0},
				        {label: '1', value: 1},
				        {label: '2', value: 2},
				        {label: '3', value: 3},
				        {label: '4', value: 4},
				        {label: '5', value: 5},
				        {label: '6', value: 6},
				        {label: '7', value: 7},
				        {label: '8', value: 8}
				    ],	  
				    selectedIndex: 3
                }
            },{
                headerText: '是否贡献行为',
                dataIndex: 'contributionBehavior',
                headerAlign: 'center',
                align: 'center',
                width: 100,
                renderer: function(v){
			    	if(v == true || v == 'true'|| v == 1){
			    		return '是';
			    	}else{
			    		return '否';
			    	}
			    },
                editor: {
                    type: 'combo',
                    valueField: 'value',
				    displayField: 'label',
				    data: [
				        {label: '是', value: true},
				        {label: '否', value: false}
				    ],	  
				    selectedIndex: 0
                }
            }
        ],
        data: myBehaviorData,
        //onbeforecelledit: function(e){
	    //	currentTarget = e.column.dataIndex;
	    // 	currentId = e.record.id;
		//},
		onaftercelledit: function(e){
			cims201.utils.getData('repoint!update.action',e.record);			
		} 		
	});
	
	var outPanel = Edo.create({
		type: 'box',
		width: '100%',
		height: '100%',
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout: 'vertical',
		children: [
			cims201.utils.createBtPanel(['重新计算'],[function(){
				cims201.utils.warn(null,null,'您确定重新计算？',null,function(e){
					if(e == 'yes'){
						cims201.utils.getData('repoint!repoint.action');
						alert('计算成功!');
					}
				});	
			}]),
			myBehaviorList
		]
	});
	
	this.getList = function(){
		return outPanel;
	}
}