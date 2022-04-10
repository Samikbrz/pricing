package com.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class PricingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PricingApplication.class, args);
	}

}
