/**
 *名人专栏主页  江丁丁添加 2013-6-5
 *用于展示专栏知识 
*/
function createExpertHome(){
	
	//默认多少行
	var defaultPageSize = 10;
	
	var myUrl = {};
	var myInputForm = {};
	
	//领域id
	var domainName = "";
	
	//创建分页条
	var myPager = null;
	var myPager2 = null;
	var myPagerId;
	var myPagerId2;
	
	var expertsBox = Edo.create({
		type: 'panel',
		title: '专家列表',
		border: [1,1,1,1],
		padding: [0,0,0,0],
		width: '100%',
		height: '100%',
		layout: 'vertical',
		verticalScrollPolicy: 'auto',
		children: [
		           ]
	});
	
	var articlesBox = Edo.create({
		type: 'panel',
		title: '专家博文',
		border: [1,1,1,1],
		padding: [0,0,0,0],
		width: '100%',
		height: '100%',
		layout: 'vertical',
		verticalScrollPolicy: 'auto',
		children: [
		           ]
	});
	
	//首先判断paging bar是否存在
   	myPagerId = expertsBox.id+'paging';
	myPager = Edo.create({
		id: myPagerId,    
	    type: 'pagingbar',
	    width: '100%',
	    visible: false,
	    //autoPaging: true,		    
	    border: [0,1,1,1], 
	    padding: 2
	});	
	
	Edo.get(myPagerId).on('paging',function(e){
		searchExpert();	
	});
	
	//首先判断paging bar是否存在
   	myPagerId2 = articlesBox.id+'paging';
	myPager2 = Edo.create({
		id: myPagerId2,    
	    type: 'pagingbar',
	    width: '100%',
	    visible: false,
	    //autoPaging: true,		    
	    border: [0,1,1,1], 
	    padding: 2
	});	
	
	Edo.get(myPagerId2).on('paging',function(e){
		searchArticle();	
	});
	
	
	//当查询没有数据的时候显示该条
	var bBar = Edo.create({
		type: 'label',
		width: '100%',
		visible: false,
		style: ' color: red; font-size:16px; font-weight: bold; ',
		text: '没有查询到任何数据!'
	});
	
	var toppicture = Edo.create({
		type:'label',
//		text:'<image height=150 width=940 src=css/images/mr_banner.jpg>'
		text:'<image width="1200px" height="191px" src=css/images/mr_banner.jpg>'
	});
	
	var searchbox = Edo.create({
		type:'box',
		width:'100%',
		border: [0,0,0,0],
	    padding:[0,0,0,5],
	    layout:'horizontal',
	    horizontalGap:'5',
	    children:[
	              {
	            	  type:'box',
	            	  width:'90%',
	          		  border: [0,0,0,0],
	          	      padding:[0,0,0,0],
	          	      layout:'horizontal',
	          	      horizontalGap:'5',
	          	      children:[
									{
										  type:'label',
										  text:'专家所在的行业及领域&nbsp;&nbsp;<span style="color:grey">></span>'
									},
									{
										  type:'box',
										  id:'searchitembox',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  layout:'horizontal',
										  horizontalGap:'5',
										  children:[]
									}
	          	                ]
	              
	              },
	              {
	            	  type:'search',
	            	  id:'searchkey',
	            	  width:'200',
	            	  clearVisible:true,
	            	  text:'输入专家、行业、领域等试试',
	            	  ontrigger: function(e){
	            	        this.set('clearVisible', true);
	            	        keysearch(this.text);
	            	  },
	            	  oncleartrigger: function(e){
	            		  this.set('text', "");
	            	   }
	              }
	              ]
	});
	
	var domainselectbar = Edo.build({
		type: 'box',  
	    width:'100%',
	    border: [1,1,1,1],
	    padding:[0,0,0,0],
	    layout:'vertical',
	    bodyStyle:'background:#F3F8FC',
	    children:[
	              {
	            	  type:'box',
	            	  width:'100%',
	            	  height:'80',
	            	  border:[0,0,1,0],
	            	  padding:[0,0,0,0],
	            	  layout:'horizontal',
	            	  children:[
	            	            {
	            	            	type:'label',
	            	            	width:'100',
	            	            	height:'80',
	            	            	text:'行业：',
	            	            	style:'background:#EEEEEE; padding:5px 0 0 20px;'
	            	            },
	            	            {
	            	            	type:'checkgroup',
	            	            	id:'industry_checkgroup',
	            	            	repeatDirection: 'horizontal',
	            	                repeatItems: 8,
	            	                repeatLayout: 'table',       
	            	                itemWidth: '100px',
	            	                valueField: 'value',
	            	                multiSelect:false,
	            	                repeatSelect:true,
//	            	                value: [1,3],
	            	                data: [
	            	                    {text: '汽车零部件', value: '汽车零部件'},
	            	                    {text: '汽轮机', value: '汽轮机'},
	            	                    {text: '变压器', value:'变压器'},
	            	                    {text: '模具', value: '模具'},
	            	                    {text: '服装', value: '服装'}
	            	                ],
	            	                onselectionchange: function(e){
	            	                	var indusutry = this.getValue();

	            	                	var existcomponent=	iscomponentexist('searchitem1','searchitem1');
	            	            		if(null!=existcomponent)
	            	            		{
	            	            			existcomponent.destroy();	            	            			
	            	            		}
            	            				var searchitem1 = Edo.create({
            	            					
    	            	                			id:'searchitem1',
    	            	                			type:'text',
    	            	                			width:'150',
    	            	                			text:'行业:'+indusutry,
    	            	                			value:indusutry
    	            	                		
            	            				});
            	            				
            	            				Edo.get('searchitembox').addChild(searchitem1);
	            	            			
            	            				searchExpert();
            	            				searchArticle();
	            	                }
	            	            },
	            	            {
	            	            	type:'label',
	            	            	width:'100',
	            	            	height:'50',
	            	            	text:'<a href="#">取消选择</a>',
//	            	            	style:'color:blue;',
	            	            	onclick:function(){
	            	            		resetIndustry_checkgroup();
	            	            	}
	            	            }
	            	            ]
	            	  
	              },
	              {
	            	  type:'box',
	            	  width:'100%',
	            	  height:'80',
	            	  border:[0,0,0,0],
	            	  padding:[0,0,0,0],
	            	  layout:'horizontal',
	            	  children:[
	            	            {
	            	            	type:'label',
	            	            	width:'100',
	            	            	height:'80',
	            	            	padding:[5,0,0,5],
	            	            	text:'领域：',
	            	            	style:'background:#EEEEEE; padding:5px 0 0 20px;'
	            	            },
	            	            {
	            	            	type:'checkgroup',
	            	            	id:'domain_checkgroup',
	            	            	repeatDirection: 'horizontal',
	            	                repeatItems: 8,
	            	                repeatLayout: 'table',       
	            	                itemWidth: '100px',
	            	                valueField: 'value',
	            	                multiSelect:false,
	            	                repeatSelect:true,
	            	                data: [
	            	                    {text: '知识管理', value: '知识管理'},
	            	                    {text: '制造服务', value: '制造服务'},
	            	                    {text: '大批量定制', value:'大批量定制'},
	            	                    {text: '成组技术', value: '成组技术'},
	            	                    {text: '模块化设计', value: '模块化设计'},
	            	                    {text: '环境化设计', value: '环境化设计'},
	            	                    {text: '创新设计', value: '创新设计'},
	            	                    {text: '绿色制造', value: '绿色制造'},
	            	                    {text: '网络化制造', value: '网络化制造'},
	            	                    {text: '工业工程', value: '工业工程'},
	            	                    {text: 'PDM/PLM', value: 'PDM/PLM'},
	            	                    {text: 'ERP', value: 'ERP'},
	            	                    {text: 'MES', value: 'MES'},
	            	                    {text: 'CAD', value: 'CAD'},
	            	                    {text: 'CAM', value: 'CAM'},
	            	                    {text: 'CAE', value: 'CAE'},
	            	                    {text: 'CAPP', value: 'CAPP'}
	            	                   
	            	                ],
	            	                onselectionchange: function(e){
	            	                	var domain = this.getValue();
	            	                	
	            	                	var existcomponent=	iscomponentexist('searchitem2','searchitem2');
	            	            		if(null!=existcomponent)
	            	            		{
	            	            			existcomponent.destroy();	            	            			
	            	            		}
	            	                	
	            	                	var searchitem2 = Edo.create({
	            	                		
															type:'text',
															id:'searchitem2',
															width:'150',
															text:'领域:'+domain,
															value:domain
	            	                		
	            	                	});
	            	                	Edo.get('searchitembox').addChild(searchitem2);
	            	                	
	            	                	searchExpert();
	            	                	searchArticle();
	            	                }
	            	            },
	            	            {
	            	            	type:'label',
	            	            	width:'100',
	            	            	height:'50',
	            	            	text:'<a href="#">取消选择</a>',
//	            	            	style:'color:blue;',
	            	            	onclick:function(){
	            	            		resetDomain_checkgroup();
	            	            	}
	            	            }
	            	            ]
	            	  
	              }
	              ]
	});

	
//	var rightBt = '';
//	rightBt += '<a href="javascript:#";><span class="cims201_knowledge_topbar_bt6"><span class="cims201_article_topbar_addarticle">';
//	rightBt += '发布博文';
//	rightBt +='</span></span></a>';	
	
	var addArticleBox = Edo.create({
		type: 'box',
		border: [1,1,1,1],
		padding: [0,0,0,0],
		width: '100%',
		height: 40,
		bodyStyle:'background:#F3F8FC',
		horizontalAlign:'center',
		children: [
		           {
		        	   type:'label',
//		        	   text:rightBt
		        	   text:'<a href="javascript:addArticle()";><span><image align="left" src=css/images/askmanage.gif>发布博文</span></a>'
		           }
		           ]
	});
	
	
	var leftBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		width: '60%',
		//height: '100%',
		layout: 'vertical',
		horizontalAlign:'center',
		children:[
					expertsBox,
					myPager, 
					bBar
		          ]
	});
	
	
	
	var rightBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		width: '40%',
		//height: '100%',
		layout: 'vertical',
		horizontalAlign:'center',
		children: [
		           addArticleBox,
		           articlesBox,
		           myPager2, 
		           bBar
		           ]
	});
	
	
	var contentBox = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,5,0,5],
		width: '100%',
		//height: '100%',
		layout: 'horizontal',
		horizontalGap:'10',
		children: [
		           leftBox,
		           rightBox
		           ]
	});
		
	
	var mainbox = Edo.create({
		   type:'box',
	 	   border:[1,1,1,1],
	 	   padding:[0,10,0,10],
	 	   width:'100%',
	 	   verticalScrollPolicy: 'auto',
		   layout: 'vertical',
		   horizontalAlign:'center',
	 	   children:[
	 	             	toppicture,
	 	             	searchbox,
		            	domainselectbar,
		            	contentBox
	 	             ]
		});

	//查询专家列表
	function searchExpert(){
		
		var form = mainbox.getForm();// 获取表单值
		var formvalue = Edo.util.Json.encode(form);
		
	    myInputForm.pg = myPager.index;
	    myInputForm.size = myPager.size;
	    myInputForm.formvalue = formvalue;
	    
		//接收领域专家数据	    
    	var rData = cims201.utils.getData('expert/expert!listExpertByDomain.action',myInputForm);

    	expertsBox.removeAllChildren();	
		showExpert(rData.data);
		//如果数据为空
		if(rData.data == null ||rData.data.length == 0){
			myPager.set('visible',false);				
			bBar.set('visible',true);
			myPager.total = 0;
			myPager.totalPage = 0;
			myPager.refresh();
		}else{
			myPager.set('visible',true);
			bBar.set('visible',false);
			myPager.total = rData.total;
			myPager.totalPage = rData.totalPage;
			myPager.refresh();
		}

	}

	//检索最新名人文章
	function searchArticle(){
		var form = mainbox.getForm();// 获取表单值
		var formvalue = Edo.util.Json.encode(form);
		
		 myInputForm.pg = myPager2.index;
		 myInputForm.size = myPager2.size;
		 myInputForm.formvalue = formvalue;
		
//		alert(formvalue);
		var articleData = cims201.utils.getData('expert/expert!listArticleByDomain.action',myInputForm);
//		alert(articleData.total);
		
		articlesBox.removeAllChildren();
		showArticle(articleData.data);
		//如果数据为空
		if(articleData.data == null ||articleData.data.length == 0){
			myPager2.set('visible',false);				
			bBar.set('visible',true);
			myPager2.total = 0;
			myPager2.totalPage = 0;
			myPager2.refresh();
		}else{
			myPager2.set('visible',true);
			bBar.set('visible',false);
			myPager2.total = articleData.total;
			myPager2.totalPage = articleData.totalPage;
			myPager2.refresh();
		}

	};
	
	function keysearch(key){
		var expertData = cims201.utils.getData('expert/expert!expertKeySearch.action',{key:key});
		expertsBox.removeAllChildren();	
		showExpert(expertData.data);
		
		var articleData = cims201.utils.getData('expert/expert!articleKeySearch.action',{key:key});
		articlesBox.removeAllChildren();
		showArticle(articleData.data);
	};
	

	//根据data显示专家
	function showExpert(data){
		if(data){
			expertsBox.removeAllChildren();	
			var i = 0;
			data.each(function(o){				
				var oneExpert = Edo.create({
					type: 'box',
					border: [0,0,0,0],
					padding: [0,0,0,0],
					width: '100%',
					//height: 85,
					layout: 'horizontal',
					children:[
						createExpertDetail(o)
					]
				});
				
				expertsBox.addChild(oneExpert);
				
			});
		}
	}
	
	
	function createExpertDetail(r){
		var outReply = null;
		var namechildren = [];

		  var label_str = '';
		 		label_str += '<a href="javascript:showHisInfo(\''+r.id+'\',\''+r.username+'\')";>';		 		
		   		label_str += '【详情】';
		   		label_str += '&nbsp</a>';								
		 var oneName = Edo.create({
		 	type:'box',
		 	width:'100%',
		 	layout: 'horizontal',  
		 	children:[
		 		{type:'box',	 	     
		 	     border: [0,0,0,0],
			     padding: [0,0,0,0],
				 children: [					
						{type: 'label', 
						//id:'personimg' , 
						text: '<img src=thumbnail/'+r.picturePath+' height=120></img>'}					
					]
				 },
				 {type:'box',	 	     
		 	     border: [0,0,0,0],
			     padding: [0,0,0,0],
			     verticalGap: 2, 
			     layout: 'vertical',  
		 		 children: [					
						{type:'label',text:'姓名：'+r.username},
				 		{type:'label',text:'性别：'+r.sex},
				 		{type:'label',text:'邮箱：'+r.email},
		 				{type:'label',text:'擅长领域：'+r.treenodename},
		 				{type:'label',text:'介绍：'+r.introduction},
		 				{type:'label',text:label_str}
		 				 					 
				             //}		
					]
		 		 }	
		 		]
		 	
		 	});				
		 namechildren.add(oneName);		
		
			outReply = Edo.create({
				type: 'box',				
				border: [0,0,0,0],							
				padding: [0,0,0,0],
				width: '100%',
				layout: 'horizontal',  
				//horizontalAlign: 'center', 
				children: namechildren
			});	
		
		
		return outReply;
	}
	
	//根据data显示专家文章
	function showArticle(data){
		if(data){
			articlesBox.removeAllChildren();	
			var i = 0;
			data.each(function(o){			
				createArticleDetail(o);
				
			});
		}
	}
	
	function createArticleDetail(r){
		var articleDetailBox = Edo.create({
			type: 'box',
			border: [0,0,0,0],
			padding: [0,0,0,0],
//			width: '100%',
//			height:'100%',
			bodyCls: 'knowledge_list',
			layout: 'vertical'
		});
		
		var title_outStr = '';
		if(r.titleName.length > 20){
			title_outStr += r.titleName.substring(0,20)+'...';
		}else{
			title_outStr += r.titleName;
		}

		var titleNameLabel = Edo.create({
			type: 'box',
			border:[0,0,0,0],
			padding:[0,0,0,0],
			width: '100%',
			layout:'horizontal',
			children:[
			          {type: 'label',width:'280',text: '<a href=\"javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_list_title">'+title_outStr+'</span></a>'},
			          {type: 'label',width:'50',text: r.uploader.name},
			          {type: 'label',text: r.uploaddate}
			          ]
		});
		articleDetailBox.addChild(titleNameLabel);
		articlesBox.addChild(articleDetailBox);
	}
	
	function resetIndustry_checkgroup(){
		Edo.get('industry_checkgroup').resetValue();
		var existcomponent=	iscomponentexist('searchitem1','searchitem1');
		if(null!=existcomponent)
		{
			existcomponent.destroy();	            	            			
		}
		searchExpert();
		searchArticle();
	};

	function resetDomain_checkgroup(){
		Edo.get('domain_checkgroup').resetValue();
		var existcomponent=	iscomponentexist('searchitem2','searchitem2');
		if(null!=existcomponent)
		{
			existcomponent.destroy();	            	            			
		}
		searchExpert();
		searchArticle();
	};
	
	//设置检索的属性
	this.search = function(queryForm,url){
		if(url != null){
	    	myUrl = url;
	    }
	    for(var key in queryForm){
	    	myInputForm[key] = queryForm[key];
	    	

	    }	    
	    searchExpert();	
	    searchArticle();
	};

	
	
	this.getExpertHome= function(){
		return mainbox;
	};
}


function addArticle(){
	openNewTab('publisharticle', 'publisharticle',"<div class=cims201_tab_font align=center>发布博文</div>",{btIcon:'cims201_expert_icon_publisharticle_small'});
};
