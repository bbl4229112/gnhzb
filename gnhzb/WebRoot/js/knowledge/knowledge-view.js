//定义配置参数
var knowledgeDetailWidth = 130;
//相关推荐文档的条数
var relatedKnowledgeSize = 4;
//同类热门知识显示的条数 
var recomendedKnowledgeSize = 4;
//增加评论时候的弹出面板
var addKnowledgeReplyWin;
//记录知识的域和分类用于知识分类和域的修改
var knoweldgedomain;
var knowledgecategroys;
var knowledgesourcefilepath;
//创建审批的面板
var createApprovalWin = null;
//查看审批的面板
var viewApprovalWin = null;
//审批知识的面板
var approveWin = null;

//修改类别面板
var ctreewin = null;
var dtreewin = null;
var modifykwin = null;
var globalid;
//创建最外层知识显示的
var showapprovaldata;
var topbar_style;




function createKnowledgeView(id,borrowflowid,messageType){
	var topbarStyle = Edo.get('topbar_style');
	if(topbarStyle != null){
		topbarStyle.destroy();
	}
	//最外层的窗口
	var myWin;

	//增加tag的窗口
	var myTagFormWin;

	var centerPanel;
	var rightPanel;
	var mainPanel;
	var topBar;
	var flashKnowledge;
	var commentPanel;
	var actionPanel;
	var citationPanel;
	var versionsPanel;
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
	var isQuestion=0;
	
	//加载数据
	var kData = cims201.utils.getkData('knowledge/knowledge!showknowledge.action',{id:id,borrowFlowId:borrowflowid,messageType:messageType},id);
	

	if(kData.ktype.name=='问题')//知识类型为4时，是'问题'
	{
	mainPanel=createQuestionView(id,kData);
	this.getKnowledgeView = function(){
	return mainPanel;}
	
	}else if(kData.ktype.name == '岗位知识体系') {
		mainPanel = createPositionKnowledgeView(id,kData);
		this.getKnowledgeView = function(){
			return mainPanel;
		}
	}else{

	
	if(kData=='error')
	
	{
	mainPanel='error'
	
	}
	else{

	globalid=id;		
	topBar = new createTopBar(kData.titleName,id,kData.borrowFlowId,null,kData.ktype.id,kData.knowledgeSourceFilePath).getTopBar();	
	flashKnowledge = new createFlashKnowlege(kData.flashFilePath,kData.id,kData.knowledgeSourceFilePath).getKnowledge();
	commentPanel = new createComment(kData.id,kData.ktype.name).getComment();

	//判断是否展示flash
	var showflash=false;
	
        
	  if(null!=kData.flashFilePath&&kData.flashFilePath!=""&&kData.flashFilePath!="noflash")
		  showflash=true;
	  else{
		  
		  
	  if(null!=kData.knowledgeSourceFilePath&&(kData.knowledgeSourceFilePath.indexOf(".pdf")!=-1||kData.knowledgeSourceFilePath.indexOf(".PDF")!=-1))
	  {
		  showflash=true;
	  }

	  }
	  
	  if(showflash){
			
			flashKnowledge = new createFlashKnowlege(kData.flashFilePath,kData.id,isQuestion,kData.questioncontent,kData.knowledgeSourceFilePath).getKnowledge();
	  }else{
			if(kData.ktype.name=='文章'||kData.ktype.name=='新闻'){//江丁丁添加 2013-6-18   knowledge-util.js加方法createArticleContent
				flashKnowledge=new createArticleContent(kData.id).getReply();
			}else{
				flashKnowledge=new createExpandPropertyList(kData.id).getReply();
			}
	  }
	
	commentPanel = new createComment(kData.id,kData.ktype.name,null).getComment();


	actionPanel = new createAction(kData.attachments).getAction();
	citationPanel = new createCitations(kData.citationKnowledges).getCitations();
	//版本
	versionsPanel = new createVersions(kData.id).getVersions();
	if(null!=kData.domainNode)
	knoweldgedomain=kData.domainNode.id;
	else
		{
		knoweldgedomain=null;
		}
	knowledgecategroys=kData.categories;
	knowledgesourcefilepath=kData.knowledgeSourceFilePath;
	knowledgeDetail = new createKnowledgeDetail(kData.commentRecord,kData.uploadTime, kData.uploader, id).getKnowledgeDetail();
	knowledgeKeyword = new createKnowledgeKeyword(kData.keywords).getKnowledgeKeyword();
	var keepornotdata=cims201.utils.getData('custom/customization!kKeep.action');
	var keepAdd = null;
	if(keepornotdata == true || keepornotdata == "true"){
		keepAdd = new createKeepAdd(kData.id,kData.titleName).getKeepAdd();
	}
	//alert(kData.abstractText);
	if(null!=kData.abstractText)
	knowledgeAbstractpanel=new createKnowledgeAbstract(kData.abstractText).getKnowledgeAbstract();
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
		children: [commentPanel, actionPanel,citationPanel,versionsPanel]
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
		
		children: 
			[
			{type: 'button',icon: 'icon-cims201-comment', text: '用户评价'},
			{type: 'button',icon: 'icon-cims201-action', text: '附件列表'},
			{type: 'button',icon: 'icon-cims201-citation', text: '引证文献'},
			{type: 'button',icon: 'icon-cims201-version', text: '版本管理'}
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
	        topBar,flashKnowledge, centerTab, centerContent
	    ]
	});
	if(null == keepAdd ){
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
	}else{
		rightPanel = Edo.create({
			//id: 'rightPanel',    
			type: 'box',   
			border: [0,0,0,0], 
			padding: [0,0,0,6], 
			width: 230,
			//height: '100%',              
			layout: 'vertical',    
			children: [
			           keepAdd,knowledgeKeyword,knowledgeDetail,relatedKnowledge,popKnowledge	    
			           ]
		});
	}	
	if(null!=knowledgeAbstractpanel)
	rightPanel.addChildAt(1,knowledgeAbstractpanel);
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
	
	
	
	
	
	
	
	
	
	}}
		this.getKnowledgeView = function(){
		return mainPanel;}
	
	

}



			

//当评价星星点击后执行的事件
//知识评价的方法
function starJudge(index, knowledgeID){	
	
	var res = cims201.utils.getData('comment/comment!rating.action',{knowledgeID:knowledgeID, score:index});
	if(res.isSupport == true || res.isSupport == 'true' || res.isSupport == 1){
		Edo.get('totalscore_'+knowledgeID).set('text','<span class="knowledge_view_bigscore">'+index+'</span>');
	    Edo.MessageBox.alert("提示", "评价成功", null);

	}else{
		Edo.MessageBox.alert("提示", "您已经评价过该知识了", null);
	}
}


//知识展示最顶端的工具条
function createTopBar(myTitle,id,borrowFlowId,borrowFlowNodeId,ktypeId,kSourceFilePath){

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
	

	
	
	
	
	
	topbar_style = Edo.create({
		id: 'topbar_style',
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
	
	
	//查看avidm原文
	cims201.utils.getData_Async('privilege/show-button!whetherShowAVIDMBtn.action',{knowledgeId:id}, function(text){
	var data = Edo.util.Json.decode(text);
	if(null!= data&&data != false && data != 'false'&& data != ''){
		
	//if(true){
//		var rightBt = '';
//		rightBt += '<a href="'+data+'" target="_blank";><span class="cims201_knowledge_topbar_bt'+(currentBtIndex++)+'"><span class="cims201_knowledge_topbar_borrowflow">';
//		rightBt += '查看原文';
//		rightBt +='</span></span></a>';	
		
		 leftTitle = '';
		leftTitle += '<span class="cims201_knowledge_topbar_title">';
		leftTitle += '<span style="font-size:'+title_size+'px;">';
		leftTitle += title+'&nbsp;&nbsp;<a href="'+data+'" target="_blank";><span class="cims201_knowledge_topbar_borrowflow">查看原文</span></a>';
		leftTitle += '</span>';
		leftTitle += '</span>';
		
		topbar_style.set('text',leftTitle);
		
	//	topbar_style.set('text',topbar_style.get('text')+rightBt);
	}

	
	//右边的操作按钮
	var currentBtIndex = 6;
	
	//下载知识
	cims201.utils.getData_Async('privilege/show-button!whetherShowDownloadKnowledgeBtn.action',{knowledgeId:id}, function(text){
		if(null!=text&&text!=""){
		var data = Edo.util.Json.decode(text);
		
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<a href="knowledge/viewfile!download.action?id='+id+'"><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_download">';
			rightBt += '下载知识';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
		}


		//pl 审批改域                   注意：modifydtree 是实际改域过程，先要进行审批。
		cims201.utils.getData_Async('privilege/show-button!whetherShowapprovalModifyDTreeBtn.action',{knowledgeId:id}, function(text){
		showapprovaldata = Edo.util.Json.decode(text);
//		data = showapprovaldata;
		if(showapprovaldata.show == true || showapprovaldata.show == 'true'){
			var rightBt = '';
			rightBt += '<a href=javascript:approvemodifydtree('+id+')><span id="modifydtreebt'+id+'" class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_modifydtree">';
			rightBt += '审批改域';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
//			'<a href=javascript:approvemodifydtree('+knowledgeid+')><span id="modifydtreebt'+id+'" class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_modifydtree">审批改域</span></span></a>'
		}
		
		//pl 申请修改知识域 
		cims201.utils.getData_Async('privilege/show-button!whetherShowapplyModifyDTreeBtn.action',{knowledgeId:id}, function(text){
			var data = Edo.util.Json.decode(text);
			if(data.show == true || data.show == 'true'){
				var rightBt = '';
				rightBt += '<span id="applymodifydtreebt'+id+'" class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><a href=javascript:applymodifydtree('+id+')><span class="cims201_knowledge_topbar_modifydtree">';
				rightBt += '申请改域';
				rightBt +='</span></a></span>';	
				topbar_style.set('text',topbar_style.get('text')+rightBt);
			}

		
		//修改类别
		cims201.utils.getData_Async('privilege/show-button!whetherShowModifyCTreeBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		
		//修改知识	
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			//rightBt += '<a href="/caltks/opendoc.jsp?knowledgeid='+id+'&ktypeid='+ktypeId+'&sourcefilepath='+kSourceFilePath+'"><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_modifyctree">';
			rightBt += '<a href=javascript:modifyKnowledge('+id+')><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_modifyknowledge">';
			rightBt += '修改知识';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
		//修改类别
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<a href=javascript:modifyctree('+id+')><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_modifyctree">';
			rightBt += '修改类别';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}

       //查看审批
			cims201.utils.getData_Async('privilege/show-button!whetherShowViewApprovalBtn.action',{knowledgeId:id}, function(text){
			
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<a href=javascript:showApprovalFlowStatus('+id+');><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_viewapproval">';
			rightBt += '查看审批';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);	
		}
	    //发起审批
	    cims201.utils.getData_Async('privilege/show-button!whetherShowInitiateApprovalBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<a href=javascript:createApprovalFlowWithFirstNode('+id+');><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_initiateapproval">';
			rightBt += '发起审批';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
			//审批知识
		cims201.utils.getData_Async('privilege/show-button!whetherShowDoApprovalBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			var rightBt = '';
			rightBt += '<a href=javascript:approveKnowledge('+id+');><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_approve">';
			rightBt += '审批知识';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
	
			//借阅流程定义
		cims201.utils.getData_Async('privilege/show-button!whetherShowAdminBorrowFlowBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
		//if(true){
			var rightBt = '';
			rightBt += '<a href=javascript:editborrowflow('+id+',\'admin\','+borrowFlowId+');><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_borrowflow">';
			rightBt += '处理借阅';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
			//审批借阅
		cims201.utils.getData_Async('privilege/show-button!whetherShowDoBorrowingBtn.action',{knowledgeId:id}, function(text){
		var data = Edo.util.Json.decode(text);
		if((data.show == true || data.show == 'true')&&null!=data.borrowFlowId){
		//if(true){
			var rightBt = '';
			rightBt += '<a href=javascript:approveborrowflow('+id+',\'admin\','+data.borrowFlowId+');><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_borrowflow">';
			rightBt += '审核借阅';
			rightBt +='</span></span></a>';	
			topbar_style.set('text',topbar_style.get('text')+rightBt);
		}
	
		});
		});
		});
		});
		}); 
	    });
		});
	    });
	    });
	    
	});
	
	//rightBt += '<span class="cims201_knowledge_topbar_bt2">';
	//rightBt += '审批知识';
	//rightBt +='</span>';
	
	//rightBt += '<span class="cims201_knowledge_topbar_bt3">';
	//rightBt += '审批知识';
	//rightBt +='</span>';
	//
	//rightBt += '<span class="cims201_knowledge_topbar_bt4">';
	//rightBt += '审批知识';
	//rightBt +='</span>';
	
	this.getTopBar = function(){
		return topBar;
	}
}



//创建flash形式的知识展示
function createFlashKnowlege(flashFilePath,kid,knowledgeSourceFilePath){
var knowledgeModule;
var myqsBox=null;
var myWin2 = null;
	var knowledgeModule;
	if(null!=Edo.get('knowledgeModule'+kid))
	{knowledgeModule=Edo.get('knowledgeModule'+kid);
	
	}else{

		var screenh=cims201.utils.getScreenSize().height;


	//创建知识显示的模块，该模块从指定路径加载知识	
	 knowledgeModule = Edo.create({
		id:'knowledgeModule'+kid,
		type:"module",
		width: '100%',
		height: screenh-290,
		style: 'border:0;',
		src: 'flashPathConvertor.jsp?path='+flashFilePath+'&sourcefilepath='+knowledgeSourceFilePath
	});
	
	
	}
	

	
	
	this.getKnowledge = function(){
		return knowledgeModule;
	}
}






//创建评论界面
function createComment(knowledgeID,ktypename,bestAnswerId){
	//var judgeModule = Edo.create({
		//id:'judgeModule',
	//	type:"module",
	//	width: '100%',
		//height: 1000,
		//autoHeight: true,
	//	style: 'border:0',
	//	src: '/caltks/knowledge/ui!knowledgecomment.action?knowledgeID='+knowledgeID
	//});
	var myComment = new createCommentPanel(ktypename,bestAnswerId,knowledgeID);
	myComment.search({knowledgeID:knowledgeID}, 'comment/comment!listComment.action');
	this.getComment = function(){
		//return judgeModule;
		return myComment.getComment();
	}
}

//创建附件列表
function createAction(attaches){
	//附件列表
	var actionModule = Edo.create({
		type:"box",
		width: '100%',
		//height: '100%',
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout: 'vertical',
		children: []
	});
	if(attaches.length == 0){
		attaches.each(function(o){
			actionModule.addChild({
				type: 'label',
				width: '100%',
				text: '没有附件'
			});
		});
	}else{
		attaches.each(function(o){
			actionModule.addChild({
				type: 'label',
				width: '100%',
				text: '<a href="knowledge/sourcefiledownload!downloadattach.action?id='+o.id+'">'+o.name+'</a>'
			});
		});
	}
	this.getAction = function(){
		return actionModule;
	}
}

//创建引证文献列表
function createCitations(citations){
	//附件列表
	var citationsModule = Edo.create({
		type:"box",
		width: '100%',
		height: 200,
		verticalScrollPolicy : 'on',		
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout: 'vertical',
		children: []
	});
	if(citations.length == 0){
		citations.each(function(o){
			citationsModule.addChild({
				type: 'label',
				width: '100%',
				text: '没有引证文献'
			});
		});
	}else{
		var i=1;
		citations.each(function(o){
			var title_outStr = '';
			var	author_outStr = '';	
			var titlelink = '';		
					if(o.titleName.length > 4){
						title_outStr += o.titleName.substring(0,4)+'...';
					}else{
						title_outStr += o.titleName;
					}
				o.KAuthors.each(function(o){				            			
             			author_outStr += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
            titlelink = '<a href="javascript:showKnowledgeDetail('+o.id+',\''+title_outStr+'\')";>'+o.titleName+'</a>'
			var title = '['+i+']'+'&nbsp&nbsp'+author_outStr+'.'+titlelink+'.'+o.uploaddate;
			i++;
			citationsModule.addChild({
				type: 'label',
				width: '100%',
				text: title
			});
		});
	}
	this.getCitations = function(){
		return citationsModule;
	}
}
//创建版本管理树列表
function createVersions(knowldgeID){

	var versionModule = Edo.create({
		type:"box",
		width: '100%',
		height: 200,
		verticalScrollPolicy : 'on',		
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout: 'vertical',
		children: []
	});
	
	
	var versionData = cims201.utils.getData('knowledge/knowledge!listKVersions.action',{id:knowldgeID});

	var treeColumns = [{
				      header: '树名',
				      dataIndex: 'versionNumber'
				      				     			     				      
				 }							 				 
				 ];						 
	var versionTree = new createTree({},treeColumns,versionData,'single',[],[],domainTreeSelectEvent);
	
			versionModule.addChild(versionTree.getTree());
	
	this.getVersions = function(){
		return versionModule;
	}
}

function domainTreeSelectEvent(cn){ 
	var title_outStr=''; 
	if(cn.knowledgeTitleName.length > 4){
			title_outStr += cn.knowledgeTitleName.substring(0,4)+'...';
		}else{
			title_outStr += cn.knowledgeTitleName;
		}	 
	showKnowledgeDetail(cn.knowledgeID,title_outStr);              		    		
}

//创建当前知识信息面板
function createKnowledgeDetail(comment, uploadtime, uploader, knowledgeID){
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
		knowledge_view_judge_total_str += '<span class="knowledge_view_general">已有'+(comment.commentCount?comment.commentCount:0)+'人评论,'+(comment.ratecount?comment.ratecount:0)+'人评分</span>';
		knowledge_view_judge_total_str += '<br>';
		knowledge_view_judge_total_str += '<span class="knowledge_view_general">浏览：'+(comment.viewCount?comment.viewCount:0)+'次  下载：'+(comment.downloadCount?comment.downloadCount:0)+'次';
		knowledge_view_judge_total_str += '<br>';
		knowledge_view_judge_total_str += '发布时间：'+uploadtime;
		knowledge_view_judge_total_str += '<br>';
		knowledge_view_judge_total_str += '<hr>';
		knowledge_view_judge_total_str += '发布者：<a href="javascript:showUploaderKnowledgeList('+uploader.id+',\''+uploader.name+'\')";>'+uploader.name+'</a>';
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
文档摘要
*/
function createKnowledgeAbstract(abstracttext){
	//文档关键词面板
	var knowledgeAbstractPanelStr = '摘要：';

		knowledgeAbstractPanelStr+=abstracttext;
	
	var knowledgeAbstractPanel = Edo.create({
		border: [1,1,1,1], 
	    padding: [12,0,0,12],    
	    type: 'panel',
	    title: '<span style="font-weight:bold;">文档摘要</span>',
	    titleIcon: 'icon-cims201-ktitle',    
	    width: '100%',
	    height: '230',
	    bodyCls: 'cims201_knowledge_keyword',             
	    layout: 'vertical',    
	    children: [
	        {
	        	type: 'boxtext',	   
	        	width: '100%', 
	        	height:'100%',
	        	text: knowledgeAbstractPanelStr
	        }
	    ]
	});
	this.getKnowledgeAbstract = function(){
		return knowledgeAbstractPanel;
	}
}
/**
 * @author panlei
 * 收藏按钮
 * */
function createKeepAdd(knowledgeID,ktitleName){
	var existdata =  cims201.utils.getData(
			'knowledge/keep/keep!keepIsExisted.action',
			{
				knowledgeId : knowledgeID
			});
	var isConnected = existdata;
	var connecttext;
	if(isConnected == "收藏"){
		connecttext = '<br/><div ><a><span ><font size = 4 color="blue">添加到收藏</font></span></a></div>';
	}else{
		connecttext = '<br/><div ><span ><font size = 4 color="red">'+isConnected+'</font></span></div>';
	}
	var keepadd = Edo.create({
		type : 'panel',
		title: '<span style="font-weight:bold;">知识收藏</span>',
		titleIcon: 'icon-cims201-ktitle',    
		width: '100%',
		layout: 'horizontal',
		children: [
		           {   	   
					type : 'label',
					text : '<div class="cims201_topbar_icon"><img height=\'50px\' src="css/images/getKnowledgeKeep.gif"></div>'
		           },
			{type: 'label',id : 'keep'+knowledgeID, width: 100, text:connecttext ,onclick : function(){
				if(this.text == '<br/><div ><a><span ><font size = 4 color="blue">添加到收藏</font></span></a></div>'){
					createAddKeepBox(knowledgeID,ktitleName);
					this.set('text','<br/><div ><a><span><font size = 4 color = "black">编辑</font></span></a></div>');
				}else if(this.text == '<br/><div ><a><span><font size = 4 color = "black">编辑</font></span></a></div>' ){
					createAddKeepBox(knowledgeID,ktitleName,"1");
				}
					
					
			}
			}
		]
	})
	this.getKeepAdd = function(){
		return keepadd;
	}
}
/**
文档关键词
*/
function createKnowledgeKeyword(keywords){
	//文档关键词面板
	var knowledgeKeyWordPanelStr = '';
	keywords.each(function(o){
		knowledgeKeyWordPanelStr += '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name+'</a>';
	});
	
	var knowledgeKeyWordPanel = Edo.create({
		border: [1,1,1,1], 
	    padding: [12,0,0,12],    
	    type: 'panel',
	    title: '<span style="font-weight:bold;">关键词</span>',
	    titleIcon: 'icon-cims201-ktitle',    
	    width: '100%',
	    height: '65',
	    bodyCls: 'cims201_knowledge_keyword',             
	    layout: 'vertical',    
	    children: [
	        {
	        	type: 'label',	   
	        	width: '100%',     	
	        	text: knowledgeKeyWordPanelStr
	        }
	    ]
	});
	this.getKnowledgeKeyword = function(){
		return knowledgeKeyWordPanel;
	}
}


/**
相关推荐文档
*/
function createRelatedKnowledge(id){
	//添加更多相关知识
	
	
	var RelatedKnowledgePanel = Edo.create({
		border: [1,1,1,1], 
	    padding: [12,0,0,0],    
	    type: 'panel',
	    title: '<span style="font-weight:bold;">相关推荐文档</span>',
	    titleIcon: 'icon-cims201-ktitle',    
	    width: '100%',
	    //height: 280,             
	    layout: 'vertical',    
	    children: []
	});
	
	//从后台取数据
	cims201.utils.getData_Async('knowledge/knowledge!listRcommentKnowledge.action',{id:id},function(text){
		if(text != null && text != 'null' && text != ''){
			var relatedKnowledgeData = Edo.util.Json.decode(text);
			//相关推荐文档
			relatedKnowledgeData.each(function(o){
				var oneRelatedKnowledgeStr = '';
				
				oneRelatedKnowledgeStr += '<div class="knowledge_view_relate_word">';
				var title_outStr = '';
				if(o.titleName.length > 4){
					title_outStr += o.titleName.substring(0,4)+'...';
				}else{
					title_outStr += o.titleName;
				}
				oneRelatedKnowledgeStr += '<a href="javascript:showKnowledgeDetail('+o.id+',\''+title_outStr+'\')";>'+o.titleName+'</a>';
				oneRelatedKnowledgeStr += '<br>';
				oneRelatedKnowledgeStr += '<div style="float:left; padding-top:5px;">';
				if(o.commentRecord){
					var tempScore = o.commentRecord.rate;
				
					for(var i=1;i<=(tempScore-tempScore%1);i++){
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_full"></div>';
					}
					if((tempScore%1) == 0){
						for(var j=i;j<=5;j++){
							oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
						}
					}else{
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_half"></div>';
						for(var j=(i+1);j<=5;j++){
							oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
						}
					}
					oneRelatedKnowledgeStr += '</div>';
					oneRelatedKnowledgeStr += '&nbsp'+o.commentRecord.commentCount+'人评';
					
					oneRelatedKnowledgeStr += '</div>';
				}else{
					var tempScore = 0;
				
					for(var i=1;i<=(tempScore-tempScore%1);i++){
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_full"></div>';
					}
					if((tempScore%1) == 0){
						for(var j=i;j<=5;j++){
							oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
						}
					}else{
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_half"></div>';
						for(var j=(i+1);j<=5;j++){
							oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
						}
					}
					oneRelatedKnowledgeStr += '</div>';
					oneRelatedKnowledgeStr += '&nbsp'+0+'人评';
					
					oneRelatedKnowledgeStr += '</div>';
				}
				
				
				var oneRelatedKnowledge = Edo.create({
					type: 'box',
					border: [0,0,0,0],
					padding: [0,0,0,12],
					width : '100%',
					height : 45,
					children: [
						{type:'label', text: oneRelatedKnowledgeStr}
					]		
				});
				RelatedKnowledgePanel.addChild(oneRelatedKnowledge);
			})
		}else{
			RelatedKnowledgePanel.addChild({
				type: 'label',
				width: '100%',
				style: 'font-size:13px; color: blue;',
				text: '当前没有推荐知识'
			});
		}	
	});
		
	this.getRelatedKnowledge = function(){
		return RelatedKnowledgePanel;
	}

}

/**
热门评论openNewTab
*/
function createPopKnowledge(id){
	
	//热门评论	
	var recomendedKnowledgePanel = Edo.create({
		border: [1,1,1,1], 
	    padding: [0,5,0,12],    
	    type: 'panel',
	    title: '<span style="font-weight:bold;">热门评论</span>',
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

//回复评论
function addKnowledgeReply(commentid,knowledgeID){
	if(addKnowledgeReplyWin == null){			
		htmleditor2 = Edo.create({
			type: 'textarea',
			width: '100%',
			height: 180
		});
		var editorBox = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,0],
			width: 500,
			height: 220,
			layout: 'vertical',
			children: [
				htmleditor2,
				{type: 'button', text: '确定提交', onclick: function(e){
					var qqq = {};
					if(knowledgeID){
						qqq['knowledgeID'] = knowledgeID;
					}
					if(commentid){
						qqq['commentid'] = commentid;
					}
					qqq['content'] = htmleditor2.get('text');
					cims201.utils.getData_Async('comment/comment!save.action',qqq,function(){
					});
							
					alert('添加评论成功!');
					addKnowledgeReplyWin.hide();
				}}
			]
		});
		addKnowledgeReplyWin = cims201.utils.getWin(500,230,'添加评论',editorBox);
	}	
	//增加遮罩
	htmleditor2.set('text','');
	//addKnowledgeReplyWin.show(200,100,true);
	setWinScreenPositions(500,230,addKnowledgeReplyWin,knowledgeID);
}

function voteKnowledgeComment(commentid, isSupport){
	var res = cims201.utils.getData('comment/comment!addCommentVote.action',{commentid:commentid,isSupport:(isSupport==1?true:false)});
	if(res.isSupport == true || res.isSupport == 'true' || res.isSupport == 1){
		alert('支持成功!');
		document.getElementById(commentid+'_'+(isSupport==1?'vote':'againstvote')).innerHTML = parseInt(document.getElementById(commentid+'_'+(isSupport==1?'vote':'againstvote')).innerHTML) + 1;
	}else{
		alert('您已经支持过该评论了!');
	}
}

//发起审批流程触发的方法
function createApprovalFlowWithFirstNode(knowledgeId){
	
	var mf = cims201.utils.getData('knowledge/approval/approval!createApprovalFlowWithFirstNode.action',{knowledgeId:knowledgeId});
	
	
		
		if(createApprovalWin == null){
			var myFlowBox = new createApprovalFlow(mf);
			createApprovalWin = cims201.utils.getWinforkview(700,350,'创建审批',myFlowBox.getFlow(),knowledgeId);
		}else{
			createApprovalWin.destroy();
			var myFlowBox = new createApprovalFlow(mf);
			createApprovalWin = cims201.utils.getWinforkview(700,350,'创建审批',myFlowBox.getFlow(),knowledgeId);
		}
		setWinScreenPositions(700,350,createApprovalWin,knowledgeId);
	
//	alert(knowledgeidstring);
  //  unvisiblemodul(knowledgeId);
    	
}
//批量发起审批流程触发的方法
function createBatchApprovalFlowWithFirstNode(knowledgeId){
	
	var knowledgeIdString = new String();
	
	
	if(!isNaN(knowledgeId)){
		knowledgeIdString = knowledgeId+"";
	}
	else{
		for(var i =0;i<knowledgeId.length;i++){
			
			knowledgeIdString += knowledgeId[i]+" ";
		}
	}
	
	var mf = cims201.utils.getData('knowledge/approval/approval!createBatchApprovalFlowWithFirstNode.action',{knowledgeIdString:knowledgeIdString});

	
		
		if(createApprovalWin == null){
			var myFlowBox = new createBatchApprovalFlow(mf);
			createApprovalWin = cims201.utils.getWinforkview(700,350,'创建审批',myFlowBox.getFlow(),knowledgeId[0]);
		}else{
			createApprovalWin.destroy();
			var myFlowBox = new createBatchApprovalFlow(mf);
			createApprovalWin = cims201.utils.getWinforkview(700,350,'创建审批',myFlowBox.getFlow(),knowledgeId[0]);
		}
		setWinScreenPositions(700,350,createApprovalWin,knowledgeId[0]);
	
//	alert(knowledgeidstring);
  //  unvisiblemodul(knowledgeId);
    	
}
//查看审批状态
function showApprovalFlowStatus(knowledgeId){
	var mf = cims201.utils.getData('knowledge/approval/approval!showApprovalFlowStatus.action',{knowledgeId:knowledgeId});
	
		if(viewApprovalWin == null){
			var myFlowBox = new createApprovalFlow(mf);
			viewApprovalWin = cims201.utils.getWinforkview(700,350,'查看审批',myFlowBox.getFlow(),knowledgeId);
		}else{
			viewApprovalWin.removeAllChildren();
			viewApprovalWin.destroy();
			var myFlowBox = new createApprovalFlow(mf);
			viewApprovalWin = cims201.utils.getWinforkview(700,350,'查看审批',myFlowBox.getFlow(),knowledgeId);
		}
		
		setWinScreenPositions(700,350,viewApprovalWin,knowledgeId);
	
	
}

//修改知识域类别
function modifydtree(knowledgeId){

 
if(dtreewin == null){

	
		dtreewin =cims201.utils.getWinforkview(700,450,'修改域别',creatmodifydtreebox('dtreewinbox',"","modify",knowledgeId),knowledgeId);
		
	}else{
		dtreewin.destroy();
		dtreewin =cims201.utils.getWinforkview(700,450,'修改域别',creatmodifydtreebox('dtreewinbox',"","modify",knowledgeId),knowledgeId);

	}
    setWinScreenPosition(700,450,dtreewin,knowledgeId);

}

//pl 申请修改知识域方法
function applymodifydtree(knowledgeId){
 
if(dtreewin == null){

	
		dtreewin =cims201.utils.getWinforkview(700,450,'申请修改域别',createapplymodifydtreebox('dtreewinbox',"","modify",knowledgeId),knowledgeId);
		
	}else{
		dtreewin.destroy();
		dtreewin =cims201.utils.getWinforkview(700,450,'申请修改域别',createapplymodifydtreebox('dtreewinbox',"","modify",knowledgeId),knowledgeId);

	}
    setWinScreenPosition(700,550,dtreewin,knowledgeId);

}
//pl 超管审批改域方法
function approvemodifydtree(knowledgeId){
	if(dtreewin == null){

		
		dtreewin =cims201.utils.getWinforkview(700,150,'审批修改域别',createapprovemodifydtreebox('dtreewinbox',"","modify",knowledgeId),knowledgeId);
		
	}else{
		dtreewin.destroy();
		dtreewin =cims201.utils.getWinforkview(700,150,'审批修改域别',createapprovemodifydtreebox('dtreewinbox',"","modify",knowledgeId),knowledgeId);

	}
    setWinScreenPosition(700,450,dtreewin,knowledgeId);
}
//pl 超管审批改域方法
function clickagain(knowledgeId){
	creatalert("该申请已被处理!");
}

//修改知识
function modifyKnowledge(knowledgeId){
	if(modifykwin == null){
		modifykwin =cims201.utils.getWinforkview(700,350,'修改知识',new modifybox(knowledgeId).getoutReply(),knowledgeId);
	
	}else{
		modifykwin.destroy();
		modifykwin =cims201.utils.getWinforkview(700,350,'修改知识',new modifybox(knowledgeId).getoutReply(),knowledgeId);
		//modifykwin.show(400,100,true);
	}
    setWinScreenPositions(700,350,modifykwin,knowledgeId);


}
	
//修改知识类树
function modifyctree(knowledgeId){
	var knolwledge = cims201.utils.getData('knowledge/knowledge!showknowledge.action',{id:knowledgeId});	
	var knowledgecategroys=knolwledge.categories;
	var knowledgecategroyids=new Array();
	//给已经有分类的知识先将分类结果付给分类树
    if(null!=knowledgecategroys){
    
    	for(var i=0;i< knowledgecategroys.length;i++)
    		{
    		knowledgecategroyids[knowledgecategroyids.length]=knowledgecategroys[i].id;
    		}
    }
 
if(ctreewin == null){

		ctreewin =cims201.utils.getWinforkview(700,450,'修改类别',creatmodifyctreebox('ctreewinbox',knowledgecategroyids,"","modify",knowledgeId),knowledgeId);
		
	}else{
		ctreewin.destroy();
		ctreewin =cims201.utils.getWinforkview(700,450,'修改类别',creatmodifyctreebox('ctreewinbox',knowledgecategroyids,"","modify",knowledgeId),knowledgeId);

	}
    setWinScreenPositions(700,450,ctreewin,knowledgeId);

}

//发起审批流程触发的方法
function approveKnowledge(knowledgeId){
	var knowledgeIdString = new String();
	var approvalResult = new String();
	var opinion = new String();
	
	var arr = new Array();
	if(!isNaN(knowledgeId)){
		arr[0] = knowledgeId;
	}
	else{
		for(var i =0;i<knowledgeId.length;i++){
			arr[i] = knowledgeId[i];
		}
	}
	
		var myApproveBox;
		var i = 0;

			myApproveBox=Edo.get('approveKnowledge_'+arr[i]);
			if(myApproveBox!=null)
				myApproveBox.destroy();
			myApproveBox = Edo.create({
				id: 'approveKnowledge_'+arr[i],
				type: 'box',
				//width: '100%',
				//height: '100%',
				border: [0,0,0,0],
				padding: [0,0,0,0],
				//layout: 'vertical',
				children: [
				           {
				        	   type: 'formitem',label: '审批结果<span style="color:red;">*</span>:', labelWidth:'100',
				        	   children:[
				        	             {type: 'radiogroup', id: 'approvalResult'+arr[i], displayField: 'name',checkField: 'checked',valueField: 'value',data: [
				        	                                                                                                                                     {name: '审批通过', value: '通过'},
				        	                                                                                                                                     {name: '审批不通过', value: '未通过'},
				        	                                                                                                                                     {name: '审批通过但不允许继续升级', value: '通过但停止升级'}
				        	                                                                                                                                     ]}
				        	             ]
				           },
				           {
				        	   type: 'formitem',label: '审批内容:',
				        	   children:[
				        	             {type: 'textarea', id: 'opinion'+arr[i], width: '500',defaultHeight: 100}
				        	             ]
				           },
				           {
				        	   type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
				        	   children:[
				        	             {id: 'approvesubmit'+arr[i], type: 'button', text: '提交审批'}
				        	             ]
				           }
				           
				           ]
			});
			
			if(approveWin == null){	
				
				approveWin = cims201.utils.getWinforkview(700,350,'审批知识',myApproveBox,arr[i]);
				}else{
					
					if(Edo.get('approveKnowledge_'+arr[i])!=null){
						Edo.get('approveKnowledge_'+arr[i]).reset();
						approveWin = cims201.utils.getWinforkview(700,350,'审批知识',myApproveBox,arr[i]);
					}
				}
				setWinScreenPositions(700,350,approveWin,arr[i]);
			Edo.get('approvesubmit'+arr[i]).on('click',function(e){
				
				
				if(Edo.get('approveKnowledge_'+arr[i])){
					var tempresult = Edo.get('approveKnowledge_'+arr[i]).getForm();
					for(var a = 0 ;a < arr.length;a++){
						
							tempresult['knowledgeIdString'] += arr[a]+" ";	
//						alert(arr[a]+"----"+Edo.get('approvalResult'+arr[i]).getValue()+"----"+Edo.get('opinion'+arr[i]).getValue());
					}
					tempresult['approvalResult']+=Edo.get('approvalResult'+arr[i]).getValue();
					tempresult['opinion']+=Edo.get('opinion'+arr[i]).getValue();
//						alert(tempresult['knowledgeIdString']);
//						alert(tempresult['approvalResultString']);
//						alert(tempresult['opinionString']);
						var result=	cims201.utils.getData('knowledge/approval/approval!approval.action',tempresult);
					
					if(null!=result) 
						creatalert(result,approveWin);
//					approveWin.hide();
					
				}
				
			});
			
		// unvisiblemodul(knowledgeId);
		//approveWin.show(250,110,true);
}

function creatmodifyctree(knowledgeId)
	{
	var modifybox=Edo.get('modifybox');
	if(null==modifybox)
		{
		modifybox= creatmodifyctreebox(knowledgeId);
		}
		else
		{
		if(Edo.get('modifybox')){
			Edo.get('modifybox').destroy();
		
		modifybox= creatmodifyctreebox(knowledgeId);
		}
	
	}	
	return modifybox;
	}
	
//function creatmodifyctreebox(knowledgeId)
//	{
//	
//		
//	var modifybox=Edo.create({
//			id: 'modifybox',
//			type: 'box',
//		
//			border: [0,0,0,0],
//			padding: [0,0,0,0],
//			
//			children: [
//			]}
//			);
//if(null==knoweldgedomain){
//				// 取域树节点的数据
//				var dtreeData = cims201.utils.getData(
//						'tree/privilege-tree!listPrivilegeTreeNodes.action',
//						{
//							treeType : 'domainTree',
//							operationName : '上传知识'
//						});
//				var domaintree_select = new Edo.controls.TreeSelect().set({
//							id : 'dtree',
//							type : 'dtreeselect',
//							multiSelect : true,
//							displayField : 'name',
//							width : 400,
//							valueField : 'id',
//								padding: [20,0,0,0],
//							// rowSelectMode: 'single',
//							data : dtreeData,
//
//							treeConfig : {
//								treeColumn : 'name',
//								columns : [Edo.lists.Table.createMultiColumn(),
//										{
//											id : 'name',
//											header : '域名称',
//											width : 350,
//											dataIndex : 'name'
//										}]
//							}
//						});
//				var domainpanel = {
//					type : 'formitem',
//					label : '选择域结点',
//					labelWidth : 100,
//					labelAlign : 'right',
//					children : [domaintree_select]
//				};
//				modifybox.addChild(domainpanel);	
//				
//}
//
//				// 取分类树节点的数据
//
//				var ctreeData = cims201.utils.getData(
//						'tree/privilege-tree!listPrivilegeTreeNodes.action',
//						{
//							treeType : 'categoryTree',
//							operationName : '上传知识'
//						});
//				var ctree_select = new Edo.controls.TreeSelect().set({
//							id : 'ctree',
//							type : 'ctreeselect',
//							multiSelect : true,
//							displayField : 'name',
//							valueField : 'id',
//							width : 400,
//							padding: [80,0,0,0],
//							// rowSelectMode: 'single',
//							data : ctreeData,
//
//							treeConfig : {
//								treeColumn : 'name',
//								columns : [Edo.lists.Table.createMultiColumn(),
//										{
//											id : 'name',
//											header : '分类名称',
//											width : 372,
//											dataIndex : 'name'
//										}]
//							}
//						});
//				var categorytree = {
//					type : 'formitem',
//					label : '选择分类节点',
//					labelWidth : 100,
//					labelAlign : 'right',
//					children : [ctree_select]
//				};
//			
//				
//			
//			modifybox.addChild(categorytree);	
//			
//			var button = {
//			type:'box',
//			border:[0,0,0,0],
//			text:'',
//			layout:'horizontal',
//			
//			children:[{
//				border:[0,0,0,0],	
//			type:'box',
//			width:196
//			
//			},
//			{ type: 'button', text: '确定',
//		
//            padding: [0,0,0,0],
//            enableToggle: false,            
//            onclick: function(e){
//           
//            	var dnodeid="";
//            	if(null!= 	Edo.get('dtree'))
//                dnodeid=Edo.get('dtree').getValue();
//            	var cnodeid="";
//            	if(null!=Edo.get('ctree'))
//            	cnodeid=Edo.get('ctree').getValue();
//           updateknowledgectree(knowledgeId,dnodeid,cnodeid);
//            }
//            }]
//
//			};
//			
//			modifybox.addChild(button);	
//			
//		
//		    return modifybox;
//	}	
	
	function updateknowledgectree(kid,did,cid,win)
		{
			cims201.utils.getData_Async(
						'knowledge/knowledge!modifycdtree.action',
						{
							id : kid,
							categoryids:cid, 
							domainids:did
						},function(text){
		        var result = Edo.util.Json.decode(text);	   
		         creatalert(result,win);
		     	
		     
		     }
		);
//	if(null!=did&&did!='null'&&did!='')
//	{
//	document.getElementById('modifydtreebt'+kid).innerHTML="";
//	}
		}
	//pl 申请变更知识域，即申请更新知识域树  
	function applyupdateknowledgectree(kid,did,receiverid,win,applytext)
	{
		cims201.utils.getData_Async(
					'knowledge/knowledge!applymodifycdtree.action',
					{
						id : kid,
						receiverid:receiverid, 
						domainids:did,
						applytext : applytext
					},function(text){
	        var result = Edo.util.Json.decode(text);	   
	         creatalert(result,win);
	     	
	     
	     }
	);
	}
	//pl 审批通过并变更知识域
	function approvalupdateknowledgectree(kid,did,cid,win,approvalresult)
	{
			cims201.utils.getData_Async(
					'knowledge/knowledge!approvalupdateknowledgectree.action',
					{
						id : kid,
						categoryids:cid, 
						domainids:did,
						approvalresult : approvalresult
					},function(text){
						var result = Edo.util.Json.decode(text);	   
						creatalert(result,win);
						
						
					}
			);
		
//if(null!=did&&did!='null'&&did!='')
//{
//document.getElementById('modifydtreebt'+kid).innerHTML="";
//}
	}
		//对于pdf文件的展示因为没有办法用蒙版遮挡，因此将module隐藏掉，在处理好后在展示出来
	function unvisiblemodul(knowledgeId)
		{
	
	   if(null!=knowledgesourcefilepath&&knowledgesourcefilepath!='')
	   	{
	   	if(knowledgesourcefilepath.indexOf('.')!=-1){
	   	var ext=knowledgesourcefilepath.substring(knowledgesourcefilepath.lastIndexOf('.')+1,knowledgesourcefilepath.length);
	 
	   	if(null!=ext&&(ext=='pdf'||ext=='PDF'))
	   		{
	   		if(null!=Edo.get('knowledgeModule'+knowledgeId))
	   		Edo.get('knowledgeModule'+knowledgeId).set('visible',false);
	   		}
	   	
	   	}
	   	}
		
		
		}	
	function creatalert(message,win)
		{
		
     Edo.MessageBox.alert("消息", message, showResult);  
     if(null != win){
    	 win.hide();
     }
		}	
		
	function showResult(action,value)
		{
	    if(action=='ok'){
		 //Edo.get('modifybox').parent.parent.hide();
	    	if(null!=Edo.get('knowledgeModule'+globalid))
		 Edo.get('knowledgeModule'+globalid).set('visible',true);
		}
		}
   function setWinScreenPositions(width,height,win,kid)
		{
		
		var screenw= cims201.utils.getScreenSize().width;
		var screenh=cims201.utils.getScreenSize().height;
		if(width<screenw)
		{width=(screenw-width)/2
		
		}
		else{
		width=0;
		}
		if(height<screenh)
		{height=(screenh-height)/2
		
		}
		else{
		height=0;
		}
		if(null!=kid)
		unvisiblemodul(kid);
		 win.show(width,height,true);
		}		
//通过ajax想后台取数据
cims201.utils.getkData = function(url,params,kid){
// 从服务器端获取树的数据
	var data;
	Edo.util.Ajax.request({
		url : url,
		type : 'post',
		params : params,
		async : false,
		onSuccess : function(text) {
			// text就是从url地址获得的文本字符串
			if(text == null || text == ''){
				data = null;
			}else{
		
				data = Edo.util.Json.decode(text);
			}			
		},
		onFail : function(code,a,b,c,d,e,f) {
			// code是网络交互错误码,如404,500之类
			if(code=="500")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统内部错误！是可能有误操作，如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=="403"){
				var borrowdata=cims201.utils.getData('custom/customization!kBorrow.action');
				if(borrowdata == false || borrowdata == 'false'){
					Edo.MessageBox.show({
						title : '警告！',
						msg : '您没有权限该操作！',
						buttons : Edo.MessageBox.YESNOCANCEL,
						icon : Edo.MessageBox.WARNING
					});
					
				}else{
					Edo.MessageBox.show({
						title : '警告！',
						msg : '您没有权限该操作！现在就向管理员申请借阅该文献吗？',
						buttons : Edo.MessageBox.YESNOCANCEL,
						callback : function tocreateBorrowApply(action){
						if(action=='yes')
						{
							createBorrowApply(kid);
						}
					},
					icon : Edo.MessageBox.WARNING
					});
				}
			}
			if(code=="404")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统错误！没有找到该页面',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});				
			
			data = 'error'; 
		}
	});
	
	return data;
}	
function deleteapproval(knowledgeid){
	var ss = topbar_style.get('text');
	var aa = ss.replace('approvemodifydtree','clickagain');
	topbar_style.set('text',aa);
}