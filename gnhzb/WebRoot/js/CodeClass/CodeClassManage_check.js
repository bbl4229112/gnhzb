function createCodeClassManage_check(codeClassId){
	var inputparam=new Array();
	var outputparam=new Array();
	this.initinputparam=function(param){
		inputparam=param;
		return inputparam;
	}
	this.initresultparam=function(param){
		outputparam=param;
		return outputparam;
		
	}
	this.submitResult=function(){
		return outputparam;
	}
	if(!Edo.get('CodeClassMange_check_window')){
		Edo.create({
		 	id:'CodeClassMange_check_window',
	 		type:'window',
	 		title:'已建立分类结构审批',
	 		width:'300',
	 		height:'200',
	 		titlebar:[
	            {
	                cls: 'e-titlebar-close',
	                onclick: function(e){
	                    this.parent.owner.hide();       //hide方法
	                }
	            }
	        ],
	        padding:[0,0,0,0],
	        children:[{
	        	type:'tree',id:"ClassTree_check",width:'100%',height:'100%',
				autoColumns:true,
				horizontalLine:false,
				//data:[{text:'<span style="color:red;">缸体</span>分类结构'}],
				columns:[
					{header:'分类结构',dataIndex:'classname'}
				]
	        }]
		});
	}



	var classTreeData = cims201.utils.getData('codeclass/code-class!findById.action',{id:codeClassId});
	console.log(classTreeData);
	for(var i=0;i<classTreeData.result.length;i++){
		classTreeData.result[i].icon ='e-tree-folder';
	}
	ClassTree_check.set("data",classTreeData.result);
	 
	 CodeClassMange_check_window.show('center','middle',true);
}