/**
显示评论的界面
*/
var bestAnswerId=null;
var showBestAnswerBt;
function createCommentPanel(ktypename,best_answer_id,knowledgeID){	
	//新增评论的界面
	//var knowledgeID;
	var commentID;
	var myWin = null;
	var htmleditor1 = null;
	bestAnswerId=best_answer_id;
	//alert('sss'+bestAnswerId);
	//定义摘要的字数和行数
	var abstractTextLength = 60;
	var abstractTextLine = 2; 
	//默认多少行
	var defaultPageSize = 10;
	
	var myUrl = {};
	var myInputForm = {};
	
	
	//列表数据
	var rData;
	
	
	var contentBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		width: '100%',
		//height: '100%',
		layout: 'vertical',
		children: []
	});
	
	//最佳答案列表
		cims201.utils.getData_Async('comment/comment!listBestAnswer.action',{knowledgeID:knowledgeID},function(text){
				//var rData = cims201.utils.getData(myUrl,myInputForm);
				//alert(text);
				Edo.util.Json.decode(text).each(function(c){
				
				 if(null==Edo.get('bestAnswers'+c.id)){
				 Edo.get(bestAnswerId).set('visible',true);
					
					Edo.get(bestAnswerId).addChild(Edo.create({
							type: 'box',
							id:'bestAnswers'+c.id,
							//bodyCls: 'cims201_comment_replyr_bg',
							border: [0,0,1,0],
							padding: [0,0,0,0],
							width: '100%',
							layout: 'vertical',
							children:[createSingleComment(c)]
						}) );
				}
				});
	});
	
	//首先判断paging bar是否存在		  
   	myPagerId = contentBox.id+'paging';
	myPager = Edo.create({
		id: myPagerId,    
	    type: 'pagingbar',
	    width: '100%',
	    visible: false,
	    //设置默认值
	    size: 5,
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
			
	var oc = Edo.create({
  				type: 'box',
  				border: [0,0,0,0],
  				padding: [12,0,0,12],
  				width: '100%',
  				height: 40,
  				layout: 'horizontal',
  				children: [
  					
  					{type: 'button', icon: 'cims201_comment_edit', text: ((ktypename=='问题')? '我来回答':'快速评论'), onclick: function(e){
  						knowledgeID = myInputForm.knowledgeID;
  						commentID = null;
  						if(knowledgeID){
  							addComment();
  						}else{
  							cims201.utils.warn(null,null,((ktypename=='问题')? '您还没有查看任何问题，还不能回答！':'您还没有查看任何知识，还不能评论！'),null,null);
  						}
  					}}
  				]
  				
  			});
	
	var mainBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,20,0],
		width: '100%',
		//height: '100%',
		layout: 'vertical',
		children: [oc, myPager, contentBox, bBar]
		//children: [contentBox]
	});
	
	this.getComment = function(){
		return mainBox;
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
	
	//搜索数据
	function search(){
		if(myUrl != null && myUrl != ''){		    			    
		    myInputForm.index = myPager.index;    
		    myInputForm.size = myPager.size;		   
			
			cims201.utils.getData_Async(myUrl,myInputForm,function(text){
				//var rData = cims201.utils.getData(myUrl,myInputForm);
				//alert(text);
				rData = Edo.util.Json.decode(text);
				if(oc.children.length > 1){
					oc.removeChildAt(0);
				}
				//设置顶部条我要上传
				var topLabel = Edo.create(
				{
					type: 'label', 
					style:'padding-right:10px;',
					text: ' 共有<span class="cims201_comment_focus">'+(rData.kccounts?rData.kccounts:0)+'</span>条记录'
				});
				oc.addChildAt(0,topLabel);
				
				showComments(rData.data);
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
			});
		}	
	}
	
	
	//生成整个评论内容
	function showComments(data){
		if(data){		
			contentBox.removeAllChildren();	
		//	Edo.get(bestAnswerId).removeAllChildren();			 
			data.each(function(o,iii){
				//contentBox.removeChildAt(iii);	
				//alert(o.isBest+"__"+iii);			
				var oneComment = createRReplyBox(o,iii);		
				oneComment.set('bodyCls','cims201_comment_bg');
				oneComment.set('border',[1,1,1,1]);
				contentBox.addChild(oneComment);

			});
		}
	}
	
	//生成单条评论
	function createSingleComment(c,isSubComment){
		//author bar
		//alert(showBestAnswerBt);
		var ct = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,12],
			width: '100%',
			//height: 45, 
			layout: 'horizontal',
			children: [
			
				{type: 'label', width: '50%', text: '<span class="cims201_comment_date">'+c.commmentTime+' 发表</span>'}
				//{type: 'label', width: '10', style:'float:right; font-size: 12px;', text: ''+c.index+'#'}
			           ]
		});
			
		var tttt = c.content;
		var out_tttt = cims201.utils.addBrToText(tttt,abstractTextLength);	
		var cc = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,12],
			width: '100%',
			bodyCls: 'cims201_comment_list_f1',
			children: [
				{type: 'boxtext', width: cims201.utils.getScreenSize().width-600, text: ((c.commented!=null&&isSubComment==1)? ('<font color="red">回复'+c.commented.commenterName+':</font>'):'')+c.content}
			]
		});
		var cdbutton=Edo.create({ type: 'button',visible:false,icon: 'cims201_comment_delete',commentId:c.id, text:'删除', onclick: function(e){
			commentID = this.commentId;
			Edo.MessageBox.confirm("确认删除", "你确定这条解释吗？",
		    		 function(action){
			
			cims201.utils.getData_Async('comment/comment!delete.action',{commentid:commentID},function(){
				search();	
			});
			}
			 );
		}});
		var bestAbswerBt=Edo.create ({type: 'button',icon: 'cims201_comment_edit',visible: false,commentId:c.id, text:'采纳', onclick: function(e){
							//knowledgeID = myInputForm.knowledgeID;
							//commentID = this.commentId;
							 bestAnswer(c);
							 bestAbswerBt.set('visible',false);
							 cdbutton.set('visible',false);
						}});

		var showdelete= cims201.utils.getData('privilege/show-button!whetherShowcommentdeleteButton.action',{knowledgeId:c.id});
		
		if(showdelete.show==true){
			
			cdbutton.set('visible',true);
		}
		if(ktypename=='问题'){
			var cbottom = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,12],
			width: '100%',
			layout: 'vertical',
			children: [
				Edo.create({
					type: 'box',
					padding: [20,0,0,0],
					border: [0,0,0,0],
					width: '100%',
					layout: 'horizontal',
					children: [
						{
							type: 'label',
							width: '100%',
							text: ''
						},cdbutton,
						{type: 'button',icon: 'cims201_comment_edit',commentId:c.id, text:'回复', onclick: function(e){
							knowledgeID = myInputForm.knowledgeID;
							commentID = this.commentId;
							addComment(c);
						}},
						{type: 'button',icon: 'icon-cims201-comment-agree',commentId:c.id, vcount:c.supportVoteCount,text:'支持('+c.supportVoteCount+')', onclick: function(e){
							var res = cims201.utils.getData('comment/comment!addCommentVote.action',{commentid:c.id,isSupport:true});
							if(res['isSupport'] == true || res['isSupport'] == 'true' || res['isSupport'] == 1){
								alert('支持成功!');
								this.set('text','支持('+(this.get('vcount')+1)+')');
							}else{
								alert('您已经支持过该答案了!');
							}
						}},
						{type: 'button',icon: 'icon-cims201-comment-disagree',commentId:c.id,vcount:c.againstVoteCount, text:'反对('+c.againstVoteCount+')', onclick: function(e){
							var res = cims201.utils.getData('comment/comment!addCommentVote.action',{commentid:c.id,isSupport:false});
							if(res['isSupport'] == true || res['isSupport'] == 'true' || res['isSupport'] == 1){
								alert('反对成功!');
								this.set('text','反对('+(this.get('vcount')+1)+')');
							}else{
								alert('您已经反对过该答案了!');
							}
						}},bestAbswerBt
					]
				})
				
				//{type: 'label', width: '100%', text: '<div class="cims201_comment_reply_f1"><a style="text-decoration: none;" href="">顶<span class="cims201_comment_focus">[34]</span></a>  <a style="text-decoration: none;" href=javascript:addCommented();>回复</a></div>'},
				//{type: 'label', width: '100%', text: '<div class="cims201_comment_reply_f1">'+commentLabel+'</div>'}
			]
		});
		
		}else{
			var cbottom = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,12],
			width: '100%',
			layout: 'vertical',
			children: [
				Edo.create({
					type: 'box',
					padding: [20,0,0,0],
					border: [0,0,0,0],
					width: '100%',
					layout: 'horizontal',
					children: [
						{
							type: 'label',
							width: '100%',
							text: ''
						},cdbutton,
						{type: 'button',icon: 'cims201_comment_edit',commentId:c.id, text:'回复', onclick: function(e){
							knowledgeID = myInputForm.knowledgeID;
							commentID = this.commentId;
							addComment(c);
						}},
						{type: 'button',icon: 'icon-cims201-comment-agree',commentId:c.id, vcount:c.supportVoteCount,text:'支持('+c.supportVoteCount+')', onclick: function(e){
							var res = cims201.utils.getData('comment/comment!addCommentVote.action',{commentid:c.id,isSupport:true});
							if(res['isSupport'] == true || res['isSupport'] == 'true' || res['isSupport'] == 1){
								alert('支持成功!');
								this.set('text','支持('+(this.get('vcount')+1)+')');
							}else{
								alert('您已经支持过该评论了!');
							}
						}},
						{type: 'button',icon: 'icon-cims201-comment-disagree',commentId:c.id,vcount:c.againstVoteCount, text:'反对('+c.againstVoteCount+')', onclick: function(e){
							var res = cims201.utils.getData('comment/comment!addCommentVote.action',{commentid:c.id,isSupport:false});
							if(res['isSupport'] == true || res['isSupport'] == 'true' || res['isSupport'] == 1){
								alert('反对成功!');
								this.set('text','反对('+(this.get('vcount')+1)+')');
							}else{
								alert('您已经反对过该评论了!');
							}
						}}
					]
				})
				
				//{type: 'label', width: '100%', text: '<div class="cims201_comment_reply_f1"><a style="text-decoration: none;" href="">顶<span class="cims201_comment_focus">[34]</span></a>  <a style="text-decoration: none;" href=javascript:addCommented();>回复</a></div>'},
				//{type: 'label', width: '100%', text: '<div class="cims201_comment_reply_f1">'+commentLabel+'</div>'}
			]
		});
		}
		//是否显示‘采纳’按钮
		if(c.isBest!=1){
		cims201.utils.getData_Async('privilege/show-button!whetherShowBestAnswerButton.action',{knowledgeId:knowledgeID}, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
		
		    bestAbswerBt.set('visible',true);
		}
		
		
		});
		}
		
		
		var cb = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,0],
			width: '100%',
			layout: 'vertical',
			children: [ct,cc,cbottom]
		});
		
		//左边的人员信息显示栏
		var lb = Edo.create({
			type: 'box',
			border: [0,1,0,0],
			padding: [0,0,0,0],
			width: (c.commented!=null&&isSubComment==1)?50:100,
			height: '100%',
			bodyStyle: 'background-color: rgb(223,234,255);	',
			layout: 'vertical',
			children: (c.commented!=null&&isSubComment==1)?[
				{type: 'label', minHeight:'5', text:''},
				{type: 'label', style:'padding-top;15px; padding-left:2px; padding-right:8px;', text: '<img src="'+c.commenterpicpath+'" height=50></img>'},
				{type: 'label', width: '100%', style:'padding-left: 5px;color: #1E50A2; display: block;', text: c.commenterName}
			]:
			[
				{type: 'label', minHeight:'10', text:''},
				{type: 'label', style:'padding-top;10px; padding-left:1px; padding-right:10px;', text: '<img  src="'+c.commenterpicpath+'"  height=110></img>'},
				{type: 'label', width: '100%', style:'padding-left: 38px;color: #1E50A2; display: block;', text: c.commenterName}
			]
		});
		
		var mb = Edo.create({
			type: 'box',
			border: (c.commented!=null&&isSubComment==1)?[1,0,0,0]:[0,0,0,0],
			//padding: [10,10,0,10],
			padding: [0,0,0,0],
			width: '100%',
			layout: 'horizontal',
			children: [lb , cb]
		});
		
		return mb;
	}
	function expandComment(c,subcomment,isSubComment){

		var outReply = null;
		if(c != null && c!= '' ){
         if(isSubComment==1)
		    { subcomment.addChild(createSingleComment(c,1));
		   }

		  var subdata = c.commentdtos;
			  if(subdata != null && subdata!= ''){
				
				subdata.each(function(o){
               expandComment(o,subcomment,1);
				
							});
			       
			    }
	
		
		outReply=subcomment;
		}
	
		return outReply;
		
	}
	
	
	//生成嵌套评论
	function createRReplyBox(c){
	var outReply = null;
		//如果评论不为空
		
	if(c != null && c!= '' ){

	var descendent=[];
	var initialcomment=createSingleComment(c);
	
	  var subdata = c.commentdtos;
	     if(subdata != null && subdata!= ''){
						var subcomment=Edo.create({
									type: 'box',
									bodyCls: 'cims201_comment_replyr_bg',
									border: [0,0,1,0],
									padding: [0,0,0,0],
									width: '100%',
									layout: 'vertical',
									children:[]
								});	
							subcomment=expandComment(c,subcomment,0);	
							
						    initialcomment.getChildAt(1).addChild(subcomment);
	   }
	
	var children=descendent.add(initialcomment);

		outReply = Edo.create({
				type: 'box',
				bodyCls: 'cims201_comment_replyr_bg',
				border: [0,0,1,0],
				padding: [0,0,0,0],
				width: '100%',
				layout: 'vertical',
				children:children
			});	
				
			}
		//如果是最佳答案 单独加到‘最佳答案’栏目	
	//alert(c.knowledgeid);
		if(c.isBest==1)
		{   
	
		}

		return outReply;
	}
	
	//列为最佳答案
	function bestAnswer(c){
	cims201.utils.getData_Async('comment/comment!bestAnswer.action',{commentid:c.id},function(){
							addBestAnswer(c);	
						});
	}
	function addBestAnswer(c){
	 if(null==Edo.get('bestAnswers'+c.id)){
					c.isBest=1;
					 Edo.get(bestAnswerId).set('visible',true);
					Edo.get(bestAnswerId).addChild(Edo.create({
							type: 'box',
							id:'bestAnswers'+c.id,
							//bodyCls: 'cims201_comment_replyr_bg',
							border: [0,0,1,0],
							padding: [0,0,0,0],
							width: '100%',
							layout: 'vertical',
							children:[createSingleComment(c)]
						}) );
				}
	
	}
	//评论的事件
	function addComment(targetComment){
		if(myWin == null){			
			htmleditor1 = Edo.create({
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
					htmleditor1,
					{type: 'button', text: '确定提交', onclick: function(e){
						var qqq = {};
						if(knowledgeID){
							qqq['knowledgeID'] = knowledgeID;
						}
						if(commentID){
							qqq['commentid'] = commentID;
						}
						qqq['content'] = htmleditor1.get('text');
						//cims201.utils.getData('comment/comment!save.action',qqq);
						cims201.utils.getData_Async('comment/comment!save.action',qqq,function(){
							search();	
						});
						
						myWin.hide();
					}}
				]
			});
			myWin = cims201.utils.getWin(500,230,((ktypename=='问题')?'回答问题':'添加评论'),editorBox);
		}
		
		//增加遮罩
		htmleditor1.set('text','');
	//	myWin.show(200,100,true);
		setWinScreenPosition(500,230,myWin,knowledgeID)
	}

}


