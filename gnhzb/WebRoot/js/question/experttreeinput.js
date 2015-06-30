/**
专家领域信息输入的界面
*/
function createExpertTreenode(){

var einput = Edo.build({
    type: 'box', verticalGap: 0,        
    children:[
        {
            type: 'tabbar', selectedIndex: 0,
            onselectionchange: function(e){                    
                etict2.set('selectedIndex', this.selectedIndex);
            },
            children: [{text: '领域树专家', type: 'button'},{text: '多维分类树专家', type: 'button'}]
        },
        {
            id: 'etict2', type: 'box', border: [0,0,0,0], layout: 'viewstack', 
            style: 'background-color:white;', width: '100%',
            children: [            
                  //按领域树节点定制
                { 
					type: 'module',
					width: 800,
		    		height: 650,		    		
		    		render: document.body,		    		
					src: 'question/dtreeexpertinput.action' 
		
                      
    				
                },
                  //按多维分类树节点定制
                  {
                    type: 'module',
					width: 800,
		    		height: 650,
		    		render: document.body,	
					src: 'question/ctreeexpertinput.action' 
                    
					 
    				
                }
                
            ]
        }
    ]
});


this.getEinput=function(){
	return einput;
}

}


