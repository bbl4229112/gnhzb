<?php  
	$key = $_GET["key"];
  
  //获得分页信息
  $index = (int)$_GET["index"];
  $size = (int)$_GET["size"];
  
  $total = 123;
	$start = $index * $size;
  $end = $start + $size;
	
  //根据分页信息,模拟数据库查询分页
  $list = array();
  for($i=$start; $i < $end; $i++){
  
      if($i >= $total) break;
      
      $o = array();
      $o["company"] = "易度"+$i;
      $o["update"] = date('Y-m-d\TH:i:s');
      
      $list[] = $o;
  }
  
  $result = array();
  $result["total"] = $total;
  $result["result"] = $list;
    
	echo json_encode($result);  
?>