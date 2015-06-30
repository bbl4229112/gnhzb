<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'smc.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath %>js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
	<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
	    <script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
  </head>
  
  <body>
    
  </body>
</html>
<script type="text/javascript">
	//知识共建模块 
	var cobuilddata = [
		            {text: '直接协同', value: 'COL'},
		            {text: '知识问答', value: 'KQA'},
		            {text: '专家协同', value: 'EXC'},
		            {text: '用户协同', value: 'USC'},
		            {text: '数据协同', value: 'DAC'},
		            {text: '标准协同', value: 'STC'},
		            {text: '专利协同', value: 'PAC'},
		            {text: '新闻协同', value: 'NEC'}
		        ];
	var cobuildmodulesbox = new createCheckGroup('cobuild','  知识共建模块：','180px',cobuilddata).getCheckgroup();
	//知识有序化模块 
	var coorderdata = [
		            {text: '知识地图', value: 'KMP'},
		            {text: '知识审批', value: 'KAP'},
		            {text: '知识分级', value: 'KRK'},
		            {text: '知识分类', value: 'KCA'},
		            {text: '知识评分', value: 'KSC'},
		            {text: '知识评价', value: 'KEV'},
		            {text: '知识标签', value: 'KTG'},
		            {text: '知识价值', value: 'KVA'},
		            {text: '业务建模', value: 'BMO'},
		            {text: '节点知识关联', value: 'NKB'}
		        ];
	var coordermodulesbox = new createCheckGroup('coorder','知识有序化模块：','180px',coorderdata).getCheckgroup();
	//知识推送模块
	var kpushdata = [
		            {text: '推荐相关知识', value: 'PRK'},
		            {text: '推荐问题回答', value: 'PQA'},
		            {text: '推荐专家', value: 'PEX'},
		            {text: '知识订阅', value: 'KSU'},
		            {text: '兴趣模型推送', value: 'PIM'}
		        ];
	var kpushmodulesbox = new createCheckGroup('kpush','知识推送模块：','180px',kpushdata).getCheckgroup();
	//知识挖掘模块
	var kminingdata = [
		            {text: '知识浏览人数', value: 'KSP'},
		            {text: '知识浏览次数', value: 'KSCN'},
		            {text: '知识下载次数', value: 'KDN'},
		            {text: '知识评价次数', value: 'KEN'},
		            {text: '知识评分排行', value: 'KSR'},
		            {text: '知识统计分析', value: 'KSA'}
		        ];
	var kminingmodulesbox = new createCheckGroup('kmining','知识挖掘模块：','180px',kminingdata).getCheckgroup();
	//知识协同导航模块
	var knavdata = [
		            {text: '节点知识导航', value: 'NKN'},
		            {text: '知识精确导航', value: 'KEN'},
		            {text: '知识快速导航', value: 'KRN'},
		            {text: '知识多维导航', value: 'KMN'}
		        ];
	var knavmodulesbox = new createCheckGroup('knav','知识协同导航模块：','180px',knavdata).getCheckgroup();
	//用户有序化模块
	var userorderdata = [
		            {text: '用户知识数量', value: 'UKN'},
		            {text: '用户知识评分', value: 'UKS'},
		            {text: '用户知识浏览次数', value: 'UKSN'},
		            {text: '用户知识下载次数', value: 'UKDN'},
		            {text: '用户评论采纳次数', value: 'UEAN'},
		            {text: '用户回答采纳次数', value: 'UAAN'},
		            {text: '用户兴趣模型', value: 'UIM'}
		        ];
	var userordermodulesbox = new createCheckGroup('userorder','用户有序化模块：','180px',userorderdata).getCheckgroup();
	//知识服务模块
	var kservicedata = [
		            {text: '知识检索', value: 'KSE'},
		            {text: '知识浏览', value: 'KSN'},
		            {text: '知识下载', value: 'KDL'},
		            {text: '知识收藏', value: 'KKP'},
		            {text: '知识编辑', value: 'KED'},
		            {text: '知识版本管理', value: 'KEM'},
		            {text: '知识删除', value: 'KDT'}
		        ];
	var kservicemodulesbox = new createCheckGroup('kservice','知识服务模块：','180px',kservicedata).getCheckgroup();
	//系统功能模块
	var sfunctiondata = [
		            {text: '权限配置', value: 'PCF'},
		            {text: '角色配置', value: 'RCF'},
		            {text: '知识类型配置', value: 'KTCF'},
		            {text: '积分配置', value: 'SCF'},
		            {text: '个人名片', value: 'PIC'},
		            {text: '个人任务', value: 'PTA'},
		            {text: '个人仓库', value: 'PWH'},
		            {text: '系统消息', value: 'SMG'}
		        ];
	var sfunctionmodulesbox = new createCheckGroup('sfunction','系统功能模块：','180px',sfunctiondata).getCheckgroup();
	
    	Edo.create({
    		id : 'allcheckgroup',
    		type: 'formitem',
    		render: document.body,
    	    width: cims201.utils.getScreenSize().width,
            height: cims201.utils.getScreenSize().height-20,
    		border: [1,1,1,1],
    		padding: [10,10,10,10],
    		layout: 'vertical',
    		children: [
    			{
			    	type: 'label',
			    	height:'50',
			    	border:[2,2,2,2],
			    	padding: [10,10,10,10], 
			    	text: '<font size="5">&nbsp&nbsp&nbsp欢迎来到功能模块配置平台！ 请勾选需要屏蔽的功能组件！</font>'
    			},
    			cobuildmodulesbox,
    			coordermodulesbox,
    			kpushmodulesbox,
    			kminingmodulesbox,
    			knavmodulesbox,
    			userordermodulesbox,
    			kservicemodulesbox,
    			sfunctionmodulesbox,
    			{
    				type : 'button',
    				text: '确定配置',
    				onclick : function(e){
    					alert(cobuild.getValue());
    					alert(coorder.getValue());
    					alert(kpush.getValue());
    					alert(kmining.getValue());
    					alert(knav.getValue());
    					alert(userorder.getValue());
    					alert(kservice.getValue());
    					alert(sfunction.getValue());
    				}
    			}
    		]
    	});
function createCheckGroup(cgid,modulename,itemWidth,cgdata){

	var currentCG = Edo.create({
		type : 'formitem',
		label:'<b>'+modulename+'</b>',
		labelWidth: 140,
		children : [
			{
				id : cgid,
				type : 'checkgroup',              
		        repeatDirection: 'horizontal',
		        repeatItems: 6,
		        repeatLayout: 'table',       
		        itemWidth: itemWidth,
		        valueField: 'text',
		        data: cgdata
    		}
			
		]
		
	});
	
	this.getCheckgroup = function(){
		return currentCG;
	}
}
</script>
