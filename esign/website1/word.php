<html>
<?php
require_once 'PHPWord.php';

$PHPWord = new PHPWord();

$document = $PHPWord->loadTemplate('contract1/hetong1.docx');

$namea=$_POST['namea'];
$ida=$_POST['ida'];
$emaila=$_POST['emaila'];
$nameb=$_POST['nameb'];
$idb=$_POST['idb'];
$emailb=$_POST['emailb'];
$address=$_POST['address'];
$size=$_POST['size'];
$date1=$_POST['date1'];
$date2=$_POST['date2'];
$date3=$_POST['date3'];
$rental=$_POST['rental'];
$date4=$_POST['date4'];
$other=$_POST['other'];


$document->setValue('myReplacedValue',$namea);
$document->setValue('Value1', $namea);
$document->setValue('Value2', $ida);
$document->setValue('Value3', $emaila);
$document->setValue('Value4', $nameb);
$document->setValue('Value5', $idb);
$document->setValue('Value6', $emailb);
$document->setValue('Value7', $address);
$document->setValue('Value8', $size);
$document->setValue('Value9', $date1);
$document->setValue('Value10', $date2);
$document->setValue('Value11', $date3);
$document->setValue('Value12', $rental);
$document->setValue('Value13', $date4);
$document->setValue('Value14', $other);


$document->setValue('weekday', date('l'));
$document->setValue('time', date('H:i'));

$document->save('download/test1.docx');

header('Location: http://139.196.46.139/website1/sign/mainsign.html');
exit;
?>
</html>