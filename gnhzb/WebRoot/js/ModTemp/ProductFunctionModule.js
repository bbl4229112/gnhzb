function createProductFunctionModule(){
	var component = Edo.create({
		type:'box',  layout: 'vertical',verticalGap:0,width:'100%',height:'100%',border:[0,0,0,0],
		children:[
		          {  type:'box',width:'100%',height:'70%', layout:'vertical',
		        	 children:[
		        	           {   type:'label', text:'模块创建' },
		        	           {   type:'table', width:'100%',height:'70%',
		        	        	   columns:[
		        	        	            { header:'序号'},
		        	        	            { header:'模块名称'},
		        	        	            { header:'实现功能'},
		        	        	            { header:'备注'}
		        	        	   ]
		        	           }
		        	  ]		        	  
		          },
		          {  type:'box',width:'100%',height:'30%',
		        	  children:[
		        	            {  type:'button', text:'实例' }
		        	  ]
		          }
		]	
});
	
	this.getComponent = function(){
		return component;
	};
}