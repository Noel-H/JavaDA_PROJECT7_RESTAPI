package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class BidListServiceTest extends TestCase {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void getBidListList_Should_Work(){
        bidListService.getBidListList();
        verify(bidListRepository,times(1)).findAll();
    }

    @Test
    public void addBidList_Should_Work(){
        BidList bidList = new BidList();
        bidListService.addBidList(bidList);
        verify(bidListRepository,times(1)).save(bidList);
    }
}