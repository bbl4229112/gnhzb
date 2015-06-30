/**
*
显示提交审批请求的界面，需要传递的参数有
knowledgeId,对应知识的id
knowledgeStatus,对应知识的等级
*/
function showApprovalWin(knowledgeId,knowledgeStatus){
	//创建最外层面板
	var win = Edo.get('approvalWin');
	//定义数据，并且与后台交互取得知识
	var qualifiedUsersData;
	var approvalStart_right;
	if(win == null){
		
		
		//生成表格
		Edo.create(
	    {
	        id: 'qualifiedUsers',
	        type: 'table',                
	        width: 270,
	        height: 300,	   
	        //horizontalLine: true,
	        verticalLine: true,
	        verticalScrollPolicy: 'on',
	        horizontalScrollPolicy: 'on',
	        //autoColumns: true,
	        showHeader: true,
	        rowSelectMode: 'single',        
	        //enableDragDrop: true,   	        
	        //summaryRowVisible: true,
	        //summaryRowPosition: 'bottom',   //top
	        foolterVisible: false,               //false
	        
	        //data: qualifiedUsersData,
	        data: [],
	        visible: false,
	        columns:[
	        	Edo.lists.Table.createMultiColumn(),
	            {header: '域', dataIndex: 'domainName'},        
                {header: '员工', dataIndex: 'userName'}	            
	        ],    
	        onselectionchange: function(e){
	            var r = this.getSelecteds();
	            if(r){
	                var printStr = '您将选择<span style="color:red;">'+r[0].userName+'</span>来审批您的知识！';
	                               
		    		Edo.get('tiplabel').set('text',printStr);
	            }
	        }
	    }            
	);       

		approvalStart_right = Edo.get('qualifiedUsers');
		
	
	
	
		//从服务器获取知识的审批状态
		var statusStr;
		Edo.util.Ajax.request(
	    {
	        type: "get",                    //交互方式:get,post      
	        url: 'approval!listApprovalStatus.action',         //数据源地址            
	         params: {                       //key-value形式,value必须为字符串对象(jsonString).
	            knowledgeId: knowledgeId
	        },     
	        async: false,                    //是否异步            
	        onSuccess: function(text){      //成功回调函数, 函数接收一个从服务端返回的字符串
	           //将json字符串,转化为js对象
	           statusStr = text;
	        },         
	        onFail: function(code){
	        	errorAlert(code);
	        }      
		});
		//定义显示审批状态的面板
		var statusWin;
		//显示一些知识提示以及审批提示的面板
		var showTipsWin;
		
		//当目前知识还没有入库，显示让用户申请入库的界面
		var statusData;
		if('null' == statusStr){
			statusData = [];			
		}else{
			//当用户已经入库了，则显示状态管理界面	
			statusData = Edo.util.Json.decode(statusStr);		
		}
			
			var statusWin_data = Edo.create({
			    id: 'statusWin_data',    
			    type: 'table',                
		        width: 400,
		        height: 200,	   
		        //horizontalLine: true,
		        //verticalLine: true,
		        verticalScrollPolicy: 'on',
		        horizontalScrollPolicy: 'on',
		        //autoColumns: true,
		        showHeader: true,
		        rowSelectMode: 'single',        
		        //enableDragDrop: true,   	        
		        //summaryRowVisible: true,
		        //summaryRowPosition: 'bottom',   //top
		        foolterVisible: false,               //false
		        
		        data: statusData,
		      
		        columns:[
		        	{
                        headerText: '',
                        align: 'center',
                        width: 75,                        
                        enableSort: false,
                        enableDragDrop: true,
                        enableColumnDragDrop: false,
                        style:  'cursor:move;',
                        renderer: function(v, r, c, i, data, t){
                            //return '<span style="padding:2px;padding-left:6px;padding-right:6px;line-height:20px;">'+i+'</span>';
                            //return '<div style="width:25px;height:22px;text-align:center;line-height:20px;">'+(i+1)+'</div>';
                            var index = i+1;
                            return '第'+index+'级';
                        }
                    }, 
		        	//Edo.lists.Table.createMultiColumn(),
		            {header: '节点', dataIndex: 'id'},        
	                {header: '审批人', dataIndex: 'initiatorName'},	            
		        	{header: '审批状态', dataIndex: 'nodeStatus'}
		        ]
			});
			
			var tips = new Array();
			var knowStr;
			if('null' == statusStr){
				knowStr = '本知识目前还未入库!';
			}else{
				knowStr = '本知识目前第<span style="color:red;">'+knowledgeStatus+'</span>级';
			}
			tips[tips.length] = {
			    type: 'label',
			    text: knowStr
			};
			
			var lastNode = statusData[statusData.length-1];
			
			
			
			if(('null' == statusStr)||(lastNode.nodeStatus == '通过' && knowledgeStatus <4 && knowledgeStatus)){
				tips[tips.length] = {
		    			type: 'button',
		    			text: '申请审批知识',
		    			onclick: function(e){
		    				Edo.util.Ajax.request(
						    {
						        type: "get",                    //交互方式:get,post      
						        url: 'approval!getQualifiedSystemUsers.action',         //数据源地址            
						         params: {                       //key-value形式,value必须为字符串对象(jsonString).
						            knowledgeID: knowledgeId
						        },     
						        async: false,                    //是否异步            
						        onSuccess: function(text){      //成功回调函数, 函数接收一个从服务端返回的字符串
						           //将json字符串,转化为js对象
						           
						           qualifiedUsersData = Edo.util.Json.decode(text);	            
						        },         
						        onFail: function(code){         //失败回调函数.这里是网络失败, 比如500错误等
						            
						        }            
							});
							Edo.get('qualifiedUsers').set('data',qualifiedUsersData);
		    				Edo.get('qualifiedUsers').set('visible',true);
		    			
		    				var ttt = Edo.get('tiplabel').text+'请在右侧列表中选择一个审批人员!';
		    				Edo.get('tiplabel').set('text',ttt);
		    				Edo.get('approvalSubmit').set('mode','save');
		    				Edo.get('approvalSubmit').set('visible',true);
		    			}			
		    };
		}else if(lastNode.nodeStatus == '正在审批'){
			tips[tips.length] = {
		    			type: 'button',
		    			text: '修改审批人',
		    			onclick: function(e){
		    				Edo.util.Ajax.request(
						    {
						        type: "get",                    //交互方式:get,post      
						        url: 'approval!getQualifiedSystemUsers.action',         //数据源地址            
						         params: {                       //key-value形式,value必须为字符串对象(jsonString).
						            knowledgeID: knowledgeId
						        },     
						        async: false,                    //是否异步            
						        onSuccess: function(text){      //成功回调函数, 函数接收一个从服务端返回的字符串
						           //将json字符串,转化为js对象
						           
						           qualifiedUsersData = Edo.util.Json.decode(text);	            
						        },         
						        onFail: function(code){         //失败回调函数.这里是网络失败, 比如500错误等
						            
						        }            
							});
							Edo.get('qualifiedUsers').set('data',qualifiedUsersData);
		    			
		    				Edo.get('qualifiedUsers').set('visible',true);
		    						    				
		    				
		    				var ttt = Edo.get('tiplabel').text+'请在右侧列表中选择一个审批人员!';
		    				Edo.get('tiplabel').set('text',ttt);
		    				Edo.get('approvalSubmit').set('mode','update');
		    				Edo.get('approvalSubmit').set('visible',true);
		    				
		    			}			
		    };
		}
			
			
		    
		    tips[tips.length] = {
		    			type: 'label',
		    			text: '',
		    			id: 'tiplabel'
		    };
		    
		    tips[tips.length] = {
		    			type: 'button',
		    			text: '提交表单',
		    			visible: false,
		    			mode: 'save',
		    			id: 'approvalSubmit',
		    			onclick: function(e){
		    				var r = Edo.get('qualifiedUsers').getSelecteds();
				            var mode = this.mode;
				            if(r.length > 0){
				            	
				                var userId = r[0].userId;
				                Edo.util.Ajax.request(
							    {
							        type: "get",                    //交互方式:get,post      
							        url: 'knowledge/approval/approval!'+mode+'.action',         //数据源地址            
							        params: {                       //key-value形式,value必须为字符串对象(jsonString).
							            knowledgeId: knowledgeId,
							        	userId:userId
							        },     
							        async: false,                    //是否异步            
							        onSuccess: function(text){      //成功回调函数, 函数接收一个从服务端返回的字符串
							           //将json字符串,转化为js对象
							           statusStr = text;
							        },         
							        onFail: function(code){
							        	errorAlert(code);
							        }      
								});
				            }else{
				            	warnAlert('请选择一个审批人员!');
				            }
		    				
		    			}
		    };
			
		
			//显示该知识和审批的一些提示
			showTipsWin = Edo.create({
			    id: 'showTipsWin',    
			    type: 'box',
			    border: [0,0,0,0],
			    width: 400,
			    height: 300,
			    children: tips	
			});
		
			statusWin = Edo.create({
			    id: 'statusWin',    
			    type: 'box',
			    border: [0,0,0,0],
			    width: 400,
			    height: 550,
			    children: [statusWin_data,showTipsWin]
			});
		
		
			
		win = new Edo.containers.Window();
		win.set('id', 'approvalWin');
		win.set('title', '提交知识审批');
		win.set('titlebar',
		    [      //头部按钮栏
		        {
		            cls: 'e-titlebar-close',
		            onclick: function(e){
		                //this是按钮
		                //this.parent是按钮的父级容器, 就是titlebar对象
		                //this.parent.owner就是
		                this.parent.owner.hide();       //hide方法
		            }
		        }
		    ]
		);
		
		Edo.create({
		    id: 'approvalStart_main',    
		    type: 'box',
		    border: [0,0,0,0],
		    layout: 'horizontal',
		    width: 700,
		    height: 400,
		    children:[statusWin,approvalStart_right]
		});
		
		
		
		win.addChild({
		    type: 'box',
		    border: [1,1,1,1],
		    width: '100%',
		    height: '100%',    
		    children: [
		        approvalStart_main
		    ]
		});
	}
	
	//show方法
	win.show(200, 100, true);       //true是遮罩, false不遮罩
}