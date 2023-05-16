package com.onlinegroceryshopping.onlinegroceryshopping;

import com.onlinegroceryshopping.onlinegroceryshopping.controller.CustomerController;
import com.onlinegroceryshopping.onlinegroceryshopping.model.*;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.CartItemRepository;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.CartRepository;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.CustomerRepository;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.ProductRepository;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CartItemService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CartService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CustomerService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProductService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class Tester {
    CustomerController customerController = new CustomerController();

    @Test
    public void testLogIn(){
        Customer customer = new Customer("example@gmail.com", "password123", "John Smith", 21, "Boulevard Street", "07874744");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        CustomerService customerService = new CustomerService(customerRepository);
        customerService.save(customer);
        customerController.setCustomerService(customerService);
        if(customerController.logIn("example@gmail.com","password123").equals(customer)){
            System.out.println("Log-in customer ok");
        }
    }

    @Test
    public void testPlaceOrder(){
        Customer customer = new Customer("example1@gmail.com", "password123", "John Smith", 21, "Boulevard Street", "07874744");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        CustomerService customerService = new CustomerService(customerRepository);
        customerService.save(customer);
        customerController.setCustomerService(customerService);

        Product product = new Product("product1", "description1", 2);
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);
        productService.save(product);

        Cart cart = new Cart(customer);
        CartRepository cartRepository = mock(CartRepository.class);
        CartService cartService = new CartService(cartRepository);
        cartService.save(cart);

        //mock service and controllers next time
        //use when etc.
        CartItem cartItem = new CartItem(cart, product, 100);
        CartItemRepository cartItemRepository = mock(CartItemRepository.class);
        CartItemService cartItemService = new CartItemService(cartItemRepository);
        cartItemService.save(cartItem);

        if(customerController.logIn("example1@gmail.com", "password123").equals(customer)){
            if(customerController.placeOrder("example1@gmail.com", "password123", PaymentMethod.Cash, "Street1").equals("Could not place order")){
                System.out.println("Place order test ok");
            }
        }
    }


}
