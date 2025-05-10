package com.alkemy.java2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@Slf4j
public class Java22025Application {


	public static void main(String[] args) {
		SpringApplication.run(Java22025Application.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args ->
		log.info("La aplicación a iniciado");			}
}
