Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	render: document.body,
	layout:'absolute',
	children:[{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'300',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'结构模块管理'}]
	},{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'550',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'编码管理系统'}]
	},{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'800',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'模块管理系统'}]
	},
	{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'1050',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'模块化平台管理'}]
	},
	]
});