//消息列表

function createWaitedQuestionList(){
	//创建角色list知识评论
   	var myConfig1 = {};
	var myColumns1 = [
               {
                   headerText: '解答状态',
                   dataIndex: 'isAnswered',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: '10%',
                   renderer: function(v){             
                   		if(v == 0){
                   			return '<img src="css/messageunread.gif"></img>';
                   		}
                   		else 
                   		return  '<img src="css/messagereaded.gif"></img>';
                   }
               },
                {
                   headerText: '问题',
                   dataIndex: 'knowledgeName',
                   headerAlign: 'center',                 
                   align: 'center',                
                   width: '40%',
                   renderer: function(v,r){
                   		var title_outStr = '';
						if(r.knowledgeName.length > 4){
							title_outStr += r.knowledgeName.substring(0,4)+'...';
						}else{
							title_outStr += r.knowledgeName;
						}
                        return '<a href="javascript:showKnowledgeDetail('+r.knowledgeid+',\''+title_outStr+'\','+r.id+')";><span class="knowledge_list_title_keyword">'+r.knowledgeName+'</span></a>';
                   }
               },             
               {
                   headerText: '提问人',
                   dataIndex: 'sender',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: '20%'
               },
               {
                   headerText: '提问时间',
                   dataIndex: 'sendTime',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: '20%'
               },
                 {
                   headerText: '删除',
                   dataIndex: 'id',
                   headerAlign: 'center',                 
                   align: 'center',                
                   width: '10%',
                   renderer: function(v,r){
                   		
                        return '<a href=javascript:deleteQuestionMessage('+r.id+');>'+'<img src="css/questiondelete.gif"></img>'+'</a>';
                   }
               }              
                            
           ];
     
   	myTable1_rt = new createTable(myConfig1,'100%','100%','已选角色',myColumns1,[],[],'message/message!list.action', {},true);
   	myTable1_rt.search({messageType:'askForAnswer'}, 'message/message!listTypeMessages.action');
   	
   	var outBox = Edo.create({
   		type: 'box',
   		width: '100%',
   		height: '100%',
   		layout: 'vertical',
   		border: [0,0,0,0],
   		padding: [0,0,0,0],
   		children:myTable1_rt.getTable()
   		
   	});
   	
   	this.getMessageList = function(){
   		return outBox;
   	}
}

  	function deleteQuestionMessage(messageid)
   	{   			
   		cims201.utils.getData('message/message!delete.action',{id : messageid});
		myTable1_rt.search({messageType: 'askForAnswer'}, 'message/message!listTypeMessages.action');
   	}
