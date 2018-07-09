package com.test.user.Notification;

public class Rating {

    String rate, comment,reg, username;

    public Rating(String rate, String comment,String reg, String username) {
        this.rate = rate;
        this.comment = comment;
        this.reg = reg;
        this.username = username;
    }

    public Rating(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }
}
