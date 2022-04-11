package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
@WebMvcTest(RatingController.class)
public class RatingControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    public void home_Should_Return_Ok() throws Exception{
        when(ratingService.getRatingList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    public void addRatingForm_Should_Return_Ok() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void validate_Should_Return_Ok() throws Exception {
        when(ratingService.addRating(any())).thenReturn(new Rating());
        when(ratingService.getRatingList()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating","test01")
                        .param("sandPRating","test02")
                        .param("fitchRating","test03")
                        .param("orderNumber","10")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    public void validate_Without_MoodysRating_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("sandPRating","test02")
                        .param("fitchRating","test03")
                        .param("orderNumber","10")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void validate_Without_SandPRating_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating","test01")
                        .param("fitchRating","test03")
                        .param("orderNumber","10")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void validate_Without_FitchRating_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating","test01")
                        .param("sandPRating","test02")
                        .param("orderNumber","10")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void validate_Without_OrderNumber_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating","test01")
                        .param("sandPRating","test02")
                        .param("fitchRating","test03")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void validate_Without_Int_Type_In_OrderNumber_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating","test01")
                        .param("sandPRating","test02")
                        .param("fitchRating","test03")
                        .param("orderNumber","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void showUpdateForm_Should_Return_Ok() throws Exception {
        Rating rating = new Rating("test01","test02","test03",10);
        when(ratingService.getRatingById(1)).thenReturn(rating);
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
        verify(ratingService,times(1)).getRatingById(1);
    }

    @Test
    public void showUpdateForm_Should_Return_RatingList_If_Id_Not_Found() throws Exception {
        when(ratingService.getRatingById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
        verify(ratingService,times(1)).getRatingById(1);
    }

    @Test
    public void updateRating_Should_Return_Ok() throws Exception {
        Rating rating = new Rating("newTest01","newTest02","newTest03",1111);
        rating.setId(1);
        when(ratingService.updateRatingById(1,rating)).thenReturn(new Rating());
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating","newTest01")
                        .param("sandPRating","newTest02")
                        .param("fitchRating","newTest03")
                        .param("orderNumber","1111")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
        verify(ratingService,times(1)).updateRatingById(1,rating);
    }

    @Test
    public void updateRating_Without_MoodysRating_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("sandPRating","newTest02")
                        .param("fitchRating","newTest03")
                        .param("orderNumber","1111")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void updateRating_Without_SandPRating_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating","newTest01")
                        .param("fitchRating","newTest03")
                        .param("orderNumber","1111")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void updateRating_Without_FitchRating_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating","newTest01")
                        .param("sandPRating","newTest02")
                        .param("orderNumber","1111")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void updateRating_Without_OrderNumber_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating","newTest01")
                        .param("sandPRating","newTest02")
                        .param("fitchRating","newTest03")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void updateRating_Without_Int_Type_In_OrderNumber_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating","newTest01")
                        .param("sandPRating","newTest02")
                        .param("fitchRating","newTest03")
                        .param("orderNumber","newTest04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void deleteRating_Should_Return_Ok() throws Exception {
        when(ratingService.deleteRatingById(1)).thenReturn(new Rating());
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
        verify(ratingService,times(1)).deleteRatingById(1);
    }

    @Test
    public void deleteRating_Should_Return_RatingList_If_Id_Not_Found() throws Exception {
        when(ratingService.deleteRatingById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
        verify(ratingService,times(1)).deleteRatingById(1);
    }
}