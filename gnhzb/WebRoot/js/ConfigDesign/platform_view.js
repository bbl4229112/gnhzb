function createPlatform_view(){
	var panel =Edo.create({
			type: 'panel', id:'', title:'<h3><font color="blue">平台类型</font></h3>', padding: [0,0,0,0],
			width:'100%', height:'100%', verticalGap: 0,
		    children:[
				{
	    		    type: 'group',
	    		    width: '100%',
				    layout: 'horizontal',
				    cls: 'e-toolbar',
				    children: [
				        {type: 'button',id:'',text: '刷新'}	        
				    ]
	        	},
	      		{
				    id: '', type: 'table', width: '100%', height: '100%',autoColumns: true,
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
				        { header: '平台名称', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},
				        { header: '搭建者', enableSort: true,dataIndex: '', headerAlign: 'center',align: 'center' },
				        { header: '审核者', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},
				        { header: '状态', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center',			        				        },
				        { header: '平台描述', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},
				        { header: '最后修改时间', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'}			      
				    ]    			    
				}
	      ]
	});
	
	this.getPanel =function(){
		return panel;
	};
}