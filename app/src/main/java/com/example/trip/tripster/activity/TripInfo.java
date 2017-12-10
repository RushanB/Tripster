package com.example.trip.tripster.activity;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.tripster.R;
import com.example.trip.tripster.fragment.PaymentFragment;
import com.example.trip.tripster.fragment.PlaceFragment;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class TripInfo extends AppCompatActivity implements PlaceFragment.OnFragmentInteractionListener, PaymentFragment.OnFragmentInteractionListener {

    private Trip myTrip;
    private Trips trips;
    private TextView title;
    private Button calculateButton;
    private int tripPosition;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info);

        //get the clicked trip position
        trips = Trips.getInstance();
        if (getIntent().hasExtra("tripPosition")) {
            tripPosition = getIntent().getExtras().getInt("tripPosition");
        }
        myTrip = trips.getTripArrayList().get(tripPosition);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tripInfoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(myTrip.getTripName());

        BottomNavigationView navigationView = (BottomNavigationView)  findViewById(R.id.tabs);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        selectedFragment = PlaceFragment.newInstance(tripPosition);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tripInformation, selectedFragment);
        transaction.commit();


        title = (TextView) findViewById(R.id.infoTitle);
        calculateButton = (Button) findViewById(R.id.calculatePayments);
        calculateButton.setVisibility(View.INVISIBLE);
        adjustTitle();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public boolean openCalculatePayments(View v) {
        ((PaymentFragment)selectedFragment).calculatePayments();
        return true;
    }


    public void adjustTitle() {
        if (selectedFragment.getClass().equals(PlaceFragment.class)) {
            title.setText("Total Places: " + myTrip.getMyPlaces().size());
        } else if (selectedFragment.getActivity().equals(PaymentFragment.class)) {
            title.setText("Amount Spent: " + myTrip.getTripBudget().getAmountSpent());
        }
    }

    public void adjustTrip() {
        try {
            FileOutputStream fileOutputStream = openFileOutput("trips", Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(trips.getTripArrayList());

            fileOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Trip", Toast.LENGTH_SHORT).show();
            Log.e("","exception",e);
        }
    }

    public void addTripInfo(View v) {
        if(selectedFragment.getClass().equals(PlaceFragment.class)) {
            ((PlaceFragment) selectedFragment).updatePlaceForTrip();
        } else if (selectedFragment.getClass().equals(PaymentFragment.class)) {
            ((PaymentFragment)selectedFragment).updatePaymentForTrip();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tabPlaces:
                    calculateButton.setVisibility(View.INVISIBLE);
                    selectedFragment = PlaceFragment.newInstance(tripPosition);
                    break;
                case R.id.tabPayments:
                    calculateButton.setVisibility(View.VISIBLE);
                    selectedFragment = PaymentFragment.newInstance(tripPosition);
                    break;
                default:
                    break;
            }

            adjustTitle();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.tripInformation, selectedFragment);
            transaction.commit();

            return true;
        }
    };
}
