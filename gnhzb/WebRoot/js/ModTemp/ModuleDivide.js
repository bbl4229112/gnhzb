function createModuleDivide(){
	var component = Edo.create({
		type:'box', layout: 'horizontal',verticalGap:0,width:'100%', height:'100%',border:[0,0,0,0],
		children:[
		          {   type:'box',width:'50%',height:'100%',	        	  
		        	  children:[	        	  	         
							{	type: 'label',text: '模块划分原则:' },
							{	type: 'combo', id: '',
								width: '150', displayField: 'label', valueField: 'id',
								data :[
								            { label:'可重用原则' },
								            { label:'基础件模块化原则' },
								            { label:'相对独立原则' },
								            { label:'模块聚类原则' },
								            { label:'产品整体最优原则' },
								            { label:'可扩展原则' },
								            { label:'可维护原则' }
								],					
						}]
		          },
		          {
		        	  type: 'panel', title:'模块列表', width: '100%', height:'100%',
		        	  verticalGap:0,
		        	  children:[
									{
										id:'table', type:'table', width:'100%', height:'100%', 
										columns: [
										          { header:'序号', enableSort:  true,  headerAlign: 'center' , },
										          { header:'模块名称', enableSort:  true, headerAlign: 'center' ,},
										          { header:'实现功能', enableSort:  true, headerAlign: 'center' ,},								          					         
										]
									},
		        	   ]	        	  	        	  
		          }
		        
		 ]
	});
	
	
	this.getComponent = function(){
		return component;
	};
}