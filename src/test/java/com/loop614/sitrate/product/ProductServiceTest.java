package com.loop614.sitrate.product;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.transfer.FilterProduct;
import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.TopRatedProduct;
import com.loop614.sitrate.review.ReviewService;
import com.loop614.sitrate.review.entity.Review;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductServiceTest {
    @Autowired
    private final ProductService productService;

    private final ReviewService reviewService;

    @Autowired
    public ProductServiceTest(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @Test
    public void savefindOkTest() {
        String exampleCode = "example code";
        String exampleName = "example name";
        Product exampleProduct = new Product(
            exampleCode,
            exampleName,
            BigDecimal.valueOf(2.2),
            "example description"
        );
        Product savedProduct = this.productService.upsert(exampleProduct);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getId() > 0);

        FilterProduct filterProduct = new FilterProduct(exampleCode, exampleName);
        List<Product> foundProducts = this.productService.find(filterProduct);
        boolean itWasFound = false;
        for (Product product : foundProducts) {
            if (product.getId() == savedProduct.getId()) {
                itWasFound = true;
                break;
            }
        }
        Assertions.assertTrue(itWasFound);
    }

    @Test
    public void getPopularProductsOkTest() throws HnbClientException {
        Product exampleProduct = new Product("example code", "example name", BigDecimal.valueOf(1.1), "example description");
        Product savedProduct = this.productService.upsert(exampleProduct);
        Review exampleReview = new Review("reviewer", "text", BigDecimal.valueOf(Integer.MAX_VALUE));
        exampleReview.setProduct(savedProduct);

        this.reviewService.save(exampleReview);
        PopularProductsResponse popularProductsResponse = this.productService.getTopRatedProducts();
        boolean itWasFound = false;
        for (TopRatedProduct topRatedProduct : popularProductsResponse.popularProducts) {
            if ("example name".equals(topRatedProduct.getName())) {
                itWasFound = true;
            }
        }

        Assertions.assertTrue(itWasFound);
    }
}
