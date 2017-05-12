package com.photobox.demo.aggregates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

@EnableBinding(Sink.class)
public class SinkComponent {
	
	private final Logger logger = LoggerFactory.getLogger(SinkComponent.class);

	@ServiceActivator(inputChannel = Sink.INPUT)
	public void loggerSink(Object payload) {
		logger.warn("Received: " + payload);
	}
}