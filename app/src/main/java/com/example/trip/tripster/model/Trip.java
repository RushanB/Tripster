package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by rush on 2017-12-06.
 */

public class Trip implements Serializable {
    private String tripName;
    private Budget tripBudget;
    private String tripLocation;
    private ArrayList<Place> myPlaces;

    public Trip(String name, double total) {
        tripName = name;
        tripBudget = new Budget(total);
        myPlaces = new ArrayList<Place>();
    }

    public Trip(String name, double total, String location) {
        tripName = name;
        tripBudget = new Budget(total);
        tripLocation = location;
        myPlaces = new ArrayList<Place>();
    }

    public Budget getTripBudget() {
        return tripBudget;
    }

    public String getTripName() {
        return tripName;
    }

    public ArrayList<Place> getMyPlaces() {
        return myPlaces;
    }

    public void addPlace(String name) {
        myPlaces.add(new Place(name));
    }

    public void addPlace(String name, GregorianCalendar startDate, GregorianCalendar endDate) {
        myPlaces.add(new Place(name, startDate, endDate));
    }

    public void addPlace(String name, String placeId, GregorianCalendar startDate, GregorianCalendar endDate) {
        myPlaces.add(new Place(name, placeId, startDate, endDate));
    }
}
