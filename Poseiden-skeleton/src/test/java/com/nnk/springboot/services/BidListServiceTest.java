package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class BidListServiceTest extends TestCase {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void getBidListList_Should_Get_All_Bid(){
        when(bidListRepository.findAll()).thenReturn(new ArrayList<>());
        bidListService.getBidListList();
        verify(bidListRepository,times(1)).findAll();
    }

    @Test
    public void getBidListById_Should_Get_Bid(){
        when(bidListRepository.findById(1)).thenReturn(Optional.of(new BidList()));
        bidListService.getBidListById(1);
        verify(bidListRepository,times(1)).findById(1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getBidListById_Should_Throw_Exception(){
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());
        bidListService.getBidListById(1);
        verify(bidListRepository,times(1)).findById(1);
    }

    @Test
    public void addBidList_Should_Add_Bid(){
        BidList bidList = new BidList();
        when(bidListRepository.save(bidList)).thenReturn(new BidList());
        bidListService.addBidList(bidList);
        verify(bidListRepository,times(1)).save(bidList);
    }

    @Test
    public void updateBidList_Should_Update_Bid(){
        BidList bidListToUpdate = new BidList();
        bidListToUpdate.setBidListId(1);

        BidList bidList = new BidList("testAccount","testType",100.50);

        BidList expectedBidListSave = new BidList("testAccount","testType",100.50);
        expectedBidListSave.setBidListId(1);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidListToUpdate));
        when(bidListRepository.save(bidListToUpdate)).thenReturn(new BidList());

        bidListService.updateBidListById(1,bidList);

        verify(bidListRepository,times(1)).save(expectedBidListSave);
    }

    @Test
    public void deleteBidListById_Should_Delete_Bid(){
        BidList bidListToDelete = new BidList();
        bidListToDelete.setBidListId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidListToDelete));
        bidListService.deleteBidListById(1);
        verify(bidListRepository,times(1)).delete(bidListToDelete);
    }
}