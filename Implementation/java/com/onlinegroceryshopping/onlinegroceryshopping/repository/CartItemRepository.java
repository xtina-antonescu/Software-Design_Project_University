package com.onlinegroceryshopping.onlinegroceryshopping.repository;

import com.onlinegroceryshopping.onlinegroceryshopping.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
