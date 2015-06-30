/**
知识订阅输入的界面
*/
function changeInterestModelCount(id){
	var imdata = cims201.utils.getData('knowledge/knowledge!saveImcounts.action',{id:id});
	
	if(Edo.get("imtree")!=null){
		Edo.get("imtree").data.each(function(o){
		if(o.text!='firstLayer'){
		var type = o.text;
		var treenodeid = o.id;
		var trueid = o.id.substring(8);

		imdata.each(function(o){		
			if(o.interestItemType==type && o.interestItemId==trueid){
					Edo.get(treenodeid).set('name',o.interestItemName+'('+o.counts+')');
				}
			});
			
		}

	});
 
	Edo.get("imtree").refresh(); 
	}
	

}