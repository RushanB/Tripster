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

    TextView nameTextF;
    TextView budgetTextF;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        nameTextF = (TextView) findViewById(R.id.tripName);

        budgetTextF = (TextView) findViewById(R.id.tripBudget);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    public boolean addTrip(MenuItem menuItem) {
        if (nameTextF.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (budgetTextF.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Budget", Toast.LENGTH_SHORT).show();
            return false;
        }
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int myId = item.getItemId();
        if(myId == R.id.save) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        if (!nameTextF.getText().toString().matches("") && !budgetTextF.getText().toString().matches("")) {
            intent.putExtra("nameTextF", nameTextF.getText().toString());
            intent.putExtra("budgetTextF", budgetTextF.getText().toString());

            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        super.finish();
    }
}
