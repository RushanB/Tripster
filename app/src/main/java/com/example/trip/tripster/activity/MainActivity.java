package com.example.trip.tripster.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.trip.tripster.R;
import com.example.trip.tripster.RecyclerViewListener;
import com.example.trip.tripster.adapter.TripAdapter;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewListener {

    public static final String fileName = "trips";
    private Trips trips;
    private String tripNameId = "NameTextF";
    private String tripBudgetId = "BudgetTextF";
    private RecyclerView tripRecycler;
    private RecyclerView.Adapter tripAdapter;
    private int adapterPosition;
    private ArrayList<Trip> tripArrayList;
    public static final int TRIP_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerForContextMenu(findViewById(R.id.myTrips));

        tripArrayList = new ArrayList<>();
        try {
            File file = new File(this.getFilesDir(), fileName);
            if (file == null || !file.exists()) {
                FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                Trip first = new Trip("European Adventure", 1200.0);
                Trip second = new Trip("The Grand Canyon", 500.0);
                tripArrayList.add(first);
                tripArrayList.add(second);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(tripArrayList);

                fileOutputStream.close();
                objectOutputStream.close();
            } else {
                FileInputStream fileInputStream = openFileInput(fileName);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                tripArrayList = (ArrayList<Trip>) objectInputStream.readObject();

                fileInputStream.close();
                objectInputStream.close();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Trip 1", Toast.LENGTH_SHORT).show();
            Log.e("", "exception", e);
        }
        //set instance of trips
        trips = Trips.getInstance();
        trips.setTripArrayList(tripArrayList);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        //Set title
        setTitle("My Trips");

        if(getIntent().hasExtra(tripNameId) && getIntent().hasExtra(tripBudgetId)) {
            addTrip(new Trip(getIntent().getStringExtra(tripNameId),Double.parseDouble((getIntent().getStringExtra(tripBudgetId)))));
        }
        //set recycler view
        tripRecycler = (RecyclerView) findViewById(R.id.myTrips);
        tripRecycler.setHasFixedSize(false);
        //linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        tripRecycler.setLayoutManager(layoutManager);
        tripAdapter = new TripAdapter(getBaseContext(), tripArrayList, this);
        tripRecycler.setAdapter(tripAdapter);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request,result,data);

        if(request == TRIP_REQUEST && result == RESULT_OK) {
            if (data.hasExtra(tripNameId) && data.hasExtra(tripBudgetId)) {
                addTrip(new Trip(data.getStringExtra(tripNameId), Double.parseDouble((data.getStringExtra(tripBudgetId)))));
            }
        }
    }

    @Override
    public void recyclerViewClicked(View v, int position) {
        adapterPosition = position;
    }

    public void newTrip(View v) {
        //set return intents
        Intent newTripIntent = new Intent(this, AddTrip.class);
        startActivityForResult(newTripIntent, TRIP_REQUEST);
    }

    public void addTrip(Trip newTrip) {
        tripArrayList.add(newTrip);
        tripAdapter.notifyDataSetChanged();
        updateTripList();
    }

    public void updateTripList() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tripArrayList);

            Trips.getInstance().setTripArrayList(tripArrayList);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Trip 2", Toast.LENGTH_SHORT).show();
            Log.e("","exception", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trips, menu);

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trips_delete, menu);
    }

    //Automatically handle clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.settings) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.action_delete) {

            tripArrayList.remove(adapterPosition);

            updateTripList();

            tripAdapter.notifyDataSetChanged();

            return true;
        }
        return super.onContextItemSelected(menuItem);
    }


}
