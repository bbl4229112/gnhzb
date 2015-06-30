//创建知识分类
function createKnowledgeRank(listtype,size){
	var countStr = '';
	if(listtype == 'viewCount'){
		countStr = '点击率';
	}else if(listtype == 'commentCount'){
		countStr = '评论';
	}else if(listtype == 'rate'){
		countStr = '知识评分';
	}else if(listtype == 'downloadCount'){
		countStr = '下载';
	}
	
	var data = cims201.utils.getData('statistic/khotlist!list.action',{listtype:listtype, index:0, size:(size?size:10)});
	var myKnowledgerank = Edo.create(
        {
            type: 'table',
            width: '100%',
            height: '95%',
            editAction: 'click',
            data: data,
            rowHeight:25,
            columns:[
                {header: '知识名称', dataIndex: 'titleName',width:350,
                 renderer: function(v,r){
                     	var title_outStr = '';
				if(v){
					if(v.length > 4){
						title_outStr += v.substring(0,4)+'...';
					}else{
						title_outStr += v;
					}
	             	var outStr = '';
	             	//alert(r.id);
	             	outStr += '<a href="javascript:showKnowledgeDetail('+r.id+',\''+title_outStr+'\')";><span class="knowledge_simplist_title">'+v+'</span></a>';
             	}
             	return outStr;
                }    
                },   
                {header: '知识类型', dataIndex: 'knowledgetype', width:110,renderer:function(v){
                	return '<a href="javascript:showKnowledgetypeKnowledgeList('+v.id+',\''+v.name+'\')";>'+v.name + '</a>';
                }}, 
                {header: '上传者', dataIndex: 'uploader',width:100, renderer:function(v,r){
                     	var uploader_outStr = '';
				if(v){
			    
	             	uploader_outStr = '<a href="javascript:showUploaderKnowledgeList('+v.id+',\''+v.name+'\')";>'+v.name+'</a>';
             	}
             	 uploader_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_author">&nbsp;</span>'+uploader_outStr;
             	return uploader_outStr;
                }},       
                       {
             headerText: '关键词',
             dataIndex: 'keywords',
             headerAlign: 'center',                 
             align: 'left',
             width: 240,
             renderer: function(v,r){
             	var keywords_outStr = '';
             	var i=0;
             	if(v){ 
             	
             		v.each(function(o){
             			i++;
             			keywords_outStr +=  '<a href="javascript:showKeywordKnowledgeList('+o.id+',\''+o.name+'\')";>'+o.name + '</a>&nbsp';
             		});
             	}
             	if(i>0)
             	keywords_outStr = '&nbsp;&nbsp;&nbsp;<span class="knowledge_simplist_title_keyword">&nbsp;</span>'+keywords_outStr;
             	return keywords_outStr;
             }
         } ,
                
                
                {header: '时间', dataIndex: 'uploadTime',width:150},
                {header: countStr, dataIndex: 'commentRecord',width:80, renderer:function(v){
                	//alert(v);
                	return v[listtype];
                }}
            ]
        }
    );  
    
    this.getKnowledgeRank = function(){
    	return myKnowledgerank;
    }    
}
var knowledgerankdata = [
             	        {label: '点击率', value: 'viewCount'},
            	        {label: '评论', value: 'commentCount'},
            	        {label: '知识评分', value: 'rate'},
            	        {label: '下载', value: 'downloadCount'}
            	    ];
var valueddata=cims201.utils.getData('custom/customization!kValued.action');
if(valueddata == false || rssdata == "false"){
	knowledgerankdata.splice(2,2);
}
//创建知识分类的面板
function createKhotlistPanel(){
	var outPanel = null;
	var currentType = 'viewCount';
	var currentSize = 10;
	var khotlist = new createKnowledgeRank(currentType,currentSize);
	var typeCombo = Edo.create({
		type: 'combo',
		valueField: 'value',
	    displayField: 'label',
	    data: knowledgerankdata,	    
	    selectedIndex: 0,
	    onselectionchange: function(e){
	        currentType = this.getValue();
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createKnowledgeRank(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
	    }		
	});
	
	var sizeCombo = Edo.create({
		type: 'combo',
		valueField: 'value',
	    displayField: 'label',
	    data: [
	        {label: '10', value: 10},
	        {label: '20', value: 20},
	        {label: '50', value: 50},
	        {label: '100', value: 100}
	    ],	    
	    selectedIndex: 1,
	    onselectionchange: function(e){
	        currentSize = this.getValue();
	        if(outPanel){
	        	outPanel.removeChildAt(1);
		        khotlist.getKnowledgeRank().destroy();
		        khotlist = new createKnowledgeRank(currentType,currentSize);
		        outPanel.addChild(khotlist.getKnowledgeRank());
	        }
	    }		
	});
	
	outPanel = Edo.create({
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
					{type: 'label', text: '排行类型'},
					typeCombo,
					{type: 'label', text: '每页数量'},
					sizeCombo
				]
			},
			khotlist.getKnowledgeRank()
		]
	});
	
	this.getKhot = function(){
		return outPanel;
	}
}
