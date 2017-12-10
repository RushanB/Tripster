package com.example.trip.tripster.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rush on 2017-12-06.
 */

public class Budget implements Serializable{

    private static final long serialVersionUID = 42L;
    private ArrayList payments;
    private double totalCost;
    private double amountSpent;

    public Budget(double totalCost) {

        this.totalCost = totalCost;
        amountSpent = 0;
        payments = new ArrayList<Payment>();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public ArrayList getPayments() {
        return payments;
    }

    public void addPayment(double total) {
        totalCost += totalCost;

        payments.add(new Payment(total));
    }

    public void removePayment(int position) {
        Payment payment = (Payment) payments.get(position);

        amountSpent -= payment.getTotal();
        payments.remove(position);
    }

}
