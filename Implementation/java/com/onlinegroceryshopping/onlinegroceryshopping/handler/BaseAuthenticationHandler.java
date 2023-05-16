package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.model.User;

public abstract class BaseAuthenticationHandler implements AuthenticationHandler {
    private AuthenticationHandler nextHandler;

    @Override
    public void setNextHandler(AuthenticationHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public AuthenticationHandler getNextHandler() {
        return nextHandler;
    }

    protected User callNextHandler(String email, String password) {
        if (nextHandler != null) {
            return nextHandler.authenticate(email, password);
        }
        return null;
    }
}
