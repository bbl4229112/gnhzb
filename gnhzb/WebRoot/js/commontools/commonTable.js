 /**
这个方法用来创建比较一般的表格类
*/

/**
selectionContainer该table的选择容器，是在每一次search完后就扫描该容器，看是否设置选中
myConfig：对表格的个性化配置
width，height：宽和高
title：标题
columns：表格的列集合
btNames：表格顶端的按钮名字
btFunctions：表格顶端按钮对应的点击函数
inputUrl：表格从后台取数据的url地址 
inputForm：表格从后台取数据的查询参数
isPager：是否需要分页栏
selectionContainer：配置该表格的选择容器
*/

function createTable(myConfig,width,height,title,columns,btNames,btFunctions,inputUrl, inputForm,isPager,selectionContainer,autocolums,orderBtn){
	if(null==autocolums)
	autocolums=true;
	//这个参数是用来设置默认的每页显示多少条的参数
	if(title == "知识模板展示"){
		var defaultPageSize = 5;
		var mutiornot = 'single';
		
	}else{
		var defaultPageSize = 15;
		var mutiornot = 'multi';
	}
	//这个是用来设置表格加载数据的url地址，返回值必须是pageDTO格式的数据
	var myUrl = inputUrl;
	//这个是用来设置在请求的时候传递的参数列表，它是一个json对象，里面size和index是一定要有的，然后还可以增加别的参数
	var myInputForm = inputForm;
	//定义表格面板上显示的按钮栏
	var myBtPanel;

	
	
	//container是配置的一个可选参数，如果设置了container参数则在每次刷新表格前会检查下在container是否有当页得表格数据，有的话则勾选上该表格
	var myContainer = selectionContainer;
	if(btNames == null || btNames.length == 0){
		myBtPanel = null;
	//一下为添加orderBtn按钮修改
	}else if(orderBtn==null){
		var myBtPanel = cims201.utils.createBtPanel(btNames, btFunctions);
	}else{
		var myBtPanel = cims201.utils.createBtPanel(btNames, btFunctions,orderBtn);
	}
	
	//该参数是配置表格的一些默认参数
	var currentConfig = {
	    type: 'table', 
	    width: '100%', 
	    height: '95%',
		rowSelectMode: mutiornot, 
		cellDirtyVisible: false, 
		//autoColumns默认关闭
		autoColumns: autocolums,
	    columns: columns,
	    rowHeight:25
	};
	//设置用户配置的参数
	for(var key in myConfig){
		currentConfig[key] = myConfig[key];
	}
	//生成表格
	var myTable = Edo.create(currentConfig);
	//生成分页条
	var myPager;
	var myPagerId;
	if(isPager != false){
		myPagerId = myTable.id+'paging';
		myPager = Edo.create({
			id: myPagerId,    
		    type: 'pagingbar',
		    width: '100%',
		    //autoPaging: true,		    
		    //初始的时候设置为不可见
		    visible: false,
		    border: [0,1,1,1], 
		    padding: 2
		});	
		
		
		
		Edo.get(myPagerId).on('paging',function(e){
			search();	
		});	
			
		myPager.index = 0;
		myPager.size = defaultPageSize;
	}else{
		myPager = null;
	}

	//当数据为空的时候底端的显示条
	var alertBar = Edo.create({
		type: 'label',
		width: '100%',
		visible: false,
		style: 'padding-left: 100px; color: red; font-size:16px; font-weight: bold; ',
		text: '没有查询到任何数据!'		
	});
	
	//更具具体的参数生成表格面板的元素
	var children = new Array();
	if(myBtPanel != null){
		children[children.length] = myBtPanel;	
	}
	
	children[children.length] = myTable;
	
	if(myPager != null){
		children[children.length] = myPager;	
	}
	
	children[children.length] = alertBar;	
		
	var mainPanel = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		title: title,
		layout: 'vertical',    
		width: width,
	    height: height,  
	    children: children
	});
	
	//该方法根据url和查询参数检索后台数据
	function search(){    
	    if(myUrl != null && myUrl != ''){
		    
		    myInputForm.index = myPager.index;    
		    myInputForm.size = myPager.size;		   
	
		    var rData = cims201.utils.getData(myUrl,myInputForm);
			//如果数据为空的时候显示提示
			if(rData){
				if(rData.data == null || rData.data.length == 0){
					myTable.set('width','2%');
					myTable.set('visible', false);
					myPager.set('visible', false);
					alertBar.set('visible', true);	
					myPager.total = 0;
					myPager.totalPage = 0;
					myPager.refresh();
				}else{
					myTable.set('width','100%');
					myTable.set('visible', true);
					myPager.set('visible', true);
					alertBar.set('visible', false);
					myTable.data.load(rData.data);
					myPager.total = rData.total;
					myPager.totalPage = rData.totalPage;
					myPager.refresh();
					
					setIsChecked();	
				}	
			}else{
				myTable.set('width','2%');
				myTable.set('visible', false);
				myPager.set('visible', false);
				alertBar.set('visible', true);	
				myPager.total = 0;
				myPager.totalPage = 0;
				myPager.refresh();
			}
		}
	}
	
	if(myUrl == null || myUrl == ''){
			
	}else{
		search();
	}
	/**
	设置选中表格某一行的方法
	*/
	function setSelectMyRow(myRows){
		if(myRows){
			var rowIds  = myTable.data.source;
			for(var i=0;i<rowIds.length;i++){
				var rowId = rowIds[i];
				var bbb = 0;
				for(var j=0;j<myRows.length;j++){
					if(rowId.id == myRows[j].id){
						myTable.select(i);
						bbb = 1;
						break;			
					}	
				}
				if(bbb == 0){
					myTable.deselect(i);
				}			
			}
		}	
	}
	//根据容器的内容，选择自己的勾选状态
	function setIsChecked(){
		//设置选中
		if(myContainer != null){
			setSelectMyRow(myContainer.getAllRows());
		}
	}
	
	
	/*
		以下方法是对外提供的函数接口
	*/    
	/*
		根据查询参数和url查询后台数据
	*/
	this.search = function(queryForm,url){		
	    if(url != null){
	    	myUrl = url;
	    }
	    
	    for(var key in queryForm){
	    	myInputForm[key] = queryForm[key];	
	    }
	    
	    
	    search();
	}
	//重置表格
	this.reset = function(){
		myPager.index = 0;
		myPager.size = defaultPageSize;
		myInputForm = {};
		//myPager.size = 10;
	}
	
	//获取表格所有选择的节点
	this.getSelectedItems = function(){		
		var rs = myTable.getSelecteds(); 
		return rs;
	}
	//获取表格所有选择的节点的id
	this.getSelectedIds = function(){
		var rs = myTable.getSelecteds(); 
		var ids = new Array();
		for(var i=0;i<rs.length;i++){
			ids[i] = rs[i].id; 
		}
		return ids;
	}
	//获取和表格关联的树节点的id
	this.getNodeId = function(){
		return myInputForm.nodeId;
	}	
	/**
	设置选中表格某一行的方法
	*/
	this.setSelectMyRow = function(myRows){
	//	alert(myRows.id)
		setSelectMyRow(myRows);
	}	
	/**
	设置不选中表格某一行的方法
	*/
	this.setDeSelectMyRow = function(myRows){
		var rowIds  = myTable.data.source;
		for(var i=0;i<rowIds.length;i++){
			var rowId = rowIds[i];
			for(var j=0;j<myRows.length;j++){
				if(rowId.id == myRows[j].id){
					myTable.deselect(i);
					break;			
				}	
			}
			
		}	
	}	
	//得到表格
	this.getTable = function(){
		return mainPanel;
	}	
	//增加表格的一条记录
	this.addRecord = function(r){
		//判断该表格是否有这条记录
		var rowIds  = myTable.data.source;
		var status = true;
		for(var i=0;i<rowIds.length;i++){
			var rowId = rowIds[i];
			if(rowId.id == r.id){
				status = false;
			}			
		}
		if(status){
			myTable.data.insert(0, r);
		}
	}
	//增加表格的多条记录
	this.addRecords = function(rs){		
		//myTable.data.insertRange(0,rs);
		for(var i=0; i<rs.length; i++){
			if(this.hasRowItem(rs[i]) == false){
				myTable.data.insert(0, rs[i]);
			}
		}
	}
	//判断表格是否有改行记录，主要是根据表格的id是否相同来进行判断
	this.hasRowItem = function(r){
		var rowIds  = myTable.data.source;
		for(var i=0;i<rowIds.length;i++){
			var rowId = rowIds[i];
			if(rowId.id == r.id){
				return true;
			}			
		}
		return false;
	}
		//判断表格是否有记录
	this.hasRow = function(){
		var rowIds  = myTable.data.source;
		if(rowIds.length>0)
			return true;
			else

		return false;
	}	
	
	
	//删除一条记录
	this.deleteRecord = function(r){
		//myTable.data.remove(r);
		deleteRow(r);
	}
	//删除多条记录
	this.deleteRecords = function(rs){
		for(var i=0; i<rs.length; i++){
			deleteRow(rs[i]);
		}
	}	
	//设置容器的方法  
	this.setContainer = function(c){
		myContainer = c;
	}
	//根据container中的数据判断是否应该选择新表格的某一行数据
	this.setIsCheckedStatus = function(){
		setIsChecked();
	}	
	//获取所有行的方法 
	this.getAllRows = function(){
		return myTable.data.source;
	}
	//获得初级表	
	this.getMyTable = function(){	
		return myTable;
	}	
	/**
		清空表格数据
	*/
	this.clearAllRows = function(){
		var mt = myTable.data;
		myTable.data.beginChange();
		myTable.data.removeRange(myTable.data.source);		
		myTable.data.endChange();		
	}	
	/**
	手动设置表格的数据
	*/
	this.setTableData = function(md){
		myTable.data.load(md);
		setIsChecked();
	}	
	/**
	是否选中该列
	*/
	this.isSelect = function(r){
		var rs = myTable.getSelecteds(); 
		for(var i=0; i<rs.length;i++){
			if(r.id == rs[i].id){	
				return true;
			}
		}
		return false;
	}
	//根据row的id获取某一行
	this.getRowById_zd = function(rid){
		var result = null;
		myTable.data.source.each(function(o){
			if(rid == o.id){
				result = o;
				return;
			}
		}); 
		return result;
	}
	
	//表中行排序，direction为方向
	this.orderSwap=function(direction,url,currentNode){
		var items=this.getSelectedItems();
		//alert(items);
		if(items.length==0){
			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请选择须排序的人员！',Edo.MessageBox.OK); 
			return;
		}
		if(items.length>1){
			//alert("请一次选择一位人员进行排序！");
			cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'请一次选择一位人员进行排序！',Edo.MessageBox.OK); 
			return;
		}
		var upperRow;
		var lowerRow;
		var firstSel=items[0];
		var index=myTable.data.indexOf(firstSel);
		if(direction>0){
			if(index==0){
				var pageIndex=myPager.index;
				if(pageIndex==0){
					//alert("该用户已经排在第一位了！");
					cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'该用户已经排在第一位了！',Edo.MessageBox.OK); 
					return;
				}else{
					myPager.index=pageIndex-1;
					search();
					var preItems=myTable.data.source;
					upperRow=preItems[preItems.length-1];
				}	
			}else{
				upperRow=myTable.data.source[index-1];
			}
			swap(upperRow, firstSel,this,url,currentNode);
		}else{
			if(index==myTable.data.source.length-1){
				var pageIndex=myPager.index;
				if(pageIndex==myPager.totalPage-1){
					//alert("");
					cims201.utils.warn('提醒',Edo.MessageBox.WARNING,'该用户已经排在最后一位了！',Edo.MessageBox.OK); 
					return;
				}else{
					myPager.index=pageIndex+1;
					search();
					var afterItems=myTable.data.source;
					lowerRow=afterItems[0];
					
				}
			}else{
				lowerRow=myTable.data.source[index+1];
			}
			swap(firstSel,lowerRow,this,url,currentNode);
			
		
		}
		function swap(row, otherRow,createTable,url,currentNode){	
			
			var otherIndex=myTable.data.indexOf(otherRow);
			var rowIndex=myTable.data.indexOf(row);
			
			cims201.utils.getData(url,{id:currentNode.id,userid1:row.id,userid2:otherRow.id});
			
			myTable.data.move(otherRow,rowIndex);
			var itemsNew=createTable.getSelectedItems();
			if(itemsNew.length==0){
				var indexNew=myTable.data.indexOf(otherRow);
				var index1=myTable.data.indexOf(row);
				if(index1>0){
					myTable.select(indexNew);
					myTable.data.source.pop();
				}else{
					myTable.data.insert(0,row);
					myTable.select(0);
					myTable.data.source.pop();
				}
			}
			
		}	
	}
	
	//删除某一行	
	function deleteRow(r){
		myTable.data.source.each(function(o){
			if(r.id == o.id){
				myTable.data.remove(o);
			}
		});
	}	
}

function createAVIDMTable(myConfig,width,height,title,columns,btNames,btFunctions,inputUrl, inputForm,isPager,selectionContainer){
	//这个参数是用来设置默认的每页显示多少条的参数
	var defaultPageSize = 15;
	//这个是用来设置表格加载数据的url地址，返回值必须是pageDTO格式的数据
	var myUrl = inputUrl;
	//这个是用来设置在请求的时候传递的参数列表，它是一个json对象，里面size和index是一定要有的，然后还可以增加别的参数
	var myInputForm = inputForm;
	//定义表格面板上显示的按钮栏
	var myBtPanel;
	//定义所有的查询结果
	var allData;
	//container是配置的一个可选参数，如果设置了container参数则在每次刷新表格前会检查下在container是否有当页得表格数据，有的话则勾选上该表格
	var myContainer = selectionContainer;
	if(btNames == null || btNames.length == 0){
		myBtPanel = null;
	}else{
		var myBtPanel = cims201.utils.createBtPanel(btNames, btFunctions);
	}
	
	//该参数是配置表格的一些默认参数
	var currentConfig = {
	    type: 'table', 
	    width: '100%', 
	    height: '95%',
		rowSelectMode: 'multi', 
		cellDirtyVisible: false, 
		//autoColumns默认关闭
		autoColumns: true,
	    columns: columns,
	    rowHeight:25
	};
	//设置用户配置的参数
	for(var key in myConfig){
		currentConfig[key] = myConfig[key];
	}
	//生成表格
	var myTable = Edo.create(currentConfig);
	
	//生成分页条
	var myPager;
	var myPagerId;
	if(isPager != false){
		myPagerId = myTable.id+'paging';
		myPager = Edo.create({
			id: myPagerId,    
		    type: 'pagingbar',
		    width: '100%',
		    //autoPaging: true,		    
		    //初始的时候设置为不可见
		    visible: false,
		    border: [0,1,1,1], 
		    padding: 2
		});	
		
		
		
		Edo.get(myPagerId).on('paging',function(e){
			search();	
		});	
			
		myPager.index = 0;
		myPager.size = defaultPageSize;
	}else{
		myPager = null;
	}

	//当数据为空的时候底端的显示条
	var alertBar = Edo.create({
		type: 'label',
		width: '100%',
		visible: false,
		style: 'padding-left: 100px; color: red; font-size:16px; font-weight: bold; ',
		text: '没有查询到任何数据!'		
	});
	
	//更具具体的参数生成表格面板的元素
	var children = new Array();
	if(myBtPanel != null){
		children[children.length] = myBtPanel;	
	}
	
	children[children.length] = myTable;
	
	if(myPager != null){
		children[children.length] = myPager;	
	}
	
	children[children.length] = alertBar;	
		
	var mainPanel = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		title: title,
		layout: 'vertical',    
		width: width,
	    height: height,  
	    children: children
	});
	
	//该方法根据url和查询参数检索后台数据
	function search(){    
	    if(myUrl != null && myUrl != ''){
		    
		    myInputForm.index = myPager.index;    
		    myInputForm.size = myPager.size;	
		    
	       if(null==allData)
	        allData= cims201.utils.getData(myUrl,myInputForm);
		    var rData=new Array();
		    var t=0;
		    //alert("allData.length=="+allData.length);
		    for(var i=(myPager.index*myPager.size);i<((myPager.index+1)*myPager.size);i++)
		    	{
		    	//alert("i=="+i);
		    	
		    	if(i<allData.length)	
		    	{	rData[t]=allData[i];
		    	    t=t+1;
		    	}
		    	}
			 // alert("rData.length=="+rData.length);
			//如果数据为空的时候显示提示
			if(rData){
				if(rData == null || rData.length == 0){
					myTable.set('width','2%');
					myTable.set('visible', false);
					myPager.set('visible', false);
					alertBar.set('visible', true);	
					myPager.total = 0;
					myPager.totalPage = 0;
					myPager.refresh();
				}else{
					myTable.set('width','100%');
					myTable.set('visible', true);
					myPager.set('visible', true);
					alertBar.set('visible', false);
					myTable.data.load(rData);
					myPager.total = allData.length;
					myPager.totalPage = allData.length/defaultPageSize;
					myPager.refresh();
					
					setIsChecked();	
				}	
			}else{
				myTable.set('width','2%');
				myTable.set('visible', false);
				myPager.set('visible', false);
				alertBar.set('visible', true);	
				myPager.total = 0;
				myPager.totalPage = 0;
				myPager.refresh();
			}
		}
	}
	
	if(myUrl == null || myUrl == ''){
			
	}else{
		search();
	}
	/**
	设置选中表格某一行的方法
	*/
	function setSelectMyRow(myRows){
		if(myRows){
			var rowIds  = myTable.data.source;
			for(var i=0;i<rowIds.length;i++){
				var rowId = rowIds[i];
				var bbb = 0;
				for(var j=0;j<myRows.length;j++){
					if(rowId.id == myRows[j].id){
						myTable.select(i);
						bbb = 1;
						break;			
					}	
				}
				if(bbb == 0){
					myTable.deselect(i);
				}			
			}
		}	
	}
	//根据容器的内容，选择自己的勾选状态
	function setIsChecked(){
		//设置选中
		if(myContainer != null){
			setSelectMyRow(myContainer.getAllRows());
		}
	}
	
	
	/*
		以下方法是对外提供的函数接口
	*/    
	/*
		根据查询参数和url查询后台数据
	*/
	this.search = function(queryForm,url){		
	    if(url != null){
	    	myUrl = url;
	    }
	    
	    for(var key in queryForm){
	    	myInputForm[key] = queryForm[key];	
	    }
	    
	    
	    search();
	}
	//重置表格
	this.reset = function(){
		myPager.index = 0;
		myPager.size = defaultPageSize;
		myInputForm = {};
		//myPager.size = 10;
	}
	
	//获取表格所有选择的节点
	this.getSelectedItems = function(){		
		var rs = myTable.getSelecteds(); 
		return rs;
	}
	//获取表格所有选择的节点的id
	this.getSelectedIds = function(){
		var rs = myTable.getSelecteds(); 
		var ids = new Array();
		for(var i=0;i<rs.length;i++){
			ids[i] = rs[i].id; 
		}
		return ids;
	}
	//获取和表格关联的树节点的id
	this.getNodeId = function(){
		return myInputForm.nodeId;
	}	
	/**
	设置选中表格某一行的方法
	*/
	this.setSelectMyRow = function(myRows){
	//	alert(myRows.id)
		setSelectMyRow(myRows);
	}	
	/**
	设置不选中表格某一行的方法
	*/
	this.setDeSelectMyRow = function(myRows){
		var rowIds  = myTable.data.source;
		for(var i=0;i<rowIds.length;i++){
			var rowId = rowIds[i];
			for(var j=0;j<myRows.length;j++){
				if(rowId.id == myRows[j].id){
					myTable.deselect(i);
					break;			
				}	
			}
			
		}	
	}	
	//得到表格
	this.getTable = function(){
		return mainPanel;
	}	
	//增加表格的一条记录
	this.addRecord = function(r){
		//判断该表格是否有这条记录
		var rowIds  = myTable.data.source;
		var status = true;
		for(var i=0;i<rowIds.length;i++){
			var rowId = rowIds[i];
			if(rowId.id == r.id){
				status = false;
			}			
		}
		if(status){
			myTable.data.insert(0, r);
		}
	}
	//增加表格的多条记录
	this.addRecords = function(rs){		
		//myTable.data.insertRange(0,rs);
		for(var i=0; i<rs.length; i++){
			if(this.hasRowItem(rs[i]) == false){
				myTable.data.insert(0, rs[i]);
			}
		}
	}
	//判断表格是否有改行记录，主要是根据表格的id是否相同来进行判断
	this.hasRowItem = function(r){
		var rowIds  = myTable.data.source;
		for(var i=0;i<rowIds.length;i++){
			var rowId = rowIds[i];
			if(rowId.id == r.id){
				return true;
			}			
		}
		return false;
	}
		//判断表格是否有记录
	this.hasRow = function(){
		var rowIds  = myTable.data.source;
		if(rowIds.length>0)
			return true;
			else

		return false;
	}	
	
	
	//删除一条记录
	this.deleteRecord = function(r){
		//myTable.data.remove(r);
		deleteRow(r);
	}
	//删除多条记录
	this.deleteRecords = function(rs){
		for(var i=0; i<rs.length; i++){
			deleteRow(rs[i]);
		}
	}	
	//设置容器的方法  
	this.setContainer = function(c){
		myContainer = c;
	}
	//根据container中的数据判断是否应该选择新表格的某一行数据
	this.setIsCheckedStatus = function(){
		setIsChecked();
	}	
	//获取所有行的方法 
	this.getAllRows = function(){
		return myTable.data.source;
	}		
	/**
		清空表格数据
	*/
	this.clearAllRows = function(){
		var mt = myTable.data;
		myTable.data.beginChange();
		myTable.data.removeRange(myTable.data.source);		
		myTable.data.endChange();		
	}	
	/**
	手动设置表格的数据
	*/
	this.setTableData = function(md){
		myTable.data.load(md);
		setIsChecked();
	}	
	/**
	是否选中该列
	*/
	this.isSelect = function(r){
		var rs = myTable.getSelecteds(); 
		for(var i=0; i<rs.length;i++){
			if(r.id == rs[i].id){	
				return true;
			}
		}
		return false;
	}
	//根据row的id获取某一行
	this.getRowById_zd = function(rid){
		var result = null;
		myTable.data.source.each(function(o){
			if(rid == o.id){
				result = o;
				return;
			}
		}); 
		return result;
	}
	//删除某一行	
	function deleteRow(r){
		myTable.data.source.each(function(o){
			if(r.id == o.id){
				myTable.data.remove(o);
			}
		});
	}	
}
