package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Rating Service
 */
@Service
public class RatingService {
    
    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Get a list of all rating
     * @return a list of all rating
     */
    public List<Rating> getRatingList() {
        return ratingRepository.findAll();
    }

    /**
     * Get a rating by his id
     * @param id is the id of the rating
     * @return the rating or an error if it is not found
     */
    public Rating getRatingById(int id){
        return ratingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating : " +id+ " not found"));
    }

    /**
     * Add a new rating
     * @param rating is the rating that need be added
     * @return the added rating
     */
    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }


    /**
     * Update a rating
     * @param id is the id of the rating that need to be updated
     * @param rating is the new data for the update
     * @return the updated rating
     */
    public Rating updateRatingById(Integer id, Rating rating) {
        Rating ratingToUpdate = getRatingById(id);
        ratingToUpdate.setMoodysRating(rating.getMoodysRating());
        ratingToUpdate.setSandPRating(rating.getSandPRating());
        ratingToUpdate.setFitchRating(rating.getFitchRating());
        ratingToUpdate.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.save(ratingToUpdate);
    }

    /**
     * Delete a rating
     * @param id is the id of the rating that need to be deleted
     * @return the deleted rating
     */
    public Rating deleteRatingById(Integer id) {
        Rating ratingToDelete = getRatingById(id);
        ratingRepository.delete(ratingToDelete);
        return ratingToDelete;
    }
}
