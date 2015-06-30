 var height = window.screen.height;  
 var width = window.screen.width;  
 var leftW = 300;  
 if(width>1200){  
    leftW = 650;  
 }else if(width>1000){  
    leftW = 400;  
 }else {  
    leftW = 100;  
 }  
   
 var _html = "<div id='loading' style='position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#B0C4DE;opacity:0.8;filter:alpha(opacity=100);'>\  <div style='position:absolute; font-size:12px; cursor1:wait;left:"+leftW+"px;top:300px;width:auto;height:16px;padding:12px 20px 10px 30px;\  background:#fff url(img/20080320132843661.gif) no-repeat scroll 5px 10px;border:2px solid #ccc;color:#000;'>\  正在加载，请等待...   \  </div></div>";  
   
 window.onload = function(){  
    var _mask = document.getElementById('loading');  
    _mask.parentNode.removeChild(_mask);  
  
    var isAdmin =com_zyct_getDataAsyn('privilege/show-button!whetherShowSystemManagerBtn.action',null,null);

    if(!isAdmin.show){
    	$('#privilegetebtn').linkbutton('disable');
    	$("#privilegetebtn").css({display:"none"});   	   
    	   }
    if(nowuser.sex=='女')
    	$("#privtateInforbtn").linkbutton({iconCls:"icon-woman"});   	   
  
 };
  
       
 document.write(_html);  