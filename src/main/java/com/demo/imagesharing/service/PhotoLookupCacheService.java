package com.demo.imagesharing.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.imagesharing.dao.MetaDataManagementDAO;
import com.demo.imagesharing.model.ImageDetails;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class PhotoLookupCacheService {

	private static final Logger log = LoggerFactory.getLogger(PhotoLookupCacheService.class);

	private MetaDataManagementDAO metaDataManagementDAO;

	PhotoLookupCacheService(MetaDataManagementDAO metaDataManagementDAO) {
		this.metaDataManagementDAO = metaDataManagementDAO;
	}

	LoadingCache<Integer, ImageDetails> imageMetaDetailCache = CacheBuilder.newBuilder().maximumSize(1000)
			.expireAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<Integer, ImageDetails>() {

				@Override
				public ImageDetails load(Integer key) throws Exception {
					log.info(String.format("Photoid %d not found in cache.", key));
					return metaDataManagementDAO.getMetaDataDetailByPhotoId(key);
				}
			});

	/**
	 * This a guava based cache to enhance the speed of retrival of metadata details by photoid
	 * @param photoId
	 * @return
	 */
	public ImageDetails getMetaDataDetailByPhotoId(Integer photoId) {
		try {
			return imageMetaDetailCache.get(photoId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
