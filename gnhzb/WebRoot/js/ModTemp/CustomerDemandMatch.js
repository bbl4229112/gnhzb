function createCustomerDemandMatch(){
	var component = Edo.create({
		type: 'box',width: '100%', height: '100%', border:[0,0,0,0],
		layout:'vertical', verticalGap:0,
		children:[{ type:'box', width: '100%', height: '50%' , layout:'horizontal',border:0,		
      	  children:[
    	            {   
    	            	type:'box', width: '50%', height: '100%' , layout:'vertical',verticalGap:0,	border:0,			 
    	            	children:[ 									          
						           { type:'label', text:'已有产品'},									        	        									        	  
						           { type:'table', width:'100%', height:200,
			        	            	 columns:[
			        	            	          	{header:'编号'},
			        	            	          	{header:'产品名称'},
			        	            	          	{header:'备注'},					        	            	          
			        	            	  ]
			        	           }
						     ]
    	            },
    	            {  
    	            	type:'box', width: '50%', height: '100%' , verticalGap:0,border:0,		
    	            	children:[ 								              
					           { type:'label', text:'已有市场' },								        	          								        	            								        	    								            
					           { type:'table', width:'100%', height:200,
		        	            	 columns:[
		        	            	          	{ header:'编号'},
		        	            	          	{ header:'名称'},
		        	            	          	{ header:'备注'}				        	            	          
		        	            	  ]
		        	           }
					    ]
    	            }
    	        ]
      },
      {   type:'box', width: '100%', height: '50%' , layout:'horizontal',border:0,		
    	  children:[
    	            {    type:'box', width: '100%', height: '100%',  layout:'vertical',border:0,		
       					 children:[
       					           { type:'label', text:'分析' },
       					           { type:'text', width:'100%',height:'100%' },
       					   ]
   				   },			
    	            {  
    	            	type:'box', width: '100%', height: '100%' , verticalGap:0,border:0,		
    	            	children:[ 								           								        
					        	           { type:'label', text:'新市场' },								        	          							        	            								        	  
								           { type:'table', width:'100%', height:'100%',
				        	            	 columns:[
				        	            	          	{ header:'编号'},
				        	            	          	{ header:'名称'},
				        	            	          	{ header:'备注'}				        	            	          
				        	            	  ]
					        	           }
					    ]
    	            }
    	  ]
      }]
	});
	this.getComponent = function(){
		return component;
	};
}