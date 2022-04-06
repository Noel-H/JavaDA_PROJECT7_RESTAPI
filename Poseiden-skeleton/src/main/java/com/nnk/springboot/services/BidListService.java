package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * BidList Service
 */
@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;


    /**
     * Get a list of all bid
     * @return a list of all bid
     */
    public List<BidList> getBidListList() {
        return bidListRepository.findAll();
    }

    /**
     * Get a bid by his id
     * @param id is the id of the bid
     * @return the bid or an error if it is not found
     */
    public BidList getBidListById(int id){
        return bidListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BidList : " +id+ " not found"));
    }

    /**
     * Add a new bid
     * @param bid is the bid that need be added
     * @return the added bid
     */
    public BidList addBidList(BidList bid) {
        return bidListRepository.save(bid);
    }


    /**
     * Update a bid
     * @param id is the id of the bid that need to be updated
     * @param bidList is the new data for the update
     * @return the updated bid
     */
    public BidList updateBidListById(Integer id, BidList bidList) {
        BidList bidListToUpdate = getBidListById(id);
        bidListToUpdate.setAccount(bidList.getAccount());
        bidListToUpdate.setType(bidList.getType());
        bidListToUpdate.setBidQuantity(bidList.getBidQuantity());
        return bidListRepository.save(bidListToUpdate);
    }
}
