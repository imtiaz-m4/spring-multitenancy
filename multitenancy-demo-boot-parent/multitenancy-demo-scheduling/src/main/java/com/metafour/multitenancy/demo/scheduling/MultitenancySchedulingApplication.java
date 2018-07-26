package com.metafour.multitenancy.demo.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.metafour.multitenancy.demo.scheduling", "com.metafour.multitenancy.config" })
public class MultitenancySchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultitenancySchedulingApplication.class, args);
	}
}
