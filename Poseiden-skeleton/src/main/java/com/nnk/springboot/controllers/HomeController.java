package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller
 */
@Slf4j
@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		log.info("GET /");
		return "redirect:/bidList/list";
	}
}
