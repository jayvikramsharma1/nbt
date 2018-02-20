package com.jvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class UlApplication {

	public static void main(String[] args) {
		SpringApplication.run(UlApplication.class, args);
	}
}
