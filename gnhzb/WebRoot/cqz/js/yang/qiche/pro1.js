//创建用户面板
Edo.create(
		{
			type: 'app',render: document.body,width: '100%',height: '100%',layout: 'horizontal',padding:[0,0,0,0],
			children:[
				{
			
				    id: 'userForm',    
				    type: 'box',width:'100%',height:'100%',padding:[10,0,0,10],border: [0,0,0,0],/*horizontalAlign:'center',verticalAlign:'middle',*/
				    children: [
				        {
				            type: 'formitem',label: '产品名称',
				            children:[{type: 'text', id: 'name', text:'汽车1',width:480}]
				        },
				        {
				            type: 'formitem',label: '产品ID',
				            children:[{type: 'text', id: 'ID', text:'Q1',width:480}]
				        },
				        {
				            type: 'formitem',label: '产品关联ID',
				            children:[{type: 'text', id: 'LID', text:'J1、F1',width:480}]
				        },
				        {
				            type: 'formitem',label: '产品功能',
				            children:[{type: 'text', id: 'function', text:'运输',width:480}]
				        },
				        {
				            type: 'formitem',label: '产品厂家',
				            children:[{type: 'text', id: 'company', text:'杭汽轮',width:480}]
				        },
				        {
				            type: 'formitem',label: '功能单元',
				            children:[{type: 'text', id: 'unit', text:'1台',width:480}]
				        },
				        {
				            type: 'formitem',label: '数据提供者',
				            children:[{type: 'text', id: 'provider', text:'张安三',width:480}]
				        },
				        {
				            type: 'formitem',label: '数据审核者',
				            children:[{type: 'text', id: 'checker', text:'张安三',width:480}]
				        },
				        {
				            type: 'formitem',label: '开展日期:',
				            children:[{type: 'date', id: 'date', text:'2014-10-10',width:480}]
				        },
				        {
				            type: 'formitem',label: '国家:',
				            children:[
				                {type: 'combo', id: 'country',readOnly: true, displayField: 'name', valueField: 'id', data: Countrys,text:'中国',width:480}
				            ]
				        },
				        {
				            type: 'formitem',label: '模板评分',
				            children:[{type: 'text', id: 'grade', text:'A',width:480}]
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
				}]
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
