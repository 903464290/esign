package com.mf.util;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.api.NamedStreamable.FileWrapper;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


//Classes dedicated to ipfs operations, including file upload and download
public class IPFSTools {
	//Create an ipfs instance, and specify the listening IP address and port
	
    private static IPFS ipfsObj = new IPFS(FileUtils.readPropertiesFile("app.ipfs"));
    //File upload method
    //fileName: The name of the local file to be added
    public static String IPFS_Upload(String fileName) {
    	try{
	    	//Define FileWrapper object, used to add files to ipfs
	        FileWrapper file = new NamedStreamable.FileWrapper(new File(fileName));
	        //Call the add method to add a file, and at the same time get the newly added MerkleNode object of the file in ipfs
	        MerkleNode  merkleNode= ipfsObj.add(file).get(0);
	        //Get the hash value of MerkleNode and return
	        return merkleNode.hash.toString();
    	}catch(Exception ex){
    		return null;
    	}
    }

    //Download method
    //hash: Specify the hash in the ipfs to be downloaded
    //destFile: To download to the local file, it is equivalent to save as
    public static void IPFS_Download(String hash, String destFile) throws IOException {
    	//Define the byte stream to be saved
        byte[] fileByteStream = null;
    	//Call the cat method of ipfs to get the byte stream of the specified hash file
        fileByteStream = ipfsObj.cat(Multihash.fromBase58(hash));
        //Determine whether the byte stream is empty, if it is not empty, start saving
        if (fileByteStream != null && fileByteStream.length > 0) {
        	//Create a file and specify destFile as the file name
            File downFile = new File(destFile);
            //If the file exists, delete the file first
            if (downFile.exists()) {
            	//Delete Files
                downFile.delete();
            }
            //Define the input stream, ready to download
            FileOutputStream fos = null;
            try {
              //Instantiate the FileOutputStream object through the file object
                fos = new FileOutputStream(downFile);
                //Write stream to file name
                fos.write(fileByteStream);
                //Persist the content of the written file
                fos.flush();
            } finally {
                try {
                	//Release memory
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
