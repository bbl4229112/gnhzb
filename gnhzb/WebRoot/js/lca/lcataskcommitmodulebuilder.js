var a=document.getElementById('leftpanel');
var b=document.getElementById('builder');
var c=document.getElementById('detaildiv');
var taskdetail= cims201.utils.getData("task/task!getMytaskdetail.action",{taskid:taskid});
var cell=null;
var levelmodule=null;
//alert(taskdetail.name)
Edo.create({
	  type: 'ct',
	  height: '40', 
	  layout: 'horizontal',
	  render:document.getElementById('topbar'),
	  children:[
	            {
	              type:   'label',width:200,height: '100%',
	              style:  'font-size:20px;padding:5px;padding-top:8px;font-family:微软雅黑, 宋体, Verdana;font-weight:bold; ',
	              text: '任务执行平台'
	            },
	            {
	            	type:'space',width:1000,height: '100%',	
	            },
	            {
	            	type: 'label', text: '您好, admin <a href="#" style="color:black;text-decoration:none;">退出</a>'
	            }
	            ]
          });
Edo.create(
		{
        	id:'leftPanel',
        	type: 'panel',
            title: '任务详细信息',
            width: 260,
            height: 600,
            verticalGap:'10',
		    padding:[10,0,0,10],
            collapseProperty: 'width',
            enableCollapse: true,
            layout:'vertical',
            render:document.getElementById('leftpanel'),
            titlebar:[
                      {
                          cls:'e-titlebar-toggle-west',
                          icon: 'button',
                          onclick: function(e){
                        	  a.style.width='0px';
                        	  b.style.left='0px';
                        	  if( c.style.width!='0px'){
                        		  c.style.width=b.offsetWidth-40;
                        	  }
                        	  createsb();
                        	  resetm1();
                        	  
                        	  }
                      }
                      ],
            children:[
					{type : 'formitem',label : '任务名称:',labelWidth : 80,labelAlign : 'left',
						    children : [{type : 'text',width : 150,text:taskdetail.name,id : 'taskname'}]
				    },
				    {type : 'formitem',label : '构建零件:',labelWidth : 80,labelAlign : 'left',
					    children : [{type : 'text',width : 150,id : 'compname',text:taskdetail.compname}]
				    },
				    {type : 'formitem',label : '执行人:',labelWidth : 80,labelAlign : 'left',
					    children : [{type : 'text',width : 150,text:taskdetail.Carrier,id : 'processperson'}]
				    },
				    {type : 'formitem',label : '创建时间:',labelWidth : 80,labelAlign : 'left',
					    children : [{type : 'text',width : 150,text:taskdetail.Createdate,id : 'createdate'}]
				    },
					                      
           
                        ]
		});

function createsb(){
	 var sb=document.getElementById('stagebutton');
	 sb.style.width='20px';
	 sb.style.height='100px';
	 var e = document.createElement("input");  
    e.type = "button";
    //e.style.top='200px';
    e.style.width='20px';
    //e.style.height='400px';
    e.style.left='0px';
    e.style.position='absolute';
    e.value = '查\n看\n任\n务\n详\n情\n';  
    e.onclick=function(){
   	 sb.removeChild(e);
   	if( c.style.width!='0px'){
   		movep1();
   	}else{
   		movep();
   		
   	}
   	
   	
    }
    sb.appendChild(e);
}
var a=document.getElementById('leftpanel');
//a.addEventListener('mouseover', movep, false);
var t1;
var m1=0;
 //展开零件信息panel
function movep(){ 
	if(m1<260){
	     m1=m1+20;
		 a.style.width=m1;
		 b.style.left=m1;
		 t1=setTimeout('movep()',10);

	}
}
function movep1(){
	if(m1<260){
	 m1=m1+20;
	 a.style.width=m1;
	 c.style.width=c.offsetWidth-20;
	 b.style.left=m1;
	 t1=setTimeout('movep1()',10);

	}
}
function resetm1(){
	m1=0;
}
var t2;
var m2=0;
function movec(width){ 
 if(m2<width){
 m2=m2+40;
 c.style.width=m2;
 t2=setTimeout('movec('+width+')',8);
}
}
function resetm2(){
m2=0;
}
function showdetail(editcell,module){
	/*var astr=b.style.left;
	var str=astr.substring(0,astr.length-2);
	alert(str);*/
	/*c.style.left=b.style.left;
	c.style.right='0px';*/
	movec(b.offsetWidth-40);
	cell=editcell;
	levelmodule=module;
	if(!Edo.get('processmain')){
		new getprocessdetaildefine();
		
	}else{
		openNewTab(stepdata.source[0]);
		
		
	}
	
}