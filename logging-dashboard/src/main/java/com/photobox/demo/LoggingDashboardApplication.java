package com.photobox.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class LoggingDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggingDashboardApplication.class, args);
	}
}