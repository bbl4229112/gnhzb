/**
按领域或分类树进行知识导航
*/
function createTreeKlist(treetype,nodeid){
		var filterbox=null;
		var myTable1;
		var myfilter1;
		var myfilter2;
		var myfilter3;
		var myfilter4;
		
 
		        var queryForm = {name:treetype,value:nodeid};
		       // var queryForm = {name:"domainnodeid",value:nodeid};
				var searchlist = {searchlist: [queryForm]};
			 	var queryFormStr = Edo.util.Json.encode(searchlist);
			 	
			 	//myTableCt.removeChildAt(1); 
		        myTable1 = new createCDKnowledgeList_table11('/caltks/knowledge/knowledge!qsearch.action',{formvalue:queryFormStr}, [], []);
		        var myyy = myTable1.getKnowledgeList().getTable();	
		        //myTableCt.addChild(myTable1.getKnowledgeList().getTable());
		        
		        
		        //过滤1
	            myfilter1 = Edo.create({
                type: 'textinput',
                width:'40%',	              
                ontextblur: function(e){
                alert(11111);
                	
                		//myTable1.getKnowledgeList().getTableData().each(function(o){alert("ddd"+o.commentRecord.commentCount);});
                		myTable1.getKnowledgeList().getMyTable().data.filter(function(o, i){
                			alert("o.titleName"+o.titleName);
                			alert("myfilter1.getValue()"+myfilter1.getValue());
                		
                		
                       		 if(o.titleName.indexOf(myfilter1.getValue())!=-1) return true;
                      		 return false;
                       
                       		 });  
                	}                                             
            	}); 
		        //过滤2
	            myfilter2 = Edo.create({
                type: 'textinput',
                width:'20%',	              
                ontextblur: function(e){
                	                		
                		myTable1.getKnowledgeList().getMyTable().data.filter(function(o, i){
                		
                		
                       		 if(o.uploader.name.indexOf(myfilter2.getValue())!=-1) return true;
                      		 return false;
                       
                       		 });  
                	}                                             
            	}); 
   	
               //过滤3
	           myfilter3 = Edo.create({
                type: 'textinput',
                width:'20%',          
                ontextblur: function(e){
                	
                		//myTable1.getKnowledgeList().getMyTable().data.each(function(o){alert("ddd"+o.uploadTime);});
                		myTable1.getKnowledgeList().getMyTable().data.filter(function(o, i){
                		
                		
                			/*new Edo.controls.Date().set({    
								    ondatechange: function(e){
								        alert(e.date.format('Y-m-d'));
								    }
								});
								*/
                			
                		
                       		 if(o.uploadTime.indexOf(myfilter3.getValue())!=-1) return true;
                      		 return false;
                       
                       		 });  
                	}                                             
            	});     
		        
		        //过滤4
		        myfilter4 = Edo.create({
                type: 'textinput',  
                width:'20%',            
                ontextblur: function(e){                       			
                		//myTable1.getKnowledgeList().getTableData().each(function(o){alert("ddd"+o.commentRecord.commentCount);});
                		myTable1.getKnowledgeList().getMyTable().data.filter(function(o, i){
                       		 if(o.commentRecord.commentCount >= myfilter4.getValue()) return true;
                      		 return false;
                       
                       		 });  
                	}                                             
            	}); 
      
		        	

		
       var filterbutton = Edo.create({
       		type:'button',
       		text:'显示filter',
       		onclick:function(e){
	       		filterbutton.set('text','隐藏filter');
	       		if(filterbox==null){
	       			 filterbox = Edo.create({
	       				type:'ct',
	       				width:'100%',
	       				layout: 'horizontal',
	       				children:[
	       						 myfilter1,
								 myfilter2,
								 myfilter3,
								 myfilter4    					
	       					]
	       				});
       				
       		    myTableCt.addChildAt (1,filterbox);
       		}else{
	       		filterbutton.set('text','显示filter');
	       		filterbox.destroy();
	       		filterbox=null;
	       		myfilter1.setValue(null);
				myfilter2.setValue(null);
				myfilter3.setValue(null);
				myfilter4.setValue(null);
	       		myTable1.getKnowledgeList().search();
	       		}
       		}		  
       		}); 
	
		/*var myTableCt = Edo.create({		 
		    type: 'box',                 
		    layout: 'vertical',
		    width: 1000,
		    height: 500,    
		    children: [
			      {type: 'box',  
			        border:[0,0,0,0],               
				    layout: 'horizontal',				   				   
				    children:[domaintree_select,				                
			                     bb,
			                     filterbutton
			                      ]    
			        }       
		      
		    ]
			
		});	
		*/
		

	    this.getTKlist = function(){
	    	return myyy;
	    }    
}

