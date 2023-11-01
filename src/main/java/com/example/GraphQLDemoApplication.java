package com.example;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphQLDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphQLDemoApplication.class, args);
	}

	@Bean
	Faker faker(){
		return new Faker();
	}
}
