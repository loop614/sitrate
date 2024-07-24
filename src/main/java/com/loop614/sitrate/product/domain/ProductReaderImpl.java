package com.loop614.sitrate.product.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.product.transfer.ProductName;
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
            "JOIN Review r ON r.productId = p.id " +
            "GROUP BY p.id " +
            "ORDER BY avgRating DESC, p.id DESC";
        TypedQuery<Object[]> query = this.entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(3);
        List<Object[]> resultList = query.getResultList();
        List<TopRatedProduct> products = resultList.stream()
            .map(result -> new TopRatedProduct(String.valueOf(result[0]), BigDecimal.valueOf((double)result[1])))
            .collect(Collectors.toList());

        return new PopularProductsResponse(products);
    }

    public List<ProductName> findNamesByIdIn(List<Long> ids) {
        String queryString =
            "SELECT p.id, p.name " +
            "FROM Product p " +
            "WHERE id in :ids ";

        TypedQuery<Object[]> query = this.entityManager.createQuery(queryString, Object[].class);
        query.setParameter("ids", ids);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
            .map(result -> new ProductName(
                (long)result[0],
                String.valueOf(result[1])
            ))
            .collect(Collectors.toList());
    }
}
