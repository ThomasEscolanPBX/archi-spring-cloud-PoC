package com.photobox.demo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.event.HelloRemoteApplicationEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EventListener
	public void receiveMessage(HelloRemoteApplicationEvent msg) {
		logger.warn("HELLO " + msg.getLabel());
	}

	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private ApplicationContext context;

	@GetMapping("/send")
	public void sendMessage() {
		Map<String, String> properties = new HashMap<>();
		properties.put("foo", "bar");
		publisher.publishEvent(new HelloRemoteApplicationEvent(this, context.getId(), "service-d:**", "foobar"));
	}
}
