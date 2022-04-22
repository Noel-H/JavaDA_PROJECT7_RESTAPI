package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Trade Service
 */
@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Get a list of all trade
     * @return a list of all trade
     */
    public List<Trade> getTradeList() {
        return tradeRepository.findAll();
    }

    /**
     * Get a trade by his id
     * @param id is the id of the trade
     * @return the trade or an error if it is not found
     */
    public Trade getTradeById(int id){
        return tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade : " +id+ " not found"));
    }

    /**
     * Add a new trade
     * @param trade is the trade that need be added
     * @return the added trade
     */
    public Trade addTrade(Trade trade) {
        return tradeRepository.save(trade);
    }


    /**
     * Update a trade
     * @param id is the id of the trade that need to be updated
     * @param trade is the new data for the update
     * @return the updated trade
     */
    public Trade updateTradeById(Integer id, Trade trade) {
        Trade tradeToUpdate = getTradeById(id);
        tradeToUpdate.setAccount(trade.getAccount());
        tradeToUpdate.setType(trade.getType());
        tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
        return tradeRepository.save(tradeToUpdate);
    }

    /**
     * Delete a trade
     * @param id is the id of the trade that need to be deleted
     * @return the deleted trade
     */
    public Trade deleteTradeById(Integer id) {
        Trade tradeToDelete = getTradeById(id);
        tradeRepository.delete(tradeToDelete);
        return tradeToDelete;
    }
}
