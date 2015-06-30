/**
知识订阅列表的界面
*/
var myImTable1_rt;
function knowledgeSubscribeList(){
var newAddUIC1 = new Array();
var newAddUIC2 = new Array();
var newAddUIC3 = new Array();
var newAddUIC4 = new Array();
var i = 10000;

var ktablebox = Edo.create({
    	type: 'box',
    	width: '100%',
    	//height: cims201.utils.getScreenSize().height,    	
    	padding: [0,0,0,0],
    	border: [0,0,0,0],     	
    	children: []
    });
    
var rightBox = Edo.create({
    	type: 'box',
    	width: '80%',
    	height: '100%',       		
    	padding: [0,0,0,0],
    	border: [0,0,0,0],  
    	verticalScrollPolicy :'on',    	
    	children: ktablebox
    });
    


//订阅信息列表数据的获取
var data = cims201.utils.getData('interestmodel/interestmodel!listinterest.action',{});
				 
data.each(function(o){

   if(o.interestItemType == 'keyword') {
		
		var newUIC = new Edo.core.Component();
		if(null!=Edo.get('sub'+(i)+o.interestItemId))
		{
		Edo.get('sub'+(i)+o.interestItemId).destroy();
		}
        newUIC.set({
       		type: 'cmp',
       		id:'sub'+(i++)+o.interestItemId,            
            name: o.interestItemName+'('+o.counts+')',
            text:'keyword',                     
            children:[]
         });       
         
        newAddUIC1[newAddUIC1.length] = newUIC;  
 
   }
   if(o.interestItemType == 'uploader') {
	   
	   var newUIC = new Edo.core.Component();
	   if(null!=Edo.get('sub'+(i)+o.interestItemId))
		{
		Edo.get('sub'+(i)+o.interestItemId).destroy();
		}
        newUIC.set({
       		type: 'cmp',
       		id:'sub'+(i++)+o.interestItemId,            
            name: o.interestItemName+'('+o.counts+')',
            text:'uploader',                      
            children:[]
         });
        newAddUIC2[newAddUIC2.length] = newUIC;  
   }
   if(o.interestItemType == 'domain') {
	   if(null!=Edo.get('sub'+(i)+o.interestItemId))
		{
		Edo.get('sub'+(i)+o.interestItemId).destroy();
		}
	  var newUIC = Edo.create({
       		type: 'box',
       		id:'sub'+(i++)+o.interestItemId,            
            name: o.interestItemName+'('+o.counts+')',           
            text:'domain',                      
            children:[]
         });
              		
       	if(o.children!=null) {
        	o.children.each(function(o){
				getSubImNodes(o,newUIC);
       		})	
       	}
       		
         
        newAddUIC3[newAddUIC3.length] = newUIC;  
		
   }
   if(o.interestItemType == 'category') {
	   if(null!=Edo.get('sub'+(i)+o.interestItemId))
		{
		Edo.get('sub'+(i)+o.interestItemId).destroy();
		}
	   var newUIC = Edo.create({
       		type: 'box',
       		id:'sub'+(i++)+o.interestItemId,            
            name: o.interestItemName+'('+o.counts+')',           
            text:'category',                      
            children:[]
         });
         
        if(o.children!=null) {
        	o.children.each(function(o){
				getSubImNodes(o,newUIC);
       		})	
       	}
                 
        newAddUIC4[newAddUIC4.length] = newUIC;  
		
   }
});

function getSubImNodes(imdto,componnet) {
	
	if(imdto!=null) {	
	if(null!=Edo.get('sub'+(i)+imdto.interestItemId))
		{
		Edo.get('sub'+(i)+imdto.interestItemId).destroy();
		}
		var subUIC = Edo.create({
       		type: 'box',
       		id:'sub'+(i++)+imdto.interestItemId,            
            name: imdto.interestItemName+'('+imdto.counts+')',           
            text:imdto.interestItemType,                      
            children:[]
         });  
         componnet.addChild(subUIC);
       
        if(imdto.children!=null) {
        	imdto.children.each(function(o){
				getSubImNodes(o,subUIC);
        });           
		}	
	}

}
//构建订阅列表树
var newUIC1 = 
  {
  	id:'firstLayer1',
    icon: 'e-tree-folder',  
    name: '关键词列表', 
    text:'firstLayer',   
    children:newAddUIC1       
};
var newUIC2 = 
  {
  	id:'firstLayer2',
    icon: 'e-tree-folder',
    name: '上传者列表', 
    text:'firstLayer',     
    children:newAddUIC2       
};
var newUIC3 = 
  {
   	id:'firstLayer3',
    icon: 'e-tree-folder',
    name: '领域树节点列表',  
    text:'firstLayer',    
    children:newAddUIC3      
};
var newUIC4 = 
  {
   	id:'firstLayer4',
    icon: 'e-tree-folder',
    name: '多维分类树节点列表',  
    text:'firstLayer',    
    children:newAddUIC4       
};

var newUIComponents = [newUIC1,newUIC2,newUIC3,newUIC4];
var dataTree = new Edo.data.DataTree(newUIComponents);
var tree = new Edo.lists.Tree();
tree.set({
	id:'imtree',
    cls: 'e-tree-allow',
    width: '100%',
    height: '100%',
    headerVisible:false,   
    autoColumns: true,
    horizontalLine: false,
    columns: [
        {
            header: '组件名称',
            dataIndex: 'name'          
        }
    ],
    onselectionchange: function(e){	    
    	if(e.selected.text!='firstLayer')
		{
		var id =Edo.get("imtree").getSelected().id;
    	var queryForm;	
    	
    	//------屏蔽的知识类型【Question Dandianfujianthree Dandianfujianfour】        
		if(e.selected.text=='keyword'){queryForm = [{name: 'keywordid',value:e.selected.id.substring(8)}
		,{name: 'ktypenames',value:'Question Dandianfujianthree Dandianfujianfour',and_or:'and'}];}
		
		if(e.selected.text=='uploader'){queryForm = [{name: 'uploaderid',value:e.selected.id.substring(8)}
		,{name: 'ktypenames',value:'Question Dandianfujianthree Dandianfujianfour',and_or:'and'}];}
		
		if(e.selected.text=='domain'){queryForm = [{name: 'domainnodeid',value:e.selected.id.substring(8)}
		,{name: 'ktypenames',value:'Question Dandianfujianthree Dandianfujianfour',and_or:'and'}];}
		
		if(e.selected.text=='category'){queryForm = [{name: 'categoriesid',value:e.selected.id.substring(8)}
		,{name: 'ktypenames',value:'Question Dandianfujianthree Dandianfujianfour',and_or:'and'}];}
		
		var searchlist = {searchlist: queryForm};
	 	var queryFormStr = Edo.util.Json.encode(searchlist); 
	 	//var myTable1_rt = new createImKnowledgeList_box();   			
		//myTable1_rt.search({formvalue:queryFormStr},'knowledge/knowledge!interestKSearch.action');
	 	myImTable1_rt = new createImKnowledgeList_box();   			
		myImTable1_rt.search({formvalue:queryFormStr},'knowledge/knowledge!interestKSearch.action');
		ktablebox.removeAllChildren();
	 	//ktablebox.addChild(myTable1_rt.getKnowledgeList());	
	 	ktablebox.addChild(myImTable1_rt.getKnowledgeList());	
	 	}	                                           
           
        },
    data: dataTree
});

         
var interestModelInput = Edo.create({
    
    type: 'panel',  
    title:'我的订阅列表',      
    border: [1,1,1,1],
    padding: [20,20,20,20],
    height:'100%',
    width:'100%',
    layout: 'horizontal',  
    verticalGap: 10,      
    children: [              
        {
        type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		width: '20%',
		height: '100%',
		layout: 'vertical',
		children: tree             
        },
        rightBox
        
    ]
});




this.getInterestModelinput = function(){
	return interestModelInput;
}

}


