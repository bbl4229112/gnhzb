//格式化显示easyui的日期显示方式
$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m<10){
	   m="0"+m;
	}
	if(d<10){
	   d="0"+d;
	}
	return y+'-'+m+'-'+d;
};
//将得到的组织域数据进行二次提取包装为easyui-tree需要的格式
    function dataTj2(data){
      var easyuiJSON=[];
   	  data.each(function(o){
   		  var obj=new Object();
   		  obj.id=o.id;
   		  obj.text=o.name;
   		  if(o.children){
   	  		 obj.children=dataTj(o.children); 
   		  }
   		  easyuiJSON.push(obj);
   	  });
   	   return easyuiJSON;
    }
    function dataTj(data){
        var easyuiJSON=[];
     	  for(var i=0;data&&data[i];i++){
     		  var obj=new Object();
     		  var o=data[i];
     		  obj.id=o.id;
     		  obj.text=o.name;
     		  if(o.children){
     	  		 obj.children=dataTj(o.children); 
     		  }
     		  easyuiJSON.push(obj);
     	  };
     	   return easyuiJSON;
      }

//将object转换为json格式的字符串   
function JSONtoString(obj){
	   var arry=[];
	   if(obj instanceof Array){
		   var string;
		   for(var i=0;obj[i];i++){
			  arry.push(JSONtoString(obj[i]));
		   }
		   return "["+arry.join(",")+"]";
	   }else{
		   for(var key in obj){
			   var string="";
			   if(obj[key] instanceof Array){
				  arry.push("\""+key+"\":"+JSONtoString(obj[key]));
			   }else{
				   if(obj[key] instanceof Object){
					   arry.push("\""+key+"\":"+JSONtoString(obj[key]));
					   }
					   else if(isNaN(obj[key])||obj[key]==""){
					   arry.push("\""+key+"\":\""+obj[key]+"\"");
				   }else{ 
					   arry.push("\""+key+"\":"+obj[key]+"");
				   }		  
			   }
		   }
		   var String="{"+arry.join(",")+"}";
		   return String;
	   }
	  
   }
//将数组转化为json
function arrayToJson(o) { 
	var r = []; 
	if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\""; 
	if (typeof o == "object") { 
	if (!o.sort) { 
	for (var i in o) 
	r.push(i + ":" + arrayToJson(o[i])); 
	if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) { 
	r.push("toString:" + o.toString.toString()); 
	} 
	r = "{" + r.join() + "}"; 
	} else { 
	for (var i = 0; i < o.length; i++) { 
	r.push(arrayToJson(o[i])); 
	} 
	r = "[" + r.join() + "]"; 
	} 
	return r; 
	} 
	return o.toString(); 
	} 
//异步获取数据
function com_zyct_getDataSync(reqUrl,params,callback,datatype){
	if(!datatype){
		datatype="json";
	}
  var data;
	$.ajax({
	   url:reqUrl,
	   type:"POST",
	   data:params,
	   dataType:datatype,
	   async:true,
	   success:callback,
	   statusCode:{500:function(){
		   $.messager.alert("提示","抱歉！系统内部错误，请刷新页面重试或联系管理员","warning");
	   },403:function(){
		   $.messager.alert("提示","您没有该权限","warning");
	   },404:function(){
		   $.messager.alert("提示","对不起，系统没有该页面","warning");
	   }}
	});
	return data;
}
//同步获取数据
function com_zyct_getDataAsyn(reqUrl,params,datatype){
	if(!datatype){
		datatype="json";
	}
  var data;
	$.ajax({
	   url:reqUrl,
	   type:"POST",
	   data:params,
	   dataType:datatype,
	   async:false,
	   success:function(msg){
		  data=msg;
	   },
	   statusCode:{500:function(){
		   $.messager.alert("提示","抱歉！系统内部错误，请刷新页面重试或联系管理员","warning");
	   },403:function(){
		   $.messager.alert("提示","您没有该权限","warning");
	   },404:function(){
		   $.messager.alert("提示","系统没有该页面","warning");
	   }}
	});
	return data;
}
//分页条
function createPageTool(totalpage,pageSize,callback){
	var modulefactory=new moduleFactory();
    var pageToolbar=modulefactory.createNode({
		type:"div",
		attr:{"class":"easyui-pagination",style:"background:#efefef;border:1px solid #ccc;margin-top:20px;"}
	});
    $(pageToolbar).pagination({
    	total:totalpage,
    	pageSize:pageSize,
    	onSelectPage:callback,
    	displayMsg:"总共{total}条数据,显示了从第{from}到第{to}条",
    	beforePageText:"第",
    	afterPageText:"/{pages}页"
    });
    return pageToolbar;
}
//message box 
function newMessage(){
	var modulefactory=new moduleFactory();
}
//创建表格
function newMyTable(theadParams,tbodyParams){
	
	var modulefactory=new moduleFactory();
	var table=modulefactory.createNode({
		type:"table",
	    attr:{"class":"easyui-datagrid"}
	});
	var thead=modulefactory.createNode({
		type:"thead"
	});
	var headtr=modulefactory.createNode({
		type:"tr"
	});
	for(var i=0;theadParams[i];i++){
		var th=modulefactory.createNode({
			type:"th",
			text:theadParams[i]
		});
		headtr.appendChild(th);
	}
	thead.appendChild(headtr);
	var tbody=modulefactory.createNode({
		type:"tbody"
	});
	for(var i=0;tbodyParams[i];i++){
		var tr=modulefactory.createNode({
			type:"tr"
		});
		for(var j=0;tbodyParams[i][j];j++){
			var td=modulefactory.createNode({
				type:"td",
				text:tbodyParams[i][j]
			});
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	table.appendChild(thead);
	table.appendChild(tbody);
	return table;
}
function com_zyct_createTool(toolName,toolImage){
	var modulefactory=new moduleFactory();
	var row1=modulefactory.createNode({
		type:"div",
		style:{marginTop:"10px",cursor:"pointer"}
	});
	var tool=modulefactory.createNode({
		type:"div",
		style:{marginLeft:"20px",marginRight:"20px",minWidth:"80px"},
		child:[{type:"center",
			    child:[
                    {type:"div",
                         child:[{type:"img",attr:{src:toolImage}}],
                         style:{width:"80px",marginLeft:"auto",marginRight:"auto"}},
                    {type:"div",style:{width:"80px",marginLeft:"auto",marginRight:"auto"},text:toolName}
			           ]},
		       
		       ]
	});
	row1.appendChild(tool);
	$(row1).mouseover(function(){
		$(this).css({"background":"#fbec88"});
	});
	$(row1).mouseout(function(){
		$(this).css({"background":"white"});
	});
	return row1;
}

function com_zyct_showBuildingMessage()
{
	$.messager.alert('Sorry',
			'对不起，您请求的功能正在建设中,敬请谅解！', "warning");	

}