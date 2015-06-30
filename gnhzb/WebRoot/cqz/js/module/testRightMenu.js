Edo.build({
 	id:'mm',
    type: 'window',        
    children: [
        {
            	type: 'menu',                
                width: '100%',
                height:'100%',
                children:
                    [
                        {
                            type: 'button',
                            text: '菜单项1'
                        },{
                            type: 'button',
                            icon: 'e-tree-folder',
                            text: '菜单项2'
                        }
                    ]
                    
                    }
                    ]
    
});
Edo.build({
    id: 'ct',
    width: 400,height:200,
    title: 'Panel',
    titleIcon: 'e-tree-folder',
    type: 'panel',
    enableCollapse: true,  
    titlebar:[
        {
        cls:'e-titlebar-accordion',
        onclick: function(e){
            this.parent.owner.toggle();
        }
    },
        {
        cls:'e-titlebar-close',
        onclick: function(e){
            this.parent.owner.toggle();
        }
    },{
        cls:'e-titlebar-toggle',
        onclick: function(e){
            this.parent.owner.toggle();
        }
    },{
        cls:'e-titlebar-min',
        onclick: function(e){                        
            this.parent.owner.toggle();
        }
    },{
        cls:'e-titlebar-max',
        onclick: function(e){                        
            this.parent.owner.toggle();
        }
    },{
        cls:'e-titlebar-resume',
        onclick: function(e){                        
            this.parent.owner.toggle();
        }
    },{
        cls:'e-titlebar-refresh',
        onclick: function(e){                        
            this.parent.owner.toggle();
        }
    },
        {
            cls:'e-titlebar-toggle',
            icon: 'button',
            onclick: function(e){
                this.parent.owner.toggle();
            }
        }
    ],
    //layout: 'horizontal',
    children: [
        {
            type: 'button',
            text: '按钮'
        },
        {
            type: 'text',
            text: '文本框'
        }
    ],
    render: document.body
});
/*Edo.get(document.body).on("contextmenu",function(e){
	console.log(e);
	
});*/
/*Edo.util.Dom.on( window ,"contextmenu",function(e){
	console.log(e.rawEvent);
	e.rawEvent.preventDefault();
	//e.defaultPrevented=true;
	//e.preventDefault();
	//e.stopEvent();
});*/
ct.on("contextmenu",function(e){
	e.rawEvent.preventDefault();
	console.log(e.x);
	mm.show(e.x,e.y,true);
	//console.log(e);
	//e.preventDefault();
	//e.stopEvent(); 
});