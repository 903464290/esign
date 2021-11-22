package com.mf.bean;

/**
 * File
 */
public class IPfsFile {
	/**
     * File hash code
     */
    private String hash;
	/**
     * file name
     */
    private String filename;
    //File md5 value
    private String md5;

	private String mongodbId;
	private String  createDtm;
    
    public String getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(String createDtm) {
		this.createDtm = createDtm;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
    public String getMongodbId() {
		return mongodbId;
	}

	public void setMongodbId(String mongodbId) {
		this.mongodbId = mongodbId;
	}

	
	
    public IPfsFile() {
    	
    }

    public IPfsFile(String hash, String filename) {
 		this.hash = hash;
 		this.filename = filename;
 	}
 
}
