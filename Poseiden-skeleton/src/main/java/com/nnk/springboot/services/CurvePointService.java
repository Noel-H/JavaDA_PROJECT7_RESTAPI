package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * CurvePoint Service
 */
@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * find a list of all curvePoint
     * @return a list of all curvePoint
     */
    public List<CurvePoint> getCurvePointList() {
        return curvePointRepository.findAll();
    }

    /**
     * Add a new curvePoint
     * @param curvePoint is the curvePoint that need be added
     * @return the added curvePoint
     */
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Get a curvePoint by his id
     * @param id is the id of the curvePoint
     * @return the curvePoint or an error if it is not found
     */
    public CurvePoint getCurvePointById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CurvePoint : " +id+ " not found"));
    }

    /**
     * Update a curvePoint
     * @param id is the id of the curvePoint that need to be updated
     * @param curvePoint is the new data for the update
     * @return the updated curvePoint
     */
    public CurvePoint updateCurvePointById(Integer id, CurvePoint curvePoint) {
        CurvePoint curvePointToUpdate = getCurvePointById(id);
        curvePointToUpdate.setCurveId(curvePoint.getCurveId());
        curvePointToUpdate.setTerm(curvePoint.getTerm());
        curvePointToUpdate.setValue(curvePoint.getValue());
        return curvePointRepository.save(curvePointToUpdate);
    }

    /**
     * Delete a curvePoint
     * @param id is the id of the curvePoint that need to be deleted
     * @return the deleted curvePoint
     */
    public CurvePoint deleteCurvePointById(Integer id) {
        CurvePoint curvePointToDelete = getCurvePointById(id);
        curvePointRepository.delete(curvePointToDelete);
        return curvePointToDelete;
    }
}
