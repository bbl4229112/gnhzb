<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*,edu.zju.cims201.GOF.hibernate.pojo.MdeResult"%>
<HTML>
	<HEAD>
		<TITLE>FusionCharts Free - Simple Column 3D Chart using dataXML method</TITLE>
        <span style="display: none">hack ie</span>
		<SCRIPT LANGUAGE="Javascript" SRC="../js/FusionChartsFree/FusionCharts.js"></SCRIPT>
	</HEAD>
	<BODY>
		<%
				//柱颜色
				String[] ac = {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D"};
				String strXML = "";
				strXML += "<graph caption='产品2相对于产品1模块化程度评价各指标变化比例(%)' showNames='1'  decimalPrecision='0' decimals='2'  baseFontSize='13' numberSuffix=''>";
                List<String> ln = (List<String>)request.getAttribute("listname");
		        List<Float> lv = (List<Float>)request.getAttribute("listvalue");
		
		        for(int i=0;i<ln.size();i++){
			       if(lv.get(i) != 0)
			       	strXML += "<set name='"+ln.get(i)+"' value='"+lv.get(i)*100+"' color='"+ac[i]+"'/>";
		        }
	
				strXML += "</graph>";
				

				//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
				
			%> 
			<s:include value="/js/FusionChartsFree/FusionChartsRenderer.jsp" > 
			<s:param name="chartSWF" >../js/FusionChartsFree/FCF_Column3D.swf</s:param> 
							<s:param name="strURL" ></s:param> 
							<s:param name="strXML" ><%=strXML%></s:param> 
							<s:param name="chartWidth" value="600" /> 
							<s:param name="chartHeight" value="400" /> 
							<s:param name="wmode">Opaque</s:param>						
			</s:include>
	</BODY>
</HTML>
