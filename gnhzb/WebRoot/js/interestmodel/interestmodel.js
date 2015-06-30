/**
知识订阅输入的界面
*/
function createKnowledgeSubscribe(){

var ks = Edo.build({
    type: 'box', verticalGap: 0,
    // renderTo: '#body',        
    children:[
        {
            type: 'tabbar', selectedIndex: 0,
            onselectionchange: function(e){                    
                ct2.set('selectedIndex', this.selectedIndex);
            },
            children: [{text: '关键词定制', type: 'button'},{text: '按上传者定制', type: 'button'}
            ,{text: '领域树节点定制', type: 'button'},{text: '多维分类树节点定制', type: 'button'}]
        },
        {
            id: 'ct2', type: 'box', border: [1,0,1,1], layout: 'viewstack', 
            style: 'background-color:white;', width: '100%',
            children: [
            //关键词定制
                {	
                	type: 'module',
					width: 1000,
		    		height: 600,
		    		render: document.body,	
					src: 'interestmodel/keywordcustomize.action' 

                },
                
              //按上传者定制
                {	
                	type: 'module',
					width: 1000,
		    		height: 600,
		    		render: document.body,	
					src: 'interestmodel/authorcustomize.action' 
                  
                },
                  //按领域树节点定制
                {
                    type: 'module',
					width: 1000,
		    		height: 600,
		    		render: document.body,	
					src: 'interestmodel/domaincustomize.action' 
                },
                  //按多维分类树节点定制
                  {
                     type: 'module',
					width: 1000,
		    		height: 600,
		    		render: document.body,	
					src: 'interestmodel/categorycustomize.action' 
					 
    				
                }
                
            ]
        }
    ]
});

this.getKS = function() {
	return ks;
}


}


