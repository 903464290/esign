<?php
require_once 'vendor/autoload.php';

// Creating the new document...
//$phpWord = new \PhpOffice\PhpWord\PhpWord();

/*$section = $phpWord->addSection();

$fontStyle = new \PhpOffice\PhpWord\Style\Font();
$fontStyle->setBold(true);
$fontStyle->setName('Tahoma');
$fontStyle->setSize(13);
$myTextElement = $section->addText('"Believe you can and you\'re halfway there." (Theodor Roosevelt)');
$myTextElement->setFontStyle($fontStyle);

$textrun = $section->addTextRun();
$textrun->addImage('2016111101545711290.png');

$myTextElement1 = $section->addText('"Believe you can and you\'re halfway there." (Theodor Roosevelt)');
$myTextElement1->setFontStyle($fontStyle);

$objWriter = \PhpOffice\PhpWord\IOFactory::createWriter($phpWord, 'Word2007');
$objWriter->save('Template.docx');
*/
/*$templateProcessor = new TemplateProcessor('Template.docx');
$templateProcessor->setValue('Name', 'John Doe');
$templateProcessor->setValue(array('City', 'Street'), array('Detroit', '12th Street'));*/



$templateProcessor = new \PhpOffice\PhpWord\TemplateProcessor('download/test1.docx');


$arrImagenes=array('src' => 'sign/1.png');
$templateProcessor->setImg('Image', $arrImagenes);

$arrImagenes2=array('src' => 'sign/2/2.png');
$templateProcessor->setImg('Image2', $arrImagenes2);

$templateProcessor->setValue('Name', date('Y-m-d H:i'));
$templateProcessor->setValue('Name1', date('Y-m-d H:i'));
$templateProcessor->saveAs('download/final.docx');

header('Location: http://139.196.46.139:8088/admin/index.html');
exit;