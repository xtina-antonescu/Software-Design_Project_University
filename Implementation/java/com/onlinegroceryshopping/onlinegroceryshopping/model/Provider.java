package com.onlinegroceryshopping.onlinegroceryshopping.model;

import jakarta.persistence.*;

@Entity
@Table(name ="providers")
public class Provider extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int providerID;

    private String name;
    private String contactInfo;

    public Provider() {
    }

    public Provider(String email, String password, String name, String contactInfo) {
        super(email, password);
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public int getProviderID() {
        return providerID;
    }

    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
