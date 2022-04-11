package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameServiceTest extends TestCase {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    public void getRuleNameList_Should_Get_All_RuleName(){
        when(ruleNameRepository.findAll()).thenReturn(new ArrayList<>());
        ruleNameService.getRuleNameList();
        verify(ruleNameRepository,times(1)).findAll();
    }

    @Test
    public void getRuleNameById_Should_Get_RuleName(){
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(new RuleName()));
        ruleNameService.getRuleNameById(1);
        verify(ruleNameRepository,times(1)).findById(1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getRuleNameById_Should_Throw_Exception(){
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        ruleNameService.getRuleNameById(1);
        verify(ruleNameRepository,times(1)).findById(1);
    }

    @Test
    public void addRuleName_Should_Add_RuleName(){
        RuleName ruleName = new RuleName();
        when(ruleNameRepository.save(ruleName)).thenReturn(new RuleName());
        ruleNameService.addRuleName(ruleName);
        verify(ruleNameRepository,times(1)).save(ruleName);
    }

    @Test
    public void updateRuleName_Should_Update_RuleName(){
        RuleName ruleNameToUpdate = new RuleName();
        ruleNameToUpdate.setId(1);

        RuleName ruleName = new RuleName("test01","test02","test03","test04","test05","test06");

        RuleName expectedRuleNameSave = new RuleName("test01","test02","test03","test04","test05","test06");
        expectedRuleNameSave.setId(1);

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleNameToUpdate));
        when(ruleNameRepository.save(ruleNameToUpdate)).thenReturn(new RuleName());

        ruleNameService.updateRuleNameById(1,ruleName);

        verify(ruleNameRepository,times(1)).save(expectedRuleNameSave);
    }

    @Test
    public void deleteRuleNameById_Should_Delete_RuleName(){
        RuleName ruleNameToDelete = new RuleName();
        ruleNameToDelete.setId(1);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleNameToDelete));
        ruleNameService.deleteRuleNameById(1);
        verify(ruleNameRepository,times(1)).delete(ruleNameToDelete);
    }
}