package com.loop614.sitrate.product;

import com.loop614.sitrate.product.domain.ProductReader;
import com.loop614.sitrate.product.domain.ProductWriter;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.repository.ProductRepository;
import com.loop614.sitrate.product.transfer.FilterProduct;
import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.ProductName;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductReader productReader;

    private final ProductWriter productWriter;

    private final ProductRepository productRepository;

    public ProductServiceImpl(
            ProductReader productReader,
            ProductWriter productWriter,
            ProductRepository productRepository) {
        this.productReader = productReader;
        this.productWriter = productWriter;
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return this.productWriter.save(product);
    }

    public List<Product> find(FilterProduct product) {
        return this.productRepository.findByCodeContainingAndNameContaining(product.getCode(), product.getName());
    }

    public PopularProductsResponse getTopRatedProducts() {
        return this.productReader.getTopRatedProducts();
    }

    public List<ProductName> findNamesByIdIn(List<Long> ids) {
        return this.productReader.findNamesByIdIn(ids);
    }
}
