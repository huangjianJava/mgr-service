package com.advance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;


@SpringBootApplication
@EnableRetry
public class MgrServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgrServiceApplication.class, args);
	}
}
