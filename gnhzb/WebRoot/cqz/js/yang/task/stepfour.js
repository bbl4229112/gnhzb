function getstepfour(){
	var fileuploadbox = Edo.build(
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
				   	        	text:'请添加改过程节点涉及到的相关知识'
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
					{type: 'panel',title:'知识列表',width: '100%',height:300,layout: 'vertical',border: [0,0,1,0],padding: [0,0,0,0],verticalGap:'0',
						children:[
							{
								type: 'group',
							    width: 120,
							    layout: 'horizontal',
							    cls: 'e-toolbar',
							    children: [
									{
									    type: 'button',
									    text: '添加',
									    onclick: function(e){
									        var content=new fileuploadcontent();
									        var toolbar=new getfiletoolbar();
									  	    var win=cims201.utils.getWin(500,450,'选择输入',[content,toolbar]);
									 	    win.show('center', 'middle', true);
									 	    win.set('padding',[0,0,0,0]);
									    }                
									    
									    },
							        {
							        type: 'button',
							        text: '删除',
							        onclick: function(e){
							            var r = Edo.get(inputtable).getSelected();
							            if(r){
							                Edo.get(inputtable).data.remove(r);
							            }else{
							                alert("请选择行");
							            }
							        }                
							        
							        }
								]
							},
							{
						        type: 'table',
						        id:'knowledgetable',
						        width: 600,
						        height: 260,
						        horizontalScrollPolicy:'off',
						        columns:[
					                 	Edo.lists.Table.createMultiColumn(),
					                    {header: '编号', dataIndex: 'id',width:100,headerAlign: 'center',align: 'center'
						               
					                    },
					                    {header: '知识名称', dataIndex: 'name',width:120,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '知识类别', dataIndex: 'category',width:120,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '文件数量', dataIndex: 'filecount',width:120,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '文件列表', dataIndex: 'filelist',width:120,headerAlign: 'center',align: 'center',renderer: function(v, r){
					                    	return '<span style="cursor: pointer;" onclick="showfilelist();">查看</span>';
					                    }
					                    }
					                    
						                
						        ],
						        data:levelmodule.levelmoduleobject.knowledge
							
						      
						    }
						          ]
					},
					{
						type:'box',layout:'horizontal',width:'100%',padding:[10,0,0,0],border: [0,0,0,0],
						children:[
							{type: 'button',text: '上一步',style:'margin-left:100px;',width:80,height: 30,onclick: function(e){
									 var knowledge=knowledgetable.data.source;
							       	 levelmodule.levelmoduleobject.knowledge=knowledge;	
								    removeselected();
									openNewTab(stepdata.source[2]);
						        	}},
					        {type: 'button',text: '下一步',width:80,height: 30,style:'margin-left:120px;',onclick: function(e){
					        	 var knowledge=knowledgetable.data.source;
						       	 levelmodule.levelmoduleobject.knowledge=knowledge;	
						       	 removeselected();
						       	 openNewTab(stepdata.source[4]);
					        	}}
				       	
						
						]
	   	   	    	}
		       	    
		       	    
		       	    ]
		       	}
		       	
			);
		return fileuploadbox;
		}
function getfiletoolbar(){
	var toolbar=Edo.create(
			{type: 'ct',
			    cls: 'e-dialog-toolbar',
			    width: '100%',
			    layout: 'horizontal',
			    height: 30,
			    horizontalAlign: 'center',
			    verticalAlign: 'middle',
			    horizontalGap: 10,
	            children: [      
					{
					    type: 'button',
					    text: '确定',
					    onclick: function(e){
					        //upload对象就是swfupload的原生对象
					    	var formvalue=knowledgedefinebox.getForm();
					    	var filename='';
					    	choosenfiletable.data.each(function(o, i){
					            filename=filename+o.name+';';
					            	
					        });
					    	filename=filename.substring(0,filename.length-1);
					    	var filecount=choosenfiletable.data.source.length;
					    	var newrow={id:knowledgetable.data.source.length+1,name:formvalue.name,category:formvalue.category,filecount:filecount,filenames:filename}
					    	knowledgetable.data.insert(knowledgetable.data.source.length+1, newrow);
					    	this.parent.parent.parent.destroy();
					    }
					},
					{
	                    type: 'button',
	                    text: '重置',
	                    onclick: function(e){ 
	                    	var aa=knowledgedefinebox.getForm ();
	                    	//alert(aa.name);
	                    	Edo.get('knowledgedefinebox').reset();
	                    	choosenfiletable.set('data',[]);
	                    }
	                },
	                {
	                    type: 'button',
	                    text: '取消',
	                    onclick: function(e){       
	                    	 this.parent.parent.parent.destroy();
	                    }
	                }
	            ]
		        }
			);
	return toolbar;
}
function fileuploadcontent(){
		var fileuploadcontent=Edo.create(
				{
				    type: 'box',
				    width : 600,
				    height:350,
				    id:'knowledgedefinebox',
				    padding: [0,0,0,0],
				    border: [0,0,0,0],
				    children: [
						{type: 'panel',title:'填写知识信息',width: '100%',height:150,layout: 'vertical',border: [0,0,0,0],padding: [10,0,0,10],verticalGap:'0',
							children:[          
								{
									type : 'formitem',
									label : '标题:',
									labelWidth : 100,
									padding : [0, 0, 10, 0],
									labelAlign : 'left',
									children : [{
										id:'name',
										type:'text',
										width:300
									}]
								},
								{
									type : 'formitem',
									label : '知识分类:',
									labelWidth : 100,
									padding : [0, 0, 10, 0],
									labelAlign : 'left',
									children : [
											{
												type : 'combo',
												width : 300,
												
											/*	valid : noEmpty,*/
												id:'category',
												displayField : 'name',
												valueField : 'id',
												data : [
												        {
												        	id:1,name:'工艺知识'
												        },
												        {
												        	id:2,name:'节点过程介绍知识'
												        },
												        {
												        	id:3,name:'图纸'
												        },
												        {
												        	id:4,name:'专利'
												        }
												        
												        ]
											}
									          ]
								}
								]
						},
						{
				            type: 'box', width: 600, layout: 'horizontal',border: [0,0,0,0],
				            children: [  
						        {
						        	type:'label',
						            text:'请选择上传的文件:',
						            width:150
						        },
								{
								    id: 'lcauploader',
								    type: 'fileupload',
								    textVisible : false,
								    width: 380,
								    swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明
								
										upload_url : 'http://localhost:8080/Myproject20140827/lca/lcamodule!fileupload.action', // 上传地址
										flash_url : 'js/swfupload/swfupload.swf',
										flash9_url : "js/swfupload/swfupload_fp9.swf",
										button_image_url : 'js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
										file_types : '*.doc;*.docx', // 上传文件后缀名限制
										file_post_name : 'file', // 上传的文件引用名
										file_size_limit : '5000000',
										post_params : {
											'uploadDir' : 'uploadDir'
										}
									},
								    onfilequeueerror: function(e){
								        alert("文件选择错误:"+e.message);
								    },
								    onfilequeued: function(e){
								    	for(i in e.file ){
								    		/*  alert(i);           //获得属性 
								    		  alert(e.file[i]);  //获得属性值
*/								    		}
								    	var a=false;
								    	var newrow={name:e.filename};
								    	choosenfiletable.data.each(function(o, i){
					                        if(o.name == e.filename) {
					                        	alert('重复上传');
					                        	a=true;
					                        }
					                        	
					                    });
					                    if(a){
					                    	return null;
					                    }
								    	choosenfiletable.data.insert(0, newrow);
								    	//choosenfiletable.data.insert(knowledgetable.data.source.length, newrow);
								    	//alert(e.filename); 
								    	var ppp = lcauploader.upload;
										ppp.startUpload();
								
								    },
								    
								    onfilestart: function(e){   
								        alert("开始上传");
								    	//lcaupload.mask();
								    },
								    onfileerror: function(e){
								        alert("上传失败:"+e.message);
								        
								        //lcaupload.unmask();
								    },
								    onfilesuccess: function(e){    
								        alert("上传成功");
								        //lcaupload.unmask();
								    }
							    }]
						},
						{type: 'panel',title:'已选择文件列别',width: '100%',height:200,layout: 'vertical',border: [1,0,1,0],padding: [0,0,0,0],verticalGap:'0',
							children:[          
								{
							        type: 'table',
							        id:'choosenfiletable',
							        width: '100%',
							        height:'100%',
							        rowSelectMode: 'single', 
							        headerVisible:false,
							        enableDragDrop: true,  
							        horizontalScrollPolicy:'off',
							        columns:[
						                    {header: '文件名称', dataIndex: 'name',width:400,align: 'left',renderer: function(v, r){
						                    	return '<span class="icon-lcaknowledge-delete" onclick="deleteUploadFile('+r+');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>文档《'
						                    	+v+'》';
						                    }
					                    	}
							                
							        ]
							      
							    }]
						}
						
            ]
        }
				
				);
		return fileuploadcontent;
		
}
		    /*{
		    	type: 'box',
		    	width: 1100,
		    	height:'100%',
		    	border: [0,0,0,0],
		    	padding: [20,0,0,20],
		    	layout: 'horizontal',
		    	verticalAlign:'middle',
		    	horizontalAlign:'center',
		    	//style: 'background:white;',
		   	    children: [
		   	               
				   {
					    type: 'box',
					    border: [0,0,0,0],
					    width : 300,
					    children: [
			   	   	        {
			   	   	        	type:'textarea',
			      	        	width : 300,
			      	        	height:400,
			      	            style:  'font-size:20px;font-family:verdana;font-weight:bold;border:0;background:rgba(0,0,0,0);',
			      	        	text:'说明文字。。。。。。。。。。。。。。。dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd'
			   	   	        }, 
				   	   	    {
									type:'box',layout:'horizontal',width:'100%',padding:[0,0,0,0],border: [0,0,0,0],
									children:[
										{type: 'button',text: '上一步',style:'margin-left:100px;',width:80,height: 30,onclick: function(e){
												openNewTab(stepdata.source[2]);
									        	}},
								        {type: 'button',text: '下一步',width:80,height: 30,style:'margin-left:120px;',onclick: function(e){
									       	 openNewTab(stepdata.source[4]);
								        	}}
							       	
									
									]
				   	   	    }]
				    },
					{
					    type: 'box',
					    width : 600,
					    height:'100%',
					    padding: [0,0,0,20],
					    border: [0,0,0,0],
					    children: [
				       
					        {
			                    type: 'button',
			                    text: '添加知识',
			                    onclick: function(e){       
			                    	knowledgepanel.toggle();
		                    	
		                    }
			                },
			                {   id:'knowledgepanel',
								type:'panel',
								title: '知识面板',
								layout:'vertical',
								style:'border:0;',
								width:600,
								padding:[10,0,0,10],
								verticalAlign:'middle',
						    	horizontalAlign:'left',
						    	enableCollapse: true,
		                       // onclick: onPanelClick,
		                        expanded:false,
								children:[
									{
									    id:'knowledgedefinebox',type: 'box',width: 600, layout: 'vertical',border: [0,0,0,0],
									    children: [            
											{
												type : 'formitem',
												label : '标题<font color=red></font>',
												labelWidth : 100,
												padding : [0, 0, 10, 0],
												labelAlign : 'left',
												children : [{
													id:'name',
													type:'text',
													width:300
												}]
											},
											{
												type : 'formitem',
												label : '知识分类<font color=red></font>',
												labelWidth : 100,
												padding : [0, 0, 10, 0],
												labelAlign : 'left',
												children : [{
													id:'category',
													type:'text',
													width:300
												}]
											}
											]
									},
									{
							            type: 'box', width: 600, layout: 'horizontal',border: [0,0,0,0],
							            children: [  
									        {
									        	type:'label',
									            text:'请选择上传的文件',
									            width:150
									        },
											{
											    id: 'lcauploader',
											    type: 'fileupload',
											    textVisible : false,
											    width: 380,
											    swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明
											
													upload_url : 'http://localhost:8080/Myproject20140827/lca/lcamodule!fileupload.action', // 上传地址
													flash_url : 'js/swfupload/swfupload.swf',
													flash9_url : "js/swfupload/swfupload_fp9.swf",
													button_image_url : 'js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
													file_types : '*.doc;*.docx', // 上传文件后缀名限制
													file_post_name : 'file', // 上传的文件引用名
													file_size_limit : '5000000',
													post_params : {
														'uploadDir' : 'uploadDir'
													}
												},
											    onfilequeueerror: function(e){
											        alert("文件选择错误:"+e.message);
											    },
											    onfilequeued: function(e){
											    	for(i in e.file ){
											    		  alert(i);           //获得属性 
											    		  alert(e.file[i]);  //获得属性值
											    		}
											    	var a=false;
											    	var newrow={name:e.filename};
											    	choosenfiletable.data.each(function(o, i){
								                        if(o.name == e.filename) {
								                        	alert('重复上传');
								                        	a=true;
								                        }
								                        	
								                    });
								                    if(a){
								                    	return null;
								                    }
											    	choosenfiletable.data.insert(0, newrow);
											    	//choosenfiletable.data.insert(knowledgetable.data.source.length, newrow);
											    	//alert(e.filename); 
											    	var ppp = lcauploader.upload;
													ppp.startUpload();
											
											    },
											    
											    onfilestart: function(e){   
											        alert("开始上传");
											    	//lcaupload.mask();
											    },
											    onfileerror: function(e){
											        alert("上传失败:"+e.message);
											        
											        //lcaupload.unmask();
											    },
											    onfilesuccess: function(e){    
											        alert("上传成功");
											        //lcaupload.unmask();
											    }
										    }]
									},
									{
								        type: 'table',
								        id:'choosenfiletable',
								        width: 400,
								        rowSelectMode: 'single', 
								        headerVisible:false,
								        enableDragDrop: true,   
								        columns:[
							                    {header: '文件名称', dataIndex: 'name',width:400,align: 'left',renderer: function(v, r){
							                    	return '<span class="icon-lcaknowledge-delete" onclick="deleteUploadFile('+r+');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>文档《'
							                    	+v+'》';
							                    }
						                    	}
								                
								        ]
								      
								    },
								    {
							            type: 'box', width: 600, layout: 'horizontal',border: [0,0,0,0],
							            children: [                
							                {
							                    type: 'button',
							                    text: '取消',
							                    onclick: function(e){       
							                    	knowledgepanel.toggle();
							                    }
							                },
							                {
							                    type: 'button',
							                    text: '重置',
							                    onclick: function(e){ 
							                    	var aa=knowledgedefinebox.getForm ();
							                    	alert(aa.name);
							                    	Edo.get('knowledgedefinebox').reset();
							                    }
							                },
							                {
							                    type: 'button',
							                    text: '确定',
							                    onclick: function(e){
							                        //upload对象就是swfupload的原生对象
							                    	var formvalue=knowledgedefinebox.getForm();
							                    	var filename='';
							                    	choosenfiletable.data.each(function(o, i){
								                        filename=filename+o.name+';';
								                        	
								                    });
							                    	filename=filename.substring(0,filename.length-1);
							                    	var filecount=choosenfiletable.data.source.length;
							                    	var newrow={id:knowledgetable.data.source.length+1,name:formvalue.name,category:formvalue.category,filecount:filecount,filenames:filename}
							                    	knowledgetable.data.insert(knowledgetable.data.source.length+1, newrow);
							                    	knowledgepanel.toggle();
							                    }
							                }
							            ]
								        }
					            ]
				            },
				            {
						        type: 'table',
						        id:'knowledgetable',
						        width: 600,
						        height: 260,
						        columns:[
						            
					                    {header: '编号', dataIndex: 'id',width:120,headerAlign: 'center',align: 'center'
						               
					                    },
					                    {header: '知识名称', dataIndex: 'name',width:120,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '知识类别', dataIndex: 'category',width:120,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '文件数量', dataIndex: 'filecount',width:120,headerAlign: 'center',align: 'center'
					                    },
					                    {header: '文件列表', dataIndex: 'filelist',width:120,headerAlign: 'center',align: 'center',renderer: function(v, r){
					                    	return '<span style="cursor: pointer;" onclick="showfilelist();">查看</span>';
					                    }
					                    }
					                    
						                
						        ]
						      
						    }
				            
				          
					    ]
				}
			]}*/
	/*return fileuploadbox;

	}*/
function showfilelist(){
	var r=knowledgetable.getSelected();
	//alert(r.name);
	
}
