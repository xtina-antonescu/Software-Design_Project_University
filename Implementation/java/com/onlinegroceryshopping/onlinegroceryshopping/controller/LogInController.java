package com.onlinegroceryshopping.onlinegroceryshopping.controller;

import com.onlinegroceryshopping.onlinegroceryshopping.handler.AuthenticationHandler;
import com.onlinegroceryshopping.onlinegroceryshopping.handler.AuthentificationChain;
import com.onlinegroceryshopping.onlinegroceryshopping.model.User;
import com.onlinegroceryshopping.onlinegroceryshopping.service.AdminService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CourierService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CustomerService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LogInController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CustomerService customerService;

    private AuthentificationChain authenticationChain = new AuthentificationChain(adminService, customerService, providerService, courierService);

    @PostMapping
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        // Start the authentication chain with the first handler
        AuthenticationHandler currentHandler = authenticationChain.getFirstHandler();

        // Iterate through the chain until a handler successfully authenticates the user
        User user = null;
        while (user == null && currentHandler != null) {
            user = currentHandler.authenticate(email, password);
            currentHandler = currentHandler.getNextHandler();
        }

        if (user != null) {
            // User is authenticated
            return ResponseEntity.ok("User is authenticated.");
        } else {
            // User is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }
}
