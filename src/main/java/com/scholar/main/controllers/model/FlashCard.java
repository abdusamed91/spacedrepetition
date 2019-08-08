package com.scholar.main.controllers.model;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;

public class FlashCard implements Comparable<FlashCard>, Comparator<FlashCard> {
	
	private final UUID id;
	
	private String content;

	private boolean flagNever;
	private boolean flagDisplay = true;
	
	private int wrongCounter;
	
	private Bucket bucket;
	
	private Calendar lastAccessedTime;
	private Calendar currTime;
	private Calendar targetTime = Calendar.getInstance();
	
	private String reviewTime = "NOT_SEEN";
	
	
	// Countdown timer which updates upon each load up and sets up the correct bucket -> engine
	public FlashCard () {
		this.id = UUID.randomUUID();
		this.targetTime.set(1970, 1, 1);
		this.bucket = Bucket.ZERO;
		this.wrongCounter = 0;
		this.flagNever = false;
	}
	
	public FlashCard (String content) {
		this.id = UUID.randomUUID();
		this.targetTime.set(1970, 1, 1);
		this.bucket = Bucket.ZERO;
		this.wrongCounter = 0;
		this.flagNever = false;
		this.content = content;
	}
	
	/**
	 * Used to clone a card from the pool
	 * @param fc
	 */
	public FlashCard (FlashCard fc) {
		this.id = fc.getId();
		this.targetTime = fc.getTargetTime();
		this.bucket = fc.getBucket();
		this.wrongCounter = fc.getWrongCounter();
		this.flagNever = fc.isFlagNever();
		this.content = fc.getContent();
	}
	
	
	
		/**
	 *  Bins 1-11 are associated with the following timespans: 
	 *  ONE  ->  5 sec 	TWO -> 25 sec 	THREE -> 2 mins 
	 *  FOUR -> 10 min	FIVE -> 1 hor 	SIX   -> 5 hurs
	 *  SEVEN -> 1 day 	EIGHT-> 5 days 	NINE  -> 25 days
	 *  TEN   -> 4 mon	ELEVEN -> NEVER
	 *  
	 *  The timespans 
	 *  reflect the amount of time to wait before the next review of that card.
	 *  
	 *  Bucket ELEVEN represents `NEVER` or `HARD TO REMEMBER BUCKET`
	 * @author aahmed
	 *
	 */
	public enum Bucket{
		ZERO(-1000),ONE(5),TWO(5*5),THREE(2*60),FOUR(10*60),FIVE(60*60),SIX(5*60*60),SEVEN(24*60*60),EIGHT(5*24*60*60),
		NINE(25*24*60*60),TEN(4*31*24*60*60),ELEVEN(Integer.MAX_VALUE);
		
		private int val;
		
		Bucket(int val) {
			this.val = val;
		}

		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}

		
	}

	// Setters and Getters

	

	public UUID getId() {
		return id;
	}

	public Calendar getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Calendar lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	public Calendar getCurrTime() {
		return currTime;
	}

	public void setCurrTime(Calendar currTime) {
		this.currTime = currTime;
	}

	public Calendar getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(Calendar targetTime) {
		this.targetTime = targetTime;
	}

	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Bucket getBucket() {
		return bucket;
	}

	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}

	public boolean isFlagNever() {
		return flagNever;
	}

	public void setFlagNever(boolean flagNever) {
		this.flagNever = flagNever;
	}

	public int getWrongCounter() {
		return wrongCounter;
	}

	public void increWrongCounter() {
		this.wrongCounter++;
	}

	@Override
	public String toString() {
		return "Bucket:" + this.bucket.name() 
		+ " Content:" + this.content + " Display:" + this.flagDisplay;
	}

	@Override
	public int compareTo(FlashCard o) {
		return this.getBucket().getVal() - o.getBucket().getVal();
	}

	@Override
	public int compare(FlashCard o1, FlashCard o2) {
		return o1.getBucket().getVal() - o2.getBucket().getVal();
	}
	
	
	
	public String getReviewTime() {
		return reviewTime;
	}

	public String printTimeToReview() {
		String res = printTimeToReview(new Object());
		this.reviewTime = res;
		return res;
		
	}
	
	
	private String printTimeToReview(Object o) {
		if (bucket == Bucket.ELEVEN)
			return "FlashCard is HARD to Review";
		if (bucket == Bucket.ZERO)
			return "Reviewing Now";
		long now = Calendar.getInstance().getTimeInMillis(); 
		long tar = targetTime.getTimeInMillis();
		if(tar - now <= 0)  {
			return "In Review ..";
		}
		else {
			long diff = Math.abs(tar - now);
			if(TimeUnit.MILLISECONDS.toDays(diff) >= 30) {
				return "Next review longer than 30 days ...";
			}
			else {
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				return String.format("ext review time in %d days %d hours "
						+ "%d minutes %d seconds %n"
						, diffDays,diffHours,diffMinutes,diffSeconds);
				
			}
		}
			
	}
	
	
	
	public boolean isFlagDisplay() {
		return flagDisplay;
	}

	public void setFlagDisplay(boolean flagDisplay) {
		this.flagDisplay = flagDisplay;
	}

	// Compare to flashCard based on their UUID uniqueness
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if(!(o instanceof FlashCard))
			return false;
		FlashCard c = (FlashCard) o;
		
		return this.getId().equals(c.getId());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
