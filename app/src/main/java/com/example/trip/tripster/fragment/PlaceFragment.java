package com.example.trip.tripster.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trip.tripster.R;
import com.example.trip.tripster.RecyclerViewListener;
import com.example.trip.tripster.activity.AddPlace;
import com.example.trip.tripster.activity.TripInfo;
import com.example.trip.tripster.adapter.PlacesAdapter;
import com.example.trip.tripster.model.Trip;
import com.example.trip.tripster.model.Trips;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.GregorianCalendar;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceFragment extends Fragment implements RecyclerViewListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, ResultCallback<PlaceBuffer> {


    private Trip myTrip;
    private Trips trips;
    private int tripPosition;
    private GoogleApiClient googleApiClient;
    private Place myPlace; //google places
    String placeName;
    private RecyclerView placeRecyclerView;
    private RecyclerView.Adapter placeAdapter;
    private int adapterPosition;
    private OnFragmentInteractionListener mListener;

    public PlaceFragment() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance(int tripPosition) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putInt("tripPosition", tripPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        registerForContextMenu(view.findViewById(R.id.myPlaces));

        trips = Trips.getInstance();
        if(getArguments() != null) {
            tripPosition = getArguments().getInt("tripPosition");
            myTrip = trips.getTripArrayList().get(tripPosition);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        placeRecyclerView.setLayoutManager(layoutManager);

        placeRecyclerView = (RecyclerView) view.findViewById(R.id.myPlaces);
        placeRecyclerView.setHasFixedSize(false);

        placeAdapter = new PlacesAdapter(view.getContext(), myTrip, this);
        placeRecyclerView.setAdapter(placeAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request == 1) {
            if (Activity.RESULT_OK == result) {
                if (data.hasExtra("PlaceName") && data.hasExtra("StartInfo") && data.hasExtra("EndInfo") && data.hasExtra("Address")) {
                    myTrip.addPlace(data.getStringExtra("PlaceName"), data.getStringExtra("Address"), (GregorianCalendar)data.getSerializableExtra("StartInfo"), (GregorianCalendar)data.getSerializableExtra("EndInfo"));

                    placeAdapter.notifyDataSetChanged();

                    ((TripInfo)getActivity()).adjustTrip();
                    ((TripInfo)getActivity()).adjustTitle();

                    Toast.makeText(getActivity(), data.getStringExtra("Address"), Toast.LENGTH_SHORT).show();
                } else if (data.hasExtra("PlaceName") && data.hasExtra("StartInfo") && data.hasExtra("EndInfo")) {
                    myTrip.addPlace(data.getStringExtra("PlaceName"), data.getStringExtra("Address"), (GregorianCalendar)data.getSerializableExtra("StartInfo"), (GregorianCalendar)data.getSerializableExtra("EndInfo"));

                    placeAdapter.notifyDataSetChanged();

                    ((TripInfo)getActivity()).adjustTrip();
                    ((TripInfo)getActivity()).adjustTitle();
                }
            } else {
                super.onActivityResult(request, result, data);
            }
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_trips_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_delete) {
            myTrip.getMyPlaces().remove(adapterPosition);
            placeAdapter.notifyDataSetChanged();

            ((TripInfo)getActivity()).adjustTrip();
            ((TripInfo)getActivity()).adjustTitle();

            return true;
        }
        return super.onContextItemSelected(menuItem);
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

    public void updatePlaceForTrip() {
        Intent intent = new Intent(getActivity(), AddPlace.class);
        startActivityForResult(intent, 1);
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

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("","Google Places API Connection Successful");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("","Google Places API Connection Has Ended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("","Google Places API Connection Failed - " + connectionResult.getErrorCode() + connectionResult.getErrorMessage());

        Toast.makeText(getActivity(), "Google Places API Connection Failed - " + connectionResult.getErrorMessage() + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResult(@NonNull PlaceBuffer places) {
        if (places.getStatus().isSuccess() && places.getCount() > 0) {
            myPlace = places.get(0);
            Log.i(TAG, "Place Found: " + myPlace.getName());
        } else {
            Log.e(TAG, "Place Not Found");
        }
        places.release();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
