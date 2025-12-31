package com.pfms.model;

public class User {
    private int id;
    private String full_name;
    private String email;

    public User() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFull_name() { return full_name; }
    
    // FIXED: Removed the "throw" line and added actual assignment
    public void setFull_name(String full_name) { 
        this.full_name = full_name; 
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}