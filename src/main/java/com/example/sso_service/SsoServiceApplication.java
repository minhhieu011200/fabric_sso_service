package com.example.sso_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SsoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoServiceApplication.class, args);
	}

}
