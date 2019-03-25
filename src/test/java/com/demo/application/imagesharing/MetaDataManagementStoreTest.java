package com.demo.application.imagesharing;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.demo.imagesharing.Exception.ImageSharingBusinessException;
import com.demo.imagesharing.dao.MetaDataManagementDAO;
import com.demo.imagesharing.model.ImageDetails;

import junit.framework.Assert;

public class MetaDataManagementStoreTest {
	
	MetaDataManagementDAO dao = null;

	@Before
	public void setUp() throws Exception {
		this.dao = new MetaDataManagementDAO();
	}

	@Test
	public void storeImageMetaDataTest() {
		ImageDetails uploadRequestDetails = new ImageDetails();
		uploadRequestDetails.setPhotoId(100);
		uploadRequestDetails.setShardZone(dao.getShardZone(100));
		uploadRequestDetails.setUploadedUserId("1234");
		dao.storeImageMetaData(uploadRequestDetails);
		try {
			Assert.assertEquals("1234", dao.getMetaDataDetailByPhotoId(100).getUploadedUserId());
		} catch (ImageSharingBusinessException e) {
			Assert.fail(e.getMessage());
		}
	}
}
