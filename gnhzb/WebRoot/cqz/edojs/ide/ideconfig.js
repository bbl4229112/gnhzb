var appViewId = 'app_view';
var selected;
function deselect(){
    if(selected){
        Edo.managers.ResizeManager.unreg(selected);    
        selected = null;
    }
}
 
function select(o, resizable){ 
    if(o.id == appViewId) resizable = false;           
    viewProperties.defer(50, null, [o]);
    
    if(selected == o) return;
    
    if(selected && selected != o){
        deselect();
    }
    selected = o;
    
    Edo.managers.ResizeManager.reg({
        target: o,
        square: true,
        transparent: false,
        resizable: resizable,
        handlers: ['n','s','w','e','ne','nw','se','sw'],
        onresizecomplete: function(e){
            select(o, resizable);
        }
    });    
    var row = design_control_tree.data.find({
        id: o.id
    });
    if(row){
        design_control_tree.select(row);
    }
}
var bd = document.body;
function findCmp(el, stop){    
    while(el && el != stop && el != bd){
        if(el.design) {
            //alert(Edo.getCmp(el.id));
            return window[el.id];
        }
        el = el.parentNode; 
    }
}
function onViewMouseDown(e){    
    
    deselect();
    var cmp = findCmp(e.target, this.el);    
    if(cmp) {     
        //select.defer(100, null, [cmp]);
        select(cmp);
        startDragDrop(e, cmp, cmp);
    }else{
        //select.defer(100, null, [app_view, false]);
        select(app_view);
    }        
}


function startDragDrop(e, dragData, dragObject){

    Edo.managers.DragManager.startDrag({
        event: e,
        dragObject: dragObject,
        delay: 200,
        dragData: dragData,
        alpha: 1,
//        xOffset: 15,
//        yOffset: 18,
        enableDrop: true,
        ondragstart: function(e){            

            this.proxy = Edo.util.Dom.append(document.body, '<div style="width:100px;height:20px;border:solid 1px black;"></div>');
            
            var w = h = 0;
            
            if(this.dragData.renderTo){
                w = this.dragObject.realWidth;
                h = this.dragObject.realHeight;               
                
                Edo.util.Dom.addClass(this.dragData.el, 'e-dragmove');
            }else{
                var cls = Edo.getType(this.dragData.type);
            
                w = cls.prototype.defaultWidth;
                h = cls.prototype.defaultHeight;
                
                this.xOffset = -5;
                this.yOffset = -5;
            }
            Edo.util.Dom.setSize(this.proxy, w, h);
            
            trace('dragstart'); 
        },        
        ondragcomplete: function(e){
            Edo.util.Dom.remove(this.proxy);
         
            if(this.dragData.el){
                
                Edo.util.Dom.removeClass(this.dragData.el, 'e-dragmove');
            }
            trace('dragcomplete'); 
            
            
            
            Edo.managers.DragProxy.hideInsertProxy();
        },
        ondropenter: function(e){
            
            if(this.dropObject.isType('ct')){
            
                if(this.dropObject != app_view && e.dragData.constructor.type){    //表明是已经常见的组件,则判断目标组件不是它的子组件
                    var p = this.dropObject;
                    while(p){
                        if(p != e.dragData){
                            p = p.parent;
                        }else{
                            return;
                        }
                    }
                }
                                 
                Edo.managers.DragManager.acceptDragDrop();                
            }
        },
        ondropover:function(e){        
         
            var cmp = this.dropObject;
            
        },
        ondropout: function(e){
            var cmp = this.dropObject;
            Edo.managers.DragProxy.hideInsertProxy();
        },
        ondropmove: function(e){
            this.dropIndex = -1;
        
            //根据dropObject的布局器,以及当前的坐标点,进行显示
            
            //如果是vertical或horizontal,则进行判断            
            var x = this.now[0], y = this.now[1];
            
            var box = this.dropObject.getBox(true);
            var boxs = [];
            
            var p1 = 'y', p2 = 'bottom', p3 = 'height', p = y;
            
            if(this.dropObject.layout == 'vertical'){
                //1)划分区块:根据children,drop box, layout, 
                var cs = this.dropObject.getDisplayChildren();
                
                
                var top = box.y;
                for(var i=0,l=cs.length; i<l; i++){
                    var c = cs[i];
                    var r = c.getBox(true);
                    
                    var line = r.bottom - r.height/2;
                    
                    boxs.add({top: top, bottom: line, index: i});
                    
                    top = line;                    
                }
                boxs.add({top: top, bottom: box.bottom, index: -1});
                
                //2)判断x,y在哪一个区块内,则...
                var targetIndex = -1;
                for(var i=0,l=boxs.length; i<l; i++){
                    var tb = boxs[i];                    
                    if(y >= tb.top && y <= tb.bottom){
                        targetIndex = tb.index;
                        
                        break;
                    }
                }
                
                this.dropIndex = targetIndex;
                
                //status =targetIndex+":"+boxs.length+":"+ new Date();
                var tc = this.dropObject.getChildAt(targetIndex);
                
                var proxy = Edo.managers.DragProxy.getInsertProxy();                          
                var pbox = {};
                if(tc){
                    var r = tc.getBox(true);
                    
                    pbox = {
                        width: r.width,
                        height: 1,
                        x: r.x,
                        y: r.y - this.dropObject.verticalGap/2
                    };
                }else{
                    //如果没有tc,则加入到本容器的最后一个
                    tc = this.dropObject.getChildAt(this.dropObject.numChildren()-1);
                    if(tc){
                        var r = tc.getBox(true);
                        pbox = {
                            width: r.width,
                            height: 1,
                            x: r.x,
                            y: r.bottom + this.dropObject.verticalGap/2
                        };
                    }else{
                        var layoutBox = this.dropObject.getLayoutBox();
                        pbox = {
                            width: layoutBox.width,
                            height: 1,
                            x: layoutBox.x,
                            y: layoutBox.y + this.dropObject.verticalGap/2
                        };
                    }
                }
                
                Edo.util.Dom.setBox(proxy, pbox);
                    
                
                
            }else if(this.dropObject.layout == 'horizontal'){
            
                var cs = this.dropObject.getDisplayChildren();
                
                
                var left = box.x;
                for(var i=0,l=cs.length; i<l; i++){
                    var c = cs[i];
                    var r = c.getBox(true);
                    
                    var line = r.right - r.width/2;
                    
                    boxs.add({left: left, right: line, index: i});
                    
                    left = line;                    
                }
                boxs.add({left: left, right: box.right, index: -1});
                
                //2)判断x,y在哪一个区块内,则...
                var targetIndex = -1;
                for(var i=0,l=boxs.length; i<l; i++){
                    var tb = boxs[i];                    
                    if(x >= tb.left && x <= tb.right){
                        targetIndex = tb.index;
                        
                        break;
                    }
                }
                
                this.dropIndex = targetIndex;
                
                //status =targetIndex+":"+boxs.length+":"+ new Date();
                var tc = this.dropObject.getChildAt(targetIndex);
                
                var proxy = Edo.managers.DragProxy.getInsertProxy('h');                          
                var pbox = {};
                if(tc){
                    var r = tc.getBox(true);
                    
                    pbox = {
                        width: 1,
                        height: r.height,
                        x: r.x - this.dropObject.horizontalGap/2,
                        y: r.y
                    };
                }else{
                    //如果没有tc,则加入到本容器的最后一个
                    tc = this.dropObject.getChildAt(this.dropObject.numChildren()-1);
                    if(tc){
                        var r = tc.getBox(true);
                        pbox = {
                            width: 1,
                            height: r.height,
                            x: r.right + this.dropObject.horizontalGap/2,
                            y: r.y
                        };
                    }else{
                        var layoutBox = this.dropObject.getLayoutBox();
                        pbox = {
                            width: 1,
                            height: layoutBox.height,
                            x: layoutBox.x + this.dropObject.horizontalGap/2,
                            y: layoutBox.y
                        };
                    }
                }
                
                Edo.util.Dom.setBox(proxy, pbox);                
            }
        },
        ondragdrop: function(e){
            //根据index,加入父容器内,或者调整!!!
            var p = e.dropObject;
            var xy = e.event.xy;
            
            //var idSet = true;
            if(!e.dragData.id) {
                //e.dragData.id = 'edodesign'+Edo.id(null,'$');
                e.dragData.id = createID(e.dragData.type);
                //idSet = false;
                
            }
            
            //如果是absolute布局,得到left,top值
            if(p.layout == 'absolute'){            
                //left,top是相对于scrollEl的偏移!
                var layoutBox = p.getLayoutBox();
                var xy = e.event.xy;
                
                e.dragData.left = xy[0] - layoutBox.x + this.xOffset;
                e.dragData.top = xy[1] - layoutBox.y + this.yOffset;
            }
            
            var o ;
            
            //e.dragData.layout = 'horizontal';///###测试代码
            
            if(p.layout == 'vertical' || p.layout == 'horizontal'){
                //var tc = this.dropObject.getChildAt(this.dropIndex);
                //var o = 
                o = p.addChildAt(this.dropIndex, e.dragData);
                
            }else{
                o = p.addChild(e.dragData);
            }
            
            refreshDesignTree();
            
            //选中组件
            if(p.layout != 'viewstack'){
                //if(o.id == appViewId) select.defer(10, null, [o, false]);
                //else select.defer(10, null, [o]);
                select.defer(10, null, [o]);
            }
            
        }
    });
}

//--------- properties
function filterProperties(className, data, edata){
    var cls = MapClasses[className];
    if(cls){
        className = cls.extend;
        var ps = cls.properties;
        if(ps){
            ps.each(function(o){
                data[data.length] = o.name;
            });
        }
        
        var es = cls.events;
        if(es){
            es.each(function(o){
                edata[edata.length] = o.name;
            });
        }
        if(className) filterProperties(className, data, edata);
    }
}

var showPropertiesCmp;
function viewProperties(cmp){    

    //if(!cmp) return;
  
    //1)解析出cmp的所有属性,和属性值,做成一个数组
    //2)将data设置给property table

    var data = [], edata = [];
    
    filterProperties(cmp.className, data, edata);            
    
    //切换布局属性:
    if(cmp.parent.layout != 'absolute'){
        data.remove('left');
        data.remove('right');
        data.remove('bottom');
        data.remove('top');
    }
    
    data.sort();
    edata.sort();
    
    
    var dd = [];
    var pp = {
        property: '属性',
        enableEdit: false,
        children: []
    };
    dd.add(pp);
    var pd = pp.children;
    
    data.each(function(p){
        pd[pd.length] = {property: p, value: cmp[p]};
    });    
    
    var ep = {
        property: '事件',
        enableEdit: false,
        children: []
    };
    dd.add(ep);
    var ed = ep.children;
    
    edata.each(function(p){
        ed[ed.length] = {property: p, value: ""};
    });    
    
    
    design_property.data.load(dd);
    
    var text = property_text.text;
    design_property.data.filter(function(o, i){
        if(o['property'].indexOf(text) == 0) return true;
    });
    
    showPropertiesCmp = cmp;
    
    //property_text.focus.defer(50, property_text);
}

function onItemUpdate(e){
    if(e.action == 'update'){        
    
        if(showPropertiesCmp){
            showPropertiesCmp.set(e.record['property'], e.value);
        }
    }
}

function trace(s){
    if(s){
        //design_trace.set('text', design_trace.text+s+"\n");
    }
}
//获得当前app_view下的组件树
function getDesignTree(p){
    var obj = {
        id: p.id,
        type: p.type,
        icon: 'ui-'+p.type
    }
    
    //if(p.isType('ct')){
    if(p.componentMode == 'container'){
        obj.expanded = true;
        obj.children = [];
        var cs = p.getChildren();
        cs.each(function(o){
            obj.children.add(getDesignTree(o));
        });
    }
    return obj;    
}


//从组件树获得组件描述字符串

function getPublics(className, obj){    
    var cls = MapClasses[className];
    if(cls){
        className = cls.extend;
        var ps = cls.properties;
        if(ps){
            ps.each(function(o){                
                obj[o.name] = 1;
            });
        }
        if(className) getPublics(className, obj);
    }   
}
function getDesignTreeCode(object){
    //1)获得对应类的所有公开属性
    
    var publics = {};
    getPublics(object.className, publics);    
    
    if(object.parent && object.parent.layout != 'absolute'){
        delete publics['left'];
        delete publics['right'];
        delete publics['bottom'];
        delete publics['top'];
    }
    
    var obj = {
        type: object.type
    };
    var prototype = Edo.getType(object.type).prototype;
    if(object.type != 'app'){        
        for(var p in object){
            if(p == 'style') continue;
            if(publics[p]){         //1)必须是公开属性
                //2)和默认设置不同(明确改变)
                var v = object[p];
                if(prototype[p] != v){ 
                    obj[p] = object[p];
                }
            }
        }     
    }else{
        obj.render = '#body';
    }
    if(obj.id && obj.id.indexOf('edodesign') == 0) delete obj.id;
     
    //if(object.isType('ct')){
    if(object.componentMode == 'container'){
        var cs = object.getChildren();
        if(cs.length != 0) obj.children = [];
        cs.each(function(o){
            obj.children.add(getDesignTreeCode(o));
        });
    }
    
    
    
    return obj;
}

function getCodeString(o){
    var tt = '    ';
    function getT(deep){
        var s = tt;
        for(var i=0; i<deep; i++){        
            s += tt;
        }
        return s;
    }
            
    function getCode(o, deep){
        var s = '';
        var t = getT(deep), t2 = t + tt;
        s += t + '{';
        
        var isfirst = true;
        for(var p in o){
            if(p == 'children') continue;
            if(isfirst){
                s += '\n' + t2 + p + ': "' + o[p] + '",';
            }else{
                s += ' ' + p + ': "' + o[p] + '",';
            }
            isfirst = false;
        }
        s = s.substring(0, s.length - 1);        
        
        if(o.children && o.children.length > 0){
            s += ',\n' + t2 + 'children : [';            
            var len = o.children.length-1;
            
            o.children.each(function(obj, i){
                s += '\n' + getCode(obj, deep+2);
                if(len != i) s += ',';                
                
            });
            
            s += '\n' + t2 + ']';
        }
        
        s += '\n' + t + '}';
        
        return s;
    }
    
    var s = getCode(o, 0);
    
    var script = '<script type="text/javascript">\n//todo:放置变量定义和函数定义\n'+'\n</script>\n';
    s = '<script id="edo-uiconfig" type="text/javascript">\n//todo:本标签内放置界面配置代码,不能进行变量和函数定义\nEdo.create(\n'+s+'\n);\n</script>';
    
    return script + s;
}

function refreshDesignTree(){
    var d = [getDesignTree(app_view)]    
    
    //    
    design_control_tree.data.filterFn = function(node){        
        if(node.componentMode == 'container') {
            node.__viewToggle = false;
            return 2;
        }            

    }
    design_control_tree.data.load(d);
    
    //design_control_tree.data.fileter(
}

//
function getCodeByCode(str){
    var scripts = str.match(/(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig) || [];
    
    var start = "Edo.create(", end = ");\n</script>";
    
    var code = scripts[1];
    var si = code.indexOf(start);
    
    //得到干净的json配置
    code = code.substring(si+start.length, code.length - end.length);
    
    //将on...事件处理函数,如果是纯函数名,转换为函数名字符串
    
    //alert(code);
    
}
//id生成器
var idMap = {};
function createID(type){
    var id = idMap[type];
    if(!id) id = 1;
    else id++;
    idMap[type] = id;
    
    var cmp = Edo.getCmp(type+id);
    if(cmp) return createID(type);
    return type+id;
}
 
//切换编辑器
var checkMap = {
    enable: 1,
    checked: 1,
    visible: 1,
    readOnly: 1,
    enableSelect: 1,    
    enableCellSelect: 1,
    enableCollapse: 1
    
};
var numberEditor = {
    type: 'spinner'
};
var scrollPolicyEditor = {
    type: 'combo',
    readOnly: true,
    data: [
        {label: 'auto', text: 'auto'},
        {label: 'on', text: 'on'},
        {label: 'off', text: 'off'}
    ]
};
var editMap = {
    'layout': {
        type: 'combo',
        readOnly: true,
        data: [
            {label: 'vertical', text: 'vertical'},
            {label: 'horizontal', text: 'horizontal'},
            {label: 'absolute', text: 'absolute'},
            {label: 'viewstack', text: 'viewstack'}
        ]
    },
    date: {
        type: 'date'        
    },
    defaultWidth: numberEditor,
    defaultHeight: numberEditor,
    minWidth: numberEditor,
    minHeight: numberEditor,
    maxWidth: numberEditor,
    maxHeight: numberEditor,
    popupWidth: numberEditor,
    popupHeight: numberEditor,
    /*
    left: numberEditor,
    top: numberEditor,
    right: numberEditor,
    bottom: numberEditor,*/
    
    verticalScrollPolicy: scrollPolicyEditor,
    horizontalScrollPolicy: scrollPolicyEditor,
    
    verticalAlign: {
        type: 'combo',
        readOnly: true,
        data: [
            {label: 'top', text: 'top'},
            {label: 'middle', text: 'middle'},
            {label: 'bottom', text: 'bottom'}
        ]
    },
    horizontalAlign: {
        type: 'combo',
        readOnly: true,
        data: [
            {label: 'left', text: 'left'},
            {label: 'center', text: 'center'},
            {label: 'right', text: 'right'}
        ]
    }
    
}
function getEditor(name){
    var editor = editMap[name];
    if(editor && !editor.renderTo){
        //editor.renderTo = document.body;
        editor = Edo.create(editor);
    }
    return editor;
}

function onbeforecelledit(e){
    
    
    //var row = e.source.data.getAt(e.row);
    var row = e.record;
    
    if(checkMap[row.property]){
        e.source.data.update(row, "value", !row.value);
        return false;
    }
    
    var editor = getEditor(row.property);
    
    
    
    if(editor) e.editor = editor;    
}

