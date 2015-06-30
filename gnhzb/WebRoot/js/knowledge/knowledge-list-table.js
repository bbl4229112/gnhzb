//以表格的方式展示知识
function createKnowledgeList_table(url, params, btNames, btFunctions){

	
	
	var myColumns = [
	    
	   
	   {
             headerText: '序号',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '知识标题',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 250,               
             align: 'left',
             renderer: function(v,r){
             	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	var xml_Str = '';
	        		if(r.knowledgetype!=null&&r.knowledgetype.id!=null&&r.knowledgetype.name == '岗位知识') {
	        			xml_Str = '&nbsp;&nbsp;<a title="此知识属于岗位知识，点击查询其他岗位知识。" href="javascript:showKnowledgetypeKnowledgeList('+r.knowledgetype.id+',\''+r.knowledgetype.name+'\')";><img src=\'./css/xml.jpg\'></a>';
	        		}
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_simplist_title">'+v+'</span></a>'+xml_Str;
	            }
             	return outStr;
             }
         },
      {
             headerText: '作者',
             dataIndex: 'KAuthors',
             headerAlign: 'center',                 
             align: 'left',
             width: 130,
             renderer: function(v,r){
             		var i=0;
             	var author_outStr = '';
             	if(v){
             		
             		v.each(function(o){
             			i++
             			author_outStr += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+author_outStr;
             	return author_outStr;
             }
         },
         {
             headerText: '关键词',
             dataIndex: 'keywords',
             headerAlign: 'center',                 
             align: 'left',
             width: 150,
             renderer: function(v,r){
             	var keywords_outStr = '';
             	var i=0;
             	if(v){ 
             	
             		v.each(function(o){
             			i++;
             			keywords_outStr +=  '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	keywords_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+keywords_outStr;
             	return keywords_outStr;
             }
         },
         {
             headerText: '知识类型',
             dataIndex: 'knowledgetype',
             headerAlign: 'center',                 
             align: 'left',
             width: 110,
             renderer: function(v,r){
             	var knowledgetype_outStr = '';
           
             	knowledgetype_outStr +=  '<a href="javascript:showKnowledgetypeKnowledgeList('+v.id+',\''+v.name+'\')";>'+v.name + '</a>&nbsp';
             	
             	knowledgetype_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+knowledgetype_outStr;
             	return knowledgetype_outStr;
             }
         },
                 {
             headerText: '上传时间',
             dataIndex: 'uploadTime',
             headerAlign: 'center',                 
             align: 'left',
             width: 110,
             renderer: function(v,r){
             	
             	return r.uploadTime;
             } 
                 }
         
     ];

	
		var myTable = new createTable({},'100%','100%','知识列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	
	
	
}

//江丁丁添加 2013-620
function createArticleList_table(url, params, btNames, btFunctions){

	
	
	var myColumns = [
	    
	   
	   {
             headerText: '序号',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '文章标题',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 250,               
             align: 'left',
             renderer: function(v,r){
             	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	var xml_Str = '';
	        		if(r.knowledgetype!=null&&r.knowledgetype.id!=null&&r.knowledgetype.name == '岗位知识') {
	        			xml_Str = '&nbsp;&nbsp;<a title="此知识属于岗位知识，点击查询其他岗位知识。" href="javascript:showKnowledgetypeKnowledgeList('+r.knowledgetype.id+',\''+r.knowledgetype.name+'\')";><img src=\'./css/xml.jpg\'></a>';
	        		}
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_simplist_title">'+v+'</span></a>'+xml_Str;
	            }
             	return outStr;
             }
         },
//         {
//                headerText: '作者',
//                dataIndex: 'KAuthors',
//                headerAlign: 'center',                 
//                align: 'left',
//                width: 130,
//                renderer: function(v,r){
//                		var i=0;
//                	var author_outStr = '';
//                	if(v){
//                		
//                		v.each(function(o){
//                			i++
//                			author_outStr += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
//                		});
//                	}
//                	if(i>0)
//                	author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+author_outStr;
//                	return author_outStr;
//                }
//            },
         {headerText: '发布者', dataIndex: 'uploader',width:130, headerAlign: 'center',align:'center', renderer:function(v,r){                    
      	    var uploader_outStr = ''+v.name;
      	return uploader_outStr;
         }},  
         {
             headerText: '关键词',
             dataIndex: 'keywords',
             headerAlign: 'center',                 
             align: 'left',
             width: 150,
             renderer: function(v,r){
             	var keywords_outStr = '';
             	var i=0;
             	if(v){ 
             	
             		v.each(function(o){
             			i++;
             			keywords_outStr +=  '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	keywords_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+keywords_outStr;
             	return keywords_outStr;
             }
         },       
                 {
             headerText: '上传时间',
             dataIndex: 'uploadTime',
             headerAlign: 'center',                 
             align: 'left',
             width: 110,
             renderer: function(v,r){
             	
             	return r.uploadTime;
             } 
                 }
         
     ];

	
		var myTable = new createTable({},'100%','100%','文章列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	
	
	
}

//审批任务，专门为了批量审批
function createKnowledgeListForBatchApprove_table(url, params, btNames, btFunctions){

	
	
	var myColumns = [
	    
	    	Edo.lists.Table.createMultiColumn(),//pl 这个是支持选择多行的选框条
	   
	   {
             headerText: '序号',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '知识标题',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 250,               
             align: 'left',
             renderer: function(v,r){
             	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_simplist_title">'+v+'</span></a>';
             	}
             	return outStr;
             }
         },
      {
             headerText: '作者',
             dataIndex: 'KAuthors',
             headerAlign: 'center',                 
             align: 'left',
             width: 130,
             renderer: function(v,r){
             		var i=0;
             	var author_outStr = '';
             	if(v){
             		
             		v.each(function(o){
             			i++
             			author_outStr += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+author_outStr;
             	return author_outStr;
             }
         },
         {
             headerText: '关键词',
             dataIndex: 'keywords',
             headerAlign: 'center',                 
             align: 'left',
             width: 150,
             renderer: function(v,r){
             	var keywords_outStr = '';
             	var i=0;
             	if(v){ 
             	
             		v.each(function(o){
             			i++;
             			keywords_outStr +=  '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	keywords_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+keywords_outStr;
             	return keywords_outStr;
             }
         },
         {
             headerText: '知识类型',
             dataIndex: 'knowledgetype',
             headerAlign: 'center',                 
             align: 'left',
             width: 110,
             renderer: function(v,r){
             	var knowledgetype_outStr = '';
           
             	knowledgetype_outStr +=  '<a href="javascript:showKnowledgetypeKnowledgeList('+v.id+',\''+v.name+'\')";>'+v.name + '</a>&nbsp';
             	
             	knowledgetype_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+knowledgetype_outStr;
             	return knowledgetype_outStr;
             }
         },
                 {
             headerText: '上传时间',
             dataIndex: 'uploadTime',
             headerAlign: 'center',                 
             align: 'left',
             width: 110,
             renderer: function(v,r){
             	
             	return r.uploadTime;
             } 
                 }
         
     ];

	
		var myTable = new createTable({},'100%','100%','知识列表',myColumns,['批量审批'],[batchApprove],url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	function batchApprove(){
		var rs = myTable.getSelectedIds();
		if(rs.length == 0){
			creatalert("未选定!");
		}else{
			approveKnowledge(rs);
		}
		
	}
	
	
}
//个人问题列表(带删除)
function createPersonalQuestionList_table(url, params, btNames, btFunctions){
	var myColumns = [
         		
			{
             headerText: '序号',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: '4%',               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
             },
         		
			{header: '问题标题', dataIndex: 'titleName',width:'34%', headerAlign: 'center',align:'center',
                 renderer: function(v,r){
                     	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";>'+v+'</a>';
             	}
             	return outStr;
                }    
                },                  
                {header: '提问人', dataIndex: 'uploader',width:'15%', headerAlign: 'center',align:'center', renderer:function(v,r){                    
             	    var uploader_outStr = ''+v.name;
             	return uploader_outStr;
                }},              
                {header: '回复次数', dataIndex: 'commentRecord',width:'13%', headerAlign: 'center',align:'center', renderer:function(v){                            	
                	return v['commentCount'];
                }},
                {header: '解决情况', dataIndex: 'questionstatus',width:'13%' ,headerAlign: 'center',align:'center',
                  renderer: function(v){             
                   		if(v == 0){
                   			return '<img src="css/notsolve.gif"></img>';
                   		}
                   		else 
                   		return  '<img src="css/havesolve.gif"></img>';
                   }                             
                },       
                {header: '发布时间', dataIndex: 'uploadTime',width:'17%', headerAlign: 'center',align:'center'},
                
             {
             headerText: '删除',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: '4%',               
             align: 'center',
             renderer: function(v,r){
	             	var outStr = '';
	             	outStr += '<a href=javascript:unvisibleQuestion('+r.id+');>'+'<img src="css/questiondelete.gif"></img>'+'</a>';
             	
             	return outStr;
             }
         }
         
     ];

	var myTable = new createTable({},'100%','100%','知识列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	
	this.reload = function(){
		myTable.search();
	}
	
}
//问题列表（通用）
function createCDQuestionList_table(url, params, btNames, btFunctions){

	var myColumns = [
			 {
             headerText: '序号',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: '4%',               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
             },
         		
			{header: '问题标题', dataIndex: 'titleName',width:'34%', headerAlign: 'center',align:'center',
                 renderer: function(v,r){
                     	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";>'+v+'</a>';
             	}
             	return outStr;
                }    
                },                  
                {header: '提问人', dataIndex: 'uploader',width:'15%', headerAlign: 'center',align:'center', renderer:function(v,r){                    
             	    var uploader_outStr = ''+v.name;
             	return uploader_outStr;
                }},              
                {header: '回复次数', dataIndex: 'commentRecord',width:'13%', headerAlign: 'center',align:'center', renderer:function(v){                            	
                	return v['commentCount'];
                }},
                {header: '解决情况', dataIndex: 'questionstatus',width:'13%' ,headerAlign: 'center',align:'center',
                  renderer: function(v){             
                   		if(v == 0){
                   			return '<img src="css/notsolve.gif"></img>';
                   		}
                   		else 
                   		return  '<img src="css/havesolve.gif"></img>';
                   }                             
                },       
                {header: '发布时间', dataIndex: 'uploadTime',width:'21%', headerAlign: 'center',align:'center'}
                
         
     ];

	var myTable = new createTable({},'100%','100%','知识列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
}
function createUnApprovalKnowledgeList_table(url, params, btNames, btFunctions){

	var myColumns = [
	   Edo.lists.Table.createMultiColumn(),//pl 这个是支持选择多行的选框条 
	   {
             headerText: '序号',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '知识标题',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 250,               
             align: 'left',
             renderer: function(v,r){
             	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	//alert(title_outStr);
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_simplist_title">'+v+'</span></a>';
             	}
             	return outStr;
             }
         },
     {
             headerText: '作者',
             dataIndex: 'KAuthors',
             headerAlign: 'center',                 
             align: 'left',
             width: 100,
             renderer: function(v,r){
             		var i=0;
             	var author_outStr = '';
             	if(v){
             		
             		v.each(function(o){
             			i++
             			author_outStr += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+author_outStr;
             	return author_outStr;
             }
         },
         {
             headerText: '关键词',
             dataIndex: 'keywords',
             headerAlign: 'center',                 
             align: 'left',
             width: 150,
             renderer: function(v,r){
             	var keywords_outStr = '';
             	var i=0;
             	if(v){ 
             	
             		v.each(function(o){
             			i++;
             			keywords_outStr +=  '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	keywords_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+keywords_outStr;
             	return keywords_outStr;
             }
         }  ,
            {
             headerText: '知识类型',
             dataIndex: 'knowledgetype',
             headerAlign: 'center',                 
             align: 'left',
             width: 110,
             renderer: function(v,r){
             	var knowledgetype_outStr = '';
           
             	knowledgetype_outStr +=  '<a href="javascript:showKnowledgetypeKnowledgeList('+v.id+',\''+v.name+'\')";>'+v.name + '</a>&nbsp';
             	
             	knowledgetype_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+knowledgetype_outStr;
             	return knowledgetype_outStr;
             }
         },
                 {
             headerText: '上传时间',
             dataIndex: 'uploadTime',
             headerAlign: 'center',                 
             align: 'left',
             width: 140,
             renderer: function(v,r){
             	
             	return r.uploadTime;
             }
         
            
         }  ,
         
         
         
         {
             headerText: '删除',
             dataIndex: 'titleName',
             headerAlign: 'center',  
             width: 40,               
             align: 'center',
             renderer: function(v,r){
             	var title_outStr = '';
				if(v){
				
	             	var outStr = '';
	             	outStr += '<a href=javascript:unvisibleKnowledge('+r.id+');>删除</a>';
             	}
             	return outStr;
             }
         }
         
         
     ];
     

	var myTable = new createTable({},'100%','100%','知识列表',myColumns,['批量发起审批'],[batchCreateApprovalFlow],url, params,true,null,false);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	this.reload = function(){
		myTable.search();
	}
	function batchCreateApprovalFlow(){
		var rs = myTable.getSelectedIds();
		if(rs.length == 0){
			creatalert("未选定!");
		}else{
			createBatchApprovalFlowWithFirstNode(rs);
		}
	}
	}
//avidm知识搜索结果列表
	function createAVIDMKnowledgeList_table(url, params, btNames, btFunctions){

	var myColumns = [
         
	   {
             headerText: '序号',
             dataIndex: 'index',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '知识标题',
             dataIndex: 'fileOriginalName',
             headerAlign: 'center',  
             width: 300,               
             align: 'left',
             renderer: function(v,r){
             	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	outStr += '<a href="http://'+r.avidmip+'/avidm/customize/knowledge/loginIntoAvidm.jsp?UserName='+r.Userid+'&documentIID='+r.documentIID+'&productIID='+r.productID+'&fileIID='+r.fileIID+'&versionIID='+r.versionIID+'><span class="knowledge_simplist_title">'+v+'</span></a>';
             	}
             	return outStr;
             }
         },
     {
             headerText: '作者',
             dataIndex: 'creator',
             headerAlign: 'center',                 
             align: 'left',
             width: 150,
             renderer: function(v,r){
             	
             	var author_outStr = r.creator;
         
             	author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+author_outStr;
             	return author_outStr;
             }
         }
          
         
     ];

	var myTable = new createTable({},'100%','100%','知识列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	
	this.reload = function(){
		myTable.search();
	}
	}
//avidm知识搜索结果列表
	function createAVIDMSearchKnowledgeList_table(url, params, btNames, btFunctions){
		

	var myColumns = [
         
	   {
             headerText: '序号',
             dataIndex: 'index',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '知识标题',
             dataIndex: 'titlename',
             headerAlign: 'center',  
             width: 245,               
             align: 'left',
             renderer: function(v,r){
             	
				if(v){
				
	             	var outStr = '';
	             	outStr += '<a href="http://'+r.avidmip+'/avidm/customize/knowledge/loginIntoAvidm.jsp?UserName='+r.userid+'&documentIID='+r.documentIID+'&productIID='+r.productID+'&fileIID='+r.fileIID+'&versionIID='+r.versionIID+'><span class="knowledge_simplist_title">'+v+'</span></a>';
             	}
             	return outStr;
             }
         },
     {
             headerText: '作者',
             dataIndex: 'creator',
             headerAlign: 'center',                 
             align: 'left',
             width: 80,
             renderer: function(v,r){
             	
             	var author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+v;
             	return author_outStr;
             }
         },
         {
             headerText: '上传时间',
             dataIndex: 'updatetime',
             headerAlign: 'center',                 
             align: 'left',
             width: 100,
             renderer: function(v,r){
             	
             	return '&nbsp;&nbsp;&nbsp;&nbsp;'+v;
             }
         },
           {
             headerText: '类型',
             dataIndex: 'ktype',
             headerAlign: 'center',                 
             align: 'left',
             width: 100,
             renderer: function(v,r){
             	
             	return '&nbsp;&nbsp;&nbsp;&nbsp;'+v;
             }
         }
         
     ];

	var myTable = new createAVIDMTable({},'100%','100%','知识列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
	
	this.reload = function(){
		myTable.search();
	}
	}	
	
function createBorrowKnowledgeList_table(url, params, btNames, btFunctions,tabletype){

	var myColumns = [
         
	   {
             headerText: '序号',
             dataIndex: 'knowledge',
             headerAlign: 'center',  
             width: 25,               
             align: 'center',
             renderer: function(v, r, c, i, data, t){
             	var outStr = i+1;
             	return outStr;
             }
         },
		
			{
             headerText: '知识标题',
             dataIndex: 'knowledge',
             headerAlign: 'center',  
             width: 150,               
             align: 'left',
             renderer: function(v,r){
             	var title_outStr = '';
             	var titlename=v.titleName;
				if(titlename){
					if(titlename.length > 4){
						title_outStr += titlename.substring(0,4)+'...';
					}else{
						title_outStr += titlename;
					}
	             	var outStr = '';
	             	
	             	if(null!=tabletype&&tabletype=='borrowing')
	             	outStr += '<a href="javascript:editborrowflow('+v.id+',\'borrower\','+r.id+')";><span class="knowledge_simplist_title">'+titlename+'</span></a>';
	             	 	if(null!=tabletype&&tabletype=='admin')
	             	outStr += '<a href=javascript:editborrowflow('+v.id+',"admin",'+r.id+');><span class="knowledge_simplist_title">'+titlename+'</span></a>';
             	    if(null!=tabletype&&(tabletype=='passborrow'||tabletype=='borrowed'||tabletype=='approval'))
	             	outStr += '<a href="javascript:showKnowledgeDetail('+v.id+',\''+title_outStr+'\',\'\','+r.id+')";><span class="knowledge_simplist_title">'+titlename+'</span></a>';
             
             	}
             	return outStr;
             }
         },
         {
             headerText: '关键词',
             dataIndex: 'knowledge',
             headerAlign: 'center',                 
             align: 'left',
             width: 150,
             renderer: function(v,r){
             	var keywords_outStr = '';
             	var i=0;
             	if(v.keywords){ 
             	
             		v.keywords.each(function(o){
             			i++;
             			keywords_outStr +=  '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	keywords_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+keywords_outStr;
             	return keywords_outStr;
             }
         },
      {
             headerText: '上传者',
             dataIndex: 'knowledge',
             headerAlign: 'center',                 
             align: 'left',
             width: 50,
             renderer: function(v,r){
             		var i=0;
             	var author_outStr = '';
             	var uploader=v.uploader;
             	if(uploader){
             		
             		
             			author_outStr += '<a href="javascript:showAuthorKnowledgeList('+uploader.id+',\''+uploader.name+'\')";>'+uploader.name + '</a>&nbsp';
             		
             	}
          
             	author_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+author_outStr;
             	return author_outStr;
             }
         }
         ,
  
                 {
             headerText: '借阅时间',
             dataIndex: 'startTime',
             headerAlign: 'center',                 
             align: 'left',
             width: 80,
             renderer: function(v,r){
             	
             	return v;
             } 
                },    {
             headerText: '借阅状态',
             dataIndex: 'status',
             headerAlign: 'center',                 
             align: 'left',
             width: 50,
             renderer: function(v,r){
             	
             	return v;
             } 
                }
         
     ];

	var myTable = new createTable({},'100%','100%','知识列表',myColumns,btNames,btFunctions,url, params,true,null);
	
	this.getKnowledgeList = function(){
		return myTable;
	}
}	
	

	
	
	
	

