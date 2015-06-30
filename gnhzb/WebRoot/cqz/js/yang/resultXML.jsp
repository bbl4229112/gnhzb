<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
	<HEAD>
		<TITLE>FusionCharts Free - Simple Column 3D Chart using dataXML method</TITLE>
        <span style="display: none">hack ie</span>
        <link href="<%=basePath %>js/edo/res/css/edo-all.css" rel="stylesheet"	type="text/css" />
		<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
		<link rel="stylesheet" href="../../css/icon.css" type="text/css"></link>
		
		<script type="text/javascript" src="<%=basePath %>js/FusionChartsFree/FusionCharts.js"></script>
	<%
		//柱颜色

		String[] ac = {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D"};
		String strXML = "";
		
		strXML += "<graph caption='Cabon Footprint of Life Cycle(kg)' showNames='1'  decimalPrecision='2' decimals='2'  baseFontSize='13' numberSuffix=''>";
		/* List<String> ln = (List<String>)request.getAttribute("listname");
		List<Float> lv = (List<Float>)request.getAttribute("listvalue"); */
		List<String> ln = new ArrayList();
		List<Float> lv =  new ArrayList();
		for(int i=0;i<ln.size();i++){
		   
		   	strXML += "<set name='"+ln.get(i)+"' value='"+lv.get(i)+"' color='"+ac[i]+"'/>";
	
		}
		
		strXML += "</graph>";
	%>
<style type="text/css">
/*button*/
.e-btn
{
	color:#2C4D79;
	border:1px solid #A3C0E8; 
    background:#E2F0FF url(../images/button/btn.gif) repeat-x;    
}
.e-btn:hover, .e-btn-over
{
	color:#7e3c3e;	
	border:1px solid #d6a886; 	
	background:#fedc75 url(../images/button/btn-over.gif) repeat-x;
}
.e-btn-pressed:hover, .e-btn-pressed, .e-btn-click:hover, .e-btn-click
{
	color:#7e3c3e;	
	border:1px solid #cb9280; 	
	background:#ffb46d url(../images/button/btn-pressed.gif) repeat-x;
}
.e-btn-arrow-left .e-btn-arrow, .e-btn-arrow-right .e-btn-arrow
{
	background-image:url(../images/button/arrow.gif)
}
.e-btn-split-left .e-btn-arrow, .e-btn-split-right .e-btn-arrow
{
	background-image:url(../images/button/arrow.gif)
}
.e-btn-close-left .e-btn-arrow, .e-btn-close-right .e-btn-arrow
{
	background-image:url(../images/button/close.gif);
	background-position:2px 3px;
	height:12px;
}
.e-btn-close-left .e-btn-arrow, .e-btn-close-right .e-btn-arrow
{
	background-image:url(../images/button/close.gif);
	background-position:2px 3px;
	height:12px;
}
/*.e-btn-split-right .e-btn-arrow
{
	background:url(../images/button/s-arrow.gif) no-repeat 0 50%;
	right:2px;height:16px;margin-top:-8px;
}*/
</style>

	<script type="text/javascript">
			function updateChart(chartSWF){
				var chart1 = new FusionCharts(chartSWF, "chart1Id", "400", "200", "0", "0");
				chart1.setDataXML("<%=strXML%>");
				chart1.render("chart1Div");
			}
		</script>

	</HEAD>
	<BODY>			
	     <div id='chart1Div' align='center'>Chart.</div>  
		<!-- START Script Block for Chart -->
		<script type='text/javascript'>
			var chart1 = new FusionCharts("./../js/FusionChartsFree/FCF_Column3D.swf","chart1Id","400","200","0","0");
			chart1.setDataXML("<%=strXML%>");
			chart1.render("chart1Div");
		</script>	
 <!--  <script type='text/javascript'>
    		Edo.create({
				type: 'box',
				width: '60%',
				height: 20,
				layout: 'horizontal',
				border: [0,0,0,0],
				padding: [400,0,0,0],
				children:[
								{type: 'button',width: 113, text: '柱状图', icon: 'e-icon-column', onClick: function(){javaScript:updateChart('./js/FusionChartsFree/FCF_Column3D.swf');}},
								{type: 'button',width: 162, text: '折线图', icon: 'e-icon-line',onClick: function(){javaScript:updateChart('./js/FusionChartsFree/Line.swf');}},
								{type: 'button',width: 210, text: '饼图', icon: 'e-icon-pie',onClick: function(){javaScript:updateChart('./js/FusionChartsFree/FCF_Doughnut2D.swf');}}
				         ]
				
			})	
</script>
 -->   	
		
		<div align="center">			
	         &nbsp;
	         <input type="button" class="e-btn" value="柱状图" onClick="javaScript:updateChart('./../js/FusionChartsFree/FCF_Column3D.swf');" name="btnColumn" />&nbsp;
	         <input type="button" class="e-btn" value="折线图" onClick="javaScript:updateChart('./../js/FusionChartsFree/Line.swf');" name="btnLine" />&nbsp;
	         <input type="button" class="e-btn" value="饼图" onClick="javaScript:updateChart('./../js/FusionChartsFree/FCF_Doughnut2D.swf');" name="btnPie" />         
		</div>	 				 		
	</BODY>
</HTML>
