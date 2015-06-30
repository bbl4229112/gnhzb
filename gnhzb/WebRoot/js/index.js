//index主页
//顶栏
function createIndexTopBar(){
	var myIndexTopBar = Edo.create(
		{
            type: 'ct',padding: [0,0,0,0],border:[0,0,0,0],width: '100%',height: '40', layout: 'horizontal',titleIcon:'',
            children:[
           
             {   
			    type: 	'label',width: 	'100%',height: '100%',
			    style:	'font-size:20px;padding:5px;padding-top:8px;font-family:微软雅黑, 宋体, Verdana;font-weight:bold; ',
			    text: '中国运载火箭研究院知识服务平台-研发中心'
             },
             {
                 type: 'label', text: '您好, <%=SpringSecurityUtils.getCurrentUserName()%><a href="#" style="color:black;text-decoration:none;">退出</a>'
             }
            ]
        }
	);
	this.getTopBar = function(){
		return myIndexTopBar;
	}
}

//构建左侧的导航栏
function createIndexNavTree(){
	var myIndexNavTree = Edo.create(
	{
       type: 'ct',width: '180',height: '100%',
       padding: [0,0,0,0],border:[0,0,0,0],
       collapseProperty: 'width',
       enableCollapse: true,
       splitRegion: 'west',
       splitPlace: 'after',
       children: [
           {
               id: 'leftPanel',type: 'panel',title: '左侧面板',width: '100%',height: '100%',
               padding: [0,0,0,0],border:[0,0,0,0],
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
                           {id: 2, index: 'knowledgesearch', name: '知识检索'},
                           {id: 3, index: 'knowledgesearchindex', name: '搜索知识'}
                       ],
                       onselectionchange: function(e){		                                    
                           //var r = this.getSelected();
                           //if(r && r.url){
                               //mainTabBar.set('selectedIndex', 0);		                                        
                               //mainModule.load(r.url);
                           //}
                           
                           openNewTab(e.selected.id, e.selected.index,e.selected.name);
                       }
                   }
               ]
           }
       ]
   });
   
   this.getNavTree = function(){
   		return myIndexNavTree;
   }
}

//构建显示内容的主体界面
function createIndexContent(){
	var myIndexContent = Edo.create({
    	id:'mainPanel',
     	type: 'ct',
     	width: '100%',
     	height: '100%',
     	padding: [0,0,0,0],
     	border:[0,0,0,0],
     	verticalGap: 0,
        children:[
		{
			id:'mainTabBar',
			type: 'tabbar',
			selectedIndex: 0,
			border: [0,0,1,0],
			padding: [0,0,0,0],
			
			onselectionchange: function(e){								
				mainTabContent.set('selectedIndex', e.index);
			},
			children: [
				{index:0,type: 'button',text: '主页'}
			]
		},
		{
			id: 'mainTabContent',
			selectedIndex: 0,
			layout: 'viewstack',
			type: 'box',
			
			border: [0,1,1,1],
			padding: [0,0,0,0],
			width: '100%',
			height: '100%',
			verticalScrollPolicy: 'auto',
			onselectionchange: function(e){
				alert('content-selected');
			},
			children: [								    
			    {type:'label', text:'this is our main'}
			]
		}
	]
       });
       
       this.getContent = function(){
       	return myIndexContent;
       }
}
  	