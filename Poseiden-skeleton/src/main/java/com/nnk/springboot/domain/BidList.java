package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * BidList Domain
 */
@Entity
@Data
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer BidListId;

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    @Digits(integer = 5,fraction = 2)
    @NotNull
    private Double bidQuantity;

    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public BidList(String account_test, String type_test, double v) {
        account = account_test;
        type = type_test;
        bidQuantity = v;
    }

    public BidList() {

    }
}
