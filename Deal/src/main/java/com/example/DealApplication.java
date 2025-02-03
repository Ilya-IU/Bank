package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableFeignClients
public class DealApplication {

	public static void main(String[] args) {

		SpringApplication.run(DealApplication.class, args);
	}

}
