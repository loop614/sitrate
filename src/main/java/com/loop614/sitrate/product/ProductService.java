package com.loop614.sitrate.product;

import java.util.List;

import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.transfer.FilterProduct;

public interface ProductService
{
    Product save(Product newproduct);

    List<Product> find(FilterProduct product);

    List<Product> popular();
}
