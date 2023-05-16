package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.*;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public void save(Order order){
        orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrder(Integer id){
        return orderRepository.findById(id).get();
    }

    public void deleteOrder(Integer id){
        orderRepository.deleteById(id);
    }

    public Order placeOrder(Customer customer, List<CartItem> cart, PaymentMethod paymentMethod, String address){
        boolean canPlaceOrder = true;
        for(CartItem item : cart){
            if(item.getProduct().getQuantityInStock() - item.getQuantity() < 0){
                canPlaceOrder = false;
            }
        }
        Order order = new Order(customer, paymentMethod, address);
        if(canPlaceOrder){
            return order;
        }
        return null;
    }
}
