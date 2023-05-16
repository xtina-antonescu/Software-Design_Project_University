package com.onlinegroceryshopping.onlinegroceryshopping.controller;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Product;
import com.onlinegroceryshopping.onlinegroceryshopping.model.Provider;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProductService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/provider")
@CrossOrigin
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProductService productService;

    @PostMapping("/log-in")
    public ResponseEntity<Provider> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Provider provider = providerService.authenticate(email, password);
            if (provider != null) {
                provider.setAuthenticated(true);
                providerService.save(provider);
                return new ResponseEntity<>(provider, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log-off")
    public ResponseEntity<Provider> logOff(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Provider provider = providerService.authenticate(email, password);
            if (provider != null && provider.getAuthenticated()) {
                provider.setAuthenticated(false);
                providerService.save(provider);
                return new ResponseEntity<>(provider, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @RequestParam("email") String email, @RequestParam("password") String password){
        Provider provider = providerService.authenticate(email, password);
        try{
            if(provider.getAuthenticated()){
                productService.save(product);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping ("/update-product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Provider provider = providerService.authenticate(email, password);
        try{
            if(provider.getAuthenticated()){
                product.setProductID(id);
                productService.save(product);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("delete-product")
    public ResponseEntity<String> deleteProduct(@RequestParam("id")Integer id,@RequestParam("email") String email, @RequestParam("password") String password){
        Provider provider = providerService.authenticate(email, password);
        try{
            if(provider.getAuthenticated()){
                productService.deleteProduct(id);
                return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
