function getstepone(){
	var taskdefine = Edo.build(
		
		    {type: 'box',
	    	width: 1100,
	    	height:'100%',
	    	border: [0,0,0,0],
	    	padding: [0,0,0,0],
	    	layout: 'vertical',
	    	verticalGap:'0',
	    	/*verticalAlign:'middle',
	    	horizontalAlign:'center',*/
	    	//style: 'background:white;',
	   	    children: [
				{type: 'panel',title:'步骤说明',width: '100%',height:200,layout: 'horizontal',border: [0,0,0,0],padding: [0,0,0,0],
						children:[     
				   	        {type:'textarea',
			   	        	width : 300,
			   	        	height:200,
			   	            style:  'font-size:20px;font-family:verdana;font-weight:bold;border:0;background:rgba(0,0,0,0);',
			   	        	text:'请定义该过程节点的基本信息'
				   	        }]
				},      
	       	    /*{type : 'formitem',
	       	    label : '模板名称:',
	       	    padding:[5,0,0,0],
	       	    labelStyle:'font-size:20px;font-family:SimSun;border:0;',
	       	    labelWidth : 100,
	       	    labelHeight : 30,
	       	    children : [
	                {type : 'text',width : 200,height:30,id : 'taskname'}]
	       	    },
	   	    	{type : 'formitem',
	       	    label : '流程备注:',
	       	    padding:[5,0,0,0],
	       	    labelWidth : 100,
	       	    labelHeight : 30,
	       	    labelStyle:'font-size:20px;font-family:SimSun;border:0;',
	       	    children : [
	                {type : 'text',width : 200,height:30,id : 'tasknote'}]
	       	    },*/
				{type: 'panel',title:'定义过程基本信息',width: '100%',height:300,layout: 'vertical',border: [0,0,1,0],padding: [10,0,0,10],
					children:[
						{
						    type: 'formitem',label: '过程名称:', labelWidth : 100,
						    children:[{type: 'text', id: 'processname',text:levelmodule.levelmoduleobject.processname,width:150}]
						},
						{
						    type: 'formitem',label: '过程说明:', labelWidth : 100,
						    children:[{type: 'textarea', id: 'processnote',text:levelmodule.levelmoduleobject.processnote,width:150,
						    	height:40}]
						}
					          ]
				},
	       	    {
       			type:'box',layout:'horizontal',width:'100%',padding:[10,0,0,0],border: [0,0,0,0],
       			children:[
       		        {type: 'button',text: '下一步',width:80,height: 30,style:'margin-left:220px;',onclick: function(e){
       		        	var processname=Edo.get('processname').text;
       		        	var processnote=Edo.get('processnote').text;
	       		     	levelmodule.levelmoduleobject.processname=processname;
	       		        levelmodule.levelmoduleobject.processnote=processnote;
	       		        removeselected();
       		        	//aa.editcell(cell,processname);
       			       	openNewTab(stepdata.source[1]);
       		        	}}
	       			]
	       			}
	       	    
	       	    
	       	    ]
	       	}
	       	
		);
	return taskdefine;
	}

