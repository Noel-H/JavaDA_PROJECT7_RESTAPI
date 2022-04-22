package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
 * CurvePoint Controller
 */
@Slf4j
@Controller
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private UserService userService;

    /**
     * Get /curvePoint/list
     * @param model is used for the html template
     * @return curvePoint/list.html
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        log.info("GET /curvePoint/list : {}", userService.getUsername());
        model.addAttribute("curvePointList",curvePointService.getCurvePointList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "curvePoint/list";
    }

    /**
     * Get /curvePoint/add
     * @param curvePoint is used as attribute for the html template
     * @param model is used for the html template
     * @return curvePoint/add.html
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
        log.info("GET /curvePoint/add : {}", userService.getUsername());
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    /**
     * Post /curvePoint/validate
     * @param curvePoint is the object that need to be validated
     * @param result is used to check if there is an error
     * @param model is used for the html template
     * @return curvePoint/list if no error or curvePoint/add if error
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("POST /curvePoint/validate : {}", userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /curvePoint/validate : {} : {} ERROR - {}",userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "curvePoint/add";
        }
        curvePointService.addCurvePoint(curvePoint);
        model.addAttribute("curvePointList",curvePointService.getCurvePointList());
        model.addAttribute("username", userService.getUsername());
        model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
        return "curvePoint/list";
    }

    /**
     * Get /curvePoint/update/{}
     * @param id is the id of the curvePoint
     * @param model is used for the html template
     * @return curvePoint/update or curvePoint/list if the id is not found
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET /curvePoint/update/{} : {}",id, userService.getUsername());
        CurvePoint curvePoint;
        try{
            curvePoint = curvePointService.getCurvePointById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /curvePoint/update/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
            model.addAttribute("curvePointList",curvePointService.getCurvePointList());
            model.addAttribute("username", userService.getUsername());
            model.addAttribute("isRoleAdmin",userService.isRoleAdmin());
            return "curvePoint/list";
        }
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Post /curvePoint/update/{id}
     * @param id is the id of the curvePoint to update
     * @param curvePoint is the object that need to be validated
     * @param result is used to check if there is an error
     * @return redirect:/curvePoint/list if no error or curvePoint/update if error
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result) {
        log.info("POST /curvePoint/update/{} : {}", id, userService.getUsername());
        if (result.hasErrors()){
            log.error("POST /curvePoint/update/{} : {} : {} ERROR - {}", id, userService.getUsername(), result.getErrorCount(), result.getAllErrors());
            return "curvePoint/update";
        }
        curvePointService.updateCurvePointById(id, curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Get /curvePoint/delete/{id}
     * @param id is the id of the curvePoint to delete
     * @return redirect:/curvePoint/list
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        log.info("GET /curvePoint/delete/{} : {}", id, userService.getUsername());
        try{
            curvePointService.deleteCurvePointById(id);
        } catch (EntityNotFoundException e){
            log.error("GET /curvePoint/delete/{} : {} : ERROR - {}",id, userService.getUsername(), e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }
}
