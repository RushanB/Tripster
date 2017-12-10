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
import com.example.trip.tripster.activity.TripInfo;
import com.example.trip.tripster.model.Trip;

import java.util.List;

/**
 * Created by rush on 2017-12-06.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripView> {

    private List<Trip> tripList;
    private RecyclerViewListener listener;
    private Context context;
    private static final String TAG = "";

    public TripAdapter(Context context, List<Trip> tripList, RecyclerViewListener listener) {
        this.context = context;
        this.listener = listener;
        this.tripList = tripList;
    }

    @Override
    public TripView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_trip, parent, false);

        return new TripView(v);
    }

    @Override
    public void onBindViewHolder(TripView holder, int position) {
        String myBudget = "Budget: " + Double.toString(tripList.get(position).getTripBudget().getTotalCost());

        holder.name.setText(tripList.get(position).getTripName());
        holder.budget.setText(myBudget);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class TripView extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView name;
        TextView budget;
        CardView cardView;

        TripView(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.trip_Name);
            budget = (TextView)itemView.findViewById(R.id.trip_Budget);
            cardView = (CardView)itemView.findViewById(R.id.tripCard);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void onClick(View v) {
            listener.recyclerViewClicked(v, this.getAdapterPosition());

            Intent intent = new Intent(context, TripInfo.class);
            intent.putExtra("tripPosition", (Integer)getAdapterPosition());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.recyclerViewClicked(v, this.getAdapterPosition());
            itemView.showContextMenu();

            return true;
        }
    }


}
