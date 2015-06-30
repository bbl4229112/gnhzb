function nodemanage(){
	
	var mainbox= Edo.create({
		type: 'module',
		width: '100%',
		height:'100%',
		src:'lca/welcome!nodemanage.action'
	});
	
	this.getnodemanage = function(){
		return mainbox;
	};
}