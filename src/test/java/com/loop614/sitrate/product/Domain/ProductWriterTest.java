package com.loop614.sitrate.product.Domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.loop614.sitrate.hnbClient.HnbClientService;
import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;
import com.loop614.sitrate.product.domain.ProductWriter;
import com.loop614.sitrate.product.domain.ProductWriterImpl;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.repository.ProductRepository;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductWriterTest {
    @Autowired
    private final ProductRepository productRepository;

    @MockBean
    private final HnbClientService hnbClientServiceMock;

    private final ProductWriter productWriter;

    @Autowired
    public ProductWriterTest(HnbClientService hnbClientServiceMock, ProductRepository productRepository) {
        this.hnbClientServiceMock = hnbClientServiceMock;
        this.productRepository = productRepository;
        this.productWriter = new ProductWriterImpl(
            this.productRepository,
            this.hnbClientServiceMock
        );
    }

    @Test
    public void writeProductOkResponseTest() throws HnbClientException {
        BigDecimal onepointone = BigDecimal.valueOf(1.1);
        Product exampleProduct = new Product("example code", "example name", onepointone, "example description");
        List<HnbCurrency> hnbCurrencies = new ArrayList<HnbCurrency>();
        HnbCurrency usdCurrency = new HnbCurrency();
        usdCurrency.setCurrency("USD");
        usdCurrency.setCurrencyMiddleValue(1.1);
        hnbCurrencies.add(usdCurrency);
        Mockito
            .when(this.hnbClientServiceMock.currencyEur())
            .thenReturn(hnbCurrencies);

        Product savedProduct = this.productWriter.save(exampleProduct);
        Assertions.assertNotNull(savedProduct);
        onepointone = onepointone.multiply(BigDecimal.valueOf(1.1));
        Assertions.assertTrue(savedProduct.getPriceUsd().equals(onepointone));
    }

    @Test
    public void writeProductEmptyHnbClientResponseTest() throws HnbClientException {
        Product exampleProduct = new Product("example code", "example name", BigDecimal.valueOf(1.1), "example description");
        List<HnbCurrency> hnbCurrencies = new ArrayList<HnbCurrency>();
        Mockito
            .when(this.hnbClientServiceMock.currencyEur())
            .thenReturn(hnbCurrencies);

        Product savedProduct = this.productWriter.save(exampleProduct);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertNull(savedProduct.getPriceUsd());
    }

    @Test
    public void writeProductsHnbClientExceptionTest() throws HnbClientException {
        Product exampleProduct = new Product("example code", "example name", BigDecimal.valueOf(1.1), "example description");
        Mockito
            .when(this.hnbClientServiceMock.currencyEur())
            .thenThrow(HnbClientException.class);

        Product savedProduct = this.productWriter.save(exampleProduct);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertNull(savedProduct.getPriceUsd());
    }
}
