/**
专家领域信息输入的界面
*/
function createTreeExpertList(){
var myWin1 = null;
var myWin2 = null;
var currentNode1;	
var currentNode2;	
//从域树接收数据
var experttreeData = cims201.utils.getData('tree/privilege-tree!listCDTreeNodes.action',{treeType:"domainTree"});
//alert("ddddddddddd"+experttreeData.id);
	var treeColumns = [{
				      header: '树名',
				      dataIndex: 'name'
				      				     			     				      
				 }							 				 
				 ];						 
var myExpertTree = new createTree({},treeColumns,experttreeData,'single',[],[],domainTreeSelectEvent);

//从分类树接收数据
var experttreeData2 = cims201.utils.getData('tree/privilege-tree!listCDTreeNodes.action',{treeType:"categoryTree"});
var treeColumns2 = [{
				      header: '树名',
				      dataIndex: 'name'				     			     				      
				 }							 				 
				 ];						 
var myExpertTree2 = new createTree({},treeColumns2,experttreeData2,'single',[],[],categoryTreeSelectEvent);


var teTableCt = Edo.create({		 
		    type: 'box',                 
		    layout: 'vertical',
		    width: 1000,
		   // height: 500,    
		    children: [
			      
		    ]
			
		});	




var teinput = Edo.build({
    type: 'box', verticalGap: 0, 
    border: [0,0,0,0],       
    children:[
        {
            type: 'tabbar', selectedIndex: 0,
            onselectionchange: function(e){                    
                expertlistct2.set('selectedIndex', this.selectedIndex);
            },
            children: [{text: '领域树专家', type: 'button'},{text: '多维分类树专家', type: 'button'}]
        },
        {
            id: 'expertlistct2', type: 'box', border: [1,1,1,1], layout: 'viewstack', 
            style: 'background-color:white;', width: '100%',
            children: [            
                  //按领域树节点展示专家
                {
                      type: 'box', 
					  border: [0,0,0,0],
					  padding: [0,0,0,0],
					  width:1000,
					  //height: '100%',
					  layout: 'horizontal', 
					  horizontalGap:10,   
					  children: [						 
                	     {                    
				    		 
						    type: 'panel', 
						    title:'领域树',        
						    width: 300,
						    height: 580,
						    border: [1,1,1,1],  
						    padding: [0,0,0,0],    						   
						    children: [
						        myExpertTree.getTree()
						    ]
					     },
					     {
					     	 type: 'ct', 
							 border: [0,0,0,0],
							 padding: [0,0,0,0],
							 width:685,
							 layout: 'vertical', 
							 verticalGap: 10, 
							 children: [
							 	 {                    				    		 
								    id:'treeexpertshow1',
								    type: 'panel', 
								    title:'领域的专家',        
								    width:685,
								    height:180,
								    border: [1,1,1,1],  
								    padding: [0,0,0,0],  
								    horizontalScrollPolicy:'on',						    
								    children: []
						    	},
						    	  {
							     	 type: 'ct', 
									 border: [0,0,0,0],
									 padding: [0,0,0,0],
									 width:685,
									 layout: 'vertical', 
									 verticalGap: 0, 
									 children: [
									 	 cims201.utils.createNameBtPanel('dtreenameid','领域的问题',['过滤器'],[domainfilter]),
									 	 {                    				    		 
										    id:'treeklistshow1',
										    type: 'box',        
										    width:685,
										    height: 365,
										    border: [0,1,1,1],  
										    padding: [0,0,0,0], 
										    //verticalScrollPolicy:'on',   						    
										    children: []
								    	}
									 
									 ]}
						    	
			    	         ]			

					     }

					     ]		
                },
                  //按多维分类树节点展示专家
                      {
                      type: 'box', 
					  border: [0,0,0,0],
					  padding: [0,0,0,0],
					  width:1000,
					  //height: 420,
					  layout: 'horizontal', 
					  horizontalGap:10,   
					  children: [						 
                	     {                    
				    		 
						    type: 'panel', 
						    title:'多维分类树',        
						    width: 300,
						    height: 580,
						    border: [1,1,1,1],  
						    padding: [0,0,0,0],    						   
						    children: [
						       myExpertTree2.getTree()
						    ]
					     },
					     {
					     	 type: 'ct', 
							 border: [0,0,0,0],
							 padding: [0,0,0,0],
							 width:685,
							 //height: 420,
							 layout: 'vertical', 
							 verticalGap: 10, 
							 children: [
							 	 {                    				    		 
								    id:'treeexpertshow2',
								    type: 'panel', 
								    title:'分类的专家',        
								    width:685,
								    height:180,
								    //height: 300,
								    border: [1,1,1,1],  
								    padding: [0,0,0,0],  
								    horizontalScrollPolicy:'on',						    
								    children: []
						    	},
						    	{
							     	 type: 'ct', 
									 border: [0,0,0,0],
									 padding: [0,0,0,0],
									 width:685,
									 //height: 420,
									 layout: 'vertical', 
									 verticalGap: 0, 
									 children: [
									 	 cims201.utils.createNameBtPanel('ctreenameid','分类的问题',['过滤器'],[categoryfilter]),
									 	 {                    				    		 
										    id:'treeklistshow2',
										    type: 'box',        
										    width:685,
										    height: 365,
										    //height: 300,
										    border: [0,1,1,1],  
										    padding: [0,0,0,0],
										    //verticalScrollPolicy:'on',   						    
										    children: []
								    	}
									 
									 ]}
			    	         ]			

					     }

					     ]		
                }
                
            ]
        }
    ]
});

function domainfilter(){
	  var myFilter1 = null;
	  var myFilterComponent1 = null;
      if(null!=myFilterComponent1){myFilterComponent1.destroy();}
	  if(null!=myWin1){myWin1.destroy();}
		myWin1 = null;
    	if(myWin1 == null){						    		 						
			myFilter1 = new questionFilter(function(){myWin1.hide();},'domainnode',currentNode1,'treeklistshow1');	
			if(myFilterComponent1==null){
				myFilterComponent1 = myFilter1.getInput();
			}
			
			myWin1 = new cims201.utils.getWin(685,140,"问题过滤",myFilterComponent1);																							
		}
		myFilter1.getInput().reset();
		myWin1.show(500,340,true);
				    
}

function categoryfilter(){
	  var myFilter2 = null;
	  var myFilterComponent2 = null;
      if(null!=myFilterComponent2){myFilterComponent2.destroy();}
	  if(null!=myWin2){myWin2.destroy();}
		myWin2 = null;
    	if(myWin2 == null){						    		 						
			myFilter2 = new questionFilter(function(){myWin2.hide();},'categories',currentNode2,'treeklistshow2');	
			if(myFilterComponent2==null){
				myFilterComponent2 = myFilter2.getInput();
			}
			
			myWin2 = new cims201.utils.getWin(685,140,"问题过滤",myFilterComponent2);																							
		}
		myFilter2.getInput().reset();
		myWin2.show(500,340,true);
				    
}


	

//域树
function domainTreeSelectEvent(cn){
Edo.get('treeexpertshow1').removeAllChildren();
Edo.get('treeklistshow1').removeAllChildren();
//Edo.get('treeexpertshow1').set('title',+'的专家');
 		
    	currentNode1 = cn.id;    		    	    
              
        var selecttree = cims201.utils.getData('qanda/qanda!listtreeexperts.action',{json:currentNode1});
             	         
	          var oneTreeNode = createTreeBox(selecttree);	
	          Edo.get('treeexpertshow1').set('title','<span style="color:red;">'+'['+selecttree.name+']'+'</span>'+'领域的专家');					
				oneTreeNode.set('border',[1,1,1,1]);
				treeexpertshow1.addChild(oneTreeNode);


               var mySelectTreeKList = createTreeQlist("domainnodeid",cn.id);            
               
               Edo.get('dtreenameid').set('text','<span style="color:red;">'+'['+selecttree.name+']'+'</span>'+'领域的问题');	
			 	treeklistshow1.addChild(mySelectTreeKList);                    		    		
}





//分类树
function categoryTreeSelectEvent(cn){
Edo.get('treeexpertshow2').removeAllChildren();
Edo.get('treeklistshow2').removeAllChildren();
//Edo.get('treeexpertshow1').set('title',+'的专家');
 		
    	currentNode2 = cn.id;    		    	    
              
        var selecttree = cims201.utils.getData('qanda/qanda!listtreeexperts.action',{json:currentNode2});
             	         
	          var oneTreeNode = createTreeBox(selecttree);	
	          Edo.get('treeexpertshow2').set('title','<span style="color:red;">'+'['+selecttree.name+']'+'</span>'+'分类的专家');					
				oneTreeNode.set('border',[1,1,1,1]);
				treeexpertshow2.addChild(oneTreeNode);

               var mySelectTreeKList = createTreeQlist("categoriesid",cn.id);
               Edo.get('ctreenameid').set('text','<span style="color:red;">'+'['+selecttree.name+']'+'</span>'+'分类的问题');	
			 	treeklistshow2.addChild(mySelectTreeKList);            
		    
}

function createTreeBox(c){
	var outReply = null;
	var namechildren = [];

	for(var i=0;i<c.children.length;i++) {
	  var label_str = '';
	 		label_str += '<a href="javascript:showHisInfo(\''+c.children[i].id+'\',\''+c.children[i].username+'\')";>';		 		
	   		label_str += '详细信息';
	   		label_str += '&nbsp</a>';								
	 var oneName = Edo.create({
	 	type:'box',
	 	layout: 'horizontal',  
	 	children:[
	 		{type:'box',	 	     
	 	     border: [0,0,0,0],
		     padding: [0,0,0,0],		     
			 children: [					
					{type: 'label', 
					//id:'personimg' , 
					text: '<img src=thumbnail/'+c.children[i].picturePath+' height=120></img>'}					
				]
			 },
			 {type:'box',	 	     
	 	     border: [0,0,0,0],
		     padding: [0,0,0,0],
		     verticalGap: 2, 
		     layout: 'vertical',  
	 		 children: [					
					{type:'label',text:'姓名：'+c.children[i].username},
			 		{type:'label',text:'性别：'+c.children[i].sex},
			 		{type:'label',text:'邮箱：'+c.children[i].email},
	 				{type:'label',text:'擅长领域：'+c.children[i].treenodename},
	 				{type:'label',text:label_str}	
	 				 					 
			             //}		
				]
	 		 }	
	 		]
	 	
	 	});				
	 namechildren.add(oneName);		
	}
	
		outReply = Edo.create({
			type: 'box',				
			border: [1,1,1,1],							
			padding: [0,0,0,0],
			//width: '100%',
			layout: 'horizontal',  
			//horizontalAlign: 'center', 
			children: namechildren
		});	
	
	
	return outReply;
}

function createTreeQlist(treetype,nodeid){
		var queryForm = {name:treetype,value:nodeid};		     
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
        var myTable = new createCDQuestionList_table('knowledge/knowledge!qsearch.action',{formvalue:queryFormStr}, [], []);
        var myklist = myTable.getKnowledgeList().getTable();	
 	   
	    return myklist;
   
}


this.getTEinput=function(){
	return teinput;
}

}


