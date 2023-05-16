package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Customer;
import com.onlinegroceryshopping.onlinegroceryshopping.model.User;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CustomerService;

public class CustomerAuthenticationHandler extends BaseAuthenticationHandler {
    private final CustomerService customerService;

    public CustomerAuthenticationHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public User authenticate(String email, String password) {
        Customer customer = customerService.authenticate(email, password);
        if (customer != null) {
            return customer;
        }
        return callNextHandler(email, password);
    }
}
