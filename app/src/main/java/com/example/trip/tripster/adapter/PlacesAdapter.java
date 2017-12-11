package com.example.trip.tripster.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trip.tripster.R;
import com.example.trip.tripster.RecyclerViewListener;
import com.example.trip.tripster.model.Place;
import com.example.trip.tripster.model.Trip;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by rush on 2017-12-08.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesView>{

    private final Trip myTrip;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;
    private RecyclerViewListener listener;
    private Context context;

    public class PlacesView extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView name;
        TextView date;
        TextView time;
        CardView cardView;

        PlacesView(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.place_Name);
            date = (TextView)itemView.findViewById(R.id.place_Date);
            time = (TextView)itemView.findViewById(R.id.place_Time);
            cardView = (CardView)itemView.findViewById(R.id.placeCard);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void onClick(View v) {
            listener.recyclerViewClicked(v, this.getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.recyclerViewClicked(v, this.getAdapterPosition());
            itemView.showContextMenu();

            return true;
        }
    }

    public PlacesAdapter(Context context, Trip trip, RecyclerViewListener listener) {
        this.context = context;
        myTrip = trip;
        this.listener = listener;
    }

    @Override
    public PlacesView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_place, parent, false);
        dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        timeFormat = new SimpleDateFormat("hh:mm aa");

        return new PlacesView(v);
    }

    @Override
    public void onBindViewHolder(PlacesView holder, int position) {
        Place place = (Place)myTrip.getMyPlaces().get(position);
        holder.name.setText(place.getPlaceName());

        String startDate =  getDate(dateFormat, place.getStartTime());
        String startTime = getDate(timeFormat, place.getStartTime());
        String endDate = getDate(dateFormat, place.getEndTime());
        String endTime = getDate(timeFormat, place.getEndTime());

        if(startDate.equals(endDate)) {
            holder.date.setText(startDate);
        } else {
            holder.date.setText(startDate + " - " + endDate);
        }

        if(startTime.equals(endTime)) {
            holder.time.setText(startTime);
        } else {
            holder.time.setText(startTime + " - " + endTime);
        }
    }

    public String getDate(SimpleDateFormat simpleDateFormat, GregorianCalendar calendar) {
        simpleDateFormat.setCalendar(calendar);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return myTrip.getMyPlaces().size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
