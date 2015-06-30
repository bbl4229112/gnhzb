
function createQuestionView(id,kData){
//最外层的窗口
	var myWin;
	
	//增加tag的窗口
	var myTagFormWin;

	var centerPanel;
	var rightPanel;
	var mainPanel;
	var topBar;
	var questioncontent;
	var commentPanel;
	var actionPanel;
	var centerContent;
	var centerTab;
	var bestAnswer;
	var knowledgeDetail;
	var knowledgeKeyword;
	
	var knowledgeAbstractpanel;
	var relatedKnowledge;
	var popKnowledge;
	var myTag;
	var myQuestionSupplement;
	
	 bestAnswer = Edo.create({
	    //id:bestAnswerId,
		type: 'panel',
		border: [1,1,1,1],
		padding: [0,0,0,0],
		visible: false,
		width: '100%',
		title: '<span class="knowledge_view_title">采纳答案</span>',
	    titleIcon: 'icon-cims201-ktitle',  
		//height: '100%',
		layout: 'vertical',
		children: []
	});
	
	 myQuestionSupplement = Edo.create({
	    //id:bestAnswerId,
		type: 'panel',
		border: [1,1,1,1],
		padding: [0,0,0,0],
		visible: false,
		width: '100%',
		title: '<span class="knowledge_view_title">问题补充</span>',
	    titleIcon: 'icon-cims201-ktitle',  
		//height: '100%',
		layout: 'vertical',
		children: []
	});

	var bestAnswerId=bestAnswer.id;
	
	if(kData=='error')
	
	{
    
	mainPanel='error'
	
	}
	else{
	globalid=id;		
	topBar = new createQuestionTopBar(kData.titleName,id).getTopBar();	
	questioncontent = new createQuestioncontent(kData.id,kData.questioncontent).getKnowledge();
	commentPanel = new createComment(kData.id,kData.ktype.name,bestAnswerId).getComment();
	actionPanel = new createAction(kData.attachments).getAction();
	if(null!=kData.domainNode)
	knoweldgedomain=kData.domainNode.id;
	else
		{
		knoweldgedomain=null;
		}
	knowledgecategroys=kData.categories;
	knowledgesourcefilepath=kData.flashFilePath;
	knowledgeDetail = new createQuestionDetail(kData.commentRecord,kData.uploadTime, kData.uploader, id).getKnowledgeDetail();
	knowledgeKeyword = new createKnowledgeKeyword(kData.keywords).getKnowledgeKeyword();
	//knowledgeAbstractpanel=new createKnowledgeAbstract(kData.abstractText).getKnowledgeAbstract();
	relatedKnowledge = new createRelatedKnowledge(id).getRelatedKnowledge();
	popKnowledge = new createPopKnowledge(id).getPopKnowledge();

	//实例化这些对象
	//alert("knowledgeview"+id);
	
	
	centerContent = Edo.create({
		//id: 'centerContent',
		selectedIndex: 0,
		layout: 'viewstack',
		type: 'box',
		border: [0,0,0,0], 
	    padding: [0,0,0,0], 
		width: '100%',
		//height: '20%',
		//verticalScrollPolicy: 'auto',
		children: [commentPanel, actionPanel]
	});
	
	centerTab = Edo.create({
		//id: 'centerTab',
		type: 'tabbar',
		selectedIndex: 0,
		border: [0,0,1,0],
		style: 'background: white',
		onselectionchange: function(e){
			centerContent.set('selectedIndex', e.selectedIndex);
		},
		//如果kData.ktype.id=2，即知识类型是'问题'的时候
		children: 
		    [{type: 'button',icon: 'icon-cims201-comment', text: '回答列表'}
		    ]
	});

	//{type: 'button',icon: 'icon-cims201-action', text: '问题补充'}
	centerPanel = Edo.create({
		//id: 'centerPanel',    
	    type: 'box',   
	    border: [0,0,0,0], 
	    padding: [0,0,0,0],    
	    width: '100%',
	    //height: '100%',            
	    layout: 'vertical',    
	    children: [
	        topBar,questioncontent,myQuestionSupplement,bestAnswer, centerTab, centerContent
	    ]
	});
		
	rightPanel = Edo.create({
		//id: 'rightPanel',    
	    type: 'box',   
	    border: [0,0,0,0], 
	    padding: [0,0,0,6], 
	    width: 230,
	    //height: '100%',              
	    layout: 'vertical',    
	    children: [
	       knowledgeKeyword,knowledgeDetail,relatedKnowledge,popKnowledge	    
	    ]
	});
	
	mainPanel = Edo.create({
		//id: 'mainPanel',    
	    type: 'box',
	    border: [0,0,0,0], 
	    padding: [0,0,0,0], 
	    width: '100%',
	    //height: '100%',                 
	    layout: 'horizontal',    
	    children: [
	        //leftPanel,
	        centerPanel,rightPanel
	    ]
	});
	

	
	//异步加载tag
	cims201.utils.getData_Async('knowledge/tag/tag!list.action',{knowledgeID:id},function(text){
		var ddd;
		if(text == null || text == ''){
				ddd = null;
		}else{			
			createTagBar(text,id);			
		}
	});
	//构建tag显示的框
	function createTagBar(text,knowledgeID){
		if(text == null || text == ''){
			if(myWin){
				myWin.hide();
			}
			return;
		}
		ddd = Edo.util.Json.decode(text);			
		var outStr_personal = '';
		var personalTagStr = '';
		
		ddd[0].each(function(o){
			outStr_personal += '<a href="javascript:showTagKnowledgeList('+o.id+',\''+o.tagName+'\')";><span class="knowledge_tag_personal">';
			outStr_personal += o.tagName;
			outStr_personal += '</span></a>';
			personalTagStr += o.tagName;
			personalTagStr += ',';
		});
		
		var outStr_pop = '';
		ddd[1].each(function(o){
			outStr_pop += '<a href="javascript:showPopTagKnowledgeList('+o.id+',\''+o.tagName+'\')";><span class="knowledge_tag_pop">';
			outStr_pop += o.tagName;
			outStr_pop += '</span></a>';
		});
		
		if(myTag == null){
			myTag = Edo.create({
				type: 'box',
				border: [0,0,0,0],
				padding: [0,0,0,12],
				width: '100%',
				//bodyCls: 'cims201_knowledge_topbar_back',
				//height: '20%',
				layout: 'horizontal',
				children:[
					{
						type: 'label',
						style: 'cursor:pointer;',
						text: '<span class="cims201_add">添加标签</span>',
						onclick: function(e){
							if(myTagFormWin == null){
								var myTagInput = Edo.create(
									{type: 'text', width: 220,text:personalTagStr, valid: cims201.utils.validate.noEmpty}
								);
								var myTagForm = Edo.create({
									type: 'box',
									border: [0,0,0,0],
									padding: [0,0,0,0],
									//width: 200,
									height: 100,
									layout: 'vertical',
									children: [
										myTagInput,
										{type:'label', text:'提示：多个标签用,隔开'},
										{type: 'button', text: '确定提交', onclick: function(e){
											if(myTagForm.valid()){
												var tagText = myTagInput.get('text');
												cims201.utils.getData_Async('knowledge/tag/tag!save.action',{knowledgeID:knowledgeID, tagname:tagText},function(rText){
													createTagBar(rText,knowledgeID);
													var rtrt = Edo.util.Json.decode(rText);	
													var myStr = '';
													rtrt[0].each(function(o){
														myStr += o.tagName;
														myStr += ',';
													});
													myTagInput.set('text',myStr);
												});				
												
												creatalert('增加标签成功!',myTagFormWin);
											//	myTagFormWin.hide();
											//	alert('增加标签成功!');
											}
										}}
									]
								});
								myTagFormWin = cims201.utils.getWin(220,100,'添加标签',myTagForm);
							}
							//增加遮板
							//myTagFormWin.show(300,300,true);	
								setWinScreenPosition(220,100,myTagFormWin,knowledgeID)
						}
					}				
				]
			});
			topBar.addChild(myTag);
		}
		//首先删除原来的tag标签
		if(myTag.children.length > 2){
			myTag.removeChildAt(0);
			myTag.removeChildAt(0); 
		}
		
		//然后添加新的tag标签
		myTag.addChildAt(0,Edo.create({
			type: 'label',
			text: '<div class="cims201_knowledge_topbar_tag">大众标签:'+outStr_pop+'</div>'
		}));
		
		myTag.addChildAt(1,Edo.create({
			type: 'label',
			style: 'padding-right:10px;',
			text: '<div class="cims201_knowledge_topbar_tag">个人标签:'+outStr_personal+'</div>'
		})); 
		
		
	}	
	
		
	}	
	
	return mainPanel;
}



function createQuestionTopBar(myTitle,id){

	var title = myTitle;
	//var title = '基于本体驱动的企业语义';
	var title_length;
	var title_size;
	title_length = title.length;
	if(title_length > 26){
		title = title.substring(0,26);
		title_length = 26;
		title_size = 14;
	}else if(title_length < 11){
		title_length = 11;
		title_size = 21;
	}else{
		title_size = (-0.47*title_length+26.13)-((-0.47*title_length+26.13)%1);
	}
	
	//左边的标题栏knowledge_sim
	var leftTitle = '';
	leftTitle += '<span class="cims201_knowledge_topbar_title">';
	leftTitle += '<span style="font-size:'+title_size+'px;">';
	leftTitle += title;
	leftTitle += '</span>';
	leftTitle += '</span>';
	
	var topbar_style = Edo.create({
		type: 'label',
		width: '100%',
		//text: leftTitle+rightBt
		text: leftTitle
	});
	
	var topBar = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		width: '100%',
		bodyCls: 'cims201_knowledge_topbar_back',
		layout: 'vertical',
		height: 60,
		children: [
			Edo.create({
				type: 'box',
				border: [0,0,0,0],
				padding: [2,0,0,12],
				width: '100%',
				//bodyCls: 'cims201_knowledge_topbar_back',
				//height: '80%',
				children:[
					topbar_style
				]
			})
		]
	});

	
	//右边的操作按钮
	var currentBtIndex = 1;
     //修改域
		cims201.utils.getData_Async('privilege/show-button!whetherShowModifyDTreeBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<span id="modifydtreebt'+id+'" class="cims201_knowledge_topbar_bt'+(currentBtIndex++)+'"><a href=javascript:modifydtree('+id+')><span class="cims201_knowledge_topbar_modifydtree">';
			rightBt += '修改域别';
			rightBt +='</span></a></span>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}

		//修改类别
		cims201.utils.getData_Async('privilege/show-button!whetherShowModifyCTreeBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<a href=javascript:modifyctree('+id+')><span class="cims201_knowledge_topbar_bt'+(currentBtIndex++)+'"><span class="cims201_knowledge_topbar_modifyctree">';
			rightBt += '修改类别';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
		
		});
	
	    });
	    
	
	
	this.getTopBar = function(){
		return topBar;
	}
}
function createQuestioncontent(kid,questioncontent){
var knowledgeModule;
var myqsBox=null;
var myWin2 = null;
	var knowledgeModule;
	if(null!=Edo.get('knowledgeModule'+kid))
	{knowledgeModule=Edo.get('knowledgeModule'+kid);
	
	}else{




	//创建知识显示的模块，该模块从指定路径加载知识	
knowledgeModule=Edo.create({
        type:'box',
        width:'100%',    
        border:[0,0,0,0],	 
	    children:[{
	         type:'boxtext',
			 width: '100%',
			 height: 250,
			 style: 'overflow:auto;',
			 text:'<span style="font-size:16px;">'+'&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp'+questioncontent+'</span>',
			 id: 'knowledgeModule'+kid,
			 padding: [5,2,5,2]
			 }
		 
			 ]
       
		
	
	});
	
		//问题补充按钮
		cims201.utils.getData_Async('privilege/show-button!whetherShowModifyCTreeBtn.action',{knowledgeId:kid}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			knowledgeModule.addChild(Edo.create({
					            type: 'button', 			                   
					            text: '问题补充',
					            onclick: function(e){
				
										if(myWin2 == null){			
											
											    var qseditor = Edo.create({
												type: 'textarea',
												width: '100%',
												height: '70%'
											});
											var editorBox = Edo.create({
												type: 'box',
												border: [0,0,0,0],
												padding: [0,0,0,0],
												width: 500,
												height: 250,
												layout: 'vertical',
												children: [
													qseditor,
													{type: 'button', text: '确定提交', onclick: function(e){					
														var qs = cims201.utils.getData('knowledge/knowledge!saveQuestionSupplement.action',{id:kid,questionsupplement:qseditor.getValue()});
													   
													    if(myqsBox!=null){
													    	knowledgeModule.removeChild(myqsBox);
													    }	 
													    	 if(qs.questionsupplement!=null){
																myqsBox = new creatQSBox(qs.questionsupplement).getqsbox();
																knowledgeModule.addChildAt(1,myqsBox);}					    	 					    	 
													    
														myWin2.hide();
													}}
												]
											});
											myWin2 = cims201.utils.getWin(500,200,'问题补充',editorBox);
										}
										
										//增加遮罩	
										myWin2.show(500,200,true);	
										}       
					        }));
		     }		
		});

//问题加载时若有问题补充生成问题补充栏
	var qsdata = cims201.utils.getData('knowledge/knowledge!showquestionsupplement.action',{id:kid});
	//alert("data  "+qsdata.questionsupplement);
	if(qsdata.questionsupplement!=null){
		myqsBox = new creatQSBox(qsdata.questionsupplement).getqsbox();
		knowledgeModule.addChildAt(1,myqsBox);}
	
	
	}
	

	this.getKnowledge = function(){
		return knowledgeModule;}


}

//创建当前问题信息面板
function createQuestionDetail(comment, uploadtime, uploader, knowledgeID){
	var kdd = '';
	kdd += '<span class="knowledge_view_bigscore">';
	if(comment){
		kdd += (comment.rate<5?comment.rate:5);
	}else{
		kdd += 3.5;
	}	
	kdd += '</span>';
	var knowledge_view_score_total;
	if(null!=Edo.get('totalscore_'+knowledgeID))
	Edo.get('totalscore_'+knowledgeID).destroy();
	 knowledge_view_score_total = Edo.create({
		id: 'totalscore_'+knowledgeID,
		type: 'label',
		text: kdd
	});
	
	var knowledge_view_score_star = null;
	
	if(comment){
		knowledge_view_score_star = new starJudge_big(onStarView,starJudge,(comment.rate<5?comment.rate:5),knowledgeID);
	}else{
		knowledge_view_score_star = new starJudge_big(onStarView,starJudge,3.5,knowledgeID);
	}
	
	var knowledge_view_score_judge = Edo.create({
		type: 'label',
		text: ''
	});
	
	function onStarView(index){
		if(index){
			if(index == 5){
				knowledge_view_score_judge.set('text','力荐');
			}else if(index == 4){
				knowledge_view_score_judge.set('text','推荐');
			}else if(index == 3){
				knowledge_view_score_judge.set('text','还行');
			}else if(index == 2){
				knowledge_view_score_judge.set('text','较差');
			}else if(index == 1){
				knowledge_view_score_judge.set('text','很差');
			}
		}
	}
	
	var knowledge_view_score_box = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,10,0],
		width: '100%',
		layout: 'horizontal',
		children: [knowledge_view_score_total,knowledge_view_score_star.getBigStar(),knowledge_view_score_judge]
	});
		
	//已经评价的人数
	var knowledge_view_judge_total_str = '';
	
	
	if(comment){
		knowledge_view_judge_total_str += '<span class="knowledge_view_general">已有'+(comment.commentCount?comment.commentCount:0)+'人回答,'+(comment.ratecount?comment.ratecount:0)+'人评分</span>';
		knowledge_view_judge_total_str += '<br>';
		knowledge_view_judge_total_str += '<span class="knowledge_view_general">浏览：'+(comment.viewCount?comment.viewCount:0)+'次';
		knowledge_view_judge_total_str += '<br>';
		knowledge_view_judge_total_str += '提问时间：'+uploadtime;
		knowledge_view_judge_total_str += '<br>';
		knowledge_view_judge_total_str += '<hr>';
		knowledge_view_judge_total_str += '提问者：<a href="javascript:showAuthorKnowledgeList('+uploader.id+',\''+uploader.name+'\')";>'+uploader.name+'</a>';
		knowledge_view_judge_total_str += '</span>';
		var knowledge_view_judge_total = Edo.create({
			type: 'label',
			text: knowledge_view_judge_total_str 
		});
	}
	//其他的浏览信息

	//当前知识信息面板
	var rightKrowledgeDetail = Edo.create({
		border: [1,1,1,1], 
	    padding: [12,0,0,12],    
	    type: 'panel',
	    title: '<span class="knowledge_view_title">相关信息</span>',
	    titleIcon: 'icon-cims201-ktitle',    
	    width: '100%',
	    height: '200',             
	    layout: 'vertical',    
	    children: [
	        knowledge_view_score_box,
	        knowledge_view_judge_total
	    ]
	});
	
	this.getKnowledgeDetail = function(){
		return rightKrowledgeDetail;
	}
}

/**
热门回答openNewTab
*/
function createPopKnowledge(id){
	
	//热门评论	
	var recomendedKnowledgePanel = Edo.create({
		border: [1,1,1,1], 
	    padding: [0,5,0,12],    
	    type: 'panel',
	    title: '<span style="font-weight:bold;">热门回答</span>',
	    titleIcon: 'icon-cims201-ktitle',    
	    width: '100%',
	    //height: 400,             
	    layout: 'vertical',    
	    children: []
	});
	
	
	cims201.utils.getData_Async('comment/comment!listHotComment.action',{knowledgeID:id},function(text){
		var popCommentStr = '';	
		var commentList = Edo.util.Json.decode(text);
		commentList.each(function(o){
			recomendedKnowledgePanel.addChild({
				type: 'label',
				width: '100%',
				style: 'font-size:12px;color:rgb(51,102,153);',
				text: o.commenterName+'&nbsp发表于<span class="cims201_comment_date">'+o.commmentTime.substring(0,10)+'</span>'				
			});
			
			recomendedKnowledgePanel.addChild({
				type: 'boxtext',
				width: 230,
				style: 'font-size:12px;',
				text: o.content				
			});
			
			recomendedKnowledgePanel.addChild({
				type: 'label',
				width: '100%',
				style: 'font-size:12px;',
				text: '<a href=javascript:addKnowledgeReply('+o.id+','+id+');>回复</a> <a href=javascript:voteKnowledgeComment('+o.id+',1);><span style="color:rgb(188,41,49);">支持</span></a>(<span id="'+o.id+'_vote">'+(o.supportVoteCount?o.supportVoteCount:0)+'</span>) <a href=javascript:voteKnowledgeComment('+o.id+',0);>反对</a>(<span id="'+o.id+'_againstvote">'+(o.againstVoteCount?o.againstVoteCount:0)+'</span>)<hr>'				
			});			
		});
	});
	this.getPopKnowledge = function(){
		return recomendedKnowledgePanel;
	}	
}


//创建问题补充界面
function creatQSBox(c){
    var cc = null;
	if(c != null && c!= '' ){		
			
		cc = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,12],
			width: '100%',
			bodyCls: 'cims201_comment_list_f1',
			children: [
				{type: 'label', text:'<span style="font-weight:bold;font-size:16px;">问题补充：</span>'},
				{type: 'boxtext', text:'<span style="font-size:16px;">'+c+'</span>' }

			]
		});
	
	}	
	this.getqsbox = function(){
		return cc;
	}	
}
