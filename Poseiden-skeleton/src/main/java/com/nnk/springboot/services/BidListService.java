package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;


    public List<BidList> getBidListList() {
        return bidListRepository.findAll();
    }

    public BidList getBidListById(int id){
        return bidListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BidList : " +id+ " not found"));
    }
}
