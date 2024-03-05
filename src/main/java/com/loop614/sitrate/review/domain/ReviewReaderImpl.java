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

     /*
                                                QUERY PLAN
    --------------------------------------------------------------------------------------------------------------------------------------
    Limit  (cost=27.57..27.58 rows=3 width=556) (actual time=0.134..0.151 rows=3 loops=1)
    ->  Sort  (cost=27.57..27.85 rows=110 width=556) (actual time=0.132..0.143 rows=3 loops=1)
            Sort Key: (avg(r.rating)) DESC
            Sort Method: quicksort  Memory: 25kB
            ->  HashAggregate  (cost=24.78..26.15 rows=110 width=556) (actual time=0.118..0.129 rows=3 loops=1)
                Group Key: p.i    @Override
(cost=12.47..24.13 rows=130 width=550) (actual time=0.046..0.098 rows=18 loops=1)
                        Hash Cond: (r.product_id = p.id)
                        ->  Seq Scan on review r  (cost=0.00..11.30 rows=130 width=34) (actual time=0.005..0.021 rows=18 loops=1)
                        ->  Hash  (cost=11.10..11.10 rows=110 width=524) (actual time=0.027..0.030 rows=3 loops=1)
                            Buckets: 1024  Batches: 1  Memory Usage: 9kB
                            ->  Seq Scan on product p  (cost=0.00..11.10 rows=110 width=524) (actual time=0.003..0.007 rows=3 loops=1)
    Planning Time: 0.091 ms
    Execution Time: 0.196 ms
    (15 rows)
    */
    public PopularProductsResponse getTopRatedProductsJoinFromReview() {
        String queryString =
            "SELECT p.name, AVG(r.rating) as avgRating " +
            "FROM Review r " +
            "JOIN Product p ON r.productId = p.id " +
            "GROUP BY r.productId " +
            "ORDER BY avgRating DESC, r.productId DESC";

        TypedQuery<Object[]> query;
        query = this.entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(3);
        List<Object[]> resultList = query.getResultList();
        List<TopRatedProduct> products = resultList.stream()
            .map(result -> new TopRatedProduct(String.valueOf(result[0]), BigDecimal.valueOf((double)result[1])))
            .collect(Collectors.toList());

        return new PopularProductsResponse(products);
    }

    /*
                                                QUERY PLAN
    -------------------------------------------------------------------------------------------------------------------------
    Limit  (cost=15.26..15.26 rows=3 width=40) (actual time=0.094..0.106 rows=3 loops=1)
    ->  Sort  (cost=15.26..15.58 rows=130 width=40) (actual time=0.092..0.098 rows=3 loops=1)
            Sort Key: (avg(rating)) DESC
            Sort Method: quicksort  Memory: 25kB
            ->  HashAggregate  (cost=11.95..13.57 rows=130 width=40) (actual time=0.055..0.062 rows=3 loops=1)
                Group Key: product_id
                Batches: 1  Memory Usage: 40kB
                ->  Seq Scan on review r  (cost=0.00..11.30 rows=130 width=34) (actual time=0.007..0.023 rows=18 loops=1)
    Planning Time: 0.165 ms
    Execution Time: 0.173 ms
    (10 rows)
                                                QUERY PLAN
    ------------------------------------------------------------------------------------------------------
    Seq Scan on product p  (cost=0.00..11.38 rows=2 width=524) (actual time=0.013..0.016 rows=2 loops=1)
    Filter: (id = ANY ('{1,2}'::integer[]))
    Rows Removed by Filter: 1
    Planning Time: 0.110 ms
    Execution Time: 0.030 ms
    (5 rows)
    */
    public PopularProductsResponse getTopRatedProductsTwoQueries() {
        LinkedHashMap<Long, TopRatedProduct> topRatedMap = new LinkedHashMap<Long, TopRatedProduct>();
        String queryString =
            "SELECT r.productId, AVG(r.rating) as avgRating " +
            "FROM Review r " +
            "GROUP BY r.productId " +
            "ORDER BY avgRating DESC, r.productId DESC";

        System.out.println(queryString);

        TypedQuery<Object[]> query;
        query = this.entityManager.createQuery(queryString, Object[].class);
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
