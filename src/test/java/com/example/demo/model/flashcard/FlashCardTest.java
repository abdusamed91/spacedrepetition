package com.example.demo.model.flashcard;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import org.hamcrest.core.IsNot;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.scholar.main.controllers.model.FlashCard;
import com.scholar.main.controllers.model.User;
import com.scholar.main.controllers.model.FlashCard.Bucket;
import com.scholar.main.service.FlashCardEngine;
import com.scholar.main.service.FlashCardEngineImpl;

public class FlashCardTest {

	private static FlashCardEngine flashcardengine;
	
	@BeforeClass
	public static void init() {
		flashcardengine = new FlashCardEngineImpl();
		for(int i = 0; i < 15; i++) {
			flashcardengine.getFlashCardPool().add(
				new FlashCard("Dummy_content-" + i));
			}
		}
	
	@Test
	public void FlashCardInNeverBinTest() {
		FlashCard fl = new FlashCard();
		fl.setTargetTime(Calendar.getInstance());
		fl.setBucket(Bucket.EIGHT);
		
		
		assertTrue(flashcardengine.updateFlashCardBucket(fl, Bucket.TWO));
		assertFalse(fl.isFlagNever());
		assertTrue(flashcardengine.updateFlashCardBucket(fl,Bucket.ELEVEN));
		assertTrue(fl.isFlagNever());
		assertFalse(flashcardengine.updateFlashCardBucket(fl,Bucket.THREE));

	}
	
	
	@Test
	public void FlashCardObjectTest() {
		FlashCard f = new FlashCard();
		assertNotNull(f.getId());
		
		assertTrue(flashcardengine.updateFlashCardBucket(f, Bucket.TWO));
	}

	@Test
	@Ignore
	public void FLashCardReviewTest() {
		FlashCard fl = new FlashCard();
		assertTrue(flashcardengine.isReviewCard(fl));
		flashcardengine.updateFlashCardBucket(fl, Bucket.FOUR);
		assertFalse(flashcardengine.isReviewCard(fl));
		
	}
	
	// User Interaction Test
	
	@Test
	public void FlashCardIncrementAndDecrementTest() {
		FlashCard fl = new FlashCard();
		assertTrue(fl.getBucket() == Bucket.ZERO);
		assertTrue(flashcardengine.incrementBucket(fl));
		assertTrue(fl.getBucket() == Bucket.ONE);
		
		assertTrue(flashcardengine.incrementBucket(fl));
		assertTrue(fl.getBucket() == Bucket.TWO);
		
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(fl.getBucket() == Bucket.ZERO);

		// Never Bucket Test
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		assertTrue(flashcardengine.resetBucket(fl));
		
		// Ten Resets calls have been made, hence in NEVER bucket
		assertTrue(fl.getBucket() == Bucket.ELEVEN);
		
		assertFalse(flashcardengine.resetBucket(fl));
		
	}
	
	// Priority Queue Test
	// Be able to remove if something is ELEVEN bin in it
	@Test
	@Ignore
	public void FlashCardQueueTest() {
		List<FlashCard> l = new LinkedList<>();
		FlashCard f = new FlashCard();
		f.setBucket(Bucket.ONE);
		l.add(f);
		f = new FlashCard();
		f.setBucket(Bucket.TWO);
		l.add(f);
		f = new FlashCard();
		f.setBucket(Bucket.THREE);
		l.add(f);
		f = new FlashCard();
		f.setBucket(Bucket.FIVE);
		l.add(f);
		f = new FlashCard();
		f.setBucket(Bucket.TEN);
		l.add(f);
		f = new FlashCard();
		f.setBucket(Bucket.FIVE);
		l.add(f);
		f.setBucket(Bucket.ONE);
		l.add(f);
		
		List<FlashCard> list = flashcardengine.buildQueueForFlashCards(l);
		assertTrue(list.size() == l.size());		
		FlashCard temp = list.get(0);
		for(int i = 1; i < l.size(); i++) {
			assertTrue(temp.getBucket().getVal() >= 
					list.get(i).getBucket().getVal());
			temp = list.get(i);
		}
		
		// Removing one items of from queue ...
		assertTrue(flashcardengine.updateFlashCardBucket(list.get(0), Bucket.ELEVEN));
		int oldSize = list.size();
		list = flashcardengine.buildQueueForFlashCards(l);
		temp = list.get(0);
		//assertFalse(list.size() == oldSize);
		for(int i = 1; i < oldSize - 1; i++) {
			assertTrue(temp.getBucket().getVal() >= 
					list.get(i).getBucket().getVal());
			temp = list.get(i);
		}
	}
	
	/**
	 * User action method test in engine
	 * User flashcard dictionary is incremently increased as opposed 
	 * to loading it up all in the memory #feature
	 */
	
	// TODO the flashcard is only retrieved from the user 'dictionary' 
	// and sent a reference of it and the attributes which are to be changed
	// is updated
	@Test
	public void testQueneManupilationForUser() {
		User u = new User("andy", "murphy");
		
		flashcardengine.updateFlashCardsForUser(u, 5);
		assertThat(u.getFlashCards().size(), is(6));
		
		// Build a queue for those flashcards to be displayed to user
		flashcardengine.buildQueueForFlashCards(u.getFlashCards());
		System.out.println(u.getFlashCards().toString());

		//################### Queue Built ########################
		//############Start of performing Actions ################
		
		// Card 2 is easy ...
		flashcardengine.incrementBucket(u.getFlashCards().get(2));
		flashcardengine.incrementBucket(u.getFlashCards().get(2));
		// Card 4 is hard so remove and put in NEVER bin for user
		FlashCard flashCard_4 = u.getFlashCards().get(4);
		for(int i = 1; i <=10;i++) 
			flashcardengine.resetBucket(u.getFlashCards().get(4));
		
		// Increment rest card by one
		flashcardengine.incrementBucket(u.getFlashCards().get(0));
		flashcardengine.incrementBucket(u.getFlashCards().get(1));
		flashcardengine.incrementBucket(u.getFlashCards().get(5));
		
		// Card 3 too easy and will not show up in the next session 
		// and in push to bucket SIX
		FlashCard flashCard_3 = u.getFlashCards().get(3);
		for(int i = 1; i <=6;i++) 
			flashcardengine.incrementBucket(u.getFlashCards().get(3));			
				
		// Assert dictionary flashcards are not changed at all
		for(FlashCard f : flashcardengine.getFlashCardPool())
				assertThat("Pool is modified. Bad.",f.getBucket(),is(Bucket.ZERO));
		
		//############End of performing Actions ################
		
		//############SIMULATION RESULT CHECK##############
		flashcardengine.updateFlashCardsForUser(u, 5);
		flashcardengine.buildQueueForFlashCards(u.getFlashCards());
		
 		System.out.println(u.getFlashCards().toString());
		assertThat("Flashcard deck is six",
				u.getFlashCards().size(), is(6));
		assertThat("FlashCard_4 is in 'No Review Bin'",
				u.getFlashCardsNoReviewSet().contains(flashCard_4),is(true));
		assertThat("FlashCard_3 is in 'User pool bin'",
				u.getFlashCardsOPendingPool().contains(flashCard_3),is(true));
		assertThat("FlashCard_4 DNE in User Queue list",
						u.getFlashCards().contains(flashCard_4),is(false));
		
		//##############SESSION 2 SIMULATION############################
		//##########Make Card in Pending Pool Discoverable##############
		FlashCard flashCardFromPoolToQueue = u.getFlashCardsOPendingPool().get(0);
		for(int i = 1; i <=10;i++) 
			// Push second card into NEVER bin
			flashcardengine.resetBucket(u.getFlashCards().get(1));
		
		flashcardengine.resetBucket(flashCardFromPoolToQueue); // Now pool card availiable for view
		flashcardengine.updateFlashCardsForUser(u,5); // clean up and get card from pool  
		flashcardengine.buildQueueForFlashCards(u.getFlashCards()); // queue it all up
		assertThat(u.getFlashCards().size(),is(6));
		assertThat("FlashCard from Pool is now in for display",
				u.getFlashCards().contains(flashCardFromPoolToQueue),is(true));

		for(FlashCard f : u.getFlashCards())
			if(f.equals(flashCardFromPoolToQueue))
				assertThat("FlashCard from pool is viewable",
						f.isFlagDisplay(),is(true));
		
		assertThat("FlashCard from is in 'User pool bin'",
				u.getFlashCardsOPendingPool().contains(flashCardFromPoolToQueue),is(false));
		
		System.out.println(u.getFlashCards().toString());
	}
}
