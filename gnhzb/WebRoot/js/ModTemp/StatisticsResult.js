function createStatisticsResult(){
	var component = Edo.create({
		type: 'box', width: '100%', height: '100%', border:[0,0,0,0],
		layout:'vertical', verticalGap:0,
		children:[
		          {
						type:'box',width: '100%',height: '300',cls: 'e-toolbar',
						layout:'horizontal',
						children:[
							{	type:'label',text:'零件种类:' },
							{	type:'combo', id:'ClassNameCombo',
								width:'150', displayField: 'label', valueField: 'id',
								data :[
								            { label:'叶片' },
								            { label:'部件' },
								            { label:'零件' },
								            { label:'外购' },
								            { label:'备用' }
								],					
							},
							{type:'space'},
							{	type:'label', text:'图形选择:' },
							{	type:'combo', id:'ClassNameCombo1',
								width:'150', displayField: 'label', valueField: 'id',
								data :[
								            { label:'直方图' },
								            { label:'饼图' },					            
								],					
							},	
							{   type:'space' },
							{	type:'label', text:'排序方式:' },
							{	type:'combo', id:'ClassNameCombo2',
								width:'150', displayField: 'label', valueField: 'id',
								data :[
								            { label:'降序' },
								            { label:'升序' },					            
								],					
							},	
							{   type:'space' },
							{	type:'label', text:'划分类型:' },
							{	type:'combo', id:'ClassNameCombo3',
								width:'150', displayField: 'label', valueField: 'id',
								data :[
								            { label:'A' },
								            { label:'B' },	
								            { label:'C' },
								],					
							},			
							]
		          },
		          /*{
		        	  type:'box',width: '100%',height: '100',cls: 'e-toolbar',
		          },*/
		          {
		        	  type:'box',width: '100%',height: '100',cls: 'e-toolbar',
		        	  children:[
		        	            { type:'button',text:'实例'}
		        	   ]
		          }	          
		  ]
	});
	
	this.getComponent = function(){
		return component;
	};
}