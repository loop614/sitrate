package com.loop614.sitrate.product.controller;

import com.loop614.sitrate.product.ProductService;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.transfer.FilterProduct;
import com.loop614.sitrate.product.transfer.PopularProductsResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/new")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<Product>(this.productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/product/filter")
    public ResponseEntity<List<Product>> filterProduct(@ModelAttribute FilterProduct product) {
        return new ResponseEntity<List<Product>>(this.productService.find(product), HttpStatus.OK);
    }

    @GetMapping("/product/popular")
    public ResponseEntity<PopularProductsResponse> popularProduct() {
        return new ResponseEntity<PopularProductsResponse>(this.productService.getTopRatedProducts(), HttpStatus.OK);
    }
}
