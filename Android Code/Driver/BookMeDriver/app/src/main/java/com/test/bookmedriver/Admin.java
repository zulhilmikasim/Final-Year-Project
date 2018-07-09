package com.test.bookmedriver;

public class Admin {

    private String id, regnum;

    public Admin(String regnum, String id) {
        this.id = id;
        this.regnum = regnum;
    }

    public Admin(){

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
