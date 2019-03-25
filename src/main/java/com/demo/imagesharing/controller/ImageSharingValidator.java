package com.demo.imagesharing.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.demo.imagesharing.Exception.ImageSharingBusinessException;

public class ImageSharingValidator {

	public static void isUploadedFileValid(MultipartFile uploadedFile) throws ImageSharingBusinessException {
		if (StringUtils.isEmpty(uploadedFile)) {
			throw new ImageSharingBusinessException("UploadedFile cannot be null");
		}
		if (StringUtils.isEmpty(uploadedFile.getContentType())) {
			throw new ImageSharingBusinessException("UploadedFile content type cannot be null");
		}
		if (StringUtils.isEmpty(uploadedFile.getName())) {
			throw new ImageSharingBusinessException("UploadedFile name  cannot be null");
		}
	}

	public static void isImageUrlValid(String url) throws ImageSharingBusinessException {
		if (StringUtils.isEmpty(url)) {
			throw new ImageSharingBusinessException("Image URL cant be null");
		}
	}

	public static void isPhotoIdValid(Integer photoId) throws ImageSharingBusinessException {
		if (photoId == null) {
			throw new ImageSharingBusinessException("Photo id cant be null");
		}
	}
}
