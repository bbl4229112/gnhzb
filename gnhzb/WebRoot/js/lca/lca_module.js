
function createnewmodule(){
	var flowmodule= Edo.create({
		type: 'module',
		width: '100%',
		height:'100%',
		src:'lca/welcome!getdraw.action'
	});
	this.getnewmodule = function(){
		return flowmodule;
	};
}
function showmodule(){
	var data= cims201.utils.getData('lca/welcome!getModuletree.action');
	var box=Edo.build({
	    type: 'box',
	    width: '100%',
		height:'100%',
	    layout: 'horizontal',
	    children:[
	        {                
	            type: 'ct',
	            width: 180,
	            height: '100%',
	            collapseProperty: 'width',
	            enableCollapse: true,
	            splitRegion: 'west',
	            splitPlace: 'after',
	            children: [
	                {
                	    type: 'tree',
	   			        width: '100%',
	   			        height: '100%',
	   			        autoColumns:true,
	   			        headerVisible: false,
	   			        verticalLine:false,
	   			        horizontalLine:false,
	   			        id: 'moduletree',
	   			        enabelCellSelect: false,
				        autoColumns: true,
				        enableDragDrop: true,
				        showHeader: false,
				        onbodymousedown: function(e){
				        	var r = this.getSelected();
				        	Edo.get('moduleview').set('src', 'lca/welcome!getmodulelook.action?moduleid='+r.id);
				        	Edo.get('modulename').set('text', r.Name);
				        	Edo.get('createuserid').set('text', r.Createuserid);
				        	Edo.get('date').set('text', r.Createdate);
				        	Edo.get('note').set('text', r.Note);
				        	Edo.get('version').set('text', r.Version);
				        },
				        columns:[
				            {   
				                enableDragDrop: true,
				                dataIndex: "Name"
				            
	                        }
				            ],
	                    data:data
				           
	            }
	                ]
	        },
	        {
	    	    type: 'box',
	    	    width: '100%',
	    		height:'100%',
	    	    layout: 'vertical',
	    	    children:[
	    	       
	    	                {
	    	                    type: 'ct',
	    	                    id:'modulecontent',
	    	                    width: '100%',
	    	                    height: 150,
	    	                    enableCollapse: true,   //1)设置为可伸缩               
	    	                    splitRegion: 'north',   //2)设置调节方向
	    	                    splitPlace: 'after' ,
	    	                    children:[
	    	                              {
	    	                  	    	    type: 'box',
	    	                  	    	    width: '100%',
	    	                  	    		height:'33%',
	    	                  	    	    layout: 'horizontal',
	    	                  	    	    children:[
    	                  	    	            {	type : 'formitem',label : '模型名称:',labelWidth : 100,labelAlign : 'center',
    							               	    children : [{type : 'text',width : 200,id : 'modulename'}]
    							               	},
		    					               	{	type : 'formitem',label : '模型创建人:',labelWidth : 100,labelAlign : 'center',
		    						               	    children : [{type : 'text',width : 200,id : 'createuserid'}]
		    						               	    }
	    	                  	    	              ]
	    	                              },
	    	                              {
		    	                  	    	    type: 'box',
		    	                  	    	    width: '100%',
		    	                  	    		height:'33%',
		    	                  	    	    layout: 'horizontal',
		    	                  	    	    children:[
												{	type : 'formitem',label : '创建时间:',labelWidth : 100,labelAlign : 'center',
													    children : [{type : 'text',width : 200,id : 'date'}]
													        } ,
												   {	type : 'formitem',label : '版本号:',labelWidth : 100,labelAlign : 'center',
												   	    children : [{type : 'text',width : 200,id : 'version'}]
												   	    } ,       
		    	                  	    	              ]
		    	                              },
	    	                              {
		    	                  	    	    type: 'box',
		    	                  	    	    width: '100%',
		    	                  	    		height:'33%',
		    	                  	    	    layout: 'horizontal',
		    	                  	    	    children:[
												{	type : 'formitem',label : '模型评分:',labelWidth : 100,labelAlign : 'center',
													    children : [{type : 'text',width : 200,id : 'score'}]
													    } ,
												{	type : 'formitem',label : '备注:',labelWidth : 100,labelAlign : 'center',
													    children : [{type : 'text',width : 200,id : 'note'}]
													    } 
		    	                  	    	              ]
		    	                              }
	    	                               
	    	                               ]
	    	                    	
	    	                    
	    	                }, 
	    	                {
			    	        	type: 'module',
			    	        	id:'moduleview',
			    	    		width: '100%',
			    	    		height:'100%'
	    	        }
	    	        
	    	       
	    	    ]
	    	}
	       
	    ]
	}); 
	this.getoldmodule = function(){
		return box;
	};
}
