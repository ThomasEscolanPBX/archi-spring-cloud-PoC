package com.photobox.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.TraceRunnable;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Value("${com.photobox.demo.message}")
	private String hello;

	@Value("${server.port}")
	private int port;

	private final AtomicInteger counter = new AtomicInteger();

	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	@Autowired
	private Tracer tracer;
	@Autowired
	private SpanNamer namer;

	@GetMapping("/hello_a")
	public String get(@RequestParam String name) throws InterruptedException, ExecutionException {
		Future process = EXECUTOR.submit(new TraceRunnable(tracer, namer, new SomethingCallable() {
			@Override
			public String toString() {
				return "custom_span";
			}
		}));
		Thread.sleep(200);	// more lost time
		process.get();
		return hello + " " + name + "! I'm A(" + counter.incrementAndGet() + ") on port " + port;
	}

	private class SomethingCallable implements Runnable {
		@Override
		public void run() {
			try {
				tracer.addTag("business_transaction", "audience");
				tracer.getCurrentSpan() .logEvent("waiting");
				Thread.sleep(400);
				tracer.getCurrentSpan() .logEvent("sleeping");
				Thread.sleep(400);
				tracer.getCurrentSpan() .logEvent("waking_up");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
