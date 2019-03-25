package com.demo.imagesharing.model;

import org.springframework.web.multipart.MultipartFile;

public class ImageDetails {

	private String uploadedUserId;
	private Integer photoId;
	private String title;
	private String contentType;
	private String locationUrl;
	private String canonicalUrl;
	private long uploadedTimestamp;
	private MultipartFile uploadedFile;
	private Integer shardZone;

	public String getUploadedUserId() {
		return uploadedUserId;
	}

	public void setUploadedUserId(String uploadedUserId) {
		this.uploadedUserId = uploadedUserId;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getLocationUrl() {
		return locationUrl;
	}

	public void setLocationUrl(String locationUrl) {
		this.locationUrl = locationUrl;
	}

	public String getCanonicalUrl() {
		return canonicalUrl;
	}

	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}

	public long getUploadedTimestamp() {
		return uploadedTimestamp;
	}

	public void setUploadedTimestamp(long uploadedTimestamp) {
		this.uploadedTimestamp = uploadedTimestamp;
	}

	public MultipartFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(MultipartFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Integer getShardZone() {
		return shardZone;
	}

	public void setShardZone(Integer shardZone) {
		this.shardZone = shardZone;
	}

}
