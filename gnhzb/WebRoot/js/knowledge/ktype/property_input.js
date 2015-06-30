
function propertyinput(callback){
//创建用户面板
var pinput=Edo.create({
       
    type: 'box',
   // title: '知识属性输入',
 //   render: document.body,
 	width: 220,
	height: 220,
   
    children: [
        {
            type: 'formitem',label: '属性英文名<span style="color:red;">*</span>:',labelWidth : 83,
            children:[{type: 'text', id: 'name',width : 120,valid:cims201.form.validate.isKtypeProperytEnglishName}]
        },
        {
            type: 'formitem',label: '中文名描述<span style="color:red;">*</span>:',labelWidth : 83,
            children:[{type: 'text', id: 'description',width : 120,valid:cims201.form.validate.noEmpty}]
        },       
        {
            type: 'formitem',label: '是否可为空<span style="color:red;">*</span>:',labelWidth : 83,
            children:[{
		            id: 'isNotNull',
		            type: 'combo', 
		            width : 120,
		            valid: cims201.form.validate.noEmpty,
		            readOnly: true,              
		            displayField: 'text',
		            valueField: 'value',
		            data: [
		                {   text: '是' ,value: false},
		                {   text: '否' ,value: true}
		            ]
		            
		        }
            ]
        },
        
         {
            type: 'formitem',label: '是否可见<span style="color:red;">*</span>:',labelWidth : 83,
            children:[{
		            id: 'isVisible',
		            type: 'combo', 
		            width : 120,
		            valid: cims201.form.validate.noEmpty,
		            readOnly: true,              
		            displayField: 'text',
		            valueField: 'value',
		            data: [
		                {   text: '是' ,value: true},
		                {   text: '否' ,value: false}
		            ]
		            
		        }
            ]
        },
              
        {
            type: 'formitem',label: '字段类型<span style="color:red;">*</span>:',labelWidth : 83,
            children:[{
		            id: 'propertyType',
		            type: 'combo',    
		            width : 120, 
		            valid: cims201.form.validate.noEmpty,
		            readOnly: true,       
		            displayField: 'text',
		            valueField: 'value',
		            data: [
		                {   text: '字符' ,value: 'java.lang.String' },
		                {   text: '整型' ,value: 'java.lang.Integer' },
		                {   text: '长整型',value: 'java.lang.Long' },
		                {   text: '浮点' ,value: 'java.lang.Float' },
		                {   text: '布尔' ,value: 'java.lang.Boolean' },
		                {   text: '日期' ,value: 'java.util.Date' }
		            ],
		            onSelectionChange : function(e) {
						if(this.selectedItem){
							var	combovalue = this.selectedItem.value;
							//alert(combovalue.value);
							if(combovalue=="java.lang.String"){
							Edo.get('length1').set('readOnly',false);
							}
							if(combovalue=="java.lang.Long"){
							Edo.get('length1').set('readOnly',true);
							Edo.get('length1').setValue('19');
							}
							if(combovalue=="java.lang.Boolean"){
							Edo.get('length1').set('readOnly',true);
							Edo.get('length1').setValue('1');
							}
								if(combovalue=="java.lang.Integer"){
							Edo.get('length1').set('readOnly',true);
							Edo.get('length1').setValue('10');
							}
							if(combovalue=="java.lang.Float"){
								Edo.get('length1').set('readOnly',true);
								Edo.get('length1').setValue('19');
								}
								if(combovalue=="java.util.Date"){
									Edo.get('length1').set('readOnly',true);
									Edo.get('length1').setValue('6');
									}
						}
					}
	            
	        }
            ]
        }
        ,
        {
            type: 'formitem',label: '字符长度<span style="color:red;">*</span>:',labelWidth : 83,
            children:[{id: 'length1',  type: 'text',width: 120,valid: cims201.form.validate.isNumber,readOnly:false}]
        },
        {
            type: 'formitem',layout:'horizontal', labelWidth : 70,padding: [8,0,8, 0],
            children:[
                {id: 'submitBtn', type: 'button', text: '提交'},
                {id: 'reset', type: 'button', text: '重置'}
            ]
        }
    ]
});


 //事件监听
Edo.get('submitBtn').on('click', function(e){
 
    if(pinput.valid()){
    	 
        var o = pinput.getForm();    //获取表单值        
        o['length'] = o['length1'];
        var json = Edo.util.Json.encode(o);
   //     alert(json);    //可以用ajax发送到服务端
              	    
	   var result= cims201.utils.getData('property!save.action',{json:json});
       alert(result); 
	   callback();
        }
        
    
});

Edo.get('reset').on('click', function(e){
    
pinput.reset();    
    
});  


this.getPropertyinput = function(){
	return pinput;
}	



}