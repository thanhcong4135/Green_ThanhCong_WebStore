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

//		exposeDirectory(AppConstant.PROFILE_PHOTO_DIR, registry);
//		exposeDirectory(AppConstant.PRODUCT_PHOTO_DIR, registry);
//		exposeDirectory(AppConstant.CUSTOMER_PHOTO_DIR, registry);
//		exposeDirectory(AppConstant.CATEGORY_PHOTO_DIR, registry);
		
		Path productUploadDir = Paths.get("./product-photos");
		Path userUploadDir = Paths.get("./profile-photos");
		
		String productUploadPath = productUploadDir.toFile().getAbsolutePath();
		String userUploadPath = userUploadDir.toFile().getAbsolutePath();
		
		
		registry.addResourceHandler("/product-photos/**").addResourceLocations("file:/" + productUploadPath + "/");
		registry.addResourceHandler("/profile-photos/**").addResourceLocations("file:/" + userUploadPath + "/");
	}
	
//	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
//		Path uploadDir = Paths.get(dirName);
//		String uploadPath = uploadDir.toFile().getAbsolutePath();
//		
//		registry.addResourceHandler("/profile-photos/**").addResourceLocations("file:/" + uploadPath + "/");
//
//	}

}
