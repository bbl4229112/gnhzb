<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'aaa.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link href="<%=basePath%>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/edo/edo.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/index.js" type="text/javascript"></script>
	</head>

	<body>
		<button onclick="createNewTab('knowledge123');">
			b1
		</button>
		<br>
		<button onclick="createNewTab1('knowledge456');">
			b2
		</button>
		<button onclick="createNewTab2('knowledge789');">
			b3
		</button>
		<br>
		<button onclick="createWin();">
			b4
		</button>

		<p align="center">
			<embed id="top10movie" name="top10movie"
				pluginspage="http://www.macromedia.com/go/getflashplayer"
				src="http://localhost:8080/caltks/knowledge/viewfile!view.action?fileId=7" width="650"
				height="488" type="application/x-shockwave-flash" menu="false"
				quality="high" /></embed />
		</p>

	</body>

</html>

<script type="text/javascript">
 
 	function createNewTab(id){
 	var tt = this;
	 	var pp = tt.parent;
	 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= id;
		myTabItem.selected.url= '<%=basePath%>judge.jsp';
		myTabItem.selected.name = 'test';
	 	openNewTabFromChild(myTabItem,pp);
 		
 	}
 	
 	function createNewTab1(id){
 	var tt = this;
	 	var pp = tt.parent;
	 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= id;
		myTabItem.selected.url= '<%=basePath%>dwrMessage.jsp';
		myTabItem.selected.name = '消息';
	 	openNewTabFromChild(myTabItem,pp);
 		
 	}
 	function createNewTab2(id){
 		var tt = this;
	 	var pp = tt.parent;
	 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= id;
		myTabItem.selected.url= '<%=basePath%>message/message!list.action';
		myTabItem.selected.name = '消息列表';
	 	openNewTabFromChild(myTabItem,pp);
 		
 	}
 	
 	function createWin(){
 		var win = Edo.create({
 			type: 'window',
 			title:'弹出面板',
 			titlebar:[      //头部按钮栏
		        {
		            cls: 'e-titlebar-close',
		            onclick: function(e){
		                //this是按钮
		                //this.parent是按钮的父级容器, 就是titlebar对象
		                //this.parent.owner就是
		                this.parent.owner.hide();       //hide方法
		            }
		        }
		    ],
		    children: [{
		    	type: 'box',
			    width: '100%',
			    height: '100%',    
			    children: [
			        { type: 'date', width: '100%' }
			    ]
			    }]
 		});
 		
		
		//show方法
		win.show(200, 100, true);       //true是遮罩, false不遮罩
 		
 	}
 
 </script>


