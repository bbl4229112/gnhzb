function createProductMarket(){
	var component = Edo.create({
		type: 'box',width: '100%', height: '100%', border:[0,0,0,0],
		layout:'vertical', verticalGap:0,
		children:[
		          { type:'box', width: '100%', height: '50%' , layout:'horizontal',border:0,		
		        	  children:[
		        	            {   
		        	            	type:'box', width: '50%', height: '100%' , layout:'vertical',	border:0,			 
		        	            	children:[ 
									           { type:'group', width:'100%', layout:'horizontal',
									        	   children:[
									        	             { type:'label', text:'顾客需求',},
									        	        
									        	             { type:'button', text:'增加'},
									        	             { type:'button', text:'删除'},
									        	             { type:'button', text:'修改'},
									        	    ]
									           },
									           { type:'table', width:'100%', height:200,
						        	            	 columns:[
						        	            	          	{header:'产品名称'},
						        	            	          	{header:'高'},
						        	            	          	{header:'市场份额'},					        	            	          
						        	            	  ]
						        	           }
									     ]
		        	            },
		        	            {  
		        	            	type:'box', width: '50%', height: '100%' , verticalGap:0,border:0,		
		        	            	children:[ 
								            {  type:'group', width:'100%',layout:'horizontal',
								        	   children:[
								        	             { type:'label', text:'企业优势'},								        	          
								        	             { type:'button', text:'增加'},
								        	             { type:'button', text:'删除'},
								        	             { type:'button', text:'修改'},
								        	    ]
								            },
								           { type:'table', width:'100%', height:200,
					        	            	 columns:[
					        	            	          	{ header:'优势强弱'},
					        	            	          	{ header:'名称'},
					        	            	          	{ header:'备注'}				        	            	          
					        	            	  ]
					        	           }
								    ]
		        	            }
		        	        ]
		          },
				 {   type:'box', width: '100%', height: '50%',  layout:'vertical',border:0,		
					 children:[
					           { type:'label', text:'分析图' },
					           { type:'text', width:'100%',height:200 },
					   ]
				 },			
		 ]
	});
	this.getComponent = function(){
		return component;
	};
}