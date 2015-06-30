var picturepath;
var username;
var useremail;
function register(button) {

ChatManager.updateUsersList(true,function(data){
		if(data == null || data == '' || data == 'null') return;
		var onlineUsers = Edo.util.Json.decode(data);
			
		onlineUserDataList = new Array();
		
		onlineUserList.removeAllChildren();
		onlineUsers.each(function(o){
			var zd = new createNewUserOnline(o);
			onlineUserList.addChild(zd.getUser());
			onlineUserDataList[onlineUserDataList.length] = zd;		
		});
	}); 
}


function registerMessage()
{
ChatManager.listUnreadMessage(function(data){
	if(data == null || data == '' || data == 'null') return;
	var newMessages = Edo.util.Json.decode(data);		

	newMessageList.removeAllChildren();
	var myMessageListStr = Edo.util.Json.decode(newMessages.name);
	myMessageListStr.each(function(r,index){
		var title_outStr = '';
		if(r.knowledgeName.length > 10){
			title_outStr += r.knowledgeName.substring(0,10)+'...';
		}else{
			title_outStr += r.knowledgeName;
		}
		
		var outStr = '&nbsp;&nbsp;<img src="css/chat-icon.gif"></img>';
		
		if(r.messageType=="subscribeknowledge")
		{	outStr += '<span class="cims201_message_newmessage_f1">预定知识</span>';}
       	else{
       	outStr += '<span class="cims201_message_newmessage_f1">'+r.sender+'</span>';}
       	
       	//if('question' == r.messageType){
       	//	outStr += '向您提问知识';
       	//}else{
       	//	outStr += '向您推荐知识';
       	//}
       	outStr += r.content;
       	//outStr += '<a href=javascript:showKnowledgeDetail('+r.knowledgeid+',"'+title_outStr+'",'+r.id+');>';
       	outStr += '<span class="cims201_message_newmessage_f1">《'+title_outStr +'》</span>';	
       	//outStr += '</span>';
		//outStr += '</a>';
			
		newMessageList.addChildAt(index,{
			type: 'label',
			width: '100%',
			style: 'cursor:pointer;',
			onclick: function(e){
				showKnowledgeDetail(r.knowledgeid,title_outStr,r.id);
				newMessageList.removeChildAt(index);
				messageBarBtList[1].setText('消息列表');
				registerMessage();
			},
			text: outStr
		});
	});

	if(newMessages.id && newMessages.id != 0){
		messageBarBtList[1].closeNewMessage();
		
		messageBarBtList[1].setText(newMessages.id+'条未读消息');
		newMessageList.addChild({
			type: 'label',
			width: '100%',
			text: '&nbsp;&nbsp;<a href=javascript:showMessageList();><img src="css/messageunread.gif"></img>查看所有消息</a>'
		});
		messageBarBtList[1].showNewMessage();
	}
});

}


function changeTitle()
{
	alert(dwr.util.getValue('msg'));
}

function init() {
 dwr.engine.setActiveReverseAjax(true); 

 ChatManager.updateUsersList(false,function(data){
 
 var userInfo=new Array();
 userInfo=data.split("+");
 username=userInfo[0];
 useremail=userInfo[1];
 picturepath="/caltks/thumbnail/"+userInfo[2];

 
 
 }); 

}


function send() {

 var sender = dwr.util.getValue('username'); 
 var receiver = dwr.util.getValue('receiver'); 
 var msg = dwr.util.getValue('message'); 
 ChatManager.send(sender, receiver, msg); 
}

window.onload = init;

function showMessageList(){
	var params=new function(){
	this.btIcon=null;
	}
	
	//params['btIcon']=null;
	openNewTab(13,"messagelist", "查看消息",params);
}

function showNewMessage(){
	messageBarBtList[1].showNewMessage();
}