package com.loop614.sitrate.product.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.hnbClient.HnbClientService;
import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;
import com.loop614.sitrate.product.entity.Product;
import com.loop614.sitrate.product.repository.ProductRepository;

@Service
public class ProductWriterImpl implements ProductWriter {

    private final ProductRepository productRepository;

    private final HnbClientService hnbClientService;

    public ProductWriterImpl(
            ProductRepository productRepository,
            HnbClientService hnbClientService) {
        this.productRepository = productRepository;
        this.hnbClientService = hnbClientService;
    }

    public Product upsert(Product product) {
        Product existingProduct = this.productRepository.findByCode(product.getCode());
        if (existingProduct != null) {
            product.setId(existingProduct.getId());
            if (product.getPriceEur() == existingProduct.getPriceEur()) {
                return this.productRepository.save(product);
            }
        }

        List<HnbCurrency> hnbCurrencies = new ArrayList<HnbCurrency>();
        try {
            hnbCurrencies = this.hnbClientService.currencyEur();
        } catch (HnbClientException e) {
            return this.productRepository.save(product);
        }

        for (HnbCurrency hnbCurrency : hnbCurrencies) {
            if (hnbCurrency.getCurrency().equals("USD") && hnbCurrency.getCurrencyMiddleValue() > 0) {
                product.setPriceUsd(
                        product.getPriceEur().multiply((BigDecimal.valueOf(hnbCurrency.getCurrencyMiddleValue()))));
                break;
            }
        }

        return this.productRepository.save(product);
    }
}
