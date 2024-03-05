package com.loop614.sitrate.product.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PopularProductsResponse {
    public List<TopRatedProduct> popularProducts;

    public PopularProductsResponse() {
    }

    public PopularProductsResponse(List<TopRatedProduct> popularProducts) {
        this.popularProducts = popularProducts;
    }

    public PopularProductsResponse(Collection<TopRatedProduct> popularProducts) {
        this.popularProducts = new ArrayList<TopRatedProduct>(popularProducts);
    }
}
