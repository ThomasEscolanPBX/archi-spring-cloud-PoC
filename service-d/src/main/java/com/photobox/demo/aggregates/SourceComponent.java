package com.photobox.demo.aggregates;

import static java.time.LocalDateTime.now;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@EnableBinding(Source.class)
public class SourceComponent {

	private final Logger logger = LoggerFactory.getLogger(SourceComponent.class);

	@Bean
	@InboundChannelAdapter(Source.OUTPUT)
	public MessageSource<String> createMessage() {
		return () -> {
			String payload = now().toString();
			logger.warn("Sent: " + payload);
			return new GenericMessage<>(payload);
		};
	}
}