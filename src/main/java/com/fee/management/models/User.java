package com.fee.management.models;

public class User {
    
    private String id;
    private String name;
    private String role;// Student or Admin
    private String email;
    private String contactNumber;
    private boolean isActive = true;

    //Constructor
    public User(String id, String name, String role, String email, String contactNumber,
            boolean isActive) {
        this.id = id;

        this.name = name;
        this.role = role;
        this.email = email;
        this.contactNumber = contactNumber;
        this.isActive = isActive;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
