package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by rush on 2017-12-11.
 */

public class Item implements Serializable {

    private static final long serialVersionUID = 48L;

    private GregorianCalendar startTime;
    private GregorianCalendar endTime;

    private String itemName;
    private String itemLocation;

    //Placeholder constructor for view testing
    public Item(String name) {
        itemName = name;
        startTime = new GregorianCalendar();
        endTime = new GregorianCalendar();
    }

    public Item(String name, GregorianCalendar start, GregorianCalendar end) {
        itemName = name;
        startTime = start;
        endTime = end;
    }

    public Item(String name, String targetLocation, GregorianCalendar start, GregorianCalendar end) {
        itemName = name;
        itemLocation = targetLocation;
        startTime = start;
        endTime = end;
    }

    public String getName() {
        return itemName;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public String getItemLocationId() {
        return itemLocation;
    }
}
