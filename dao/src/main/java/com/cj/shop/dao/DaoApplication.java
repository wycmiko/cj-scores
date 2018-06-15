package com.cj.shop.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cj.shop.*")
public class DaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaoApplication.class, args);
	}
}
