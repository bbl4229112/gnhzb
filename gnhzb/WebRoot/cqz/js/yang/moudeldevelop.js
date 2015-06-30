Edo.build({
	type: 'app',width: '100%',height: '100%',border:[0,0,0,0],
	render: document.body,
	layout:'absolute',
	children:[{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'150',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'产品需求管理',}]
	},{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'400',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'功能原理和功能模块'}]
	},{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'650',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'零部件ABC分析'}]
	},
	{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'900',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'原理方案及总体结构'}]
	},
	{
		type:'box',
		horizontalAlign:'center',
		verticalAlign:'middle',
		left:'1150',
		top:'300',
		width: '200',height: '200',	
		children:[{type:'button',text:'产品功能系列化和模块化'}]
	},]
});