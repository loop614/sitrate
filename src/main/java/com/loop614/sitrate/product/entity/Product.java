package com.loop614.sitrate.product.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE", unique = true, length = 32, nullable = false)
    private String code;

    @Column(name = "NAME", length = 255, nullable = false)
    private String name;

    @Column(name = "PRICE_EUR", scale = 15, precision = 30)
    private BigDecimal priceEur;

    @Column(name = "PRICE_USD", scale = 15, precision = 30)
    private BigDecimal priceUsd;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    public Product() {}

    public Product(String code, String name, BigDecimal priceEur, String description) {
        this.code = code;
        this.name = name;
        this.priceEur = priceEur;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPriceEur() {
        return this.priceEur;
    }

    public BigDecimal getPriceUsd() {
        return this.priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }
}
