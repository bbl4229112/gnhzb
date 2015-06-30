var editorcreate = null;
//knowledge-list的box实现
function createKnowledgeList_box(){
	//定义摘要的字数和行数
	var abstractTextLength = 50;
	var abstractTextLine = 2; 
	//默认多少行
	var defaultPageSize = 10;
	
	var myUrl = {};
	var myInputForm = {};
	
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
		text: '没有查询到任何数据!'
	});
	
			
	var mainBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,20,0],
		width: '100%',
		
		children: [contentBox, myPager, bBar]
		//children: [contentBox]
	});
	
	//检索数据库，填充表格
	function search(){    
	    if(myUrl != null && myUrl != ''){		    			    
		    myInputForm.index = myPager.index;    
		    myInputForm.size = myPager.size;		   
	
		    var rData = cims201.utils.getData(myUrl,myInputForm);
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
		}
	}
	
	//根据data显示知识
	function showKnowledge(data){
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
				var existdata =  cims201.utils.getData(
						'knowledge/keep/keep!keepIsExisted.action',
						{
							knowledgeId : o.id
						});
				
				var isConnected = existdata;
				var connecttext;
				if(isConnected == "收藏"){
					connecttext = '<div ><a><span class="collection_add">'+isConnected+'</span></a></div>';
				}else{
					connecttext = '<div ><span class="collection_already"><font color="red">'+isConnected+'</font></span></div>';
				}
				var title_outStr = '';
				if(o.titleName.length > 4){
					title_outStr += o.titleName.substring(0,4)+'...';
				}else{
					title_outStr += o.titleName;
				}
				var keepanddetailchildren = [
				                             {type: 'label',id : idstring, width: 60, text:connecttext ,onclick : function(){
				                            	 if(this.text == '<div ><a><span class="collection_add">收藏</span></a></div>' ){
				                            		 createAddKeepBox(o.id,o.titleName);
				                            		 this.set('text','<div ><a><span class="collection_edit"><font color = "black">编辑</font></span></a></div>');
				                            	 }else if(this.text == '<div ><a><span class="collection_edit"><font color = "black">编辑</font></span></a></div>'){
				                            		 createAddKeepBox(o.id,o.titleName,"1");
				                            	 }
				                             }},
				                             {type: 'label', width: 60, text: '<div class="knowledge_list"><a href="javascript:showKnowledgeDetail('+o.id+',\''+title_outStr+'\')";><span class="knowledge_list_detail">详情</span></a></div>'}
				                             ];
				var keepornotdata=cims201.utils.getData('custom/customization!kKeep.action');
				if(keepornotdata == false || keepornotdata == 'false'){
					keepanddetailchildren.splice(0,1);
				}
				var oneKnowledge = Edo.create({
					type: 'box',
					border: [0,0,0,0],
					padding: [0,0,0,0],
					width: '100%',
					//height: 85,
					layout: 'horizontal',
					children:[
						//{type: 'label', width: '95%', text: createKnowledgeDetail(o)},
						createKnowledgeDetail(o),
						Edo.create({
							type: 'box',
							border: [0,0,0,0],
							padding: [0,0,0,0],
							width: 120,
							layout: 'horizontal',
							children: keepanddetailchildren
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
	function createKnowledgeDetail(r){
		var knowledgeDetailBox = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,0],
			width: '100%',
			height:'100%',
			bodyCls: 'knowledge_list',
			layout: 'vertical'
		});
		
		var title_outStr = '';
		if(r.titleName.length > 4){
			title_outStr += r.titleName.substring(0,4)+'...';
		}else{
			title_outStr += r.titleName;
		}
		//alert('<a href=javascript:showKnowledgeDetail('+r.id+',"'+title_outStr+'");><span class="knowledge_list_title">'+r.titleShowName+'</span></a>');
		var xml_Str = '';
		if(r.knowledgetype!=null&&r.knowledgetype.id!=null&&r.knowledgetype.name == '岗位知识') {
			xml_Str = '&nbsp;&nbsp;&nbsp;&nbsp;<a title="此知识属于岗位知识，点击查询其他岗位知识。" href="javascript:showKnowledgetypeKnowledgeList('+r.knowledgetype.id+',\''+r.knowledgetype.name+'\')";><img src=\'./css/xml.jpg\'></a>';
		}
		var titleNameLabel = Edo.create({
			type: 'label',
			width: '100%',
			text: '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_list_title">'+r.titleShowName+'</span></a>'+xml_Str
		});
		knowledgeDetailBox.addChild(titleNameLabel);
		
		var authorLabel_str = '';
		authorLabel_str += '[作者]';
		r.KAuthors.each(function(o){
    		authorLabel_str += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>';
    		
    		authorLabel_str += o.showname;
    		authorLabel_str += '&nbsp</a>';
    	});
    	authorLabel_str += '[关键字]';           	    	
    	if(r.keywords){
     		r.keywords.each(function(o){
	    		authorLabel_str += '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>';
	    		authorLabel_str += o.showname;
	    		authorLabel_str += '&nbsp</a>';
	    	});
    	}
    	if(r.knowledgetype!=null&&r.knowledgetype.id!=null&&r.knowledgetype.name!=null){
    		authorLabel_str += '[<span style="color:blue;">所属类别</span>]';
    		authorLabel_str += '<a href="javascript:showKnowledgetypeKnowledgeList('+r.knowledgetype.id+',\''+r.knowledgetype.name+'\')";>'+r.knowledgetype.name + '</a>';
    	}
    	

		var authorLabel = Edo.create({
			type: 'label',
			width: '100%',
			text: authorLabel_str
		});
		
		knowledgeDetailBox.addChild(authorLabel);
		var abstractText = Edo.create({
			type: 'boxtext', 
			width: cims201.utils.getScreenSize().width-400,
			//height: '100%',
			//height: 60,
			text: '[摘要]'+r.abstractText
		});
		if(null!=r.abstractText)
		knowledgeDetailBox.addChild(abstractText);
		
    	return knowledgeDetailBox;
	}
}

