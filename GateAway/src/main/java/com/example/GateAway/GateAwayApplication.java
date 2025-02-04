package com.example.GateAway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GateAwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateAwayApplication.class, args);
	}

}
