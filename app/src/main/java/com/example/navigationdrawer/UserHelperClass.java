package com.example.navigationdrawer;

public class UserHelperClass {

    String name,username;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public UserHelperClass(String name,  String username) {
        this.name = name;

        this.username = username;
    }
}
