package com.scholar.main.controllers.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class User {
	private static int counter;
	
	private final UUID id;
	private final int userid;
	private String username;
	private String password;
	
	private final List<FlashCard> flashCards;
	private final Set<FlashCard> flashCardsNoReviewSet;
	// Cards which are to be reviewed in the future are pushed into pending flashCardsOwnPool
	private final List<FlashCard> flashCardsOPendingPool;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getId() {
		return id;
	}

	public List<FlashCard> getFlashCards() {
		return flashCards;
	}

	
	public User(String username,String password) {
		this.userid = counter++;
		id = UUID.randomUUID();
		this.username = username;
		this.password = password;
		this.flashCards = new LinkedList<>();
		this.flashCardsOPendingPool = new LinkedList<>();
		this.flashCardsNoReviewSet = new HashSet<>();
	}

	public Set<FlashCard> getFlashCardsNoReviewSet() {
		return flashCardsNoReviewSet;
	}

	public List<FlashCard> getFlashCardsPendingPool() {
		return flashCardsOPendingPool;
	}

	public int getUserid() {
		return userid;
	}

	
	
	
	

}
