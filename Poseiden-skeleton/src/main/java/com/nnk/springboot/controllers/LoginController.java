package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login Controller
 */
@Slf4j
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get login
     * @return login
     */
    @GetMapping("login")
    public ModelAndView login() {
        log.info("GET login");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

//    @GetMapping("login")
//    public ModelAndView getGithub() {
//        log.info("GET login Github");
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("oauth2");
//        return mav;
//    }

    /**
     * Get secure/article-details
     * @return user/list
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("GET secure/article-details");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Get error
     * @return 403
     */
    @GetMapping("error")
    public ModelAndView error() {
        log.info("GET error");
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

//    @GetMapping("logout")
//    public ModelAndView logout() {
//        log.info("GET logout");
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("logout");
//        return mav;
//    }
}
