package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(BidListController.class)
public class BidListControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @Test
    public void home_Should_Return_Ok() throws Exception{
        when(bidListService.getBidListList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    public void addBidForm_Should_Return_Ok() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void validate_Should_Return_Ok() throws Exception {
        when(bidListService.addBidList(any())).thenReturn(new BidList());
        when(bidListService.getBidListList()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/bidList/validate")
                        .param("account","test")
                        .param("type","testType")
                        .param("bidQuantity","10.0" )
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    public void validate_Without_Account_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("type","testType")
                        .param("bidQuantity","10.0" )
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void validate_Without_Type_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account","test")
                        .param("bidQuantity","10.0" )
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void validate_Without_Bid_Quantity_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account","test")
                        .param("type","testType")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void validate_Without_Double_Type_In_Bid_Quantity_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account","test")
                        .param("type","testType")
                        .param("bidQuantity","test123" )
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }
}