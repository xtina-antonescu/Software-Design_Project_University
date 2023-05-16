package com.onlinegroceryshopping.onlinegroceryshopping.repository;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
