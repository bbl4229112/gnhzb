/**
 * 专利列表页面 jiangdingding添加 2013-5-19
 * @returns {createPatentList}
 */
function createPatentList(){
	//定义摘要的字数和行数
	var abstractTextLength = 50;
	var abstractTextLine = 2; 
	//默认多少行
	var defaultPageSize = 10;
	
	var myUrl = {};
	var myInputForm = {};
	
	//选择的专利数据库
	var selectbase = "0";
	var dbchange = false;
	var sSearchContentTmp = "";
	var fromsearch = "2";
	var keyword = null;
	
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
	    	//从列表页面检索
	    	var rData;
	    	//换页
		    if(myPager.index >0 || dbchange == true){
//		    	headbox.removeAllChildren();
		    	myUrl = 'patent/patent!searchPatentCN.action?';
		    	pg = myPager.index+1;
		    	//alert(pg);
		    	myInputForm = {selectbase:selectbase,sSearchContentTmp:sSearchContentTmp,pg:pg,fromsearch:fromsearch};    	
		    	rData = cims201.utils.getData(myUrl,myInputForm);		    	
			    dbchange = false;
		    }else{
		    	rData = cims201.utils.getData(myUrl,myInputForm);
		    	keyword = rData.keyword;
		    	headbox.removeAllChildren();
				var typecountbox = Edo.create({
					   type:'box',
					   border:[0,0,0,0],
					   padding:[0,0,0,0],
					   layout:'horizontal',
					   horizontalGap:'10',
					   children:[
								 {
					            	 type:'label',
					            	 text:'共有 ： 发明'+'<a href="#"><font color="red">'+rData.fmRecord+'</font></a>条；',
					            	 onclick:function(){				            		 	
					            		 	selectbase = '11';
					            		 	sSearchContentTmp = rData.sSearchContentTmp;
					            		 	dbchange = true;
					            			search();
					            			
					            	 }
					             },
					             {
					            	 type:'label',
					            	 text:'实用新型'+'<a href="#"><font color="red">'+rData.xxRecord+'</font></a>条；',
					            	 onclick:function(){				            		 	
					            		 	selectbase = '22';
					            		 	sSearchContentTmp = rData.sSearchContentTmp;
					            		 	dbchange = true;
					            			search();
					            			
					            	 }
					             },
					             {
					            	 type:'label',
					            	 text:'外观设计'+'<a href="#"><font color="red">'+rData.wgRecord+'</font></a>条',
					            	 onclick:function(){				            		 	
					            		 	selectbase = '33';
					            		 	sSearchContentTmp = rData.sSearchContentTmp;
					            		 	dbchange = true;
					            			search();
					            			
					            	 }
					             }
					            ]
				});
				headbox.addChild(typecountbox);
		    }
		    sSearchContentTmp = rData.sSearchContentTmp;
		    
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
				if(o.titleName.length > 20){
					title_outStr += o.titleName.substring(0,20)+'...';
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
								{type: 'label', width: 60, text: '<div class="knowledge_list"><a href="javascript:searchpatentdetailcn('+'\''+o.appCode+'\',\''+o.titleName+'\',\''+o.patentType+'\',\''+keyword+'\')";><span class="knowledge_list_detail">详情</span></a></div>'},
								{type: 'label', width: 60, text: '<div class="knowledge_list"><a href="javascript:downcnpatenttext('+'\''+o.appCode+'\',\''+o.patentType+'\',\''+keyword+'\')";><span class="cims201_knowledge_topbar_download">下载</span></a></div>'},
								{type: 'label', width: 100, text: '<div class="knowledge_list"><a href="javascript:redowncnpatenttext('+'\''+o.appCode+'\',\''+o.patentType+'\')";><span class="cims201_knowledge_topbar_download">重新下载</span></a></div>'}
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
	this.getPatentList = function(){
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
		if(r.titleName.length > 40){
			title_outStr += r.titleName.substring(0,40)+'...';
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
			          {type: 'label',width:'100',text: '<a href="javascript:searchpatentdetailcn('+'\''+r.appCode+'\',\''+r.titleName+'\',\''+r.patentType+'\',\''+keyword+'\')";><span class="knowledge_list_title">'+r.appCode+'</span></a>'},
			          {type: 'label',text: '<a href="javascript:searchpatentdetailcn('+'\''+r.appCode+'\',\''+r.titleName+'\',\''+r.patentType+'\',\''+keyword+'\')";><span class="knowledge_list_title">'+title_outStr+'</span></a>'}
			          ]
		});
		knowledgeDetailBox.addChild(titleNameLabel);
		
		var appcode_str = '';
		appcode_str += '[申请（专利）号]';		
    	appcode_str += '<a href="javascript:searchpatentdetailcn('+'\''+r.appCode+'\',\''+r.titleName+'\',\''+r.patentType+'\')";>';    		
    	appcode_str +=r.appCode;
    	appcode_str += '&nbsp</a>';
    		
    	return knowledgeDetailBox;
	}
	
}
function alreadyCollect(idstring){
	alert(idstring.toString());
	Edo.get(idstring.toString).set('text','收藏成功');
}


//显示中国专利详细
function searchpatentdetailcn(appCode,titleName, patentType, keyword){
	openNewTab('patentcndetaillist', 'patentcndetaillist',
			"<div class=cims201_tab_font align=center>"+titleName+"</div>", {appCode:appCode,patentType:patentType,keyword:keyword, btIcon:''});		
}


//下载专利文件
function downcnpatenttext(appCode,patentType, keyword){
	//设置界面loading							
	createLoading();
//	keyword = encodeURIComponent(keyword);
	window.location.href="patent/patent!downCNPatentText.action?appCode="+appCode+"&patentType="+patentType+"&keyword="+keyword;
	setTimeout(deleteMask, 3000);
}

//下载专利文件
function redowncnpatenttext(appCode,patentType){
	//设置界面loading
	createLoading();
	var redown = "1";
	window.location.href="patent/patent!downCNPatentText.action?appCode="+appCode+"&patentType="+patentType+"&redown="+redown;
	setTimeout(deleteMask, 3000);
}

