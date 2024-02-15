package com.loop614.sitrate.product.domain;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;

public interface ProductReader {
    public PopularProductsResponse getTopRatedProducts();
}
