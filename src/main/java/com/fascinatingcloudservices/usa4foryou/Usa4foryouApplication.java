package com.fascinatingcloudservices.usa4foryou;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Usa4foryouApplication {

		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
		}

	public static void main(String[] args) {
		SpringApplication.run(Usa4foryouApplication.class, args);
		System.out.println("hello there two");
	}

}
