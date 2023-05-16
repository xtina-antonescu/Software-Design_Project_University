package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Courier;
import com.onlinegroceryshopping.onlinegroceryshopping.model.User;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CourierService;

public class CourierAuthenticationHandler extends BaseAuthenticationHandler {
    private final CourierService courierService;

    public CourierAuthenticationHandler(CourierService courierService) {
        this.courierService = courierService;
    }

    @Override
    public User authenticate(String email, String password) {
        Courier courier = courierService.authenticate(email,password);
        if (courier != null) {
            return courier;
        }
        return callNextHandler(email, password);
    }
}
