Edo.Demo = function(){    
    Edo.Demo.superclass.constructor.call(this);    
}
Edo.Demo.extend(Edo.containers.Box, {
    width: '100%',
    height: '100%',
    verticalGap: 0,
    mainContent: '',
    load: function(url){
        if(!url) return;                
        this.mask();
        Edo.util.Ajax.request({
            url: url,
            type: 'get',            
            nocache: false,
            onSuccess: this.onLoad.bind(this)    
        });
    },
    onLoad: function(text){
        var tree = Edo.util.XmlToJson(text);
        tree = tree.root;
        
        this.componentMap = {};
        this.toDemoTree(tree, false);   
        
        tree.children.each(function(o){
            //o.expanded = true;
        });
        demoTree.data.load(tree.children);         
        
        this.fireEvent('demoready', {
            type: 'demoready',
            source: this
        });
        
        this.unmask();
    },
    toDemoTree: function(obj){        
        obj.children = [];
        if(obj.demo){
            if(!(obj.demo instanceof Array)) obj.demo = [obj.demo];
            obj.children.addRange(obj.demo);
            delete obj.demo;
        }
        if(obj.run){
            if(!(obj.run instanceof Array)) obj.run = [obj.run];
            obj.children.addRange(obj.run);
            delete obj.run;
        }
        if(obj.children && obj.children.length > 0){
            if(!Edo.isArray(obj.children)) obj.children = [obj.children];
            obj.children.each(function(o){
                this.toDemoTree(o);
            }, this);
            obj.icon = 'e-tree-folder';
        }else{
            obj.icon = 'e-tree-node';
        }
        if(obj['@expanded'] == "true"){
            obj.expanded = true;       
        }else{
            obj.expanded = false;       
        }      
        if(obj['@src']){
            obj.src = obj['@src'];
            var srcs = obj.src.split('/');
            var ids = srcs[srcs.length-1].split('.');
            obj.id = ids[0];
            //if(obj.name == 'Button') debugger
            this.componentMap[obj.id.toLowerCase()] = obj;
        }
        if(obj['@name']) obj.name = obj['@name'];
        obj.target = obj['@target'];
    },
    init: function(){    
        var demo = this;
        var tabItems = [
            {type: 'button', text: 'Overview', src: 'overview.html'}
        ];        
        
        this.set('children', [
            {
                type: 'ct', width: '100%', height: 35, layout: 'horizontal', verticalAlign: 'middle', style: 'background:#1e4176',
                children:[
                    {type: 'html',width: 400,height: 30,html: '<a href="http://www.edojs.com" target="_blank" style="FONT: 16px tahoma, arial, sans-serif;color: white;line-height:30px;padding-left:15px;text-decoration:none;">Edo Explorer Demo</a>'},
                    {type: 'space',width: '100%'},                    
                    {type: 'label', text: '切换皮肤', style: 'color:white;'},
                    {id: 'skinCombo', type: 'combo', data: this.skins,
                        selectedIndex: this.skinIndex,width: 100, readOnly: true,
                        onselectionchange: function(e){
                            if(!skinCombo.selectedItem) return;
                            var src = skinCombo.selectedItem.src;
                            changeSkin(src, document);
                            
                            var moduleViewer = Edo.get('moduleViewer');
                            if(moduleViewer && moduleViewer.childWindow){
                                try{
                                changeSkin(src, moduleViewer.childWindow.document);
                                }catch(e){}
                            }
                        }
                    },
                    {type: 'space',width: 5}
                ]
            },
            {
                type: 'box',width: '100%',height: '100%',
                children:[
                    
                    {
                        type: 'ct',width: '100%',height: '100%',layout: 'horizontal',enableSplit: true,
                        children:[
                            {
                                type: 'ct', width: 218, height: '100%',enableCollapse: true, splitRegion: 'west',splitPlace: 'after', collapseProperty: 'width',
                                children: [
                                    {
                                        title: '<b>Navigation</b>',type: 'panel',padding: [3,0,0,0],verticalGap: 3,width: '100%',height: '100%',                                        
                                        children: [
                                            {type: 'box',border:0,padding:[0,5,0,5],width: '100%',layout: 'horizontal',horizontalGap: 2,
                                                children: [                                            
                                                    {id: 'searchBtn',type: 'search',width: '100%', changeAction: 'keydown'},
                                                    {type: 'ct', cls: 'e-toolbar', layout: 'horizontal', style: 'background:white', horizontalGap: 0,
                                                        children: [
                                                            {id: 'collapseallBtn',type: 'button', icon: 'collapseall', tips: '收缩所有节点'},
                                                            {type: 'split'},
                                                            {id: 'expandallBtn',type: 'button', icon: 'expandall', tips: '展开所有节点'}
                                                        ]
                                                    }
                                                ]
                                            },{
                                                id: 'demoTree',type: 'tree',cls:'e-tree-allow',style: 'border-width:0px;border-top-width:1px;',width: '100%',height: '100%',autoColumns: true,horizontalLine: false,verticalLine: false,
                                                columns: [
                                                    {header: '', dataIndex: 'name',
                                                        renderer: function(v){
                                                            //key 查询关键字高亮
                                                            return v;
                                                        }
                                                    }
                                                ]
                                            }
                                        ]
                                    }                                    
                                ]
                            },
                            {
                                type: 'box',width: '100%',height: '100%',verticalGap: 0,padding: 0,border: 0,                                
                                children:[
                                    {
                                        type: 'tabbar',id: 'tbar',width: '100%',horizontalGap: 2,selectedIndex: 0,border:[0,0,0,0],
                                        children: tabItems                                        
                                    },
                                    {id: 'viewBox',type:'box',width: '100%',height:'100%', layout: 'viewstack',                                                                                                      
                                        children: [
                                            {
                                                id: 'moduleViewer', type: 'module',border: [1,0,1,1],layout: 'viewstack',style: 'background-color:white;border:0',minWidth: 300,width: '100%',height: '100%'
                                            },{
                                                type: 'ct', width: '100%',height:'100%',
                                                children:[
                                                    {type: 'ct', width: '100%',
                                                        children: [
                                                            {
                                                                type: 'button', text: '运行示例', id: 'runCode'
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        id: 'codeViewer',type: 'codeeditor', width: '100%', height: '100%', path: this.editorPath
                                                    }
                                                ]
                                            },{
                                                id: 'mainPager', type: 'html', width: '100%', height: '100%', html: this.mainContent
                                            }
                                        ]
                                    },
                                    {id: 'bottomBar', type:'ct',width:'100%',layout:'horizontal', id: 'bottomBar',
                                        children:[
                                            {
                                                id: 'viewTab',type: 'tabbar',position: 'bottom',border: [0,1,1,1],selectedIndex: 0,
                                                children: [
                                                    {type: 'button',text: 'Demo'},
                                                    {type: 'button',text: 'Source'}
                                                ],
                                                onselectionchange: function(e){                                                
                                                    viewDemo(this.selectedIndex, tbar.selectedItem.src, tbar.selectedItem.target);
                                                }
                                            }
                                        ]
                                    }
//                                    ,{
//                                        id: 'targetModule', type: 'module', width: '100%', height: '100%', visible: false
//                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]);            
        
        Edo.Demo.superclass.init.call(this);
        
        tbar.on('selectionchange', function(e){
            var index = e.source.selectedIndex;
            var item = e.source.getChildAt(index);
            if(viewTab.selectedIndex == 0){
                viewDemo(0, tbar.selectedItem.src,tbar.selectedItem.target);
            }else{
                viewTab.set('selectedIndex', 0);                
            }            
        });
        demoTree.on('selectionchange', function(e){
            var o = e.selected;
            demo.viewDemo(o.id);                                        
        });
        this.demoTree = demoTree;
        var demoObj = {};
        function viewDemo(type, src, target){
            if(!src) return;
            
//            if(target){
//                viewBox.set('visible', false);
//                bottomBar.set('visible', false);
//                targetModule.set('visible', true);
//                targetModule.set('src', src);
//                demo.demo = tbar.selectedItem;
//                demo.fireEvent('demochange', {
//                    type: 'demochange',
//                    source: demo,
//                    demo: tbar.selectedItem
//                });
//                return;
//            }else{
//                viewBox.set('visible', true);
//                bottomBar.set('visible', true);
//                targetModule.set('visible', false);
//            }
            
            viewBox.set('selectedIndex', type);
            if(type==0){
                //moduleViewer.load(src);
                moduleViewer.set('src', target || src);
                
                demo.demo = tbar.selectedItem;
                demo.fireEvent('demochange', {
                    type: 'demochange',
                    source: demo,
                    demo: tbar.selectedItem
                });
                        
            }else{
                if(demoObj.src == src) return;
            
                demoObj.src = src;
                Edo.util.Ajax.request({
                    url: src,
                    onSuccess: function(text){                    
                        demoObj.text = text;
                        codeViewer.set('text', text)
                    },onFail: function(code){
                    
                        code = String(code);
                        demoObj.text = code;
                        codeViewer.set('text', code)
                    }
                });
            }
        }        
        
        runCode.on('click', function(e){
            demo.runCode(codeViewer.get('text'));
        });
        
        var key = '';
        searchBtn.on('trigger', function(e){
            key = this.text.toLowerCase();       
            if(key == "") {
                if(this.clearVisible){
                    this.set('clearVisible', false);
                    demoTree.data.clearFilter();
                }
                return;
            }   
            
            this.set('clearVisible', true);
            
            demoTree.data.filter(function(node){            
                if(node.name.toLowerCase().indexOf(key) != -1 || (node.src && node.src.toLowerCase().indexOf(key) != -1)) return true;
                return false;
            });
            demoTree.data.expand(demoTree.data, true);
        });
        searchBtn.on('keydown', function(e){        
            if(e.keyCode == 13){            
                this.fireEvent('trigger');
            }
        });        
        searchBtn.on('cleartrigger', function(e){
            this.set('clearVisible', false);
            //this.set('text', '');
            
            key = '';
            demoTree.data.clearFilter();
        });
        collapseallBtn.on('click', function(e){
            demoTree.data.collapse(demoTree.data, true);
        });
        expandallBtn.on('click', function(e){
            demoTree.data.expand(demoTree.data, true);
        });
        
        function changeSkin(src, doc){
            var skinLink = doc.getElementById('edoskin');
            if(skinLink) skinLink.parentNode.removeChild(skinLink);//Edo.removeNode(skinLink);
            if(src){
                var link = doc.createElement('link');
                link.id = 'edoskin',
                link.href = src;
                link.rel = 'stylesheet';
                link.type = "text/css";
                doc.body.appendChild(link);                                
            }
        }
        
        moduleViewer.on('load', function(e){
            try{
                var doc = e.window.document;
                
                var src = skinCombo.selectedItem.src;
                                
                changeSkin(src, doc);
            }catch(e){
            }
        });
    },
    viewDemo: function(name){
        if(!name) return;
        
        var srcs = name.split('/');
        var ids = srcs[srcs.length-1].split('.');
        name = ids[0];
        
        
        id = name.toLowerCase();
        var run = this.componentMap[id];
        if(!run) return;
        name = run.name;
                
        this.demoTree.data.expand(run);
        
        var iid = id;
        var id = "_"+id;
        var bar = Edo.get(id);        
        if(!bar){
            var ns = name.split('.');
            bar = tbar.addChild({
                id: id,
                src: run.src,
                target: run.target,
                name: iid,
                type: 'button',
                arrowMode: 'close',
                text: name,
                icon: 'e-tree-node'
            });
        }
        tbar.set('selectedItem', bar);
        

    },
    getDemo: function(id){
        
        return this.componentMap[id.toLowerCase()];
    },
    showDemo: function(name){
        var srcs = name.split('/');
        var ids = srcs[srcs.length-1].split('.');
        name = ids[0];
        
        id = name.toLowerCase();
        var run = this.componentMap[id];
        if(!run) return;        
                
        this.demoTree.data.expand(run);
        
        this.demoTree.select(run);
    },
    runCode: function(code) {
	    var winname = window.open('', "_blank", '');
	    winname.document.open('text/html', 'replace');
	    winname.document.write(code);
	    winname.document.close();
    }
});
Edo.Demo.regType('demo');    