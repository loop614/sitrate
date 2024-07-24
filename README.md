### Description
- Java Spring app with h2
- Product with Reviews (1-n relationship)
- Use external service
- Popular product query scan count comparison using EXPLAIN ANALYZE

### Requirements
- maven, java

### Quick Start
```console
$ ./mvnw spring-boot:run
```
- [open example 1](http://localhost:12345/product/popular) popular products, product join review
- [open example 2](http://localhost:12345/review/popular/join) popular products, review join product
- [open example 3](http://localhost:12345/review/popular/two) popular products, query review then query product
- [open filter products](http://localhost:12345/product/filter?code=SGS23&name=Samsung)
```console
$ curl --request POST --url http://localhost:12345/product/upsert --header 'content-type: application/json' --data '{"code": "Apple", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}'
```
or
```console
$ wget --header="content-type: application/json" --post-data='{"code": "Apple2", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}' --output-document - http://localhost:12345/product/upsert
```

### Explain Analyse
- example 1:
```sql
SELECT
    "P"."NAME",
    AVG("R"."RATING") AS "AVGRATING"
FROM "PUBLIC"."PRODUCT" "P"
    /* PUBLIC.PRIMARY_KEY_1 */
    /* scanCount: 11 */
INNER JOIN "PUBLIC"."REVIEW" "R"
    /* PUBLIC.FKIYOF1SINDB9QIQR9O8NPJ8KLT_INDEX_8: PRODUCT_ID = P.ID */
    ON 1=1
    /* scanCount: 10010 */
WHERE "R"."PRODUCT_ID" = "P"."ID"
GROUP BY "P"."ID"
ORDER BY 2 DESC, "P"."ID" DESC
/* group sorted */
```

- example 2:
```sql
SELECT
    "P"."NAME",
    AVG("R"."RATING") AS "AVGRATING"
FROM "PUBLIC"."PRODUCT" "P"
    /* PUBLIC.PRODUCT.tableScan */
    /* scanCount: 11 */
INNER JOIN "PUBLIC"."REVIEW" "R"
    /* PUBLIC.FKIYOF1SINDB9QIQR9O8NPJ8KLT_INDEX_8: PRODUCT_ID = P.ID */
    ON 1=1
    /* scanCount: 10010 */
WHERE "R"."PRODUCT_ID" = "P"."ID"
GROUP BY "R"."PRODUCT_ID"
ORDER BY 2 DESC, "R"."PRODUCT_ID" DESC
```

- example 3:
```sql
SELECT
    "R"."PRODUCT_ID",
    AVG("R"."RATING") AS "AVGRATING"
FROM "PUBLIC"."REVIEW" "R"
    /* PUBLIC.FKIYOF1SINDB9QIQR9O8NPJ8KLT_INDEX_8 */
    /* scanCount: 10001 */
GROUP BY "R"."PRODUCT_ID"
ORDER BY 2 DESC, 1 DESC
/* group sorted */

SELECT
    "P"."ID",
    "P"."NAME"
FROM "PUBLIC"."PRODUCT" "P"
    /* PUBLIC.PRIMARY_KEY_1: ID IN(1, 2, 3) */
    /* scanCount: 4 */
WHERE "ID" IN(1, 2, 3)
```

### Run tests
```console
$ ./mvnw test
```
