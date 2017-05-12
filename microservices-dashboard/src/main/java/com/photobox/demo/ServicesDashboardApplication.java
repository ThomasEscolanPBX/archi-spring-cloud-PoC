package com.photobox.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import be.ordina.msdashboard.EnableMicroservicesDashboardServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMicroservicesDashboardServer
public class ServicesDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicesDashboardApplication.class, args);
	}
}
