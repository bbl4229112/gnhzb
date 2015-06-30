<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*"%>

<HTML>
	<HEAD>
		<TITLE>FusionCharts Free - Simple Column 3D Chart using dataXML
		method</TITLE>
        <span style="display: none">hack ie</span>
		<SCRIPT LANGUAGE="Javascript" SRC="../js/FusionChartsFree/FusionCharts.js"></SCRIPT>

	</HEAD>
	<BODY>
		<CENTER>
			<%
				
				//柱颜色
				String[] ac5 = {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D"};
				String strXML5 = "";
				strXML5 += "<graph caption='个人上传知识类型' showNames='1' decimalPrecision='0' formatNumberScale='0' baseFontSize='12' numberSuffix='篇'>";
				
		        
                List pktl = (List)request.getAttribute("pktl");
		        Map pktm = (Map)request.getAttribute("pktm");
		
		
		        for(int i=0;i<pktl.size();i++){
			       if(!pktm.get(pktl.get(i)).equals("0"))
			       strXML5 += "<set name='"+pktl.get(i)+"' value='"+pktm.get(pktl.get(i))+"' color='"+ac5[i%10]+"'/>";
			 
		        }
	           //  strXML5 += "<set name='daifeng' value='10' color='"+ac5[10%10]+"'/>";
				strXML5 += "</graph>";
				
				
				//Create the chart - Column 3D Chart with data from strXML5 variable using dataXML method
				
			%> 
			<s:include value="/js/FusionChartsFree/FusionChartsRenderer.jsp"> 
			
			                <s:param name="chartSWF" >../js/FusionChartsFree/FCF_Pie3D.swf</s:param> 
							<s:param name="strURL" ></s:param> 
							<s:param name="strXML" ><%=strXML5%></s:param> 
							<s:param name="chartId" > myNext5</s:param>
							<s:param name="chartWidth" >${ktypewidth+50}</s:param> 
							<s:param name="chartHeight">${ktypeheight}</s:param> 
							<s:param name="debugMode" >false</s:param> 	
							<s:param name="registerWithJS" >false</s:param>
							
													
			</s:include>
			
			
			
		</CENTER>
	</BODY>
</HTML>
