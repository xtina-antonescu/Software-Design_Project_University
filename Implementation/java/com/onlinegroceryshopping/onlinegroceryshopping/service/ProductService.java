package com.onlinegroceryshopping.onlinegroceryshopping.service;

import com.onlinegroceryshopping.onlinegroceryshopping.model.CartItem;
import com.onlinegroceryshopping.onlinegroceryshopping.model.Product;
import com.onlinegroceryshopping.onlinegroceryshopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer id){
        return productRepository.findById(id).get();
    }

    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

    public void updateProducts(List<CartItem> cartItems){
        for(Product product: productRepository.findAll()){
            for(CartItem cartItem: cartItems){
                if(product.getProductID() == cartItem.getProduct().getProductID()){
                    product.setQuantityInStock(product.getQuantityInStock() - cartItem.getQuantity());
                }
            }
        }
    }
}
