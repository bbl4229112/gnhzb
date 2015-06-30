
//定义流程数组
var basepathh='http://localhost:8080/gnhzb';
var cellcollection=new Array();
function getPDMmoduledefine(isdefined){
	var func=function(id){
     	moduleobject.modulename=Edo.get('mdname').text;
     	moduleobject.modulenote=Edo.get('mdnote').text;
     	isdefined=true;
     	savepdmmodule();
 	}
	    var content=new getprojectdef();
	    var toolbar=new gettoolbar(null,func);
 	    var win=getmywin(400,200,'填写模板信息',[content,toolbar]);
	    win.show('center', 'middle', true);
		   
}
function savepdmmodule(){
	 	var graph=editor.graph;
		var cells =  graph.getChildCells();
		var alllevels=wholelevel.alllevels;
		if(cells.length>0&&!moduleobject.issaved){
			if(moduleobject.moduletype=='PDM'){
				savecells();
				var moduleid= cims201.utils.getData(basepathh+'/module/module!addnewmodule.action',{modulename:moduleobject.modulename,modulenote:moduleobject.modulenote}); 
			   	for(var j=0;j<alllevels.length;j++){
			   	   var level=alllevels[j];
			       var modules=level.levelmodules;
			       if(modules.length==0){
			    	   
			       }else{
				       for(var i=0;i<modules.length;i++){
					       if( modules[i].levelmoduleobject.type=='process'){
					            var cell={};
					      		cell.processname=modules[i].levelmoduleobject.processname;
					        	cell.processnote=modules[i].levelmoduleobject.processnote;
					            cell.id=modules[i].levelmoduleobject.cellid;
					            cell.knowledge=modules[i].levelmoduleobject.knowledge;
					            cell.input=modules[i].levelmoduleobject.input;
					            cell.output=modules[i].levelmoduleobject.output;
					            cell.knowledgecategorylist=modules[i].levelmoduleobject.knowledgecategorylist;
					            cell.orderid=modules[i].levelmoduleobject.orderid;
					            alert(cell.knowledgecategorylist)
					            cell.tasktreenodeid=modules[i].levelmoduleobject.tasktreenodeid;
					            cellcollection.push(cell);
			       	   		}
		       	 		}
		       	 	   var levelid=level.levelid;
			       	   var parentlevelid=level.parentlevelid;
			       	   var xmldata = level.xml;
			       	   if('level_0'==levelid){
			       	    	var data= cims201.utils.getData(basepathh+'/module/module!addlevelmodule.action',{levelid:levelid,parentlevelid:parentlevelid,moduleid:moduleid,cellcollection:cellcollection,xmldata:xmldata}); 
			       	   		
			       	   }else{
			       	    	var parentcellid=level.parentcellid;
			       	   		var data= cims201.utils.getData(basepathh+'/module/module!addlevelmodule.action',{levelid:levelid,parentcellid:parentcellid,moduleid:moduleid,parentlevelid:parentlevelid,cellcollection:cellcollection,xmldata:xmldata});
			       	   }
			       	   cellcollection=new Array;
			       }
				}
				
			}
		}else if(cells.length==0){
			alert('请完成流程图再保存!');
			return null;
		}
		if(data==null){
		  	alert('保存成功！');
		}
		cellcollection=null;
		cellcollection=new Array;
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
function getprojectdef(){
	
	var content = Edo.create(
	    {type: 'box',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',
       	    children: [
       	    //				           
       	    {	type : 'formitem',label : '模板名称:',labelWidth : 150,labelAlign : 'right',
       	    children : [{type : 'text',width : 200,id : 'mdname'}]
       	    },
       	    {	type : 'formitem',label : '模板备注:',labelWidth : 150,labelAlign : 'right',
       	    children : [{type : 'text',width : 200,id : 'mdnote'}]
       	    }
       	   
       	    ]
       	});
       	return content;
       	
       	}