/**
 * 中国专利展示页面 jiangdingding 添加 2013-5-20
 * @param appCode
 * @param patentType
 * @returns {createPatentView}
 */
function createPatentCNView(appCode, patentType, keyword){
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
	var kData = cims201.utils.getkData('patent/patent!searchPatentDetailCN.action',{appCode:appCode,patentType:patentType,keyword:keyword});
	
	if(kData=='error')
	
	{
	mainPanel='error';
	
	}
	else{

//	globalid=id;		
	topBar = new createPatentTopBar(kData.titleName,null,null,null,null,null).getTopBar();
	
	//右边的操作按钮
	var currentBtIndex = 6;
	var rightBt = '';
	rightBt += '<a href="javascript:downcnpatenttext('+'\''+appCode+'\',\''+patentType+'\',\''+keyword+'\')";><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_download">';
	rightBt += '下载说明书';
	rightBt +='</span></span></a>';	
	topbar_style.set('text',topbar_style.get('text')+rightBt);
	
	commentPanel = new createComment(kData.id,"专利").getComment();

	commentPanel = new createComment(kData.id,"专利",null).getComment();

	//附件列表
	//actionPanel = new createAction(kData.attachments).getAction();
	//引证文献列表
	//citationPanel = new createCitations(kData.citationKnowledges).getCitations();
	//版本
	versionsPanel = new createVersions(kData.id).getVersions();

	knowledgesourcefilepath=kData.knowledgeSourceFilePath;
	
	//收藏按钮
	keepAdd = new createKeepAdd(kData.id,kData.titleName).getKeepAdd();
	knowledgeKeyword = new createKnowledgeKeyword(kData.keywords).getKnowledgeKeyword();
	if(null!=kData.abstract_){
		knowledgeAbstractpanel=new createKnowledgeAbstract(kData.abstract_).getKnowledgeAbstract();
	}
	relatedKnowledge = new createRelatedKnowledge(kData.id).getRelatedKnowledge();
	//热门评论
	popKnowledge = new createPopKnowledge(kData.id).getPopKnowledge();

    //专利详细信息
	detailTable = Edo.create({
		type:'box',
		width:'100%',
		bodyStyle:'background:#F3F8FC',
	  	border:[0,0,0,0],
		padding:[5,0,10,0],
		layout:'vertical',
		horizontalAlign:'center',
		verticalGap:'0',
		children:[
		        {
		    		type:'box',
		    		width:'80%',
		    	  	border:[1,1,1,1],
		    		padding:[5,0,5,0],
		    		layout:'vertical',
		    		horizontalAlign:'center',
		    		verticalGap:'0',
		    		children:[
		    		          {
		    		        	  type:'box',
		    		        	  border:[0,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'申请（专利）号:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.appCode,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'申   请   日:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],
		    										  children:[{type:'text',text:kData.appDate,width:'200',readOnly:true}]
		    									  }											        	            ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'名          称:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,0,0,0],
		    										children:[{type:'text',text:kData.titleName,width:'515',readOnly:true}]
		    									  }
		    									 ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'公开（公告） 号:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.pubCode,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'公开（公告）日:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],
		    										  children:[{type:'text',text:kData.pubDate,width:'200',readOnly:true}]
		    									  }											        	            ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'主  分  类  号:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.ipc,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'分案 原申请号:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],
		    										  children:[{type:'text',text:'',width:'200',readOnly:true}]
		    									  }											        	            ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'分    类    号:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,0,0,0],
		    										children:[{type:'text',text:kData.catCode,width:'515',readOnly:true}]
		    									  }
		    									 ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'颁    证    日:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.cerDate,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'优    先    权:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],
		    										  children:[{type:'text',text:kData.priory,width:'200',readOnly:true}]
		    									  }											        	            ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'申请（专利权）人:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,0,0,0],
		    										children:[{type:'text',text:kData.appPerson,width:'515',readOnly:true}]
		    									  }							        	            ]
		    		          },
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'地          址:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,0,0,0],
		    										children:[{type:'text',text:kData.appAddress,width:'515',readOnly:true}]
		    									  }			        	            ]
		    		          },		         
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'发明（设计）人:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.invPerson,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'国  际  申  请:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],										  
		    										  children:[{type:'text',text:kData.intApp,width:'200',readOnly:true}]
		    									  }
		    								]
		    		          },		         
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'国  际  公  布:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.intPub,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'进入国家日期:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],										  
		    										  children:[{type:'text',text:kData.entNationDate,width:'200',readOnly:true}]
		    									  }
		    								]
		    		          },		         
		    		          {
		    		        	  type:'box',
		    		        	  border:[1,0,0,0],
		    		        	  padding:[2,0,2,0],
		    		        	  layout:'horizontal',
		    		        	  
		    		        	  width:'100%',
		    		        	  children:[
		    									{
		    										type:'formitem',
		    										label:'专利 代理 机构:',
		    										labelWidth:'100',
		    										labelAlign:'right',
		    										border:[0,0,0,0],
		    										padding:[0,5,0,0],
		    										children:[{type:'text',text:kData.subAgent,width:'200',readOnly:true}]
		    									  },
		    									  {
		    										  type:'formitem',
		    										  label:'代    理    人:',
		    										  labelWidth:'100',
		    										  labelAlign:'right',
		    										  border:[0,0,0,0],
		    										  padding:[0,0,0,0],										  
		    										  children:[{type:'text',text:kData.subPerson,width:'200',readOnly:true}]
		    									  }
		    								]
		    		          }      
		    		        ]}
		          ]});
		
	
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
		children: [
		           commentPanel, 
//		           actionPanel,
//		           citationPanel,
		           versionsPanel
		           ]
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
//			{type: 'button',icon: 'icon-cims201-action', text: '附件列表'},
//			{type: 'button',icon: 'icon-cims201-citation', text: '引证文献'},
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
	    horizontalAlign:'center',
	    children: [
	        topBar,
//	        flashKnowledge, 
	        detailTable,
	        centerTab, 
	        centerContent
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
	       keepAdd,
	       knowledgeKeyword,
//	       knowledgeDetail,
//	       relatedKnowledge,
	       popKnowledge	    
	    ]
	});
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
	cims201.utils.getData_Async('knowledge/tag/tag!list.action',{knowledgeID:kData.id},function(text){
		var ddd;
		if(text == null || text == ''){
				ddd = null;
		}else{			
			createTagBar(text,kData.id);			
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
		this.getPatentCNView = function(){
			return mainPanel;
		};
	
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
function createPatentTopBar(myTitle,id,borrowFlowId,borrowFlowNodeId,ktypeId,kSourceFilePath){

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
	
		
	this.getTopBar = function(){
		return topBar;
	};
	
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
	};
}






//创建评论界面
function createComment(knowledgeID,ktypename,bestAnswerId){

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