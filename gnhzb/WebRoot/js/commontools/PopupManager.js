/**
    @name Edo.managers.PopupManager
    @class 
    @single
    @description 弹出定位管理器   
    @example 


*/    
Edo.managers.PopupManager1 = {
    zindex: 9100,
    popups: {},
    /**        
        @name Edo.managers.PopupManager1#createPopup
        @function 
        @description 弹出显示组件
        @param {Object} config 弹出显示的配置对象
<pre>        
{
    target: 需要弹出的组件对象
    x:  坐标
    y:
    modal:  是否遮罩背景
}                    
</pre>                
    */        
    createPopup: function(cfg){
        var cmp = cfg.target;
        if(!cmp) return false;
        var bd = Edo.getBody();
        
        Edo.applyIf(cfg, {
            x: 'center',
            y: 'middle',
            //shadow: true,
            modal: true,
            modalCt: bd,
            onout: Edo.emptyFn,
            onin: Edo.emptyFn,
            onmousedown: Edo.emptyFn
        });                    
        
        var x = cfg.x, y = cfg.y, modalCt = cfg.modalCt;

        if(Edo.isValue(cfg.width)) cmp._setWidth(cfg.width);
        if(Edo.isValue(cfg.height)) cmp._setHeight(cfg.height);
        
        var zIndex = this.zindex++;
        cmp._setStyle('z-index:'+zIndex+';position:absolute;');
        cfg.zIndex = zIndex;
                
        var boxCt = Edo.util.Dom.getBox(modalCt);
        var sizeCt = Edo.util.Dom.getViewSize(document);
        boxCt.width = sizeCt.width;
        boxCt.height = sizeCt.height;
        
        
        if(!cmp.layouted){
            cmp.doLayout();
        }
        var boxCmp = cmp._getBox();
        
        if((!x && x !== 0) || x == 'center'){           
            x = (boxCt.x + boxCt.width/2) - boxCmp.width/2
        }
        if((!y && y !== 0) || y == 'middle'){
            y = (boxCt.y + boxCt.height/2) - boxCmp.height/2
        }
        //调节下坐标定位(如果是边缘等)
        cmp.set('visible', true);
        cmp._setXY([x, y]);
        
        cmp.left = parseInt(cmp.el.style.left);
        cmp.top = parseInt(cmp.el.style.top);
        
        if(isOpera) cmp._setXY(x, y);
        
        if(cfg.modal){
        	createMask();
            //if(cims201.utils.getExploreVersion().ie == '6.0'){
            //	createMask();	
            //}else{
            //	Edo.util.Dom.mask(modalCt);
            //	if(modalCt._mask) modalCt._mask.style.zIndex = zIndex-1;
            //}                       
        }else{
        	deleteMask();
            //this.unmask(cmp, modalCt);
        }
        
        //cmp._setStyle('z-index:'+(parseInt(modalCt._mask.style.zIndex)+1)+';');
        
        this.popups[cmp.id] = cfg;
        
        if(cfg.focus){
            cmp.focus.defer(30, cmp);
        }                        
        
        setTimeout(function(){
        Edo.util.Dom.repaint(cmp.el);   
        }, 10);
        
        //对tab键的特殊控制处理                
    },
    /**        
        @name Edo.managers.PopupManager1#removePopup
        @function 
        @description 隐藏弹出显示组件
        @param {UIComponent} 组件对象                
    */      
    removePopup: function(cmp){
    
        var o = this.popups[cmp.id];
        if(!o) return;
        cmp._setX(-3000);
        cmp.blur();
        //cmp.set('visible', false);
        
        //cmp.set.defer(1, cmp, ['visible', false]);
        if(o.modal) {
            //遍历popups,判断是否有一样的modalCt,如果有,则不unmask
            //unmask.defer(100, null, [cmp, cmp.modalCt]);
            deleteMask();
            //if(cims201.utils.getExploreVersion().ie == '6.0'){
            //	deleteMask();
            //}else{
            //	this.unmask(cmp, o.modalCt);
            //}                                
        }
        delete cmp.modalCt;
        delete this.popups[cmp.id];
    },
    unmask: function(cmp, mct){
        var repeat = false;
        var zindex = -1;
        for(var id in this.popups){
            if(id == cmp.id) continue;
            var pop = this.popups[id];
            if(pop.modalCt === mct && pop.modal){
                repeat = true;
                zindex = pop.zIndex;
                break;
            }
        }
        if(repeat){
            if(mct._mask) mct._mask.style.zIndex = zindex-1;
        }
        else Edo.util.Dom.unmask(mct);
    }
};
Edo.util.Dom.on(document, 'mousedown', function(e){
    var popups = Edo.managers.PopupManager1.popups;
    
    for(var id in popups){
        var popup = popups[id];               
        popup.onmousedown(e);
        if(!popup.target.within(e)){
            popup.onout(e);
        }else{
            popup.onin(e);
        }
    }
});