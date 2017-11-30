package com.example.trip.tripster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;.

import com.example.trip.tripster.models.Location;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AddPlaces extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int PLACE_PICKER_REQUEST = 100;
    private static final int EDIT_REQUEST = 200;

    private GoogleApiClient googleApiClient;
    private Location



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);
    }
}
