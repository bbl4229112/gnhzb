function createFunctionTreeCreate(){
	var component =Edo.create({
	    type:'box', layout: 'vertical',verticalGap:0,border:[0,0,0,0],width: '100%', height:'100%',
		children:[
				 {
			      	  type: 'box',	width: '100%',padding: 2, layout: 'horizontal', 
			      	  verticalAlign: 'bottom', cls: 'e-toolbar',               
			      	  children:[	
			      	            		  { type:'label', text:'主功能参数' },
							              { type: 'button',text: '创建' },
							              { type: 'button',text: '修改' },
			          ]
		        },
				{
						id:'', type:'box', width:'100%', height:'100%', 
						children:[
						          { type:'label',text:'产品功能列表' },
						          { 
						        	  type:'table',width:'100%', height:'100%', layout:'center',
						        	  	columns: [
												{ header:  '主功能参数' , dataIndex:  'en_name' , editor: {type:  'text' } },
												{ header:  '单位' , dataIndex:  'site'  },
									    ]
						 }]
			    },			   
	    ]
	});
	
	
	this.getComponent = function(){
		return component;
	};
}