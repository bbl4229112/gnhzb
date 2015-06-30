/*
 * 根据index创建对应的组件
 */
function moduleFactory(){
  var mycompent;
  var child={};
  	 //{nodeType:type,width:number,height:hei,text:'',attr:{},child:{}}
	this.createNode=function (obj){
		mycompent=createChildNode(obj);
		for(key in child){
			mycompent[key]=child[key];
		}
	   return	mycompent;
	};
	//调用发发创建节点
	function createChildNode(obj){
	 var compent;
	    //增加按钮
		if("linkbutton"==obj.type){
			compent=document.createElement("a");	
		}else if("easyui-combobox"==obj.type){
			compent=document.createElement("select");
		}else if("easyui-combotree"==obj.type){
		    compent=document.createElement("input");
		}else if("easyui-datebox"==obj.type){
			compent=document.createElement("input");
		}else if("textlabel"==obj.type){
			
		}else{
			//万能构造
			compent=document.createElement(obj.type);
		}
		//采用回调的方法增加子节点
		for(var i=0;obj.child&&obj.child[i];i++){
			var childNode=createChildNode(obj.child[i]);
			compent.appendChild(childNode);
			
		}
		createAttrNode(obj,compent);
		return compent;
	}
  //创建节点属性
  function createAttrNode(obj,compent){
	  var attr=obj.attr;     //属性
	  var style=obj.style;   //样式
	  var envets=obj.events;
	  var name=obj.name;
	//  var extend=obj.extend;
	  for(key in attr){
			 var objattr=document.createAttribute(key);
			 objattr.value=attr[key];
			 compent.setAttributeNode(objattr);
	  };
	  //设定样式	 
	  for(key in obj.style){
		 compent.style[key]=style[key];
	  }
	  //设定响应事件
	  for(key in obj.events){
		 compent[key]=obj.events[key];
	  }
		//如果存在文本节点
	  if(obj.text){
		  compent.appendChild(document.createTextNode(obj.text));
		}
	  if(name){
		  child[name]=compent;
	  }
	  //设定执行事件
	  if(obj.callback){
		  obj.callback(compent);
	  }
  }
  //创建组件
  this.createTextlabel=function (label,inputText,customstyle){
	  if(!label.style){
		  label.style={marginRight:"10px"};
	  }
	  if(!inputText){
		  inputText={};
	  }
	  if(!customstyle){
		  customstyle={attr:{},style:{}};
	  }

	  var span=this.createNode({
		   type:"span",
		   style:customstyle.style,
		   attr:customstyle.attr
	  });
	  var mylabel=this.createNode({
		  type:"span",
		  text:label.text,
		  attr:label.attr,
		  style:label.style
	  });
	  var input=this.createNode({
		  type:"input",
		  attr:inputText.attr,
		  style:inputText.style
	  });
	  span.appendChild(mylabel);
	  span.appendChild(input);
	  span["label"]=label;
	  span["input"]=input;
	  return span;
  };
  //半透明膜
//  this.droppanel=function(ele,par){ 
//	  var droppanel=this.createNode({
//		  type:"div",
//		  style:par.style,
//		  attr:par.attr
//	   });
//
//  };
};