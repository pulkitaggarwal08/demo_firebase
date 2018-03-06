package com.demo_firebase.pulkit.models;

import com.google.firebase.database.GenericTypeIndicator;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by pulkit on 30/8/17.
 */

public class SearchFriend implements Serializable{

    private String fullName;
    private String email;
    private String my_data;
    private HashMap<String, Object> test_data;

    public HashMap<String, Object> getTest_data() {
        return test_data;
    }

    public void setTest_data(HashMap<String, Object> test_data) {
        this.test_data = test_data;
    }

    public SearchFriend() {
    }

    public SearchFriend(String fullName, String email) {
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

    public String getMy_data() {
        return my_data;
    }

    public void setMy_data(String my_data) {
        this.my_data = my_data;
    }
}
