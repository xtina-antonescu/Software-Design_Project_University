package com.onlinegroceryshopping.onlinegroceryshopping.observer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LogFileNotificationObserver implements DeliveryNotificationObserver {
    private String logFilePath;

    public LogFileNotificationObserver(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public void update(String message) {
        try {
            Files.write(Paths.get(logFilePath), message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
