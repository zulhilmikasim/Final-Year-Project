package com.test.user.Booking;

public class Admin {

    private String Date;
    private String Pickup;
    private String Destination;
    private String Company;
    private String Time,id, regnum;
    private String seat, numpass, name, phone;

    public Admin( String date, String pickup, String destination, String company,String Time,
                  String regnum,String id, String seat, String numpass, String name, String phone) {
        this.Date = date;
        this.Pickup = pickup;
        this.Destination = destination;
        this.Company = company;
        this.Time = Time;
        this.id = id;
        this.regnum = regnum;
        this.seat = seat;
        this.numpass = numpass;
        this.name = name;
        this.phone = phone;
    }

    public Admin(){

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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPickup() {
        return Pickup;
    }

    public void setPickup(String pickup) {
        Pickup = pickup;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getNumpass() {
        return numpass;
    }

    public void setNumpass(String numpass) {
        this.numpass = numpass;
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
}
