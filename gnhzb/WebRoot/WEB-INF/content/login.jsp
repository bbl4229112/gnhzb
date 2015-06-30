<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page
	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>基于全生命周期的高能耗装备设计平台</title>
				<!--[if lt IE 7]>
 <style type="text/css">
 img { behavior: url(<%=request.getContextPath()%>/js/iepngfix.htc) }
 </style>
<![endif]-->
		<%@ include file="/common/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/css/login/jquery.js"> </script>
		<script type="text/javascript" src="${ctx}/css/login/jq-easing.js"> </script>
		<script type="text/javascript" src="${ctx}/css/login/sifr.js"> </script>
		<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
		<script src="${ctx}/js/easySlider1.7.js" type="text/javascript"></script>
		<script src="${ctx}/js/validate/jquery.validate.js"
			type="text/javascript"></script>
		<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
		<script>
		$(document).ready(function() {
			$("#loginForm").validate();
			$("#slider").easySlider({
				auto: true, 
				continuous: true,
				numeric: true,
				speed: 800,		
			    pause: 6000
			});
			
		});
		
	</script>
	</head>
	<body>
	<body class="sIFR-active" screen_capture_injected="true">
		<span id="chromeFix"></span>
		<div id="wrapper">
			<div id="header" class="clearfix">
				<div id="logo">
					<h1>

						<img src="${ctx}/css/login/login222.png" height="84"
							width="430" alt="浙江大学现代制造工程研究所" />

					</h1>
				</div>
				
	   
					
			
			</div>
			<div id="main" class="clearfix">
				<div class="box-home-left home-top-left">
					 <div id="slider">
			<ul>	
				<li><a href="user!contentpage.action"><img src="test/images/000.png" alt="Css Template Preview" /></a></li>			
				<li><a href="user!contentpage.action"><img src="test/images/011.png" alt="Css Template Preview" /></a></li>
				<li><a href="user!contentpage.action"><img src="test/images/012.png" alt="Css Template Preview" /></a></li>
				<li><a href="user!contentpage.action"><img src="test/images/013.png" alt="Css Template Preview" /></a></li>
				<li><a href="user!contentpage.action"><img src="test/images/014.png" alt="Css Template Preview" /></a></li>
			</ul>
		</div>
			  </div>
			
			

		<div class="box-home home-top">
					<h3  class="loginbaner">
						<span>【单位简介】</span>
					</h3>
				<MARQUEE onmouseover=this.stop() onmouseout=this.start() scrollAmount="1" scrollDelay="0" direction="up" style="cursor:pointer;height:80px;padding:0px 0pt;">
					<p>
						 &nbsp;&nbsp;&nbsp;&nbsp;浙江大学机械工程学系是二十世纪二十年代至三十年代中国最早开始培养机械门类高级专业人才的少数单位之一。经过近九十年的发展，浙江大学机械工程学系为国家培养了包括中国科学院院长路甬祥院士在内的一批又一批机械工程领域里的英才。该系是国内首批机械工程一级学科博士点单位之一，是首批机械工程博士后流动站单位之一，是首批机械工程国家重点学科单位之一，是中国机械工程学会理事长（路甬祥院士）和副理事长（潘云鹤院士）单位，为推进机械工程学科的进步和机械工程产业的发展，做出了很大的贡献，在国内外产生了很大的影响。
						浙江大学机械工程学系在国内最早从事知识管理和知识挖掘研究，取得国内领先的研究成果。近期获得“机械产品信息配置集成工程研究及应用”等省部级科技进步一等奖、“面向知识创新和大批量定制的知识管理方法研究及应用”、“制造企业知识工程实施支持技术及产品应用”、“面向复杂机电装备的集成产品建模技术及其应用”、“基于互联网的机械零件特征信息抽取和配置设计技术与工具”等省部级科技进步二等奖，发表相关专著《制造企业知识工程理论、方法与工具》、《知识型制造企业》、《企业工程建模方法与企业参考模型》、《现代制造系统工程导论》、《制造业信息化导论》等，在核心期刊发表论文百余篇，并获得授权发明专利1项“分布式知识管理集成系统和方法（专利号：ZL
						200510061382.X）”。<br>
						&nbsp;&nbsp;&nbsp;&nbsp;近年来承担了国家自然科学基金项目、863项目、浙江省科技重大攻关项目多项：863项目“企业间专利资源协同管理模式及其软件使能技术研究”、863项目“基于多学科知识导航、支持复杂产品快速设计的关键技术及其实现”、863项目“基于元模型的复杂产品多学科协同设计技术研究”、863项目“知识管理技术和工具的开发”、863项目“大规模客户化产品协同设计系统的研究与开发”、863项目“制造网格与制造资源协同管理技术”、863项目“PLM实施方法、评价技术与工具研究”、国家科技支撑计划项目“家电行业（企业）的数字化综合集成技术开发与应用”、国家自然科学基金项目“知识集成的组织管理机制、模型和工具的研究”、国家自然科学基金项目“面向知识管理的无比例知识网格研究”、浙江省科技重大攻关项目“面向汽配行业的数字化设计、制造和管理综合集成技术开发与应用示范”、浙江省公共科技条件平台建设项目“浙江省服装产业科技创新服务平台”和“浙江省汽车零部件产业科技创新服务平台”。在一些企业推广应用了知识管理和知识挖掘系统，取得了显著的经济效益和社会效益。
					</p>					
<!--  				<p>
				中国运载火箭研究院创建于1957年11月16日，世界著名科学家钱学森先生任第一任院长，是中国最大的火箭研制基地已形成了完整的集研究、设计、试制、生产和试验为一体的研制实体，为我国航天事业年的发展做出了不可磨灭的贡献，走出了一条具有中国特色发展火箭技术的道路，取得了举世瞩目的成就，跻身于世界先进的行列。研究院为中国航天事业的发展做出了卓越的贡献，共获得3500余项部级以上的科研成果，其中有3项获国家科技进步特等奖。
					</p>
-->					
				</MARQUEE>	
				</div>

				

				<div class="box-home">
				
					<div id="twitter_div">
						<ul id="twitter_update_list">
							<li>
							<%
							if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
						%>
						<div class="error">
							登录失败，请重试.
						</div>
						<%
							}
						%>	
						<form id="loginForm" action="${ctx}/j_spring_security_check"
							method="post" style="margin-top: 1em">
							<table class="noborder">
								<tr>
									<td>
										<label>
											用户名:
										</label>
									</td>
									<td>
										<input type='text' id='j_username' name='j_username'
											style="width:200px;" value="" />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											密码:
										</label>
									</td>
									<td>
										<input type='password' value="" id='j_password' style="width:200px;"
											name='j_password' class="required" />
									</td>
								</tr>
								<tr>
									<td colspan='2' align="right">
										<input type="checkbox" name="_spring_security_remember_me" />
										两周内记住我
										<input value="登录" type="submit" class="button" />
									</td>
								</tr>
							</table>
						</form>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="footer">
				<div id="secondnav" class="clearfix">
					<a href="http://www.calt.casc/" title="浙江大学现代制造工程研究所cims项目组"
						class="fullyIllustrated">浙江大学现代制造工程研究所cims项目组 Copyright &copy;
								2011-2012</a>
					<ul class="right">
						
						<li>
							
							<span> | </span>
						</li>
					</ul>
				</div>
			</div>
		
			

		</div>