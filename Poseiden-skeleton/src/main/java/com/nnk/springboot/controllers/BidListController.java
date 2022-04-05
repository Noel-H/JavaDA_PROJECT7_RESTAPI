package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * BidList Controller
 */
@Slf4j
@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    /**
     * Get /bidList/list
     * @param model is used for the html template
     * @return bidList/list.html
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        log.info("GET /bidList/list");
        model.addAttribute("bidListList",bidListService.getBidListList());
        return "bidList/list";
    }

    /**
     * Get /bidList/add
     * @param bid is used as attribute for the html template
     * @param model is used for the html template
     * @return bidList/add
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        log.info("GET /bidList/add");
        model.addAttribute("bidList", bid);
        return "bidList/add";
    }

    /**
     * Post /bidList/validate
     * @param bid is the object that need to be validate
     * @param result is used to check if there is an error
     * @param model is used for the html template
     * @return bidList/list if no error or bidList/add if error
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        log.info("POST /bidList/validate");
        if (result.hasErrors()){
            log.error("POST /bidList/validate : {} ERROR - {}",result.getErrorCount(), result.getAllErrors());
            return "bidList/add";
        }
        bidListService.addBidList(bid);
        model.addAttribute("bidListList",bidListService.getBidListList());
        return "bidList/list";
    }

    /**
     * Get /bidList/update/{id}
     * @param id is the id of the bid
     * @param model is used for the html template
     * @return bidList/update
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET /bidList/update/{}",id);
        model.addAttribute("bidList", bidListService.getBidListById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
