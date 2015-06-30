//standard-list的box实现
function createStandardKnowledgeList_box(){
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
			data.each(function(o){			
				var title_outStr = '';
				if(o.titleNameChi.length > 4){
					title_outStr += o.titleNameChi.substring(0,4)+'...';
				}else{
					title_outStr += o.titleNameChi;
				}
				var oneKnowledge = Edo.create({
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
							width: 60,
							layout: 'vertical',
							children: [
								{type: 'label', width: '100%', text: ''},
								{type: 'label', width: 80, text: '<div class="knowledge_list"><a href=javascript:showKnowledgeDetail('+o.id+',"'+title_outStr+'");><span class="knowledge_list_detail">详情</span></a></div>'}
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
		if(r.titleNameChi.length > 4){
			title_outStr += r.titleNameChi.substring(0,4)+'...';
		}else{
			title_outStr += r.titleNameChi;
		}
		if(r.titleNameEng!=null){
			var titleNameLabel = Edo.create({
			type: 'label',
			width: '100%',
			text: '<a href=javascript:showKnowledgeDetail('+r.id+',"'+title_outStr+'");><span class="knowledge_list_title">'+r.titleNameChi+'('+r.titleNameEng+')'+'</span></a>'
		});
		}else{
			var titleNameLabel = Edo.create({
			type: 'label',
			width: '100%',
			text: '<a href=javascript:showKnowledgeDetail('+r.id+',"'+title_outStr+'");><span class="knowledge_list_title">'+r.titleNameChi+'</span></a>'
		});
		}		
		
		knowledgeDetailBox.addChild(titleNameLabel);
		
		var showLabel_str = '';
		showLabel_str += '[<span style="color:seagreen;">标准号</span>]';
		if(r.biaozhunCode!=null)
		showLabel_str +=r.biaozhunCode;
		showLabel_str += '[<span style="color:seagreen;">所属行业</span>]';
		if(r.beyondedIndustry!=null)
		showLabel_str +=r.beyondedIndustry;
		showLabel_str += '[<span style="color:seagreen;">发布时间</span>]';
		if(r.publishDate!=null)
		showLabel_str +=r.publishDate;
		showLabel_str += '[<span style="color:seagreen;">执行时间</span>]';
		if(r.implDate!=null)
		showLabel_str +=r.implDate;			
		
		var showLabel = Edo.create({
			type: 'label',
			width: '100%',
			text: showLabel_str
		});		
		knowledgeDetailBox.addChild(showLabel);
		var abstractText = Edo.create({
			type: 'boxtext', 
			width: cims201.utils.getScreenSize().width-400,
			text: '[<span style="color:seagreen;">摘要</span>]'+r.abstractString
		});
		if(null!=r.abstractString)
		knowledgeDetailBox.addChild(abstractText);
		
    	return knowledgeDetailBox;
	}
}

