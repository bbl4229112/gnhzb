function createTreeKnowledge(nodeId,myId){
	//创建右边的知识列表
	var rightPanel;
	//左边的树 
	var leftTree = Edo.create({
        type: 'tree',
        cls: 'e-tree-allow',
	    width: '15%',
	    height: '100%',
	    headerVisible:false,
	    autoColumns: true,
	    horizontalLine: false,
	    columns: [
	        {
	            header: '分类节点',
	            dataIndex: 'name'
	        }
	    ],
	    onselectionchange: function(e){		        
			//alert("serach");
			var queryForm = {name:e.selected.index,value:e.selected.id};
			var searchlist = {searchlist: [queryForm]};
		 	var queryFormStr = Edo.util.Json.encode(searchlist);                                                 
            var myTable1_rt = new createKnowledgeList_box();   			
			myTable1_rt.search({formvalue:queryFormStr},'knowledge/knowledge!ksearch.action');
			rightPanel.removeAllChildren();
			rightPanel.addChild(myTable1_rt.getKnowledgeList());
        },
	    data: []                	
    });
    //从后台取数据加载左边的树
    cims201.utils.getData_Async('tree/tree!listSubNode.action',{nodeId:nodeId,operationName:'查看知识'},function(text){
		if(text == null || text == '') return;
		var data = Edo.util.Json.decode(text);
		leftTree.set('data',data);
	});
    
    rightPanel = Edo.create({
    	type: 'box',
    	width: '85%',
        layout: 'vertical', 
         height: cims201.utils.getScreenSize().height-130,
		  verticalScrollPolicy:'on',
		
    	padding: [0,0,0,0],
    	border: [0,0,0,0],
    	children: []
    });
 
    var outerPanel = Edo.create({
    	id: myId?myId:'treeKnowledgepanel123',
    	type: 'box',
    	width: '100%',
    	height: cims201.utils.getScreenSize().height-120,
    	padding: [0,0,0,0],
    	border: [0,0,0,0],
    	layout: 'horizontal',
    	//verticalScrollPolicy:'on',	
    	children: [leftTree,rightPanel]
    });
    
    this.getPanel = function(){
    	return outerPanel;
    }
}
////pl 以树的结构展示收藏夹结构。点击子收藏夹，显示该收藏夹中知识。
//function createKeepTreeKnowledge(myId){
//	//创建右边的知识列表
//	var rightPanel;
//	//左边的树 
//	var leftTree = Edo.create({
//        type: 'tree',
//        cls: 'e-tree-allow',
//	    width: '15%',
//	    height: '100%',
//	    headerVisible:false,
//	    autoColumns: true,
//	    horizontalLine: false,
//	    columns: [
//	        {
//	            header: '分类节点',
//	            dataIndex: 'name'
//	        }
//	    ],
//	    onselectionchange: function(e){		        
//			//alert("serach");
//			var queryForm = {name:e.selected.index,value:e.selected.id};
//			var searchlist = {searchlist: [queryForm]};
//		 	var queryFormStr = Edo.util.Json.encode(searchlist);                                                 
//            var myTable1_rt = new createKnowledgeList_box();   			
//			myTable1_rt.search({formvalue:queryFormStr},'knowledge/knowledge!ksearch.action');
//			rightPanel.removeAllChildren();
//			rightPanel.addChild(myTable1_rt.getKnowledgeList());
//        },
//	    data: []                	
//    });
//    //从后台取数据加载左边的树
//    cims201.utils.getData_Async('tree/tree!listKeepSubNode.action',{operationName:'查看知识'},function(text){
//		if(text == null || text == '') return;
//		var data = Edo.util.Json.decode(text);
//		leftTree.set('data',data);
//	});
//    
//    rightPanel = Edo.create({
//    	type: 'box',
//    	width: '85%',
//        layout: 'vertical', 
//         height: cims201.utils.getScreenSize().height-130,
//		  verticalScrollPolicy:'on',
//		
//    	padding: [0,0,0,0],
//    	border: [0,0,0,0],
//    	children: []
//    });
// 
//    var outerPanel = Edo.create({
//    	id: myId?myId:'treeKnowledgepanel123',
//    	type: 'box',
//    	width: '100%',
//    	height: cims201.utils.getScreenSize().height-120,
//    	padding: [0,0,0,0],
//    	border: [0,0,0,0],
//    	layout: 'horizontal',
//    	//verticalScrollPolicy:'on',	
//    	children: [leftTree,rightPanel]
//    });
//    
//    this.getPanel = function(){
//    	return outerPanel;
//    }
//}