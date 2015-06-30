Edo.build({
	type: 'app',render: document.body,width: '100%',height: '100%',layout: 'vertical',padding:[0,0,0,0],
    children: [
               
		{type: 'box',width: '100%',height: '100%',padding:[0,0,0,0],border:[0,0,0,0],verticalGap:'0',
		children:[
	        //{type: 'button',text: '选择评价指标',},
	        /*{
	            type: 'box',
	            width: '100%', height: 60,padding:[0,0,0,0],
	            enableCollapse: true,
	            splitRegion: 'north',
	            splitPlace: 'after',
	            layout:'horizontal',
	            	children:[
	                          
	            	          {type: 'check',text: '全球变暖',},
	            	          {type: 'check',text: '臭氧层破坏',},
	            	          {type: 'check',text: '酸化',},
	            	          {type: 'check',text: '富营养化',},
	            	          {type: 'check',text: '光化学烟雾',},
	            	          {type: 'check',text: '陆地生态毒性',},
	            	          {type: 'check',text: '水体生态毒性',},
	            	          {type: 'check',text: '土地利用',},
	            	          {type: 'check',text: '水体利用',}]
	        }*/
	        {
                type: 'panel',
                title:'选择评价指标',
                width: '100%',
                collapseProperty: 'width',
                border:[0,0,0,0],
                children:[
                  {   
    	        	type:'CheckGroup', 
    	            repeatDirection: 'horizontal',
    	            repeatItems: 5,
    	            repeatLayout: 'table',       
    	            itemWidth: '100px',
    	            valueField: 'value',
    	            data: [
    	                {text: '全球变暖', value: 1},
    	                {text: '臭氧层破坏', value: 2},
    	                {text: '酸化', value: 3},
    	                {text: '富营养化', value: 4},
    	                {text: '光化学烟雾', value: 5},
    	                {text: '陆地生态毒性', value: 6},
    	                {text: '水体生态毒性', value: 7},
    	                {text: '土地利用', value: 8},
    	                {text: '水体利用', value: 9}
    	            ]
    	        }]
	        }
	        ,
	        //{type: 'button',text: '选择计算指标',},
	        /*{
	            type: 'box',
	            width: '100%', height: 60,padding:[0,0,0,0],
	            enableCollapse: true,
	            splitRegion: 'south',
	            splitPlace: 'before',
	            layout:'horizontal',
	            children:[
	                      
	        	          {type: 'check',text: '特征化指标',},
	        	          {type: 'check',text: '归一化指标',},
	        	          {type: 'check',text: '加权指标',}]
	        },*/
	        {
                type: 'panel',
                title:'选择计算指标',
                width: '100%',
                collapseProperty: 'width',
                border:[1,0,0,0],
                children:[
			        {   
			        	type:'CheckGroup', 
			            repeatDirection: 'horizontal',
			            repeatItems: 5,
			            repeatLayout: 'table',       
			            itemWidth: '100px',
			            valueField: 'value',
			            data: [
			                {text: '特征化指标', value: 1},
			                {text: '归一化指标', value: 2},
			                {text: '加权指标', value: 3}
			            ]
			        }]
	        },
	        //{type: 'button',text: '计算结果输出',},
	        {
                type: 'panel',
                title:'计算结果输出',
                width: '100%',
                border:[1,0,0,0],
                collapseProperty: 'width',
                children:[
			        {
			            type: 'ct',
			            width: '100%', height: '100%',padding:[0,0,0,0],
			            layout: 'horizontal',
			            children:[
			                {
			                    type: 'panel',
			                    title:'物质清单',
			                    width: 200,
			                    height: 200,
			                    collapseProperty: 'width',
			                    enableCollapse: true,
			                    splitRegion: 'west',
			                    splitPlace: 'after'
			                },
			                {
			                	type: 'panel',
			                	title:'影响评价',
			                    width: 200,
			                    height: 200,
			                    collapseProperty: 'width',
			                    enableCollapse: true,
			                    splitRegion: 'west',
			                    splitPlace: 'after'
			                },
			                {
			                    type: 'panel',
			                    title:'敏感度分析',
			                    width: 200,
			                    height: 200,
			                    collapseProperty: 'width',
			                    enableCollapse: true,
			                }
			            ]
			        }]}
	        ]
		}
        
    ]
    
    
});