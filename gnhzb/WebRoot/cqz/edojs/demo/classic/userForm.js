//表单验证器函数
function noEmpty(v){
    if(v == "") return "不能为空";
}
function mustPassword(v){
    if(v != '12345') return "密码必须是12345";
}
function dateLimit(v){
    if(!v) return "必须选择日期";
    if(v < new Date(2020, 1,1)) return "日期不能小于2020年2月1号";
}
function showUserForm(callback){
    if(!Edo.get('userForm')) {
        //创建用户面板
        Edo.create({
            id: 'userForm',            
            type: 'window',title: '用户录入',
            render: document.body,
            titlebar: [
                {
                    cls: 'e-titlebar-close',
                    onclick: function(e){
                        this.parent.owner.hide();
                    }
                }
            ],
            children: [
                {
                    type: 'formitem',label: '账号<span style="color:red;">*</span>:',
                    children:[{type: 'text', name: 'loginname', valid: noEmpty}]
                },
                {
                    type: 'formitem',label: '密码<span style="color:red;">*</span>:',
                    children:[{type: 'password', name: 'password', valid: mustPassword}]
                },
                {
                    type: 'formitem',label: '性别<span style="color:red;">*</span>:',
                    children:[
                        {type: 'radiogroup', name: 'gender',repeatDirection: 'horizontal',displayField: 'name',checkField: 'checked',valueField: 'id',data: Genders}
                    ]
                },
                {
                    type: 'formitem',label: '生日:',
                    children:[{type: 'date', name: 'birthday', valid: dateLimit, required: false}]
                },
                {
                    type: 'formitem',label: '年龄:', layout: 'horizontal',
                    children:[
                        {type: 'slider', name: 'age', minValue: 0, maxValue: 120,
                            onvaluechange: function(e){
                                agetext.set('text', e.value);
                            }
                        },
                        {id: 'agetext',type: 'label'}
                    ]
                },
                {
                    type: 'formitem',label: '身高:',
                    children:[{type: 'spinner', name: 'tall', minValue: 30, maxValue: 250}]
                },
                {
                    type: 'formitem',label: '国家:',
                    children:[
                        {type: 'combo', name: 'country',readOnly: true, displayField: 'name', valueField: 'id', data: Countrys}
                    ]
                },
                {
                    type: 'formitem',label: '爱好:',
                    children:[
                        {type: 'checkgroup', name: 'interest',repeatDirection: 'horizontal',repeatItems: 3,displayField: 'name',checkField: 'checked',valueField: 'id',data: Interests}
                    ]
                },
                {
                    type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
                    children:[
                        {name: 'submitBtn', type: 'button', text: '提交表单', 
                            onclick: function(){
                                if(userForm.valid()){
                                    var o = userForm.getForm();
                                    if(userForm.callback) userForm.callback(o);
                                    userForm.hide();
                                }
                            }
                        }
                    ]
                }
            ]
        });
    }
    userForm.callback = callback;
    userForm.show('center', 'middle', true);
    return userForm;
}