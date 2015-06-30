function createSMLParamPool_view(){
	var smlTable=Edo.create({
		type:'table',width:'100%',height:'100%',autoColumns: true,
		columns:[
	         { header: '事物特性名称', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '事物特性代码', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '数据类型', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '最大值', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '最小值', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '隶属关系', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '单位', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '缺省值', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '描述', dataIndex: 'classname', headerAlign: 'center',align: 'center'}
		]
	});
	
	this.getSMLTable=function(){
		return smlTable;
	};
}