package com.loop614.sitrate.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.loop614.sitrate.product.ProductService;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.review.ReviewService;
import com.loop614.sitrate.review.entity.Review;

@Configuration
public class ExampleDataImport {

    private static final int RANDOM_PRODUCT_COUNT = 10;

    private static final int RANDOM_REVIEW_PER_PRODUCT = 1000;

    @Bean
    CommandLineRunner productCommandLineRunner(ProductService productService, ReviewService reviewService) {
        return args -> {
            this.addSamsungGalaxy(productService, reviewService);
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
            Product savedproduct = productService.upsert(newproduct);

            List<Review> reviews = new ArrayList<>();

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

    private void addSamsungGalaxy(ProductService productService, ReviewService reviewService) {
        Product newproduct = new Product("SGS23", "Samsung Galaxy S23", BigDecimal.valueOf(23.23), "Samsung Galaxy S23 description");
        Product savedproduct = productService.upsert(newproduct);
        System.out.println("new product created with id " + savedproduct.getId());

        Review newreivew = new Review("Tom", "tom wrote this", BigDecimal.valueOf(1.1));
        newreivew.setProduct(savedproduct);
        Review savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());

        newreivew = new Review("Tommy", "tommy wrote this", BigDecimal.valueOf(1.2));
        newreivew.setProduct(savedproduct);
        savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());
    }
}
