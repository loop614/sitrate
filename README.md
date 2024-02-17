### Description
- Java Spring app

### Requirements
- maven

### Quick Start
```console
$ ./mvnw spring-boot:run
```
- [open popular products](http://localhost:12345/product/popular)
- [open filter products](http://localhost:12345/product/filter?code=SGS23&name=Samsung)
```console
$ curl --request POST --url http://localhost:12345/product/new --header 'content-type: application/json' --data '{"code": "Apple", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}'
```
or
```console
$ wget --header="content-type: application/json" --post-data='{"code": "Apple2", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}' --output-document - http://localhost:12345/product/new
```

### Run tests
```console
$ ./mvnw test
```
