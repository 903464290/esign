package com.mf.util;

 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mf.bean.IPfsFile;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFSFile;

//Tools for operating MongoDB
public class MongoDBUtil  {
	//Read the configuration file and get the IP address
	private final static String MONGO_IP=FileUtils.readPropertiesFile("spring.data.mongodb.uri").split("//")[1].split(":")[0];
	//Read the configuration file, get the database
	private final static String MONGO_DATABASE=FileUtils.readPropertiesFile("spring.data.mongodb.uri").split("//")[1].split("/")[1];
	private final static String MONGO_COLLECTION="fileInfo";
	//Connect to mongodb service
	private static MongoClient mongoClient = new MongoClient(MONGO_IP, 27017);
	//Connect to the database
	private static  MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGO_DATABASE);
	//Get collection
	private static  MongoCollection<Document> collection = mongoDatabase.getCollection(MONGO_COLLECTION);
	
	//Add an IPfsFile file to mongodb
	 public static void insertIPFS(IPfsFile file){
		  //Construct the data Document object to be inserted
		    Document document = new Document("fileName",file.getFilename())
		    		.append("hash", file.getHash())
		    		.append("md5", file.getMd5())
		    		.append("mongodbId", file.getMongodbId())
		    		.append("createDtm", MongoDBUtil.formatDateTime(file.getFilename()));
			 //Construct the data Document object to be inserted
		    collection.insertOne(document);
	 }
	 
		//Query all file records saved by mongodb
	 public static  List<IPfsFile> query(){
		 //Define collection variables
		 List<IPfsFile> result=new ArrayList<IPfsFile>();
		 //Query all data in mongo
		 FindIterable findIterable = collection.find();
		 //Start looping to get each ipfs
		 MongoCursor  cursor = findIterable.iterator();
		 //If there is data
		    while (cursor.hasNext()) {
		    	//Take out each document object
		    	Document document = (Document)cursor.next();
		    	//Create an IPfsFile object
		    	IPfsFile obj=new IPfsFile();
		    	//Set a value
		    	obj.setFilename(document.getString("fileName"));
		    	obj.setMd5(document.getString("md5"));
		    	obj.setHash(document.getString("hash"));
		    	obj.setCreateDtm(document.getString("createDtm"));
		    	obj.setMongodbId(document.getString("mongodbId"));
		        //Add to collection
		    	result.add(obj);
		    }
		    //Return result
		    return result;
	 }	 
	 
	  //Upload local files to mongodb and return the corresponding id
	   public static  String upload(GridFsTemplate gridFsTemplate,String fileName){
		   try{
		   //Create File object
		   File upLoadfile = new File(fileName);
		   //Use upLoadfile object to create fileInputStream object
	        FileInputStream fileInputStream = new FileInputStream(upLoadfile);
	        GridFSFile  gridFSFile=gridFsTemplate.store(fileInputStream, fileName);
	        return gridFSFile.getId().toString();
		   }catch(Exception e){
			   e.printStackTrace();
			   return null;
		   }
	    }
	   
	   //Get the creation date of the file and convert the FileTime format into a readable format
	   public static String formatDateTime(String filePath) {
			File file=new File(filePath);
			BasicFileAttributes bAttributes = null;
			try {
						bAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
						FileTime fileTime=bAttributes.lastModifiedTime();
						System.out.println("fileTime:"+fileTime.toString());
					   LocalDateTime localDateTime = fileTime
							   .toInstant()
							   .atZone(ZoneId.systemDefault())
							   .toLocalDateTime();
							   return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			} catch (IOException e) {
			    e.printStackTrace();
			    return null;
			}
   }
	   
}
