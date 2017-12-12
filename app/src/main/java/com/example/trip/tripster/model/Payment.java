package com.example.trip.tripster.model;

import java.io.Serializable;

/**
 * Created by rush on 2017-12-11.
 */

public class Payment implements Serializable {

    private double amount;
    private String reason;

    private static final long serialVersionUID = 23L;

    public Payment(double amount, String reason) {
        this.amount = amount;
        this.reason = reason;
    }

    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

}
