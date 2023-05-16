package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.model.User;

public interface AuthenticationHandler {
    void setNextHandler(AuthenticationHandler handler);
    AuthenticationHandler getNextHandler();
    User authenticate(String email, String password);

}
