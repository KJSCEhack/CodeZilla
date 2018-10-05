package com.back.vom.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.back.vom.R;
import com.back.vom.adapters.YourReportsAdapter;

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

    /**
     * Instantiates a new Sign up step one fragment.
     */
    public LocationReports() {
    }

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
    public void onResume() {
        super.onResume();
    }

}