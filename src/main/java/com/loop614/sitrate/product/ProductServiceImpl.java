package com.loop614.sitrate.product;

import com.loop614.sitrate.product.domain.ProductWriter;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.repository.ProductRepository;
import com.loop614.sitrate.product.transfer.FilterProduct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductWriter productWriter;

    public ProductServiceImpl(ProductWriter productWriter, ProductRepository productRepository) {
        this.productWriter = productWriter;
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return this.productWriter.save(product);
    }

    public List<Product> find(FilterProduct product) {
        return this.productRepository.findByCodeContainingAndNameContaining(product.getCode(), product.getName());
    }

    public List<Product> popular() {
        return new ArrayList<Product>();
    }
}
