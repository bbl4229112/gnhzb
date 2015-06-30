Edo.build(
		{
			id:'main',
			type: 'app',
			width: '100%',
			height: '100%',
			layout: 'vertical',
			render: document.body,	
			style:'background:#B5E3E0',
			padding:0,
			children:[
                      //顶栏描述
			          {
			        	  type: 'ct',
			        	  width: '100%',
			        	  height: '40', 
			        	  layout: 'horizontal',			        	  
			        	  children:[
			        	            {
			        	              type:   'label',width:'100%',height: '100%',
			      		              style:  'font-size:20px;padding:5px;padding-top:8px;font-family:微软雅黑, 宋体, Verdana;font-weight:bold; ',
			      		              text: '面向高能耗设备全生命周期协同评价管理系统'
			        	            },
			        	            { 
			        	            	type: 'label', text: '您好, admin <a href="#" style="color:black;text-decoration:none;">退出</a>'
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
			                        	id:'leftPanel',
			                        	type: 'panel',
			                            title: '导航列表',
			                            width: '260',
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
			                                    	    type: 'panel',
				  			                            title: 'LCA模版管理',
				  			                            width: '100%',
				  			                            height: '100%',
				  			                            enableCollapse: true,
				  			                            onclick: onPanelClick,  
				  			                            expanded:false,
				  			                            titlebar:[{
				  			                            cls:'e-titlebar-accordion',
				  			                            onclick: toggle}],
				  			                            children:[
				  			                                      {
				  			                                    	  type: 'table', 
					  			                                      autoColumns: true, 
					  			                                      headerVisible: false,
					  			                                      width: '100%', height: '100%',
					  			                                      style:'border:0;',
					  			                                      verticalLine: false, 
					  			                                      horizontalLine: false, 
					  			                                      rowHeight: 25,
					  			                                      columns: [
								                                                {								                                                	header: '导航树',
								                                                    dataIndex: 'url',
								                                                    renderer: function(v, r){
								                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
								                                                        return r.name;	}
								                                                }],
								                                    data:[{id: 'modulebuilderCT', url: '', name: '创建流程模版'},
						                                    	           /*  {id: 'test', url: 'module6.htm', name: '流程模版管理'},
						                                    	             {id: 'nodemanage', url: 'module6.htm', name: '流程节点管理'},*/
						                                    	             {id: 'modulecheck', url: 'module6.htm', name: '流程模版管理'}],
						                                    	             onselectionchange: function(e){  
										  	                                        var r = e.selected;
										  	                                        //console.log(r);
										  	                                        //console.log(mainTabBar.children.length);
										  	                                        if(r){
										  	                                        	openNewTab(r);
										  	                                        }
										  	                                    }
				  			                                      }
				  			                                      ],			                              			                                    
					                                       },
					                                       {			         	                                    	 	                                    	
					                                    	    type: 'panel',
						  			                            title: 'LCA方案管理',
						  			                            width: '100%',
						  			                            height: '100%',
						  			                            enableCollapse: true,
						  			                            onclick: onPanelClick,  
						  			                            expanded:false,
						  			                            titlebar:[{
						  			                            cls:'e-titlebar-accordion',
						  			                            onclick: toggle}],
						  			                            children:[
						  			                                      {
						  			                                    	  type: 'table', 
							  			                                      autoColumns: true, 
							  			                                      headerVisible: false,
							  			                                      width: '100%', height: '100%',
							  			                                      style:'border:0;',
							  			                                      verticalLine: false, 
							  			                                      horizontalLine: false, 
							  			                                      rowHeight: 25,
							  			                                      columns: [
										                                                {								                                                	header: '导航树',
										                                                    dataIndex: 'url',
										                                                    renderer: function(v, r){
										                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
										                                                        return r.name;	}
										                                                }],
										                                    data:[{id: 'modulebuilderCT', url: '', name: '新建LCA方案'},
								                                    	           /*  {id: 'test', url: 'module6.htm', name: '流程模版管理'},
								                                    	             {id: 'nodemanage', url: 'module6.htm', name: '流程节点管理'},*/
								                                    	             {id: 'modulecheck', url: 'module6.htm', name: '管理LCA方案'}],
								                                    	             onselectionchange: function(e){  
												  	                                        var r = e.selected;
												  	                                        //console.log(r);
												  	                                        //console.log(mainTabBar.children.length);
												  	                                        if(r){
												  	                                        	openNewTab(r);
												  	                                        }
												  	                                    }
						  			                                      }
						  			                                      ],			                              			                                    
							                                       },
							                                       {			         	                                    	 	                                    	
							                                    	    type: 'panel',
								  			                            title: '物资名录管理',
								  			                            width: '100%',
								  			                            height: '100%',
								  			                            enableCollapse: true,
								  			                            onclick: onPanelClick,  
								  			                            expanded:false,
								  			                            titlebar:[{
								  			                            cls:'e-titlebar-accordion',
								  			                            onclick: toggle}],
								  			                            children:[
								  			                                      {
								  			                                    	  type: 'table', 
									  			                                      autoColumns: true, 
									  			                                      headerVisible: false,
									  			                                      width: '100%', height: '100%',
									  			                                      style:'border:0;',
									  			                                      verticalLine: false, 
									  			                                      horizontalLine: false, 
									  			                                      rowHeight: 25,
									  			                                      columns: [
												                                                {								                                                	header: '导航树',
												                                                    dataIndex: 'url',
												                                                    renderer: function(v, r){
												                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
												                                                        return r.name;	}
												                                                }],
												                                    data:[{id: 'basedatamanage', url: '', name: '基础物质管理'}
										                                    	         ],
										                                    	             onselectionchange: function(e){  
														  	                                        var r = e.selected;
														  	                                        //console.log(r);
														  	                                        //console.log(mainTabBar.children.length);
														  	                                        if(r){
														  	                                        	openNewTab(r);
														  	                                        }
														  	                                    }
								  			                                      }
								  			                                      ],			                              			                                    
									                                       },
									                                       {			         	                                    	 	                                    	
									                                    	    type: 'panel',
										  			                            title: '评价指标管理',
										  			                            width: '100%',
										  			                            height: '100%',
										  			                            enableCollapse: true,
										  			                            onclick: onPanelClick,  
										  			                            expanded:false,
										  			                            titlebar:[{
										  			                            cls:'e-titlebar-accordion',
										  			                            onclick: toggle}],
										  			                            children:[
										  			                                      {
										  			                                    	  type: 'table', 
											  			                                      autoColumns: true, 
											  			                                      headerVisible: false,
											  			                                      width: '100%', height: '100%',
											  			                                      style:'border:0;',
											  			                                      verticalLine: false, 
											  			                                      horizontalLine: false, 
											  			                                      rowHeight: 25,
											  			                                      columns: [
														                                                {								                                                	header: '导航树',
														                                                    dataIndex: 'url',
														                                                    renderer: function(v, r){
														                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
														                                                        return r.name;	}
														                                                }],
														                                    data:[{id: 'evaluationmethodsmanage', url: 'module6.htm', name: '评价方法管理'}],
												                                    	             onselectionchange: function(e){  
																  	                                        var r = e.selected;
																  	                                        //console.log(r);
																  	                                        //console.log(mainTabBar.children.length);
																  	                                        if(r){
																  	                                        	openNewTab(r);
																  	                                        }
																  	                                    }
										  			                                      }
										  			                                      ],			                              			                                    
											                                       },
	                                      {			         	                                    	 	                                    	
	                                    	    type: 'panel',
		  			                            title: '组织与人员管理',
		  			                            width: '100%',
		  			                            height: '100%',
		  			                            enableCollapse: true,
		  			                            onclick: onPanelClick, 
		  			                            padding:0,
		  			                            expanded:false,
		  			                            titlebar:[{
		  			                            cls:'e-titlebar-accordion',
		  			                            onclick: toggle}],
		  			                            children:[
		  			                                      {
		  			                                    	  type: 'table', 
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
						                                        data:[{id: 'myjob', url: 'module2.htm', name: '我的任务'},
			                                    	             {id: 'clerk', url: 'module3.htm', name: '人员管理'},
			                                    	             {id: 'department', url: 'module3.htm', name: '组织管理'},
			                                    	             {id: 'privilege', url: 'module3.htm', name: '角色管理'},
			                                    	             {id: 'role', url: 'module3.htm', name: '权限管理'},
			                                    	             {id: 'tasktree', url: 'module3.htm', name: '功能模块管理'},
			                                    	             {id: 're', url: 'module3.htm', name: '工作界面'},],
			                                    	             onselectionchange: function(e){  
							  	                                        var r = e.selected;
							  	                                        //console.log(r);
							  	                                        //console.log(mainTabBar.children.length);
							  	                                        if(r){
							  	                                        	openNewTab(r);
							  	                                        }
							  	                                    }
		  			                                      }
		  			                                      ],			                              			                                    
			                                       },
			                                       {			         	                                    	 	                                    	
			                                    	    type: 'panel',
				  			                            title: '关联知识管理',
				  			                            width: '100%',
				  			                            height: '100%',
				  			                            enableCollapse: true,
				  			                            onclick: onPanelClick,  
				  			                            expanded:false,
				  			                            titlebar:[{
				  			                            cls:'e-titlebar-accordion',
				  			                            onclick: toggle}],
				  			                            children:[
				  			                                      {
				  			                                    	  type: 'table', 
					  			                                      autoColumns: true, 
					  			                                      headerVisible: false,
					  			                                      width: '100%', height: '100%',
					  			                                      style:'border:0;',
					  			                                      verticalLine: false, 
					  			                                      horizontalLine: false, 
					  			                                      rowHeight: 25,
					  			                                      columns: [
								                                                {								                                                	header: '导航树',
								                                                    dataIndex: 'url',
								                                                    renderer: function(v, r){
								                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
								                                                        return r.name;	}
								                                                }],
								                                    data:[{id: 'modulebuilderCT', url: '', name: '知识搜索'},
						                                    	           /*  {id: 'test', url: 'module6.htm', name: '流程模版管理'},
						                                    	             {id: 'nodemanage', url: 'module6.htm', name: '流程节点管理'},*/
						                                    	             {id: 'modulecheck', url: 'module6.htm', name: '知识浏览'}],
						                                    	             onselectionchange: function(e){  
										  	                                        var r = e.selected;
										  	                                        //console.log(r);
										  	                                        //console.log(mainTabBar.children.length);
										  	                                        if(r){
										  	                                        	openNewTab(r);
										  	                                        }
										  	                                    }
				  			                                      }
				  			                                      ],			                              			                                    
					                                       }
			                                      /* {			         	                                    	 	                                    	
			                                    	    type: 'panel',
				  			                            title: '流程节点库',
				  			                            width: '100%',
				  			                            height: '100%',
				  			                            enableCollapse: true,
				  			                            onclick: onPanelClick,    
				  			                            expanded:false,                                      
				  			                            titlebar:[{
				  			                            cls:'e-titlebar-accordion',
				  			                            onclick: toggle}],
				  			                            children:[
				  			                                      {
				  			                                    	  type: 'table', 
					  			                                      autoColumns: true, 
					  			                                      headerVisible: false,
					  			                                      width: '100%', height: '100%',
					  			                                      style:'border:0;',
					  			                                      verticalLine: false, 
					  			                                      horizontalLine: false, 
					  			                                      rowHeight: 25,
					  			                                      columns: [
								                                                {								                                                	header: '导航树',
								                                                    dataIndex: 'url',
								                                                    renderer: function(v, r){
								                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
								                                                        return r.name;	}
								                                                }],
								                                    data:[{id:'moudeldevelop',url:'',name:'模块化开发'},
						                                    	             {id:'moudelcreat',url:'',name:'模块化平台搭建'},
						                                    	             {id:'moudeluse',url:'',name:'模块运用'},],
						                                    	             onselectionchange: function(e){  
										  	                                        var r = e.selected;
										  	                                        //console.log(r);
										  	                                        //console.log(mainTabBar.children.length);
										  	                                        if(r){
										  	                                        	openNewTab(r);
										  	                                        }
										  	                                    }
				  			                                      }
				  			                                      ],			                              			                                    
					                                       },*/
					                                      /*
							                                       {			         	                                    	 	                                    	
							                                    	    type: 'panel',
								  			                            title: '项目任务管理',
								  			                            width: '100%',
								  			                            height: '100%',
								  			                            enableCollapse: true,
								  			                            onclick: onPanelClick,
								  			                            expanded:false,
								  			                            titlebar:[{
								  			                            cls:'e-titlebar-accordion',
								  			                            onclick: toggle}],
								  			                            children:[
								  			                                      {
								  			                                    	  type: 'table', 
									  			                                      autoColumns: true, 
									  			                                      headerVisible: false,
									  			                                      width: '100%', height: '100%',
									  			                                      style:'border:0;',
									  			                                      verticalLine: false, 
									  			                                      horizontalLine: false, 
									  			                                      rowHeight: 25,
									  			                                      columns: [
												                                                {								                                                	header: '导航树',
												                                                    dataIndex: 'url',
												                                                    renderer: function(v, r){
												                                                    	//return '<a href="javascript:mainModule.load(\"'+r.url+'\")">'+r.name+'</a>';
												                                                        return r.name;	}
												                                                }],
												                                    data:[{id: 'projectbuilder', url: 'module8.htm', name: '项目创建'},
										                                    	             {id: 'projectmanage', url: 'module8.htm', name: '项目管理'},
										                                    	             {id: 'id8', url: 'module8.htm', name: '任务分配'},],
										                                    	             onselectionchange: function(e){  
														  	                                        var r = e.selected;
														  	                                        //console.log(r);
														  	                                        //console.log(mainTabBar.children.length);
														  	                                        if(r){
														  	                                           openNewTab(r);
														  	                                        }
														  	                                    }
								  			                                      }
								  			                                      ],			                              			                                    
									                                       }*/
									                                       
			                                   
			                                    	          ],			                            
			                                       },
			                        
			                        //右主界面
			                        {
			                        	id:'mainPanel',
			                        	type: 'ct',width: '100%',height: '100%',verticalGap: 0,padding:[0,0,0,0],
			                        	children:[
			                        		{id:'mainTabBar',type: 'tabbar',selectedIndex: 0,border: [0,0,0,0],padding:[0,0,0,0],
			                        		onselectionchange: function(e){        
			                                    mainTabContent.set('selectedIndex', e.index);
			                                  },
			                                  children: [
			                                             {id:"tbar_myjob",index:0,type: 'button',text: '主页'}		                                       
			                                           ]
			                        	    },
			                        	{
			                        	    	id: 'mainTabContent',selectedIndex: 0,layout: 'viewstack',type: 'box',padding:[0,0,0,0],
			                        	    	border: [1,0,1,1],width: '100%',height: '100%',verticalScrollPolicy: 'auto',
			                        	    	onselectionchange: function(e){
			                                        alert('content-selected');},
			                                    children: [{id: 'cont_job',type:"module",src:'cqz/js/yang/myjob.jsp',width: '100%',height: '100%', style: 'border:0'
			                                    }]  
			                        	}]
			                        }
			                        ]
			          }
			          ]
		});
function toggle(e){
    var panel = this.parent.owner;
    var accordion = panel.parent;
    accordion.getChildren().each(function(child){
        if(panel != child) child.collapse();
    });
    panel.toggle();
}
function onPanelClick(e){
    if(e.within(this.headerCt)){
        var panel = this;
        var accordion = panel.parent;
        accordion.getChildren().each(function(child){
            if(panel != child) child.collapse();
        });
        panel.toggle();
    }
}
function openModule(src){
    alert(src);
}

//打开新的选项卡
function openNewTab(r){	
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
    var module = mainTabContent.addChildAt(idx,
      {
        id:'cont_'+id,type:"module",width: '100%',height: '100%',style: 'border:0'
      }
    );    
    module.load('cqz/js/yang/'+r.id+'.jsp');   
  };
  mainTabBar.set('selectedItem', c);
    
};