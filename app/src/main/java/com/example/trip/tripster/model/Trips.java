package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rush on 2017-12-11.
 */

public class Trips implements Serializable {

    private static Trips instance;
    private ArrayList<Trip> tripList;

    private Trips() {
        tripList = new ArrayList<Trip>();
    }

    public static Trips getInstance() {
        if (instance == null) {
            instance = new Trips();
        }
        return instance;
    }

    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }

}