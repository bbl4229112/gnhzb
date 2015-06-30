// 知识检索的界面
function createStandardSearchIndex() {
	var myHotword = Edo.create({
				type : 'box',
				width : '100%',
				border : [0, 0, 0, 0],
				padding : [10, 0, 0, 200],
				bodyStyle : 'font-size:13px;',
				layout : 'horizontal',
				children : [{
							type : 'label',
							align : 'center',
							text : '热词:'
						}]
			});
	var mySearchInput = Edo.create({
				type : 'autocomplete',
				width : '90%',
				url : 'knowledge/knowledge!searchHotword.action',
				popupHeight : '100',
				valueField : 'name',
				displayField : 'name',
				onclick : function(e) {
					currentEventID = 'knowledgesearchindex';
				},
				onblur : function(e) {
					currentEventID = null;
				}
			});

	var mySearchIndex = Edo.create({
		type : 'box',
		border : [0, 0, 0, 0],
		padding : [(cims201.utils.getScreenSize().height / 10), 0, 0, 0],
		width : '100%',
		layout : 'vertical',
		children : [{
			type : 'label',
			width : '100%',
			style : 'padding-left:'
					+ (cims201.utils.getScreenSize().width / 2 - 365) + 'px;',
			text : '<img src="css/images/standardsearchengine.jpg" height=100></img>'
		},Edo.create({
					type : 'box',
					width : '100%',
					border : [0, 0, 0, 0],
					padding : [0, 200, 20, 200],
					layout : 'horizontal',
					children : [mySearchInput, {
								type : 'button',
								icon : 'knowldge_search_action',
								text : '搜索',
								onclick : function(e) {
									search();
								}
							}]
				}), myHotword]
	});

	function search() {
		// 设置界面loading
		var myKey = mySearchInput.get('text');
		if (myKey != null && myKey != ''){
	
		setTimeout(deleteMask, 1500);
		openNewTab(myKey, 'standardknowledgelist_fulltext', myKey, {
					key : myKey
				});		
	
		}
	}
	/*
	function search() {
		// 设置界面loading
		var myKey = mySearchInput.get('text');
		if (myKey != null && myKey != ''){
		
		var standardip = mystandardipchoose.getValue();
		if(standardip != null && standardip != ''){
		createLoading();
	
		
		setTimeout(deleteMask, 1500);
		openNewTab(myKey, 'standardknowledgelist_fulltext', myKey, {
					key : myKey,standardip:standardip
				});
		}
		else
			{
			alert('请输入要搜索的词！');
			}
		}
	}
	*/

	cims201.utils.getData_Async('knowledge/knowledge!listHotword.action', {},
			function(text) {
				if (text != null && text != '') {
					var data = Edo.util.Json.decode(text);
					data.each(function(o) {
						myHotword.addChild({
									type : 'label',
									text : '<a href=javascript:standardhotwordsearch("'
											+ o.name + '");>' + o.name
											+ '</a>'
								});
					});
				}
			});

	this.getSearchIndex = function() {
		return mySearchIndex;
	}

	// 回车事件
	this.enter = function() {
		search();
	}
}
	function standardhotwordsearch(myKey) {
		// 设置界面loading
	
		if (myKey != null && myKey != ''){	
		setTimeout(deleteMask, 1500);
		openNewTab(concatName(myKey), 'standardknowledgelist_fulltext', concatName(myKey), {
					key : myKey
				});	
		}
	}