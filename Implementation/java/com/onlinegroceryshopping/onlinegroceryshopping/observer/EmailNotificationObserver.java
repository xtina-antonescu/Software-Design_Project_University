package com.onlinegroceryshopping.onlinegroceryshopping.observer;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailNotificationObserver implements DeliveryNotificationObserver {
    private String recipientEmail;

    public EmailNotificationObserver(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void update(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Delivery Notification");
        mailMessage.setText(message);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Set mail sender properties here
        mailSender.send(mailMessage);
        System.out.println(message);
    }
}
