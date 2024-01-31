package com.lorang.mif.servicenow.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.lorang.mif.servicenow.*", "com.lorang.mif.collibra.*" })
public class ServicenowIndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicenowIndApplication.class, args);
	}

}
