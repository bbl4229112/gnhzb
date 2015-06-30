// 知识检索的界面
function createAvidmSearchIndex() {
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
	var hostdata=cims201.utils.getData('knowledge/knowledge!shwoAVIDMHOST.action', {});		
	var myavidmipchoose = Edo.create({
				id : 'avidmip',
				type : 'combo',
				width : '100%',
				valid : noEmpty,

				readOnly : true,
				displayField : 'showname',
				valueField : 'name',
				data : hostdata
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
			text : '<img src="css/images/avidmsearchengine.jpg" height=100></img>'
		}, Edo.create({
					type : 'box',
					width : '100%',
					border : [0, 0, 0, 0],
					padding : [0, 260, 20, 200],
					bodyStyle : 'font-size:13px;',
					layout : 'horizontal',
					children : [myavidmipchoose, {
								type : 'label',
								align : 'center',
								text : '选择服务<font color=red>*</font>'
							}]
				}), Edo.create({
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
		
		var avidmip = myavidmipchoose.getValue();
		if(avidmip != null && avidmip != ''){
		createLoading();
	
		
		setTimeout(deleteMask, 1500);
		openNewTab(myKey, 'avidmknowledgelist_fulltext', myKey, {
					key : myKey,avidmip:avidmip
				});
		}
		else
			{
			alert('请选择需要搜索的服务器！');
			}
		}
	}

	cims201.utils.getData_Async('knowledge/knowledge!listHotword.action', {},
			function(text) {
				if (text != null && text != '') {
					var data = Edo.util.Json.decode(text);
					data.each(function(o) {
						myHotword.addChild({
									type : 'label',
									text : '<a href=javascript:avidmhotwordsearch("'
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
	function avidmhotwordsearch(myKey) {
		// 设置界面loading
	
		if (myKey != null && myKey != ''){
		
		var avidmip = Edo.get('avidmip').getValue();
		if(avidmip != null && avidmip != ''){
		createLoading();
	
		
		setTimeout(deleteMask, 1500);
		openNewTab(concatName(myKey), 'avidmknowledgelist_fulltext', concatName(myKey), {
					key : myKey,avidmip:avidmip
				});
		}
		else
			{
			alert('请选择需要搜索的服务器！');
			}
		}
	}