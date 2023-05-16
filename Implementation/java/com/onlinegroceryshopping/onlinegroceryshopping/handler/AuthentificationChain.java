package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.service.AdminService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CourierService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CustomerService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProviderService;

public class AuthentificationChain {

    private AuthenticationHandler firstHandler;

        public AuthentificationChain(AdminService adminService, CustomerService customerService, ProviderService providerService, CourierService courierService) {
            // Create the handlers
            AuthenticationHandler adminHandler = new AdminAuthenticationHandler(adminService);
            AuthenticationHandler customerHandler = new CustomerAuthenticationHandler(customerService);
            AuthenticationHandler providerHandler = new ProviderAuthenticationHandler(providerService);
            AuthenticationHandler courierHandler = new CourierAuthenticationHandler(courierService);

            // Set up the chain
            adminHandler.setNextHandler(customerHandler);
            customerHandler.setNextHandler(providerHandler);
            providerHandler.setNextHandler(courierHandler);

            // Set the first handler in the chain
            this.firstHandler = adminHandler;
        }

        public AuthenticationHandler getFirstHandler() {
            return firstHandler;
        }
}
