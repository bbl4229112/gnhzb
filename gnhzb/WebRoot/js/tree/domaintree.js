
function domainTreeSelectEvent(){
	//alert(1);
}

cims201.tree.createDomainTree = function(type){
	//从服务器端获取数据
	var treeData = cims201.utils.getData('privilege-tree!listPrivilegeTreeNodes.action',{treeType:type,operationName:"节点管理"});
	//定义树列的显示名称
	var treeColumns = [{
				      header: '权限',
				      dataIndex: 'name',
				      renderer: function(v,r){
				      	  //return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '+(r.checked ? 'e-table-checked' : '')+'"></div>'+v+'</div>';            
				      	return '<div class="e-tree-checkbox"><div class="e-tree-check-icon  '+(r.checked ? 'e-table-notallowchecked' : '')+'"></div>'+v+'</div>';            
				      	
				      }
				 }];
	
	var myTree = cims201.tree.createTree('domainTree',treeColumns,treeData,domainTreeSelectEvent,300,500,'single');	
	return myTree;
}

cims201.tree.createTreeNodeForm = function(){
	var addDomainTreeNodeForm = Edo.create({
		type: 'box',
		border: [0,0,0,0],
		title: '增加域',
	    children: [
	        {
	            type: 'formitem',label: '名称<span style="color:red;">*</span>:',
	            children:[{type: 'text', id: 'name', valid: cims201.utils.validate.noEmpty, defaultWidth: 200}]
	        },
	        {
	            type: 'formitem',label: '描述:',
	            children:[{type: 'textarea', id: 'nodeDescription', defaultHeight:80, defaultWidth: 200}]
	        },
	        {
	            type: 'formitem',layout:'horizontal', padding: [8,0,8, 0],
	            children:[
	                {id: 'domainSubmitBtn1', type: 'button', text: '提交表单', onclick: this.recall}
	            ]
	        }
	    ]
	});
	return addDomainTreeNodeForm;
}

//新增域
cims201.tree.addDomainTreeNode = function(domainForm){
	domainForm.set('title','新增域信息');
	domainForm.reset();
}

//编辑域
cims201.tree.editDomainTreeNode = function(domainForm,data){
	domainForm.set('title','编辑域信息');
	//domainForm.reset();
	
	domainForm.setForm(data);
}








