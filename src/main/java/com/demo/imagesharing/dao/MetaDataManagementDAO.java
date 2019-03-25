package com.demo.imagesharing.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.imagesharing.Exception.ImageSharingBusinessException;
import com.demo.imagesharing.model.ImageDetails;
/**
 * This is mimic of distributed meta store with sharding, canonical url service and phot id generator.
 * @author aramu
 *
 */
@Component
public class MetaDataManagementDAO {

	private static final Logger log = LoggerFactory.getLogger(MetaDataManagementDAO.class);

	Map<Integer, Map<Integer, ImageDetails>> metaDataStore = new ConcurrentHashMap<>();

	private AtomicInteger generator = new AtomicInteger();

	public MetaDataManagementDAO() {

	}

	/**
	 * This is mimic of oracle id generator, which generates unique id for every call
	 * @return Unique id
	 */
	public synchronized Integer getUniquePhotoId() {
		return generator.incrementAndGet();
	}

	/**
	 * This is mimic of hashing and shard finding. Due to high volume, its better to to store the details in sharding..
	 * @return Shard id
	 */
	public Integer getShardZone(Integer photoId) {
		return photoId % 10;
	}

	/**
	 * This is mimic of matching images and finding is there already a uploaded pic and then mark the url as canonical
	 * @return Shard id
	 */	public String findCanonicalURl(String url) {
		return null;
	}

	 /**
	  * This method store the meta data details per sharding zone, to have a well distributed data
	  * @param uploadRequestDetails
	  */
	public void storeImageMetaData(ImageDetails uploadRequestDetails) {
		Map<Integer, ImageDetails> metaDataStoreForShardZone = metaDataStore.get(uploadRequestDetails.getShardZone());
		if (metaDataStoreForShardZone == null) {
			metaDataStoreForShardZone = new ConcurrentHashMap<>();
			metaDataStore.put(uploadRequestDetails.getShardZone(), metaDataStoreForShardZone);
		}
		metaDataStoreForShardZone.put(uploadRequestDetails.getPhotoId(), uploadRequestDetails);
	}

	/**
	 * Gets the metadata by photo id, by first finding the shard zone and retrieve it from there.
	 * @param photoId
	 * @return
	 * @throws ImageSharingBusinessException
	 */
	public ImageDetails getMetaDataDetailByPhotoId(Integer photoId) throws ImageSharingBusinessException {
		log.info(String.format("Going to find Photoid %d in DAO.", photoId));

		Integer shardZone = getShardZone(photoId);
		Map<Integer, ImageDetails> metaDataStoreByShard = metaDataStore.get(shardZone);
		if (metaDataStoreByShard == null || metaDataStoreByShard.get(photoId) == null) {
			throw new ImageSharingBusinessException("Photo not found!.");
		}
		return metaDataStoreByShard.get(photoId);
	}

	/**
	 * This gets all the images to render.
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ImageDetails> getAllImages(Integer start, Integer end) {
		List<ImageDetails> allImageDetails = new ArrayList<>();
		for (Entry<Integer, Map<Integer, ImageDetails>> shardZoneEntry : metaDataStore.entrySet()) {
			for (Entry<Integer, ImageDetails> imageDetailEntry : shardZoneEntry.getValue().entrySet()) {
				allImageDetails.add(imageDetailEntry.getValue());
			}
		}
		if (allImageDetails.size() > start && allImageDetails.size() > end) {
			allImageDetails.subList(start, end);
		}
		return allImageDetails;
	}

}
