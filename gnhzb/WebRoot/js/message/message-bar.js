//定义全局变量
//底栏按钮列表
var messageBarBtList = new Array();

//新消息和好友列表框的坐标
var messageAndFriendList_x;
var messageAndFriendList_y;

//新消息和好友列表管理框
var messageAndFriendManager = {};

//好友列表
var onlineUserList = null;
//用户信息
var userInfo=null;
//好友列表数据
var onlineUserDataList = new Array();

var newMessageList = null;
//好友列表数据
var newMessageDataList = new Array();

//新建消息条
function createMessageBar(cont){
	var myCont;
	if(cont == null || cont == ''){
		myCont = document.body;
	}else{
		myCont = cont;
	}
	var screenSize = cims201.utils.getScreenSize();
	
	//创建底层按钮
	//好友列表按钮id,icon,text,onclickevent
	var friendListBt = new createMessageBarBt('friendListBt','cims201_message_onlineuser','在线好友',function(e){
		messageBarBtManager('right','friendListItem');
	});
	var messageListBt = new createMessageBarBt('messageListBt','cims201_message_newmessage','消息列表',function(e){
		messageBarBtManager('right','newMessageItem');
	});
	var userInfoBt = Edo.create({
				type : 'button',
				id : 'userInfoBt',
				minWidth : 50,
				
				text : '<span>帐号</span><image style=height:5px  src="js/edo/res/images/table/sort_asc2.gif">',
				onclick : function(e) {
					
					messageBarBtManager('right','userInfoItem');
				}
			});
	
	messageBarBtList = new Array();
	messageBarBtList[0] = friendListBt;
	messageBarBtList[1] = messageListBt;
	
	
	//创建好友列表和新消息显示框
	var friendListItem = new createFriendListItem();
	messageAndFriendManager['friendListItem'] = friendListItem;
	var newMessageItem = new createNewMessageItem();
	messageAndFriendManager['newMessageItem'] = newMessageItem;
	var userInfoItem = new createUserInfoItem();
	messageAndFriendManager['userInfoItem'] = userInfoItem;
		
	var messageBar = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		bodyCls: 'cims201_message_bar_bg',
		render: myCont,		
		width: screenSize.width,
		height: 20,	
		layout: 'horizontal',	
		children: [
			//{type: 'space', width:screenSize.width/2-150 },
			//{},
			//{type: 'space', width:screenSize.width/2-270 },
//			{type: 'split'},
//			friendListBt.getBt(),
//			{type: 'split'},
//			messageListBt.getBt(),
//			{type: 'split'},
//			userInfoBt,
//			{}
		]
	});
	var introduction = Edo.create({
		type: 'label',
		width:'100%', 
		style:'font-size:11px; color: blue; padding-left:10px;', 
		text: '浙江大学现代制造工程研究所cims项目组 Copyright &copy; 2008-2011 <a target=_blank href="http://www.calt.com">http://www.ie.zju.edu.cn</a>' 
	});
	var split = Edo.create({
		type : 'split'
	});
	var margin = Edo.create({
		type: 'label',width:screenSize.width*0.005
	});
	messageBar.addChild(introduction);
	messageBar.addChild(split);
	var data=cims201.utils.getData('custom/customization!whetherShowCooperationOnLine.action');
	if(data == true || data == 'true'){
		messageBar.addChild(friendListBt.getBt());
		messageBar.addChild(split);
	}
	messageBar.addChild(messageListBt.getBt());
	messageBar.addChild(split);
	messageBar.addChild(userInfoBt);
	messageBar.addChild(split);
	messageBar.addChild(margin);
	
	//创建好友列表和新消息的坐标体系
	var friendListBt = Edo.get('friendListBt');

	messageAndFriendList_x = userInfoBt.x -230+ 3;
	messageAndFriendList_y = cims201.utils.getScreenSize().height- 400+2;
	
	this.getMessageBar = function(){
		return messageBar;
	}	
	
	//增加按钮
	this.addNewBt = function(id,name){
		var myBt = new createMessageBarBt(id+'dialog',null,name,null);
		messageBar.addChildAt(2,myBt.getBt());		
	}	
	
	
	//加载消息列表
	newMessageItem.refresh();
}

//按钮管理器
function messageBarBtManager(type,id){
	if('right' == type){
		for(var key in messageAndFriendManager){
			if(key != id){
				if(messageAndFriendManager[key].isVisible()){
					messageAndFriendManager[key].removePopup();
				}
			}else{
				if(messageAndFriendManager[key].isVisible()){
					messageAndFriendManager[key].removePopup();
					messageAndFriendManager[key].refresh();
				}else{
					messageAndFriendManager[key].popup();
					if(key!="newMessageItem")
					messageAndFriendManager[key].refresh();
				}
			}
		}
	}
}



//在线好友的弹出窗口
function createFriendListItem(){	
	if(onlineUserList == null){
		onlineUserList = Edo.create({
			type: 'panel',
			title: '在线好友列表',
			width: 200,
			height: 400,
			visible: false,
			padding: [0,0,0,0],
			titlebar:[
				{
			        cls:'e-titlebar-min',
			        onclick: function(e){                        
			            //this.parent.owner.toggle();
			        	onlineUserList.set('visible', false);
	            		Edo.managers.PopupManager.removePopup(onlineUserList);
			        }
			    }				
			],
			render: document.body,
			children:[]
		});
	}
			
	this.popup = function(){
		if(onlineUserList.visible == false){ 		                       
		    Edo.managers.PopupManager.createPopup({
		        target: onlineUserList,                                
		        x: messageAndFriendList_x,
		        y: messageAndFriendList_y,
		
		        onout: function(e){
		    
		        	//onlineUserList.set('visible', false);
		            //Edo.managers.PopupManager.removePopup(onlineUserList);
		        }
		    });
		}
	}
	//移除弹出框
	this.removePopup = function(){
		onlineUserList.set('visible', false);
	    Edo.managers.PopupManager.removePopup(onlineUserList);
	}
	
	//刷新数据
	this.refresh = function(){
		//获取后台数据		
		//DWREngine.setAsync(false);
		register();
		//DWREngine.setAsync(true);	
	}
	
	//获取所有的数据
	this.getAllFriend = function(){
		return onlineUserDataList;
	}
	
	this.isVisible = function(){
		return onlineUserList.visible;
	}
}

//新消息弹出窗口
function createNewMessageItem(){	
	if(newMessageList == null){
		newMessageList = Edo.create({
			type: 'panel',
			title: '本人消息',
			width: 230+250,
			height: 600-100,
			visible: false,
			padding: [0,0,0,0],
			titlebar:[
				{
			        cls:'e-titlebar-min',
			        onclick: function(e){                        
			            //this.parent.owner.toggle();
			        	newMessageList.set('visible', false);
	            		Edo.managers.PopupManager.removePopup(newMessageList);
			        }
			    }				
			],
			render: document.body,
			children:[]
		});
	}
			
	this.popup = function(){
		if(newMessageList.visible == false){ 		                       
		    Edo.managers.PopupManager.createPopup({
		        target: newMessageList,                                
		        x: messageAndFriendList_x-250,
		        y: messageAndFriendList_y+100,
		        onout: function(e){
		        	//newMessageList.set('visible', false);
		            //Edo.managers.PopupManager.removePopup(newMessageList);
		        }
		    });
		}
	}
	
	//移除弹出框
	this.removePopup = function(){
		newMessageList.set('visible', false);
	    Edo.managers.PopupManager.removePopup(newMessageList);
	}
	
	//刷新数据
	this.refresh = function(){
		//获取后台数据
		registerMessage();
	}
	
	//获取所有的数据
	this.getAllMessage = function(){
		return newMessageDataList;
	}
	
	this.isVisible = function(){
		return newMessageList.visible;
	}
}

//提示新的消息
function remaindNewMessage(){
	messageAndFriendManager['newMessageItem'].refresh();
	//messageBarBtList[1].showNewMessage();

}
//创建个人信息列表
function createUserInfoItem(){	

	if(userInfo == null){
		userInfo = Edo.create({
			type: 'panel',
			title: '用户信息',
			width: 140,
			height: 220,
			visible: false,
			padding: [5,0,0,0],
			titlebar:[
				{
			        cls:'e-titlebar-min',
			        onclick: function(e){                        
			            //this.parent.owner.toggle();
			        	userInfo.set('visible', false);
	            		Edo.managers.PopupManager.removePopup(userInfo);
			        }
			    }				
			],
			render: document.body,
			children:[
				{
	            type: 'ct',
	            width: '100%', height: 70,
	             
	            layout: 'horizontal',
	            children:[
			            {
			            type: 'label',
			            width: '60%', height: '100%',
			            id:'userInfo_pic',
			            text:''
			            
			            },
			            {
			            type: 'label',
			            id:'userInfo_name',
			            width: '40%', height:40,
			            text:username
			            
			            }
                        ] 
                },
            
			{
            type: 'label',
            width: '100%', height: '100%',
            id:'userInfo_info',
            text:''
            
            },
            {
            type: 'label',
            width: '100%', height: 40,
            id:'userInfo_logout',
            onMouseover : function() {
			tempStatus = false;
			this.set('style','background:#e4e4e4;');
	                                	},
		    onMouseout : function() {
			tempStatus = true;
			this.set('style','background:white;');
		                            },
	    	style : 'padding-bottom:5px; float:left;',
            text:''
            
            }
			
			
			]
		});
	}
			
	this.popup = function(){
	Edo.get('userInfo_pic').setValue('<img src="'+picturepath+'" width=60px height=60px></img>');
	Edo.get('userInfo_name').setValue('<div class="cims201_topbar_icon"><br>'+username+'</div>');
	Edo.get('userInfo_info').setValue(useremail);
	
	Edo.get('userInfo_logout').set('text','<span><a style="" href="'+basePath+'j_spring_security_logout"><div class="cims201_topbar_icon"><image style=height:35px  src="css/images/exit2.png">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp注销&nbsp</div></a></span>');
		if(userInfo.visible == false){ 		                       
		    Edo.managers.PopupManager.createPopup({
		        target: userInfo,                                
		        x: messageAndFriendList_x+140,
		        y: messageAndFriendList_y+180,
		        onout: function(e){
		        	//onlineUserList.set('visible', false);
		            //Edo.managers.PopupManager.removePopup(onlineUserList);
		        }
		    });
		}
	}
	//移除弹出框
	this.removePopup = function(){
		userInfo.set('visible', false);
	    Edo.managers.PopupManager.removePopup(userInfo);
	}
	
	//刷新数据
	this.refresh = function(){
		//获取后台数据		
		//DWREngine.setAsync(false);
		
		//DWREngine.setAsync(true);	
	}
	

	this.isVisible = function(){
		return userInfo.visible;
	}
	}