package com.green.webstoreadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan({"com.green.webstoremodels"})
public class WebStoreAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebStoreAdminApplication.class, args);
	}

}
