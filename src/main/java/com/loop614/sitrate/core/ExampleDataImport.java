package com.loop614.sitrate.core;

import com.loop614.sitrate.product.ProductService;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.review.ReviewService;
import com.loop614.sitrate.review.entity.Review;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleDataImport {
    @Bean
    CommandLineRunner productCommandLineRunner(ProductService productService, ReviewService reviewService) {
        return args -> {
            this.addFirstProduct(productService, reviewService);
            this.addSecondProduct(productService, reviewService);
            this.addThirdProduct(productService, reviewService);
            this.addFourthProduct(productService, reviewService);
            this.addFifthProduct(productService, reviewService);
        };
    }

    private void addFirstProduct(ProductService productService, ReviewService reviewService) {
        Product newproduct = new Product("SGS23", "Samsung Galaxy S23", BigDecimal.valueOf(23.23), "Samsung Galaxy S23 description");
        Product savedproduct = productService.save(newproduct);
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

    private void addSecondProduct(ProductService productService, ReviewService reviewService) {
        Product newproduct = new Product("IPSE", "iPhone SE", BigDecimal.valueOf(52), "iPhone SE description");
        Product savedproduct = productService.save(newproduct);
        System.out.println("new product created with id " + savedproduct.getId());


        Review newreivew = new Review("Marc", "marc wrote this", BigDecimal.valueOf(2.1));
        newreivew.setProduct(savedproduct);
        Review savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());

        newreivew = new Review("Martin", "martin wrote this", BigDecimal.valueOf(2.2));
        newreivew.setProduct(savedproduct);
        savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());
    }

    private void addThirdProduct(ProductService productService, ReviewService reviewService) {
        Product newproduct = new Product("X13", "Xiaomi 13", BigDecimal.valueOf(13.13), "Xiaomi 13 description");
        Product savedproduct = productService.save(newproduct);
        System.out.println("new product created with id " + savedproduct.getId());

        Review newreivew = new Review("David", "david wrote this", BigDecimal.valueOf(3.1));
        newreivew.setProduct(savedproduct);
        Review savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());

        newreivew = new Review("Donatelo", "donatelo wrote this", BigDecimal.valueOf(3.2));
        newreivew.setProduct(savedproduct);
        savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());
    }

    private void addFourthProduct(ProductService productService, ReviewService reviewService) {
        Product newproduct = new Product("H12", "Honor 12", BigDecimal.valueOf(12.12), "Honor 12 description");
        Product savedproduct = productService.save(newproduct);
        System.out.println("new product created with id " + savedproduct.getId());

        Review newreivew = new Review("Elizabeth", "elizabeth wrote this", BigDecimal.valueOf(4.1));
        newreivew.setProduct(savedproduct);
        Review savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());

        newreivew = new Review("Petra", "petra wrote this", BigDecimal.valueOf(4.2));
        newreivew.setProduct(savedproduct);
        savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());
    }

    private void addFifthProduct(ProductService productService, ReviewService reviewService) {
        Product newproduct = new Product("N23", "Nokia 23", BigDecimal.valueOf(23.23), "Nokia 23 description");
        Product savedproduct = productService.save(newproduct);
        System.out.println("new product created with id " + savedproduct.getId());

        Review newreivew = new Review("Wilma", "wilma wrote this", BigDecimal.valueOf(5.1));
        newreivew.setProduct(savedproduct);
        Review savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());

        newreivew = new Review("Silvia", "silvia wrote this", BigDecimal.valueOf(5.2));
        newreivew.setProduct(savedproduct);
        savedreview = reviewService.save(newreivew);
        System.out.println("new reivew created with id " + savedreview.getId());
    }
}
