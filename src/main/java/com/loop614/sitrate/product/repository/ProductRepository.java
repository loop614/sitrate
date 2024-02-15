package com.loop614.sitrate.product.repository;

import com.loop614.sitrate.product.entity.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByCodeContainingAndNameContaining(String code, String name);
}
