<%@page import="com.lowagie.text.Document"%>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>
	创新知识管理门户，免费下载，免费浏览，知识共享
</title>
	<link rel="shortcut icon" href="<%=basePath %>images/favicon.ico" />
	<link rel="icon" type="image/gif" href="<%=basePath %>images/favicon.ico" />
	<link href="<%=basePath %>css/base.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/menu.css" rel="stylesheet" />
	<link href="<%=basePath %>css/20130424.css" rel="stylesheet" />
	<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/jquery.1.5.2.pack.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/Default.js" type="text/javascript"></script>
    
   	<script type="text/javascript">
	    //设置用户排行榜的内容 
	    //var newsdata =  cims201.utils.getData('statistic/khotlist!list.action',{listtype:'viewCount', index:0, size:10});
	    var userrankdata = cims201.utils.getData('userranking/userranking!listTopFourUsers.action',{model_:'all',size:4,index:0});
	    for(var i = 0;i<4;i++){
		    $("#userrank"+(i+1)).append('<img src=thumbnail/'+userrankdata[i].picturePath+' width="76" height="83" alt="" /></a>');
		    $("#userrank"+(i+1)+"_name").append(userrankdata[i].name);
	    }
	    var hotwords = '';
	    cims201.utils.getData_Async('knowledge/knowledge!listHotword.action',{},function(text){
			if(text != null && text != ''){
				var data = Edo.util.Json.decode(text);
				data.each(function(o){
					$("#hotwords_id").append('<a href=javascript:openNewTab("'+(o.name)+'","knowledgelist_fulltext","'+(o.name)+'",{key:"'+o.name+'"});>'+o.name+'</a>');
				});
			}
		});
		//一周热点资讯的数据
	    var newsdata =  cims201.utils.getData('statistic/khotlist!listNews.action',{listtype:'commentrecord.commentCount', index:0, size:7});
		var newstext = "";
	    for(var i = 0; i < newsdata.length;i++){
	    	newstext += '<li><a class="fc_gray6" href="<%=basePath %>kshow.action?kid='+newsdata[i].id+'">['+newsdata[i].uploader.name+'] '+concatText(newsdata[i].titleName,20)+' ('+newsdata[i].commentRecord.commentCount+'条)</a><span class="fc_orange"></span></li>';
	    }
		$("#hotnews_id").append(newstext);
		//一周热点知识的数据
	    var hotknowledgedata =  cims201.utils.getData('statistic/khotlist!list.action',{listtype:'commentCount', index:0, size:7});
		var hotknowledgetext = "";
	    for(var i = 0; i < hotknowledgedata.length;i++){
	    	hotknowledgetext += '<li><a class="fc_gray6" href="<%=basePath %>kshow.action?kid='+hotknowledgedata[i].id+'">['+hotknowledgedata[i].uploader.name+'] '+concatText(hotknowledgedata[i].titleName,20)+' ('+hotknowledgedata[i].commentRecord.commentCount+'条)</a><span class="fc_orange"></span></li>';
	    }
		$("#hotknowledges_id").append(hotknowledgetext);
	    //最新知识的数据
	    var latestknowledgedata =  cims201.utils.getData('statistic/khotlist!list.action',{listtype:'viewCount', index:0, size:8});
		var latestknowledgetext = "";
	    for(var i = 0; i < latestknowledgedata.length;i++){
	    	latestknowledgetext += '<div class="bl_1box "><div><a href="<%=basePath %>kshow.action?kid='+latestknowledgedata[i].id+'" class="fc_gray6">'+concatText(latestknowledgedata[i].titleName,15)+'</a></div><div class="clearfix"><span class="l">用户：<a class="fc_blue2" href="/member/131378">'+latestknowledgedata[i].uploader.name+'</a></span><span class="r fc_gray9">'+latestknowledgedata[i].uploadTime+'</span></div></div>'
	    }
		$("#latestknowledge_id").append(latestknowledgetext);
		//热门问题的数据
	    var hotquestiondata =  cims201.utils.getData('statistic/khotlist!listHotQuestions.action',{listtype:'commentCount', index:0, size:8});
		var hotquestiontext = "";
	    for(var i = 0; i < hotquestiondata.length;i++){
	    	hotquestiontext += '<div class="bl_1box "><div><a href="<%=basePath %>kshow.action?kid='+hotquestiondata[i].id+'" class="fc_gray6">'+concatText(hotquestiondata[i].titleName,15)+' ('+hotquestiondata[i].commentRecord.commentCount+'条)</a></div><div class="clearfix"><span class="l">用户：<a class="fc_blue2" href="/member/131378">'+hotquestiondata[i].uploader.name+'</a></span><span class="r fc_gray9">'+hotquestiondata[i].uploadTime+'</span></div></div>'
	    }
		$("#hotquestion_id").append(hotquestiontext);
		//最新资讯的数据
	    var latestnewsdata =  cims201.utils.getData('statistic/khotlist!listNews.action',{listtype:'uploadtime', index:0, size:7});
		var latestnewstext = "";
	    for(var i = 0; i < latestnewsdata.length;i++){
	    	latestnewstext += '<li class="noBorder"><a class="fc_blue2" href="<%=basePath %>kshow.action?kid='+latestnewsdata[i].id+'">• '+concatText(latestnewsdata[i].titleName,15)+'</a></li>'
	    }
		$("#latestnews_id").append(latestnewstext);
		//行业知识-服装的数据
	    var categorygarmentdata =  cims201.utils.getData('tree/tree!listSubNode.action',{nodeId:78});
		var categorygarmenttext = "";
	    for(var i = 0; i < categorygarmentdata[0].children.length;i++){
	    	categorygarmenttext += '<li><a href="/data/list/602002">'+categorygarmentdata[0].children[i].name+'</a></li>';
	    }
		$("#category_garment_id").append(categorygarmenttext);
		//行业知识-汽车零部件的数据
	    var categorycarpartsdata =  cims201.utils.getData('tree/tree!listSubNode.action',{nodeId:75});
		var categorycarpartstext = "";
	    for(var i = 0; i < categorycarpartsdata[0].children.length;i++){
	    	categorycarpartstext += '<li><a href="/data/list/602002">'+categorycarpartsdata[0].children[i].name+'</a></li>';
	    }
		$("#category_carparts_id").append(categorycarpartstext);
		//行业知识-变压器的数据
	    var categorytransformerdata =  cims201.utils.getData('tree/tree!listSubNode.action',{nodeId:76});
		var categorytransformertext = "";
	    for(var i = 0; i < categorytransformerdata[0].children.length;i++){
	    	categorytransformertext += '<li><a href="/data/list/602002">'+categorytransformerdata[0].children[i].name+'</a></li>';
	    }
		$("#category_transformer_id").append(categorytransformertext);
		//行业知识-汽轮机的数据
	    var categoryturbinedata =  cims201.utils.getData('tree/tree!listSubNode.action',{nodeId:77});
		var categoryturbinetext = "";
	    for(var i = 0; i < categoryturbinedata[0].children.length;i++){
	    	categoryturbinetext += '<li><a href="/data/list/602002">'+categoryturbinedata[0].children[i].name+'</a></li>';
	    }
	    $("#category_turbine_id").append(categoryturbinetext);
	    //行业知识-模具的数据
	    var categorymoulddata =  cims201.utils.getData('tree/tree!listSubNode.action',{nodeId:79});
		var categorymouldtext = "";
	    for(var i = 0; i < categorymoulddata[0].children.length;i++){
	    	categorymouldtext += '<li><a href="/data/list/602002">'+categorymoulddata[0].children[i].name+'</a></li>';
	    }
	    $("#category_mould_id").append(categorymouldtext);
	    //热门专利数据
	    var patentdata =  cims201.utils.getData('statistic/khotlist!listHotDKKnowledge.action',{knowledgetype : 'Patent', listtype:'commentrecord.commentCount', index:0, size:8});
		var patenttext = "";
	    for(var i = 0; i < patentdata.length;i++){
	    	patenttext += '<li><a class="fc_gray6" href="<%=basePath %>kshow.action?kid='+patentdata[i].id+'">['+patentdata[i].uploader.name+'] '+patentdata[i].titleName+' ('+patentdata[i].commentRecord.commentCount+'条)</a><span class="fc_orange"></span></li>';
	    }
		$("#patent_id").append(patenttext);
		
		 //热门名人专栏（专家博文）数据
	    var articledata =  cims201.utils.getData('statistic/khotlist!listHotDKKnowledge.action',{knowledgetype : 'Article', listtype:'commentrecord.commentCount', index:0, size:8});
		var articletext = "";
	    for(var i = 0; i < articledata.length;i++){
	    	articletext += '<li><a class="fc_gray6" href="<%=basePath %>kshow.action?kid='+articledata[i].id+'">['+articledata[i].uploader.name+'] '+articledata[i].titleName+' ('+articledata[i].commentRecord.commentCount+'条)</a><span class="fc_orange"></span></li>';
	    }
		$("#expertarticle_id").append(articletext);
		
		function concatText(sourcetext,length){
			var concatstring = "";
			if(sourcetext.length < length ){
	    		concatstring = sourcetext;
	    	}else{
	    		concatstring = sourcetext.substr(0,length)+"...";
	    	}
	    	return concatstring;
		}
	</script>
</head>
<body>

    <table align="center">
	    <FORM>
	<div class="aspNetHidden">
	</div>
	    <!--search panel-->
	    <div id="header">
	        <div id="search-panel">
	        <a href="javascript:document.location.reload();"><div class="logoer"></div></a>
	        <div class="data-search">
	            <fieldset>
	                <legend>搜索数据</legend>
	                <div class="search-box clear">
	                    <div id="searchType">
	                        <input id="selType" type="button" value="知识" />
	                        <ul id = "searchTypeOption">
	                            <li>知识</li>
	                            <li>专利</li>
	                            <li>标准</li>
	                            <li>博文</li>
	                            <li>数据</li>
	                            <li>问题</li>
	                        </ul>
	                    </div>
	                    <script type="text/javascript">
	                        $('#selType').mouseover(function () {
	                            $('#searchType ul').attr('style', 'border:1px solid #006B98;border-top:0;');
	                            $('#searchType ul li').show();
	                        });
	                        $('#searchTypeOption').mouseleave(function () {
	                            $('#searchType ul li').hide();
	                            $('#searchType ul').removeAttr('style', 'border:1px solid #006B98;border-top:0;');
	                            $('#selType').removeAttr('border-bottom:none;');
	                        });
	                        $('#searchType ul li').each(function () {
	                            $(this).click(function () {
	                                $('#selType').val($(this).text());
	                                $('#searchType ul li').hide();
	                            });
	                        })
	                    </script>
	                    <input type="text" x-webkit-grammar="builtin:search" x-webkit-speech="" autocomplete="off" tabindex="9" accesskey="s" id="searchText" name="kw" value="" onkeydown="javascript:if(event.keyCode==13)DoSearch();" />
	                    <input type="button" id="searchBtn" onclick="DoSearch();" value="搜 索" />
	                    <script type="text/javascript">

	                        function DoSearch() {
	                            var wd = $.trim($('#searchText').val());
	                            if (wd != '' && wd != '请输入搜索关键词') {
	                                var t = $('#selType').val();
	                                switch (t) {
	                                	case '知识':
	                                        window.location = '<%=basePath %>klist.action?key=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '专利':
	                                        window.location = 'knowledge/knowledge!fulltextserach.action?key=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '标准':
	                                        window.location = 'knowledge/knowledge!fulltextserach.action?key=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '博文':
	                                        window.location = 'knowledge/knowledge!fulltextserach.action?key=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '数据':
	                                        window.location = 'knowledge/knowledge!fulltextserach.action?key=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '问题': 
	                                        window.location = 'knowledge/knowledge!fulltextserach.action?key=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    default:
	                                        break;
	                                }
	                            } else {
	                                $('#searchText').val('请输入搜索关键词');
	                            }
	                        }                                                                                                                                                                                                              3
	                    </script>
	                </div>                                                                                                                                                                                                                    
	                <ul id="hotwordsUL">
	                    <li style="margin-right:0px;">热词：</li>
	                    <li id="hotwords_id"></li>
	                </ul>
	            </fieldset>
	        </div>                                                                                                                                                                                                                                                                             
	    </div>
	    </div>
	    <!--search panel end-->
	    <div class="clear"></div>
	    
	    <div class="content clearfix">
		
		<div class="clearfix  pt10">
			<div class="navTree l">
	            
	<style type="text/css">
		#left_tab{position:fixed;left:0;top:100px;_position:absolute;_top:expression(documentElement.scrollTop+100+"px");z-index:100000;}
		#left_tab a{width:32px;outline:none;height:165px;padding-top:10px;display:block;border:1px solid #cdcdcd;background:url(/images/0815/rb.png) no-repeat 13px 150px #f8f8f8;color:#646464;text-align:center;text-decoration:none;font:normal 12px/22px simsun;}
		#left_tab.on a{width:32px;height:165px;padding-top:10px;display:block;border:1px solid #cdcdcd;background:url(/images/0815/lb.png) no-repeat 13px 150px #f8f8f8;color:#333;font-weight:bold;}
		#left_tab a:hover{color:#333;border-color:#ccc;}
		#left_tab_main{position:fixed;left:33px;z-index:100003;top:100px;display:none;_position:absolute;_top:expression(documentElement.scrollTop+100+"px")}
		#left_tab_main ul{margin:0;padding:0;list-style:none;font:normal 14px/30px "simsun";background:url(/images/0815/left_tab.png) repeat-y right #e4f9fc;padding:5px 0 5px 5px;border:0 solid #cccacd;border-width:1px 0 1px 1px;width:110px;}
		#left_tab_main ul li{width:110px;height:32px;position:relative}
		#left_tab_main ul li a{font:normal 14px/30px "simsun";position:absolute;left:0;top:0;z-index:100000;text-decoration:none;color:#333;display:block;padding-left:5px;width:102px;border:0 solid #e4f9fc;border-width:1px 0 1px 1px;}
		#left_tab_main ul li a.lthover{border-color:#6c9eb5;font-weight:bold;background-color:#f9ffff;z-index:100005;padding-right:1px}
		.left_tab_content{position:absolute;}
	
		.left_tab_content .l_table td{padding-top:2px;padding-bottom:2px;font:normal 12px/12px "simsun";}
		.left_tab_content .l_table a:hover{text-decoration:underline;}
		.left_tab_content .l_table{min-height:265px;_height:300px;z-index:100002;background:url(/images/0815/left_tab2.png) repeat-y left #f9ffff;border:0 solid #6c9eb5;border-width:1px 1px 1px 0;margin-top:0;margin-left:114px;padding-right:10px;padding-left:8px;width:750px;}
		.left_tab_content .l_table td.tbl{text-align:left; }
		.left_tab_content .l_table td.tbr{text-align:right;width:100px;font-weight:bold;vertical-align:top;}
		.left_tab_content .l_table td.tbl a{display:block;float:left;border-left:1px solid #999;padding:0px 8px;*padding:0px 8px !important;*padding:2px 8px;margin:7px 0px;white-space:nowrap;color:#666;}
		.left_tab_content .l_table td.tbr a{display:block;float:right;padding:0px 10px;*padding:0px 10px !important;*padding:2px 10px;margin:7px 0px;color:#E76D24; }
		
		table.hide{display:none;z-index:100002;}
	</style>
	
				<div class="nt_title">行业知识</div>
				
				<div class="nt_box">
					<a href=""><div class="ntb_title" style="background-color:#fbfbfb;">汽车零部件</div></a>
					<ul id = "category_carparts_id" class="ntb_box">
						
					</ul>
				</div>
				<div class="nt_box">
					<a href=""><div class="ntb_title" style="background-color:#fbfbfb;">变压器</div></a>
					<ul id = "category_transformer_id" class="ntb_box">
						
					</ul>
				</div>
				<div class="nt_box">
					<a href="/data/list/800001"><div class="ntb_title" style="background-color:#fbfbfb;">汽轮机</div></a>
					<ul id = "category_turbine_id" class="ntb_box">
	                        
					</ul>
				</div>
				<div class="nt_box">
					<a href=""><div class="ntb_title" style="background-color:#fbfbfb;">模具</div></a>
					<ul id = "category_mould_id" class="ntb_box">
	                        
					</ul>
				</div>
				<div class="nt_box">
					<a href=""><div class="ntb_title" style="background-color:#fbfbfb;">服装</div></a>
					<ul id = "category_garment_id" class="ntb_box">
	                    
					</ul>
				</div>
			</div>
			<div class="clearfix r cickrbox">
				<div class="swfBox l">
				   <div id="eyefoot" style="display:block;">
	                    <a target="_top" href="/scires/index" ><img src="<%=basePath %>images/banner/home1.png" style="width:529px;height:242px;"/></a>
	                    <a target="_blank" href="http://factory.datatang.com" ><img src="<%=basePath %>images/banner/home3.png" style="width:529px;height:242px;"/></a>
	                    <a target="_blank" href="http://www.datatang.com/pack/index.aspx" ><img src="<%=basePath %>images/banner/home4.png" class="hide" style="width:529px;height:242px;"/></a>
	                    <a target="_blank" href="/org/orgindex.aspx" ><img src="<%=basePath %>images/banner/home5.png" class="hide" style="width:529px;height:242px;"/></a>
					</div>
					<div id="div_flash_index" class="slideBox" style="display:block;">
	                    <a target="_top" href="/scires/index" onmouseover="GoFlash(0);" >1</a>
	                    <a target="_blank" href="http://factory.datatang.com" onmouseover="GoFlash(1);" >2</a>
	                    <a target="_blank" href="http://www.datatang.com/pack/index.aspx" onmouseover="GoFlash(2);" class="on">3</a>
	                    <a target="_blank" href="/org/orgindex.aspx" onmouseover="GoFlash(3);" class="on">4</a>
					</div>
	                <script type="text/javascript">
	                    var i = 0;
	                    $(function () {
	                        $('#eyefoot img').hide();
	                        $('#eyefoot img').eq(0).show();
	                        $('#div_flash_index a').removeClass("on");
	                        $('#div_flash_index a').eq(0).addClass("on");
	                        setInterval('GoIndexFlash();', 4000);
	                    });
	                    function GoFlash(i) {
	                        if ($(this).hasClass("on")) return;
	                        $('#div_flash_index a').removeClass("on");
	                        $(this).addClass("on");
	                        $('#eyefoot img').hide();
	                        $('#eyefoot img').eq(i).show();
	                    }
	                    function GoIndexFlash() {
	                        if (i > 3) {
	                            i = 0;
	                        }
	                        SetIndexFlash(i);
	                        i++;
	                    }
	                    function SetIndexFlash(j) {
	                        $('#eyefoot img').hide();
	                        $('#eyefoot img').eq(j).show();
	                        $('#div_flash_index a').removeClass('on');
	                        $('#div_flash_index a').eq(j).addClass('on');
	                    }
	                </script>
				</div>
				<div class="r_newIndex r">
					<div class="r1 tc">
						
						<h3 class="fc_blue tc">我的创新知识管理系统</h3>
						<div class="r1Text tc"><strong class="fc_blue"><a href="<%=basePath %>login.action">登录</a>&nbsp&nbsp&nbsp&nbsp</strong><strong class="fc_red"><a href="<%=basePath %>login.action">注册</a></strong></div>
						<div class="btnBox tc"><a class="indexBtn_1" href="<%=basePath %>login.action">发布数据</a><a class="indexBtn_2" href="<%=basePath %>login.action">发布知识</a></div>
						<div class="btnBox tc"><a class="indexBtn_1" href="<%=basePath %>login.action">发布专利</a><a class="indexBtn_2" href="<%=basePath %>login.action">发布标准</a></div>
						<div class="btnBox tc"><a class="indexBtn_1" href="<%=basePath %>login.action">发布博文</a><a class="indexBtn_2" href="<%=basePath %>login.action">发布问题</a></div>
					</div>
				</div>
			</div>
			<div class="new_list r">
	        
				<div class="nl_box noBorder">     
					<div class="nlb_title clearfix">
						<h5 style="margin-bottom:6px;"><a href="" class="fc_blue2">热点知识</a></h5>
						<!--<div class="l"><a href="/Member/131378/"><img src="<%=basePath %>images/hot.jpg" alt="热点知识" style="width:118px;height:48px;border:1px solid #fbfbfb" /></a></div>
						<div class="r">
							<p class="fc_gray6">热度·关注·世界在发生着什么...</p>
						</div>
					--></div>
					<ul id="hotknowledges_id" class="list clearfix">
	                 
					</ul>
				</div>
	        
				<div class="nl_box ">     
					<div class="nlb_title clearfix">
						<h5 style="margin-bottom:12px;"><a href="" class="fc_blue2">热点资讯</a></h5>
						<!--<div class="l"><a href="/Member/130070/"><img src="<%=basePath %>images/hotnews.png" alt="热点资讯" style="width:118px;height:48px;border:1px solid #fbfbfb" /></a></div>
						<div class="r">
							<p class="fc_gray6">行业资讯·快捷的知识速递<br/>秀才不出门，尽知天下事...</p>
						</div>
					--></div>
					<ul id="hotnews_id" class="list clearfix">
	                 
					</ul>
				</div>
	        
			</div>
		</div>
		<div class="clearfix pt10">
	        
			<div class="l index_l">
				<div id = "latestknowledge_id" class="boxl_1 left_index_1" style="width:206px;">
					<div class="bl_1title clearfix"><strong class="l fc_blue2">最新知识</strong><a href="" class="r fc_blue">更多&gt;</a></div>
					      
				</div>
				<div id = "hotquestion_id" class="boxl_1 mt10 left_index_2" style="width:206px;">
					<div class="bl_1title clearfix"><strong class="l fc_blue2">热门问题</strong><a href="" class="r fc_blue">更多&gt;</a></div>
					
				</div>
				<div class="boxl_1 mt10 left_index_3" style="width:206px;">
					<div class="bl_1title clearfix noBorder"><strong class="l fc_blue2">最新资讯</strong><a href="" class="r fc_blue">更多&gt;</a></div>
					<div >
						<ul id = "latestnews_id" class="newList">
	                         
						</ul>
					</div>
				</div>
			</div>
			<div class="r index_r">
				<div class="boxr_l mb12">
					<div class="bl_1title clearfix noBorder"><strong class="l fc_blue2">用户排行榜</strong></div>
					<div class="clearfix ir_photoBox">
					 
						<div class="clearfix l ipbox">
							<div id="userrank1" class="l"></div>
							<div class="r">
								<a id = "userrank1_name" class="fc_blue2">&nbsp&nbsp</a><br />
								纵横四海 6级 <br />
								发布数据 153
							</div>
						</div>
	                     
						<div class="clearfix l ipbox">
							<div id="userrank2" class="l"></div>
							<div class="r">
								<a id = "userrank2_name" class="fc_blue2">&nbsp&nbsp</a><br />
								纵横四海 6级 <br />
								发布数据 133
							</div>
						</div>
	                     
						<div class="clearfix l ipbox">
							<div id="userrank3" class="l"></div>
							<div class="r">
								<a id = "userrank3_name" class="fc_blue2">&nbsp&nbsp</a><br />
								纵横四海 6级 <br />
								发布数据 119
							</div>
						</div>
	                     
						<div class="clearfix l ipbox">
							<div id="userrank4" class="l"></div>
							<div class="r">
								<a id = "userrank4_name" class="fc_blue2">&nbsp&nbsp</a><br />
								纵横四海 6级 <br />
								发布数据 114
							</div>
						</div>
	                    
					</div>
				</div>
	            
	                <div class="l2 ">
					<h2 class="fc_blue clearfix"><span class="l">行业·专利（来源：国家产权局 & 美国专利网）</span><a class="r more" href="/data/list/602002">更多 &gt;</a></h2>
					<div class="clearfix">
						<div class="imgBox l"><a href=""><img style="width:139px;height:171px;" src="<%=basePath %>images/homepatent.jpg" alt="数据挖掘" /></a></div>
	                
						<div class="list r">
							<ul id = "patent_id">
							</ul>
						</div>
					</div>
				</div>	
	            
	                <div class="l2 ">
					<h2 class="fc_blue clearfix"><span class="l">行业·标准</span><a class="r more" href="/special/data/2">更多 &gt;</a></h2>
					<div class="clearfix">
						<div class="imgBox l"><a href=""><img style="width:139px;height:171px;" src="<%=basePath %>images/homestandard.jpg" alt="图像处理" /></a></div>
	                
						<div class="list r">
							<div class="bigTxt"><a href="" class="bigTxt">微小图像数据集（完整版）<span class="fc_orange">(484.29G)</span></a></div>
							<div class="desc">
								Tiny Images dataset, which consists of 79,302,017 images, each being a 32x32 color image. This data is stored in the form of large binary files which can be accesed by a Matlab toolbox that we have written. You will need around 400Gb of free disk space to store all the files. In total there are 5..
	                            
							</div>
							<ul>
	                        
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">数据堂人脸数据集（小，200张人脸图片）</a><span class="fc_orange">(16.53M)</span></span><span class="r fc_gray9">2012-04-28</span></li>
	                            
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">数据堂人脸数据集：国内最大的人脸数据库</a><span class="fc_orange">(18.63G)</span></span><span class="r fc_gray9">2012-04-28</span></li>
	                            
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">车牌图片数据库（2010年-2011年间的数据）</a><span class="fc_orange">(20.00G)</span></span><span class="r fc_gray9">2011-10-13</span></li>
	                            
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">行人高清户外视频数据库（2012年4月，含各种户外场景）</a><span class="fc_orange">(200.03G)</span></span><span class="r fc_gray9">2012-05-15</span></li>
	                            
							</ul>
						</div>
					</div>
				</div>	
	            
	                <div class="l2 ">
					<h2 class="fc_blue clearfix"><span class="l">名人·专栏</span><a class="r more" href="/special/data/3">更多 &gt;</a></h2>
					<div class="clearfix">
						<div class="imgBox l"><a href=""><img style="width:139px;height:171px;" src="<%=basePath %>images/homearticle.jpg" alt="语音处理" /></a></div>
	                
						<div class="list r">
							<ul id = "expertarticle_id">
							</ul>
						</div>
					</div>
				</div>	
	            
	                <div class="l2 right_index_bottom">
					<h2 class="fc_blue clearfix"><span class="l">零件库</span><a class="r more" href="/data/list/602001">更多 &gt;</a></h2>
					<div class="clearfix">
						<div class="imgBox l"><a href=""><img style="width:139px;height:171px;" src="<%=basePath %>images/homeparts.jpg" alt="机器学习" /></a></div>
	                
						<div class="list r">
							<div class="bigTxt"><a href="" class="bigTxt">Forest Fires Data Set (UCI)<span class="fc_orange">(8.51K)</span></a></div>
							<div class="desc">
								This dataset is public available for research. The details are described in [Cortez and Morais, 2007]: [pdf]. Please include this citation if you plan to use this database:
	
	P. Cortez and A. Morais. A Data Mining Approach to Predict Forest Fires using Meteorological Data. In J. Neves, M. F. San..
	                            
							</div>
							<ul>
	                        
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">Iris Data Set (UCI)</a><span class="fc_orange">(3.76K)</span></span><span class="r fc_gray9">2010-12-07</span></li>
	                            
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">Wine Data Set (UCI)</a><span class="fc_orange">(5.73K)</span></span><span class="r fc_gray9">2010-12-13</span></li>
	                            
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">Abalone Data Set</a><span class="fc_orange">(53.98K)</span></span><span class="r fc_gray9">2010-12-09</span></li>
	                            
								<li><span class="l" style="width:500px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;"><a class="fc_gray6" href="">Breast Cancer Wisconsin (Diagnostic) Data Set (UCI)</a><span class="fc_orange">(83.98K)</span></span><span class="r fc_gray9">2010-12-02</span></li>
	                            
							</ul>
						</div>
					</div>
				</div>	
	            
			</div>
		</div>
		</FORM>
		<div class="foot">
			<br/>
		    Copyright © 2013 <a href="http://www.zju.edu.cn">浙江大学现代制造工程研究所知识管理课题组</a> | Designed by <a href="http://www.zju.edu.cn" target="_parent">http://icme.zju.edu.cn</a><br />浙江省杭州市西湖区浙大路38号 浙江大学玉泉校区 现代制造工程研究所 310027 <a href="">联系我们</a> 
		    <br/>
		    <br/>
		</div>
	</table>

    


    
</body>
</html>
