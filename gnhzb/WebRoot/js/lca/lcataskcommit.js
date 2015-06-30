var stepcount=0;
	/*var data=[{id: 'stepone', url: 'module2.htm',index:0, func:getstepone,name: '1.流程定义'},
	          {id: 'steptwo', url: 'module3.htm',index:1,func:getsteptwo, name: '2.输入设定'},
	          {id: 'stepthree', url: 'module3.htm',index:2,func:getstepthree,name: '3.输出设定'},
	          {id: 'stepfour', url: 'module3.htm',index:3,func:getstepfour,name: '4.相关知识上传'},
	          {id: 'stepfive', url: 'module3.htm',index:4,func:getstepfive,name: '5.评价指标设定'}
	          ];*/
	var data=[{id: 'stepone', url: 'module2.htm',index:0, func:getstepone,name: '1.基本信息定义'},
	          {id: 'steptwo', url: 'module3.htm',index:1,func:getsteptwo, name: '2.输入设定'},
	          {id: 'stepthree', url: 'module3.htm',index:2,func:getstepthree,name: '3.输出设定'},
	          {id: 'stepfour', url: 'module3.htm',index:3,func:getstepfour,name: '4.相关知识上传'},
	          {id: 'stepfive', url: 'module3.htm',index:4,func:getstepfive,name: '5.评价指标设定'},
	          {id: 'stepsix', url: 'module3.htm',index:4,func:getstepsix,name: '6.查看结果'}
	          ];

var stepdata = new Edo.data.DataTable().set('data', data);
function getprocessdetaildefine(){
	Edo.build(
			{
				id:'processmain',
				type: 'app',
				width: '100%',
				layout: 'vertical',
				render: document.getElementById('detaildiv'),	
				style:'background:#B5E3E0',
				padding:[0,0,0,0],
				children:[
	                      //顶栏描述
				          {
				        	  type: 'ct',
				        	  width: '100%',
				        	  height: 30, 
				        	  layout: 'horizontal',	
				        	  padding:[0,0,0,0],
				        	  children:[
				        	            {
				        	              type:   'label',width:200,height: '100%',
				      		              style:  'font-size:16px;font-family:微软雅黑, 宋体, Verdana;padding-left:10px;padding-top:10px;font-weight:bold; ',
				      		              text: '过程信息定义'
				        	            },
				        	            {
				        	            	type:'space',width:900,height: '100%',	
				        	            },
				        	            {
				        	            	type: 'label', text: '退出编辑', style:  'font-size:12px;cursor:pointer;padding-top:10px;font-weight:bold; ',onclick: function(e){
			        	            		if(!cell.isedit){
			        	            			levelmodule.levelmoduleobject.processname=null;
			        		       		        levelmodule.levelmoduleobject.processnote=null;
			        		       		        levelmodule.levelmoduleobject.knowledge=null;
			        		       		        levelmodule.levelmoduleobject.inputmaterial=null;
			        		       		        levelmodule.levelmoduleobject.outputmaterial=null;
			        		       		        levelmodule.levelmoduleobject.evaluationmethod=null;
			        	            		}
				        	            	var c=document.getElementById('detaildiv');
								        	c.style.width='0px';
								        	resetm2();
								        	removeselected();
								        	}
				        	            }
				        	            ]
				          },
				         
				          //主界面
				          {
				        	  type: 'ct',
				              width: '100%',
				              height: '100%',
				              layout: 'horizontal',
				              padding:0,
				              children:[
	                                    //左侧边
				                        {
				                        	id:'processleftPanel',
				                        	type: 'panel',
				                            title: '导航列表',
				                            width: 150,
				                            height: '100%',
				                            verticalGap:'0',
				        				    padding:[0,0,0,0],
				                            collapseProperty: 'width',
				                            enableCollapse: true,
				                            splitRegion: 'west',
				                            splitPlace: 'after',
				                            layout:'vertical',
				                            titlebar:[
				                                      {
				                                          cls:'e-titlebar-toggle-west',
				                                          icon: 'button',
				                                          onclick: function(e){this.parent.owner.toggle();}
				                                      }
				                                      ],
				                            children:[
		                                   
			                                      {
		                                    	  type: 'table', 
		                                    	  id:'steptable',
			                                      autoColumns: true, 
			                                      headerVisible: false,
			                                      width: '100%', height: '100%',
			                                      style:'border:0;',
			                                      verticalLine: false, 
			                                      horizontalLine: false, 
			                                      rowHeight: 25,
			                                      columns: [
	                                                 {
	                                            	 header: '导航树',
	                                                 dataIndex: 'url',
	                                                 renderer: function(v, r){
	                                                	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
	                                                    return r.name;	}
	                                                }],
				                                    data:stepdata,
	                                  	             onselectionchange: function(e){  
			  	                                        var r = e.selected;
			  	                                        //console.log(r);
			  	                                        //console.log(mainTabBar.children.length);
			  	                                        if(r){
			  	                                        	openNewTab(r);
			  	                                            mainTabBar.set('selectedIndex', mainTabBar.children.length);                                            
			  	                                        }
			  	                                    }
					                                      }
					                                    ]
				                        },
				                        
				                        //右主界面
				                        {
				                        	id:'processmainPanel',
				                        	type: 'ct',width: '100%',height: '100%',verticalGap: 0,padding:[0,0,0,0],
				                        	children:[
				                        		{id:'mainTabBar',type: 'tabbar',selectedIndex: 0,border: [0,0,0,0],padding:[0,0,0,0],width: '100%',
				                        		onselectionchange: function(e){        
				                                    mainTabContent.set('selectedIndex', e.index);
				                                    stepcount=e.index;
				                                  }
				                        	    },
				                        	    {
			                        	    	id: 'mainTabContent',selectedIndex: 0,layout: 'viewstack',type: 'box',padding:[0,0,0,0],
			                        	    	border: [1,1,1,1],width: '100%',height: '100%',
			                        	    	onselectionchange: function(e){
			                                        alert('content-selected');}
				                        	    }
				                        	   
				                        	]
				                        }
				                        ]
				          }
				          ]
			});
	openNewTab(stepdata.source[0]);
	
}
function openNewTab(r){	
	/*var toolbar=Edo.create({
		type:'box',layout:'horizontal',height: '10%',width:'100%',padding:[0,0,0,0],border: [0,1,1,1],
		children:[
			{type: 'button',id:'previous',text: '上一步',visible:false,style:'margin-left:200px;',width:80,height: 30,onclick: function(e){
				   if(stepcount==stepdata.source.length-1){
					   next.set('visible',true);
					   submi.destroy();
				   }
					stepcount=stepcount-1;
					openNewTab(stepdata.source[stepcount]);
		        	}},
	        {type: 'button',id:'next',text: '下一步',width:80,height: 30,style:'margin-left:230px;',onclick: function(e){
		         if(stepcount==0){
		        		previous.set('visible',true);
		        	  }
		         stepcount=stepcount+1;
		       	 openNewTab(stepdata.source[stepcount]);
	        	}}
       	
		
		]
		});
  if(stepcount==stepdata.source.length-1){
	  next.set('visible',false);
	  var submi=Edo.create({type: 'button',id:'submi',text: '提交',width:80,height: 30,style:'margin-left:230px;',onclick: function(e){
		   opener.document.location='cqz/js/yang/myjob.jsp';  
      	   window.close(); 
       	}});
	  toolbar.addChild(submi);
  }
 
  if(stepcount==0){
	  previous.set('visible',false);
  }*/
  var id = r.id;
  var idx  = mainTabBar.children.length;
  var c = Edo.get("tbar_"+id);
  if(c==null){
    c = mainTabBar.addChildAt(idx,
      {id:'tbar_'+id,type: 'button',text:r.name,arrowMode: 'close',
          onarrowclick:function(e){
          //根据idx, 删除对应的容器
          var c = Edo.get('cont_'+id);          
          c.destroy();
          //选中原来Index处          
          var tabitem = mainTabBar.getChildAt(mainTabBar.selectedIndex);          
          if(!tabitem){
              tabitem = mainTabBar.getChildAt(mainTabBar.selectedIndex-1);               
          }          
          mainTabBar.set('selectedItem', tabitem);        
        }
      }
    );
    
    var content =mainTabContent.addChildAt(idx,
      {
        id:'cont_'+id,type:"box",width: '100%',height: '100%',style: 'border:0;',padding:[0,0,0,0]
      }
    );    
   content.addChild(new r.func());
  };
  mainTabBar.set('selectedItem', c);
    
};
function removeselected(){
	 mainTabContent.getChildAt(mainTabContent.selectedIndex).destroy();
	 mainTabBar.getChildAt(mainTabBar.selectedIndex).destroy();
    
}

