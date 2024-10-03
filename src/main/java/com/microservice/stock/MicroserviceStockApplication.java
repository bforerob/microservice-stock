package com.microservice.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MicroserviceStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceStockApplication.class, args);
	}

}
