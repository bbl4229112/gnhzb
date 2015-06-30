////问题搜索，按照检索条件搜索知识
//创建搜索面板类
function questionSearchPanel(mtl){
	
	var knowledgeList = mtl;
	//定义该对象的一些参数
	
	
	//左边的组件的宽度
	var leftwidth = 50;
	var combowidth = 150;
	//var inputwidth = 300;
	var inputwidth = '100%';
	
	//定义数组来容纳所有的搜索选项组件
	var comboArray = new Array();
	var inputArray = new Array();
	var logicArray = new Array();
	var boxArray = new Array();
	
	var comboData = [];
			
		cims201.utils.getData_Async('knowledge/ktype/ktype!listKtypeProperty.action',{id:10000},function(text){

			comboData = Edo.util.Json.decode(text);
			
			comboData_available = new Array();
			comboData.each(function(o,i){
				if(o.isVisible == true||o.name=='uploadtime'){
					comboData_available[comboData_available.length] = o;
				}
			});
			
			for(var i=0; i<comboArray.length; i++){					
				comboArray[i].set('data',comboData_available);							
			}
			
			if(comboArray.length>0){
				comboArray[0].set('selectedIndex', 0);
			}	
			});
					
					
					
	
    
    
    //第一行搜索框
    var firstCombo = new Edo.controls.ComboBox()
    .set({
    	id: 'qks_combo_0',
   		displayField: 'description', 
       	valueField: 'name', 
       	readOnly: true,
		selectedIndex: 0,
       	width: combowidth,
       	data: comboData,
       	onclick: function(e){
			registEnter();
		},  
		onblur: function(e){
			unregistEnter();
		},
       	onSelectionChange: onComboSelectionChange
    });
    
    var firstInput = Edo.create({
  		id: 'qks_input_0',
  		type: 'text',
  		name: 'qks_box_0_input',
  		width: inputwidth,
   		//width: '100%',
   		onclick: function(e){
			registEnter();
		},  
		onblur: function(e){
			unregistEnter();
		}
   	});
    
    var firstLine = Edo.create({
    	type: 'box',
        id: 'qks_box_0',
	    width: '100%',
	    //height: '100%',
	    padding: [0,0,0,0],
	    border: [0,0,0,0],      
	    layout: 'horizontal',    
	    children: [
	    	Edo.create({
	    		type: 'box',       
			    width: leftwidth,
			    height: '100%',
			    padding: [0,0,0,0],
			    border: [0,0,0,0],      
			    layout: 'horizontal',    
			    children: [
			        {type: 'button',icon:'icon-cims201-additem', onclick:createNewSearchItem},
			      	{type: 'button',icon:'icon-cims201-deleteitem', onclick:deleteLastSearchItem}
			    ]
	    	}),
	    	firstCombo,
	    	firstInput       	      
	    ]
    });
    
    comboArray[comboArray.length] = firstCombo;
    inputArray[inputArray.length] = firstInput;
   	boxArray[boxArray.length] = firstLine;
    
    var searchBox2 = Edo.create({
    	id: 'searchBox2',
    	type: 'box',
		//title: '搜索知识',         
	    width: '100%',
	    //height: '100%',
	    padding: [0,0,0,0],
	    border: [0,0,0,0],      
	    layout: 'vertical',  
	    children: [
	        firstLine
	    ]
    });
    
    
    var mySubmitBt = Edo.create({
    	type: 'button', 
    	text:'搜索', 
    	onclick: submitSearch
    });
    var resetBt = Edo.create({
    	type: 'button', 
    	text:'重置', 
    	onclick: resetSearchItems
    });
    var btPanel = Edo.create({
    	type: 'box',
		//title: '搜索知识',         
	    width: '100%',
	    //height: '100%',
	    padding: [0,0,0,0],
	    border: [0,0,0,0],      
	    layout: 'horizontal',  
	    horizontalAlign: 'right',  
	    children: [
	        mySubmitBt,resetBt
	    ]
    });
    
	
	//创建上层主框架	
	var mainPanel = Edo.create({
		type: 'box',
		title: '搜索知识',      
	
	    width: 990,
	    //height: '100%',
	    border: [0,0,0,0],      
	    layout: 'vertical',    
	    children: [	    	
	    	{type: 'label',text:'<span style="font-size:15;">'+'问题检索'+'</span>', height: 20},
	        searchBox2,
	        btPanel
	    ]
	});
	
     //创建主框架
			var myTableCt2 = Edo.create({		 
		    type: 'box',                 
		    layout: 'vertical',
		    border:[0,0,0,0], 
		    width: 1000,
		    height: 600,    
		    children: [
			      {type: 'box',  
			        border:[0,0,0,0],               
				    layout: 'horizontal',				   				   
				    children:mainPanel
			                         
			        }       
		      
		    ]
			
		});	
	
	
	/**
	在初始的时候创建3个搜索条
	*/
	for(var i=0;i<2;i++){
		createNewSearchItem();
	}
	
	
	
	
	
	this.getSearchQu = function(){
		return myTableCt2;
	}
	
	
	//创建一排搜索选项
	function createNewSearchItem(){
		//新建某一排其他的组件
		var newBoxIndex = boxArray.length;
	
		//创建新的搜索框
		comboData_available = new Array();
		comboData.each(function(o,i){
			if((o.isVisible == true&&o.searchable == true)||o.name=='uploadtime'){
				comboData_available[comboData_available.length] = o;
			}
		});
	    var newCombo = new Edo.controls.ComboBox()
	    .set({
	    	id: 'qks_combo_'+newBoxIndex,
	   		displayField: 'description', 
	       	valueField: 'name', 
	       	readOnly: true,
	       	width: combowidth,
	       	data: comboData_available,
	       	onclick: function(e){
				registEnter();
			},  
			onblur: function(e){
				unregistEnter();
			},
	       	onSelectionChange: onComboSelectionChange
	    });
	    
	    var newInput = Edo.create({
	  		id: 'qks_input_'+newBoxIndex,
	  		name: 'qks_box_'+newBoxIndex+'_input',
	  		width: inputwidth,
	  		onclick: function(e){
				registEnter();
			},  
			onblur: function(e){
				unregistEnter();
			},
	   		type: 'text'
	   	});
	   	var newLogic = 	Edo.create({
    		type: 'combo',
    		id: 'qks_box_'+newBoxIndex+'_logic', 
		    width: leftwidth,
		    displayField: 'name', 
	       	valueField: 'value', 
	       	selectedIndex: 0,
	       	onclick: function(e){
				registEnter();
			},  
			onblur: function(e){
				unregistEnter();
			},
	       	data: [{name:'与',value:'and'},{name:'或',value:'or'}]
    	});	
		var newLine = Edo.create({
	    	type: 'box',
	        id: 'qks_box_'+newBoxIndex,
		    width: '100%',
		    //height: '100%',
		    padding: [0,0,0,0],
		    border: [0,0,0,0],      
		    layout: 'horizontal',    
		    children: [
		    	newLogic,
		    	newCombo,
		    	newInput       	      
		    ]
	    });
	    
	    comboArray[comboArray.length] = newCombo;
	    inputArray[inputArray.length] = newInput;
	    logicArray[logicArray.length] = newLogic;
   		boxArray[boxArray.length] = newLine;
	    
	    Edo.get('searchBox2').addChild(newLine);   		
	}
	
	/**
	当组件combo选择变化后执行的操作
	*/
	function onComboSelectionChange(e){

      		var myComboId = this.id;
      		var tempIndex = myComboId.substring('qks_combo_'.length,myComboId.length);
      		//var tempCmpId = 'qks_input_'+ tempIndex;
      		var tempCmpId = 'qks_box_'+ tempIndex;
      		var myTextfiled;
      		
      		var currentCmps = Edo.getByProperty('name',tempCmpId+'_input');       		       		
      		//首先销毁之前的组件      		   		
      		currentCmps.each(function(o){
      			o.destroy();
      		});
      		
      		
      		
      		//然后创建新的组件
  			if('date' == e.selectedItem.vcomponent){
  				myTextfiled = new Array();
  				//var myInputItem = new Array();
  				myTextfiled[myTextfiled.length] = Edo.create({
  					name: tempCmpId+'_input',
  					type: 'label',
  					width: 20,
  					text: '从'
  				});
  				myTextfiled1 = Edo.create({
		  			id: tempCmpId+'@'+e.selectedItem.name+'_after',
			  		name: tempCmpId+'_input',
			  		width: '50%',
			  		readOnly : true,
			  		onclick: function(e){
						registEnter();
					},  
					onblur: function(e){
						unregistEnter();
					},
			   		type: 'date'
			   	});	
	   			myTextfiled[myTextfiled.length] = myTextfiled1;
		   		myTextfiled[myTextfiled.length] = Edo.create({
	  				name: tempCmpId+'_input',
	  				type: 'label',
	  				width: 20,
	  				text: '到'
	   			});
	   			
			   	myTextfiled2 = Edo.create({
			  		id: tempCmpId+'@'+e.selectedItem.name+'_before',
			  		name: tempCmpId+'_input',
			  		width: '50%',
			  		readOnly : true,
			  		onclick: function(e){
						registEnter();
					},  
					onblur: function(e){
						unregistEnter();
					},
			   		type: 'date'
			   	});				   	
		   		myTextfiled[myTextfiled.length] = myTextfiled2;
    		}else if('combo' == e.selectedItem.vcomponent){
    				
    			var templistvalue = e.selectedItem.valuelist.split(',');
				var outcombodata = new Array();
				for(var i=0;i<templistvalue.length;i++){
					outcombodata[i] = {label:templistvalue[i],value:templistvalue[i]};
				}
	    				
	    		myTextfiled = Edo.create({
			  		id: tempCmpId+'@'+e.selectedItem.name,
			  		name: tempCmpId+'_input',
			   		type: 'combo',
			   		displayField: 'label', 
	       			valueField: 'value',
	       			width: inputwidth, 
	       			readOnly : true,
	       			onclick: function(e){
						registEnter();
					},  
					onblur: function(e){
						unregistEnter();
					},
	       			data: outcombodata
			   	});	
    		}
    		
    		
    		
    		
    		
    		
    		
    		 else if ( e.selectedItem.vcomponent == 'domaintree') {
				// 取树节点的数据
			
				var treeData = cims201.utils.getData(
						'tree/privilege-tree!listPrivilegeTreeNodes.action',
						{
							treeType : 'domainTree',
							operationName : '上传知识'
						});
				myTextfiled = new Edo.controls.TreeSelect().set({
							id: tempCmpId+'@'+e.selectedItem.name,
								name: tempCmpId+'_input',
							type : 'treeselect',
							multiSelect : false,
							displayField : 'name',
							width : 800,
							valueField : 'id',
							// rowSelectMode: 'single',
							data : treeData,

							treeConfig : {
								treeColumn : 'name',
								columns : [Edo.lists.Table.createMultiColumn(),
										{
											id : 'name',
											header : '域名称',
											width : 750,
											dataIndex : 'name'
										}]
							}
						});
			

			} else if (e.selectedItem.vcomponent== 'catagorytree') {
				// 取树节点的数据

				var treeData = cims201.utils.getData(
						'tree/privilege-tree!listPrivilegeTreeNodes.action',
						{
							treeType : 'categoryTree',
							operationName : '上传知识'
						});
				myTextfiled = new Edo.controls.TreeSelect().set({
							id: tempCmpId+'@'+e.selectedItem.name,
							name: tempCmpId+'_input',
							type : 'treeselect',
							multiSelect : false,
							displayField : 'name',
							valueField : 'id',
							width : 800,
							//rowSelectMode: 'single',
							data : treeData,

							treeConfig : {
								treeColumn : 'name',
								columns : [Edo.lists.Table.createMultiColumn(),
										{
											id : 'name',
											header : '分类名称',
											width : 772,
											dataIndex : 'name'
										}]
							}
						});
			
			}
    		 
    		else{
    			
    			//alert(e.selectedItem.vcomponent);
	    		myTextfiled = Edo.create({
			  		id: tempCmpId+'@'+e.selectedItem.name,
			  		name: tempCmpId+'_input',
			   		type: 'text',
			   		onclick: function(e){
						registEnter();
					},  
					onblur: function(e){
						unregistEnter();
					},
			   		width: inputwidth
			   	});	
	    	}
    	
    	
    	//替换数组inputarray中的元素 
    	inputArray[tempIndex] = myTextfiled; 		
	   	if(Edo.get(tempCmpId)){
	   		if(Edo.isArray(myTextfiled)){
	   			myTextfiled.each(function(o){
	   				
	   				Edo.get(tempCmpId).addChild(o);
	   				
	   			});
	   		}else{
	   			
	   			Edo.get(tempCmpId).addChild(myTextfiled);
	   		}
	   	}
	}
	
	
	/**
	当点击减号后去掉一列搜索栏
	*/
	function deleteLastSearchItem(){
		//首先判断是否大于三，否则不执行
		var ll = boxArray.length;
		if(ll>1){
			var lastBoxIndex = boxArray.length - 1;
			if(Edo.get('qks_box_'+lastBoxIndex)){
				Edo.get('qks_box_'+lastBoxIndex).destroy();
				comboArray.splice(ll-1,1);
				inputArray.splice(ll-1,1);
				logicArray.splice(ll-2,1);
	 			boxArray.splice(ll-1,1);
			}
		}
	}
	
	/**
	重置搜索选项
	*/
	function resetSearchItems(e){
		var ccc = comboArray;
		var iii = inputArray;
		var lll = logicArray;
	 	var bbb = boxArray;
	 	
	 	ccc.each(function(o){
	 		o.reset();
	 	});
	 	
	 	iii.each(function(o){
	 		if(Edo.isArray(o)){
	 			o.each(function(oo){
	 				if('label' != oo.type){
	 					oo.set('text','');
	 				}
	 			});
	 		}else{
	 			o.set('text','');
	 		}
	 	});
	}
	
	//提交
	function submitSearch(e){
		//设置界面loading
	
		createLoading();	
		var mmm = mySubmitBt;
		if(mySubmitBt.enable == true){
			mySubmitBt.set('enable',false);
			//定义返回的数组
			var resultList = new Array();
			var ccc = comboArray;
			var iii = inputArray;
			var lll = logicArray;
		 	var bbb = boxArray;
		 	
		 	//首先验证数据
		 	var selectNum = 0;
		 	ccc.each(function(cc){
		 		if(cc.getValue()){
		 			selectNum++;
		 		}
		 	});
		 	
		 	if(selectNum == 0){
		 		cims201.utils.warn('提醒',null,'请输入搜索条件',null,null);
		 		return;
		 	}
		 	
		 	ccc.each(function(cc,i){
		 		
		 		if(cc.getValue()){
		 		    if('date'!= cc.selectedItem.vcomponent){
		 		    iii[i].blur();
		 				}
		 			var result = {};
		 			//date要单独判断 	 		
		 			if('date' == cc.selectedItem.vcomponent){
		 				result.name = cc.selectedItem.name;
		 				result.after = iii[i][1].text;
		 				result.before = iii[i][3].text;
		 				
		 			}else if('combo' == cc.selectedItem.vcomponent){
		 				result.name = cc.selectedItem.name;
		 				result.value = iii[i].getValue();				
		 			}
		 			else if('domaintree' == cc.selectedItem.vcomponent){
		 				result.name = cc.selectedItem.name;
		 				result.value = iii[i].getValue();		
		 				
		 			}
		 			else if('catagorytree' == cc.selectedItem.vcomponent){
		 				result.name = cc.selectedItem.name;
		 				result.value = iii[i].getValue();				
		 			}
		 			
		 			
		 			else{
		 			    var myinput = 	iii[i];
		 				result.name = cc.selectedItem.name;
		 				result.value = iii[i].text;
		 				
		 			}
		 			
		 			// var myinput = 	iii[i];
		 			//	result.name = cc.selectedItem.name;//1
		 			//	result.value = iii[i].text;//2
		 			
		 			if(i>0){
	 					result.and_or = lll[i-1].getValue();
	 				}else{
	 					result.and_or = 'and';//3
	 				}
	 				//var pwefowj = cc.selectedItem;
	 				result.propertytype = cc.selectedItem.vcomponent;//4	
	 				resultList[resultList.length] = result;//5		
		 		}
		 	});
		 	

		 	var json = Edo.util.Json.encode({searchlist:resultList});
		 	//alert(json);
		 	var myTable = new createCDQuestionList_table('knowledge/knowledge!qsearch.action',{formvalue:json}, [], []);
			myTableCt2.removeChildAt(1);
			myTableCt2.addChild(myTable.getKnowledgeList().getTable());

			setTimeout(deleteMask, 1500);	
			mySubmitBt.set('enable',true);
		}
		
	}
	
	
	//定义提交的事件
	this.submitBt = function(){
		submitSearch();
	}
	
	//相应回车事件
	this.enter = function(){
		//首先将回车锁定
		enterlock = false;
		
		setTimeout(submitSearch, 1000);
		//然后打开回车
		setTimeout(function(){enterlock = true},800);	
	}	
	
	this.getId = function(){
		return mainPanel.id;
	}
	
	//在编辑器选择的时候注册回车事件
	function registEnter(){
		currentEventID = 'knowledgesearch';
	}
	
	//在编辑器不选择的时候取消注册回车事件
	function unregistEnter(){
		currentEventID = null;
	}
} 

