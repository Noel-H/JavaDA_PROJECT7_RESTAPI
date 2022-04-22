package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller
 */
@Slf4j
@Controller
public class HomeController {

	/**
	 * Get /
	 * @return redirect:/bidList/list
	 */
	@GetMapping("/")
	public String home() {
		log.info("GET /");
		return "redirect:/bidList/list";
	}
}
