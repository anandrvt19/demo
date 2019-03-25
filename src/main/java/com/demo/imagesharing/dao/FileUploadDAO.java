package com.demo.imagesharing.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is mimic of image storage service
 * @author aramu
 *
 */
@Component
public class FileUploadDAO {

	public FileUploadDAO() {

	}

	/**
	 * Uploads the image
	 * @param uploadedFile
	 * @return
	 */
	public String uploadImage(MultipartFile uploadedFile) {
		String fileUrl = null;
		try {
			byte[] bytes = uploadedFile.getBytes();
			fileUrl = "/Users/aramu/Documents/imagesharing/" + uploadedFile.getOriginalFilename();
			Path path = Paths.get(fileUrl);
			Files.write(path, bytes);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileUrl;
	}
	
   /**
    * When ui fires the url, based on proximity and cdn , this retrieves the image.
    * @param photoURL
    * @return
    * @throws IOException
    */
	public byte[] getImageByPhotoURL(String photoURL) throws IOException {
		FileSystemResource fsr = new FileSystemResource(photoURL);
		byte[] bytes = StreamUtils.copyToByteArray(fsr.getInputStream());
		return bytes;
	}

}
