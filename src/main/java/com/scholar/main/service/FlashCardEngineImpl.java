package com.scholar.main.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.scholar.main.controllers.model.FlashCard;
import com.scholar.main.controllers.model.FlashCard.Bucket;
import com.scholar.main.controllers.model.User;

@Repository
public class FlashCardEngineImpl implements FlashCardEngine {
	
	final static Bucket[] buckets = Bucket.values();
	final static List<FlashCard> flashCardPool = new LinkedList<>(); // All cards start in Bin ZERO

	// Called upon flash-card refresh when user starts the game
	@Override
	public void updateTargetTimeUseBucket(FlashCard fl) {
		Calendar c = Calendar.getInstance();
		fl.setTargetTime(c);
		fl.getTargetTime().add(Calendar.SECOND, fl.getBucket().getVal());
		
	}

	/**
	 * FlashCard to check if to review the flash card in the current session or not
	 */
	@Override
	public boolean isReviewCard(FlashCard fl) {
		if(fl.getBucket() == Bucket.ELEVEN) 
			return false;
		Calendar c = Calendar.getInstance();
		if(fl.getTargetTime().before(c)) {
			fl.setFlagDisplay(true);
			return true;
		}
		else {
			fl.setFlagDisplay(false);
			return false;
		}
	}
	

	// Expose method to API
	@Override
	public boolean updateFlashCardBucket(FlashCard fl, Bucket bucket) {
		if(!fl.isFlagNever()) {
			if(bucket == Bucket.ELEVEN) {
				fl.setBucket(bucket);
				fl.setFlagNever(true);
				return true;
			}
			else
				fl.setBucket(bucket);
			
			updateTargetTimeUseBucket(fl);
			return true;
		} else {
			System.out.println(fl.toString() + ": is Forgotten Card");
			return false;
		}
		
	}

	// User Actions
	@Override
	public boolean incrementBucket(FlashCard fl) {
		if(fl.getBucket() == Bucket.ELEVEN)
			return false;
		int i = getBucketIndex(fl);
		return updateFlashCardBucket(fl,buckets[i+1]);
	}
	
	
	@Override
	public boolean resetBucket(FlashCard fl) {
		fl.increWrongCounter();
		if(fl.getWrongCounter() >= 10) {
			return updateFlashCardBucket(fl,Bucket.ELEVEN);
		}
		return updateFlashCardBucket(fl,Bucket.ZERO);
	}
	
	private int getBucketIndex(FlashCard fl) {
		for(int i = 0; i < buckets.length - 2;i++) {
			if(buckets[i] == fl.getBucket()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns Size zero if no cards are available to be reviewed currently
	 */
	@Override
	public List<FlashCard> buildQueueForFlashCards(List<FlashCard> flashCards) {
		List<FlashCard> res = new LinkedList<>();		
		if(flashCards.size() == 0)
			return res;
		
		Collections.sort(flashCards,Collections.reverseOrder());

		return flashCards;
	}

	/**
	 * Picks up card from the pool
	 * Two Condition to meet on load of FlashCards
	 * 1) Temporarily no cards in review
	 * 2) Permanently no cards to review
	 */
	@Override
	public void updateFlashCardsForUser(User user, int size) {
		List<FlashCard> flashCards = user.getFlashCards();
		
		// Remove 'Forgotten' state cards
		Iterator<FlashCard> it = flashCards.iterator();
		while(it.hasNext()) {
			FlashCard f = it.next();
			if(f.getBucket() == Bucket.ELEVEN || f.isFlagNever() == true) {
				user.getFlashCardsNoReviewSet().add(f);
				it.remove();
				continue;
			}
			// If card cannot be reviewed, put it in the pending flashcard pool for user
			else if (!isReviewCard(f)) {
				f.printTimeToReview();
				user.getFlashCardsOPendingPool().add(f);
				it.remove();
			}
		}
		
		// Add all flash cards from the pending pool which can be reviewed right now
		it = user.getFlashCardsOPendingPool().iterator();
		while(it.hasNext()) {
			FlashCard f = it.next();
			if(flashCards.size() > size)
				return;
			else if(isReviewCard(f)) {
				flashCards.add(f);
				it.remove();
			}
		}
		
		
		// All the possible flash card to be reviewed have been added and still flashcart full
		// Therefore, get new flashCard from global pool.
		for (FlashCard f : flashCardPool) {
 			if(flashCards.size() <= size) {
				// Check if card not already in review card for user && not in Never Review Card for that user
				if(!flashCards.contains(f) && !user.getFlashCardsNoReviewSet().contains(f) 
						&& !user.getFlashCardsOPendingPool().contains(f))
					flashCards.add(new FlashCard(f));
			}
			else {
				return;
			}
		}

	}
	
	// Too Add New card from the lot if all have positive timers
	@Override
	public boolean isFlashCardFromBinAddable (List<FlashCard> flashCards) {
		for(FlashCard f : flashCards) {
			if(isReviewCard(f))
				return false;
		}
		return true;
	}
	
	// Check if user has permanently no more cards to review
	@Override
	public boolean isUserPermanentlyDone(User user) {
		return user.getFlashCardsNoReviewSet().size() == flashCardPool.size();
	}
	
	// Check if user has some cards to review 
	@Override
	public boolean isUserTemporarilyDone(User user) {
		if(isUserPermanentlyDone(user) == true) {
			System.out.println("User is permanently done...");
			return false;
		}
		// No cards should exist to be in 'reviewable' state
		// in user flashcard deck 
		for(FlashCard f : user.getFlashCards()) {
			if(isReviewCard(f) || f.isFlagDisplay()) 
				return false;
		}
		
		return true;
	}
	
	@Override
	public List<FlashCard> getFlashCardPool() {
		return flashCardPool;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 15; i++) {
			this.getFlashCardPool().add(
				new FlashCard("Dummy_content-" + i));
			}
		}		
	
}
