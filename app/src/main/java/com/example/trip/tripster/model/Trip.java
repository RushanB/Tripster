package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by rush on 2017-12-11.
 */

public class Trip implements Serializable {

    private String tripName;
    private Budget tripBudget;
    private String tripLocation;
    private ArrayList<Item> items;

    private static final long serialVersionUID = 56L;

    public Trip(String name, double maxBudget) {
        tripName = name;
        tripBudget = new Budget(maxBudget);
        items = new ArrayList<Item>();
    }

    public Trip(String name, double maxBudget, String targetLocation) {
        tripName = name;
        tripBudget = new Budget(maxBudget);
        tripLocation = targetLocation;
        items = new ArrayList<Item>();
    }

    public void addItem(String name) {
        items.add(new Item(name));
    }

    public void addItem(String name, GregorianCalendar start, GregorianCalendar end) {
        items.add(new Item(name, start, end));
    }

    public void addItem(String name, String placeId, GregorianCalendar start, GregorianCalendar end) {
        items.add(new Item(name, placeId, start, end));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getTripName() {
        return tripName;
    }

    public Budget getTripBudget() {
        return tripBudget;
    }

}
