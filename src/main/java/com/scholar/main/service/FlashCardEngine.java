package com.scholar.main.service;

import java.util.List;

import com.scholar.main.controllers.model.FlashCard;
import com.scholar.main.controllers.model.FlashCard.Bucket;
import com.scholar.main.controllers.model.User;


public interface FlashCardEngine {
	
	// Core Functions
	public void updateTargetTimeUseBucket(FlashCard fl);
	public boolean isReviewCard( FlashCard fl);
	public boolean updateFlashCardBucket(FlashCard fl, Bucket bucket);
		
	// Action on User State
	public boolean isFlashCardFromBinAddable (List<FlashCard> flashCards);
	public boolean isUserPermanentlyDone(User user);
	public boolean isUserTemporarilyDone(User user);
	
	// Returning the pool of cards
	public List<FlashCard> getFlashCardPool();
	
	// Method to be used on the interface
	public void updateFlashCardsForUser(User user, int size); // Clean up flashcards
	
	// Queue Logic
	public List<FlashCard> buildQueueForFlashCards(List<FlashCard> flashCards);
	
	// User Reactions Actions
	public boolean incrementBucket(FlashCard fl);
	public boolean resetBucket(FlashCard fl);
	
	// Init
	public void init();

	
}
