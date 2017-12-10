package com.example.trip.tripster.model;

import java.io.Serializable;

/**
 * Created by rush on 2017-12-07.
 */

public class Payment implements Serializable {

    private static final long serialVersionUID = 23L;
    private double total;

    public Payment(double total) {

        this.total = total;
    }

    public double getTotal() {
        return total;
    }

}
