function createPlatformStruct_view(){
	var panel = Edo.create({
			type: 'panel', id:'', title:'<h3><font color="blue">查看产品主结构</font></h3>', padding: [0,0,0,0],
			width:'100%', height:'100%', layout:'vertical',verticalGap: 0,
	    	children:[
				{
        		    type: 'group',
        		    width: '100%',
				    layout: 'horizontal',
				    cls: 'e-toolbar',
				    children: [
		               	{type:'label',text:'请选择建立结构的产品平台:'},
					    {	type:'combo', id:'',
							width:'150',
							Text :'请先选择适合的分类',
							readOnly : true,
							valueField: 'id',
						    displayField: 'text',			    
						    onselectionchange: function(e){}
					}]     
			}] 
	});
	
	this.getPanel =function(){
		return panel;
	};
}






