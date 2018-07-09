package com.test.user.DriverDetails;

public class DriverDetail {

    private String Company, id;
    private String  name, age, address, regnum;

    public DriverDetail(String company, String name, String age, String address, String regnum, String id) {

        this.Company = company;
        this.name = name;
        this.age = age;
        this.address = address;
        this.regnum = regnum;
        this.id = id;
    }

    public DriverDetail(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
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
