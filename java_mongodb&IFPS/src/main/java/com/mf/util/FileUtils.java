package com.mf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.alibaba.fastjson.JSONObject;
import com.mf.bean.IPfsFile;

import io.ipfs.api.IPFS;

//Classes for file operations
public class FileUtils {

	
     /**
		* Read content by configuration file name
		* @param fileName
		* @return
		*/
	public static String readPropertiesFile(String key) {
	  try {
		         Resource resource = new ClassPathResource("application.properties");
		         Properties props = PropertiesLoaderUtils.loadProperties(resource);
		         return props.getProperty(key);
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
	  		return null;
	}


	//Get all file information under the specified path, including file name+md5
	public static void getAllFilesMd5(File file,List<IPfsFile> list){
		try{
			//Get all sub-files under the file
			File[] fs = file.listFiles();
			for(File f:fs){
				//If it is a directory, all files in the directory are recursively
				if(f.isDirectory())	
					 //If it is a directory, continue to call this method recursively to get
					getAllFilesMd5(f,list);
				if(f.isFile()){		
					//If it is a file, create a new IPfsFile object
					IPfsFile  ipf=new IPfsFile();
					//Specify file name
					ipf.setFilename(f.getAbsolutePath());
					//Specify the md5 value of the file
					ipf.setMd5(DigestUtils.md5Hex(new FileInputStream(f.getAbsolutePath())));
					ipf.setCreateDtm(MongoDBUtil.formatDateTime(f.getAbsolutePath()));
					//Add to list
					list.add(ipf);
			    }
			}
		}catch(Exception ex){
				ex.printStackTrace();
		}
	}
 
	
	//Get the MD5 value of all files under the specified path
	public List<String> getAllFilesMd5(String path){
		File file=new File(path);
	    return null;
		
	}
	
	//write to local file
	public static void writeIpfsFile(IPfsFile ipf){
		//Define FileWriter object, used to write txt file
		 FileWriter fw=null;
		 //Define a BufferedWriter object, which is used to cache and write txt files
		 BufferedWriter bw=null;
		try{
				 //Instantiate the FileWriter object and specify the file to be saved
				 fw=new FileWriter(new File("IPFS.txt"),true);	      
				//Instantiate a BufferedWriter object
		         bw = new BufferedWriter(fw); 	
		         //Convert the object to be written into json string format
			    String jonStr=JSONObject.toJSONString(ipf);
			    //Write an ipfs file to a local file
			    bw.write(jonStr+"\n");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				 //Release resources, close the bw object
			     bw.close();
			   //Release resources, close the fw object
			     fw.close();
			}catch(Exception e){}
		}		
	}

	//Read all IFPS files from the file
	public static List<IPfsFile> loadData(){
		//Define the FileReader object, ready to read the file
		 FileReader fr=null;
		//Define the BufferedReader object and prepare to read the file with FileReader
		 BufferedReader br=null;
		 //Define the List collection, use ArrayList to instantiate
		 List<IPfsFile> IPSList=new ArrayList<IPfsFile>();
			try{
				//Instantiate the fr object and specify the txt file to be read
				 fr=new FileReader("IPFS.txt");
				//Instantiate the br object
			     br=new BufferedReader(fr);
			     //Define the variable line, which represents each line read
			     String line="";			   
			     //Start reading in a loop
			    while ((line=br.readLine())!=null) {
			    	//Parse each line into an IPfsFile object
			    	IPfsFile ipfsObj=JSONObject.parseObject(line, IPfsFile.class);		
			    	//Add the ipfsObj object to the collection
			    	IPSList.add(ipfsObj);
			    }
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try{
					 //Release resources, close the br object
				    br.close();
					 //Release resources, close the fr object
				    fr.close();
				}catch(Exception e){}
			}
			return IPSList;
	}
	
	
	//Find the file by hash and return the IPfsFile object
	public static  IPfsFile findByHash(String hash){
		//Define the FileReader object, ready to read the file
		 FileReader fr=null;
		//Define the BufferedReader object and prepare to read the file with FileReader
		 BufferedReader br=null;
		 //Define the List collection, use ArrayList to instantiate
		 List<IPfsFile> IPSList=new ArrayList<IPfsFile>();
			try{
				//Instantiate the fr object and specify the txt file to be read
				 fr=new FileReader("IPFS.txt");
				//Instantiate the br object
			     br=new BufferedReader(fr);
			     //Define the variable line, which represents each line read
			     String line="";			   
			     //Start reading in a loop
			    while ((line=br.readLine())!=null) {
			    	//Parse each line into an IPfsFile object
			    	IPfsFile ipfsObj=JSONObject.parseObject(line, IPfsFile.class);		
			    	//Determine whether the hashes are equal
			    	 if(ipfsObj.getHash().equals(hash)){
			    		 //Return
			    		 return ipfsObj;
			    	 }
			    }
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}finally {
				try{
					 //Release resources, close the br object
				    br.close();
					 //Release resources, close the fr object
				    fr.close();
				}catch(Exception e){}
			}
			return null;
	}
	
}
