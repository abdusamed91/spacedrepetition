package com.scholar.main.controllers;

import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting( Model model) {
        model.addAttribute("name", "yes!!");
        return "index";
    }
    
    @GetMapping("/hello")
    public String greeting( Map<String, Object> model) {
        model.put("message", "You are in new page !!");
        return "index";
    }

}