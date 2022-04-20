package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller
 */
@Slf4j
@Controller
public class HomeController {

	/**
	 * Get /
	 * @param model is used for the html template
	 * @return home.html
	 */
	@RequestMapping("/")
	public String home(Model model) {
		log.info("GET /");
		return "home";
	}

	/**
	 * Get /admin/home
	 * @param model is used for the html template
	 * @return redirect:/bidList/list
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		log.info("GET /admin/home");
		return "redirect:/bidList/list";
	}

//	@RequestMapping("/*")
//	public String getGithub() {
//		log.info("GET /*");
//		return "home";
//	}
}
