function openNewTab(e){
  var idx  = e.index+1;
  var c = Edo.get("tbar_"+idx);
  if(c==null){
    c = mainTabBar.addChildAt(idx,
      {id:'tbar_'+idx,type: 'button',text: '测试-'+idx,arrowMode: 'close',
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
    module.load('module'+idx+'.htm');
    
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
                type: 'ct',width: '100%',height: '40', layout: 'horizontal',padding:[0,0,0,0],
                children:[
                  {
              type:   'label',width:   '100%',height: '100%',
              style:  'font-size:20px;padding:5px;padding-top:8px;font-family:微软雅黑, 宋体, Verdana;font-weight:bold; ',
              text: '汽车1生命周期分析模板'
                  }
                ]
            },
            //工具栏
            
            //主界面
            {
                type: 'ct',
                width: '100%',
                height: '100%',
                layout: 'horizontal',
                padding:[0,0,0,0],
                children:[
                  //左侧边
                  {
                    type: 'ct',width: '180',height: '100%',
                    collapseProperty: 'width',
                    enableCollapse: true,
                    splitRegion: 'west',
                    splitPlace: 'after',
                    padding:[0,0,0,0],
                    children: [
                        {
                            id: 'leftPanel',type: 'panel',title: '汽车1LCA分析导航',width: '100%',height: '100%',padding:[0,0,0,0],
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
                                        {id: 1,  name: '汽车1LCA模板', icon: 'e-tree-folder',
                                            children: [
                                                {id: 3, url: 'cqz/js/yang/qiche/pro1.jsp', name: '模板信息'},
                                                {id: 4, url: 'cqz/js/yang/qiche/pro2.jsp?moduleid='+moduleid, name: '生命周期流程图'},
                                                {id: 4, url: 'cqz/js/yang/qiche/pro3.jsp', name: '清单数据收集'},
                                                {id: 4, url: 'cqz/js/yang/qiche/pro4.jsp', name: '影响评价'},
                                                {id: 4, url: 'cqz/js/yang/qiche/pro5.jsp', name: '结果解释'},
                                                {id: 4, url: 'cqz/js/yang/qiche/pro6.jsp', name: '模型打分'}
                                            ]
                                        }
                                    ],
                                    onselectionchange: function(e){                                        
                                        var r = this.getSelected();
                                        if(r && r.url){
                                            mainTabBar.set('selectedIndex', 0);                                            
                                            mainModule.load(r.url);
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                },
                //右主界面
                  {
                    id:'mainPanel',type: 'ct',width: '100%',height: '100%',verticalGap: 0,padding:[0,0,0,0],
                    children:[
			              {
			                id:'mainTabBar',type: 'tabbar',selectedIndex: 0,border: [0,0,1,0],padding:[0,0,0,0],
			                onselectionchange: function(e){                
			                  mainTabContent.set('selectedIndex', e.index);
			                },
			                children: [
			                  {index:0,type: 'button',text: '汽车1生命周期评价模型'}
			                ]
			              },
			              {
			                id: 'mainTabContent',selectedIndex: 0,layout: 'viewstack',padding:[0,0,0,0],type: 'box',border: [1,1,1,1],width: '100%',height: '100%',verticalScrollPolicy: 'auto',
			                onselectionchange: function(e){
			                  alert('content-selected');
			                },
			                children: [                    
			                    {id: 'mainModule',type:"module",width: '100%',height: '100%',padding:[0,0,0,0],
			                    }
			                ]
			              }
              ]
                }//end 右主界面
                ]
        }
        ]
    });      
});