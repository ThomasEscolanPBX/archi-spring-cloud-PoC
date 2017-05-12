package com.photobox.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryClientRunner implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiscoveryClient client;

	@Override
	public void run(String... args) throws Exception {
		client.getInstances("service-a").forEach((ServiceInstance s) -> {
			logger.debug(String.format("%s: %s", s.getServiceId(), s.getUri()));
		});
	}
}
