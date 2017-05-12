package com.photobox.demo;

import java.util.WeakHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-a", fallback = ServiceClient.Fallback.class)
public interface ServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/hello_a")
	String getHello(@RequestParam("name") String name);

	@Component
	class Fallback implements ServiceClient {
		private final WeakHashMap<String, String> cache = new WeakHashMap<String, String>();
		public WeakHashMap<String, String> getCache() {
			return cache;
		}
		@Override
		public String getHello(String name) {
			return cache.getOrDefault(name, "fallback");
		}
	}

	@Component
	@Aspect
	class FallbackCache {
		@Autowired
		private Fallback fallback;

		@Around("execution(public * com.photobox.demo.ServiceClient.getHello(..))")
		public Object cacheMessages(ProceedingJoinPoint pjp) throws Throwable {
			Object result = pjp.proceed();
			fallback.getCache().put((String) pjp.getArgs()[0], result + " (fallback)");
			return result;
		}
	}
}
