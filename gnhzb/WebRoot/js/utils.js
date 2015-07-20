//创建按钮框的方法
//orderBtn下拉按钮
cims201.utils.createBtPanel = function (methodNames,methodFunctions,orderBtn){
	 var panelId = '';
	 var children = new Array();
	 for(var i=0;i<methodNames.length;i++){
	 	panelId += methodNames[i];
	 	children[children.length] = {
                type: 'button',
                text: methodNames[i],
                onclick: methodFunctions[i]                 
            };
	 }
	 if(orderBtn!=null){
	 	panelId += "排序";
	 	children[children.length]=orderBtn;
	 }
	 
	 var myBtPanel = Edo.create({
	 	//id: panelId,
	 	type: 'box',
        width: '100%',    
        padding: 2,    
        layout: 'horizontal',
        verticalAlign: 'bottom',
        horizontalAlign: 'left',
        cls: 'e-toolbar',                
        children:children
        
    });
	
	return myBtPanel;
}

cims201.utils.createNameBtPanel = function (wordLabelid,wordName,methodNames,methodFunctions){
 	 var panelId = '';
	 var children = new Array();
	 children[children.length] = {
	 			id:wordLabelid,
                type: 'label',
                text: wordName                            
            };
            
	 for(var i=0;i<methodNames.length;i++){
	 	panelId += methodNames[i];
	 	children[children.length] = {
                type: 'button',
                text: '<span style="color:blue;">'+methodNames[i]+'</span>',               
                cls: 'e-toolbar', 
                
                onclick: methodFunctions[i]                 
            };
	 }
	 
	 var myBtPanel = Edo.create({
	 	//id: panelId,
	 	type: 'box',
        width: '100%',    
        padding: 2,    
        layout: 'horizontal',
        verticalAlign: 'bottom',
        horizontalAlign: 'left',
        cls: 'e-toolbar',                
        children:children
        
    });
	
	return myBtPanel;
}


//通过ajax从后台取数据
cims201.utils.getData = function(url,params){
// 从服务器端获取树的数据
	var data;
	Edo.util.Ajax.request({
		url : url,
		type : 'post',
		params : params,
		async : false,
		onSuccess : function(text) {
			// text就是从url地址获得的文本字符串
			if(text == null || text == ''){
				data = null;
			}else{
		
				data = Edo.util.Json.decode(text);
			}			
		},
		onFail : function(code,a,b,c,d,e,f) {
			// code是网络交互错误码,如404,500之类
			if(code=="500")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统内部错误！是可能有误操作，如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=="403")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '您没有权限该操作！如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=="404")
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统错误！没有找到该页面',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});				
			
			data = 'error'; 
		}
	});
	
	return data;
};
//JCCBZ 2014.06.13
cims201.utils.getData_Boolean = function(url,params){
	var data=false;
	Edo.util.Ajax.request({
		url : url,
		type : 'post',
		params : params,
		async : false,
		onSuccess : function(text) {
			if(text =='yes'){
		
				data =true;
			}else{
				data =false;
			}			
		},
		onFail : function(code) {
			alert(code);
		}
	});
	return data;
};

//通过ajax从后台取数据,通过异步方式
cims201.utils.getData_Async = function(url,params,callback){
// 从服务器端获取树的数据
	var data;
	Edo.util.Ajax.request({
		url : url,
		type : 'post',
		params : params,
		async : true,
		onSuccess : callback,
		onFail : function(code,a,b,c,d,e,f) {
			// code是网络交互错误码,如404,500之类
			if(code=500)
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统内部错误！是可能有误操作，如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=403)
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '您没有权限该操作！如果还有问题请联系管理员',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});
			if(code=404)
			    	Edo.MessageBox.show({
								title : '警告！',
								msg : '系统错误！没有找到该页面',
								buttons : Edo.MessageBox.CANCEL,
								callback : null,
								icon : Edo.MessageBox.WARNING
							});				
			
			data = 'error'; 
		}
	});
	
	return data;
};

cims201.utils.saveData = function(url,params){
	// 从服务器端获取树的数据
	//alert(params);
		Edo.util.Ajax.request({
			url : url,
			type : 'post',
			params : params,
			async : false,
			onSuccess :function(text) {
				Edo.MessageBox.show({
					title : '成功！',
					msg : '保存成功',
					buttons : Edo.MessageBox.CANCEL,
					callback : null,
					icon : Edo.MessageBox.SUCCESS
				});
			},
			onFail : function(code,a,b,c,d,e,f) {
				// code是网络交互错误码,如404,500之类
				if(code=="500")
				    	Edo.MessageBox.show({
									title : '警告！',
									msg : '系统内部错误！是可能有误操作，如果还有问题请联系管理员',
									buttons : Edo.MessageBox.CANCEL,
									callback : null,
									icon : Edo.MessageBox.WARNING
								});
				if(code=="403")
				    	Edo.MessageBox.show({
									title : '警告！',
									msg : '您没有权限该操作！如果还有问题请联系管理员',
									buttons : Edo.MessageBox.CANCEL,
									callback : null,
									icon : Edo.MessageBox.WARNING
								});
				if(code=="404")
				    	Edo.MessageBox.show({
									title : '警告！',
									msg : '系统错误！没有找到该页面',
									buttons : Edo.MessageBox.CANCEL,
									callback : null,
									icon : Edo.MessageBox.WARNING
								});				
				
				data = 'error'; 
			}
		});
	}

/**
返回一个window窗口
*/
cims201.utils.getWin = function(width,height,title,children){
	var win = new Edo.containers.Window();

	win.set('title', title);
	win.set('titlebar',
	    [      //头部按钮栏
	        {
	            cls: 'e-titlebar-close',
	            onclick: function(e){
	                //this是按钮
	                //this.parent是按钮的父级容器, 就是titlebar对象
	                //this.parent.owner就是
	           
	                this.parent.owner.hide();       //hide方法
	            }
	        }
	    ]
	);
	//win.set('border',[1,1,1,1]);
	
	win.addChild({
	    type: 'box',
	    width: width,
	    height: height,    
	    border: [1,1,1,1],
	    children: children
	});	
	return win;
}


cims201.utils.getWinforkview = function(width,height,title,children,kid){
	var win = new Edo.containers.MyWindow();

	win.set('title', title);
	win.set('titlebar',
	    [      //头部按钮栏
	        {
	            cls: 'e-titlebar-close',
	            onclick: function(e){
	                //this是按钮
	                //this.parent是按钮的父级容器, 就是titlebar对象
	                //this.parent.owner就是
	           
	                this.parent.owner.hide();  //hide方法
	                var knowledgeModule=Edo.get('knowledgeModule'+kid);
	                if(null!=knowledgeModule)
	                 Edo.get('knowledgeModule'+kid).set('visible',true);
	                
	                //deleteMask();
	            }
	        }
	    ]
	);
	//win.set('border',[1,1,1,1]);
	
	win.addChild({
	    type: 'box',
	    width: width,
	    height: height,    
	    border: [1,1,1,1],
	    children: children
	});	
	
		win.addChild({
		type:'box',
	     padding: [1,1,1,width/2-10],	
	     border: [0,0,0,0],	
	     children:[
	         {	
	    type: 'button',
	    text:'关闭窗口',
        onclick: function(e){
	                //this是按钮
	                //this.parent是按钮的父级容器, 就是titlebar对象
	                //this.parent.owner就是
	           
	                this.parent.parent.hide();  //hide方法
	                var knowledgeModule=Edo.get('knowledgeModule'+kid);
	                if(null!=knowledgeModule)
	                 Edo.get('knowledgeModule'+kid).set('visible',true);
	                
	                //deleteMask();
	            }
	   
	    //children: children
	}
	     
	     
	     ]
	    }
			
	);	
	
	return win;
}


cims201.utils.getWinforcdtree = function(width,height,title,children){
	
	var win = new Edo.containers.MyWindow();

	win.set('title', title);
	win.set('titlebar',
	    [      //头部按钮栏
	        {
	            cls: 'e-titlebar-close',
	            onclick: function(e){
	                //this是按钮
	                //this.parent是按钮的父级容器, 就是titlebar对象
	                //this.parent.owner就是
	           
	                this.parent.owner.hide();       //hide方法
	                //deleteMask();
	            }
	        }
	    ]
	);
	win.addChild({
	    type: 'box',
	    width: width,
	    height: height,    
	    border: [1,1,1,1],
	    children: children
	});	
	

	
	return win;
}







/**
表单验证函数
*/
//表单验证器函数
cims201.utils.validate.noEmpty = function(v){
    if(v == "") return "不能为空";
}
cims201.utils.validate.mustPassword = function(v){
    if(v != '12345') return "密码必须是12345";
}
cims201.utils.validate.dateLimit = function(v){
    if(!v) return "必须选择日期";
    if(v < new Date(2020, 1,1)) return "日期不能小于2020年2月1号";
}

/**
*提示窗口函数
*/
cims201.utils.warn = function(title,icon,msg,bt,callback){
	Edo.MessageBox.show({
        //autoClose: 2000,
        title: title?title:'提醒',
        //width: 300,
        icon: icon?icon:Edo.MessageBox.WARNING,
        msg: msg,
        callback: callback,
        buttons: bt?bt:Edo.MessageBox.YESNO
    }); 
}

/**
生成加载界面
*/
    
function createLoading(){  
    deleteMask();
    var str = '';
	str += '<div id="cims201-mask-1" style="height: 300px; overflow: hidden;position:absolute;left:0px;top:0px;width:100%;height:100%;z-index: 10000;overflow:hidden;">';
  	str += '<div style="position: absolute;top:0;left:0;-moz-opacity: 0.3;opacity: .30;filter: alpha(opacity=30);background-color:#777;width: 100%;height: 100%;zoom: 1;height: 3000px;background:url(large-loading.gif) center center no-repeat;width:32px;height:32px;position:absolute;left:50%;top:30%;"></div>';
  	str += '</div>';
	Edo.util.Dom.append(document.body, str);
}  

function createMask(){
	deleteMask();
	var str = '';
	str += '<div id="cims201-mask-1" style="height: 300px; overflow: hidden;position:absolute;left:0px;top:0px;width:100%;height:100%;z-index: 9000;overflow:hidden;">';
  	str += '<div style="position: absolute;top:0;left:0;-moz-opacity: 0.3;opacity: .30;filter: alpha(opacity=30);background-color:#777;width: 100%;height: 100%;zoom: 1;height: 3000px;"></div>';
  	str += '</div>';

  	Edo.util.Dom.append(document.body, str);
}

function deleteMask(){
	for(var i=1;i<=5;i++){
		var deleteNode = document.getElementById('cims201-mask-'+i);  
		if(deleteNode){
			Edo.util.Dom.remove(deleteNode);
		}	
	}  
}   

//根据组件长度进行断句的工具
cims201.utils.addBrToText = function addBrToText(text,width,firstTab){
	if(text != null && text != ''){
		var tl = text.length;
		var rStr = '';
		var i=0;
		
		if(firstTab != null && firstTab != '' && firstTab != 0){
			if(tl < (width-firstTab)){
				i=tl;
				rStr += text;
			}else{
				i = width-firstTab;
				rStr += text.substring(0,(width-firstTab))+'<br>';
			}
		}
		
		while((i+width)<tl){
			rStr += text.substring(i,(i+width))+'<br>';
			i = i+width;
		}
		rStr += text.substring(i,tl);
		return rStr;	
	}else{
		return '';
	}	
}
      
//获取屏幕尺寸
cims201.utils.getScreenSize = function(){

	var infoHeight = document.body.scrollHeight; 
	var infoWidth = document.body.scrollWidth; 
	
	//var bottomHeight = document.getElementById("footer").scrollHeight; 
	var allHeight = document.documentElement.clientHeight; 
	var allHeight2 = document.body.clientHeight; 
	
	var allWidth = document.documentElement.clientWidth; 
	var allWidth2 = document.body.clientWidth; 
	
	if(allHeight==0&&allHeight2!=0)
		 allHeight=allHeight2;
		 
	if(allWidth==0&&allWidth2!=0)
		 allWidth=allWidth2;	 
	 //var bottom = document.getElementById("footer"); 
	//if((infoHeight + bottomHeight) < allHeight){ 
	//	  bottom.style.position = "absolute"; 
	//	  bottom.style.left = "0"; 
	//	  bottom.style.bottom = "15"; 
	 //}else{ 
	//	  bottom.style.position = ""; 
	//	  bottom.style.bottom = ""; 
	 //}  
	return {height:allHeight, width:allWidth};
}      


//判断浏览器版本
cims201.utils.getExploreVersion = function(){
	var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    if (window.ActiveXObject)
        Sys.ie = ua.match(/msie ([\d.]+)/)[1];
    else
   		Sys.firefox = 'firefox';
    //以下进行测试
    //if(Sys.ie) alert(Sys.ie);
    //if(Sys.firefox) alert('Firefox: '+Sys.firefox);
	return Sys;
}
    
//从子窗口创建一个新的tab
function openNewTabFromChild(e,child){

	var parent = child.parent;
	var idx	= e.selected.id;
	var taburl=e.selected.url;
	var tabname=e.selected.name;
	
	
	var mainTabBar = parent.mainTabBar;
	var mainTabContent = parent.mainTabContent;
	
	var tabSelectedIndex = mainTabBar.selectedIndex;
	var tabSize = mainTabBar.children.length;
	
	var c = null;
	mainTabBar.children.each(function(o){		
		if(('tbar_'+idx) == o.id){
			c = o;		
		}
	});
	
	if(c==null){	
		c = mainTabBar.addChildAt(tabSize,
			{id:'tbar_'+idx,type: 'button',text: tabname,arrowMode: 'close',
			    onarrowclick:function(e){
					//根据idx, 删除对应的容器
					
					var currentContent = null;
					mainTabContent.children.each(function(o){
						if(('cont_'+idx) == o.id){
							currentContent = o;
						}
					});
					if(currentContent != null){
						currentContent.destroy();
					}			
					//选中原来Index处					
					var tabitem = mainTabBar.getChildAt(tabSelectedIndex);					
					if(!tabitem){
					    tabitem = mainTabBar.getChildAt(tabSelectedIndex-1);						    
					}
					
					mainTabBar.set('selectedItem', tabitem);				
				}
			}
		);
		
		var module = mainTabContent.addChildAt(mainTabBar.children.length,
			{
				id:'cont_'+idx,type:"module",width: '100%',height: '100%',style: 'border:0'
			}
		);		
		module.load(taburl);
	
	};
	mainTabBar.set('selectedItem', c);		
}