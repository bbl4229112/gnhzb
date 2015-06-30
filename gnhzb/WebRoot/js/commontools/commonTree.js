/**
树组件，该树可以实现级联配置，配置容器等拓展功能
*/

/**
mode表示树的级联方式，可以选single,multi,cascade
在级联状态下有四种状态
0没有选择，还可以操作
1选择，还可以操作
2没有选择，但不可以操作
3选择了，但不可以操作

myConfig：对表格的个性化配置
treeColumns：表格的列集合
treeData: 加载树的数据
mode：树的几种模式，在上面已经介绍过了
btNames：表格顶端的按钮名字
btFunctions：表格顶端按钮对应的点击函数
selectionEvent：选择树了之后的事件
bodySelectionEvent：点击树节点body之后的事件
*/
function createTree(myConfig,treeColumns,treeData,mode,btNames, btFunctions,selectionEvent,bodySelectionEvent,ajaxloadurl){
	
	//单选的时候目前选中的节点
	var currentNode = null;
	//多选的时候目前选中的节点
	var currentNodes = new Array();
	
	//上面工具栏
	var myBtPanel;
	
	if(btNames == null || btNames.length == 0){
		myBtPanel = null;
	}else{
		var myBtPanel = cims201.utils.createBtPanel(btNames, btFunctions);
	}	
	//树的配置
	var currentConfig = {
	    cls: 'e-tree-allow',
	    width: '100%',
	    height: '100%',
	    headerVisible:false,
	    enabelCellSelect: true,
	    enableDragDrop: true, 
	    autoColumns: true,
	    horizontalLine: false,
	    columns: treeColumns,
	    border: [0,0,0,0],
	    data: treeData
	};
	
	//用户的个性化配置
	for(var key in myConfig){
		currentConfig[key] = myConfig[key];
	}
	//构建树	
	var myTree = new Edo.lists.Tree();
	myTree.set(currentConfig);	
	
	
	//动态加载树
	if(ajaxloadurl){
		myTree.on('beforetoggle', function(e){
			 	var row = e.record;
	            var dataTree = myTree.data;           
	            if(!row.children || row.children.length == 0){
	                //显示树形节点的loading图标,表示正在加载
	                this.addItemCls(row, 'tree-node-loading');
	                Edo.util.Ajax.request({
	                    //url: 'nodes.txt',
	                    url: ajaxloadurl,
	                    params: {
	                        pid: row.id   //传递父节点的id(也可以是ID)
	                    },
	                    defer: 200,
	                    onSuccess: function(text){
	                        var data = Edo.util.Json.decode(text);
	                        
	                        dataTree.beginChange();
	                        if(!(data instanceof Array)) data = [data]; //必定是数组
	                        data.each(function(o){
	                            dataTree.insert(0, o, row);    
	                        });                    
	                        dataTree.endChange();    
	                    }
	                });
	            }
	            return !!row.children;
		});
	}
	
	
	
	
	
	//根据输入参数判断树的面板应该显示哪些元素
	var children = new Array();
	if(myBtPanel != null){
		children[children.length] = myBtPanel;	
	}
	children[children.length] = myTree;
	
	var mainPanel = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		//title: title,
		layout: 'vertical',    
		width: '100%',
	    height: '96%',  
	    children: children
	});
	
	
	
	
	
	//注册选择手型事件
	myTree.on('bodymousedown', function(e){
    	var eee = e;
    	var r = this.getSelected();
    
	    //在这里捕捉点击事件  
	    if(r){	    	
	    	//如果节点的status为2或者3，之间跳过
	    	if(r.status != 2 && r.status != 3){
	    		
	    		//单选模式
		    	if('single' == mode){
		    		if(currentNode){
			    		if(r.id == currentNode.id){
			    			currentNode = null;
			    			singleSelect(r,false);
			    		
			    		}else{
			    			currentNode = r;
			    			singleSelect(r,true);
			    		}
			    	}else{
			    		currentNode = r;
			    		singleSelect(r,true);
			    	}
		    	}
		    	
		    	//多选模式或级联模式
	    		if('multi' == mode || 'cascade' == mode){
		    		if(currentNode){
			    		if(r.id == currentNode.id){
			    			currentNode = null;
			    			for(var i=0;i<currentNodes.length;i++){
			    				if(currentNodes[i].id == r.id){
			    					currentNodes.splice(i,1);
			    				}
			    			}
			    			multiSelect(r,false);
			    		}else{
			    			currentNode = r;
			    			for(var i=0;i<currentNodes.length;i++){
			    				if(currentNodes[i].id == r.id){
			    					currentNodes.splice(i,1);
			    				}
			    			}
			    			currentNodes[currentNodes.length] = r;
			    			multiSelect(r,true);
			    		}
			    	}else{
			    		currentNode = r;
			    		currentNodes[currentNodes.length] = r;
			    		multiSelect(r,true);
			    	}
		    		if('cascade' == mode){
			    		//级联选择
			    		cascadeSelect();
			    	}
		    	}
		    		
		    	if('single' == mode || 'multi' == mode || 'cascade' == mode){	
		    		//在最后执行那个选择方法
			    	if(selectionEvent){
			    		selectionEvent(r);	
		    		}
		    	}
		    		
		    	if('cascade2' == mode){
		    		//当点击的不是checkbox时
		    		if(e.target.className.indexOf('e-tree-check-icon')<0){
		    			bodySelectionEvent(r.id);
		    		}else{
		    			if(currentNode){
				    		if(r.id == currentNode.id){
				    			currentNode = null;
				    			for(var i=0;i<currentNodes.length;i++){
				    				if(currentNodes[i].id == r.id){
				    					currentNodes.splice(i,1);
				    				}
				    			}
				    			multiSelect(r,false);
				    		}else{
				    			currentNode = r;
				    			for(var i=0;i<currentNodes.length;i++){
				    				if(currentNodes[i].id == r.id){
				    					currentNodes.splice(i,1);
				    				}
				    			}
				    			currentNodes[currentNodes.length] = r;
				    			multiSelect(r,true);
				    		}
				    	}else{
				    		currentNode = r;
				    		currentNodes[currentNodes.length] = r;
				    		multiSelect(r,true);
				    	}
				    	
				    	cascadeSelect();
				    	if(selectionEvent){
				    		selectionEvent(r);	
			    		}
		    		}
		    		
		    	}		  
	    	}else{
	    	//当设置为cascade2的时候，需要对bodyselection做出响应
	    		if(bodySelectionEvent){
	    			bodySelectionEvent(r.id);
	    		}    		
	    	}
	    }
	});
	//在级联状态下给节点增加标记
	function tagNode(nodes){
		if(nodes != null){
			for(var i=0;i<nodes.length;i++){
				
				if(nodes[i].checked == true){
					myTree.data.update(nodes[i], 'status', 1);
					tagNode_fc(nodes[i].children);
					//nodes[i].status = 1;
					//nodes[i].children = tagNode_fc(nodes[i].children);
				}else{
					myTree.data.update(nodes[i], 'status', 0);
					tagNode(nodes[i].children);
					//nodes[i].status = 0;
					//nodes[i].children = tagNode(nodes[i].children);
				}
			}
		}	
	}	
	//当父节点选择的情况下标记所有的子节点
	function tagNode_fc(nodes){
		if(nodes != null){
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].checked == true){
					myTree.data.update(nodes[i], 'status', 3);
					//nodes[i].status = 3;
				}else{
					myTree.data.update(nodes[i], 'status', 2);
					//nodes[i].status = 2;
				}				
				tagNode_fc(nodes[i].children);
			}
		}
	}	
	/**
	单选
	*/
	function singleSelect(r,s){
		//myTree.data.beginChange();
	    for(var i=0;i<myTree.data.source.length;i++){  	
	    	//if(myTree.data.source[i].checked ){
	    	//}
	    	myTree.data.update(myTree.data.source[i], 'checked', false);
	       // myTree.data.source[i].status = 0;
	        myTree.data.update(myTree.data.source[i], 'status', 0);	    	
	    }
	    			 		               		        
	    myTree.data.update(r, 'checked', s);
	  	if(s == true){
	  		//myTree.data.source[i].status = 1;
	  		myTree.data.update(r, 'status', 1);
	  	}else{
	  		//myTree.data.source[i].status = 0;
	  		myTree.data.update(r, 'status', 0);
	  	}	  	   
		//myTree.data.endChange();
	}
	
	//多选
	function multiSelect(r,s){
		myTree.data.beginChange();
		
		if(s == false){
			myTree.data.update(r, 'checked', false);
			myTree.data.update(r, 'status', 0);
		}else{
			myTree.data.update(r, 'checked', true);
			myTree.data.update(r, 'status', 1);
		}
	  	   
		myTree.data.endChange();
	}
	
	//级联选择
	function cascadeSelect(){		
		myTree.data.beginChange();	
		tagNode(myTree.data.children);	  	   
		myTree.data.endChange();		
	}
	
	/**
	以下是对外提供的一些接口
	*/
	/**
	返回要得到的树结构
	*/
	this.getTree = function(){
//		alert(treeData);
		if(treeData == ""){
			mainPanel = new Edo.create({
				type : 'label',
				text : '<font size = 3 color = "blue" >没有需要管理的节点!</font>'
			});
		}
		return mainPanel;
	}	
	
	/**
	返回树   江丁丁添加   2013-1-16
	*/
	this.getMyTree = function(){
		return myTree;
	}
	
	/**
	得到当前节点
	*/
	this.getCurrentNode = function(){
		return currentNode;
	}
	
	/**
	返回当前节点组
	*/
	this.getCurrentNodes = function(){
		return currentNodes;
	}
	
	/**
	选择或不选择某一个节点
	*/
	this.selectNode = function(r,t){
		//var result = null
		for(var i=0; i<myTree.data.source.length; i++){
			if(myTree.data.source[i].id == r.id){
				//alert('find it');
				myTree.data.beginChange();
				//不选中 								
				if(t){
					myTree.data.source[i].checked = false;
				}else{
				//选中
					myTree.data.source[i].checked = true;
				} 
				
				myTree.data.endChange();
		
				if('cascade'==mode || 'cascade2'==mode){
					cascadeSelect();
				}
			}
		}
	}
	/**
	加载新的数据
	*/
	this.reloadData = function(data){
		myTree.data.beginChange();
		myTree.data.load(data);
		myTree.data.endChange();
		
		if('cascade'==mode){
			cascadeSelect();
		}
	}
	
	/**
	初始话操作
	*/
	//首先根据不同的模式对treeData进行预处理
	if('cascade'==mode){
		cascadeSelect();
	}	
}

//级联树用来渲染的函数
function renderCascadeTree(v,r){
	if(r.status == 0){
		return '<div class="e-tree-checkbox"><div class="e-tree-check-icon "></div>'+v+'</div>'; 
	}
	if(r.status == 1){
		return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  e-table-checked"></div>'+v+'</div>'; 
	}		
	if(r.status == 2){
		return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  e-table-notallowchecked"></div>'+v+'</div>'; 
	}		
	if(r.status == 3){
		return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  e-table-notallowchecked_c"></div>'+v+'</div>'; 
	}      	            	        
  	return '<div class="e-tree-checkbox"><div class="e-tree-check-icon "></div>'+v+'</div>'; 
}

