<?php
/*  base64格式编码转换为图片并保存对应文件夹 */  
$base64_content =$_POST['src_data'];

//截取数据为数组
$base64_content= explode(',',$base64_content); 

//生成目录：demo所在根目录下
$new_file = "./".date('Ymd',time())."/";  

$new_flie = 'sign_image/'.date('Ymd',time())."/";  
if(!file_exists($new_flie)){  
	//检查是否有该文件夹，如果没有就创建，并给予最高权限  
	$flag =mkdir($new_flie,0700,true);
}

//文件后缀名
$type = 'png';
//生成文件名：相对路径
$new_file = '2'.".$type";
//转换为图片文件
if (file_put_contents($new_file,base64_decode($base64_content[1]))){
	echo $new_file; 
}else{ 
	return false; 
} 

?>
