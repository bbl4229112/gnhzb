function createSMLCodePool_view(){
	var smlCodeTable=Edo.create({
		type:'table',width:'100%',height:'100%',autoColumns: true,
		columns:[
	         { header: '分类码', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '名称', dataIndex: 'classname', headerAlign: 'center',align: 'center'},
	         { header: '描述', dataIndex: 'classname', headerAlign: 'center',align: 'center'}
		]
	});
	
	this.getSMLCodeTable=function(){
		return smlCodeTable;
	};
}