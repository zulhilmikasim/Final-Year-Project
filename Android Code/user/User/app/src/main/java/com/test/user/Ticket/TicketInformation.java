package com.test.user.Ticket;

/**
 * Created by Asus on 3/2/2018.
 */

public class TicketInformation {
    public String passenger;

    public TicketInformation(){

    }

    public TicketInformation(String passenger){

        this.passenger = passenger;
    }

    public String getPassenger() {
        return passenger;
    }
}