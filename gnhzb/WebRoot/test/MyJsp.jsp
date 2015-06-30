<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSF 'MyJsp.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">


		<link href="js/edo/res/css/edo-all.css" rel="stylesheet"
			type="text/css" />
		<script src="js/edo/edo.js" type="text/javascript"></script>

		<script src="js/cims201.js" type="text/javascript"></script>

		<script src="js/utils.js" type="text/javascript"></script>
		<link href="test/js/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
		<script src="test/js/jquery.min.js"></script>
		<script src="test/js/jquery.Jcrop.js"></script>


		<script src="js/swfupload/swfupload.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/swfupload/swfupload.js"></script>
		<script type="text/javascript" src="js/swfupload/swfupload.queue.js"></script>
	</head>
	<script>

  

       
  </script>
	<body>
		<table cellpadding="0" cellspacing="0" >
			<tr >
				<td colspan="2" align="center">
					<div id="uploader"></div>
				</td>
			</tr>
			<tr>
				<td>
                    <div id="target" style="height:300px;border:thin;border-width: 1;border-color: blue">
				
					</div>
				</td>
				<td>
					<div
						style="width: 100px; height: 100px; overflow: hidden; margin-left: 5px;border:thin;border-width:1;border-color: blue" id="target2">
					
					</div>
				
		
		

				</td>
			</tr>
			<tr>
			<td colspan="2" align="center">
				<div>
								
			<form action="knowledge/fileupload!cutthumbnail.action"> 
			<input type="hidden" size="4" id="thumbnailpath" name="filename" />
			<input type="hidden" size="4" id="x" name="x" />
			<input type="hidden" size="4" id="y" name="y" />	
	        <input type="hidden" size="4" id="w" name="pwidth" />
			<input type="hidden" size="4" id="h" name="pheight" />
			<input type="submit" id="submitimg" name="确定" style="display:none;" value="确定">
				</form> 	
					</div>
			</td>
			</tr>
		</table>
	</body>
</html>
	<script type="text/javascript">
	
	var fileupload1 = Edo.create( {
            id: 'fileupload1',
            type: 'fileupload',
          
            width: 300,
            swfUploadConfig: {              //swfUploadConfig配置对象请参考swfupload组件说明
                
          	upload_url : '../knowledge/fileupload!thumbnailfileupload.action', // 上传地址
							flash_url : 'js/swfupload/swfupload.swf',
							flash9_url : "js/swfupload/swfupload_fp9.swf",
							button_image_url : '../js/swfupload/XPButtonUploadText_61x22.png', // 按钮图片地址
							file_types : '*.gif;*.png;*.jpg', // 上传文件后缀名限制
							file_post_name : 'file', // 上传的文件引用名
							file_size_limit : '100'
                
            },
            
            onfilequeueerror: function(e){
                alert("文件选择错误:"+e.message);
            },
            onfilequeued: function(e){
              fileupload1.upload.startUpload();
            
            
            
            },
            
            onfilestart: function(e){   
                //alert("开始上传");
                 //  box.mask();
            },
            onfileerror: function(e){
                alert("上传失败:"+e.message);
                
             //   box.unmask();
            },
            onfilesuccess: function(e){    
              	var succssfulFile = Edo.util.Json.decode(e.serverData);
           
           
            
               	var filepath=succssfulFile['filepath'];
              
               	var pwidth=succssfulFile.width;
               	
               	var pheight=succssfulFile.height;
          
               
                document.getElementById("target").innerHTML="<img height='300px' src='thumbnail/temp/"+filepath+"' id='jcrop_target' />";

                document.getElementById("target2").innerHTML="<img src='thumbnail/temp/"+filepath+"' id='preview' />";
                document.getElementById("thumbnailpath").value=filepath;
             
                document.getElementById("submitimg").style.display="block";
                 $(function(){

	$('#jcrop_target').Jcrop({
		onChange: showPreview,
		onSelect: showPreview,
		aspectRatio: 1
	});
	
	function showPreview(coords)
{
	if (parseInt(coords.w) > 0)
				{
					var rx = 100 / coords.w;
					var ry = 100 / coords.h;

					jQuery('#preview').css({
						width: Math.round(rx * (300/pheight)* pwidth) + 'px',
						height: Math.round(ry * (300/pheight)* pheight) + 'px',
						marginLeft: '-' + Math.round(rx * coords.x) + 'px',
						marginTop: '-' + Math.round(ry * coords.y) + 'px'
					});
				}
	            jQuery('#x').val(parseInt(coords.x* (pheight/300)));
				jQuery('#y').val(parseInt(coords.y* (pheight/300)));
			
				jQuery('#w').val(parseInt(coords.w* (pheight/300)));
				jQuery('#h').val(parseInt(coords.h* (pheight/300)));
	
	
	
}; 
	
	
	

});             	            
            }
                  });
	
	
	 Edo.create({
    id: 'box',
    type: 'box',
    render: document.getElementById("uploader"),
    children: [
        {
            type: 'panel',title:'上传用户头像',width: '100%', layout: 'horizontal', padding: 2,
            children:[fileupload1]
           
        }        
    ]
});
	
	
	
	
	</script>