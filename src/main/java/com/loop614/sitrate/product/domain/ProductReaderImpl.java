package com.loop614.sitrate.product.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.TopRatedProduct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class ProductReaderImpl implements ProductReader {
    private final EntityManager entityManager;

    public ProductReaderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PopularProductsResponse getTopRatedProducts() {
        String queryString =
            "SELECT p.name, AVG(r.rating) as avgRating " +
            "FROM Product p " +
            "JOIN Review r ON r.product.id = p.id " +
            "GROUP BY p.id " +
            "ORDER BY avgRating DESC";

        TypedQuery<Object[]> query;
        query = this.entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(3);
        List<Object[]> resultList = query.getResultList();
        List<TopRatedProduct> products = resultList.stream()
            .map(result -> new TopRatedProduct((String) result[0], (double) result[1]))
            .collect(Collectors.toList());

        return new PopularProductsResponse(products);
    }
}
