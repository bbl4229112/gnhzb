var qiche1Data = new Edo.data.DataTable().set('data', Qiche1);
var positionData = new Edo.data.DataTree(Positions);

    Edo.create({
        type: 'box',
        render: document.body,
        layout: 'horizontal',
        children: [
            {
            	id:'qiche1',
                type: 'button',
                text: '零部件查看',
                onclick: function(e){
                    var r = table1.getSelected();
                    if(r){
                    	//??
                    }else{
                        alert("请选择行");
                    }
                }                
                
            } ,
            {
                type: 'button',
                text: '零部件排序',
                onclick: function(e){
                    table1.data.sort(function(pre, next){                        
                        return !!(pre.power > next.power);
                    }, this);  
                }                
                
            },
            {
                type: 'button',
                text: '进入生命周期分析模板',
                onclick:'window.location.href="cqz/js/yang/qiche/qiche1_1.jsp"'
            }
        ]        
    });
    Edo.create(
        {
            id: 'table1',
            type: 'table',
            width: 600,
            height: 200,
            editAction: 'click',
            render: document.body,
            data: qiche1Data,
            columns:[
                Edo.lists.Table.createMultiColumn(),
                {header: '零部件名称', dataIndex: 'part'},        
                {header: '版本信息', dataIndex: 'version', editor: 'text'},                        
                {header: '创建日期', dataIndex: 'date', type: 'text'},
                {header: '其他', dataIndex: 'other', type: 'text'},
                {header: '模板信息', dataIndex: 'versioninfo', type: 'text',width:180,},
            ]
        }
    );
