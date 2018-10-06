package com.back.vom.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.back.vom.R;
import com.back.vom.adapters.YourReportsAdapter;
import com.back.vom.models.Report;
import com.back.vom.services.ReportService;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sign up step one fragment.
 */
public class YourReports extends Fragment {


    private static final String TAG = YourReports.class.getSimpleName();

    /**
     * The M view to inflate the fragment view .
     */
    private View mView;

    /**
     * Instantiates a new Sign up step one fragment.
     */
    public YourReports() {
    }


    RecyclerView mYourReportsRecyclerView;

    YourReportsAdapter mYourReportsAdapter;

    private FirebaseFunctions mFunctions;
// ...


    List<Report> data = new ArrayList<>();
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

        mView = inflater.inflate(R.layout.yourreports, container, false);
        mYourReportsRecyclerView = mView.findViewById(R.id.yout_reports_recyclerview);
        mYourReportsAdapter = new YourReportsAdapter(data,getActivity());
        mYourReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mYourReportsRecyclerView.setAdapter(mYourReportsAdapter);

        mFunctions = FirebaseFunctions.getInstance();

        getMyData();
        return mView;
    }











    public void getMyData() {
        ReportService.getMyReports(new ReportService.CompleteListener() {
            @Override
            public void complete(Object object) {
                Log.d(TAG, "complete: ");
                if(object instanceof Exception) {
                    Log.e(TAG, "complete: ", (Throwable) object);
                } else {
                    data.clear();
                    data.addAll((List<Report>) object);
                    for (Report r: data) {
                        Log.d(TAG, "! complete: "+r.getCreatedBy());
                    }


                    mYourReportsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}