package com.bitsian.BitsianJavaClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BitsianJavaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitsianJavaClientApplication.class, args);
	}

}
