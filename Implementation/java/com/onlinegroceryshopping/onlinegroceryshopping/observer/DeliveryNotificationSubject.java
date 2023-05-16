package com.onlinegroceryshopping.onlinegroceryshopping.observer;

public interface DeliveryNotificationSubject {
    void attach(DeliveryNotificationObserver observer);
    void detach(DeliveryNotificationObserver observer);
    void notifyObservers(Integer id);
}
