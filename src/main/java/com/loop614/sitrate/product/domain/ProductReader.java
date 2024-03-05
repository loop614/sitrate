package com.loop614.sitrate.product.domain;

import java.util.List;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.ProductName;

public interface ProductReader {
    public PopularProductsResponse getTopRatedProducts();

    public List<ProductName> findNamesByIdIn(List<Long> ids);
}
