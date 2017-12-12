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
import com.example.trip.tripster.fragment.ItemsFragment;
import com.example.trip.tripster.fragment.PaymentsFragment;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class AddInfo extends AppCompatActivity implements
        ItemsFragment.OnFragmentInteractionListener,
        PaymentsFragment.OnFragmentInteractionListener{

    private Trips trips;
    private Trip tripToDetail;
    private int tripPosition;

    private Fragment selectedFragment;
    private TextView header;

    private Button calcPaymentsButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    calcPaymentsButton.setVisibility(View.INVISIBLE);
                    selectedFragment = ItemsFragment.newInstance(tripPosition);
                    break;
                case R.id.navigation_payments:
                    calcPaymentsButton.setVisibility(View.INVISIBLE);  //visible
                    selectedFragment = PaymentsFragment.newInstance(tripPosition);
                    break;
                default:
                    break;
            }

            updateHeader();
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.tripDetails, selectedFragment);
            transaction.commit();
            return true;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        //Get the clicked adapter position
        trips = Trips.getInstance();
        if (getIntent().hasExtra("tripPosition")) {
            tripPosition = getIntent().getExtras().getInt("tripPosition");
        }
        tripToDetail = trips.getTripList().get(tripPosition);

        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tripDetailsToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(tripToDetail.getTripName());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.menu_tabs);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        selectedFragment = ItemsFragment.newInstance(tripPosition);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tripDetails, selectedFragment);
        transaction.commit();

        header = (TextView) findViewById(R.id.trip_Details_Header);
        calcPaymentsButton = (Button) findViewById(R.id.calcPaymentsButton);
        calcPaymentsButton.setVisibility(View.INVISIBLE);
        updateHeader();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    public void createTripDetails(View v) {
        if (selectedFragment.getClass().equals(ItemsFragment.class)) {
            ((ItemsFragment) selectedFragment).addEventToTrip();
        } else if (selectedFragment.getClass().equals(PaymentsFragment.class)) {
            ((PaymentsFragment) selectedFragment).addPaymentToTrip();
        }
    }

    public void updateTrips() {
        try {
            FileOutputStream fileOutputStream = openFileOutput("trips", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(trips.getTripList());

            fileOutputStream.close();
            oos.close();
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Trip", Toast.LENGTH_SHORT).show();
            Log.e("", "exception", e);
        }
    }

    public void updateHeader() {
        if (selectedFragment.getClass().equals(ItemsFragment.class)) {
            header.setText("Total Events: " + tripToDetail.getItems().size());
        } else if (selectedFragment.getClass().equals(PaymentsFragment.class)) {
            header.setText("Amount Spent: " + tripToDetail.getTripBudget().getAmountSpent());
        }
    }

    public boolean openCalculatePayments(View v) {
        ((PaymentsFragment) selectedFragment).calculatePayments();
        return true;
    }
}
