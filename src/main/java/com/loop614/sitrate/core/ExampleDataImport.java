package com.loop614.sitrate.core;

import com.loop614.sitrate.product.ProductService;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.review.ReviewService;
import com.loop614.sitrate.review.entity.Review;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleDataImport {
    private static final int RANDOM_PRODUCT_COUNT = 10;

    private static final int RANDOM_REVIEW_PER_PRODUCT = 1000;

    @Bean
    CommandLineRunner productCommandLineRunner(ProductService productService, ReviewService reviewService) {
        return args -> {
            this.addMultipleRandomProducts(productService, reviewService);
        };
    }

    private void addMultipleRandomProducts(ProductService productService, ReviewService reviewService) {
        Random rand = new Random();
        for (int i = 0; i < RANDOM_PRODUCT_COUNT; i++) {
            int randomNum = rand.nextInt((10000 - 100) + 1) + 100;
            Product newproduct = new Product(
                "Code " + String.valueOf(i),
                "Product " + String.valueOf(i),
                BigDecimal.valueOf(randomNum),
                "Product description " + String.valueOf(i));
            Product savedproduct = productService.save(newproduct);

            List<Review> reviews = new ArrayList<Review>();

            for (int j = 0; j < RANDOM_REVIEW_PER_PRODUCT; j++) {
                int randomNum2 = rand.nextInt((10 - 0) + 1) + 0;
                Review newreivew = new Review(
                    "Review " + String.valueOf(i),
                    "tom wrote this",
                    BigDecimal.valueOf(randomNum2));
                newreivew.setProduct(savedproduct);
                reviews.add(newreivew);
            }
            reviewService.saveAll(reviews);
        }
        System.out.println("added " + RANDOM_PRODUCT_COUNT + " random products");
        System.out.println("added " + RANDOM_REVIEW_PER_PRODUCT + " random reviews per product");
    }
}
