package com.example.trip.tripster.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trip.tripster.R;
import com.example.trip.tripster.RecyclerViewListener;
import com.example.trip.tripster.model.Item;
import com.example.trip.tripster.model.Trip;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by rush on 2017-12-11.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewHolder> {

    private final Trip tripToDetail;
    private RecyclerViewListener itemListener;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;

    private Context context;

    public class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cvEvents;
        TextView eventName;
        TextView eventDate;
        TextView eventTime;

        ItemsViewHolder(View itemView) {
            super(itemView);
            cvEvents = (CardView) itemView.findViewById(R.id.cvEvents);
            eventName = (TextView) itemView.findViewById(R.id.event_Name);
            eventDate = (TextView) itemView.findViewById(R.id.Date);
            eventTime = (TextView) itemView.findViewById(R.id.Time);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void onClick(View v) {
            itemListener.recyclerViewClicked(v, this.getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            itemListener.recyclerViewClicked(v, this.getAdapterPosition());
            itemView.showContextMenu();
            return true;
        }
    }

    public ItemAdapter(Context context, Trip trip, RecyclerViewListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
        tripToDetail = trip;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.content_item, viewGroup, false);
        timeFormat = new SimpleDateFormat("hh:mm aa");
        dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        return new ItemsViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ItemsViewHolder eventsViewHolder, int i) {
        Item e = (Item) tripToDetail.getItems().get(i);
        eventsViewHolder.eventName.setText(e.getName());

        String startDate = formatDate(dateFormat, e.getStartTime());
        String endDate = formatDate(dateFormat, e.getEndTime());
        String startTime = formatDate(timeFormat, e.getStartTime());
        String endTime = formatDate(timeFormat, e.getEndTime());

        if (startDate.equals(endDate)) {
            eventsViewHolder.eventDate.setText(startDate);
        } else {
            eventsViewHolder.eventDate.setText(startDate + " - " + endDate);
        }

        if (startTime.equals(endTime)) {
            eventsViewHolder.eventTime.setText(startTime);
        } else {
            eventsViewHolder.eventTime.setText(startTime + " - " + endTime);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return tripToDetail.getItems().size();
    }

    public String formatDate(SimpleDateFormat fmt, GregorianCalendar calendar) {
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}