package com.loop614.sitrate.product;

import org.junit.jupiter.api.Test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.transfer.FilterProduct;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void savefindOkTest() {
        String exampleCode = "example code";
        String exampleName = "example name";
        Product exampleProduct = new Product(
            exampleCode,
            exampleName,
            2.2,
            "example description"
        );
        Product savedProduct = this.productService.save(exampleProduct);
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
}
