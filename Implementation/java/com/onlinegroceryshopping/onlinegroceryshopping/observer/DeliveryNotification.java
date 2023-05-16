package com.onlinegroceryshopping.onlinegroceryshopping.observer;

import java.util.ArrayList;
import java.util.List;

public class DeliveryNotification implements DeliveryNotificationSubject {
    private List<DeliveryNotificationObserver> observers = new ArrayList<>();

    @Override
    public void attach(DeliveryNotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(DeliveryNotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Integer id) {
        for (DeliveryNotificationObserver observer : observers) {
            observer.update("Delivery " + id+ "is InTransit!");
        }
    }

}
