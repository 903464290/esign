<html>
<?php
require_once 'PHPWord.php';

$PHPWord = new PHPWord();

$document = $PHPWord->loadTemplate('download/test1.docx');

$img1='139.196.46.139/sign/1.png';




$document->setValue('Value15', $img1);



$document->setValue('weekday', date('l'));
$document->setValue('time', date('H:i'));

$document->save('download/test2.docx');


exit;
?>
</html>