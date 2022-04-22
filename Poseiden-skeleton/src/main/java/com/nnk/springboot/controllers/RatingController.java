package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

/**
 * Rating Controller
 */
@Slf4j
@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    /**
     * Get /rating/list
     * @param model is used for the html template
     * @return rating/list.html
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        log.info("GET /rating/list");
        model.addAttribute("ratingList",ratingService.getRatingList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "rating/list";
    }

    /**
     * Get /rating/add
     * @param rating is used as attribute for the html template
     * @param model is used for the html template
     * @return rating/add
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        log.info("GET /rating/add");
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    /**
     * Post /rating/validate
     * @param rating is the object that need to be validated
     * @param result is used to check if there is an error
     * @param model is used for the html template
     * @return rating/list if no error or rating/add if error
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        log.info("POST /rating/validate");
        if (result.hasErrors()){
            log.error("POST /rating/validate : {} ERROR - {}",result.getErrorCount(), result.getAllErrors());
            return "rating/add";
        }
        ratingService.addRating(rating);
        model.addAttribute("ratingList",ratingService.getRatingList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "rating/list";
    }

    /**
     * Get /rating/update/{id}
     * @param id is the id of the rating
     * @param model is used for the html template
     * @return rating/update or rating/list if the id is not found
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET /rating/update/{}",id);
        Rating rating;
        try{
            rating = ratingService.getRatingById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /rating/update/{} : ERROR - {}",id, e.getMessage());
            model.addAttribute("ratingList",ratingService.getRatingList());
            model.addAttribute("username", userService.getUsername());
            model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
            return "rating/list";
        }
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Post /rating/update/{id}
     * @param id is the id of the rating to update
     * @param rating is the object that need to be validated
     * @param result is used to check if there is an error
     * @return redirect:/rating/list if no error or rating/update if error
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                            BindingResult result) {
        log.info("POST /rating/update/{}",id);
        if (result.hasErrors()){
            log.error("POST /rating/update/{} : {} ERROR - {}",id, result.getErrorCount(), result.getAllErrors());
            return "rating/update";
        }
        ratingService.updateRatingById(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Get /rating/delete/{id}
     * @param id is the id of the rating to delete
     * @return redirect:/rating/list
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        log.info("GET /rating/delete/{}",id);
        try{
            ratingService.deleteRatingById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /rating/delete/{} : ERROR - {}",id, e.getMessage());
        }
        return "redirect:/rating/list";
    }
}
