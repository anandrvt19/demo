package com.demo.imagesharing.Exception;

public class ImageSharingSystemException extends Exception {

	private static final long serialVersionUID = 8476697327430836813L;

	public ImageSharingSystemException(String message) {
		super(message);
	}

	public ImageSharingSystemException(String message, Exception e) {
		super(message, e);
	}
}
