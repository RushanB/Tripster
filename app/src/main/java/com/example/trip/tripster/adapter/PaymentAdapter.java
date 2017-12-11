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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentView> {

    private final Trip myTrip;
    private Context context;
    private RecyclerViewListener listener;


    public class PaymentView extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView amountPaid;

        PaymentView(View itemView){
            super(itemView);

            amountPaid = (TextView) itemView.findViewById(R.id.payment_Amount);

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

    public PaymentAdapter(Context context, Trip trip, RecyclerViewListener listener) {
        myTrip = trip;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public PaymentView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_payment, parent, false);

        return new PaymentView(v);
    }

    @Override
    public void onBindViewHolder(PaymentView holder, int position) {
        Payment payment = (Payment) myTrip.getTripBudget().getPayments().get(position);
        holder.amountPaid.setText(Double.toString(payment.getTotal()));
    }

    @Override
    public int getItemCount() {
        return myTrip.getTripBudget().getPayments().size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
