function createCodeClassRuleManage_check(codeClassId){
	
	var ruleData = cims201.utils.getData('codeclass/code-class!getRuleByCodeClassId.action',{id:codeClassId});
	var ruleObj =  Edo.util.JSON.decode(ruleData.rule);
	if(!Edo.get('CodeClassRuleManageTree_check_window')){
		Edo.create({
			id:'CodeClassRuleManageTree_check_window',
			type:'window',
			height:'300',
			width:'500',
			title:ruleData.className+"的规则审批",
			padding:[0,0,0,0],
			titlebar:[
	            {
	                cls: 'e-titlebar-close',
	                onclick: function(e){
	                    this.parent.owner.hide();       //hide方法
	                }
	            }
	        ],
			children:[{
				type:'tree',id:'CodeClassRuleManageTree_check',width:'100%',height:'100%',
				headerVisible:false,
			    autoColumns: true,
			    horizontalLine: false,
				columns: [
			        {
			            dataIndex: 'text'
			        }
			    ],
			    //data:[{'text':'分类码 首字段：T','value':'0:T'},{'text':'分类码 第【1】层 字符型，长度：5','value':'1:C5'},{'text':'分类码 第【2】层 字符型，长度：5','value':'2:C5'},{'text':'分类码 第【3】层 字符型，长度：5','value':'3:C5'}]
			    data:ruleObj
			}]
		});
	}
	
	
	
	CodeClassRuleManageTree_check_window.show('center', 'middle', true);
}