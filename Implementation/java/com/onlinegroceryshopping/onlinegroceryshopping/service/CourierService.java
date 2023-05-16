package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Courier;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class CourierService {

    @Autowired
    private CourierRepository courierRepository;

    public void save(Courier courier){
        courierRepository.save(courier);
    }

    public List<Courier> getAllCouriers(){
        return courierRepository.findAll();
    }

    public Courier getCourier(Integer id){
        return courierRepository.findById(id).get();
    }

    public void deleteCourier(Integer id){
        courierRepository.deleteById(id);
    }

    public Courier authenticate(String email, String password){
        for(Courier courier: courierRepository.findAll()){
            String verify = Base64.getEncoder().encodeToString(password.getBytes());
            if(courier.getEmail().equals(email) && courier.getPassword().equals(verify)){
                return courier;
            }
        }
        return null;
    }
}

