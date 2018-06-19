package com.cj.shop.web;

import com.cj.shop.ucapi.cfg.ConsulProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

@EnableFeignClients
@EnableSwagger2
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

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("20MB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("20MB");
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
