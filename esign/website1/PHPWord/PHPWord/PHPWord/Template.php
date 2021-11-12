<?php
/**
 * PHPWord
 *
 * Copyright (c) 2011 PHPWord
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * @category   PHPWord
 * @package    PHPWord
 * @copyright  Copyright (c) 010 PHPWord
 * @license    http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt    LGPL
 * @version    Beta 0.6.3, 08.07.2011
 */


/**
 * PHPWord_DocumentProperties
 *
 * @category   PHPWord
 * @package    PHPWord
 * @copyright  Copyright (c) 2009 - 2011 PHPWord (http://www.codeplex.com/PHPWord)
 */
class PHPWord_Template {
	
	 const MAXIMUM_REPLACEMENTS_DEFAULT = -1;
 
    //添加两个变量
    protected $_rels;
    protected $_types;
    
    /**
     * ZipArchive
     * 
     * @var ZipArchive
     */
    private $_objZip;
    
    /**
     * Temporary Filename
     * 
     * @var string
     */
    private $_tempFileName;
    
    /**
     * Document XML
     * 
     * @var string
     */
    private $_documentXML;
    
    
    /**
     * Create a new Template Object
     * 
     * @param string $strFilename
     */
    public function __construct($strFilename) {
        $path = dirname($strFilename);
        $this->_tempFileName = $path.DIRECTORY_SEPARATOR.time().'.docx';
        
        copy($strFilename, $this->_tempFileName); // Copy the source File to the temp File

        $this->_objZip = new ZipArchive();
        $this->_objZip->open($this->_tempFileName);
        
        $this->_documentXML = $this->_objZip->getFromName('word/document.xml');
		 $this->_countRels=100;
    }
    
    /**
     * Set a Template value
     * 
     * @param mixed $search
     * @param mixed $replace
     */
    public function setValue($search, $replace) {
        if(substr($search, 0, 2) !== '${' && substr($search, -1) !== '}') {
            $search = '${'.$search.'}';
        }
        
        //if(!is_array($replace)) {
            //$replace = utf8_encode($replace);
			//$replace =iconv('gbk', 'utf-8', $replace);
       // }
        $replace =iconv('gbk', 'utf-8', $replace);
        
        $this->_documentXML = str_replace($search, $replace, $this->_documentXML);
    }
    
    /**
     * Save Template
     * 
     * @param string $strFilename
     */
    public function save($strFilename) {
        if(file_exists($strFilename)) {
            unlink($strFilename);
        }
        
        $this->_objZip->addFromString('word/document.xml', $this->_documentXML);
		
		if($this->_rels!="")
		{
			$this->_objZip->addFromString('word/_rels/document.xml.rels', $this->_rels);
		}
		if($this->_types!="")
		{
			$this->_objZip->addFromString('[Content_Types].xml', $this->_types);
		}
        
        // Close zip file
        if($this->_objZip->close() === false) {
            throw new Exception('Could not close zip file.');
        }
        
        rename($this->_tempFileName, $strFilename);
    }
	
	
	
	public function setImg( $strKey, $img){
        $strKey = '${'.$strKey.'}';
        $relationTmpl = '<Relationship Id="RID" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/IMG"/>';
 
        $imgTmpl = '<w:pict><v:shape type="#_x0000_t75" style="width:WIDpx;height:HEIpx"><v:imagedata r:id="RID" o:title=""/></v:shape></w:pict>';
 
        $toAdd = $toAddImg = $toAddType = '';
        $aSearch = array('RID', 'IMG');
        $aSearchType = array('IMG', 'EXT');
        $countrels=$this->_countRels++;
		$imgExt = 'jpg';
        $imgName = 'img' . $countrels . '.' . $imgExt;
		
		$this->_objZip->deleteName('word/media/' . $imgName);
		$this->_objZip->addFile($img['src'], 'word/media/' . $imgName);
 
		$typeTmpl = '<Override PartName="/word/media/'.$imgName.'" ContentType="image/EXT"/>';
		
 
	
		$rid = 'rId' . $countrels;
		$countrels++;
		list($w,$h) = getimagesize($img['src']);
	
		 if(isset($img['swh'])) //Image proportionally larger side
		 {
			 if($w<=$h)
			 {
				$ht=(int)$img['swh'];
				$ot=$w/$h;
				$wh=(int)$img['swh']*$ot;
				$wh=round($wh);
			 }
			 if($w>=$h)
			 {
				$wh=(int)$img['swh'];
				$ot=$h/$w;
				$ht=(int)$img['swh']*$ot;
				$ht=round($ht);
			 }
			 $w=$wh;
			 $h=$ht;
		 }
 
		if(isset($img['size']))
		{
		$w = $img['size'][0];
		$h = $img['size'][1];           
		}
 
 
		$toAddImg .= str_replace(array('RID', 'WID', 'HEI'), array($rid, $w, $h), $imgTmpl) ;
		
		if(isset($img['dataImg']))
		{
			$toAddImg.='<w:br/><w:t>'.$this->limpiarString($img['dataImg']).'</w:t><w:br/>';
		}
		
 
		$aReplace = array($imgName, $imgExt);
		$toAddType .= str_replace($aSearchType, $aReplace, $typeTmpl) ;
 
		$aReplace = array($rid, $imgName);
		$toAdd .= str_replace($aSearch, $aReplace, $relationTmpl);
 
		$this->_documentXML=str_replace('<w:t>' . $strKey . '</w:t>', $toAddImg, $this->_documentXML);
 
 
 
		if($this->_rels=="")
		{
			$this->_rels=$this->_objZip->getFromName('word/_rels/document.xml.rels');
			$this->_types=$this->_objZip->getFromName('[Content_Types].xml');
		}

		$this->_types       = str_replace('</Types>', $toAddType, $this->_types) . '</Types>';
		$this->_rels        = str_replace('</Relationships>', $toAdd, $this->_rels) . '</Relationships>';
		
	}
	function limpiarString($str) {
		print_r($str);
			return str_replace(
					array('&', '<', '>', "\n"), 
					array('&amp;', '&lt;', '&gt;', "\n" . '<w:br/>'), 
					$str
			);
	}
}
?>