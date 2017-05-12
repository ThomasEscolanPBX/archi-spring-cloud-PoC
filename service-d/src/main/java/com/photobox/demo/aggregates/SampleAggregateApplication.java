package com.photobox.demo.aggregates;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;

@SpringBootApplication
public class SampleAggregateApplication {

	public static void main(String[] args) {
		new AggregateApplicationBuilder().web(false)
			.from(SourceComponent.class).args("--fixedDelay=5000")
			.via(ProcessorComponent.class)
			.to(SinkComponent.class)
		.run(args);
	}
}