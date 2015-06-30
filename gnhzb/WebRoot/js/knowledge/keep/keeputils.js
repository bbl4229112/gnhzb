var keepWin;
var bookmarkWin;
function createAddKeepBox(knowledgeId,knowledgeTitle,editorcreate){
	var keepremind = null;
	if(editorcreate == null){
		keepremind = '<font size=4.9><b>添加到收藏</b></font><br/><font size=2,color="black">请填写新收藏的详细信息</font>';
	}else{
		keepremind = '<font size=4.9><b>编辑收藏</b></font><br/><font size=2,color="black">请填写新收藏的详细信息</font>';
	}
	var kt = Edo.get('keeptitle');
	if(kt != null){
		kt.destroy();
	}
		var keepaddBox;

		if(Edo.get('keepnode'+knowledgeId) != null){
			Edo.get('keepnode'+knowledgeId).destroy();
		}
		
			keepaddBox=Edo.get('addkeep'+knowledgeId);
			if(keepaddBox!=null)
				keepaddBox.destroy();
			keepaddBox = Edo.create({
				id: 'addkeep'+knowledgeId,
				type: 'box',
				width: '100%',
				height: '100%',
				border: [0,0,0,0],
				padding: [0,0,0,0],
				layout: 'vertical',
				children: [
				           {
				        	   type : 'box',width: '100%',border: [0,0,0,0],layout : 'horizontal',
				        	   children :[
				        	              {
												   labelwidth : '25%',
												   type : 'label',
												   text : ''
				        	              },
				        	              {
								        	   
								        	   type : 'label',
								        	   text : '<div class="cims201_topbar_icon"><img height=\'50px\' src="css/images/getKnowledgeKeep.gif"></div>'
								           },
				        	              {
								        	   
								        	   type : 'box',
								        	   border : [0,0,0,0],
								        	   padding : [5,0,0,0],
								        	   children : [
								        	               {
								        	            	   type : 'label',
								        	            	   text : keepremind
								        	               }
								        	               ]
								        	  
								           }
				        	              ]
				        	   
				           },
				           {
				        	   type: 'box',border: [0,0,0,0],padding: [20,0,0,10], width:'100%',layout : 'horizontal',
				        	   children:[
				        	             {
				        	            	type : 'label',
				        	            	labelWidth : 50,
				        	            	text : '收藏名:'+'<font size=1.5 color="red">*</font>'
				        	             },
				        	             {
				        	            	 id : 'keeptitle',
				        	            	 type : 'text',
				        	            	 text : knowledgeTitle,//到了点击收藏，收藏名默认为知识名###################################################
				        	            	 width : 370
				        	         
				        	             }
				        	             ]
				           }
				           ]
			});
//			Edo.get('keeptitle').set('text',knowledgeTitle);
			var createnewkeeps = new Edo.create({
				type : 'label',
				width : '80',
				text : '<a><u size=2 >新建收藏夹...</u></a>',
				onclick : function(){
				createNewBookmark(knowledgeId);
			}
			});
			
			var keepData = cims201.utils.getData(
					'knowledge/keep/keep!listBookmarkTreeNodes.action',
					{
					});
			var keeptree_select = new Edo.controls.TreeSelect()
			.set({
			    id : 'keepnode'+knowledgeId,
			    type: 'treeselect',              
			    multiSelect: false,
			    displayField: 'nodeName',
			    width:280,
			    valueField: 'id',
			    rowSelectMode: 'single',    
			    data: keepData,
			    selectedIndex : 0,
			    treeConfig: {
			        treeColumn: 'name',
			        columns: [   
			            Edo.lists.Table.createMultiColumn(),
			            {id: 'name',header: '收藏夹',width:230,dataIndex: 'nodeName'}
			        ]
			    }
			});
		
		formitems123 = {
		type : 'formitem',
		label : '收藏夹:',
		width : '430',
		labelWidth : 50,
		padding : [20, 0, 0, 10],
		labelAlign : 'left',
		layout : 'horizontal',
		children : [keeptree_select,createnewkeeps]
		};

		Edo.get('addkeep'+knowledgeId).addChild(formitems123);
		var button = {
				type : 'box',
				border : [0, 0, 0, 0],
				text : '',
				padding : [20,0,0,10],
				layout : 'horizontal',

				children : [ {
							type : 'button',
							text : '确定',
							padding : [0, 0, 0, 0],
							enableToggle : false,
							onclick : function(e) {
					var keeptitle = new String(); 
					var bookmarktitle = new String();
					keeptitle = Edo.get('keeptitle').getValue();
					bookmarktitle = Edo.get('keepnode'+knowledgeId).getValue();
					if(keeptitle == ""){
						creatalert("收藏名不能为空!",keepWin);
					}else{
						addNewKeep(keeptitle,bookmarktitle,knowledgeId);
					}
				
				}
						}, {
							type : 'button',
							text : '重置',

							padding : [0, 0, 0, 0],
							enableToggle : false,
							onclick : function(e) {

								Edo.get('keeptitle_'+knowledgeId).set('text','');
							 
							}
						}, {
							type : 'button',
							text : '取消',

							padding : [0, 0, 0, 0],
							enableToggle : false,
							onclick : function(e) {

								keepWin.hide();
							}
						}

				]

			};
		Edo.get('keeptitle').set('text',knowledgeTitle);
		Edo.get('addkeep'+knowledgeId).addChild(button);
			
			if(editorcreate == null){
				if(keepWin == null){	
				
				keepWin = cims201.utils.getWinforkview(450,250,'添加收藏',keepaddBox,knowledgeId);
				}else{
					
					if(Edo.get('addkeep'+knowledgeId)!=null){
						Edo.get('addkeep'+knowledgeId).reset();
						keepWin = cims201.utils.getWinforkview(450,250,'添加收藏',keepaddBox,knowledgeId);
					}
				}
			}else{
				if(keepWin == null){	
					
					keepWin = cims201.utils.getWinforkview(450,250,'编辑收藏',keepaddBox,knowledgeId);
					}else{
						
						if(Edo.get('addkeep'+knowledgeId)!=null){
							Edo.get('addkeep'+knowledgeId).reset();
							keepWin = cims201.utils.getWinforkview(450,250,'编辑收藏',keepaddBox,knowledgeId);
						}
					}
			}
			
				setWinScreenPositions(700,350,keepWin,knowledgeId);
			
			
}
function createNewBookmark(knowledgeId){

	var arr = new Array();
	if(!isNaN(knowledgeId)){
		arr[0] = knowledgeId;
	}
	else{
		for(var i =0;i<knowledgeId.length;i++){
			arr[i] = knowledgeId[i];
		}
	}
	
		var bookmarkaddBox;
		var i = 0;

		bookmarkaddBox=Edo.get('addbookmark'+arr[i]);
			if(bookmarkaddBox!=null)
				bookmarkaddBox.destroy();
			bookmarkaddBox = Edo.create({
				id: 'addbookmark'+arr[i],
				type: 'box',
				width: '100%',
				height: '100%',
				border: [0,0,0,0],
				padding: [0,0,0,0],
				layout: 'vertical',
				children: [
				           {
				        	   type : 'box',width: '100%',border: [0,0,0,0],layout : 'horizontal',
				        	   children :[
				        	              {
												   labelwidth : '25%',
												   type : 'label',
												   text : ''
				        	              },
				        	              {
								        	   
								        	   type : 'label',
								        	   text : '<div class="cims201_topbar_icon"><img height=\'50px\' src="css/images/getKnowledgeKeep.gif"></div>'
								           },
				        	              {
								        	   
								        	   type : 'box',
								        	   border : [0,0,0,0],
								        	   padding : [5,0,0,0],
								        	   children : [
								        	               {
								        	            	   type : 'label',
								        	            	   text : '<font size=4.9><b>新建收藏夹</b></font><br/><font size=2,color="black">请填写新收藏夹的详细信息</font>'
								        	               }
								        	               ]
								           }
				        	              ]
				        	   
				           },
				           {
				        	   type: 'box',border: [0,0,0,0],padding: [20,0,0,10], width:'100%',layout : 'horizontal',
				        	   children:[
				        	             {
				        	            	type : 'label',
				        	            	labelWidth : 50,
				        	            	text : '收藏夹名:'+'<font size=1.5 color="red">*</font>'
				        	             },
				        	             {
				        	            	 id : 'bookmark_2'+arr[i],
				        	            	 type : 'text',
				        	            	 text : '',
				        	            	 width : 300
				        	         
				        	             }
				        	             ]
				           }
				           ]
			});
		
			var bookmarkData = cims201.utils.getData(
					'knowledge/keep/keep!listBookmarkTreeNodes.action',
					{
					});
			var keeptree_select = new Edo.controls.TreeSelect()
			.set({
			    id : 'fatherbookmarknode_2'+arr[i],
			    type: 'treeselect',              
			    multiSelect: false,
			    displayField: 'nodeName',
			    width:300,
			    valueField: 'id',
			    rowSelectMode: 'single',    
			    data: bookmarkData,
			    treeConfig: {
			        treeColumn: 'name',
			        columns: [
			            Edo.lists.Table.createMultiColumn(),
			            {id: 'name',header: '收藏夹',width:250, dataIndex: 'nodeName'}
			        ]
			    }
			});
			
		formitems123 = {
		type : 'formitem',
		label : '父收藏夹:',
		width : '430',
		labelWidth : 60,
		padding : [20, 0, 0, 10],
		labelAlign : 'left',
		layout : 'horizontal',
		children : [keeptree_select]
		};

		Edo.get('addbookmark'+arr[i]).addChild(formitems123);
		var button = {
				type : 'box',
				border : [0, 0, 0, 0],
				text : '',
				padding : [20,0,0,10],
				layout : 'horizontal',

				children : [ {
							type : 'button',
							text : '确定',
							padding : [0, 0, 0, 0],
							enableToggle : false,
							onclick : function(e) {
								var bookmarkvalue = Edo.get('bookmark_2'+arr[i]).getValue();
								var fatherbookmarknodevalue = Edo.get('fatherbookmarknode_2'+arr[i]).getValue();
								if(bookmarkvalue == ""){
									creatalert("收藏夹名不能为空!",bookmarkWin);
								}else{
									addNewBookmark(bookmarkvalue,fatherbookmarknodevalue);
									var newkeepdata = cims201.utils.getData(
											'knowledge/keep/keep!listBookmarkTreeNodes.action',
											{
											});
									Edo.get('keepnode'+knowledgeId).set('data',newkeepdata);
								}
							}
						}, {
							type : 'button',
							text : '重置',

							padding : [0, 0, 0, 0],
							enableToggle : false,
							onclick : function(e) {
							Edo.get('bookmark_2'+arr[i]).set('text','');
						}
						}, {
							type : 'button',
							text : '取消',

							padding : [0, 0, 0, 0],
							enableToggle : false,
							onclick : function(e) {

							bookmarkWin.hide();
							}
						}

				]

			};

		Edo.get('addbookmark'+arr[i]).addChild(button);
			
			if(bookmarkWin == null){	
				
				bookmarkWin = cims201.utils.getWinforkview(450,250,'新建收藏夹',bookmarkaddBox,arr[i]);
				}else{
					
					if(Edo.get('addbookmark'+arr[i])!=null){
						Edo.get('addbookmark'+arr[i]).reset();
						bookmarkWin = cims201.utils.getWinforkview(450,250,'新建收藏夹',bookmarkaddBox,arr[i]);
					}
				}
				setWinScreenPositions(550,500,bookmarkWin,arr[i]);
			

}
//pl 新建收藏夹
function addNewBookmark(bookmarkvalue,fatherbookmarknodevalue){
	if(fatherbookmarknodevalue == ""){
		fatherbookmarknodevalue = "1";
	}
	var keepData = cims201.utils.getData(
			'knowledge/keep/keep!createNewBookmark.action',
			{
				bookmarkvalue : bookmarkvalue,
				fatherbookmarknodevalue : fatherbookmarknodevalue
			});
	creatalert(keepData,bookmarkWin);
	bookmarkWin.hide();
	
}
//pl 增加收藏
function addNewKeep(keeptitle,bookmarktitle,knowledgeId){
	if(bookmarktitle == ""){
		bookmarktitle = "1";
	}
	var keepData = cims201.utils.getData(
			'knowledge/keep/keep!createNewKeep.action',
			{
				keeptitle : keeptitle,
				bookmarktitle : bookmarktitle,
				knowledgeId : knowledgeId
			});
	creatalert(keepData,keepWin);
	keepWin.hide();
	
}
//pl 创建收藏树
function createKeepTreeKnowledge(nodeId,myId){
	//创建右边的知识列表
	var rightPanel;
	//左边的树 
	leftTree = Edo.create({
        type: 'tree',
//        verticalScrollPolicy:'auto',
        horizontalScrollPolicy:'on',
        cls: 'e-tree-allow',
	    width: '30%',
	    height: '98%',
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
			treenodeid = e.selected.id;        
//			alert(treenodeid+"::::"+e.selected.name);
            var myTable1_rt = new createKeepList_box();
			myTable1_rt.search({treenodeid:treenodeid},'knowledge/keep/keep!keepSearch.action');
			rightPanel.removeAllChildren();
			rightPanel.addChild(myTable1_rt.getKnowledgeList());
        },
	    data: []                	
    });
    //从后台取数据加载左边的树
    cims201.utils.getData_Async('knowledge/keep/keep!listSubNodes.action',{nodeId:nodeId},function(text){
		if(text == null || text == '') return;
		var data = Edo.util.Json.decode(text);
		leftTree.set('data',data);
	});
    
    rightPanel = Edo.create({
    	type: 'box',
    	width: '70%',
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


//pl 创建收藏列表页面
function createKeepList_box(){
	//定义摘要的字数和行数
	var abstractTextLength = 50;
	var abstractTextLine = 2; 
	//默认多少行
	var defaultPageSize = 10;
	
	var myUrl = {};
	var myInputForm = {};
	var keepManager = Edo.create({
		type : 'box',
		width : '100%',
		height : 32,
		layout: 'horizontal',
		children : [
		            {
		            	type : 'button',
		            	text : '删除',
		            	onclick : function (){
			            	cims201.utils.getData_Async('knowledge/keep/keep!isBookMark.action',{nodeId:treenodeid},function(text){
			            		var data = Edo.util.Json.decode(text);
			        			if(data == 'rootbookmark'){
			        				creatalert("根收藏夹不能删除！");
			        			}else if(data == 'bookmark'){
			        				Edo.MessageBox.show({
			        					title : '提醒！',
			        					msg : '确定删除这些收藏？',
			        					buttons : Edo.MessageBox.YESNO,
			        					callback : deleteisgoback,
			        					icon : Edo.MessageBox.WARNING
			        				});
			        			}else{
			        				deleteKeep(treenodeid);
			        				contentBox.removeAllChildren();
			        				creatalert("收藏删除成功！");
			        			}
			        		});
		            }
		            },
		            {
		            	type : 'button',
		            	text : '重命名',
		            	onclick : function (){
		            	cims201.utils.getData_Async('knowledge/keep/keep!isBookMark.action',{nodeId:treenodeid},function(text){
		            		var data = Edo.util.Json.decode(text);
		        			if(data == 'rootbookmark'){
		        				creatalert("根收藏夹不能重命名！");
		        			}else if(data == 'bookmark'){
		        				Edo.MessageBox.prompt("请在文本框填写新收藏夹名", "", renamecallback);
		        			}else{
		        				Edo.MessageBox.prompt("请在文本框填写新收藏名", "", renamecallback);
		        				
		        			}
		        		});
	            }
		            }
		            ]
		            
	});
	function deleteisgoback(action, value) {
		if(action=='yes')
			{
				cims201.utils.getData_Async('knowledge/keep/keep!deleteBookMark.action',{nodeId:treenodeid},function(text){
	        		var data = Edo.util.Json.decode(text);
	        		contentBox.removeAllChildren();
	        		cims201.utils.getData_Async('knowledge/keep/keep!listSubNodes.action',{nodeId:'1'},function(text){
	        			if(text == null || text == '') return;
	        			var data = Edo.util.Json.decode(text);
	        			leftTree.set('data',data);
	        		});
	    			creatalert("收藏夹删除成功!");
	    		});
			}
			if(action=='no')
			{
			}	
			
	}
	function renamecallback(action, value){
				if(action=='ok')
				{
					cims201.utils.getData_Async('knowledge/keep/keep!renameBookMark.action',{nodeId:treenodeid,newName:value},function(text){
		        		var data = Edo.util.Json.decode(text);
		        		cims201.utils.getData_Async('knowledge/keep/keep!listSubNodes.action',{nodeId:'1'},function(text){
		        			if(text == null || text == '') return;
		        			var data = Edo.util.Json.decode(text);
		        			leftTree.set('data',data);
		        		});
		    			creatalert(data);
		    		});
				}
	}
	var contentBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		width: '100%',
		//height: '100%',
		layout: 'vertical',
		children: []
	});
		
	
	//创建分页条
	var myPager = null;
	var myPagerId;
	
	//首先判断paging bar是否存在
		  
   	myPagerId = contentBox.id+'paging';
	myPager = Edo.create({
		id: myPagerId,    
	    type: 'pagingbar',
	    width: '100%',
	    visible: false,
	    //autoPaging: true,		    
	    border: [0,1,1,1], 
	    padding: 2
	});	
	
	Edo.get(myPagerId).on('paging',function(e){
		search();	
	});
	
	//mainBox.addChild(myPager);
	
	//当查询没有数据的时候显示该条
	var bBar = Edo.create({
		type: 'label',
		width: '100%',
		visible: false,
		style: 'padding-left: 370px; color: red; font-size:16px; font-weight: bold; ',
		text: '该收藏夹中还没有收藏!'
	});
	
			
	mainBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,20,0],
		width: '100%',
		
		children: [keepManager,contentBox, myPager, bBar]
		//children: [contentBox]
	});
	
	//检索数据库，填充表格
	function search(){
	    if(myUrl != null && myUrl != ''){	
		    myInputForm.index = myPager.index;    
		    myInputForm.size = myPager.size;		  
		    var rData = cims201.utils.getData(myUrl,myInputForm);
//		    alert(":::::::--------::::"+typeof(rData.data) == "string");
//		    if(typeof(rData) == "string"){
//		    	var arr = rData.split();
//		    	var kid = arr[0];
//		    	var kname = arr[1];
//		    	showKnowledgeDetail(kid,kname);
//		    }else{
		    
		    	showKnowledge(rData.data);
				//如果数据为空
				if(rData.data == null ||rData.data.length == 0){
					
					myPager.set('visible',false);				
					bBar.set('visible',true);
					myPager.total = 0;
					myPager.totalPage = 0;
					myPager.refresh();
				}else{
					myPager.set('visible',true);
					bBar.set('visible',false);
					myPager.total = rData.total;
					myPager.totalPage = rData.totalPage;
					myPager.refresh();			
				}
//		    }
			
		}
	}
	
	//根据data显示知识
	function showKnowledge(data){
//		if(data == "已删除!"){}
		if(data){
			contentBox.removeAllChildren();	
			var i= 0;
			data.each(function(o){
				i = i+1;
				idstring = "collection_add_2_"+i;
				var com = Edo.get(idstring);
				if(com != null){
					com.destroy();
				}
				var oneKnowledge = Edo.create({
					id : idstring,
					type: 'box',
					border: [0,0,0,0],
					padding: [0,0,0,0],
					width: '100%',
					//height: 85,
					layout: 'horizontal',
					children:[
						createKnowledgeDetail(o),
						Edo.create({
							type: 'box',
							border: [0,0,0,0],
							padding: [0,0,0,0],
							width: 130,
							layout: 'horizontal',
							children: [
								{type: 'label', width: 60, text: '<div ><a><span class="collection_edit"><font color = "black">编辑</font></span></a></div>',onclick : function(){
									createAddKeepBox(o.knowledgeId,o.titleName,"1");									
								}},{type: 'label', width: 60, text: '<div class="knowledge_list"><a><span class="collection_delete">删除</span></a></div>',onclick : function(){
									
									contentBox.removeChild(Edo.get(oneKnowledge.id));
									deleteKeep(o.id);
								}}
							]
						})
						 
					]
				});
				
				contentBox.addChild(oneKnowledge);
				
			});
		}
	}
	
	//设置检索的属性
	this.search = function(queryForm,url){
		if(url != null){
	    	myUrl = url;
	    }
	    
	    for(var key in queryForm){
	    	myInputForm[key] = queryForm[key];	
	    }	    
	    search();	
	}
	
	//获取表格
	this.getKnowledgeList = function(){
		return mainBox;
	}	
	function deleteKeep(treenodeid){
		cims201.utils.getData_Async('knowledge/keep/keep!deleteKeepById.action',{treenodeid:treenodeid},function(text){
			if(text == null || text == '') return;
			var data = Edo.util.Json.decode(text);
			creatalert(data);
		});
		cims201.utils.getData_Async('knowledge/keep/keep!listSubNodes.action',{nodeId:'1'},function(text){
			if(text == null || text == '') return;
			var data = Edo.util.Json.decode(text);
			leftTree.set('data',data);
		});
	}
	function createKnowledgeDetail(r){
		var knowledgeDetailLabel = Edo.create({
			type: 'label',
			width: '100%',
			height:'100%',
			text : '<a href="javascript:showKnowledgeDetail('+r.knowledgeId+',\''+r.nodeName+'\')";><span class="knowledge_list_title">'+r.nodeName+'</span></a>'
		});

    	return knowledgeDetailLabel;
	}
}