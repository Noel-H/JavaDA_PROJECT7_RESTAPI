package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
 * Trade Controller
 */
@Slf4j
@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    /**
     * Get /trade/list
     * @param model is used for the html template
     * @return trade/list.html
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        log.info("GET /trade/list : {}", userService.getUsername());
        model.addAttribute("tradeList",tradeService.getTradeList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "trade/list";
    }

    /**
     * Get /trade/add
     * @param trade is used as attribute for the html template
     * @param model is used for the html template
     * @return trade/add
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade, Model model) {
        log.info("GET /trade/add : {}", userService.getUsername());
        model.addAttribute("trade", trade);
        return "trade/add";
    }

    /**
     * Post /trade/validate
     * @param trade is the object that need to be validated
     * @param result is used to check if there is an error
     * @param model is used for the html template
     * @return trade/list if no error or trade/add if error
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        log.info("POST /trade/validate : {}", userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /trade/validate : {} : {} ERROR - {}",userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "trade/add";
        }
        tradeService.addTrade(trade);
        model.addAttribute("tradeList",tradeService.getTradeList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "trade/list";
    }

    /**
     * Get /trade/update/{id}
     * @param id is the id of the trade
     * @param model is used for the html template
     * @return trade/update or trade/list if the id is not found
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET /trade/update/{} : {}",id, userService.getUsername());
        Trade trade;
        try{
            trade = tradeService.getTradeById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /trade/update/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
            model.addAttribute("tradeList",tradeService.getTradeList());
            model.addAttribute("username", userService.getUsername());
            model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
            return "trade/list";
        }
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Post /trade/update/{id}
     * @param id is the id of the trade to update
     * @param trade is the object that need to be validated
     * @param result is used to check if there is an error
     * @return redirect:/trade/list if no error or trade/update if error
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                            BindingResult result) {
        log.info("POST /trade/update/{} : {}", id, userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /trade/update/{} : {} : {} ERROR - {}", id, userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "trade/update";
        }
        tradeService.updateTradeById(id, trade);
        return "redirect:/trade/list";
    }

    /**
     * Get /trade/delete/{id}
     * @param id is the id of the trade to delete
     * @return redirect:/trade/list
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        log.info("GET /trade/delete/{} : {}", id, userService.getUsername());
        try{
            tradeService.deleteTradeById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /trade/delete/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
        }
        return "redirect:/trade/list";
    }
}
