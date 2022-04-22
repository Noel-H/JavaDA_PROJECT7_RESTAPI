package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
 * RuleName Controller
 */
@Slf4j
@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private UserService userService;

    /**
     * Get /ruleName/list
     * @param model is used for the html template
     * @return ruleName/list.html
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        log.info("GET /ruleName/list : {}", userService.getUsername());
        model.addAttribute("ruleNameList",ruleNameService.getRuleNameList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "ruleName/list";
    }

    /**
     * Get /ruleName/add
     * @param ruleName is used as attribute for the html template
     * @param model is used for the html template
     * @return ruleName/add
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName, Model model) {
        log.info("GET /ruleName/add : {}", userService.getUsername());
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    /**
     * Post /ruleName/validate
     * @param ruleName is the object that need to be validated
     * @param result is used to check if there is an error
     * @param model is used for the html template
     * @return ruleName/list if no error or ruleName/add if error
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("POST /ruleName/validate : {}", userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /ruleName/validate : {} : {} ERROR - {}",userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleName);
        model.addAttribute("ruleNameList",ruleNameService.getRuleNameList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "ruleName/list";
    }

    /**
     * Get /ruleName/update/{id}
     * @param id is the id of the ruleName
     * @param model is used for the html template
     * @return ruleName/update or ruleName/list if the id is not found
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET /ruleName/update/{} : {}",id, userService.getUsername());
        RuleName ruleName;
        try{
            ruleName = ruleNameService.getRuleNameById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /ruleName/update/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
            model.addAttribute("ruleNameList",ruleNameService.getRuleNameList());
            model.addAttribute("username", userService.getUsername());
            model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
            return "ruleName/list";
        }
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Post /ruleName/update/{id}
     * @param id is the id of the ruleName to update
     * @param ruleName is the object that need to be validated
     * @param result is used to check if there is an error
     * @return redirect:/ruleName/list if no error or ruleName/update if error
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result) {
        log.info("POST /ruleName/update/{} : {}", id, userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /ruleName/update/{} : {} : {} ERROR - {}", id, userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "ruleName/update";
        }
        ruleNameService.updateRuleNameById(id, ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Get /ruleName/delete/{id}
     * @param id is the id of the ruleName to delete
     * @return redirect:/ruleName/list
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        log.info("GET /ruleName/delete/{} : {}", id, userService.getUsername());
        try{
            ruleNameService.deleteRuleNameById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /ruleName/delete/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}
