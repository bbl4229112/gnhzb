function createDesignTaskDeclare(){
	var component = Edo.create({   type:'box', width: '100%', height: '100%' , 
		 children:[
		           {
			           type:'box', width: '100%', height: '100%' , layout:'vertical', border:0,
			        	  children:[
			        	            {
			        	            type:'group', layout:'horizontal', width: '100%',/*padding:0,*/
			        	            children:[
											{
												type: 'formitem',label: '任务名称:', 														
												children: [
												    {
												        type: 'text'
												    }
												]
											},
											{
												type: 'formitem',label: '作者:', 														
												children: [
												    {
												        type: 'text'
												    }
												]
											},
											{
												type: 'formitem',label: '时间:', 													
												children: [
												    {
												        type: 'text'
												    }
												]
											},
											{
												type: 'formitem',label: '编号:', 														
												children: [
												    {
												        type: 'text'
												    }
												]
											},
											{
												type: 'formitem',label: '备注:', 															
												children: [
												    {
												        type: 'text'
												    }
												]
											}]
			        	            },
			        	            {
										type: 'formitem',label: '1.功能', 														
										children: [
										    {
										        type: 'text'
										    }
										 ]
									},
									{ type:'text', text:'解释说明:', width:'100%',height:200 },
									{ 
										type:'group',width:'100%', layout:'horizontal',horizontalAlign:  'right' ,
										children:[
											    { type:'button', text:'增加', },
												{ type:'button', text:'删除', }
									    ]
									}
			        	   ]
		           },					          
		 ]
	 });
	
	
	this.getComponent = function(){
		return component;
	};
}