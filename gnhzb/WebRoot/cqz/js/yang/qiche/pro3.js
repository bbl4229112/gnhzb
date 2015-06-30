//创建用户面板
Edo.create({
    id: 'userForm',    
    type: 'panel',title: '汽车1清单数据收集',width:600,height:400,
    render: document.body,
    children: [
        {
            type: 'formitem',label: '输入',
            children:[{type: 'text', id: 'name', text:'1Kg钢铁'}]
        },
        {
            type: 'formitem',label: '输出',
            children:[{type: 'text', id: 'company', text:'1KgCO2'}]
        },
        {
            type: 'formitem',label: '数据来源',
            children:[{type: 'text', id: 'people1', text:'生产车间'}]
        },
        {
            type: 'formitem',label: '数据提供者',
            children:[{type: 'text', id: 'people2', text:'张安阿三'}]
        },
        {
            type: 'formitem',label: '数据打分',
            children:[{type: 'text', id: 'grade', text:'A'}]
        },
        {
            type: 'formitem',label: '创建日期:',
            children:[{type: 'date', id: 'date', text:'2014-10-10'}]
        },
        {
            type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
            children:[
                {id: 'submitBtn', type: 'button', text: '确定'},
                {type: 'space', width: 5},
                {type: 'label', text: '<a href="javascript:resetForm();">重置</a>'}
            ]
        }
    ]
});
    
//事件监听
submitBtn.on('click', function(e){
    
    //验证表单
    if(userForm.valid()){
        var o = userForm.getForm();    //获取表单值
        var json = Edo.util.Json.encode(o);
        alert(json);    //可以用ajax发送到服务端
    }
});
function resetForm(){
    userForm.reset();
}
