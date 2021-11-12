<?php

error_reporting(E_ALL ^ E_DEPRECATED);

include_once("connect.php");

$username = stripslashes(trim($_POST['username']));

//检测用户名是否存在
$query = mysql_query("select id from t_user where username='$username'");
$num = mysql_num_rows($query);
if($num==1){
	echo '<script>alert("User name already exists, please change another one");window.history.go(-1);</script>';
	exit;
    }
$password = md5(trim($_POST['password']));
$email = trim($_POST['email']);
$regtime = time();

$token = md5($username.$password.$regtime); //创建用于激活识别码
$token_exptime = time()+60*60*24;//过期时间为24小时后

$sql = "insert into `t_user` (`username`,`password`,`email`,`token`,`token_exptime`,`regtime`) values ('$username','$password','$email','$token','$token_exptime','$regtime')";

mysql_query($sql);

if(mysql_insert_id()){//写入成功，发邮件
function sendMail($to,$title,$content) {
    // 这个PHPMailer 就是之前从 Github上下载下来的那个项目
    require 'PHPMailer/PHPMailerAutoload.php';

    $mail = new PHPMailer;
    //使用smtp鉴权方式发送邮件
    $mail->isSMTP();
    //smtp需要鉴权 这个必须是true
    $mail->SMTPAuth = true;
    // qq 邮箱的 smtp服务器地址，这里当然也可以写其他的 smtp服务器地址
    $mail->Host = 'smtp.qq.com';
    //smtp登录的账号 这里填入字符串格式的qq号即可
    $mail->SMTPSecure = 'ssl';
		//设置ssl连接smtp服务器的远程服务器端口号 可选465或587
	$mail->Port = 465;
    $mail->Username = '903464290@qq.com';
    // 这个就是之前得到的授权码，一共16位
    $mail->Password = 'epygvhoxdpzhbbbj';
    $mail->setFrom('903464290@qq.com', 'E-Sign');
    // $to 为收件人的邮箱地址，如果想一次性发送向多个邮箱地址，则只需要将下面这个方法多次调用即可
    $mail->addAddress($to);
    // 该邮件的主题
    $mail->Subject = $title;
    // 该邮件的正文内容
    $mail->Body = $content;

    // 使用 send() 方法发送邮件
    if(!$mail->send()) {
        return 'send fail, please try again' . $mail->ErrorInfo;
    } else {
        return "send successful, please check your email";
    }
}

// 调用发送方法，并在页面上输出发送邮件的状态

    $emailbody = "Dear Mr/Miss.".$username.": 
        This mail is send by E-Sign.
        Clike the link to active your id. http://139.196.46.139/login/active.php?verify=".$token."
        If the above link cannot be clicked, please copy it to your browser's address bar for access, the link will be valid for 24 hours. 
        If this activation request was not sent by you, please ignore this email.———— E-Sign Group";
var_dump(sendMail($email,'Activation link',$emailbody));  
}
?>