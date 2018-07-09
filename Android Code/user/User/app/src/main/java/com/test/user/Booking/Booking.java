package com.test.user.Booking;

public class Booking {

    private String Date;
    private String Pickup;
    private String Destination;
    private String Company;
    private String Time,id, regnum;
    private String seat, name, phone;

    public Booking(String date, String pickup, String destination, String company,String Time,
                   String regnum,String id, String seat, String name, String phone) {
        this.Date = date;
        this.Pickup = pickup;
        this.Destination = destination;
        this.Company = company;
        this.Time = Time;
        this.id = id;
        this.regnum = regnum;
        this.seat = seat;
        this.name = name;
        this.phone = phone;
    }

    public Booking(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
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
}

