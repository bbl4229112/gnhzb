

//创建一个树
cims201.tree.createTree = function(treeName,treeColumns,treeData,selectEvent,width,height,singleOrMulti){
	
	var tree = new Edo.lists.Tree();
	//表示当前节点
	var currentNode;
	//表示所选中的节点
	var allSelectedNodes;
	
	tree.set({
		id: treeName+''+treeData.id,
	    cls: 'e-tree-allow',
	    width: width,
	    height: height,
	    headerVisible:false,
	    enabelCellSelect: true,
	    enableDragDrop: true, 
	    autoColumns: true,
	    horizontalLine: false,
	    columns: treeColumns,
	    border: [0,0,0,0],
	    data: treeData
	});
	
	//注册选择手型事件
	tree.on('bodymousedown', function(e){
    	var r = this.getSelected();
    
	    //在这里捕捉点击事件  
	    if(r){
	    	//读取目前点击节点的信息
	    	//currentMode = r.mode;
	    	//currentId = r.id;
	    	//currentIndex = Edo.get('cata_1').selectedIndex;
	    	//currentNode = r;
	    					
	        var inCheckIcon = Edo.util.Dom.hasClass(e.target, 'e-tree-check-icon');		          
	   		var t =this;
	   		
	   		if(inCheckIcon && r.checked){
	            setTreeSelect(r, 0, false,singleOrMulti);
	        	tree.currentNode = null;
	        }else{
	        		        
	            setTreeSelect(r, 1, false,singleOrMulti);
	            tree.currentNode = r;	
	            selectEvent(r);            
	        }

	    }
	});
	
	//设置树的选择
	function setTreeSelect(sels, checked, deepSelect,singleOrMulti){//deepSelect:是否深度跟随选择
	    if('single' == singleOrMulti){
	    	//单选
			if(!Edo.isArray(sels)) sels = [sels];
			    tree.data.beginChange();
			    tree.data.source.each(function(o){                
			        this.data.update(o, 'checked', false);
			    },tree);
			    
			    
		    //sels.each(function(o){
			//        if(o.children && o.children.length > 0){    //只有父任务才可以选中
			//            this.data.update(o, 'checked', checked);
			//        }
			//    },tree);
		    
		    for(var i=0,l=sels.length; i<l; i++){
		        var r = sels[i];        		        
		        tree.data.update(r, 'checked', checked);
		    }
		    
		    tree.data.endChange();
	    }else{
	    	//多选
		    if(!Edo.isArray(sels)) sels = [sels];
		    tree.data.beginChange();
		    for(var i=0,l=sels.length; i<l; i++){
		        var r = sels[i];        
		        var cs = r.children;        
		        if(deepSelect){
		            tree.data.iterateChildren(r, function(o){
		                this.data.update(o, 'checked', checked);
		            },tree);
		        }
		        tree.data.update(r, 'checked', checked);
		    }
		    tree.data.endChange();
	    }
	    
	}
	
	if(treeData == ""){
		 tree = new Edo.create({
			border : [0,1,1,1],
			width : '100%',
			height : 400,
			type : 'box',
			children : [
			            {
			            	type : 'label',
			    			text : '<font size = 3 color = "blue" >没有需要管理的节点!</font>'
			            }
			            ]
		});
	}
	return tree;	 
}

//读取所有选中的树节点
cims201.tree.getTreeSelected = function(myTree){

    var sels = [];
    myTree.data.source.each(function(node){        
        if(node.checked) sels.add(node);
    });
    return sels;
}



//增加树的节点
cims201.tree.addTreeNode = function(myTree,mn){
	
	var r = myTree.currentNode;
	myTree.data.beginChange();
    myTree.data.add(mn,r);
    myTree.data.endChange();
}

//新增树的顶级模块
cims201.tree.addTopTreeNode = function(myTree,mn){
	myTree.data.beginChange(); 
    myTree.data.insert(0,mn);
    myTree.data.endChange();  
}

//删除树的节点
cims201.tree.deleteTreeNode = function(myTree){
	var r = myTree.currentNode;
	myTree.data.remove(r);   
}

//编辑树的节点
cims201.tree.editTreeNode = function(myTree,mn){
	var r = myTree.currentNode;
    myTree.data.beginChange();
  
    for(var key in mn){
        myTree.data.update(r, key, mn[key]);   
    }	
    
    myTree.data.update(r, 'checked', 1);	
    		    
  	myTree.data.endChange();
}


//给树节点排序
cims201.tree.order=function(myTree,direction,url){
	var r = myTree.currentNode;
	if(r==null){
		cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择树节点',Edo.MessageBox.OK); 
		return;
	}
	//alert(r.index);
	var parentNode=myTree.data.findParent(r);
	var index=myTree.data.indexOfChild(parentNode,r);
	var upperNode;
	var lowerNode;
	if(direction>0){
		if(myTree.data.isFirst(r)){
			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'已经是本级最后一个节点了！',Edo.MessageBox.OK); 
			return;
		}else{
			upperNode=myTree.data.getChildAt(parentNode,index-1);
			var data=cims201.utils.getData(url,{id:r.id,swapId:upperNode.id});
			swap(upperNode,r);
		}	
	}else{
		if(myTree.data.isLast(r)){
			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'已经是本级最后一个节点了！',Edo.MessageBox.OK); 
			return;
		}else{
			
			lowerNode=myTree.data.getChildAt(parentNode,index+1);
			var data=cims201.utils.getData(url,{id:r.id,swapId:lowerNode.id});
			swap(r,lowerNode);
				
		}
	}
	function swap(node1,node2){
		myTree.data.beginChange(); 
		myTree.data.move(node2,node1);
		myTree.data.endChange();	
	}
	
}


