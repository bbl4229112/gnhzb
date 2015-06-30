Edo.IDE = function(){    
    Edo.IDE.superclass.constructor.call(this);
}
Edo.IDE.extend(Edo.containers.Box, {    
    editorPath: '',
    load: function(url){
        
    },
    init: function(){
        this.setChildren([
            {
                type: 'box',layout:'horizontal', width: '100%', //cls: 'e-toolbar',
                children: [
                    {type: 'button',enable: false,text: '新建'},
                    {type: 'button',enable: false,text: '打开'},
                    {type: 'button',enable: false,text: '保存'},
                    {type: 'button',enable: false,text: '新增'},
                    {type: 'button',text: '删除',
                        onclick: function(e){
                            if(selected && selected != app_view){
                                selected.destroy();
                                refreshDesignTree();
                            }
                        }
                    },
                    {type: 'button',text: '运行',
                        onclick: function(e){
                            var o = getDesignTreeCode(app_view);                            
                            //window.codeString = getCodeString(o);
                            window.open('template.htm#'+ Edo.util.Json.encode(o));
                        }
                    },
                    {type: 'split'},
                    {type: 'button',text: 'edoIDE教学视频!',
                        onclick: function(e){
                            window.open('http://bbs.edojs.com/viewthread.php?tid=134&extra=page%3D1');
                        }
                    },
                    {type: 'space',width: '100%'},
                    //{type: 'html',width: 80,height: 25, html: '<a href="http://www.edojs.com" target="_blank"><img src="res/edojs.gif" width="100%" height="100%"/></a>'},
                    {type: 'label', text: '切换皮肤', style: 'color:white;'},
                    {type: 'combo', data: this.skins,
                        selectedIndex: this.skinIndex,width: 100, readOnly: true,
                        onselectionchange: function(e){
                            var skinLink = document.getElementById('edoskin');
                            if(skinLink) Edo.removeNode(skinLink);
                            var src = e.selectedItem.src;
                            
                            if(src){
                                var link = document.createElement('link');
                                link.id = 'edoskin',
                                link.href = src;
                                link.rel = 'stylesheet';
                                link.type = "text/css";
                                document.body.appendChild(link);                                                            
                            }
                        }
                    }
                ]
            },
            {
                type: 'ct',width:'100%',height:'100%', layout: 'horizontal',
                children:[
                    {
                        type: 'panel', titleIcon: 'ui-tools',title: '组件工具箱', width: 180,height:'100%',padding:0,                        
                        splitRegion: 'west',splitPlace: 'after', collapseProperty: 'width',enableCollapse: true,
                        titlebar:[
                            {
                                cls: 'e-titlebar-toggle-west',
                                onclick: function(e){                                
                                    this.parent.owner.toggle();
                                }
                            }
                        ],
                        children: [
                            {
                                id: 'design-pv-tree', type: 'tree', cls: 'e-tree-allow',width:'100%',height:'100%',headerVisible: false,autoExpandColumn: 'tool',style: 'background-color:White;border-width:0;cursor:default;',
                                horizontalLine: false, verticalLine: false,
                                columns: [
                                    {id: 'tool', header: '组件工具箱', dataIndex: 'name', width: 154}
                                ],
                                data: new Edo.data.DataTree(UIComponents),
                                onitemmousedown: function(e){                                    
                                
                                    var item = e.item;
                                    if(item && item.type) {     
                                        startDragDrop(e, {
                                                type: item.type,
                                                design: true,
                                                className: item.className
                                            }, this);
                                    }
                                }                                            
                            }
                        ]
                        
                    },{
                        type: 'ct',width: '100%',height: '100%',verticalGap: 0,                        
                        children: [
                            {
                                type: 'ct', id: 'app_container',width: '100%',height: '100%',layout: 'viewstack',                                
                                children:[
                                    {
                                        type: 'app',className: 'edo.containers.Application',id: 'app_view',designview: true,bodyStyle: 'background-color:white;',design: true,border: [1,1,0,1],padding: 10,width: '100%',height: '100%',minHeight: 10,minWidth: 10,verticalScrollPolicy: 'auto',horizontalScrollPolicy: 'auto',
                                        style: 'background:#dfdfdf;',
                                        onmousedown: onViewMouseDown,
                                        oncreation: function(e){
                                            Edo.managers.DragManager.regDrop(this);
                                        }
                                    },{
                                        type: 'codeeditor',id: 'app_code',width: '100%',height: '100%',style: 'border-bottom:0',path: this.editorPath
                                    }
                                ]
                            },                            
                            {
                                type: 'tabbar',position: 'bottom',selectedIndex: 0,
                                onselectionchange: function(e){
                                    var index = e.source.selectedIndex;
                                    app_container.set('selectedIndex', index);     
                                    if(index == 0){ //code -> view
                                        var code = app_code.text;
                                        if(code){
                                            getCodeByCode(code);
                                        }
                                        
                                    }else{          //view -> code
                                        var o = getDesignTreeCode(app_view);
                                        var s = getCodeString(o);
                                        app_code.set('text', s);
                                        
                                    }
                                },
                                children: [
                                    {text: 'view',type: 'button'},
                                    {text: 'code',type: 'button'}
                                ]
                            }
                        ]
                            
                    },
                    {
                        type: 'panel',titleIcon: 'ui-tree',title: '组件树',width: 200,padding: 0,height: '100%',enableCollapse: true, collapseProperty: 'width',
                        splitRegion: 'east',splitPlace: 'before', 
                        titlebar:[
                            {
                                cls: 'e-titlebar-toggle-east',
                                onclick: function(e){                                
                                    this.parent.owner.toggle();
                                }
                            }
                        ],
                        children: [
                            {
                                type: 'tree',id: 'design_control_tree',headerVisible: false,horizontalLine: false,style: 'border-width:0;border-bottom-width:1px;',width: '100%',autoColumns: true,height: '50%',
                                columns: [
                                    {headerText: 'type', dataIndex: 'type'}
                                ],
                                onselectionchange: function(e){
                                    if(e.selected){
                                        var id = e.selected.id;
                                        select(Edo.getCmp(id));                                            
                                    }
                                }
                            },{
                                type: 'panel',
                                border: [1,0,0,0],
                                padding: 0,
                                title: '组件属性',
                                width: '100%',
                                height: '50%',
                                children: [
                                    {
                                        type: 'box',
                                        layout: 'horizontal',
                                        width: '100%',
                                        border: 0,
                                        children:[
                                            {
                                                type: 'label',
                                                text: '快速查找:'
                                            },
                                            {
                                                type: 'text',
                                                id: 'property_text',
                                                width: '100%',
                                                changeAction: 'keyup',
                                                onTextChange: function(e){
                                                    var text = e.text;
                                                    design_property.data.filter(function(o, i){                                                        
                                                        if(o['property'].toLowerCase().indexOf(text.toLowerCase()) == 0) return true;
                                                        else return false;
                                                    });                                                    
                                                }
                                            }      
                                        ]
                                    },
                                    {
                                        type: 'tree',id: 'design_property',headerVisible: false,editAction: 'click',data: new Edo.data.DataTree([]),
                                        oncreation: function(e){                                        
                                            this.data.on('datachange', onItemUpdate);
                                        },
                                        style: 'border-width:0;border-top-width:1px;',
                                        width: '100%',
                                        height: '100%',
                                        //autoExpandColumn: 'value',
                                        autoColumns: true,
                                        columns: [
                                            {header: 'Property', dataIndex: 'property', width: 100, forId: 'value'},
                                            {id:'value',header: 'Value', dataIndex: 'value', width: 70,
                                                //cls: 'e-checkbox',
                                                renderer: function(v){
                                                    if(v === true || v === false){
                                                        v = Edo.toBool(v);
                                                        return '<div class="e-tree-checkbox" style="text-align:center;line-height:0px;height:18px;"><div class="e-tree-check-icon  '+(v ? "e-table-checked" : "")+'" style="position:static;width:15px;height:18px;"></div></div>';
                                                    }else{
                                                        return v;
                                                    }
                                                },
                                                editor:{
                                                    type: 'text'
                                                }
                                            }
                                        ],
                                        onbeforecelledit: onbeforecelledit,
                                        onkeydown: function(e){                                        
                                            switch(e.keyCode){
                                            case 13:                                            
                                                //this.submitEdit();
                                            break;
                                            }
                                        }
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]);
    
        Edo.IDE.superclass.init.call(this);
    }
});
Edo.IDE.regType('ide');

