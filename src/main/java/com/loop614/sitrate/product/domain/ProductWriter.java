package com.loop614.sitrate.product.domain;

import com.loop614.sitrate.product.entity.Product;

public interface ProductWriter {
    public Product upsert(Product product);
}
