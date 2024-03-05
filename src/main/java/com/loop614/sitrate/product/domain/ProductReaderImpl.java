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
    /*
    EXPLAIN ANALYZE:
                                                                QUERY PLAN
    --------------------------------------------------------------------------------------------------------------------------------------
    Limit  (cost=27.47..27.48 rows=3 width=556) (actual time=0.061..0.074 rows=1 loops=1)
    ->  Sort  (cost=27.47..27.75 rows=110 width=556) (actual time=0.059..0.069 rows=1 loops=1)
            Sort Key: (avg(r.rating)) DESC
            Sort Method: quicksort  Memory: 25kB
            ->  HashAggregate  (cost=24.68..26.05 rows=110 width=556) (actual time=0.049..0.058 rows=1 loops=1)
                Group Key: p.id
                Batches: 1  Memory Usage: 24kB
                ->  Hash Join  (cost=12.47..24.13 rows=110 width=550) (actual time=0.030..0.044 rows=3 loops=1)
                        Hash Cond: (r.product_id = p.id)
                        ->  Seq Scan on review r  (cost=0.00..11.30 rows=130 width=30) (actual time=0.005..0.008 rows=3 loops=1)
                        ->  Hash  (cost=11.10..11.10 rows=110 width=524) (actual time=0.014..0.017 rows=3 loops=1)
                            Buckets: 1024  Batches: 1  Memory Usage: 9kB
                            ->  Seq Scan on product p  (cost=0.00..11.10 rows=110 width=524) (actual time=0.003..0.007 rows=3 loops=1)
    Planning Time: 0.087 ms
    Execution Time: 0.114 ms
    (15 rows)
    */
    public PopularProductsResponse getTopRatedProducts() {
        String queryString =
            "SELECT p.name, AVG(r.rating) as avgRating " +
            "FROM Product p " +
            "JOIN Review r ON r.productId = p.id " +
            "GROUP BY p.id " +
            "ORDER BY avgRating DESC, p.id DESC";

        TypedQuery<Object[]> query;
        query = this.entityManager.createQuery(queryString, Object[].class);
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

        TypedQuery<Object[]> query;
        query = this.entityManager.createQuery(queryString, Object[].class);
        query.setParameter("ids", ids);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
            .map(result -> new ProductName(
                Long.valueOf((long)result[0]),
                String.valueOf(result[1])
            ))
            .collect(Collectors.toList());
    }
}
