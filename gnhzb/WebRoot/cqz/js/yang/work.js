Edo.build({
    type: 'app',
    render: document.body,
    width: '100%',
    height: '100%',
    border:[0,0,0,0],
    layout: 'horizontal',
    children:[{
    	type:'ct',
    	width: '80%',
    	height: '100%',
    	children:[{
            id: 'panel1',
            type: 'panel',
            //width: '100%',
            height: 100,
            layout: 'horizontal',
            title:'模块化设计',
            enableCollapse: true,   //1)设置为可伸缩               
            splitRegion: 'north',   //2)设置调节方向
            splitPlace: 'after',     //3)split占位符在本组件的位置
            children:[
                      {type:'button',text:'任务一',onclick:function(e){module.set('src','cqz/js/module/CodeClassRuleManage.jsp');}},
                      {type:'button',text:'任务二',onclick:function(e){module.set('src','cqz/js/yang/show.jsp');}},
                      {type:'button',text:'任务三',onclick:function(e){module.set('src','cqz/js/module/CodeClassRuleManage.jsp');}},
                      {type:'button',text:'任务四',onclick:function(e){module.set('src','cqz/js/module/CodeClassRuleManage.jsp');}},
                      ]	
        },
        {
            type: 'module',
            id:'module',
            width: '100%',
            height: '100%'
        },]},
        {
            type: 'box',
            width: '20%',
            height: 200,
            enableCollapse: true,
            splitRegion: 'south',
            splitPlace: 'before',
            //layout: 'horizontal',
            children:[{
                     type: 'panel',
                     id: 'knowledge',
                     title:'知识推荐',
                     width:'100%',
                     height: '100%',
                     children:[{
                    	 type:'search',
                    	 
                     }]
            },           
            {
            	type:'button',
            	align: 'center',
            	text:'上一步',
            	width: '80',
                height: '50',
            },
            {
            	type:'button',
            	text:'下一步',
            	width: '80',
                height: '50',
                onclick:function(e){
                	//var i=0;
                	//a[0]='module/codeclassdefi.jsp';
                	//a[1]='CodeClassRuleManage.jsp';
                	module.set('src','cqz/js/module/codeclassdefi.jsp');
            	},
            }
                      ]
        }
    ]
});