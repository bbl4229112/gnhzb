Edo.build({
	type: 'app',render: document.body,width: '100%',height: '100%',layout: 'vertical',padding:[0,0,0,0],
    children: [
		{type: 'box',width: '100%',height: '100%',padding:[0,0,0,0],border:[0,0,0,0],
			children:[
		        //{type: 'button',text: '完整性',},
		        /*{
		            type: 'box',
		            width: '100%', height: 60,
		            enableCollapse: true,
		            splitRegion: 'north',
		            splitPlace: 'after',
		            layout:'horizontal',
		            	children:[
		            	          {type: 'check',text: '1分',},
		            	          {type: 'check',text: '3分',},
		            	          {type: 'check',text: '5分',},
		            	          {type: 'check',text: '7分',},
		            	          {type: 'check',text: '9分',}]
		        },*/
		        {
                type: 'panel',
                title:'完整性',
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
			                {text: '1分', value: 1},
			                {text: '2分', value: 2},
			                {text: '3分', value: 3},
			                {text: '4分', value: 4},
			                {text: '5分', value: 5}
			            ]
			        }]
		        },
		        //{type: 'button',text: '可靠性',},
		        /*{
		            type: 'box',
		            width: '100%', height: 60,
		            enableCollapse: true,
		            splitRegion: 'north',
		            splitPlace: 'after',
		            layout:'horizontal',
		            	children:[
		            	          {type: 'check',text: '1分',},
		            	          {type: 'check',text: '3分',},
		            	          {type: 'check',text: '5分',},
		            	          {type: 'check',text: '7分',},
		            	          {type: 'check',text: '9分',}]
		        },*/
		        {
	                type: 'panel',
	                title:'可靠性',
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
				                {text: '1分', value: 1},
				                {text: '2分', value: 2},
				                {text: '3分', value: 3},
				                {text: '4分', value: 4},
				                {text: '5分', value: 5}
				            ]
				        }]
		        },
		        //{type: 'button',text: '一致性性',},
		        /*{
		            type: 'box',
		            width: '100%', height: 60,
		            enableCollapse: true,
		            splitRegion: 'north',
		            splitPlace: 'after',
		            layout:'horizontal',
		            	children:[
		            	          {type: 'check',text: '1分',},
		            	          {type: 'check',text: '3分',},
		            	          {type: 'check',text: '5分',},
		            	          {type: 'check',text: '7分',},
		            	          {type: 'check',text: '9分',}]
		        },*/
		        {
	                type: 'panel',
	                title:'一致性性',
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
				                {text: '1分', value: 1},
				                {text: '2分', value: 2},
				                {text: '3分', value: 3},
				                {text: '4分', value: 4},
				                {text: '5分', value: 5}
				            ]
				        }]
		        },
		        {
	                type: 'panel',
	                title:'文字点评',
	                width: '100%',
	                collapseProperty: 'width',
	                border:[1,0,1,0],
	                padding:[0,0,0,0],
	                children:[
				        {   
				        	type:'textarea', 
				        	width: '100%',
				        	style:'border:0;',
				            height: 100
				            
				        }]
		        },
		        {type: 'button',text: '提交',style:'margin-left:30px;'}
		        //{type: 'label',text: '专家名字：',},{type: 'text',text: '张安三',},{type: 'button',text: '提交'}
		        ]
		}
    ]
 
});