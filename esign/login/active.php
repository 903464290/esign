<?php
error_reporting(E_ALL ^ E_DEPRECATED);
include_once("connect.php");

$verify = stripslashes(trim($_GET['verify']));
$nowtime = time();

$query = mysql_query("select id,token_exptime from t_user where status='0' and `token`='$verify'");
$row = mysql_fetch_array($query);
if($row){
	if($nowtime>$row['token_exptime']){ //30min
		$msg = 'Your activation period has expired. Please log in to your account and resend the activation email';
	}else{
		mysql_query("update t_user set status=1 where id=".$row['id']);
		if(mysql_affected_rows($link)!=1) die(0);
		$msg = 'Successful activation. The login page is displayed after 5 seconds';
		header("refresh:5;url=login.html");
	}
}else{
	$msg = 'error.';	
}

echo $msg;
?>
