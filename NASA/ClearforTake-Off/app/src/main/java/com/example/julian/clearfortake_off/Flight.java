package com.example.julian.clearfortake_off;

import java.io.Serializable;

public class Flight implements Serializable {

    public Flight (String air, int dh, int dm, int ah, int am) {

        Airline = air;
        depHour = dh;
        depMinute = dm;
        arrHour = ah;
        arrMinute = am;
    }

    public Flight () {

        Airline = null;
        depHour = -1;
        depMinute = -1;
        arrHour = -1;
        arrMinute = -1;
        FlightNumber = null;
        arrivingAirport = null;
        destinationAirport = null;

    }
    String departCity;
    String arriveCity;
    String Airline;
    Integer depHour;
    Integer depMinute;
    Integer arrHour;
    Integer arrMinute;
    String FlightNumber;
    String destinationAirport;
    String arrivingAirport;
}