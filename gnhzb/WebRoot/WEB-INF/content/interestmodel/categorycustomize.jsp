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
	//从分类树接收数据
var treeData2 = cims201.utils.getData('../tree/privilege-tree!listCDTreeNodes.action',{treeType:"categoryTree"});
var treeColumns2 = [{
				      header: '树名',
				      dataIndex: 'name',				     
				      renderer: function(v, r){
                    	return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '+(r.checked ? 'e-table-checked' : '')+'"></div>'+v+'</div>';}
				      
				 }							 				 
				 ];						 
var myTree2 = cims201.tree.createTree('categoryTree',treeColumns2,treeData2,categoryTreeSelectEvent,350,375,'Multi');

//创建相应领域的专家列表
    	var myExpertConfig4 = {
    		horizontalScrollPolicy : 'off'   		
    		};
    		
		var myColumns4 = [
                {
                    headerText: '已订阅的节点',
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
                    	//alert(r.interestItem);
                    	var out_str = '';                   	
               
                    	out_str += '<span class="icon-cims201-delete" onclick="deleteinterestitem('+r.id+');">';
                    	out_str += '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}                 
            ];
    	myTable4 = new createTable(myExpertConfig4,'100%','100%','业务列表',myColumns4,[],[],'', {},false);
    		
	Edo.create({
		 			  type: 'box',                     
					  border: [0,0,0,0],
					  padding: [0,0,0,0],
					  width: 568,
					  height: 420,
					  layout: 'horizontal',
					  render: document.body, 
					  children: [						 
                	     {                    
				    		 
						    type: 'panel', 
						    title:'领域树',        
						    width: 350,
						    height: 400,
						    border: [1,1,1,1],  
						    padding: [0,0,0,0],    						   
						    children: [
						        myTree2
						    ]
					     },
					     {                    
				    		 
						    type: 'box', 
						    width: 50,
						    height: 400,
						    border: [0,0,0,0],  
						    padding: [0,0,0,0],    
						    layout: 'vertical',
						    horizontalAlign: 'center',
						    verticalAlign: 'middle',
						    verticalGap:10, 
						    
						    children: [
						  
						    {
           						 id: 'submitBtn4',type: 'button', height:60,width:40,text:'订阅>'
        				     }
						    ]
					     },
					     {                    
				    		 
						    type: 'box', 
						    title:'已订阅的领域树节点',        
						    width: 156,
						    height: 420,
						    border: [0,0,0,0],  
						    padding: [0,0,0,0],    
						    children: myTable4.getTable()
					     }
					     ]
	
	
	
	
	});
	
		//导入用户定制的域信息
		var typedata = cims201.utils.getData('../interestmodel/interestmodel!listinterest.action',{});
		var categorydata=[];				 
			typedata.each(function(o){				
				   if(o.interestItemType == 'category') {
				   categorydata.add(o);				      
				   }				 				
				});
		myTable4.setTableData(categorydata);
	
		
		
		//域树的选择按钮	
	function categoryTreeSelectEvent(){
	}
	
	
	Edo.get('submitBtn4').on('click', function(e){
	      //  if(pinput.valid()){ 
	     var sels = cims201.tree.getTreeSelected(myTree2);            
            var idset = [];
            for(var i=0,l=sels.length; i<l; i++){               
                idset.add(sels[i].id);
            } 
           
	    cims201.utils.getData('interestmodel!savecategoryselect.action',{json:idset}); 
	    var typedataa = cims201.utils.getData('../interestmodel/interestmodel!listinterest.action',{});
		var categorydataa=[];				 
			typedataa.each(function(o){				
				   if(o.interestItemType == 'category') {
				   categorydataa.add(o);				      
				   }				 				
				});
		myTable4.setTableData(categorydataa);
	  
	    //    } 
});

	function deleteinterestitem(rId){
	//alert("id===="+rId);
	//alert("type="+type);
    		//向后台请求删除数据    		
    		var deleteInterest = cims201.utils.getData('../interestmodel/interestmodel!deleteTreeInterest.action',{id:rId});
    		var r = myTable4.getRowById_zd(rId);
    	    myTable4.deleteRecord(r);   	
    	}
    
</script>


