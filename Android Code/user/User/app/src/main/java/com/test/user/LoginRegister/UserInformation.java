package com.test.user.LoginRegister;

public class UserInformation {

    private String username;
    private String phone;

    public UserInformation(){

    }

    public UserInformation(String username, String phone) {
        this.username = username;
        this.phone = phone;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}