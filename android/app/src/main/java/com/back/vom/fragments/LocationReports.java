package com.back.vom.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.back.vom.R;
import com.back.vom.adapters.LocationAdapter;
import com.back.vom.adapters.YourReportsAdapter;
import com.back.vom.models.Report;
import com.back.vom.services.ReportService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sign up step one fragment.
 */
public class LocationReports extends Fragment {

    /**
     * The constant TAG for logs.
     */
    private static final String TAG = LocationReports.class.getSimpleName();

    /**
     * The M view to inflate the fragment view .
     */
    private View mView;
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Instantiates a new Sign up step one fragment.
     */
    public LocationReports() {
    }

    RecyclerView mRecyclerView;
    LocationAdapter mLocationAdapter;
    List<Report> data = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.locationreports, container, false);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mRecyclerView = mView.findViewById(R.id.location_reports_recyclerview);
        mLocationAdapter = new LocationAdapter(data,getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mRecyclerView.setAdapter(mLocationAdapter);

        return mView;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        999);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
        public void onResume () {
            super.onResume();
        }

    }