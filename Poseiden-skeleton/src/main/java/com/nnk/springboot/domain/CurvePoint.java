package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * CurvePoint Domain
 */
@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer curveId;

    private Timestamp asOfDate;

    @Digits(integer = 5,fraction = 2)
    @NotNull
    private Double term;

    @Digits(integer = 5,fraction = 2)
    @NotNull
    private Double value;

    private Timestamp creationDate;

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint() {

    }
}
