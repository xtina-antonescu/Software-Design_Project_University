package com.onlinegroceryshopping.onlinegroceryshopping.repository;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
}
