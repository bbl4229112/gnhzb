/**
显示我的回答的界面
*/
function createExpertQuestionRank(listtype,size,expertid){
	var myKnowledgerank;
	
	if(listtype=='eknowledge'){
    	myKnowledgerank = new createKnowledgeList_table('statistic/khotlist!listExpertInfo.action', {listtype:listtype,expertid:expertid}, [], []).getKnowledgeList().getTable();
	}else if(listtype=='earticle'){ //江丁丁添加 2013-6-20
    	myKnowledgerank = new createArticleList_table('statistic/khotlist!listExpertInfo.action', {listtype:listtype,expertid:expertid}, [], []).getKnowledgeList().getTable();
	}else{
		myKnowledgerank = new createCDQuestionList_table('statistic/khotlist!listExpertInfo.action', {listtype:listtype,expertid:expertid}, [], []).getKnowledgeList().getTable();
	}
    
    this.getKnowledgeRank = function(){
    	return myKnowledgerank;
    }    
}

//创建知识分类的面板
function createExpertInfoList(expertid){
	var outPanel = null;
	var currentType = 'eknowledge';
	var currentSize = 10;
	var khotlist = new createExpertQuestionRank(currentType,currentSize,expertid);
	
	var questioncategory = Edo.build({
			    type: 'togglebar',  
			    layout: 'horizontal',			          			    
			    children: [	
			        {
			            type: 'button',
			            text: '专家上传的知识',
			            onclick: function(e){
					        currentType = 'eknowledge';
					        if(outPanel){
					        	outPanel.removeChildAt(1);
						        khotlist.getKnowledgeRank().destroy();
						        khotlist = new createExpertQuestionRank(currentType,currentSize,expertid);
						        outPanel.addChild(khotlist.getKnowledgeRank());
					        }
							}
			        },	
			      //江丁丁添加
					{
					    type: 'button',
					    text: '专家发布的文章',
					    onclick: function(e){
					        currentType = 'earticle';
					        if(outPanel){
					        	outPanel.removeChildAt(1);
						        khotlist.getKnowledgeRank().destroy();
						        khotlist = new createExpertQuestionRank(currentType,currentSize,expertid);
						        outPanel.addChild(khotlist.getKnowledgeRank());
					        }
							}
					},		       
			        {
			            type: 'button',
			            text: '专家发布的问题',
			            onclick:function(e){
					        currentType = 'equestion';
					        if(outPanel){
					        	outPanel.removeChildAt(1);
						        khotlist.getKnowledgeRank().destroy();
						        khotlist = new createExpertQuestionRank(currentType,currentSize,expertid);
						        outPanel.addChild(khotlist.getKnowledgeRank());
					        }
					    }
			        },
			        {
			            type: 'button',
			            text: '专家回答的问题',
			            onclick:function(e){
					        currentType = 'eanswer';
					        if(outPanel){
					        	outPanel.removeChildAt(1);
						        khotlist.getKnowledgeRank().destroy();
						        khotlist = new createExpertQuestionRank(currentType,currentSize,expertid);
						        outPanel.addChild(khotlist.getKnowledgeRank());
					        }
						}
			        },
			        {
			            type: 'button',
			            text: '专家回答被采纳的问题',
			            onclick:function(e){
					        currentType = 'ebestanswer';
					        if(outPanel){
					        	outPanel.removeChildAt(1);
						        khotlist.getKnowledgeRank().destroy();
						        khotlist = new createExpertQuestionRank(currentType,currentSize,expertid);
						        outPanel.addChild(khotlist.getKnowledgeRank());
					        }
						}
			        }
			    ]
			});
	
	

	outPanel = Edo.create({
		type: 'box',
		width: '100%',
		height: '100%',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		layout: 'vertical',
		children: [
			{
				type: 'box',
				width: '100%',
				border: [0,0,0,0],
				padding: [0,0,0,0],
				layout: 'horizontal',
				children: [
					//{type: 'label', text: '排行类型'},					
					questioncategory
				]
			},
			khotlist.getKnowledgeRank()
		]
	});
	
	this.getinfolist = function(){
		return outPanel;
	}
}

