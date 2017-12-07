package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rush on 2017-12-06.
 */

public class Trip implements Serializable {
    private String name;
    private Budget budget;
    private String location;
    private ArrayList<Place> places;

    public Trip(String name, double maxBudget) {

    }
}
