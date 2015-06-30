/**
 * 美国专利展示页面 jiangdingding 添加 2013-5-30
 * @param appCode
 * @param titleName
 * @param fullurl
 * @returns {createPatentUSView}
 */
function createPatentUSView(appCode, titleName, fullurl, keyword){
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
	var kData = cims201.utils.getkData('patent/patent!searchPatentDetailUS.action',{appCode:appCode,patent_name:titleName,fullurl:fullurl, keyword:keyword});
	
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
	rightBt += '<a href="javascript:downuspatenttext('+'\''+appCode+'\',\''+titleName+'\',\''+fullurl+'\',\''+keyword+'\')";><span class="cims201_knowledge_topbar_bt'+(currentBtIndex--)+'"><span class="cims201_knowledge_topbar_download">';
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

	if(null!=kData.abstract_)
	knowledgeAbstractpanel=new createKnowledgeAbstract(kData.abstract_).getKnowledgeAbstract();
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
//	       knowledgeKeyword,
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
		this.getPatentUSView = function(){
			return mainPanel;
		};
	
}
			