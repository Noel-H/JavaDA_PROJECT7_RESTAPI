package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

/**
 * User Controller
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get /user/list
     * @param model is used for the html template
     * @return user/list.html
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        log.info("GET /user/list : {}",userService.getUsername());
        model.addAttribute("userList",userService.getUserList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "user/list";
    }

    /**
     * Get /user/add
     * @param user is used as attribute for the html template
     * @param model is used for the html template
     * @return user/add
     */
    @GetMapping("/user/add")
    public String addUserForm(User user, Model model) {
        log.info("GET /user/add : {}",userService.getUsername());
        model.addAttribute("user", user);
        return "user/add";
    }

    /**
     * Post /user/validate
     * @param user is the object that need to be validated
     * @param result is used to check if there is an error
     * @param model is used for the html template
     * @return user/list if no error or user/add if error
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        log.info("POST /user/validate : {}",userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /user/validate : {} : {} ERROR - {}",userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "user/add";
        }
        try {
            userService.addUser(user);
        } catch (EntityExistsException e){
            log.error("POST /user/validate : {} : Error - {}",userService.getUsername(), e.getMessage());
        }
        model.addAttribute("userList",userService.getUserList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "user/list";
    }

    /**
     * Get /user/update/{id}
     * @param id is the id of the user
     * @param model is used for the html template
     * @return user/update or user/list if the id is not found
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET /user/update/{} : {}",id, userService.getUsername());
        User user;
        try{
            user = userService.getUserById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /user/update/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
            model.addAttribute("userList",userService.getUserList());
            return "user/list";
        }
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Post /user/update/{id}
     * @param id is the id of the user to update
     * @param user is the object that need to be validated
     * @param result is used to check if there is an error
     * @return redirect:/user/list if no error or user/update if error
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result) {
        log.info("POST /user/update/{} : {}", id, userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /user/update/{} : {} : {} ERROR - {}", id, userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "user/update";
        }
        userService.updateUserById(id, user);
        return "redirect:/user/list";
    }

    /**
     * Get /user/delete/{id}
     * @param id is the id of the user to delete
     * @return redirect:/user/list
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        log.info("GET /user/delete/{} : {}", id, userService.getUsername());
        try{
            userService.deleteUserById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /user/delete/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
        }
        return "redirect:/user/list";
    }
}
