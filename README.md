### Description
- Java Spring app
- Product with Reviews (1-n relationship)
- Popular Product Query Comparison using EXPLAIN ANALYZE

### Requirements
- maven, java

### Quick Start
```console
$ ./mvnw spring-boot:run
```
- [open](http://localhost:12345/product/popular) popular products, product join review
- [open](http://localhost:12345/review/popular/join) popular products, review join product
- [open](http://localhost:12345/review/popular/two) popular products, query review then query product
- [open filter products](http://localhost:12345/product/filter?code=SGS23&name=Samsung)
```console
$ curl --request POST --url http://localhost:12345/product/upsert --header 'content-type: application/json' --data '{"code": "Apple", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}'
```
or
```console
$ wget --header="content-type: application/json" --post-data='{"code": "Apple2", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}' --output-document - http://localhost:12345/product/upsert
```

### Run tests
```console
$ ./mvnw test
```
