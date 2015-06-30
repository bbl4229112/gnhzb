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
				String[] ac3 = {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D"};
				String strXML3 = "";
				strXML3 += "<graph caption='近期知识上传走势' PYAxisName='篇数' PYAxisMinValue='0' PYAxisMaxValue='100' SYAxisMinValue='0' SYAxisMaxValue='100' numDivLines='4' showNames='1' decimalPrecision='0' formatNumberScale='0' baseFontSize='12' numberSuffix='篇' anchorSides='10' anchorRadius='3' anchorBorderColor='009900'>";
				
		        
                List ktrendl = (List)request.getAttribute("ktrendl");
		        Map ktrendm = (Map)request.getAttribute("ktrendm");
		        strXML3 += "<categories>";
		        		
		        for(int i=0;i<ktrendl.size();i++){
			       
			       strXML3 += "<category name='"+ktrendl.get(i)+"' />";
		        }
		        strXML3 += "</categories>";
		        
		        strXML3 += "<dataset seriesName='上传量' color='AFD8F8' showValues='0'>";
		        for(int i=0;i<ktrendl.size();i++){
		//	       System.out.println("上传量---------"+ktrendm.get(ktrendl.get(i)));
			       strXML3 += "<set value='"+ktrendm.get(ktrendl.get(i))+"' />";
		        }
		        strXML3 += "</dataset>";
		        strXML3 += "<dataset seriesName='走势线' color='8BBA00' showValues='0' parentYAxis='S' >";
		        for(int i=0;i<ktrendl.size();i++){
			//       System.out.println("走势线---------"+ktrendm.get(ktrendl.get(i)));
			       strXML3 += "<set value='"+ktrendm.get(ktrendl.get(i))+"' />";
		        }
		        strXML3 += "</dataset>";
	
				strXML3 += "</graph>";
				
				//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
				
			%> 
			<s:include value="/js/FusionChartsFree/FusionChartsRenderer.jsp" > 
							<s:param name="chartSWF" >../js/FusionChartsFree/FCF_MSColumn3DLineDY.swf</s:param> 
							<s:param name="strURL" ></s:param> 
							<s:param name="strXML" ><%=strXML3%></s:param> 
							<s:param name="chartId" > myNext3</s:param>
							<s:param name="chartWidth" >${ktrendwidth}</s:param> 
							<s:param name="chartHeight">${ktrendheight}</s:param> 
							<s:param name="debugMode" >false</s:param> 	
							<s:param name="registerWithJS" >false</s:param> 							
			</s:include>
			
			
			
		</CENTER>
	</BODY>
</HTML>
