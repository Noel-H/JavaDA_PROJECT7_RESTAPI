package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RatingServiceTest extends TestCase {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void getRatingList_Should_Get_All_Rating(){
        when(ratingRepository.findAll()).thenReturn(new ArrayList<>());
        ratingService.getRatingList();
        verify(ratingRepository,times(1)).findAll();
    }

    @Test
    public void getRatingById_Should_Get_Rating(){
        when(ratingRepository.findById(1)).thenReturn(Optional.of(new Rating()));
        ratingService.getRatingById(1);
        verify(ratingRepository,times(1)).findById(1);
    }

    @Test
    public void addRating_Should_Add_Rating(){
        Rating rating = new Rating();
        when(ratingRepository.save(rating)).thenReturn(new Rating());
        ratingService.addRating(rating);
        verify(ratingRepository,times(1)).save(rating);
    }

    @Test
    public void updateRating_Should_Update_Rating(){
        Rating ratingToUpdate = new Rating();
        ratingToUpdate.setId(1);

        Rating rating = new Rating("test01","test02","test03",1);

        Rating expectedRatingSave = new Rating("test01","test02","test03",1);
        expectedRatingSave.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingToUpdate));
        when(ratingRepository.save(ratingToUpdate)).thenReturn(new Rating());

        ratingService.updateRatingById(1,rating);

        verify(ratingRepository,times(1)).save(expectedRatingSave);
    }

    @Test
    public void deleteRatingById_Should_Delete_Rating(){
        Rating ratingToDelete = new Rating();
        ratingToDelete.setId(1);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingToDelete));
        ratingService.deleteRatingById(1);
        verify(ratingRepository,times(1)).delete(ratingToDelete);
    }
}