package com.onlinegroceryshopping.onlinegroceryshopping.model;

import jakarta.persistence.*;

@Entity
@Table(name ="couriers")
public class Courier extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courierID;

    private String name;
    private String phoneNumber;
    private String company;

    public Courier() {
    }

    public Courier(String email, String password, String name, String phoneNumber, String company) {
        super(email, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.company = company;
    }

    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
