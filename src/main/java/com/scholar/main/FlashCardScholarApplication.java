package com.scholar.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.scholar.main.service.FlashCardEngine;

@SpringBootApplication
	public class FlashCardScholarApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashCardScholarApplication.class, args);
	}
	

    @Bean
    CommandLineRunner initDatabase(FlashCardEngine repository) {
        return args -> {
        	repository.init();
        };
    }
}
