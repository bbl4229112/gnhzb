var userChatBoxList = {};



//构建聊天盒
function createNewChatItem(id,name,email){
	//对应的按钮
	var userBt;
	
	var newChatBox = Edo.create({
		type: 'panel',
		title: '<span style="font-size:12px;">您和'+name+'的留言板</span>',
		width: 230,
		height: 300,
		visible: false,
		padding: [3,0,0,0],
		mydestroy: function(){
			userChatBoxList[id] = undefined; 
			newChatBox.destroy();
			//从messagebar移除该按钮
			myMessageBar.children.each(function(c,index){
				if(c.id == (id+'_bt')){
					myMessageBar.removeChildAt(index);
				}
			});
			userBt.getBt().destroy();
		},
		titlebar:[
			{
		        cls:'e-titlebar-min',
		        onclick: function(e){                        
		            //this.parent.owner.toggle();
		        	newChatBox.set('visible', false);
            		Edo.managers.PopupManager.removePopup(newChatBox);
		        }
		    },
		    {
		        cls:'e-titlebar-close',
		        onclick: function(e){
		            newChatBox.mydestroy();
		            //删除对应的enter事件
					enterEventIDList.each(function(o,mi){
						if(('dialog_'+id+'_submit') == o){
							enterEventIDList.splice(mi,1);
							enterEventList.splice(mi,1);
						}
					});
		        }
	        }				
		],
		render: document.body,
		layout: 'vertical',
		children:[
	
		{type:'boxtext',
		 width: '100%',
		 height: '97%',
		 style: 'overflow:auto;',
		 text:'',
		 id: 'dialog_'+id,
		 padding: [5,2,5,2]
		 
		 
		 
		 },
			{
				type: 'box',
				width: '100%',
				height: 52,
				border: [1,0,0,0],
				padding: [5,2,5,2],
				layout: 'horizontal',
				children: [
					{type: 'textarea', id: 'dialog_'+id+'_input', width:'100%',rows:'2', onmouseover:function(e){
						//当鼠标滑过的时候，消除按钮的提示效果
						userBt.closeNewMessage();
					}, 
					onclick: function(e){
						currentEventID = 'dialog_'+id+'_submit';
					},
					onblur: function(e){
						currentEventID = null;
					}},
					{type: 'button', text: '发送', id: 'dialog_'+id+'_submit', onclick: function(e){
						Edo.get('dialog_'+id+'_submit').enter();
					},
					//回车的时候访问的时间
					enter: function(){
					Edo.get('dialog_'+id+'_input').blur();
					var myMsg=Edo.get('dialog_'+id+'_input').getValue().replace(/(^\s*)|(\s*$)/g, "");  
					if(myMsg==""){ Edo.MessageBox.alert("提示", "请输入内容!");}
					else{
						
						//var currentText = Edo.get('dialog_'+id).get('text');
						//currentText += '<br>';
						//currentText += name+':'+Edo.get('dialog_'+id+'_input').get('text');
						ChatManager.send('user',id,Edo.get('dialog_'+id+'_input').get('text'));
						
						//在页面上显示新的聊天记录
						//Edo.get('dialog_'+id).addChild({
						//	type: 'label',
						//	width: '100%',
						//	text: '自己 在 '+new Date()+'<br>'+currentText
						//});
						var eee = Edo.get('dialog_'+id);
						
						var nowDate = new Date();
						var month=nowDate.getMonth()+1;
						
						if(month<10)
						month="0"+month;
						var date=nowDate.getDate();
						if(date<10)
						date="0"+date;
						var hour=nowDate.getHours();
						if(hour<10)
						{hour="0"+hour;}
						var min=nowDate.getHours();
						if(min<10)
						{min="0"+min;}
						var sec=nowDate.getSeconds();
						if(sec<10)
						{sec="0"+sec;}
						
						Edo.get('dialog_'+id).setValue(Edo.get('dialog_'+id).getValue().replace('<a name="chat_buttom"></a>','')+'<span style="font-size:10px;padding-left:5px"><span style="color:#3A5FCD;font-size:11px;"><img src="'+picturepath+'" width=30px height=30px></img>'+username+'</span>  &nbsp&nbsp'+month+'-'+date+' '+hour+':'+min+':'+sec+'</span><div  style="padding-left:5px;padding-top:3px;padding-bottom:4px">'+myMsg+'</div><a name="chat_buttom"></a>');
						//var tempDialog = Edo.get('dialog_'+id).data;
						//Edo.get('dialog_'+id).data.insert(tempDialog.source.length, {dialog:'<span style="font-size:10px;"><span style="color:#3A5FCD;font-size:11px;">自己</span>  '+nowDate.getHours()+':'+min+':'+nowDate.getSeconds()+'</span>' });
						//Edo.get('dialog_'+id).data.insert(tempDialog.source.length, {dialog: Edo.get('dialog_'+id+'_input').get('text') });
						//Edo.get('dialog_'+id).y = Edo.get('dialog_'+id).y-20;
						//清空输入框
						
						//alert(i);
						Edo.get('dialog_'+id+'_input').set('text','');
						setTimeout(function(){document.location = "#chat_buttom";},1);
						Edo.get('dialog_'+id+'_input').focus();
						
						currentEventID = 'dialog_'+id+'_submit';
					}
					}}
				]				
			}
		]
	});
	
	//注册回车事件
	enterEventList[enterEventList.length] = Edo.get('dialog_'+id+'_submit');
	enterEventIDList[enterEventIDList.length] = 'dialog_'+id+'_submit';
	
	userBt = new createMessageBarBt(id+'_bt','cims201_chat',name,function(){
		userBt.closeNewMessage();
		dialogBoxManager(id,name,email,500,500);
	});
	//在bar上添加该按钮
	myMessageBar.addChildAt(2,userBt.getBt());
	
	//注册该组件
	userChatBoxList[id] = this;
			
	this.popup = function(x,y){
		if(newChatBox.visible == false){ 		                       
		    Edo.managers.PopupManager.createPopup({
		        target: newChatBox,                                
		        x: x,
		        y: y,
		        onout: function(e){
		        	//newMessageList.set('visible', false);
		            //Edo.managers.PopupManager.removePopup(newMessageList);
		        }
		    });
		}
	}
	
	//移除弹出框
	this.removePopup = function(){
		newChatBox.set('visible', false);
	    Edo.managers.PopupManager.removePopup(newChatBox);
	}
	
	//刷新数据
	this.refresh = function(){
		
	}
	
	this.isVisible = function(){
		return newChatBox.visible;
	}
	
	//显示新的消息
	this.showNewMessage = function(){
		userBt.showNewMessage();
	}
	
	//销毁该组件
	this.destroy = function(){
		userChatBoxList[id] = undefined; 
		newChatBox.destory();
		//从messagebar移除该按钮
		myMessageBar.children.each(function(c,index){
			if(c.id == (id+'_bt')){
				myMessageBar.children.splice(index,1);
			}
		});
		userBt.destroy();
	}
	
	//获取该组件的显示坐标
	this.getPosition = function(){
		var p = {};
		var fff = Edo.get(id+'_bt');

		return p;
	}
}

//聊天盒管理器,输入参数为user对象
function dialogBoxManager(id,name,email,x,y){
	if(id){
		var isFinished = false;
		for(var key in userChatBoxList){
			if(key == id){
				if(userChatBoxList[key]){
					isFinished = true;
					if(userChatBoxList[key].isVisible()==false){
						//newChatItem.showNewMessage();
						userChatBoxList[key].popup(messageAndFriendList_x-230-3,messageAndFriendList_y+100);
					}
					else{userChatBoxList[key].removePopup();
					}
				}
			}else{
				if(userChatBoxList[key]){
					if(userChatBoxList[key].isVisible()){
						userChatBoxList[key].removePopup();
					}
				}
			}
		}
		
		//如果没有注册该组件，则需要新创建该组件
		if(isFinished == false){
			var newChatItem = new createNewChatItem(id,name,email);			
			newChatItem.showNewMessage();
			newChatItem.popup(messageAndFriendList_x-230-3,messageAndFriendList_y+100);
		}		
	}
}

//利用dwr进行聊天时候的回调函数
function dialog_callback(){
	
	var newMessage = dwr.util.getValue('cimsMessage');
	if(newMessage){
		var result = Edo.util.Json.decode(newMessage);
		var sender = result[2];
		dialogBoxManager(sender.id,sender.name,sender.email);
		//var tempDialog = Edo.get('dialog_'+sender.id).data;
		//Edo.get('dialog_'+sender.id).data.insert(tempDialog.source.length, {dialog: result[0] });
		//Edo.get('dialog_'+sender.id).data.insert(tempDialog.source.length, {dialog: result[1]+'<br/>jkl' });
		
	Edo.get('dialog_'+sender.id).setValue(Edo.get('dialog_'+sender.id).getValue().replace('<a name="chat_buttom"></a>','')+'<span style="padding-left:5px">'+result[0]+'<div style="padding-left:5px;padding-top:3px;padding-bottom:4px">'+result[1]+'</div></span><a name="chat_buttom"></a>');
    setTimeout(function(){document.location = "#chat_buttom";},1);	
    Edo.get('dialog_'+sender.id+'_input').focus();	
			
		//将发送消息写到面板上
		//Edo.get('dialog_'+sender.id).addChild({
		//	type: 'label',
		//	width: '100%',
		//	text: result[0]
		//});
		//var currentText = Edo.get('dialog_'+sender.id).get('text');
		//currentText += '<br>';
		//currentText += result[0];
		//Edo.get('dialog_'+sender.id).set('text',currentText);	
	}
}
