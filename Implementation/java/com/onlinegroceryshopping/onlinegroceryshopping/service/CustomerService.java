package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Customer;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(Integer id){
        return customerRepository.findById(id).get();
    }

    public void deleteCustomer(Integer id){
        customerRepository.deleteById(id);
    }

    public Customer authenticate(String email, String password){
        for(Customer customer: customerRepository.findAll()){
            String verify = Base64.getEncoder().encodeToString(password.getBytes());
            if(customer.getEmail().equals(email) && customer.getPassword().equals(verify)){
                return customer;
            }
        }
        return null;
    }
}
