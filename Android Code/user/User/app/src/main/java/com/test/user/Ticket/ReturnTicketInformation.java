package com.test.user.Ticket;

public class ReturnTicketInformation {

    public String passenger;

    public ReturnTicketInformation(){

    }

    public ReturnTicketInformation(String passenger){

        this.passenger = passenger;
    }

    public String getPassenger() {
        return passenger;
    }
}
