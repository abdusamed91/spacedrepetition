package com.scholar.main.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scholar.main.controllers.model.FlashCard;
import com.scholar.main.controllers.model.User;
import com.scholar.main.service.FlashCardEngine;

@Controller
@RequestMapping("/flashcard")
public class FlashCardUserController {

	@Autowired
	FlashCardEngine flashcardengine;

	private final static String INDEX = "index";
	private final static String ERROR = "error";
	private final static String FLASHCARD = "flashcard";
	private final static String INFORM = "inform";
	private final static String WELCOME = "welcome";
	private final static String FLASHCARDPRINT = "flashcardprint";
	private final static String CREATEFLASHCARD = "createflashcard";

	private final static List<User> userSession = new LinkedList<>();
	private static int counter = 0;
	
	// Create Flashcards
	@GetMapping("/create")
	public String getcreateFlashCard(Model model,@CookieValue(value="userid",defaultValue="-1") int userid) {
		if(userid == -1) {
			model.addAttribute("userid","... no userid currently assigned");
			return WELCOME;
		}
		model.addAttribute("flashcardtotalpool",flashcardengine.getFlashCardPool());
		return CREATEFLASHCARD;
	}
	
	@PostMapping("/create")
	public String getcreateFlashCard(Model model,HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		flashcardengine.getFlashCardPool().add(new FlashCard(title,content));
		
		model.addAttribute("flashcardtotalpool",flashcardengine.getFlashCardPool());
		model.addAttribute("message","Flashcard created successfully");
		return CREATEFLASHCARD;
	}
	
	@GetMapping("/all")
	public String userAllFlashCard( Model model,@CookieValue(value="userid",defaultValue="-1") int userid) {
		if(userid == -1) {
			model.addAttribute("userid","... no userid currently assigned");
			return WELCOME;
		}
		
		User u = userSession.get(userid);
		
		// Refresh the review counter
		for(FlashCard f : u.getFlashCards())
			f.printTimeToReview();
		for(FlashCard f : u.getFlashCardsNoReviewSet())
			f.printTimeToReview();
		for(FlashCard f : u.getFlashCardsPendingPool())
			f.printTimeToReview();
		
		
		model.addAttribute("usermessage","Showing FlashCard details for userid: " + userid);
		model.addAttribute("flashcardlist",u.getFlashCards());
		model.addAttribute("flashcardNeverPool",u.getFlashCardsNoReviewSet());
		model.addAttribute("flashcardPendingPool",u.getFlashCardsPendingPool());
		
		return FLASHCARDPRINT;
		
	}
	
	@GetMapping("/")
	public String userMain( Model model,@CookieValue(value="userid",defaultValue="-1") int userid) {
		if(userid == -1) 
			model.addAttribute("userid","... no userid currently assigned");
		else 
			model.addAttribute("userid",String.valueOf(userid));
		return WELCOME;
		
	}
	
//	private void loadUser(Model, model, HttpServletRequst request, int userid){ 
//		
//	}

	@GetMapping("/init")
	public String initUser( Model model, HttpServletResponse response, HttpServletRequest request) {
		User user = new User("User-"+counter++,"pass");	
		userSession.add(user);
		model.addAttribute("user",user);
		flashcardengine.updateFlashCardsForUser(user, 5);
		flashcardengine.buildQueueForFlashCards(user.getFlashCards());

		Cookie cookie = new Cookie("userid",String.valueOf(user.getUserid()));
		response.addCookie(cookie);

		model.addAttribute("method", "initUser");
		return INDEX;
	}
	
	@GetMapping("/user/{userid}")
	public String refreshSessionUser(Model model,@PathVariable("userid") int userid, HttpServletResponse response) {
		if(userid == -1) {
			model.addAttribute("message","userid_cannot_be_negative");
			return ERROR;
		}
		User user = userSession.get(userid);
		if(user == null) {
			model.addAttribute("message","No_user_found_for_given_userid:" + userid);
			return ERROR;

		}
		Cookie cookie = new Cookie("userid",String.valueOf(userid));
		response.addCookie(cookie);
		
		flashcardengine.updateFlashCardsForUser(user,5); // clean up and get card from pool  
		flashcardengine.buildQueueForFlashCards(user.getFlashCards()); // queue it all up
		return WELCOME;
	}


	/**
	 * Call after *init* endpoint to use userid in cookie to find appropriate flashcards
	 * @param model
	 * @param userid
	 * @param response
	 * @return
	 */
	@GetMapping("/run")
	public String continueFlashCard(Model model, @CookieValue(value="userid",defaultValue="-1") int userid,
			@CookieValue(value="flashcardNum",defaultValue="-1") int flashcardNumCurr, HttpServletResponse response) {
		if(userid == -1) {
			model.addAttribute("message","Invalid userid.");
			return ERROR;
		}
		User user = userSession.get(userid);
		
		if(flashcardengine.isUserPermanentlyDone(user)) {
			model.addAttribute("message","User is Permanently Done!" + user.getUsername());
			return INFORM;
		} else if(flashcardengine.isUserTemporarilyDone(user)) {
			flashcardengine.updateFlashCardsForUser(user,5); // clean up and get card from pool  
			flashcardengine.buildQueueForFlashCards(user.getFlashCards()); // queue it all up
			model.addAttribute("message","User is Temporarily Done!" + user.getUsername());
			return INFORM;
			
		}
		
		
		System.out.println(user.getFlashCards().size());

		if(flashcardNumCurr == -1) {
			// Cookie contains userId and flashcardNum
			Cookie flashcardNum = new Cookie("flashcardNum",String.valueOf(0));
			response.addCookie(flashcardNum);

			model.addAttribute("flashcard",userSession.get(userid).getFlashCards().get(0));
			model.addAttribute("reviewtime",userSession.get(userid).getFlashCards().get(0).printTimeToReview());
			model.addAttribute("showdef",false);

			return FLASHCARD;
		} else {
			
			// Reached end of the flashcard deck, time to refresh and reboot
			if(flashcardNumCurr >= user.getFlashCards().size() -1) {
				flashcardengine.updateFlashCardsForUser(user,5);   
				flashcardengine.buildQueueForFlashCards(user.getFlashCards());
				flashcardNumCurr = -1;
				
			}
			
			Cookie flashcardNum = new Cookie("flashcardNum",String.valueOf(flashcardNumCurr+1));
			response.addCookie(flashcardNum);

			model.addAttribute("flashcard",userSession.get(userid).getFlashCards().get(flashcardNumCurr+1));
			model.addAttribute("reviewtime",userSession.get(userid).getFlashCards().get(flashcardNumCurr+1).printTimeToReview());
			model.addAttribute("showdef",false);

			return FLASHCARD;
			
		}
		

	}

	// User clicks show definition ... 
	@PostMapping("/showdefinition") 
	public String showDefFlashCard(Model model, @CookieValue(value="userid",defaultValue="-1") int userid,
			@CookieValue(value="flashcardNum",defaultValue="-1") int flashcardNumCurr, HttpServletResponse response) {
		if(userid == -1 || flashcardNumCurr == -1) {
			model.addAttribute("message","Invalid userid.");
			return ERROR;
		}        	
		// if wrong show description and proceed to next card

		model.addAttribute("flashcard",userSession.get(userid).getFlashCards().get(flashcardNumCurr));
		model.addAttribute("reviewtime",userSession.get(userid).getFlashCards().get(flashcardNumCurr).printTimeToReview());
		model.addAttribute("showdef",true);

		return FLASHCARD;
	}


	@PostMapping("/answer/{ansId}") 
	public String igotitFlashCard(Model model, @CookieValue(value="userid",defaultValue="-1") int userid,
			@CookieValue(value="flashcardNum",defaultValue="-1") int flashcardNumCurr, HttpServletResponse response
			, @PathVariable("ansId") int ansId) {
		if(userid == -1 || flashcardNumCurr == -1) {
			model.addAttribute("message","Invalid userid.");
			return ERROR;
		}
		
		if(ansId == 0) { // I got it case
			flashcardengine.incrementBucket(userSession.get(userid).getFlashCards().get(flashcardNumCurr));
		}
		else {
			flashcardengine.resetBucket(userSession.get(userid).getFlashCards().get(flashcardNumCurr));
		}
		
		return "redirect:/flashcard/run";
	}
}


