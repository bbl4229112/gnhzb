//显示知识列表
function createKnowledgeList(){
	//定义摘要的字数和行数
	var abstractTextLength = 50;
	var abstractTextLine = 2;    
		
	var myConfig1 = {
		verticalLine: false,
		horizontalLine: false,
		headerVisible: false,
		horizontalScrollPolicy : 'off',
		rowHeight: 80,
		autoColumns: false
	};
	var myColumns1 = [
          {
              headerText: '内容',
              dataIndex: 'titleName',
              headerAlign: 'center',                 
              width: 750,             
              align: 'left',
              renderer: function(v,r){
                	var outStr = '';
                	outStr += '<div class="knowledge_list">';
                	outStr += '<span style="color:red; cursor:pointer;"><a href="javascript:alert(1);">';
                	outStr += '<span style="color:red;font-size: 12px;font-weight:bold; ">';
                	outStr += r.titleName;
                	outStr += '</span>';
                	outStr += '</a></span><br>';
                	outStr += '[作者]';
                	
                	r.KAuthors.each(function(o){
                		outStr += '<a href="javascript:showAuthorKnowledgeList('+o.id+',\''+o.name+'\')";>';
                		outStr += o.name;
                		outStr += '&nbsp</a>';
                	});
                	
                	outStr += r.KAuthors;
                	outStr += '&nbsp&nbsp';               	
                	
                	if(r.keywords){
	                	outStr += '[关键字]';
	                	outStr += r.keywords;
	                	outStr += '&nbsp&nbsp';
                	}
                	
                	outStr += '[<span style="color:blue;">所属类别</span>]';
                	outStr += r.ktype;
                	
                	outStr += '<br>';
                	
             		if(r.abstractText){
                		var truncateAbstract = new Array();
              			
              			for(var i=0;i<abstractTextLine;i++){
              				if(r.abstractText.length > (abstractTextLength*i)){
              					truncateAbstract[i] = r.abstractText.substring(i*abstractTextLength,(i+1)*abstractTextLength);
              					if(i == (r.abstractText.length-1)){
              						truncateAbstract[truncateAbstract.length] = '......';
              					}
              				}else{        		
              					truncateAbstract[i] = r.abstractText.substring(i*abstractTextLength,r.abstractText.length);              					
              				}	
              			}
              			
              			var truncateAbstractStr = '';
              			truncateAbstract.each(function(o){
              				truncateAbstractStr += o;
              				truncateAbstractStr += '<br>';
              			});
                		
	                	outStr += '[摘要]';                   		                	
	                	outStr += truncateAbstractStr;
	                	outStr += '&nbsp&nbsp';
                	}
                    
                    outStr += '</div>'; 	
                	return outStr;
                }
          },
          {
              headerText: '查看详细',
              dataIndex: '',
              headerAlign: 'center',                 
              align: 'left',
              //width: 50,        
              renderer: function(v,r){
              	var outStr = '';
              	outStr += '<br>';
              	for(var i=0;i<abstractTextLine;i++){
              		outStr += '<br>';
              	}
              	outStr += '<span class="icon-cims201-detail" style="padding-left:20px; cursor:pointer;"><a style="curor:pointer;" onclick="alert(1);">详情</a></span>';
              	//outStr += '<a style="curor:pointer;" onclick="alert(1);">详情</a>';	
              	return outStr;		
              }
          }              
      ];
   	var myTable1_rt = new createTable(myConfig1,'100%','100%','知识列表',myColumns1,[],[],'', {});
 	
 	this.getKnowledgeList = function(){
 		return myTable1_rt;
 	}
}

//显示作者相关知识的列表
function showAuthorKnowledgeList(id,name){ 	
	 	var myTabItem = {};
	 	myTabItem.selected = {}; 
	 	myTabItem.selected.id	= 'showAuthorKnowledgeList'+id;
	 	var queryForm = {name: 'kauthorid',value:id};
		var searchlist = {searchlist: [queryForm]};
	 	var queryFormStr = Edo.util.Json.encode(searchlist);
	 	
		myTabItem.selected.url= 'knowledge/ui!knowledgesearchlist.action?url=knowledge!ksearch.action&formvalue='+queryFormStr;
		myTabItem.selected.name = name+'的知识';
	 	openNewTabFromChild(myTabItem,this);
	
}

//显示关键词相关知识的列表
