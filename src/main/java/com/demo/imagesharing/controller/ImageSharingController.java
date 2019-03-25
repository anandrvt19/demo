package com.demo.imagesharing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.imagesharing.Exception.ImageSharingBusinessException;
import com.demo.imagesharing.model.ImageDetails;
import com.demo.imagesharing.service.ImageSharingService;
import com.demo.imagesharing.service.PhotoLookupCacheService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/v1/imagesharing")
public class ImageSharingController {

	private static final Logger log = LoggerFactory.getLogger(ImageSharingController.class);

	private ImageSharingService imageSharingService = null;
	private PhotoLookupCacheService photoLookupCacheService = null;
	private Gson gson = new Gson();

	@Autowired
	public ImageSharingController(ImageSharingService imageSharingService,
			PhotoLookupCacheService photoLookupCacheService) {
		this.imageSharingService = imageSharingService;
		this.photoLookupCacheService = photoLookupCacheService;
	}

	/**
	 * Api to check health of application
	 * 
	 * @return
	 */
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public Object checkHealth() {
		log.info("In ImageSharingController, checking health");
		return ResponseEntity.ok("Healthy");
	}

	/**
	 * This api used to upload the image.Upon uploading, it returns the metadata
	 * of the uploaded image with photo url
	 * 
	 * @param httpRequest
	 * @param uploadedFile
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity uploadImage(HttpServletRequest httpRequest, @RequestBody MultipartFile uploadedFile) {
		String errorMessage = null;
		try {
			ImageSharingValidator.isUploadedFileValid(uploadedFile);

			ImageDetails imageDetails = new ImageDetails();
			imageDetails.setContentType(uploadedFile.getContentType());
			imageDetails.setTitle(uploadedFile.getName());
			imageDetails.setUploadedTimestamp(System.currentTimeMillis());
			imageDetails.setUploadedFile(uploadedFile);
			imageDetails.setUploadedUserId(httpRequest.getHeader("userId"));

			imageSharingService.uploadImage(imageDetails);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(imageDetails);
		} catch (ImageSharingBusinessException be) {
			log.error("Exception while uploading Image ", be);
			errorMessage = be.getMessage();
		} catch (Exception e) {
			log.error("Exception while uploading Image ", e);
			errorMessage = "Unable to upload Image, please check and try again!!";

		}
		return ResponseEntity.badRequest().body(errorMessage);
	}

	/**
	 * This api gets all the imagedetails matching pagination..This will return only metadata of the image,
	 * not the image itself.. UI has to fire all url aync to load the image, to minimize IO operation.
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	@RequestMapping(value = "/getAllImages", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getAllImages(Integer startIndex, Integer endIndex) {
		List<ImageDetails> imageDetails;
		try {
			imageDetails = imageSharingService.getAllImages(startIndex, endIndex);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(imageDetails);

		} catch (Exception e) {
			log.error("Exception while processing getAllImages ", e);
		}
		return ResponseEntity.badRequest().body("Unable to process request, please check and try again!!");

	}

	/**
	 * After reponse of getAllImages is rendered, this api  will retrieve image for those url based on proximity and cdn.
	 * @param photoURL
	 * @return
	 */
	@RequestMapping(value = "/getImageByPhotoURL", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity getImageByPhotoId(String photoURL) {
		String errorMessage = null;

		try {
			ImageSharingValidator.isImageUrlValid(photoURL);

			byte[] imageInByte = imageSharingService.getImageByPhotoURL(photoURL);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageInByte);
		} catch (ImageSharingBusinessException be) {
			log.error("Exception while getImageByPhotoURL  ", be);
			errorMessage = be.getMessage();
		} catch (Exception e) {
			log.error("Exception while processing getImageByPhotoURL ", e);
			errorMessage = "Invalid URL, please check and try again!!";
		}
		return ResponseEntity.badRequest().body(errorMessage);
	}

	/**
	 * Api to retrieve phOto by id
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value = "/getImageMetaDataByPhotoId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getImageMetaDataByPhotoId(Integer photoId) {
		String errorMessage = null;

		try {
			ImageSharingValidator.isPhotoIdValid(photoId);
			ImageDetails imageDetails = photoLookupCacheService.getMetaDataDetailByPhotoId(photoId);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(imageDetails);
		} catch (ImageSharingBusinessException be) {
			log.error("Exception while getImageMetaDataByPhotoId  ", be);
			errorMessage = be.getMessage();
		} catch (Exception e) {
			log.error("Exception while processing getImageMetaDataByPhotoId ", e);
			errorMessage = "Unable to process request, please check and try again!!";
		}
		return ResponseEntity.badRequest().body(errorMessage);
	}

}