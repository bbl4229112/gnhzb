function toggle(e){

    var panel = this.parent.owner;
    var accordion = panel.parent;
    accordion.getChildren().each(function(child){
        if(panel != child) child.collapse();
    });
    panel.toggle();
}

function onPanelClick(e){
    if(e.within(this.headerCt)){
        var panel = this;
        var accordion = panel.parent;
        accordion.getChildren().each(function(child){
            if(panel != child) child.collapse();
        });
        panel.toggle();
    }
}
function onselectionchange(e){
    if(!e.selected) return;
    
    alert(e.selected.url);
    //这里找到module.set('url', url);
}
function openModule(src){
    alert(src);
}

//打开新的选项卡
function openNewTab(r){
	
  var id = r.id;
  var idx  = mainTabBar.children.length;
  var c = Edo.get("tbar_"+id);
  if(c==null){
    c = mainTabBar.addChildAt(idx,
      {id:'tbar_'+id,type: 'button',text:r.text,arrowMode: 'close',
          onarrowclick:function(e){
          //根据idx, 删除对应的容器
          var c = Edo.get('cont_'+id);          
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
        id:'cont_'+id,type:"module",width: '100%',height: '100%',style: 'border:0'
      }
    );    
    module.load('cqz/js/module/'+r.id+'.jsp');
    
  };
  mainTabBar.set('selectedItem', c);
    
};

Edo.util.Dom.on(window, 'domload', 
function(e){    
    Edo.build(
    {
        type: 'app',render: document.body,width: '100%',height: '100%',layout: 'vertical',
        children:[
          //顶栏描述
          {
                type: 'ct',width: '100%',height: '40', layout: 'horizontal',
                children:[
                  {
		              type:   'label',width:'100%',height: '100%',
		              style:  'font-size:20px;padding:5px;padding-top:8px;font-family:微软雅黑, 宋体, Verdana;font-weight:bold; ',
		              text: '机电产品模块化设计平台 V1.0'
                  },
                  {
                      type: 'label', text: '您好, admin <a href="#" style="color:black;text-decoration:none;">退出</a>'
                  }
                ]
            },
            //主界面
            {
                type: 'ct',
                width: '100%',
                height: '100%',
                layout: 'horizontal',
                children:[
                  //左侧边
                  {	
                  	id:'accordin',
                    type: 'panel',
                    title: '导航列表',
                    width: '260',
                    height: '100%',
                    border:[0,0,0,0],
				    padding:[3,0,0,3],
                    collapseProperty: 'width',
                    enableCollapse: true,
                    splitRegion: 'west',
                    splitPlace: 'after',
                    layout:'vertical',
                    verticalGap: 0,                      //去除竖直方向间隙
                    children: [
                        {
				            type: 'panel',
				            width: '100%',
				            height: '100%',              
				            title: '编码管理系统',
				            border:[0,0,0,0],
				            padding:[0,0,0,0],
				            enableCollapse: true,
				            onclick: onPanelClick,                                           
				            titlebar:[{
				                cls:'e-titlebar-accordion',
				                onclick: toggle
				            }],
				            children:[
				            	{
				            		id: 'bmgl', type: 'tree', width: '100%',height: '100%',
								    headerVisible: false,  
								    autoColumns: true,       
								    horizontalLine: false,  
								    columns: [   
								        {dataIndex: 'text'}
								    ],
							  		onselectionchange: function(e){  
                                        var r = e.selected;
                                        //console.log(r);
                                        //console.log(mainTabBar.children.length);
                                        if(r){
                                        	openNewTab(r);
                                           //mainTabBar.set('selectedIndex', mainTabBar.children.length);                                            
                                           //mainModule.load('cqz/js/module/'+r.id+'.jsp');
                                        }
                                    }
				            	}				            
				            ]
				        },
				        {
				            type: 'panel',
				            width: '100%',height: '100%',
				            enableCollapse: true,
				            expanded: false,    
				            onclick: onPanelClick,   
				            title: 'accordion2',
				            titlebar:[{
				                cls:'e-titlebar-accordion',
				                onclick: toggle
				            }],
				            children: [
				                {type: 'dialog'}
				            ]
				        },
				        {
				            type: 'panel',
				            width: '100%',height: '100%',
				            title: 'accordion3',
				            enableCollapse: true,
				            expanded: false,
				            onclick: onPanelClick,
				            titlebar:[{
				                cls:'e-titlebar-accordion',
				                onclick: toggle
				            }],
				            children: [
				                {type: 'spinner'}
				            ]
				        }
                    ]
                },
                //右主界面
                {
                    id:'mainPanel',type: 'ct',width: '100%',height: '100%',verticalGap: 0,
                    children:[
		              	{
		                	id:'mainTabBar',type: 'tabbar',selectedIndex: 0,border: [0,0,1,0],padding: [0,0,0,0],
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
			                    {id: 'mainModule',type:"module",width: '100%',height: '100%',style: 'border:0'}
			                ]
		              	}
		            ]
                }//end 右主界面
              ]
        	}
        ]
    });   
    //编码管理系统数据
    Edo.util.Ajax.request({
	        type: 'get',        
	        url: 'cclass/codeclass.action',
	        onSuccess: function(text){
	            var data = Edo.util.Json.decode(text);
	            bmgl.set('data', data);
	        }
	});    

});


