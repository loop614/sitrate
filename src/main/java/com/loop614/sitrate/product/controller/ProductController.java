package com.loop614.sitrate.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loop614.sitrate.product.ProductService;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.transfer.FilterProduct;
import com.loop614.sitrate.product.transfer.PopularProductsResponse;

@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/upsert")
    public ResponseEntity<Product> upsertProduct(@RequestBody Product product) {
        return new ResponseEntity<>(this.productService.upsert(product), HttpStatus.CREATED);
    }

    @GetMapping("/product/filter")
    public ResponseEntity<List<Product>> filterProduct(@ModelAttribute FilterProduct product) {
        return new ResponseEntity<>(this.productService.find(product), HttpStatus.OK);
    }

    @GetMapping("/product/popular")
    public ResponseEntity<PopularProductsResponse> popularProduct() {
        return new ResponseEntity<>(this.productService.getTopRatedProducts(), HttpStatus.OK);
    }
}
