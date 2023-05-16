package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Admin;
import com.onlinegroceryshopping.onlinegroceryshopping.model.User;
import com.onlinegroceryshopping.onlinegroceryshopping.service.AdminService;

public class AdminAuthenticationHandler extends BaseAuthenticationHandler {
    private final AdminService adminService;

    public AdminAuthenticationHandler(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public User authenticate(String email, String password) {
        Admin admin = adminService.authenticate(email,password);
        if(admin != null){
            return admin;
        }
        return callNextHandler(email, password);
    }
}
