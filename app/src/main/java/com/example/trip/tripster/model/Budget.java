package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rush on 2017-12-11.
 */

public class Budget implements Serializable {

    private static final long serialVersionUID = 42L;

    private double maxBudget;
    private double amountSpent;
    private ArrayList payments;

    public Budget(double maxBudget) {
        this.maxBudget = maxBudget;
        amountSpent = 0;
        payments = new ArrayList<Payment>();
    }

    public void addPayment(double amount, String reason) {
        amountSpent += amount;
        payments.add(new Payment(amount, reason));
    }

    public void removePayment(int position) {
        Payment pToRemove = (Payment) payments.get(position);
        amountSpent -= pToRemove.getAmount();
        payments.remove(position);
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public ArrayList getPayments() {
        return payments;
    }
}