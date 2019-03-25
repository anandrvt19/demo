package com.demo.imagesharing.Exception;

public class ImageSharingBusinessException extends Exception {

	private static final long serialVersionUID = 8476697327430836813L;

	public ImageSharingBusinessException(String message) {
		super(message);
	}

	public ImageSharingBusinessException(String message, Exception e) {
		super(message, e);
	}
}
