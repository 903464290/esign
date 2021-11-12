
<?php
require_once './vendor/autoload.php';

echo date('H:i:s'), ' Creating new TemplateProcessor instance...', EOL;
$templateProcessor = new TemplateProcessor('download/test1.docx');




$img1='sign/1.png';
$img2='sign/2/2.png';


$templateProcessor->setImageValue('Value15', [
            'path' => $img1,
            'width' => 200,
            'height' => 200,
            'ratio' => false]);

$templateProcessor->setImageValue('Value17', [
            'path' => $img2,
            'width' => 200,
            'height' => 200,
            'ratio' => false]);
$templateProcessor->setValue('Value16',date('Y-m-d-H-i-s'));
$templateProcessor->setValue('Value18',date('Y-m-d-H-i-s'));

$templateProcessor->saveAs('download/final.docx');

header('Location: http://139.196.46.139/sign/final.html');
exit;

     
 

sss
?>
