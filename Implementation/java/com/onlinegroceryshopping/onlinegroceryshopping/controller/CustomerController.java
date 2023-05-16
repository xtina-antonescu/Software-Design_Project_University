package com.onlinegroceryshopping.onlinegroceryshopping.controller;

import com.onlinegroceryshopping.onlinegroceryshopping.model.*;
import com.onlinegroceryshopping.onlinegroceryshopping.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryService deliveryService;

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/log-in")
    public ResponseEntity<Customer> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Customer customer = customerService.authenticate(email, password);
            if (customer != null) {
                customer.setAuthenticated(true);
                customerService.save(customer);
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log-off")
    public ResponseEntity<Customer> logOff(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Customer customer = customerService.authenticate(email, password);
            if (customer != null && customer.getAuthenticated()) {
                customer.setAuthenticated(false);
                customerService.save(customer);
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create-cart")
    public ResponseEntity<Cart> createCart(@RequestParam("email") String email, @RequestParam("password") String password){
        Customer customer = customerService.authenticate(email, password);
        try{
            if(customer.getAuthenticated()){
                Cart cart = new Cart(customer);
                cartService.save(cart);
                return new ResponseEntity<>(cart, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-product")
    public ResponseEntity<CartItem> addProduct(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("productID")Integer productID, @RequestParam("quantity")Integer quantity) {
        Customer customer = customerService.authenticate(email, password);
        try {
            if (customer.getAuthenticated()) {
                Cart cart = cartService.findByCustomer(customer.getCustomerID());
                Product product = productService.getProduct(productID);
                CartItem cartItem = new CartItem(cart, product, quantity);
                cartItemService.save(cartItem);
                return new ResponseEntity<>(cartItem, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProduct(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("id")Integer id){
        Customer customer = customerService.authenticate(email, password);
        try{
            if (customer.getAuthenticated()) {
                cartItemService.deleteCartItem(id);
                return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
        return new ResponseEntity<>("Invalid credetials", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Could not perform operations", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-item")
    public ResponseEntity<CartItem> updateItem(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("id")Integer id, @RequestParam("quantity")Integer quantity){
        Customer customer = customerService.authenticate(email, password);
        try {
            if (customer.getAuthenticated()) {
                CartItem cartItem = cartItemService.getCartItem(id);
                cartItem.setQuantity(quantity);
                cartItemService.save(cartItem);
                return new ResponseEntity<>(cartItem, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/view-cart")
    public ResponseEntity<List<String>> viewCart(@RequestParam("email") String email, @RequestParam("password") String password){
        Customer customer = customerService.authenticate(email, password);
        try {
            if (customer.getAuthenticated()) {
                Cart cart = cartService.findByCustomer(customer.getCustomerID());
                List<String> cartItems = cartItemService.viewCartItemsString(cart.getCartID());
                return new ResponseEntity<>(cartItems, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("paymentMethod")PaymentMethod paymentMethod, @RequestParam("address")String address){
        Customer customer = customerService.authenticate(email, password);
        try {
            if (customer.getAuthenticated()) {
                Cart cart = cartService.findByCustomer(customer.getCustomerID());
                List<CartItem> cartItems = cartItemService.viewCartItems(cart.getCartID());
                Order order = orderService.placeOrder(customer, cartItems, paymentMethod, address);
                if(order != null){
                    orderService.save(order);
                    productService.updateProducts(cartItems);
                    Delivery delivery = new Delivery(order);
                    deliveryService.save(delivery);
                    return new ResponseEntity<>("Order was placed successfully", HttpStatus.OK);
                }
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Could not place order", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
