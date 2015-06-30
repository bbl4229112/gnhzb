
function questionFilter(callback,treetype,treenodeid,treeklistshowid){
	var filterLine;
	var myTextfiled1 = null;
	var myTextfiled2 = null;
	var myTextfiled = new Array(); 
	var dateline = null;
	var resultList = new Array();
	
	if(Edo.get('filtersubmit')!=null){Edo.get('filtersubmit').destroy();}
	
	var firstInput = Edo.create({
  		type: 'text',
  		width: '20%',
   		onclick: function(e){
			registEnter();
		},  
		onblur: function(e){
			unregistEnter();
		}
		
   	});
	var secondInput = Edo.create({
  		type: 'text',
   		width: '20%',
   		onclick: function(e){
			registEnter();
		},  
		onblur: function(e){
			unregistEnter();
		}
		
   	});
	var thirdInput = Edo.create({
   		type: 'text',
   		width: '20%',
   		onclick: function(e){
			registEnter();
		},  
		onblur: function(e){
			unregistEnter();
		}
		
   	});
	var fourthInput = Edo.create({
  		type: 'combo',
  		displayField: 'label', 
		valueField: 'value',
		width: '20%',
		readOnly : true,
		onclick: function(e){
			registEnter();
		},  
		onblur: function(e){
			unregistEnter();
		},
		
	    data: [
        {label: '未解决', value: 1},
        {label: '已解决', value: 2}
        ]

   	});
   	var dateInput = Edo.create({
   		type: 'text',
   		readOnly : true,
   		width: '15%'	
		
   	});
  	
   	var dateButton = Edo.create({
   		type:'button',
   		text:'日期',
   		onclick:function(e){
   			if(dateline==null){
   				myTextfiled[myTextfiled.length] = Edo.create({
  					type: 'label',
  					width: 20,
  					text: '从'
  				});
  				myTextfiled1 = Edo.create({
			  		width: '45%',
			  		readOnly : true,
			  		
			  		onclick: function(e){
						registEnter();
					},  
					onblur: function(e){
						unregistEnter();
					},
					
			   		type: 'date'
			   	});	
	   			myTextfiled[myTextfiled.length] = myTextfiled1;
		   		myTextfiled[myTextfiled.length] = Edo.create({
	  				type: 'label',
	  				width: 20,
	  				text: '到'
	   			});
	   			
			   	myTextfiled2 = Edo.create({
			  		width: '45%',
			  		readOnly : true,
			  		
			  		onclick: function(e){
						registEnter();
					},  
					onblur: function(e){
						unregistEnter();
					},
					
					type: 'date'
			   	});				   	
		   		myTextfiled[myTextfiled.length] = myTextfiled2;
		   		
		   		dateline = Edo.create({
			   		type: 'box',       
				    width: '100%',
				    //height: '100%',
				    padding: [0,0,0,0],
				    border: [0,0,0,0],      
				    layout: 'horizontal',    
				    children: myTextfiled
				    
			    });
			    filterLine.addChildAt(2,dateline);
   			}   				 				 							    
   		}  		
   		});
   	
   	      filterLine = Edo.create({
   	   		type: 'box',       
		    width: '100%',
		    //height: '100%',
		    padding: [0,0,0,0],
		    border: [0,0,0,0],      
		    layout: 'vertical',    
		    children: [
		    	{type: 'box',       
			    width: '100%',
			    //height: '100%',
			    padding: [0,0,0,0],
			    border: [0,0,0,0],      
			    layout: 'horizontal',    
			    children: [
			    	{type:'label',width: '20%',text:'问题标题'},
			    	{type:'label',width: '20%',text:'提问者'},
			    	{type:'label',width: '20%',text:'回复次数'},
			    	{type:'label',width: '20%',text:'解决情况'},
			    	{type:'label',width: '20%',text:'时间'}
			    	
			    	       	      
			    ]
		    	},
		    	{type: 'box',       
			    width: '100%',
			    //height: '100%',
			    padding: [0,0,0,0],
			    border: [0,0,0,0],      
			    layout: 'horizontal',    
			    children: [
			    	firstInput,
			    	secondInput,
			    	thirdInput,			    	
			    	fourthInput,
			    	dateInput,
			    	dateButton
			    	       	      
			    ]}
		    	
		    ]
   	   
		   
    });
    
    var mySubmitBt = Edo.create({
    	id:'filtersubmit',
    	type: 'button', 
    	text:'搜索', 
    	onclick: function(e){Edo.get('filtersubmit').enter();},
    	enter: function(){

			firstInput.blur();
			secondInput.blur();
			thirdInput.blur();
			fourthInput.blur();
			
			 var componentArray = [treenodeid,firstInput.getValue(),secondInput.getValue(),'uploadtime',thirdInput.getValue(),fourthInput.getValue()];
			
				componentArray.each(function(o,i){
				 		
				 		if(o){		 			
			 				if(inputArray[i]=="uploadtime"){
			 					if(myTextfiled1!=null&&myTextfiled2!=null&&(myTextfiled1.getValue()!=null||myTextfiled2.getValue()!=null)){
			 					var result = {};
			 					result.propertytype = "date";
			 					result.name = inputArray[i];
			 					if(myTextfiled1.getValue()!=null)
			 					result.after = myTextfiled1.getValue().format('Y-m-d');
			 					if(myTextfiled2.getValue()!=null)
				 				result.before = myTextfiled2.getValue().format('Y-m-d');
				 				result.and_or = 'and';	
			 					}	 					
			 				}
				 			else{
				 				var result = {};		 			    
				 				result.name = inputArray[i];		 				
				 				result.value = componentArray[i];
				 				result.and_or = 'and';	 	
				 			}
				 			 				 								
			 				resultList[resultList.length] = result;		
				 		}
				 	});
				
			   var json = Edo.util.Json.encode({searchlist:resultList});
			   //alert(json);			   
			    
			    var myTable = new createCDQuestionList_table('knowledge/knowledge!qsearch.action',{formvalue:json}, [], []);	
			    
			    Edo.get(treeklistshowid).removeAllChildren();
			    Edo.get(treeklistshowid).addChild(myTable.getKnowledgeList().getTable());
			    currentEventID = 'filtersubmit';
			    callback()
		 
		    }
    });
    filterLine.addChild(mySubmitBt);

    var inputArray = [treetype,'titlename','uploader','uploadtime','answernumbers','isSolved'];
    
    //提交
	//function submitSearch(e){
	
    
    //在编辑器选择的时候注册回车事件
	function registEnter(){
		currentEventID = 'filtersubmit';
	}
	
	//在编辑器不选择的时候取消注册回车事件
	function unregistEnter(){
		currentEventID = null;
	}
	
	enterEventList[enterEventList.length] = Edo.get('filtersubmit');
	enterEventIDList[enterEventIDList.length] = 'filtersubmit';
    
    
    this.getInput = function(){
    	return filterLine;
    }
	
}

