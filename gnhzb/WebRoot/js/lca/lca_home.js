function createLcaHome(){
	
	var mainbox = Edo.create({
		type:'module',
		width: '100%',
		height: 700,	
		border:[0,0,0,0],
		padding:[0,0,0,0],
		//render: document.body,
		verticalScrollPolicy: 'off',
		src: 'knowledge/ui!lcahome.action' 
			
	});
	
	this.getLcaHome = function(){
		return mainbox;
	};
}