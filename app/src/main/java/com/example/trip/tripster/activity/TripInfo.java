package com.example.trip.tripster.activity;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.trip.tripster.R;
import com.example.trip.tripster.fragment.PaymentFragment;
import com.example.trip.tripster.fragment.PlaceFragment;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

public class TripInfo extends AppCompatActivity implements PlaceFragment.OnFragmentInteractionListener, PaymentFragment.OnFragmentInteractionListener {

    private Trip myTrip;
    private Trips trips;
    private int tripPosition;
    private Fragment selectedFragment;
    private TextView header;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info);
    }

    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        }
    }
}
