package com.onlinegroceryshopping.onlinegroceryshopping.controller;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Admin;
import com.onlinegroceryshopping.onlinegroceryshopping.model.Courier;
import com.onlinegroceryshopping.onlinegroceryshopping.model.Customer;
import com.onlinegroceryshopping.onlinegroceryshopping.model.Provider;
import com.onlinegroceryshopping.onlinegroceryshopping.service.AdminService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CourierService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CustomerService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
        try{
            adminService.save(admin);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/log-in")
    public ResponseEntity<Admin> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Admin admin = adminService.authenticate(email, password);
            if (admin != null) {
                admin.setAuthenticated(true);
                adminService.save(admin);
                return new ResponseEntity<>(admin, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log-off")
    public ResponseEntity<Admin> logOff(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Admin admin = adminService.authenticate(email, password);
            if (admin != null && admin.getAuthenticated()) {
                admin.setAuthenticated(false);
                adminService.save(admin);
                return new ResponseEntity<>(admin, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                customerService.save(customer);
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                customer.setCustomerID(id);
                customerService.save(customer);
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<String> deleteCustomer(@RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                customerService.deleteCustomer(id);
                return new ResponseEntity<>("Customer deleted successfully!", HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>("Invalid credetials", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Could not perform operations", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/add-courier")
    public ResponseEntity<Courier> addCourier(@RequestBody Courier courier, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                courierService.save(courier);
                return new ResponseEntity<>(courier, HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-courier")
    public ResponseEntity<Courier> updateCourier(@RequestBody Courier courier, @RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                courier.setCourierID(id);
                courierService.save(courier);
                return new ResponseEntity<>(courier, HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-courier")
    public ResponseEntity<String> deleteCourier(@RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                courierService.deleteCourier(id);
                return new ResponseEntity<>("Courier deleted successfully!", HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Could not perform operations", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-provider")
    public ResponseEntity<Provider> addProvider(@RequestBody Provider provider, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                providerService.save(provider);
                return new ResponseEntity<>(provider, HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-provider")
    public ResponseEntity<Provider> updateProvider(@RequestBody Provider provider, @RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Admin admin = adminService.authenticate(email, password);
        try{
            if(admin.getAuthenticated()) {
                provider.setProviderID(id);
                providerService.save(provider);
                return new ResponseEntity<>(provider, HttpStatus.OK);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-provider")
    public ResponseEntity<String> deleteProvider(@RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password) {
        Admin admin = adminService.authenticate(email, password);
        try {
            if (admin.getAuthenticated()) {
                providerService.deleteProvider(id);
                return new ResponseEntity<>("Provider deleted successfully!", HttpStatus.OK);
            }

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Could not perform operations", HttpStatus.BAD_REQUEST);
    }
}
