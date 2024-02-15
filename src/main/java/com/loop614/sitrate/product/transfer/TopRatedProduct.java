package com.loop614.sitrate.product.transfer;

public class TopRatedProduct {
    private String name;
    private double rating;

    public TopRatedProduct() {}

    public TopRatedProduct(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
