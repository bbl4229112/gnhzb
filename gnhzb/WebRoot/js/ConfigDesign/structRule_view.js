function createStructRule_view(){
	var structPanel = Edo.create({
		 type: 'ct',
        width: '220',
        height: '100%',
        collapseProperty: 'width',
        enableCollapse: true,
        splitRegion: 'west',
        splitPlace: 'after',
        children: [
           {
				type: 'panel', id:'', title:'主结构', padding: [0,0,0,0],
				width:'100%', height:'100%',
				titlebar:[
					{
				    	cls:'e-titlebar-toggle-west',
				        icon: 'button',
				        onclick: function(e){
				            this.parent.owner.parent.toggle();
				        }
					}
				],
				children:[
				      {
				    	  type:'group',
				    	  width:'100%',
				    	  cls:'e-toolbar',
				    	  children:[
							{	type:'combo', id:'',
								width:'150',
								readOnly : true,
								valueField: 'id',
							    displayField: 'text',			    
							    onselectionchange: function(e){}
							}       
				          ]
				      }
				]     
           }
        ]	
	});
	
	var structRulePanel = Edo.create({
		type: 'panel', id:'', title:'<h3><font color="blue">配置规则处理</font></h3>', padding: [0,0,0,0],
		width:'100%', height:'100%', verticalGap: 0,
		children:[
				{
	       		    type: 'group',
	       		    width: '100%',
				    layout: 'horizontal',
				    cls: 'e-toolbar',
				    children: [
				        {type: 'button',id:'',text: '接口查看'},
				        {type: 'split'},
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
				        { header: '零件名称', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},
				        { header: '零件编码', enableSort: true,dataIndex: '', headerAlign: 'center',align: 'center' },
				        { header: '必选项', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},
				        { header: '可选项', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},
				        { header: '排除项', enableSort: true, dataIndex: '', headerAlign: 'center',align: 'center'},				        			      
				    ] 
	        	}
	   ]
	});
	this.getStructPanel = function(){
		return structPanel;
	};
	
	this.getStructRulePanel = function(){
		return structRulePanel;
	};
}