package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.*;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public void save(Delivery delivery){
        deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries(){
        return deliveryRepository.findAll();
    }

    public Delivery getDelivery(Integer id){
        return deliveryRepository.findById(id).get();
    }

    public void deleteDelivery(Integer id){
        deliveryRepository.deleteById(id);
    }

    public List<String> getDeliveryItems(Integer id){
        Delivery delivery = deliveryRepository.findById(id).get();
        Order order = orderRepository.findById(delivery.getOrder().getOrderID()).get();
        Cart cart = new Cart();
        for(Cart c: cartRepository.findAll()){
            if(c.getCustomer().getCustomerID() == order.getCustomer().getCustomerID()){
                cart =c;
                break;
            }
        }

        List<String> deliveryItems = new ArrayList<>();
        for(CartItem item: cartItemRepository.findAll()){
            if(item.getCart().getCartID() == cart.getCartID()) {
                Product product = productRepository.findById(item.getProduct().getProductID()).get();
                deliveryItems.add(product.getName() + " " + item.getQuantity());
            }
        }
        deliveryItems.add(order.getAddress());
        return deliveryItems;
    }
}
