package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Admin;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void save(Admin admin){
        adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Admin getAdmin(Integer id){
        return adminRepository.findById(id).get();
    }

    public void deleteAdmin(Integer id){
        adminRepository.deleteById(id);
    }

    public Admin authenticate(String email, String password){
        for(Admin admin: adminRepository.findAll()){
            String verify = Base64.getEncoder().encodeToString(password.getBytes());
            if(admin.getEmail().equals(email) && admin.getPassword().equals(verify)){
                return admin;
            }
        }
        return null;
    }
}
