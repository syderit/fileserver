package com.syder.itservices.filesmanager.model;

import java.util.Date;

public class File {
	private int id;
	private String originalname;
	private String uid;
	private Date uploadDate;
	private String location;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOriginalname() {
		return originalname;
	}
	public void setOriginalname(String originalname) {
		this.originalname = originalname;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("File [id=");
		builder.append(id);
		builder.append(", originalname=");
		builder.append(originalname);
		builder.append(", uid=");
		builder.append(uid);
		builder.append(", uploadDate=");
		builder.append(uploadDate);
		builder.append(", location=");
		builder.append(location);
		builder.append("]");
		return builder.toString();
	}
}
