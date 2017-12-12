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
import com.example.trip.tripster.model.Payment;
import com.example.trip.tripster.model.Trip;

/**
 * Created by rush on 2017-12-08.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {


    private final Trip tripToDetail;
    private RecyclerViewListener itemListener;

    private Context context;

    public class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView amountPaid;
        TextView reasonPaid;

        PaymentViewHolder(View itemView) {
            super(itemView);

            amountPaid = (TextView) itemView.findViewById(R.id.payment_Amount);
            reasonPaid = (TextView) itemView.findViewById(R.id.payment_Reason);

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

    public PaymentAdapter(Context context, Trip trip, RecyclerViewListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
        tripToDetail = trip;
    }

    //Create new views
    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.content_payment, viewGroup, false);
        return new PaymentViewHolder(v);
    }

    //Replace the contents of a view
    @Override
    public void onBindViewHolder(PaymentViewHolder paymentViewHolder, int i) {
        Payment p = (Payment) tripToDetail.getTripBudget().getPayments().get(i);
        paymentViewHolder.amountPaid.setText(Double.toString(p.getAmount()));
        paymentViewHolder.reasonPaid.setText(p.getReason());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Return the size of your data
    @Override
    public int getItemCount() {
        return tripToDetail.getTripBudget().getPayments().size();
    }
}
