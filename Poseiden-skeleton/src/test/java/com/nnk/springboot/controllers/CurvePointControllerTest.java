package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
@WebMvcTest(CurveController.class)
public class CurvePointControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @Test
    public void home_Should_Return_Ok() throws Exception{
        when(curvePointService.getCurvePointList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    public void addBidForm_Should_Return_Ok() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate_Should_Return_Ok() throws Exception {
        when(curvePointService.addCurvePoint(any())).thenReturn(new CurvePoint());
        when(curvePointService.getCurvePointList()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId","1")
                        .param("term","1.1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    public void validate_Without_CurveId_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("term","1.1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate_Without_Term_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId","1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate_Without_Value_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId","1")
                        .param("term","1.1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate_Without_Int_Type_In_CurveId_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId","testType")
                        .param("term","1.1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate_Without_Double_Type_In_Term_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId","1")
                        .param("term","testType")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate_Without_Double_Type_In_Value_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId","1")
                        .param("term","1.1")
                        .param("value","testType")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void showUpdateForm_Should_Return_Ok() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,1.1,111.11);
        when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
        verify(curvePointService,times(1)).getCurvePointById(1);
    }

    @Test
    public void showUpdateForm_Should_Return_CurvePointList_If_Id_Not_Found() throws Exception {
        when(curvePointService.getCurvePointById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
        verify(curvePointService,times(1)).getCurvePointById(1);
    }

    @Test
    public void updateBid_Should_Return_Ok() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1,1.1,111.11);
        curvePoint.setId(1);
        when(curvePointService.updateCurvePointById(1,curvePoint)).thenReturn(new CurvePoint());
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId","1")
                        .param("term","1.1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
        verify(curvePointService,times(1)).updateCurvePointById(1, curvePoint);
    }

    @Test
    public void updateBid_Without_CurveId_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("term","1.1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void updateBid_Without_Term_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId","1")
                        .param("value","111.11")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void updateBid_Without_Value_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId","1")
                        .param("term","1.1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void updateBid_Without_Double_Type_In_Value_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId","1")
                        .param("term","1.1")
                        .param("value","test123")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void deleteBid_Should_Return_Ok() throws Exception {
        when(curvePointService.deleteCurvePointById(1)).thenReturn(new CurvePoint());
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
        verify(curvePointService,times(1)).deleteCurvePointById(1);
    }

    @Test
    public void deleteBid_Should_Return_CurvePointList_If_Id_Not_Found() throws Exception {
        when(curvePointService.deleteCurvePointById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
        verify(curvePointService,times(1)).deleteCurvePointById(1);
    }
}