package com.onlinegroceryshopping.onlinegroceryshopping.controller;

import com.onlinegroceryshopping.onlinegroceryshopping.model.Courier;
import com.onlinegroceryshopping.onlinegroceryshopping.model.Delivery;
import com.onlinegroceryshopping.onlinegroceryshopping.model.StateOrder;
import com.onlinegroceryshopping.onlinegroceryshopping.observer.DeliveryNotification;
import com.onlinegroceryshopping.onlinegroceryshopping.observer.DeliveryNotificationSubject;
import com.onlinegroceryshopping.onlinegroceryshopping.observer.EmailNotificationObserver;
import com.onlinegroceryshopping.onlinegroceryshopping.observer.LogFileNotificationObserver;
import com.onlinegroceryshopping.onlinegroceryshopping.service.CourierService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.DeliveryService;
import com.onlinegroceryshopping.onlinegroceryshopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private OrderService orderService;

    private DeliveryNotificationSubject deliveryNotification;

    @PostMapping("/log-in")
    public ResponseEntity<Courier> logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Courier courier = courierService.authenticate(email, password);
            if (courier != null) {
                courier.setAuthenticated(true);
                courierService.save(courier);
                return new ResponseEntity<>(courier, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log-off")
    public ResponseEntity<Courier> logOff(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Courier courier = courierService.authenticate(email, password);
            if (courier != null && courier.getAuthenticated()) {
                courier.setAuthenticated(false);
                courierService.save(courier);
                return new ResponseEntity<>(courier, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/accept-delivery")
    public ResponseEntity<List<String>> acceptDelivery(@RequestParam("id") Integer id, @RequestParam("email") String email, @RequestParam("password") String password){
        Courier courier = courierService.authenticate(email, password);
        try{
            if(courier.getAuthenticated()){
                Delivery delivery = deliveryService.getDelivery(id);
                delivery.setStateOrder(StateOrder.InTransit);
                deliveryService.save(delivery);
                DeliveryNotification deliveryNotification = new DeliveryNotification();
                deliveryNotification.attach(new LogFileNotificationObserver("D:\\1.FACULTA\\AN3\\SEM2\\SD\\LABS\\Project\\Implementation\\online-grocery-shopping\\src\\main\\resources\\logs.txt"));
                //String emailTo = delivery.getOrder().getCustomer().getEmail();
                //deliveryNotification.attach(new EmailNotificationObserver(emailTo));
                // Notify observers that delivery is in transit
                deliveryNotification.notifyObservers(delivery.getDeliveryID());
                return new ResponseEntity<>(deliveryService.getDeliveryItems(delivery.getDeliveryID()), HttpStatus.OK);
            }
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/delivered")
    public ResponseEntity<Delivery> delivered(@RequestParam("id") Integer id,@RequestParam("email") String email, @RequestParam("password") String password){
        Courier courier = courierService.authenticate(email, password);
        try{
            if(courier.getAuthenticated()){
                Delivery delivery = deliveryService.getDelivery(id);
                delivery.setStateOrder(StateOrder.Delivered);
                deliveryService.save(delivery);
                return new ResponseEntity<>(delivery,HttpStatus.OK);
            }
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
