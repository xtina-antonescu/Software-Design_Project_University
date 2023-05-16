package com.onlinegroceryshopping.onlinegroceryshopping.handler;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Provider;
import com.onlinegroceryshopping.onlinegroceryshopping.model.User;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProviderService;

public class ProviderAuthenticationHandler extends BaseAuthenticationHandler {
    private final ProviderService providerService;

    public ProviderAuthenticationHandler(ProviderService providerService) {
        this.providerService = providerService;
    }

    @Override
    public User authenticate(String email, String password) {
        Provider provider = providerService.authenticate(email, password);
        if (provider!= null) {
            return provider;
        }
        return callNextHandler(email, password);
    }
}
