package com.cj.scores.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "com.cj")
public class WebApplication {
	/**
	 * for uc-api config
	 * @return
	 */
	@Bean
	public ConsulProperties getConsulProper() {
		return new ConsulProperties();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
