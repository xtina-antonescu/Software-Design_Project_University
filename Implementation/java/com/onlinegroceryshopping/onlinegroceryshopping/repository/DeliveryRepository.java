package com.onlinegroceryshopping.onlinegroceryshopping.repository;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
