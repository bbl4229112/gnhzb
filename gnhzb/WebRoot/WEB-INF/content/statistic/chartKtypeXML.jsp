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
	<BODY style="background-color: #fff">
		<CENTER>
			<%
				
				//柱颜色
				String[] ac = {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D"};
				String strXML = "";
				if(null!=request.getAttribute("ktypeheight")&&request.getAttribute("ktypeheight").toString().equals("190"))
				strXML += "<graph caption='知识类型分布' showNames='0'  decimalPrecision='0' formatNumberScale='0' baseFontSize='12' numberSuffix=''>";
			else
				strXML += "<graph caption='知识类型分布' showNames='1'  decimalPrecision='0' formatNumberScale='0' baseFontSize='12' numberSuffix=''>";
				
		        
                List ktl = (List)request.getAttribute("ktl");
		        Map ktm = (Map)request.getAttribute("ktm");
		
		
		        for(int i=0;i<ktl.size();i++){
			       if(!ktm.get(ktl.get(i)).equals("0"))
			       strXML += "<set name='"+ktl.get(i)+"' value='"+ktm.get(ktl.get(i))+"' color='"+ac[i%10]+"'/>";
		        }
	
				strXML += "</graph>";
				

				//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
				
			%> 
			<s:include value="/js/FusionChartsFree/FusionChartsRenderer.jsp" > 
			<s:param name="chartSWF" >../js/FusionChartsFree/FCF_Column3D.swf</s:param> 
							<s:param name="strURL" ></s:param> 
							<s:param name="strXML" ><%=strXML%></s:param> 
							<s:param name="chartId" > myNext</s:param>
							<s:param name="chartWidth">${ktypewidth}</s:param> 
							<s:param name="chartHeight" >${ktypeheight}</s:param> 
							<s:param name="debugMode" >false</s:param> 	
							<s:param name="registerWithJS" >false</s:param>
						
							 
							<s:param name="wmode">Opaque</s:param>						
			</s:include>
			
			
			
		</CENTER>
	</BODY>
</HTML>
