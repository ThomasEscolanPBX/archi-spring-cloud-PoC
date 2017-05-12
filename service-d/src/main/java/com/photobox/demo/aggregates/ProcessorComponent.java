package com.photobox.demo.aggregates;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

@EnableBinding(Processor.class)
public class ProcessorComponent {

	@Transformer(inputChannel = Processor.INPUT,
				 outputChannel = Processor.OUTPUT)
	public String processMessage(String payload) {
		return payload + " is the time.";
	}
}