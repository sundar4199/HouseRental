package com.example.navigationdrawer.activity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NewHouse {

    public String name,address,location,amount;

    public NewHouse() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public NewHouse(String name, String address, String location, String amount) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
