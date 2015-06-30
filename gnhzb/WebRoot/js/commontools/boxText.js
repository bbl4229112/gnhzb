/**
    @name Edo.controls.boxtexxt
    @class
    @typeName boxtext
    @description 文本显示框,能自动换行,因为edo自带的text文本框只能单行显示，不能够自动换行
    @extend Edo.controls.Control
*/
Edo.controls.BoxText = function(){

    Edo.controls.BoxText.superclass.constructor.call(this);
};
Edo.controls.BoxText.extend(Edo.controls.Control,{
    elCls: 'e-boxtext',
    /**
        @name Edo.controls.Label#autoWidth
        @property
        @default true
    */
    autoWidth: true,
    /**
        @name Edo.controls.Label#autoHeight
        @property
        @default true
    */
    autoHeight: true,
    /**
        @name Edo.controls.Label#minWidth
        @property
        @default 20
    */
    minWidth: 100,
        

    /**
        @name Edo.controls.Label#text
        @property
        @type String
        @description 文本
    */
    text: '',    
    /**
        @name Edo.controls.Label#forId
        @property    
        @type {String}
        @description 目标组件ID
    */
    forId: '',
    
//    sizeSet: false,
//    widthGeted: false,
      
    getInnerHtml: function(sb){
        sb[sb.length] = this.text;
    },
    _setText: function(value){
    	//this.width = 700;
        if(this.text !== value){
            this.text = value;
            if(this.el){
                this.el.innerHTML = value;
                //this.el.style.height = 'auto';
                //this.el.style.width = 'auto';
            }
            if(!Edo.isInt(this.width)){
                this.widthGeted = false;                
//                this.el.style.width = 'auto';
//                Edo.util.Dom.repaint(this.el);
            }
            if(!Edo.isInt(this.height)){
                this.heightGeted = false;
            }
            this.changeProperty('text', value);
            this.relayout('text', value);
        }
    }     
});

Edo.controls.BoxText.regType('boxtext');