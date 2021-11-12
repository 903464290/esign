<?php
require_once '../PHPWord.php';

$PHPWord = new PHPWord();

$document = $PHPWord->loadTemplate('Templates.docx');

//设置甲方(买方)名称：
$document->setValue('jname', 'Sun');

//设置乙方(卖方)名称：
$document->setValue('yname', 'Mercury123');

//设置甲方地址：
$document->setValue('jaddress', 'Mercury123');

//设置乙方地址：
$document->setValue('yaddress', 'Mercury123');

//设置甲方电话：
$document->setValue('jphone', 'Mercury123');

//设置乙方电话：
$document->setValue('yphone', 'Mercury123');

//产品名称：
$document->setValue('cname', 'Mercury123');

//型号、规格：
$document->setValue('ctype', 'Mercury123');

//生产厂家：
$document->setValue('changjia', 'Mercury123');

//单位：
$document->setValue('danwei', 'Mercury123');

//数量：
$document->setValue('num', 'Mercury123');

//单价：
$document->setValue('price', 'Mercury123');

//总价：
$document->setValue('total', 'Mercury123');

//总金额：
$document->setValue('dtotal', 'Mercury123');

//保修年限：
$document->setValue('byear', 'Mercury123');

//交货天数：
$document->setValue('jdate', 'Mercury123');

//交货地点：
$document->setValue('jaddress', 'Mercury123');

//联系方式：
$document->setValue('phone', 'Mercury123');

//付款方式：预付款/货到付款
$document->setValue('factive', 'Mercury123');

//支付方式：银行汇款/支票
$document->setValue('zactive', 'Mercury123');

//付款期限：甲方在合同签订后一周内支付100%货款，即￥XXX元人民币。

//调用方式：
	//$document->setValue('Template', iconv('utf-8', 'GB2312//IGNORE', '中文'));
$document->setValue('fqi', iconv('utf-8', 'GB2312//IGNORE', '甲方在合同签订后一周内支付100%货款，即￥100元人民币。'));

//乙方开户银行：
$document->setValue('ybank', 'Mercury123');

//开户账号：
$document->setValue('yzhang', 'Mercury123');

//合同份数：
$document->setValue('htnum', 'Mercury123');

//甲一份
$document->setValue('jnum', 'Mercury123');

//乙一份
$document->setValue('ynum', 'Mercury123');

//甲方盖章图：
$document->setValue('jpic', 'Mercury123');

//乙方盖章图：
$document->setValue('ypic', 'Mercury123');

//甲方签字代表：
$document->setValue('jqian', 'Mercury123');

//乙方签字代表：
$document->setValue('yqian', 'Mercury123');

//甲方签字日期：
$document->setValue('jqiandate', 'Mercury123');

//乙方签字日期：
$document->setValue('yqiandate', 'Mercury123');

$document->save('ht1.docx');
?>