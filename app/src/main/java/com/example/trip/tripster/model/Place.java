package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by rush on 2017-12-06.
 */

public class Place implements Serializable {

    private static final long serialVersionUID = 48L;
    private String placeName;
    private String placeLocation;
    GregorianCalendar startTime;
    GregorianCalendar endTime;

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public Place(String name) {
        placeName = name;
        startTime = new GregorianCalendar();
        endTime = new GregorianCalendar();
    }

    public Place(String name, GregorianCalendar start, GregorianCalendar end) {
        placeName = name;
        startTime = start;
        endTime = end;
    }

    public Place(String name, String location, GregorianCalendar start, GregorianCalendar end) {
        placeName = name;
        placeLocation = location;
        startTime = start;
        endTime = end;
    }


}
