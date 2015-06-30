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
	//上传者搜索框
var searchUploaders = Edo.create({
    		type: 'autocomplete', 
			width: 130, 			
			queryDelay: 500,
			url: 'user/user!searchuser.action',			
			popupHeight: '100',
			valueField: 'name', 
			displayField: 'name'
			
			
    	});	

//创建已订阅的上传者列表
    	var myExpertConfig6 = {
    		horizontalScrollPolicy : 'off'   		
    		};
    		
		var myColumns6 = [
                {
                    headerText: '已订阅的上传者',
                    dataIndex: 'interestItemName',
                    headerAlign: 'center',  
                            
                    align: 'center'
                } ,
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
    	myTable6 = new createTable(myExpertConfig6,'100%','50%','业务列表',myColumns6,[],[],'', {},false);	
    	
    	Edo.create({
    				type: 'panel', 				   
				    title: '请输入上传者名',              
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
						    children: [searchUploaders,
								    {id: 'submitBtn2',
									 type: 'button',
									 width:110, 
									 text: '提交订阅的上传者    '
									}	
						    
						    	]
					    	
						},
						{ type: 'box', 
						    title:'已订阅的上传者',        
						    width: 156,
						    height: 420,
						    border: [0,0,0,0],  
						    padding: [0,0,0,0],    
						    children: myTable6.getTable()
						 }
							
												
				        
				    ]
    		});
    		
    //导入用户定制的信息
		var typedata = cims201.utils.getData('../interestmodel/interestmodel!listinterest.action',{});
		var uploaderdata=[];				 
			typedata.each(function(o){				
				   if(o.interestItemType == 'uploader') {
				   uploaderdata.add(o);				      
				   }				 				
				});
		myTable6.setTableData(uploaderdata);
   
   
   
   Edo.get('submitBtn2').on('click', function(e){
	      //  if(pinput.valid()){ 	  
	        
	    var warn = cims201.utils.getData('../interestmodel/interestmodel!saveuploader.action',{json:searchUploaders.get('text')});
	    if(warn!='定制成功'){
	    	alert(warn);
	    }	    	    
	    var typedataa = cims201.utils.getData('../interestmodel/interestmodel!listinterest.action',{});
		var uploaderdataa=[];				 
			typedataa.each(function(o){				
				   if(o.interestItemType == 'uploader') {
				   uploaderdataa.add(o);				      
				   }				 				
				})
		myTable6.setTableData(uploaderdataa);
	  
	    //    } 
});

	function deleteinterestitem(rId){
	//alert("id===="+rId);
    		//向后台请求删除数据    		
    		var deleteInterest = cims201.utils.getData('../interestmodel/interestmodel!deleteUserInterest.action',{id:rId});
    		var r = myTable6.getRowById_zd(rId);
    	    myTable6.deleteRecord(r);   	
    	}
    	
    	
</script>


