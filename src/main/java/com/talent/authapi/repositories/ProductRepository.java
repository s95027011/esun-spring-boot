package com.talent.authapi.repositories;

import com.talent.authapi.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);
    Optional<Product> findById(Long id);
}
