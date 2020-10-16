package com.company.unauthorizedDeliveries;

import com.company.unauthorizedDeliveries.controller.MainPageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UnauthorizedDeliveriesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UnauthorizedDeliveriesApplication.class, args);
	}

}
