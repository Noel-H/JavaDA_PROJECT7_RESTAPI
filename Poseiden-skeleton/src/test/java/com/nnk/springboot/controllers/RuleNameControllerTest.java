package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    public void home_Should_Return_Ok() throws Exception{
        when(ruleNameService.getRuleNameList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    public void addRuleNameForm_Should_Return_Ok() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate_Should_Return_Ok() throws Exception {
        when(ruleNameService.addRuleName(any())).thenReturn(new RuleName());
        when(ruleNameService.getRuleNameList()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/ruleName/validate")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    public void validate_Without_Name_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate_Without_Description_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name","test01")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate_Without_Json_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name","test01")
                        .param("description","test02")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate_Without_Template_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate_Without_SqlStr_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate_Without_SqlPart_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void showUpdateForm_Should_Return_Ok() throws Exception {
        RuleName ruleName = new RuleName("test01","test02","test03","test04","test05","test06");
        when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
        verify(ruleNameService,times(1)).getRuleNameById(1);
    }

    @Test
    public void showUpdateForm_Should_Return_RuleNameList_If_Id_Not_Found() throws Exception {
        when(ruleNameService.getRuleNameById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
        verify(ruleNameService,times(1)).getRuleNameById(1);
    }

    @Test
    public void updateRuleName_Should_Return_Ok() throws Exception {
        RuleName ruleName = new RuleName("test01","test02","test03","test04","test05","test06");
        ruleName.setId(1);
        when(ruleNameService.updateRuleNameById(1,ruleName)).thenReturn(new RuleName());
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
        verify(ruleNameService,times(1)).updateRuleNameById(1,ruleName);
    }

    @Test
    public void updateRuleName_Without_Name_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void updateRuleName_Without_Description_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name","test01")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void updateRuleName_Without_Json_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name","test01")
                        .param("description","test02")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void updateRuleName_Without_Template_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("sqlStr","test05")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void updateRuleName_Without_SqlStr_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlPart","test06")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void updateRuleName_Without_SqlPart_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name","test01")
                        .param("description","test02")
                        .param("json","test03")
                        .param("template","test04")
                        .param("sqlStr","test05")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void deleteRuleName_Should_Return_Ok() throws Exception {
        when(ruleNameService.deleteRuleNameById(1)).thenReturn(new RuleName());
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
        verify(ruleNameService,times(1)).deleteRuleNameById(1);
    }

    @Test
    public void deleteRuleName_Should_Return_RuleNameList_If_Id_Not_Found() throws Exception {
        when(ruleNameService.deleteRuleNameById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
        verify(ruleNameService,times(1)).deleteRuleNameById(1);
    }
}