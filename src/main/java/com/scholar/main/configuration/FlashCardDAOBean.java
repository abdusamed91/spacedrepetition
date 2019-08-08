package com.scholar.main.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scholar.main.controllers.model.FlashCard;

@Configuration
public class FlashCardDAOBean {
	
	@Bean
	public FlashCard getFlashCardBean() {
		return new FlashCard();
	}
}
