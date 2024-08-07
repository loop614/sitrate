package com.loop614.sitrate.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loop614.sitrate.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByCode(String code);

    List<Product> findByCodeContainingAndNameContaining(String code, String name);
}
