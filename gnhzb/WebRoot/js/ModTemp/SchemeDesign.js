function createSchemeDesign(){
	var component =Edo.create({
		type: 'box', width: '100%', height: '100%', border:[0,0,0,0],
		layout:'horizontal', horizontalGap:0,
		children:[
		          {
						type:'box',width: '50%',height: '100%',cls: 'e-toolbar',
						layout:'vertical',
						children:[    					          		  
								          {   
								        	   type:'group',layout:'horizontal',width:'100%',
								        	   children:[
										          		{ type:'label', text:'零件种类:' },
										          		{ type:'button', text:'删除'},
										          		{ type:'button', text:'添加'},
								          		]
								          },
						          		  {
							          			type:'table',id:'tb',width:'100%',height:'100%',
							          			 columns:[
							          			         { header:'方案名称', },
							          			         { header:'实现功能', },
							          			         { header:'备注', }	
							          			 ]					          			
						          		  }
						]
		          },
		          { 
		        	  type:'box', width: '50%', height: '100%' ,
		        	  children:[
										{
											type: 'formitem',									
											label: '功能查询:',
											labelWidth: 60,
											/*layoutAlign:'center',*/
											style: 'background:#ccc',
											render: document.body,
											children: [
											    {
											        type: 'text'
											    }
											]
										}
		        	   ]	        	  
		          }
		  ]
	});
	
	
	this.getComponent = function(){
		return component;
	};
}