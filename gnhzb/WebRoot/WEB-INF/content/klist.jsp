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
	
	<script src="<%=basePath %>js/knowledge/knowledge-list-fulltext.js" type="text/javascript"></script>
	
	<%
	   String key = request.getParameter("key");
	%>
	
	
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
    
    
<div id="knowledge_list_id">
	 
</div>


    </form>
    	<div class="foot">
			<br/>
		    Copyright © 2013 <a href="http://www.mianfeimoban.com">浙江大学现代制造工程研究所知识管理课题组</a> | Designed by <a href="http://www.mianfeimoban.com" target="_parent">http://icme.zju.edu.cn</a><br />浙江省杭州市西湖区浙大路38号 浙江大学玉泉校区 现代制造工程研究所 310027 <a href="">联系我们</a> 
		    <br/>
		    <br/>
		</div>
    </table>
    
     <script type="text/javascript">
     	alert("key="+<%= key %>);
	    var myTable1_rt = new createKnowledgeList_fulltext(); 
	    
		myTable1_rt.search({key:<%= key %>},'knowledge/knowledge!fulltextserach.action');
		$("#knowledge_list_id").append(myTable1_rt);
	
	</script>
  
	
</body>

</html>
