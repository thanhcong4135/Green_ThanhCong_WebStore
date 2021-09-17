package com.green.webstoreadmin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.green.webstoreadmin.handler.AppConstant;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		exposeDirectory(AppConstant.PROFILE_PHOTO_DIR, registry);
//		exposeDirectory(AppConstant.PRODUCT_PHOTO_DIR, registry);
		exposeDirectory(AppConstant.CUSTOMER_PHOTO_DIR, registry);
		exposeDirectory(AppConstant.CATEGORY_PHOTO_DIR, registry);
		
		Path userUploadDir = Paths.get("./product-photos");
		String userUploadPath = userUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/product-photos/**").addResourceLocations("file:/" + userUploadPath + "/");
	}
	
	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);
//		String uploadPath = uploadDir.toFile().getAbsolutePath();
		String uploadPath2 = "";
		try {
			uploadPath2 = uploadDir.toFile().getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("MvcConfig :: " + uploadPath);
//		System.out.println("MvcConfig :: " + dirName);
		
		if(dirName.startsWith("../")) {
			dirName.replace("../", "");
		}
		
		System.out.println("------------------- " + uploadPath2);
		
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath2 + "/");
	}

}
