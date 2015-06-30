Edo.create({
	id:'re',
	type:'app',
	layout: 'horizontal',
	collapseProperty: 'width',
    enableCollapse: true,
    splitRegion: 'east',
    splitPlace: 'before',
	width: '100%',
	height: '100%',
	border:[0,0,0,0],
    render:document.body,
    padding:[0,0,0,0],
    children: [
               {
            	   id:'task',
            	   type:'panel',
            	   width: '100%',
            	   height: '100%',
            	   layout: 'vertical',
            	   children: [
            	              {
            	            	  id:'tasktitle',
            	            	  type:'formitem',
            	            	  layout: 'horizontal',
            	            	  width: '100%',
            	            	  //height: 50,
            	            	  children: [

                                             {
	                                           id:'one',
	                                           type:'button',
	                                           text:'零部件分类',
	                                           width: 100,
		                                       height: 30,
		                                       onclick:function(e){module.set('src','cqz/js/module/CodeClassRuleManage.jsp');}
                                             },
                                            
                                             {
                                               id:'two',
                                               type:'button',
                                               text:'编码规则创建',
                                               width: 100,
                                               height: 30,
                                               onclick:function(e){module.set('src','cqz/js/yang/show.jsp');}
                                             },
                                             {
                                                 id:'three',
                                                 type:'button',
                                                 text:'编码规则管理',
                                                 width: 100,
                                                 height: 30,
                                                 onclick:function(e){module.set('src','cqz/js/yang/show.jsp');}
                                               },
            	            	             ]
            	              },
            	              {
            	            	  id:'module',
            	            	  type:'module',
            	            	  width: '100%',
            	            	  height: '100%',
            	              },
            	              ]
               },
               {
            	   id:'Na',
            	   type:'box',            	               	   
            	   width: 250,
            	   height: '100%',
            	   layout: 'vertical',
            	   verticalGap:'0',
           		   padding:[0,0,0,0],
           		   collapseProperty: 'width',
                   enableCollapse: true,
                   splitRegion: 'east',
                   splitPlace: 'before',
           	       children: [
            	              {
            	            	  id:'research',
            	            	  type:'formitem',
            	            	  layout: 'horizontal',
            	            	  //align: 'center',
            	            	  width: '100%',
            	            	  children:[
            	                       	 {
            	                       		 type:'text',
            	                       		 id:'ID',
            	                       		 width: '100%',
            	                       		 height: 30,
            	                       	 },
            	                       	 {
            	                       		 type:'button',
            	                       		 text: '搜索',
            	                       		 width: '30%',
            	                       		 height: 30,
            	                       	 }
            	                       	           ],
            	            	 
            		          },
            	              {
            	            	  id:'RelatedKnowledgePanel',
            	            	  type: 'panel',
            	            	  layout: 'vertical', 
            	            	  width: '100%',
            	            	  height: '100%',           	            	 
      	            		      title: '<span style="font-weight:bold;">相关推荐文档</span>',
            	            	 children:[
                                            {
                                        type: 'label',	   
                                        width: '100%',     	
                                        text: '<a href="http://wenku.baidu.com/link?url=Adfuo5j0rGqPkoyC5viAj1TnZ3vhX_6xCuIRLC0cdORQYivZ4BYb1Pdf7MawiWNWWYbbGD8TqIJ5v6oUgo7izKkm2V3cWJpdyM2MlWO_c1m " style="color:black;text-decoration:none;">模块化设计的编码定义</a>',
                                          
                                            },
                                            {
                                            	type:'group',
                                            	layout: 'horizontal',
                                            	children:[
                                            	          {
                                            	        	  type:'button',
                                            	        	  text:'相关',
                                            	          },
                                            	          {
                                            	        	  type:'button',
                                            	        	  text:'不相关',
                                            	          },]
                                            }
                                          
            	            		]
            	              },
           	              {
           	            	  id:'knowledgeKeyWordPanel',
            	              type: 'panel',
            	            	  layout: 'vertical', 
            	            	  width: '100%',
            	            	  height: '30%',
            	            	  title: '<span style="font-weight:bold;">主要信息</span>',
            	            	  bodyCls: 'cims201_knowledge_keyword',  
            	            	  children: [
            	            	             {
            	            	             	type: 'label',	   
           	            	             	    width: '100%',     	
            	            	              	text: '模块化设计',
            	            	             },
            	            	             {
             	            	             	type: 'label',	   
            	            	             	width: '100%',     	
             	            	              	text: '编码',
             	            	             }
            	            	         ]
           	              },
           	           {
           	            	  id:'recommended',
            	              type: 'panel',
            	            	  
            	            	  width: '100%',
            	            	  height: '20%',
            	            	  title: '<span style="font-weight:bold;">目标结果</span>',
            	            	  bodyCls: 'cims201_knowledge_keyword',  
            	            	  children: [
            	            	             {
                                                 type: 'label',	   
                                                 width: '100%',     	
                                                 text: '<a href="http://localhost:8080/gnhzb/main.jsp " style="color:black;text-decoration:none;">目标结果</a>',
                                                   
                                                     },
                                                      
            	            	         ]
           	              },
            	              {            	            	
            	            	 type:'formitem',
            	            	 layout: 'horizontal',
            	            	 width: '100%', 
            	            	 children:[
                                            {
                                            id:'button',
		                                    type:'button',
		                                    text: '上一步',
		                                    width: '40%',
		                                    height: 30,
	                                        },
	                                        {
	                                        id:'next',	
	                                        type:'button',
	                                        text: '下一步',
	                                        width: '40%',
	                                        height: 30,
	                                        }
            	            	           ]
            	              }
            	             ],
               },
               ],
});
//从后台取数据
cims201.utils.getData_Async('knowledge/knowledge!listRcommentKnowledge.action',{id:id},function(text){
	if(text != null && text != 'null' && text != ''){
		var relatedKnowledgeData = Edo.util.Json.decode(text);
		//相关推荐文档
		relatedKnowledgeData.each(function(o){
			var oneRelatedKnowledgeStr = '';
			
			oneRelatedKnowledgeStr += '<div class="knowledge_view_relate_word">';
			var title_outStr = '';
			if(o.titleName.length > 4){
				title_outStr += o.titleName.substring(0,4)+'...';
			}else{
				title_outStr += o.titleName;
			}
			oneRelatedKnowledgeStr += '<a href="javascript:showKnowledgeDetail('+o.id+',\''+title_outStr+'\')";>'+o.titleName+'</a>';
			oneRelatedKnowledgeStr += '<br>';
			oneRelatedKnowledgeStr += '<div style="float:left; padding-top:5px;">';
			if(o.commentRecord){
				var tempScore = o.commentRecord.rate;
			
				for(var i=1;i<=(tempScore-tempScore%1);i++){
					oneRelatedKnowledgeStr += '<div class="cims201_little_star_full"></div>';
				}
				if((tempScore%1) == 0){
					for(var j=i;j<=5;j++){
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
					}
				}else{
					oneRelatedKnowledgeStr += '<div class="cims201_little_star_half"></div>';
					for(var j=(i+1);j<=5;j++){
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
					}
				}
				oneRelatedKnowledgeStr += '</div>';
				oneRelatedKnowledgeStr += '&nbsp'+o.commentRecord.commentCount+'人评';
				
				oneRelatedKnowledgeStr += '</div>';
			}else{
				var tempScore = 0;
			
				for(var i=1;i<=(tempScore-tempScore%1);i++){
					oneRelatedKnowledgeStr += '<div class="cims201_little_star_full"></div>';
				}
				if((tempScore%1) == 0){
					for(var j=i;j<=5;j++){
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
					}
				}else{
					oneRelatedKnowledgeStr += '<div class="cims201_little_star_half"></div>';
					for(var j=(i+1);j<=5;j++){
						oneRelatedKnowledgeStr += '<div class="cims201_little_star_null"></div>';
					}
				}
				oneRelatedKnowledgeStr += '</div>';
				oneRelatedKnowledgeStr += '&nbsp'+0+'人评';
				
				oneRelatedKnowledgeStr += '</div>';
			}
							
			var oneRelatedKnowledge = Edo.create({
				type: 'box',
				border: [0,0,0,0],
				padding: [0,0,0,12],
				width : '100%',
				height : 45,
				children: [
					{type:'label', text: oneRelatedKnowledgeStr}
				]		
			});
			RelatedKnowledgePanel.addChild(oneRelatedKnowledge);
		})
	}else{
		RelatedKnowledgePanel.addChild({
			type: 'label',
			width: '100%',
			style: 'font-size:13px; color: blue;',
			text: '当前没有推荐知识'
		});
	}	
});
	
this.getRelatedKnowledge = function(){
	return RelatedKnowledgePanel;
}
