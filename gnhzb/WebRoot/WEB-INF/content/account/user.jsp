<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>中国运载火箭技术研究院知识服务平台</title>
		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/css/wenku.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body style="margin: 0px; width: 100%; overflow:hidden;" scroll="no">
		<div id="bottomBar" class="cims201_message_bar_ie">
    	
    	</div>
	
	</body>
</html>
<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
<script src="${ctx}/js/cims201.js" type="text/javascript"></script>
<script src="${ctx}/js/utils.js" type="text/javascript"></script>
<script src="${ctx}/js/message/message-bar.js" type="text/javascript"></script>

<script type="text/javascript">

	/**
	ajax应用
	{
                type: 'button',
                text: '新增角色',
                onclick: function(e){
                    createRoleForm();
                    setReadOnly(false);
                    Edo.get('win_3').reset();
                    Edo.get('submitBtn').set('onClick', function(){
                    	if(Edo.get('win_3').valid()){
                    		var json = Edo.util.Json.encode(Edo.get('win_3').getForm());
                    		//从服务器端获取类别信息
							Edo.util.Ajax.request({
							    url: 'modelallocation.do?method=manageRole&type=add&role='+json,
							    type: 'post',
							    async: false,
							    onSuccess: function(text){
							        //text就是从url地址获得的文本字符串
							        alert('新增成功');
							        alert(text);
							        var role = Edo.util.Json.decode(text);
							        
							        Edo.get('table1').data.add(role);
							        
							        Edo.get('win_3').hide();
							    },
							    onFail: function(code){
							        //code是网络交互错误码,如404,500之类
							        alert(code);
							    }
							});
                    	}
                 
                    });
                }
            }
	*/
	
//创建一个新的tab
	function openNewTab(e){

		var idx	= e.selected.id;
		var taburl=e.selected.url;
		var tabname=e.selected.name;
		
		var c = Edo.get("tbar_"+idx);
		if(c==null){
			c = mainTabBar.addChildAt(idx,
				{id:'tbar_'+idx,type: 'button',text: tabname,arrowMode: 'close',
				    onarrowclick:function(e){
						//根据idx, 删除对应的容器
						var c = Edo.get('cont_'+idx);					
						c.destroy();
						//选中原来Index处					
						var tabitem = mainTabBar.getChildAt(mainTabBar.selectedIndex);					
						if(!tabitem){
						    tabitem = mainTabBar.getChildAt(mainTabBar.selectedIndex-1);	
						    
						}
						
						mainTabBar.set('selectedItem', tabitem);				
					}
				}
			);
		var module = mainTabContent.addChildAt(idx,
			{
				id:'cont_'+idx,type:"module",width: '100%',height: '100%',style: 'border:0'
			}
		);		
		module.load(taburl);
		
		};
		mainTabBar.set('selectedItem', c);
			
	};
	


 	//构建主体框架
    Edo.build(
    {
        type: 'app',
        render: document.body,
        //width: cims201.utils.getScreenSize().width,
        //height: cims201.utils.getScreenSize().height,
        //height: cims201.utils.getScreenSize().height+50,
        layout: 'vertical',
        padding: [0,0,0,0],
        border: [0,0,0,0],
        //增加框架的元素
        children:[
        	//顶栏描述
        	{
                type: 'ct',padding: [0,0,0,0],width: '100%',height: '40', layout: 'horizontal',titleIcon:'',
                children:[
               
	                {   
					    type: 	'label',width: 	'100%',height: '100%',
					    style:	'font-size:20px;padding:5px;padding-top:8px;font-family:微软雅黑, 宋体, Verdana;font-weight:bold; ',
					    text: '中国运载火箭研究院知识服务平台-研发中心'
	                },
	                {
	                    type: 'label', text: '您好, <%=SpringSecurityUtils.getCurrentUserName()%><a href="${ctx}/j_spring_security_logout">退出</a>'
	                }
                ]
            },
            //工具栏
            
            //主界面
            {
                type: 'ct',
                padding: [0,0,0,0],
                width: '100%',
                height: '100%',
                layout: 'horizontal',
                children:[
                	//左侧边
                	{
		                type: 'ct',width: '180',height: '100%',
		                collapseProperty: 'width',
		                enableCollapse: true,
		                splitRegion: 'west',
		                splitPlace: 'after',
		                children: [
		                    {
		                        id: 'leftPanel',type: 'panel',title: '左侧面板',width: '100%',height: '100%',
		                        children: [
		                            {
		                                id: 'navTree',type: 'tree',width: '100%',height: '100%',headerVisible: false,autoColumns: true,horizontalLine: false,
		                                columns: [
		                                    {
		                                        header: '导航树',
		                                        dataIndex: 'url',
		                                        renderer: function(v, r){
		                                            //return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
		                                            return r.name;
		                                        }
		                                    }
		                                ],
		                                data: [
		                               
		                                    {id: 2, url: 'user!input.action', name: '创建用户'},
		                                    {id: 3, url: '../knowledge/property/property!input.action', name: '创建知识属性'},
		                                    {id: 4, url: '../knowledge/ktype/ktypeinput!ktypeinput.action', name: '创建知识类型'},
		                                    {id: 5, url: '../knowledge/ui!kupload.action', name: '采集知识'},
		                                    {id: 6, url: '../knowledge/ui!ksearch.action', name: '搜索知识'}
		                                   // {id: 7, url: '../knowledge/knowledge!showknowledgeUI.action', name: '查看知识'}
		                                ],
		                                onselectionchange: function(e){		                                    
		                                    //var r = this.getSelected();
		                                    //if(r && r.url){
		                                        //mainTabBar.set('selectedIndex', 0);		                                        
		                                        //mainModule.load(r.url);
		                                    //}
		                                    
		                                    openNewTab(e);
		                                }
		                            }
		                        ]
		                    }
		                ]
		            },
		            //右主界面
                	{
                		id:'mainPanel',type: 'ct',width: '100%',height: '100%',verticalGap: 0,
		                children:[
							{
								id:'mainTabBar',type: 'tabbar',selectedIndex: 0,border: [0,0,1,0],
								onselectionchange: function(e){								
									mainTabContent.set('selectedIndex', e.index);
								},
								children: [
									{index:0,type: 'button',text: '主页'}
								]
							},
							{
								id: 'mainTabContent',selectedIndex: 0,layout: 'viewstack',type: 'box',border: [0,1,1,1],width: '100%',height: '100%',verticalScrollPolicy: 'auto',
								onselectionchange: function(e){
									alert('content-selected');
								},
								children: [								    
								    {id: 'mainModule',type:"module",width: '100%',height: '100%', style: 'border:0', src:'${ctx}/header.jsp'}
								]
							}
			  			]
		            }//end 右主界面
                ]
		    }
		    
        ]
    });      
	
	
	//根据不同的浏览器设置不同的样式  	
	var Sys = cims201.utils.getExploreVersion();
	if(Sys.ie == '6.0'){
		document.getElementById('bottomBar').setAttribute("className","cims201_message_bar_ie");	
		//alert('ie6');
	}else{
		document.getElementById('bottomBar').setAttribute("class","cims201_message_bar");	
	}
	
	var myMessageBar = new createMessageBar(document.getElementById('bottomBar')).getMessageBar();
</script>
