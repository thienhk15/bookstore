package com.thien.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AppApplication {
	//testt
	public static void main(String[] args) {
		//test
		SpringApplication.run(AppApplication.class, args);
	}

}
