package com.example.trip.tripster.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.trip.tripster.R;

public class MainActivity extends AppCompatActivity {

     SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SearchView searchView = (SearchView) findViewById(R.id.searchPlaces);
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(this, AddPlaces.class);
//                startActivity(i);
//            }
//        });
    }
}
