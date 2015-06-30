/**
 * 美国专利检索页面  jiangdingding添加 2013-5-19
 * @returns {createPatentUSSearchHome}
 */

function createPatentUSSearchHome() {
	

	var combowidth = 250;
	var inputwidth = '70%';
	var comboData = [   {text: '全部(All Fields)',value:''},
	                    {text: '主题(Title)', value: 'TI'},
		        		{text:'摘要(Abstract)', value:'ABTX'},
		        		{text:'发布时间(Issue Date)', value:'ISD'},
		        		{text:'专利号(Patent Number)', value:'PN'},
		        		{text:'申请日期(Application Date)', value:'AD'},
		        		{text:'申请序列号(Application Serial Number)', value:'AP'},
		        		{text:'专利类型(Application Type)', value:'KD'},
		        		{text:'国际分类号（International Classification）', value:'CIPC'},
		        		{text:'Assignee Name', value:'ASNM'},
		        		{text:'Assignee City', value:'ASCI'},
		        		{text:'Assignee State', value:'ASST'},
		        		{text:'Assignee Country', value:'ASCO'},
		        		{text:'Current US Classification', value:'ORCL'},
		        		{text:'Primary Examiner', value:'XP'},
		        		{text:'Assistant Examiner', value:'XA'},
		        		{text:'Inventor Name', value:'INNM'},
		        		{text:'Inventor City', value:'INCI'},
		        		{text:'Inventor State', value:'INST'},
		        		{text:'Inventor Country', value:'INCO'},
		        		{text:'Government Interest', value:'GOTX'},
		        		{text:'Attorney or Agent', value:'LREP'},
		        		{text:'PCT Information', value:'PCTA'},
		        		{text:'Foreign Priority', value:'PRFR'},
		        		{text:'Reissue Data', value:'REIS'},
		        		{text:'Related US App. Data', value:'RLAP'},
		        		{text:'Referenced By', value:'UREF'},
		        		{text:'Foreign References', value:'FREF'},
		        		{text:'Other References', value:'OREF'},
                        {text:'Claim(s)', value:'ACLM'},
                        {text:'Description/Specification', value:'PPDB'}
					];
	
   	var logic = 	Edo.create({
		type: 'combo',
		id:'co1',
	    width: '70',
	    displayField: 'name', 
       	valueField: 'value', 
       	selectedIndex: 0,
       	horizontalAlign:'left',
       	onclick: function(e){
//			registEnter();
		},  
		onblur: function(e){
//			unregistEnter();
		},
       	data: [{name:'与(AND)',value:'and'},{name:'或(OR)',value:'or'},{name:'非(ANDNOT)',value:'andnot'}]
	});	
	
    //第一行搜索框
    var firstCombo = new Edo.controls.ComboBox()
    .set({
    	id: 'FIELD1',
    	displayField: 'text', 
       	valueField: 'value', 
       	readOnly: true,
		selectedIndex: 0,
       	width: combowidth,
       	data: comboData,
       	onclick: function(e){
//			registEnter();
		},  
		onblur: function(e){
//			unregistEnter();
		}
    });


    var firstInput = Edo.create({
  		id: 'TERM1',
  		type: 'text',
  		width: inputwidth,
   		//width: '100%',
   		onclick: function(e){
//			registEnter();
		},  
		onblur: function(e){
//			unregistEnter();
		}
   	});
    
    var firstLine = Edo.create({
    	type: 'box',
	    width: '100%',
	    //height: '100%',
	    padding: [0,0,0,75],
	    border: [0,0,0,0],      
	    layout: 'horizontal',   
	    horizontalAlign:'center',
	    children: [
            
	    	firstCombo,
	    	firstInput       	      
	    ]
    });
	
    //第二行搜索框
    var secondCombo = new Edo.controls.ComboBox()
    .set({
    	id: 'FIELD2',
    	displayField: 'text', 
       	valueField: 'value', 
       	readOnly: true,
		selectedIndex: 0,
       	width: combowidth,
       	data: comboData,
       	onclick: function(e){
//			registEnter();
		},  
		onblur: function(e){
//			unregistEnter();
		}
    });


    var secondInput = Edo.create({
  		id: 'TERM2',
  		type: 'text',
  		width: inputwidth,
   		//width: '100%',
   		onclick: function(e){
//			registEnter();
		},  
		onblur: function(e){
//			unregistEnter();
		}
   	});
    
    var secondLine = Edo.create({
    	type: 'box',
	    width: '100%',
	    //height: '100%',
	    padding: [0,0,0,0],
	    border: [0,0,0,0],      
	    layout: 'horizontal',   
	    horizontalAlign:'center',
	    children: [
	        logic,
	    	secondCombo,
	    	secondInput       	      
	    ]
    });
    
    var yearsCombo = new Edo.controls.ComboBox()
    .set({
    	id: 'd',
    	displayField: 'text', 
       	valueField: 'value', 
       	readOnly: true,
		selectedIndex: 0,
       	width: 200,
       	padding: [0,0,0,70],
       	data: [
       	    {text: '1976 to present[full-text]',value:'PTXT'},
            {text: '1790 to present[entire database]', value: 'PALL'}
       	       ]
    });
	
    var yearsbox = Edo.create({
    	type:'box',
    	width:'100%',
    	layout:'horizontal',
    	horizontalGap:'10',
    	padding: [10,0,0,0],
	    border: [0,0,0,0],  
	    horizontalAlign:'center',
    	children:[
    	          {type:'label',text:'请选择年份：'},
    	          yearsCombo
    	          ]
    });

    
    var patentusform = Edo.create({
    	id: 'patentusform',
    	type: 'formitem',        
	    width: '100%',
	    //height: '100%',
	    padding: [0,0,0,0],
	    border: [0,0,0,0],      
	    layout: 'vertical',  
	    horizontalAlign:'center',
	    children: [
	        firstLine,
	        secondLine,
	        yearsbox
	    ]
    });
	
/*	var choose_databasebox=Edo.create({
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
			     	  id:'choose_patentusdb', 
			     	  repeatDirection:'horizontal',
			     	  repeatLayout:'flow',
			     	  multiSelect:false,
			     	  data:[
			     	        {text:'美国专利网', value:'0', checked:true},
			     	        {text:'服务器数据库', value:'1'}
			     	        ]
		          }
		          ]
	});
*/    
    
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
		        	  type:'label',style:'color:red',text:'1790至1975的专利仅有日期、专利号以及分类号可供查询。'
		          },
		          {
		        	  type:'label',style:'color:red',text:'(Patents from 1790 through 1975 are searchable only by Issue Date, Patent Number, and Current US Classification.)'
		          },
		          {
		        	  type:'label',style:'color:red',text:'以专利号为关键词进行搜索时，专利号仅可有七位字符长度（包含符号在内，符号可选）。'
		          },
		          {
		        	  type:'label',style:'color:red',text:'（When searching for specific numbers in the Patent Number field, patent numbers must be seven characters in length, excluding commas, which are optional.）'
		          },
					{
						type:'label',text:'友情链接：<a href="http://patft.uspto.gov/netahtml/PTO/search-bool.html">美国专利网</a>'
					}
		          ]
	});
	
	
	
	var patentussearchbox = Edo.create({
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
		        	   title:'美国专利检索',
		        	   width:'100%'       	   
		           },
		           {
		        	   type:'label',
		        	   text:'美国专利检索',
		        	   style:'font-size:25px;padding-top:20px;padding-bottom:20px;font-family:微软雅黑, Verdana;font-weight:bold;color:black;'
		           },	           
		           patentusform,
//		           choose_databasebox,
		           buttons,
		           introduction
		           ]
	});    	   	
	
	
	function submitSearch(e){
		 if (patentussearchbox.valid){ 		   
			 var o = patentusform.getForm(); // 获取表单值
			 //判断表单，如果值为空，则提醒输入
			 var values = 0;
			 for(var key in o){
				 if(o[key]!=""&&o[key]!=null){
					 values++;
				 }
			 }
//			 alert(values);
			 if(values<3){
				 Edo.MessageBox.alert("提醒", "请输入搜索条件！"); 
			 }else{				 
				 var searchPatentForm = Edo.util.Json.encode(o);
//				 alert(searchPatentForm);
				 if(mySubmitBt.enable == true){
					 //设置界面loading							
					 createLoading();
					 mySubmitBt.set('enable',false);
					//从专利网查询
					 openNewTab('patentuslist', 'patentuslist',
							 "<div class=cims201_tab_font align=center>专利列表</div>", {searchPatentForm:searchPatentForm,btIcon:'cims201_mypatent_icon_patentussearch_small'});	
//					 if(Edo.get('choose_patentusdb').getValue()!='1'){
//						 
//						 //从专利网查询
//						 openNewTab('patentuslist', 'patentuslist',
//								 "<div class=cims201_tab_font align=center>专利列表</div>", {searchPatentForm:searchPatentForm,btIcon:'cims201_mypatent_icon_patentussearch_small'});	
//					 }else{
//						 //从服务器数据库检索
//						 openNewTab('patentusdblist', 'patentusdblist',
//								 "<div class=cims201_tab_font align=center>专利列表</div>", {searchPatentForm:searchPatentForm,btIcon:'cims201_mypatent_icon_patentussearch_small'});	
//						 
//					 }	
					 setTimeout(deleteMask, 500);	
					 mySubmitBt.set('enable',true);
				 }						
			 }

		  }else{
			  Edo.MessageBox.alert("提醒", "请输入搜索条件！");
		  }
	}
	
	function resetSearch(e){
		patentusform.reset();
	}
	
	
	this.getPatentUSSearchHome = function() {
		return patentussearchbox;
	};
	
	
	
}