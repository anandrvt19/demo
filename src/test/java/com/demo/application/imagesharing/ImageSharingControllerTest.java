package com.demo.application.imagesharing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.demo.imagesharing.controller.ImageSharingController;
import com.demo.imagesharing.dao.MetaDataManagementDAO;
import com.demo.imagesharing.model.ImageDetails;
import com.demo.imagesharing.service.ImageSharingService;
import com.demo.imagesharing.service.PhotoLookupCacheService;

import junit.framework.Assert;

public class ImageSharingControllerTest {

	ImageSharingController controller = null;
	ImageSharingService mockSharingService = Mockito.mock(ImageSharingService.class);
	PhotoLookupCacheService photoLookupCacheService = Mockito.mock(PhotoLookupCacheService.class);

	@Before
	public void setUp() throws Exception {
		this.controller = new ImageSharingController(mockSharingService, photoLookupCacheService);
	}

	@Test
	public void uploadImageNullTest() {
		ResponseEntity response = controller.uploadImage(Mockito.mock(HttpServletRequest.class), null);
		Assert.assertEquals(400, response.getStatusCodeValue());
	}

	@Test
	public void getImageMetaDataByPhotoIdTest() {
		ImageDetails imageDetails = new ImageDetails();
		imageDetails.setPhotoId(1);
		imageDetails.setShardZone(1);
		imageDetails.setLocationUrl("dummyurl");

		Mockito.when(photoLookupCacheService.getMetaDataDetailByPhotoId(1)).thenReturn(imageDetails);
		ResponseEntity responseEntity = controller.getImageMetaDataByPhotoId(1);

		ImageDetails imageResponse = (ImageDetails) responseEntity.getBody();
		Assert.assertEquals(new Integer(1), imageResponse.getPhotoId());
	}

	@Test
	public void getAllImagesEmptyTest() {

		ImageSharingController controller = new ImageSharingController(
				new ImageSharingService(new MetaDataManagementDAO(), null), null);

		ResponseEntity responseEntity = controller.getAllImages(100, 200);

		List<ImageDetails> details = (List<ImageDetails>) responseEntity.getBody();
		Assert.assertEquals(0, details.size());

	}

}
