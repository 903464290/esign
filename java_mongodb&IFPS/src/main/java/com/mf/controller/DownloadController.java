package com.mf.controller;


import com.mf.bean.IPfsFile;
import com.mf.util.FileUtils;
import com.mf.util.IPFSTools;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class DownloadController {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    
    //Download files from ipfs
    @RequestMapping(value = "/downloadFileForIPFS")
    public void downloadFileForIPFS(@RequestParam(name = "hash") String hash, @RequestParam(name = "fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//Get the path of the current container and create a temporary folder
        String tempFilePath = request.getSession().getServletContext().getRealPath("/tempFile");
        String strYear=Calendar.getInstance().get(Calendar.YEAR)+"";
       //Get the path to save the temporary file, use tempFile+year to save
        File tempFileObj = new File(tempFilePath + "/"+strYear);
        //If the directory does not exist, create the directory
        if (!tempFileObj.isDirectory()){

            tempFileObj.mkdirs();
        }
        System.out.println("fileName="+fileName);
        String downLoadFileName =fileName;
        String destFile =tempFileObj.getPath()+"\\"+downLoadFileName;
        //Call the Ipfs tool to start downloading files from the ipfs server
        IPFSTools.IPFS_Download(hash, destFile);
        //Setup file
        File file = new File(destFile);
        // 5.Set the response header
		if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
			downLoadFileName = java.net.URLEncoder.encode(downLoadFileName, "UTF-8");
        } else {
        	downLoadFileName = new String(downLoadFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
       // Set forced download not to open
         response.setContentType("application/force-download");
      // Set file name
         response.addHeader("Content-Disposition", "attachment;fileName=" + downLoadFileName);
         //Define byte stream
            byte[] buffer = new byte[1024];
            //Define the FileInputStream object to start downloading
            FileInputStream fis = null;
            //Define BufferedInputStream to cooperate with buffer download
            BufferedInputStream bis = null;
            try {
            	//Instantiate fis
                fis = new FileInputStream(file);
                //Instantiate bis
                bis = new BufferedInputStream(fis);
                //Get response stream
                OutputStream os = response.getOutputStream();
                //Start reading data
                int i = bis.read(buffer);
                //When it's not at the end of the file
                while (i != -1) {
                	//Write response stream
                    os.write(buffer, 0, i);
                    //Keep reading
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally { 
                if (bis != null) {
                    try {
                    	// Close operation, release resources
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                    	// Close operation, release resources
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
