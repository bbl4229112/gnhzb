//创建知识分类

function createQuestionRanks(listtype,size){
	var myKnowledgerank = new createCDQuestionList_table('statistic/khotlist!listQuestion.action', {listtype:listtype}, [], []).getKnowledgeList().getTable();
	
    this.getKnowledgeRank = function(){
    	return myKnowledgerank;
    }    
}

//创建知识分类的面板
function createQuestionlistPanel(){
	var outPanel = null;
	var currentType = 'uploadtime';
	var currentSize = 10;
	var khotlist = new createQuestionRanks(currentType,currentSize);
	
	var questioncategory = Edo.build({
			    type: 'togglebar',  
			    layout: 'horizontal',			          			    
			    children: [
			        {
			            id: 'Newquestion',  
			            type: 'button', 			                   
			            text: '最新问题'        
			        },
			        {
			            id: 'Hotquestion', 
			            type: 'button',            
			            text: '最热问题'          
			        },        
			        {
			            id: 'Zeroanswer', 
			            type: 'button',
			            text: '零回答问题'
			        },
			        {
			            id: 'Waitanswer', 
			            type: 'button',
			            text: '待解决问题'
			        },
			        {
			            id: 'Existanswer', 
			            type: 'button',
			            text: '已解决问题'
			        }
			    ]
			});
			
	Newquestion.on('click', function(e){
	        currentType = 'uploadtime';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createQuestionRanks(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
	});
	Hotquestion.on('click', function(e){
	        currentType = 'commentCount';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createQuestionRanks(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
	});
	Zeroanswer.on('click', function(e){
	        currentType = 'zeroanswer';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createQuestionRanks(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
	});
	Waitanswer.on('click', function(e){
	        currentType = 'waitanswer';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createQuestionRanks(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
	});
	Existanswer.on('click', function(e){
	        currentType = 'existanswer';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createQuestionRanks(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
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
	
	this.getKhot = function(){
		return outPanel;
	}
}
