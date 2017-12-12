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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.tripster.R;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

public class AddPayment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Trip tripToDetail;
    Toolbar myToolbar;
    TextView paymentAmountField;
    TextView paymentDescriptionField;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        //Get the clicked adapter position
        Trips trips = Trips.getInstance();
        int tripPosition = getIntent().getExtras().getInt("tripPosition");
        tripToDetail = trips.getTripList().get(tripPosition);

        //toolbar
        myToolbar = (Toolbar) findViewById(R.id.createPaymentToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        paymentAmountField = (TextView)findViewById(R.id.payment_amount_field);
        paymentDescriptionField = (TextView)findViewById(R.id.payment_description_field);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_payment, menu);

        return true;
    }

    public boolean createPayment(MenuItem menu) {
        if (paymentAmountField.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (paymentDescriptionField.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Reason", Toast.LENGTH_SHORT).show();
            return false;
        }
        finish();
        return true;
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        if (!paymentAmountField.getText().toString().matches("") && !paymentDescriptionField.getText().toString().matches("") ) {
            returnIntent.putExtra("paymentAmountField", paymentAmountField.getText().toString());
            returnIntent.putExtra("paymentDescriptionField", paymentDescriptionField.getText().toString());
            setResult(RESULT_OK, returnIntent);
        } else {
            setResult(RESULT_CANCELED, returnIntent);
        }
        super.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
        selectedPosition = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
