<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>
		<title>环境化设计知识服务平台</title>
		
	</head>
	<body style="margin: 0px;  width: 100%; overflow:hidden;" scroll="no">
	<div id="moduledefineContainer"
		style="position:absolute;overflow:hidden;top:50px;width:0px;right:0px;cursor:default;padding-top:0px;z-index:1">
	</div>
	<div id="stagebutton"
		style="position:absolute;overflow:hidden;left:0px;top:200px;cursor:default;padding-top:0px;z-index:1">
	</div>
	<div id="modulebutton"
		style="position:absolute;overflow:hidden;right:0px;top:200px;cursor:default;padding-top:0px;z-index:1">
	</div>
	<div id="builder"
		style="position:absolute;overflow:hidden;top:0px;left:0px;right:0px;bottom:0px;cursor:default;padding-top:0px;">
		<iframe id="aa" height= 100%  width=100%  src="http://localhost:8080/gnhzb/cqz/js/yang/modulebuilder.jsp"></iframe>
	</div>
	<div id="productContainer"
		style="position:absolute;overflow:hidden;top:50px;left:0px;width:0px;cursor:default;padding-top:0px;z-index:1">
	</div>
	<div id="stageContainer"
		style="position:absolute;overflow:hidden;top:50px;left:0px;width:0px;width:0px;cursor:default;padding-top:0px;z-index:1">
	</div>
	</body>
</html>
  	
  	<script type="text/javascript">
  		//整个应用的全局变量
  		//系统用户
  		cims201User = null;
  		
  	</script>
     	<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/welcome.css">
		<link href="<%=basePath%>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/icon.css" rel="stylesheet"
			type="text/css" />

		<script src="<%=basePath%>js/edo/edo.js" type="text/javascript"></script>
		<script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/utils.js" type="text/javascript"></script>	
		<script src="<%=basePath%>js/lca/moduledefine.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/lca/pdm_moduledefine.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/lca/lca_moduledefine_CT.js" type="text/javascript"></script>
	
	<!-- 统计分析 -->
	
	<!-- 系统管理 -->
		<!-- 本体编辑管理 -->
	<!--标准集成  -->
	
	<script type="text/javascript">
	//传递模型对象到iframe aa,即构建页面中
	function delivermoduleobject(){
		Edo.get('modulename').set('text',moduleobj.modulename);
        Edo.get('modulenote').set('text',moduleobj.modulenote);
		aa.initmodule(moduleobj);
	
	}
	//创建零部件模型
	function createcompmodule(){
		movep();
	}
	</script >
	
	<script type="text/javascript">
	var moduletype=null;
	//var toolbar=getnextbar();
	var content=new gettypedefine();
	var win=new Edo.containers.Window();
	var win = new Edo.containers.Window();
	win.set('title','选择构建类型');
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
	    width: 300,
	    height: 200, 
	    style:'border:0;',
	    padding:0,
	    children: content
	});	
	deleteMask();
    win.show('center', 'middle', true);
    var c=document.getElementById('moduledefineContainer');
    var t;
    var m=0;
    //展开模型信息panel
    function move(){ 
		     if(m<200){
		         m=m+10;
    			 c.style.width=m;
    			 t=setTimeout('move()',10);
		     }
	     	
    }
    //重置宽度计数m
    function resetm(){
    	m=0;
    }
    var a=document.getElementById('productContainer');
    //a.addEventListener('mouseover', movep, false);
    var t1;
    var m1=0;
     //展开零件信息panel
    function movep(){ 
 		if(m1<200){
	     m1=m1+10;
		 a.style.width=m1;
		 t1=setTimeout('movep()',10);
 
		}
	}
    function resetm1(){
    	m1=0;
    }
   var t2;
   var m2=0;
   var s=document.getElementById('stageContainer');
    //展开阶段信息panel
   function moves(){ 
     if(m2<200){
         m2=m2+10;
  			 s.style.width=m2;
  			 t2=setTimeout('moves()',10);
     }
     
    }
    function resetm2(){
    m2=0;
    }
  /*   c.addEventListener('mouseout', moveout, false);
    function moveout(iscontinue){
        if(iscontinue==true){
          if(m>=20){
        ismove=false;
		           m=m-10;
    			 c.style.width=m;
    			 t=setTimeout('moveout(true)',20);
		     }else{
		     ismove=true;
		     }
   
      
        }else{
             if(ismove&&m>=100){
                 ismove=false;
		         m=m-10;
    			 c.style.width=m;
    			 t=setTimeout('moveout(true)',20);
        }
      
		     }
		
    } */
    function gettypedefine(){
	
	var content = Edo.create(
	    {type: 'box',width: '100%',height:'70%',border: [0,0,0,0],padding: [0,0,0,0],layout: 'vertical',horizontalAlign:'center',verticalAlign:'middle',
       	    children: [
       	    //				           
       	    {	type : 'button',text : 'LCA模型',width:150,height:40,align:'center',onclick:function(e){moduleobj.moduletype='LCA';new getlcamoduledefine();this.parent.parent.parent.destroy();}
       	    },
       	    {	type : 'button',text : 'PDM模型',width:150,height:40,align:'center',onclick:function(e){moduleobj.moduletype='PDM';new getPDMmoduledefine();this.parent.parent.parent.destroy();}
       	    }
       	    ]
       	});
       	return content;
       	
       	}
/*     function getnextbar(){
	    var toolbar = Edo.create(
		    {type: 'ct',
		    cls: 'e-dialog-toolbar',
		    width: '100%',
		    layout: 'horizontal',
		    height: '30%',
		    horizontalAlign: 'center',
		    verticalAlign: 'middle',
		    horizontalGap: 10,
		    children: [
		               
		        {
		            id:'mm',
		            type: 'button',
		            text: '下一步',
		            minWidth: 70,
		            onclick: function(e){
		             	if(moduletype=='LCA'){
		             		toolbar=new getlcamoduledefine();
		             		this.parent.parent.parent.destroy();
		             	}else if(moduletype=='PDM'){
		             		toolbar=new getPDMmoduledefine();
		             		this.parent.parent.parent.destroy();
		             	}else if(moduletype==null){
		             	alert('选择构建类型！')
		             	}
		            
		            }
		        },{
		            type: 'button',
		            text: '取消',
		            minWidth: 70,
		            onclick: function(e){
						width:150,height:40,
		
		            }
		        }
		    ]
			});
		return toolbar;
	} */

 	//构建主体框架
 	function modulepaneldefine(){
	    Edo.create(
		    {
	        id:'mdct',
	        type: 'panel',
	       	title: '模型信息',
	        width: 200,
	        height: 500,
	        verticalGap:'0',
	  		padding:[0,0,0,0],
	    	render: document.getElementById('moduledefineContainer'),
	        collapseProperty: 'width',
	        enableCollapse: true,
	        layout:'vertical',
	        titlebar:[
	           {
	            cls:'e-titlebar-toggle-east',
	            icon: 'button',
	            align:'left',
	            onclick: function(e){
	            var b=document.getElementById('moduledefineContainer');
	            if(b.style.width="200px"){
	            b.style.width="0px";
	            resetm();
	            createmb();
	            }
	            }
	            }
	        ]});
             
	     var modulecontent = Edo.create(
	   	    {type: 'box',width: '100%',id:'modulecontent',height:'100%',border: [1,1,1,1],padding: [0,0,0,0],layout: 'vertical',
	      	    children: [
	      	    //				           
	      	    {	type : 'formitem',label : '模板名称:',labelWidth : 80,labelAlign : 'left',
	      	    children : [{type : 'text',width : 80,id : 'modulename'}]
	      	    },
	      	    {	type : 'formitem',label : '模板备注:',labelWidth : 80,labelAlign : 'left',
	      	    children : [{type : 'text',width : 80,id : 'modulenote'}]
	      	    }
	      	   
	      	    ]
	      	});
       Edo.get('mdct').addChild(modulecontent);
       } 
       //创建查看模型信息按钮
    function createmb(){
	    var mb=document.getElementById('modulebutton');
		 mb.style.width='20px';
		 mb.style.height='100px';
		 var e1 = document.createElement("input");  
	     e1.type = "button";
	     //e.style.top='200px';
	     e1.style.width='20px';
	     e1.style.height='100px';
	     e1.style.right='0px';
	     e1.style.position='absolute';
	     e1.value = '查\n看\n模\n型\n';  
	     e1.onclick=function(){
	    	 mb.removeChild(e1);
	    	 move();
	     }
	     mb.appendChild(e1);	
    }
    function lcatreedivdefine(){
       var a=document.getElementById('productContainer');
       a.style.width=0;
        Edo.create(
			              
	    {
        id:'treect',
        type: 'panel',
       	title: '选择零部件',
        width: 200,
        height: 500,
        verticalGap:'0',
  		padding:[0,0,0,0],
    	render: a,
        collapseProperty: 'width',
        enableCollapse: true,
        layout:'vertical',
        titlebar:[
           {
            cls:'e-titlebar-toggle-west',
            icon: 'button',
   			onclick: function(e){
	            if(a.style.width=="200px"){
	            a.style.width="0px";
	            resetm1();
            	}
            }
            }
        ]
              }
			        
        );

       }
       function lcastagedivdefine(){
	       var s=document.getElementById('stageContainer');
	       s.style.width=0;
	        Edo.create(
				              
		    {
	        id:'stagect',
	        type: 'panel',
	       	title: '选择阶段',
	        width: 200,
	        height: 500,
	        verticalGap:'0',
	  		padding:[0,0,0,0],
	    	render: s,
	        collapseProperty: 'width',
	        enableCollapse: true,
	        layout:'vertical',
	        titlebar:[
	           {
	            cls:'e-titlebar-toggle-west',
	            icon: 'button',
	   			onclick: function(e){
		            if(s.style.width=="200px"){
		            s.style.width="0";
		            resetm2();
		            createsb();
	            	}
	            }
            }
        ]
              }
       
			        
        );
       }
</script>
