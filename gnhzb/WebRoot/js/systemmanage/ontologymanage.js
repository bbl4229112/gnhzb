//创建行为列表
function createOntofileList(){
	var currentTarget;
	var currentId;
	
	var filelist = cims201.utils.getData('ontoeditcenter!listontofile.action');
	var systemfile=cims201.utils.getData('ontoeditcenter!getSystemontofile.action');
	if(Edo.get("filelist"))
	{
		Edo.get("filelist").destroy();	
	}
	var ontofileList = Edo.create({
		type: 'table',
        width: '100%',
        height: '100%',
        id:"filelist",
        rowHeight : 30,
        columns: [    
                  {
                headerText: '文件名',
                dataIndex: 'filename',
                headerAlign: 'center',
                align: 'center',
                width: 200
            },          
            {
                headerText: '上次修改时间',
                dataIndex: 'lastchangetime',
                headerAlign: 'center',
                align: 'center',
                width: 200
         
            },{
                headerText: '操作行为',
                dataIndex: 'filename',
                headerAlign: 'center',
                align: 'center',
                width: 400,
                renderer: function(v){
			  
			    		return '<input type="button" value="编辑" onclick="owlFilesEdit(\''+v+'\')"/>&nbsp;&nbsp;'+
			    		'<input type="button" value="删除" onclick="owlFilesDelete(\''+v+'\')"/>&nbsp;&nbsp;'+
						'<input type="button" value="新建" onclick="owlFilesNew(\''+v+'\')"/>&nbsp;&nbsp;'+
						'<input type="button" value="查看日志" onclick="owlFilesShowLog(\''+v+'\')"/>&nbsp;&nbsp;'+
						'<input type="button" value="设为系统文件" onclick="owlFilesSetAsSys(\''+v+'\')"/>';
			    	}
			   
  
         
            }
        ]	,
    data: filelist
	});

	var outPanel = Edo.create({
		id : 'out',
	    type: 'panel',
	    title: systemfile,
		width: '100%',
		height: '100%',
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout: 'vertical',
		children: [		
          ontofileList
		]
	});
	
	this.getList = function(){
		return outPanel;
	};
}
function owlFilesDelete(filename){
	 Edo.MessageBox.confirm("确认删除", "你确定删除文件"+filename+"吗？",
    		 function(action){
			if(action=='yes'){
	var result = cims201.utils.getData('ontoeditcenter!DeleteFile.action',{owlFileName:filename});
	alert(result);
	var filelist = cims201.utils.getData('ontoeditcenter!listontofile.action');
	Edo.get("filelist").set("data",filelist);
	}
			});
};
function owlFilesNew(filename)
{
	var result = cims201.utils.getData('ontoeditcenter!addnewfile.action',{owlFileName:filename});
	alert(result);
	var filelist = cims201.utils.getData('ontoeditcenter!listontofile.action');
	Edo.get("filelist").set("data",filelist);
}
function owlFilesShowLog(filename)
{
	
      openNewTab('owlFilesShowLog'+filename, 'owlFilesShowLog',"日志列表", {filenamevalue:filename})	;
      
}

function owlFilesSetAsSys(filename){
	 Edo.MessageBox.confirm("确认", "你确定设定文件："+filename+"为系统展示文件吗？",
   		 function(action){
			if(action=='yes'){
	var result = cims201.utils.getData('ontoeditcenter!SetSystemFile.action',{owlFileName:filename});
	alert(result);
	systemfile = cims201.utils.getData('ontoeditcenter!getSystemontofile.action');
	Edo.get('out').set('title',systemfile);
	}
			});
};

function ontolog_box(filename) 
{ 
	var myConfig1 = {
			verticalLine: true,
			horizontalLine: true,
			headerVisible: true,
			horizontalScrollPolicy : 'off',
	
			autoColumns: false
		};
	var myColumns1 = [
               {
                   headerText: '用户姓名',
                   dataIndex: 'owlfilename',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 150,
                   renderer: function(v,r){  
                	   return r.username+"("+r.userEmail+")";
                   }
               },
               {
                   headerText: '文件名称',
                   dataIndex: 'owlfilename',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 100

               }
               ,
               {
                   headerText: '涉及实例',
                   dataIndex: 'operationobject',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 80

               },
               {
                   headerText: '操作',
                   dataIndex: 'operationtype',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 600

               },
               {
                   headerText: '操作时间',
                   dataIndex: 'timestr',
                   headerAlign: 'center',                 
                   align: 'center',
                   width: 120

               }
               ];

 	var myTable1_rt = new createTable(myConfig1,'100%','100%','本体文件修改日志列表',myColumns1,[],[],'ontoeditcenter!owleidtlogshow.action',{owlFileName:filename},true);
 	var 
    outBox	= Edo.create({
    		type: 'box',
    		width: '100%',
    		height: '100%',
    		layout: 'vertical',
    		border: [0,0,0,0],
    		padding: [0,0,0,0],
    		children: [
    			
       			myTable1_rt.getTable()
       		]
       	});
 	
 	
 	this.getontologbox = function(){
 		return outBox;
 	};
}

function owlFilesEdit(filename)
{
	  navManager("ontoedit",filename);

}
//添加本体术语
function addontology_box(filename,ontoname,className)
{ 
	
	var data=cims201.utils.getData('ontoeditcenter!FindClassPropsUI.action?classURI='+encodeURIComponent(className)+'&owlFileName='+encodeURIComponent(filename));
	var stringProp=data[0];
	var stringProplist=stringProp.props;
	
	var objProp=data[1];
	var objProplist=objProp.props;
	var outPanel =  creatformbox(stringProplist,objProplist,filename,null,className);	
    this.getontobox=function(){
	return outPanel;
	
};

}
//编辑本体术语
function editontology_box(filename,ontoname)
{
	var data=cims201.utils.getData('ontoeditcenter!findIndivPropsWithValue.action?indivName='+encodeURIComponent(ontoname)+'&owlFileName='+encodeURIComponent(filename));
	if(data.length>2)
	{
		alert(data);
	}
	var stringProplist=data[0];	
	var objProplist=data[1];
	var outPanel = creatformbox(stringProplist,objProplist,filename,ontoname,null);	
    this.getontobox=function(){
	return outPanel;
	
};

}

function creatformbox(stringProplist,objProplist,filename,ontoname,className)
{   
	//组件id后缀
	var suffix=filename;

	//是添加本体的情况
	if(className==null)
		{
		suffix=suffix+ontoname;	
		}
	else{
		suffix=suffix+className;	
	} 
	var outPanel = Edo.create({
	
			layout : 'vertical',
		    type: 'panel',
		 
			width: '100%',
			height: '100%',
			padding: [0,0,30,0],
			border: [0,0,0,0],
			layout: 'vertical',
			children: [		
	          
			]
		});
	
	//术语名称组件
	if(Edo.get("indivName"+suffix))
		   Edo.get("indivName"+suffix).destroy();
	var ontonameform=Edo.create(
		 	{
		 		padding: [20,0,0,20],
  				 type: 'formitem',
  				 label: '术语名称<span style="color:red;">*</span>:',
  				 labelWidth: 90,
				 children:[{type: 'text', width : 680,id: 'indivName'+suffix}]
				}		
	);
	
	//术语属性表单组件
	if(className==null)
	{outPanel.set('title',"编辑本体术语");
	Edo.get('indivName'+suffix).set('text',ontoname);
	Edo.get('indivName'+suffix).set('readOnly',true);
	}
	else
	{outPanel.set('title',"添加本体术语");
	
	}
	//术语属性表单组件
	var probox=Edo.create({
		   type: 'box',
			layout : 'horizontal',
			horizontalGap:35,
	
			padding: [10,0,0,0],
			border: [0,0,0,0]
		});
	//术语数据属性表单组件
	var stringprobox=Edo.create({
		   type: 'box',
		   layout : 'vertical',
		   verticalGap:15,
		
		   padding: [20,0,0,0],
		   border: [0,0,0,0]
		});
	//术语对象属性表单组件
	var objprobox=Edo.create({
		type:'box',
		layout : 'vertical',
	   
		padding: [0,0,0,0],
		border: [0,0,0,0]
	});
	//添加数据属性表单
	for(var i=0;i<stringProplist.length;i++){
		if(Edo.get(stringProplist[i].name+suffix))
			   Edo.get(stringProplist[i].name+suffix).destroy();
	var tempvaue="";
	if( stringProplist[i].propValue!=null)
		tempvaue=stringProplist[i].propValue;
	formitems = {
			type : 'formitem',
			label : stringProplist[i].name,
			labelWidth : 100,
			labelAlign : 'right',
			children : [{
						type : 'text',
					//	valid : chkText,
						text : stringProplist[i].propValue,
						width : 200,
						id :  stringProplist[i].name+suffix
					}]
		};
	stringprobox.addChild(formitems);
 }
	
	//添加对象属性表单
	vbox= Edo.create({
		layout : 'horizontal',
		padding: [0,0,0,0],
		border: [0,0,0,0],
		type:"box"
	});	
	for(var i=0;i<objProplist.length;i++){
		
		proitembox= Edo.create({
			padding: [0,0,0,0],
			border: [0,0,0,0],
			type:"box"
		});	
			
	
		if(Edo.get(objProplist[i].name+suffix))
			   Edo.get(objProplist[i].name+suffix).destroy();
	        buttonitem=	Edo.create({
			type : 'box',
		
			padding: [0,0,0,0],
			border: [0,0,0,0],
			layout : 'horizontal',
			children:[
			        {
			        	type : 'formitem',
			        	labelWidth : 100,
						labelAlign : 'right',
						label : objProplist[i].name
			        },  
			          {
	               type: 'button',
	               text: '添加',
	               name:  i+'',
	               onclick: function(e)
	               {
	            		 var t=  parseInt(this.name);
		            	  // alert(objProplist[t].name);
			            var alreadylist=Edo.get(objProplist[t].name+suffix).getChildren();
			           
		            	   getontopanenlbox(objProplist[t].name,objProplist[t].propRange,filename,suffix,alreadylist);    
	            	   
	               }                
	               
	           } ,
	           {
	               type: 'button',
	               text: '删除',
	               name:  i+'',
	               onclick: function(e){
	            	   var t=  parseInt(this.name);
	            	   var cks=Edo.get(objProplist[t].name+suffix).getChildren();
	            	   var l=Edo.get(objProplist[t].name+suffix).numChildren(); 
	            	   for(var q=l-1;q>=0; q--){
	            		
	                       var ck = cks[q];
	                     //  alert(ck.value+ck.checked);
	                       if(ck.checked) {
	                    	  
	                    	   Edo.get(objProplist[t].name+suffix).removeChildAt(q);
	                         
	                       }
	                   }

	               }                
	               
	           }]
			
		});	
	    	formitems = Edo.create({
		     	id :  objProplist[i].name+suffix,
		        type: 'box',    
		        border:1,
		        verticalScrollPolicy:'on',
		        width: 250,
		        height: 100
			
		});
	    	if(className==null){
	    	    var objpropValue=objProplist[i].propValue;
		        var objvaluelist=objpropValue.split(',');
		        for ( var int = 0; int < objvaluelist.length; int++) {
			    	if(objvaluelist[int]!=null&&objvaluelist[int]!='null'&&objvaluelist[int]!=""){
			         radioitem={type: 'check', name: objProplist[i].name, value: objvaluelist[int], text: objvaluelist[int]};
			         formitems.addChild(radioitem);
			        }
			    }
	    	}
		proitembox.addChild(buttonitem);
		proitembox.addChild(formitems);
		if(i%2==0)
			vbox= Edo.create({
				layout : 'horizontal',
				padding: [0,0,0,0],
				border: [0,0,0,0],
				type:"box"
			});
		vbox.addChild(proitembox);
		if((i%2!=0&&i!=0)||i==objProplist.length-1)
			objprobox.addChild(vbox);
	    }

	itembutton={
			 type:'formItem',
			 labelWidth: 400,
			 layout : 'horizontal',
			 children:
				 [
				  {
		               type: 'button',
		               text: '提交',
		              //提交表单
		               onclick: function(e){
		              //手工拼写表单并递交
		            	 submitform(filename,ontoname,className,stringProplist,objProplist) ; 
		               }                
		               
		           } ,
		           {   
		               type: 'button',
		               text: '取消',
		               onclick: function(e){
		            	//   chooseboxwin.hide();
		               }                
		               
		           }
				  ]
			 
	};
	
	
	probox.addChild(stringprobox);
	probox.addChild(objprobox);
	outPanel.addChild(ontonameform);
	outPanel.addChild(probox);
	outPanel.addChild(itembutton);

    return outPanel;
}
//弹出窗口选择术语
function getontopanenlbox(onto,range,filename,suffix,alreadylist)
{
	var choosebox=Edo.create({
		type:"box",
		 width: "100%",
		 height: "100%",
		padding: [0,0,0,0],
		border: [0,0,0,0],
		layout : 'horizontal'
	});
	//var dataTree=cims201.utils.getData('ontoeditcenter!owlClassWrite.action?owlFileName='+filename);

	var tree = new Edo.lists.Tree();
	tree.set({
	    cls: 'e-tree-allow',
	    border: [0,0,0,0],
	    width: "100%",
	    height: "100%",
	    enableCellEdit:false,
	    headerVisible:false,
	    autoColumns: true,
	    horizontalLine: false,
	    columns: [
	        {
	            header: 'bentileibie',
	            dataIndex: 'name',
	            editor: {
	                type: 'text'
	            }
	        }
	    ],
	    data: range,
	    oncellclick :function(e){
            var onto = tree.getSelected();
            if(onto.name!='本体类'){
            var ontochooselist=cims201.utils.getData('ontoeditcenter!addIndivs.action?&classURI='+encodeURIComponent(onto.name)+'&owlFileName='+encodeURIComponent(filename));
            var newontochooselist=new Array();          
	             for ( var int = 0; int < ontochooselist.length; int++) {
	           //判断是否已经存在改选项
				var ontoshuyu = ontochooselist[int];
				var isnotexist=true;
				 for ( var tmpint = 0; tmpint < alreadylist.length; tmpint++) {
					// alert("已存在"+alreadylist[int].text);
					
				if(ontoshuyu.text==alreadylist[tmpint].text)
				{
					
					isnotexist=false;
					
				}

				}
				if(isnotexist){
					
					newontochooselist[newontochooselist.length]=ontoshuyu;
	             }
				
			}
            
            ontochoosetable.set('data',newontochooselist);
        }
	    }
	});
	leftpanel=Edo.create(
	{
			type:"panel",
			title :"可选择类",
			 width: "50%",
			 height: "100%",
			padding: [0,0,0,0],
			border: [0,0,0,0],
			layout : 'vertical',
			children:tree
			
	});
	
	rightbox=Edo.create(
	{
			type:"box",
		
			 width: "50%",
			 height: "100%",
			padding: [0,0,0,0],
			border: [0,0,0,0],
			layout : 'vertical'
			//children:tree
			
	});
	rightlistpanel=Edo.create(
			{
					type:"panel",
					title :"可选属性",
					 width: "100%",
					 height: "85%",
					padding: [0,0,0,0],
					border: [0,0,0,0],
					layout : 'horizontal'
					//children:tree
					
			});
	
	var ontochoosetable=Edo.create(
	{
			type: 'MultiSelect',
	        width: '100%',
	        height: '100%'
	   
	        
			
	});
	
    if(	Edo.get('searchtext'+filename+"panel"))
    	Edo.get('searchtext'+filename+"panel").destroy();	
	 rightsearchpanel= {
           type: 'panel',padding:0,
           padding: [5,0,0,10],
           layout: 'horizontal',
           width: '100%',height: '15%',              
           title: '<span class=cims201_leftbar_font>查询实例</span>',
                                        
           children: [
                  	{   
                  	id:'searchtext'+filename+"panel",
               		type: 'autocomplete', 
               		border: [0,0,0,0],
           			width: "90%", 
           			queryDelay: 200,
           			url: 'kmap/onto!recommentTag.action?owlFileName='+encodeURIComponent(filename),
           			popupHeight: '605',
           			valueField: 'tagName', 
           			displayField: 'tagName'
               	},
           		    {type:'button',
           		    	text:'查询',
           		    	 onclick: function(e){
           		             var onto = Edo.get('searchtext'+filename+"panel").get('text');
           		             var oldontochooselist=ontochoosetable.get('data');
           		          
           		             var newontochooselist=new Array();          
           		             for ( var int = 0; int < oldontochooselist.source.length; int++) {
								var ontoshuyu = oldontochooselist.source[int];
				           
								if(ontoshuyu.text.indexOf(onto)!=-1)
								{
									newontochooselist[newontochooselist.length]=ontoshuyu;
								}
							}
           		             if(newontochooselist.length>0)
           		          ontochoosetable.set('data',newontochooselist);
           		             else
           		           alert("没有查询到！");	 

           		    	 }}]};
	 itembutton={
			 type:'formItem',
			 labelWidth: 150,
			 layout : 'horizontal',
			 children:
				 [
				  {
		               type: 'button',
		               text: '确定',
		               
		               onclick: function(e){
		            	
		            	   var templist=ontochoosetable.getValue().split(',');
		            	
		            	  var tembox= Edo.get(onto+suffix);
		            	
		            	  for ( var int = 0; int < templist.length; int++) {
		            		  radioitem={type: 'check', name: onto, value: templist[int], text: templist[int]};
			            	  tembox.addChild(radioitem);
						}
		            	 
		            	   chooseboxwin.hide();
		            	   
		               }                
		               
		           } ,
		           {
		               type: 'button',
		               text: '取消',
		               onclick: function(e){
		            	   chooseboxwin.hide();
		               }                
		               
		           }
				  ]
			 
	 };
	 leftpanel.addChild(itembutton);
	rightlistpanel.addChild(ontochoosetable);
	rightbox.addChild(rightsearchpanel);
	rightbox.addChild(rightlistpanel);
	choosebox.addChild(leftpanel);
	choosebox.addChild(rightbox);
	chooseboxwin=cims201.utils.getWin(700,400,"选择属性值",choosebox); 

	setWinScreenPosition(700, 470, chooseboxwin);
	
}

//提交表单方法
function 	 submitform(filename,ontoname,className,stringProplist,objProplist) 
{
	//组件id后缀
	var suffix=filename;
	var formvalue='{"owlFileName":'+fixStr(filename);
	//是添加本体的情况
	if(ontoname==null)
		{suffix=suffix+className;		
		formvalue+=',"className":'+fixStr(className);
		}
		//是编辑本体的情况
	if(className==null){
		suffix=suffix+ontoname;
	
	} 
	//首先获得术语名称
	var indivName=Edo.get("indivName"+suffix).getValue();
	formvalue+=',"indivName":'+fixStr(indivName);
	//遍历数据属性值
	for ( var int = 0; int < stringProplist.length; int++) {
		var stringpro = stringProplist[int];
		var tempvalue=Edo.get(stringpro.name+suffix).getValue();
		formvalue+=','+fixStr(stringpro.name)+':'+fixStr(tempvalue);
		
	}
	
	//遍历对象属性值
	for ( var int = 0; int < objProplist.length; int++) {
		var objpro = objProplist[int];
		var radiolist=Edo.get(objpro.name+suffix).getChildren();
		var tempvalue="";
		for ( var t = 0; t < radiolist.length; t++) {
			
		if(t==radiolist.length-1)
		tempvalue+=radiolist[t].value;
		else
		tempvalue+=radiolist[t].value+',';	
		}
	
		formvalue+=','+fixStr(objpro.name)+':'+fixStr(tempvalue);
	}
	formvalue=formvalue+'}';
var result=	cims201.utils.getData("ontoeditcenter!editowl.action",{formvalue:formvalue});
     alert(result);
}
function fixStr(temp)
{
	temp='"'+temp+'"'	;
    return temp;
}
