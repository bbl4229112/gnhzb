<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*,edu.zju.cims201.GOF.hibernate.pojo.SystemUser, edu.zju.cims201.GOF.rs.dto.PageDTO"%>
<HTML>
	<HEAD>
		<TITLE>FusionCharts Free - Simple Column 3D Chart using dataXML
		method</TITLE>
        <span style="display: none">hack ie</span>
		<SCRIPT LANGUAGE="Javascript" SRC="../js/FusionChartsFree/FusionCharts.js"></SCRIPT>
	</HEAD>
	<BODY>
		<CENTER>
			<%//柱颜色
			    String rankName=(String)request.getAttribute("rankName");
			    
				String[] ac = {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D"};
				String strXML = "";
				
				strXML += "<graph caption='"+rankName+"' xAxisName='user' yAxisMinValue='0' yAxisMaxValue='100' yAxisName='Score' decimalPrecision='0' formatNumberScale='0' baseFontSize='12'>";
				
		        PageDTO pageDTO = (PageDTO)request.getAttribute("pageDTO");	
		        SystemUser currentUser = (SystemUser)request.getAttribute("currentUser");
		        String currentRank = (String)request.getAttribute("currentRank");
		        List<SystemUser> lt = pageDTO.getData();
		
		        for(int i=0;i<lt.size();i++){
			       SystemUser user = lt.get(i);
			       
			     //  strXML += "<set name='"+user.getName()+"' value='"+((double)user.getScoreTemp()/1000000)+"' color='"+ac[i]+"'/>";
			       strXML += "<set name='"+user.getName()+"' value='"+user.getScoreTemp()+"' color='"+ac[i]+"'/>";
			      
		        }
		        if(null!=currentRank&&!"".equals(currentRank))
			       {
			      strXML += "<set name='"+currentUser.getName()+"' value='"+currentUser.getScoreTemp()+"' color='"+ac[10]+"'/>";}
			     //  strXML += "<set name='"+currentUser.getName()+"' value='"+((double)currentUser.getScoreTemp()/1000000)+"' color='"+ac[10]+"'/>";}
				strXML += "</graph>";
				
				//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
					 String rootpath="../js/FusionChartsFree/FCF_Column3D.swf";
			%> 
			<jsp:include page="/js/FusionChartsFree/FusionChartsRenderer.jsp" flush="true"> 
								<jsp:param name="chartSWF" value="<%=rootpath%>" /> 
							<jsp:param name="strURL" value="" /> 
							<jsp:param name="strXML" value="<%=strXML%>" /> 
							<jsp:param name="chartId" value="myNext" /> 
							<jsp:param name="chartWidth" value="800" /> 
							<jsp:param name="chartHeight" value="400" /> 
							<jsp:param name="debugMode" value="false" /> 	
							<jsp:param name="registerWithJS" value="false" /> 							
			</jsp:include>
			
			
			
		</CENTER>
	</BODY>
</HTML>
