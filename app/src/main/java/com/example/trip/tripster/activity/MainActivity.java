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

public class MainActivity  extends AppCompatActivity implements RecyclerViewListener {

    private static final String newTripNameId = "TripNameField";
    private static final String newTripBudgetId = "TripBudgetField";
    private RecyclerView tripRecyclerView;
    private RecyclerView.Adapter tripAdapter;
    private int adapterPosition;

    public static final int CREATE_TRIP_REQUEST = 1;
    public static final String fileName = "trips";
    private ArrayList<Trip> tripList;
    private Trips trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerForContextMenu(findViewById(R.id.rvTrips));
        tripList = new ArrayList<>();

        try {
            File file = new File(this.getFilesDir(), fileName);
            if (file == null || !file.exists()) {
                FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

                //TESTING®
//                Trip firstTrip = new Trip("European Adventure", 1200);
//                Trip secondTrip = new Trip("The Grand Canyon", 800);
//                tripList.add(firstTrip);
//                tripList.add(secondTrip);

                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(tripList);

                fileOutputStream.close();
                oos.close();
            } else {
                FileInputStream fileInputStream = openFileInput(fileName);
                ObjectInputStream ois = new ObjectInputStream(fileInputStream);
                tripList = (ArrayList<Trip>) ois.readObject();
                fileInputStream.close();
                ois.close();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Trip", Toast.LENGTH_SHORT).show();
            Log.e("", "exception", e);
        }
        //Set up singleton
        trips = Trips.getInstance();

        trips.setTripList(tripList);

        //Toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.tripActivityToolbar);
        setSupportActionBar(myToolbar);

        setTitle("Tripster");

        if (getIntent().hasExtra(newTripNameId) && getIntent().hasExtra(newTripBudgetId)) {
            addTrip(new Trip(getIntent().getStringExtra(newTripNameId),
                    Double.parseDouble((getIntent().getStringExtra(newTripBudgetId)))));
        }

        //create the recyclerview
        tripRecyclerView = (RecyclerView) findViewById(R.id.rvTrips);
        //improves performance
        //do not change the layout size of the RecyclerView
        tripRecyclerView.setHasFixedSize(false);
        //linear layout manager
        LinearLayoutManager llm = new LinearLayoutManager(this);
        tripRecyclerView.setLayoutManager(llm);

        //specify an adapter
        tripAdapter = new TripAdapter(getBaseContext(), tripList, this);
        tripRecyclerView.setAdapter(tripAdapter);
    }

    public void createTrip(View view) {
        //Set up return intents
        Intent createTripIntent = new Intent(this, AddTrip.class);
        startActivityForResult(createTripIntent, CREATE_TRIP_REQUEST);
    }

    public void addTrip(Trip tripToAdd) {
        tripList.add(tripToAdd);
        tripAdapter.notifyDataSetChanged();
        updateTrips();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        inflater.inflate(R.menu.menu_action_trip, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            tripList.remove(adapterPosition);
            updateTrips();
            tripAdapter.notifyDataSetChanged();

            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void recyclerViewClicked(View v, int position) {
        adapterPosition = position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_TRIP_REQUEST && resultCode == RESULT_OK) {

            if (data.hasExtra(newTripNameId) && data.hasExtra(newTripBudgetId)) {

                addTrip(new Trip(data.getStringExtra(newTripNameId),
                        Double.parseDouble((data.getStringExtra(newTripBudgetId)))));
            }
        }
    }

    public void updateTrips() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(tripList);
            Trips.getInstance().setTripList(tripList);

            fileOutputStream.close();
            oos.close();

        } catch (Exception e) {
            Toast.makeText(this, "Invalid Trip", Toast.LENGTH_SHORT).show();

            Log.e("", "exception", e);
        }
    }
}
