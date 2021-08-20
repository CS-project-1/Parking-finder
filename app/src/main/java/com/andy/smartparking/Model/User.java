package com.andy.smartparking.Model;

public class User {
    public String firstName,image;;
    public String surName;
    public String email;

    public User() {
    }

    public User(String firstName, String surName, String email) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public User(String firstName, String image, String surName, String email) {
        this.firstName = firstName;
        this.image = image;
        this.surName = surName;
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
