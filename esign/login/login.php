
<?php
	header("content-type:text/html;charset=utf-8");
	//连接数据库

	$link = mysqli_connect("localhost:3306","n_users","WmjtkjrmBR265Xsa","n_users");
	if (!$link) {
		die("连接失败: " . mysqli_connect_error());
	}
	//接收$_POST用户名和密码
	$username = $_POST['username'];
	$password = md5(trim($_POST['password']));
	//查看表user用户名与密码和传输值是否相等
	$sql = "SELECT * FROM t_user WHERE username = '$username' AND password = '$password'";
	//result必需规定由 mysqli_query()、mysqli_store_result() 或 mysqli_use_result() 返回的结果集标识符。
	$result = mysqli_query($link,$sql);
	$num = mysqli_num_rows($result);//函数返回结果集中行的数量
	//判断是否登录后显示或跳转
	if($num){
        echo "<script>'sucessful!';
        location.href='../person.html';</script>";
	}else{
		echo'Fail to login';
	}
    
?>
