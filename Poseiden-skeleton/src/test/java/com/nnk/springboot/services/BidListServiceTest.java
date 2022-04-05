package com.nnk.springboot.services;

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
    public void getBidListList_Should_Return_List(){
        bidListService.getBidListList();
        verify(bidListRepository,times(1)).findAll();
    }
}