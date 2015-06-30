function createSMLPoolManage_view(){
	var smlPoolTable=Edo.create({
		type:'table',width:'100%',height:'100%',autoColumns: true,
		columns:[
	         { header: 'SMLID', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '事物特性名称', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '事物特性代码', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '数据类型', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '隶属关系', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '最大值', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '最小值', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '单位', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '创建人', dataIndex: 'classname', headerAlign: 'center',align: 'center'}
		]
	});
	
	this.getSMLPoolTable=function(){
		return smlPoolTable;
	};
}