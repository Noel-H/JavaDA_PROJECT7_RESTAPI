package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Login Controller
 */
@Slf4j
@Controller
public class LoginController {

    /**
     * Get login page
     * @return login.html
     */
    @GetMapping("/login")
    public String getLogin(){
        log.info("GET /login");
        return "login";
    }
}
