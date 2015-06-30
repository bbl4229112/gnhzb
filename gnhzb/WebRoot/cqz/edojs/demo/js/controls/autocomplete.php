<?php
	$key = $_GET["key"];
	
	$db = array(
		array('id'=>'1', 'text'=> 'jack'),
		array('id'=>'2', 'text'=> 'niko'),
		array('id'=>'3', 'text'=> 'jason'),
		array('id'=>'4', 'text'=> '张三'),
		array('id'=>'5', 'text'=> '李四')
	);
	
	$list = array();
	foreach ($db as $o) {  				
		if(strrpos($o["text"], $key) !== false){
			$list[] = $o;
		}
	}
	echo json_encode($list);
?>