package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rush on 2017-12-08.
 */

public class Trips implements Serializable {

    private ArrayList<Trip> tripArrayList;
    private static Trips instance;

    private Trips() {
        tripArrayList = new ArrayList<Trip>();
    }

    public void setTripArrayList(ArrayList<Trip> tripArrayList) {
        this.tripArrayList = tripArrayList;
    }

    public ArrayList<Trip> getTripArrayList() {
        return tripArrayList;
    }

    public static Trips getInstance() {
        if (instance == null) {
            instance = new Trips();
        }
        return instance;
    }

}
