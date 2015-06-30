//导航栏高度
var navRowHeight = 98;
var updata = [
		{id: '4', index: 'knowledgeupload', type:'knowledgeupload',name: '我要上传'},
        {id: '4', index: 'batchknowledgeupload', type:'knowledgeupload',name: '批量上传'},
		{id: '6', index: 'approvalstatistics', type:'getUnApprovalKnowledges', name: '未入库'},
		{id: '7', index: 'approvalstatistics', type:'getPendingApprovalKnowledges', name: '审批中'},
		{id: '8', index: 'approvalstatistics', type:'getPassApprovalKnowledges', name: '审批通过'},
		{id: '9', index: 'approvalstatistics', type:'getEndApprovalKnowledges', name: '审批结束'},
		{id: '122', index: 'publisharticle', type:'expert',name: '发布博文'},//江丁丁添加 2013-6-20
		{id: '116', index: 'approvalstatistics', type:'getPendingBorrowApprovalKnowledges', name: '借阅中'},
		{id: '117', index: 'approvalstatistics', type:'getPassBorrowApprovalKnowledges', name: '借阅通过'},
		{id: '118', index: 'approvalstatistics', type:'getEndBorrowApprovalKnowledges', name: '借阅历史'}
];

var taskdata = [
                {id: '10', index: 'approvalstatistics', type:'getNeededApprovalKnowledges', name: '待办审批', showname: '待办审批'},
                {id: '11', index: 'approvalstatistics', type:'passApprovalKnowledges', name: '已审批', showname: '已审批'},
		        {id: '13', index: 'approvalstatistics', type:'getNeedBorrowApprovalKnowledges', name:'&nbsp;&nbsp;&nbsp;待办<br>借阅审批', showname: '待办借阅...'},
	    		{id: '17', index: 'approvalstatistics', type:'getNeedBorrowApprovalKnowledgesAdmin', name:'&nbsp;&nbsp;&nbsp;待办<br>借阅管理', showname: '待办借阅...'},
	 			{id: '14', index: 'approvalstatistics', type:'passBorrowApproval', name:'&nbsp;&nbsp;已审批<br>借阅申请',showname: '已审批借...'}
            ];
            

//屏蔽借阅功能
var borrowdata=cims201.utils.getData('custom/customization!kBorrow.action');
if(borrowdata == false || borrowdata == 'false'){
	updata.splice(8,3);
	taskdata.splice(2,3);
}

//去掉上传通道2和单点上传
var data=cims201.utils.getData('privilege/show-button!whetherShowSystemManagerBtn.action');
if(data.show == false || data.show == 'false'){
	updata.splice(2,2);
}
//我的知识仓库
var warehousedata = [
                     {id: '16', index: 'myBusiness', type:'myBusiness', name: '我的知识'},
                     {id: '17', nodeId: '1',index: 'keep', type:'getKnowledgeKeep', name: '知识收藏'},
			 		 {id: '19', index: 'knowledgesSubscription', type:'getKnowledgesSubscription', name: '知识订阅'},
			 		 {id: '191', index: 'getKnowledgesSubscriptionList', type:'getKnowledgesSubscriptionList', name: '订阅列表'}
                     ];
var rssdata=cims201.utils.getData('custom/customization!kRSS.action');
if(rssdata == false || rssdata == "false"){
	warehousedata.splice(2,2);
}
var keepsdata=cims201.utils.getData('custom/customization!kKeep.action');
if(keepsdata == false || keepsdata == "false"){
	warehousedata.splice(1,1);
}
//知识搜索—多维搜索
var knowledgesearchdata = [
                           {id: 22, index: 'knowledgesearchindex', name: '全文检索'},
	                        {id: 21, index: 'knowledgesearch', name: '多维检索'},
	                        {id: 100, index: 'knowledgesearchonto', name: '本体检索'}
	                    ];
var kmultisearchdata=cims201.utils.getData('custom/customization!kMultiSearch.action');
if(kmultisearchdata == false || kmultisearchdata == "false"){
	knowledgesearchdata.splice(1,1);
}
//专家管理屏蔽
var systemmanagementdata = [
	                        {id: 69, index: 'configktype', name: '配&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp置<br>知识模板', showname: '知识模板'},
	                        {id: 70, index: 'configproperty', name: '配&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp置<br>知识属性', showname: '知识属性'},
	                        {id: 71, index: 'ktypelist', name: '知识模板<br>列&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp表', showname: '知识模板'},	 
	                        {id: 72, index: 'indexall', name: '重新构建<br>索引文件', showname: '构建索引'},                       
	                        {id: 73, index: 'behaviorlist', name: '统计分析<br>模型配置', showname: '统计配置'},	   
	                        {id: 62, index: 'userlist', name: '用户管理', showname: '用户管理'},  
	                        {id: 74, index: 'inituser', name: '初&nbsp始&nbsp化<br>&nbsp用&nbsp&nbsp&nbsp户', showname: '初始化用户'},   
	                        {id: 35, index: 'expertmaintain', name: '专&nbsp家&nbsp集<br>&nbsp维&nbsp&nbsp&nbsp护', showname: '专家集维护'},   
	                        {id: 36, index: 'expertset', name: '领域专家<br>设&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp置', showname: '领域专家设置'}
	                
	                    ];
var expertmanagementdata=cims201.utils.getData('custom/customization!kQA.action');
if(expertmanagementdata == "false" || expertmanagementdata == false){
	systemmanagementdata.splice(7,2);
}
var statisticconfigdata = cims201.utils.getData('custom/customization!kStatisticConfig.action');
if(statisticconfigdata == "false" || statisticconfigdata == false){
	systemmanagementdata.splice(4,1);
}
//构造左侧的工具栏
//导航栏管理器
function navManager(type,filename){
	var leftRegion = Edo.get('leftRegion');
	var myNavPanel = Edo.get('leftPanel');
	
	leftRegion.set('visible',true);
	
	if('user' == type){		
		if(myNavPanel){
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			title: '用户中心',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0]	
		});

		if(Edo.get('userNavTree')){
			myNavPanel.addChild(Edo.get('userNavTree'));
		}else{
			myNavPanel.addChild((new createNav_user()).getUser());
		}
		leftRegion.addChild(myNavPanel);
	}else if('resource' == type){
		if(myNavPanel) {
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0],	
		    border: [0,0,0,0]			
		});

		if(Edo.get('resourceNav')){
			myNavPanel.addChild(Edo.get('resourceNav'));
		}else{
			myNavPanel.addChild(new createNav_resource().getResource());
		}
		leftRegion.addChild(myNavPanel);
	}else if('knowledge' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0],	
		    border: [0,0,0,0]	
		});

		if(Edo.get('knowledgeNav')){
			myNavPanel.addChild(Edo.get('knowledgeNav'));
		}else{
			myNavPanel.addChild(new createNav_knowledge().getKnowledge());
		}
		leftRegion.addChild(myNavPanel);
	}else if('patent' == type){//江丁丁 2013-5-12添加，专利
		if(myNavPanel){
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0],	
		    border: [0,0,0,0]	
		});

		if(Edo.get('patentNav')){
			myNavPanel.addChild(Edo.get('patentNav'));
		}else{
			myNavPanel.addChild(new createNav_patent().getPatent());
		}
		leftRegion.addChild(myNavPanel);
	}else if('expert' == type){//江丁丁 2013-6-5添加，名人专栏	
		openNewTab('cims201_experthome', 'experthome', '<div class=cims201_tab_font align=center>专家黄页</div>', {btIcon:'cims201_expert_icon_experthome_small'});
		leftRegion.set('visible',false);
	
   }else if('system' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0],	
		    border: [0,0,0,0]	
		});

		if(Edo.get('knowledgeNav')){
			myNavPanel.addChild(Edo.get('knowledgeNav'));
		}else{
			myNavPanel.addChild(new createNav_system().getSystem());
		}
		leftRegion.addChild(myNavPanel);
	}else if('smc' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
		openNewTab('cims201_smc', 'smc', '<div class=cims201_tab_font align=center>SMC</div>', {});
		leftRegion.set('visible',false);
		
	}else if('position' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
		openNewTab('cims201_position', 'position', '<div class=cims201_tab_font align=center>岗位知识</div>', {btIcon:'cims201_position_icon_small'});
		leftRegion.set('visible',false);

	}else if('ask' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
	
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0],	
		    border: [0,0,0,0]	
		});
		if(Edo.get('askNav')){
			myNavPanel.addChild(Edo.get('askNav'));
		}else{
			myNavPanel.addChild(new createNav_ask().getAsk());
		}
		leftRegion.addChild(myNavPanel);
		
	}
	else if('culture' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
	
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'panel',
			title: '文化动态',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0]	
		});
		if(Edo.get('cultureNav')){
			myNavPanel.addChild(Edo.get('cultureNav'));
		}else{
			myNavPanel.addChild(new createNav_culture().getCulture());
		}
		leftRegion.addChild(myNavPanel);
		
	}
	else if('integrated' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'box',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0],	
		    border: [0,0,0,0]	
		});

		if(Edo.get('integratedNav')){
			myNavPanel.addChild(Edo.get('integratedNav'));
		}else{
			myNavPanel.addChild(new createNav_integrated().getIntegrated());
		}
		leftRegion.addChild(myNavPanel);
	}else if('statistics' == type){	
	if(myNavPanel){
			myNavPanel.destroy();
		}
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'panel',
			title: '<div class=cims201_tab_font align=center>统计分析</div>',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0]	
		});

		if(Edo.get('statisticsNav')){
			myNavPanel.addChild(Edo.get('statisticsNav'));
		}else{
			myNavPanel.addChild((new createNav_statistics()).getStatistics());
		}
		leftRegion.addChild(myNavPanel);
	}
	else if('ontoedit' == type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
	
		myNavPanel = Edo.create({
			id: 'leftPanel',
			type: 'panel',
			title: '<div class=cims201_tab_font align=center>本体编辑</div>',
			width: '100%',
			height: '100%',
		    padding: [0,0,0,0]	
		});
		if(Edo.get('editontology'+filename)){
			myNavPanel.addChild(Edo.get('editontology'+filename));
		}else{
			myNavPanel.addChild(new createNav_ontoeidte(filename).getOntoEditeNav());
		}
		leftRegion.addChild(myNavPanel);
		
	}
	else if('ModManage'== type){
		if(myNavPanel){
			myNavPanel.destroy();
		}
		if(centerRegion){
			centerRegion.destroy();
		}
		myNavPanel = new createNav_ModManageAccordin().getModManageAccordin();
		leftRegion.addChild(myNavPanel);
	}

}
//编辑本体文件
function createNav_ontoeidte(filename)
{
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	}	
	
	var dataTree=cims201.utils.getData('ontoeditcenter!owlClassWrite.action?owlFileName='+filename);

	var tree = new Edo.lists.Tree();
	tree.set({
	    cls: 'e-tree-allow',
	    border: [0,0,0,0],
	    width: "100%",
	    height: "100%",
	    enableCellEdit:false,
	    headerVisible:false,
	    autoColumns: true,
	    horizontalLine: false,
	    columns: [
	        {
	            header: 'bentileibie',
	            dataIndex: 'name',
	            editor: {
	                type: 'text'
	            }
	        }
	    ],
	    data: dataTree,
	    oncellclick :function(e){
            var onto = tree.getSelected();
          
            if(onto.name!='本体类')
            openNewTab('addontologyshuyu'+filename+onto.name, 'addontologyshuyu', "添加"+onto.name, {filenamevalue:filename,ontoname:onto.name,className:onto.nodeDescription});	
            
        }
	});
	var OntoEditeNav = Edo.create({
	    id: 'editontology'+filename,
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    border: [0,0,0,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>查询实例</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                   	{   
	                   		id:'searchtext'+filename,
	                		type: 'autocomplete', 
	                		border: [0,0,0,0],
	            			width: 170, 
	            			queryDelay: 200,
	            			url: 'kmap/onto!recommentTag.action?owlFileName='+encodeURIComponent(filename),
	            			popupHeight: '605',
	            			valueField: 'tagName', 
	            			displayField: 'tagName'
	                	},
	                	{
	                		type: 'formitem', 
	                		width: 170, 
	                		layout: 'horizontal',
	                		children:[
	            		    {type:'button',
	            		    	text:'修改',
	            		    	 onclick: function(e){
	            		             var onto = Edo.get('searchtext'+filename).get('text');
	            		             if(onto.name!='本体类')
		            		             openNewTab('editontologyshuyu'+filename+onto, 'editontologyshuyu', "编辑"+onto, {filenamevalue:filename,ontoname:onto});	
		            		          
	            		         }

	            			},
	            			{
	            				type:'button',
	            				text:'删除',
	            				 onclick: function(e){
	            		             var onto = Edo.get('searchtext'+filename).get('text');
	            		             Edo.MessageBox.confirm("确认删除", "你确定删除术语"+onto+"吗？",
	            		            		 function(action){
	            		            		if(action=='yes'){
	            		             var result=	cims201.utils.getData("ontoeditcenter!delete.action",{owlFileName:filename,indivName:onto});
	            		             alert(result);
	            		        };
	            		         });
	            			}	 
	            			}        
	            		  ]
	                	}
	                	
	                       ]
	        },
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',  
	            border: [0,0,0,0],
	            title: '<span class=cims201_leftbar_font>添加实例</span>',
	            enableCollapse: true,
	            expanded: false,  
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: tree
				        }]
	});
	this.getOntoEditeNav= function(){
		return OntoEditeNav;
	};
	
}

//设计资源获取 张买军添加  2014年6月
function createNav_resource() {
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	};
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	};
	
	function onselectionchange(e){
	    if(!e.selected) return;
	    openNewTab(e.selected.id, e.selected.index,e.selected.name);
	} 
	
	var myResourceNav = Edo.create({
	    id: 'resourceNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    collapseProperty: 'width',
        enableCollapse: true,
	    
	    //cls: 'e-title-collapse',
	   	children: [{
            type: 'panel',
            width: '100%',height: '100%',
            horizontalAlign: 'center',
            enableCollapse: true,
            expanded: true,
            onclick: onPanelClick,   
            title: '<span class=cims201_leftbar_font>产品知识资源</span>',
            titlebar:[{
                cls:'e-titlebar-accordion',
                onclick: toggle
            }],
            children: [
                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, 
                   onselectionchange: function(e){
                   		if(e.selected.index == 'resourceUser') {
                   			navManager01("user");
                   		} else if(e.selected.index == 'resourceKnowledge') {
                   			navManager01("knowledge");
                   		} else if(e.selected.index == 'resourceQuestion') {
                   			navManager01("ask");
                   		} else if(e.selected.index == 'resourcePatent') {
                   			navManager01("patent");
                   		} else if(e.selected.index == 'resourceStatic') {
                   			navManager01("statistics");
                   		}
			        },
                    rowHeight: navRowHeight,
                    columns: [
                        {   
                            renderer: function(v, r){
                            	if('resourceUser' == r.index) {
                            		return '<div  class="cims201_topbar_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<image height=45 width=45 src=css/images/per.png><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户中心</div>';
                            	} else if('resourceKnowledge' == r.index) {
                            		return '<div class="cims201_topbar_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style=height:45px  src=css/images/ku.png><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;知识仓库</div>';
                            	} else if('resourceQuestion' == r.index) {
                            		return '<div class="cims201_topbar_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<image style=height:45px  src=css/images/ask.png><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;知识问答</div>';
                            	} else if('resourcePatent' == r.index) {
                            		return '<div class="cims201_topbar_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style=height:45px  src=css/images/myquestionlist.gif><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专利协同</div>';
                            	} else if('resourceStatic' == r.index) {
                            		return '<div class="cims201_topbar_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<image style=height:45px  src=css/images/tongji.png><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;统计分析</div>';
                            	}
                                
                            }
                        }
                    ],
                    data: [
	                    {id: '1', index: 'resourceUser', type:'resource', name: '用户中心' },
			 			{id: '2', index: 'resourceKnowledge', type:'resource', name: '知识仓库'},
			 			{id: '3', index: 'resourceQuestion', type:'resource', name: '<a href=javascript:navManager01("ask");>知识问答</a>'},
			 			{id: '4', index: 'resourcePatent', type:'resource', name: '<a href=javascript:navManager01("patent");>专利分析</a>'},
			 			{id: '5', index: 'resourceStatic', type:'resource', name: '<a href=javascript:navManager01("statistics");>统计分析</a>'}
                    ]
                }
            ]
        },{
            type: 'panel',
            width: '100%',height: '100%',
            enableCollapse: true,
            expanded: false,    
            onclick: onPanelClick,   
            title: '<span class=cims201_leftbar_font>产品数据资源</span>',
            titlebar:[{
                cls:'e-titlebar-accordion',
                onclick: toggle
            }],
            children: [
                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, 
                    onselectionchange: function(e){		                                                         
			            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.type,btIcon:'cims201_myinfor_icon_'+e.selected.type+'_small'});
			        },
                    rowHeight: navRowHeight,
                    columns: [
                        {   
                            renderer: function(v, r){
                                //return '<div class="cims201_navtree cims201_myinfor_icon_'+r.type+'">'+r.name+'</div>'; //此处修改左边栏小图标
                            	return r.name;
                            }
                        }
                    ],
                    data: [
	                    {id: '6', index: 'resourceDemand', type:'resource', name: '需求获取'},
			 			{id: '7', index: 'resourcePart', type:'resource', name: '零部件'},
			 			{id: '8', index: 'resourceEnergy', type:'resource', name: '能耗数据'}
                    ]
                }
            ]
        }]
	});
	
	this.getResource = function() {
		return myResourceNav;
	};
}

//我的信息
function createNav_user(){
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	}

	var myUserNav = Edo.create({
	    id: 'knowledgeNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        
	       
	        
	           {
	            type: 'panel',
	            width: '100%',height: '100%',
	            enableCollapse: true,
	            expanded: true,    
	            onclick: onPanelClick,   
	            title: '<span class=cims201_leftbar_font>我的知识库</span>',
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){	
	                    		if(e.selected.index!='approvalstatistics')
	                            		{
	                        openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.type,btIcon:'cims201_myknowledgebase_icon_'+e.selected.type+'_small'});
	                            		}
	                            		else
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.type,btIcon:'cims201_knowledgeapproval_icon_'+e.selected.type+'_small'});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                            	
	                            	if(r.index!='approvalstatistics')
	                            		{
	                            		   return '<div class="cims201_navtree cims201_myknowledgebase_icon_'+r.type+'">'+r.name+'</div>';
	                            		
	                            		}
	                            	else
	                            	
	                                return '<div class="cims201_navtree cims201_knowledgeapproval_icon_'+r.type+'">'+r.name+'</div>';
	                            }
	                        }
	                    ],
	                    data:updata
	                    
	                }
	            ]
	        },
	           {
	            type: 'panel',
	            width: '100%',height: '100%',
	            enableCollapse: true,
	            expanded: false,    
	            onclick: onPanelClick,   
	            title: '<span class=cims201_leftbar_font>我的任务</span>',
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){		                                                         
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>",{type:e.selected.type,btIcon:'cims201_mytask_icon_'+e.selected.type+'_small'});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                                return '<div class="cims201_navtree cims201_mytask_icon_'+r.type+'">'+r.name+'</div>';
	                            }
	                        }
	                    ],
	                    data: taskdata
	                }
	            ]
	        },
	          {
	            type: 'panel',
	            width: '100%',height: '100%',
	            enableCollapse: true,
	            expanded: false,    
	            onclick: onPanelClick,   
	            title: '<span class=cims201_leftbar_font>我的信息</span>',
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){		                                                         
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.type,btIcon:'cims201_myinfor_icon_'+e.selected.type+'_small'});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                       //  if(r.name=='myinforcard'){return '<img src=css/images/myinforcard.gif><div class="cims201_navtree">'+r.name+'</div>'; }
	                            
	                             
	                                return '<div class="cims201_navtree cims201_myinfor_icon_'+r.type+'">'+r.name+'</div>'; //此处修改左边栏小图标
	                            }
	                        }
	                    ],
	                    data: [
		                    {id: '1', index: 'myinfor', type:'myinforcard', name: '我的名片'},
				 		//	{id: '2', index: 'myinfor', type:'myinforfriend', name: '我的好友'},
				 			{id: '3', index: 'myinfor', type:'myinformessage', name: '我的消息'}
	                    ]
	                }
	            ]
	        }
	         
	        
	        ,
	           {
	            type: 'panel',
	            width: '100%',height: '100%',
	            enableCollapse: true,
	            expanded: false,    
	            onclick: onPanelClick,   
	            title: '<span class=cims201_leftbar_font>我的知识仓库</span>',
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){		                                                         
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.type,btIcon:'cims201_mykbase_icon_'+e.selected.type+'_small',nodeId:e.selected.nodeId});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                                return '<div class="cims201_navtree cims201_mykbase_icon_'+r.type+'">'+r.name+'</div>';
	                            }
	                        }
	                    ],
	                    data: warehousedata
	                }
	            ]
	        }
	        	    ]
	}); 
	
	this.getUser = function(){
		return myUserNav;
	};
}

//专利   江丁丁添加，2013-5-12
function createNav_patent(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	}
	function onselectionchange(e){
	    if(!e.selected) return;
	    //openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{btIcon:'cims201_usercenter_icon_'+e.selected.index+'_small'});
	} 
	
	var myPatentNav = Edo.create({
	    id: 'patentNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>专利搜索&抓取</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){		                                                         
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.index,btIcon:'cims201_mypatent_icon_'+e.selected.index+'_small'});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                          
	                                return '<div class="cims201_navtree cims201_mypatent_icon_'+r.index+'">'+r.name+'</div>';
	                            }
	                          
	                        }
	                    ],
	                    data: [
	                        {id: 21, index: 'patentchinesesearch', name: '中国专利'},
	                        {id: 22, index: 'patentussearch', name: '美国专利'}
	                    ]
	                }
	            ]
	        }
	        ]
	}); 
	
	this.getPatent = function(){
		return myPatentNav;
	};
}

//名人专栏 江丁丁添加  2013-6-5
function createNav_expert(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	}
	function onselectionchange(e){
	    if(!e.selected) return;
	    //openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{btIcon:'cims201_usercenter_icon_'+e.selected.index+'_small'});
	} 
	
	var myExpertNav = Edo.create({
	    id: 'patentNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>专家黄页</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){		                                                         
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.index,btIcon:'cims201_expert_icon_'+e.selected.index+'_small'});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                          
	                                return '<div class="cims201_navtree cims201_expert_icon_'+r.index+'">'+r.name+'</div>';
	                            }
	                          
	                        }
	                    ],
	                    data: [
	                        {id: 26, index: 'experthome', name: '专家专栏'},
	                        {id: 27, index: 'publisharticle', name: '发布博文'}//以后移到用户中心
	                    ]
	                }
	            ]
	        }
	        ]
	}); 
	
	this.getExpert = function(){
		return myExpertNav;
	};
}

//知识仓库
function createNav_knowledge(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	}
	function onselectionchange(e){
	    if(!e.selected) return;
	    openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{btIcon:'cims201_usercenter_icon_'+e.selected.index+'_small'});
	} 
	
	var myKnowledgeNav = Edo.create({
	    id: 'knowledgeNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>知识搜索</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){		                                                         
				            openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{type:e.selected.index,btIcon:'cims201_myknowledgebase_icon_'+e.selected.index+'_small'});
				        },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                          
	                                return '<div class="cims201_navtree cims201_myknowledgebase_icon_'+r.index+'">'+r.name+'</div>';
	                            }
	                          
	                        }
	                    ],
	                    data: knowledgesearchdata
	                }
	            ]
	        },
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>知识多维导航</span>',
	            enableCollapse: true,
	            expanded: false,  
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    id: 'catagoryTree_nav',
	                    onselectionchange: function(e){
	                    	var showname=e.selected.name;
	                    	if(showname.length>4)
	                    	showname=showname.substring(0,4)+"...";
	                    	openNewTab(e.selected.nodeId, e.selected.index,"<div class=cims201_tab_font align=center>"+showname+"</div>",{btIcon:'cims201_catagoryTree_icon_small',nodeId:e.selected.nodeId});
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                            	     	
	                            var showname=r.name;
	                          	if(showname.length==5)
	                            	{
	                            	showname="&nbsp;&nbsp;"+showname.substring(0,2)+"<br>"+showname.substring(2);	
	                            	}
	                            	else
	                            	if(showname.length==6)
	                            	{
	                            	showname=showname.substring(0,3)+"<br>"+showname.substring(3);	
	                            	}else
	                            	if(showname.length==7)
	                            	{
	                            	showname=showname.substring(0,4)+"<br>"+showname.substring(4);	
	                            	}else	
	                            	if(showname.length==8)
	                            	{
	                            	showname=showname.substring(0,4)+"<br>"+showname.substring(4);
	                            	}else
	                            	if(showname.length==9)
	                            	{
	                            	showname="&nbsp;&nbsp;"+showname.substring(0,4)+"<br>"+showname.substring(4);
	                            	}else
	                            	if(showname.length>9)
	                            	{
	                            	showname="&nbsp;&nbsp;"+showname.substring(0,4)+"<br>"+showname.substring(4,8)+"...";
	                            	}
	                            	
	                                return '<div class="cims201_navtree cims201_catagoryTree_icon">'+showname+'</div>';
	                            }
	                        }
	                    ],
	                    data: []
	                }
	            ]
	        },
	        
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>知识域导航</span>',
	            enableCollapse: true,
	            expanded: false,  
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    id: 'domainTree_nav',
	                    onselectionchange: function(e){
	                       	
	                    var showname=e.selected.name;
	                    	if(showname.length>4)
	                    	showname=showname.substring(0,4)+"...";
	                    	
	                    	openNewTab(e.selected.nodeId, e.selected.index,"<div class=cims201_tab_font align=center>"+showname+"</div>",{btIcon:'cims201_domainTree_icon_small',nodeId:e.selected.nodeId});
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                            	   var showname=r.name;
	                            	if(showname.length==5)
	                            	{
	                            	showname="&nbsp;&nbsp;"+showname.substring(0,2)+"<br>"+showname.substring(2);	
	                            	}
	                            	else
	                            	if(showname.length==6)
	                            	{
	                            	showname=showname.substring(0,3)+"<br>"+showname.substring(3);	
	                            	}else
	                            	if(showname.length==7)
	                            	{
	                            	showname=showname.substring(0,4)+"<br>"+showname.substring(4);	
	                            	}else	
	                            	if(showname.length==8)
	                            	{
	                            	showname=showname.substring(0,4)+"<br>"+showname.substring(4);
	                            	}else
	                            	if(showname.length==9)
	                            	{
	                            	showname="&nbsp;&nbsp;"+showname.substring(0,4)+"<br>"+showname.substring(4);
	                            	}else
	                            	if(showname.length>9)
	                            	{
	                            	showname="&nbsp;&nbsp;"+showname.substring(0,4)+"<br>"+showname.substring(4,8)+"...";
	                            	}
	                                return '<div class="cims201_navtree cims201_domainTree_icon">'+showname+'</div>';
	                            }
	                        }
	                    ],
	                    data: []
	                }
	                
	            ]
	        }
	      
	    ]
	}); 
	//从后台加载数据
	cims201.utils.getData_Async('tree/tree!listTreeRoots.action',{type:'domainTree'},function(text){
		if(text == null || text == '') return;
		var data = Edo.util.Json.decode(text);
		Edo.get('domainTree_nav').set('data',data);
	});
	cims201.utils.getData_Async('tree/tree!listTreeRoots.action',{type:'categoryTree'},function(text){
		if(text == null || text == '') return;
		var data = Edo.util.Json.decode(text);
		Edo.get('catagoryTree_nav').set('data',data);
	});

	
	this.getKnowledge = function(){
		return myKnowledgeNav;
	};
}

//知识地图
function createNav_map(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	} 
	
	
	
	var myMapNav = Edo.create({
	    id: 'mapNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>知识地图</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                 
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'roleTree',btIcon:'cims201_knowledgemap_icon_'+e.selected.index+'_small'});             
	                    	
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                            	if(r.index == "ontology"){
	                                	return '<div class="cims201_navtree cims201_system_icon_'+r.index+'">'+r.name+'</div>';
	                            	}else
	                                	return '<div class="cims201_navtree cims201_knowledgemap_icon_'+r.index+'">'+r.name+'</div>';
	                               
	                            }
	                        }
	                    ],
	                    data: [

	                        {id: 31, index: 'kmap', name: '地图浏览', showname: '知识地图'},
	                        {id: 86, index: 'ontology', name: '地图编辑', showname: '地图编辑'}
	                    
	                    ]
	                }
	                    
	            ]
	        }
	    ]
	}); 
	
	this.getMap = function(){
		return myMapNav;
	};
}

//知识问答
function createNav_ask(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	} 
	
	
	
	var myAskNav = Edo.create({
	    id: 'askNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>问答导航</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                    
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'roleTree',btIcon:'cims201_qanda_icon_'+e.selected.index+'_small'});             
	                    	
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                            
	                                	return '<div class="cims201_navtree cims201_qanda_icon_'+r.index+'">'+r.name+'</div>';
	                                
	                            }
	                        }
	                    ],
	                    data: [
	                        {id: 38, index: 'equestiondaohang', name: '领域专家'+'<br>'+'问题导航', showname: '领域专家问题导航'},
	                        {id: 39, index: 'qcategorydaohang', name: '问题分类<br>导&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp航', showname: '问题分类导航'},
 	                        {id: 40, index: 'questionsearch', name: '问题检索', showname: '问题检索'}  
	                    ]
	                }
	            ]
	        },
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',
	            enableCollapse: true,
	            expanded: false,    
	            onclick: onPanelClick,   
	            title: '<span class=cims201_leftbar_font>我的问答</span>',
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                   
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font>"+e.selected.showname+"</div>", {type:'domainTree',btIcon:'cims201_qanda_icon_'+e.selected.index+'_small'});
	                    
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                          
	                         	return '<div class="cims201_navtree cims201_qanda_icon_'+r.index+'">'+r.name+'</div>';
	                            		                      
	                            }
	                        }
	                    ],
	                    data: [
	                        {id: 41, index: 'askpublish', name: '问题发布', showname: '问题发布'},
	                        {id: 42, index: 'myquestionlist', name: '问答列表', showname: '问答列表'},
	                        {id: 43, index: 'waitedquestion', name: '待&nbsp解&nbsp决<br>&nbsp问&nbsp&nbsp&nbsp题', showname: '待解决问题'}                    
	                    ]
	                }
	            ]
	        }

	    ]
	}); 
	
	this.getAsk = function(){
		return myAskNav;
	};
}

//知识集成 integrated
function createNav_integrated(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	}
	function onselectionchange(e){
	    if(!e.selected) return;
	    //openNewTab(e.selected.id, e.selected.index,e.selected.name);
	} 
	
	var myIntegratedNav = Edo.create({
	    id: 'integratedNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>文档系统集成</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                     onselectionchange: function(e){
	                   
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font>"+e.selected.showname+"</div>", {type:'categoryTree',btIcon:'cims201_integrate_icon_integratetemp_small'});             
	                    
	                    	
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                                //return '<div class="cims201_navtree cims201_integrated_icon_'+r.index+'">'+r.name+'</div>';
	        
	                            	return '<div class="cims201_navtree cims201_integrate_icon_integratetemp">'+r.name+'</div>';
	                            }
	                        }
	                    ],
	                    data: [
	                        {id: 50, index: 'avidmsystem', name: 'AVIDM系统',showname:'AVIDM系...'},
	                        {id: 53, index: 'standardsystem', name: '标准系统',showname:'标准系统'}
	                    ]
	                }
	            ]
	        }
	    ]
	}); 
	
	this.getIntegrated = function(){
		return myIntegratedNav;
	};
}

//统计分析
function createNav_statistics(){
	var knowledgerankdata = [
	                         	{id: '56', index: 'khotlist', name: '热点知识'},
	      		 		   		{id: '57', index: 'userrank', name: '用户排行'},
	      		 		   		{id: '58', index: 'statisticswhole', name: '总体趋势'}
	                        ];
	var trenddata=cims201.utils.getData('custom/customization!kTrend.action');
	if(trenddata == false || trenddata == "false"){
		knowledgerankdata.splice(2,1);
	}     
	var myStatisticsNav = Edo.create({
	    id: 'statisticsNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    children:[        
              {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
                  onselectionchange: function(e){		                                                         
			          openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{btIcon:'cims201_statistics_icon_'+e.selected.index+'_small'});
			      },
                  rowHeight: navRowHeight,
                  columns: [
                      {   
                          renderer: function(v, r){
                              return '<div class="cims201_navtree cims201_statistics_icon_'+r.index+'">'+r.name+'</div>';
                          }
                      }
                  ],
                  data: knowledgerankdata
              }
             ]
         });
	            
	
	this.getStatistics = function(){
		return myStatisticsNav;
	};
}

//系统配置
function createNav_system(){
	
	function toggle(e){
	    var panel = this.parent.owner;
	    var accordion = panel.parent;
	    accordion.getChildren().each(function(child){
	        child.collapse();
	    });
	    panel.expand();
	}
	
	function onPanelClick(e){
	    if(e.within(this.headerCt) && this.expanded == false){
	        this.parent.getChildren().each(function(child){
	            child.collapse();
	        });
	        this.expand();
	    }
	} 
	
	var mySystemNav = Edo.create({
	    id: 'knowledgeNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    //render: document.body,    
	    cls: 'e-title-collapse',
	    children:[
	        {
	            type: 'panel',padding:0,
	            width: '100%',height: '100%',              
	            title: '<span class=cims201_leftbar_font>人员角色树</span>',
	            enableCollapse: true,
	            onclick: onPanelClick,                                           
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                    	if('edittree' == e.selected.index){
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'roleTree',btIcon:'cims201_system_icon_'+e.selected.index+'_role_small'});             
	                    	}else{
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'roleTree',btIcon:'cims201_system_icon_'+e.selected.index+'_small'});             
	                    	}
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                                if('edittree' == r.index){
	                                	return '<div class="cims201_navtree cims201_system_icon_'+r.index+'_role">'+r.name+'</div>';
	                                }else{
	                                	return '<div class="cims201_navtree cims201_system_icon_'+r.index+'">'+r.name+'</div>';
	                                }
	                                
	                            }
	                        }
	                    ],
	                     data: [
                  
                            {id: 60, index: 'edittree', name: '编&nbsp&nbsp&nbsp&nbsp辑<br>角色树', showname: '编辑角色树'},
	                        {id: 59, index: 'assignmanager', name: '分配角色<br>树管理员', showname: '角色管理员'},
	                        {id: 61, index: 'addpermission', name: '增加角色<br>&nbsp树人员', showname: '增加角色人'}   
	                                     

	                    ]
	                }
	            ]
	        },
	        {
	            type: 'panel',
	            width: '100%',height: '100%',
	            enableCollapse: true,
	            expanded: false,    
	            onclick: onPanelClick,   
	            title: '<span class=cims201_leftbar_font>知识域管理</span>',
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                    	if(e.selected.index == 'edittree'){
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'domainTree',btIcon:'cims201_system_icon_'+e.selected.index+'_domain_small'});             
	                    	}else{
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'domainTree',btIcon:'cims201_system_icon_'+e.selected.index+'_small'});
	                    	}
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                            	if(r.index == 'edittree'){
	                            		return '<div class="cims201_navtree cims201_system_icon_'+r.index+'_domain">'+r.name+'</div>';	
	                            	}else{
	                            		return '<div class="cims201_navtree cims201_system_icon_'+r.index+'">'+r.name+'</div>';
	                            	}	                      
	                            }
	                        }
	                    ],
	                    data: [
	                        {id: 63, index: 'assignmanager', name: '&nbsp分配域<br>&nbsp管理员', showname: '域管理员'},
	                        {id: 64, index: 'edittree', name: '编辑域树', showname: '编辑域树'},
	                        {id: 65, index: 'addpermission', name: '设置域树<br>&nbsp&nbsp权限', showname: '设域权限'}	                     
	                    ]
	                }
	            ]
	        },
	        {
	            type: 'panel',
	            width: '100%',height: '100%',
	            title: '<span class=cims201_leftbar_font>分类树管理</span>',
	            enableCollapse: true,
	            expanded: false,
	            onclick: onPanelClick,
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                    	if(e.selected.index == 'edittree'){
	                    		openNewTab(e.selected.id, e.selected.index, "<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'categoryTree',btIcon:'cims201_system_icon_'+e.selected.index+'_category_small'});             
	                    	}else{
	                    		openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'categoryTree',btIcon:'cims201_system_icon_'+e.selected.index+'_small'});             
	                    	}
	                    	
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){
	                          
	                                	return '<div class="cims201_navtree cims201_system_icon_'+r.index+'">'+r.name+'</div>';
	                                }
	                           
	                        }
	                    ],
	                    data: [

	                        {id: 66, index: 'assignmanager', name: '分配分类<br>树管理员', showname: '类管理员'},
	                        {id: 67, index: 'edittree', name: '编&nbsp&nbsp&nbsp&nbsp辑<br>分类树', showname: '编辑分类'},
	                        {id: 69, index: 'addpermission', name: '设置分类<br>&nbsp树权限', showname: '分类权限'}	                     

	                    ]
	                }
	            ]
	        },
	        {
	            type: 'panel',
	            width: '100%',height: '100%',
	            title: '<span class=cims201_leftbar_font>系统管理</span>',
	            enableCollapse: true,
	            expanded: false,
	            onclick: onPanelClick,
	            titlebar:[{
	                cls:'e-titlebar-accordion',
	                onclick: toggle
	            }],
	            children: [
	                {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
	                    onselectionchange: function(e){
	                    	
	                    		openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.showname+"</div>", {type:'categoryTree',btIcon:'cims201_system_icon_'+e.selected.index+'_small'});             
	                    	
	                    },
	                    rowHeight: navRowHeight,
	                    columns: [
	                        {   
	                            renderer: function(v, r){                 
	                                	return '<div class="cims201_navtree cims201_system_icon_'+r.index+'">'+r.name+'</div>';
	                            }
	                        }
	                    ],
	                    data: systemmanagementdata
	                }
	            ]
	        }
	    ]
	}); 
	
	
	this.getSystem = function(){
		//判断是否展示系统配置的部分
		
			cims201.utils.getData_Async('privilege/show-button!whetherShowSuperAdminBtn.action',null, function(text){
		var data = Edo.util.Json.decode(text);
		if(data.show == true || data.show == 'true'){
			
		}
		else{
	       if( mySystemNav.numChildren()==4)
			mySystemNav.removeChildAt(3); 
		}
			});
		return mySystemNav;
	};
}

//文化动态
function createNav_culture(){	
	var myCltureNav = Edo.create({
	    id: 'cultureNav',
	    type: 'box',
	    height: '100%', 
	    width: '100%',
	    padding: [0,0,20,0],
	    children:[        
              {type: 'table', autoColumns: true, headerVisible: false,width: '100%', height: '100%',style:'border:0;',verticalLine: false, horizontalLine: false, //rowHeight: 25,
                  onselectionchange: function(e){		                                                         
			          openNewTab(e.selected.id, e.selected.index,"<div class=cims201_tab_font align=center>"+e.selected.name+"</div>",{btIcon:'cims201_statistics_icon_'+e.selected.index+'_small'});
			      },
                  rowHeight: navRowHeight,
                  columns: [
                      {   
                          renderer: function(v, r){
                              return '<div class="cims201_navtree cims201_statistics_icon_'+r.index+'">'+r.name+'</div>';
                       }
                      }
                  ],
                  data: [
                   {id: '75', index: 'hforum', name: '高端论坛'},
		 		   {id: '76', index: 'themeactive', name: '主题活动'},
		 		   {id: '77', index: 'theory', name: '理论知识'}
                  ]
              }
             ]
         });
	            
	
	this.getCulture= function(){
		return myCltureNav;
	};
}

