var projectobj={
		versionid:null,
		productid:null,
		projectname:null,
		projectnote:null,
		projecttype:null,
		moduleid:null,
		modulename:null,
		Createdate:null,
		Version:null,
		createuserid:null
		}
var basepathh='http://localhost:8080/gnhzb';
var cell=null;
var levelmodule=null;
var currentmoduleid=null;
var cellcollection=new Array();
function getmodulediv(){
    new moduledivdefine();
    var box= new getmodulebox();
    Edo.get('mdct').addChild(box);
    movemodulediv();
}

function getmywin(width,height,title,children){
	var win=new Edo.containers.Window();
	var win = new Edo.containers.Window();
	win.set('title',title);
	win.set('titlebar',
	    [      //头部按钮栏
	        {
	            cls: 'e-titlebar-close',
	            onclick: function(e){
	                //this是按钮
	                //this.parent是按钮的父级容器, 就是titlebar对象
	                //this.parent.owner就是窗体
	                this.parent.owner.destroy();
	                //deleteMask();
	            }
	        }
	    ]
	);
	
	win.addChild({
	    type: 'box',
	    width: width,
	    height: height, 
	    style:'border:0;',
	    padding:0,
	    children: children
	});	
	return win;
}
function gettoolbar(id,func){
    var toolbar = Edo.create(
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
            minWidth: 70,
            onclick: function(e){
            if(func==undefined){
            }else{
            func(id);
            }
            this.parent.parent.parent.destroy();
            }
        },{
            type: 'button',
            text: '取消',
            minWidth: 70,
            onclick: function(e){
            this.parent.parent.parent.destroy();

            }
        }
    ]
});
return toolbar;
}
function getmodulebox(){
	var moduledata= cims201.utils.getData(basepathh+'/module/module!getModuletree.action',{moduletype:'PDM'});
	/*var componentmodeltree=getcomponentmodeltree();
	var moduledetail=getmoduledetail();*/
	var modulebox=Edo.create(
			{type: 'ct',id:'modulebox',width: 400,height: '100%',padding:[0,0,0,0],border:[0,0,0,0],layout:'vertical',verticalGap:'0',
	              children:[
                	              {
									type: 'group',
									width: '100%',
								    layout: 'horizontal',
								    cls: 'e-toolbar',
								    width:400,
								    children: [
								        {
								            type: 'button',
								            text: '选择该模型创建',
								            onclick: function(e){
								            	var r = moduletree.getSelected();
								            	projectobj.moduleid=r.superparentid;
								            	projectobj.modulename=r.name;
									        	projectobj.createdate=r.createdate;
									        	projectobj.version=r.version;
									        	projectobj.createuserid=r.createuserid;
								            	modulebox.destroy();
								            	 var a=document.getElementById('moduledefineContainer');
								            	 a.style.width=0;
								            	 resetm();
								            	 aa.initproject(projectobj);
								            	 aa.showmodule(r.id);
								            }                
								            
								        }]
                	              },
	                              {
			                	    type: 'tree',
				   			        width: '100%',
				   			        height:'100%',
				   			        autoColumns:true,
				   			        headerVisible: false,
				   			        verticalLine:false,
				   			        horizontalLine:false,
				   			        id: 'moduletree',
				   			        enabelCellSelect: false,
							        autoColumns: true,
							        enableDragDrop: true,
							        showHeader: false,
							        /*ondblclick:function(e){
							        	var r = this.getSelected();
							        	Edo.get('modulename').set('text',r.name);
							        	Edo.get('Createdate').set('text',r.Createdate);
							        	Edo.get('Version').set('text',r.Version);
							        	Edo.get('createuserid').set('text',r.createuserid);
							        	Edo.get('componentmodelTree').set('data', []);
							        	moduleid=r.id;
							        	 Edo.util.Ajax.request({
										        type: 'post',        
										        url: basepathh+'/lca/lcamodule!getmoduleComponentsList.action',
										        params: {
									                parentId: null,   //传递父节点的Name(也可以是ID)
									                id:r.componentid,
									                moduleid:r.id
									            },
										        onSuccess: function(text){
										           // alert(text);
										            var data = Edo.util.Json.decode(text);
										            Edo.get('componentmodelTree').set('data', data);
										        }
										        
										    });
							        },*/
							        columns:[
							            {   
							                enableDragDrop: true,
							                dataIndex: "name"
							            
				                        }
							            ],
				                     data:moduledata
							           
				            }
	                              
		         ]
	})
	return modulebox;
}
function showdetail(editcell,module,moduleid){
		cell=editcell;
		levelmodule=module;
		currentmoduleid=moduleid;
		/*var data= cims201.utils.getData(basepathh+'/module/module!getmoduleprocesscontent.action',{moduleid:currentmoduleid,processid:cell.getId()});
		levelmodule.levelmoduleobject.processname=data.name;
		levelmodule.levelmoduleobject.processnote=data.note;
    	levelmodule.levelmoduleobject.knowledgecategoryindex=Edo.get('category').data.indexOf(Edo.get('category').selectedItem);
        levelmodule.levelmoduleobject.knowledgecategoryid=Edo.get('category').getValue();
   	    levelmodule.levelmoduleobject.tasktreenodeid=data.tasktreenodeid;
   	    levelmodule.levelmoduleobject.tasktreenodename=data.tasktreenodename;
   	    levelmodule.levelmoduleobject.input=data.input;
   	    levelmodule.levelmoduleobject.output=data.output;*/
		if(!Edo.get('processct')){
			new processdivdefine();
		}
		 if(Edo.get('moduleprocessbox')){
			 resetprocessdiv(); 
			 Edo.get('moduleprocessbox').destroy();
		 }
		 var box=new getmoduleprocessdefinebox(cell);
	     Edo.get('processct').addChild(box);
	     moveprocessdiv();
	}
function getmoduleprocessdefinebox(cell){
	var index=null;
	if(levelmodule.levelmoduleobject.knowledgecategoryindex!=null)
	{
		/*alert(levelmodule.levelmoduleobject.knowledgecategoryindex)*/
		index=levelmodule.levelmoduleobject.knowledgecategoryindex;
		/*var r=Edo.get('category').data.getAt(levelmodule.levelmoduleobject.knowledgecategoryindex);
		alert(r.name)
		Edo.get('category').data.beginChange()
	    Edo.get('category').data.select(r)
	    Edo.get('category').data.endChange()*/
	}
	var box=Edo.create(
			{type: 'box',id:'moduleprocessbox',width: '100%',border: [0,0,0,0],padding: [20,0,0,0],layout: 'vertical',verticalGap:'20',
           	    children: [
					{	type : 'formitem',label : '执行模块:',labelWidth : 100,labelAlign : 'right',layout: 'horizontal',
						    children : [{type : 'text',width : 200,id : 'tasktreenodename',text:levelmodule.levelmoduleobject.tasktreenodename}
						                ]
						    },
	           	    {	type : 'formitem',label : '模块名称:',labelWidth : 100,labelAlign : 'right',
	           	    children : [{type : 'text',width : 200,id : 'processname',text:levelmodule.levelmoduleobject.processname}]
	           	    },
           
		           	 {	type : 'formitem',label : '知识领域:',labelWidth : 100,labelAlign : 'right',
		               	    children : [{type : 'text',width : 200,id : 'categorynames',text:levelmodule.levelmoduleobject.categorynames}
								        ],
		               	    },
		           	 {	type : 'formitem',label : '输入:',labelWidth : 100,labelAlign : 'right',
		               	    children : [{type : 'text',width : 200,id : 'input',text:levelmodule.levelmoduleobject.input}]
		               	    },
		           	 {	type : 'formitem',label : '输出:',labelWidth : 100,labelAlign : 'right',
		               	    children : [{type : 'text',width : 200,id : 'output',text:levelmodule.levelmoduleobject.output}]
		               	    },
		             {	type : 'formitem',label : '文件上传:',labelWidth : 100,labelAlign : 'right',
	               	    children : [{
						    id: 'pdmfileuploader',
						    type: 'fileupload',
						    textVisible : false,
						    width: 200,
						    swfUploadConfig : { // swfUploadConfig配置对象请参考swfupload组件说明
						
								upload_url : basepathh+'/lca/lcamodule!fileupload.action', // 上传地址
								flash_url : 'js/swfupload/swfupload.swf',
								flash9_url : " js/swfupload/swfupload_fp9.swf",
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
						    	choosenfiletable.data.insert(knowledgetable.data.source.length, newrow);
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
	           	 {	type : 'formitem',label : '说明:',labelWidth : 100,labelAlign : 'right',
	               	    children : [{type : 'textarea',width : 200,height:50,id : 'description',text:levelmodule.levelmoduleobject.processnote}]
	               	    },
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
               	             minWidth: 70,
               	             onclick: function(e){
               	   /*
               					levelmodule.levelmoduleobject.processname=Edo.get('processname').text;
               					levelmodule.levelmoduleobject.processnote=Edo.get('description').text;
               					aa.changecelllabel(Edo.get('processname').text,cell);
	               	         	levelmodule.levelmoduleobject.knowledgecategoryindex=Edo.get('category').data.indexOf(Edo.get('category').selectedItem);
	               	            levelmodule.levelmoduleobject.knowledgecategoryid=Edo.get('category').getValue();
		               	        levelmodule.levelmoduleobject.description=Edo.get('description').text;
			               	    levelmodule.levelmoduleobject.tasktreenodeid=Edo.get('tasktreenodeid').text;
			               	    levelmodule.levelmoduleobject.tasktreenodename=Edo.get('tasktreenodename').text;
			               	    levelmodule.levelmoduleobject.input=Edo.get('input').text;
			               	    levelmodule.levelmoduleobject.output=Edo.get('output').text;
           */
	               	             resetprocessdiv();
	               	             this.parent.parent.destroy();
               	             }
               	         },{
               	             type: 'button',
               	             text: '取消',
               	             minWidth: 70,
               	             onclick: function(e){
               	            	resetprocessdiv();	
	               	             this.parent.parent.destroy();

               	             }
               	         }
               	     ]
               	 }
	    			]
				});
	
   return box;	 
}