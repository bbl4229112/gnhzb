/**
显示我的回答的界面
*/
var myQuestionTable;
function createQuestionRank(listtype,size){
	var myKnowledgerank = new createCDQuestionList_table('statistic/khotlist!listQuestion.action', {listtype:listtype}, [], []).getKnowledgeList().getTable();	
    
    this.getKnowledgeRank = function(){
    	return myKnowledgerank;
    }    
}

//创建知识分类的面板
function createMyAnswerList(){
	var outPanel = null;
	var currentType = 'myquestion';
	var currentSize = 10;
	myQuestionTable = new createPersonalQuestionList_table('statistic/khotlist!listQuestion.action', {listtype:'myquestion'}, [], []);
	var khotlist = myQuestionTable.getKnowledgeList().getTable();
	
	var questioncategory = Edo.build({
			    type: 'togglebar',  
			    layout: 'horizontal',			          			    
			    children: [	
			        {
			            id: 'Myquestion', 
			            type: 'button',
			            text: '我的全部提问'
			        },		       
			        {
			            id: 'Myanswer', 
			            type: 'button',
			            text: '我的全部回答'
			        },
			        {
			            id: 'Acceptedanswer', 
			            type: 'button',
			            text: '被采纳的回答'
			        }
			    ]
			});
			
	Myquestion.on('click', function(e){
	        currentType = 'myquestion';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.destroy();
		        myQuestionTable = new createPersonalQuestionList_table('statistic/khotlist!listQuestion.action', {listtype:'myquestion'}, [], []);
		        khotlist = myQuestionTable.getKnowledgeList().getTable();
		        outPanel.addChild(khotlist);
	        }
	});
				
	Myanswer.on('click', function(e){
	        currentType = 'myanswer';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.destroy();
		        khotlist = new createQuestionRank(currentType,currentSize).getKnowledgeRank();
		        outPanel.addChild(khotlist);
	        }
	});
	Acceptedanswer.on('click', function(e){
	        currentType = 'acceptedanswer';
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.destroy();
		        khotlist = new createQuestionRank(currentType,currentSize).getKnowledgeRank();
		        outPanel.addChild(khotlist);
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
			khotlist
		]
	});
	
	this.getMyanswerlist = function(){
		return outPanel;
	}
}

