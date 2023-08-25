package com.randps.randomdefence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
public class RandomdefenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandomdefenceApplication.class, args);
	}

}
