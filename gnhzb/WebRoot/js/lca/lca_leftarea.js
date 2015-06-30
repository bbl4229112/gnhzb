var lcaNav = Edo.create({
                		id:'mainPanel',type: 'ct',width: 1150,height: 686,verticalGap: 0,
                		render: document.getElementById('leftarea'),
		                children:[
							{
								id:'mainTabBar',type: 'tabbar',selectedIndex: 0,border: [0,0,1,0],
								onselectionchange: function(e){								
									mainTabContent.set('selectedIndex', e.index);
								},
								children: [
									{index:0,type: 'button', icon: 'cims201_topbar_icon_homepage_small', width: '80', text:'<div style="font-size:11px;font-weight: bolder;" align=center>主页</div>'}
									
								]
							},
							{
								id: 'mainTabContent',selectedIndex: 0,layout: 'viewstack',type: 'box',border: [0,1,1,1],width: '100%',height: '100%',verticalScrollPolicy: 'auto',
								onselectionchange: function(e){
									alert('content-selected');
								},
								children: [	
								   {type: 'label', text: '<img src="css/images/Ferrari1.jpg" height=686 width=1150></img>'}
								    //cims201_indexPage.getIndex()
								]
							}
			  			]
		            });

function openNewTab(id, index, title, params){
	//首先将enter事件取消注册
	currentEventID = null;

	var mainTabBar = Edo.get('mainTabBar');
	var mainTabContent = Edo.get('mainTabContent');
	var tabSize1 = mainTabBar.children.length;
	var tabSelectedIndex1 = mainTabBar.selectedIndex;
	var c = null;
	var module = getComponentByIndex('cont_'+index+''+id,index,params);
	if(module=='error')
		{
		}
		else{
		if(module == null){
			module = Edo.create({
		
			type: 'module',
			width: '100%',
    		height: '95%',
			src: 'building.jsp'
		});
		}
	
	
	
	mainTabBar.children.each(function(o){		
		if(('tbar'+index+''+id) == o.id){
			c = o;		
		}
	});
	
	if(c==null){	
		c = mainTabBar.addChildAt(tabSize1,
			{id:'tbar'+index+''+id ,type: 'button', icon:params['btIcon']?params['btIcon']:null,width: 100, text: title, arrowMode: 'close',
			    onarrowclick:function(e){
					//删除对应的enter事件
					enterEventIDList.each(function(o,mi){
						if(('cont_'+index+''+id) == o){
							enterEventIDList.splice(mi,1);
							enterEventList.splice(mi,1);
						}
					});
					//alert(index+id);
					//根据idx, 删除对应的容器				
					var currentContent = null;
					mainTabContent.children.each(function(o){
			
						if(('cont_'+index+''+id) == o.id){
							currentContent = o;
						}
					});
					
					//currentContent = mainTabContent.getChildAt(mainTabBar.selectedIndex);
					if(currentContent != null){
						
						currentContent.destroy();
					}			
					//选中原来Index处					
					var tabitem = mainTabBar.getChildAt(mainTabBar.selectedIndex);					
					if(!tabitem){
					    tabitem = mainTabBar.getChildAt(mainTabBar.selectedIndex-1);						    
					}					
					mainTabBar.set('selectedItem', tabitem);				
				}
			}
		);
		

		mainTabContent.addChildAt(tabSize1,module);			
	};
	mainTabBar.set('selectedItem', c);		
}
}
var unapprovalbox;
function getComponentByIndex(id,index,params){	
	var myComponent = null;
	//知识检索的界面
	if('newmodule' == index){
	
		var existcomponent=	iscomponentexist(id,'newmodule1234');
		if(null!=existcomponent)
			{
			myComponent=existcomponent;
			
			}		
		else	{
		
		var myTable1_rt = new createnewmodule();   
		myComponent = Edo.create({
			id: id?id:'newmodule1234',   
		    type: 'box',                 
		    layout: 'vertical',  
		    width: '100%',
		    height: '100%',
		    border:[0,0,0,0],
		    padding: [0,22,0,12],
		    children: [
		    	myTable1_rt.getnewmodule()
		    ]
		});
		}
	}
	else if('oldmodule' == index){
		var existcomponent=	iscomponentexist(id,'oldmodule1234');
		if(null!=existcomponent)
			{
			myComponent=existcomponent;
			
			}
		
		else	{
		
		var myTable1_rt = new showmodule();   
		myComponent = Edo.create({
			id: id?id:'oldmodule1234',   
		    type: 'box',                 
		    layout: 'vertical',  
		    width: '100%',
		    height: '100%',
		    border:[0,0,0,0],
		    padding: [0,0,0,0],
		    children: [
		    	myTable1_rt.getoldmodule()
		    ]
		});
		}
	//知识检索的界面
	
	}else if('nodemanage' == index){
		var existcomponent=	iscomponentexist(id,'nodemanage1234');
		if(null!=existcomponent)
			{
			myComponent=existcomponent;
			
			}
		
		else	{
		
		var myTable1_rt = new nodemanage();   
		myComponent = Edo.create({
			id: id?id:'nodemanage1234',   
		    type: 'box',                 
		    layout: 'vertical',  
		    width: '100%',
		    height: '100%',
		    border:[0,0,0,0],
		    padding: [0,0,0,0],
		    children: [
		    	myTable1_rt.getnodemanage()
		    ]
		});
		}
	//知识检索的界面
	
	}
	else if('experthome' == index){//名人专栏首页
		var existcomponent=	iscomponentexist(id,'experthome234');
		if(null!=existcomponent)
			{
			myComponent=existcomponent;
			
			}		
		
		var myTable1_rt = new createExpertHome();   	
		myTable1_rt.search();
		
		
		myComponent = Edo.create({
		    id: id?id:'experthome234', 
		    type: 'box',                 
		    layout: 'vertical',  
		    width: '100%',
		    //height: 1250,  
		    children: [
		        myTable1_rt.getExpertHome()
		    ]
		});
	}else{
		}
	return myComponent;
	}
function iscomponentexist(id,backupid)
{
		var existcomponent=Edo.get(id?id:backupid)
		if(null!=existcomponent)
		return existcomponent;
		else
		return null;

}
/*var carChooseSelector = Edo.create(
		{
			type: 'box',
			id:'leftbox',
			//title: '选择汽车零部件',
			render: document.getElementById('rightarea'),
			width: '100%',
			height: '100%',
			padding: 0,
			border:[0,0,0,0],
			horizontalScrollPolicy:'off',
			children: [			
			{
				type: 'box',
				width: 290,
				border: 0,
				padding: 0,
				layout: 'vertical',
				children: [
					{
					    type: 'button', width: '100%',  text: '选择已有过程模型', onclick: function(e){
						if(Edo.get('componentNameInput').get('text') == null || Edo.get('componentNameInput').get('text') == ''){
							alert('请选择服装!');    //  画流程图 
						}else{
							Edo.util.Ajax.request({
						        type: 'post',        
						        url: '<%=basePath%>/lca/welcome!checkComponent.action',
						        params: {
					                name: Edo.get('componentNameInput').get('text')   //传递父节点的Name(也可以是ID)
					            },
						        onSuccess: function(text){
						            var data = Edo.util.Json.decode(text);
						            if(data.result >= 0){
						            	startFlowChart(data.result);
						            }else{
						            	alert('请选择服装!');
						            }
						        }
						    });
						}
					}},
					{
					    type: 'button', width: '100%',  text: '新建过程建模', onclick: function(e){
					    var componentTree=new getcomponentTree();
					    var toolbar=new gettoolbar();
						var win=cims201.utils.getWin(400,600,'选择零部件',[componentTree,toolbar]);
						Edo.util.Ajax.request({
					        type: 'post',        
					        url: 'lca/welcome!getComponentsList.action',
					        params: {
				                parentId: '0'   //传递父节点的Name(也可以是ID)
				            },
					        onSuccess: function(text){
					        alert(text);
					            var data = Edo.util.Json.decode(text);
					           
					            Edo.get('componentTree').set('data', data);
					        }
					        
					    });
					     win.show('center', 'middle', true);
					}},
					{
					    type: 'button', width: '100%',  text: '开始计算', onclick: function(e){
						if(Edo.get('componentNameInput').get('text') == null || Edo.get('componentNameInput').get('text') == ''){
							alert('请选择发动机部件!');            //startCaculator
						}else{
							Edo.util.Ajax.request({
						        type: 'post',        
						        url: '<%=basePath%>/lca/welcome!checkComponent.action',
						        params: {
					                name: Edo.get('componentNameInput').get('text')   //传递父节点的Name(也可以是ID)
					            },
						        onSuccess: function(text){
						            var data = Edo.util.Json.decode(text);
						            if(data.result >= 0){
						            	startCalculator(data.result);
						            }else{
						            	alert('please enter right component name!');
						            }
						        }
						    });
						}
					}}
				]
			}
			]
		});*/