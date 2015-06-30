/**
 * 美国专利列表页面 jiangdingding添加 2013-5-19
 * @returns {createPatentList}
 */
function createPatentUSList(){
	//定义摘要的字数和行数
	var abstractTextLength = 50;
	var abstractTextLine = 2; 
	//默认多少行
	var defaultPageSize = 50;
	
	var myUrl = {};
	var myInputForm = {};
	
	//检索参数
	var fromsearch = "2";	
	var d = "";
	var TERM1 = "";
	var FIELD1 = "";
	var co1 = "";
	var TERM2 = "";
	var FIELD2 = "";
	var totallist = "";
	var keyword = "";
	var Srch1 = "";
	var s1 = "";
	var s2 = "";
	
	
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
		border: [1,1,1,1],
		padding: [5,5,5,5],
		width: '100%',
		height: '100%',
		layout: 'vertical',
		verticalAlign:'0',
		children: [
//					{
//						   type:'panel',
//						   border:[0,0,0,0],
//						   padding:[0,0,0,0],
//						   title:'中国专利检索列表',
//						   width:'100%'       	   
//					},
		           contentBox, 
		           myPager, 
		           bBar
		           ]
		//children: [contentBox]
	});
	
	var headbox = Edo.create({
	       
		   type:'box',
		   border:[0,0,0,0],
		   padding:[0,0,0,0],
		   width:'100%',
		   children:[
		            
		             ]
	});
	

	//检索数据库，填充表格
	function search(){		
	    if(myUrl != null && myUrl != ''){		    			    
		    //myInputForm.pg = myPager.index;
		    //myInputForm.size = myPager.size;
	    	var rData;
	    	if(myPager.index >0){//从列表页面检索	    		
		    	myUrl = 'patent/patent!searchPatentUS.action?';
		    	pg = myPager.index+1;
		    	var fromsearch = "2";
		    	myInputForm = {d:d,TERM1:TERM1,FIELD1:FIELD1,co1:co1,TERM2:TERM2,FIELD2:FIELD2,totallist:totallist,keyword:keyword,Srch1:Srch1,s1:s1,s2:s2,pg:pg,fromsearch:fromsearch};    	
		    	rData = cims201.utils.getData(myUrl,myInputForm);		    	
		    }else{		    	
		    	rData = cims201.utils.getData(myUrl,myInputForm);
		    	//设置检索参数
		    	d = rData.d;
		    	TERM1 = rData.TERM1;
		    	FIELD1 = rData.FIELD1;
		    	co1 = rData.co1;
		    	TERM2 = rData.TERM2;
		    	FIELD2 = rData.FIELD2;
		    	totallist = rData.total;
		    	keyword = rData.keyword;
		    	s1 = rData.s1;
		    	s2 = rData.s2;
		    }
	    	contentBox.removeAllChildren();	
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
			contentBox.addChild(headbox);			
			var i = 0;
			data.each(function(o){				
				i = i+1;				
				var isConnected = data;
				var connecttext;
				if(isConnected == "收藏"){
					connecttext = '<div ><a><span class="collection_add">'+isConnected+'</span></a></div>';
				}else{
					connecttext = '<div ><span class="collection_already"><font color="red">'+isConnected+'</font></span></div>';
				}
				idstring = "collection_add_"+i;
				var ke = Edo.get(idstring);
				if(ke != null){
					ke.destroy();
				}
				var title_outStr = '';
				if(o.titleName.length > 4){
					title_outStr += o.titleName.substring(0,4)+'...';
				}else{
					title_outStr += o.titleName;
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
							width: 220,
							layout: 'horizontal',
							children: [
								{type: 'label', width: 60, text: '<div class="knowledge_list"><a href="javascript:searchpatentusdetail('+'\''+o.appCode+'\',\''+o.titleName+'\',\''+o.fullurl+'\',\''+keyword+'\')";><span class="knowledge_list_detail">详情</span></a></div>'},
								{type: 'label', width: 60, text: '<div class="knowledge_list"><a href="javascript:downuspatenttext('+'\''+o.appCode+'\',\''+o.titleName+'\',\''+o.fullurl+'\',\''+keyword+'\')";><span class="cims201_knowledge_topbar_download">下载</span></a></div>'},
								{type: 'label', width: 100, text: '<div class="knowledge_list"><a href="javascript:redownuspatenttext('+'\''+o.appCode+'\',\''+o.titleName+'\',\''+o.fullurl+'\')";><span class="cims201_knowledge_topbar_download">重新下载</span></a></div>'}
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
	};
	
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
		if(r.titleName.length > 80){
			title_outStr += r.titleName.substring(0,80)+'...';
		}else{
			title_outStr += r.titleName;
		}

		var titleNameLabel = Edo.create({
			type: 'box',
			border:[0,0,0,0],
			padding:[0,0,0,0],
			width: '100%',
			layout:'horizontal',
			children:[
			          {type: 'label',width:'100',text: '<a href="javascript:searchpatentusdetail('+'\''+r.appCode+'\',\''+r.titleName+'\',\''+r.fullurl+'\',\''+keyword+'\')";><span class="knowledge_list_title">'+r.appCode+'</span></a>'},
			          {type: 'label',text: '<a href="javascript:searchpatentusdetail('+'\''+r.appCode+'\',\''+r.titleName+'\',\''+r.fullurl+'\',\''+keyword+'\')";><span class="knowledge_list_title">'+title_outStr+'</span></a>'}
			          ]
		});
		knowledgeDetailBox.addChild(titleNameLabel);
		    		
    	return knowledgeDetailBox;
	}
	
	//获取表格
	this.getPatentList = function(){
		return mainBox;
	};

	
}
function alreadyCollect(idstring){
	alert(idstring.toString());
	Edo.get(idstring.toString).set('text','收藏成功');
}


//显示美国专利详细
function searchpatentusdetail(appCode,titleName, fullurl, keyword){
	openNewTab('patentusdetaillist', 'patentusdetaillist',
			"<div class=cims201_tab_font align=center>"+titleName+"</div>", {appCode:appCode,titleName:titleName,fullurl:fullurl, keyword:keyword, btIcon:''});		
}


//下载美国专利文件
function downuspatenttext(appCode,titleName, fullurl, keyword){
	//设置界面loading							
	createLoading();
	window.location.href="patent/patent!downUSPatentText.action?appCode="+appCode+"&patent_name="+titleName+"&fullurl="+fullurl+"&keyword="+keyword;
	setTimeout(deleteMask, 3000);
}

//重新下载美国专利文件
function redownuspatenttext(appCode,titleName, fullurl, redown){
	//设置界面loading
	createLoading();
	var redown = "1";
	window.location.href="patent/patent!downUSPatentText.action?appCode="+appCode+"&patent_name="+titleName+"&fullurl="+fullurl+"&redown="+redown;
	setTimeout(deleteMask, 3000);
}

