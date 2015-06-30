function createSchemeChoose(){
	var component = Edo.create({
		type: 'box', width: '100%', height: '100%', border:[0,0,0,0],
		layout:'vertical', verticalGap:0,
		children:[
		          { type:'panel', title:'原理方案评价表', width:'100%' , height:10 },
		          { 
		        	  type:'table', id:'', width:'100%' ,height:'100%',
		        	  columns: [
		        	            {header:'123'}
		        	           
		        	  ]
		        	  
		          },
		          {
		        	  type:'box',width: '100%',cls: 'e-toolbar',layout:'horizontal',
		        	  children:[
		        	            { type:'button',text:'文档'},
		        	            { type:'button',text:'实例'},
		        	            { type:'button',text:'工具'},
		        	   ]
		          }	   
		]
	});
	
	this.getComponent = function(){
		return component;
	};
}