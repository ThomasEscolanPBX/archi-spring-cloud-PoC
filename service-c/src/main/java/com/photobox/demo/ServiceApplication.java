package com.photobox.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RestController
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Autowired
	private ServiceClient client;

	@GetMapping("/hello_c")
	String get(@RequestParam String name) {
		return "Hello, HAL. Do you read me, HAL?.. "
			+ client.getHelloFailure(name);
	}

	@Service
	class ServiceClient {
		@HystrixCommand(fallbackMethod = "getHelloFallback")
		public String getHelloFailure(String name) {
			throw new RuntimeException(String.format(
				"Stop, %s. Will you stop %s? Stop, %s...", name));
		}

		protected String getHelloFallback(String name) {
			return "Affirmative, " + name + ". I read you.";
		}
	}
}
