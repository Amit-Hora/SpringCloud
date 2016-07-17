package com.ash.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //or @EnableDiscoveryClient

public class EurekaPocAddService {

	public static void main(String[] args) {
		SpringApplication.run(EurekaPocAddService.class, args);
	}
}
