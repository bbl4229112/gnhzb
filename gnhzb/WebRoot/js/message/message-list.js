//消息列表

function createMessageList(){
	//创建角色list知识评论
   	var myConfig1 = {};
	var myColumns1 = [
               {
                   headerText: '',
                   dataIndex: 'isRead',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 30,
                   renderer: function(v){             
                   		if(v == 0){
                   			return '<img src="css/messageunread.gif"></img>';
                   		}
                   		else 
                   		return  '<img src="css/messagereaded.gif"></img>';
                   }
               },
               {
                   headerText: '发信人',
                   dataIndex: 'sender',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 100
               },
               {
                   headerText: '日期',
                   dataIndex: 'sendTime',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 80
               },
               {
                   headerText: '消息类型',
                   dataIndex: 'messageType',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 50
               },
               {
                   headerText: '知识',
                   dataIndex: 'knowledgeName',
                   headerAlign: 'center',                 
                   align: 'center',                
                   width: 150,
                   renderer: function(v,r){
                   		var title_outStr = '';
						if(r.knowledgeName.length > 4){
							title_outStr += r.knowledgeName.substring(0,4)+'...';
						}else{
							title_outStr += r.knowledgeName;
						}
                        return '<a href=javascript:showKnowledgeDetail('+r.knowledgeid+',"'+title_outStr+'",'+r.id+');><span class="knowledge_list_title_keyword">'+r.knowledgeName+'</span></a>';
                   }
               }  ,
                  {
                   headerText: '删除',
                   dataIndex: 'id',
                   headerAlign: 'center',                 
                   align: 'center',                
                   width: 50,
                   renderer: function(v,r){
                   		
                        return '<a href=javascript:deleteMessage('+r.id+');><span class="knowledge_list_title_keyword">删除</span></a>';
                   }
               } 
           ];
     
   	myTable1_rt = new createTable(myConfig1,'100%','100%','消息列表',myColumns1,[],[],'message/message!list.action', {},true);
   	
   	var 
   outBox	= Edo.create({
   		type: 'box',
   		width: '100%',
   		height: '100%',
   		layout: 'vertical',
   		border: [0,0,0,0],
   		padding: [0,0,0,0],
   		children: [
   			{
	   			type: 'box',
		   		width: '100%',
		   		layout: 'horizontal',
		   		border: [0,0,0,0],
		   		padding: [0,0,0,0],
		   		children:[
		   			{
		   				type: 'label',
		   				text: '消息类型'
		   			},
		   			{
		   				type: 'combo',
		   				id:'listtype',
		   				valueField: 'id',
					    displayField: 'label',
					    selectedIndex: 0,
					    data: [
					    	{label: '全部', id: 'all'},
					        {label: '评论', id: 'comment'},
					        {label: '审批', id: 'ApprovalFlow'},
					        {label: '推荐', id: 'recommend'},
					        {label: '订阅', id: 'subscribeknowledge'},
					        {label: '问题求解', id: 'askForAnswer'}
					    ],
					    
					    onselectionchange: function(e){
					        if('all' == this.getValue()){
					        	myTable1_rt.search({}, 'message/message!list.action');
					        }else{
					        	myTable1_rt.search({messageType: this.getValue()}, 'message/message!listTypeMessages.action');
					        }
					    }
		   				
		   			}	
		   		]
		   	},
   			myTable1_rt.getTable()
   		]
   	});
   	
   	this.getMessageList = function(){
   		return outBox;
   	}

}
   	function deleteMessage(messageid)
   		{
   			
   			cims201.utils.getData(
										'message/message!delete.action',
										{
											id : messageid
										});
   			var listtype=Edo.get('listtype').getValue();
   		      if('all' == listtype){
					        	myTable1_rt.search({}, 'message/message!list.action');
					        }else{
					        	myTable1_rt.search({messageType: this.getValue()}, 'message/message!listTypeMessages.action');
					        }
   		}