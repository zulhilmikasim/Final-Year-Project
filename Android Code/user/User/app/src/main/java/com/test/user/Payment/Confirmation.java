package com.test.user.Payment;

public class Confirmation {

    private String Date;
    private String Pickup;
    private String Destination;
    private String price;
    private String Company;
    private String Time, name, age, address, regnum,id;

    public Confirmation(String date, String pickup, String destination, String price, String company,String Time,
                        String name, String age, String address, String regnum, String id) {
        this.Date = date;
        this.Pickup = pickup;
        this.Destination = destination;
        this.price = price;
        this.Company = company;
        this.Time = Time;
        this.name = name;
        this.age = age;
        this.address = address;
        this.regnum = regnum;
        this.id = id;
    }

    public Confirmation(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getPickup() {
        return Pickup;
    }

    public void setPickup(String pickup) {
        this.Pickup = pickup;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }
}

