package com.demo.imagesharing.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.demo.imagesharing.Exception.ImageSharingBusinessException;
import com.demo.imagesharing.dao.FileUploadDAO;
import com.demo.imagesharing.dao.MetaDataManagementDAO;
import com.demo.imagesharing.model.ImageDetails;

@Component
public class ImageSharingService {

	private FileUploadDAO fileUploadDAO = null;
	private MetaDataManagementDAO metaDataManagementDAO = null;

	public ImageSharingService(MetaDataManagementDAO metaDataManagementDAO, FileUploadDAO fileUploadDAO) {
		this.metaDataManagementDAO = metaDataManagementDAO;
		this.fileUploadDAO = fileUploadDAO;
	}

	public ImageDetails uploadImage(ImageDetails imageDetails) {
		Integer uniquePhotoId = metaDataManagementDAO.getUniquePhotoId();
		Integer shardZone = metaDataManagementDAO.getShardZone(uniquePhotoId);
		String fileUrl = fileUploadDAO.uploadImage(imageDetails.getUploadedFile());
		imageDetails.setUploadedFile(null);
		imageDetails.setCanonicalUrl(metaDataManagementDAO.findCanonicalURl(fileUrl));
		imageDetails.setPhotoId(uniquePhotoId);
		imageDetails.setLocationUrl(fileUrl);
		imageDetails.setShardZone(shardZone);
		metaDataManagementDAO.storeImageMetaData(imageDetails);
		return imageDetails;
	}

	public byte[] getImageByPhotoURL(String photoURL) throws IOException {
		return fileUploadDAO.getImageByPhotoURL(photoURL);
	}

	public List<ImageDetails> getAllImages(Integer start, Integer end) {
		return metaDataManagementDAO.getAllImages(start, end);
	}

	public ImageDetails getMetaDataDetailByPhotoId(Integer photoId) throws ImageSharingBusinessException {
		return metaDataManagementDAO.getMetaDataDetailByPhotoId(photoId);
	}

}
