/**
 * 显示评论的界面
 */
var bestAnswerId = null;
var showBestAnswerBt;
// 类别
function gongjianleibieontologyshuyu_box() {

	// 类别术语导航
	var tree = new Edo.lists.Tree();
	tree
			.set({
				type : 'tree',
				cls : 'e-tree-allow',
				border : [ 0, 0, 0, 0 ],
				width : "100%",
				height : "100%",
				enableCellEdit : false,
				headerVisible : false,
				autoColumns : true,
				horizontalLine : false,
				columns : [ {
					header : 'bentileibie',
					dataIndex : 'name',
					editor : {
						type : 'text'
					}
				} ],
				data : cims201.utils
						.getData('ontoeditcenter!owlIndividualWrite.action?owlFileName=default'),
				oncellclick : function(e) {

					var onto = this.getSelected();

					if (onto.name != '类别') {
						var rightbox = creatontojieshilist_box(onto.name);
						ontomainBox.removeChildAt(1);
						ontomainBox.addChild(rightbox);
						// cims201.utils.getData('ontoeditcenter!writeIndividualList.action',{owlFileName:'default',indivName:onto.name});
					}

				}
			});

	var leftpanel = {
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		width : '20%',
		height : '100%',
		layout : 'vertical',
		children : [ tree ]
	};

	var rightpanel = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		width : '80%',
		height : '100%',
		layout : 'vertical',
		children : []
	});
	var ontomainBox = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 15, 0 ],
		width : '100%',
		height : '100%',
		layout : 'horizontal',
		children : [ leftpanel ]
	});
	this.getontobox = function() {
		return ontomainBox;
	};
}
// 术语
function gongjianshuyuontologyshuyu_box(ontoname) {
	var ontomainBox = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		width : '100%',
		// height: '100%',
		layout : 'vertical',
		children : []
	});
	this.getontobox = function() {
		return ontomainBox;
	};
}
// 术语解释的列表
function creatontojieshilist_box(ontoclassname) {
	var myConfig1 = {
		verticalLine : true,
		horizontalLine : true,
		headerVisible : true,
		horizontalScrollPolicy : 'off',

		autoColumns : false
	};
	var myColumns1 = [
			{
				headerText : '术语名称',
				dataIndex : 'ontoname',
				headerAlign : 'center',
				align : 'center',
				width : 120

			},
			{
				headerText : '所属类别',
				dataIndex : 'classname',
				headerAlign : 'center',
				align : 'center',
				width : 120

			},
			{
				headerText : '状态',
				dataIndex : 'hasExplain',
				headerAlign : 'center',
				align : 'left',
				width : 120,
				renderer : function(v, r) {
					var result;
					if (v == true) {
						result = '<img src="css/2.gif"></img>'
								+ r.ontocommentcount + '人参与,已完成';
					} else {

						if (null == r.creatername) {
							result = '<img src="css/add.png"></img>还没有人创建';
						} else {
							result = '<img src="css/newmessage.gif"></img>共有'
									+ r.ontocommentcount + '人参与';
						}
					}
					return result;
				}

			},
			{
				headerText : '创建人',
				dataIndex : 'creatername',
				headerAlign : 'center',
				align : 'center',
				width : 200,
				renderer : function(v, r) {
					var result;
					if (r.creatername == null)
						result = '/';
					else
						result = v + '(' + r.createremail + ')';
					return result;
				}

			},
			{
				headerText : '创建时间',
				dataIndex : 'creattime',
				headerAlign : 'center',
				align : 'center',
				width : 130

			},
			{
				headerText : '操作',
				dataIndex : 'ontoname',
				headerAlign : 'center',
				align : 'center',
				width : 100,
				renderer : function(v, r) {
					var link;
					if (r.creatername == null)
						link = '<a href="javascript:openNewTab(\'gongjianontologyshuyu'
								+ v
								+ '\', \'gongjianontologyshuyu\',\''
								+ v
								+ '解释\', {ontoname:\''
								+ v
								+ '\'});">创建术语解释</a>';

					else {
						if (r.hasExplain == true)
							link = '<a href="javascript:openNewTab(\'gongjianontologyshuyu'
									+ v
									+ '\', \'gongjianontologyshuyu\',\''
									+ v
									+ '解释\', {ontoname:\''
									+ v
									+ '\'});">查看术语解释</a>';
						else
							link = '<a href="javascript:openNewTab(\'gongjianontologyshuyu'
									+ v
									+ '\', \'gongjianontologyshuyu\',\''
									+ v
									+ '解释\', {ontoname:\''
									+ v
									+ '\'});">共建术语解释</a>';

					}
					return link;
				}

			} ];

	var myTable1_rt;
	if (null != ontoclassname)
		myTable1_rt = new createTable(myConfig1, '100%', '100%', '术语共建解释列表',
				myColumns1, [], [],
				'ontoeditcenter!writeIndividualList.action', {
					owlFileName : 'default',
					indivName : ontoclassname
				}, true);
	else
		myTable1_rt = new createTable(myConfig1, '100%', '100%', '术语共建解释列表',
				myColumns1, [], [],
				'ontoeditcenter!writeIndividualList.action', {
					owlFileName : 'default'
				}, true);

	var outBox = Edo.create({
		type : 'box',
		width : '100%',
		height : '100%',
		layout : 'vertical',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		children : [

		myTable1_rt.getTable() ]
	});

	return outBox;

}

function gongjianontology_box(ontoname) {
	var ontobuild = cims201.utils.getData(
			'comment/ontocomment!searchandcreatontobuild.action', {
				content : ontoname
			});
	var ontobuildid = ontobuild.id;
	if (null != ontobuildid && ontobuildid != "error") {
		var tobar = new creattitlebar(ontobuild).getTopBar();
		var commentPanel = new createontoComment(ontoname, ontobuildid)
				.getComment();
		var ontomainBox = Edo.create({
			type : 'box',
			border : [ 0, 0, 0, 0 ],
			padding : [ 0, 0, 0, 0 ],
			width : '100%',
			height : '100%',
			layout : 'vertical',
			children : [ tobar, commentPanel ]
		});
		this.getontobox = function() {
			return ontomainBox;
		};
	}
}
//
function creattitlebar(ontobuild) {
	var myWin2 = null;
	var title = ontobuild.ontoname + '的共建解释';
	var title_length;
	var title_size;
	title_length = title.length;
	if (title_length > 26) {
		title = title.substring(0, 26);
		title_length = 26;
		title_size = 14;
	} else if (title_length < 11) {
		title_length = 11;
		title_size = 21;
	} else {
		title_size = (-0.47 * title_length + 26.13)
				- ((-0.47 * title_length + 26.13) % 1);
	}

	// 左边的标题栏knowledge_sim
	var leftTitle = '';
	leftTitle += '<span class="cims201_knowledge_topbar_title">';
	leftTitle += '<span style="font-size:' + title_size + 'px;">';
	leftTitle += title;
	leftTitle += '</span>';
	leftTitle += '</span>';

	var topbar_style = Edo.create({
		padding : [ 10, 0, 0, 12 ],
		type : 'label',
		width : '100%',
		// text: leftTitle+rightBt
		text : leftTitle
	});

	var createrinforbar = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 12 ],
		width : '100%',
		layout : 'horizontal',
		children : [ {
			type : 'label',
			text : '<img src="css/person-child.gif">由<font color=blue>'+ontobuild.creatername+'('+ontobuild.createremail+')</font>创建  &nbsp &nbsp <img src="css/time.png">'+ontobuild.creattime+'&nbsp;&nbsp;<img src="css/viewp.png">共有<font color=blue>'+ontobuild.ontocommentcount+'人</font>参与讨论共建 &nbsp;&nbsp; <img src="css/viewt.jpg"><font color=blue>'+ontobuild.ontocommentratecount+'人</font>参与了评分'
		} ]
	});
	if(Edo.get('finalexplain'+ontobuild.ontoname))
	{
		Edo.get('finalexplain'+ontobuild.ontoname).destroy();
	}
	var gongjianerinforbar = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 12 ],
		width : '90%',
		layout : 'horizontal',
		children : [ {type:'label',width:60,text:'<font color=green><b>最终解释：</b></font>'},{
			id:'finalexplain'+ontobuild.id,
			type : 'boxtext',
			width: 200,
			text : ''
				} ]
	});
	var finalexplain="<font size=2>暂缺，共建中...</font>";
	var finalbutton= Edo.create({
		type : 'button',
		icon : 'cims201_comment_edit',
		text : '添加最终解释',
		visible : false,
		onclick : function(e) {
			ontobuildID = ontobuild.id;
			if (ontobuildID) {
				addfinalExplain(ontobuildID,ontobuild.finalExplain);
			}

		}
});
	if(true==ontobuild.hasExplain)
	{
		finalexplain='<font size=2>'+ontobuild.finalExplain+'</font><br><img src="css/images/userlist_small.gif"><font color=red size=2>由'+ontobuild.finalExplainCreatername+'('+ontobuild.finalExplainCreateremail+')最终修改&nbsp &nbsp <img src="css/time.png">'+ontobuild.explaintime+'</font>';
		Edo.get('finalexplain'+ontobuild.id).set('width',cims201.utils.getScreenSize().width-600);
		finalbutton.set('text','修改最终解释');
	}
	Edo.get('finalexplain'+ontobuild.id).set('text',finalexplain);

	var showfinalbutto=cims201.utils.getData(
			'privilege/show-button!whetherShowshyufinalExplainButton.action', {
				knowledgeId : ontobuild.id
			});
	if (showfinalbutto.show == true) {

		finalbutton.set('visible', true);
	}
	function addfinalExplain(ontobuildID,content) {
		
		if (myWin2 == null) {
			var temptext='';
			if(null!=content&&content!='null')
				temptext=content;
		    if(Edo.get('textaraa'+ontobuildID))
		    {
		    	Edo.get('textaraa'+ontobuildID).destroy();
		    }
			htmleditor3 = Edo.create({
				id:'textaraa'+ontobuildID,
				type : 'textarea',
				width : '100%',
				height : 180,
				text:temptext
			});
		//	 htmleditor3.set('text',temptext);
			var editorBox2 = Edo.create({
				type : 'box',
				border : [ 0, 0, 0, 0 ],
				padding : [ 0, 0, 0, 0 ],
				width : 500,
				height : 220,
				layout : 'vertical',
				children : [
						htmleditor3,
						{
							type : 'button',
							text : '确定提交',
							onclick : function(e) {
								var vr = Edo.get('textaraa'+ontobuildID).get('text').replace(
										/(^\s*)|(\s*$)/g, "");
								if (vr != "") {
									var qqq = {};
									if (ontobuildID) {
										qqq['ontobuildID'] = ontobuildID;
									}
									
									qqq['content'] = htmleditor3.get('text');
									
									cims201.utils.getData_Async(
											'comment/ontocomment!savefinalexplain.action',
											qqq, function(text) {
												if(text == null || text == '') return;
												var ontobuild2 = Edo.util.Json.decode(text);
												finalexplain='<font size=2>'+ontobuild2.finalExplain+' </font><br><img src="css/images/userlist_small.gif"><font color=red size=2>由'+ontobuild2.finalExplainCreatername+'('+ontobuild2.finalExplainCreateremail+')最终修改&nbsp &nbsp <img src="css/time.png">'+ontobuild2.explaintime+'</font>';
												Edo.get('finalexplain'+ontobuild2.id).set('text',finalexplain);
												Edo.get('finalexplain'+ontobuild2.id).set('width',cims201.utils.getScreenSize().width-600);
												finalbutton.set('text','修改最终解释');
											});

									myWin2.hide();
								} else {
									Edo.MessageBox.alert("提示", "请不要提交空数据!",
											null);

								}
							}
						} ]
			});
			myWin2 = cims201.utils.getWin(500, 230, '编辑最终解释', editorBox2);
		}

		// 增加遮罩
		//htmleditor3.set('text', '');
		// myWin.show(200,100,true);
		setWinScreenPosition(500, 230, myWin2, ontobuildID);
	}
	gongjianerinforbar.addChild(finalbutton);
	var topBar = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		width : '100%',
		bodyCls : 'cims201_knowledge_topbar_back',
		layout : 'vertical',
	//	height : 390,
		children : [ Edo.create({
			type : 'box',
			border : [ 0, 0, 0, 0 ],
			padding : [ 2, 0, 0, 12 ],
			width : '100%',
			// bodyCls: 'cims201_knowledge_topbar_back',
			// height: '80%',
			children : [ topbar_style,createrinforbar,{type:'box',border : [ 0, 0, 0, 0 ],height:15},gongjianerinforbar ,{type:'box',border : [ 0, 0, 0, 0 ],height:20}]
		}) ]
	});
	
	
	this.getTopBar = function() {

		return topBar;
	};

}
// 创建评论界面
function createontoComment(ontoname, ontobuildID) {

	var myComment = new createontoCommentPanel(ontoname, ontobuildID);
	myComment.search({
		ontobuildID : ontobuildID
	}, 'comment/ontocomment!listComment.action');
	this.getComment = function() {

		return myComment.getComment();
	};
}

function createontoCommentPanel(ontoname, ontobuildID) {
	var commentID;
	var myWin = null;
	var htmleditor1 = null;
	// bestAnswerId=best_answer_id;
	// alert('sss'+bestAnswerId);
	// 定义摘要的字数和行数
	var abstractTextLength = 60;
	var abstractTextLine = 2;
	// 默认多少行
	var defaultPageSize = 10;

	var myUrl = {};
	var myInputForm = {};

	// 列表数据
	var rData;

	var contentBox = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		width : '100%',
		// height: '100%',
		layout : 'vertical',
		children : []
	});

	// 首先判断paging bar是否存在
	myPagerId = contentBox.id + 'paging';
	myPager = Edo.create({
		id : myPagerId,
		type : 'pagingbar',
		width : '100%',
		visible : false,
		// 设置默认值
		size : 5,
		// autoPaging: true,
		border : [ 0, 1, 1, 1 ],
		padding : 2
	});

	Edo.get(myPagerId).on('paging', function(e) {
		search();
	});

	// 当查询没有数据的时候显示该条
	var bBar = Edo
			.create({
				type : 'label',
				width : '100%',
				visible : false,
				style : 'padding-left: 370px; color: red; font-size:16px; font-weight: bold; ',
				text : '没有查询到任何数据!'
			});
	var q = 0;
	if (Edo.get('listtype' + ontobuildID))
		Edo.get('listtype' + ontobuildID).destroy();
	var oc = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 12, 0, 0, 12 ],
		width : '100%',
		height : 40,
		layout : 'horizontal',
		children : [ {
			type : 'label',
			text : ''
		}, {
			type : 'label',
			text : '排序类型'
		}, {
			type : 'combo',
			id : 'listtype' + ontobuildID,
			valueField : 'id',
			displayField : 'label',
			selectedIndex : 0,
			data : [ {
				label : '发表时间',
				id : 'shuyutime'
			}, {
				label : '评分',
				id : 'shuyuscore'
			}, {
				label : '讨论数',
				id : 'shuyucount'
			}

			],

			onselectionchange : function(e) {

				if (q != 0) {
					search(this.getValue());

				}
				q++;
			}

		}, {
			type : 'button',
			icon : 'cims201_comment_edit',
			text : '我来解释',
			onclick : function(e) {
				ontobuildID = myInputForm.ontobuildID;
				commentID = null;
				if (ontobuildID) {
					addComment();
				}

			}
		} ]

	});

	var htmleditor2 = Edo.create({
		type : 'textarea',
		width : '100%',
		height : 160
	});
	var quickeditorBox = Edo.create({
		type : 'panel',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 0, 0 ],
		width : '99%',
		height : 240,
		title : '我来解释',
		horizontalAlign : 'center',
		layout : 'vertical',
		children : [
				htmleditor2,
				{
					type : 'button',
					text : '快速提交',
					onclick : function(e) {
						var vr = htmleditor2.get('text').replace(
								/(^\s*)|(\s*$)/g, "");
						if (vr != "") {
							var qqq = {};
							if (ontobuildID) {
								qqq['ontobuildID'] = ontobuildID;
							}
							if (commentID) {
								qqq['commentid'] = commentID;
							}
							qqq['content'] = htmleditor2.get('text');

							cims201.utils.getData_Async(
									'comment/ontocomment!save.action', qqq,
									function() {
										search();
										htmleditor2.set('text', '');
										Edo.MessageBox.alert("提示", "操作成功!",
												null);
									});

						} else {
							Edo.MessageBox.alert("提示", "请不要提交空数据!", null);
						}
					}
				} ]
	});
	var dfdf = Edo.create({
		type : 'box'
	});
	var mainBox = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 20, 0 ],
		width : '100%',
		// height: '100%',
		layout : 'vertical',
		children : [ oc, myPager, contentBox, bBar, quickeditorBox ]
	// children: [contentBox]
	});

	this.getComment = function() {
		return mainBox;
	};
	// 设置检索的属性
	this.search = function(queryForm, url) {
		if (url != null) {
			myUrl = url;
		}

		for ( var key in queryForm) {
			myInputForm[key] = queryForm[key];
		}
		search(null);
	};

	// 搜索数据
	function search(searchtype) {
		if (Edo.get('listtype' + ontobuildID)) {
			searchtype = Edo.get('listtype' + ontobuildID).getValue();

		}
		if (myUrl != null && myUrl != '') {
			myInputForm.index = myPager.index;
			myInputForm.size = myPager.size;
			if (null != searchtype)
				myInputForm.content = searchtype;
			cims201.utils.getData_Async(myUrl, myInputForm, function(text) {

				rData = Edo.util.Json.decode(text);
				if (oc.children.length > 1) {
					oc.removeChildAt(0);
				}
				// 设置顶部条
				var topLabel = Edo.create({
					type : 'label',
					style : 'padding-right:10px;',
					text : ' 共有<span class="cims201_comment_focus">'
							+ (rData.kccounts ? rData.kccounts : 0)
							+ '</span>条记录'
				});
				oc.addChildAt(0, topLabel);

				showComments(rData.data);
				// 如果数据为空
				if (rData.data == null || rData.data.length == 0) {
					myPager.set('visible', false);
					bBar.set('visible', true);
					myPager.total = 0;
					myPager.totalPage = 0;
					myPager.refresh();
				} else {
					myPager.set('visible', true);
					bBar.set('visible', false);
					myPager.total = rData.total;
					myPager.totalPage = rData.totalPage;
					myPager.refresh();
				}
			});
		}
	}
	// 生成整个评论内容
	function showComments(data) {
		if (data) {
			contentBox.removeAllChildren();
			// Edo.get(bestAnswerId).removeAllChildren();
			data.each(function(o, iii) {
				// contentBox.removeChildAt(iii);
				// alert(o.isBest+"__"+iii);
				var oneComment = createRReplyBox(o, iii);
				oneComment.set('bodyCls', 'cims201_comment_bg');
				oneComment.set('border', [ 1, 1, 1, 1 ]);
				contentBox.addChild(oneComment);

			});
		}
	}
	// 生成单条评论
	function createSingleComment(c, isSubComment) {

		var ct = Edo.create({
			type : 'box',
			border : [ 0, 0, 0, 0 ],
			padding : [ 0, 0, 0, 12 ],
			width : '100%',
			// height: 45,
			layout : 'horizontal',
			children : [

			{
				type : 'label',
				width : '50%',
				text : '<span class="cims201_comment_date">' + c.commmentTime
						+ ' 发表</span>'
			}
			// {type: 'label', width: '10', style:'float:right; font-size:
			// 12px;', text: ''+c.index+'#'}
			]
		});

		var tttt = c.content;
		var out_tttt = cims201.utils.addBrToText(tttt, abstractTextLength);
		var cc = Edo
				.create({
					type : 'box',
					border : [ 0, 0, 0, 0 ],
					padding : [ 0, 0, 0, 12 ],
					width : '100%',
					bodyCls : 'cims201_comment_list_f1',
					children : [ {
						type : 'boxtext',
						width : cims201.utils.getScreenSize().width - 600,
						text : ((c.commented != null && isSubComment == 1) ? ('<font color="red">回复'
								+ c.commented.commenterName + ':</font>')
								: '')
								+ c.content
					} ]
				});

		// var bestAbswerBt=Edo.create ({type: 'button',icon:
		// 'cims201_comment_edit',visible: false,commentId:c.id, text:'采纳',
		// onclick: function(e){
		// //knowledgeID = myInputForm.knowledgeID;
		// //commentID = this.commentId;
		// bestAnswer(c);
		// bestAbswerBt.set('visible',false);
		// }});

		var star = createOntoCommentDetail(c, c.id);
		var label = Edo.create({
			type : 'label',
			width : '75%',
			text : '<span class="cims201_comment_date">共有' + c.supportVoteCount
					+ '人次参与讨论，' + c.againstVoteCount + '人次参与评分</span>'
		});

		if (isSubComment == 1) {
			star.set('visible', false);
			label.set('width', "95%");
			label.set('text', "");
		}
		//
		var cdbutton = Edo.create({
			type : 'button',
			visible : false,
			icon : 'cims201_comment_delete',
			commentId : c.id,
			text : '删除',
			onclick : function(e) {
				commentID = this.commentId;
				Edo.MessageBox.confirm("确认删除", "你确定这条解释吗？", function(action) {

					cims201.utils.getData_Async(
							'comment/ontocomment!delete.action', {
								ontoCommentID : commentID
							}, function() {
								search();
							});
				});
			}
		});
		var showdelete = cims201.utils
				.getData(
						'privilege/show-button!whetherShowontocommentdeleteButton.action',
						{
							knowledgeId : c.id
						});

		if (showdelete.show == true) {

			cdbutton.set('visible', true);
		}
		var cbottom = Edo.create({
			type : 'box',
			border : [ 0, 0, 0, 0 ],
			padding : [ 0, 0, 0, 12 ],
			width : '100%',
			layout : 'vertical',
			children : [ Edo.create({
				type : 'box',
				padding : [ 20, 0, 0, 0 ],
				border : [ 0, 0, 0, 0 ],
				width : '100%',
				layout : 'horizontal',
				children : [ label, star, cdbutton, {
					type : 'button',
					icon : 'cims201_comment_edit',
					commentId : c.id,
					text : '回复',
					onclick : function(e) {
						ontobuildID = myInputForm.ontobuildID;
						commentID = this.commentId;
						addComment(c);
					}
				} ]
			})

			]
		});

		var cb = Edo.create({
			type : 'box',
			border : [ 0, 0, 0, 0 ],
			padding : [ 0, 0, 0, 0 ],
			width : '100%',
			layout : 'vertical',
			children : [ ct, cc, cbottom ]
		});

		// 左边的人员信息显示栏
		var lb = Edo
				.create({
					type : 'box',
					border : [ 0, 1, 0, 0 ],
					padding : [ 0, 0, 0, 0 ],
					width : (c.commented != null && isSubComment == 1) ? 50
							: 100,
					height : '100%',
					bodyStyle : 'background-color: rgb(223,234,255);	',
					layout : 'vertical',
					children : (c.commented != null && isSubComment == 1) ? [
							{
								type : 'label',
								minHeight : '5',
								text : ''
							},
							{
								type : 'label',
								style : 'padding-top;15px; padding-left:2px; padding-right:8px;',
								text : '<img src="' + c.commenterpicpath
										+ '" height=50></img>'
							},
							{
								type : 'label',
								width : '100%',
								style : 'padding-left: 5px;color: #1E50A2; display: block;',
								text : c.commenterName
							} ]
							: [
									{
										type : 'label',
										minHeight : '10',
										text : ''
									},
									{
										type : 'label',
										style : 'padding-top;10px; padding-left:1px; padding-right:10px;',
										text : '<img  src="'
												+ c.commenterpicpath
												+ '"  height=110></img>'
									},
									{
										type : 'label',
										width : '100%',
										style : 'padding-left: 38px;color: #1E50A2; display: block;',
										text : c.commenterName
									} ]
				});

		var mb = Edo
				.create({
					type : 'box',
					border : (c.commented != null && isSubComment == 1) ? [ 1,
							0, 0, 0 ] : [ 0, 0, 0, 0 ],
					// padding: [10,10,0,10],
					padding : [ 0, 0, 0, 0 ],
					width : '100%',
					layout : 'horizontal',
					children : [ lb, cb ]
				});

		return mb;
	}
	function expandComment(c, subcomment, isSubComment) {

		var outReply = null;
		if (c != null && c != '') {
			if (isSubComment == 1) {
				subcomment.addChild(createSingleComment(c, 1));
			}

			var subdata = c.commentdtos;
			if (subdata != null && subdata != '') {

				subdata.each(function(o) {
					expandComment(o, subcomment, 1);

				});

			}

			outReply = subcomment;
		}

		return outReply;

	}

	// 生成嵌套评论
	function createRReplyBox(c) {
		var outReply = null;
		// 如果评论不为空

		if (c != null && c != '') {

			var descendent = [];
			var initialcomment = createSingleComment(c);

			var subdata = c.commentdtos;
			if (subdata != null && subdata != '') {
				var subcomment = Edo.create({
					type : 'box',
					bodyCls : 'cims201_comment_replyr_bg',
					border : [ 0, 0, 1, 0 ],
					padding : [ 0, 0, 0, 0 ],
					width : '100%',
					layout : 'vertical',
					children : []
				});
				subcomment = expandComment(c, subcomment, 0);

				initialcomment.getChildAt(1).addChild(subcomment);
			}

			var children = descendent.add(initialcomment);

			outReply = Edo.create({
				type : 'box',
				bodyCls : 'cims201_comment_replyr_bg',
				border : [ 0, 0, 1, 0 ],
				padding : [ 0, 0, 0, 0 ],
				width : '100%',
				layout : 'vertical',
				children : children
			});

		}
		// 如果是最佳答案 单独加到‘最佳答案’栏目
		// alert(c.knowledgeid);
		if (c.isBest == 1) {

		}

		return outReply;
	}

	// 评论的事件
	function addComment(targetComment) {
		if (myWin == null) {
			htmleditor1 = Edo.create({
				type : 'textarea',
				width : '100%',
				height : 180
			});
			var editorBox = Edo.create({
				type : 'box',
				border : [ 0, 0, 0, 0 ],
				padding : [ 0, 0, 0, 0 ],
				width : 500,
				height : 220,
				layout : 'vertical',
				children : [
						htmleditor1,
						{
							type : 'button',
							text : '确定提交',
							onclick : function(e) {
								var vr = htmleditor1.get('text').replace(
										/(^\s*)|(\s*$)/g, "");
								if (vr != "") {
									var qqq = {};
									if (ontobuildID) {
										qqq['ontobuildID'] = ontobuildID;
									}
									if (commentID) {
										qqq['commentid'] = commentID;
									}
									qqq['content'] = htmleditor1.get('text');
									// alert("test");
									cims201.utils.getData_Async(
											'comment/ontocomment!save.action',
											qqq, function() {
												search();
											});

									myWin.hide();
								} else {
									Edo.MessageBox.alert("提示", "请不要提交空数据!",
											null);

								}
							}
						} ]
			});
			myWin = cims201.utils.getWin(500, 230, '添加解释', editorBox);
		}

		// 增加遮罩
		htmleditor1.set('text', '');
		// myWin.show(200,100,true);
		setWinScreenPosition(500, 230, myWin, ontobuildID);
	}

}

// 创建当前知识信息面板
function createOntoCommentDetail(comment, commentID) {
	var kdd = '';
	kdd += '<span class="knowledge_view_bigscore">';
	if (comment) {
		kdd += (comment.rate < 5 ? comment.rate : 5);
	} else {
		kdd += 3;
	}
	// kdd += 3;
	kdd += '</span>';
	var OntoComment_view_score_total;
	if (null != Edo.get('totalscore_' + commentID))
		Edo.get('totalscore_' + commentID).destroy();
	OntoComment_view_score_total = Edo.create({
		id : 'totalscore_' + commentID,
		type : 'label',
		text : kdd
	});

	var OntoComment_view_score_star = null;

	if (comment) {
		OntoComment_view_score_star = new starJudge_big(onOntoCommentStarView,
				OntoCommentstarJudge, (comment.rate < 5 ? comment.rate : 5),
				commentID);
	} else {
		OntoComment_view_score_star = new starJudge_big(onOntoCommentStarView,
				OntoCommentstarJudge, 3, ontobuildID);
	}

	var OntoComment_view_score_judge = Edo.create({
		type : 'label',
		text : ''
	});

	function onOntoCommentStarView(index) {
		if (index) {
			if (index == 5) {
				OntoComment_view_score_judge.set('text', '力荐');
			} else if (index == 4) {
				OntoComment_view_score_judge.set('text', '推荐');
			} else if (index == 3) {
				OntoComment_view_score_judge.set('text', '还行');
			} else if (index == 2) {
				OntoComment_view_score_judge.set('text', '较差');
			} else if (index == 1) {
				OntoComment_view_score_judge.set('text', '很差');
			}
		}
	}

	var OntoComment_view_score_box = Edo.create({
		type : 'box',
		border : [ 0, 0, 0, 0 ],
		padding : [ 0, 0, 10, 0 ],
		width : '20%',
		layout : 'horizontal',
		children : [ OntoComment_view_score_total,
				OntoComment_view_score_star.getBigStar(),
				OntoComment_view_score_judge ]
	});
	return OntoComment_view_score_box;
}
// 当评价星星点击后执行的事件
// 知识评价的方法
function OntoCommentstarJudge(index, ontoCommentID) {

	var res = cims201.utils.getData('comment/ontocomment!rating.action', {
		ontoCommentID : ontoCommentID,
		score : index
	});
	if (res.isSupport == true || res.isSupport == 'true' || res.isSupport == 1) {
		Edo.get('totalscore_' + ontoCommentID).set('text',
				'<span class="knowledge_view_bigscore">' + index + '</span>');
		Edo.MessageBox.alert("提示", "评价成功", null);

	} else {
		Edo.MessageBox.alert("提示", "您已经评价过该知识了", null);
	}
}
