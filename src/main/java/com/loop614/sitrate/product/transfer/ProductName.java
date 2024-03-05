package com.loop614.sitrate.product.transfer;

public class ProductName {
    private Long id;
    private String name;

    public ProductName() {}

    public ProductName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
