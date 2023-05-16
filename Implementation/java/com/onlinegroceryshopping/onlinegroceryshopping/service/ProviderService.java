package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Provider;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public void save(Provider provider){
        providerRepository.save(provider);
    }

    public List<Provider> getAllProviders(){
        return providerRepository.findAll();
    }

    public Provider getProvider(Integer id){
        return providerRepository.findById(id).get();
    }

    public void deleteProvider(Integer id){
        providerRepository.deleteById(id);
    }

    public Provider authenticate(String email, String password){
        for(Provider provider: providerRepository.findAll()){
            String verify = Base64.getEncoder().encodeToString(password.getBytes());
            if(provider.getEmail().equals(email) && provider.getPassword().equals(verify)){
                return provider;
            }
        }
        return null;
    }
}
