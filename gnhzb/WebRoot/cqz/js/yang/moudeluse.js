Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	render: document.body,
	layout:'absolute',
	children:[{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'500',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'配置设计系统'}]
	},{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'800',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'变型设计系统'}]
	},
	
	]
});