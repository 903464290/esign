package com.mf.controller;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mf.bean.IPfsFile;
import com.mf.bean.ResultObject;
import com.mf.util.Constant;
import com.mf.util.FileUtils;
import com.mf.util.IPFSTools;
import com.mf.util.MongoDBUtil;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *Controller
 */
@RequestMapping("/list")
@RestController
public class ListController {

    @Autowired
    private GridFsTemplate gridFsTemplate;
       
    
    
    
    /**
     * Get a list of all successfully uploaded files from mongodb
     */
    @RequestMapping("/getAllUploadFiles") //Identify the requested address
    public ResultObject<List<IPfsFile>> getAllUploadFiles(@RequestParam("limit") int limit, @RequestParam("page") int page) {
    	//Call the mongo method to get a list of all uploaded files
    	List<IPfsFile> result=MongoDBUtil.query();
    	//Create Page class
    	Page pageObj = new Page(page, limit);
    	//为Page类中的total属性赋值
    	int total = result.size();
    	pageObj.setTotal(total);
    	//Calculate the initial value of the data index that needs to be displayed
    	int startIndex = (page - 1) * limit;
    	int endIndex = (startIndex+limit)>total?total: (startIndex+limit);
    	System.out.println("startInde="+startIndex+" endIndex="+endIndex);
    	//Intercept the sub-linked list that needs to be displayed from the linked list and add it to Page
    	pageObj.addAll(result.subList(startIndex,endIndex));
    	//Create PageInfo with Page
    	PageInfo pageInfo = new PageInfo<>(pageObj);
        // 4.Return result
        ResultObject<List<IPfsFile>> rs = new ResultObject<>();
        rs.setCode(Constant.SUCCESS_RETUEN_CODE);
        rs.setMsg("Search successful");
        rs.setData(pageInfo.getList());
        rs.setCount(pageInfo.getTotal());
        return rs;
    }

    
    
    /**
     * Get a list of all local files that have not been uploaded
     */
    @RequestMapping("/getAllPreUploadFiles") //Identify the requested address
    public ResultObject<List<IPfsFile>> getAllPreUploadFiles(@RequestParam("limit") int limit, @RequestParam("page") int page) {
    	System.out.println("limit="+limit+" page= "+page);
        // 1.Get the scanned directory
    	String searchDir=FileUtils.readPropertiesFile("app.searchDir");
    	//Get all file information, including the value of file name and md5
    	List<IPfsFile> localFilelist=new ArrayList<IPfsFile>();
    	FileUtils.getAllFilesMd5(new File(searchDir), localFilelist);
    	//Get a list of all uploaded files in mongo
    	List<IPfsFile> mongoFilelist=MongoDBUtil.query();
    	//Check whether the file is uploaded through MD5 comparison
    	List<IPfsFile> result= new ArrayList<IPfsFile>();
    	//Cycle comparison
    	for(IPfsFile  localFilelistItem:localFilelist){
    		 //Take out the md5 value
    		String md5=localFilelistItem.getMd5();
    		//Set the flag to see if it exists
    		 boolean isExists=false;
    		//cycling
    		for(IPfsFile  mongoFilelistItem:mongoFilelist){
    			 if(md5.equals(mongoFilelistItem.getMd5())){
    				  //If they are equal, make the flag true
    				 isExists=true;
						 //Exit the loop at the same time
    				 break;
    			 }
    		}
    		//After the loop exits, check the flag bit
    		 if(!isExists){
    			 //Find a target file
    			 result.add(localFilelistItem);
    		 }
    	}
    	
    	//Create Page class
    	Page pageObj = new Page(page, limit);
    	//Assign a value to the total attribute in the Page class
    	int total = result.size();
    	pageObj.setTotal(total);
    	//Calculate the initial value of the data index that needs to be displayed
    	int startIndex = (page - 1) * limit;
    	int endIndex = (startIndex+limit)>total?total: (startIndex+limit);
    	System.out.println("startInde="+startIndex+" endIndex="+endIndex);
    	//Intercept the sub-linked list that needs to be displayed from the linked list and add it to Page
    	pageObj.addAll(result.subList(startIndex,endIndex));
    	//Create PageInfo with Page
    	PageInfo pageInfo = new PageInfo<>(pageObj);
        // 4.Return result
        ResultObject<List<IPfsFile>> rs = new ResultObject<>();
        rs.setCode(Constant.SUCCESS_RETUEN_CODE);
        rs.setMsg("search successful");
        rs.setData(pageInfo.getList());
        rs.setCount(pageInfo.getTotal());
        return rs;
    }

    
    
    
    /**
     * Ready to upload the specified file
     */
    @RequestMapping("/UploadFile") //Identify the requested address
    public ResultObject<Object>   UploadFile(IPfsFile ipfsFile) {
    	//Get the file name to upload
    	String fileName=ipfsFile.getFilename();
    	//Start uploading to ifps
    	 String hash=IPFSTools.IPFS_Upload(fileName);
         ResultObject<Object> rs = new ResultObject<Object>();
    	 //Determine whether the upload is successful
    	 if (hash==null  ||  "".equals(hash)){
    		 //Indicates that the upload failed
	        // 4.Return result
	        rs.setCode(Constant.FAIL_RETUEN_CODE);
	        rs.setMsg("upload failed");
	        return rs;
    	 }
       //Start uploading mongodb
      String mongoId=MongoDBUtil.upload(gridFsTemplate, fileName);
    //Determine whether the upload is successful
 	 if (mongoId==null  ||  "".equals(mongoId)){
 		    //Indicates that the upload failed
	        rs.setCode(Constant.FAIL_RETUEN_CODE);
	        rs.setMsg("upload failed");
	        return rs;
 	 }
 	  //Insert data into mongo
 	ipfsFile.setMongodbId(mongoId);
 	ipfsFile.setHash(hash);	
 	MongoDBUtil.insertIPFS(ipfsFile);
 	 //Indicates that all uploads were successful
     rs.setCode(Constant.SUCCESS_RETUEN_CODE);
     rs.setMsg("Uploaded successful");
     return rs; 	 
   }
}
