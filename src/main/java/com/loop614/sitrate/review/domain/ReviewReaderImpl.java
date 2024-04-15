package com.loop614.sitrate.review.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.product.ProductService;
import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.ProductName;
import com.loop614.sitrate.product.transfer.TopRatedProduct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class ReviewReaderImpl implements ReviewReader {
    private final EntityManager entityManager;

    private final ProductService productService;

    public ReviewReaderImpl(EntityManager entityManager, ProductService productService) {
        this.entityManager = entityManager;
        this.productService = productService;
    }

    public PopularProductsResponse getTopRatedProductsJoinFromReview() {
        String queryString =
            "SELECT p.name, AVG(r.rating) as avgRating " +
            "FROM Review r " +
            "JOIN Product p ON r.productId = p.id " +
            "GROUP BY r.productId " +
            "ORDER BY avgRating DESC, r.productId DESC";

        TypedQuery<Object[]> query = this.entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(3);
        List<Object[]> resultList = query.getResultList();
        List<TopRatedProduct> products = resultList.stream()
            .map(result -> new TopRatedProduct(String.valueOf(result[0]), BigDecimal.valueOf((double)result[1])))
            .collect(Collectors.toList());

        return new PopularProductsResponse(products);
    }

    public PopularProductsResponse getTopRatedProductsTwoQueries() {
        LinkedHashMap<Long, TopRatedProduct> topRatedMap = new LinkedHashMap<Long, TopRatedProduct>();
        String queryString =
            "SELECT r.productId, AVG(r.rating) as avgRating " +
            "FROM Review r " +
            "GROUP BY r.productId " +
            "ORDER BY avgRating DESC, r.productId DESC";

        TypedQuery<Object[]> query = this.entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(3);
        List<Object[]> resultList = query.getResultList();
        resultList.stream()
            .forEach(
                result -> topRatedMap.put(
                    (long)result[0],
                    new TopRatedProduct(BigDecimal.valueOf((double)result[1]))
                )
            );

        List<ProductName> productNames = this.productService.findNamesByIdIn(new ArrayList<Long>(topRatedMap.keySet()));

        for (ProductName productName : productNames) {
            TopRatedProduct topRatedProduct = topRatedMap.get(productName.getId());
            topRatedProduct.setName(productName.getName());
        }

        return new PopularProductsResponse(topRatedMap.values());
    }
}
