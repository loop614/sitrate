package com.loop614.sitrate.product;

import java.util.List;

import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.transfer.FilterProduct;
import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.ProductName;

public interface ProductService {
    Product save(Product newproduct);

    List<Product> find(FilterProduct product);

    PopularProductsResponse getTopRatedProducts();

    List<ProductName> findNamesByIdIn(List<Long> arrayList);
}
