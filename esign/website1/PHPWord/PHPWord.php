<?php
    require_once './ThinkPHP/Library/Vendor/PHPWord/PHPWord.php';
    require_once './ThinkPHP/Library/Vendor/PHPWord/PHPWord/IOFactory.php';
    $PHPWord =  new \PHPWord();
    $templateProcessor = $PHPWord->loadTemplate('./Uploads/2018-03-19/djb.docx');//找到文件

    $templateProcessor->setValue('gat',iconv('utf-8', 'GB2312//IGNORE', "内容"));

    $templateProcessor->save('文件名.docx');
?>