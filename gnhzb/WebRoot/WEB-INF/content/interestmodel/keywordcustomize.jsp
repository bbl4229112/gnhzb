<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		

		<link href="${ctx}/js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />

		<script src="${ctx}/js/edo/edo.js" type="text/javascript"></script>
		<script src="${ctx}/js/cims201.js" type="text/javascript"></script>
		<script src="${ctx}/js/utils.js" type="text/javascript"></script>
		
		<script src="${ctx}/js/commontools/commonTable.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/commonTree.js" type="text/javascript"></script>
		<script src="${ctx}/js/privilege/roleUserTree.js" type="text/javascript"></script>
		
		<script src="${ctx}/js/commontools/Window.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/PopupManager.js" type="text/javascript"></script>
		<script src="${ctx}/js/commontools/commonForm.js" type="text/javascript"></script>
		<script src="${ctx}/js/tree/domaintree.js" type="text/javascript"></script>
		<script src="${ctx}/js/tree/commontree.js" type="text/javascript"></script>
		
	</head>

	<body>
    </body>
</html>


<script type="text/javascript">
	//关键词搜索框
var searchKeywords = Edo.create({
    		type: 'autocomplete', 
			width: 130, 
			queryDelay: 500,
			url: '../knowledge/knowledge!searchKeywords.action',
			popupHeight: '100',
			valueField: 'name', 
			displayField: 'name'
			
    	});

//创建已订阅的关键词列表
    	var myExpertConfig5 = {
    		horizontalScrollPolicy : 'off'   		
    		};
    		
		var myColumns5 = [
                {
                    headerText: '已订阅的关键词',
                    dataIndex: 'interestItemName',
                    headerAlign: 'center',                             
                    align: 'center'
                },
                   {
                	headerText: '',
                	dataIndex: 'interestItemName',            
                    align: 'center',
                    width: 20,
                    renderer: function(v,r){             
                    	
                    	var out_str = '';                   	
               
                    	out_str += '<span class="icon-cims201-delete" onclick="deleteinterestitem('+r.id+');">';
                    	out_str += '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}                 
            ];
    	myTable5 = new createTable(myExpertConfig5,'100%','50%','业务列表',myColumns5,[],[],'', {},false);	
    	
    	Edo.create({
    				type: 'panel', 				   
				    title: '请输入关键词',              
				    border: 1,                
				    padding: 10,
				    height:300,
				    width:430,
				    render:document.body,
				    layout: 'horizontal',  
				    horizontalGap: 50,   
                	children: [
                	//第一排关键字
				       {	type: 'panel',
				      		title :'订阅框',
					    	border: [1,1,1,1],
						    padding: [10,10,10,10],
						    height:200,
						    width:180,
						    layout: 'vertical',
						    horizontalAlign: 'center', 
						    verticalGap: 10,   
						    children: [searchKeywords,
								    {id: 'submitBtn1',
									 type: 'button',
									 width:110, 
									 text: '提交定制的关键词    '
									}	
						    
						    	]
					    	
						},
						{ type: 'box', 
						    title:'已订阅的关键词',        
						    width: 156,
						    height: 420,
						    border: [0,0,0,0],  
						    padding: [0,0,0,0],    
						    children: myTable5.getTable()
						 }
							
												
				        
				    ]
    		});
    		
    //导入用户定制的信息
		var typedata = cims201.utils.getData('../interestmodel/interestmodel!listinterest.action',{});
		var keworddata=[];				 
			typedata.each(function(o){				
				   if(o.interestItemType == 'keyword') {
				   keworddata.add(o);				      
				   }				 				
				});
		myTable5.setTableData(keworddata);
   
   
   
   Edo.get('submitBtn1').on('click', function(e){
	      //  if(pinput.valid()){ 	
	     // alert(searchKeywords.get('text'));  
	    var mykeword = searchKeywords.get('text');  
	    var warn = cims201.utils.getData('../interestmodel/interestmodel!savekeyword.action',{json:mykeword});
	    if(warn!="定制成功"){
	    	alert(warn);
	    } 
	    var typedataa = cims201.utils.getData('../interestmodel/interestmodel!listinterest.action',{});
		var keworddataa=[];				 
			typedataa.each(function(o){				
				   if(o.interestItemType == 'keyword') {
				   keworddataa.add(o);				      
				   }				 				
				});
		myTable5.setTableData(keworddataa);
	  
	    //    } 
});

	function deleteinterestitem(rId){
	//alert("id===="+rId);
    		//向后台请求删除数据    		
    		var deleteInterest = cims201.utils.getData('../interestmodel/interestmodel!deleteUserInterest.action',{id:rId});
    		var r = myTable5.getRowById_zd(rId);
    	    myTable5.deleteRecord(r);   	
    	}
    	
    	
</script>


