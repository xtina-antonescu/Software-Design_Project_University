package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Cart;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository=cartRepository;
    }

    public void save(Cart cart){
        cartRepository.save(cart);
    }

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public Cart getCart(Integer id){
        return cartRepository.findById(id).get();
    }

    public void deleteCart(Integer id){
        cartRepository.deleteById(id);
    }

    public Cart findByCustomer(Integer id){
        for(Cart cart : cartRepository.findAll()){
            if(cart.getCustomer().getCustomerID() == id){
                return cart;
            }
        }
        return null;
    }
}
