### Description
- Java Spring app

### Requirements
- maven, docker with compose

### Quick Start
```console
$ docker compose up sitrate_h2
$ ./mvnw spring-boot:run
```
- [open](http://localhost:12345/product/popular)
- [open](http://localhost:12345/product/filter?code=SGS23&name=Samsung)
```console
$ curl --request POST --url http://localhost:12345/product/new --header 'content-type: application/json' --data '{"code": "Apple", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}'
$ wget --header="content-type: application/json" --post-data='{"code": "Apple", "name":"iPhone latest","priceEur":2.2,"description":"this is iphone"}' --output-document - http://localhost:12345/product/new
```
