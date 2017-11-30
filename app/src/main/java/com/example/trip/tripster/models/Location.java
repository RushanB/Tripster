package com.example.trip.tripster.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rush on 2017-11-30.
 */

public class Location implements Parcelable {
    private long id;
    private String name;
    private String address;
    private String googleId;
    private double latitude;
    private double longitude;

    Location(){

    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Location(Parcel parcel) {
        id = parcel.readLong();
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    String getGoogleId() {
        return googleId;
    }
    void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    double getLatitude() {
        return latitude;
    }

    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    double getLongitude() {
        return longitude;
    }

    void setLongitude(double longitude) {
        this.latitude = longitude;
    }

    @Override
    public String toString() {
        return String.format();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(getId());
        parcel.writeString(getName());
        parcel.writeString(getAddress());
        parcel.writeString(getGoogleId());
        parcel.writeDouble(getLatitude());
        parcel.writeDouble(getLongitude());
    }

    private Location(Parcel parcel) {
        id = parcel.readLong();
        name = parcel.readString();
        address = parcel.readString();
        googleId = parcel.readString();
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
    }


}
