function createIndustryProductAnalyse(){
	var component = Edo.create({   type:'box', width: '100%', height: '100%' , 
		 children:[
		           {
		        	   type:'box', width: '100%', height: '100%' , layout:'vertical',border:0,
		        	   children:[
		        	             {
		        	            	 type:'label', text:'产品生命周期阶段' 
		        	             },
		        	             { type:'text', width:'100%', height:150 }
		        	    ]
		           },
		           {
		        	   type:'box', width: '100%', height: '100%' , layout:'horizontal', border:0,
		        	   children:[
		        	             {
		        	            	 type:'box', width: '50%', height: '100%' , layout:'vertical',
		        	                 children:[         
					        	             {
					        	            	 type:'label', text:'主要产品分布图' 
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
		        	            	 type:'box', width: '50%', height: '100%' , layout:'vertical', 
		        	                 children:[         
					        	             {
					        	            	 type:'label', text:'主要竞争者' 
					        	             },
					        	             { type:'table', width:'100%', height:200,
					        	            	 columns:[
					        	            	          	{header:'公司名称'},
					        	            	          	{header:'产品'},
					        	            	          	{header:'说明'}				        	            	          
					        	            	  ]
					        	             }
					        	       ]
		        	             }
		        	    ]
		           },
		           {
		        	   type:'box', width: '100%', height: '100%' , layout:'horizontal',border:0,
		        	   children:[
		        	             {
		        	            	 type:'box', width: '50%', height: '100%' , layout:'vertical',
		        	                 children:[         
					        	             { type:'label', text:'数据分析' },
					        	             { type:'text', width:'50%', height:200,	}
					        	        ]
		        	             },
		        	             {
		        	            	 type:'box', width: '50%', height: '100%' , layout:'vertical', padding:[0,0,0,0],
		        	                 children:[         
					        	             { type:'label', text:'数据分析' },
					        	             { type:'text', width:'100%', height:200, }
					        	     ]
		        	             }
		        	    ]
		           },					           					           
		  ]
	 });
	
	this.getComponent = function(){
		return component;
	};
}