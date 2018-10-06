











package com.back.vom.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.back.vom.MainActivity;
import com.back.vom.models.Report;
import com.back.vom.services.ReportService;
import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.*;


import com.back.vom.R;
import com.back.vom.adapters.YourReportsAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sign up step one fragment.
 */
public class LocationReports extends Fragment implements OnMapReadyCallback {

    /**
     * The constant TAG for logs.
     */
    private static final String TAG = LocationReports.class.getSimpleName();


    private List<Report> reportData;



    /**
     * The M view to inflate the fragment view .
     */
    private View mView;

    /**
     * Instantiates a new Sign up step one fragment.
     */
    public LocationReports() {
    }

    private GoogleMap mMap;


    RecyclerView mRecyclerView;
    YourReportsAdapter mYourReportsAdapter;
    List<String> data = new ArrayList<>();
    /**
     * This method is used to display the step one fragment for signUp with User id,
     * Email id & Parent's Phone no.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.locationreports, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void setData() {

        ReportService.getMyReports(new ReportService.CompleteListener() {
            @Override
            public void complete(Object object) {
                if(object instanceof  Exception) {

                    Log.d("In Set data: ", "Exception occured!");
                    Toast.makeText(getContext(), "Error while collecting data!", Toast.LENGTH_SHORT).show();

                } else {

                    reportData = (List<Report>) object;

                    for(int i=0;i<reportData.size();i++) {

                        double latitude = reportData.get(0).getLatitude();
                        double longitude = reportData.get(0).getLongitude();


                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude,longitude))
                                .title(reportData.get(0).getCategory())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                    }
                }
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setData();
    }
}