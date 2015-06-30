/**
 * 专利检索页面 jiangdingding 添加 2013-5-13
 * @returns {createPatentChineseSearchHome}
 */


// 创建专利搜索状态 box				

function createPatentChineseSearchHome() {
	
	//定义搜索字段数组
	var comboArray = new Array();
	
	var choose_databasebox=Edo.create({
		type:'box',
		width:'680',
	  	border:[0,0,0,0],
		padding:[0,0,0,0],
		layout:'horizontal',
		horizontalAlign:'left',
		children:[{
					type:'label',
					text:'选择数据源：'
				  },
		          {
			      	  type:'radiogroup', 
			     	  id:'choose_patentdb', 
			     	  repeatDirection:'horizontal',
			     	  repeatLayout:'flow',
			     	  multiSelect:false,
			     	  data:[
			     	        {text:'国家知识产权局', value:'0', checked:true},
			     	        {text:'服务器数据库', value:'1'}
			     	        ]
		          }
		          ]
	});
	var patentchform = Edo.create({
		id:'patentchform',
		type:'box',
		width:'680',
	  	border:[1,1,1,1],
		padding:[0,0,0,0],
		layout:'vertical',
		horizontalAlign:'center',
		verticalGap:'0',
		children:[
		          {
		        	type:'formitem',
		        	border:[1,1,0,1],
		        	padding:[2,0,2,0],
		        	width:'100%',
		        	horizontalAlign:'left',
		        	children:[
		        	          {
		        	        	  type:'radiogroup', 
		        	        	  id:'selectbase', 
		        	        	  repeatDirection:'horizontal',
		        	        	  repeatLayout:'flow',
		        	        	  multiSelect:false,
		        	        	  data:[
		        	        	        {text:'全            部', value:'0', checked:true},
		        	        	        {text:'发            明', value:'11'},
		        	        	        {text:'实 用 新 型', value:'22'},
		        	        	        {text:'外 观 设 计', value:'33'}
		        	          			]
		        	          }
		        	          ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'申请（专利）号:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'text',id:'app_code',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'名            称:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'text',id:'patent_name',width:'200'}]
									  }											        	            ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'摘            要:',
										labelAlign:'right',
										labelWidth:'100',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'text',id:'abstractcontent',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'申    请   日:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'date',id:'app_date',width:'200',popupWidth:'200'}]
									  }
									 ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'公开（公告） 日:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'date',id:'pub_date',width:'200',popupWidth:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'公开（公告）号:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'text',id:'pub_code',width:'200'}]
									  }											        	            ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'分    类    号:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'text',id:'cat_code',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'主 分 类 号:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'text',id:'IPC',width:'200'}]
									  }											        	            ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'申请（专利权）人:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'text',id:'app_person',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'发明（设计）人:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'text',id:'inv_person',width:'200'}]
									  }											        	            ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'地             址:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'text',id:'app_address',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'国  际  公  布:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'text',id:'int_pub',width:'200'}]
									  }											        	            ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'颁    证   日:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'date',id:'cerdate',width:'200',popupWidth:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'专利 代理 机构:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],
										  children:[{type:'text',id:'sub_agent',width:'200'}]
									  }											        	            ]
		          },
		          {
		        	  type:'box',
		        	  border:[1,1,1,1],
		        	  padding:[2,0,2,0],
		        	  layout:'horizontal',
		        	  horizontalAlign:'center',
		        	  width:'100%',
		        	  children:[
									{
										type:'formitem',
										label:'代     理    人:',
										labelWidth:'100',
										labelAlign:'right',
										border:[0,0,0,0],
										padding:[0,20,0,0],
										children:[{type:'text',id:'sub_person',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'优     先    权:',
										  labelWidth:'100',
										  labelAlign:'right',
										  border:[0,0,0,0],
										  padding:[0,0,0,0],										  
										  children:[{type:'text',id:'priority',width:'200'}]
									  },
									  {
										  type:'formitem',
										  label:'页            码:',
										  visible:false,
										  children:[{type:'text',value:'1', id:'pg',width:'200'}]
									  }
								]
		          }        
		          
		          
		          ]
	});
	
	
    var mySubmitBt = Edo.create({
    	type: 'button', 
    	text:'确定提交搜索', 
    	onclick: submitSearch
    });
    var resetBt = Edo.create({
    	type: 'button', 
    	text:'重置搜索选项', 
    	onclick: resetSearch
    });
	var buttons = Edo.create({
		type:'box',
		border:[0,0,0,0],
		padding:[10,0,0,0],
		layout:'horizontal',
		children:[		         
		         mySubmitBt,resetBt
		       ]
	});
	
	var introduction = Edo.create({
		type:'box',
		width:'100%',
		border:[0,0,0,0],
		padding:[30,0,0,0],
		layout:'vertical',
		horizontalAlign:'center',
		verticalGap:0,
		children:[
		          {
		        	  type:'label',style:'color:red',text:'可选数据源：①国家知识产权局：从国家知识产权局抓取您所查询的专利，请检查网络，并耐心等待；'		        		  
		          },
		          {
		        	  type:'label',style:'color:red',text:'②服务器数据库：从服务器数据库检索您所查询的专利。'		        		  
		          },
				  {
						type:'label',text:'友情链接：<a href="http://www.sipo.gov.cn/zljs/">国家知识产权局</a>'
				  }
		          ]
	});
	
	var patentchsearchbox = Edo.create({
		type: 'box',
		border : [1,1,1,1],
		padding : [0,0,0,0],
		width: '100%',
        height: '100%',
		verticalScrollPolicy: 'auto',
		layout: 'vertical',
		horizontalAlign:'center',
		children: [
		           {
		        	   type:'panel',
		        	   border:[0,0,0,0],
		        	   padding:[0,0,0,0],
		        	   title:'中国专利检索',
		        	   width:'100%'       	   
		           },
		           {
		        	   type:'label',
		        	   text:'中国专利检索',
		        	   style:'font-size:25px;padding-top:20px;padding-bottom:20px;font-family:微软雅黑, Verdana;font-weight:bold;color:black;'
		           },		           
		           patentchform,
		           choose_databasebox,
		           buttons,
		           introduction
		           ]
	});    	   	

	
	
	function submitSearch(e){
		 if (patentchsearchbox.valid){ 		   
			 var o = patentchform.getForm(); // 获取表单值
			 //判断表单，如果值为空，则提醒输入
			 var values = 0;
			 for(var key in o){
				 if(o[key]!=""&&o[key]!=null){
					 values++;
				 }
			 }
			 if(values<2){
				 Edo.MessageBox.alert("提醒", "请输入搜索条件！"); 
			 }else{				 
				 var searchPatentForm = Edo.util.Json.encode(o);
//				 alert(searchPatentForm);
				 if(mySubmitBt.enable == true){
					 //设置界面loading							
					 createLoading();
					 mySubmitBt.set('enable',false);
					 //判断从服务器数据库还是中国专利网查询
					 if(Edo.get('choose_patentdb').getValue()!='1'){
						 //从中国专利网查询
						 openNewTab('patentchineselist', 'patentchineselist',
								 "<div class=cims201_tab_font align=center>专利列表</div>", {searchPatentForm:searchPatentForm,btIcon:'cims201_mypatent_icon_patentchinesesearch_small'});	
					 }else{
						 //从服务器数据库检索
						 openNewTab('patentchinesedblist', 'patentchinesedblist',
								 "<div class=cims201_tab_font align=center>专利列表</div>", {searchPatentForm:searchPatentForm,btIcon:'cims201_mypatent_icon_patentchinesesearch_small'});	
						 
					 }	
					 setTimeout(deleteMask, 500);	
					 mySubmitBt.set('enable',true);
				 }						
			 }

		  }else{
			  Edo.MessageBox.alert("提醒", "请输入搜索条件！");
		  }
	}
	
	function resetSearch(e){
		patentchform.reset();
	}
	
	//相应回车事件
	patentchform.enter = function(){
		//首先将回车锁定
		enterlock = false;	
		setTimeout(submitSearch, 1000);
		//然后打开回车
		setTimeout(function(){enterlock = true;},800);	
	};	
	
	this.getPatentChineseSearchHome = function() {
		return patentchsearchbox;
	};
}