package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceTest extends TestCase {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    public void getCurvePointList_Should_Get_All_Bid(){
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<>());
        curvePointService.getCurvePointList();
        verify(curvePointRepository,times(1)).findAll();
    }

    @Test
    public void getCurvePointById_Should_Get_Bid(){
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(new CurvePoint()));
        curvePointService.getCurvePointById(1);
        verify(curvePointRepository,times(1)).findById(1);
    }

    @Test
    public void addCurvePoint_Should_Add_Bid(){
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepository.save(curvePoint)).thenReturn(new CurvePoint());
        curvePointService.addCurvePoint(curvePoint);
        verify(curvePointRepository,times(1)).save(curvePoint);
    }

    @Test
    public void updateCurvePoint_Should_Update_Bid(){
        CurvePoint curvePointToUpdate = new CurvePoint();
        curvePointToUpdate.setId(1);

        CurvePoint curvePoint = new CurvePoint(1,1.1,111.11);

        CurvePoint expectedCurvePointSave = new CurvePoint(1,1.1,111.11);
        expectedCurvePointSave.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePointToUpdate));
        when(curvePointRepository.save(curvePointToUpdate)).thenReturn(new CurvePoint());

        curvePointService.updateCurvePointById(1,curvePoint);

        verify(curvePointRepository,times(1)).save(expectedCurvePointSave);
    }

    @Test
    public void deleteCurvePointById_Should_Delete_Bid(){
        CurvePoint curvePointToDelete = new CurvePoint();
        curvePointToDelete.setId(1);
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePointToDelete));
        curvePointService.deleteCurvePointById(1);
        verify(curvePointRepository,times(1)).delete(curvePointToDelete);
    }
}