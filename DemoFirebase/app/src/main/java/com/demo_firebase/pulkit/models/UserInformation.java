package com.demo_firebase.pulkit.models;

/**
 * Created by agicon06 on 23/8/17.
 */

public class UserInformation {

    private String fullName;
    private String email;

    public UserInformation(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
