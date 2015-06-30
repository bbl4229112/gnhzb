// 显示新消息

function createNewMessageBox() {

	var myConfig1 = {
		verticalLine : false,
		horizontalLine : false,
		headerVisible : false,
		horizontalScrollPolicy : 'off',
		// rowHeight: 80,
		autoColumns : false
	};
	var myColumns1 = [{
		headerText : '内容',
		dataIndex : 'title',
		headerAlign : 'center',
		width : '500',
		align : 'left',
		renderer : function(v, r) {
			var outStr = '';
			outStr += '<div class="cims201_message_newmessage">';
			outStr += '<span class="cims201_message_newmessage_f1">' + r.sender
					+ '</span>';

			if ('Question' == r.messageType) {
				outStr += '向您提问知识';
			} else {
				outStr += '向您推荐知识';
			}
			outStr += '<span class="cims201_message_newmessage_f1">《'
					+ r.knowledgeName + '》</span>';
			outStr += '</div>';

			return outStr;
		}
	}];
	var newMessageTable = new createTable(myConfig1, '100%', '100%', '新消息',
			myColumns1, [], [], '', {});

	this.getNewMessage = function() {
		return newMessageTable;
	}
}

// 创建一条新的好友列表,输入参数是一个userDTO对象
function createNewUserOnline(o) {
	// 是否有新消息的讯号
	var newMessageSignal = false;
	// 在跳跃的时候控制跳跃的一些临时参数，例如当鼠标悬停的时候停滞跳跃
	var tempStatus = true;
	// 目前跳跃的状态
	var status = 1;

	var zd = Edo.create({
		type : 'label',
		onMouseover : function() {
			tempStatus = false;
			this.set('style','background:#e4e4e4;');
		},
		onMouseout : function() {
			tempStatus = true;
			this.set('style','background:white;');
		},
		style : 'padding-left:10px;padding-top:5px; float:left;',
		width : '100%',
		text : '<div style="float:left;"><img src="thumbnail/'+o.picturePath+'" height=30 width=30></img></div><div class="cims201_message_newmessage_f1"><a href=javascript:dialogBoxManager('
				+ o.id
				+ ',"'
				+ o.name
				+ '","'
				+ o.email
				+ '",500,200);>'
				+ o.name + ' ' + o.email + '</a></div>'
	});

	this.getUser = function() {
		return zd;
	}

	// 显示新的讯息
	this.showNewMessage = function() {
		newMessageSignal = true;
		setInterval(function() {
					if (newMessageSignal) {
						if (tempStatus) {
							if (status == 1) {
								zd.set('style',
										'margin-top:5px; margin-left:5px;');
								status = 0;
							} else {
								zd.set('style',
										'margin-top:2px; margin-left:2px;');
								status = 1;
							}
						}
					} else {
						return;
					}
				}, 300);
	}
	// 关闭新消息提示
	this.closeNewMessage = function() {
		newMessageSignal = false;
	}
	// 得到用户邮箱
	this.getUserEmail = function() {
		return o.email;
	}
}

// 创建底层栏显示的按钮，按钮可以有两种不同的状态显示
function createMessageBarBt(id, icon, text, onclickevent) {
	// 新消息讯号
	var newMessageSignal = false;
	// 目前闪烁状态
	var status = 1;
	var newMessageBt = Edo.create({
				type : 'button',
				id : id,
				minWidth : 100,
				pressed : false,
				icon : icon,
				text : '<span>' + text + '</span>',
				onclick : function(e) {
					newMessageSignal = false;
					onclickevent(e);
				}
			});

	this.getBt = function() {
		return newMessageBt;
	}

	// 高亮显示新的消息
	this.showNewMessage = function() {
		newMessageSignal = true;
		setInterval(function() {
					if (newMessageSignal) {
						if (status == 1) {
							newMessageBt.set('pressed', true);
							status = 0;
						} else {
							newMessageBt.set('pressed', false);
							status = 1;
						}
					} else {
						newMessageBt.set('pressed', false);
						return;
					}
				}, 400);
	}

	// 关闭消息显示
	this.closeNewMessage = function() {

		newMessageSignal = false;
		newMessageBt.set('pressed', false);
	}

	// 改变bt的显示文本
	this.setText = function(text) {
		newMessageBt.set('text', text);
	}

	// 得到id
	this.getId = function() {
		return id;
	}
}

// 弹出新的按钮来显示人员对话
function createNewDialog() {
	myMessageBar
}
