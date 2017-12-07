package com.example.trip.tripster.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AbsListView;

import com.example.trip.tripster.R;
import com.example.trip.tripster.model.Trip;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AbsListView.RecyclerListener {

    public static final String file = "trips";
    private Trips trips;
    private String tripNameId = "TripName";
    private String tripBudgetId = "TripBudget";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private int adapterPosition;
    private ArrayList<Trip> tripArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerForContextMenu(findViewById(R.id.));
    }

    @Override
    public void onMovedToScrapHeap(View view) {

    }
}
