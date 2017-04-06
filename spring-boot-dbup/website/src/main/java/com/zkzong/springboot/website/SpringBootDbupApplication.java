package com.zkzong.springboot.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.zkzong.springboot")
public class SpringBootDbupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDbupApplication.class, args);
	}
}
