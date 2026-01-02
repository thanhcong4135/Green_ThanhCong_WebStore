package com.green.webstoreadmin.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadHelper {
	
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
			
			Path uploadPath = Paths.get(uploadDir);
	
			if(Files.notExists(uploadPath, LinkOption.NOFOLLOW_LINKS)) {
				Files.createDirectories(uploadPath);
			}
			
			InputStream inputStream = multipartFile.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("FileUploadHelper ::  uploadDir ::" + uploadDir);
			System.out.println("FileUploadHelper ::  fileName ::" + fileName);
			System.out.println("FileUploadHelper ::  uploadPath ::" + uploadPath);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}

}
