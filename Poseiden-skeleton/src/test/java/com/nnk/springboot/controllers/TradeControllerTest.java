package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TradeControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private UserService userService;

    @Test
    public void home_Should_Return_Ok() throws Exception{
        when(tradeService.getTradeList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    public void addTradeForm_Should_Return_Ok() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void validate_Should_Return_Ok() throws Exception {
        when(tradeService.addTrade(any())).thenReturn(new Trade());
        when(tradeService.getTradeList()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/trade/validate")
                        .param("account","test")
                        .param("type","testType")
                        .param("buyQuantity","10.0")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    public void validate_Without_Account_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("type","testType")
                        .param("buyQuantity","10.0")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void validate_Without_Type_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account","test")
                        .param("buyQuantity","10.0")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void validate_Without_Buy_Quantity_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account","test")
                        .param("type","testType")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void validate_Without_Double_Type_In_Buy_Quantity_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account","test")
                        .param("type","testType")
                        .param("buyQuantity","test123")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void showUpdateForm_Should_Return_Ok() throws Exception {
        Trade trade = new Trade("testAccount","testType",100.50);
        when(tradeService.getTradeById(1)).thenReturn(trade);
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
        verify(tradeService,times(1)).getTradeById(1);
    }

    @Test
    public void showUpdateForm_Should_Return_TradeList_If_Id_Not_Found() throws Exception {
        when(tradeService.getTradeById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
        verify(tradeService,times(1)).getTradeById(1);
    }

    @Test
    public void updateTrade_Should_Return_Ok() throws Exception {
        Trade trade = new Trade("newTestAccount","newTestType",555.55);
        when(tradeService.updateTradeById(1,trade)).thenReturn(new Trade());
        mockMvc.perform(post("/trade/update/1")
                        .param("account","newTestAccount")
                        .param("type","newTestType")
                        .param("buyQuantity","555.55")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
        verify(tradeService,times(1)).updateTradeById(1,trade);
    }

    @Test
    public void updateTrade_Without_Account_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("type","newTestType")
                        .param("buyQuantity","555.55")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void updateTrade_Without_Type_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("account","newTestAccount")
                        .param("buyQuantity","555.55")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void updateTrade_Without_Buy_Quantity_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("account","newTestAccount")
                        .param("type","newTestType")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void updateTrade_Without_Double_Type_In_Buy_Quantity_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("account","newTestAccount")
                        .param("type","newTestType")
                        .param("buyQuantity","test123")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void deleteTrade_Should_Return_Ok() throws Exception {
        when(tradeService.deleteTradeById(1)).thenReturn(new Trade());
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
        verify(tradeService,times(1)).deleteTradeById(1);
    }

    @Test
    public void deleteTrade_Should_Return_TradeList_If_Id_Not_Found() throws Exception {
        when(tradeService.deleteTradeById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
        verify(tradeService,times(1)).deleteTradeById(1);
    }
}