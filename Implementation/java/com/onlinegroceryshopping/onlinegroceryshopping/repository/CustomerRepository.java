package com.onlinegroceryshopping.onlinegroceryshopping.repository;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
