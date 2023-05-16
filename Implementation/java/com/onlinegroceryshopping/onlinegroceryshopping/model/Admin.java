package com.onlinegroceryshopping.onlinegroceryshopping.model;

import jakarta.persistence.*;

@Entity
@Table(name ="admins")
public class Admin extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminID;

    private String name;
    private String position;

    public Admin() {
    }

    public Admin(String email, String password, String name, String position) {
        super(email, password);
        this.name = name;
        this.position = position;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
