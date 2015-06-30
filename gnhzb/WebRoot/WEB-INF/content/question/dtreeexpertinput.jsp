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
		
	</head>

	<body>
    </body>
</html>


<script type="text/javascript">
    var currentNode;  
    var powersetWin = null;		
	var currentPowerset;
//从域树接收数据
var experttreeData = cims201.utils.getData('../tree/privilege-tree!listPrivilegeTreeNodes.action',{treeType:"domainTree",operationName:"分配管理员"});

	var treeColumns = [{
				      header: '树名',
				      dataIndex: 'name'				     				      				      
				 }							 				 
				 ];						 
var myExpertTree = new createTree({},treeColumns,experttreeData,'single',[],[],domainTreeSelectEvent);

//创建相应领域的专家列表
    	var myExpertConfig = {
    		horizontalScrollPolicy : 'off'  		
    		};
    		
		var myColumns = [
                {
                    headerText: '专家集合',
                    dataIndex: 'username',
                    headerAlign: 'center',  
                            
                    align: 'center'
                } ,
                   {
                	headerText: '',
                	dataIndex: 'username',            
                    align: 'center',
                    width: 20,
                    renderer: function(v,r){                
                    	
                    	var out_str = '';                   	
               
                    	out_str += '<span class="icon-cims201-delete" onclick="deletePowerset('+r.id+');">';
                    	out_str += '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp';                    	
                    	out_str += '</span>';
                    	return out_str;
                    }
               	}                 
            ];
    	myTable = new createTable(myExpertConfig,'100%','100%','业务列表',myColumns,[],[],'', {},false);
    	    

			Edo.build({        
                      type: 'box',                      
					  border: [0,1,1,0],
					  padding: [0,0,0,0],
					  width:800,
				      height: 600,
					  render: document.body,
					  layout: 'vertical', 
					  children: [
							  cims201.utils.createBtPanel(['节点上设置专家'],[addtreeexpert]),
							  {type: 'box',                      
							  border: [0,0,0,0],
							  padding: [0,0,0,0],
						      width: 650,
						      height: 600,
						      horizontalGap:30,  
							  layout: 'horizontal', 
							  children:[						 
		                	     {                    
						    		 
								    type: 'box', 
								    title:'领域树',        
							   		width: 380,
							        height: 500,
								    border: [0,0,0,0],  
								    padding: [0,0,0,0],    						   
								    children: [
								        myExpertTree.getTree()
								    ]
							     },
							    
							     {                    
						    		 
								    type: 'box', 
								    title:'专家集合',        
							        width: 200,
							        height: 500,
								    border: [0,0,0,0],  
								    padding: [0,0,0,0],    
								    children:myTable.getTable()
							     }]
							     }
					       ]
					 
						});

//列出所有专家
var expertsListSearch = Edo.create({			
			type: 'autocomplete', 
			width: 200, 
			queryDelay: 500,
			url: '../qanda/qanda!searchExpert.action',
			popupHeight: '100',
			valueField: 'username', 
			displayField: 'username'
			//valid: cims201.utils.validate.noEmpty
			
    	});	
//域树的选择按钮

	function addtreeexpert(){		
		if(currentNode == null){
	    			
	    			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择一个域节点!',Edo.MessageBox.YESNO,
	    			function(e){
	    				
	    			});
	    			 
	    }else{ 
			  if(powersetWin == null){   			
		   		var powersetForm = Edo.create({		  
				    type: 'box',
				    width: '100%',
				    height: '100%',
				    title: '选择专家',
				    
				    children: [
				        {
				            type: 'formitem',label: '专家名<span style="color:red;">*</span>:',
				            children:expertsListSearch
				        },
				        
				        {
				            type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
				            children:[
				                {type: 'button', text: '设置专家', onclick: function(e){		                
				                			                			                	
				                	var selecttree = cims201.utils.getData('../qanda/qanda!setExpertNodes.action',{nodeid:currentNode,json:expertsListSearch.get("text")});
				                	if(selecttree=="专家不存在，请到【专家集维护】进行设置"){
				                		alert("专家不存在，请到【专家集维护】进行设置");
				                	}else if(selecttree!=null){
				                		myTable.addRecord(selecttree);
				                	}
			                	
				                	powersetWin.hide();		                	
				                }}
				              
				            ]
				        }
				    ]
				}); 	
				powersetWin = cims201.utils.getWin(350,200,'选择专家',powersetForm);
		  		
		  		}
		  		setWinScreenPosition(350,200,powersetWin,null);
		  		}
	}		

	//定义当树节点选择后执行的选择方法
    	function domainTreeSelectEvent(cn){  		
    		currentNode = cn.id;
    		var getexpert = cims201.utils.getData('../qanda/qanda!listtreeexperts.action',{json:currentNode});
    		var powerset = getexpert.children;   		
    		myTable.setTableData(powerset);    	
    		currentPowerset = null;
    		
    		
    	}
    	
      	function deletePowerset(rId){
    		//向后台请求删除数据    		
    		var deleteNodeExpert = cims201.utils.getData('../qanda/qanda!deleteTreeExpert.action',{expertid:rId,nodeid:currentNode});
    		var r = myTable.getRowById_zd(rId);
    	    myTable.deleteRecord(r);   	
    	}
    	    
    	function setWinScreenPosition(width,height,win,kid)
			{
			var screenw= cims201.utils.getScreenSize().width;
			var screenh=cims201.utils.getScreenSize().height;
			if(width<screenw)
			{width=(screenw-width)/2
			
			}
			else{
			width=0;
			}
			if(height<screenh)
			{height=(screenh-height)/2
			
			}
			else{
			height=0;
			}
			if(null!=kid)
			unvisiblemodul(kid);
			 win.show(width,height,true);
			}
			
</script>


