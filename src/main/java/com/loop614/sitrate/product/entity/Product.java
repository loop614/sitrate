package com.loop614.sitrate.product.entity;

import jakarta.persistence.*;

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

    @Column(name = "PRICE_EUR")
    private double priceEur;

    @Column(name = "PRICE_USD")
    private double priceUsd;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    public Product() {}

    public Product(String code, String name, double priceEur, String description) {
        this.code = code;
        this.name = name;
        this.priceEur = priceEur;
        this.description = description;
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

    public double getPriceEur() {
        return this.priceEur;
    }

    public double getPriceUsd() {
        return this.priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }
}
