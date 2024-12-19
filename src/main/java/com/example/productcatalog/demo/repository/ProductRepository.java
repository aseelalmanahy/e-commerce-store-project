package com.example.productcatalog.demo.repository;

import com.example.productcatalog.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryAndPriceBetween(String category, Float minPrice, Float maxPrice, Sort sort);

    List<Product> findByCategory(String category, Sort sort);

    List<Product> findByPriceBetween(Float minPrice, Float maxPrice, Sort sort);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.deleted = false")
    List<Product> findAllActiveProducts();

    List<Product> findByName(String name);

    List<Product> findByCategory(String category);


}
