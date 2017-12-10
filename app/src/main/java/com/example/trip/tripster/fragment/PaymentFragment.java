package com.example.trip.tripster.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.trip.tripster.R;
import com.example.trip.tripster.RecyclerViewListener;
import com.example.trip.tripster.activity.AddPayment;
import com.example.trip.tripster.activity.TripInfo;
import com.example.trip.tripster.adapter.PaymentAdapter;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment implements RecyclerViewListener {

    private Trip myTrip;
    private Trips trips;
    private int tripPosition;
    private int adapterPosition;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter paymentAdapter;
    private OnFragmentInteractionListener mListener;

    public PaymentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(int tripPosition) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();

        args.putInt("tripPosition", tripPosition);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        registerForContextMenu(view.findViewById(R.id.myPayments));

        trips = Trips.getInstance();
        if (getArguments() != null) {
            tripPosition = getArguments().getInt("tripPosition");
            myTrip = trips.getTripArrayList().get(tripPosition);
        }
        //recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.myPayments);
        recyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        paymentAdapter = new PaymentAdapter(view.getContext(), myTrip, this);
        recyclerView.setAdapter(paymentAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return view;
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (1 == request) {
            if (Activity.RESULT_OK == result) {
                if (data.hasExtra("paymountAmount")) {
                    myTrip.getTripBudget().addPayment(Double.parseDouble(data.getStringExtra("paymentAmount")));

                    paymentAdapter.notifyDataSetChanged();

                    ((TripInfo)getActivity()).adjustTitle();
                    ((TripInfo) getActivity()).adjustTrip();
                }
            } else {
                super.onActivityResult(request, result, data);
            }
        } else if (2 == request) {
            super.onActivityResult(request, result, data);
        }
    }

    public void updatePaymentForTrip() {
        Intent intent = new Intent(getActivity(), AddPayment.class);
        intent.putExtra("tripPosition", tripPosition);

        startActivityForResult(intent, 1);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.id.action_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            myTrip.getTripBudget().removePayment(adapterPosition);
            paymentAdapter.notifyDataSetChanged();

            ((TripInfo)getActivity()).adjustTitle();
            ((TripInfo)getActivity()).adjustTrip();

            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void calculatePayments() {
        Intent intent = new Intent(getActivity(), AddPayment.class);
        intent.putExtra("tripPosition", tripPosition);

        startActivityForResult(intent, 3);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void recyclerViewClicked(View v, int position) {
        adapterPosition = position;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable drawable;

        public SimpleDividerItemDecoration(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.line);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int lSide = parent.getPaddingLeft();
            int rSide = parent.getWidth() - parent.getPaddingRight();

            int children = parent.getChildCount();
            for (int i=0; i < children; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

                int tSide = child.getBottom() + layoutParams.bottomMargin;
                int bSide = tSide + drawable.getIntrinsicHeight();

                drawable.setBounds(lSide, rSide, tSide, bSide);
                drawable.draw(c);
            }
        }
    }
}
