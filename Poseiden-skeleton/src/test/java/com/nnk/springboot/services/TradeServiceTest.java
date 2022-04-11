package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeServiceTest extends TestCase {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    public void getTradeList_Should_Get_All_Trade(){
        when(tradeRepository.findAll()).thenReturn(new ArrayList<>());
        tradeService.getTradeList();
        verify(tradeRepository,times(1)).findAll();
    }

    @Test
    public void getTradeById_Should_Get_Trade(){
        when(tradeRepository.findById(1)).thenReturn(Optional.of(new Trade()));
        tradeService.getTradeById(1);
        verify(tradeRepository,times(1)).findById(1);
    }

    @Test
    public void addTrade_Should_Add_Trade(){
        Trade trade = new Trade();
        when(tradeRepository.save(trade)).thenReturn(new Trade());
        tradeService.addTrade(trade);
        verify(tradeRepository,times(1)).save(trade);
    }

    @Test
    public void updateTrade_Should_Update_Trade(){
        Trade tradeToUpdate = new Trade();
        tradeToUpdate.setTradeId(1);

        Trade trade = new Trade("testAccount","testType",100.50);

        Trade expectedTradeSave = new Trade("testAccount","testType",100.50);
        expectedTradeSave.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(tradeToUpdate));
        when(tradeRepository.save(tradeToUpdate)).thenReturn(new Trade());

        tradeService.updateTradeById(1,trade);

        verify(tradeRepository,times(1)).save(expectedTradeSave);
    }

    @Test
    public void deleteTradeById_Should_Delete_Trade(){
        Trade tradeToDelete = new Trade();
        tradeToDelete.setTradeId(1);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(tradeToDelete));
        tradeService.deleteTradeById(1);
        verify(tradeRepository,times(1)).delete(tradeToDelete);
    }
}