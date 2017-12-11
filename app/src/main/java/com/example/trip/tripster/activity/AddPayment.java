package com.example.trip.tripster.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.tripster.R;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

public class AddPayment extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Trip myTrip;
    Toolbar toolbar;
    TextView totalTextF;
    int selectedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        //payments
        totalTextF = (TextView)findViewById(R.id.totalAmount);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.addPaymentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //get the trips position
        Trips trips = Trips.getInstance();
        int tripPosition = getIntent().getExtras().getInt("tripPosition");
        myTrip = trips.getTripArrayList().get(tripPosition);
    }

    public boolean addPayment(MenuItem menuItem) {
        if (totalTextF.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Total Amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        finish();
        return true;
    }

    //Show which position is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, position, Toast.LENGTH_SHORT).show();
        selectedPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }

    //Show the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_payment, menu);

        return true;
    }

    //Action bar will handle the clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.save) {
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        if (!totalTextF.getText().toString().matches("")) {
            intent.putExtra("paymentAmountField", totalTextF.getText().toString());

            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        super.finish();
    }

}
