var myTable;
var currentktypeid = 0;
var propertylist;
function createKtypeList(){
var myWin = null;
var myKtypeinput = null;
 //var myData = cims201.utils.getData('property!creatpropertyinput.action');

var myColumns = [
                    Edo.lists.Table.createSingleColumn(),
                  
                    {header: '知识模板英文名',  width:80, headerAlign:'center',align:'center',dataIndex: 'name'},
        		    {header: '知识模板中文名',  width:80, headerAlign:'center',align:'center',dataIndex: 'ktypeName'},
        		    {header: '类名',  width:200, headerAlign:'center',align:'center',dataIndex: 'className'},
        			{header: '表名',  width:120, headerAlign:'center',align:'center',dataIndex: 'tableName'}
        			
                ];
               
 var myTable = new createTable({},'100%','100%','知识模板展示',myColumns,['删除','编辑'],[delRow,ediRow],'ktype!listAllktype.action', {withoutCommon:true,size:5},true);
// myTable.set('itemClick',function(){
//	 alert("g");
// });
function delRow(){
	var rs=myTable.getSelectedItems();
	if(rs.length != 0){
		Edo.MessageBox.show({
			title : '提醒！',
			msg : '删除模板会将该模板对应知识删除，请慎重，是否继续',
			buttons : Edo.MessageBox.YESNO,
			callback : isgoback,
			icon : Edo.MessageBox.WARNING
		});
	}else{
		creatalert("未选定要删除的知识模板！");
	}
}
function isgoback(action, value) {
	if(action=='yes')
		{
			var rs=myTable.getSelectedItems();
			
			if(rs){
				myTable.deleteRecord(rs);
				cims201.utils.getData_Async('ktype!deletektype.action', {
					id : rs[0].id
				}, function(text) {
					var deleteresult = Edo.util.Json.decode(text);
					creatalert(deletetext);
				});
				myTable.search();
			}
		}
		if(action=='no')
		{
		}	
		
}
Edo.create({
	type : 'panel',
	id : 'editformpanel',
	layout : 'vertical',
	labelAlign : 'left',
	width : 670,
	verticalScrollPolicy : "on",
	minHeight : 230,
	height:230,
	title : '编辑模板-添加扩展属性',
	children : [{
					type : 'formitem',
					id : 'editpropertybuttonbar',
					labelWidth : 0,
					labelAlign : 'left',
					layout : 'horizontal'
				},{
					type : 'formitem',
					id : 'editpropertybar',
					labelWidth : 0,
					labelAlign : 'left',
					layout : 'vertical'
				}]
									
});
var comboxindex = 0;
var editaddexpandCombox = {
		id : 'editsubmitBtn1',
		type : 'button',
		text : '添加',
		onclick : function (e) {
			
			var expandpropertyList = new Array();
			var commonpropertyList = new Array();
			var commonpList = new Array();
			var expandpList = new Array();
			for (var i = 0; i < propertylist.length; i++) {
				var property = propertylist[i];
				if (property.isCommon == false) {
					expandpropertyList[expandpropertyList.length] = property;
				}
			}
			
			var expandItemData = new Array();
			for (var ie = 0; ie < expandpropertyList.length; ie++) {

				var temp = {
					text : expandpropertyList[ie].description,
					value : expandpropertyList[ie],
					index : ie
				};
				// temp.value.index = vi+1;
				expandItemData[expandItemData.length] = temp;
			}
			var editexpanditem = createOneAddPro(comboxindex+"edit",comboxindex,expandItemData);
			Edo.get('editpropertybar').addChild(editexpanditem);
			comboxindex = comboxindex + 1;
			
			
			
		}
			
}
var editdeleteexpandCombox = {
		id : 'editsubmitBtn2',
		type : 'button',
		text : '删除',
		onclick : function(e){
			var propertybarchilednum = Edo.get('editpropertybar').numChildren();
			if (propertybarchilednum > 0){
				Edo.get('editpropertybar').getChildAt(Edo.get('editpropertybar')
						.numChildren()- 1).destroy();
						
				comboxindex = comboxindex - 1;
			}
}

}
var editresetexpandCombox = {
		id : 'editsubmitBtn3',
		type : 'button',
		text : '重置',
		onclick : function(e){
			Edo.get('editpropertybar').removeAllChildren();
}

}
var editsubmitexpandCombox = {
		id : 'editsubmitBtn4',
		type : 'button',
		text : '提交',
		onclick : function(e){
    		var resultpropertyList = new Array();
    		for (var index = 0; index < comboxindex; index++) {
				var expandproperty = Edo.get("expandidcombox" + index)
						.getValue(); // 得到对象
			   //得到扩展属性的显示名
				expandproperty.description = Edo.get("shownameExpandLabel"
						+ 'expandidcombox' + index).text;
				//得到扩展属性的可搜索属性		
				expandproperty.searchable = Edo.get("SearchableExpandLabel"
						+ 'expandidcombox' + index).selectedItem.value;
			   //得到扩展属性的展示类型vcomponent 
				expandproperty.vcomponent = Edo.get("ComponentExpandLabel"
						+ 'expandidcombox' + index).selectedItem.value;
			   //得到扩展属性为combo是对值域
				expandproperty.listvalue = Edo
						.get("vvaluelist" + "ComponentExpandLabel"
								+ 'expandidcombox' + index).text;
			    resultpropertyList[resultpropertyList.length]=expandproperty;
				// text设置进去
				//Edo.get("expandidcombox" + index).setValue(expandproperty); // 重新包装
    		}



		// 验证表单
		
		//	var o = ktypeForm.getForm(); // 获取表单值
		if(editformpanel.valid()){
		var rs=myTable.getSelectedItems();
		var json = Edo.util.Json.encode({propertylist:resultpropertyList});
		// alert(json); // 可以用ajax发送到服务端
		
		Edo.util.Ajax.request({
					type : "post", // 交互方式:get,post
					url : 'ktype!editKTypeSave.action', // 数据源地址
					params : {
						json : json,
						id : currentktypeid
					},
					async : false,
					// 是否异步
					onSuccess : function(t) { // 成功回调函数,
						// 函数接收一个从服务端返回的字符串
						// alert('保存成功!');
					 	cims201.utils.warn("",null,t,null,null)
					 	Edo.get('editformpanel').set('visible',false);
					
					},
					onFail : function(code) { // 失败回调函数.这里是网络失败,
						// 比如500错误等
						creatalert(code);
					}
				});}
}

}
var editcancelexpandCombox = {
		id : 'editsubmitBtn5',
		type : 'button',
		text : '取消',
		onclick : function(e){
			Edo.get('editformpanel').set('visible',false);
}
}
Edo.get('editpropertybuttonbar').addChild(editaddexpandCombox);
Edo.get('editpropertybuttonbar').addChild(editdeleteexpandCombox);
Edo.get('editpropertybuttonbar').addChild(editresetexpandCombox);
Edo.get('editpropertybuttonbar').addChild(editsubmitexpandCombox);
Edo.get('editpropertybuttonbar').addChild(editcancelexpandCombox);

function ediRow(){
	var rs=myTable.getSelectedItems();
	if(rs.length != 0){
		var ktid = rs[0].id;
		if(currentktypeid == 0){
			Edo.get('ct').addChild(Edo.get('editformpanel'));
		}else if(currentktypeid != 0 && currentktypeid != ktid){
			Edo.get('editpropertybar').removeAllChildren();
			
		}
		currentktypeid = ktid;
		propertylist =cims201.utils.getData('ktype!creatktypeinput.action',{id:currentktypeid});
		Edo.get('editformpanel').set('title','编辑模板-添加扩展属性.当前模板：'+rs[0].ktypeName);
		Edo.get('editformpanel').set('visible',true);
	}else{
		creatalert("未选定要编辑的知识模板！");
	}
	
}
Edo.create({
	id: 'ct',    
    type: 'box',                 
    layout: 'vertical',
    width: 1000,
    height: 500,    
    children: [
        myTable.getTable()
    ],
    render: document.body
	
});
this.getKtypeList = function(){
	return myTable;
}
}

function delRow(rid){

var r = myTable.getRowById_zd(rid);
if(r){
	alert("删除成功!");
	myTable.deleteRecord(r);
	cims201.utils.getData('user!delete.action',{id:rid});
	alert(123);
}
}
function createOneAddPro(idstr,comboxindex,expandItemData){
	var gagag = {
			type : 'formitem',
			label : '属性: ',
			labelAlign : 'left',
			layout : 'horizontal',
			labelWidth :40,
			id : idstr,
			children : [{
				id : 'expandidcombox' + comboxindex,
				type : 'combo',
				readOnly : true,
				// layout: 'horizontal',
                width:80,
				displayField : 'text',
				valueField : 'value',
				data : expandItemData,
				onSelectionChange : function(e) {

					var tt = e.selectedItem;

					Edo.get('shownameExpandLabel' + this.id).set('text',
							e.selectedItem.text);
							
					if(e.selectedItem.value.propertyType=='java.util.Date')
						{
						Edo.get('ComponentExpandLabel' +this.id).set('data',
							[{
								text : '日期',
								value : 'date'
							}]);
							
						Edo.get('ComponentExpandLabel' +this.id).set('selectedIndex',1);	
						}	
						
					else{
					Edo.get('ComponentExpandLabel' +this.id).set('data',
							[{
								text : '文本框',
								value : 'textarea'
							}, 
							{
								text : '文本',
								value : 'textfield'
							}, 	
							{
								text : '下拉菜单',
								value : 'combo'
							}]);
							
						Edo.get('ComponentExpandLabel' +this.id).set('selectedIndex',1);
					
						
						
					}	
					 		
				}
			},

			{
				type : 'formitem',
				label : '可搜索:',
				labelWidth : 50,
				labelAlign : 'right',
				width:90,
				children : [

				{
					type : 'combo',
					readOnly : true,
				
					id : 'SearchableExpandLabel' + 'expandidcombox'
							+ comboxindex,
								width:40,
                    selectedIndex:0,
					displayField : 'text',
					valueField : 'value',
					data : [{
								text : '是',
								value : 'true'
							}, {
								text : '否',
								value : 'false'
							}]
				}]
			},

			{
				type : 'formitem',
				label : '显示名:',
				labelWidth : 50,
				labelAlign : 'right',
				children : [

				{           width:70,
							type : 'text',
							text : '',
							id : 'shownameExpandLabel' + 'expandidcombox'
									+ comboxindex,
						    valid:cims201.form.validate.mostLength
						}]
			}, {
				type : 'formitem',
				label : '类型:',
				labelWidth : 35,
				labelAlign : 'right',
				children : [

				{
					type : 'combo',
					readOnly : true,
					id : 'ComponentExpandLabel' + 'expandidcombox'+ comboxindex,
                  //  selectedIndex:1,
					displayField : 'text',
					valueField : 'value',
					 width:70,
                     selectedIndex:1,
					data : [ {
								text : '文本框',
								value : 'textarea'
							},
							{
								text : '文本',
								value : 'textfield'
							},
								{
								text : '下拉菜单',
								value : 'combo'
							}],
					onSelectionChange : function(e) {

						var shownameExpandLabelvtype = e.selectedItem.value;
						if(null!=Edo.get('vvaluelist' + this.id)){
						if (shownameExpandLabelvtype == 'combo') {

							Edo.get('vvaluelistbar' + this.id).set(	'visible', true);
							if (Edo.get('vvaluelist' + this.id).getValue() == "")
								Edo.get('vvaluelist' + this.id)
										.setValue('用逗号隔开如： 是，否');
						} else {

							Edo.get('vvaluelistbar' + this.id).set(	'visible', false);
							Edo.get('vvaluelist' + this.id).setValue('');

						}
						}
					}

				}]
			}, {
				type : 'formitem',
				label : '值域:',
				labelWidth : 35,
				labelAlign : 'right',
				id : 'vvaluelistbar' + 'ComponentExpandLabel'
						+ 'expandidcombox' + comboxindex,
				visible : false,
				children : [

				{
					type : 'text',
					width : 140,

					id : 'vvaluelist' + 'ComponentExpandLabel'
							+ 'expandidcombox' + comboxindex,
					text : '用逗号隔开如： 是，否',
					onTextFocus : function(e) {
						var value = this.text;
						// alert(value);
						if (value.indexOf("用逗号隔开如： 是，否") != -1)
							Edo.get(this.id).setValue('');
					}

				}]
			}

			]

		};
	return gagag;
}
