package com.example.trip.tripster.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trip.tripster.R;
import com.example.trip.tripster.RecyclerViewListener;
import com.example.trip.tripster.activity.AddInfo;
import com.example.trip.tripster.model.Trip;

import java.util.List;

/**
 * Created by rush on 2017-12-06.
 */


public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

private List<Trip> trips;
private Context context;
private RecyclerViewListener itemListener;

private static final String TAG = "";

public class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    CardView cvTrip;
    TextView tripName;
    TextView tripBudget;

    TripViewHolder(View itemView) {
        super(itemView);
        cvTrip = (CardView) itemView.findViewById(R.id.cvTrip);
        tripName = (TextView) itemView.findViewById(R.id.trip_name);
        tripBudget = (TextView) itemView.findViewById(R.id.trip_budget);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void onClick(View v) {
        itemListener.recyclerViewClicked(v, this.getAdapterPosition());
        Intent tripDetailsIntent = new Intent(context, AddInfo.class);
        tripDetailsIntent.putExtra("tripPosition", (Integer)getAdapterPosition());

        context.startActivity(tripDetailsIntent);
    }

    @Override
    public boolean onLongClick(View v) {
        itemListener.recyclerViewClicked(v, this.getAdapterPosition());
        itemView.showContextMenu();
        return true;
    }
}

    public TripAdapter(Context context, List<Trip> trips, RecyclerViewListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
        this.trips = trips;
    }

    //Create new views
    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.content_trip, viewGroup, false);
        return new TripViewHolder(v);
    }

    //Replace the contents of a view
    @Override
    public void onBindViewHolder(TripViewHolder tripViewHolder, int i) {
        String budgetToSet = "Budget: " + Double.toString(
                trips.get(i).getTripBudget().getMaxBudget());
        tripViewHolder.tripName.setText(trips.get(i).getTripName());
        tripViewHolder.tripBudget.setText(budgetToSet);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Return the size of your data
    @Override
    public int getItemCount() {
        return trips.size();
    }
}
