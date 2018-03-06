package com.demo_firebase.pulkit.models;

/**
 * Created by pulkit on 30/8/17.
 */

public class FriendList {

    private String fullName;
    private String email;

    public FriendList(String fullName, String email) {
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
