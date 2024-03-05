package com.loop614.sitrate.product.transfer;

import java.math.BigDecimal;

public class TopRatedProduct {
    private String name;
    private BigDecimal rating;

    public TopRatedProduct() {}

    public TopRatedProduct(String name, BigDecimal rating) {
        this.name = name;
        this.rating = rating;
    }

    public TopRatedProduct(BigDecimal rating) {
        this.rating = rating;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRating() {
        return this.rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
