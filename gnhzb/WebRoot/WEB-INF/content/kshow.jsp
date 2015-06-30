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
	
	<link href="<%=basePath %>css/base.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/menu.css" rel="stylesheet" />
	<link href="<%=basePath %>css/20130424.css" rel="stylesheet" />
	<link href="<%=basePath %>css/list.css" rel="stylesheet" />
	<link href="<%=basePath %>css/star.css" rel="stylesheet" />
	<link href="<%=basePath %>css/text.css" rel="stylesheet" />
	<link href="<%=basePath %>css/myData.css" rel="stylesheet" />
	
	<script src="<%=basePath %>js/edo/edo.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/jquery.1.5.2.pack.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/cims201.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/utils.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/Default.js" type="text/javascript"></script>
	<script src="<%=basePath %>js/jquery.alphanumeric.pack.js" type="text/javascript"></script>

</head>
<body>
	<table align="center">
    <form>

    <div id="header">
        <div id="search-panel">
        <a href="<%=basePath %>home2.jsp"><div class="logoer"></div></a>
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
	                            <li>专栏</li>
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
	                                        window.location = '/s/data?k=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '专利':
	                                        window.location = '/s/data?k=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '标准':
	                                        window.location = '/s/data?k=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '专栏':
	                                        window.location = '/s/data?k=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case '数据':
	                                        window.location = '/s/demand?search=' + window.encodeURIComponent($.trim($('#searchText').val()));
	                                        break;
	                                    case "问题": 
	                                        window.location = 'http://scires.datatang.com/s/search.htm?wd=' + window.encodeURIComponent($.trim($('#searchText').val()));
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
    
    
<div class="content">

<!--<div class="expTitle">
	<a href="/data/list/">所有数据</a> &gt; 
	<a href="/data/list/600000" style="margin-left: 10px;">信息科学</a> &gt; 
	<a href="/data/list/602000" style="margin-left: 10px;">计算机科学</a> &gt; 
	<a href="/data/list/602010" style="margin-left: 10px;">自然语言处理</a>
</div>
--><div class="clearfix" id="content">
	<div class="list_left">
		<div class="listData_1 mb12" style="height:210px;">
			<div class="ld_title">
				<div><h3 class="title_14">知识概要</h3></div>
			</div>
			<ul class="fc_gray9">
				<li><label>发布时间：</label><strong id = "uploadtime_id" class="fc_orange"></strong></li>
				<li><label>评论次数：</label><strong id = "commentCount_id" class="fc_orange"></strong></li>
                 
                <li><label>下载次数：</label><strong id = "downloadTime_id" class="fc_orange"></strong></li>
                
				<li><label>评&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分：</label><strong id = "rate_id" class="fc_orange"></strong>
                </li>
                
                <li><label>发布者：</label><strong id = "uploader_id" class="fc_orange"></strong></li>
                
                <li><label>关键词：</label><strong id = "keywords_id" class="fc_orange"></strong></li>
			</ul>
			
		</div>
	    <div id = "hotcomment_id" class="boxl_1 left_index_1" style="width:206px;height:220px;">
					<div class="bl_1title clearfix"><strong class="l fc_blue2">热门评论</strong></div>
					      
				</div>
		
		<div id = "relatedknowledge_id" class="boxl_1 left_index_1" style="width:206px;height:220px;">
					<div class="bl_1title clearfix"><strong class="l fc_blue2">相关推荐知识</strong></div>
					      
				</div>
        
	</div>
	<div class="list_right">
		
        <h1 id = "knowledgecontent" class="fc_blue f18"></h1>
        
	</div>
</div>
</div>
<script type="text/javascript">
<!-- 
    $('img').each(function () {
        if (this.width > 655) {
            var deflationWidth = this.width - 655;
            this.setAttribute('style', 'width:655px;height:' + (this.height - deflationWidth) + 'px;');
        }
    });
//-->
</script>


    </form>
    <div class="foot">
			<br/>
		    Copyright © 2013 <a href="http://www.mianfeimoban.com">浙江大学现代制造工程研究所知识管理课题组</a> | Designed by <a href="http://www.mianfeimoban.com" target="_parent">http://icme.zju.edu.cn</a><br />浙江省杭州市西湖区浙大路38号 浙江大学玉泉校区 现代制造工程研究所 310027 <a href="">联系我们</a> 
		    <br/>
		    <br/>
		</div>
    </table>
</body>
<script type="text/javascript">
	var kid = <%= request.getParameter("kid")%>;
	var kData = cims201.utils.getData('knowledge/knowledge!showknowledge.action',{id:kid});
	$("#knowledgecontent").append('标题：'+kData.titleName);
	$("#uploadtime_id").append(kData.uploadTime.substr(0,10));
	$("#commentCount_id").append(kData.commentRecord.commentCount);
	$("#downloadTime_id").append(kData.commentRecord.downloadCount);
	$("#rate_id").append(kData.commentRecord.rate);
	$("#uploader_id").append(kData.uploader.name);
	var keywordstext = "";
	for(var i = 0;i < kData.keywords.length;i++){
		keywordstext += kData.keywords[i].name+"  ";
	}
	$("#keywords_id").append(keywordstext);
	if(kData.ktype.name == "问题"){
		$("#knowledgecontent").append('<hr/><h5>'+kData.questioncontent+'</h5>');
	}else if (kData.ktype.name == "新闻"){
		$("#knowledgecontent").append('<hr/><h5>'+kData.questioncontent+'</h5>');
	}else if (kData.ktype.name == "标准"){
	
	}else if (kData.ktype.name == "文章"){
		$("#knowledgecontent").append('<hr/><h5>'+kData.questioncontent+'</h5>');
	}else{
		$("#knowledgecontent").append('<iframe height="630" width="100%" src="<%=basePath %>/homeknowledge.action?kid='+<%=request.getParameter("kid")%>+'"></iframe>');
	}
	//$("#knowledgecontent").append('<hr/>热门评论<br/>');
	
	
	cims201.utils.getData_Async('knowledge/knowledge!listRcommentKnowledge.action',{id:kid},function(text){
		var relatedktext = "";
		if(text != null && text != 'null' && text != ''){
			var relatedKnowledgeData = Edo.util.Json.decode(text);
			for(var i = 0 ; i < relatedKnowledgeData.length;i++){
				relatedktext += '<div class="bl_1box "><div><a href="/data/44123" class="fc_gray6">'+concatText(relatedKnowledgeData[i].titleName,15)+' </a></div><div class="clearfix"><span class="l">用户：<a class="fc_blue2" href="/member/131378">'+relatedKnowledgeData[i].uploader.name+'</a></span><span class="r fc_gray9">'+concatText(relatedKnowledgeData[i].uploadTime,10)+'</span></div></div>'
			}
		}else{
			relatedktext = "没有推荐知识！";
		}	
		$("#relatedknowledge_id").append(relatedktext);
	});
	//设置搜索热词 
	var hotwords = '';
	    cims201.utils.getData_Async('knowledge/knowledge!listHotword.action',{},function(text){
			if(text != null && text != ''){
				var data = Edo.util.Json.decode(text);
				data.each(function(o){
					$("#hotwords_id").append('<a href=javascript:openNewTab("'+(o.name)+'","knowledgelist_fulltext","'+(o.name)+'",{key:"'+o.name+'"});>'+o.name+'</a>');
				});
			}
		});
	//热门评论数据 
	cims201.utils.getData_Async('comment/comment!listHotComment.action',{knowledgeID:kid},function(text){
		var commenttext = "";
		if(text != null && text != 'null' && text != ''){
			var commentData = Edo.util.Json.decode(text);
			for(var i = 0 ; i < commentData.length;i++){
			
				commenttext += '<div class="bl_1box "><div><a href="" class="fc_gray6">'+concatText(commentData[i].content,15)+' </a></div><div class="clearfix"><span class="l">用户：<a class="fc_blue2" href="/member/131378">'+commentData[i].commenterName+'</a></span><span class="r fc_gray9">发表于'+commentData[i].commmentTime.substr(0,10)+'</span></div></div>'
			}
		}else{
			commenttext = "没有人评论！";
		}	
		$("#hotcomment_id").append(commenttext);
	});
	
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
</html>
