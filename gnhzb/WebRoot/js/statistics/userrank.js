//用户贡献
var userorderdata = [
             	    
         	        {label: '参与度', value: 'totoalscore'},
         	        {label: '贡献度', value: 'contributionscore'}
         	    ];
var contributionddata=cims201.utils.getData('custom/customization!kContribution.action');
if(contributionddata == false || contributionddata == "false"){
	userorderdata.splice(1,1);
} 
function createUserRank(){
	var currentType = 'totoalscore';
	var currentModel = 'week';
	var currentIndex = 0;
	var currentSize = 10;
	var myUserRank = Edo.create({
		type: 'module',
		width: '100%',
   		height: '100%',
		src: 'userranking/userranking!list.action?rankType='+currentType+'&model_='+currentModel+'&index='+currentIndex+'&size='+currentSize
	});
	var typeCombo = Edo.create({
		type: 'combo',
		valueField: 'value',
	    displayField: 'label',
	    data: userorderdata,	  
	    selectedIndex: 0,  
	    onselectionchange: function(e){
	        currentType = this.getValue();
	        if(outPanel){
		        outPanel.removeChildAt(1);
		        myUserRank.destroy();
		        myUserRank = Edo.create({
					type: 'module',
					width: '100%',
			   		height: '100%',
					src: 'userranking/userranking!list.action?rankType='+currentType+'&model_='+currentModel+'&index='+currentIndex+'&size='+currentSize
				});
		        outPanel.addChildAt(1,myUserRank);
	        }
	    }		
	});
	
	var modelCombo = Edo.create({
		type: 'combo',
		valueField: 'value',
	    displayField: 'label',
	    data: [
	        {label: '本周', value: 'week'},
	        {label: '前两周', value: 'lasttwoweek'},
	        {label: '本月', value: 'month'},
	        {label: '前一月', value: 'lastmonth'},
	        {label: '本年', value: 'year'},
	        {label: '去年', value: 'lastyear'},
	        {label: '所有', value: 'all'}
	    ],	 
	    selectedIndex: 0,   
	    onselectionchange: function(e){
	        currentModel = this.getValue();
	        if(outPanel){
		        outPanel.removeChildAt(1);
		        myUserRank.destroy();
		        myUserRank = Edo.create({
					type: 'module',
					width: '100%',
			   		height: '100%',
					src: 'userranking/userranking!list.action?rankType='+currentType+'&model_='+currentModel+'&index='+currentIndex+'&size='+currentSize
				});
		        outPanel.addChildAt(1,myUserRank);
	        }
	    }		
	});
	
//	var sizeCombo = Edo.create({
//		type: 'combo',
//		valueField: 'value',
//	    displayField: 'label',
//	    data: [
//	        {label: '10', value: 10},
//	        {label: '20', value: 20},
//	        {label: '50', value: 50},
//	        {label: '100', value: 100}
//	    ],	
//	    selectedIndex: 0,    
//	    onselectionchange: function(e){
//	        currentSize = this.getValue();
//	        if(outPanel){	        
//		        outPanel.removeChildAt(1);
//		        myUserRank.destroy();
//		        myUserRank = Edo.create({
//					type: 'module',
//					width: '100%',
//			   		height: '100%',
//					src: 'userranking/userranking!list.action?rankType='+currentType+'&model_='+currentModel+'&index='+currentIndex+'&size='+currentSize
//				});
//		        outPanel.addChildAt(1,myUserRank);
//	        }
//	    }		
//	});
	
	var indexLabel = Edo.create({
		type: 'label',
		text: '0'
	});
	
	var usercount=cims201.utils.getData("userranking/userranking!getUserCount.action",null);
	
	var indexCombo = Edo.create({
		type: 'slider',
		minValue: 0, 
		maxValue: usercount,
		onvaluechange: function(e){
			indexLabel.set('text', e.value);
			currentIndex = e.value;
				        if(outPanel){	        
		        outPanel.removeChildAt(1);
		        myUserRank.destroy();
		        myUserRank = Edo.create({
					type: 'module',
					width: '100%',
			   		height: '100%',
					src: 'userranking/userranking!list.action?rankType='+currentType+'&model_='+currentModel+'&index='+currentIndex+'&size='+currentSize
				});
		        outPanel.addChildAt(1,myUserRank);
		        }	
		}	
	});
	
	
	var outPanel = Edo.create({
		type: 'box',
		width: '100%',
		height: '100%',
		border: [0,0,0,0],
		padding: [0,0,0,0],
		layout: 'vertical',
		children: [
			{
				type: 'box',
				width: '100%',
				border: [0,0,0,0],
				padding: [0,0,0,0],
				layout: 'horizontal',
				children: [
			    	{type: 'label', text: '模式'},
					modelCombo,
					{type: 'label', text: '排行类型'},
					typeCombo,
					
//					{type: 'label', text: '每页数量'},
//					sizeCombo,
					{type: 'label', text: '起始位置'},
					indexCombo,
					indexLabel
				]
			},
			myUserRank
		]
	});
	
	this.getUserrank = function(){
		return outPanel;
	}
}