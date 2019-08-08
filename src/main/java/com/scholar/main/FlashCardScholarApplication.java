package com.scholar.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.scholar.main.controllers.model.FlashCard;
import com.scholar.main.service.FlashCardEngine;
import com.scholar.main.service.FlashCardEngineImpl;

@SpringBootApplication
public class FlashCardScholarApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FlashCardScholarApplication.class);
	}

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
