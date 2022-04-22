package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * RuleName Service
 */
@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Get a list of all ruleName
     * @return a list of all ruleName
     */
    public List<RuleName> getRuleNameList() {
        return ruleNameRepository.findAll();
    }

    /**
     * Get a ruleName by his id
     * @param id is the id of the ruleName
     * @return the ruleName or an error if it is not found
     */
    public RuleName getRuleNameById(int id){
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RuleName : " +id+ " not found"));
    }

    /**
     * Add a new ruleName
     * @param ruleName is the ruleName that need be added
     * @return the added ruleName
     */
    public RuleName addRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }


    /**
     * Update a ruleName
     * @param id is the id of the ruleName that need to be updated
     * @param ruleName is the new data for the update
     * @return the updated ruleName
     */
    public RuleName updateRuleNameById(Integer id, RuleName ruleName) {
        RuleName ruleNameToUpdate = getRuleNameById(id);
        ruleNameToUpdate.setName(ruleName.getName());
        ruleNameToUpdate.setDescription(ruleName.getDescription());
        ruleNameToUpdate.setJson(ruleName.getJson());
        ruleNameToUpdate.setTemplate(ruleName.getTemplate());
        ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
        ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
        return ruleNameRepository.save(ruleNameToUpdate);
    }

    /**
     * Delete a ruleName
     * @param id is the id of the ruleName that need to be deleted
     * @return the deleted ruleName
     */
    public RuleName deleteRuleNameById(Integer id) {
        RuleName ruleNameToDelete = getRuleNameById(id);
        ruleNameRepository.delete(ruleNameToDelete);
        return ruleNameToDelete;
    }
}
