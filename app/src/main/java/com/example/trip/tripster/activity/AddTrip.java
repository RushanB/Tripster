package com.example.trip.tripster.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.tripster.R;

public class AddTrip extends AppCompatActivity {

    Toolbar myToolbar;
    TextView tripNameField;
    TextView tripBudgetField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        //toolbar
        myToolbar = (Toolbar) findViewById(R.id.createTripToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tripNameField = (TextView)findViewById(R.id.Title);
        tripBudgetField = (TextView)findViewById(R.id.Budget);
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
        inflater.inflate(R.menu.menu_add_trip, menu);
        return true;
    }

    public boolean createTrip(MenuItem menu) {
        if (tripNameField.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();

            return false;
        } else if (tripBudgetField.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();

            return false;
        }
        finish();
        return true;
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent(getBaseContext(), MainActivity.class);

        if (!tripNameField.getText().toString().matches("") && !tripBudgetField.getText().toString().matches("")) {

            returnIntent.putExtra("TripNameField", tripNameField.getText().toString());
            returnIntent.putExtra("TripBudgetField", tripBudgetField.getText().toString());

            setResult(RESULT_OK, returnIntent);
        } else {
            setResult(RESULT_CANCELED, returnIntent);
        }
        super.finish();
    }
}
